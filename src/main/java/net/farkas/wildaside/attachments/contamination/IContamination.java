package net.farkas.wildaside.attachments.contamination;

import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;

public interface IContamination extends INBTSerializable<CompoundTag> {
    int getDose();
    int maxAmp();
    void setDose(int value);
    void addDose(int value);
}