package fr.maygo.cite.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class PlayerFood implements Listener {
	
	@EventHandler
	public static void onPlayerFood(FoodLevelChangeEvent event) {
		event.setCancelled(true);
		event.setFoodLevel(20);
	}
	
}
