package controllers;

import java.io.IOException;

import javafx.scene.image.Image;
import runTimeChecks.JarChecker;

public class ImageFactory {
	public enum ImageType{
		PLAYER_SHIP_1			(55, 87),
		PLAYER_SHIP_1_SHIELDED	(55, 87),
		ENEMY_SHIP_1			(100, 100),
		ENEMY_SHIP_2			(58, 64),
		ENEMY_SHIP_3			(81, 94),
		PLAYER_PROJECTILE_1		(25,  25),
		ENEMY_PROJECTILE_1		(25,  25),
		
		BACKGROUND_GRASS		(800, 1000),
		SHRUB_1					(21, 19),
		SHRUB_2					(15, 13),
		SHRUB_3					(19, 18),
		SMALL_TREE_1			(32, 41),
		SMALL_TREE_2			(30, 39),
		SMALL_TREE_3			(25, 30),
		TREE_1					(113, 119),
		TREE_2					(102, 140),
		CLOUD_1					(325, 79),
		CLOUD_2					(200, 200),
		
		HEALTHBAR_EMPTY			(89, 16),
		HEALTHBAR_SPHERE		(89, 16),
		HEALTHBAR_HEALTH		(89, 16),
		HEALTHBAR_SHIELD_BAR	(89, 16),
		
		STAR_1					(13,13),
		PIXEL_GALAXY			(500,355),
		TILABLE_STAR_BACKGROUND (400, 400),
		
		PLANET_1				(85,85),
		PLANET_2				(85,85),
		PLANET_3				(85,85),
		PLANET_4				(85,85),
		
		STAR_BLUE_LARGE_2		(13, 14),
		STAR_BLUE_MEDIUM		(4, 4),
		STAR_BLUE_RED_LARGE		(8, 8),
		STAR_BLUE_RED_MEDIUM	(6, 5),
		STAR_BLUE_RED_SMALL		(5, 5),
		STAR_BLUE_SMALL			(4, 4),
		STAR_RED_LARGE			(8, 8),
		STAR_RED_MEDIUM			(4, 4),
		STAR_RED_SMALL			(3, 4),
		
		METEOR_1_LARGE			(48, 48),
		METEOR_1_MEDUIUM		(33, 32),
		METEOR_1_SMALL			(16, 16),
		METEOR_1_TINY			(8, 8),
		
		PILOT_1					(90,70);
		
		
		//Enum Initialization
		
		ImageType(int width, int height){
			this.height = height;
			this.width = width;
		}
		
		public final int getHeight(){return height;}
		public final int getWidth(){return width;}
		
		private final int height;
		private final int width;
	}
	
	private Image playerShip1;
	private Image playerShip1Shielded;
	private Image enemyShip1;
	private Image enemyShip2;
	private Image enemyShip3;
	private Image playerProjectile1;
	private Image enemyProjectile1;
	
	private Image backgroundGrass;
	private Image shrub1;
	private Image shrub2;
	private Image shrub3;
	private Image smallTree1;
	private Image smallTree2;
	private Image smallTree3;
	private Image tree1;
	private Image tree2;
	
	private Image cloud1;
	private Image cloud2;
	
	private Image healthbarEmpty;
	private Image healthbarSphere;
	private Image healthbarHealth;
	private Image healthbarShieldBar;
	
	private Image star1;
	private Image pixelGalaxy;
	private Image tilableStarBackground;
	
	private Image planet1;
	private Image planet2;
	private Image planet3;
	private Image planet4;
	
	private Image starBlueLarge2;
	private Image starBlueMedium;
	private Image starBlueRedLarge;
	private Image starBlueRedMedium;
	private Image starBlueRedSmall;
	private Image starBlueSmall;
	private Image starRedLarge;
	private Image starRedMedium;
	private Image starRedSmall;
	
	private Image meteor1Large;
	private Image meteor1Medium;
	private Image meteor1Small;
	private Image meteor1Tiny;
	
	private Image pilot1;
	
	private static ImageFactory imageFactory;
	
