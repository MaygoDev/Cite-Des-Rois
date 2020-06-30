package fr.maygo.cite.scoreboard;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.maygo.cite.sql.SqlConnection;

public class Scoreboard {

	public static Map<Player, ScoreboardSign> boards = new HashMap<>();
	public SqlConnection sql;
	
	public Scoreboard(SqlConnection sql) {
		this.sql = sql;
	}
	
	public void sendScoreboard(Player player) {
		ScoreboardSign sb = new ScoreboardSign(player, "§6La Cité des Rois");
		sb.create();
		sb.setLine(0, "§6Connéctés : §e"+Bukkit.getOnlinePlayers().size()+"/"+Bukkit.getMaxPlayers());
		sb.setLine(1, "§1");
		sb.setLine(2, "§6Team : §e"+sql.getTeam(player));
		sb.setLine(3, "§6Émeraudes : §e"+sql.getEmeralds(player));
		sb.setLine(4, "§6§l➜ §e"+player.getName());
		sb.setLine(5, "§6");
		sb.setLine(6, "§6Saison :§l 0.1");
		boards.put(player, sb);
	}

	public static void removeScoreboard(Player player) {
		if(Scoreboard.boards.containsKey(player)) {
			boards.get(player).destroy();
		}
	}
	
	public static void setNameScoreboard(String name) {
		for(Entry<Player, ScoreboardSign> sign : boards.entrySet()) {
			sign.getValue().setObjectiveName(name);
		}
	}


	public static void refreshPlayers(int nmbr) {
		for(Entry<Player, ScoreboardSign> sign : boards.entrySet()) {
			sign.getValue().setLine(0, "§6Connéctés : §e"+(Bukkit.getOnlinePlayers().size()-nmbr)+"/"+Bukkit.getMaxPlayers());
		}
	}

	public void refreshEmeralds(Player target) {
		int emeralds = sql.getEmeralds(target);
		if(boards.containsKey(target)) {
			boards.get(target).setLine(3, "§6Émeraudes : §e"+emeralds);
		}
	}
	
	public void refreshTeam(Player target) {
		if(boards.containsKey(target)) {
			boards.get(target).setLine(2, "§6Team : §e"+sql.getTeam(target));
		}
	}
	
}
