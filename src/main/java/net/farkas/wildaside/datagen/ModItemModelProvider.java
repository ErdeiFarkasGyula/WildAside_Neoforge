package net.farkas.wildaside.datagen;

import net.farkas.wildaside.WildAside;
import net.farkas.wildaside.block.ModBlocks;
import net.farkas.wildaside.item.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, WildAside.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        //VIBRION
        simpleItem(ModItems.VIBRION);
        simpleItem(ModItems.MUCELLITH_JAW);
        withExistingParent(ModItems.MUCELLITH_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        simpleBlockItemBlockTexture(ModBlocks.VIBRION_GROWTH);
        simpleBlockItem(ModBlocks.VIBRION_GLASS_PANE, ModBlocks.VIBRION_GLASS);
        simpleBlockItem(ModBlocks.LIT_VIBRION_GLASS_PANE, ModBlocks.VIBRION_GLASS);
        simpleBlockItem(ModBlocks.HANGING_VIBRION_VINES, ModBlocks.HANGING_VIBRION_VINES_PLANT);
        simpleBlockItem(ModBlocks.HANGING_VIBRION_VINES_PLANT, ModBlocks.HANGING_VIBRION_VINES_PLANT);

        //ENTORIUM
        simpleItem(ModItems.ENTORIUM);
        simpleItem(ModItems.ENTORIUM_PILL);
        simpleItem(ModItems.SPORE_ARROW);
        simpleItem(ModItems.SPORE_BOMB);
        simpleItem(ModItems.FERTILISER_BOMB);

        //SUBSTILIUM
        simpleBlockItem(ModBlocks.SUBSTILIUM_DOOR);
        trapdoorItem(ModBlocks.SUBSTILIUM_TRAPDOOR);
        evenSimplerBlockItem(ModBlocks.SUBSTILIUM_STEM);
        evenSimplerBlockItem(ModBlocks.SUBSTILIUM_WOOD);
        evenSimplerBlockItem(ModBlocks.STRIPPED_SUBSTILIUM_STEM);
        evenSimplerBlockItem(ModBlocks.STRIPPED_SUBSTILIUM_WOOD);
        evenSimplerBlockItem(ModBlocks.SUBSTILIUM_STAIRS);
        evenSimplerBlockItem(ModBlocks.SUBSTILIUM_TILE_STAIRS);
        evenSimplerBlockItem(ModBlocks.SUBSTILIUM_SLAB);
        evenSimplerBlockItem(ModBlocks.SUBSTILIUM_TILE_SLAB);
        evenSimplerBlockItem(ModBlocks.SUBSTILIUM_FENCE_GATE);
        evenSimplerBlockItem(ModBlocks.SUBSTILIUM_PRESSURE_PLATE);
        evenSimplerBlockItem(ModBlocks.SUBSTILIUM_TILE_PRESSURE_PLATE);
        wallItem(ModBlocks.SUBSTILIUM_TILE_WALLS, ModBlocks.SUBSTILIUM_TILES);
        fenceItem(ModBlocks.SUBSTILIUM_FENCE, ModBlocks.SUBSTILIUM_PLANKS);
        buttonItem(ModBlocks.SUBSTILIUM_BUTTON, ModBlocks.SUBSTILIUM_PLANKS);
        buttonItem(ModBlocks.SUBSTILIUM_TILE_BUTTON, ModBlocks.SUBSTILIUM_TILES);
        simpleItem(ModItems.SUBSTILIUM_SIGN);
        simpleItem(ModItems.SUBSTILIUM_HANGING_SIGN);
        simpleItem(ModItems.SUBSTILIUM_BOAT);
        simpleItem(ModItems.SUBSTILIUM_CHEST_BOAT);
        simpleBlockItemBlockTexture(ModBlocks.SUBSTILIUM_SPROUTS);


        //HICKORY
        simpleBlockItem(ModBlocks.HICKORY_DOOR);
        trapdoorItem(ModBlocks.HICKORY_TRAPDOOR);
        evenSimplerBlockItem(ModBlocks.HICKORY_LOG);
        evenSimplerBlockItem(ModBlocks.HICKORY_WOOD);
        evenSimplerBlockItem(ModBlocks.STRIPPED_HICKORY_LOG);
        evenSimplerBlockItem(ModBlocks.STRIPPED_HICKORY_WOOD);
        evenSimplerBlockItem(ModBlocks.HICKORY_STAIRS);
        evenSimplerBlockItem(ModBlocks.HICKORY_SLAB);
        evenSimplerBlockItem(ModBlocks.HICKORY_FENCE_GATE);
        evenSimplerBlockItem(ModBlocks.HICKORY_PRESSURE_PLATE);
        fenceItem(ModBlocks.HICKORY_FENCE, ModBlocks.HICKORY_PLANKS);
        buttonItem(ModBlocks.HICKORY_BUTTON, ModBlocks.HICKORY_PLANKS);
        simpleItem(ModItems.HICKORY_SIGN);
        simpleItem(ModItems.HICKORY_HANGING_SIGN);
        simpleItem(ModItems.HICKORY_BOAT);
        simpleItem(ModItems.HICKORY_CHEST_BOAT);

        simpleItem(ModItems.HICKORY_NUT);
        simpleItem(ModItems.HICKORY_NUT_TRAIL_MIX);
        simpleItem(ModItems.RED_HICKORY_NUT_TRAIL_MIX);
        simpleItem(ModItems.BROWN_HICKORY_NUT_TRAIL_MIX);
        simpleItem(ModItems.YELLOW_HICKORY_NUT_TRAIL_MIX);
        simpleItem(ModItems.GREEN_HICKORY_NUT_TRAIL_MIX);
        simpleBlockItemBlockTexture(ModBlocks.HICKORY_SAPLING);
        simpleBlockItemBlockTexture(ModBlocks.RED_GLOWING_HICKORY_SAPLING);
        simpleBlockItemBlockTexture(ModBlocks.BROWN_GLOWING_HICKORY_SAPLING);
        simpleBlockItemBlockTexture(ModBlocks.YELLOW_GLOWING_HICKORY_SAPLING);
        simpleBlockItemBlockTexture(ModBlocks.GREEN_GLOWING_HICKORY_SAPLING);

        simpleItem(ModItems.HICKORY_LEAF);
        simpleItem(ModItems.RED_GLOWING_HICKORY_LEAF);
        simpleItem(ModItems.BROWN_GLOWING_HICKORY_LEAF);
        simpleItem(ModItems.YELLOW_GLOWING_HICKORY_LEAF);
        simpleItem(ModItems.GREEN_GLOWING_HICKORY_LEAF);

        simpleBlockItemBlockTexture(ModBlocks.HICKORY_ROOT_BUSH);
        withExistingParent(ModItems.HICKORY_TREANT_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

        simpleBlockItemBlockTexture(ModBlocks.SPOTTED_WINTERGREEN);
        simpleBlockItemBlockTexture(ModBlocks.PINKSTER_FLOWER);

    }

    private ItemModelBuilder simpleItem(DeferredItem<Item> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(WildAside.MOD_ID, "item/" + item.getId().getPath()));
    }

    public void evenSimplerBlockItem(DeferredBlock<Block> block) {
        this.withExistingParent(WildAside.MOD_ID + ":" + BuiltInRegistries.BLOCK.getKey(block.get()).getPath(),
                modLoc("block/" + BuiltInRegistries.BLOCK.getKey(block.get()).getPath()));
    }

    public void trapdoorItem(DeferredBlock<Block> block) {
        this.withExistingParent(BuiltInRegistries.BLOCK.getKey(block.get()).getPath(),
                modLoc("block/" + BuiltInRegistries.BLOCK.getKey(block.get()).getPath() + "_bottom"));
    }

    private ItemModelBuilder simpleBlockItem(DeferredBlock<Block> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(WildAside.MOD_ID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleBlockItem(DeferredBlock<Block> item, DeferredBlock<Block> item2) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(WildAside.MOD_ID,"block/" + item2.getId().getPath()));
    }

    private ItemModelBuilder simpleBlockItemBlockTexture(DeferredBlock<Block> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(WildAside.MOD_ID,"block/" + item.getId().getPath()));
    }

    private ItemModelBuilder saplingItem(DeferredBlock<Block> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(WildAside.MOD_ID,"block/" + item.getId().getPath()));
    }

    public void buttonItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/button_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(WildAside.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    }

    public void fenceItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/fence_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(WildAside.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    }

    public void wallItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  ResourceLocation.fromNamespaceAndPath(WildAside.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    }

    private ItemModelBuilder handheldItem(DeferredItem<?> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(WildAside.MOD_ID,"item/" + item.getId().getPath()));
    }
}