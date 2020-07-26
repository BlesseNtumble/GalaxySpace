package galaxyspace.systems.SolarSystem.planets.kuiperbelt.dimension.sky;

import asmodeuscore.api.dimension.IAdvancedSpace.StarColor;
import asmodeuscore.core.astronomy.sky.SkyProviderBase;
import asmodeuscore.core.astronomy.sky.SkyProviderBaseOld;
import galaxyspace.GalaxySpace;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class SkyProviderKuiperBelt extends SkyProviderBase
{
	  
	@Override
	protected void rendererSky(Tessellator tessellator, BufferBuilder worldRenderer, float f10, float ticks) {
	}

	@Override
	protected boolean enableBaseImages() {
		return true;
	}

	@Override
	protected float sunSize() {
		return 1.5F;
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
	public boolean enableSmoothRender() {return true;}
	
	@Override
	public boolean enableRenderPlanet() {return false;}
	
	@Override
	public int expandSizeAura() {return -2;}

}