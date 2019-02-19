package sprites.movementPatterns;

import java.util.ArrayList;
import java.util.List;

import events.GameEvent;
import sprites.Enemy;

public class StationaryMovement extends MovementPattern{

	public StationaryMovement(Enemy enemy) {
		super(enemy);
	}

	@Override
	public List<GameEvent<?>> move(long cur_millis) {
		return new ArrayList<GameEvent<?>>();
	}

}
