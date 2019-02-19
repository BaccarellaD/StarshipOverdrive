package sprites.playerSpecial;

import java.util.ArrayList;
import java.util.List;

import controllers.ImageFactory;
import controllers.ImageFactory.ImageType;
import controllers.SoundFXController.SoundType;
import events.GameEvent;
import events.GameEvent.EventType;
import sprites.Player;
import sprites.ui.HealthBar;

public class PlayerShield {
	
	private Player targetPlayer;

	private boolean shielded;
	private boolean shieldChange;
	
	private long chargeTime;
	private long maxChargeTime;
	private long minChargeTime;
	private long prevTime;
	
	private double shieldOverCharge;
	private double maxShieldOverCharge;
	
	public PlayerShield(long minChargeTime, long maxChargeTime, double maxShieldOvercharge, Player player) {
		this.targetPlayer = player;
		
		this.minChargeTime = minChargeTime;
		this.maxChargeTime = maxChargeTime;
		this.chargeTime = maxChargeTime;
		shielded = false;
		
		this.maxShieldOverCharge = maxShieldOvercharge;
		
		prevTime = -1;
	}
	
	public List<GameEvent<?>> tick(long curTime) {
		
		List<GameEvent<?>> events = new ArrayList<>();
		
		if(prevTime == -1) {
			prevTime = curTime;
			return events;
		}
		
		if(shieldChange) { //sprite change if there was a change in shield
			if(shielded) {
				targetPlayer.setImage(ImageFactory.getImageFactory().getImage(ImageType.PLAYER_SHIP_1_SHIELDED));
				events.add(new GameEvent<Boolean>(EventType.UI_SET_SHIELD_UP, true));
				events.add(new GameEvent<SoundType>(EventType.PLAY_SOUND, SoundType.SHEILD_UP));
			}else {
				targetPlayer.setImage(ImageFactory.getImageFactory().getImage(ImageType.PLAYER_SHIP_1));
				events.add(new GameEvent<Boolean>(EventType.UI_SET_SHIELD_UP, false));
				events.add(new GameEvent<SoundType>(EventType.PLAY_SOUND, SoundType.SHIELD_DOWN));
			}
			shieldChange = false;
		}
		
		long dt = curTime - prevTime;
		
		boolean chargeTimeChange = true;
		
		if(!shielded) {
			chargeTime += dt/4;
			if(chargeTime > maxChargeTime) {
				chargeTime = maxChargeTime;
				chargeTimeChange = false;
			}
		}else {
			
			chargeTime -= dt;
			if(chargeTime < 0) {
				chargeTime = 0;
				shieldDown(); //if there is no time, bring the shield down
				chargeTimeChange = false;
			}
		}
		
		if(chargeTimeChange) {
			events.add(new GameEvent<Double>(EventType.UI_SET_SHIELD_PERCENT, (double)chargeTime/(double)maxChargeTime));
		}
		
		prevTime = curTime;
		
		return events;
		
	}
	
	public void shieldUp() {
		System.out.println("Attempting to put sheild up");
		
		if(!shielded && chargeTime >= minChargeTime) {
			shieldChange = true;
			shielded = true;
		}
	}
	
	public void shieldDown() {
		System.out.println("Attempting to put sheild down");
		
		if(shielded) {
			shieldChange = true;
			shielded = false;
		}
	}
	
	public boolean isShielded() {
		return shielded;
	}
	
	public void addOvercharge(double overCharge) {
		shieldOverCharge += overCharge;
		if(shieldOverCharge > maxShieldOverCharge) {
			shieldOverCharge = maxShieldOverCharge;
		}
		
		System.out.println("Overcharge: " + shieldOverCharge);
	}
	
	public double dumpOverCharge() {
		double tempOvercharge = shieldOverCharge;
		shieldOverCharge = 0;
		return tempOvercharge;
	}
}
