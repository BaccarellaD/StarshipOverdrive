package menus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import application.Game;
import application.Game.GameLevel;
import controllers.ImageFactory;
import controllers.ImageFactory.ImageType;
import controllers.MusicController.Music;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import menus.LevelInfo.LevelInfoBuilder;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;


public class LevelMenu extends SubMenu{
	
	private VBox outerVbox; //outermost Vbox
	
	private HBox upperHbox; //upper half of window
	
	private ScrollPane buttonScrollPane; //left scrollPane
	private ToggleGroup buttonGroup;
	private VBox buttonVbox;
	
	private GridPane rightGridpane; 
	private Pane planetWrapperPane;
	private ImageView planetImgView;
	private Label missionTitle;
	private Label missionLocation;
	private Label missionDescription;
	
	private Pane lowerPane; //lower pane
	private ImageView worldMap;
	private Button continueButton;
	private Button returnButton;
	private Rectangle pointerRect;
	
	private GameLevel gameLevelPointer;

	private List<Line> lines; //these lines are used to make the pointer
	
	private List<ToggleButton> levelButtons;
	
	private List<LevelInfo> infos;
	
	public LevelMenu(Music musicType) {
		super(musicType);
		
		lines = new ArrayList<>();
		
		initNodes();
		initListeners();
		initLayout();
		
		initLevelInfo();
		initButtons();
		
		initStyle();
	}

	private void initButtons() {
		//TODO
		
		levelButtons = new ArrayList<>();
		
		for(int i = 0; i < infos.size(); i++) {
			LevelInfo info = infos.get(i);
			
			ToggleButton tb = new ToggleButton("Level " + (i+1));
			
			tb.setOnAction(e ->{
				setLevelInfo(info);
			});
			
			buttonGroup.getToggles().add(tb);
			buttonVbox.getChildren().add(tb);
			levelButtons.add(tb);
		}
		
		buttonGroup.selectedToggleProperty().addListener(e ->{
			if(buttonGroup.getSelectedToggle() != null) {
				int buttonIndex = buttonGroup.getToggles().indexOf(buttonGroup.getSelectedToggle());
				LevelInfo info = infos.get(buttonIndex);
				setLevelInfo(info);
			}
		});
		
		buttonGroup.selectToggle(levelButtons.get(0));
		
	}
	
	/**
	 * Sets level info in display
	 * @param level info object
	 */
	private void setLevelInfo(LevelInfo info) {
		missionTitle.setText(info.getLevelTitle());
		missionLocation.setText(info.getLevelLocation());
		missionDescription.setText(info.getLevelDescription());
		planetImgView.setImage(ImageFactory.getImageFactory().getImage(info.getLevelImageType()));
		relocateBoxPointer(info.getLevelXCoordinate(), info.getLevelYCoordinate());
		setCurLevelPointer(info.getGameLevelType());
		setLines(info);
	};

	private void setLines(LevelInfo info) {
		
		lowerPane.getChildren().removeAll(lines); //remove the old lines
		
		lines.clear();
		
		final double tickHeight = 25;
		
		Line upperTick = new Line();
		upperTick.setStartX(Game.WINDOW_WIDTH * (3d/4d));
		upperTick.setStartY(0);
		upperTick.setEndX(Game.WINDOW_WIDTH * (3d/4d));
		upperTick.setEndY(tickHeight);
		
		Line lowerTick = new Line();
		lowerTick.setStartX(pointerRect.getX() + pointerRect.getWidth()/2d);
		lowerTick.setStartY(pointerRect.getY());
		lowerTick.setEndX(pointerRect.getX() + pointerRect.getWidth()/2d);
		lowerTick.setEndY(pointerRect.getY() - tickHeight);
		
		Line connectionLine = new Line();
		connectionLine.setStartX(upperTick.getEndX());
		connectionLine.setStartY(upperTick.getEndY());
		connectionLine.setEndX(lowerTick.getEndX());
		connectionLine.setEndY(lowerTick.getEndY());
		
		Line borderLine = new Line();
		borderLine.setStartX(Game.WINDOW_WIDTH/2d);
		borderLine.setStartY(-1);
		borderLine.setEndX(Game.WINDOW_WIDTH);
		borderLine.setEndY(-1);
		
		upperTick.setStroke(Color.WHITE);
		lowerTick.setStroke(Color.WHITE);
		connectionLine.setStroke(Color.WHITE);
		borderLine.setStroke(Color.WHITE);
		
		upperTick.setStrokeWidth(2);
		lowerTick.setStrokeWidth(2);
		connectionLine.setStrokeWidth(2);
		borderLine.setStrokeWidth(2);
		
		lines.add(upperTick);
		lines.add(lowerTick);
		lines.add(connectionLine);
		lines.add(borderLine);
		
		lowerPane.getChildren().addAll(lines);
	}

