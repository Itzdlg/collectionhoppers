package me.schooltests.collectionhoppers.util;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class Util {
    public static String locationToString(Location loc) {
        // X,Y,Z,P,Y,WUID
        return loc.getX() + "," + loc.getY() + "," + loc.getZ() + "," + loc.getPitch() + "," + loc.getYaw() + "," + loc.getWorld().getUID();
    }

    public static Location stringToLocation(String s) {
        // X,Y,Z,P,Y,WUID
        String[] args = s.split(",");
        return new Location(Bukkit.getWorld(UUID.fromString(args[5])), Double.valueOf(args[0]), Double.valueOf(args[1]), Double.valueOf(args[2]), Float.valueOf(args[3]), Float.valueOf(args[4]));
    }

    //Some extra methods that make life easier
    public static void tell(CommandSender toWhom, String message) {
        toWhom.sendMessage(colorize(message));
    }

    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void log(String messages) {
        tell(Bukkit.getConsoleSender(), "[" + "CollectionHopper" + "] " + messages);
    }

}
