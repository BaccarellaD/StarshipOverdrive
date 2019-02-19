package sprites.movementPatterns;

import java.util.ArrayList;
import java.util.List;

import events.GameEvent;
import events.GameEvent.EventType;
import sprites.Enemy;

public class StraightMovement extends MovementPattern{
	
	private long 	prev_millis;
	
	private boolean lowerXSet;
	private boolean upperXSet;
	private boolean lowerYSet;
	private boolean upperYSet;
	
	private double lowerX;
	private double upperX;
	private double lowerY;
	private double upperY;

	public StraightMovement(Enemy enemy) {
		super(enemy);
		lowerXSet = false;
		upperXSet = false;
		lowerYSet = false;
		upperYSet = false;
	}

	@Override
	public List<GameEvent<?>> move(long cur_millis) {
		
		List<GameEvent<?>> events = new ArrayList<>();
		
		if(!initalTimeSet()){ //avoid initial jump due to improper timing
			setStart_millis(cur_millis);
			prev_millis = cur_millis;
			return events;
		}
		
		long dt = cur_millis - prev_millis;
		
		enemy.setX(enemy.getX() + dt * enemy.getXVel());
		enemy.setY(enemy.getY() + dt * enemy.getYVel());
		
		prev_millis = cur_millis;
		
		if(outOfBounds()){
			GameEvent<Enemy> event = new GameEvent<>(EventType.REMOVE_ENEMY, enemy);
			events.add(event);
		}
		
		return events;
	}
	
	private boolean outOfBounds(){
		
		if(lowerXSet && enemy.getX() < lowerX - enemy.getWidth())	{return true;} //Upper X Check
		if(upperXSet && enemy.getX() > upperX)						{return true;} //Lower X Check
		if(lowerYSet && enemy.getY() < lowerY - enemy.getHeight())	{return true;} //Upper Y Check
		if(upperYSet && enemy.getY() > upperY)						{return true;} //Lower Y Check
		
		return false;

	}
	
	public void setLowerX(double lowerX){
		this.lowerX 	= lowerX;
		this.lowerXSet 	= true;
	}
	
	public void setUpperX(double upperX){
		this.upperX 	= upperX;
		this.upperXSet 	= true;
	}
	
	public void setLowerY(double lowerY){
		this.lowerY 	= lowerY;
		this.lowerYSet	= true;
	}
	
	public void setUpperY(double upperY){
		this.upperY 	= upperY;
		this.upperYSet	= true;
	}
}
