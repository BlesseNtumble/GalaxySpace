package galaxyspace.core.client.fx;

import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleRainCustom extends Particle
{
    float smokeParticleScale;

    public ParticleRainCustom(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double motionX, double motionY, double motionZ, float scale, Vector3 color)
    {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
        this.motionX *= 0.30000001192092896D;
        this.motionY = Math.random() * 0.20000000298023224D + 0.10000000149011612D;
        this.motionZ *= 0.30000001192092896D;
        this.particleRed = color.floatX();//(float)(Math.random() * 0.10000001192092896D + 0.8);
        this.particleGreen = color.floatY();
        this.particleBlue = color.floatZ();//(float)(Math.random() * 0.10000001192092896D);
        this.particleAlpha = 1.0F;
//        this.particleScale *= 0.75F;
        this.particleScale *= scale;
        this.particleGravity = 0.06F;
        this.smokeParticleScale = this.particleScale;
        this.particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
        //this.particleMaxAge = (int)((float)this.particleMaxAge * scale);
        this.canCollide = true;
        this.setParticleTextureIndex(19 + this.rand.nextInt(4));
        
    }

    @Override
    public void renderParticle(BufferBuilder worldRendererIn, Entity entityIn, float partialTicks, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_)
    {
      /*  GlStateManager.disableLighting();
        float f = (float) Math.pow(this.particleAge / 11.0, 2.0F);
        f = Math.max(f, 0.1F);
        this.particleAlpha = this.particleAlpha * 0.994F;
        this.particleScale = this.smokeParticleScale * f;*/
        super.renderParticle(worldRendererIn, entityIn, partialTicks, p_180434_4_, p_180434_5_, p_180434_6_, p_180434_7_, p_180434_8_);
    }

    @Override
    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= (double)this.particleGravity;
        this.move(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863D;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= 0.9800000190734863D;
        
        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setExpired();
        }

        if (this.onGround)
        {
        	

            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
        }
    }
}