package galaxyspace.systems.SolarSystem.moons.miranda.dimension.sky;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import galaxyspace.GalaxySpace;
import galaxyspace.core.client.render.sky.SkyProviderBase;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class SkyProviderMiranda extends SkyProviderBase{

	private static final ResourceLocation uranusTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sol/uranus.png");
    private static final ResourceLocation uranusRingsTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sol/uranus_rings.png");
       
	@Override
	protected void rendererSky(Tessellator tessellator, float f10, float ticks) 
	{
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        
        // Render uranus
        f10 = 45.0F;
        GL11.glScalef(0.6F, 0.6F, 0.6F);
        GL11.glRotatef(-180.0F, 100.0F, 1.0F, 0.0F);
        GL11.glRotatef(-75F, 1.0F, 0.0F, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.uranusTexture);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(-f10, -100.0D, f10, 0, 1);
        tessellator.addVertexWithUV(f10, -100.0D, f10, 1, 1);
        tessellator.addVertexWithUV(f10, -100.0D, -f10, 1, 0);
        tessellator.addVertexWithUV(-f10, -100.0D, -f10, 0, 0);
        tessellator.draw();
        
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_ALPHA_TEST);        
        GL11.glEnable(GL11.GL_BLEND);
        
        // Render uranus rings
        f10 = 105.0F;
        GL11.glScalef(0.6F, 0.6F, 0.6F);
        GL11.glRotatef(-180.0F, 100.0F, 1.0F, 0.0F);
        GL11.glRotatef(-75F, 1.0F, 0.0F, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.uranusRingsTexture);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(-f10, -100.0D, f10, 0, 1);
        tessellator.addVertexWithUV(f10, -100.0D, f10, 1, 1);
        tessellator.addVertexWithUV(f10, -100.0D, -f10, 1, 0);
        tessellator.addVertexWithUV(-f10, -100.0D, -f10, 0, 0);
        tessellator.draw();        
       
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_BLEND);
	}

	@Override
	protected boolean enableBaseImages() {
		return true;
	}

	@Override
	protected float sunSize() {
		return 2.5F;
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