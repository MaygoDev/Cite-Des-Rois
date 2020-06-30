package fr.maygo.cite.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.maygo.cite.villagers.Villagers;

public class PlayerInteractEntity implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void playerInteractEntity(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		Entity target = event.getRightClicked();
		
		if(target instanceof Villager) {
			event.setCancelled(true);
			Villager npc = (Villager) target;
			Villagers villager = Villagers.toVillager(npc);
			if(villager != null) {
				Inventory inv = Bukkit.createInventory(null, 9*1, villager.getName());
				inv.addItem(new ItemStack(villager.getIdNeeded(), villager.getAmountNeeded()));
				inv.addItem(new ItemStack(villager.getIdResult(), villager.getAmountResult()));
	    		ItemStack echange = new ItemStack(Material.EMERALD);
	    		ItemMeta itemM = echange.getItemMeta();
	    		itemM.addEnchant(Enchantment.FIRE_ASPECT,1, true);
	    		itemM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
	    		itemM.setDisplayName("§6§lFaire échange");
	    		echange.setItemMeta(itemM);
	    		inv.setItem(8, echange);
	    		player.openInventory(inv);
	    	}
		}
	}
	
}
