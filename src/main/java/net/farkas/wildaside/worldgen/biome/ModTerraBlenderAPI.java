package net.farkas.wildaside.worldgen.biome;

import net.farkas.wildaside.Config;
import net.farkas.wildaside.WildAside;
import net.farkas.wildaside.worldgen.biome.region.GlowingHickoryForestRegion;
import net.farkas.wildaside.worldgen.biome.region.VibrionHiveRegion;
import net.farkas.wildaside.worldgen.biome.region.HickoryForestRegion;
import net.minecraft.resources.ResourceLocation;
import terrablender.api.Regions;

public class ModTerraBlenderAPI {
    public static void registerRegions() {
        Regions.register(new HickoryForestRegion(ResourceLocation.fromNamespaceAndPath(WildAside.MOD_ID, "hickory_forest"), Config.HICKORY_FOREST_WEIGHT.get()));
        Regions.register(new GlowingHickoryForestRegion(ResourceLocation.fromNamespaceAndPath(WildAside.MOD_ID, "glowing_hickory_forest"), Config.GLOWING_HICKORY_FOREST_WEIGHT.get()));
        Regions.register(new VibrionHiveRegion(ResourceLocation.fromNamespaceAndPath(WildAside.MOD_ID, "vibrion_hive"), Config.VIBRION_HIVE_WEIGHT.get()));
    }
}