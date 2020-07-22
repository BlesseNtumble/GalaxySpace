package galaxyspace.core.client.render.entity;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.google.common.base.Function;

import galaxyspace.GalaxySpace;
import micdoodle8.mods.galacticraft.api.prefab.entity.EntityAutoRocket;
import micdoodle8.mods.galacticraft.core.client.model.OBJLoaderGC;
import micdoodle8.mods.galacticraft.core.util.ClientUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderRockets extends Render<EntityAutoRocket>
{
    private OBJModel.OBJBakedModel rocketModel;
    private String model;
    private List<String> parts;
    
    public RenderRockets(RenderManager manager, String model, String... parts)
    {
        super(manager);
        this.model = model;
        this.shadowSize = 2F;
        this.parts = Arrays.asList(parts);
    }

    private void updateModel()
    {
        if (this.rocketModel == null)
        {
            try
            {
                IModel model = OBJLoaderGC.instance.loadModel(new ResourceLocation(GalaxySpace.ASSET_PREFIX, this.model + ".obj"));
                Function<ResourceLocation, TextureAtlasSprite> spriteFunction = location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
                this.rocketModel = (OBJModel.OBJBakedModel) model.bake(new OBJModel.OBJState(this.parts, false), DefaultVertexFormats.ITEM, spriteFunction);
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
}
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityAutoRocket entity)
    {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }

    @Override
    public void doRender(EntityAutoRocket entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
    	//OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 0F, 240F);    	
        float pitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks + 180;
        GlStateManager.disableRescaleNormal();
        GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        GlStateManager.translate((float) x, (float) y, (float) z);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(180.0F - entityYaw, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(pitch, 0.0F, 0.0F, 1.0F);
        GlStateManager.translate(0.0F, entity.getRenderOffsetY(), 0.0F);
        float rollAmplitude = entity.rollAmplitude / 3 - partialTicks;

        if (rollAmplitude > 0.0F)
        {
            final float i = entity.getLaunched() ? (5 - MathHelper.floor(entity.timeUntilLaunch / 85)) / 10F : 0.3F;
            GlStateManager.rotate(MathHelper.sin(rollAmplitude) * rollAmplitude * i * partialTicks, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(MathHelper.sin(rollAmplitude) * rollAmplitude * i * partialTicks, 1.0F, 0.0F, 1.0F);
        }

        this.updateModel();
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);       
        
        if (Minecraft.isAmbientOcclusionEnabled())
        {
            GlStateManager.shadeModel(GL11.GL_SMOOTH);
        }
        else
        {
            GlStateManager.shadeModel(GL11.GL_FLAT);
        }

		//GlStateManager.scale(-1.0F, -1.0F, 1.0F);
		GlStateManager.scale(0.8F, 0.8F, 0.8F);
        ClientUtil.drawBakedModel(this.rocketModel);
		
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.enableCull();
        GlStateManager.color(1F, 1F, 1F);
        GlStateManager.popMatrix();
        //RenderHelper.enableStandardItemLighting();
    }

    @Override
    public boolean shouldRender(EntityAutoRocket rocket, ICamera camera, double camX, double camY, double camZ)
    {
        AxisAlignedBB axisalignedbb = rocket.getEntityBoundingBox().grow(0.5D, 0, 0.5D);
        return rocket.isInRangeToRender3d(camX, camY, camZ) && camera.isBoundingBoxInFrustum(axisalignedbb);
    }

}
