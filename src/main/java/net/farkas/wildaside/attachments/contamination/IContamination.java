package net.farkas.wildaside.attachments.contamination;

import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;

public interface IContamination extends IAttachmentSerializer<CompoundTag> {
    int getDose();
    int maxAmp();
    void setDose(int value);
    void addDose(int value);
}