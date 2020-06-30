package fr.maygo.cite.cmd;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.maygo.cite.sql.SqlConnection;
import fr.maygo.cite.utils.Messages;

public class CmdSetHome implements CommandExecutor {

	public SqlConnection sql;
	
	public CmdSetHome(SqlConnection sql) {
		this.sql = sql;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(args.length == 0) {
				Location loc = player.getLocation();
				sql.setHome(player, loc);
				player.sendMessage("§4§m---------------------------------------");
				player.sendMessage(Messages.PREFIX.get()+"§6Votre home est maintenant en : ");
				player.sendMessage("§6X : §e"+new Double(loc.getX()).intValue());
				player.sendMessage("§6Y : §e"+new Double(loc.getY()).intValue());
				player.sendMessage("§6Z : §e"+new Double(loc.getZ()).intValue());
				player.sendMessage(Messages.PREFIX.get()+"§6Pour vous y téléporter, faites /home");
				player.sendMessage("§4§m---------------------------------------");
			}else {
				player.sendMessage("§cUtilisation: /sethome");
			}
		}
		return false;
	}

}
