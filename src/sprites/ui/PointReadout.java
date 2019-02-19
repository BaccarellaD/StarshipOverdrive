package sprites.ui;

import java.text.DecimalFormat;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class PointReadout extends Label{
	
	DecimalFormat scoreFormat = new DecimalFormat("#00000000");
	long score;
	
	public PointReadout(){
		this.score = 0;
		this.getStyleClass().add("score_board");
		updateScoreReadout();
	}
	
	private void updateScoreReadout(){
		String text = "Score: " + scoreFormat.format(score);
		this.setText(text);
	}
	
	public void setScore(long score){
		this.score = score;
		updateScoreReadout();
	}

	public void addPoints(long points) {
		setScore(score + points);
	}
	
	public long getScore(){
		return score;
	}
	
}
