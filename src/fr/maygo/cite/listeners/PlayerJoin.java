package fr.maygo.cite.listeners;

import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.maygo.cite.Main;
import fr.maygo.cite.scoreboard.Scoreboard;
import fr.maygo.cite.sql.SqlConnection;
import fr.maygo.cite.utils.Inventory;
import fr.maygo.cite.utils.Messages;
import fr.maygo.cite.utils.TabList;
import fr.maygo.cite.utils.Teams;
import fr.maygo.cite.utils.Teleporter;

public class PlayerJoin implements Listener {
	
	public Scoreboard sb;
	public SqlConnection sql;
	public Inventory inv;
	
	public PlayerJoin(Scoreboard sb2,SqlConnection sql2, Inventory inv) {
		sql = sql2;
		sb = sb2;
		this.inv = inv;
	}

	@SuppressWarnings({ "deprecation", "static-access" })
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		sql.createAccount(player);
		OfflinePlayer mostEmeraldsPlayer = sql.getMostEmeraldsPlayer();
		Teams mostEmeraldsTeam = sql.getMostEmeraldsTeam();
		player.setGameMode(GameMode.ADVENTURE);
		event.setJoinMessage(Messages.PREFIX.get()+Teams.getTeam(sql.getTeam(player)).getDisplayName()+player.getName()+"§6 à rejoint la cité !");
		sb.refreshPlayers(0);
		sb.sendScoreboard(player);
		player.sendTitle("§eBienvenue sur", "§6La Cité des rois");
		player.setDisplayName(Teams.getTeam(sql.getTeam(player)).getDisplayName()+player.getName());
		player.setPlayerListName(Teams.getTeam(sql.getTeam(player)).getDisplayName()+player.getName());
		TabList.sendHeaderFooter(player, "§6La cité des rois !\n§3Organisateurs : Maygo & Dedox\n","\n§4© Copyright Cité des rois - 2020 @Maygo");
		inv.saveInventory(player);
		player.getInventory().clear();
		Teleporter.SPAWN.teleport(player);
		inv.addItemTeleporter(player);
		Main.just_joined.add(player);
		player.sendMessage("§4§m---------------------------------------");
		player.sendMessage(Messages.PREFIX.get()+"§4"+mostEmeraldsPlayer.getName()+"§6 est le joueur avec le plus d'émeraudes avec §4"+sql.getMapOfPlayers().get(mostEmeraldsPlayer)+"§6 émeraudes !");
		player.sendMessage(Messages.PREFIX.get()+"§4"+mostEmeraldsTeam.getName()+"§6 est l'équipe avec le plus d'émeraudes avec §4"+sql.getMapOfTeams().get(mostEmeraldsTeam)+"§6 émeraudes !");
		player.sendMessage("§4§m---------------------------------------");
	}
	
}
