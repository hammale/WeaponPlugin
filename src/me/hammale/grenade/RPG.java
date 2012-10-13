package me.hammale.grenade;

import org.bukkit.Location;
import org.bukkit.entity.FallingBlock;

public class RPG {

	FallingBlock fb;
	grenade plugin;
	int id;
	Location loc;
	
	public RPG(grenade plugin, FallingBlock fb){
		this.plugin = plugin;
		this.fb = fb;
		this.fb.setDropItem(false);
		this.loc = fb.getLocation();
		start();
	}

	private void start() {
		id = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			   public void run() {
			       if(fb.getLocation().getBlockX() == loc.getBlockX()
			    		   && fb.getLocation().getBlockY() == loc.getBlockY()
			    		   && fb.getLocation().getBlockZ() == loc.getBlockZ()){
			    	   stop();
			       }
			       loc = fb.getLocation();
			   }
		}, 2L, 2L);
	}
	
	private void stop(){
		plugin.getServer().getScheduler().cancelTask(id);
  	   	fb.remove();
  	   	fb.getLocation().getBlock().setTypeId(0);
  	   	fb.setDropItem(false);
  	   	plugin.fakeExplosion(loc, plugin.getRadius(34));
	}
	
}
