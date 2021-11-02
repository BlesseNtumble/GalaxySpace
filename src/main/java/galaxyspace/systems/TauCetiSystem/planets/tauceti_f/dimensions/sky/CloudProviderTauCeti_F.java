package galaxyspace.systems.TauCetiSystem.planets.tauceti_f.dimensions.sky;

import asmodeuscore.core.astronomy.sky.CustomCloudRender;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

public class CloudProviderTauCeti_F extends CustomCloudRender{
	static float[] f = {1.0F};

	public CloudProviderTauCeti_F() {
		super(f);
	}

	@Override
	public float getCloudMovementSpeed(WorldClient world) {
		return 1F;
	}

	@Override
	public ResourceLocation getCloudTexture() {
		return new ResourceLocation("textures/environment/clouds.png");
	}

	@Override
	public Vec3d getCloudColor(float renderPartialTicks) {
		return new Vec3d(150, 150, 150);
	}

}
