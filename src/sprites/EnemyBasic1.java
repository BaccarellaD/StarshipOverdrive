package sprites;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import application.Game;
import controllers.ImageFactory;
import controllers.ImageFactory.ImageType;
import events.GameEvent;
import events.GameEvent.EventType;
import events.GameEvent.GEBuilder;
import javafx.scene.image.Image;
import sprites.Projectile.ProjectileType;
import sprites.bulletPatterns.ArcPattern;
import sprites.bulletPatterns.BulletPattern;
import sprites.bulletPatterns.ShotgunPattern;
import sprites.bulletPatterns.StraightShot;
import sprites.movementPatterns.MovementPattern;
import sprites.movementPatterns.SinWaveMovement;

public class EnemyBasic1 extends Enemy{
	
	private double width = ImageType.ENEMY_SHIP_1.getWidth();
	private double height = ImageType.ENEMY_SHIP_1.getHeight();
	
	public final double PROJ_HEIGHT = 25.0;
	public final double PROJ_WIDTH  = 25.0;
	
	public double health;
	
	private long last_millis;
	private long start_millis;
	
	private double startX;
	private double startY;
	
	public double DAMAGE = 10.0;
	public double PROJECTILE_SPEED = .50;
	
	private BulletPattern bulletPattern;
	private MovementPattern movementPattern;
	
	private ImageFactory imgFac = ImageFactory.getImageFactory();
	
	public EnemyBasic1(double startX, double startY, double health){
		Image basicEnemyImage = imgFac.getImage(ImageType.ENEMY_SHIP_1);
		this.setImage(basicEnemyImage);
		
		this.health = health;
		
		this.setX(startX);
		this.setY(startY);
		
		this.startX = startX;
		this.startY = startY;
		
		this.setFitHeight(width);
		this.setFitWidth(height);
		
		bulletPattern = new StraightShot(this, 0, PROJECTILE_SPEED, DAMAGE, 1200);
		//bulletPattern = new ShotgunPattern(this, PROJECTILE_SPEED, 15, 5, DAMAGE);
		//bulletPattern = new ArcPattern(this, PROJECTILE_SPEED, 60, 8, 5, 1200, 1000);
		movementPattern = new SinWaveMovement(this, startX, 150, 4000);
	}

	@Override
	public List<GameEvent<?>> tick(long cur_millis) {
		
		if(start_millis == 0){
			start_millis = cur_millis;
		}
		
		movementPattern.move(cur_millis);
		this.last_millis = cur_millis;
		
		List<GameEvent<?>> events = detectHits(Game.getGame().getLevel().getPlayerProjectiles());
		events.addAll(movementPattern.move(cur_millis));
		events.addAll(bulletPattern.tick(cur_millis));
		
		return events;
	}

	private List<GameEvent<?>> detectHits(List<Projectile> playerProjectiles) {
		
		List<GameEvent<?>> events = new ArrayList<>();
		
		for(Projectile pp: playerProjectiles){
			if(this.getLayoutBounds().intersects(pp.getBoundsInParent())){
				health -= pp.getDamage();
				if(health <= 0){
					events.add(GEBuilder.killEnemy(this));
					events.add(new GameEvent<Long>(EventType.ADD_POINTS, points));
				}
				events.add(new GameEvent<Projectile>(EventType.REMOVE_PLAYER_PROJECTILE, pp));
				events.add(new GameEvent<Enemy>(EventType.DAMAGE_ENEMY, this));
				break; //if there was a hit, end loop
			}
		}
		
		return events;
	}
	
	@Override
	public void setMovementPattern(MovementPattern mp){
		this.movementPattern = mp;
	}

	@Override
	public double getHeight() {
		return height;
	}

	@Override
	public double getWidth() {
		return width;
	}

	@Override
	public void setBulletPattern(BulletPattern pattern) {
		this.bulletPattern = pattern;
	}

}
