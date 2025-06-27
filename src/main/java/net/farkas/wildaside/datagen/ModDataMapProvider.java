package net.farkas.wildaside.datagen;

import net.farkas.wildaside.block.ModBlocks;
import net.farkas.wildaside.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.ComposterBlock;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class ModDataMapProvider extends DataMapProvider {
    protected ModDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather() {
//        this.builder(NeoForgeDataMaps.FURNACE_FUELS)
//                .add(ModItems.STARLIGHT_ASHES.getId(), new FurnaceFuel(1200), false)
//                .add(ModItems.FROSTFIRE_ICE.getId(), new FurnaceFuel(2400), false);

        this.builder(NeoForgeDataMaps.COMPOSTABLES)
                .add(ModItems.VIBRION.getId(), new Compostable(0.25f), false)
                .add(ModItems.ENTORIUM.getId(), new Compostable(0.30f), false)
                .add(ModBlocks.VIBRION_BLOCK.getId(), new Compostable(1.00f), false)
                .add(ModBlocks.COMPRESSED_VIBRION_BLOCK.getId(), new Compostable(1.00f), false)
                .add(ModBlocks.VIBRION_GEL.getId(), new Compostable(0.50f), false)
                .add(ModBlocks.LIT_VIBRION_GEL.getId(), new Compostable(0.50f), false)
                .add(ModBlocks.VIBRION_GROWTH.getId(), new Compostable(0.30f), false)
                .add(ModBlocks.SUBSTILIUM_SPROUTS.getId(), new Compostable(0.30f), false)
                .add(ModBlocks.ENTORIUM_SHROOM.getId(), new Compostable(0.50f), false)
                .add(ModBlocks.VIBRION_SPOREHOLDER.getId(), new Compostable(1.00f), false)
                .add(ModBlocks.HANGING_VIBRION_VINES.getId(), new Compostable(0.50f), false)
                .add(ModBlocks.HICKORY_LEAVES.getId(), new Compostable(0.30f), false)
                .add(ModBlocks.RED_GLOWING_HICKORY_LEAVES.getId(), new Compostable(0.50f), false)
                .add(ModBlocks.BROWN_GLOWING_HICKORY_LEAVES.getId(), new Compostable(0.50f), false)
                .add(ModBlocks.YELLOW_GLOWING_HICKORY_LEAVES.getId(), new Compostable(0.50f), false)
                .add(ModBlocks.GREEN_GLOWING_HICKORY_LEAVES.getId(), new Compostable(0.50f), false)
                .add(ModItems.HICKORY_LEAF.getId(), new Compostable(0.10f), false)
                .add(ModItems.RED_GLOWING_HICKORY_LEAF.getId(), new Compostable(0.15f), false)
                .add(ModItems.BROWN_GLOWING_HICKORY_LEAF.getId(), new Compostable(0.15f), false)
                .add(ModItems.YELLOW_GLOWING_HICKORY_LEAF.getId(), new Compostable(0.15f), false)
                .add(ModItems.GREEN_GLOWING_HICKORY_LEAF.getId(),  new Compostable(0.15f), false)
                .add(ModBlocks.HICKORY_SAPLING.getId(), new Compostable(0.30f), false)
                .add(ModBlocks.RED_GLOWING_HICKORY_SAPLING.getId(), new Compostable(0.50f), false)
                .add(ModBlocks.BROWN_GLOWING_HICKORY_SAPLING.getId(), new Compostable(0.50f), false)
                .add(ModBlocks.YELLOW_GLOWING_HICKORY_SAPLING.getId(), new Compostable(0.50f), false)
                .add(ModBlocks.GREEN_GLOWING_HICKORY_SAPLING.getId(), new Compostable(0.50f), false)
                .add(ModBlocks.SPOTTED_WINTERGREEN.getId(), new Compostable(0.65f), false)
                .add(ModBlocks.PINKSTER_FLOWER.getId(), new Compostable(0.65f), false)
                .add(ModItems.HICKORY_NUT.getId(), new Compostable(0.65f), false);
    }
}