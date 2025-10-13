package net.farkas.wildaside.network;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

public class NetworkHandler {
    public static void sendWindUpdateToAll(Vec3 dir, float strength) {
        var payload = WindPayload.fromWindData(new WindData(dir.normalize(), strength));
        PacketDistributor.sendToAllPlayers(payload);
    }

    public static void sendWindUpdateTo(ServerPlayer player, Vec3 dir, float strength) {
        var payload = WindPayload.fromWindData(new WindData(dir.normalize(), strength));
        PacketDistributor.sendToPlayer(player, payload);
    }
}