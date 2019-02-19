package backgrounds;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import application.Game;
import controllers.ImageFactory;
import controllers.ImageFactory.ImageType;
import events.GameEvent;
import events.GameEvent.EventType;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import java.util.Random;

public class Level1Background extends GameBackground{
	
	private Random random = new Random();
	
	private long last_millis;
	
	public static final double xVel = 0.0;
	public static final double yVel = 0.1;
	
	private ImageFactory imgFac = ImageFactory.getImageFactory();
	
	Pane floor1;
	Pane floor2;
	
	Pane spriteLayer;
	Pane cloudLayer;
	
	private int[][] densityLevels1;
	private int[][] densityLevels2;
	
	private int[] biomeTransArr = {-2,-1,-1,0,0,1,1,1,2};
	
	private ImageType[] groundSpriteType = {ImageType.SHRUB_1,
											ImageType.SHRUB_2,
											ImageType.SHRUB_3,
											ImageType.SMALL_TREE_1,
											ImageType.SMALL_TREE_2,
											ImageType.SMALL_TREE_3,
											ImageType.TREE_1,
											ImageType.TREE_2};
	
	private int[][] upperAndLowerBounds = {{0, 2},
										   {3, 4},
										   {2, 5},
										   {3, 6},
										   {5, 7},
										   {5, 9}};
	
	private int[] dChances0 = {1, 1, 1, 1, 1, 1, 1, 1};
	private int[] dChances1 = {3, 3, 3, 2, 2, 2, 2, 2};
	private int[] dChances2 = {2, 2, 2, 2, 2, 2, 3, 3};
	private int[] dChances3 = {1, 1, 1, 3, 3, 3, 5, 5};
	private int[] dChances4 = {1, 1, 1, 2, 2, 2, 7, 7};
	private int[] dChances5 = {0, 0, 0, 2, 2, 2, 10, 10};
	
	private int[][] dChances = {dChances0, dChances1, dChances2, dChances3, dChances4, dChances5};
	
	private List<GroundSprite> groundSprites;
	private List<GroundSprite> clouds;
	
	private GroundSpritePool gsPool;
	
	private Random rand = new Random();
	
	public Level1Background(){
		gsPool = GroundSpritePool.getPool();
		
		floor1 =  new Pane();
		floor2 =  new Pane();
		
		spriteLayer = new Pane();
		cloudLayer = new Pane();
		
		initializeBackground();
		initializeFoliage();
	}
	
	private void initializeBackground() {
		
		Image backGroundImage = imgFac.getImage(ImageType.BACKGROUND_GRASS);
		
		groundSprites 	= new ArrayList<>();
		clouds			= new ArrayList<>();
		
		BackgroundImage bgi = new BackgroundImage(backGroundImage, 
													BackgroundRepeat.REPEAT,
													BackgroundRepeat.REPEAT, 
													BackgroundPosition.DEFAULT,
													BackgroundSize.DEFAULT);
		floor1.setBackground(new Background(bgi));
		floor2.setBackground(new Background(bgi));
		
		floor1.setMinHeight(Game.WINDOW_HEIGHT+1);
		floor1.setMinWidth(Game.WINDOW_WIDTH);
		
		floor2.setMinHeight(Game.WINDOW_HEIGHT+1);
		floor2.setMinWidth(Game.WINDOW_WIDTH);
		
		floor2.setTranslateY(-Game.WINDOW_HEIGHT); //give a little overlap
		
		this.getChildren().addAll(floor1, floor2, spriteLayer, cloudLayer);
		
	}

	public List<GameEvent<?>> tick(long cur_millis){
		
		List<GameEvent<?>> events =  new ArrayList<>();
		
		if(last_millis == 0){
			last_millis = cur_millis;
			return events;
		}
		updateBackground(cur_millis);
		
		last_millis = cur_millis;
		
		return events;
		
	}

