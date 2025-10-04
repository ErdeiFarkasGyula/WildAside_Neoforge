package net.farkas.wildaside.worldgen.feature.custom;

import com.mojang.serialization.Codec;
import net.farkas.wildaside.worldgen.feature.configuration.LargeMushroomConfiguration;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class LargeMushroomFeature extends Feature<LargeMushroomConfiguration> {
    public LargeMushroomFeature(Codec<LargeMushroomConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<LargeMushroomConfiguration> featurePlaceContext) {
        return false;
    }
}
