package sprites.playerWeapons;

import java.util.List;

import events.GameEvent;
import sprites.Player;

public abstract class PlayerWeapon {
	
	private long initialTime;
	protected Player player;
	
	public PlayerWeapon(Player player){
		this.player = player;
		initialTime = -1;
	}
	
	public abstract List<GameEvent<?>> tick(long curTime);
	
	public abstract List<GameEvent<?>> forceShot(long curTime);
	
	public void setInitialTime(long initialTime){
		this.initialTime = initialTime;
	}
	
	public boolean initialTimeSet(){
		return initialTime == -1;
	}
}
