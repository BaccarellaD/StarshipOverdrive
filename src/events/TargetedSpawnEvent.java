package events;

import sprites.Sprite;

public class TargetedSpawnEvent {

	private Sprite target;			//the sprite that the values will be copied from
	private Sprite absorbingSprite; //the sprite that the values will be copied to
	
	private GameEvent<?> spawnEvent;
	
	public TargetedSpawnEvent(Sprite absorbingSprite, Sprite target, GameEvent<?> spawnEvent) {
		this.target = target;
		this.absorbingSprite = absorbingSprite;
		this.spawnEvent = spawnEvent;
	}
	
	/**
	 * Will change x and y values to that of the target and return the relevant spawn event
	 * @return
	 */
	public GameEvent<?> triggerSpawn(){
		
		absorbingSprite.setX(target.getX());
		absorbingSprite.setY(target.getY());
		
		return spawnEvent;
		
	}
	
}
