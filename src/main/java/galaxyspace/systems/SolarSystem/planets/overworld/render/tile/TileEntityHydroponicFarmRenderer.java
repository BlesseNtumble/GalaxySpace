package galaxyspace.systems.SolarSystem.planets.overworld.render.tile;

import org.lwjgl.opengl.GL11;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;

import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityHydroponicFarm;
import micdoodle8.mods.galacticraft.core.client.model.OBJLoaderGC;
import micdoodle8.mods.galacticraft.core.util.ClientUtil;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.obj.OBJModel;

public class TileEntityHydroponicFarmRenderer extends TileEntitySpecialRenderer<TileEntityHydroponicFarm>{

	private OBJModel.OBJBakedModel main;
	private Block block = null;
	
	private void updateModels()
    {
        if (main == null)
        {
            try
            {
            	IModel model = OBJLoaderGC.instance.loadModel(new ResourceLocation(GalaxySpace.MODID, "hydroponic_farm.obj"));
            	Function<ResourceLocation, TextureAtlasSprite> spriteFunction = location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
            	
            	main = (OBJModel.OBJBakedModel) model.bake(new OBJModel.OBJState(ImmutableList.of("ferma_2"), false), DefaultVertexFormats.ITEM, spriteFunction);
            }
            catch (Exception e)
            {
            	throw new RuntimeException(e);
            }
        }
    }
	
	@Override
    public void render(TileEntityHydroponicFarm farm , double x, double y, double z, float partialTicks, int destroyStage, float partial)
    {
		//OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 0F, 240F);
    	
    	GlStateManager.pushMatrix();
    	GlStateManager.enableRescaleNormal();
    	
    	GlStateManager.enableLighting();
		GlStateManager.disableCull();
		GlStateManager.disableBlend();

    	this.updateModels();
    	
    	GlStateManager.translate((float) x, (float) y , (float) z);
    	
    	GlStateManager.pushMatrix();
    	RenderHelper.disableStandardItemLighting();
    	block = farm.getPlant();
    	double factor = 0.65;
    	GlStateManager.scale(factor, factor, factor);
    	GlStateManager.translate(0.8F, -0.45F, 0.8F);
    	if(block != null)
    		renderWheat(block, farm.getMetaPlant(), 0.0 / factor - 0.5, 0.65 / factor, 0.0 / factor - 0.5);
    	RenderHelper.enableStandardItemLighting();
    	GlStateManager.popMatrix();
    	 
    	GlStateManager.translate(0.5F, -1.0F, 0.5F);
    	GlStateManager.scale(0.01F, 0.01F, 0.01F);
    	 
		this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		ClientUtil.drawBakedModel(main);
		
		GlStateManager.disableRescaleNormal();
	    GlStateManager.popMatrix();
    }
	
	private void renderWheat(Block block, int meta, double x, double y, double z)
    {
		TextureAtlasSprite sprite;
		
		sprite = GSUtils.getBlockTexture(block, "_stage_" + meta);
		if(block == Blocks.MELON_STEM || block == Blocks.PUMPKIN_STEM)
		{
			TextureMap texMap = Minecraft.getMinecraft().getTextureMapBlocks();
			sprite = texMap.getAtlasSprite("minecraft" + ":blocks/melon_stem_disconnected");
			GL11.glColor3f(1.0F, 1.0F, 0.0F);
		}
		this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		
		double d3 = (double) sprite.getMinU();
		double d4 = (double) sprite.getMinV();
		double d5 = (double) sprite.getMaxU();
		double d6 = (double) sprite.getMaxV();
		
		double d7 = x + 0.5D - 0.25D;
        double d8 = x + 0.5D + 0.25D;
        double d9 = z + 0.5D - 0.5D;
        double d10 = z + 0.5D + 0.5D;
        
        bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
       
        bufferbuilder.pos(d7, y + 1.0D, d9).tex(d3, d4).endVertex();
        bufferbuilder.pos(d7, y + 0.0D, d9).tex(d3, d6).endVertex();
        bufferbuilder.pos(d7, y + 0.0D, d10).tex(d5, d6).endVertex();
        bufferbuilder.pos(d7, y + 1.0D, d10).tex(d5, d4).endVertex();
        bufferbuilder.pos(d7, y + 1.0D, d10).tex(d3, d4).endVertex();
        
        bufferbuilder.pos(d7, y + 0.0D, d10).tex(d3, d6).endVertex();
        bufferbuilder.pos(d7, y + 0.0D, d9).tex(d5, d6).endVertex();
        bufferbuilder.pos(d7, y + 1.0D, d9).tex(d5, d4).endVertex();       
        bufferbuilder.pos(d8, y + 1.0D, d10).tex(d3, d4).endVertex();       
        bufferbuilder.pos(d8, y + 0.0D, d10).tex(d3, d6).endVertex();  
        
        bufferbuilder.pos(d8, y + 0.0D, d9).tex(d5, d6).endVertex();
        bufferbuilder.pos(d8, y + 1.0D, d9).tex(d5, d4).endVertex();
        bufferbuilder.pos(d8, y + 1.0D, d9).tex(d3, d4).endVertex();
        bufferbuilder.pos(d8, y + 0.0D, d9).tex(d3, d6).endVertex();
        bufferbuilder.pos(d8, y + 0.0D, d10).tex(d5, d6).endVertex();
        bufferbuilder.pos(d8, y + 1.0D, d10).tex(d5, d4).endVertex();
        
        d7 = x + 0.5D - 0.5D;
        d8 = x + 0.5D + 0.5D;
        d9 = z + 0.5D - 0.25D;
        d10 = z + 0.5D + 0.25D;
        
        bufferbuilder.pos(d7, y + 1.0D, d9).tex(d3, d4).endVertex();
        bufferbuilder.pos(d7, y + 0.0D, d9).tex(d3, d6).endVertex();
        bufferbuilder.pos(d8, y + 0.0D, d9).tex(d5, d6).endVertex();
        bufferbuilder.pos(d8, y + 1.0D, d9).tex(d5, d4).endVertex();
        bufferbuilder.pos(d8, y + 1.0D, d9).tex(d3, d4).endVertex();
        bufferbuilder.pos(d8, y + 0.0D, d9).tex(d3, d6).endVertex();
        bufferbuilder.pos(d7, y + 0.0D, d9).tex(d5, d6).endVertex();
        bufferbuilder.pos(d7, y + 1.0D, d9).tex(d5, d4).endVertex();
        bufferbuilder.pos(d8, y + 1.0D, d10).tex(d3, d4).endVertex();
        bufferbuilder.pos(d8, y + 0.0D, d10).tex(d3, d6).endVertex();
        bufferbuilder.pos(d7, y + 0.0D, d10).tex(d5, d6).endVertex();
        bufferbuilder.pos(d7, y + 1.0D, d10).tex(d5, d4).endVertex();
        bufferbuilder.pos(d7, y + 1.0D, d10).tex(d3, d4).endVertex();
        bufferbuilder.pos(d7, y + 0.0D, d10).tex(d3, d6).endVertex();
        bufferbuilder.pos(d8, y + 0.0D, d10).tex(d5, d6).endVertex();
        bufferbuilder.pos(d8, y + 1.0D, d10).tex(d5, d4).endVertex();
        tessellator.draw();
    }
}
