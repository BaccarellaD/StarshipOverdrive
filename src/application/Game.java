package application;

import java.awt.event.ActionEvent;
import java.io.IOException;

import controllers.KeyPressController;
import controllers.Level1;
import controllers.Level2;
import controllers.LevelController;
import controllers.MusicController;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import menus.MainMenuController;
import menus.MainMenuController.MenuType;
import runTimeChecks.JarChecker;

public class Game extends StackPane{
	
	public enum GameLevel{
		LEVEL_1, 
		LEVEL_2
	}
	
	public static final double WINDOW_HEIGHT = 1000d;
	public static final double WINDOW_WIDTH =   800d;
	
	private static Game game;
	private Scene scene;
	private KeyPressController kpc;
	
	private LevelController level;
	
	private boolean inMenu;
	private MainMenuController menu;
	
	private static MusicController musicPlayer;
	
	private JarChecker jarChecker;
	
	private Game(){
		
		try {
			jarChecker = new JarChecker("music/Luna_Ascension.mp3");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("In Jar = " + jarChecker.isInJar());
		
		//level = new Level1();
		//this.getChildren().add(level);
		this.kpc = new KeyPressController();
		
		menu = new MainMenuController();
		returnToMenu();
	}
	
	public void hookScene(Scene scene){
		this.scene = scene;
		hookElements();
	}
	
	private void hookElements() {
		scene.setOnKeyPressed(e->{
			kpc.processKeyPress(e);
		});
		
		scene.setOnKeyReleased(e ->{
			kpc.processKeyRelease(e);
		});
	}

	public static Game getGame(){
		if(game == null){
			game = new Game();
		}
		return game;
	}
	
	public void returnToMenu(){
		if(!inMenu){
			
			System.gc();
			
			if(level != null){
				level.stop();
				this.getChildren().remove(level);
				level = null;
			}
			
			this.getChildren().add(menu);
			inMenu = true;
		}
		menu.setMenu(MenuType.MAIN_MENU);
	}
	
	public void setLevel(GameLevel levelType){
		if(!inMenu){
			level.stop();
			this.getChildren().remove(level);
			level = null;
		}else{
			menu.stopMenu();
			this.getChildren().remove(menu);
		}
		
		switch(levelType){
			case LEVEL_1: level = new Level1();
				break;
			case LEVEL_2: level = new Level2();
				break;
			default: throw new IllegalArgumentException("Level has not been initialized: " + levelType);
		}
		
		this.getChildren().add(level);
		inMenu = false;
	}
	
	/**
	 * Checks if the node is within the game window.
	 * @param node to check
	 * @return true if node is inside the game window
	 */
	public boolean isInWindow(Node n){
		return this.getLayoutBounds().intersects(n.getBoundsInParent());
	}

	public LevelController getLevel() {
		return level;
	}
	
	public static MusicController getMusicPlayer(){
		if(musicPlayer == null){
			musicPlayer = new MusicController();
		}
		return musicPlayer;
	}
	
	public boolean isInMenu(){
		return inMenu;
	}

	public MainMenuController getMenu() {
		return menu;
	}

	public void playCurLevel() {
		// TODO Auto-generated method stub
		setLevel(GameLevel.LEVEL_2);
	}
}
