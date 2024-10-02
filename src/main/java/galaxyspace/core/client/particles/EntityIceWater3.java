package galaxyspace.core.client.particles;



import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.systems.SolarSystem.moons.triton.dimension.WorldProviderTriton;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class EntityIceWater3 extends EntityFX {

   private float flameScale;
   private boolean isGravity;
  // private static final ResourceLocation texture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/particle/cloud.png");
   
   public EntityIceWater3(World par1World, Vector3 position, Vector3 motion, int age, int particleID, boolean hasGravity, Vector3 color, double size) 
   {
	  super(par1World, position.x, position.y, position.z, 0.0D, 0.0D, 0.0D);
      super.motionX = super.motionX * 0.009999999776482582D + motion.x;
      super.motionY = super.motionY * 0.009999999776482582D + motion.y;
      super.motionZ = super.motionZ * 0.009999999776482582D + motion.z;
      super.particleScale = (float)((double)super.particleScale * 5.0D * size);
      this.flameScale = super.particleScale;
      super.particleRed = super.particleGreen = super.particleBlue = 1.0F;
      super.particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D)) + age;
      this.setParticleTextureIndex(particleID);
      
      this.isGravity = hasGravity;
      
      if(color == null) 
      {
    	  super.particleRed = super.particleGreen = super.particleBlue = 1.0F;
      }
      
      super.particleRed = color.floatX();
      super.particleGreen = color.floatY();
      super.particleBlue = color.floatZ();
   }

   public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {
      float var8 = ((float)super.particleAge + par2) / (float)super.particleMaxAge;
      super.particleScale = this.flameScale * (0.5F - var8 * var8 * 0.5F);
      super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
      //Minecraft.getMinecraft().renderEngine.bindTexture(texture);
   }

   public int getBrightnessForRender(float par1) {
      float var2 = ((float)super.particleAge + par1) / (float)super.particleMaxAge;
      if(var2 < 0.0F) {
         var2 = 0.0F;
      }

      if(var2 > 1.0F) {
         var2 = 1.0F;
      }

      int var3 = super.getBrightnessForRender(par1);
      int var4 = var3 & 255;
      int var5 = var3 >> 16 & 255;
      var4 += (int)(var2 * 15.0F * 16.0F);
      if(var4 > 240) {
         var4 = 240;
      }

      return var4 | var5 << 16;
   }

   public float getBrightness(float par1) {
      float var2 = ((float)super.particleAge + par1) / (float)super.particleMaxAge;
      if(var2 < 0.0F) {
         var2 = 0.0F;
      }

      if(var2 > 1.0F) {
         var2 = 1.0F;
      }

      float var3 = super.getBrightness(par1);
      return var3 * var2 + (1.0F - var2);
   }

   public void onUpdate() {
		super.prevPosX = super.posX;
		super.prevPosY = super.posY;
		super.prevPosZ = super.posZ;
		if (super.particleAge++ >= super.particleMaxAge) {
			this.setDead();
		}

		if (super.particleAge > super.particleMaxAge / 10 && this.isGravity)
			super.motionY -= 0.04D;

		if(this.worldObj.provider instanceof WorldProviderTriton)
			if(super.particleAge > super.particleMaxAge / 2)
				super.motionX -= 0.04D;
		
		super.motionY += 0.01D;
		this.moveEntity(super.motionX, super.motionY, super.motionZ);
		super.motionX *= 0.9599999785423279D;
		super.motionY *= 0.9599999785423279D;
		super.motionZ *= 0.9599999785423279D;
		if (super.onGround) {
			super.motionX *= 0.699999988079071D;
			super.motionZ *= 0.699999988079071D;
		}

		Vec3 vec31 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
		Vec3 vec3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY,	this.posZ + this.motionZ);
		MovingObjectPosition movingobjectposition = this.worldObj.func_147447_a(vec31, vec3, false, true, false);

		if (movingobjectposition != null) {
			vec3 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord,
					movingobjectposition.hitVec.zCoord);
		}

		
		Entity entity = null;
		List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this,
				this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
		double d0 = 0.0D;
		int i;
		float f1;

		for (i = 0; i < list.size(); ++i) {
			Entity entity1 = (Entity) list.get(i);

			if (entity1.canBeCollidedWith()) {
				f1 = 0.3F;
				AxisAlignedBB axisalignedbb1 = entity1.boundingBox.expand((double) f1, (double) f1, (double) f1);
				MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec31, vec3);

				if (movingobjectposition1 != null) {
					double d1 = vec31.distanceTo(movingobjectposition1.hitVec);

					if (d1 < d0 || d0 == 0.0D) {
						entity = entity1;
						d0 = d1;
					}
				}
			}
		}

		if (entity != null) {
			movingobjectposition = new MovingObjectPosition(entity);
		}

		if (movingobjectposition != null) {
			if (movingobjectposition.typeOfHit.equals(MovingObjectType.ENTITY)) {
				 
				this.setDead();
			}
		}
   }
}
