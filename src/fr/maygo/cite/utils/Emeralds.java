package fr.maygo.cite.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.maygo.cite.scoreboard.Scoreboard;
import fr.maygo.cite.sql.SqlConnection;

public class Emeralds {
	
	public SqlConnection sql;
	public Scoreboard sb;
	
	public Emeralds(SqlConnection sql, Scoreboard sb) {
		this.sql = sql;
		this.sb = sb;
	}
	
	public void checkInventory(Player player) {
		Inventory inv = player.getInventory();
		boolean hasEmeralds = false;
		int emeralds = 0;
		for(ItemStack item : inv){
			if(item != null) {
				if(item.getType() == Material.EMERALD) {
					sql.addEmeralds(player, item.getAmount());
					sql.addEmeraldsTeam(Teams.valueOf(sql.getTeam(player).toUpperCase()), item.getAmount());
					emeralds = emeralds + item.getAmount();
					hasEmeralds = true;
					inv.removeItem(item);
					player.updateInventory();
				}else if(item.getType() == Material.EMERALD_BLOCK) {
					sql.addEmeralds(player, item.getAmount()*9);
					sql.addEmeraldsTeam(Teams.valueOf(sql.getTeam(player).toUpperCase()), item.getAmount()*9);
					emeralds = emeralds + item.getAmount()*9;
					hasEmeralds = true;
					inv.removeItem(item);
					player.updateInventory();
				}
			}
		}
		if(hasEmeralds == true) {
			Bukkit.broadcastMessage(Messages.PREFIX.get()+"§4"+player.getName()+"§6 à déposé §4"+emeralds+" émeraudes !");
			sb.refreshEmeralds(player);
		}
		player.updateInventory();
	}
	
	
	
}
