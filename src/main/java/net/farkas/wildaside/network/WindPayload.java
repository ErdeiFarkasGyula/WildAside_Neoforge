package net.farkas.wildaside.network;

import io.netty.buffer.ByteBuf;
import net.farkas.wildaside.WildAside;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public record WindPayload(double x, double y, double z, float strength) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<WindPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(WildAside.MOD_ID, "wind_sync"));

    public static final StreamCodec<ByteBuf, WindPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.DOUBLE, WindPayload::x,
                    ByteBufCodecs.DOUBLE, WindPayload::y,
                    ByteBufCodecs.DOUBLE, WindPayload::z,
                    ByteBufCodecs.FLOAT,  WindPayload::strength,
                    WindPayload::new
            );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public WindData toWindData() {
        return new WindData(new Vec3(x, y, z), strength);
    }

    public static WindPayload fromWindData(WindData wd) {
        Vec3 dir = wd.direction();
        return new WindPayload(dir.x, dir.y, dir.z, wd.strength());
    }
}