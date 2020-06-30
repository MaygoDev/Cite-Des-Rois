package fr.maygo.cite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import fr.maygo.cite.cmd.CmdArmorstand;
import fr.maygo.cite.cmd.CmdCite;
import fr.maygo.cite.cmd.CmdClassement;
import fr.maygo.cite.cmd.CmdEmeralds;
import fr.maygo.cite.cmd.CmdEnderChest;
import fr.maygo.cite.cmd.CmdHome;
import fr.maygo.cite.cmd.CmdLastConnection;
import fr.maygo.cite.cmd.CmdMonde;
import fr.maygo.cite.cmd.CmdRegion;
import fr.maygo.cite.cmd.CmdSetHome;
import fr.maygo.cite.cmd.CmdSetTeam;
import fr.maygo.cite.cmd.CmdTpa;
import fr.maygo.cite.cmd.CmdTpaAccept;
import fr.maygo.cite.cmd.CmdVillager;
import fr.maygo.cite.listeners.EntityDamageByEntityEvent;
import fr.maygo.cite.listeners.PlayerBreakBlock;
import fr.maygo.cite.listeners.PlayerChat;
import fr.maygo.cite.listeners.PlayerDamage;
import fr.maygo.cite.listeners.PlayerFood;
import fr.maygo.cite.listeners.PlayerInteract;
import fr.maygo.cite.listeners.PlayerInteractEntity;
import fr.maygo.cite.listeners.PlayerInteractInventory;
import fr.maygo.cite.listeners.PlayerJoin;
import fr.maygo.cite.listeners.PlayerMove;
import fr.maygo.cite.listeners.PlayerPlaceBlock;
import fr.maygo.cite.listeners.PlayerQuit;
import fr.maygo.cite.region.Region;
import fr.maygo.cite.region.RegionFlag;
import fr.maygo.cite.scoreboard.Scoreboard;
import fr.maygo.cite.sql.SqlConnection;
import fr.maygo.cite.utils.Emeralds;
import fr.maygo.cite.utils.Inventory;
import fr.maygo.cite.utils.TabList;
import fr.maygo.cite.utils.Teleporter;
import fr.maygo.cite.utils.Top;

public class Main extends JavaPlugin{
	
	public SqlConnection sql;
	public Scoreboard sb;
	public Inventory inv = new Inventory();
	public TabList tl;
	public Top top = new Top();
	public Emeralds emeralds;
	public PluginManager pm = getServer().getPluginManager();
	public static List<Player> just_joined = new ArrayList<>();
	public List<Region> regions = new ArrayList<>();
	public Map<Player,Location> tpaEnAttente = new HashMap<>();
	
	@SuppressWarnings({ "deprecation", "unused" })
	@Override
	public void onEnable() {
		World worldNether = new WorldCreator(Teleporter.WORLD_NETHER.worldName).createWorld();
		World worldEnd = new WorldCreator(Teleporter.WORLD_END.worldName).createWorld();
		getServer().getWorld("spawn").setDifficulty(Difficulty.PEACEFUL);
		System.out.println("============== Cité des rois ==============");
		System.out.println("[Cité des rois] Connection à la base de donnée sql ...");
		sql = new SqlConnection("jdbc:mysql://","localhost","cite_des_rois","root","");
		sql.connect();
		System.out.println("[Cité des rois] La base de donnée sql à bien été connectée.");
		System.out.println("[Cité des rois] Chargement des utilitaires ...");
		sb = new Scoreboard(sql);
		tl = new TabList(sql);
		emeralds = new Emeralds(sql,sb);
		System.out.println("[Cité des rois] Tous les utilitaires ont bien été chargées.");
		System.out.println("[Cité des rois] Chargement des commandes ...");
		getCommand("emeralds").setExecutor(new CmdEmeralds(sql,sb));
		getCommand("setTeam").setExecutor(new CmdSetTeam(sql,sb));
		getCommand("cite").setExecutor(new CmdCite());
		getCommand("monde").setExecutor(new CmdMonde());
		getCommand("region").setExecutor(new CmdRegion(this));
		getCommand("sethome").setExecutor(new CmdSetHome(sql));
		getCommand("home").setExecutor(new CmdHome(sql));
		getCommand("classement").setExecutor(new CmdClassement(sql));
		getCommand("armorstand").setExecutor(new CmdArmorstand(sql));
		getCommand("villager").setExecutor(new CmdVillager());
		getCommand("ec").setExecutor(new CmdEnderChest());
		getCommand("lc").setExecutor(new CmdLastConnection(sql));
		getCommand("tpa").setExecutor(new CmdTpa(this));
		getCommand("tpaaccept").setExecutor(new CmdTpaAccept(this));
		System.out.println("[Cité des rois] Toutes les commandes ont bien été chargées.");
		System.out.println("[Cité des rois] Chargement des Listeners ...");
		pm.registerEvents(new PlayerJoin(sb,sql,inv), this);
		pm.registerEvents(new PlayerMove(this), this);
		pm.registerEvents(new PlayerBreakBlock(this), this);
		pm.registerEvents(new PlayerPlaceBlock(this), this);
		pm.registerEvents(new PlayerQuit(sb,sql,inv), this);
		pm.registerEvents(new PlayerChat(sql), this);
		pm.registerEvents(new PlayerFood(), this);
		pm.registerEvents(new PlayerInteract(inv,sql), this);
		pm.registerEvents(new PlayerInteractEntity(), this);
		pm.registerEvents(new EntityDamageByEntityEvent(this), this);
		pm.registerEvents(new PlayerInteractInventory(inv,sql,emeralds), this);
		pm.registerEvents(new PlayerDamage(this), this);
		System.out.println("[Cité des rois] Tous les listeners ont bien été chargés.");
		System.out.println("© Copyright Cité des rois - 2020 @Maygo");
		System.out.println("============== Cité des rois ==============");
		Region spawn_monde = new Region("spawn", new Location(Bukkit.getWorld("world"), -3, 90, 0,-90,0), new Location(Bukkit.getWorld("world"), 15, 90, -18,-90,0),
				Arrays.asList(RegionFlag.NO_BUCKET,RegionFlag.NO_BUILD,RegionFlag.NO_PVP,RegionFlag.NO_DAMAGE,RegionFlag.NO_GRIEF));
		regions.add(spawn_monde);
		top.setTopTeamsArmorStand(sql.getMapOfTeams());
		top.setTopPlayersArmorStand(sql.getMapOfPlayers());
		Bukkit.getScheduler().runTaskTimer(this, new BukkitRunnable() {

			@Override
			public void run() {
				top.refreshClassement(sql.getMapOfPlayers(), sql.getMapOfTeams());
				for(Player player : Bukkit.getOnlinePlayers()) {
					sb.refreshEmeralds(player);
					sb.refreshTeam(player);
				}
			}
			
		},0,40);
	}
	
	@Override
	public void onDisable() {
		System.out.println("============== Cité des rois ==============");
		System.out.println("[Cité des rois] Déconnection à la base de donnée sql ...");
		sql.disconnect();
		System.out.println("[Cité des rois] La base de donnée sql à bien été déconnectée.");
		System.out.println("[Cité des rois] Suppression des armors stands ...");
		top.removeArmorStands();
		System.out.println("[Cité des rois] Toutes les armorstands ont étés supprimés !");
		System.out.println("© Copyright Cité des rois - 2020 @Maygo");
		System.out.println("============== Cité des rois ==============");
	}
	
}
