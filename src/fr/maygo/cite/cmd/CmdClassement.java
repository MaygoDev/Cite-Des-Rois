package fr.maygo.cite.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.maygo.cite.sql.SqlConnection;
import fr.maygo.cite.utils.Top;

public class CmdClassement implements CommandExecutor {

	public SqlConnection sql;
	public Top top = new Top();
	
	public  CmdClassement(SqlConnection sql) {
		this.sql = sql;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("joueurs")) {
				top.getTopPlayer((Player)sender, sql.getMapOfPlayers());
			}else if(args[0].equalsIgnoreCase("teams")) {
				top.getTopTeams((Player)sender, sql.getMapOfTeams());
			}
		}
		return false;
	}

}
