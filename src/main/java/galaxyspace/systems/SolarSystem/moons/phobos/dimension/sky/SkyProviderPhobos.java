package galaxyspace.systems.SolarSystem.moons.phobos.dimension.sky;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import galaxyspace.core.client.render.sky.SkyProviderBase;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class SkyProviderPhobos extends SkyProviderBase{
	
	private static final ResourceLocation marsTexture = new ResourceLocation(GalacticraftCore.ASSET_PREFIX, "textures/gui/celestialbodies/mars.png");
	private static final ResourceLocation overworldTexture = new ResourceLocation(GalacticraftCore.ASSET_PREFIX, "textures/gui/celestialbodies/earth.png");
	   
	    
	@Override
	protected void rendererSky(Tessellator tessellator, float f10, float partialTicks) 
	{
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		
		// Render mars
		f10 = 95.0F;
		GL11.glScalef(0.6F, 0.6F, 0.6F);
		GL11.glRotatef(-180.0F, 100.0F, 1.0F, 0.0F);
		GL11.glRotatef(15F, 1.0F, 0.0F, 0.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.marsTexture);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-f10, -100.0D, f10, 0, 1);
		tessellator.addVertexWithUV(f10, -100.0D, f10, 1, 1);
		tessellator.addVertexWithUV(f10, -100.0D, -f10, 1, 0);
		tessellator.addVertexWithUV(-f10, -100.0D, -f10, 0, 0);
		tessellator.draw();

		GL11.glPopMatrix();
	    GL11.glPushMatrix();
	    
	    
	    // Render earth
	    f10 = 0.5F;
	    GL11.glScalef(0.6F, 0.6F, 0.6F);
	    GL11.glRotatef(-180.0F, 100.0F, 50.0F, 0.0F);
	    GL11.glRotatef(360F, 1.0F, 0.0F, 0.0F);
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
	    FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.overworldTexture);
	    tessellator.startDrawingQuads();
	    tessellator.addVertexWithUV(-f10, -100.0D, f10, 0, 1);
	    tessellator.addVertexWithUV(f10, -100.0D, f10, 1, 1);
	    tessellator.addVertexWithUV(f10, -100.0D, -f10, 1, 0);
	    tessellator.addVertexWithUV(-f10, -100.0D, -f10, 0, 0);
	    tessellator.draw();
	}

	@Override
	protected boolean enableBaseImages() {
		return true;
	}

	@Override
	protected float sunSize() {
		return 12.5F;
	}

	@Override
	protected boolean enableStar() {
		return true;
	}

	@Override
	protected ResourceLocation sunImage() {
		return null;
	}

	@Override
	protected int modeLight() {
		return 0;
	}

	@Override
	protected Vector3 colorSunAura() {
		return null;		
	}

	@Override
	protected Vector3 getAtmosphereColor() {
		return null;
	}
}