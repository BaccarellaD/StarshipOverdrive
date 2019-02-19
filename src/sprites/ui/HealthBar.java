package sprites.ui;

import controllers.ImageFactory;
import controllers.ImageFactory.ImageType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HealthBar extends Pane{

	private ImageView healthBarBase;
	private ImageView healthBarColor;
	private ImageView healthBarSphere;
	private ImageView shieldBar;
	
	private Rectangle healthClip;
	private Rectangle shieldClip;
	
	private double 	health;
	private boolean shieldUp;
	
	private final double COLOR_X_OFFSET = 14;
	private final double COLOR_WIDTH	= 71;
	
	private final double SHIELD_X_OFFSET = 13;
	private final double SHIELD_WIDTH = 53;
	
	public HealthBar(double health, boolean shieldUp){
		
		this.health = health;
		this.shieldUp = shieldUp;
		
		ImageFactory imageFac = ImageFactory.getImageFactory();
		
		Image baseImage 		= imageFac.getImage(ImageType.HEALTHBAR_EMPTY);
		Image colorImage		= imageFac.getImage(ImageType.HEALTHBAR_HEALTH);
		Image sphereImage		= imageFac.getImage(ImageType.HEALTHBAR_SPHERE);
		Image shieldBarImage	= imageFac.getImage(ImageType.HEALTHBAR_SHIELD_BAR);
		
		healthBarBase 	= new ImageView(baseImage);
		healthBarColor 	= new ImageView(colorImage);
		healthBarSphere	= new ImageView(sphereImage);
		shieldBar		= new ImageView(shieldBarImage);
		
		healthClip = new Rectangle();
		//healthClip.setFill(Color.RED);
		healthClip.setHeight(ImageType.HEALTHBAR_HEALTH.getHeight());
		healthClip.setX(COLOR_X_OFFSET);
		
		shieldClip = new Rectangle();
		shieldClip.setHeight(ImageType.HEALTHBAR_SHIELD_BAR.getHeight());
		shieldClip.setX(SHIELD_X_OFFSET);
		
		updateClip();
		
		this.getChildren().add(healthBarBase);
		this.getChildren().add(healthBarColor);
		this.getChildren().add(shieldBar);
		
		healthBarColor.setClip(healthClip);
		shieldBar.setClip(shieldClip);
		
		setShieldPercent(1d);
		
		if(shieldUp){
			putShieldUp();
		}
	}
	
	private void updateClip(){
		double newWidth = COLOR_WIDTH * (health/100d);
		healthClip.setWidth(newWidth);
	}
	
	public void setHealth(double health){
		this.health = health;
		updateClip();
	}
	
	public void setShield(boolean shieldUp){
		if(this.shieldUp != shieldUp){
			this.shieldUp = shieldUp;
			if(shieldUp){
				putShieldUp();
			}else{
				putShieldDown();
			}
		}
	}
	
	private void putShieldUp(){
		this.getChildren().add(healthBarSphere);
	}
	
	private void putShieldDown(){
		this.getChildren().remove(healthBarSphere);
	}

	public void setShieldPercent(double percent) {
		shieldClip.setWidth(SHIELD_WIDTH * percent);
	}
}
