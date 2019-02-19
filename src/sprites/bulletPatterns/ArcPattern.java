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
import sprites.Projectile.ProjectileType;
import sprites.ProjectilePool;

public class ArcPattern extends BulletPattern{

	long millisSinceLastShot;
	long prev_millis;
	
	double vel;
	double angle;
	double dmg;
	int projectileAmt;
	
	long shotDuration;
	long pauseDuration;
	long cycleDuration;
	
	boolean reverse;

	public ArcPattern(Enemy e, double vel, double angle, int amt, double dmg, long shotDuration, long pauseDuration) {
		super(e); 
		
		this.vel = vel;
		this.dmg  = dmg;
		this.angle = angle;
		this.projectileAmt = amt;
		
		this.shotDuration =  shotDuration;
		this.pauseDuration = pauseDuration;
		
		this.fireRate = shotDuration/amt;
		this.cycleDuration = shotDuration + pauseDuration;
		
		reverse = false;
		
		this.setSoundType(SoundType.LASER_TINNY);
	}

	@Override
	public List<GameEvent<?>> tick(long cur_millis) {
		List<GameEvent<?>> events = new ArrayList<>();
		
		if(!this.initalTimeSet()){
			prev_millis = cur_millis;
			this.setStart_millis(cur_millis);
		}
		
		millisSinceLastShot += cur_millis - prev_millis;
		
		long timeInCycle = cur_millis - this.start_millis;
		
		if(millisSinceLastShot >= fireRate && timeInCycle <= shotDuration){
			
			ImageFactory imgFac = ImageFactory.getImageFactory();
			ProjectileType type = ProjectileType.ENEMY_PROJECTILE;
			Image img = imgFac.getImage(ImageType.ENEMY_PROJECTILE_1);
			
			double xVal = targetEnemy.getX() + targetEnemy.getWidth()/2d - ImageType.ENEMY_PROJECTILE_1.getWidth()/2d;
			double yVal = targetEnemy.getY() + targetEnemy.getHeight();
				
			long   roundNumber = (timeInCycle/fireRate);
			double roundAngle = roundNumber*(angle/projectileAmt) - angle/2d;
			
			double sinTheta = Math.sin(Math.toRadians(roundAngle));
			double cosTheta = Math.cos(Math.toRadians(roundAngle));
		
			double yVel = cosTheta * vel;
			double xVel = sinTheta * vel;
			
			if(reverse){
				xVel = -xVel;
			}
			
			Projectile proj = ProjectilePool.getPool().getProjectile(type, -xVel, yVel, dmg, img);
			proj.setX(xVal);
			proj.setY(yVal);
			
			proj.setFitHeight(ImageType.ENEMY_PROJECTILE_1.getHeight());
			proj.setFitWidth (ImageType.ENEMY_PROJECTILE_1.getWidth());
			
			GameEvent<Projectile> event 		= new GameEvent<>(EventType.SPAWN_ENEMY_PROJECTILE, proj);
			GameEvent<SoundType>  eventSound 	= new GameEvent<>(EventType.PLAY_SOUND, this.getSoundType());
			events.add(event);
			events.add(eventSound);
			
			millisSinceLastShot = 0;
		}
		else if(timeInCycle >= cycleDuration){
			this.setStart_millis(cur_millis);
			reverse = !reverse;
		}
		
		prev_millis = cur_millis;
		
		return events;
	}
	
}
