package fr.maygo.cite.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import fr.maygo.cite.sql.SqlConnection;
import fr.maygo.cite.utils.Teams;

@SuppressWarnings("deprecation")
public class PlayerChat implements Listener {

	public SqlConnection sql;
	
	public PlayerChat(SqlConnection sql) {
		this.sql = sql;
	}
	
	@EventHandler
	public void onPlayerChat(PlayerChatEvent event) {
		Player player = event.getPlayer();
		event.setFormat(Teams.getTeam(sql.getTeam(player)).getDisplayName()+player.getName()+" §8» §7"+event.getMessage());
	}
	
}
