package sprites.bulletPatterns;

import java.util.ArrayList;
import java.util.List;

import controllers.ImageFactory;
import controllers.ImageFactory.ImageType;
import controllers.SoundFXController.SoundType;
import events.GameEvent;
import events.GameEvent.EventType;
import javafx.scene.image.Image;
import sprites.Enemy;
import sprites.Projectile;
import sprites.ProjectilePool;
import sprites.Projectile.ProjectileType;

public class StraightShot extends BulletPattern{
	
	long millisSinceLastShot;
	long prev_millis;
	
	double xVel;
	double yVel;
	double dmg;

	public StraightShot(Enemy e, double xVel, double yVel, double dmg, long fireRate) {
		super(e);
		
		this.xVel = xVel;
		this.yVel = yVel;
		this.dmg  = dmg;
		
		this.fireRate = fireRate;
		
		this.setSoundType(SoundType.LASER_LIGHT);
	}

	@Override
	public List<GameEvent<?>> tick(long cur_millis) {
		List<GameEvent<?>> events = new ArrayList<>();
		
		if(!this.initalTimeSet()){
			prev_millis = cur_millis;
			this.setStart_millis(cur_millis);
		}
		
		millisSinceLastShot += cur_millis - prev_millis;
		
		if(millisSinceLastShot >= fireRate){
			
			ImageFactory imgFac = ImageFactory.getImageFactory();
			ProjectileType type = ProjectileType.ENEMY_PROJECTILE;
			Image img = imgFac.getImage(ImageType.ENEMY_PROJECTILE_1);
			
			double xVal = targetEnemy.getX() + targetEnemy.getWidth()/2d - ImageType.ENEMY_PROJECTILE_1.getWidth()/2d;
			double yVal = targetEnemy.getY() + targetEnemy.getHeight();
			
			Projectile proj = ProjectilePool.getPool().getProjectile(type, xVel, yVel, dmg, img);
			proj.setX(xVal);
			proj.setY(yVal);
			
			proj.setFitHeight(ImageType.ENEMY_PROJECTILE_1.getHeight());
			proj.setFitWidth (ImageType.ENEMY_PROJECTILE_1.getWidth());
			
			GameEvent<Projectile> event = new GameEvent<>(EventType.SPAWN_ENEMY_PROJECTILE, proj);
			GameEvent<SoundType>  eventSound 	= new GameEvent<>(EventType.PLAY_SOUND, this.getSoundType());
			
			events.add(event);
			events.add(eventSound);
			
			
			millisSinceLastShot = 0;
		}
		
		prev_millis = cur_millis;
		
		return events;
	}

	
	
}
