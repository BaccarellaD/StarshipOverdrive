package sprites.movementPatterns;

import java.util.List;

import events.GameEvent;
import sprites.Enemy;

public abstract class MovementPattern {
	
	Enemy enemy;
	long start_millis;
	
	public MovementPattern(Enemy enemy){
		this.enemy = enemy;
		this.start_millis = -1;
	}
	
	public abstract List<GameEvent<?>> move(long cur_millis);
	
	public Enemy getTargetSprite() {
		return enemy;
	}

	public void setTargetSprite(Enemy enemy) {
		this.enemy = enemy;
	}

	public long getStart_millis() {
		return start_millis;
	}

	public void setStart_millis(long start_millis) {
		this.start_millis = start_millis;
	}
	
	public boolean initalTimeSet(){
		return start_millis != -1;
	}
	
}
