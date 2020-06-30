package fr.maygo.cite.cmd;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.maygo.cite.sql.SqlConnection;
import fr.maygo.cite.utils.Messages;

public class CmdLastConnection implements CommandExecutor {
	
	public SqlConnection sql;
	
	public CmdLastConnection(SqlConnection sql) {
		this.sql = sql;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(args.length == 0) {
				player.teleport(sql.getLastConnection(player));
				player.sendMessage(Messages.PREFIX.get()+"§6Vous venez de vous téléporter à votre dernière connection, pour le changer faites /sethome");
				player.setGameMode(GameMode.SURVIVAL);
			}else {
				player.sendMessage("§cUtilisation: /home");
			}
		}
		return false;
	}

}
