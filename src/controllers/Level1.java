package controllers;

import java.util.List;

import application.Game;
import backgrounds.GameBackground;
import backgrounds.Level1Background;
import backgrounds.Level2Background;
import controllers.ImageFactory.ImageType;
import controllers.MusicController.Music;
import events.EventWave;
import events.GameEvent;
import events.GameEvent.EventType;
import events.TimedEvent;
import sprites.Enemy;
import sprites.EnemyFactory;
import sprites.PhotoSprite;
import sprites.EnemyFactory.EnemyType;
import sprites.ui.DialogPopup;
import sprites.ui.LowerThirdPopup;
import sprites.ui.WarningPopup;

public class Level1 extends WaveBasedLevel{
	
	public Level1(){
		super(Music.LUNA_ASCENSION);
	}

	@Override
	void initializeBackground() {
		bg = new Level1Background();
		this.getChildren().add(0, bg);
	}

	@Override
	public GameBackground getLevelBackground() {
		return bg;
	}
	
	@Override
	void initalizeEvents() {
		
		EnemyFactory enemyFac = new EnemyFactory();
		
		//WAVE 1
		
		Enemy e1 = enemyFac.getEnemy(EnemyType.SHOTGUN_WAVE_1, 400, 150);
		
		GameEvent<Enemy> ev1 = new GameEvent<>(EventType.SPAWN_ENEMY_ANIMATE, e1);
		
		TimedEvent te1 = new TimedEvent(ev1, 2500);
		
		EventWave wave1 = new EventWave(te1);
		
		//WAVE 2
		
		double wX = Game.WINDOW_WIDTH;
		
		Enemy e2 = enemyFac.getEnemy(EnemyType.SHOTGUN_WAVE_1, 400,	  150);
		Enemy e3 = enemyFac.getEnemy(EnemyType.FLYBY_LEFT_1,   -150,  -50);
		Enemy e4 = enemyFac.getEnemy(EnemyType.FLYBY_RIGHT_1,  wX+150,-50);
		Enemy e5 = enemyFac.getEnemy(EnemyType.FLYBY_LEFT_1,   -150,  -50);
		Enemy e6 = enemyFac.getEnemy(EnemyType.FLYBY_RIGHT_1,  wX+150,-50);
		
		GameEvent<Enemy> ev2 = new GameEvent<>(EventType.SPAWN_ENEMY_ANIMATE, e2);
		GameEvent<Enemy> ev3 = new GameEvent<>(EventType.SPAWN_ENEMY, e3);
		GameEvent<Enemy> ev4 = new GameEvent<>(EventType.SPAWN_ENEMY, e4);
		GameEvent<Enemy> ev5 = new GameEvent<>(EventType.SPAWN_ENEMY, e5);
		GameEvent<Enemy> ev6 = new GameEvent<>(EventType.SPAWN_ENEMY, e6);
		
		TimedEvent te2 = new TimedEvent(ev2, 1000);
		TimedEvent te3 = new TimedEvent(ev3, 2000);
		TimedEvent te4 = new TimedEvent(ev4, 3000);
		TimedEvent te5 = new TimedEvent(ev5, 4000);
		TimedEvent te6 = new TimedEvent(ev6, 5000);
		
		EventWave wave2 = new EventWave(te2, te3, te4, te5, te6);
		
		//WAVE 3
		
		Enemy e7 = enemyFac.getEnemy(EnemyType.MACHINEGUN_STATIONARY_1, 400, 150);
		
		GameEvent<Enemy> ev7 = new GameEvent<>(EventType.SPAWN_ENEMY_ANIMATE, e7);
		
		TimedEvent te7 = new TimedEvent(ev7, 1000);
		
		EventWave wave3 = new EventWave(te7);
		
		//WAVE 4
		
		Enemy e8 = enemyFac.getEnemy(EnemyType.FLYBY_LEFT_1,   -150,   -50);
		Enemy e9 = enemyFac.getEnemy(EnemyType.FLYBY_RIGHT_1,  wX+150, -50);
		Enemy e10 = enemyFac.getEnemy(EnemyType.FLYBY_LEFT_1,   -150,  -75);
		Enemy e11 = enemyFac.getEnemy(EnemyType.FLYBY_RIGHT_1,  wX+150,-75);
		Enemy e12 = enemyFac.getEnemy(EnemyType.FLYBY_LEFT_1,   -150,  -100);
		Enemy e13 = enemyFac.getEnemy(EnemyType.FLYBY_RIGHT_1,  wX+150,-100);
		Enemy e14 = enemyFac.getEnemy(EnemyType.FLYBY_LEFT_1,   -150,  -50);
		Enemy e15 = enemyFac.getEnemy(EnemyType.FLYBY_RIGHT_1,  wX+150,-50);
		
		GameEvent<Enemy> ev8  = new GameEvent<>(EventType.SPAWN_ENEMY, e8);
		GameEvent<Enemy> ev9  = new GameEvent<>(EventType.SPAWN_ENEMY, e9);
		GameEvent<Enemy> ev10 = new GameEvent<>(EventType.SPAWN_ENEMY, e10);
		GameEvent<Enemy> ev11 = new GameEvent<>(EventType.SPAWN_ENEMY, e11);
		GameEvent<Enemy> ev12 = new GameEvent<>(EventType.SPAWN_ENEMY, e12);
		GameEvent<Enemy> ev13 = new GameEvent<>(EventType.SPAWN_ENEMY, e13);
		GameEvent<Enemy> ev14 = new GameEvent<>(EventType.SPAWN_ENEMY, e14);
		GameEvent<Enemy> ev15 = new GameEvent<>(EventType.SPAWN_ENEMY, e15);
		
		TimedEvent te8  = new TimedEvent(ev8,  1000);
		TimedEvent te9  = new TimedEvent(ev9,  2000);
		TimedEvent te10 = new TimedEvent(ev10, 3000);
		TimedEvent te11 = new TimedEvent(ev11, 4000);
		TimedEvent te12 = new TimedEvent(ev12, 5000);
		TimedEvent te13 = new TimedEvent(ev13, 6000);
		TimedEvent te14 = new TimedEvent(ev14, 7000);
		TimedEvent te15 = new TimedEvent(ev15, 8000);
		
		EventWave wave4 = new EventWave(te8, te9, te10, te11, te12, te13, te14, te15);
		
		//WAVE 5
		
		Enemy e16 = enemyFac.getEnemy(EnemyType.MACHINEGUN_STATIONARY_1, 200, 150);
		Enemy e17 = enemyFac.getEnemy(EnemyType.MACHINEGUN_STATIONARY_1, 600, 150);
		
		GameEvent<Enemy> ev16 = new GameEvent<>(EventType.SPAWN_ENEMY_ANIMATE, e16);
		GameEvent<Enemy> ev17 = new GameEvent<>(EventType.SPAWN_ENEMY_ANIMATE, e17);
		
		TimedEvent te16 = new TimedEvent(ev16, 1000);
		TimedEvent te17 = new TimedEvent(ev17, 1000);
		
		EventWave wave5 = new EventWave(te16, te17);
		
		//WAVE 6
		
		Enemy e18 = enemyFac.getEnemy(EnemyType.FLYBY_DOWN_1, 400, -100);
		Enemy e19 = enemyFac.getEnemy(EnemyType.FLYBY_DOWN_1, 200, -100);
		Enemy e20 = enemyFac.getEnemy(EnemyType.FLYBY_DOWN_1, 600, -100);
		Enemy e21 = enemyFac.getEnemy(EnemyType.FLYBY_LEFT_1,   -150,   -25);
		Enemy e22 = enemyFac.getEnemy(EnemyType.FLYBY_RIGHT_1,  wX+150, -25);
		Enemy e23 = enemyFac.getEnemy(EnemyType.SHOTGUN_WAVE_1, 400, 150);
		
		GameEvent<Enemy> ev18 = new GameEvent<>(EventType.SPAWN_ENEMY, e18);
		GameEvent<Enemy> ev19 = new GameEvent<>(EventType.SPAWN_ENEMY, e19);
		GameEvent<Enemy> ev20 = new GameEvent<>(EventType.SPAWN_ENEMY, e20);
		GameEvent<Enemy> ev21 = new GameEvent<>(EventType.SPAWN_ENEMY, e21);
		GameEvent<Enemy> ev22 = new GameEvent<>(EventType.SPAWN_ENEMY, e22);
		GameEvent<Enemy> ev23 = new GameEvent<>(EventType.SPAWN_ENEMY_ANIMATE, e23);
		
		
		TimedEvent te18 = new TimedEvent(ev18, 800);
		TimedEvent te19 = new TimedEvent(ev19, 3000);
		TimedEvent te20 = new TimedEvent(ev20, 3000);
		TimedEvent te21 = new TimedEvent(ev21, 6500);
		TimedEvent te22 = new TimedEvent(ev22, 6500);
		TimedEvent te23 = new TimedEvent(ev23, 5500);
				
		EventWave wave6 = new EventWave(te18, te19, te20, te21, te22, te23);
		
		//Wave 7
		
		Enemy w7e1 = enemyFac.getEnemy(EnemyType.MACHINEGUN_STATIONARY_1, 400, 150);
		Enemy w7e2 = enemyFac.getEnemy(EnemyType.SHOTGUN_WAVE_1, 200, 150);
		Enemy w7e3 = enemyFac.getEnemy(EnemyType.SHOTGUN_WAVE_1, 600, 150);
		
		GameEvent<Enemy> w7ev1 = new GameEvent<>(EventType.SPAWN_ENEMY_ANIMATE, w7e1);
		GameEvent<Enemy> w7ev2 = new GameEvent<>(EventType.SPAWN_ENEMY_ANIMATE, w7e2);
		GameEvent<Enemy> w7ev3 = new GameEvent<>(EventType.SPAWN_ENEMY_ANIMATE, w7e3);
		
		TimedEvent w7te1 = new TimedEvent(w7ev1, 1000);
		TimedEvent w7te2 = new TimedEvent(w7ev2, 3000);
		TimedEvent w7te3 = new TimedEvent(w7ev3, 3000);
		
		EventWave wave7 = new EventWave(w7te1, w7te2, w7te3);
		
		//Wave 8
		
		Enemy w8e1 = enemyFac.getEnemy(EnemyType.FLYBY_DOWN_1,    400, -100);
		Enemy w8e2 = enemyFac.getEnemy(EnemyType.FLYBY_LEFT_1,   -50,   -25);
		Enemy w8e3 = enemyFac.getEnemy(EnemyType.FLYBY_LEFT_1,   -150,   -25);
		Enemy w8e4 = enemyFac.getEnemy(EnemyType.FLYBY_RIGHT_1,  wX+50,  -25);
		Enemy w8e5 = enemyFac.getEnemy(EnemyType.FLYBY_RIGHT_1,  wX+150, -25);
		Enemy w8e6 = enemyFac.getEnemy(EnemyType.SHOTGUN_WAVE_1, 350, 120);
		Enemy w8e7 = enemyFac.getEnemy(EnemyType.SHOTGUN_WAVE_1, 175, 200);
		Enemy w8e8 = enemyFac.getEnemy(EnemyType.SHOTGUN_WAVE_1, 575, 200);
		
		GameEvent<Enemy> w8ev1 = new GameEvent<>(EventType.SPAWN_ENEMY, w8e1);
		GameEvent<Enemy> w8ev2 = new GameEvent<>(EventType.SPAWN_ENEMY, w8e2);
		GameEvent<Enemy> w8ev3 = new GameEvent<>(EventType.SPAWN_ENEMY, w8e3);
		GameEvent<Enemy> w8ev4 = new GameEvent<>(EventType.SPAWN_ENEMY, w8e4);
		GameEvent<Enemy> w8ev5 = new GameEvent<>(EventType.SPAWN_ENEMY, w8e5);
		GameEvent<Enemy> w8ev6 = new GameEvent<>(EventType.SPAWN_ENEMY_ANIMATE, w8e6);
		GameEvent<Enemy> w8ev7 = new GameEvent<>(EventType.SPAWN_ENEMY_ANIMATE, w8e7);
		GameEvent<Enemy> w8ev8 = new GameEvent<>(EventType.SPAWN_ENEMY_ANIMATE, w8e8);
		
		TimedEvent w8te1 = new TimedEvent(w8ev1, 1000);
		TimedEvent w8te2 = new TimedEvent(w8ev2, 2000);
		TimedEvent w8te3 = new TimedEvent(w8ev3, 2500);
		TimedEvent w8te4 = new TimedEvent(w8ev4, 3500);
		TimedEvent w8te5 = new TimedEvent(w8ev5, 4000);
		TimedEvent w8te6 = new TimedEvent(w8ev6, 10000);
		TimedEvent w8te7 = new TimedEvent(w8ev7, 6750);
		TimedEvent w8te8 = new TimedEvent(w8ev8, 6750);
		
		EventWave wave8 = new EventWave(w8te1, w8te2, w8te3, w8te4, w8te5, w8te6, w8te7, w8te8);
		
		//TestWave1
		PhotoSprite photo = new PhotoSprite(ImageType.PILOT_1);
		
		String dialog1 = "Out of an abundance of caution, classes tommorrow will be canceled due to an alien invasion."
				+ " However retro asian pizza night is still scheuduled for tommorow at 6 in SAC ballroom A."
				+ "...Now, PREPARE TO DIE!!!";
		
		DialogPopup popup = new LowerThirdPopup(dialog1, photo, 30);
		
		GameEvent<DialogPopup> evx1 = new GameEvent<>(EventType.PLACE_POPUP, popup);
		
		TimedEvent tex1 = new TimedEvent(evx1, 2000);
		
		EventWave testWave = new EventWave(tex1);
		
		//TestWave2
		PhotoSprite photo2 = new PhotoSprite(ImageType.PILOT_1);
		
		String dialog2 = "You must really want that pizza...";
		
		DialogPopup popup2 = new LowerThirdPopup(dialog2, photo2, 30);
		
		GameEvent<DialogPopup> evx2 = new GameEvent<>(EventType.PLACE_POPUP, popup2);
		
		TimedEvent tex2 = new TimedEvent(evx2, 1500);
		
		EventWave testWave2 = new EventWave(tex2);
		
		//TestWave3
		PhotoSprite photo3 = new PhotoSprite(ImageType.PILOT_1);
		
		String dialog3 = "Its not even that good, it's like Papa Johns or something.";
		
		DialogPopup popup3 = new LowerThirdPopup(dialog3, photo3, 30);
		
		GameEvent<DialogPopup> evx3 = new GameEvent<>(EventType.PLACE_POPUP, popup3);
		
		TimedEvent tex3 = new TimedEvent(evx3, 1500);
		
		EventWave testWave3 = new EventWave(tex3);
		
		//TestWave4
		PhotoSprite photo4 = new PhotoSprite(ImageType.PILOT_1);
		
		String dialog4 = "Fine you can have your crappy pizza if it means that much to you.";
		
		DialogPopup popup4 = new LowerThirdPopup(dialog4, photo4, 30);
		
		GameEvent<DialogPopup> evx4 = new GameEvent<>(EventType.PLACE_POPUP, popup4);
		
		TimedEvent tex4 = new TimedEvent(evx4, 1500);
		
		EventWave testWave4 = new EventWave(tex4);
		
		
		levelWaves.add(testWave);
		levelWaves.add(wave1);
		levelWaves.add(wave2);
		levelWaves.add(wave3);
		levelWaves.add(testWave2);
		levelWaves.add(wave4);
		levelWaves.add(wave5);
		levelWaves.add(wave6);
		levelWaves.add(testWave3);
		levelWaves.add(wave7);
		levelWaves.add(wave8);
		levelWaves.add(testWave4);
	}
	
}
