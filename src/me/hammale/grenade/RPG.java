package me.hammale.grenade;

import org.bukkit.Location;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;

public class RPG {

	FallingBlock fb;
	grenade plugin;
	int id;
	Location loc;
	
	public RPG(grenade plugin, FallingBlock fb){
		this.plugin = plugin;
		this.fb = fb;
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
  	   	fb.getWorld().createExplosion(fb.getLocation(), 0F);
  	   	for(Player p : plugin.getServer().getOnlinePlayers()){
  	   		if(p.getLocation().distance(fb.getLocation()) <= 6){
  	   			p.damage(8);
  	   		}
  	   	}
	}
	
}
