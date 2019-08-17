package me.schooltests.collectionhoppers.listener;

import me.schooltests.collectionhoppers.CollectionHoppers;
import me.schooltests.collectionhoppers.util.CollectionHopper;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.inventory.ItemStack;

public class CropGrowthListener implements Listener {

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public final void onCropGrowth(BlockGrowEvent event){

		CollectionHoppers plugin = CollectionHoppers.getPlugin();
		final Chunk c = event.getBlock().getLocation().getChunk();
		final Block block = event.getNewState().getBlock();

		//ItemStack for the cactus and sugar cane
		final ItemStack cactus = new ItemStack(Material.CACTUS);
		final ItemStack sugarCane = new ItemStack(Material.SUGAR_CANE);

		//Tests to see if the block below is cactus or sugar cane before transfering the cactus or sugarcane
		final Location belowBlock = new Location(block.getWorld(), block.getX(), block.getY() - 1, block.getZ());

		if(!plugin.hasChunk(c))
			return;

		CollectionHopper col = plugin.getHopperFromChunk(c);
		if(belowBlock.getBlock().getType().equals(Material.CACTUS)) {
				event.setCancelled(true);
				col.queueItem(cactus);
		}else if(belowBlock.getBlock().getType().equals(Material.SUGAR_CANE_BLOCK)){
				event.setCancelled(true);
				col.queueItem(sugarCane);
		}

	}

}
