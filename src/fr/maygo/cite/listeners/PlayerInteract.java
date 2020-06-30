package fr.maygo.cite.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.maygo.cite.Main;
import fr.maygo.cite.sql.SqlConnection;
import fr.maygo.cite.utils.Inventory;

public class PlayerInteract implements Listener {

	public Inventory inv;
	public SqlConnection sql;

	public PlayerInteract(Inventory inv,SqlConnection sql) {
		this.inv = inv;
		this.sql = sql;
	}

	@EventHandler
	public void onPLayerInteractInventory(PlayerInteractEvent event) {
		Player player = (Player) event.getPlayer();
		ItemStack item = event.getItem();
		if(item != null) {
			if (item.getType() == Material.COMPASS) {
				player.teleport(sql.getLastConnection(player));
				Main.just_joined.remove(player);
				inv.getInventory(player);
				player.setGameMode(GameMode.SURVIVAL);
			}
		}
		if(event.getClickedBlock() != null) {
			if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				if (event.getClickedBlock().getType() == Material.CHEST) {
		        	if(player.getWorld().getName().equalsIgnoreCase("spawn")) {
		        		event.setCancelled(true);
		        		org.bukkit.inventory.Inventory inv = Bukkit.createInventory(null, 9*1, "§2Déposer vos émeraudes");
		        		ItemStack depot = new ItemStack(Material.EMERALD);
		        		ItemMeta itemM = depot.getItemMeta();
		        		itemM.addEnchant(Enchantment.FIRE_ASPECT,1, true);
		        		itemM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		        		itemM.setDisplayName("§2§lDéposer toutes vos émeraudes");
		        		depot.setItemMeta(itemM);
		        		inv.setItem(4, depot);
		        		player.openInventory(inv);
		        	}
				}
			}
		}
	}

}
