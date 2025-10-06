package net.farkas.wildaside.util;

import com.mojang.serialization.Codec;
import net.minecraft.util.RandomSource;

import java.util.Map;

public enum LargeMushroomCapShape {
    DOME, FLAT, CONCAVE, VANILLA_LARGE;

    public static final Codec<LargeMushroomCapShape> CODEC = Codec.STRING.xmap(LargeMushroomCapShape::valueOf, LargeMushroomCapShape::name);

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