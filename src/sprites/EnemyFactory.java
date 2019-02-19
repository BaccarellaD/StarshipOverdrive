package sprites;

import java.util.ArrayList;
import java.util.List;

import application.Game;
import controllers.ImageFactory;
import controllers.ImageFactory.ImageType;
import controllers.SoundFXController.SoundType;
import events.GameEvent;
import events.GameEvent.GEBuilder;
import events.TargetedSpawnEvent;
import javafx.scene.image.Image;
import sprites.bulletPatterns.ArcPattern;
import sprites.bulletPatterns.BulletPattern;
import sprites.bulletPatterns.ShotgunPattern;
import sprites.bulletPatterns.StraightShot;
import sprites.movementPatterns.MovementPattern;
import sprites.movementPatterns.SinWaveMovement;
import sprites.movementPatterns.StationaryMovement;
import sprites.movementPatterns.StraightMovement;

public class EnemyFactory {
	
	public enum EnemyType{
		SHOTGUN_WAVE_1,
		FLYBY_LEFT_1,
		FLYBY_RIGHT_1,
		FLYBY_DOWN_1,
		MACHINEGUN_STATIONARY_1,
		METEOR_1_LEFT_FLYBY,
		METEOR_1_RIGHT_FLYBY
	}
	
	ImageFactory imgFac;
	
	public EnemyFactory(){
		imgFac = ImageFactory.getImageFactory();
	}
	
	public Enemy getEnemy(EnemyType type, double x, double y){
		
		switch(type){
			case SHOTGUN_WAVE_1: 			return getShotGunWave1(x, y);
			case FLYBY_LEFT_1: 				return getFlyByLeft1(x, y);
			case FLYBY_RIGHT_1: 			return getFlyByRight1(x, y);
			case FLYBY_DOWN_1: 				return getFlyByDown1(x, y);
			case MACHINEGUN_STATIONARY_1:	return getMachineGunStationary1(x, y);
			case METEOR_1_LEFT_FLYBY:		return getMeteor1LeftFlyby(x, y);
			case METEOR_1_RIGHT_FLYBY:		return getMeteor1RightFlyby(x, y);
		}
		
		return null;
	}

	private Enemy getMachineGunStationary1(double x, double y) {
		double health = 80.0;
		long points = 100;
		
		//Bullet Pattern Vars
		double vel			= .50;
		double angle		= 60;
		int amtBullets		= 7;
		double dmg			= 8;
		long shotDuration	= 1200;
		long pauseDuration	= 1000;
		
		//Movement pattern vars
		//null
		
		EnemyBasic1 enemy = new EnemyBasic1(x, y, health);
		
		enemy.setShipImage(ImageType.ENEMY_SHIP_3);
		
		BulletPattern bp = new ArcPattern(enemy, vel, angle, amtBullets, dmg, shotDuration, pauseDuration);
		enemy.setBulletPattern(bp);
		
		MovementPattern mp = new StationaryMovement(enemy);
		enemy.setMovementPattern(mp);
		
		enemy.setPoints(points);
		
		return enemy;
	}

	private Enemy getFlyByRight1(double x, double y) {
		double health = 20.0;
		long points = 20;
		
		//Bullet Pattern Vars
		double bulletVelX	= .0;
		double bulletVelY	= .50;
		double dmg			= 10;
		long   firerate		= 800;
		
		//Movement pattern vars
		double enemyVelX	= -.25;
		double enemyVelY	= .15;
		double lowerX		= 0;
		double upperY		= Game.WINDOW_HEIGHT;
		
		EnemyBasic1 enemy = new EnemyBasic1(x, y, health);
		
		enemy.setShipImage(ImageType.ENEMY_SHIP_2);
		
		BulletPattern bp = new StraightShot(enemy, bulletVelX, bulletVelY, dmg, firerate);
		enemy.setBulletPattern(bp);
		
		StraightMovement mp = new StraightMovement(enemy);
		mp.setLowerX(lowerX);
		mp.setUpperY(upperY);
		enemy.setXVel(enemyVelX);
		enemy.setYVel(enemyVelY);
		enemy.setMovementPattern(mp);
		
		enemy.setPoints(points);
		
		return enemy;
	}