	/**
	 * Changes the pointer location on the map
	 * @param levelXCoordinate
	 * @param levelYCoordinate
	 */
	private void relocateBoxPointer(double levelXCoordinate, double levelYCoordinate) {
		pointerRect.setX(levelXCoordinate - pointerRect.getWidth()/2d);
		pointerRect.setY(levelYCoordinate - pointerRect.getHeight()/2d);
	}
	
	
	private void setCurLevelPointer(GameLevel gameLevelType) {
		this.gameLevelPointer = gameLevelType;
	}

	private void initNodes() {
		outerVbox = new VBox(); //outermost Vbox
		
		upperHbox = new HBox(); //upper half of window
		
		buttonScrollPane = new ScrollPane(); //left scrollPane
		buttonGroup = new ToggleGroup();
		buttonVbox = new VBox();
		
		rightGridpane = new GridPane(); 
		planetImgView = new ImageView();
		planetWrapperPane = new StackPane();
		missionTitle = new Label("Title");
		missionLocation = new Label("Location");
		missionDescription = new Label("Description");
		
		lowerPane = new Pane(); //lower pane
		worldMap = new ImageView();
		continueButton = new Button("Play");
		returnButton = new Button("Back");
		pointerRect = new Rectangle();
		pointerRect.setWidth(25);
		pointerRect.setHeight(25);
	}
	
	private void initStyle() {
		Image worldMapImage = ImageFactory.getImageFactory().getImage(ImageType.PIXEL_GALAXY);
		
		worldMap.setImage(worldMapImage);
		//worldMap.setLayoutX((Game.WINDOW_WIDTH-ImageType.PIXEL_GALAXY.getWidth())/2d);
		//worldMap.setLayoutY((Game.WINDOW_HEIGHT/2d-ImageType.PIXEL_GALAXY.getHeight())/2d);
		worldMap.setFitWidth(Game.WINDOW_WIDTH);
		worldMap.setFitHeight(Game.WINDOW_HEIGHT/2d);
		
		
		buttonScrollPane.setPrefWidth(Game.WINDOW_WIDTH/2d);
		buttonScrollPane.setPrefHeight(Game.WINDOW_HEIGHT/2d);
		buttonScrollPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		buttonScrollPane.setFitToWidth(true);
		
		buttonVbox.setPrefWidth(Game.WINDOW_WIDTH/2d);
		buttonVbox.setAlignment(Pos.TOP_CENTER);
		buttonVbox.setSpacing(10);
		buttonVbox.setPadding(new Insets(10,0,10,0));
		buttonVbox.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
		
		rightGridpane.setPrefWidth(Game.WINDOW_WIDTH/2d);
		rightGridpane.setPrefHeight(Game.WINDOW_HEIGHT/2d);
		rightGridpane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
		
		planetImgView.setFitHeight(100);
		planetImgView.setFitWidth(100);
		planetWrapperPane.getStyleClass().add("level_menu_planet");
		
		missionTitle.getStyleClass().add("level_menu_title");
		missionTitle.setWrapText(true);
		missionLocation.getStyleClass().add("level_menu_location");
		missionLocation.setWrapText(true);
		missionDescription.getStyleClass().add("level_menu_description");
		missionDescription.setWrapText(true);
		
		lowerPane.setPrefWidth(Game.WINDOW_WIDTH);
		lowerPane.setPrefHeight(Game.WINDOW_HEIGHT/2d);
		lowerPane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		
		pointerRect.getStyleClass().add("level_menu_rect");
		
		returnButton.getStyleClass().add("level_menu_lower_button");
		continueButton.getStyleClass().add("level_menu_lower_button");
		
		returnButton.applyCss();
		continueButton.applyCss();
		
		lowerPane.applyCss();
		lowerPane.layout();
		
		System.out.println("Ret But H: " + returnButton.getPrefHeight());
		
		returnButton.setLayoutX(10);
		returnButton.setLayoutY(Game.WINDOW_HEIGHT/2d - 65);
		
		continueButton.setLayoutY(Game.WINDOW_HEIGHT/2d - 65);
		continueButton.setLayoutX(Game.WINDOW_WIDTH - 110);
		
		for(ToggleButton tb: levelButtons) {
			tb.getStyleClass().add("level_menu_button");
		}
		
		//missionTitle.getStyleClass().add("level_menu_mission_title");
		
	}
	
