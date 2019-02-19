package sprites;

import java.util.ArrayList;
import java.util.List;

import controllers.ImageFactory;
import controllers.ImageFactory.ImageType;
import events.GameEvent;
import sprites.bulletPatterns.BulletPattern;
import sprites.movementPatterns.MovementPattern;

public abstract class Enemy extends Sprite{

	private double health;
	protected long points;
	protected double height;
	protected double width;
	
	private List<GameEvent<?>> deathRattleEvents;
	private double collisionDamage;
	
	public void setPoints(long points){
		this.points = points;
	}
		
	public boolean isDead(){
		return health <= 0;
	}
	
	public void addDeathRattleEvent(GameEvent<?> event) {
		 assureDeathRattleInit();
		 deathRattleEvents.add(event);
	}
	
	public void addAllDeathRattleEvents(GameEvent<?>...events) {
		assureDeathRattleInit();
		for(GameEvent<?> event: events) {
			deathRattleEvents.add(event);
		}
	}
	
	public List<GameEvent<?>> getDeathRattleEvents(){
		assureDeathRattleInit();
		return deathRattleEvents;
	}
	
	private void assureDeathRattleInit() {
		if(deathRattleEvents == null) {
			deathRattleEvents = new ArrayList<>();
		}
	}
	
	public void setShipImage(ImageType type){
		
		ImageFactory imgFac = ImageFactory.getImageFactory();
		
		this.setImage(imgFac.getImage(type));
		this.height = type.getHeight();
		this.width  = type.getWidth();
		this.setFitHeight(height);
		this.setFitWidth (width);
	}
	
	public abstract double getHeight();
	public abstract double getWidth();
	public abstract void setBulletPattern(BulletPattern pattern);
	public abstract void setMovementPattern(MovementPattern mp);

	public boolean hasDeathRattleEvents() {
		return deathRattleEvents != null;
	}

	public double getCollisionDamage() {
		return collisionDamage;
	}
	
	public void setCollisionDamage(double collisionDamage) {
		this.collisionDamage = collisionDamage;
	}
	
}
