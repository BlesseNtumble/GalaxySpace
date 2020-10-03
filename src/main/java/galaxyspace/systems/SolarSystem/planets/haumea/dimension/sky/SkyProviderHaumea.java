package galaxyspace.systems.SolarSystem.planets.haumea.dimension.sky;

import org.lwjgl.opengl.GL11;

import asmodeuscore.api.dimension.IAdvancedSpace.StarColor;
import asmodeuscore.core.astronomy.sky.SkyProviderBase;
import galaxyspace.GalaxySpace;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;

public class SkyProviderHaumea extends SkyProviderBase
{
	private static final ResourceLocation haumeaRingTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sol/haumea_rings.png");
	
	@Override
	protected void rendererSky(Tessellator tessellator, BufferBuilder worldRenderer, float f10, float ticks) {		
	
		
		GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        
		f10 = 550.0F;
		GL11.glScalef(0.8F, 0.6F, 0.8F);
		GL11.glRotatef(80.0f, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(this.mc.world.getCelestialAngle(ticks) * 360.0F, 0.0F, 1.0F, 0.0F);
		//GL11.glRotatef(-this.mc.world.getCelestialAngle(this.ticks) * 360.0F * 0.02F, 0.0F, 0.0F, 1.0F);
		
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.haumeaRingTexture);
		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
		worldRenderer.pos(-f10, -100.0D, f10).tex(0, 1.0).color(1, 1, 1, this.mc.world.getStarBrightness(ticks)).endVertex();
		worldRenderer.pos(f10, -100.0D, f10).tex(1.0, 1.0).color(1, 1, 1, this.mc.world.getStarBrightness(ticks)).endVertex();
		worldRenderer.pos(f10, -100.0D, -f10).tex(1.0, 0).color(1, 1, 1, this.mc.world.getStarBrightness(ticks)).endVertex();
		worldRenderer.pos(-f10, -100.0D, -f10).tex(0, 0).color(1, 1, 1, this.mc.world.getStarBrightness(ticks)).endVertex();
		tessellator.draw();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	@Override
	protected boolean enableBaseImages() {
		return true;
	}

	@Override
	protected float sunSize() {
		return 0.35F;
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
	public boolean enableSmoothRender() {return false;}
	
	@Override
	public int expandSizeAura() {return -4;}

}