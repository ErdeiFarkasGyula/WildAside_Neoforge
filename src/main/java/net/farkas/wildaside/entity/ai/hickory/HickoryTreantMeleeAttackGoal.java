package net.farkas.wildaside.entity.ai.hickory;

import net.farkas.wildaside.entity.custom.hickory.HickoryTreantEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;

public class HickoryTreantMeleeAttackGoal extends MeleeAttackGoal {
    private final HickoryTreantEntity entity;
    private static final int maxRange = 3;

    public HickoryTreantMeleeAttackGoal(PathfinderMob pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        this.entity = (HickoryTreantEntity) mob;
    }

    @Override
    public boolean canUse() {
        LivingEntity target = entity.getTarget();
        if (target == null || !target.isAlive()) return false;
        double distance = entity.distanceTo(target);
        return distance <= maxRange;
    }

    @Override
    public boolean canContinueToUse() {
        return canUse();
    }


    @Override
    protected void checkAndPerformAttack(LivingEntity target) {
        if (this.canPerformAttack(target)) {
            this.resetAttackCooldown();
            this.mob.swing(InteractionHand.MAIN_HAND);
            this.mob.swing(InteractionHand.OFF_HAND);
            this.mob.doHurtTarget(target);
        }
    }
}