	private void updateBackground(long cur_millis) {
		
		updateSprites(cur_millis);
		
		if(rand.nextInt(450) == 1){
			spawnCloud(cur_millis);
		}
		
		long dt = cur_millis - last_millis;
		
		floor1.setTranslateX(floor1.getTranslateX() + dt * xVel);
		floor1.setTranslateY(floor1.getTranslateY() + dt * yVel);
		
		floor2.setTranslateX(floor2.getTranslateX() + dt * xVel);
		floor2.setTranslateY(floor2.getTranslateY() + dt * yVel);
		
		if(floor1.getTranslateY() >= Game.WINDOW_HEIGHT){
			floor1.setTranslateY(floor2.getTranslateY() - Game.WINDOW_HEIGHT);
			populateNewBiomeDensities(densityLevels1, densityLevels2);
			this.populateBiomeSprites(densityLevels1, (int)-Game.WINDOW_HEIGHT);
			System.out.println("Sprites size: " + groundSprites.size());
		}
		if(floor2.getTranslateY() >= Game.WINDOW_HEIGHT){
			floor2.setTranslateY(floor1.getTranslateY() - Game.WINDOW_HEIGHT);
			populateNewBiomeDensities(densityLevels2, densityLevels1);
			this.populateBiomeSprites(densityLevels2, (int)-Game.WINDOW_HEIGHT);
			System.out.println("Sprites size: " + groundSprites.size());
		}
	}

	private void updateSprites(long cur_millis) {
		
		List<GameEvent<?>> gsEvents = new ArrayList<>();
		List<GameEvent<?>> clEvents = new ArrayList<>();
		
		for(GroundSprite sprite: groundSprites){
			List<GameEvent<?>> events = sprite.tick(cur_millis);
			if(!events.isEmpty()) gsEvents.addAll(events);
		}
		
		for(GroundSprite cloud: clouds){
			List<GameEvent<?>> events = cloud.tick(cur_millis);
			if(!events.isEmpty()) clEvents.addAll(events);
		}
		
		for(GameEvent<?> gsEvent: gsEvents){
			if(gsEvent.getEventType() == EventType.REMOVE_SPRITE){
				removeGroundSprite((GroundSprite)gsEvent.getData());
			}
		}
		
		for(GameEvent<?> clEvent: clEvents){
			if(clEvent.getEventType() == EventType.REMOVE_SPRITE){
				removeCloud((GroundSprite)clEvent.getData());
			}
		}
		
	}

	private void removeGroundSprite(GroundSprite sprite) {
		groundSprites.remove(sprite);
		spriteLayer.getChildren().remove(sprite);
		gsPool.returnGroundSprite(sprite);
	}
	
	private void spawnCloud(long cur_millis){
		GroundSprite sprite = gsPool.getGroundSprite(cur_millis);
		Image cloudImg = imgFac.getImage((Math.random() < .5)? ImageType.CLOUD_1 : ImageType.CLOUD_2);
		sprite.setImage(cloudImg);
		sprite.setX(rand.nextInt((int)(Game.WINDOW_WIDTH+cloudImg.getWidth()))-cloudImg.getWidth());
		sprite.setY(-400);
		sprite.setYVel(yVel+.06);
		
		double randScale = Math.random()+1;
		
		sprite.setFitHeight(cloudImg.getHeight()*randScale);
		sprite.setFitWidth(cloudImg.getWidth()*randScale);
		
		clouds.add(sprite);
		cloudLayer.getChildren().add(sprite);
	}
	
	private void removeCloud(GroundSprite cloud) {
		clouds.remove(cloud);
		cloudLayer.getChildren().remove(cloud);
		gsPool.returnGroundSprite(cloud);
	}
	
