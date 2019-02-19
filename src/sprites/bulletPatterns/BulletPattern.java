package sprites.bulletPatterns;

import java.util.List;

import controllers.SoundFXController.SoundType;
import events.GameEvent;
import sprites.Enemy;

public abstract class BulletPattern{
	
	protected Enemy targetEnemy;
	protected long start_millis;
	protected long fireRate;

	protected SoundType soundType;
	
	public long getFireRate() {
		return fireRate;
	}

	public void setFireRate(long fireRate) {
		this.fireRate = fireRate;
	}

	public BulletPattern(Enemy e){
		this.targetEnemy = e;
		start_millis = -1;
	}
	
	public abstract List<GameEvent<?>> tick(long cur_millis);

	
	public Enemy getTargetEnemy() {
		return targetEnemy;
	}

	public void setTargetEnemy(Enemy targetEnemy) {
		this.targetEnemy = targetEnemy;
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
	
	public void setSoundType(SoundType type) {
		this.soundType = type;
	}
	
	public SoundType getSoundType() {
		return soundType;
	}
	
}
