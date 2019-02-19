package sprites.ui;

import java.util.List;

import application.Game;
import events.GameEvent;
import events.GameEvent.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import sprites.Sprite;

public class LowerThirdPopup extends DialogPopup{
	
	VBox leftVbox;
	Pane imageWrapPane;
	Sprite imageSprite; 
	Label textLabel;
	Button contButton;
	
	private boolean removePopup;
	
	private static final boolean wvblc = true; //WAVE BLOCK BOOLEAN

	public LowerThirdPopup(String text, Sprite imageSprite, long charRevealTime) {		
		super(text, charRevealTime, wvblc);
		
		this.imageSprite = imageSprite;
		textLabel = new Label();
		contButton = new Button("Next");
		imageWrapPane = new StackPane();
		leftVbox = new VBox();
		
		setBlocking(true); //By default this node will block
		
		initLayout();
		initStyle();
		initListeners();
	}

	private void initStyle() {
		
		final int thirdHeight = 200;
		
		this.getStyleClass().add("lower_third");
		
		this.setTranslateY(Game.WINDOW_HEIGHT - thirdHeight);
		this.setMinHeight(thirdHeight);
		this.setMinWidth(Game.WINDOW_WIDTH);
		this.setMaxHeight(thirdHeight);
		this.setMaxWidth(Game.WINDOW_WIDTH);
		
		//this.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
	
		imageSprite.getStyleClass().add("lower_third_image");
		textLabel.getStyleClass().add("lower_third_text");
		contButton.getStyleClass().add("lower_third_button");
		imageWrapPane.getStyleClass().add("lower_third_image_wrap");
		
		textLabel.setWrapText(true);
		textLabel.setLineSpacing(8);
		
		imageSprite.setPreserveRatio(true);
		imageSprite.setFitWidth(150);
		imageSprite.setFitHeight(-1);
		
		this.layout();
		this.applyCss();
		
		//BorderPane.setMargin(imageSprite, new Insets(5));
		//BorderPane.setMargin(textLabel, new Insets(12,12,12,12));
		BorderPane.setMargin(contButton, new Insets((thirdHeight/2 - 25),5,5,0));
		
		textLabel.setAlignment(Pos.TOP_LEFT);
		contButton.setAlignment(Pos.BOTTOM_CENTER);
		
	}

	private void initLayout() {
		
		imageWrapPane.getChildren().add(imageSprite);
		leftVbox.getChildren().add(imageWrapPane);
		
		this.setCenter(textLabel);
		this.setLeft(leftVbox);
		this.setRight(contButton);
		
	}
	
	private void initListeners() {
		contButton.setOnAction(e ->{
			if(revealed){
				removePopup = true;
			}else{
				this.forceReveal();
			}
		});
	}

	@Override
	protected boolean updateText(String newText) {
		textLabel.setText(newText);
		return newText.equals(this.text);
	}

	@Override
	protected List<GameEvent<?>> getEvents(long curTime) {
		List<GameEvent<?>> events = imageSprite.tick(curTime);
		if(removePopup){
			events.add(new GameEvent<DialogPopup>(EventType.REMOVE_POPUP, this));
			if(waveBlocking){
				events.add(new GameEvent<Boolean>(EventType.SET_WAVE_BLOCK, false));
			}
		}
		return events;
	}

}
