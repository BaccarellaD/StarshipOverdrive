package events;

import sprites.Enemy;
import sprites.MeteorEnemy;
import sprites.Projectile;
import sprites.Sprite;

public class GameEvent<T> {

	public enum EventType{
		SPAWN_PROJECTILE,
		SPAWN_ENEMY,
		REMOVE_ENEMY_PROJECTILE,
		REMOVE_PLAYER_PROJECTILE,
		REMOVE_PROJECTILE,
		SPAWN_ENEMY_PROJECTILE,
		SPAWN_PLAYER_PROJECTILE,
		REMOVE_ENEMY,
		KILL_PLAYER,
		KILL_ENEMY,
		REMOVE_SPRITE,
		DAMAGE_ENEMY,
		DAMAGE_PLAYER, 
		SPAWN_ENEMY_ANIMATE,
		SPAWN_TARGETED,
		
		ADD_POINTS,
		
		PLAY_SOUND,
		
		SET_WAVE_BLOCK, 
		PLACE_POPUP,
		REMOVE_POPUP,
		
		UI_SET_SHIELD_UP,
		UI_SET_SHIELD_PERCENT;
	}
	
	public static class GEBuilder{
		
		public static GameEvent<Projectile> spawnProjectile(Projectile p){
			return new GameEvent<>(EventType.SPAWN_PROJECTILE, p);
		}

		public static GameEvent<Enemy> spawnEnemy(MeteorEnemy enemy) {
			return new GameEvent<>(EventType.SPAWN_ENEMY, enemy);
		}
		
		public static GameEvent<TargetedSpawnEvent> targetedSpawnEvent(Sprite absSprite, Sprite target, GameEvent<?> spawnEvent){
			TargetedSpawnEvent te =  new TargetedSpawnEvent(absSprite, target, spawnEvent);
			return new GameEvent<TargetedSpawnEvent>(EventType.SPAWN_TARGETED, te);
		}
		
		public static GameEvent<Enemy> killEnemy(Enemy enemy){
			return new GameEvent<Enemy>(EventType.KILL_ENEMY, enemy);
		}

		public static GameEvent<?> removeEnemy(Enemy enemy) {
			return new GameEvent<Enemy>(EventType.REMOVE_ENEMY, enemy);
		}
		
		
	}
	
	public EventType eventType;
	private T eventData;
	
	public GameEvent(EventType type, T eventData){
		this.eventType = type;
		this.eventData = eventData;
	}
	
	public T getData(){
		return eventData;
	}
	
	public EventType getEventType(){
		return eventType;
	}
	
}
