package net.farkas.wildaside.particle.custom;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class WindParticle extends TextureSheetParticle {

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel level,
                                       double x, double y, double z, double vx, double vy, double vz) {
            Direction facing = Direction.getNearest(vx, vy, vz);
            return new WindParticle(level, x, y, z, vx, vy, vz, facing, sprites);
        }
    }

    private static final float SPEED = 0.07f;

    private final SpriteSet spriteSet;
    private final Direction facing;

    private float rCol, gCol, bCol, alpha;

    protected WindParticle(ClientLevel world, double x, double y, double z,
                           double vx, double vy, double vz, Direction facing, SpriteSet spriteSet) {
        super(world, x, y, z);
        this.spriteSet = spriteSet;
        this.facing = facing;
        this.setSpriteFromAge(spriteSet);

        this.xd = vx * SPEED;
        this.yd = vy * SPEED;
        this.zd = vz * SPEED;

        this.quadSize = 0.4f + random.nextFloat() * 0.1f;
        this.lifetime = 20 + random.nextInt(10);
        this.gravity = 0.0f;

        float tint = 0.8f + random.nextFloat() * 0.2f;
        this.rCol = 0.85f * tint;
        this.gCol = 0.95f * tint;
        this.bCol = 1.0f * tint;
        this.alpha = 0.0f;
    }

    @Override
    public void tick() {
        super.tick();

        float lifeRatio = (float) age / (float) lifetime;
        this.alpha = (float) Math.sin(lifeRatio * Math.PI) * 0.5f;

        // small turbulence
        this.xd += (random.nextDouble() - 0.5) * 0.001;
        this.yd += (random.nextDouble() - 0.5) * 0.001;
        this.zd += (random.nextDouble() - 0.5) * 0.001;
    }

    @Override
    public void render(VertexConsumer buffer, Camera camera, float partialTicks) {
        double cx = Mth.lerp(partialTicks, xo, x) - camera.getPosition().x();
        double cy = Mth.lerp(partialTicks, yo, y) - camera.getPosition().y();
        double cz = Mth.lerp(partialTicks, zo, z) - camera.getPosition().z();

        float half = this.getQuadSize(partialTicks) / 2f;

        // Hardcoded per-facing axes
        Vector3f forward = new Vector3f(facing.getStepX(), facing.getStepY(), facing.getStepZ()).normalize();
        Vector3f up = forward.y != 0 ? new Vector3f(0,0,-1) : new Vector3f(0,1,0); // horizontal for up/down, vertical for horizontal facings
        Vector3f right = forward.cross(up, new Vector3f()).normalize();
        up = right.cross(forward, new Vector3f()).normalize();

        // Quad vertices
        Vector3f v0 = new Vector3f(right).mul(-half).add(new Vector3f(up).mul(-half));
        Vector3f v1 = new Vector3f(right).mul(-half).add(new Vector3f(up).mul( half));
        Vector3f v2 = new Vector3f(right).mul( half).add(new Vector3f(up).mul( half));
        Vector3f v3 = new Vector3f(right).mul( half).add(new Vector3f(up).mul(-half));

        for (Vector3f v : new Vector3f[]{v0,v1,v2,v3}) {
            v.add((float)cx,(float)cy,(float)cz);
        }

        float u0 = this.getU0();
        float u1 = this.getU1();
        float v0t = this.getV0();
        float v1t = this.getV1();

        int light = this.getLightColor(partialTicks);

        // Front face
        buffer.addVertex(v0.x(), v0.y(), v0.z()).setUv(u1,v1t).setColor(rCol,gCol,bCol,alpha).setLight(light);
        buffer.addVertex(v1.x(), v1.y(), v1.z()).setUv(u1,v0t).setColor(rCol,gCol,bCol,alpha).setLight(light);
        buffer.addVertex(v2.x(), v2.y(), v2.z()).setUv(u0,v0t).setColor(rCol,gCol,bCol,alpha).setLight(light);
        buffer.addVertex(v3.x(), v3.y(), v3.z()).setUv(u0,v1t).setColor(rCol,gCol,bCol,alpha).setLight(light);

        // Back face (two-sided)
        buffer.addVertex(v0.x(), v0.y(), v0.z()).setUv(u0,v0t).setColor(rCol,gCol,bCol,alpha).setLight(light);
        buffer.addVertex(v3.x(), v3.y(), v3.z()).setUv(u1,v0t).setColor(rCol,gCol,bCol,alpha).setLight(light);
        buffer.addVertex(v2.x(), v2.y(), v2.z()).setUv(u1,v1t).setColor(rCol,gCol,bCol,alpha).setLight(light);
        buffer.addVertex(v1.x(), v1.y(), v1.z()).setUv(u0,v1t).setColor(rCol,gCol,bCol,alpha).setLight(light);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }
}