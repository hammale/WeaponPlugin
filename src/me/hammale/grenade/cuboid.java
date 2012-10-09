package me.hammale.grenade;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

public class cuboid {
    private Location a, b;

    public cuboid(Location a, Location b) {
    	this.a = a;
    	this.b = b;
    }
    
	public Location getCenter() {
		return new Location(this.a.getWorld(),
				(this.a.getBlockX() + this.b.getBlockX()) / 2D,
				(this.a.getBlockY() + this.b.getBlockY()) / 2D,
				(this.a.getBlockZ() + this.b.getBlockZ()) / 2D);
	}
	
	public Block getCenterBlock(){
		return getWorld().getBlockAt(getCenter());
	}
    
    public void setA(Location a) {
        this.a = a;
    }

    public void setB(Location b) {
        this.b = b;
    }

    public Location getA() {
        return this.a;
    }

    public Location getB() {
        return this.b;
    }

    public World getWorld() {
        return this.a.getWorld();
    }

    public int getMaxX() {
        return Math.max(a.getBlockX(), b.getBlockX());
    }

    public int getMinX() {
        return Math.min(a.getBlockX(), b.getBlockX());
    }

    public int getMaxY() {
        return Math.max(a.getBlockY(), b.getBlockY());
    }

    public int getMinY() {
        return Math.min(a.getBlockY(), b.getBlockY());
    }

    public int getMaxZ() {
        return Math.max(a.getBlockZ(), b.getBlockZ());
    }

    public int getMinZ() {
        return Math.min(a.getBlockZ(), b.getBlockZ());
    }
    
    public void setType(Material m){
    	setType(m.getId());
    }
    
    public ArrayList<Block> getBlocks() {
    	ArrayList<Block> blocks = new ArrayList<Block>();
    	int minx = Math.min(a.getBlockX(), b.getBlockX()),
    	miny = Math.min(a.getBlockY(), b.getBlockY()),
    	minz = Math.min(a.getBlockZ(), b.getBlockZ()),
    	maxx = Math.max(a.getBlockX(), b.getBlockX()),
    	maxy = Math.max(a.getBlockY(), b.getBlockY()),
    	maxz = Math.max(a.getBlockZ(), b.getBlockZ());
    	for(int x = minx; x<=maxx;x++){
	    	for(int y = miny; y<=maxy;y++){
		    	for(int z = minz; z<=maxz;z++){
		    	Block b = getWorld().getBlockAt(x, y, z);
		    		blocks.add(b);
		    	}
	    	}
    	}
    	return blocks;
    }
    
    public void setType(int id) {	 
    	int minx = Math.min(a.getBlockX(), b.getBlockX()),
    	miny = Math.min(a.getBlockY(), b.getBlockY()),
    	minz = Math.min(a.getBlockZ(), b.getBlockZ()),
    	maxx = Math.max(a.getBlockX(), b.getBlockX()),
    	maxy = Math.max(a.getBlockY(), b.getBlockY()),
    	maxz = Math.max(a.getBlockZ(), b.getBlockZ());
    	for(int x = minx; x<=maxx;x++){
	    	for(int y = miny; y<=maxy;y++){
		    	for(int z = minz; z<=maxz;z++){
		    	Block b = getWorld().getBlockAt(x, y, z);
		    		b.setTypeId(id);
		    	}
	    	}
    	}
    }
    
    public boolean isInCuboid(Location location) {
        return location.getWorld() == this.getWorld()
                && location.getBlockX() >= this.getMinX()
                && location.getBlockX() <= this.getMaxX()
                && location.getBlockY() >= this.getMinY()
                && location.getBlockY() <= this.getMaxY()
                && location.getBlockZ() >= this.getMinZ()
                && location.getBlockZ() <= this.getMaxZ();
    }
    public boolean isInCuboid(Entity e) {
        return e.getWorld() == this.getWorld()
                && e.getLocation().getBlockX() >= this.getMinX()
                && e.getLocation().getBlockX() <= this.getMaxX()
                && e.getLocation().getBlockY() >= this.getMinY()
                && e.getLocation().getBlockY() <= this.getMaxY()
                && e.getLocation().getBlockZ() >= this.getMinZ()
                && e.getLocation().getBlockZ() <= this.getMaxZ();
    }

    public boolean isInCuboid(Block b) {
        return b.getWorld() == this.getWorld()
                && b.getLocation().getBlockX() >= this.getMinX()
                && b.getLocation().getBlockX() <= this.getMaxX()
                && b.getLocation().getBlockY() >= this.getMinY()
                && b.getLocation().getBlockY() <= this.getMaxY()
                && b.getLocation().getBlockZ() >= this.getMinZ()
                && b.getLocation().getBlockZ() <= this.getMaxZ();
    }

}
