package controllers;

import java.io.File;

import javafx.scene.media.AudioClip;

public class SoundFXController {
	
	public enum SoundType{
		LASER_1,
		LASER_TINNY,
		LASER_BASIC,
		LASER_HEAVY,
		LASER_HUGE,
		LASER_LIGHT,
		LASER_SNIPER,
		EXPLOSION_2,
		DASH_1,
		SHEILD_UP,
		SHIELD_DOWN
	}
	
	AudioClip laser1;
	AudioClip laserTinny;
	AudioClip laserBasic;
	AudioClip laserHeavy;
	AudioClip laserHuge;
	AudioClip laserLight;
	AudioClip laserSniper;
	AudioClip explosion2;
	AudioClip dash1;
	AudioClip shieldUp;
	AudioClip shieldDown;
	
	private double volume;

	public SoundFXController(){

		laser1 		= new AudioClip(getFilePath("soundfx/Laser1.wav"));
		laserTinny 	= new AudioClip(getFilePath("soundfx/laserTinny.mp3"));
		laserBasic	= new AudioClip(getFilePath("soundfx/laserBasic.wav"));
		laserHeavy	= new AudioClip(getFilePath("soundfx/laserHeavy.wav"));
		laserHuge	= new AudioClip(getFilePath("soundfx/laserHuge.wav"));
		laserLight	= new AudioClip(getFilePath("soundfx/laserLight.wav"));
		laserSniper	= new AudioClip(getFilePath("soundfx/laserSniper.wav"));
		explosion2 	= new AudioClip(getFilePath("soundfx/Explosion2.wav"));
		dash1		= new AudioClip(getFilePath("soundfx/Dash1.wav"));
		shieldUp	= new AudioClip(getFilePath("soundfx/shieldUp.wav"));
		shieldDown	= new AudioClip(getFilePath("soundfx/shieldDown.wav"));

		
		/*
		laser1 		= new AudioClip((new File("resources/soundfx/Laser1.wav").toURI().toString()));
		laserTinny 	= new AudioClip((new File("resources/soundfx/laserTinny.mp3").toURI().toString()));
		laserBasic	= new AudioClip((new File("resources/soundfx/laserBasic.wav").toURI().toString()));
		laserHeavy	= new AudioClip((new File("resources/soundfx/laserHeavy.wav").toURI().toString()));
		laserHuge	= new AudioClip((new File("resources/soundfx/laserHuge.wav").toURI().toString()));
		laserLight	= new AudioClip((new File("resources/soundfx/laserLight.wav").toURI().toString()));
		laserSniper	= new AudioClip((new File("resources/soundfx/laserSniper.wav").toURI().toString()));
		explosion2 	= new AudioClip((new File("resources/soundfx/Explosion2.wav").toURI().toString()));
		dash1		= new AudioClip((new File("resources/soundfx/Dash1.wav").toURI().toString()));
		shieldUp	= new AudioClip((new File("resources/soundfx/shieldUp.wav").toURI().toString()));
		shieldDown	= new AudioClip((new File("resources/soundfx/shieldDown.wav").toURI().toString()));
		*/
		
		setVolume(.20);
	}
	
	public void playSound(SoundType type){
		if(type == SoundType.LASER_1){
			laser1.play();
		}
		else if(type == SoundType.LASER_BASIC){
			laserBasic.play();
		}
		else if(type == SoundType.LASER_HEAVY){
			laserHeavy.play();
		}
		else if(type == SoundType.LASER_HUGE){
			laserHuge.play();
		}
		else if(type == SoundType.LASER_LIGHT){
			laserLight.play();
		}
		else if(type == SoundType.LASER_SNIPER){
			laserSniper.play();
		}
		else if(type == SoundType.LASER_TINNY){
			laserTinny.play();
		}
		else if(type == SoundType.EXPLOSION_2){
			explosion2.play();
		}
		else if(type == SoundType.DASH_1){
			dash1.play();
		}
		else if(type == SoundType.SHEILD_UP) {
			shieldUp.play();
		}
		else if(type == SoundType.SHIELD_DOWN) {
			shieldDown.play();
		}
	}
	
	public void setVolume(double volume){
		this.volume = volume;
		
		//Change the volumes
		laser1		.setVolume(volume * 0.75);
		laserTinny	.setVolume(volume);
		laserBasic	.setVolume(volume);
		laserHeavy	.setVolume(volume);
		laserHuge	.setVolume(volume);
		laserLight	.setVolume(volume);
		laserSniper	.setVolume(volume);
		explosion2	.setVolume(volume);
		dash1		.setVolume(volume);
		shieldUp	.setVolume(volume);
		shieldDown	.setVolume(volume);
	}
	
	private String getFilePath(String relPath) {
		
		Class<?> c = this.getClass();
		ClassLoader cLoader = c.getClassLoader();
		
		return cLoader.getResource(relPath).toExternalForm();
		
	}
	
}
