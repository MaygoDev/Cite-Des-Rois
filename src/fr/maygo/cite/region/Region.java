package fr.maygo.cite.region;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Region {
	
	private String name;
	private Location loc1;
	private Location loc2;
	private List<RegionFlag> flags;
	
	public Region(String name,Location loc1,Location loc2,List<RegionFlag> flags) {
		this.loc1 = loc1;
		this.name = name;
		this.flags = flags;
		this.loc2 = loc2;
	}

	public Location getLoc1() {
		return loc1;
	}

	public void setLoc1(Location loc1) {
		this.loc1 = loc1;
	}

	public Location getLoc2() {
		return loc2;
	}

	public void setLoc2(Location loc2) {
		this.loc2 = loc2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<RegionFlag> getFlags() {
		return flags;
	}
	
	public void addFlag(RegionFlag flag) {
		flags.add(flag);
	}
	
	public void removeFlag(RegionFlag flag) {
		flags.remove(flag);
	}
	
	public boolean hasFlag(RegionFlag flag) {
		return flags.contains(flag);
	}

	public boolean playerInArea(Player player){
		if((loc1 != null) && (loc2 != null)) {
			if((player.getLocation().getX() > loc1.getX()) && (player.getLocation().getX() < loc2.getX())) {
	        	if((player.getLocation().getZ() < loc1.getZ()) && (player.getLocation().getZ() > loc2.getZ())) {
	        		return true;
	            }
	        }
		}
        return false;
    }
	
	public boolean locationInArea(Location loc){
		if((loc1 != null) && (loc2 != null)) {
			if((loc.getX() > loc1.getX()) && (loc.getX() < loc2.getX())) {
	        	if((loc.getZ() < loc1.getZ()) && (loc.getZ() > loc2.getZ())) {
	        		return true;
	            }
	        }
		}
        return false;
    }
}
