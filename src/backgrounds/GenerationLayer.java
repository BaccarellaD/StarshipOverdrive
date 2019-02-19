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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GenerationLayer extends Pane{
	
	private long last_millis;
	
	/**
	 * X velocity of the pane and sprites
	 */
	public double xVel;
	
	/**
	 * Y velocity of the pane and sprites
	 */
	public double yVel;

	/**
	 * Each element corresponds to a modifier that each cell
	 * in the density levels can be shifted by, each element is weighted equally
	 * so using duplicates in necessary to give possibilities more weight. 
	 */
	private int[] biomeTransArr;
	
	/**
	 * An array of the possible Image types that can be spawned
	 */
	private ImageType[] groundSpriteType;
	
	/**
	 * A 2d array where each row corresponds to a density level, and each cell is a number that
	 * corresponds to the likelihood of the corresponding index of groundSpriteType to spawn
	 */
	private int[][] dChances;
	
	/**
	 * Each row corresponds to a biome density. [i][0] is the lower bound of sprites that
	 * can spawn, and [i][1] is the upper bound that can spawn.
	 */
	private int[][] upperAndLowerBounds;
	
	
	private int[][] densityLevels1;
	private int[][] densityLevels2;
	
	private List<GroundSprite> groundSprites;
	
	boolean generationPass;
	
	
	/**
	 * Floors that will scroll vertically down the page. Once one is no longer visible it
	 * will return to the top and regenerate new sprites.
	 */
	Pane floor1;
	Pane floor2;
	
	/**
	 * The ground sprite pool that all ground sprites are polled from and returned to
	 */
	private GroundSpritePool gsPool;
	
	private Random random;
	
	private ImageFactory imgFac;
	
	/* *******************************************************************************************************************************************************
	 * 							New Foliage generation
	 * 
	 * *******************************************************************************************************************************************************/
	
	
	public GenerationLayer(double xVel, double yVel, int[] biomeTransArr, ImageType[] groundSpriteType,
		int[][] dChances, int[][] upperAndLowerBounds) {
		
		last_millis = -1;
		
		this.xVel = xVel;
		this.yVel = yVel;
		this.biomeTransArr = biomeTransArr;
		this.groundSpriteType = groundSpriteType;
		this.dChances = dChances;
		this.upperAndLowerBounds = upperAndLowerBounds;
		
		this.groundSprites = new ArrayList<>();
		
		this.floor1 = new Pane();
		this.floor2 = new Pane();
		
		this.gsPool = GroundSpritePool.getPool();
		this.random = new Random();
		this.imgFac = ImageFactory.getImageFactory();
		
		this.generationPass = false;
		
		initializeBackground();
		initializeFoliage();
		
	}
	
	public void setScrollBacground(Background bg) {
		floor1.setBackground(bg);
		floor2.setBackground(bg);
	}
	
	
	public List<GameEvent<?>> tick(long cur_millis){
		
		List<GameEvent<?>> events =  new ArrayList<>();
		
		if(last_millis == -1){
			last_millis = cur_millis;
			return events;
		}
		updateBackground(cur_millis);
		
		last_millis = cur_millis;
		
		if(isGenerationPass()) 
			System.out.println("Generation Pass:" + isGenerationPass());
		
		return events;
		
	}
	
	/**
	 * Updates floor pane translation and calls a new population of sprites if needed,
	 * calls update sprites always
	 */
	private void updateBackground(long cur_millis) {
		
		updateSprites(cur_millis);
		
		long dt = cur_millis - last_millis;
		
		floor1.setTranslateX(floor1.getTranslateX() + dt * xVel);
		floor1.setTranslateY(floor1.getTranslateY() + dt * yVel);
		
		floor2.setTranslateX(floor2.getTranslateX() + dt * xVel);
		floor2.setTranslateY(floor2.getTranslateY() + dt * yVel);
		
		generationPass = true;
		
		if(floor1.getTranslateY() >= Game.WINDOW_HEIGHT){
			floor1.setTranslateY(floor2.getTranslateY() - Game.WINDOW_HEIGHT);
			populateNewBiomeDensities(densityLevels1, densityLevels2);
			this.populateBiomeSprites(densityLevels1, (int)-Game.WINDOW_HEIGHT);
			System.out.println("Sprites size: " + groundSprites.size());
		}
		else if(floor2.getTranslateY() >= Game.WINDOW_HEIGHT){
			floor2.setTranslateY(floor1.getTranslateY() - Game.WINDOW_HEIGHT);
			populateNewBiomeDensities(densityLevels2, densityLevels1);
			this.populateBiomeSprites(densityLevels2, (int)-Game.WINDOW_HEIGHT);
			System.out.println("Sprites size: " + groundSprites.size());
		}
		else {
			generationPass = false;
		}
		
	}
	
	/**
	 * Updates ground sprites
	 * @param cur_millis
	 */
	
	private void updateSprites(long cur_millis) {
		
		List<GameEvent<?>> gsEvents = new ArrayList<>();
		
		for(GroundSprite sprite: groundSprites){
			List<GameEvent<?>> events = sprite.tick(cur_millis);
			if(!events.isEmpty()) gsEvents.addAll(events);
		}
		
		for(GameEvent<?> gsEvent: gsEvents){
			if(gsEvent.getEventType() == EventType.REMOVE_SPRITE){
				removeGroundSprite((GroundSprite)gsEvent.getData());
			}
		}
		
	}
	
	private void removeGroundSprite(GroundSprite sprite) {
		groundSprites.remove(sprite);
		this.getChildren().remove(sprite);
		gsPool.returnGroundSprite(sprite);
	}
	
	/**
	 * places floor panes into place with overlap
	 */
	private void initializeBackground() {
		floor1.setMinHeight(Game.WINDOW_HEIGHT+1);
		floor1.setMinWidth(Game.WINDOW_WIDTH);
		
		floor2.setMinHeight(Game.WINDOW_HEIGHT+1);
		floor2.setMinWidth(Game.WINDOW_WIDTH);
		
		floor2.setTranslateY(-Game.WINDOW_HEIGHT); //give a little overlap
		
		this.getChildren().addAll(floor1, floor2);
	}

	/**
	 * Used for the initialization of the of foliage and biome sprites
	 */
	private void initializeFoliage(){
		
		densityLevels1 = new int[5][4];
		densityLevels2 = new int[5][4];
		
		int seed = random.nextInt(dChances.length);
		
		densityLevels1[4][0] = seed; //put seed into bottom left corner
		
		//populate left
		for(int i = 3; i >= 0; i--){
			int modifier = biomeTransArr[random.nextInt(biomeTransArr.length)];
			int newDensity = densityLevels1[i+1][0] + modifier;
			if(newDensity < 0) newDensity = 0;
			if(newDensity > (dChances.length-1)) newDensity = (dChances.length-1);
			densityLevels1[i][0] = newDensity;
		}
		
		//populate bottom
		for(int i = 1; i < 4; i++){
			int modifier = biomeTransArr[random.nextInt(biomeTransArr.length)];
			int newDensity = densityLevels1[4][i-1] + modifier;
			if(newDensity < 0) newDensity = 0;
			if(newDensity > (dChances.length-1)) newDensity = (dChances.length-1);
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
				if(newDensity > (dChances.length-1)) newDensity = (dChances.length-1);
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
			if(newDensity > (dChances.length-1)) newDensity = (dChances.length-1);
			densityChange[4][j] = newDensity;
		}

		//populate left
		for(int i = 3; i >= 0; i--){
			int modifier = biomeTransArr[random.nextInt(biomeTransArr.length)];
			int newDensity = densityChange[i+1][0] + modifier;
			if(newDensity < 0) newDensity = 0;
			if(newDensity > (dChances.length-1)) newDensity = (dChances.length-1);
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
				if(newDensity > (dChances.length-1)) newDensity = (dChances.length-1);
				densityChange[i][j] = newDensity;
			}
		}
		
	}
	
	/**
	 * Takes a density map and generates the triples based on the parameters
	 * @param densityLevels a 2d array of the density levels to populate from
	 * @param yOffset the offset for where the top of the sprites will be places
	 */
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
					
					int findIndexOf = random.nextInt(chanceTotal+1);
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
					
					int xVal = (int)((random.nextInt((int)Game.WINDOW_WIDTH)/cW) + cellWidth*j);
								
					int yVal = (int)((random.nextInt((int)Game.WINDOW_HEIGHT)/cH) + cellHeight*i + yOffset);
					
					GroundSpriteTriple triple = new GroundSpriteTriple(xVal, yVal, iType);
					triples.add(triple);
				}
				
			}
			
		}
		
		initializeBiomeSpritesTriples(triples);
		
	}
	
	/**
	 * Takes a queue of triples, initializes them, and places them into the pane
	 * @param triples Ground Sprite Tripples
	 */
	
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
		nodes.addAll(this.getChildren());
		this.getChildren().clear();
		this.getChildren().addAll(nodes);
	}
	
	/**
	 * Returns true if the last tick resulted new sprite generation
	 * @return
	 */
	public boolean isGenerationPass() {
		return generationPass;
	}

	/**
	 * Will take a ground sprite and place it into the pane,
	 *  and set its velocity to the other sprite's velocity
	 * @param sprite
	 */
	public void injectAbsorbVelocity(GroundSprite sprite) {
		sprite.setXVel(xVel);
		sprite.setYVel(yVel);
		injectSprite(sprite);
	}

	/**
	 * Will take a ground sprite and place it into the pane
	 * @param sprite
	 */
	public void injectSprite(GroundSprite sprite) {
		this.groundSprites.add(sprite);
		this.getChildren().add(sprite);
	}
	
}
