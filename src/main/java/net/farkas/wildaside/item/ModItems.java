package net.farkas.wildaside.item;

import net.farkas.wildaside.WildAside;
import net.farkas.wildaside.block.ModBlocks;
import net.farkas.wildaside.entity.ModEntities;
import net.farkas.wildaside.entity.custom.ModBoatEntity;
import net.farkas.wildaside.item.custom.*;
import net.farkas.wildaside.util.HickoryColour;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(WildAside.MOD_ID);

    public static final DeferredItem<Item> VIBRION = ITEMS.register("vibrion",
            () ->  new Vibrion(new Item.Properties().food(ModFoods.VIBRION)));
    public static final DeferredItem<Item> ENTORIUM = ITEMS.register("entorium",
            () ->  new Item(new Item.Properties()));

    public static final DeferredItem<Item> MUCELLITH_SPAWN_EGG = ITEMS.register("mucellith_spawn_egg",
            () ->  new DeferredSpawnEggItem(ModEntities.MUCELLITH, 0xc8e3ff, 0xfff571, new Item.Properties()));
    public static final DeferredItem<Item> MUCELLITH_JAW = ITEMS.register("mucellith_jaw",
            () ->  new Item(new Item.Properties()));

    public static final DeferredItem<Item> ENTORIUM_PILL = ITEMS.register("entorium_pill",
            () ->  new EntoriumPill(new Item.Properties().food(ModFoods.ENTORIUM_PILL).stacksTo(16)));
    public static final DeferredItem<Item> SPORE_ARROW = ITEMS.register("spore_arrow",
            () ->  new SporeArrow(new Item.Properties()));
    public static final DeferredItem<Item> SPORE_BOMB = ITEMS.register("spore_bomb",
            () ->  new SporeBomb(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> FERTILISER_BOMB = ITEMS.register("fertiliser_bomb",
            () ->  new FertiliserBomb(new Item.Properties().stacksTo(16)));

    public static final DeferredItem<Item> SUBSTILIUM_SIGN = ITEMS.register("substilium_sign",
            () ->  new SignItem(new Item.Properties().stacksTo(16), ModBlocks.SUBSTILIUM_SIGN.get(), ModBlocks.SUBSTILIUM_WALL_SIGN.get()));
    public static final DeferredItem<Item> SUBSTILIUM_HANGING_SIGN = ITEMS.register("substilium_hanging_sign",
            () ->  new HangingSignItem(ModBlocks.SUBSTILIUM_HANGING_SIGN.get(), ModBlocks.SUBSTILIUM_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> SUBSTILIUM_BOAT = ITEMS.register("substilium_boat",
            () ->  new ModBoatItem(false, ModBoatEntity.Type.SUBSTILIUM, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> SUBSTILIUM_CHEST_BOAT = ITEMS.register("substilium_chest_boat",
            () ->  new ModBoatItem(true, ModBoatEntity.Type.SUBSTILIUM, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> HICKORY_SIGN = ITEMS.register("hickory_sign",
            () ->  new SignItem(new Item.Properties().stacksTo(16), ModBlocks.HICKORY_SIGN.get(), ModBlocks.HICKORY_WALL_SIGN.get()));
    public static final DeferredItem<Item> HICKORY_HANGING_SIGN = ITEMS.register("hickory_hanging_sign",
            () ->  new HangingSignItem(ModBlocks.HICKORY_HANGING_SIGN.get(), ModBlocks.HICKORY_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> HICKORY_BOAT = ITEMS.register("hickory_boat",
            () ->  new ModBoatItem(false, ModBoatEntity.Type.HICKORY, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> HICKORY_CHEST_BOAT = ITEMS.register("hickory_chest_boat",
            () ->  new ModBoatItem(true, ModBoatEntity.Type.HICKORY, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> HICKORY_NUT = ITEMS.register("hickory_nut",
            () ->  new FuelItem(new Item.Properties().food(ModFoods.HICKORY_NUT), 100));

    public static final DeferredItem<Item> HICKORY_LEAF = ITEMS.register("hickory_leaf",
            () ->  new HickoryLeafItem(new Item.Properties(), 50, HickoryColour.HICKORY));
    public static final DeferredItem<Item> RED_GLOWING_HICKORY_LEAF = ITEMS.register("red_glowing_hickory_leaf",
            () ->  new HickoryLeafItem(new Item.Properties(), 50, HickoryColour.RED_GLOWING));
    public static final DeferredItem<Item> BROWN_GLOWING_HICKORY_LEAF = ITEMS.register("brown_glowing_hickory_leaf",
            () ->  new HickoryLeafItem(new Item.Properties(), 50, HickoryColour.BROWN_GLOWING));
    public static final DeferredItem<Item> YELLOW_GLOWING_HICKORY_LEAF = ITEMS.register("yellow_glowing_hickory_leaf",
            () ->  new HickoryLeafItem(new Item.Properties(), 50, HickoryColour.YELLOW_GLOWING));
    public static final DeferredItem<Item> GREEN_GLOWING_HICKORY_LEAF = ITEMS.register("green_glowing_hickory_leaf",
            () ->  new HickoryLeafItem(new Item.Properties(), 50, HickoryColour.GREEN_GLOWING));

    public static final EnumMap<HickoryColour, DeferredItem<Item>> LEAF_ITEMS = new EnumMap<>(HickoryColour.class);
    static {
        LEAF_ITEMS.put(HickoryColour.HICKORY, HICKORY_LEAF);
        LEAF_ITEMS.put(HickoryColour.RED_GLOWING, RED_GLOWING_HICKORY_LEAF);
        LEAF_ITEMS.put(HickoryColour.BROWN_GLOWING, BROWN_GLOWING_HICKORY_LEAF);
        LEAF_ITEMS.put(HickoryColour.YELLOW_GLOWING, YELLOW_GLOWING_HICKORY_LEAF);
        LEAF_ITEMS.put(HickoryColour.GREEN_GLOWING, GREEN_GLOWING_HICKORY_LEAF);
    }

    public static final DeferredItem<Item> HICKORY_NUT_TRAIL_MIX = ITEMS.register("hickory_nut_trail_mix",
            () ->  new HickoryNutTrailMix(new Item.Properties().stacksTo(1).food(ModFoods.HICKORY_NUT_TRAIL_MIX), HickoryColour.HICKORY));
    public static final DeferredItem<Item> RED_HICKORY_NUT_TRAIL_MIX = ITEMS.register("red_hickory_nut_trail_mix",
            () ->  new HickoryNutTrailMix(new Item.Properties().stacksTo(1).food(ModFoods.HICKORY_NUT_TRAIL_MIX), HickoryColour.RED_GLOWING));
    public static final DeferredItem<Item> BROWN_HICKORY_NUT_TRAIL_MIX = ITEMS.register("brown_hickory_nut_trail_mix",
            () ->  new HickoryNutTrailMix(new Item.Properties().stacksTo(1).food(ModFoods.HICKORY_NUT_TRAIL_MIX), HickoryColour.BROWN_GLOWING));
    public static final DeferredItem<Item> YELLOW_HICKORY_NUT_TRAIL_MIX = ITEMS.register("yellow_hickory_nut_trail_mix",
            () ->  new HickoryNutTrailMix(new Item.Properties().stacksTo(1).food(ModFoods.HICKORY_NUT_TRAIL_MIX), HickoryColour.YELLOW_GLOWING));
    public static final DeferredItem<Item> GREEN_HICKORY_NUT_TRAIL_MIX = ITEMS.register("green_hickory_nut_trail_mix",
            () ->  new HickoryNutTrailMix(new Item.Properties().stacksTo(1).food(ModFoods.HICKORY_NUT_TRAIL_MIX), HickoryColour.GREEN_GLOWING));

    public static final EnumMap<HickoryColour, DeferredItem<Item>> TRAIL_MIX_ITEMS = new EnumMap<>(HickoryColour.class);
    static {
        TRAIL_MIX_ITEMS.put(HickoryColour.HICKORY, HICKORY_NUT_TRAIL_MIX);
        TRAIL_MIX_ITEMS.put(HickoryColour.RED_GLOWING, RED_HICKORY_NUT_TRAIL_MIX);
        TRAIL_MIX_ITEMS.put(HickoryColour.BROWN_GLOWING, BROWN_HICKORY_NUT_TRAIL_MIX);
        TRAIL_MIX_ITEMS.put(HickoryColour.YELLOW_GLOWING, YELLOW_HICKORY_NUT_TRAIL_MIX);
        TRAIL_MIX_ITEMS.put(HickoryColour.GREEN_GLOWING, GREEN_HICKORY_NUT_TRAIL_MIX);
    }

    public static final DeferredItem<Item> HICKORY_TREANT_SPAWN_EGG = ITEMS.register("hickory_treant_spawn_egg",
            () ->  new DeferredSpawnEggItem(ModEntities.HICKORY_TREANT, 0x704626, 0x409312, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