	public static ImageFactory getImageFactory(){
		if(imageFactory == null){
			imageFactory = new ImageFactory();
		}
		return imageFactory;
	}
	
	private ImageFactory(){
		loadImages();
	}
	
	private void loadImages(){
		
		this.playerShip1       		= new Image(getFilePath("bitmaps/ships/Player_Ship_1.png"));
		this.playerShip1Shielded 	= new Image(getFilePath("bitmaps/ships/Player_Ship_1_Shielded.png"));
		this.enemyShip1        		= new Image(getFilePath("bitmaps/ships/Enemy_Ship_1.png"));
		this.enemyShip2        		= new Image(getFilePath("bitmaps/ships/Enemy_Ship_2.png"));
		this.enemyShip3		   		= new Image(getFilePath("bitmaps/ships/Enemy_Ship_3.png"));
		this.playerProjectile1 		= new Image(getFilePath("bitmaps/projectiles/Player_Laser.png"));
		this.enemyProjectile1  		= new Image(getFilePath("bitmaps/projectiles/Enemy_Laser.png"));
		                                   
		this.backgroundGrass   		= new Image(getFilePath("bitmaps/background1/FullWindowGrass.png"));
		this.shrub1            		= new Image(getFilePath("bitmaps/background1/Shrub1.png"));
		this.shrub2           		= new Image(getFilePath("bitmaps/background1/Shrub2.png"));
		this.shrub3            		= new Image(getFilePath("bitmaps/background1/Shrub3.png"));
		this.smallTree1        		= new Image(getFilePath("bitmaps/background1/SmallTree1.png"));
		this.smallTree2        		= new Image(getFilePath("bitmaps/background1/SmallTree2.png"));
		this.smallTree3        		= new Image(getFilePath("bitmaps/background1/SmallTree3.png"));
		this.tree1            	 	= new Image(getFilePath("bitmaps/background1/Tree1.png"));
		this.tree2             		= new Image(getFilePath("bitmaps/background1/Tree2.png"));
		                                     
		this.cloud1            		= new Image(getFilePath("bitmaps/background1/Cloud1.png"));
		this.cloud2            		= new Image(getFilePath("bitmaps/background1/Cloud2.png"));
		                                       
		this.healthbarEmpty	   		= new Image(getFilePath("bitmaps/healthbar/HealthBarNewEmpty.png"));
		this.healthbarHealth	   	= new Image(getFilePath("bitmaps/healthbar/HealthBarNewHealth.png"));
		this.healthbarSphere   		= new Image(getFilePath("bitmaps/healthbar/HealthBarNewShieldBall.png"));
		this.healthbarShieldBar  	= new Image(getFilePath("bitmaps/healthbar/HealthBarNewShieldBar.png"));
		                                      
		this.star1			   		= new Image(getFilePath("bitmaps/menubackground/Star1.png"));
		this.pixelGalaxy	   		= new Image(getFilePath("bitmaps/menubackground/pixelGalaxy.png"));
		this.tilableStarBackground	= new Image(getFilePath("bitmaps/space/tilableStarsBackground.gif"));

		this.planet1		   		= new Image(getFilePath("bitmaps/menubackground/Planet1.png"));
		this.planet2		   		= new Image(getFilePath("bitmaps/menubackground/Planet2.png"));
		this.planet3		   		= new Image(getFilePath("bitmaps/menubackground/Planet3.png"));
		this.planet4		   		= new Image(getFilePath("bitmaps/menubackground/Planet4.png"));
		                                     
		this.starBlueLarge2			= new Image(getFilePath("bitmaps/space/StarBlueLarge2.png"));
		this.starBlueMedium			= new Image(getFilePath("bitmaps/space/StarBlueMedium.png"));
		this.starBlueRedLarge		= new Image(getFilePath("bitmaps/space/StarBlueRedLarge.png"));
		this.starBlueRedMedium		= new Image(getFilePath("bitmaps/space/StarBlueRedMedium.png"));
		this.starBlueRedSmall		= new Image(getFilePath("bitmaps/space/StarBlueRedSmall.png"));
		this.starBlueSmall			= new Image(getFilePath("bitmaps/space/StarBlueSmall.png"));
		this.starRedLarge			= new Image(getFilePath("bitmaps/space/StarRedLarge.png"));
		this.starRedMedium			= new Image(getFilePath("bitmaps/space/StarRedMedium.png"));
		this.starRedSmall			= new Image(getFilePath("bitmaps/space/StarRedSmall.png"));
		
		this.meteor1Large			= new Image(getFilePath("bitmaps/meteors/Meteor1Large.png"));
		this.meteor1Medium			= new Image(getFilePath("bitmaps/meteors/Meteor1Medium.png"));
		this.meteor1Small			= new Image(getFilePath("bitmaps/meteors/Meteor1Small.png"));
		this.meteor1Tiny			= new Image(getFilePath("bitmaps/meteors/Meteor1Tiny.png"));
		                                       
		this.pilot1			   		= new Image(getFilePath("bitmaps/npcs/pilot1.png"));
		
		/*
		this.playerShip1       		= new Image("file:resources/bitmaps/ships/Player_Ship_1.png");
		this.playerShip1Shielded 	= new Image("file:resources/bitmaps/ships/Player_Ship_1_Shielded.png");
		this.enemyShip1        		= new Image("file:resources/bitmaps/ships/Enemy_Ship_1.png");
		this.enemyShip2        		= new Image("file:resources/bitmaps/ships/Enemy_Ship_2.png");
		this.enemyShip3		   		= new Image("file:resources/bitmaps/ships/Enemy_Ship_3.png");
		this.playerProjectile1 		= new Image("file:resources/bitmaps/projectiles/Player_Laser.png");
		this.enemyProjectile1  		= new Image("file:resources/bitmaps/projectiles/Enemy_Laser.png");
		
		this.backgroundGrass   		= new Image("file:resources/bitmaps/background1/FullWindowGrass.png");
		this.shrub1            		= new Image("file:resources/bitmaps/background1/Shrub1.png");
		this.shrub2           		= new Image("file:resources/bitmaps/background1/Shrub2.png");
		this.shrub3            		= new Image("file:resources/bitmaps/background1/Shrub3.png");
		this.smallTree1        		= new Image("file:resources/bitmaps/background1/SmallTree1.png");
		this.smallTree2        		= new Image("file:resources/bitmaps/background1/SmallTree2.png");
		this.smallTree3        		= new Image("file:resources/bitmaps/background1/SmallTree3.png");
		this.tree1            	 	= new Image("file:resources/bitmaps/background1/Tree1.png");
		this.tree2             		= new Image("file:resources/bitmaps/background1/Tree2.png");
		
		this.cloud1            		= new Image("file:resources/bitmaps/background1/Cloud1.png");
		this.cloud2            		= new Image("file:resources/bitmaps/background1/Cloud2.png");
		
		this.healthbarEmpty	   		= new Image("file:resources/bitmaps/healthbar/HealthBarNewEmpty.png");
		this.healthbarHealth	   	= new Image("file:resources/bitmaps/healthbar/HealthBarNewHealth.png");
		this.healthbarSphere   		= new Image("file:resources/bitmaps/healthbar/HealthBarNewShieldBall.png");
		this.healthbarShieldBar  	= new Image("file:resources/bitmaps/healthbar/HealthBarNewShieldBar.png");
		
		this.star1			   		= new Image("file:resources/bitmaps/menubackground/Star1.png");
		this.pixelGalaxy	   		= new Image("file:resources/bitmaps/menubackground/pixelGalaxy.png");
		this.tilableStarBackground	= new Image("file:resources/bitmaps/space/tilableStarsBackground.gif");
		
		this.planet1		   		= new Image("file:resources/bitmaps/menubackground/Planet1.png");
		this.planet2		   		= new Image("file:resources/bitmaps/menubackground/Planet2.png");
		this.planet3		   		= new Image("file:resources/bitmaps/menubackground/Planet3.png");
		this.planet4		   		= new Image("file:resources/bitmaps/menubackground/Planet4.png");
		
		this.starBlueLarge2			= new Image("file:resources/bitmaps/space/StarBlueLarge2.png");
		this.starBlueMedium			= new Image("file:resources/bitmaps/space/StarBlueMedium.png");
		this.starBlueRedLarge		= new Image("file:resources/bitmaps/space/StarBlueRedLarge.png");
		this.starBlueRedMedium		= new Image("file:resources/bitmaps/space/StarBlueRedMedium.png");
		this.starBlueRedSmall		= new Image("file:resources/bitmaps/space/StarBlueRedSmall.png");
		this.starBlueSmall			= new Image("file:resources/bitmaps/space/StarBlueSmall.png");
		this.starRedLarge			= new Image("file:resources/bitmaps/space/StarRedLarge.png");
		this.starRedMedium			= new Image("file:resources/bitmaps/space/StarRedMedium.png");
		this.starRedSmall			= new Image("file:resources/bitmaps/space/StarRedSmall.png");
		
		this.pilot1			   		= new Image("file:resources/bitmaps/npcs/pilot1.png");
		*/
	}
	