	private Enemy getFlyByLeft1(double x, double y) {
		double health = 20.0;
		long points = 20;
		
		//Bullet Pattern Vars
		double bulletVelX	= .0;
		double bulletVelY	= .50;
		double dmg			= 10;
		long   firerate		= 800;
		
		//Movement pattern vars
		double enemyVelX	= .25;
		double enemyVelY	= .15;
		double upperX		= Game.WINDOW_WIDTH;
		double upperY		= Game.WINDOW_HEIGHT;
		
		EnemyBasic1 enemy = new EnemyBasic1(x, y, health);
		
		enemy.setShipImage(ImageType.ENEMY_SHIP_2);
		
		BulletPattern bp = new StraightShot(enemy, bulletVelX, bulletVelY, dmg, firerate);
		enemy.setBulletPattern(bp);
		
		StraightMovement mp = new StraightMovement(enemy);
		mp.setUpperX(upperX);
		mp.setUpperY(upperY);
		enemy.setXVel(enemyVelX);
		enemy.setYVel(enemyVelY);
		enemy.setMovementPattern(mp);
		
		enemy.setPoints(points);
		
		return enemy;
	}
	
	private Enemy getFlyByDown1(double x, double y) {
		double health = 20.0;
		long points = 20;
		
		//Bullet Pattern Vars
		double 	vel			= .50;
		double 	angle		= 20;
		int 	amtBullets	= 3;
		double 	dmg			= 8;
		long 	fireRate	= 1000;
		
		SoundType type = SoundType.LASER_LIGHT;
		
		//Movement pattern vars
		double enemyVelX	= .00;
		double enemyVelY	= .25;
		double upperY		= Game.WINDOW_HEIGHT;
		
		EnemyBasic1 enemy = new EnemyBasic1(x, y, health);
		
		enemy.setShipImage(ImageType.ENEMY_SHIP_2);
		
		BulletPattern bp = new ShotgunPattern(enemy, vel, angle, amtBullets, dmg, fireRate);
		bp.setSoundType(type);
		enemy.setBulletPattern(bp);
		
		StraightMovement mp = new StraightMovement(enemy);
		mp.setUpperY(upperY);
		enemy.setXVel(enemyVelX);
		enemy.setYVel(enemyVelY);
		enemy.setMovementPattern(mp);
		
		enemy.setPoints(points);
		
		return enemy;
	}

	private Enemy getShotGunWave1(double x, double y) {
		double health = 40.0;
		long points = 60;
		
		//Bullet Pattern Vars
		double 	vel			= .50;
		double 	angle		= 15;
		int 	amtBullets	= 5;
		double 	dmg			= 10;
		long 	fireRate	= 1200;
		
		//Movement pattern vars
		double waveHeightX	= 150;
		long millisToCycle	= 5000;
		
		EnemyBasic1 enemy = new EnemyBasic1(x, y, health);
		
		enemy.setShipImage(ImageType.ENEMY_SHIP_1);
		
		BulletPattern bp = new ShotgunPattern(enemy, vel, angle, amtBullets, dmg, fireRate);
		enemy.setBulletPattern(bp);
		
		MovementPattern mp = new SinWaveMovement(enemy, x, waveHeightX, millisToCycle);
		enemy.setMovementPattern(mp);
		
		enemy.setPoints(points);
		
		return enemy;
	}
	
