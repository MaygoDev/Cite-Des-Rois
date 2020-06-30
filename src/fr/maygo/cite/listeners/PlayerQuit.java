package fr.maygo.cite.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.maygo.cite.Main;
import fr.maygo.cite.scoreboard.Scoreboard;
import fr.maygo.cite.sql.SqlConnection;
import fr.maygo.cite.utils.Inventory;
import fr.maygo.cite.utils.Messages;
import fr.maygo.cite.utils.Teams;

public class PlayerQuit implements Listener {

	public Scoreboard sb;
	public SqlConnection sql;
	public Inventory inv;
	
	public PlayerQuit(Scoreboard sb,SqlConnection sql,Inventory inv) {
		this.sql = sql;
		this.sb = sb;
		this.inv = inv;
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		event.setQuitMessage(Messages.PREFIX.get()+Teams.getTeam(sql.getTeam(player)).getDisplayName()+player.getName()+"§6 à quitté la cité !");
		Scoreboard.refreshPlayers(1);
		Scoreboard.removeScoreboard(player);
		if(Main.just_joined.contains(player)) {
			inv.getInventory(player);
		}
		if(player.getWorld().getName().equalsIgnoreCase("world")) {
			sql.setLastConnection(player, player.getLocation());
		}
	}
	
}
