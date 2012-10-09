package me.hammale.grenade;

import java.util.ArrayList;

import org.bukkit.block.Block;

public class glassBox {

	public ArrayList<Block> blocks = new ArrayList<Block>();
	grenade plugin;
	int id;
	
	public glassBox(grenade plugin){
		this.plugin = plugin;
		start();
	}
	
	private void start() {
		id = plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			   public void run() {
				   stop();
			   }
		}, 600L);
	}
	
	private void stop(){
		plugin.getServer().getScheduler().cancelTask(id);
		remove();
	}	
	
	public void remove(){
		for(Block b : blocks){
			b.setTypeId(0);
		}
	}
	
}
