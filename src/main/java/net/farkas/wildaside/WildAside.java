package net.farkas.wildaside;

import com.mojang.logging.LogUtils;
import net.farkas.wildaside.attachments.ModAttachments;
import net.farkas.wildaside.block.ModBlocks;
import net.farkas.wildaside.block.entity.ModBlockEntities;
import net.farkas.wildaside.config.Config;
import net.farkas.wildaside.effect.ModMobEffects;
import net.farkas.wildaside.enchantment.ModEnchantmentEffects;
import net.farkas.wildaside.entity.ModEntities;
import net.farkas.wildaside.item.ModCreativeModeTabs;
import net.farkas.wildaside.item.ModItems;
import net.farkas.wildaside.item.VanillaCreativeTabs;
import net.farkas.wildaside.particle.ModParticles;
import net.farkas.wildaside.potion.ModPotions;
import net.farkas.wildaside.recipe.ModRecipes;
import net.farkas.wildaside.screen.ModMenuTypes;
import net.farkas.wildaside.sound.ModSounds;
import net.farkas.wildaside.worldgen.biome.ModTerraBlenderAPI;
import net.farkas.wildaside.worldgen.biome.surface.ModSurfaceRules;
import net.farkas.wildaside.worldgen.feature.ModFeatures;
import net.farkas.wildaside.worldgen.feature.ModFoliagePlacers;
import net.farkas.wildaside.worldgen.feature.decorator.ModTreeDecorators;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;
import terrablender.api.SurfaceRuleManager;

@Mod(WildAside.MOD_ID)
public class WildAside {
    public static final String MOD_ID = "wildaside";
    private static final Logger LOGGER = LogUtils.getLogger();

    public WildAside(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC);

        ModCreativeModeTabs.register(modEventBus);
        ModAttachments.register(modEventBus);
        ModSounds.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEntities.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModMobEffects.register(modEventBus);
        ModPotions.register(modEventBus);
        ModParticles.register(modEventBus);
        ModTreeDecorators.register(modEventBus);
        ModFeatures.register(modEventBus);
        ModFoliagePlacers.register(modEventBus);
        ModEnchantmentEffects.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(VanillaCreativeTabs::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModTerraBlenderAPI.registerRegions();
            ((FlowerPotBlock)Blocks.FLOWER_POT).addPlant(ModBlocks.VIBRION_GROWTH.getId(), ModBlocks.POTTED_VIBRION_GROWTH);
        });

        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MOD_ID, ModSurfaceRules.makeRules());
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }
}
