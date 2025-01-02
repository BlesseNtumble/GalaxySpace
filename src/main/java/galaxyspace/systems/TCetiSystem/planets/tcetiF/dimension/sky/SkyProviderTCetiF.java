package galaxyspace.systems.TCetiSystem.planets.tcetiF.dimension.sky;

import galaxyspace.GalaxySpace;
import galaxyspace.core.client.render.sky.SkyProviderBase;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class SkyProviderTCetiF extends SkyProviderBase{

	@Override
	protected boolean enableBaseImages() {
		return true;
	}

	@Override
	protected float sunSize() {
		return 4.5F;
	}

	@Override
	protected boolean enableStar() {
		return true;
	}

	@Override
	protected ResourceLocation sunImage() {
		return new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/tauceti/TauCetiA.png");
	}

	@Override
	protected int modeLight() {
		return 0;
	}

	@Override
	protected Vector3 colorSunAura()
	{
		/*afloat[0] = 255 / 255.0F;
        afloat[1] = 194 / 255.0F;
        afloat[2] = 180 / 255.0F;
        afloat[3] = 0.3F;*/
        return new Vector3(255, 194, 180);
	}

	@Override
	protected Vector3 getAtmosphereColor() {
		return null;
	}

	@Override
	protected void rendererSky(Tessellator tessellator, float f10, float ticks) {	
	}

}