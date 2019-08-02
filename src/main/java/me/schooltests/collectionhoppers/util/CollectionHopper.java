package me.schooltests.collectionhoppers.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Hopper;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CollectionHopper implements ConfigurationSerializable {
    private Location location;

    public CollectionHopper(Location loc) {
        location = loc;
    }

    public void queueItem(List<ItemStack> items) {
        Block b = location.getWorld().getBlockAt(location);
        if(b.getType().equals(Material.HOPPER)) {
            Hopper hopperBlock = (Hopper) b.getState();
            for (ItemStack xItem : items) {
                if (xItem.getAmount() > 0) {
                    hopperBlock.getInventory().addItem(xItem);
                }
            }
        }
    }

    public Location getLocation() {
        return location;
    }

    public Map<String, Object> serialize() {
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        result.put("location", location);
        return result;
    }

    public static CollectionHopper deserialize(Map<String, Object> args) {
        CollectionHopper hopper = new CollectionHopper((Location) args.get("location"));
        return hopper;
    }
}