package controllers;

import application.Game;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyPressController {
	
	public enum PlayerActionEvent{
		PAUSE,
		MOVE_UP,
		MOVE_DOWN,
		MOVE_LEFT,
		MOVE_RIGHT,
		SHIELD_UP,
		SHIELD_DOWN,
		SPRINT,
		FIRE,
		STOP_UP,
		STOP_DOWN,
		STOP_LEFT,
		STOP_RIGHT, 
		STOP_VERT,
		STOP_HORZ, 
		STOP_FIRING
	}
	
	private static final KeyCode PAUSE			= KeyCode.ESCAPE;
	private static final KeyCode UP_CODE 		= KeyCode.W;
	private static final KeyCode DOWN_CODE 		= KeyCode.S;
	private static final KeyCode LEFT_CODE 		= KeyCode.A;
	private static final KeyCode RIGHT_CODE 	= KeyCode.D;
	private static final KeyCode SPRINT_CODE 	= KeyCode.SHIFT;
	private static final KeyCode FIRE_CODE		= KeyCode.SPACE;
	private static final KeyCode SHIELD_CODE	= KeyCode.CONTROL;
	
	private boolean upPressed 		= false;
	private boolean downPressed		= false;
	private boolean leftPressed		= false;
	private boolean rightPressed	= false;
	private boolean spacePressed	= false;
	
	
	
	public void processKeyPress(KeyEvent event){
		if(!Game.getGame().isInMenu()){
			processLevelKeyPress(event);
		}
	}
	
	public void processKeyRelease(KeyEvent event){
		if(!Game.getGame().isInMenu()){
			processLevelKeyRelease(event);
		}
	}
	
	
	private void processLevelKeyPress(KeyEvent event){
		Game game = Game.getGame();
		LevelController level = game.getLevel();
		
		KeyCode eventCode = event.getCode();
		
		if(eventCode == UP_CODE && !upPressed){
			upPressed = true;
			level.setPlayerAction(PlayerActionEvent.MOVE_UP);
		}
		else if(eventCode == DOWN_CODE && !downPressed){
			downPressed = true;
			level.setPlayerAction(PlayerActionEvent.MOVE_DOWN);
		}
		else if(eventCode == LEFT_CODE && !leftPressed){
			leftPressed = true;
			level.setPlayerAction(PlayerActionEvent.MOVE_LEFT);
		}
		else if(eventCode == RIGHT_CODE && !rightPressed){
			rightPressed = true;
			level.setPlayerAction(PlayerActionEvent.MOVE_RIGHT);
		}
		else if(eventCode == SPRINT_CODE){
			level.setPlayerAction(PlayerActionEvent.SPRINT);
		}
		else if(eventCode == SHIELD_CODE){
			level.setPlayerAction(PlayerActionEvent.SHIELD_UP);
		}
		else if(eventCode == FIRE_CODE && !spacePressed){
			spacePressed = true;
			level.setPlayerAction(PlayerActionEvent.FIRE);
		}
		else if(eventCode == PAUSE){
			level.handlePause();
		}
	}
	
	private void processLevelKeyRelease(KeyEvent event){
		Game game = Game.getGame();
		LevelController level = game.getLevel();
		
		KeyCode eventCode = event.getCode();
		
		if(eventCode == UP_CODE){
			upPressed = false;
			if(!downPressed)
				level.setPlayerAction(PlayerActionEvent.STOP_VERT);
			else
				level.setPlayerAction(PlayerActionEvent.MOVE_DOWN);
		}
		else if(eventCode == DOWN_CODE){
			downPressed = false;
			if(!upPressed)
				level.setPlayerAction(PlayerActionEvent.STOP_VERT);
			else
				level.setPlayerAction(PlayerActionEvent.MOVE_UP);
		}
		else if(eventCode == LEFT_CODE){
			leftPressed = false;
			if(!rightPressed)
				level.setPlayerAction(PlayerActionEvent.STOP_HORZ);
			else
				level.setPlayerAction(PlayerActionEvent.MOVE_RIGHT);
		}
		else if(eventCode == RIGHT_CODE){
			rightPressed = false;
			if(!leftPressed)
				level.setPlayerAction(PlayerActionEvent.STOP_HORZ);
			else
				level.setPlayerAction(PlayerActionEvent.MOVE_LEFT);
		}
		/*else if(eventCode == SPRINT_CODE){
			level.setPlayerAction(PlayerActionEvent.STOP_SPRINT);
		}*/
		else if(eventCode == SHIELD_CODE){
			level.setPlayerAction(PlayerActionEvent.SHIELD_DOWN);
		}
		else if(eventCode == FIRE_CODE){
			level.setPlayerAction(PlayerActionEvent.STOP_FIRING);
			spacePressed = false;
		}
	}
}
