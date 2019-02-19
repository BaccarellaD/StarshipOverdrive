package sprites.ui;


import java.util.ArrayList;
import java.util.List;

import events.GameEvent;
import events.GameEvent.EventType;
import javafx.scene.layout.BorderPane;
import sprites.InitEventable;

public abstract class DialogPopup extends BorderPane implements InitEventable{
	
	protected long lastReveal;

	protected long charRevealTime;
	protected boolean revealed;
	
	protected String text;
	protected String revealedText;
	
	protected boolean waveBlocking;
	
	private List<GameEvent<?>> initializationEvents;
	
	public DialogPopup(String text, long charRevealTime, boolean waveBlocking){
		
		this.charRevealTime = charRevealTime;
		
		this.text = text;
		this.revealedText = "";
		
		lastReveal = -1;
		
		this.revealed = false;
		
		this.waveBlocking = waveBlocking;
		if(waveBlocking){
			this.addInitEvent(new GameEvent<Boolean>(EventType.SET_WAVE_BLOCK, true));
		}
	}
	
	public List<GameEvent<?>> getInitEvents(){
		if(initializationEvents == null){
			initializationEvents = new ArrayList<>();
		}
		return initializationEvents;
	}
	
	public List<GameEvent<?>> tick(long curTime){
		
		List<GameEvent<?>> events = getEvents(curTime);
		
		if(lastReveal < 0){
			lastReveal = curTime;
			return events;
		}
		
		if(revealed){
			return events;
		}
		
		if(curTime - lastReveal >= charRevealTime){
			revealChar(curTime);
		}
		
		return events;
	}
	
	protected void forceReveal(){
		revealedText = text;
		revealed = updateText(text);
	}
	
	private void revealChar(long curTime){
		revealedText = text.substring(0, revealedText.length()+1);
		revealed = updateText(revealedText);
		lastReveal = curTime;
	}
	
	public void setCharRevealTime(long time){
		this.charRevealTime = time;
	}
	
	public void setBlocking(boolean blocking){
		this.waveBlocking = blocking;
	}
	/**
	 * updates the text in the textbox to the text passed
	 * @param text
	 * @return true when fully revealed
	 */
	protected abstract boolean updateText(String text);
	
	/**
	 * If there are any events that the popup dialog needs to perform, they should be sent by this method
	 * @param curTime
	 * @return game events that the dialog needs to perform
	 */
	protected abstract List<GameEvent<?>> getEvents(long curTime);
	
}
