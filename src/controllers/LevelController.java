package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import application.Game;
import backgrounds.GameBackground;
import controllers.ImageFactory.ImageType;
import controllers.KeyPressController.PlayerActionEvent;
import controllers.SoundFXController.SoundType;
import events.GameEvent;
import events.TargetedSpawnEvent;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import menus.PauseMenu;
import sprites.Enemy;
import sprites.Player;
import sprites.Projectile;
import sprites.Projectile.ProjectileType;
import sprites.ProjectilePool;
import sprites.ui.DialogPopup;
import sprites.ui.HealthBar;
import sprites.ui.PointReadout;

public abstract class LevelController extends Pane{
	
	Pane guiPane;
	Pane levelPane;
	
	AnimationController animationController;
	
	private boolean paused;
	private boolean firstUnpausedFrame;
	private long    pauseTimeTotal;
	private long 	pauseStartTime;
	
	private PauseMenu pauseMenu;
	
	private boolean waveBlock;
	
	Image playerSprite;
	
	HealthBar healthBar;
	PointReadout scoreBoard;
	
	Player player;
	List<Projectile> playerProjectiles;
	List<Projectile> enemyProjectiles;
	List<Enemy> enemies;
	List<DialogPopup> popups;
	
	AnimationTimer animationTimer;
	
	long start_millis;
	private long gameTime;
	
	public static final double PLAYER_XVEL = .30;
	public static final double PLAYER_YVEL = .30;
	
	private final double PLAYER_LASER_HEIGHT = 25;
	private final double PLAYER_LASER_WIDTH  = 25;
	
	private ImageFactory imgFac;
	
	private SoundFXController soundPlayer;
	
	public LevelController(){
		
		soundPlayer = new SoundFXController();
		
		guiPane = new Pane();
		levelPane = new Pane();
		
		guiPane.setMinWidth(Game.WINDOW_WIDTH);
		guiPane.setMinHeight(Game.WINDOW_HEIGHT);
		levelPane.setMinWidth(Game.WINDOW_WIDTH);
		levelPane.setMinHeight(Game.WINDOW_HEIGHT);
		
		animationController = new AnimationController();
		
		imgFac = ImageFactory.getImageFactory();
		
		playerSprite = imgFac.getImage(ImageType.PLAYER_SHIP_1);
		this.start_millis = System.currentTimeMillis();
		this.player = new Player(100d, start_millis, playerSprite, Game.WINDOW_WIDTH/2d, Game.WINDOW_HEIGHT/2d);
		playerProjectiles = new ArrayList<>();
		enemyProjectiles  = new ArrayList<>();
		enemies			  = new ArrayList<>();
		
		popups			  = new ArrayList<>();
		
		levelPane.getChildren().add(player);
		this.getChildren().add(levelPane);
		this.getChildren().add(guiPane);
		
		this.waveBlock = false;
		
		initializeGui();
		
		initializeBackground();
		
		initializeLayout();
		
		initializeGameLoop();
		
		this.paused = false;
	}
	
	private void initializeGui() {
		pauseMenu = new PauseMenu();
		
		healthBar = new HealthBar(player.getHealth(), false);
		
		int offset = 10;
		double scale = 2;
		
		healthBar.setTranslateY(Game.WINDOW_HEIGHT - ImageType.HEALTHBAR_EMPTY.getHeight()*scale - offset);
		healthBar.setTranslateX(offset + 50);
		healthBar.setScaleX(scale);
		healthBar.setScaleY(scale);
		
		scoreBoard = new PointReadout();

		guiPane.getChildren().addAll(healthBar, scoreBoard);
	}

	abstract void initializeBackground();
	
	abstract GameBackground getLevelBackground();

	private void initializeLayout() {
		
		levelPane.setMinHeight(Game.WINDOW_HEIGHT);
		levelPane.setMinWidth(Game.WINDOW_WIDTH);
	}

	public void addEnemy(Enemy e){
		levelPane.getChildren().add(e);
		this.enemies.add(e);
	}
	
	public void addEnemyAnimate(Enemy e){
		levelPane.getChildren().add(e);
		this.enemies.add(e);
		animationController.setEnemySpawnAnimation(e);
	}
	
	public void addEnemyProjectile(Projectile p){
		levelPane.getChildren().add(p);
		this.enemyProjectiles.add(p);
	}
	
	public void addPlayerProjectile(Projectile p){
		levelPane.getChildren().add(p);
		this.playerProjectiles.add(p);
	}
	
	public void removePlayerProjectile(Projectile p){
		levelPane.getChildren().remove(p);
		this.playerProjectiles.remove(p);
		ProjectilePool.getPool().returnProjectile(p);
	}
	
	public void removeEnemyProjectile(Projectile p){
		levelPane.getChildren().remove(p);
		this.enemyProjectiles.remove(p);
		ProjectilePool.getPool().returnProjectile(p);
	}
	
	private void killEnemy(Enemy enemy) {
		removeEnemy(enemy);
		if(enemy.hasDeathRattleEvents()) {
			System.out.println("Spawning death rattle events");
			handleGameEvents(enemy.getDeathRattleEvents());
		}
	}
	
