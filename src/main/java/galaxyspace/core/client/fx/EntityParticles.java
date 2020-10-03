package galaxyspace.core.client.fx;

import galaxyspace.systems.SolarSystem.moons.triton.dimenson.WorldProviderTriton_WE;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntityParticles extends Particle {

   private float flameScale;
   private boolean isGravity;
   private double speed;
  // private static final ResourceLocation texture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/particle/cloud.png");
  
   	public EntityParticles(World par1World, Vector3 position, Vector3 motion, int age, int particleID,	boolean hasGravity, Vector3 color, double size) {
   		this(par1World, position, motion, age, particleID, hasGravity, color, size, 1.0D);
   	}
	   
	public EntityParticles(World par1World, Vector3 position, Vector3 motion, int age, int particleID,	boolean hasGravity, Vector3 color, double size, double speed) {
		super(par1World, position.x, position.y, position.z, 0.0D, 0.0D, 0.0D);
		this.motionX = this.motionX * 0.009999999776482582D + motion.x;
		this.motionY = this.motionY * 0.009999999776482582D + motion.y;
		this.motionZ = this.motionZ * 0.009999999776482582D + motion.z;
		this.particleScale = (float) (this.particleScale * 5.0D * size);
		this.flameScale = this.particleScale;
		this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
		this.particleMaxAge = (int) (8.0D / (Math.random() * 0.8D + 0.2D)) + age;
		this.setParticleTextureIndex(particleID);

		this.isGravity = hasGravity;
		this.speed = speed;

		if (color == null) {
			this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
		}

		this.particleRed = color.floatX();
		this.particleGreen = color.floatY();
		this.particleBlue = color.floatZ();
		
		
	}
	
   @Override
   public void renderParticle(BufferBuilder worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ)
   {
	   float var8 = ( this.particleAge + partialTicks) / this.particleMaxAge;
	   this.particleScale = this.flameScale * (0.5F - var8 * var8 * 0.5F);
	   super.renderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
      //Minecraft.getMinecraft().renderEngine.bindTexture(texture);
   }

   @Override
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

//   public float getBrightness(float par1) {
//      float var2 = ((float)super.particleAge + par1) / (float)super.particleMaxAge;
//      if(var2 < 0.0F) {
//         var2 = 0.0F;
//      }
//
//      if(var2 > 1.0F) {
//         var2 = 1.0F;
//      }
//
//      float var3 = super.getBrightness(par1);
//      return var3 * var2 + (1.0F - var2);
//   }

   @Override
   public void onUpdate() {
	   this.prevPosX = this.posX;
	   this.prevPosY = this.posY;
	   this.prevPosZ = this.posZ;
		if (this.particleAge++ >= this.particleMaxAge) {
			this.setExpired();
		}

		if (this.particleAge > this.particleMaxAge / 10 && this.isGravity)
			this.motionY -= 0.04D * speed;

		this.motionY += 0.01D * speed;
		//GalaxySpace.debug(particleMaxAge + "");
		if(this.world.provider instanceof WorldProviderTriton_WE)
			if(particleMaxAge > 200 && this.particleAge > this.particleMaxAge / 2) {
				this.motionX -= 0.01D * speed;
				this.motionY *= 0.5D;
				
			}
		
		this.move(super.motionX, super.motionY, super.motionZ);
		this.motionX *= 0.9599999785423279D;
		this.motionY *= 0.9599999785423279D;
		this.motionZ *= 0.9599999785423279D;
		if (this.onGround) {
			this.motionX *= 0.699999988079071D;
			this.motionZ *= 0.699999988079071D;
		}
		/*
		Vec3d vec31 = new Vec3d(this.posX, this.posY, this.posZ);
		Vec3d vec32 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY,	this.posZ + this.motionZ);
		
		RayTraceResult ray = this.world.rayTraceBlocks(vec31, vec32, false, true, false);
		if(ray != null)
		{
			vec32 = new Vec3d(ray.hitVec.x, ray.hitVec.y, ray.hitVec.z);
		}
		
		Entity entity = null;
		List list = this.world.getEntitiesWithinAABB(this,
				new AxisAlignedBB(new BlockPos(this.motionX, this.motionY, this.motionZ)).expand(1.0D, 1.0D, 1.0D));
		double d0 = 0.0D;
		int i;
		float f1;*/
/*
		Vec3d vec31 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
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
		}*/
   }

}
