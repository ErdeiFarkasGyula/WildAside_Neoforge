package net.farkas.wildaside.event;

import com.mojang.brigadier.CommandDispatcher;
import net.farkas.wildaside.WildAside;
import net.farkas.wildaside.attachments.ModAttachments;
import net.farkas.wildaside.block.ModBlocks;
import net.farkas.wildaside.command.ContaminationCommand;
import net.farkas.wildaside.effect.ModMobEffects;
import net.farkas.wildaside.item.ModItems;
import net.farkas.wildaside.potion.ModPotions;
import net.farkas.wildaside.util.AdvancementHandler;
import net.farkas.wildaside.util.ContaminationHandler;
import net.farkas.wildaside.util.HickoryColour;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.living.LivingBreatheEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;

import java.util.List;

@EventBusSubscriber(modid = WildAside.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            AdvancementHolder advancement = player.server.getAdvancements().get(ResourceLocation.fromNamespaceAndPath(WildAside.MOD_ID,"wild_wilder_wildest"));
            if (advancement == null) return;
            AdvancementProgress progress = player.getAdvancements().getOrStartProgress(advancement);
            if (!progress.isDone()) {
                for (String criteria : progress.getRemainingCriteria()) {
                    player.getAdvancements().award(advancement, criteria);
                }
            }
        }
    }

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        ContaminationCommand.register(dispatcher);
    }

    @SubscribeEvent
    public static void brewingRecipesEvent(RegisterBrewingRecipesEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();

//        event.addRecipe(new BetterBrewingRecipe(Potions.AWKWARD, ModItems.VIBRION.get(), ModPotions.CONTAMINATION_POTION.get()));
//        event.addRecipe(new BetterBrewingRecipe(ModPotions.CONTAMINATION_POTION.get(), Items.REDSTONE, ModPotions.CONTAMINATION_POTION_2.get()));
//        event.addRecipe(new BetterBrewingRecipe(ModPotions.IMMUNITY_POTION.get(), Items.FERMENTED_SPIDER_EYE, ModPotions.IMMUNITY_POTION.get()));

//        event.addRecipe(new BetterBrewingRecipe(Potions.AWKWARD, ModItems.ENTORIUM.get(), ModPotions.IMMUNITY_POTION.get()));
//        event.addRecipe(new BetterBrewingRecipe(ModPotions.CONTAMINATION_POTION.get(), Items.FERMENTED_SPIDER_EYE, ModPotions.IMMUNITY_POTION.get()));
//        event.addRecipe(new BetterBrewingRecipe(ModPotions.IMMUNITY_POTION.get(), Items.REDSTONE, ModPotions.IMMUNITY_POTION_2.get()));
//        event.addRecipe(new BetterBrewingRecipe(ModPotions.CONTAMINATION_POTION_2.get(), Items.FERMENTED_SPIDER_EYE, ModPotions.IMMUNITY_POTION_2.get()));

        builder.addMix(Potions.AWKWARD, ModItems.MUCELLITH_JAW.get(), ModPotions.LIFESTEAL_POTION);
        builder.addMix(ModPotions.LIFESTEAL_POTION, Items.REDSTONE, ModPotions.LIFESTEAL_POTION_2);
    }

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if (event.getType() == VillagerProfession.FARMER) {
            ItemStack emerald = new ItemStack(Items.EMERALD);

            event.getTrades().get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemCost(ModItems.HICKORY_NUT.get(), 16), emerald, 20, 2, 0.05f
            ));
            for (HickoryColour colour : HickoryColour.values()) {
                event.getTrades().get(2).add((pTrader, pRandom) -> new MerchantOffer(
                        new ItemCost(ModItems.LEAF_ITEMS.get(colour).get(), 32), emerald, 20, 2, 0.05f
                ));
            }
        }

        if (event.getType() == VillagerProfession.TOOLSMITH) {
            event.getTrades().get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 6), new ItemStack(ModItems.ENTORIUM_PILL.get()), 2, 5, 0.06f
            ));

        }
    }

    @SubscribeEvent
    public static void addCustomWanderingTrades(WandererTradesEvent event) {
        List<VillagerTrades.ItemListing> genericTrades = event.getGenericTrades();
        //List<VillagerTrades.ItemListing> rareTrades = event.getRareTrades();

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemCost(Items.EMERALD, 4), new ItemStack(ModBlocks.HICKORY_SAPLING.get()), 8, 2, 0.03f
        ));
        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemCost(Items.EMERALD, 5), new ItemStack(ModBlocks.RED_GLOWING_HICKORY_SAPLING.get()), 8, 2, 0.03f
        ));
        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemCost(Items.EMERALD, 5), new ItemStack(ModBlocks.BROWN_GLOWING_HICKORY_SAPLING.get()), 8, 2, 0.03f
        ));
        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemCost(Items.EMERALD, 5), new ItemStack(ModBlocks.YELLOW_GLOWING_HICKORY_SAPLING.get()), 8, 2, 0.03f
        ));
        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemCost(Items.EMERALD, 5), new ItemStack(ModBlocks.GREEN_GLOWING_HICKORY_SAPLING.get()), 8, 2, 0.03f
        ));
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Pre event) {
        glowUpAdvancement(event);
        itsShearingTimeAdvancement(event);
        bacteriaBarrierAdvancement(event);
    }

    private static final ResourceLocation GLOWING_FOREST = ResourceLocation.fromNamespaceAndPath(WildAside.MOD_ID, "glowing_hickory_forest");

    private static void glowUpAdvancement(PlayerTickEvent.Pre event) {
        if (!event.getEntity().level().isClientSide) {
            ServerPlayer player = (ServerPlayer) event.getEntity();
            ServerLevel world = player.serverLevel();

            long time = world.getDayTime();

            if (time >= 14000 && time <= 22000) {
                Holder<Biome> biomeHolder = world.getBiome(player.blockPosition());
                ResourceKey<Biome> biomeKey = biomeHolder.unwrapKey().orElse(null);

                if (biomeKey != null && biomeKey.location().equals(GLOWING_FOREST)) {
                    AdvancementHandler.givePlayerAdvancement(player, "glow_up");
                }
            }
        }
    }

    private static void itsShearingTimeAdvancement(PlayerTickEvent event) {
        if (!event.getEntity().level().isClientSide) {
            ServerPlayer player = (ServerPlayer) event.getEntity();
            ServerLevel level = player.serverLevel();

            ClipContext clipContext = new ClipContext(player.getEyePosition(1f),
                    player.getEyePosition(1f).add(player.getViewVector(1f).scale(5)),
                    ClipContext.Block.OUTLINE,
                    ClipContext.Fluid.NONE,
                    player);

            BlockPos blockPos = level.clip(clipContext).getBlockPos();

            if (level.getBlockState(blockPos).getBlock() == ModBlocks.OVERGROWN_ENTORIUM_ORE.get()) {
                AdvancementHandler.givePlayerAdvancement(player, "its_shearing_time");
            }
        }
    }

    private static void bacteriaBarrierAdvancement(PlayerTickEvent event) {
        if (!event.getEntity().level().isClientSide) {
            ServerPlayer player = (ServerPlayer) event.getEntity();
            ServerLevel level = player.serverLevel();

            ClipContext clipContext = new ClipContext(player.getEyePosition(1f),
                    player.getEyePosition(1f).add(player.getViewVector(1f).scale(5)),
                    ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player);

            BlockPos blockPos = level.clip(clipContext).getBlockPos();

            BlockState blockState = level.getBlockState(blockPos);
            if (blockState.getBlock() == ModBlocks.SPORE_BLASTER.get() && level.getBestNeighborSignal(blockPos) > 0) {
                AdvancementHandler.givePlayerAdvancement(player, "bacteria_barrier");
            }
        }
    }

    @SubscribeEvent
    public static void blasterBustedAdvancement(BlockEvent.BreakEvent event) {
        if (!event.getPlayer().level().isClientSide) {
            if (event.getLevel().getBlockState(event.getPos()).getBlock() == ModBlocks.NATURAL_SPORE_BLASTER.get()) {
                AdvancementHandler.givePlayerAdvancement((ServerPlayer)event.getPlayer(), "blaster_busted");
            }
        }
    }

    @SubscribeEvent
    public static void onContaminationEffectExpired(MobEffectEvent.Expired event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Player) {
            MobEffectInstance mobEffectInstance = event.getEffectInstance();
            if (mobEffectInstance != null && mobEffectInstance.getEffect() == ModMobEffects.CONTAMINATION.getDelegate()) {
                entity.addEffect(new MobEffectInstance(ModMobEffects.IMMUNITY.getDelegate(), (mobEffectInstance.getAmplifier() + 1 ) * 5 * 20, mobEffectInstance.getAmplifier()));
            }
        }
    }

    @SubscribeEvent
    public static void playerDoesCriticalStrike(CriticalHitEvent event) {
        if (!event.isCriticalHit()) return;

        Holder<MobEffect> contamEffect = ModMobEffects.CONTAMINATION.getDelegate();

        Player attacker = event.getEntity();
        if (attacker.hasEffect(contamEffect)) {
            RandomSource random = attacker.getRandom();
            if ((float) attacker.getEffect(contamEffect).getAmplifier() / 5 > random.nextFloat()) {
                if (event.getTarget() instanceof LivingEntity target) {
                    int dose = attacker.getData(ModAttachments.CONTAMINATION).getDose();
                    ContaminationHandler.addDose(target, dose / 5);
                }
            }
        }
    }

    @SubscribeEvent
    public static void livingEntityTick(LivingBreatheEvent event) {
        LivingEntity entity = event.getEntity();
        entity.getData(ModAttachments.CONTAMINATION).addDose(-10);
    }
}