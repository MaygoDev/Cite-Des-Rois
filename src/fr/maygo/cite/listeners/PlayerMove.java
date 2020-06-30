package fr.maygo.cite.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.maygo.cite.Main;
import fr.maygo.cite.region.Region;
import fr.maygo.cite.utils.Messages;

public class PlayerMove implements Listener {

	public Main main;

	public PlayerMove(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (!main.regions.isEmpty()) {
			for (Region region : main.regions) {
				if ((region.getLoc1() != null) && (region.getLoc2() != null)) {
					if (region.locationInArea(event.getFrom())) {
						if (!region.locationInArea(event.getTo())) {
							player.sendMessage(Messages.PREFIX.get() + "§cAttention : Vous sortez de la zone §6"
									+ region.getName());
						}
					} else {
						if (region.locationInArea(event.getTo())) {
							player.sendMessage(Messages.PREFIX.get() + "§cVous entrez de la zone §6"
									+ region.getName());
						}
					}
				}
			}
		}
		if(!player.getWorld().getName().equalsIgnoreCase("spawn")) {
			if(!player.isOp()) {
				player.setGameMode(GameMode.SURVIVAL);
			}
		}
	}

}
