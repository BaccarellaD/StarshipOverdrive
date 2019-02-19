package sprites;

import java.util.ArrayList;
import java.util.List;

import application.Game;
import events.GameEvent;
import events.GameEvent.EventType;
import javafx.scene.image.Image;
import sprites.Projectile.ProjectileType;

public class Projectile extends Sprite{
	
	public enum ProjectileType{
		PLAYER_PROJECTILE,
		ENEMY_PROJECTILE,
		DECOR_PROJECTILE
	}
	
	public ProjectileType pType;
	private double damage;
	long last_millis;
	
	public Projectile(ProjectileType type, double xVel, double yVel, double damage, Image sprite){
		this.pType = type;
		this.xVel = xVel;
		this.yVel = yVel;
		this.damage = damage;
		this.setImage(sprite);
		
		this.last_millis = -1;
	}

	@Override
	public List<GameEvent<?>> tick(long cur_millis) {
		GameEvent<Projectile> event = this.move(cur_millis);
		this.last_millis = cur_millis;
		
		List<GameEvent<?>> events = new ArrayList<>();
		
		if(event != null){
			events.add(event);
		}
		
		return events;
	}
	
	public GameEvent<Projectile> move(long cur_millis){
		
		if(last_millis == -1){ //to avoid race condition
			last_millis = cur_millis;
			return null;
		}
		
		long dt = cur_millis - last_millis;
		
		this.setX(this.getX() + dt * this.xVel);
		this.setY(this.getY() + dt * this.yVel);
		
		if(!Game.getGame().getLayoutBounds().intersects(this.getBoundsInParent())){
			
			EventType eType = null;
			
			if(pType == ProjectileType.PLAYER_PROJECTILE){
				eType = EventType.REMOVE_PLAYER_PROJECTILE;
			}
			else if(pType == ProjectileType.ENEMY_PROJECTILE){
				eType = EventType.REMOVE_ENEMY_PROJECTILE;
			}
			else if(pType == ProjectileType.DECOR_PROJECTILE){
				eType = EventType.REMOVE_PROJECTILE;
			}
			
			return new GameEvent<Projectile>(eType, this);
		}
		else{
			return null;
		}
	}
	
	public double getDamage(){
		return damage;
	}

	public void resetWithValues(ProjectileType type, double xVel, double yVel, double damage, Image sprite) {
		this.pType = type;
		this.xVel = xVel;
		this.yVel = yVel;
		this.damage = damage;
		this.setImage(sprite);
		
		this.last_millis = -1;
	};
	
}
