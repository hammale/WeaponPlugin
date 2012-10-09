package me.hammale.grenade;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class Turret {
	
	LivingEntity le;
	Location loc;
	grenade plugin;
	int id, id1;
	Random ran = new Random();
	
	public Turret(grenade plugin, LivingEntity le){
		this.le = le;
		this.plugin = plugin;
		this.loc = le.getLocation();
		start();
	}
	
	private void start() {
		id = plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			   public void run() {
				   stop();		   
			   }
		}, 1200L);
		logic();
	}
	
	private void logic() {		
		id1 = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			   public void run() {
				   le.getLocation().setX(loc.getX());
				   le.getLocation().setY(loc.getY());
				   le.getLocation().setZ(loc.getZ());
//				   if(plugin.getTargets(loc).length > 0){
//					   LivingEntity t = plugin.getTargets(loc)[ran.nextInt(plugin.getTargets(loc).length)];
//					   Vector me = le.getVelocity();
//					   Vector you = t.getVelocity();
//					   float angle = me.angle(you);
//					   le.getLocation().setYaw(angle);
//					   Arrow a = le.launchProjectile(Arrow.class);
//					   a.setVelocity(le.getLocation().getDirection());				   
//				   }
				   le.getTargetBlock(null, 30);
			   }
		}, 5L, 5L);
	}

	private void stop(){
		plugin.getServer().getScheduler().cancelTask(id);
		plugin.getServer().getScheduler().cancelTask(id1);
		le.remove();
	}
	
}
