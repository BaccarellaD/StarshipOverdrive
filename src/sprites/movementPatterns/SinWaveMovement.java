package sprites.movementPatterns;

import java.util.ArrayList;
import java.util.List;

import events.GameEvent;
import sprites.Enemy;

public class SinWaveMovement extends MovementPattern{
	
	private long 	prev_millis;
	private double 	initialX;
	private double 	waveHeightX;
	private long	millisToCycle;

	public SinWaveMovement(Enemy enemy, double initialX, double waveHeightX, long millisToCycle) {
		super(enemy);
		this.initialX		= initialX;
		this.waveHeightX 	= waveHeightX;
		this.millisToCycle 	= millisToCycle;
	}

	@Override
	public List<GameEvent<?>> move(long cur_millis) {
		
		List<GameEvent<?>> events = new ArrayList<>();
		
		if(!initalTimeSet()){ //avoid initial jump due to improper timing
			setStart_millis(cur_millis);
			prev_millis = cur_millis;
			return events;
		}
		
		final long millisZeroed = cur_millis - start_millis + (millisToCycle/2);
		
		long millis_cropped = (millisZeroed % millisToCycle) - (millisToCycle/2);
		double outOf360 	= 360*(millis_cropped / (millisToCycle/2d));
		double rads 		= Math.toRadians(outOf360);
		double cosOffsetX 	= Math.cos(rads)*waveHeightX;
		
		long dt = cur_millis - prev_millis;
		
		enemy.setX(initialX + cosOffsetX + dt * enemy.getXVel());
		
		prev_millis = cur_millis;
		
		return events;
	}

	
	
}
