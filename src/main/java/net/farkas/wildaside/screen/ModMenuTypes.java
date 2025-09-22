package net.farkas.wildaside.screen;

import net.farkas.wildaside.WildAside;
import net.farkas.wildaside.screen.bioengineering_workstation.BioengineeringWorkstationMenu;
import net.farkas.wildaside.screen.potion_blaster.PotionBlasterMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, WildAside.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<BioengineeringWorkstationMenu>> BIOENGINEERING_WORKSTATION_MENU =
            registerMenuType("bioengineering_workstation_menu", BioengineeringWorkstationMenu::new);

    public static final DeferredHolder<MenuType<?>, MenuType<PotionBlasterMenu>> POTION_BLASTER_MENU =
            registerMenuType("potion_blaster_menu", PotionBlasterMenu::new);

    private static <T extends AbstractContainerMenu> DeferredHolder<MenuType<?>, MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}