package fr.maygo.cite.utils;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import fr.maygo.cite.sql.SqlConnection;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;

public class TabList {
	
	public static SqlConnection sql;
	
	@SuppressWarnings("static-access")
	public TabList(SqlConnection sql) {
		this.sql = sql;
	}

	public static void sendHeaderFooter(Player player, String header, String footer) {
        IChatBaseComponent tabHeader = ChatSerializer.a("{\"text\": \"" + header + "\"}");
        IChatBaseComponent tabFooter = ChatSerializer.a("{\"text\": \"" + footer + "\"}");
        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
        try
        {
            Field headerField = packet.getClass().getDeclaredField("a");
            headerField.setAccessible(true);
            headerField.set(packet, tabHeader);
            headerField.setAccessible(false);
            Field footerField = packet.getClass().getDeclaredField("b");
            footerField.setAccessible(true);
            footerField.set(packet, tabFooter);
            footerField.setAccessible(false);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
	
	public static void refreshName(Player target) {
		target.setDisplayName(Teams.getTeam(sql.getTeam(target)).getDisplayName()+target.getName());
		target.setPlayerListName(Teams.getTeam(sql.getTeam(target)).getDisplayName()+target.getName());
		Bukkit.broadcastMessage(Messages.PREFIX.get()+"§4"+target.getName()+"§6 est passé dans la team "+Teams.getTeam(sql.getTeam(target)).getDisplayName()+"!");
	}
	
}
