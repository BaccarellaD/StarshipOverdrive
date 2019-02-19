package sprites.playerWeapons;

import java.util.ArrayList;
import java.util.List;

import controllers.ImageFactory;
import controllers.ImageFactory.ImageType;
import controllers.SoundFXController.SoundType;
import events.GameEvent;
import events.GameEvent.EventType;
import javafx.scene.image.Image;
import sprites.Player;
import sprites.Projectile;
import sprites.ProjectilePool;
import sprites.Projectile.ProjectileType;

public class PlayerShotgun extends PlayerWeapon{
	
	private long prevShotTime;
	private long fireRate;
	
	private double vel;
	private int numShots;
	private double angle;
	private double dmg;
	private ImageType imageType;
	
	public PlayerShotgun(Player player, long fireRate, double vel, int numShots, double angle, double dmg, ImageType imageType){
		super(player);
		
		prevShotTime = -1;
		this.fireRate = fireRate;
		
		this.vel = vel;
		this.numShots = numShots;
		this.angle = angle;
		this.dmg = dmg;
		this.imageType = imageType;
	}

	@Override
	public List<GameEvent<?>> tick(long curTime) {
		List<GameEvent<?>> events = new ArrayList<>();
		
		if(!this.initialTimeSet()){
			this.setInitialTime(curTime);
			return events;
		}
		
		if(curTime - prevShotTime >= fireRate){
			events.addAll(getShot(curTime));
		}
		
		return events;
	}

	@Override
	public List<GameEvent<?>> forceShot(long curTime) {
		List<GameEvent<?>> events = new ArrayList<>();
		
		events.addAll(getShot(curTime));
		
		return events;
	}
	
	private List<GameEvent<?>> getShot(long curTime){
		
		this.prevShotTime = curTime;
		
		List<GameEvent<?>> events = new ArrayList<>();
		
		ImageFactory imgFac = ImageFactory.getImageFactory();
		ProjectileType type = ProjectileType.PLAYER_PROJECTILE;
		Image img = imgFac.getImage(imageType);
		
		double xVal = player.getX() + player.getFitWidth()/2d - imageType.getWidth()/2d;
		double yVal = player.getY() - imageType.getHeight();
		
		Projectile projStraight = ProjectilePool.getPool().getProjectile(type, 0, vel, dmg, img);
		projStraight.setX(xVal);
		projStraight.setY(yVal);
		
		projStraight.setFitHeight(imageType.getHeight());
		projStraight.setFitWidth (imageType.getWidth());
		
		GameEvent<Projectile> eventStraight = new GameEvent<>(EventType.SPAWN_PLAYER_PROJECTILE, projStraight);
		
		events.add(eventStraight);
		
		double angleSum = 0;
		
		for(int i = 0; i < (numShots/2); i++){
			
			angleSum += angle;
			
			double sinTheta = Math.sin(Math.toRadians(angleSum));
			double cosTheta = Math.cos(Math.toRadians(angleSum));
			
			double yVel = cosTheta * vel;
			double xVel = sinTheta * vel;
			
			Projectile projLeft = ProjectilePool.getPool().getProjectile(type, -xVel, yVel, dmg, img);
			projLeft.setX(xVal);
			projLeft.setY(yVal);
			
			projLeft.setFitHeight(imageType.getHeight());
			projLeft.setFitWidth (imageType.getWidth());
			
			Projectile projRight = ProjectilePool.getPool().getProjectile(type, xVel, yVel, dmg, img);
			projRight.setX(xVal);
			projRight.setY(yVal);
			
			projRight.setFitHeight(imageType.getHeight());
			projRight.setFitWidth (imageType.getWidth());
			
			GameEvent<Projectile> eventLeft = new GameEvent<>(EventType.SPAWN_PLAYER_PROJECTILE, projLeft);
			GameEvent<Projectile> eventRight = new GameEvent<>(EventType.SPAWN_PLAYER_PROJECTILE, projRight);
			
			events.add(eventLeft);
			events.add(eventRight);
		}
		
		GameEvent<SoundType>  eventSound 	= new GameEvent<>(EventType.PLAY_SOUND, SoundType.LASER_HUGE);
		events.add(eventSound);
		
		return events;
		
	}
	
}
