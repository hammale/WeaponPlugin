package me.hammale.grenade;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
 
public class sphere {
 
	private int xx;
	private int yy;
	private int zz;
	glassBox box;
	 
	private ArrayList<Block> set;
	 
	private int radius;
	 
	public sphere(glassBox box, Location center, int radius){
		this.box = box;
		this.radius = radius;
		int xx = (int)Math.floor(center.getX());
		int yy = (int)Math.floor(center.getY());
		int zz = (int)Math.floor(center.getZ());
		 
		cuboid region = new cuboid(new Location(center.getWorld(),xx+radius,yy+radius,zz+radius),new Location(center.getWorld(),xx-radius,yy-radius,zz-radius));
		ArrayList<Block> set = region.getBlocks();
		 
		this.xx = xx;
		this.yy = yy;
		this.zz = zz;
		this.set = set;
	 
	}
	 
	public void set(int id){
		for(int counter = 0; counter <= set.size() - 1; counter++){
			int a = (set.get(counter).getX() - xx)*(set.get(counter).getX() - xx);
			int b = (set.get(counter).getY() - yy)*(set.get(counter).getY() - yy);
			int c = (set.get(counter).getZ() - zz)*(set.get(counter).getZ() - zz);
			if(a +b +c <= (radius*radius)){
				if(set.get(counter).getTypeId() == 0){
					box.blocks.add(set.get(counter));
					set.get(counter).setType(Material.getMaterial(id));
				}
			}
		}
	}
}