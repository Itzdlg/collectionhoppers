package me.schooltests.collectionhoppers.listener;

import me.schooltests.collectionhoppers.CollectionHoppers;
import me.schooltests.collectionhoppers.util.CollectionHopper;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InvClickHandler implements Listener {
    private CollectionHoppers plugin = CollectionHoppers.getPlugin();
    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if(event.getClickedInventory() != null) {
            if(event.getCurrentItem() != null) {
                if(event.getClickedInventory().getTitle().equals(ChatColor.BLUE + "Collection Hopper")) {
                    if(!plugin.linkingHoppers.containsKey(event.getWhoClicked().getUniqueId())) {
                        if (event.getCurrentItem().getType().equals(Material.WOOL)) {
                            event.setCancelled(true);
                            plugin.linkingHoppers.put(player.getUniqueId(), plugin.openHoppers.get(player.getUniqueId()));
                            player.closeInventory();
                            plugin.openHoppers.remove(player.getUniqueId());
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bCollection Hoppers&8] &bRight click a chest to link!"));
                        } else if(event.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE)) {
                            event.setCancelled(true);
                        }
                    } else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bCollection Hoppers&8] &cYou are already in link mode!"));
                    }
                }
            }
        }
    }
}
