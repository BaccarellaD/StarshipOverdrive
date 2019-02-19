package events;

import java.util.ArrayList;
import java.util.List;

public class TimedEvent implements Comparable<TimedEvent>{
	
	private List<GameEvent<?>> gameEvents;
	private long msToTrigger;
	
	public TimedEvent(List<GameEvent<?>> gameEvents, long msToTrigger) {
		super();
		this.gameEvents = gameEvents;
		this.msToTrigger = msToTrigger;
	}
	
	public TimedEvent(GameEvent<?> gameEvent, long msToTrigger) {
		super();
		this.gameEvents = new ArrayList<>();
		gameEvents.add(gameEvent);
		this.msToTrigger = msToTrigger;
	}
	
	public TimedEvent(){}
	
	public List<GameEvent<?>> getGameEvents() {
		return gameEvents;
	}
	public void setGameEvents(List<GameEvent<?>> gameEvents) {
		this.gameEvents = gameEvents;
	}
	public void addEvent(GameEvent<?> event){
		gameEvents.add(event);
	}
	public long getMsToTrigger() {
		return msToTrigger;
	}
	public void setMsToTrigger(long msToTrigger) {
		this.msToTrigger = msToTrigger;
	}

	@Override
	public int compareTo(TimedEvent te) {
		return Long.compare(msToTrigger, te.getMsToTrigger());
	}
	
	
	
}
