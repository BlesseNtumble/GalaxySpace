package galaxyspace.systems.SolarSystem.planets.overworld.dimension.sky;

import asmodeuscore.core.astronomy.sky.SkyProviderBase;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class SkyProviderOverworld extends SkyProviderBase {

	@Override
	protected void rendererSky(Tessellator tessellator, BufferBuilder worldRenderer, float f10, float ticks) {
		
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
	protected int modeLight() {
		return 0;
	}

	@Override
	protected Vector3 colorSunAura() {
		return new Vector3(255, 194, 180);
	}

	@Override
	protected Vector3 getAtmosphereColor() {
		float f = 0.99F;
    	return new Vector3(120 / 255.0F * f, 120 / 255.0F * f, 120 / 255.0F * f);
	}
	
}
