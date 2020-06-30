package fr.maygo.cite.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.maygo.cite.Main;
import fr.maygo.cite.utils.Messages;

public class CmdTpaAccept implements CommandExecutor {
	
	public Main main;
	
	public CmdTpaAccept(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		Player player = (Player)sender;
		if(args.length == 1) {
			Player target = Bukkit.getPlayer(args[0]);
			if(target != null) {
				if(player != target) {
					if(main.tpaEnAttente.containsKey(target)) {
						target.teleport(main.tpaEnAttente.get(target));
						target.sendMessage(Messages.PREFIX.get()+"§4"+player.getName()+"§6 à accepté votre demande !");
						player.sendMessage(Messages.PREFIX.get()+"Vous avez accepté la demande de §4"+target.getName()+"§6 !");
						main.tpaEnAttente.remove(target);
					}else {
						player.sendMessage("§cErreur : Le joueur que vous avez entré ne vous a pas fait de demande de tpa !");
					}
				}else {
					player.sendMessage("§cErreur : Vous ne pouvez pas accepter demande de tpa de vous même !");
				}
			}else {
				player.sendMessage("§cErreur : Le joueur que vous avez entré est introuvable");
			}
		}else {
			player.sendMessage("§cUtilisation : /tpa <joueur>");
		}
		return false;
	}

}
