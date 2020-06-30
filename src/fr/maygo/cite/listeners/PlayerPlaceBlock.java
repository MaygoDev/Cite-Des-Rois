package fr.maygo.cite.listeners;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

import fr.maygo.cite.Main;
import fr.maygo.cite.region.Region;
import fr.maygo.cite.region.RegionFlag;
import fr.maygo.cite.utils.Messages;

public class PlayerPlaceBlock implements Listener {

	public Main main;

	public PlayerPlaceBlock(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onPlayerPlaceBlock(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();
		for (Region regions : main.regions) {
			if (regions.hasFlag(RegionFlag.NO_BUILD)) {
				if (regions.locationInArea(block.getLocation())) {
					event.setCancelled(true);
					player.sendMessage(Messages.PREFIX.get()+ "§6Vous avez pas le droit de construire dans la zone : "+regions.getName());
				}
			}
		}
	}

	@EventHandler
	public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlockClicked();
		for (Region region : main.regions) {
			if (region.hasFlag(RegionFlag.NO_BUCKET)) {
				if (region.locationInArea(block.getLocation())) {
					event.setCancelled(true);
					player.sendMessage(Messages.PREFIX.get()
							+ "§6Vous avez pas le droit de placer d'eau dans la zone : " + region.getName());
				}
			}
		}
	}

}
