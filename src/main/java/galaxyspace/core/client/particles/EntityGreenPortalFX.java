package galaxyspace.core.client.particles;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class EntityGreenPortalFX extends EntityFX
{
	private float portalParticleScale;
	private double portalPosX;
	private double portalPosY;
	private double portalPosZ;

	public EntityGreenPortalFX(World par1World, Vector3 position, Vector3 motion) 
	   {
		super(par1World, position.x, position.y, position.z, 0.0D, 0.0D, 0.0D);
		this.motionX = motion.x;
		this.motionY = motion.y;
		this.motionZ = motion.z;
		this.portalPosX = this.posX = position.x;
		this.portalPosY = this.posY = position.y;
		this.portalPosZ = this.posZ = position.z;
		this.portalParticleScale = this.particleScale = this.rand.nextFloat() * 0.2F + 0.5F;
		this.particleRed = 0.0F;
		this.particleGreen = 0.6F;
		this.particleBlue = 0.1F;
		this.particleMaxAge = (int)(Math.random() * 10.0D) + 40;
		this.noClip = true;
		this.setParticleTextureIndex((int)(Math.random() * 8.0D));
	}

	@Override
	public void renderParticle(Tessellator tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		float f = (this.particleAge + par2) / this.particleMaxAge;
		f = 1.0F - f;
		f *= f;
		f = 1.0F - f;
		this.particleScale = this.portalParticleScale * f;
		super.renderParticle(tessellator, par2, par3, par4, par5, par6, par7);
	}

	@Override
	public int getBrightnessForRender(float light)
	{
		int i = super.getBrightnessForRender(light);
		float f = (float)this.particleAge / (float)this.particleMaxAge;
		f *= f;
		f *= f;
		int j = i & 255;
		int k = i >> 16 & 255;
		k += (int)(f * 15.0F * 16.0F);

		if (k > 240)
		{
			k = 240;
		}
		return j | k << 16;
	}

	@Override
	public float getBrightness(float light)
	{
		float f1 = super.getBrightness(light);
		float f2 = (float)this.particleAge / (float)this.particleMaxAge;
		f2 = f2 * f2 * f2 * f2;
		return f1 * (1.0F - f2) + f2;
	}

	@Override
	public void onUpdate()
	{
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		float f1 = (float)this.particleAge / (float)this.particleMaxAge;
		float f2 = f1;
		f1 = -f1 + f1 * f1 * 2.0F;
		f1 = 1.0F - f1;
		this.posX = this.portalPosX + this.motionX * f1;
		this.posY = this.portalPosY + this.motionY * f1 + (1.0F - f2);
		this.posZ = this.portalPosZ + this.motionZ * f1;

		if (this.particleAge++ >= this.particleMaxAge)
		{
			this.setDead();
		}
	}
}