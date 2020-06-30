package fr.maygo.cite.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

public enum Teleporter {
	
	SPAWN("spawn",-670.5,59,-55.5,180,0),
	WORLD("world",6,92,-9,180,0),
	WORLD_NETHER("world_nether",6,92,-9,180,0),
	WORLD_END("world_end",6,92,-9,180,0);
	
	public String worldName;
	public double x;
	public double y;
	public double z;
	public float yaw;
	public float pitch;
	
	Teleporter(String worldName,double x,double y,double z,float yaw,float pitch) {
		this.worldName = worldName;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}
	
	public void teleport(Player player) {
		World world = new WorldCreator(worldName).createWorld();
		Location loc = new Location(world, x, y, z,yaw,pitch);
        player.teleport(loc);
	}
	
	public World getWorld() {
		World world = new WorldCreator(worldName).createWorld();
		return world;
	}
}
