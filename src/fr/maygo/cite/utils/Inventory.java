package fr.maygo.cite.utils;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Inventory {
	
	public Map<Player, ItemStack[]> itemsList = new HashMap<>();
	
    public void saveInventory(Player player){
    	ItemStack[] items = new ItemStack[40];
        for(int slot = 0; slot < 36; slot++){
            ItemStack item = player.getInventory().getItem(slot);
            if(item != null){
                items[slot] = item;
            }
        }
 
        items[36] = player.getInventory().getHelmet();
        items[37] = player.getInventory().getChestplate();
        items[38] = player.getInventory().getLeggings();
        items[39] = player.getInventory().getBoots();
 
        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        itemsList.put(player, items);
    }
 
    public void getInventory(Player player){
        player.getInventory().clear();
        
    	ItemStack[] items = itemsList.get(player);
        
        for(int slot = 0; slot < 36; slot++){
            ItemStack item = items[slot];
            if(item != null){
                player.getInventory().setItem(slot, item);
            }
        }
 
        player.getInventory().setHelmet(items[36]);
        player.getInventory().setChestplate(items[37]);
        player.getInventory().setLeggings(items[38]);
        player.getInventory().setBoots(items[39]);
        itemsList.remove(player);
    }

	public void addItemTeleporter(Player player) {
		ItemStack item = new ItemStack(Material.COMPASS);
		ItemMeta itemM = item.getItemMeta();
		itemM.addEnchant(Enchantment.FIRE_ASPECT,1, true);
		itemM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemM.setDisplayName("§6Se connecter au monde");
		item.setItemMeta(itemM);
		player.getInventory().setItem(4, item);
	}
}
