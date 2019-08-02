package me.schooltests.collectionhoppers.commands;

import me.schooltests.collectionhoppers.CollectionHoppers;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class CommandGetHopperWand implements CommandExecutor {
    CollectionHoppers plugin = CollectionHoppers.getPlugin();

    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            ItemStack wand = new ItemStack(Material.STICK, 1);
            ItemMeta meta = wand.getItemMeta();
            meta.setDisplayName(ChatColor.AQUA + "Collection Hopper Wand");
            meta.setLore(Arrays.asList(ChatColor.AQUA + "Right click this on a " + ChatColor.WHITE + "HOPPER", ChatColor.AQUA + "to setup a collection hopper!"));
            wand.setItemMeta(meta);

            if(args.length > 0) {
                wand.setAmount(NumberUtils.toInt(args[0], 1));
            }

            player.getInventory().addItem(wand);
        } else {
            ItemStack wand = new ItemStack(Material.STICK, 1);
            ItemMeta meta = wand.getItemMeta();
            meta.setDisplayName(ChatColor.AQUA + "Collection Hopper Wand");
            meta.setLore(Arrays.asList(ChatColor.AQUA + "Right click this on a " + ChatColor.WHITE + "HOPPER", ChatColor.AQUA + "to setup a collection hopper!"));
            wand.setItemMeta(meta);

            if(args.length > 0) {
                wand.setAmount(NumberUtils.toInt(args[1], 1));
                Player target = Bukkit.getPlayer(args[0]);
                if(target != null && target.isOnline()) {
                    target.getInventory().addItem(wand);
                }
            }
        }

        return true;
    }
}
