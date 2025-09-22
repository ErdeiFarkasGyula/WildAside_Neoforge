package net.farkas.wildaside.effect.custom;

import net.farkas.wildaside.WildAside;
import net.farkas.wildaside.effect.ModMobEffects;
import net.farkas.wildaside.particle.ModParticles;
import net.farkas.wildaside.util.AdvancementHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Lifesteal extends MobEffect {
    public Lifesteal(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }
}

@EventBusSubscriber(modid = WildAside.MOD_ID)
class LifestealHandler {
    private static class Task {
        final ServerLevel level;
        final UUID attackerId, targetId;
        final float healAmount;
        final int totalTicks;
        int ticksElapsed = 0;

        Task(ServerLevel level, UUID attackerId, UUID targetId, float healAmount, int totalTicks) {
            this.level = level;
            this.attackerId = attackerId;
            this.targetId = targetId;
            this.healAmount = healAmount;
            this.totalTicks = totalTicks;
        }
    }

    private static final List<Task> TASKS = new LinkedList<>();

    @SubscribeEvent
    public static void applyLifesteal(LivingIncomingDamageEvent event) {
        DamageSource src = event.getSource();
        if (!(src.getEntity() instanceof Player attacker)) return;

        MobEffectInstance lifesteal = attacker.getEffect(ModMobEffects.LIFESTEAL.getDelegate());
        if (lifesteal == null) return;

        float perc = (lifesteal.getAmplifier() + 1) / 10f;
        float max = (lifesteal.getAmplifier() + 1) * 3f;
        float heal = event.getAmount() * perc;
        if (heal > max) heal = max;

        LivingEntity target = event.getEntity();
        if (attacker.level() instanceof ServerLevel lvl) {
            double dist = attacker.distanceTo(target);
            int delay = Mth.ceil(dist * 4);
            TASKS.add(new Task(lvl, attacker.getUUID(), target.getUUID(), heal, delay));
        }
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Pre event) {
        Iterator<Task> it = TASKS.iterator();
        while (it.hasNext()) {
            Task t = it.next();
            var attacker = t.level.getEntity(t.attackerId);
            var target   = t.level.getEntity(t.targetId);

            if (!(attacker instanceof LivingEntity a) || !(target instanceof LivingEntity b)) {
                it.remove();
                continue;
            }

            double ax = a.getX(), ay = a.getY() + a.getBbHeight() * 0.5, az = a.getZ();
            double tx = b.getX(), ty = b.getY() + b.getBbHeight() * 0.5, tz = b.getZ();

            double prog = (double)t.ticksElapsed / t.totalTicks;
            double px = Mth.lerp(prog, tx, ax);
            double py = Mth.lerp(prog, ty, ay);
            double pz = Mth.lerp(prog, tz, az);

            t.level.sendParticles(ModParticles.LIFESTEAL_PARTICLE.get(), px, py, pz, 1, 0, 0, 0, 0);

            t.ticksElapsed++;
            if (t.ticksElapsed >= t.totalTicks) {
                if (a instanceof ServerPlayer serverPlayer) {
                    AdvancementHandler.givePlayerAdvancement(serverPlayer, "life_leech");
                }
                a.heal(t.healAmount);
                it.remove();
            }
        }
    }
}