	private void removeEnemy(Enemy enemy) {
		System.out.println("Enemy Removed");
		levelPane.getChildren().remove(enemy);
		this.enemies.remove(enemy);

	}

	public void setPlayerAction(PlayerActionEvent event) {
		
		if(event == PlayerActionEvent.MOVE_UP){
			player.setYVel(-PLAYER_YVEL);
		}
		else if(event == PlayerActionEvent.MOVE_DOWN){
			player.setYVel(PLAYER_YVEL);
		}
		else if(event == PlayerActionEvent.MOVE_LEFT){
			player.setXVel(-PLAYER_XVEL);
		}
		else if(event == PlayerActionEvent.MOVE_RIGHT){
			player.setXVel(PLAYER_XVEL);
		}
		else if(event == PlayerActionEvent.STOP_VERT){
			player.setYVel(0);
		}
		else if(event == PlayerActionEvent.STOP_HORZ){
			player.setXVel(0);
		}
		else if(event == PlayerActionEvent.FIRE){
			player.setFiring(true);
			handleGameEvents(player.getWeapon().forceShot(gameTime));
		}
		else if(event == PlayerActionEvent.STOP_FIRING){
			player.setFiring(false);
		}
		else if(event == PlayerActionEvent.SPRINT){
			if(!player.isSprinting()) soundPlayer.playSound(SoundType.DASH_1);
			player.sprint();
		}
		else if(event == PlayerActionEvent.SHIELD_UP) {
			player.shieldUp();
		}
		else if(event == PlayerActionEvent.SHIELD_DOWN) {
			player.sheildDown();
		}
		
	}
	
	private void firePlayerProjectile() {
		Image lazerImage = imgFac.getImage(ImageType.PLAYER_PROJECTILE_1);
		
		Projectile newPP = ProjectilePool.getPool().getProjectile(ProjectileType.PLAYER_PROJECTILE, 0, -1.5, 10.0, lazerImage);
		
		newPP.setFitHeight(PLAYER_LASER_HEIGHT);
		newPP.setFitWidth(PLAYER_LASER_WIDTH);
		
		newPP.setX(player.getX() + Player.WIDTH/2d - PLAYER_LASER_WIDTH/2d);
		newPP.setY(player.getY() + PLAYER_LASER_HEIGHT);
		
		soundPlayer.playSound(SoundType.LASER_1);
		
		addPlayerProjectile(newPP);
	}

	private void initializeGameLoop(){
		
		this.animationTimer = new AnimationTimer(){

			@Override
			public void handle(long currentNanoTime) {
				gameTime = TimeUnit.NANOSECONDS.toMillis(currentNanoTime) - pauseTimeTotal;
				
				if(firstUnpausedFrame){
					pauseTimeTotal += gameTime - pauseStartTime;
					System.out.println("pauseTotalTime: " + pauseTimeTotal);
					firstUnpausedFrame = false;
					return;
				}
				
				List<GameEvent<?>> backgroundGameEvents = getLevelBackground().tick(gameTime);
				
				List<GameEvent<?>> levelGameEvents = tick(gameTime);
				
				List<GameEvent<?>> playerGameEvents = player.tick(gameTime);
				
				//Flatten popupEvents
				List<GameEvent<?>> popEvents = new ArrayList<>();
				
				for(DialogPopup popup: popups){
					List<GameEvent<?>> popupEvents = popup.tick(gameTime);
					if(!popupEvents.isEmpty()){
						popEvents.addAll(popupEvents);
					}
				}
				
				//Flatten ppEvents
				List<GameEvent<?>> ppEvents = new ArrayList<>();
				
				for(Projectile proj: playerProjectiles){
					List<GameEvent<?>> projEvents = proj.tick(gameTime);
					if(!projEvents.isEmpty()){
						ppEvents.addAll(projEvents);
					}
				}
				
				//Flatten epEvents
				List<GameEvent<?>> epEvents = new ArrayList<>();
				
				for(Projectile proj: enemyProjectiles){
					List<GameEvent<?>> projEvents = proj.tick(gameTime);
					if(!projEvents.isEmpty()){
						epEvents.addAll(projEvents);
					}
				}
				
				//Flatten enemyEvents
				List<GameEvent<?>> enemyEvents = new ArrayList<>();
				
				for(Enemy enemy: enemies){
					List<GameEvent<?>> eEvents = enemy.tick(gameTime);
					if(!eEvents.isEmpty()){
						enemyEvents.addAll(eEvents);
					}
				}
				
				List<GameEvent<?>> allGameTickEvents = new ArrayList<>();
				allGameTickEvents.addAll(backgroundGameEvents);
				allGameTickEvents.addAll(levelGameEvents);
				allGameTickEvents.addAll(playerGameEvents);
				allGameTickEvents.addAll(popEvents);
				allGameTickEvents.addAll(enemyEvents);
				allGameTickEvents.addAll(ppEvents);
				allGameTickEvents.addAll(epEvents);
				
				handleGameEvents(allGameTickEvents);
			}
			
		};
		
		animationTimer.start();
		
	}
	
	
	public void handlePause(){
		if(paused){
			this.getChildren().remove(pauseMenu);
			guiPane.setBackground(null);
			this.firstUnpausedFrame = true;
			AnimationController.resumeTimelines();
			this.animationTimer.start();
			Game.getMusicPlayer().play();
		}else{
			this.animationTimer.stop();
			AnimationController.pauseTimelines();
			this.pauseStartTime = gameTime;
			this.getChildren().add(pauseMenu);
			Game.getMusicPlayer().pause();
			System.gc();
		}
		
		paused = !paused;
	}
	
