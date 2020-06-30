package fr.maygo.cite.cmd;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.maygo.cite.Main;
import fr.maygo.cite.region.Region;
import fr.maygo.cite.region.RegionFlag;
import fr.maygo.cite.utils.Messages;

public class CmdRegion implements CommandExecutor {

	public Main main;

	public CmdRegion(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if (sender.isOp()) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (args.length == 1) {
					if (args[0].equalsIgnoreCase("infos")) {
						player.sendMessage("§8§m-----------------------");
						if (main.regions.isEmpty()) {
							player.sendMessage(Messages.PREFIX.get() + "§6Il n'y a pas de région de crée !");
						} else {
							for (Region region : main.regions) {
								player.sendMessage("§8- §6" + region.getName() + " :");
								if (region.getLoc1() != null) {
									player.sendMessage("§4Location 1 :");
									player.sendMessage("§4X :§6 " + new Double(region.getLoc1().getX()).intValue());
									player.sendMessage("§4Z :§6 " + new Double(region.getLoc1().getZ()).intValue());
									player.sendMessage("§4Détails :§6 " + region.getLoc1().toString());
								} else {
									player.sendMessage("§4Location 1 :");
									player.sendMessage("§4X :§6 /");
									player.sendMessage("§4Z :§6 /");
								}
								if (region.getLoc2() != null) {
									player.sendMessage("§4Location 2 :");
									player.sendMessage("§4X :§6 " + new Double(region.getLoc2().getX()).intValue());
									player.sendMessage("§4Z :§6 " + new Double(region.getLoc2().getZ()).intValue());
									player.sendMessage("§4Détails :§6 " + region.getLoc2().toString());
								} else {
									player.sendMessage("§4Location 2 :");
									player.sendMessage("§4X :§6 /");
									player.sendMessage("§4Z :§6 /");
								}
								if (region.getFlags() != null) {
									if (!region.getFlags().isEmpty()) {
										player.sendMessage("§4Flags : ");
										for (RegionFlag region1 : region.getFlags()) {
											player.sendMessage("§4- §6" + region1.name());
										}
									} else {
										player.sendMessage("§4Flags : /");
									}
								} else {
									player.sendMessage("§4Flags : /");
								}
							}
						}
						player.sendMessage("§8§m-----------------------");
					} else {
						player.sendMessage("§cUtilisation : /region <infos/add/remove/setpos> <nom> <1/2>");
					}
				} else if (args.length == 2) {
					switch (args[0]) {
					case "add":
						main.regions.add(new Region(args[1], null, null, new ArrayList<>()));
						player.sendMessage(Messages.PREFIX.get() + "§6La région §4" + args[1] + "§6 à bien été crée !");
						break;
					case "remove":
						if (!main.regions.isEmpty()) {
							player.sendMessage(Messages.PREFIX.get() + "§6Il n'y a pas de régions a supprimer !");
						} else {
							for (Region region : main.regions) {
								if (args[1].equalsIgnoreCase(region.getName())) {
									main.regions.remove(region);
									player.sendMessage(Messages.PREFIX.get() + "§6La région §4" + args[1]
											+ "§6 à bien été supprimé !");
								}
							}
						}
						break;
					default:
						player.sendMessage("§cUtilisation : /region <infos/add/remove/setpos> <nom> <1/2>");
						break;
					}
				} else if (args.length == 3) {
					if (args[0].equalsIgnoreCase("setpos")) {
						for (Region region : main.regions) {
							if (args[1].equalsIgnoreCase(region.getName())) {
								if(args[2].equals("1")) {
									region.setLoc1(player.getLocation());
								}else if(args[2].equals("2")) {
									region.setLoc2(player.getLocation());
								}
								player.sendMessage(Messages.PREFIX.get() + "§6La position "+args[2]+" de la région §4" + args[1]
										+ "§6 à bien été remplacé !");
							}
						}
					} else if (args[0].equalsIgnoreCase("addflag")) {
						for (Region region : main.regions) {
							if (args[1].equalsIgnoreCase(region.getName())) {
								for (RegionFlag flag : RegionFlag.values()) {
									if (flag.toString().equalsIgnoreCase(args[2])) {
										if (!region.getFlags().contains(flag)) {
											region.addFlag(flag);
										} else {
											player.sendMessage(
													Messages.PREFIX.get() + "§6Cette region contient déjà ce flag !");
										}
									} else {
										player.sendMessage(Messages.PREFIX.get()
												+ "§6La flag que vous avez entré n'est pas valide !");
										player.sendMessage("§6Liste des flags :");
										for (RegionFlag flag1 : RegionFlag.values()) {
											player.sendMessage("§4- " + flag1.name());
										}
									}
								}
							}
						}
					} else if (args[0].equalsIgnoreCase("removeflag")) {
						for (Region region : main.regions) {
							if (args[1].equalsIgnoreCase(region.getName())) {
								for (RegionFlag flag : RegionFlag.values()) {
									if (flag.toString().equalsIgnoreCase(args[2])) {
										if (region.getFlags().contains(flag)) {
											region.removeFlag(flag);
										} else {
											player.sendMessage(
													Messages.PREFIX.get() + "§6Cette region ne contient pas ce flag !");
										}
									} else {
										player.sendMessage(Messages.PREFIX.get()
												+ "§6La flag que vous avez entré n'est pas valide !");
										player.sendMessage("§6Liste des flags :");
										for (RegionFlag flag1 : RegionFlag.values()) {
											player.sendMessage("§4- " + flag1.name());
										}
									}
								}
							}
						}
					} else {
						player.sendMessage("§cUtilisation : /region <infos/add/remove/setpos> <nom> <1/2>");
					}
				} else {
					player.sendMessage(
							"§cUtilisation : /region <infos/add/remove/setpos/addflag/removeflag> <nom> <1/2/nomduflag>");
				}
			}
		}
		return false;
	}

}
