package fr.maygo.cite.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import fr.maygo.cite.sql.SqlConnection;
import fr.maygo.cite.utils.Emeralds;
import fr.maygo.cite.utils.Inventory;
import fr.maygo.cite.villagers.Villagers;

public class PlayerInteractInventory implements Listener {
	
	public SqlConnection sql;
	public Inventory inv;
	public Emeralds emeralds;
	
	public PlayerInteractInventory(Inventory inv,SqlConnection sql,Emeralds emeralds) {
		this.inv = inv;
		this.sql = sql;
		this.emeralds = emeralds;
	}
	
	@EventHandler
	public void onPLayerInteractInventory(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack item = event.getCurrentItem();
		org.bukkit.inventory.Inventory inv1 = event.getClickedInventory();
		if(item != null) {
			if(player.getWorld().getName().equalsIgnoreCase("spawn")) {
				if(inv1.getName().equals("§2Déposer vos émeraudes")) {
					if(item.getItemMeta().getDisplayName().equals("§2§lDéposer toutes vos émeraudes")) {
						event.setCancelled(false);
						emeralds.checkInventory(player);
						player.closeInventory();
					}
				}
				for(Villagers villagers : Villagers.values()) {
					if(inv1.getName().equalsIgnoreCase(villagers.getName())) {
						event.setCancelled(true);
						if(inv1.getItem(8).getItemMeta().getDisplayName().equalsIgnoreCase("§6§lFaire échange")) {
							villagers.checkInventory(player);
						}
					}
				}
			}
		}
	}
	
}
