package fr.maygo.cite.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class Top {

	double ia = 0;
	public List<ArmorStand> stands = new ArrayList<>();
	
	public void getTopPlayer(Player player,Map<OfflinePlayer, Integer> map) {
		map.entrySet()
        .stream()
        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
        .limit(3)
        .forEach(e -> {
        	player.sendMessage(e.getKey().getName() + " : " + e.getValue());
        });
	}
	
	public void getTopTeams(Player player,Map<Teams, Integer> map) {
		map.entrySet()
        .stream()
        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
        .limit(3)
        .forEach(e -> {
        	player.sendMessage(e.getKey().getName() + " : " + e.getValue());
        });
	}
	
	public void setTopTeamsArmorStand(Map<Teams, Integer> map) {
		map.entrySet()
        .stream()
        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
        .limit(3)
        .forEach(e -> {
        	Location loc = new Location(Bukkit.getWorld("spawn"), -676, 58-ia, -114);
        	ArmorStand stand = (ArmorStand) Bukkit.getWorld("spawn")
        			.spawnEntity(loc, EntityType.ARMOR_STAND);
        	stand.setCustomNameVisible(true);
        	stand.setCanPickupItems(false);
        	stand.setCustomName(e.getKey().getDisplayName()+" : "+e.getValue());
        	stand.setVisible(false);
        	stand.setGravity(false);
        	stands.add(stand);
        	ia = ia + 0.5;
        });
		ia =0;
	}
	
	public void setTopPlayersArmorStand(Map<OfflinePlayer, Integer> map) {
		map.entrySet()
        .stream()
        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
        .limit(3)
        .forEach(e -> {
        	Location loc = new Location(Bukkit.getWorld("spawn"), -665, 58-ia, -114);
        	ArmorStand stand = (ArmorStand) Bukkit.getWorld("spawn")
        			.spawnEntity(loc, EntityType.ARMOR_STAND);
        	stand.setCustomNameVisible(true);
        	stand.setCanPickupItems(false);
        	stand.setCustomName("§5"+e.getKey().getName()+" : §d"+e.getValue());
        	stand.setVisible(false);
        	stand.setGravity(false);
        	stands.add(stand);
        	ia = ia + 0.5;
        });
		ia =0;
	}
	
	public void refreshClassement(Map<OfflinePlayer, Integer> mapPlayers,Map<Teams, Integer> mapTeams) {
		for(ArmorStand stand : stands) {
			stand.remove();
		}
		stands.clear();
		setTopPlayersArmorStand(mapPlayers);
		setTopTeamsArmorStand(mapTeams);
	}

	public void removeArmorStands() {
		for(ArmorStand stand : stands) {
			stand.remove();
		}
		stands.clear();
	}
	
}
