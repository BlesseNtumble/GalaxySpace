package galaxyspace.systems.SolarSystem.moons.triton.dimenson.sky;

import asmodeuscore.core.astronomy.sky.SkyProviderBase;
import galaxyspace.GalaxySpace;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class SkyProviderTriton extends SkyProviderBase {

	@Override
	protected void rendererSky(Tessellator tessellator, BufferBuilder buffer, float f10, float ticks) {	
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
		return new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sun_blank.png");
	}

	@Override
	protected boolean enableStar() {
		return true;
	}

	@Override
	protected Vector3 colorSunAura() {
		return new Vector3(150, 150, 150);
	}

	@Override
	protected Vector3 getAtmosphereColor() {
		return null;
	}	

	@Override
	public boolean enableSmoothRender() {return true;}

}
