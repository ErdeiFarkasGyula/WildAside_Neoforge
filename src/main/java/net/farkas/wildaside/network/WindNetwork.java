package net.farkas.wildaside.network;

import net.farkas.wildaside.WildAside;
import net.farkas.wildaside.client.ClientWindData;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = WildAside.MOD_ID)
public class WindNetwork {
    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        registrar.playToClient(
                WindPayload.TYPE,
                WindPayload.STREAM_CODEC,
                (WindPayload payload, IPayloadContext ctx) -> {
                    ClientWindData.setWind(new Vec3(payload.x(), payload.y(), payload.z()), payload.strength());
                }
        );
    }
}