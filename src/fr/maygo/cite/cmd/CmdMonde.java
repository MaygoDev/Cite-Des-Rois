package fr.maygo.cite.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.maygo.cite.Main;
import fr.maygo.cite.utils.Messages;
import fr.maygo.cite.utils.Teleporter;

public class CmdMonde implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(!Main.just_joined.contains(player)) {
				Teleporter.WORLD.teleport(player);
				player.sendMessage(Messages.PREFIX.get()+"§6Vous êtes maintenant à spawn du monde !");
			}else {
				player.sendMessage("§cErreur: Veuillez cliquez sur la boussole !");
			}
		}
		return false;
	}

}
