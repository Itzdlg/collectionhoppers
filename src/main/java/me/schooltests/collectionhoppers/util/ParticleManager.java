package me.schooltests.collectionhoppers.util;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ParticleManager {
    public static PacketPlayOutWorldParticles Create(EnumParticle type, Location l, Integer radius, Integer speed, Integer amount) {
        return new PacketPlayOutWorldParticles(
                type,
                true,
                l.getBlockX(),
                l.getBlockY(),
                l.getBlockZ(),
                radius.intValue(),
                radius.intValue(),
                radius.intValue(),
                speed.intValue(),
                amount.intValue(),
                null);
    }



    public static void Play(PacketPlayOutWorldParticles packet, Player player) { (((CraftPlayer)player).getHandle()).playerConnection.sendPacket(packet); }
}
