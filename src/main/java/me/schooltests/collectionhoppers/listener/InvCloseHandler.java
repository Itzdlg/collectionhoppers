package me.schooltests.collectionhoppers.listener;

import me.schooltests.collectionhoppers.CollectionHoppers;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InvCloseHandler implements Listener {
    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if(CollectionHoppers.getPlugin().openHoppers.containsKey(event.getPlayer().getUniqueId())) {
            CollectionHoppers.getPlugin().openHoppers.remove(event.getPlayer().getUniqueId());
        }
    }
}
