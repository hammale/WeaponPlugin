package me.hammale.grenade;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class grenade extends JavaPlugin {
	
	Logger log = Logger.getLogger("Minecraft");
	
	public FileConfiguration config;
	
	public ArrayList<Item> items = new ArrayList<Item>();

	public HashSet<String> frozen = new HashSet<String>();
	public HashSet<Integer> tnt = new HashSet<Integer>();
	public HashSet<Integer> golems = new HashSet<Integer>();

	@Override
	public void onEnable()
	{
		this.config = getConfig();
		PluginDescriptionFile pdfFile = getDescription();
		this.log.info(pdfFile.getName() +   " Has Been Enabled!");
		getServer().getPluginManager().registerEvents(new listener(this), this);
		handleConfig();
	}

	public void handleConfig(){
		if (!fileExists()) {
			config = getConfig();
			config.options().copyDefaults(false);
			config.addDefault("Grenade.369.Radius", 5);
			config.addDefault("Grenade.369.Delay", 5);
			String[] pots = new String[]{"3:5"};
			config.addDefault("Grenade.369.PotionEffects", pots);
			Integer[] effects = new Integer[]{1};
			config.addDefault("Grenade.369.Effects", effects);
			config.options().copyDefaults(true);
			saveConfig();
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("grenade")
			&& sender.isOp()
			&& args.length > 0
			&& args[0].equalsIgnoreCase("reload")){
				reloadConfig();
				config = getConfig();
				sender.sendMessage("Config reloaded!");
		}
		return true;
	}
	
	public boolean exists(int id){
		if(config.get("Grenade." + id + ".Radius") != null){
			return true;
		}
		return false;
	}
	
	
	public String Colorize(String s) {
	    if (s == null) return null;
	    return s.replaceAll("&([0-9a-f])", "§$1");
	}
	
	private boolean fileExists() {
		try {
			File file = new File("plugins/WeaponPlugin/config.yml");
			return file.exists();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		return true;
	}
	
	@Override
	public void onDisable()
	{
		PluginDescriptionFile pdfFile = getDescription();
		this.log.info(pdfFile.getName() + " Has Been Disabled!");
	}

	public ArrayList<Player> getNearbyPlayers(Location loc, int radius) {
		ArrayList<Player> tmp = new ArrayList<Player>();
		for(Player p : getServer().getOnlinePlayers()){
			if(p.getLocation().distance(loc) <= radius){
				tmp.add(p);
			}
		}
		return tmp;
	}
	
	public LivingEntity[] getNearbyGolems(Location l, int radius){
        int chunkRadius = radius < 16 ? 1 : (radius - (radius % 16))/16;
        HashSet<Entity> radiusEntities = new HashSet<Entity>();
            for (int chX = 0 -chunkRadius; chX <= chunkRadius; chX ++){
                for (int chZ = 0 -chunkRadius; chZ <= chunkRadius; chZ++){
                    int x=(int) l.getX(),y=(int) l.getY(),z=(int) l.getZ();
                    for (Entity e : new Location(l.getWorld(),x+(chX*16),y,z+(chZ*16)).getChunk().getEntities()){
                        if (e.getLocation().distance(l) <= radius && e.getLocation().getBlock() != l.getBlock()){
                        	if(e instanceof LivingEntity){
                        		LivingEntity le = (LivingEntity) e;
                        		if(le.getType() == EntityType.IRON_GOLEM){
                        			radiusEntities.add(e);
                        		}
                        	}
                        }
                    }
                }
            }
        return radiusEntities.toArray(new LivingEntity[radiusEntities.size()]);
    }
	
	public LivingEntity[] getTargets(Location l){
		int radius = 20;
        int chunkRadius = radius < 16 ? 1 : (radius - (radius % 16))/16;
        HashSet<Entity> radiusEntities = new HashSet<Entity>();
            for (int chX = 0 -chunkRadius; chX <= chunkRadius; chX ++){
                for (int chZ = 0 -chunkRadius; chZ <= chunkRadius; chZ++){
                    int x=(int) l.getX(),y=(int) l.getY(),z=(int) l.getZ();
                    for (Entity e : new Location(l.getWorld(),x+(chX*16),y,z+(chZ*16)).getChunk().getEntities()){
                        if (e.getLocation().distance(l) <= radius && e.getLocation().getBlock() != l.getBlock()){
                        	if(e instanceof LivingEntity){
                        		LivingEntity le = (LivingEntity) e;
                        		if(!(le.getType() == EntityType.IRON_GOLEM)){
                        			radiusEntities.add(e);
                        		}
                        	}
                        }
                    }
                }
            }
        return radiusEntities.toArray(new LivingEntity[radiusEntities.size()]);
    }
	
	public int getDelay(int id) {
		return config.getInt("Grenade." + id + ".Delay");
	}
	
	public int getRadius(int id) {
		return config.getInt("Grenade." + id + ".Radius");
	}
	
}
