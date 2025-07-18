package net.farkas.wildaside.util;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class EnchantmentUtils {
    public static Holder<Enchantment> getEnchtantmentHolder(Level level, ResourceKey<Enchantment> resourceKey) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        return registrylookup.getOrThrow(resourceKey).getDelegate();
    }

    public static Holder<Enchantment> getEnchtantmentHolder(LevelAccessor level, ResourceKey<Enchantment> resourceKey) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        return registrylookup.getOrThrow(resourceKey).getDelegate();
    }

    public static Holder<Enchantment> getEnchtantmentHolder(HolderLookup.Provider level, ResourceKey<Enchantment> resourceKey) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = level.lookupOrThrow(Registries.ENCHANTMENT);
        return registrylookup.getOrThrow(resourceKey).getDelegate();
    }

    public static Enchantment getEnchantment(Level level, ResourceKey<Enchantment> resourceKey) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        return registrylookup.getOrThrow(resourceKey).value();
    }

    public static Enchantment getEnchantment(LevelAccessor level, ResourceKey<Enchantment> resourceKey) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        return registrylookup.getOrThrow(resourceKey).value();
    }

    public static Enchantment getEnchantment(HolderLookup.Provider level, ResourceKey<Enchantment> resourceKey) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = level.lookupOrThrow(Registries.ENCHANTMENT);
        return registrylookup.getOrThrow(resourceKey).value();
    }
}
