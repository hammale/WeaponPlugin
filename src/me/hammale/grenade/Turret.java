package me.hammale.grenade;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

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
				   Location tmp = loc;
				   tmp.setPitch(le.getLocation().getPitch());
				   tmp.setYaw(le.getLocation().getYaw());
				   le.teleport(tmp);
//				   if(plugin.getTargets(loc).length > 0){
//					   LivingEntity t = plugin.getTargets(loc)[ran.nextInt(plugin.getTargets(loc).length)];
//					   Vector me = le.getVelocity();
//					   Vector you = t.getVelocity();
//					   float angle = me.angle(you);
//					   le.getLocation().setYaw(angle);
//					   Arrow a = le.launchProjectile(Arrow.class);
//					   a.setVelocity(le.getLocation().getDirection());				   
//				   }
				   if(le.getNearbyEntities(10, 1, 10).size() > 0){
					   Entity e = le.getNearbyEntities(10, 10, 10).get(ran.nextInt(le.getNearbyEntities(10, 10, 10).size()));
					   if(e instanceof LivingEntity){
						   if(!(e.getType() == EntityType.IRON_GOLEM)){
							   IronGolem golem = (IronGolem) le;
							   golem.setPlayerCreated(false);
							   golem.setTarget((LivingEntity) e);
							   le.launchProjectile(Arrow.class);
						   }
					   }
				   }
				   if(le.getHealth() < le.getMaxHealth()
						   || le.isDead()){
					   le.setHealth(le.getMaxHealth());
				   }
			   }
		}, 5L, 5L);
	}

	private void stop(){
		loc.getWorld().createExplosion(loc, 0F);
		for(Player p : plugin.getNearbyPlayers(loc, 6)){
			p.damage(8);
		}
		plugin.getServer().getScheduler().cancelTask(id);
		plugin.getServer().getScheduler().cancelTask(id1);
		le.remove();
	}
	
}
