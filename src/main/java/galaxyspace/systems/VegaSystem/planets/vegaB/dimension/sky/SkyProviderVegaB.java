package galaxyspace.systems.VegaSystem.planets.vegaB.dimension.sky;

import galaxyspace.GalaxySpace;
import galaxyspace.core.client.render.sky.SkyProviderBase;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class SkyProviderVegaB extends SkyProviderBase{

	@Override
	protected boolean enableBaseImages() {
		return true;
	}

	@Override
	protected float sunSize() {
		return 3.5F;
	}

	@Override
	protected boolean enableStar() {
		return true;
	}

	@Override
	protected ResourceLocation sunImage() {
		return new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/vega/Vega.png");
	}

	@Override
	protected int modeLight() {
		return 0;
	}

	@Override
	protected Vector3 colorSunAura() {
        return new Vector3(150, 213, 255);
	}

	@Override
	protected Vector3 getAtmosphereColor() {
		return null;
	}

	@Override
	protected void rendererSky(Tessellator tessellator, float f10, float ticks) {
	
	}

}