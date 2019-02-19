package menus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import application.Game;
import backgrounds.GameBackground;
import backgrounds.MainMenuBackground;
import controllers.MusicController.Music;
import events.GameEvent;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import menus.MainMenuController.MenuType;

public class MainMenu extends SubMenu{
	
	private VBox buttonContainer;
	
	private Button continueButton;
	private Button levelsButton;
	private Button exitButton;
	
	private Label gameTitle;
	
	private AnimationTimer timer;
	
	private long gameTime;
	
	private GameBackground background;
	

	public MainMenu(Music musicType) {
		super(musicType);
		initNodes();
		initStyle();
		initListeners();
		initLayout();
		initTimer();
	}

	private void initTimer() {
		this.timer =  new AnimationTimer(){

			@Override
			public void handle(long currentNanoTime) {
				
				gameTime = TimeUnit.NANOSECONDS.toMillis(currentNanoTime);
				
				List<GameEvent<?>> events = new ArrayList<>();
				
				List<GameEvent<?>> be = background.tick(gameTime);
			}
			
		};
	}

	private void initLayout() {
		buttonContainer.getChildren().addAll(gameTitle, continueButton, levelsButton, exitButton);
		
		this.getChildren().addAll(background, buttonContainer);
	}

	private void initListeners() {
		continueButton.setOnAction(e ->{
			Game.getGame().playCurLevel();
		});
		levelsButton.setOnAction(e ->{
			Game.getGame().getMenu().setMenu(MenuType.LEVEL_MENU);
		});
		exitButton.setOnAction(e ->{
			System.exit(0);
		});
	}

	private void initStyle() {
		buttonContainer	.getStyleClass().add("main_menu_vbox");
		
		buttonContainer.setAlignment(Pos.CENTER);
		
		continueButton	.getStyleClass().add("main_menu_button");
		levelsButton	.getStyleClass().add("main_menu_button");
		exitButton		.getStyleClass().add("main_menu_button");
		
		gameTitle		.getStyleClass().add("main_menu_label");
		
		this.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
	}

	private void initNodes() {
		buttonContainer = new VBox();
		
		continueButton	= new Button("Continue");
		levelsButton	= new Button("Levels");
		exitButton		= new Button("Exit");
		
		gameTitle		= new Label("Starship   Overdrive");
		
		background		= new MainMenuBackground();
	}

	@Override
	public void start() {
		Game.getMusicPlayer().setMusic(this.getMusicType());
		timer.start();
	}

	@Override
	public void stop() {
		System.out.println("Stopping Main menu");
		timer.stop();
	}

}
