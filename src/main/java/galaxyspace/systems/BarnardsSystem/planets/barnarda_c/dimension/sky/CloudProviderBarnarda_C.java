package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.dimension.sky;

import asmodeuscore.core.astronomy.sky.CloudProviderBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CloudProviderBarnarda_C extends CloudProviderBase{

	@Override
	public float getCloudMovementSpeed(World world) {
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
