package sprites;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import application.Game;
import controllers.AnimationController;
import controllers.ImageFactory.ImageType;
import events.GameEvent;
import events.GameEvent.EventType;
import events.GameEvent.GEBuilder;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.shape.Polygon;
import sprites.playerSpecial.PlayerShield;
import sprites.playerWeapons.PlayerShotgun;
import sprites.playerWeapons.PlayerStraightShot;
import sprites.playerWeapons.PlayerWeapon;

public class Player extends Sprite{
	
	public static final double WIDTH = ImageType.PLAYER_SHIP_1.getWidth();
	public static final double HEIGHT = ImageType.PLAYER_SHIP_1.getHeight();
	
	private AnimationController animationController;
	
	private PlayerWeapon weapon;
	private boolean isFiring;
	
	private PlayerShield shield;
	
	private double health;
	private long last_millis;
	
	private boolean sprinting;
	private boolean blurSet;
	
	private static long 	SPRINT_DURATION = 175;
	private static double	SPRINT_MULTIPLIER = 3.0;
	
	public Player(double health, long last_millis, Image sprite,  double startX, double startY){
		
		animationController = new AnimationController();
		
		weapon = new PlayerStraightShot(this, 200, 0, -1.25, 10, ImageType.PLAYER_PROJECTILE_1);
		//weapon = new PlayerShotgun(this, 500, -.75, 5, 15d, 10, ImageType.PLAYER_PROJECTILE_1);
		
		shield = new PlayerShield(150, 500, 60d, this);
		
		this.health = health;
		this.last_millis = last_millis;
		this.setImage(sprite);
		
		this.setX(startX);
		this.setY(startY);
		
		this.setFitHeight(HEIGHT);
		this.setFitWidth(WIDTH);
		
		sprinting = false;
	}
	

	@Override
	public List<GameEvent<?>> tick(long cur_millis) {
		this.move(cur_millis);
		this.last_millis = cur_millis;
		
		List<GameEvent<?>> events = new ArrayList<>();
		
		List<GameEvent<?>> shieldEvents = shield.tick(cur_millis);
		if(!shieldEvents.isEmpty()) {
			events.addAll(shieldEvents);
		}
		
		List<GameEvent<?>> hitEvents = detectHits(Game.getGame().getLevel().getEnemyProjectiles());
		if(!hitEvents.isEmpty()){
			events.addAll(hitEvents);
		}
		
		List<GameEvent<?>> collisionEvents = detectEnemyCollisions(Game.getGame().getLevel().getEnemies());
		if(!collisionEvents.isEmpty()){
			events.addAll(collisionEvents);
		}
		
		if(isFiring){
			List<GameEvent<?>> shotEvents = weapon.tick(cur_millis);
			if(!shotEvents.isEmpty()){
				events.addAll(shotEvents);
			}
		}
		
		return events;
	}


	private void move(long cur_millis) {
		long dt = cur_millis - last_millis;
		
		if(sprinting && !blurSet){
			animationController.setMotionBlur(this, 150d);
			blurSet = true;
		}
		
		double sprintMult = (sprinting)? SPRINT_MULTIPLIER : 1;
		
		this.setX(this.getX() + dt * this.xVel * sprintMult);
		this.setY(this.getY() + dt * this.yVel * sprintMult);
		
		//Window bound check
		
		//left check
		if(this.getX() < 0){
			this.setX(0);
		}
		//right check
		if(this.getX() + WIDTH > Game.WINDOW_WIDTH){
			this.setX(Game.WINDOW_WIDTH - WIDTH);
		}
		//top check
		if(this.getY() < 0){
			this.setY(0);
		}
		//bottom check
		if(this.getY() + HEIGHT > Game.WINDOW_HEIGHT){
			this.setY(Game.WINDOW_HEIGHT - HEIGHT);
		}
	}
	