	private void handleGameEvents(List<GameEvent<?>> gameEvents){
		for(GameEvent<?> event: gameEvents){
			handleGameEvent(event);
		}
	}
	
	
	public void handleGameEvent(GameEvent<?> event) {
		
		switch(event.getEventType()){
			case REMOVE_PLAYER_PROJECTILE: 	removePlayerProjectile((Projectile)event.getData());
				break;
			case REMOVE_ENEMY_PROJECTILE:  	removeEnemyProjectile((Projectile)event.getData());
				break;
			case REMOVE_ENEMY: 				removeEnemy((Enemy)event.getData());
				break;
			case SPAWN_ENEMY:				addEnemy((Enemy)event.getData());
				break;
			case SPAWN_ENEMY_ANIMATE:		addEnemyAnimate((Enemy)event.getData());
				break;
			case SPAWN_TARGETED:			handleGameEvent(((TargetedSpawnEvent)event.getData()).triggerSpawn());
				break;
			case SPAWN_ENEMY_PROJECTILE:	addEnemyProjectile((Projectile)event.getData());
				break;
			case SPAWN_PLAYER_PROJECTILE:	addPlayerProjectile((Projectile)event.getData());
				break;
			case KILL_ENEMY:				killEnemy((Enemy)event.getData());
				break;
			case KILL_PLAYER:				endSequence();
				break;
			case DAMAGE_PLAYER:				healthBar.setHealth(player.getHealth());
				break;
			case DAMAGE_ENEMY:				animationController.setDamageAnimation((Enemy)event.getData());
				break;
			case ADD_POINTS:				scoreBoard.addPoints((long)event.getData());
				break;
			case PLAY_SOUND:				soundPlayer.playSound((SoundType)event.getData());
				break;
			case SET_WAVE_BLOCK:			this.setWaveBlock((boolean)event.getData());
				break;
			case PLACE_POPUP:				this.placePopup((DialogPopup)event.getData());
				break;
			case REMOVE_POPUP:				this.removePopup((DialogPopup)event.getData());
				break;
			case UI_SET_SHIELD_UP:			healthBar.setShield((boolean)event.getData());
				break;
			case UI_SET_SHIELD_PERCENT:		healthBar.setShieldPercent((double)event.getData());
				break;
			default:						throw new UnsupportedOperationException();
		}
	}

	private void removePopup(DialogPopup popup) {
		System.out.println("popup removed");
		popups.remove(popup);
		this.guiPane.getChildren().remove(popup);
	}

	private void placePopup(DialogPopup popup) {
		System.out.println("popup placed");
		popups.add(popup);
		this.guiPane.getChildren().add(popup);
		if(!popup.initEventsEmpty()){
			handleGameEvents(popup.getInitEvents());
		}
	}

	public void setWaveBlock(boolean waveBlock) {
		this.waveBlock = waveBlock;
		if(waveBlock){
			System.out.println("Blocking Wave");
			player.setFiring(false);
		}
	}
	
	public boolean isWaveBlocked(){
		return waveBlock;
	}
	
	public void stopPlayerActions(){
		player.setFiring(false);
		player.setYVel(0);
		player.setXVel(0);
	}

	private void endSequence() {
		
		stop();
		
		Label endGameLabel = new Label("GAME OVER");
		levelPane.getChildren().add(endGameLabel);
		levelPane.getChildren().remove(player);
		endGameLabel.applyCss();
		endGameLabel.getParent().layout();
		endGameLabel.setTranslateX(Game.WINDOW_WIDTH/2d - endGameLabel.getWidth()/2d);
		endGameLabel.setTranslateY(Game.WINDOW_HEIGHT/2d - endGameLabel.getHeight()/2d);
		
		
	}
	/**
	 * Used to update the level with the game time
	 * @param gameTime current counter time
	 * @return game events to perform
	 */
	public abstract List<GameEvent<?>> tick(long gameTime);

	public List<Projectile> getPlayerProjectiles() {
		return playerProjectiles;
	}

	public List<Projectile> getEnemyProjectiles() {
		return enemyProjectiles;
	}
	
	public List<Enemy> getEnemies() {
		return enemies;
	}

	public long getGameTime() {
		return gameTime;
	}
	
	public boolean stageClear(){
		return enemies.isEmpty();
	}

	public void stop() {
		animationTimer.stop();
	}
	
	
}
