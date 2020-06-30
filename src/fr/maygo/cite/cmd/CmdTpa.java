package fr.maygo.cite.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.maygo.cite.Main;
import fr.maygo.cite.utils.Messages;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class CmdTpa implements CommandExecutor {
	
	public Main main;
	
	public CmdTpa(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		Player player = (Player)sender;
		if(args.length == 1) {
			Player target = Bukkit.getPlayer(args[0]);
			if(target != null) {
				if(player != target) {
					main.tpaEnAttente.put(player, target.getLocation());
					TextComponent msg = new TextComponent(Messages.PREFIX.get()+"§4"+player.getName()+"§6 voudrait se téléporter à vous,§6 cliquez ici pour accepter sa demande !");
					msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§6Accepter sa demande !").create()));
					msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/tpaaccept "+player.getName()));
					target.spigot().sendMessage(msg);
					player.sendMessage(Messages.PREFIX.get()+"Vous avez envoyé une demande à §4"+target.getName()+"§6 !");
					main.tpaEnAttente.remove(target);
				}else {
					player.sendMessage("§cErreur : Vous ne pouvez pas faire demande de tpa à vous même !");
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
