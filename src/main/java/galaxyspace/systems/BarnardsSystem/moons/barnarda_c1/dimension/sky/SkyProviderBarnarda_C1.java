package galaxyspace.systems.BarnardsSystem.moons.barnarda_c1.dimension.sky;

import asmodeuscore.core.astronomy.dimension.world.data.FrozenStormSaveData;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import org.lwjgl.opengl.GL11;

import asmodeuscore.api.dimension.IAdvancedSpace.StarColor;
import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_WorldProviderSpace;
import asmodeuscore.core.astronomy.sky.SkyProviderBase;
import galaxyspace.GalaxySpace;
import galaxyspace.systems.BarnardsSystem.BarnardsSystemBodies;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.WorldProviderSpace;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

public class SkyProviderBarnarda_C1 extends SkyProviderBase{

	private static final ResourceLocation barnarda_c_Texture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/barnards/barnarda_c.png");
	private static final ResourceLocation barnarda_c2_Texture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/barnards/moons/barnarda_c2.png");

	@Override
	protected void rendererSky(Tessellator tessellator, BufferBuilder buffer, float size, float ticks) {
		GL11.glPushMatrix();

        if(!this.mc.world.isRaining() && this.mc.world.provider instanceof IGalacticraftWorldProvider) {

			IGalacticraftWorldProvider gc_provider = (IGalacticraftWorldProvider) this.mc.world.provider;
			FrozenStormSaveData fsd = FrozenStormSaveData.get(this.mc.world, gc_provider.getCelestialBody().getName());

			if(!fsd.isFrozenStorm()) {
				this.renderImage(barnarda_c_Texture, 0, 0, this.getCelestialAngle(getDayLength()), 15.5F, 1.0F, 0.55F);
				this.renderImage(barnarda_c2_Texture, 10, 40, this.getCelestialAngle((long) (getDayLength() * 1.2)) + 80F, 2.5F, 0.4F);


				float f = 0.9F;
				GL11.glPushMatrix();
				this.renderAtmo(tessellator, -90.0F, this.getCelestialAngle(getDayLength()), 15.2F, new Vec3d(61 / 255.0F * f, 86 / 255.0F * f, 175 / 255.0F * f));
				GL11.glPopMatrix();
			}
		}
     
        GL11.glPopMatrix();
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
		if(this.mc.world.provider instanceof WE_WorldProviderSpace)
			return ((WE_WorldProviderSpace)this.mc.world.provider).getSkyColor();

		return ((WorldProviderSpace)this.mc.world.provider).getSkyColor();

	}

}
