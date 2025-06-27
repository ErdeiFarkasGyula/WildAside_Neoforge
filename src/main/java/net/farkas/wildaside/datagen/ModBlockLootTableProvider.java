package net.farkas.wildaside.datagen;

import net.farkas.wildaside.block.ModBlocks;
import net.farkas.wildaside.item.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        //VIBRION
        this.dropSelf(ModBlocks.VIBRION_GEL.get());
        this.dropSelf(ModBlocks.COMPRESSED_VIBRION_BLOCK.get());
        this.dropSelf(ModBlocks.LIT_VIBRION_GEL.get());
        this.dropSelf(ModBlocks.VIBRION_GROWTH.get());
        this.dropSelf(ModBlocks.HANGING_VIBRION_VINES.get());

        this.add(ModBlocks.HANGING_VIBRION_VINES_PLANT.get(),
                block -> createSingleItemTable(ModBlocks.HANGING_VIBRION_VINES.get()));
        this.add(ModBlocks.POTTED_VIBRION_GROWTH.get(),
                createPotFlowerItemTable(ModBlocks.VIBRION_GROWTH.get()));
        this.add(ModBlocks.VIBRION_BLOCK.get(),
                block -> createOreDrop(ModBlocks.VIBRION_BLOCK.get(), ModItems.VIBRION.get()));
        this.add(ModBlocks.VIBRION_SPOREHOLDER.get(),
                block -> createOreDrop(ModBlocks.VIBRION_SPOREHOLDER.get(), ModItems.VIBRION.get()));
        this.add(ModBlocks.VIBRION_GLASS.get(),
                block -> createSingleItemTableWithSilkTouch(ModBlocks.VIBRION_BLOCK.get(), ModItems.VIBRION.get()));
        this.add(ModBlocks.LIT_VIBRION_GLASS.get(),
                block -> createSingleItemTableWithSilkTouch(ModBlocks.VIBRION_BLOCK.get(), ModItems.VIBRION.get()));
        this.add(ModBlocks.VIBRION_GLASS_PANE.get(),
                block -> createSingleItemTableWithSilkTouch(ModBlocks.VIBRION_BLOCK.get(), ModItems.VIBRION.get()));
        this.add(ModBlocks.LIT_VIBRION_GLASS_PANE.get(),
                block -> createSingleItemTableWithSilkTouch(ModBlocks.VIBRION_BLOCK.get(), ModItems.VIBRION.get()));

        this.dropSelf(ModBlocks.SPORE_BLASTER.get());
        this.dropSelf(ModBlocks.POTION_BLASTER.get());
        this.dropSelf(ModBlocks.NATURAL_SPORE_BLASTER.get());
        this.dropSelf(ModBlocks.BIOENGINEERING_WORKSTATION.get());

        //ENTORIUM
        this.dropSelf(ModBlocks.ENTORIUM_SHROOM.get());
        this.add(ModBlocks.ENTORIUM_ORE.get(),
                block -> createOreDrop(ModBlocks.ENTORIUM_ORE.get(), ModItems.ENTORIUM.get()));
        this.add(ModBlocks.OVERGROWN_ENTORIUM_ORE.get(), block -> createSingleItemTableWithSilkTouch(ModBlocks.OVERGROWN_ENTORIUM_ORE.get(), Blocks.AIR.asItem()));


        //SUBSTILIUM
        this.dropSelf(ModBlocks.SUBSTILIUM_SOIL.get());
        this.dropSelf(ModBlocks.COMPRESSED_SUBSTILIUM_SOIL.get());
        this.dropSelf(ModBlocks.SMOOTH_SUBSTILIUM_SOIL.get());
        this.dropSelf(ModBlocks.CHISELED_SUBSTILIUM_SOIL.get());
        this.dropSelf(ModBlocks.SUBSTILIUM_TILES.get());
        this.dropSelf(ModBlocks.CRACKED_SUBSTILIUM_TILES.get());
        this.dropSelf(ModBlocks.SUBSTILIUM_TILE_STAIRS.get());
        this.dropSelf(ModBlocks.SUBSTILIUM_TILE_WALLS.get());
        this.dropSelf(ModBlocks.SUBSTILIUM_TILE_BUTTON.get());
        this.dropSelf(ModBlocks.SUBSTILIUM_TILE_PRESSURE_PLATE.get());

        this.add(ModBlocks.SUBSTILIUM_COAL_ORE.get(),
                block -> createSingleItemTableWithSilkTouch(ModBlocks.SUBSTILIUM_COAL_ORE.get(), Items.COAL, UniformGenerator.between(1, 2)));
        this.add(ModBlocks.SUBSTILIUM_COPPER_ORE.get(),
                block -> createSingleItemTableWithSilkTouch(ModBlocks.SUBSTILIUM_COPPER_ORE.get(), Items.RAW_COPPER, UniformGenerator.between(2, 6)));
        this.add(ModBlocks.SUBSTILIUM_LAPIS_ORE.get(),
                block -> createSingleItemTableWithSilkTouch(ModBlocks.SUBSTILIUM_LAPIS_ORE.get(), Items.LAPIS_LAZULI, UniformGenerator.between(4, 10)));
        this.add(ModBlocks.SUBSTILIUM_IRON_ORE.get(),
                block -> createSingleItemTableWithSilkTouch(ModBlocks.SUBSTILIUM_IRON_ORE.get(), Items.RAW_IRON, UniformGenerator.between(1, 2)));
        this.add(ModBlocks.SUBSTILIUM_GOLD_ORE.get(),
                block -> createSingleItemTableWithSilkTouch(ModBlocks.SUBSTILIUM_GOLD_ORE.get(), Items.RAW_GOLD, UniformGenerator.between(1, 2)));
        this.add(ModBlocks.SUBSTILIUM_REDSTONE_ORE.get(),
                block -> createSingleItemTableWithSilkTouch(ModBlocks.SUBSTILIUM_REDSTONE_ORE.get(), Items.REDSTONE, UniformGenerator.between(4, 6)));
        this.add(ModBlocks.SUBSTILIUM_DIAMOND_ORE.get(),
                block -> createSingleItemTableWithSilkTouch(ModBlocks.SUBSTILIUM_DIAMOND_ORE.get(), Items.DIAMOND, UniformGenerator.between(1, 2)));
        this.add(ModBlocks.SUBSTILIUM_EMERALD_ORE.get(),
                block -> createSingleItemTableWithSilkTouch(ModBlocks.SUBSTILIUM_EMERALD_ORE.get(), Items.EMERALD, UniformGenerator.between(1, 2)));

        this.dropSelf(ModBlocks.SUBSTILIUM_STEM.get());
        this.dropSelf(ModBlocks.STRIPPED_SUBSTILIUM_STEM.get());
        this.dropSelf(ModBlocks.SUBSTILIUM_WOOD.get());
        this.dropSelf(ModBlocks.STRIPPED_SUBSTILIUM_WOOD.get());
        this.dropSelf(ModBlocks.SUBSTILIUM_PLANKS.get());
        this.dropSelf(ModBlocks.SUBSTILIUM_STAIRS.get());
        this.dropSelf(ModBlocks.SUBSTILIUM_FENCE.get());
        this.dropSelf(ModBlocks.SUBSTILIUM_FENCE_GATE.get());
        this.dropSelf(ModBlocks.SUBSTILIUM_BUTTON.get());
        this.dropSelf(ModBlocks.SUBSTILIUM_PRESSURE_PLATE.get());
        this.dropSelf(ModBlocks.SUBSTILIUM_TRAPDOOR.get());
        this.dropSelf(ModBlocks.SUBSTILIUM_SPROUTS.get());

        this.add(ModBlocks.SUBSTILIUM_SIGN.get(),
                block -> createSingleItemTable(ModBlocks.SUBSTILIUM_SIGN.get()));
        this.add(ModBlocks.SUBSTILIUM_WALL_SIGN.get(),
                block -> createSingleItemTable(ModBlocks.SUBSTILIUM_SIGN.get()));
        this.add(ModBlocks.SUBSTILIUM_HANGING_SIGN.get(),
                block -> createSingleItemTable(ModBlocks.SUBSTILIUM_HANGING_SIGN.get()));
        this.add(ModBlocks.SUBSTILIUM_WALL_HANGING_SIGN.get(),
                block -> createSingleItemTable(ModBlocks.SUBSTILIUM_HANGING_SIGN.get()));


        this.add(ModBlocks.SUBSTILIUM_TILE_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.SUBSTILIUM_TILE_SLAB.get()));
        this.add(ModBlocks.SUBSTILIUM_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.SUBSTILIUM_SLAB.get()));
        this.add(ModBlocks.SUBSTILIUM_DOOR.get(),
                block -> createDoorTable(ModBlocks.SUBSTILIUM_DOOR.get()));


        //HICKORY
        this.add(ModBlocks.HICKORY_LEAVES.get(), block ->
                createBiggerLeavesDrops(block, ModBlocks.HICKORY_SAPLING.get(), ModItems.HICKORY_NUT.get()));
        this.add(ModBlocks.RED_GLOWING_HICKORY_LEAVES.get(), block ->
                createBiggerLeavesDrops(block, ModBlocks.RED_GLOWING_HICKORY_SAPLING.get(), ModItems.HICKORY_NUT.get()));
        this.add(ModBlocks.BROWN_GLOWING_HICKORY_LEAVES.get(), block ->
                createBiggerLeavesDrops(block, ModBlocks.BROWN_GLOWING_HICKORY_SAPLING.get(), ModItems.HICKORY_NUT.get()));
        this.add(ModBlocks.YELLOW_GLOWING_HICKORY_LEAVES.get(), block ->
                createBiggerLeavesDrops(block, ModBlocks.YELLOW_GLOWING_HICKORY_SAPLING.get(), ModItems.HICKORY_NUT.get()));
        this.add(ModBlocks.GREEN_GLOWING_HICKORY_LEAVES.get(), block ->
                createBiggerLeavesDrops(block, ModBlocks.GREEN_GLOWING_HICKORY_SAPLING.get(), ModItems.HICKORY_NUT.get()));

        this.add(ModBlocks.FALLEN_HICKORY_LEAVES.get(), block ->
                createBiggerLeavesDrops(Blocks.AIR, Blocks.AIR, Items.AIR));

        this.dropSelf(ModBlocks.HICKORY_SAPLING.get());
        this.dropSelf(ModBlocks.RED_GLOWING_HICKORY_SAPLING.get());
        this.dropSelf(ModBlocks.BROWN_GLOWING_HICKORY_SAPLING.get());
        this.dropSelf(ModBlocks.YELLOW_GLOWING_HICKORY_SAPLING.get());
        this.dropSelf(ModBlocks.GREEN_GLOWING_HICKORY_SAPLING.get());

        this.dropSelf(ModBlocks.HICKORY_LOG.get());
        this.dropSelf(ModBlocks.STRIPPED_HICKORY_LOG.get());
        this.dropSelf(ModBlocks.HICKORY_WOOD.get());
        this.dropSelf(ModBlocks.STRIPPED_HICKORY_WOOD.get());
        this.dropSelf(ModBlocks.HICKORY_PLANKS.get());
        this.dropSelf(ModBlocks.HICKORY_STAIRS.get());
        this.dropSelf(ModBlocks.HICKORY_FENCE.get());
        this.dropSelf(ModBlocks.HICKORY_FENCE_GATE.get());
        this.dropSelf(ModBlocks.HICKORY_BUTTON.get());
        this.dropSelf(ModBlocks.HICKORY_PRESSURE_PLATE.get());
        this.dropSelf(ModBlocks.HICKORY_TRAPDOOR.get());

        this.add(ModBlocks.HICKORY_SIGN.get(),
                block -> createSingleItemTable(ModBlocks.HICKORY_SIGN.get()));
        this.add(ModBlocks.HICKORY_WALL_SIGN.get(),
                block -> createSingleItemTable(ModBlocks.HICKORY_SIGN.get()));
        this.add(ModBlocks.HICKORY_HANGING_SIGN.get(),
                block -> createSingleItemTable(ModBlocks.HICKORY_HANGING_SIGN.get()));
        this.add(ModBlocks.HICKORY_WALL_HANGING_SIGN.get(),
                block -> createSingleItemTable(ModBlocks.HICKORY_HANGING_SIGN.get()));

        this.add(ModBlocks.HICKORY_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.HICKORY_SLAB.get()));
        this.add(ModBlocks.HICKORY_DOOR.get(),
                block -> createDoorTable(ModBlocks.HICKORY_DOOR.get()));

        this.dropSelf(ModBlocks.HICKORY_ROOT_BUSH.get());

        this.dropSelf(ModBlocks.SPOTTED_WINTERGREEN.get());
        this.dropSelf(ModBlocks.PINKSTER_FLOWER.get());

    }

    protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));
    }

    protected LootTable.Builder createBiggerLeavesDrops(Block leaves, Block sapling, Item otherDrop) {
        return createLeavesDrops(leaves, sapling, NORMAL_LEAVES_SAPLING_CHANCES).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(hasSilkTouch().invert()).add(this.applyExplosionCondition(leaves, LootItem.lootTableItem(otherDrop)).when(hasSilkTouch().invert())));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}