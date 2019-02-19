package sprites.bulletPatterns;

import java.util.ArrayList;
import java.util.Arrays;
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

public class ShotgunPattern extends BulletPattern{

	long millisSinceLastShot;
	long prev_millis;
	
	double vel;
	double angle;
	double dmg;
	int projectileAmt;

	public ShotgunPattern(Enemy e, double vel, double angle, int amt, double dmg, long fireRate) {
		super(e); 
		
		this.vel = vel;
		this.dmg  = dmg;
		this.angle = angle;
		this.projectileAmt = amt;
		
		this.fireRate = fireRate;
		
		this.setSoundType(SoundType.LASER_HUGE);
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
			
			Projectile projStraight = ProjectilePool.getPool().getProjectile(type, 0, vel, dmg, img);
			projStraight.setX(xVal);
			projStraight.setY(yVal);
			
			projStraight.setFitHeight(ImageType.ENEMY_PROJECTILE_1.getHeight());
			projStraight.setFitWidth (ImageType.ENEMY_PROJECTILE_1.getWidth());
			
			GameEvent<Projectile> eventStraight = new GameEvent<>(EventType.SPAWN_ENEMY_PROJECTILE, projStraight);
			
			events.add(eventStraight);
			
			double angleSum = 0;
			
			for(int i = 0; i < (projectileAmt/2); i++){
				
				angleSum += angle;
				
				double sinTheta = Math.sin(Math.toRadians(angleSum));
				double cosTheta = Math.cos(Math.toRadians(angleSum));
				
				double yVel = cosTheta * vel;
				double xVel = sinTheta * vel;
				
				Projectile projLeft = ProjectilePool.getPool().getProjectile(type, -xVel, yVel, dmg, img);
				projLeft.setX(xVal);
				projLeft.setY(yVal);
				
				projLeft.setFitHeight(ImageType.ENEMY_PROJECTILE_1.getHeight());
				projLeft.setFitWidth (ImageType.ENEMY_PROJECTILE_1.getWidth());
				
				Projectile projRight = ProjectilePool.getPool().getProjectile(type, xVel, yVel, dmg, img);
				projRight.setX(xVal);
				projRight.setY(yVal);
				
				projRight.setFitHeight(ImageType.ENEMY_PROJECTILE_1.getHeight());
				projRight.setFitWidth (ImageType.ENEMY_PROJECTILE_1.getWidth());
				
				GameEvent<Projectile> eventLeft = new GameEvent<>(EventType.SPAWN_ENEMY_PROJECTILE, projLeft);
				GameEvent<Projectile> eventRight = new GameEvent<>(EventType.SPAWN_ENEMY_PROJECTILE, projRight);
				
				events.add(eventLeft);
				events.add(eventRight);
			}
			
			GameEvent<SoundType>  eventSound 	= new GameEvent<>(EventType.PLAY_SOUND, this.getSoundType());
			events.add(eventSound);
			
			millisSinceLastShot = 0;
		}
		
		prev_millis = cur_millis;
		
		return events;
	}

}