	public Image getImage(ImageType type){
		switch(type){
			case PLAYER_SHIP_1: 		return playerShip1;
			case PLAYER_SHIP_1_SHIELDED:return playerShip1Shielded;
			case ENEMY_SHIP_1:          return enemyShip1;
			case ENEMY_SHIP_2:			return enemyShip2;
			case ENEMY_SHIP_3:			return enemyShip3;
			case PLAYER_PROJECTILE_1:   return playerProjectile1;
			case ENEMY_PROJECTILE_1:    return enemyProjectile1; 
			case BACKGROUND_GRASS:      return backgroundGrass;  
			case SHRUB_1:               return shrub1;           
			case SHRUB_2:               return shrub2;           
			case SHRUB_3:               return shrub3;           
			case SMALL_TREE_1:          return smallTree1;       
			case SMALL_TREE_2:          return smallTree2;       
			case SMALL_TREE_3:          return smallTree3;       
			case TREE_1:                return tree1;            
			case TREE_2:                return tree2; 
			case CLOUD_1:				return cloud1;
			case CLOUD_2:				return cloud2;
			case HEALTHBAR_EMPTY:		return healthbarEmpty;
			case HEALTHBAR_SPHERE: 		return healthbarSphere;
			case HEALTHBAR_HEALTH:		return healthbarHealth;
			case HEALTHBAR_SHIELD_BAR:	return healthbarShieldBar;
			case STAR_1:				return star1;
			case PIXEL_GALAXY:			return pixelGalaxy;
			case TILABLE_STAR_BACKGROUND:return tilableStarBackground;
			case PLANET_1:				return planet1;
			case PLANET_2:				return planet2;
			case PLANET_3:				return planet3;
			case PLANET_4:				return planet4;
			case STAR_BLUE_LARGE_2:		return starBlueLarge2;
			case STAR_BLUE_MEDIUM:		return starBlueMedium;
			case STAR_BLUE_RED_LARGE:	return starBlueRedLarge;
			case STAR_BLUE_RED_MEDIUM:	return starBlueRedMedium;
			case STAR_BLUE_RED_SMALL:	return starBlueRedSmall;
			case STAR_BLUE_SMALL:		return starBlueSmall;
			case STAR_RED_LARGE:		return starRedLarge;
			case STAR_RED_MEDIUM:		return starRedMedium;
			case STAR_RED_SMALL:		return starRedSmall;
			case METEOR_1_LARGE:		return meteor1Large;
			case METEOR_1_MEDUIUM:		return meteor1Medium;
			case METEOR_1_SMALL:		return meteor1Small;
			case METEOR_1_TINY:			return meteor1Tiny;
			case PILOT_1:				return pilot1;
		}
		return null;
	}
	
	private String getFilePath(String relPath) {
		
		Class<?> c = this.getClass();
		ClassLoader cLoader = c.getClassLoader();
		
		return cLoader.getResource(relPath).toExternalForm();
		
	}
}
