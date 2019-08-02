package me.schooltests.collectionhoppers.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Hopper;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CollectionHopper implements ConfigurationSerializable {
    private Location location;
    private Location chestLocation = null;

    public CollectionHopper(Location loc) {
        location = loc;
    }

    public void setChestLocation(Location loc) {
        if(loc.getWorld().getBlockAt(loc).getType().equals(Material.CHEST)) {
            chestLocation = loc;
        }
    }

    public void queueItem(List<ItemStack> items) {
        if(chestLocation != null) {
            Block b = chestLocation.getWorld().getBlockAt(chestLocation);
            if (b.getType().equals(Material.CHEST)) {
                Chest chest = (Chest) b.getState();
                for (ItemStack xItem : items) {
                    if (xItem.getAmount() > 0) {
                        chest.getInventory().addItem(xItem);
                    }
                }
            }
        }
    }

    public Location getLocation() {
        return location;
    }

    public Location getChestLocation() {
        return chestLocation;
    }

    public Map<String, Object> serialize() {
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        result.put("location", location);
        if(chestLocation != null) result.put("chestLocation", chestLocation);
        return result;
    }

    public static CollectionHopper deserialize(Map<String, Object> args) {
        CollectionHopper hopper = new CollectionHopper((Location) args.get("location"));
        if(args.containsKey("chestLocation")) hopper.setChestLocation((Location) args.get("chestLocation"));
        return hopper;
    }
}