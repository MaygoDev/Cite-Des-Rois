package fr.maygo.cite.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.maygo.cite.utils.Messages;
import fr.maygo.cite.villagers.Villagers;

public class CmdVillager implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		Player player = (Player) sender;
		if(player.isOp()) {
			if(args.length == 1) {
				if(Villagers.valueOf(args[0]) != null) {
					Villagers villager = Villagers.valueOf(args[0]);
					villager.spawnVillager(player.getLocation());
					player.sendMessage(Messages.PREFIX.get()+"§6Vous avez bien fait spawn le villageoi : "+villager.getName()+"§6 !");
				}
			}else {
				sender.sendMessage("§cUtilisation : /villager <nom>");
			}
		}
		return false;
	}

}
