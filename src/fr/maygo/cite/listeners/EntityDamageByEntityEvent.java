package fr.maygo.cite.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import fr.maygo.cite.Main;
import fr.maygo.cite.region.Region;
import fr.maygo.cite.region.RegionFlag;

public class EntityDamageByEntityEvent implements Listener {

	public Main main;
	
	public EntityDamageByEntityEvent(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onEntityDamageByEntityEvent(org.bukkit.event.entity.EntityDamageByEntityEvent event) {
		Entity victim = event.getEntity();
		Entity attacker = event.getDamager();
		if(victim instanceof Player && attacker instanceof Player) {
			Player target = (Player) event.getEntity();
			Player player = (Player) event.getDamager();
			if(!main.regions.isEmpty()) {
				for(Region region : main.regions) {
					if(region.playerInArea(target) && region.playerInArea(player)) {
						if(region.hasFlag(RegionFlag.NO_PVP)) {
							event.setCancelled(true);
						}
					}
				}
			}
		} else if (victim instanceof Villager && attacker instanceof Player) {
			if (event.getEntity().getWorld().getName().equalsIgnoreCase("spawn")) {
				if(!attacker.isOp()) {
					event.setCancelled(true);
				}
			}
		}
	}
	
}
