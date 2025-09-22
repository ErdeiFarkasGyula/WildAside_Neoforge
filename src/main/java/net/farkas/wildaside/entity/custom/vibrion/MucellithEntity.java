package net.farkas.wildaside.entity.custom.vibrion;

import net.farkas.wildaside.effect.ModMobEffects;
import net.farkas.wildaside.entity.ai.mucellith.MucellithAttackGoal;
import net.farkas.wildaside.entity.ai.mucellith.MucellithRandomLookAroundGoal;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class MucellithEntity extends PathfinderMob implements RangedAttackMob {
    private static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(MucellithEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DEFENDING = SynchedEntityData.defineId(MucellithEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> HAS_DEFENDED = SynchedEntityData.defineId(MucellithEntity.class, EntityDataSerializers.BOOLEAN);

    private int soundCooldown = 0;
    private final int soundCooldownMax = 20;

    public final AnimationState idleAnimation = new AnimationState();
    private final int idleAnimationMax = 40;
    private int idleAnimationTimeout = 0;

    public final AnimationState attackAnimation = new AnimationState();
    public final int attackAnimationMax = 60;
    public int attackAnimationTimeout = 0;

    public final AnimationState defenseAnimation = new AnimationState();
    public final AnimationState defenseAnimationReverse = new AnimationState();
    private final int defenseAnimationReverseMax = 15;
    private int defenseAnimationReverseTimeout = 15;

    public MucellithEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MucellithAttackGoal(this, 60, 8f));
        this.goalSelector.addGoal(4, new MucellithRandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true,
                (entity) -> entity.hasEffect(ModMobEffects.CONTAMINATION.getDelegate()) && !(entity instanceof MucellithEntity)));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, LivingEntity.class, true,
                (entity) -> entity.hasEffect(ModMobEffects.CONTAMINATION.getDelegate()) && !(entity instanceof MucellithEntity)));

    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 40)
                .add(Attributes.FOLLOW_RANGE, 32)
                .add(Attributes.MOVEMENT_SPEED, 0)
                .add(Attributes.JUMP_STRENGTH, 0)
                .add(Attributes.FLYING_SPEED, 0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (belowHealthThreshold(0.25f) && !hasDefended()) {
            setDefending(true);
            this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, MobEffectInstance.INFINITE_DURATION, 6, false, true));
            this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, MobEffectInstance.INFINITE_DURATION, 1, false, true));
        }

        if (isDefending()) {
            if (this.soundCooldown <= 0) {
                this.playSound(SoundEvents.WARDEN_HEARTBEAT, 1f, 1.5f);
                soundCooldown = soundCooldownMax;
            } else {
                --this.soundCooldown;
            }

            if (!belowHealthThreshold(0.5f)) {
                this.playSound(SoundEvents.WARDEN_ANGRY, 1f, 1.5f);
                this.removeEffect(MobEffects.DAMAGE_RESISTANCE);
                this.removeEffect(MobEffects.REGENERATION);
                setHasDefended(true);
                setDefending(false);
            }
        }
    }

    private void setupAnimationStates() {
        if (isDefending()) {
            if (!defenseAnimation.isStarted()) {
                defenseAnimation.start(tickCount);
            }
        } else {
            if (this.idleAnimationTimeout <= 0) {
                this.idleAnimationTimeout = idleAnimationMax;
                this.idleAnimation.start(this.tickCount);
            } else {
                --this.idleAnimationTimeout;
            }

            if (hasDefended()) {
                if (defenseAnimationReverseTimeout == defenseAnimationReverseMax) {
                    defenseAnimationReverse.start(tickCount);
                    --defenseAnimationReverseTimeout;
                } else
                    if (defenseAnimationReverseTimeout > 0) {
                        --defenseAnimationReverseTimeout;
                    } else {
                        defenseAnimationReverse.stop();
                    }

            }
            defenseAnimation.stop();
        }

        if (isAttacking()) {
            if (attackAnimationTimeout <= 0) {
                attackAnimation.start(tickCount);
                attackAnimationTimeout = attackAnimationMax;
            }
            --attackAnimationTimeout;
        } else {
            if (attackAnimation.isStarted()) {
                attackAnimation.stop();
                attackAnimationTimeout = 0;
            }
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(ATTACKING, false);
        pBuilder.define(DEFENDING, false);
        pBuilder.define(HAS_DEFENDED, false);
    }

    @Override
    public void performRangedAttack(LivingEntity pTarget, float pVelocity) {
        this.attackAnimation.start(0);
        SporeBombEntity sporeBomb = new SporeBombEntity(this.level(), this, this.random.nextFloat());
        double d0 = pTarget.getEyeY() - (double)1.1F;
        double d1 = pTarget.getX() - this.getX();
        double d2 = d0 - sporeBomb.getY() - 0.5f;
        double d3 = pTarget.getZ() - this.getZ();
        double d4 = Math.sqrt(d1 * d1 + d3 * d3) * (double)0.2F;
        sporeBomb.shoot(d1, d2 + d4, d3, 1.6F, 2);
        this.playSound(SoundEvents.SNOWBALL_THROW, 1.0F, 0.2f);
        this.level().addFreshEntity(sporeBomb);
    }

    @Override
    public void push(Entity pEntity) {
        //NO PUSH!
    }

    @Override
    public void push(double pX, double pY, double pZ) {
        //NO PUSH!
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isPushedByFluid(net.neoforged.neoforge.fluids.FluidType type) { return false; }

    @Override
    public boolean ignoreExplosion(Explosion pExplosion) { return true; }

    public void setAttacking(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }

    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }

    public void setDefending(boolean defending) {
        this.entityData.set(DEFENDING, defending);
    }

    public boolean isDefending() {
        return this.entityData.get(DEFENDING);
    }

    public void setHasDefended(boolean hasDefended) {
        this.entityData.set(HAS_DEFENDED, hasDefended);
    }

    public boolean hasDefended() {
        return this.entityData.get(HAS_DEFENDED);
    }

    public boolean belowHealthThreshold(float threshold) {
        return this.getHealth() / this.getMaxHealth() <= threshold;
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return SoundEvents.WARDEN_AMBIENT;
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.WARDEN_HURT;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.WARDEN_DEATH;
    }
}
