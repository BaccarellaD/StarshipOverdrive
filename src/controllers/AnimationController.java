package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.scene.CacheHint;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.effect.Effect;
import javafx.scene.effect.MotionBlur;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import sprites.Enemy;
import sprites.Player;
import sprites.Sprite;

public class AnimationController {
	
	private static List<Timeline> timeLines = new ArrayList<>();
	
	public void setDamageAnimation(Sprite dmgSprite){       
		
		Blend dmgBlend = getNewCI(dmgSprite, Color.RED);
		
		Timeline timeline = new Timeline();
		timeline.setCycleCount(1);
		timeline.setAutoReverse(false);
		KeyValue kv1 = new KeyValue(dmgSprite.effectProperty(), dmgBlend);
		KeyFrame kf1 = new KeyFrame(Duration.millis(0), kv1);
		KeyValue kv2 = new KeyValue(dmgSprite.effectProperty(), null);
		KeyFrame kf2 = new KeyFrame(Duration.millis(50), kv2);
		KeyValue kv3 = new KeyValue(dmgSprite.effectProperty(), dmgBlend);
		KeyFrame kf3 = new KeyFrame(Duration.millis(100), kv3);
		KeyValue kv4 = new KeyValue(dmgSprite.effectProperty(), null);
		KeyFrame kf4 = new KeyFrame(Duration.millis(150), kv4);
		timeline.getKeyFrames().addAll(kf1, kf2, kf3, kf4);
		
		timeline.play();
		addTimeline(timeline);
	}
	/**
	 * Helper method for setDamageAnimation which makes a new color blend each time
	 * @param dmgSprite
	 * @return a new color blend
	 */
	private Blend getNewCI(Sprite dmgSprite, Color color){
		
        ColorAdjust monochrome = new ColorAdjust();
        monochrome.setSaturation(-.5);
        
        ColorInput ci = new ColorInput(
                dmgSprite.getX(),
                dmgSprite.getY(),
                dmgSprite.getFitWidth(),
                dmgSprite.getFitHeight(),
                color
        );
        
        ci.xProperty().bind(dmgSprite.xProperty());
        ci.yProperty().bind(dmgSprite.yProperty());

        Blend dmgBlend = new Blend(
                BlendMode.SRC_ATOP,
                monochrome,
                ci
        );
        
        return dmgBlend;
	}
	public void setEnemySpawnAnimation(Enemy e) {
		
		double curY = e.getY();
		
		//Put above screen
		e.setY(-e.getFitHeight());
		
		Timeline timeline = new Timeline();
		timeline.setCycleCount(1);
		timeline.setAutoReverse(false);
		KeyValue kv1 = new KeyValue(e.yProperty(), curY);
		KeyFrame kf1 = new KeyFrame(Duration.millis(500), kv1);
		
		timeline.getKeyFrames().addAll(kf1);
		timeline.play();
		addTimeline(timeline);
		
	}
	
	public void setMotionBlur(Sprite sprite, double intensity){
		MotionBlur motionBlur = new MotionBlur();
		
		double x = sprite.getXVel();
		double y = sprite.getYVel();
		
		if(x == 0 && y == 0){ //if there's no movement return
			return;
		}
		
		double velVector = Math.sqrt((x*x)+(y*y));
		double angle = 0;
		
		if(y == 0 && x != 0){
			angle = 0;
		}
		else if(x == 0 && y != 0){
			angle = 90;
		}
		else{
			angle = Math.toDegrees(Math.atan(x/y));
		}
		
		
		
		motionBlur.setAngle(angle);
		motionBlur.setRadius(velVector * intensity);
		
		sprite.setEffect(motionBlur);
	}
	
	public void setFadeIn(Sprite sprite, long duration){
		Timeline timeline = new Timeline();
		timeline.setCycleCount(1);
		timeline.setAutoReverse(false);
		KeyValue kv1 = new KeyValue(sprite.opacityProperty(), 0);
		KeyFrame kf1 = new KeyFrame(Duration.millis(0), kv1);
		KeyValue kv2 = new KeyValue(sprite.opacityProperty(), 0);
		KeyFrame kf2 = new KeyFrame(Duration.millis(duration/4), kv2);
		KeyValue kv3 = new KeyValue(sprite.opacityProperty(), 1);
		KeyFrame kf3 = new KeyFrame(Duration.millis(duration), kv3);
		timeline.getKeyFrames().addAll(kf1, kf2, kf3);
		
		timeline.play();
		addTimeline(timeline);
	}
	
	public void removeEffect(Sprite sprite){
		sprite.setEffect(null);
	}
	
	public void setShieldedDamageAnimation(Player dmgSprite) {
		
		Blend dmgBlend = getNewCI(dmgSprite, Color.ALICEBLUE);
		
		Timeline timeline = new Timeline();
		timeline.setCycleCount(1);
		timeline.setAutoReverse(false);
		KeyValue kv1 = new KeyValue(dmgSprite.effectProperty(), dmgBlend);
		KeyFrame kf1 = new KeyFrame(Duration.millis(0), kv1);
		KeyValue kv2 = new KeyValue(dmgSprite.effectProperty(), null);
		KeyFrame kf2 = new KeyFrame(Duration.millis(50), kv2);
		KeyValue kv3 = new KeyValue(dmgSprite.effectProperty(), dmgBlend);
		KeyFrame kf3 = new KeyFrame(Duration.millis(100), kv3);
		KeyValue kv4 = new KeyValue(dmgSprite.effectProperty(), null);
		KeyFrame kf4 = new KeyFrame(Duration.millis(150), kv4);
		timeline.getKeyFrames().addAll(kf1, kf2, kf3, kf4);
		
		timeline.play();
		addTimeline(timeline);
	}
	
	private static void addTimeline(Timeline tl) {
		
		timeLines.add(tl);
		
		tl.setOnFinished(e ->{   //set up the timeline to remove itself
			timeLines.remove(tl);
		});
		
	}
	
	public static void pauseTimelines() {
		for(Timeline tl: timeLines) {
			tl.pause();
		}
	}
	
	
	public static void resumeTimelines() {
		for(Timeline tl: timeLines) {
			tl.play();
		}
	}
	
	public static void pauseAndRemoveTimeline(Timeline tl) {
		tl.pause();
		timeLines.remove(tl);
	}
	
}
