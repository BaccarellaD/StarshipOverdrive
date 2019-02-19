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

public class PlayerStraightShot extends PlayerWeapon{
	
	private long prevShotTime;
	private long fireRate;
	
	private double xVel;
	private double yVel;
	private double dmg;
	private ImageType imageType;
	
	public PlayerStraightShot(Player player, long fireRate, double xVel, double yVel, double dmg, ImageType imageType){
		super(player);
		
		prevShotTime = -1;
		this.fireRate = fireRate;
		
		this.xVel = xVel;
		this.yVel = yVel;
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
			events.add(getShot(curTime));
			events.add(getShotSound());
		}
		
		return events;
	}

	@Override
	public List<GameEvent<?>> forceShot(long curTime) {
		List<GameEvent<?>> events = new ArrayList<>();
		
		events.add(getShot(curTime));
		events.add(getShotSound());
		
		return events;
	}
	
	private GameEvent<?> getShot(long curTime){
		
		prevShotTime = curTime;
		
		Image img = ImageFactory.getImageFactory().getImage(imageType);
		
		Projectile proj = ProjectilePool.getPool().getProjectile(Projectile.ProjectileType.PLAYER_PROJECTILE,
																	xVel,
																	yVel,
																	dmg,
																	img);
		proj.setX(this.player.getX() + player.getFitWidth()/2d - imageType.getWidth()/2d);
		proj.setY(this.player.getY() + imageType.getHeight());
		
		proj.setFitWidth(imageType.getWidth());
		proj.setFitHeight(imageType.getHeight());
		
		return new GameEvent<Projectile>(EventType.SPAWN_PLAYER_PROJECTILE, proj);
		
	}
	
	private GameEvent<?> getShotSound() {
		return new GameEvent<>(EventType.PLAY_SOUND, SoundType.LASER_1  );
	}
	
	

}
