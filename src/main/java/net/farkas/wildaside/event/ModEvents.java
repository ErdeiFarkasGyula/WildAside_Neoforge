package net.farkas.wildaside.event;

import com.mojang.brigadier.CommandDispatcher;
import net.farkas.wildaside.WildAside;
import net.farkas.wildaside.attachments.ModAttachments;
import net.farkas.wildaside.block.ModBlocks;
import net.farkas.wildaside.command.ModCommands;
import net.farkas.wildaside.effect.ModMobEffects;
import net.farkas.wildaside.entity.ModEntitySpawns;
import net.farkas.wildaside.item.ModItems;
import net.farkas.wildaside.network.WindSavedData;
import net.farkas.wildaside.potion.ModPotions;
import net.farkas.wildaside.util.AdvancementHandler;
import net.farkas.wildaside.util.ContaminationHandler;
import net.farkas.wildaside.util.WindManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.living.LivingBreatheEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.Optional;

@EventBusSubscriber(modid = WildAside.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            AdvancementHandler.givePlayerAdvancement(player, "wild_wilder_wildest");
        }
    }

    @SubscribeEvent
    public static void addPackFinders(AddPackFindersEvent event) {
        ModContainer mod = ModList.get().getModContainerById(WildAside.MOD_ID).orElse(null);
        if (mod == null || event.getPackType() != PackType.CLIENT_RESOURCES) return;

        String id = "wildaside_ce";

        Pack.ResourcesSupplier supplier;
        supplier = new PathPackResources.PathResourcesSupplier(
                mod.getModInfo().getOwningFile().getFile().findResource("resourcepacks/" + id));

        PackLocationInfo location = new PackLocationInfo(
                id,
                Component.literal("Wild Aside CEntertain"),
                PackSource.DEFAULT,
                Optional.empty());

        PackSelectionConfig config = new PackSelectionConfig(false, Pack.Position.TOP, false);

        Pack pack = Pack.readMetaAndCreate(location, supplier, PackType.CLIENT_RESOURCES, config);
        if (pack != null) {
            event.addRepositorySource(consumer -> consumer.accept(pack));
        }
    }

    @SubscribeEvent
    public static void onRegisterSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        ModEntitySpawns.registerSpawnPlacements(event);
    }

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        ModCommands.register(dispatcher);
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
    public static void onWorldLoad(LevelEvent.Load event) {
        loadWind(event);
    }

    public static void loadWind(LevelEvent.Load event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) return;
        if (!serverLevel.dimension().equals(ServerLevel.OVERWORLD)) return;

        WindSavedData data = WindSavedData.get(serverLevel);

        Vec3 dir = data.getWindDirection();
        float strength = data.getWindStrength();

        WindManager.setWind(dir, strength);
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        manageWeather(event);
    }

    public static void manageWeather(ServerTickEvent.Post event) {
        MinecraftServer server = event.getServer();
        ServerLevel overworld = server.getLevel(Level.OVERWORLD);
        if (overworld == null) return;

        WindSavedData weatherData = WindSavedData.get(overworld);

        boolean raining = overworld.isRaining();
        boolean thundering = overworld.isThundering();

        if (raining != weatherData.wasRaining() || thundering != weatherData.wasThundering()) {
            WindManager.calculateAndSetWind(overworld, false);
        }

        int time = 1200;
        if (server.getTickCount() % time == 0) {
            WindManager.calculateAndSetWind(overworld, true);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Pre event) {
        if (event.getEntity().level().isClientSide) return;
        glowUpAdvancement(event);
        clipContextCheckingTickEvent(event);
    }

    private static final ResourceLocation GLOWING_HICKORY_FOREST = ResourceLocation.fromNamespaceAndPath(WildAside.MOD_ID, "glowing_hickory_forest");

    private static void glowUpAdvancement(PlayerTickEvent.Pre event) {
        ServerPlayer player = (ServerPlayer) event.getEntity();
        ServerLevel world = player.serverLevel();

        long time = world.getDayTime();

        if (time >= 14000 && time <= 22000) {
            Holder<Biome> biomeHolder = world.getBiome(player.blockPosition());
            ResourceKey<Biome> biomeKey = biomeHolder.unwrapKey().orElse(null);

            if (biomeKey != null && biomeKey.location().equals(GLOWING_HICKORY_FOREST)) {
                AdvancementHandler.givePlayerAdvancement(player, "glow_up");
            }
        }
    }

    private static void clipContextCheckingTickEvent(PlayerTickEvent event) {
        ServerPlayer player = (ServerPlayer) event.getEntity();
        ServerLevel level = player.serverLevel();

        ClipContext clipContext = new ClipContext(player.getEyePosition(1f),
                player.getEyePosition(1f).add(player.getViewVector(1f).scale(5)),
                ClipContext.Block.OUTLINE,
                ClipContext.Fluid.NONE,
                player);

        HitResult hitResult = level.clip(clipContext);
        if (hitResult.getType() != HitResult.Type.BLOCK) return;

        BlockHitResult blockHit = (BlockHitResult) hitResult;
        BlockPos blockPos = blockHit.getBlockPos();
        BlockState state = level.getBlockState(blockPos);


        if (state.is(ModBlocks.OVERGROWN_ENTORIUM_ORE.get())) {
            AdvancementHandler.givePlayerAdvancement(player, "its_shearing_time");
        } else if (state.is(ModBlocks.SPORE_BLASTER.get()) && level.getBestNeighborSignal(blockPos) > 0) {
            AdvancementHandler.givePlayerAdvancement(player, "bacteria_beacon");
        }
    }

    @SubscribeEvent
    public static void onBlockBroken(BlockEvent.BreakEvent event) {
        if (event.getPlayer().level().isClientSide) return;
        blasterBustedAdvancement(event);
    }

    public static void blasterBustedAdvancement(BlockEvent.BreakEvent event) {
        if (event.getLevel().getBlockState(event.getPos()).getBlock() == ModBlocks.NATURAL_SPORE_BLASTER.get()) {
            AdvancementHandler.givePlayerAdvancement((ServerPlayer)event.getPlayer(), "blaster_busted");
        }
    }

    @SubscribeEvent
    public static void onMobEffectExpired(MobEffectEvent.Expired event) {
        if (event.getEntity().level().isClientSide) return;
        onContaminationEffectExpired(event);
    }

    public static void onContaminationEffectExpired(MobEffectEvent.Expired event) {
        LivingEntity entity = event.getEntity();
        MobEffectInstance mobEffectInstance = event.getEffectInstance();

        if (mobEffectInstance != null && mobEffectInstance.getEffect() == ModMobEffects.CONTAMINATION.getDelegate()) {
            Holder<MobEffect> immunity = ModMobEffects.IMMUNITY.getDelegate();
            entity.addEffect(new MobEffectInstance(immunity, (mobEffectInstance.getAmplifier() + 1 ) * 5 * 20, mobEffectInstance.getAmplifier()));
        }
    }

    @SubscribeEvent
    public static void onCriticalHit(CriticalHitEvent event) {
        if (event.getEntity().level().isClientSide || !event.isCriticalHit()) return;
        spreadContaminationOnCriticalHit(event);
    }

    public static void spreadContaminationOnCriticalHit(CriticalHitEvent event) {
        if (event.getEntity().level().isClientSide()) return;
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
    public static void onLivingBreathing(LivingBreatheEvent event) {
        if (event.getEntity().level().isClientSide) return;
        passiveContaminationDoseReduction(event);
    }

    public static void passiveContaminationDoseReduction(LivingBreatheEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide()) return;
        entity.getData(ModAttachments.CONTAMINATION).addDose(-10);
    }
}