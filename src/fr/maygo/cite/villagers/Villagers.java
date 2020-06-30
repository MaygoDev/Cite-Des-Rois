package fr.maygo.cite.villagers;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public enum Villagers {
	
	packedIce("§6Packed Ice",174,4,388,1),
	patissier("§6Gâteau",354,1,388,2),
	pommeDor("§6Pomme d'or",322,1,388,4),
	cookies("§6Cookies",357,5,388,1),
	livre("§6Livres",340,1,388,2),
	diamants("§6Diamants",264,2,388,1),
	fireCharges("§6Fire Charges",385,1,388,1),
	pasteques("§6Pastèques",103,2,388,1),
	rails("§6Rails de minecarts",66,16,388,2),
	pains("§6Pains",297,2,388,1),
	seaLantern("§6Sea Lantern",169,1,388,5),
	beacon("§6Beacon",138,1,133,64),
	enderDragon("§6Oeufs de Dragons",122,1,133,64),
	glowstone("§6Glowstones",89,2,388,1),
	tableEnchantement("§6Tables d'enchantements",116,1,388,10),
	alambic("§6Alambics",379,1,388,3);
	
	String name;
	int idNeeded;
	int amountNeeded;
	int idResult;
	int amountResult;
	
	Villagers(String name, int idNeeded,int amountNeeded, int idResult,int amountResult){
		this.name = name;
		this.idNeeded = idNeeded;
		this.amountNeeded = amountNeeded;
		this.idResult = idResult;
		this.amountResult = amountResult;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIdNeeded() {
		return idNeeded;
	}

	public void setIdNeeded(int idNeeded) {
		this.idNeeded = idNeeded;
	}

	public int getAmountNeeded() {
		return amountNeeded;
	}

	public void setAmountNeeded(int amountNeeded) {
		this.amountNeeded = amountNeeded;
	}

	public int getIdResult() {
		return idResult;
	}

	public void setIdResult(int idResult) {
		this.idResult = idResult;
	}

	public int getAmountResult() {
		return amountResult;
	}

	public void setAmountResult(int amountResult) {
		this.amountResult = amountResult;
	}
	
	public void spawnVillager(Location loc) {
		VillagerSpawner.spawnVillager(loc,this);
		
	}
	
	public static Villagers toVillager(Villager villager) {
		for(Villagers villagers : Villagers.values()) {
			if(villager.getCustomName().equalsIgnoreCase(villagers.getName())) {
				return villagers;
			}
		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public void checkInventory(Player player) {
		Inventory inv = player.getInventory();
		int itemNeeded = 0;
		int itemResult = 0;
		for(ItemStack item : inv){
			if(item != null) {
				if(item.getTypeId() == idNeeded) {
					if(amountNeeded <= item.getAmount()) {
						itemNeeded = itemNeeded + item.getAmount();
						inv.removeItem(item);
						player.updateInventory();
					}
				}
			}
		}
		if(itemNeeded >= amountNeeded) {
			for(int i = 0; i < 1000; i++) {
				if(itemNeeded >= amountNeeded) {
					itemNeeded = itemNeeded - amountNeeded;
					itemResult = itemResult + amountResult;
				}
			}
		}
		for(int i = 0; i < itemResult; i++) {
			player.getInventory().addItem(new ItemStack(idResult,1));
		}
		player.updateInventory();
	}
	
}
