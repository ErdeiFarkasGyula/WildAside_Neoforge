package net.farkas.wildaside.datagen;

import net.farkas.wildaside.WildAside;
import net.farkas.wildaside.block.ModBlocks;
import net.farkas.wildaside.item.ModItems;
import net.farkas.wildaside.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_, CompletableFuture<TagLookup<Block>> p_275322_) {
        super(p_275343_, p_275729_, WildAside.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(ModTags.Items.SHEARS)
                .add(Items.SHEARS);

        this.tag(ItemTags.LOGS_THAT_BURN)
                .add(ModBlocks.SUBSTILIUM_STEM.get().asItem())
                .add(ModBlocks.STRIPPED_SUBSTILIUM_STEM.get().asItem())
                .add(ModBlocks.SUBSTILIUM_WOOD.get().asItem())
                .add(ModBlocks.STRIPPED_SUBSTILIUM_WOOD.get().asItem())
                .add(ModBlocks.HICKORY_LOG.get().asItem())
                .add(ModBlocks.STRIPPED_HICKORY_LOG.get().asItem())
                .add(ModBlocks.HICKORY_WOOD.get().asItem())
                .add(ModBlocks.STRIPPED_HICKORY_WOOD.get().asItem());

        this.tag(ItemTags.PLANKS)
                .add(ModBlocks.SUBSTILIUM_PLANKS.get().asItem())
                .add(ModBlocks.HICKORY_PLANKS.get().asItem());

        this.tag(ItemTags.ARROWS)
                .add(ModItems.SPORE_ARROW.get());
    }
}