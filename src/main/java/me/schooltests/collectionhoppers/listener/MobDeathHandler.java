package me.schooltests.collectionhoppers.listener;

import me.schooltests.collectionhoppers.CollectionHoppers;
import me.schooltests.collectionhoppers.util.CollectionHopper;
import org.bukkit.Chunk;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Arrays;
import java.util.List;

public class MobDeathHandler implements Listener {
    private CollectionHoppers plugin = CollectionHoppers.getPlugin();

    private final List<EntityType> passedMobs = Arrays.asList(EntityType.BAT, EntityType.OCELOT, EntityType.CHICKEN, EntityType.COW, EntityType.HORSE, EntityType.MUSHROOM_COW,
            EntityType.PIG, EntityType.RABBIT, EntityType.SHEEP, EntityType.SQUID, EntityType.VILLAGER, EntityType.WOLF, EntityType.CAVE_SPIDER, EntityType.ENDERMAN, EntityType.SPIDER,
            EntityType.PIG_ZOMBIE, EntityType.BLAZE, EntityType.GUARDIAN, EntityType.ENDERMITE, EntityType.GHAST, EntityType.MAGMA_CUBE, EntityType.SILVERFISH, EntityType.SKELETON, EntityType.SLIME,
            EntityType.WITCH, EntityType.ZOMBIE, EntityType.IRON_GOLEM);

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onMobDeath(EntityDeathEvent event) {
        if(passedMobs.contains(event.getEntityType())) {
            Chunk c = event.getEntity().getLocation().getChunk();
            if(plugin.hasChunk(c)) {
                CollectionHopper col = plugin.getHopperFromChunk(c);
                col.queueItem(event.getDrops());
                event.getDrops().clear();
            }
        }
    }
}
