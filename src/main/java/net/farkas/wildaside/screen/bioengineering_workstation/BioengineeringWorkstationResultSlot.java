package net.farkas.wildaside.screen.bioengineering_workstation;

import net.farkas.wildaside.WildAside;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class BioengineeringWorkstationResultSlot extends SlotItemHandler {
    private final Player player;

    public BioengineeringWorkstationResultSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, Player player) {
        super(itemHandler, index, xPosition, yPosition);
        this.player = player;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return false;
    }

    @Override
    public void onTake(Player pPlayer, ItemStack pStack) {
        super.onTake(pPlayer, pStack);

        if (!player.level().isClientSide && player instanceof ServerPlayer serverPlayer) {
            ResourceLocation id = ResourceLocation.fromNamespaceAndPath(WildAside.MOD_ID, "we_need_to_cook");
            AdvancementHolder adv = serverPlayer.server.getAdvancements().get(id);
            if (adv != null) {
                AdvancementProgress progress = serverPlayer.getAdvancements().getOrStartProgress(adv);
                if (!progress.isDone()) {
                    for (String criterion : progress.getRemainingCriteria()) {
                        serverPlayer.getAdvancements().award(adv, criterion);
                    }
                }
            }
        }
    }
}
