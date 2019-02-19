package events;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class EventWave {
	
	private PriorityQueue<TimedEvent> eventStack;
	private long waveStartTime;
	
	public EventWave(){
		eventStack = new PriorityQueue<>();
		this.waveStartTime = -1;
	}
	
	public EventWave(TimedEvent...timedEvents){
		this();
		for(TimedEvent e: timedEvents) eventStack.add(e);
	}
	
	
	public void addTimedEvent(TimedEvent timedEvent){
		eventStack.add(timedEvent);
	}
	/**
	 * Will take the game time and check that against the start time to see if there are any events that it contains that should be returned
	 * @param cur_time current game time
	 * @return list of events that at or over time
	 */
	public List<GameEvent<?>> tick(long cur_time){
		
		List<GameEvent<?>> events = new ArrayList<>();
		
		if(waveStartTime == -1){
			waveStartTime = cur_time;
			return events;
		}
		
		if(!hasEvent()){
			return events;
		}
		
		long timeSinceWaveStart = cur_time - waveStartTime;
		TimedEvent nextEvent = eventStack.peek();
		
		while(nextEvent.getMsToTrigger() < timeSinceWaveStart){
			events.addAll(eventStack.poll().getGameEvents());
			if(hasEvent()){
				nextEvent = eventStack.peek();
			}else{
				break;
			}
		}
		
		return events;
	}
	/**
	 * Returns true if there are any events left, false if there are not
	 * @return if there is any events left
	 */
	public boolean hasEvent(){
		return !eventStack.isEmpty();
	}
	
	public boolean timeSet(){
		return waveStartTime != -1;
	}
	
	public void setWaveStartTime(long startTime){
		this.waveStartTime = startTime;
	}
	/**
	 * Wipes all events out of the stack
	 */
	public void clearEvents(){
		eventStack.clear();
	}

}
