package galaxyspace.systems.TauCetiSystem.planets.tauceti_f.dimensions.sky;

import org.lwjgl.opengl.GL11;

import asmodeuscore.api.dimension.IAdvancedSpace.StarColor;
import asmodeuscore.core.astronomy.sky.SkyProviderBase;
import galaxyspace.systems.TauCetiSystem.TauCetiSystemBodies;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

public class SkyProviderTauCeti_F extends SkyProviderBase{

	//private static final ResourceLocation barnarda_c1_Texture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/barnards/moons/barnarda_c1.png");
	//private static final ResourceLocation barnarda_c2_Texture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/barnards/moons/barnarda_c2.png");
	
	@Override
	protected void rendererSky(Tessellator tessellator, BufferBuilder buffer, float size, float ticks) {
        
        GL11.glEnable(GL11.GL_BLEND);
        
        if(!this.mc.world.isRaining()) {
        	//this.renderImage(barnarda_c1_Texture, 0, 0, this.getCelestialAngle(getDayLenght()), 5.5F);
        	//this.renderImage(barnarda_c2_Texture, 40, 0, this.getCelestialAngle((long) (getDayLenght() * 1.2)) + 80F, 1.5F);
        }
        GL11.glDisable(GL11.GL_BLEND);
	}

	@Override
	protected ModeLight modeLight() {
		return ModeLight.DEFAULT;
	}

	@Override
	protected boolean enableBaseImages() {
		return true;
	}

	@Override
	protected float sunSize() {
		return 4;
	}

	@Override
	protected ResourceLocation sunImage() {
		return TauCetiSystemBodies.TauCetiSystem.getMainStar().getBodyIcon();
	}

	@Override
	protected boolean enableStar() {
		return true;
	}

	@Override
	protected StarColor colorSunAura() {
		return StarColor.YELLOW;
	}

	@Override
	protected Vec3d getAtmosphereColor() {
		float f = mc.world.getSunBrightness(ticks) + 0.2F;
		return new Vec3d(120 / 255.0F * f, 120 / 255.0F * f, 160 / 255.0F * f);
	}

}