	private Enemy getMeteor1LeftFlyby(double x, double y) {
		double health = 30.0;
		long points = 10;
		double collisionDamage = 6;
		
		//Resize Parameters
		ImageType imgType = ImageType.METEOR_1_LARGE;
		double imgScale = 2.0;
		
		//Movement pattern vars
		double enemyVelX	= .05;
		double enemyVelY	= .15;
		double upperX		= Game.WINDOW_WIDTH;
		double upperY		= Game.WINDOW_HEIGHT;
		
		MeteorEnemy enemy = new MeteorEnemy(x, y, 2000, health);
		
		enemy.setShipImage(imgType);
		
		enemy.setWidth(imgScale * imgType.getWidth());
		enemy.setHeight(imgScale * imgType.getHeight());
		
		StraightMovement mp = new StraightMovement(enemy);
		mp.setUpperX(upperX);
		mp.setUpperY(upperY);
		enemy.setXVel(enemyVelX);
		enemy.setYVel(enemyVelY);
		enemy.setMovementPattern(mp);
		
		enemy.setPoints(points);
		enemy.setCollisionDamage(collisionDamage);
		
		injectMeteorDeathRattleEvents(enemy, 5);
		
		return enemy;
	}

	private Enemy getMeteor1RightFlyby(double x, double y) {
		double health = 30.0;
		long points = 10;
		double collisionDamage = 8;
		
		//Resize Parameters
		ImageType imgType = ImageType.METEOR_1_LARGE;
		double imgScale = 2.0;
		
		//Movement pattern vars
		double enemyVelX	= -.05;
		double enemyVelY	= .15;
		double lowerX		= 0;
		double upperY		= Game.WINDOW_HEIGHT;
		
		MeteorEnemy enemy = new MeteorEnemy(x, y, 2000, health);
		
		enemy.setShipImage(imgType);
		
		enemy.setWidth(imgScale * imgType.getWidth());
		enemy.setHeight(imgScale * imgType.getHeight());
		
		StraightMovement mp = new StraightMovement(enemy);
		mp.setLowerX(lowerX);
		mp.setUpperY(upperY);
		enemy.setXVel(enemyVelX);
		enemy.setYVel(enemyVelY);
		enemy.setMovementPattern(mp);
		
		enemy.setPoints(points);
		enemy.setCollisionDamage(collisionDamage);
		
		injectMeteorDeathRattleEvents(enemy, 5);
		
		return enemy;
	}
	
	private void injectMeteorDeathRattleEvents(MeteorEnemy meteor, int numChildren) {
		
		for(int i = 0; i < numChildren; i++) {
			
			double health = 20.0;
			long points = 10;
			double collisionDamage = 4;
			
			//Resize Parameters
			ImageType imgType = ImageType.METEOR_1_SMALL;
			double imgScale = 2.0;
			
			//Movement pattern vars
			
			double roundAngle = i*(360/numChildren);
			
			double sinTheta = Math.sin(Math.toRadians(roundAngle));
			double cosTheta = Math.cos(Math.toRadians(roundAngle));
			
			double vel =  .05; // Math.sqrt(meteor.getXVel()*meteor.getXVel() + meteor.getYVel()*meteor.getYVel());
			
			double enemyVelX = cosTheta * vel + meteor.getXVel();
			double enemyVelY = sinTheta * vel + meteor.getYVel();
			
			double lowerX		= 0;
			double lowerY		= 0;
			double upperY		= Game.WINDOW_HEIGHT;
			double upperX 		= Game.WINDOW_WIDTH;
			
			MeteorEnemy enemy = new MeteorEnemy(2000, health);
			
			enemy.setShipImage(imgType);
			
			enemy.setWidth(imgScale * imgType.getWidth());
			enemy.setHeight(imgScale * imgType.getHeight());
			
			StraightMovement mp = new StraightMovement(enemy);
			mp.setLowerX(lowerX);
			mp.setUpperX(upperX);
			mp.setUpperY(upperY);
			mp.setLowerY(lowerY);
			
			enemy.setXVel(enemyVelX);
			enemy.setYVel(enemyVelY);
			enemy.setMovementPattern(mp);
			
			enemy.setPoints(points);
			enemy.setCollisionDamage(collisionDamage);
			
			GameEvent<Enemy> spawnEvent = GEBuilder.spawnEnemy(enemy);
			
			GameEvent<TargetedSpawnEvent> te = GEBuilder.targetedSpawnEvent(enemy, meteor, spawnEvent);
			
			meteor.addDeathRattleEvent(te);
		}
		
	}
	
}