	/* *******************************************************************************************************************************************************
	 * 							New Foliage generation
	 * 
	 * *******************************************************************************************************************************************************/
	private void addFoliageSprite(ImageType type, int x, int y){
		Image spriteImage = imgFac.getImage(type);
		
		GroundSprite sprite = gsPool.getGroundSprite(System.currentTimeMillis());
		sprite.setImage(spriteImage);
		sprite.setX(x - type.getWidth()/2);
		sprite.setY(y - type.getHeight());
		sprite.setYVel(yVel);
		
		groundSprites.add(sprite);
		spriteLayer.getChildren().add(sprite);
	}
	
	private void initializeFoliage(){
		
		densityLevels1 = new int[5][4];
		densityLevels2 = new int[5][4];
		
		int seed = random.nextInt(6);
		
		densityLevels1[4][0] = seed; //put seed into bottom left corner
		
		//populate left
		for(int i = 3; i >= 0; i--){
			int modifier = biomeTransArr[random.nextInt(biomeTransArr.length)];
			int newDensity = densityLevels1[i+1][0] + modifier;
			if(newDensity < 0) newDensity = 0;
			if(newDensity > 5) newDensity = 5;
			densityLevels1[i][0] = newDensity;
		}
		
		//populate bottom
		for(int i = 1; i < 4; i++){
			int modifier = biomeTransArr[random.nextInt(biomeTransArr.length)];
			int newDensity = densityLevels1[4][i-1] + modifier;
			if(newDensity < 0) newDensity = 0;
			if(newDensity > 5) newDensity = 5;
			densityLevels1[4][i] = newDensity;
		}
		
		//populate middle
		for(int i = 3; i >= 0; i--){
			for(int j = 1; j < 4; j++){
				
				int biomeSum = 0;
				biomeSum += densityLevels1[i][j-1];
				biomeSum += densityLevels1[i+1][j-1];
				biomeSum += densityLevels1[i+1][j];
				
				int biomeAvg = (int)(biomeSum/3d);
				
				int modifier = biomeTransArr[random.nextInt(biomeTransArr.length)];
				int newDensity = biomeAvg + modifier;
				if(newDensity < 0) newDensity = 0;
				if(newDensity > 5) newDensity = 5;
				densityLevels1[i][j] = newDensity;
			}
		}
		
		
		//Printing
		for(int i = 0; i < densityLevels1.length; i++){
			for(int j = 0; j < densityLevels1[i].length; j++){
				System.out.print(densityLevels1[i][j] + " ");
			}
			System.out.println();
		}
		
		populateNewBiomeDensities(densityLevels2, densityLevels1);
		
		populateBiomeSprites(densityLevels1, (int)-Game.WINDOW_HEIGHT);
		populateBiomeSprites(densityLevels2, 0);
	}

	/**
	 * Used to populate a new density map based on the top of the other density map
	 * @param densityChange - 2d array of int values to change
	 * @param densitySeed  - 2d array who's top row will be used to populate the new map
	 */
	private void populateNewBiomeDensities(int[][] densityChange, int[][] densitySeed) {
		
		//populate bottom
		for(int j = 0; j < 4; j++){
			int modifier = biomeTransArr[random.nextInt(biomeTransArr.length)];
			
			int densitySum = 0;
			int seedSum = 0;
			if(j != 0){
				densitySum += densitySeed[0][j-1];
				seedSum++;
			}
			densitySum += densitySeed[0][j];
			seedSum++;
			if(j != 3){
				densitySum += densitySeed[0][j+1];
				seedSum++;
			}
			
			int newDensity = densitySum/seedSum + modifier;
			
			if(newDensity < 0) newDensity = 0;
			if(newDensity > 5) newDensity = 5;
			densityChange[4][j] = newDensity;
		}

		//populate left
		for(int i = 3; i >= 0; i--){
			int modifier = biomeTransArr[random.nextInt(biomeTransArr.length)];
			int newDensity = densityChange[i+1][0] + modifier;
			if(newDensity < 0) newDensity = 0;
			if(newDensity > 5) newDensity = 5;
			densityChange[i][0] = newDensity;
		}
		
		//populate middle
		for(int i = 3; i >= 0; i--){
			for(int j = 1; j < 4; j++){
				
				int biomeSum = 0;
				biomeSum += densityChange[i][j-1];
				biomeSum += densityChange[i+1][j-1];
				biomeSum += densityChange[i+1][j];
				
				int biomeAvg = (int)(Math.round(biomeSum/3d));
				
				int modifier = biomeTransArr[random.nextInt(biomeTransArr.length)];
				int newDensity = biomeAvg + modifier;
				if(newDensity < 0) newDensity = 0;
				if(newDensity > 5) newDensity = 5;
				densityChange[i][j] = newDensity;
			}
		}
		
	}
	
