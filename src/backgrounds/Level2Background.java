package backgrounds;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import application.Game;
import controllers.ImageFactory;
import controllers.ImageFactory.ImageType;
import events.GameEvent;
import events.GameEvent.EventType;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Level2Background extends GameBackground{
	
	private GenerationLayer starLayer1;
	private GenerationLayer starLayer2;
	private GenerationLayer starLayer3;
	
	private long last_millis;
	
	public Level2Background(){
		createStarLayer1();
		createStarLayer2();
		createStarLayer3();
		
		this.getChildren().add(starLayer1);
		this.getChildren().add(starLayer2);
		this.getChildren().add(starLayer3);
		
		this.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
	}
	
	private void createStarLayer1() {
		
		double xVel = 0;
		double yVel = .02;
		
		int[] biomeTransArr = {-2,-1,-1,0,0,1,1,1,2};
		
		ImageType[] groundSpriteType = {ImageType.STAR_BLUE_RED_SMALL,
												ImageType.STAR_BLUE_SMALL,
												ImageType.STAR_BLUE_MEDIUM,
												ImageType.STAR_BLUE_RED_MEDIUM,
												ImageType.STAR_RED_MEDIUM,
												ImageType.STAR_BLUE_RED_LARGE,
												ImageType.STAR_RED_LARGE,
												ImageType.STAR_BLUE_LARGE_2,
												ImageType.STAR_1};
												
		int[][] upperAndLowerBounds = {{0, 2},
									   {3, 4},
									   {2, 5},
									   {3, 6},
									   {5, 7},
									   {5, 9}};
		
		int[] dChances0 = {1, 1, 1, 1, 1, 1, 1, 1, 1};
		int[] dChances1 = {3, 3, 3, 2, 2, 2, 2, 2, 2};
		int[] dChances2 = {2, 2, 2, 2, 2, 2, 3, 3, 3};
		int[] dChances3 = {1, 1, 1, 3, 3, 3, 5, 5, 7};
		int[] dChances4 = {1, 1, 1, 2, 2, 2, 7, 7, 10};
		int[] dChances5 = {0, 0, 0, 2, 2, 2, 10, 10, 12};
		

		
		int[][] dChances = {dChances0, dChances1, dChances2, dChances3, dChances4, dChances5};
		
		starLayer1 = new GenerationLayer(xVel, yVel, biomeTransArr, groundSpriteType, dChances, upperAndLowerBounds);
		injectPlanet(starLayer1);
	}
	
	
	private void createStarLayer2() {
		
		double xVel = 0;
		double yVel = .05;
		
		int[] biomeTransArr = {-1,-1,0,0,1,1};
		
		ImageType[] groundSpriteType = {ImageType.STAR_BLUE_RED_SMALL,
												ImageType.STAR_BLUE_SMALL,
												ImageType.STAR_BLUE_MEDIUM,
												ImageType.STAR_BLUE_RED_MEDIUM,
												ImageType.STAR_RED_MEDIUM,
												ImageType.STAR_BLUE_RED_LARGE,
												ImageType.STAR_RED_LARGE,
												ImageType.STAR_BLUE_LARGE_2,
												ImageType.STAR_1};
		
		int[][] upperAndLowerBounds = {{0, 2},
											   {3, 4},
											   {2, 5}};
		
		int[] dChances0 = {1, 1, 1, 1, 1, 1, 1, 1, 1};
		int[] dChances1 = {3, 3, 3, 2, 2, 2, 2, 2, 2};
		int[] dChances2 = {2, 2, 2, 2, 2, 2, 3, 3, 3};
		
		int[][] dChances = {dChances0, dChances1, dChances2};
		
		starLayer2 = new GenerationLayer(xVel, yVel, biomeTransArr, groundSpriteType, dChances, upperAndLowerBounds);
	}
	
	private void createStarLayer3() {
		
		double xVel = 0;
		double yVel = .075;
		
		int[] biomeTransArr = {-1,-1,0,0,1,1};
		
		ImageType[] groundSpriteType = {ImageType.STAR_BLUE_RED_SMALL,
												ImageType.STAR_BLUE_SMALL,
												ImageType.STAR_BLUE_MEDIUM,
												ImageType.STAR_BLUE_RED_MEDIUM,
												ImageType.STAR_RED_MEDIUM,
												ImageType.STAR_BLUE_RED_LARGE,
												ImageType.STAR_RED_LARGE,
												ImageType.STAR_BLUE_LARGE_2,
												ImageType.STAR_1};
		
		int[][] upperAndLowerBounds = {{0, 2},
											   {3, 4},
											   {2, 5}};
		
		int[] dChances0 = {1, 1, 1, 1, 1, 1, 1, 1, 1};
		int[] dChances1 = {3, 3, 3, 2, 2, 2, 2, 2, 2};
		int[] dChances2 = {2, 2, 2, 2, 2, 2, 3, 3, 3};
		
		int[][] dChances = {dChances0, dChances1, dChances2};
		
		starLayer3 = new GenerationLayer(xVel, yVel, biomeTransArr, groundSpriteType, dChances, upperAndLowerBounds);
	}

	public List<GameEvent<?>> tick(long cur_millis){
		
		List<GameEvent<?>> events =  new ArrayList<>();
		
		if(last_millis == 0){
			last_millis = cur_millis;
			return events;
		}
		
		starLayer1.tick(cur_millis);	
		starLayer2.tick(cur_millis);
		starLayer3.tick(cur_millis);
		
		if(starLayer1.isGenerationPass()) {
			injectPlanet(starLayer1);
		}
		
		last_millis = cur_millis;
		
		return events;
		
	}

	private void injectPlanet(GenerationLayer starLayer) {
		
		System.out.println("injecting planet");
		
		Random rand = new Random();
		
		double randX = rand.nextDouble()*Game.WINDOW_WIDTH;
		double randY = -rand.nextDouble()*Game.WINDOW_HEIGHT;
		
		ImageFactory imgFac = ImageFactory.getImageFactory();
		Image planetImage = null;
		
		switch(rand.nextInt(3)) {
			case 0: planetImage = imgFac.getImage(ImageType.PLANET_1);
				break;
			case 1: planetImage = imgFac.getImage(ImageType.PLANET_2);
				break;
			case 2: planetImage = imgFac.getImage(ImageType.PLANET_3);
				break;
		}
		
		GroundSprite planetSprite = GroundSpritePool.getPool().getGroundSprite(System.currentTimeMillis());
		planetSprite.setX(randX);
		planetSprite.setY(randY);
		planetSprite.setImage(planetImage);
		planetSprite.setFitHeight(planetImage.getRequestedHeight());
		planetSprite.setFitWidth(planetImage.getRequestedWidth());
		
		starLayer.injectAbsorbVelocity(planetSprite);
		
	}
	
}
