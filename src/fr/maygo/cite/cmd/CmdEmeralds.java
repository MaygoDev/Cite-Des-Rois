package fr.maygo.cite.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.maygo.cite.scoreboard.Scoreboard;
import fr.maygo.cite.sql.SqlConnection;
import fr.maygo.cite.utils.Messages;
import fr.maygo.cite.utils.Teams;

public class CmdEmeralds implements CommandExecutor {
	
	public SqlConnection sql;
	public Scoreboard sb;
	
	public CmdEmeralds(SqlConnection sql2, Scoreboard sb) {
		this.sb = sb;
		this.sql = sql2;
	}


	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(sender.isOp()){
			if(args.length == 4) {
				String targetType = args[0];
				Player target = Bukkit.getPlayer(args[2]);
				int montant = Integer.valueOf(args[3]);
				switch (args[1]) {
				case "add":
					if(targetType.equalsIgnoreCase("joueurs")) {
						if(target != null) {
							sql.addEmeralds(target, montant);
							sql.addEmeraldsTeam(Teams.valueOf(sql.getTeam(target).toUpperCase()), montant);
							sb.refreshEmeralds(target);
						}else {
							sender.sendMessage("§cLe joueur "+args[1]+" est introuvable !");
						}
					}else if(targetType.equalsIgnoreCase("teams")) {
						for(Teams team : Teams.values()) {
							if(args[2].equalsIgnoreCase(team.getName())) {
								sql.addEmeraldsTeam(team, montant);
							}
						}
					}
					break;
				case "remove":
					if(targetType.equalsIgnoreCase("joueurs")) {
						if(target != null) {
							sql.removeEmeralds(target, montant);
							sql.removeEmeraldsTeam(Teams.valueOf(sql.getTeam(target).toUpperCase()), montant);
							sb.refreshEmeralds(target);
						}else {
							sender.sendMessage("§cLe joueur "+args[1]+" est introuvable !");
						}
					}else if(targetType.equalsIgnoreCase("teams")) {
						for(Teams team : Teams.values()) {
							if(args[2].equalsIgnoreCase(team.getName())) {
								sql.removeEmeraldsTeam(team, montant);
							}
						}
					}
					break;
				default:
					sender.sendMessage("§cUtilisation : /emeralds <teams/joueurs> <add/remove/set> <joueur> <montant>");
					break;
				}
			}else {
				sender.sendMessage("§cUtilisation : /emeralds <teams/joueurs> <add/remove/set> <joueur> <montant>");
			}
		}else {
			sender.sendMessage(Messages.NOT_PERM.get());
		}
		return false;
	}

}
