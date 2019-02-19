package menus;

import application.Game;
import controllers.MusicController.Music;
import javafx.scene.layout.StackPane;

public class MainMenuController extends StackPane{

	public enum MenuType{
		MAIN_MENU,
		LEVEL_MENU
	}
	
	private SubMenu curMenu;
	
	private Music mainMenuMusic = Music.ROR;
	private Music levelMenuMusic = Music.ROR;
	
	public MainMenuController(){
		setMenu(MenuType.MAIN_MENU);
	}

	public void setMenu(MenuType type) {
		if(curMenu != null){
			stopMenu();
			this.getChildren().remove(curMenu);
		}
		
		switch(type){
			case MAIN_MENU: curMenu = new MainMenu(mainMenuMusic);
				break;
			case LEVEL_MENU: curMenu = new LevelMenu(levelMenuMusic);
				break;
		}
		
		hookMenu();
	}
	
	private void hookMenu(){
		curMenu.start();
		this.getChildren().add(curMenu);
	}
	
	public void stopMenu(){
		curMenu.stop();
	}
	
}
