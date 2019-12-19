package galaxyspace.systems.SolarSystem.planets.mercury.dimension.sky;

import org.lwjgl.opengl.GL11;

import asmodeuscore.core.astronomy.sky.SkyProviderBase;
import galaxyspace.GalaxySpace;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.Constants;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;

public class SkyProviderMercury extends SkyProviderBase
{
	private static final ResourceLocation venusTexture = new ResourceLocation(Constants.ASSET_PREFIX, "textures/gui/celestialbodies/venus.png");
    
	@Override
	protected void rendererSky(Tessellator tessellator, BufferBuilder worldRenderer, float f10, float ticks) {
		
		GL11.glPopMatrix();
        GL11.glPushMatrix();
        
		// Render venus
        f10 = 0.5F;
        GL11.glScalef(0.6F, 0.6F, 0.6F);
        GL11.glRotatef(-180.0F, 50.0F, 1.0F, 0.0F);
        GL11.glRotatef(90F, 190.0F, 50.0F, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.venusTexture);
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(-f10, -100.0D, f10).tex(0, 1.0).endVertex();
        worldRenderer.pos(f10, -100.0D, f10).tex(1.0, 1.0).endVertex();
        worldRenderer.pos(f10, -100.0D, -f10).tex(1.0, 0).endVertex();
        worldRenderer.pos(-f10, -100.0D, -f10).tex(0, 0).endVertex();
        tessellator.draw();
	}

	@Override
	protected boolean enableBaseImages() {
		return true;
	}

	@Override
	protected float sunSize() {
		return 20.5F;
	}

	@Override
	protected boolean enableStar() {
		return true;
	}

	@Override
	protected ResourceLocation sunImage() {
		return new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sun_blank.png");
	}

	@Override
	protected int modeLight() {
		return 0;
	}

	@Override
	protected Vector3 colorSunAura() {
		return new Vector3(150, 150, 150);
	}

	@Override
	protected Vector3 getAtmosphereColor() {
		return null;
	}

	@Override
	public boolean enableSmoothRender() {return true;}
	
	@Override
	public int addSizeAura() {return 32;}
	
	@Override
	public boolean enableLargeSunAura() {return true;}
}