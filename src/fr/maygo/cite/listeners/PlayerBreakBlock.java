package fr.maygo.cite.listeners;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import fr.maygo.cite.Main;
import fr.maygo.cite.region.Region;
import fr.maygo.cite.region.RegionFlag;
import fr.maygo.cite.utils.Messages;

public class PlayerBreakBlock implements Listener {

	public Main main;
	
	public PlayerBreakBlock(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onPlayerBreakBlock(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();
		for (Region regions : main.regions) {
			if (regions.hasFlag(RegionFlag.NO_GRIEF)) {
				if (regions.locationInArea(block.getLocation())) {
					event.setCancelled(true);
					player.sendMessage(Messages.PREFIX.get()+ "§6Vous avez pas le droit de casser des blocs dans la zone : "+regions.getName());
				}
			}
		}
	}
	
}
