package sprites.ui;

import java.util.ArrayList;
import java.util.List;

import application.Game;
import events.GameEvent;
import events.GameEvent.EventType;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class WarningPopup extends DialogPopup{
	
	private long opacityChangeTime;
	private long lastOpacitySwap; //keeps track of last time that the opacity changed;
	private boolean isVisible; //use to check if the popup is visible
	private long timeOutTime;
	private long initialTime;
	
	private StackPane stackPane;
	private Label textLabel;

	public WarningPopup(String warningText, long opacityChangeTime,  long timeOutTime) {
		super(warningText, 0, false);
		
		isVisible = true;
		initialTime = -1;
		
		this.opacityChangeTime = opacityChangeTime;
		this.timeOutTime = timeOutTime;
		
		initNodes();
		initStyle();
		initLayout();
		forceReveal();
	}

	private void initNodes() {
		stackPane = new StackPane();
		textLabel = new Label();
	}

	private void initStyle() {
		stackPane.setMinHeight(Game.WINDOW_HEIGHT);
		stackPane.setMinWidth(Game.WINDOW_WIDTH);
		
		textLabel.getStyleClass().add("warning_popup_text");
		textLabel.setWrapText(true);
		
	}
	
	private void initLayout() {
		stackPane.getChildren().add(textLabel);
		this.setCenter(stackPane);
	}
	
	@Override
	protected boolean updateText(String text) {
		textLabel.setText(text);
		return text.equals(this.text);
	}

	@Override
	protected List<GameEvent<?>> getEvents(long curTime) {
		
		List<GameEvent<?>> events = new ArrayList<>();
		
		if(initialTime == -1) {
			initialTime = curTime;
			lastOpacitySwap = curTime;
			return events;
		}
		
		updateOpacity(curTime);
		
		if(isTimeOut(curTime)) {
			events.add(new GameEvent<DialogPopup>(EventType.REMOVE_POPUP, this));
		}
		
		return events;
	}
	
	private void updateOpacity(long curTime) {
		
		if(curTime - lastOpacitySwap < opacityChangeTime) {
			return;
		}
		
		if(isVisible) {
			System.out.println("Making invisible");
			textLabel.setOpacity(0);
		}else {
			System.out.println("Making visible");
			textLabel.setOpacity(1);
		}
		
		isVisible = !isVisible;
		lastOpacitySwap = curTime;
	}
	
	private boolean isTimeOut(long curTime) {
		return curTime >= initialTime + timeOutTime;
	}
	
	

}
