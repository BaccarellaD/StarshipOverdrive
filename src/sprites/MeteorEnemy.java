package sprites;

import java.util.ArrayList;
import java.util.List;

import application.Game;
import controllers.ImageFactory;
import controllers.ImageFactory.ImageType;
import events.GameEvent;
import events.GameEvent.EventType;
import events.GameEvent.GEBuilder;
import sprites.bulletPatterns.BulletPattern;
import sprites.bulletPatterns.BulletlessPattern;
import sprites.movementPatterns.MovementPattern;
import sprites.movementPatterns.StationaryMovement;

public class MeteorEnemy extends Enemy{
	
	private long rotationTime;
	private long lastFullRotTime;
	
	private MovementPattern movementPattern;
	private BulletPattern bulletPattern;
	
	private double health;

	public MeteorEnemy(long rotationTime, double health) {
		this.rotationTime = rotationTime;
		this.lastFullRotTime = -1;
		
		this.health = health;
		
		movementPattern = new StationaryMovement(this);
		bulletPattern = new BulletlessPattern(this);
		
		this.setImage(ImageFactory.getImageFactory().getImage(ImageType.METEOR_1_LARGE));
	}
	
	public MeteorEnemy(double x, double y, long rotationTime, double health) {
		this(rotationTime, health);
		this.setX(x);
		this.setY(y);
	}

	@Override
	public double getHeight() {
		return height;
	}

	@Override
	public double getWidth() {
		return width;
	}
	
	public void setWidth(double width) {
		this.width = width;
		this.setFitWidth(width);
	}
	
	public void setHeight(double height) {
		this.height = height;
		this.setFitHeight(height);
	}

	@Override
	public void setBulletPattern(BulletPattern pattern) {
		bulletPattern = pattern;
	}

	@Override
	public List<GameEvent<?>> tick(long cur_millis) {
		
		List<GameEvent<?>> events = new ArrayList<>();
		
		List<GameEvent<?>> movmentEvents = movementPattern.move(cur_millis);
		List<GameEvent<?>> bulletEvents = bulletPattern.tick(cur_millis);
		List<GameEvent<?>> hitEvents = detectHits(Game.getGame().getLevel().getPlayerProjectiles());
		
		if(!movmentEvents.isEmpty()) events.addAll(movmentEvents);
		if(!bulletEvents.isEmpty())  events.addAll(bulletEvents);
		if(!hitEvents.isEmpty())	 events.addAll(hitEvents);
		
		setRotation(cur_millis);
		
		return events;
	}

	@Override
	public void setMovementPattern(MovementPattern mp) {
		movementPattern = mp;
	}
	
	private void setRotation(long curMillis) {
		
		if(lastFullRotTime == -1) {
			this.lastFullRotTime = curMillis;
			return;
		}
		
		long timeSinceLastRot = curMillis - lastFullRotTime;
		
		double timePercent = (double)timeSinceLastRot/(double)rotationTime;
		
		this.setRotate(timePercent * 360);
		
		if(timeSinceLastRot >= rotationTime) 
			lastFullRotTime = curMillis;
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

}
