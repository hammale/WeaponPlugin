package me.hammale.grenade;

import java.util.Arrays;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class projectile {
	
	grenade plugin;
	public int taskId, delay, radius;
	public Item item;
	Random ran = new Random();
	
	projectile(grenade plugin, int delay, int radius, Item item){
		this.plugin = plugin;
		this.delay = delay;
		this.radius = radius;
		this.item = item;
	}
	
	public void fire() {
	    taskId = plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
	      public void run(){
	    	  boom();
	      }
	    }
	    , delay*20);
	  }

	private void boom(){
		plugin.items.remove(item);
		plugin.getServer().getScheduler().cancelTask(taskId);
		if(plugin.getConfig().get("Grenade.369.PotionEffects") != null){
			for(String s : plugin.getConfig().getStringList("Grenade.369.PotionEffects")){
				String[] split = s.split(":");
				if(plugin.getNearbyPlayers(item.getLocation(), radius) != null){	
					for(Player p : plugin.getNearbyPlayers(item.getLocation(), radius)){
						p.addPotionEffect(new PotionEffect(PotionEffectType.getById(Integer.parseInt(split[0])), Integer.parseInt(split[1])*20, 1));
					}
				}
			}
		}
		addEffects();
		item.remove();
	}

	private void addEffects() {
		if(plugin.getConfig().get("Grenade.369.Effects") != null){
			for(int i : plugin.getConfig().getIntegerList("Grenade.369.Effects")){
				if(i == 1){			
					Entity e = item.getLocation().getWorld().spawnEntity(item.getLocation(), EntityType.PRIMED_TNT);
					plugin.tnt.add(e.getEntityId());
				}else if(i == 2){
					item.getLocation().getWorld().spawnEntity(item.getLocation(), EntityType.CHICKEN);				
				}else if(i == 3){
					item.getLocation().getWorld().spawnEntity(item.getLocation(), EntityType.COW);				
				}else if(i == 4){
					item.getLocation().getWorld().spawnEntity(item.getLocation(), EntityType.CREEPER);				
				}else if(i == 5){
					item.getLocation().getWorld().spawnEntity(item.getLocation(), EntityType.CAVE_SPIDER);				
				}else if(i == 6){
					item.getLocation().getWorld().spawnEntity(item.getLocation(), EntityType.BLAZE);				
				}else if(i == 7){
					item.getLocation().getWorld().spawnEntity(item.getLocation(), EntityType.ENDERMAN);				
				}else if(i == 8){
					item.getLocation().getWorld().spawnEntity(item.getLocation(), EntityType.ENDER_DRAGON);				
				}else if(i == 9){
					item.getLocation().getWorld().spawnEntity(item.getLocation(), EntityType.GHAST);				
				}else if(i == 10){
					item.getLocation().getWorld().spawnEntity(item.getLocation(), EntityType.GIANT);				
				}else if(i == 11){
					item.getLocation().getWorld().spawnEntity(item.getLocation(), EntityType.IRON_GOLEM);				
				}else if(i == 12){
					item.getLocation().getWorld().spawnEntity(item.getLocation(), EntityType.MAGMA_CUBE);				
				}else if(i == 13){
					item.getLocation().getWorld().spawnEntity(item.getLocation(), EntityType.MUSHROOM_COW);				
				}else if(i == 14){
					item.getLocation().getWorld().spawnEntity(item.getLocation(), EntityType.OCELOT);			
				}else if(i == 15){
					item.getLocation().getWorld().spawnEntity(item.getLocation(), EntityType.PIG);				
				}else if(i == 16){
					item.getLocation().getWorld().spawnEntity(item.getLocation(), EntityType.PIG_ZOMBIE);				
				}else if(i == 17){
					item.getLocation().getWorld().spawnEntity(item.getLocation(), EntityType.SHEEP);				
				}else if(i == 18){
					item.getLocation().getWorld().spawnEntity(item.getLocation(), EntityType.SILVERFISH);				
				}else if(i == 19){
					item.getLocation().getWorld().spawnEntity(item.getLocation(), EntityType.SKELETON);				
				}else if(i == 20){
					item.getLocation().getWorld().spawnEntity(item.getLocation(), EntityType.SLIME);				
				}else if(i == 21){
					item.getLocation().getWorld().spawnEntity(item.getLocation(), EntityType.SNOWMAN);				
				}else if(i == 22){
					item.getLocation().getWorld().spawnEntity(item.getLocation(), EntityType.SPIDER);			
				}else if(i == 23){
					item.getLocation().getWorld().spawnEntity(item.getLocation(), EntityType.SQUID);				
				}else if(i == 24){
					item.getLocation().getWorld().spawnEntity(item.getLocation(), EntityType.VILLAGER);				
				}else if(i == 25){
					item.getLocation().getWorld().spawnEntity(item.getLocation(), EntityType.WOLF);				
				}else if(i == 26){
					item.getLocation().getWorld().spawnEntity(item.getLocation(), EntityType.ZOMBIE);				
				}else if(i == 27){
					item.getLocation().getWorld().strikeLightning(item.getLocation());				
				}else if(i == 28){
					for(Player p : plugin.getNearbyPlayers(item.getLocation(), radius)){
						p.setFireTicks(100);
					}
				}else if(i == 29){
					for(Player p : plugin.getNearbyPlayers(item.getLocation(), radius)){					
						p.setVelocity(p.getLocation().getDirection().multiply(ran.nextInt(10)+10));
					}
				}else if(i == 30){
					for(Player p : plugin.getNearbyPlayers(item.getLocation(), radius)){					
						p.kickPlayer("GRENADE!");
					}
				}else if(i == 31){
					for(Player p : plugin.getNearbyPlayers(item.getLocation(), radius)){					
						plugin.frozen.add(p.getName());
						startFreeze(p);
					}
				}else if(i == 32){
					for (int x = 0; x < 15; x++) {
						Location loc = item.getLocation();
						loc.setY(loc.getY() + 15);
						loc.setX( loc.getX() + (ran.nextInt((2*radius)+1)-radius) );
						loc.setZ( loc.getZ() + (ran.nextInt((2*radius)+1)-radius) );
						loc.setPitch(-90);
						Vector vec = new Vector(0, -1, 0);
						item.getWorld().spawnArrow(loc, vec, 0.6f, 12f);
					}
				}else if(i == 33){
					for (int x = 0; x < 15; x++) {
						Location loc = item.getLocation();
						loc.setY(loc.getY());
						loc.setX( loc.getX() + (ran.nextInt((2*radius)+1)-radius) );
						loc.setZ( loc.getZ() + (ran.nextInt((2*radius)+1)-radius) );
						item.getWorld().strikeLightning(loc);
					}
				}else if(i == 35){
					glassBox box = new glassBox(plugin);
					for(Player p : plugin.getNearbyPlayers(item.getLocation(), radius)){
						new sphere(box, p.getLocation().add(0, 2, 0), 3).set(20);
							p.getLocation().add(0, 1, 0).getBlock().setTypeId(0);
							p.getLocation().add(0, 2, 0).getBlock().setTypeId(0);
							p.teleport(p.getLocation().add(0, 1, 0));
					}				
				}else if(i == 36){
					for(LivingEntity g  : Arrays.asList(plugin.getNearbyGolems(item.getLocation(), radius))){
						new Turret(plugin, g);
					}
				}
			}
		}	
	}
	
	public void startFreeze(final Player p){
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run(){
				plugin.frozen.remove(p.getName());
	    	}
		}
		, 100);
	}
	
}