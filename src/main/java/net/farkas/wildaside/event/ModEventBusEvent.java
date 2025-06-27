package net.farkas.wildaside.event;


import net.farkas.wildaside.WildAside;
import net.farkas.wildaside.attachments.ModAttachments;
import net.farkas.wildaside.attachments.contamination.ContaminationImplementation;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@Mod.EventBusSubscriber(modid = WildAside.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvent {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.MUCELLITH.get(), MucellithEntity.createAttributes().build());
        event.put(ModEntities.HICKORY_TREANT.get(), HickoryTreantEntity.createAttributes().build());
    }

}
