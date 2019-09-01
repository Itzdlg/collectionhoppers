package me.schooltests.collectionhoppers.listener;

import me.schooltests.collectionhoppers.CollectionHoppers;
import me.schooltests.collectionhoppers.util.CollectionHopper;
import me.schooltests.collectionhoppers.util.ParticleManager;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.*;
import org.bukkit.block.Chest;
import org.bukkit.block.Hopper;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class InteractHandler implements Listener {
    private CollectionHoppers plugin = CollectionHoppers.getPlugin();
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if(event.getClickedBlock().getType().equals(Material.HOPPER)) {
                if (event.getItem() != null && event.getItem().getItemMeta() != null && event.getItem().getItemMeta().getLore() != null) {
                    if (event.getItem().getItemMeta().getLore().equals(Arrays.asList(ChatColor.AQUA + "Left click this on a " + ChatColor.WHITE + "HOPPER", ChatColor.AQUA + "to setup a collection hopper!"))) {
                        //if (!plugin.collectionHoppers.containsKey(event.getClickedBlock().getChunk())) {
                        if(!plugin.hasChunk(event.getClickedBlock().getChunk())) {
                            CollectionHopper col = new CollectionHopper(event.getClickedBlock().getLocation());
                            plugin.putChunk(event.getClickedBlock().getChunk(), col);
                            ItemStack i = event.getItem();
                            int newAmount = i.getAmount() - 1;
                            i.setAmount(newAmount);
                            event.getPlayer().getInventory().setItem(event.getPlayer().getInventory().getHeldItemSlot(), i);
                            final Location loc = event.getClickedBlock().getLocation().add(0, 1, 0);
                            final Player player = event.getPlayer();
                            final BukkitTask task = new BukkitRunnable() {
                                public void run() {
                                    for (Player xplayer : Bukkit.getServer().getOnlinePlayers()) {
                                        if (xplayer.getWorld().equals(player.getWorld())) {
                                            ParticleManager.Play(ParticleManager.Create(EnumParticle.REDSTONE, loc, 1, 1, 15), xplayer);
                                        }
                                    }
                                }
                            }.runTaskTimerAsynchronously(plugin, 0L, 10L);

                            new BukkitRunnable() {
                                public void run() {
                                    task.cancel();
                                }
                            }.runTaskLater(plugin, 100L);

                            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bCollection Hoppers&8] &bCreated Collection Hopper for this chunk"));
                        } else {
                            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bCollection Hoppers&8] &bYou already have a collection hopper in this chunk!"));
                        }
                    }
                }
            }
        } else if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if(event.getClickedBlock().getType().equals(Material.HOPPER)) {
                if(plugin.hasChunk(event.getClickedBlock().getChunk())) {
                    if(plugin.getHopperFromChunk(event.getClickedBlock().getChunk()).getLocation().equals(event.getClickedBlock().getLocation())) {
                        event.setCancelled(true);
                        CollectionHopper hop = plugin.getHopperFromChunk(event.getClickedBlock().getChunk());
                        plugin.openHoppers.put(event.getPlayer().getUniqueId(), hop);
                        Inventory inv = Bukkit.createInventory(null, 9, ChatColor.BLUE + "Collection Hopper");
                        ItemStack pane = new ItemStack(Material.STAINED_GLASS_PANE, 1);
                        pane.setDurability((short) 7);
                        for(int i = 0; i < 9; i++) {
                            inv.setItem(i, pane);
                        }

                        ItemStack linkHopper = new ItemStack(Material.WOOL, 1);
                        ItemMeta meta = linkHopper.getItemMeta();
                        meta.setDisplayName(ChatColor.BOLD + "" + ChatColor.GREEN + "Link Single Chest");
                        linkHopper.setItemMeta(meta);
                        linkHopper.setDurability((short) 3);

                        inv.setItem(4, linkHopper);
                        event.getPlayer().openInventory(inv);
                    }
                }
            } else if(event.getClickedBlock().getType().equals(Material.CHEST)) {
                if (plugin.linkingHoppers.containsKey(event.getPlayer().getUniqueId())) {
                    event.setCancelled(true);
                    CollectionHopper hopper = plugin.linkingHoppers.get(event.getPlayer().getUniqueId());
                    hopper.setChestLocation(event.getClickedBlock().getLocation());
                    plugin.putChunk(hopper.getLocation().getChunk(), hopper);
                    plugin.linkingHoppers.remove(event.getPlayer().getUniqueId());
                    event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bCollection Hoppers&8] &bLinked collection hopper to chest!"));
                }
            }
        }
    }
}
