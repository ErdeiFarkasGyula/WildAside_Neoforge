package net.farkas.wildaside.attachments;

import net.farkas.wildaside.WildAside;
import net.farkas.wildaside.attachments.contamination.ContaminationImplementation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModAttachments {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, WildAside.MOD_ID);

    public static final Supplier<AttachmentType<ContaminationImplementation>> CONTAMINATION =
            ATTACHMENT_TYPES.register("contamination", () -> AttachmentType.serializable(ContaminationImplementation::new).build());

    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }
}
