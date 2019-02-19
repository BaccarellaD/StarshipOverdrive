package backgrounds;

import java.util.ArrayList;
import java.util.List;

import application.Game;
import events.GameEvent;
import events.GameEvent.EventType;
import sprites.Sprite;

public class GroundSprite extends Sprite{
	
	private long last_millis;
	
	public GroundSprite(long cur_millis){
		last_millis = cur_millis;
	}

	@Override
	public List<GameEvent<?>> tick(long cur_millis) {
		
		List<GameEvent<?>> events = new ArrayList<>();
		
		if(cur_millis < last_millis){
			last_millis = cur_millis;
			return events;
		}
		
		GameEvent<GroundSprite> event = this.move(cur_millis);
		this.last_millis = cur_millis;
		
		if(event != null){
			events.add(event);
		}
		
		return events;
	}
	
	public GameEvent<GroundSprite> move(long cur_millis){
		
		long dt = cur_millis - last_millis;
		
		if(this.xVel != 0)
			this.setX(this.getX() + dt * this.xVel);
		
		this.setY(this.getY() + dt * this.yVel);
		
		if(this.getY() >= Game.WINDOW_HEIGHT){		
			return new GameEvent<GroundSprite>(EventType.REMOVE_SPRITE, this);
		}
		else{
			return null;
		}
	}

	public void resetWithValues(long cur_millis) {
		this.last_millis = cur_millis;
		this.setFitHeight(-1);
		this.setFitWidth(-1);
	}

}
