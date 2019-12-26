package galaxyspace.systems.SolarSystem.planets.overworld.render.tile;

import org.lwjgl.opengl.GL11;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;

import asmodeuscore.api.dimension.IAdvancedSpace;
import galaxyspace.GalaxySpace;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityModernSolarPanel;
import micdoodle8.mods.galacticraft.core.client.model.OBJLoaderGC;
import micdoodle8.mods.galacticraft.core.util.ClientUtil;
import micdoodle8.mods.galacticraft.planets.venus.dimension.WorldProviderVenus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.obj.OBJModel;

public class TileEntityModernSolarPanelRenderer extends TileEntitySpecialRenderer<TileEntityModernSolarPanel>
{
    private OBJModel.OBJBakedModel main;
    private OBJModel.OBJBakedModel panels;
    
    private void updateModels()
    {
        if (main == null)
        {
            try
            {
            	IModel model = OBJLoaderGC.instance.loadModel(new ResourceLocation(GalaxySpace.MODID, "modern_solarpanel.obj"));
            	Function<ResourceLocation, TextureAtlasSprite> spriteFunction = location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
            	
            	main = (OBJModel.OBJBakedModel) model.bake(new OBJModel.OBJState(ImmutableList.of("base"), false), DefaultVertexFormats.ITEM, spriteFunction);
            	panels = (OBJModel.OBJBakedModel) model.bake(new OBJModel.OBJState(ImmutableList.of("panels"), false), DefaultVertexFormats.ITEM, spriteFunction);
            }
            catch (Exception e)
            {
            	throw new RuntimeException(e);
            }
        }
    }
    
    @Override
    public void render(TileEntityModernSolarPanel panel, double x, double y, double z, float partialTicks, int destroyStage, float partial)
    {
    	//OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 0F, 240F);
 
    	GlStateManager.pushMatrix();
    	GlStateManager.disableRescaleNormal();
    	this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
    	//GlStateManager.enableLighting();
    	//RenderHelper.disableStandardItemLighting();
		//GlStateManager.disableCull();
		//GlStateManager.disableBlend();
       	this.updateModels();
       	
     	if (Minecraft.isAmbientOcclusionEnabled()) {
			GlStateManager.shadeModel(GL11.GL_SMOOTH);
		} else {
			GlStateManager.shadeModel(GL11.GL_FLAT);
		}
    	
    	//GlStateManager.color(0.5F, 0.5F, 0.5F, 0.5F);
    	GlStateManager.translate((float) x + 0.5F, (float) y + 3.1F, (float) z + 0.48F);
    	GlStateManager.rotate(-90, 0F, 1F, 0F);

       
        ClientUtil.drawBakedModel(main);

        float celestialAngle = (panel.getWorld().getCelestialAngle(1.0F) - 0.784690560F) * 360.0F;
        float celestialAngle2 = panel.getWorld().getCelestialAngle(1.0F) * 360.0F;

        float par1 = celestialAngle - celestialAngle2;
        if(panel.getWorld().provider instanceof WorldProviderVenus || (panel.getWorld().provider instanceof IAdvancedSpace && ((IAdvancedSpace)panel.getWorld().provider).isRetrogradeRotation()))
        	par1 = celestialAngle + celestialAngle2;
        
        if(panel.getWorld().isRaining())
        	par1 = 80F;
        GlStateManager.rotate(panel.currentAngle - (par1), 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(0.9F, 0.9F, 0.9F);

        //GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.rotate(-90, 0F, 1F, 0F);
        ClientUtil.drawBakedModel(panels);
      
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        RenderHelper.enableStandardItemLighting();
    }
}
