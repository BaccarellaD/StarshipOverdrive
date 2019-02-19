package sprites;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import events.GameEvent;
import javafx.scene.CacheHint;
import javafx.scene.image.ImageView;

public abstract class Sprite extends ImageView implements InitEventable{
	
	private List<GameEvent<?>> initializationEvents;
	
	public Sprite(){
		//setCache(true);
		//setCacheHint(CacheHint.SPEED);
	}
	
	protected double xVel;
	protected double yVel;
	
	public boolean isCollided(Sprite s){
		return this.getBoundsInParent()
					.intersects(s.getBoundsInParent());
	}
	
	public abstract List<GameEvent<?>> tick(long cur_millis);

	public double getXVel() {
		return xVel;
	}

	public void setXVel(double xVel) {
		this.xVel = xVel;
	}

	public double getYVel() {
		return yVel;
	}

	public void setYVel(double yVel) {
		this.yVel = yVel;
	}
	
	public List<GameEvent<?>> getInitEvents(){
		if(initializationEvents == null){
			initializationEvents = new ArrayList<>();
		}
		return initializationEvents;
	}
}
