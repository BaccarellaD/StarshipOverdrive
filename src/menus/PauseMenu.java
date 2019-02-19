package menus;

import application.Game;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class PauseMenu extends BorderPane{

	VBox   vbox;
	Label  pauseLabel;
	Button menuButton;
	Button resumeButton;
	
	public PauseMenu(){
		initNodes();
		initStyle();
		initListeners();
		initLayout();
	}
	private void initNodes(){
		vbox 			= new VBox();
		pauseLabel		= new Label ("Paused");
		menuButton 		= new Button("Menu");
		resumeButton 	= new Button("Resume");
		
	}
	
	private void initStyle() {
		pauseLabel.getStyleClass().add("pause_label");
		menuButton.getStyleClass().add("pause_menu_button");
		resumeButton.getStyleClass().add("pause_menu_button");
		
		vbox.getStyleClass().add("pause_menu_vbox");
		
		//set background to transparent black
		this.setBackground(new Background(new BackgroundFill(Color.web("#000000",.80), null, null)));
		this.setMinHeight(Game.WINDOW_HEIGHT);
		this.setMinWidth(Game.WINDOW_WIDTH);
		
		vbox.setAlignment(Pos.CENTER);
	}
	
	private void initListeners() {
		menuButton.setOnAction(e ->{
			Game.getGame().returnToMenu();
		});
		
		resumeButton.setOnAction(e ->{
			//unpause the game
			Game.getGame().getLevel().handlePause();
		});
		
	}
	
	private void initLayout() {
		vbox.getChildren().addAll(pauseLabel, resumeButton, menuButton);
		this.setCenter(vbox);
	}

	
}
