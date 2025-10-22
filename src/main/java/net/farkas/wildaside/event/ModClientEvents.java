package net.farkas.wildaside.event;

import net.farkas.wildaside.WildAside;
import net.farkas.wildaside.block.ModBlocks;
import net.farkas.wildaside.block.entity.ModBlockEntities;
import net.farkas.wildaside.entity.ModEntities;
import net.farkas.wildaside.entity.client.ModBoatRenderer;
import net.farkas.wildaside.entity.client.ModModelLayers;
import net.farkas.wildaside.entity.client.hickory.HickoryTreantRenderer;
import net.farkas.wildaside.entity.client.vibrion.MucellithModel;
import net.farkas.wildaside.entity.client.vibrion.MucellithRenderer;
import net.farkas.wildaside.entity.custom.vibrion.SporeArrowEntity;
import net.farkas.wildaside.particle.ModParticles;
import net.farkas.wildaside.particle.custom.*;
import net.farkas.wildaside.screen.ModMenuTypes;
import net.farkas.wildaside.screen.bioengineering_workstation.BioengineeringWorkstationScreen;
import net.farkas.wildaside.screen.potion_blaster.PotionBlasterScreen;
import net.farkas.wildaside.util.ModWoodTypes;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = WildAside.MOD_ID, value = Dist.CLIENT)
public class ModClientEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.SUBSTILIUM_BOAT_LAYER, BoatModel::createBodyModel);
        event.registerLayerDefinition(ModModelLayers.SUBSTILIUM_CHEST_BOAT_LAYER, ChestBoatModel::createBodyModel);
        event.registerLayerDefinition(ModModelLayers.HICKORY_BOAT_LAYER, BoatModel::createBodyModel);
        event.registerLayerDefinition(ModModelLayers.HICKORY_CHEST_BOAT_LAYER, ChestBoatModel::createBodyModel);

        event.registerLayerDefinition(ModModelLayers.MUCELLITH_LAYER, MucellithModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        Sheets.addWoodType(ModWoodTypes.SUBSTILIUM);
        Sheets.addWoodType(ModWoodTypes.HICKORY);
        Sheets.addWoodType(ModWoodTypes.CYPRESS);

        EntityRenderers.register(ModEntities.MOD_BOAT.get(), pContext -> new ModBoatRenderer(pContext, false));
        EntityRenderers.register(ModEntities.MOD_CHEST_BOAT.get(), pContext -> new ModBoatRenderer(pContext, true));
        EntityRenderers.register(ModEntities.SPORE_BOMB.get(), ThrownItemRenderer::new);
        EntityRenderers.register(ModEntities.FERTILISER_BOMB.get(), ThrownItemRenderer::new);
        EntityRenderers.register(ModEntities.SPORE_ARROW.get(),pContext -> new ArrowRenderer<SporeArrowEntity>(pContext) {
            @Override
            public ResourceLocation getTextureLocation(SporeArrowEntity pEntity) {
                return ResourceLocation.fromNamespaceAndPath(WildAside.MOD_ID, "textures/entity/projectiles/spore_arrow.png");
            }
        });
        EntityRenderers.register(ModEntities.MUCELLITH.get(), MucellithRenderer::new);

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.VIBRION_GLASS_PANE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.LIT_VIBRION_GLASS_PANE.get(), RenderType.translucent());

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.FALLEN_HICKORY_LEAVES.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.HICKORY_ROOT_BUSH.get(), RenderType.cutout());
    }

    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.ENTORIUM_PARTICLE.get(), EntoriumParticle.Provider::new);
        event.registerSpriteSet(ModParticles.SUBSTILIUM_PARTICLE.get(), SubstiliumParticle.Provider::new);
        event.registerSpriteSet(ModParticles.STILL_SUBSTILIUM_PARTICLE.get(), StillSubstiliumParticle.Provider::new);
        event.registerSpriteSet(ModParticles.LIFESTEAL_PARTICLE.get(), LifestealParticle.Provider::new);
        event.registerSpriteSet(ModParticles.VIBRION_PARTICLE.get(), VibrionParticle.Provider::new);
        event.registerSpriteSet(ModParticles.VIBRION_DRIP_PARTICLE.get(), ModDripParticle.Provider::new);
        event.registerSpriteSet(ModParticles.HICKORY_LEAF_PARTICLE.get(), FallingHickoryLeafParticle.Provider::new);
        event.registerSpriteSet(ModParticles.RED_GLOWING_HICKORY_LEAF_PARTICLE.get(), FallingHickoryLeafParticle.Provider::new);
        event.registerSpriteSet(ModParticles.BROWN_GLOWING_HICKORY_LEAF_PARTICLE.get(), FallingHickoryLeafParticle.Provider::new);
        event.registerSpriteSet(ModParticles.YELLOW_GLOWING_HICKORY_LEAF_PARTICLE.get(), FallingHickoryLeafParticle.Provider::new);
        event.registerSpriteSet(ModParticles.GREEN_GLOWING_HICKORY_LEAF_PARTICLE.get(), FallingHickoryLeafParticle.Provider::new);
        event.registerSpriteSet(ModParticles.WIND_PARTICLE.get(), WindParticle.SmallProvider::new);
    }

    @SubscribeEvent
    public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.MOD_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.MOD_HANGING_SIGN.get(), HangingSignRenderer::new);
        event.registerEntityRenderer(ModEntities.HICKORY_LEAF_PROJECTILE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntities.HICKORY_TREANT.get(), HickoryTreantRenderer::new);
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.BIOENGINEERING_WORKSTATION_MENU.get(), BioengineeringWorkstationScreen::new);
        event.register(ModMenuTypes.POTION_BLASTER_MENU.get(), PotionBlasterScreen::new);
    }

    @SubscribeEvent
    public static void registerColoredBlocks(RegisterColorHandlersEvent.Block event) {
        event.register((pState, pLevel, pPos, pTintIndex) -> pLevel != null &&
                        pPos != null ? BiomeColors.getAverageFoliageColor(pLevel, pPos) : FoliageColor.getDefaultColor(),
                ModBlocks.HICKORY_LEAVES.get(), ModBlocks.FALLEN_HICKORY_LEAVES.get());
    }

    @SubscribeEvent
    public static void registerColoredItems(RegisterColorHandlersEvent.Item event) {
        event.register((pStack, pTintIndex) -> {
            BlockState state = ((BlockItem)pStack.getItem()).getBlock().defaultBlockState();
            return event.getBlockColors().getColor(state, null, null, pTintIndex);
        }, ModBlocks.HICKORY_LEAVES.get());
    }
}