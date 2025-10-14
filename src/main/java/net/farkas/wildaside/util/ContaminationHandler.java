package net.farkas.wildaside.util;

import net.farkas.wildaside.attachments.ModAttachments;
import net.farkas.wildaside.effect.ModMobEffects;
import net.farkas.wildaside.entity.custom.vibrion.MucellithEntity;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class ContaminationHandler {
    private static final int MAX_AMPLIFIER = 5;

    public static void addDose(Entity entity, int dose) {
        if (!(entity instanceof LivingEntity livingEntity)) return;

        var data = livingEntity.getData(ModAttachments.CONTAMINATION);
        data.addDose(dose);
        applyContamination(livingEntity, data.getDose());
    }

    public static void setDose(Entity entity, int dose) {
        if (!(entity instanceof LivingEntity livingEntity)) return;

        var data = livingEntity.getData(ModAttachments.CONTAMINATION);
        data.setDose(dose);
        applyContamination(livingEntity, data.getDose());
    }

    public static int getDose(Entity entity) {
        if (!(entity instanceof LivingEntity livingEntity)) return 0;

        var data = livingEntity.getData(ModAttachments.CONTAMINATION);
        return data.getDose();
    }

    public static void applyContamination(LivingEntity entity, int dose) {
        if (dose == 0 || entity instanceof MucellithEntity) return;

        Holder<MobEffect> immunity = ModMobEffects.IMMUNITY.getDelegate();
        Holder<MobEffect> contamination = ModMobEffects.CONTAMINATION.getDelegate();

        int amplifier = Mth.clamp(dose / 1000, 0, MAX_AMPLIFIER);;
        int contaminationDuration = calculateContaminationDuration(amplifier);

        MobEffectInstance existing = entity.getEffect(contamination);

        if (existing == null) {
            if (entity.hasEffect(immunity)) {
                int immunityAmp = entity.getEffect(immunity).getAmplifier();
                if (immunityAmp >= amplifier) return;
            }

            entity.addEffect(new MobEffectInstance(contamination, contaminationDuration, amplifier));
        } else {
            int existingAmp = existing.getAmplifier();
            int existingDuration = existing.getDuration();

            amplifier = Mth.clamp(dose / 1000, existingAmp, MAX_AMPLIFIER);
            contaminationDuration = calculateContaminationDuration(amplifier);

            if (entity.hasEffect(immunity)) {
                int immunityAmp = entity.getEffect(immunity).getAmplifier();
                if (immunityAmp >= amplifier) return;
            }

            if (amplifier > existingAmp || existingDuration < calculateContaminationDuration(amplifier)) {
                entity.addEffect(new MobEffectInstance(contamination, contaminationDuration, amplifier));
            }
        }

        if (amplifier >= 4) {
            entity.addEffect(new MobEffectInstance(MobEffects.POISON, (amplifier + 1) * 2 * 20, amplifier - 3, false, true));
            if (amplifier >= 5) {
                entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, (amplifier + 1) * 3 * 20, amplifier - 4, false, true));
            }
        }
    }

    public static int calculateContaminationDuration(int amplifier) {
        return (amplifier + 1) * 10 * 20;
    }
}