	private void populateBiomeSprites(int[][] densityLevels, int yOffset){
		
		double cW = densityLevels[0].length;
		double cH = densityLevels.length;
		
		double cellWidth = Game.WINDOW_WIDTH/cW;
		double cellHeight = Game.WINDOW_HEIGHT/cH;
		
		PriorityQueue<GroundSpriteTriple> triples = new PriorityQueue<>();
		
		for(int i = 0; i < densityLevels.length; i++){ 			//i  corresponds to Y Val
			
			for(int j = 0; j < densityLevels[i].length; j++){	//j  corresponds to X Val
				
				int density = densityLevels[i][j];
				
				int upperBoundDensity = upperAndLowerBounds[density][1];
				int lowerBoundDensity = upperAndLowerBounds[density][0];
				
				int boundDist = upperBoundDensity - lowerBoundDensity;
				
				int numSprite = random.nextInt(boundDist)+lowerBoundDensity;
				
				for(int k = 0; k < numSprite; k++){
					
					int[] densChances = dChances[density];
					int chanceTotal = 0;
					for(int m = 0; m < densChances.length; m++){
						chanceTotal += densChances[m];
					}
					
					int findIndexOf = random.nextInt(chanceTotal);
					chanceTotal = 0; //Going to reuse this
					int imgIndex = 0;
					for(int m = 0; m < densChances.length; m++){
						chanceTotal += densChances[m];
						if(chanceTotal >= findIndexOf){
							imgIndex = m;
							break;
						}
					}
					
					ImageType iType = groundSpriteType[imgIndex];
					
					int xVal = (int)((rand.nextInt((int)Game.WINDOW_WIDTH)/cW) + cellWidth*j);
								
					int yVal = (int)((rand.nextInt((int)Game.WINDOW_HEIGHT)/cH) + cellHeight*i + yOffset);
					
					GroundSpriteTriple triple = new GroundSpriteTriple(xVal, yVal, iType);
					triples.add(triple);
				}
				
			}
			
		}
		
		initializeBiomeSpritesTriples(triples);
		
	}
	
	private void initializeBiomeSpritesTriples(PriorityQueue<GroundSpriteTriple> triples){
		
		ArrayList<GroundSprite> sprites = new ArrayList<>();
		
		while(triples.peek() != null){
			GroundSpriteTriple triple = triples.remove();
			
			ImageType iType = triple.getType();
			int		  xVal  = triple.getX();
			int		  yVal  = triple.getY();
			
			Image spriteImage = imgFac.getImage(iType);
			
			GroundSprite sprite = gsPool.getGroundSprite(System.currentTimeMillis());
			sprite.setImage(spriteImage);
			sprite.setX(xVal - iType.getWidth()/2);
			sprite.setY(yVal - iType.getHeight());
			sprite.setYVel(yVel);
			
			sprites.add(sprite);
		}
		
		groundSprites.addAll(sprites);
		List<Node> nodes = new ArrayList<>(sprites);
		nodes.addAll(spriteLayer.getChildren());
		spriteLayer.getChildren().clear();
		spriteLayer.getChildren().addAll(nodes);
	}
	
}
