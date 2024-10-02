package galaxyspace.systems.BarnardsSystem.planets.barnardaE.dimension.sky;

import galaxyspace.GalaxySpace;
import galaxyspace.core.client.render.sky.SkyProviderBase;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class SkyProviderBarnardaE extends SkyProviderBase{

	@Override
	protected void rendererSky(Tessellator tessellator, float f10, float ticks) {		
	}

	@Override
	protected int modeLight() {
		return 0;
	}

	@Override
	protected boolean enableBaseImages() {
		return true;
	}

	@Override
	protected float sunSize() {
		return 2;
	}

	@Override
	protected ResourceLocation sunImage() {
		return new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/barnards/barnarda_a.png");
	}

	@Override
	protected boolean enableStar() {
		return true;
	}

	@Override
	protected Vector3 colorSunAura() {
		return new Vector3(150, 50, 0);
	}

	@Override
	protected Vector3 getAtmosphereColor() {
		return null;
	}
}