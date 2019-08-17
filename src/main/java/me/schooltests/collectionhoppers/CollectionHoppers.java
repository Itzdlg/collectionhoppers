package me.schooltests.collectionhoppers;

import me.schooltests.collectionhoppers.commands.CommandGetHopperWand;
import me.schooltests.collectionhoppers.listener.*;
import me.schooltests.collectionhoppers.util.CollectionHopper;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.*;

public class CollectionHoppers extends JavaPlugin {
    private static CollectionHoppers plugin;
    public Map<UUID, CollectionHopper> linkingHoppers = new HashMap<UUID, CollectionHopper>();
    public Map<UUID, CollectionHopper> openHoppers = new HashMap<UUID, CollectionHopper>();
    public Map<String, CollectionHopper> collectionHoppers = new HashMap<String, CollectionHopper>();
    public YamlConfiguration config = new YamlConfiguration();

    static {
        ConfigurationSerialization.registerClass(CollectionHopper.class);
    }

    @Override
    public void onEnable() {
        this.plugin = this;

        try {
            File file = new File(getDataFolder(), "config.yml");
            if(!file.exists()) {
                file.getParentFile().mkdirs();
                saveResource("config.yml", false);
            }

            config.load(file);

            if(config.contains("data")) {
                loadData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        getCommand("getHopperWand").setExecutor(new CommandGetHopperWand());
        getServer().getPluginManager().registerEvents(new InteractHandler(), this);
        getServer().getPluginManager().registerEvents(new BlockBreakHandler(), this);
        getServer().getPluginManager().registerEvents(new MobDeathHandler(), this);
        getServer().getPluginManager().registerEvents(new InvClickHandler(), this);
        getServer().getPluginManager().registerEvents(new InvCloseHandler(), this);

        new BukkitRunnable() {
            public void run() {
                saveData();
                for(Player plr : Bukkit.getOnlinePlayers()) {
                    if(plr.hasPermission("collectionhoppers.save")) {
                        plr.sendMessage(ChatColor.GREEN + "Collection Hopper data saved!");
                        plr.getWorld().playSound(plr.getLocation(), Sound.NOTE_PLING, 50, 1);
                    }
                }
            }
        }.runTaskTimer(this, 54000, 54000);
    }

    @Override
    public void onDisable() {
        //File file = new File(getDataFolder(), "config.yml");
        //config.set("data", collectionHoppers);
        saveData();

        getServer().getScheduler().cancelTasks(this);
    }

    public static CollectionHoppers getPlugin() {
        return plugin;
    }
    // Serialize my chunk
    private String chunkAsString(Chunk c) {
        String x = String.valueOf(c.getX());
        String z = String.valueOf(c.getZ());
        String uid = String.valueOf(c.getWorld().getUID());
        String ret = x + "," + z + "," + uid;
        return ret;
    }
    // Get hopper from chunk
    public CollectionHopper getHopperFromChunk(Chunk c) {
        return collectionHoppers.get(chunkAsString(c));
    }
    // Add a hopper with a chunk
    public void putChunk(Chunk c, CollectionHopper hopper) {
        collectionHoppers.put(chunkAsString(c), hopper);
    }
    // Remove a chunk's hopper
    public void removeChunk(Chunk c) {
        collectionHoppers.remove(chunkAsString(c));
    }
    // Save me
    private void saveData() {
        try {
            File file = new File(getDataFolder(), "config.yml");
            config.set("data", null);
            for(String key : collectionHoppers.keySet()) {
                config.set("data." + key, collectionHoppers.get(key));
            }

            config.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Load me
    private void loadData() {
        try {
            for(String key : config.getConfigurationSection("data").getKeys(true)) {
                collectionHoppers.put(key, (CollectionHopper) config.get("data." + key));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // do i have a hopper at this chunk
    public boolean hasChunk(Chunk c) {
        return collectionHoppers.containsKey(chunkAsString(c));
    }
}
