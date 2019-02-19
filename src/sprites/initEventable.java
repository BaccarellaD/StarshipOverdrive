package sprites;

import java.util.Collection;
import java.util.List;

import events.GameEvent;

public interface InitEventable {
	
	public List<GameEvent<?>> getInitEvents();
	
	public default boolean initEventsEmpty(){
		return getInitEvents().isEmpty();
	}
	
	public default void addInitEvent(GameEvent<?> event){
		getInitEvents().add(event);
	}
	
	public default void addAllInitEvents(Collection<GameEvent<?>> events){
		getInitEvents().addAll(events);
	}
	
}
