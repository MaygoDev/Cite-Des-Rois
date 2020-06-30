package fr.maygo.cite.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import fr.maygo.cite.Main;
import fr.maygo.cite.region.Region;
import fr.maygo.cite.region.RegionFlag;

public class PlayerDamage implements Listener {

	public Main main;
	
	public PlayerDamage(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (player.getWorld().getName().equalsIgnoreCase("spawn")) {
				event.setCancelled(true);
			}
			if (!main.regions.isEmpty()) {
				for (Region region : main.regions) {
					if (region.playerInArea(player)) {
						if (region.getFlags().contains(RegionFlag.NO_DAMAGE)) {
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}

}
