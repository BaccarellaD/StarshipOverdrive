package backgrounds;

import java.util.ArrayList;
import java.util.List;

import application.Game;
import controllers.AnimationController;
import controllers.ImageFactory.ImageType;
import events.GameEvent;
import events.GameEvent.EventType;
import sprites.Projectile;
import sprites.ProjectilePool;
import sprites.special.ParticleEmitter;

public class MainMenuBackground extends GameBackground{
	
	private AnimationController ac;
	
	private ParticleEmitter  starEmitter;
	private List<Projectile> stars;

	public MainMenuBackground(){
		stars =  new ArrayList<>();
		
		ac = new AnimationController();
		
		initEmiter();
		
		initStyle();
	}
	
	private void initStyle() {
		//this.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		
		/*this.setBackground(new Background(new BackgroundImage(ImageFactory.getImageFactory().getImage(ImageType.LENS_FLARE), 
				BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, 
				BackgroundPosition.CENTER,
				BackgroundSize.DEFAULT)));*/
		this.setMinHeight(Game.WINDOW_HEIGHT);
		this.setMinWidth(Game.WINDOW_WIDTH);
	}

	private void initEmiter(){

		double x		= Game.WINDOW_WIDTH/2d;
		double y		= Game.WINDOW_HEIGHT/2d;
		double radius	= 360;
		double offset	= 0;
		double particlesPerSec = 30;
		double velocity	= .25;
		ImageType type	= ImageType.STAR_1;
		starEmitter = new ParticleEmitter(x, y, radius, offset, particlesPerSec, velocity, type);
	}

	@Override
	public List<GameEvent<?>> tick(long cur_millis) {
		List<GameEvent<?>> events = new ArrayList<>();
		
		tickStars(cur_millis);
		
		return events;
	}

	private void tickStars(long cur_millis) {
		
		List<GameEvent<?>> events = new ArrayList<>();
		
		List<GameEvent<?>> emitterEvents = starEmitter.tick(cur_millis);
		List<GameEvent<?>> starEvents = new ArrayList<>();
		for(Projectile star: stars){
			List<GameEvent<?>> se = star.tick(cur_millis);
			if(!se.isEmpty()){
				starEvents.addAll(se);
			}
		}
		
		events.addAll(emitterEvents);
		events.addAll(starEvents);
		
		handleEvents(events);
	}

	private void handleEvents(List<GameEvent<?>> events) {
		for(GameEvent<?> event: events){
			if(event.eventType.equals(EventType.SPAWN_PROJECTILE)){
				addStar((Projectile)event.getData());
			}
			else if(event.eventType.equals(EventType.REMOVE_PROJECTILE)){
				removeStar((Projectile)event.getData());
			}
		}
	}

	private void removeStar(Projectile star) {
		ac.removeEffect(star);
		this.stars.remove(star);
		this.getChildren().remove(star);
		ProjectilePool.getPool().returnProjectile(star);
	}

	private void addStar(Projectile star) {
		//ac.setMotionBlur(star, 10);
		star.setOpacity(0);
		ac.setFadeIn(star, 750);
		this.stars.add(star);
		this.getChildren().add(star);
	}
	
}
