package me.hammale.grenade;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class listener implements Listener {
	
	private grenade plugin;

	public listener(grenade plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onExplosion(EntityExplodeEvent e){
		if(e.getEntity() != null
				&& e.getEntity().getType() == EntityType.PRIMED_TNT){
			if(plugin.tnt.contains(e.getEntity().getEntityId())){
				e.setYield(0);
			}
		}
	}
	
	 @EventHandler
	 public void onThrow(PlayerInteractEvent event)
	 {
		 if(event.getAction() == Action.RIGHT_CLICK_AIR
				 || event.getAction() == Action.RIGHT_CLICK_BLOCK){
			 Player player = event.getPlayer();
		     ItemStack eggItem = player.getItemInHand();
			 if(!plugin.exists(eggItem.getType().getId())){
				 return;
			 }
		     ItemStack throwStack = new ItemStack(eggItem);
		     throwStack.setAmount(1);
		     int amt = eggItem.getAmount();
		     Location pLoc = player.getEyeLocation();    
		     
			 if(plugin.getConfig().get("Grenade." + throwStack.getTypeId() + ".Effects") != null
					 && plugin.getConfig().getIntegerList("Grenade." + throwStack.getTypeId() + ".Effects").contains(34)){
				 Byte blockData = 0x0;
				 FallingBlock fb = pLoc.getWorld().spawnFallingBlock(pLoc, Material.TNT, blockData);
				 fb.setVelocity(pLoc.getDirection().multiply(2.5F));
				 eggItem.setAmount(amt - 1);
			     player.setItemInHand(eggItem);
			     new RPG(plugin, fb);
				 return;
			 }		     
		     
			 Item thrownItem = player.getWorld().dropItem(pLoc, throwStack);			 
		     thrownItem.setVelocity(pLoc.getDirection());
		     
		     eggItem.setAmount(amt - 1);
		     player.setItemInHand(eggItem);
		     plugin.items.add(thrownItem);
			 
		     new projectile(plugin, plugin.getDelay(eggItem.getType().getId()), plugin.getRadius(eggItem.getType().getId()), thrownItem).fire();
		 }
	 }
	
	 @EventHandler
	 public void onItemPickup(PlayerPickupItemEvent e){
		if(plugin.items.contains(e.getItem())){
			e.setCancelled(true);
		}
	 }
	 
	 @EventHandler
	 public void onPlayerMove(PlayerMoveEvent e){
		 if(plugin.frozen.contains(e.getPlayer().getName())){
			 e.setTo(e.getFrom());
		 }
	 }
	 
}
