package fr.maygo.cite.cmd;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.maygo.cite.utils.Messages;
import fr.maygo.cite.utils.Teleporter;

public class CmdCite implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			Teleporter.SPAWN.teleport(player);
			player.setGameMode(GameMode.ADVENTURE);
			player.sendMessage(Messages.PREFIX.get()+"§6Vous êtes maintenant à la cité !");
		}
		return false;
	}

}
