package galaxyspace.systems.SolarSystem.planets.overworld.dimension.sky;

import org.lwjgl.opengl.GL11;

import asmodeuscore.api.dimension.IAdvancedSpace.StarColor;
import asmodeuscore.core.astronomy.sky.SkyProviderBase;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class SkyProviderOverworld extends SkyProviderBase {

	private static final ResourceLocation moonTexture = new ResourceLocation("textures/environment/moon_phases.png");
	   
	@Override
	protected void rendererSky(Tessellator tessellator, BufferBuilder worldRenderer, float f10, float ticks) {
		
		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		float f24 = 1.0F - this.mc.world.getRainStrength(ticks);
        GlStateManager.color(1.0F, 1.0F, 1.0F, f24);
        GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(this.mc.world.getCelestialAngle(ticks) * 360.0F, 1.0F, 0.0F, 0.0F);
        
		f10 = 20.0F;
		mc.renderEngine.bindTexture(this.moonTexture);
		int k = mc.world.getMoonPhase();
		int l = k % 4;
		int i1 = k / 4 % 2;
		float f14 = (float) (l + 0) / 4.0F;
		float f15 = (float) (i1 + 0) / 2.0F;
		float f16 = (float) (l + 1) / 4.0F;
		float f17 = (float) (i1 + 1) / 2.0F;
		
		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		worldRenderer.pos(-f10, -100.0D, f10).tex(f16, f17).endVertex();
		worldRenderer.pos(f10, -100.0D, f10).tex(f14, f17).endVertex();
		worldRenderer.pos(f10, -100.0D, -f10).tex(f14, f15).endVertex();
		worldRenderer.pos(-f10, -100.0D, -f10).tex(f16, f15).endVertex();
		tessellator.draw();
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}
	
	@Override
	protected boolean enableBaseImages() {
		return true;
	}

	@Override
	protected float sunSize() {
		return 19.5F;
	}

	@Override
	protected boolean enableStar() {
		return true;
	}

	@Override
	protected ResourceLocation sunImage() {
		return new ResourceLocation("textures/environment/sun.png");
	}

	@Override
	protected ModeLight modeLight() {
		return ModeLight.DEFAULT;
	}

	@Override
	protected StarColor colorSunAura() {
		return StarColor.YELLOW;
	}

	@Override
	protected Vector3 getAtmosphereColor() {
		float f = 0.99F;
    	return new Vector3(120 / 255.0F * f, 120 / 255.0F * f, 120 / 255.0F * f);
	}
	
}
