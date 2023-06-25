package galaxyspace.systems.SolarSystem.moons.titan.dimension.sky;

import org.lwjgl.opengl.GL11;

import asmodeuscore.api.dimension.IAdvancedSpace.StarClass;
import asmodeuscore.core.astronomy.sky.SkyProviderBase;
import galaxyspace.GalaxySpace;
import micdoodle8.mods.galacticraft.core.Constants;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.client.FMLClientHandler;

public class SkyProviderTitan extends SkyProviderBase
{
	private static final ResourceLocation saturnTexture = new ResourceLocation(Constants.ASSET_PREFIX, "textures/gui/celestialbodies/saturn.png");
	private static final ResourceLocation saturnRingsTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sol/saturn_rings.png");
	
	@Override
	protected void rendererSky(Tessellator tessellator, BufferBuilder worldRenderer, float f10, float ticks) {
		
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		// Render saturn
		f10 = 20.0F;
		GL11.glScalef(0.6F, 0.6F, 0.6F);
		GL11.glRotatef(240F, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(330F, 0F, 0F, 1.0F);
		GL11.glRotatef(-this.mc.world.getCelestialAngle(ticks) * 360.0F, 0.0F, 1.0F, 0.0F);

		
		float color = this.mc.world.isRaining() ? (1.0F - (this.mc.world.rainingStrength / 4)) - this.mc.world.getStarBrightness(1.0F) : 1.1F - this.mc.world.getStarBrightness(1.0F);
		GL11.glColor4f(color, color, color, 1.0F);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.saturnTexture);
		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		worldRenderer.pos(-f10, -100.0D, f10).tex(0, 1.0).endVertex();
		worldRenderer.pos(f10, -100.0D, f10).tex(1.0, 1.0).endVertex();
		worldRenderer.pos(f10, -100.0D, -f10).tex(1.0, 0).endVertex();
		worldRenderer.pos(-f10, -100.0D, -f10).tex(0, 0).endVertex();
		tessellator.draw();

		 // Render saturn Rings
        f10 = 50.0F;
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.saturnRingsTexture);
		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		worldRenderer.pos(-f10, -100.0D, f10).tex(0, 1.0).endVertex();
		worldRenderer.pos(f10, -100.0D, f10).tex(1.0, 1.0).endVertex();
		worldRenderer.pos(f10, -100.0D, -f10).tex(1.0, 0).endVertex();
		worldRenderer.pos(-f10, -100.0D, -f10).tex(0, 0).endVertex();
		tessellator.draw();
		
		GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
	}

	@Override
	protected boolean enableBaseImages() {
		return false;
	}

	@Override
	protected float sunSize() {
		return 4.0F;
	}

	@Override
	protected boolean enableStar() {
		return false;
	}

	@Override
	protected ResourceLocation sunImage() {
		return null;
	}

	@Override
	protected ModeLight modeLight() {
		return ModeLight.DEFAULT;
	}

	@Override
	protected StarClass colorSunAura() {
		return null;
	}

	@Override
	protected Vec3d getAtmosphereColor() {
		return null;
	}
	
	@Override
	public boolean enableSmoothRender() {return false;}

}