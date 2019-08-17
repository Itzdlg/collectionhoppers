package me.schooltests.collectionhoppers.listener;

import me.schooltests.collectionhoppers.CollectionHoppers;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakHandler implements Listener {
    CollectionHoppers plugin = CollectionHoppers.getPlugin();
    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if(event.getBlock().getType().equals(Material.HOPPER)) {
            if(plugin.hasChunk(event.getBlock().getChunk())) {
                if(plugin.getHopperFromChunk(event.getBlock().getChunk()).getLocation().equals(event.getBlock().getLocation())) {
                    plugin.removeChunk(event.getBlock().getChunk());
                    event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bCollection Hoppers&8] &bBroken Collection Hopper in this chunk"));
                }
            }
        }
    }
}
