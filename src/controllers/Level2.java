package controllers;

import java.util.Random;

import application.Game;
import backgrounds.GameBackground;
import backgrounds.Level2Background;
import controllers.ImageFactory.ImageType;
import controllers.MusicController.Music;
import events.EventWave;
import events.GameEvent;
import events.TimedEvent;
import javafx.geometry.HorizontalDirection;
import events.GameEvent.EventType;
import sprites.Enemy;
import sprites.EnemyFactory;
import sprites.PhotoSprite;
import sprites.EnemyFactory.EnemyType;
import sprites.MeteorEnemy;
import sprites.ui.DialogPopup;
import sprites.ui.LowerThirdPopup;
import sprites.ui.WarningPopup;

public class Level2 extends WaveBasedLevel{

	
	public Level2(){
		super(Music.LUNA_ASCENSION);
	}

	@Override
	void initializeBackground() {
		bg = new Level2Background();
		this.getChildren().add(0, bg);
	}

	@Override
	public GameBackground getLevelBackground() {
		return bg;
	}
	
	@Override
	void initalizeEvents() {
		
		EnemyFactory enemyFac = new EnemyFactory();
		
		
		//Meteor Warning
		
		String dialog5 = "Meteor Shower Incoming!";
		
		DialogPopup popup5 = new WarningPopup(dialog5, 300, 1500);
		
		GameEvent<DialogPopup> evx5 = new GameEvent<>(EventType.PLACE_POPUP, popup5);
		
		TimedEvent tex5 = new TimedEvent(evx5, 1000);
		
		EventWave meteorWarning = new EventWave(tex5);
		
		//Meteor Wave
		
		EventWave wave1 = generateMeteorShower(HorizontalDirection.RIGHT, 10, 1000, 1000);
		
		
		//WAVE 2
		
		Enemy e2 = enemyFac.getEnemy(EnemyType.SHOTGUN_WAVE_1, 400, 150);
		
		GameEvent<Enemy> ev2 = new GameEvent<>(EventType.SPAWN_ENEMY_ANIMATE, e2);
		
		TimedEvent te2 = new TimedEvent(ev2, 2500);
		
		EventWave wave2 = new EventWave(te2);
		
		

		
		
		
		levelWaves.add(meteorWarning);
		levelWaves.add(wave1);
		levelWaves.add(wave2);

	}
	
	
	private EventWave generateMeteorShower(HorizontalDirection direction, int numOfMeteors, long startDelayTime, long timeBetween) {
		
		EnemyFactory enemyFac = new EnemyFactory();
		Random rand = new Random();
		EventWave wave = new EventWave();
		
		double wX = Game.WINDOW_WIDTH;
		
		for(int i = 0; i < numOfMeteors; i++) {
			
			Enemy enemy = null;
			
			if(direction.equals(HorizontalDirection.LEFT)) {
				double rX = (rand.nextDouble() * wX) - 25;
				double rY = (rand.nextDouble() * -100) - ImageType.METEOR_1_LARGE.getHeight();
				
				System.out.println("RY: " + rY);
				
				enemy = enemyFac.getEnemy(EnemyType.METEOR_1_LEFT_FLYBY, rX,  rY);
			}else {
				double rX = (rand.nextDouble() * (wX*(3d/4d)) + (wX*(1/4d))) + 25;
				double rY = (rand.nextDouble() * -20) - ImageType.METEOR_1_LARGE.getHeight();
				
				System.out.println("RY: " + rY);
				
				enemy = enemyFac.getEnemy(EnemyType.METEOR_1_RIGHT_FLYBY, rX,  rY);
			}
			
			GameEvent<Enemy> ev = new GameEvent<>(EventType.SPAWN_ENEMY, enemy);
			TimedEvent te = new TimedEvent(ev, i*timeBetween+startDelayTime);
			wave.addTimedEvent(te);
		}
		
		return wave;
	}
	 
}
