package fr.maygo.cite.cmd;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import fr.maygo.cite.sql.SqlConnection;
import fr.maygo.cite.utils.Messages;
import fr.maygo.cite.utils.Top;

public class CmdArmorstand implements CommandExecutor {

	public List<ArmorStand> as = new ArrayList<>();
	public SqlConnection sql;
	public Top top = new Top();
	
	public CmdArmorstand(SqlConnection sql) {
		this.sql = sql;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.isOp()) {
				if (args.length == 0) {
					player.sendMessage("§cUtilisation: /armorstand [nom]/<remove>");
					if (as.size() == 0) {
						player.sendMessage("§cErreur: Il n'y a aucune armorstand créer!");
					} else {
						for (ArmorStand stands : as) {
							player.sendMessage("§3- §r" + stands.getCustomName());
						}
					}
				} else if (args.length >= 1) {
					if (args[0].equalsIgnoreCase("remove")) {
						if (player.getNearbyEntities(1, 1, 1).size() == 0) {
							player.sendMessage(
									"§cErreur: Il n'y a aucune Armorstand personalisée aux alentours de 1 bloc.");
						} else {
							for (Entity entity : player.getNearbyEntities(1, 1, 1)) {
								if (entity instanceof ArmorStand) {
									for (ArmorStand stands : as) {
										if (entity == stands) {
											stands.remove();
											as.remove(stands);
										}
									}
								}
							}
						}

					}else {
						StringBuilder sb = new StringBuilder();
						for (String part : args) {
							sb.append(part + " ");
						}
						ArmorStand stand = (ArmorStand) player.getLocation().getWorld()
								.spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
						as.add(stand);
						stand.setCustomNameVisible(true);
						stand.setCanPickupItems(false);
						stand.setCustomName(sb.toString().replace("&", "§").substring(0, sb.toString().length()-1));
						stand.setVisible(false);
						stand.setGravity(false);
						player.sendMessage("§3L'Armostand §r" + sb.toString().replace("&", "§").substring(0, sb.toString().length()-1) + "§3 à été créer !");
					}
				}
			}else {
				player.sendMessage(Messages.NOT_PERM.get());
			}

		}
		return false;
	}

}
