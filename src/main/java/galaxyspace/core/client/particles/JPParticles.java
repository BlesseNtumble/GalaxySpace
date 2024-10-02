package galaxyspace.core.client.particles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFlameFX;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

public class JPParticles {
	
	public static void spawnParticle(Minecraft mc, World world, double posX, double posY, double posZ, double velX, double velY, double velZ) {
		mc.effectRenderer.addEffect(new EntityCustomFlameFX(world, posX, posY, posZ, velX, velY, velZ));
		mc.effectRenderer.addEffect(new EntityCustomSmokeFX(world, posX, posY, posZ, velX, velY - 0.1D, velZ));
	}

	public static class EntityCustomFlameFX extends EntityFlameFX {
		public EntityCustomFlameFX(World world, double posX, double posY, double posZ, double velX, double velY, double velZ) {
			super(world, posX, posY, posZ, velX, velY, velZ);
			this.noClip = false;
			this.particleMaxAge = 10;
		}

		public int getBrightnessForRender(float p_70013_1_) {
			return 190 + (int) (20.0F * (1.0F - Minecraft.getMinecraft().gameSettings.gammaSetting));
		}

		public void renderParticle(Tessellator p_70539_1_, float p_70539_2_, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_) {
			if (this.particleAge > 0) {
				super.renderParticle(p_70539_1_, p_70539_2_, p_70539_3_, p_70539_4_, p_70539_5_, p_70539_6_, p_70539_7_);
			}
		}
	}
	
	public static class EntityCustomSmokeFX extends EntitySmokeFX {

		public EntityCustomSmokeFX(World world, double posX, double posY, double posZ, double velX, double velY, double velZ) {
			super(world, posX, posY, posZ, velX, velY, velZ);
			this.particleMaxAge = 10;
		}

		public int getBrightnessForRender(float p_70013_1_) {
			return 190 + (int) (20.0F * (1.0F - Minecraft.getMinecraft().gameSettings.gammaSetting));
		}

		public void renderParticle(Tessellator p_70539_1_, float p_70539_2_, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_) {
			if (this.particleAge > 0) {
				super.renderParticle(p_70539_1_, p_70539_2_, p_70539_3_, p_70539_4_, p_70539_5_, p_70539_6_, p_70539_7_);
			}
		}
	}

}
