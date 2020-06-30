package fr.maygo.cite.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.maygo.cite.scoreboard.Scoreboard;
import fr.maygo.cite.sql.SqlConnection;
import fr.maygo.cite.utils.Messages;
import fr.maygo.cite.utils.TabList;

public class CmdSetTeam implements CommandExecutor {
	
	public SqlConnection sql;
	public Scoreboard sb;
	
	public CmdSetTeam(SqlConnection sql, Scoreboard sb2) {
		this.sql = sql;
		this.sb = sb2;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player)sender;
			if(args.length < 2) {
				player.sendMessage("§cUtilisation : /setteam <joueur> <team>");
			}else {
				Player target = Bukkit.getPlayer(args[0]);
				if(target != null) {
					if(player.isOp()) {
						switch (args[1].toLowerCase()) {
						case "lion":
							sql.setTeam(target, "Lion");
							sb.refreshTeam(target);
							TabList.refreshName(target);
							break;
						case "requin":
							sql.setTeam(target, "Requin");
							sb.refreshTeam(target);
							TabList.refreshName(target);
							break;
						case "aigle":
							sql.setTeam(target, "Aigle");
							sb.refreshTeam(target);
							TabList.refreshName(target);
							break;
						default:
							player.sendMessage("§cLa team que vous avez entrez n'existe pas !");
							player.sendMessage("§cListe des teams :");
							player.sendMessage("§c- lion");
							player.sendMessage("§c- requin");
							player.sendMessage("§c- aigle");
							break;
						}
					}else {
						Messages.NOT_PERM.get();
					}
				}else {
					player.sendMessage("§cLe joueur : §4"+args[0]+"§c est introuvable !");
				}
			}	
		}
		return false;
	}

}
