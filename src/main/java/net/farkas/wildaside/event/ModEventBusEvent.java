package net.farkas.wildaside.event;


import net.farkas.wildaside.WildAside;
import net.farkas.wildaside.entity.ModEntities;
import net.farkas.wildaside.entity.client.ModModelLayers;
import net.farkas.wildaside.entity.client.vibrion.MucellithModel;
import net.farkas.wildaside.entity.custom.hickory.HickoryTreantEntity;
import net.farkas.wildaside.entity.custom.vibrion.MucellithEntity;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = WildAside.MOD_ID)
public class ModEventBusEvent {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.MUCELLITH.get(), MucellithEntity.createAttributes().build());
        event.put(ModEntities.HICKORY_TREANT.get(), HickoryTreantEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.SUBSTILIUM_BOAT_LAYER, BoatModel::createBodyModel);
        event.registerLayerDefinition(ModModelLayers.SUBSTILIUM_CHEST_BOAT_LAYER, ChestBoatModel::createBodyModel);
        event.registerLayerDefinition(ModModelLayers.HICKORY_BOAT_LAYER, BoatModel::createBodyModel);
        event.registerLayerDefinition(ModModelLayers.HICKORY_CHEST_BOAT_LAYER, ChestBoatModel::createBodyModel);

        event.registerLayerDefinition(ModModelLayers.MUCELLITH_LAYER, MucellithModel::createBodyLayer);
    }
}
