package me.schooltests.collectionhoppers.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;

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
}
