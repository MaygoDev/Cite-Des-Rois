package fr.maygo.cite.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import fr.maygo.cite.utils.Teams;
import fr.maygo.cite.utils.Teleporter;

public class SqlConnection {

	private Connection connection;

	private String urlbase;
	private String host;
	private String database;
	private String user;
	private String mdp;

	public SqlConnection(String urlbase, String host, String database, String user, String mdp) {
		this.urlbase = urlbase;
		this.host = host;
		this.database = database;
		this.user = user;
		this.mdp = mdp;
	}

	public void connect() {
		if (!isConnected()) {
			try {
				connection = DriverManager.getConnection(urlbase + host + "/" + database, user, mdp);
				System.out.println("[Cité des rois] Sql Connected with succes !");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void disconnect() {
		if (isConnected()) {
			try {
				connection.close();
				System.out.println("[Cité des rois] Sql Disconnected with succes !");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public boolean isConnected() {
		return connection != null;
	}

	public void createAccount(Player player) {
		if (!hasAccount(player)) {
			try {
				PreparedStatement q = connection
						.prepareStatement("INSERT INTO joueurs(uuid,team,emeralds) VALUES (?,?,?)");
				q.setString(1, player.getUniqueId().toString());
				q.setString(2, "Indéfini");
				q.setInt(3, 0);
				q.execute();
				q.close();

				PreparedStatement q1 = connection
						.prepareStatement("INSERT INTO last_connection(uuid,x,y,z) VALUES (?,?,?,?)");
				q1.setString(1, player.getUniqueId().toString());
				q1.setInt(2, 6);
				q1.setInt(3, 92);
				q1.setInt(4, -9);
				q1.execute();
				q1.close();

				PreparedStatement q2 = connection.prepareStatement("INSERT INTO homes(uuid,x,y,z) VALUES (?,?,?,?)");
				q2.setString(1, player.getUniqueId().toString());
				q2.setInt(2, 6);
				q2.setInt(3, 92);
				q2.setInt(4, -9);
				q2.execute();
				q2.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean hasAccount(Player player) {

		try {
			PreparedStatement q = connection.prepareStatement("SELECT uuid FROM joueurs WHERE uuid = ?");
			q.setString(1, player.getUniqueId().toString());
			ResultSet resultat = q.executeQuery();
			boolean hasAccount = resultat.next();
			q.close();

			PreparedStatement q1 = connection.prepareStatement("SELECT uuid FROM last_connection WHERE uuid = ?");
			q1.setString(1, player.getUniqueId().toString());
			ResultSet resultat1 = q1.executeQuery();
			boolean hasAccount1 = resultat1.next();
			q1.close();

			PreparedStatement q2 = connection.prepareStatement("SELECT uuid FROM homes WHERE uuid = ?");
			q2.setString(1, player.getUniqueId().toString());
			ResultSet resultat2 = q2.executeQuery();
			boolean hasAccount2 = resultat2.next();
			q2.close();

			if (hasAccount == true && hasAccount1 == true && hasAccount2 == true) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public int getEmeralds(Player player) {
		try {
			PreparedStatement q = connection.prepareStatement("SELECT emeralds FROM joueurs WHERE uuid = ?");
			q.setString(1, player.getUniqueId().toString());

			int balance = 0;
			ResultSet rs = q.executeQuery();

			while (rs.next()) {
				balance = rs.getInt("emeralds");
			}

			q.close();

			return balance;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}
	
	public int getEmeraldsTeam(Teams team) {
		try {
			PreparedStatement q = connection.prepareStatement("SELECT emeralds FROM teams WHERE team = ?");
			q.setString(1, team.getName().toLowerCase());

			int balance = 0;
			ResultSet rs = q.executeQuery();

			while (rs.next()) {
				balance = rs.getInt("emeralds");
			}

			q.close();

			return balance;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	public Map<OfflinePlayer, Integer> getMapOfPlayers() {
		Map<OfflinePlayer, Integer> players = new HashMap<>();
		try {
			PreparedStatement q = connection.prepareStatement("SELECT * FROM joueurs");

			ResultSet rs = q.executeQuery();

			while (rs.next()) {
				players.put(Bukkit.getOfflinePlayer(UUID.fromString(rs.getString("uuid"))), rs.getInt("emeralds"));
			}
			q.close();
			return players;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public Map<Teams, Integer> getMapOfTeams() {
		Map<Teams, Integer> teams = new HashMap<>();
		try {
			PreparedStatement q = connection.prepareStatement("SELECT * FROM teams");

			ResultSet rs = q.executeQuery();

			while (rs.next()) {
				teams.put(Teams.valueOf(rs.getString("team").toUpperCase()), rs.getInt("emeralds"));
			}
			q.close();
			return teams;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public OfflinePlayer getMostEmeraldsPlayer() {
		Map<OfflinePlayer, Integer> players = getMapOfPlayers();

		int maxValueInMap = (Collections.max(players.values()));
		for (Entry<OfflinePlayer, Integer> entry : players.entrySet()) {
			if (Objects.equals(maxValueInMap, entry.getValue())) {
				return entry.getKey();
			}
		}

		return null;
	}
	
	public Teams getMostEmeraldsTeam() {
		Map<Teams, Integer> teams = getMapOfTeams();

		int maxValueInMap = (Collections.max(teams.values()));
		for (Entry<Teams, Integer> entry : teams.entrySet()) {
			if (Objects.equals(maxValueInMap, entry.getValue())) {
				return entry.getKey();
			}
		}

		return null;
	}

	public void addEmeralds(Player player, int amount) {
		int balance = getEmeralds(player);
		int newbalance = balance + amount;

		try {
			PreparedStatement rs = connection.prepareStatement("UPDATE joueurs SET emeralds = ? WHERE uuid = ?");
			rs.setInt(1, newbalance);
			rs.setString(2, player.getUniqueId().toString());
			rs.executeUpdate();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void removeEmeralds(Player player, int amount) {
		int balance = getEmeralds(player);
		int newbalance = balance - amount;

		if (newbalance <= 0) {
			return;
		}

		try {
			PreparedStatement rs = connection.prepareStatement("UPDATE joueurs SET emeralds = ? WHERE uuid = ?");
			rs.setInt(1, newbalance);
			rs.setString(2, player.getUniqueId().toString());
			rs.executeUpdate();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addEmeraldsTeam(Teams team, int amount) {
		int balance = getEmeraldsTeam(team);
		int newbalance = balance + amount;

		try {
			PreparedStatement rs = connection.prepareStatement("UPDATE teams SET emeralds = ? WHERE team = ?");
			rs.setInt(1, newbalance);
			rs.setString(2, team.getName().toLowerCase());
			rs.executeUpdate();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void removeEmeraldsTeam(Teams team, int amount) {
		int balance = getEmeraldsTeam(team);
		int newbalance = balance - amount;

		if (newbalance <= 0) {
			return;
		}

		try {
			PreparedStatement rs = connection.prepareStatement("UPDATE teams SET emeralds = ? WHERE team = ?");
			rs.setInt(1, newbalance);
			rs.setString(2, team.getName().toLowerCase());
			rs.executeUpdate();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getTeam(Player player) {
		try {
			PreparedStatement q = connection.prepareStatement("SELECT team FROM joueurs WHERE uuid = ?");
			q.setString(1, player.getUniqueId().toString());

			String team = "";
			ResultSet rs = q.executeQuery();

			while (rs.next()) {
				team = rs.getString("team");
			}

			q.close();

			return team;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}

	public void setTeam(Player player, String team) {
		try {
			PreparedStatement rs = connection.prepareStatement("UPDATE joueurs SET team = ? WHERE uuid = ?");
			rs.setString(1, team);
			rs.setString(2, player.getUniqueId().toString());
			rs.executeUpdate();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Location getLastConnection(Player player) {
		try {
			PreparedStatement q = connection.prepareStatement("SELECT x, y, z FROM last_connection WHERE uuid = ?");
			q.setString(1, player.getUniqueId().toString());

			Location loc = new Location(Teleporter.WORLD.getWorld(), 6, 92, -9);
			ResultSet rs = q.executeQuery();

			while (rs.next()) {
				loc.setX(rs.getInt("x"));
				loc.setY(rs.getInt("y"));
				loc.setZ(rs.getInt("z"));
			}

			q.close();

			return loc;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setLastConnection(Player player, Location loc) {
		try {
			PreparedStatement rs = connection
					.prepareStatement("UPDATE last_connection SET x = ? , y = ?, z = ? WHERE uuid = ?");
			rs.setInt(1, new Double(loc.getX()).intValue());
			rs.setInt(2, new Double(loc.getY()).intValue());
			rs.setInt(3, new Double(loc.getZ()).intValue());
			rs.setString(4, player.getUniqueId().toString());
			rs.executeUpdate();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Location getHome(Player player) {
		try {
			PreparedStatement q = connection.prepareStatement("SELECT x, y, z FROM homes WHERE uuid = ?");
			q.setString(1, player.getUniqueId().toString());

			Location loc = new Location(Teleporter.WORLD.getWorld(), 6, 92, -9);
			ResultSet rs = q.executeQuery();

			while (rs.next()) {
				loc.setX(rs.getInt("x"));
				loc.setY(rs.getInt("y"));
				loc.setZ(rs.getInt("z"));
			}

			q.close();

			return loc;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setHome(Player player, Location loc) {
		try {
			PreparedStatement rs = connection.prepareStatement("UPDATE homes SET x = ? , y = ?, z = ? WHERE uuid = ?");
			rs.setInt(1, new Double(loc.getX()).intValue());
			rs.setInt(2, new Double(loc.getY()).intValue());
			rs.setInt(3, new Double(loc.getZ()).intValue());
			rs.setString(4, player.getUniqueId().toString());
			rs.executeUpdate();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}