package galaxyspace.systems.SolarSystem.moons.phobos.dimension.sky;

import org.lwjgl.opengl.GL11;

import asmodeuscore.api.dimension.IAdvancedSpace.StarColor;
import asmodeuscore.core.astronomy.sky.SkyProviderBase;
import galaxyspace.GalaxySpace;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.Constants;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class SkyProviderPhobos extends SkyProviderBase
{
	private static final ResourceLocation marsTexture = new ResourceLocation(Constants.ASSET_PREFIX, "textures/gui/celestialbodies/mars.png");
	private static final ResourceLocation overworldTexture = new ResourceLocation(Constants.ASSET_PREFIX, "textures/gui/celestialbodies/earth.png");
	private static final ResourceLocation deimosTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sol/moons/deimos.png");
	
	@Override
	protected void rendererSky(Tessellator tessellator, BufferBuilder worldRenderer, float f10, float ticks) {


		GL11.glPushMatrix();
		
		this.renderImage(overworldTexture, 40F, -this.mc.world.getCelestialAngle(ticks) * 360F, 0F, 0.8F);
		this.renderImage(deimosTexture, (this.mc.world.getCelestialAngle(ticks) * 360F) / 4, 95F, 10F, 4.0F);
		
		this.renderImage(marsTexture, 90F, 90F, 0F, 60F, 1.0F, .4F);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_ALPHA_TEST);

		GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F); 
		GL11.glRotatef(0F, 0.0F, 1.0F, 0.0F); 
		GL11.glRotatef(-90F, 0.0F, 0.0F, 1.0F); 

		f10 = 60F;
		
		
		float alpha = 0.7F;
		float k = f10;
		float l = 20;
		float angle = mc.world.getCelestialAngle(ticks)*360 % 360;
		
		
		
		if (angle > 300 && angle < 360)
			l = -f10 + angle - 290;
		else if (angle > 0 && angle < 50)
			l = f10 - 50 + angle;
		else if (angle > 180 && angle < 300) {
			k = f10 - (angle - 180);
			l = f10;
		} else
			l = f10;
		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
		worldRenderer.pos(-f10, -100.0D, k).color(0.0F, 0.0F, 0.0F, alpha).endVertex();
		worldRenderer.pos(f10, -100.0D, k).color(0.0F, 0.0F, 0.0F, alpha).endVertex();
		worldRenderer.pos(f10, -100.0D, -l).color(0.0F, 0.0F, 0.0F, alpha).endVertex();
		worldRenderer.pos(-f10, -100.0D, -l).color(0.0F, 0.0F, 0.0F, alpha).endVertex();

		tessellator.draw();

		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		float f = 0.9F - mc.world.getStarBrightness(ticks);
		this.renderAtmo(tessellator, 90F, 90F, f10 - 8, new Vector3(30 / 255.0F * f, 40 / 255.0F * f, 40 / 255.0F * f));
		
		GL11.glPopMatrix();
	
	}

	@Override
	protected boolean enableBaseImages() {
		return true;
	}

	@Override
	protected float sunSize() {
		return 5.2F;
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
	protected Vector3 getAtmosphereColor() {
		return null;
	}
	
	@Override
	public boolean enableSmoothRender() {return true;}

}