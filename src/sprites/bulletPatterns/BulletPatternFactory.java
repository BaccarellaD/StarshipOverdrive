package sprites.bulletPatterns;

import sprites.Enemy;

public class BulletPatternFactory {

	double 	arc_Vel;
	double 	arc_Angle;
	int 	arc_ProjectileAmount;
	double 	arc_Damage;
	long 	arc_ShotDuration;
	long 	arc_PauseDuration;
	
	double  straightShot_XVel;
	double  straightShot_YVel;
	double  straightShot_Damage;
	long	straightShot_FireRate;
	
	double 	shotgun_Vel;
	double 	shotgun_Angle;
	int 	shotgun_BulletAmount;
	double 	shotgun_Damage;
	long	shotgun_FireRate;
	
	
	public ArcPattern getNewArcPattern(Enemy e){
		return new ArcPattern(e, arc_Vel, arc_Angle, arc_ProjectileAmount, arc_Damage, arc_ShotDuration, arc_PauseDuration);
	}
	
	public StraightShot getNewStraightShot(Enemy e){
		return new StraightShot(e, straightShot_XVel, straightShot_YVel, straightShot_Damage, straightShot_FireRate);
	}
	
	public ShotgunPattern getNewShotgunPattern(Enemy e){
		return new ShotgunPattern(e, shotgun_Vel, shotgun_Angle, shotgun_BulletAmount, shotgun_Damage, shotgun_FireRate);
	}

	
	public void setArc_Vel(double arc_Vel) {
		this.arc_Vel = arc_Vel;
	}

	public void setArc_Angle(double arc_Angle) {
		this.arc_Angle = arc_Angle;
	}

	public void setArc_ProjectileAmount(int arc_Amt) {
		this.arc_ProjectileAmount = arc_Amt;
	}

	public void setArc_Damage(double arc_Damage) {
		this.arc_Damage = arc_Damage;
	}

	public void setArc_ShotDuration(long arc_ShotDuration) {
		this.arc_ShotDuration = arc_ShotDuration;
	}

	public void setArc_PauseDuration(long arc_PauseDuration) {
		this.arc_PauseDuration = arc_PauseDuration;
	}

	public void setStraightShot_XVel(double straightShot_XVel) {
		this.straightShot_XVel = straightShot_XVel;
	}

	public void setStraightShot_YVel(double straightShot_YVel) {
		this.straightShot_YVel = straightShot_YVel;
	}

	public void setStraightShot_Damage(double straightShot_Damage) {
		this.straightShot_Damage = straightShot_Damage;
	}

	public void setShotgun_Vel(double shotgun_Vel) {
		this.shotgun_Vel = shotgun_Vel;
	}

	public void setShotgun_Angle(double shotgun_Angle) {
		this.shotgun_Angle = shotgun_Angle;
	}

	public void setShotgun_BulletAmount(int shotgun_BulletAmount) {
		this.shotgun_BulletAmount = shotgun_BulletAmount;
	}

	public void setShotgun_Damage(double shotgun_Damage) {
		this.shotgun_Damage = shotgun_Damage;
	}
	
	public void setStraightShot_FireRate(long straightShot_FireRate) {
		this.straightShot_FireRate = straightShot_FireRate;
	}

	public void setShotgun_FireRate(long shotgun_fireRate) {
		this.shotgun_FireRate = shotgun_fireRate;
	}

	
	
	
}
