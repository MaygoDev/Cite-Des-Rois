package fr.maygo.cite.villagers;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftVillager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

import net.minecraft.server.v1_8_R3.EntityVillager;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class VillagerSpawner {
	
	public static void spawnVillager(Location loc, Villagers villager) {

		String name = villager.getName();
		
		Villager villager1 = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
		villager1.setCustomNameVisible(true);
		villager1.setCustomName(name);

		EntityVillager entityVillager = ((CraftVillager) villager1).getHandle();
		entityVillager.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		
		NBTTagCompound nbttag = entityVillager.getNBTTag();

		if (nbttag == null)
			nbttag = new NBTTagCompound();

		entityVillager.c(nbttag);
		nbttag.setInt("NoAI", 1);
		entityVillager.f(nbttag);
	}
}
