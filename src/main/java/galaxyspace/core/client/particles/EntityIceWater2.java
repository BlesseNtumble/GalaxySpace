package galaxyspace.core.client.particles;



import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class EntityIceWater2 extends EntityFX {

   private float flameScale;
   private static final ResourceLocation texture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/particle/cloud.png");
   //Этот метод скопипастен полностью, кроме того, что помечено.


   public EntityIceWater2(World par1World, Vector3 position, Vector3 motion) {
	   
	   super(par1World, position.x, position.y, position.z, 0.0D, 0.0D, 0.0D);
      super.motionX = super.motionX * 0.009999999776482582D + motion.x;
      super.motionY = super.motionY * 0.009999999776482582D + motion.y;
      super.motionZ = super.motionZ * 0.009999999776482582D + motion.z;
      super.particleScale = (float)((double)super.particleScale * 5.0D);
      this.flameScale = super.particleScale;
      super.particleRed = super.particleGreen = super.particleBlue = 1.0F;
      super.particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D)) + 4;
      this.setParticleTextureIndex(17); //84 - пар квадратами
   }

   public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {
      float var8 = ((float)super.particleAge + par2) / (float)super.particleMaxAge;
      super.particleScale = this.flameScale * (1.0F - var8 * var8 * 0.5F);
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
      if(super.particleAge++ >= super.particleMaxAge) {
         this.setDead();
      }

      super.motionY += 0.004D;
      this.moveEntity(super.motionX, super.motionY, super.motionZ);
      super.motionX *= 0.9599999785423279D;
      super.motionY *= 0.9599999785423279D;
      super.motionZ *= 0.9599999785423279D;
      if(super.onGround) {
         super.motionX *= 0.699999988079071D;
         super.motionZ *= 0.699999988079071D;
      }

   }
}
