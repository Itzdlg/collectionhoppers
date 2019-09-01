package me.schooltests.collectionhoppers.commands;

import me.schooltests.collectionhoppers.util.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HopperInfoCommand implements CommandExecutor {


	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		Util.tell(sender, " ");
		Util.tell(sender, "&b&L[!] Collection Hopper Information");
		Util.tell(sender,"&7&oHoppers the automatically pickup items from mobs and crops (&a&oSugar Cane & Cactus&7&o).");
		Util.tell(sender, "&7&oIn a chunk (16 x 16 Area). To obtain the wand use \"/shop\" and under miscellaneous.");
		Util.tell(sender, "&7&oHow to use once obtaining the wand left-click a hopper then in the GUI click the wool, ");
		Util.tell(sender, "&7&oand then right-click a single chest on your island.");
		Util.tell(sender, " ");


		return true;
	}

}