	private void initListeners() {
		// TODO Auto-generated method stub
		
		continueButton.setOnAction(e ->{
			Game.getGame().setLevel(gameLevelPointer);
		});
		
		returnButton.setOnAction(e ->{
			Game.getGame().returnToMenu();
		});
		
	}
	
	private void initLayout() {
		//Top Left init
		buttonScrollPane.setContent(buttonVbox);
		
		//Top Right init
		planetWrapperPane.getChildren().add(planetImgView);
		
		rightGridpane.add(planetWrapperPane, 	0, 0);
		rightGridpane.add(missionTitle,  		1, 0);
		rightGridpane.add(missionLocation,		1, 1);
		rightGridpane.add(missionDescription,  	0, 2);
		
		GridPane.setRowSpan(planetWrapperPane, 2);
		GridPane.setColumnSpan(missionDescription, 2);
		
		//Top init
		upperHbox.getChildren().addAll(buttonScrollPane, rightGridpane);
		
		//Bottom init
		lowerPane.getChildren().addAll(worldMap, pointerRect, returnButton, continueButton);
		
		//Putting it together
		outerVbox.getChildren().addAll(upperHbox, lowerPane);
		this.getChildren().add(outerVbox);
	}
	
	private void initLevelInfo() {
		
		infos = new ArrayList<>();
		
		String level1Description = "This jungle planet is the home base Star Fleet squadron 12. "
				+ "It was established in 3256 AD after the Veldebar uprising as a central hub for the Psi Quadrant."
				+ " After the Veldebars were defeated it has seen little action mainly defending trading hubs in the local area.";
		
		String level2Description = "War torn for millenia Eta Vendrizi has become a baren planet devoid of unintelligent life."
				+ " Several cities still remain but are populated by some of the most dangerous individuals in the galaxy."
				+ " There is little reason to go there... atleast legal ones.";
		
		String level3Description = "With its surface almost entirely covered by water, Vesna is home to many aquatic life forms."
				+ " With few natural resources and even less land, the planet has remained peaceful throughout all of the great wars."
				+ " Peaceful life allowed Vesna to flourish and become of the largest trade hubs in the galaxy."
				+ " Vanity goods being particularly popular.";
		
		String level4Description = "In the far reaches of Alpha Quadrant, Kaitos is one of the newest planets to become populated."
				+ " Not much is known of its history but with constant scientific expeditions being conducted that is sure to change the upcoming years."
				+ " Apart from bacterial life nothing has been found to live on the planet yet, which is strange due to the abundance of liquid water and hydrothermal springs.";
		
		//Level 1
		LevelInfo info1 = LevelInfo.getBuilder()
							.levelTitle("Eldirass")
							.levelLocation("Psi Quadrant")
							.levelDescription(level1Description)
							.levelImageType(ImageType.PLANET_1)
							.levelXCoordinate(560)
							.levelYCoordinate(230)
							.gameLevelType(GameLevel.LEVEL_1)
							.build();
		
		//Level 2
		LevelInfo info2 = LevelInfo.getBuilder()
									.levelTitle("Eta Vendrizi")
									.levelLocation("Gamma Quadrant")
									.levelDescription(level2Description)
									.levelImageType(ImageType.PLANET_2)
									.levelXCoordinate(200)
									.levelYCoordinate(200)
									.gameLevelType(GameLevel.LEVEL_2)
									.build();
		//Level 3
		LevelInfo info3 = LevelInfo.getBuilder()
									.levelTitle("Vesna")
									.levelLocation("Gamma Quadrant")
									.levelDescription(level3Description)
									.levelImageType(ImageType.PLANET_3)
									.levelXCoordinate(230)
									.levelYCoordinate(300)
									.gameLevelType(GameLevel.LEVEL_2)
									.build();
		
		//Level 4
		LevelInfo info4 = LevelInfo.getBuilder()
									.levelTitle("Kaitos")
									.levelLocation("Alpha Quadrant")
									.levelDescription(level4Description)
									.levelImageType(ImageType.PLANET_4)
									.levelXCoordinate(650)
									.levelYCoordinate(375)
									.gameLevelType(GameLevel.LEVEL_2)
									.build();
		
		
		infos.add(info1);
		infos.add(info2);
		infos.add(info3);
		infos.add(info4);
		
		//Scroll test
		infos.add(info2);
		infos.add(info2);
		infos.add(info2);
		infos.add(info2);
		infos.add(info2);
		infos.add(info2);
		infos.add(info2);
		infos.add(info2);
		infos.add(info2);
		infos.add(info2);
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
