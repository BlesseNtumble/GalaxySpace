package galaxyspace.core.client.render.entity;

import galaxyspace.core.prefab.entities.EntityLaserBeam;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderDragon;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class RenderLaserBeam extends Render<EntityLaserBeam>{

	public RenderLaserBeam(RenderManager renderManager) {
		super(renderManager);
		
	}

	@Override
	public void doRender(EntityLaserBeam entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*
		double distance = 10;
		double distance_start = Math.min(1.0d, distance);
		
		int maxTicks = 100;
		 
		float prog = ((float)entity.ticksExisted)/((float)maxTicks);
		double width = 15.0F * (Math.sin(Math.sqrt(prog)*Math.PI))*2;		
		
		GlStateManager.pushMatrix();
		GlStateManager.enableRescaleNormal();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.disableCull();
		GlStateManager.depthMask(false);
		GlStateManager.translate(x, y, z);
		//GlStateManager.rotate(entity.rotationYaw-90F, 0.0F, 1.0F, 0.0F);              
       // GlStateManager.rotate(entity.rotationPitch, 0.0F, 0.0F, 1.0F);
        
        
		GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
		
        
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        
        distance*=80.0D;
        distance_start*=80.0D;
        float f10 = 0.0125F;
        
        float rot_x = 45f+(prog*180f);

        //GlStateManager.rotate(rot_x+90f , 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(f10, f10, f10);
        float brightness = (float) Math.sin(Math.sqrt(prog)*Math.PI);
        /*
        if (distance > distance_start) {
        	GlStateManager.pushMatrix();
	        for (int i = 0; i < 2; ++i)
	        {
	            GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
	            GlStateManager.glNormal3f(0.0F, 0.0F, f10);
	            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
	           
	            bufferbuilder.pos(distance_start, -width, 0.0D).color(1.0f, 1.0f, 1.0f, brightness).endVertex();
	            bufferbuilder.pos(0, -width, 0.0D).color(1.0f, 1.0f, 1.0f, brightness).endVertex();
	            bufferbuilder.pos(0, width, 0.0D).color(1.0f, 1.0f, 1.0f, brightness).endVertex();
	            bufferbuilder.pos(distance_start, width, 0.0D).color(1.0f, 1.0f, 1.0f, brightness).endVertex();
	            tessellator.draw();
	            
	        }
	        GlStateManager.popMatrix();
        }
        
        
        GlStateManager.glNormal3f(0.05625F, 0.0F, 0.0F);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(-7.0D, -2.0D, -2.0D).tex(0.0D, 0.15625D).endVertex();
        bufferbuilder.pos(-7.0D, -2.0D, 2.0D).tex(0.15625D, 0.15625D).endVertex();
        bufferbuilder.pos(-7.0D, 2.0D, 2.0D).tex(0.15625D, 0.3125D).endVertex();
        bufferbuilder.pos(-7.0D, 2.0D, -2.0D).tex(0.0D, 0.3125D).endVertex();
        tessellator.draw();
        GlStateManager.glNormal3f(-0.05625F, 0.0F, 0.0F);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(-7.0D, 2.0D, -2.0D).tex(0.0D, 0.15625D).endVertex();
        bufferbuilder.pos(-7.0D, 2.0D, 2.0D).tex(0.15625D, 0.15625D).endVertex();
        bufferbuilder.pos(-7.0D, -2.0D, 2.0D).tex(0.15625D, 0.3125D).endVertex();
        bufferbuilder.pos(-7.0D, -2.0D, -2.0D).tex(0.0D, 0.3125D).endVertex();
        tessellator.draw();
        
        GlStateManager.depthMask(true);
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.disableRescaleNormal();	         
		GlStateManager.popMatrix();		 */
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		GlStateManager.rotate(180F, 0.0F, 0.0F, 1.0F);
		BlockPos blockpos = entity.rayTrace(16, partialTicks).getBlockPos();
		
		float f1 = MathHelper.sin(partialTicks * 0.2F) / 2.0F + 0.5F;
        f1 = f1 * f1 + f1;

		if (blockpos != null)
        {
            this.bindTexture(RenderDragon.ENDERCRYSTAL_BEAM_TEXTURES);
            float f2 = (float)blockpos.getX() + 0.5F;
            float f3 = (float)blockpos.getY() + 0.5F;
            float f4 = (float)blockpos.getZ() + 0.5F;
            double d0 = (double)f2 - entity.posX;
            double d1 = (double)f3 - entity.posY;
            double d2 = (double)f4 - entity.posZ;
            RenderDragon.renderCrystalBeams(x + d0, y - 0.3D + (double)(f1 * 0.4F) + d1, z + d2, partialTicks, (double)f2, (double)f3, (double)f4, (int) entity.ticksExisted, entity.posX, entity.posY, entity.posZ);
        } 
		
		GlStateManager.popMatrix();
	    super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityLaserBeam entity) {
		return null;
	}

}
