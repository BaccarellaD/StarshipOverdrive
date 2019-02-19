package sprites.special;

import java.util.ArrayList;
import java.util.List;

import controllers.ImageFactory;
import controllers.ImageFactory.ImageType;
import events.EventWave;
import events.GameEvent;
import events.GameEvent.EventType;
import events.TimedEvent;
import javafx.scene.image.Image;
import sprites.Projectile;
import sprites.Projectile.ProjectileType;
import sprites.ProjectilePool;
import sprites.Sprite;

public class ParticleEmitter extends Sprite{
	
	private EventWave particleStack;
	
	ImageType imgType;
	Image img;
	
	long startTime = -1;
	long prevTime = -1;
	long lastParticle = -1;
	long lastTimeInterval = -1;
	long timeInterval;
	
	double radius;
	double offset;
	double velocity;
	double particlesPerSec;
	
	public ParticleEmitter(double x, double y, double radius, double offset, double particlesPerSec, double velocity, ImageType type){
		this.setX(x);
		this.setY(y);
		this.radius = radius;
		this.offset = offset;
		this.particlesPerSec = particlesPerSec;
		
		this.velocity = velocity;
		
		this.imgType = type;
		this.img = ImageFactory.getImageFactory().getImage(type);
		
		timeInterval = (long)(1000d/particlesPerSec); //time between particles if constant
		
		particleStack = new EventWave();
		
		renewParticleStack();
	}

	private void renewParticleStack() {
		
		particleStack.clearEvents();
		
		double particleAngle = Math.random()*radius + offset;

		double sinTheta = Math.sin(Math.toRadians(particleAngle));
		double cosTheta = Math.cos(Math.toRadians(particleAngle));
		
		double yVel = cosTheta * velocity;
		double xVel = sinTheta * velocity;
		
		Projectile proj = ProjectilePool.getPool().getProjectile(ProjectileType.DECOR_PROJECTILE, xVel, yVel, 0, img);
		
		proj.setX(this.getX());
		proj.setY(this.getY());
		
		proj.setFitHeight(imgType.getHeight());
		proj.setFitWidth(imgType.getWidth());
		
		GameEvent<Projectile> ge = new GameEvent<>(EventType.SPAWN_PROJECTILE, proj);
		
		long eventTime = (long)(Math.random()*timeInterval);
		TimedEvent te = new TimedEvent(ge, eventTime);
		
		particleStack.addTimedEvent(te);
		
	}

	@Override
	public List<GameEvent<?>> tick(long cur_millis) {
		
		List<GameEvent<?>> particles = new ArrayList<GameEvent<?>>();
		
		if(this.startTime == -1){
			startTime 			= cur_millis;
			prevTime 			= cur_millis;
			lastParticle 		= cur_millis;
			lastTimeInterval 	= cur_millis;
			
			return particles;
		}
		
		List<GameEvent<?>> p = particleStack.tick(cur_millis);
		if(!p.isEmpty()){
			particles.addAll(p);
		}
		
		if(cur_millis - lastTimeInterval >= timeInterval){ //its a new second
			lastTimeInterval = cur_millis;
			renewParticleStack();
		}
		
		return particles;
	}

}
