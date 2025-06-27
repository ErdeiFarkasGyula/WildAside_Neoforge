package net.farkas.wildaside.attachments.contamination;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.UnknownNullability;

public class ContaminationImplementation implements IContamination{
    private int dose = 0;

    @Override
    public int getDose() {
        return dose;
    }

    @Override
    public int maxAmp() {
        return 5;
    }

    @Override
    public void setDose(int value) {
        this.dose = value;
    }

    @Override
    public void addDose(int value) {
        this.dose = Math.max(0, this.dose + value);
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("contamination_dose", this.dose);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag compoundTag) {
        this.dose = compoundTag.getInt("contamination_dose");
    }
}
