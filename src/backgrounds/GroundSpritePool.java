package backgrounds;

import java.util.ArrayDeque;


public class GroundSpritePool {

	private static GroundSpritePool pool;
	
	private ArrayDeque<GroundSprite> gsPool;
	
	//private HashSet<Projectile> poolHash;
	
	private GroundSpritePool(){
		gsPool = new ArrayDeque<>();
		//poolHash = new HashSet<>();
	}
	
	public static GroundSpritePool getPool(){
		if(pool == null){
			pool = new GroundSpritePool();
		}
		return pool;
	}
	
	public GroundSprite getGroundSprite(long cur_millis){
		
		GroundSprite sprite = null;
		
		if(gsPool.isEmpty()){
			//System.out.println("New Sprite");
			sprite = new GroundSprite(cur_millis);
		}else{
			//System.out.println("popping");
			sprite = gsPool.pop(); //pop
			sprite.resetWithValues(cur_millis);
		}
		
		return sprite;
		
	}
	
	public void returnGroundSprite(GroundSprite gs){
		gsPool.add(gs);
		//System.out.println("Pool size: " + gsPool.size());
	}
	
}
