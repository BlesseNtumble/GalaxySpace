package galaxyspace.systems.SolarSystem.planets.pluto.dimension.sky;

import org.lwjgl.opengl.GL11;

import asmodeuscore.api.dimension.IAdvancedSpace.StarColor;
import asmodeuscore.core.astronomy.sky.SkyProviderBase;
import galaxyspace.GalaxySpace;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.client.FMLClientHandler;

public class SkyProviderPluto extends SkyProviderBase
{
	private static final ResourceLocation charonTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sol/moons/charon.png");
	
	@Override
	protected void rendererSky(Tessellator tessellator, BufferBuilder worldRenderer, float f10, float ticks) {		
		
        GL11.glPushMatrix();
        // Render charon
        f10 = 20.0F;
        GL11.glScalef(0.6F, 0.6F, 0.6F);
        GL11.glRotatef(-180.0F, 50.0F, 1.0F, 0.0F);
        GL11.glRotatef(25F, 1.0F, 0.0F, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.charonTexture);
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(-f10, -100.0D, f10).tex(0, 1.0).endVertex();
        worldRenderer.pos(f10, -100.0D, f10).tex(1.0, 1.0).endVertex();
        worldRenderer.pos(f10, -100.0D, -f10).tex(1.0, 0).endVertex();
        worldRenderer.pos(-f10, -100.0D, -f10).tex(0, 0).endVertex();
        tessellator.draw();
        GL11.glPopMatrix();
	}

	@Override
	protected boolean enableBaseImages() {
		return true;
	}

	@Override
	protected float sunSize() {
		return 0.4F;
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
	protected ModeLight modeLight() {
		return ModeLight.DEFAULT;
	}

	@Override
	protected StarColor colorSunAura() {
		return StarColor.WHITE;
	}

	@Override
	protected Vec3d getAtmosphereColor() {
		return null;
	}
	
	@Override
	public boolean enableSmoothRender() {return false;}
	
	@Override
	public int expandSizeAura() {return -4;}

}