package controllers;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import application.Game;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicController {

	public enum Music{
		LUNA_ASCENSION,
		ROR
	}
	
	private MediaPlayer player;
	private Music curMusic;
	
	private Media lunaAscension;
	private Media ror;
	
	private double volume;
	
	public MusicController(){
		
		Class<?> c = MusicController.class;
		ClassLoader cLoader = c.getClassLoader();
		String lunaAscensionPath = cLoader.getResource("music/Luna_Ascension.mp3").toExternalForm();
		
		System.out.println(lunaAscensionPath);
		
		String rorPath = cLoader.getResource("music/RoR.mp3").toExternalForm();
		
		lunaAscension 	= new Media(lunaAscensionPath);
		ror			 	= new Media(rorPath);
		
		/*lunaAscension 	= new Media(new File("resources/music/Luna_Ascension.mp3").toURI().toString());
		ror			 	= new Media(new File("resources/music/ror.mp3").toURI().toString());
		*/
	}
	
	public void setMusic(Music music){
		
		if(curMusic == music){ //checks if music is the same
			return;
		}else{
			curMusic = music;
		}
		
		if(music == Music.LUNA_ASCENSION){
			playMusic(lunaAscension);
		}
		else if(music == Music.ROR){
			playMusic(ror);
		}
	}
	
	private void playMusic(Media music){
		if(player != null){
			player.stop();
		}
		
		player = new MediaPlayer(music);
		this.setVolume(.50);
		player.setCycleCount(Integer.MAX_VALUE);
		player.play();
	}

	public void pause() {
		if(player == null){
			return;
		}
		player.pause();
	}
	
	public void play(){
		if(player == null){
			return;
		}
		player.play();
	}
	
	public void setVolume(double volume){
		this.volume = volume;
		if(player != null){
			player.setVolume(this.volume);
		}
	}
	
}
