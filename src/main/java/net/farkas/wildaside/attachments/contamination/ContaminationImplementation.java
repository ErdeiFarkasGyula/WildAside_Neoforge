package net.farkas.wildaside.attachments.contamination;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
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
    public CompoundTag read(IAttachmentHolder iAttachmentHolder, ValueInput valueInput) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("contamination_dose", this.dose);
        return tag;
    }

    @Override
    public boolean write(CompoundTag tag, ValueOutput valueOutput) {
        this.dose = tag.getInt("contamination_dose").get();
        return false;
    }
}
