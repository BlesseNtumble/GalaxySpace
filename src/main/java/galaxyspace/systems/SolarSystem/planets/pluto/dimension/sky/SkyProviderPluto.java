package galaxyspace.systems.SolarSystem.planets.pluto.dimension.sky;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import galaxyspace.GalaxySpace;
import galaxyspace.core.client.render.sky.SkyProviderBase;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class SkyProviderPluto extends SkyProviderBase{

	private static final ResourceLocation charonTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sol/moons/charon.png");
	   
	@Override
	protected void rendererSky(Tessellator tessellator, float f10, float ticks) 
	{
		GL11.glPopMatrix();
        GL11.glPushMatrix();
       
        // Render charon
        f10 = 20.0F;
        GL11.glScalef(0.6F, 0.6F, 0.6F);
        GL11.glRotatef(-180.0F, 50.0F, 1.0F, 0.0F);
        GL11.glRotatef(25F, 1.0F, 0.0F, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.charonTexture);
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
		return 1.5F;
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
