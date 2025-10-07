package net.farkas.wildaside.util;

import com.mojang.serialization.Codec;
import net.minecraft.util.RandomSource;

import java.util.Map;

public enum LargeMushroomCapShape {
    FLAT(true),
    DOME(true),
    VANILLA_LARGE(true),
    BELL(true),
    FLARED(true),
    PARABOLIC(true),
    CONCAVE(false),
    CONE(false),
    LOBED(true),
    PILZ(false);

    private final boolean canDecorate;

    public static final Codec<LargeMushroomCapShape> CODEC = Codec.STRING.xmap(LargeMushroomCapShape::valueOf, LargeMushroomCapShape::name);

    LargeMushroomCapShape(boolean canDecorate) {
        this.canDecorate = canDecorate;
    }

    public boolean canDecorate() {
        return canDecorate;
    }

    public static LargeMushroomCapShape pickWeightedShape(RandomSource random, Map<LargeMushroomCapShape, Float> weights) {
        float total = 0f;
        for (float w : weights.values()) total += w;

        float r = random.nextFloat() * total;
        float cumulative = 0f;

        for (var entry : weights.entrySet()) {
            cumulative += entry.getValue();
            if (r <= cumulative) return entry.getKey();
        }

        return LargeMushroomCapShape.DOME;
    }
}