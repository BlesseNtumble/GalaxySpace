package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.dimension.sky;

import org.lwjgl.opengl.GL11;

import asmodeuscore.api.dimension.IAdvancedSpace.StarColor;
import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_WorldProviderSpace;
import asmodeuscore.core.astronomy.sky.SkyProviderBase;
import asmodeuscore.core.astronomy.sky.SkyProviderBaseOld;
import galaxyspace.GalaxySpace;
import galaxyspace.systems.BarnardsSystem.BarnardsSystemBodies;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.WorldProviderSpace;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;

public class SkyProviderBarnarda_C extends SkyProviderBase{

	private static final ResourceLocation barnarda_c1_Texture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/barnards/moons/barnarda_c1.png");
	private static final ResourceLocation barnarda_c2_Texture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/barnards/moons/barnarda_c2.png");
	
	@Override
	protected void rendererSky(Tessellator tessellator, BufferBuilder buffer, float size, float ticks) {
        
        GL11.glEnable(GL11.GL_BLEND);
        
        if(!this.mc.world.isRaining()) {
        	this.renderImage(barnarda_c1_Texture, 0, 0, this.getCelestialAngle(getDayLenght()), 5.5F);
        	this.renderImage(barnarda_c2_Texture, 40, 0, this.getCelestialAngle((long) (getDayLenght() * 1.2)) + 80F, 1.5F);
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
		return BarnardsSystemBodies.BarnardsSystem.getMainStar().getBodyIcon();
	}

	@Override
	protected boolean enableStar() {
		return true;
	}

	@Override
	protected StarColor colorSunAura() {
		return StarColor.ORANGE;
	}

	@Override
	protected Vector3 getAtmosphereColor() {
		return null;
	}

}
