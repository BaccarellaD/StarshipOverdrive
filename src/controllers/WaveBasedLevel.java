package controllers;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import application.Game;
import backgrounds.GameBackground;
import controllers.MusicController.Music;
import events.EventWave;
import events.GameEvent;
import events.TimedEvent;

public abstract class WaveBasedLevel extends LevelController{
	
	
	GameBackground bg;
	
	long initial_time = 0;
	long prev_time = 0;
	
	Queue<EventWave> levelWaves;
	TimedEvent nextEvent;
	
	public WaveBasedLevel(Music mtype){
		levelWaves = new ArrayDeque<>();
		initalizeEvents();
		Game.getMusicPlayer().setMusic(mtype);
	}
	
	@Override
	public List<GameEvent<?>> tick(long cur_time) {
		
		List<GameEvent<?>> gameEvents = new ArrayList<>();

		if(prev_time == 0){
			initial_time = cur_time;
			prev_time = cur_time;
			return gameEvents;
		}
		
		gameEvents.addAll(getEvents(cur_time));
		
		return gameEvents;
	}
	
	private List<GameEvent<?>> getEvents(long cur_time){
		
		List<GameEvent<?>> events = new ArrayList<>();
		
		if(outOfWaves() || isWaveBlocked()){
			return events;
		}
		
		EventWave curWave = levelWaves.peek();
		
		if(!curWave.timeSet()) curWave.setWaveStartTime(cur_time);
		
		events.addAll(curWave.tick(cur_time));
		if(!curWave.hasEvent() && this.stageClear() && events.isEmpty()){
			levelWaves.poll();
			if(!outOfWaves()){
				System.out.println("Wave start time set");
				curWave = levelWaves.peek();
				curWave.setWaveStartTime(cur_time);
			}
		}
		
		return events;
		
	}
	
	public boolean outOfWaves(){
		return levelWaves.isEmpty();
	}

	
	abstract void initalizeEvents();
	
}
