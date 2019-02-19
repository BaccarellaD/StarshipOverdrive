package sprites.bulletPatterns;

import java.util.ArrayList;
import java.util.List;

import events.GameEvent;
import sprites.Enemy;

public class BulletlessPattern extends BulletPattern{

	public BulletlessPattern(Enemy e) {
		super(e);
	}

	@Override
	public List<GameEvent<?>> tick(long cur_millis) {
		return new ArrayList<GameEvent<?>>();
	}

}
