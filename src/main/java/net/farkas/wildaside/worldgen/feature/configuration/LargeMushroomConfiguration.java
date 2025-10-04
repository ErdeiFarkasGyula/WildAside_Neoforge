package net.farkas.wildaside.worldgen.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.List;

public class LargeMushroomConfiguration implements FeatureConfiguration {
    public static final Codec<LargeMushroomConfiguration> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("minHeight").forGetter(cfg -> cfg.minHeight),
                    Codec.INT.fieldOf("maxHeight").forGetter(cfg -> cfg.maxHeight),
                    Codec.INT.fieldOf("maxBranches").forGetter(cfg -> cfg.maxBranches),
                    BlockStateProvider.CODEC.fieldOf("capBlock").forGetter(cfg -> cfg.capBlock),
                    BlockStateProvider.CODEC.fieldOf("stemBlock").forGetter(cfg -> cfg.stemBlock),
                    BlockStateProvider.CODEC.fieldOf("woodBlock").forGetter(cfg -> cfg.woodBlock),
                    BlockStateProvider.CODEC.listOf().fieldOf("validBaseBlocks").forGetter(cfg -> cfg.validBaseBlocks),
                    BlockStateProvider.CODEC.listOf().fieldOf("decoratorBlocks").forGetter(cfg -> cfg.decoratorBlocks)
            ).apply(instance, LargeMushroomConfiguration::new)
    );

    public final int minHeight;
    public final int maxHeight;
    public final int maxBranches;
    public final BlockStateProvider capBlock;
    public final BlockStateProvider stemBlock;
    public final BlockStateProvider woodBlock;
    public final List<BlockStateProvider> validBaseBlocks;
    public final List<BlockStateProvider> decoratorBlocks;

    public LargeMushroomConfiguration(int minHeight, int maxHeight, int maxBranches,
                                      BlockStateProvider capBlock, BlockStateProvider stemBlock, BlockStateProvider woodBlock,
                                      List<BlockStateProvider> validBaseBlocks, List<BlockStateProvider> decoratorBlocks) {
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.capBlock = capBlock;
        this.stemBlock = stemBlock;
        this.woodBlock = woodBlock;
        this.maxBranches = maxBranches;
        this.validBaseBlocks = validBaseBlocks;
        this.decoratorBlocks = decoratorBlocks;
    }
}