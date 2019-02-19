package sprites;

import java.util.ArrayDeque;
import java.util.HashSet;

import controllers.ImageFactory;
import controllers.ImageFactory.ImageType;
import javafx.scene.image.Image;
import sprites.Projectile.ProjectileType;

public class ProjectilePool {
	
	private static ProjectilePool pool;
	
	private ArrayDeque<Projectile> projectilePool;
	
	//private HashSet<Projectile> poolHash;
	
	private ProjectilePool(){
		projectilePool = new ArrayDeque<>();
		//poolHash = new HashSet<>();
	}
	
	public static ProjectilePool getPool(){
		if(pool == null){
			pool = new ProjectilePool();
		}
		return pool;
	}
	
	public Projectile getProjectile(ProjectileType type, double xVel, double yVel, double damage, Image sprite){
		
		Projectile proj = null;
		
		if(projectilePool.isEmpty()){
			proj = new Projectile(type, xVel, yVel, damage, sprite);
		}else{
			proj = projectilePool.pop(); //pop
			//poolHash.remove(proj);
			proj.resetWithValues(type, xVel, yVel, damage, sprite);
		}
		
		return proj;
		
	}
	
	public void returnProjectile(Projectile p){		
		
		if(projectilePool.contains(p)) {
			System.err.println("Duplicate child blocked!");
			return;
		}
		projectilePool.add(p);
		//poolHash.add(p);
	}

}
