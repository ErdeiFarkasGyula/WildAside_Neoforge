package net.farkas.wildaside.particle.custom;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class WindParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    protected WindParticle(ClientLevel level, double x, double y, double z, double vx, double vy, double vz, SpriteSet sprites) {
        super(level, x, y, z, vx, vy, vz);
        this.sprites = sprites;
        this.setSpriteFromAge(sprites);

        this.lifetime = 40 + this.random.nextInt(10);
        this.quadSize = 0.8F + this.random.nextFloat() * 0.3F;

        float r = 0.7F + this.random.nextFloat() * 0.1F;
        float g = 0.9F + this.random.nextFloat() * 0.1F;
        float b = 1.0F;
        this.setColor(r, g, b);

        this.alpha = 0.0F;
        this.hasPhysics = false;
    }

    @Override
    public void tick() {
        if (this.age++ >= this.lifetime) {
            this.remove();
            return;
        }

        float halfLife = this.lifetime / 2.0F;
        if (this.age < halfLife)
            this.alpha = Mth.clamp(this.age / halfLife, 0.0F, 0.6F);
        else
            this.alpha = Mth.clamp(1.0F - (this.age - halfLife) / halfLife, 0.0F, 0.6F);

        this.x += this.xd;
        this.y += this.yd;
        this.z += this.zd;

        this.xd *= 0.98;
        this.yd *= 0.98;
        this.zd *= 0.98;

        this.setSpriteFromAge(this.sprites);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new WindParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.sprites);
        }
    }

    public static class SmallProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public SmallProvider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            Particle particle = new WindParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.sprites);
            particle.scale(0.4f);
            return particle;
        }
    }
}