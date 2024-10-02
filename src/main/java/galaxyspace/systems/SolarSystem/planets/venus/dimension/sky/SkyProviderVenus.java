package galaxyspace.systems.SolarSystem.planets.venus.dimension.sky;

import galaxyspace.core.client.render.sky.SkyProviderBase;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class SkyProviderVenus extends SkyProviderBase
{

	@Override
	protected void rendererSky(Tessellator tessellator, float f10, float partialTicks) {
		
	}

	@Override
	protected boolean enableBaseImages() {
		return false;
	}

	@Override
	protected float sunSize() {
		return 25.5F;
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
	protected int modeLight() {
		return 2;
	}

	@Override
	protected Vector3 colorSunAura() {
		return null;
	}

	@Override
	protected Vector3 getAtmosphereColor() {
		float f = 0.5F;
    	return new Vector3(91 / 255.0F * f, 61 / 255.0F * f, 8 / 255.0F * f);
	}

}