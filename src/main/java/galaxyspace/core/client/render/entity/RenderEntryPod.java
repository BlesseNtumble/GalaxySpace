package galaxyspace.core.client.render.entity;

import galaxyspace.core.prefab.entity.EntityEntryPod;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import galaxyspace.GalaxySpace;
import micdoodle8.mods.galacticraft.planets.mars.client.model.ModelBalloonParachute;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderEntryPod extends Render{

	protected IModelCustom modelEntryPod;
	public static final ResourceLocation textureEntryPod = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/model/space_pod.png");
	public static final ResourceLocation textureEntryPodFlame = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/model/pod_flame.png");
	
	protected ModelBalloonParachute parachuteModel = new ModelBalloonParachute();
	
	public RenderEntryPod()
    {
        this.shadowSize = 1.2F;
        this.modelEntryPod = AdvancedModelLoader.loadModel(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "models/pod_flame.obj"));
    }
	
	@Override
    public void doRender(Entity entity, double par2, double par4, double par6, float par8, float par9)
    {
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPushMatrix();
        final float var24 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * par9;
        final float var25 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * par9;

        GL11.glTranslatef((float) par2, (float) par4, (float) par6);
        GL11.glRotatef(180.0F - par8, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(180.0F - var24, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(-var25, 0.0F, 1.0F, 0.0F);

        this.bindEntityTexture(entity);
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        GL11.glScalef(0.65F, 0.6F, 0.65F);
        //modelEntryPod.renderAll();
        modelEntryPod.renderPart("PodBody");
        
        if (entity.posY > 382.0F)
        {
	       GL11.glPushMatrix();
	        	float val = (float) (Math.sin(entity.ticksExisted) / 20.0F + 0.5F);
	        	
		        RenderHelper.disableStandardItemLighting();
		        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
		        GL11.glDisable(2896);
		        GL11.glEnable(GL11.GL_BLEND);
		        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		        GL11.glCullFace(GL11.GL_FRONT);
		        GL11.glScalef(1.0F, 1.0F + val, 1.0F);
		        GL11.glRotatef(entity.ticksExisted * 20.0F, 0.0F, 1.0F, 0.0F);
	
		        GL11.glScalef(1.0F, 1.0F + val / 6.0F, 1.0F);
		        GL11.glRotatef(entity.ticksExisted * 5.0F, 0.0F, 1.0F, 0.0F);
		        
		        this.bindTexture(textureEntryPodFlame);
		        modelEntryPod.renderPart("Flame_Sphere");

		        GL11.glCullFace(GL11.GL_BACK);
		        GL11.glEnable(GL11.GL_CULL_FACE);
		        modelEntryPod.renderPart("Flame_Sphere");
		        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		        RenderHelper.enableStandardItemLighting();
	        GL11.glPopMatrix();
        } else if(!((EntityEntryPod)(entity)).landed() && entity.riddenByEntity != null) {
        	GL11.glPushMatrix();
        	GL11.glTranslatef((float) par2 - 1.25F, (float) par4 + 2.93F, (float) par6 - 0.3F);
            GL11.glScalef(2.5F, 3.0F, 2.5F);
            this.parachuteModel.renderAll();

            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();
    }

	@Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return textureEntryPod;
    }

}