	private List<GameEvent<?>> detectHits(List<Projectile> enemyProjectiles) {
		
		List<GameEvent<?>> events = new ArrayList<>();
		
		for(Projectile ep: enemyProjectiles){
			
			if(this.getLayoutBounds().intersects(ep.getBoundsInParent())){
				
				double projMaxX = ep.getBoundsInParent().getMaxX();
				double projMinX = ep.getBoundsInParent().getMinX();
				double thisMinX = this.getLayoutBounds().getMinX();
				double thisMaxX = this.getLayoutBounds().getMinX();
				double projMaxY = ep.getBoundsInParent().getMaxY();
				double thisMaxY = this.getLayoutBounds().getMaxY();
				
				boolean inTopLeft = (projMaxX < thisMinX +HEIGHT*.35) && (projMaxY < thisMaxY-HEIGHT/3d);
				boolean inTopRight =  (projMinX > thisMaxX-HEIGHT*.35) && (projMaxY < thisMaxY-HEIGHT/3d);
				
				if(!inTopLeft && !inTopRight){	//filter in place of a better hit box
					if(!shield.isShielded()) {
						health -= ep.getDamage();
						if(health <= 0){
							events.add(new GameEvent<Player>(EventType.KILL_PLAYER, this));
						}
						animationController.setDamageAnimation(this);
						events.add(new GameEvent<Player>(EventType.DAMAGE_PLAYER, this));
					}
					else {
						shield.addOvercharge(ep.getDamage());
						animationController.setShieldedDamageAnimation(this);
					}
					events.add(new GameEvent<Projectile>(EventType.REMOVE_ENEMY_PROJECTILE, ep));
					
					break; //if there was a hit, end loop
				}
			}
		}
		
		return events;
	}
	
	private List<GameEvent<?>> detectEnemyCollisions(List<Enemy> enemies) {
		
		List<GameEvent<?>> events = new ArrayList<>();
		
		for(Enemy enemy: enemies){
			
			double enemyMaxX = enemy.getX() + enemy.getWidth();
			double enemyMinX = enemy.getX();
			double thisMinX =  this.getLayoutBounds().getMinX();
			double thisMaxX =  this.getLayoutBounds().getMinX();
			double enemyMinY = enemy.getY();
			double enemyMaxY = enemy.getY() + enemy.getHeight();
			double thisMaxY =  this.getLayoutBounds().getMaxY();
			
			if(this.getLayoutBounds().intersects(enemyMinX, enemyMinY, enemy.getWidth(), enemy.getHeight())){
				
				boolean inTopLeft = (enemyMaxX < thisMinX +HEIGHT*.35) && (enemyMaxY < thisMaxY-HEIGHT/3d);
				boolean inTopRight =  (enemyMinX > thisMaxX-HEIGHT*.35) && (enemyMaxY < thisMaxY-HEIGHT/3d);
				
				if(!inTopLeft && !inTopRight){	//filter in place of a better hit box
					if(!shield.isShielded()) {
						health -= enemy.getCollisionDamage();
						if(health <= 0){
							events.add(new GameEvent<Player>(EventType.KILL_PLAYER, this));
						}
						animationController.setDamageAnimation(this);
						events.add(new GameEvent<Player>(EventType.DAMAGE_PLAYER, this));
					}
					else {
						shield.addOvercharge(enemy.getCollisionDamage());
						animationController.setShieldedDamageAnimation(this);
					}
					events.add(GEBuilder.removeEnemy(enemy));
					
					break; //if there was a hit, end loop
				}
			}
		}
		
		return events;
	}


	public void sprint() {
		if(!sprinting){
			sprinting = true;
			
			if(xVel != 0 || yVel != 0){
				animationController.setMotionBlur(this, 150d);
				blurSet = true;
			}
			
		    Timer timer = new Timer();
		    
		    Sprite player = this;
		    
		    //Run a new thread with the task
		    TimerTask delayedThreadStartTask = new TimerTask() {
		        @Override
		        public void run() {
		            new Thread(new Runnable() {
		                @Override
		                public void run() {
		                	animationController.removeEffect(player);
		                	sprinting =  false; //set the value to false
		                	blurSet = false;
		                }
		            }).start();
		        }
		    };
		    
		    timer.schedule(delayedThreadStartTask, SPRINT_DURATION);
		}
	}
	
	public void setWeapon(PlayerWeapon weapon){
		this.weapon = weapon;
	}

	public void setFiring(boolean isFiring){
		this.isFiring = isFiring;
	}

	public double getHealth() {
		return health;
	}
	
	public void shieldUp() {
		shield.shieldUp();
	}
	
	public void sheildDown() {
		shield.shieldDown();
	}

	public boolean isSprinting() {
		return sprinting;
	}


	public PlayerWeapon getWeapon() {
		return weapon;
	}

}
