package menus;

import application.Game;
import controllers.MusicController.Music;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public abstract class SubMenu extends StackPane{
	
	private Music musicType;
	
	public SubMenu(Music musicType){
		this.setMinHeight(Game.WINDOW_HEIGHT);
		this.setMinWidth(Game.WINDOW_WIDTH);
		
		setMusicType(musicType);
	}

	public Music getMusicType() {
		return musicType;
	}
	
	public void setMusicType(Music type){
		this.musicType = type;
	}
	
	/**
	 * Used to start any initial loops or animation timers
	 */
	public abstract void start();
	
	/**
	 * Used to stop any animation loops to avoid leaky threads
	 */
	public abstract void stop();
}
