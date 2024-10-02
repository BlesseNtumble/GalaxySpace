package galaxyspace.systems.BarnardsSystem.planets.barnardaC.dimension;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.api.dimension.IAdvancedSpace;
import galaxyspace.core.world.gen.WorldProviderAdvancedSpace;
import galaxyspace.systems.BarnardsSystem.BarnardsSystemBodies;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.dimension.sky.SkyProviderBarnardaC;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.ISolarLevel;
import micdoodle8.mods.galacticraft.core.client.CloudRenderer;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.IRenderHandler;

public class WorldProviderBarnardaC extends WorldProviderAdvancedSpace implements ISolarLevel, IAdvancedSpace {
	
    @Override
	public double getSolarEnergyMultiplier()
	{
		double solarMultiplier = -1D;
		if (solarMultiplier < 0D)
		{
			double s = this.getSolarSize();
			solarMultiplier = s * s * s * ConfigManagerCore.spaceStationEnergyScalar;
		}
		return solarMultiplier;
	}
    
    @Override
    public boolean canSpaceshipTierPass(int tier) {
        return tier >= this.getCelestialBody().getTierRequirement();
    }

    @Override
    public double getHorizon() {
        return 4.0D;
    }

    @Override

    public float getFallDamageModifier() {
        return 0.9F;
    }

 

    @Override

    public double getFuelUsageMultiplier() {

        return 1.0;

    }

    @Override
    public double getMeteorFrequency() {
        return 0.0;
    }

 

    @Override

    public float getSoundVolReductionAmount() {

        return Float.MAX_VALUE;

    }
    
    @Override
    public boolean canRainOrSnow() {
        return true;
    }

    @Override
    public boolean canBlockFreeze(int x, int y, int z, boolean byWater)
    {
		return false;
    }

    @Override
    public CelestialBody getCelestialBody() {
        return BarnardsSystemBodies.barnardaC;
    }
    
    @Override
    public Class<? extends IChunkProvider> getChunkProviderClass() {
        return ChunkProviderBarnardaC.class;
    }

    @Override
    public Vector3 getFogColor() {

    	float f = 1.0F - this.getStarBrightness(1.0F);
        return new Vector3(250 / 255.0F * f, 167 / 255.0F * f, 107 / 255.0F * f);

    }

    @Override
    public Vector3 getSkyColor() {

    	float f = 1.0F - this.getStarBrightness(1.0F);
    	return new Vector3(61 / 255.0F * f, 86 / 255.0F * f, 175 / 255.0F * f);
    }

     
     @Override
     public boolean isSkyColored()
     {
         return true;
     }
 

     @Override
     public Class<? extends WorldChunkManager> getWorldChunkManagerClass() {
        return WorldChunkManagerBarnardaC.class;
     }

     @Override
     public boolean hasSunset() {
		return false;
     }

 

    //Can players respawn here?

	@Override

	public boolean shouldForceRespawn() {

		return !ConfigManagerCore.forceOverworldRespawn;

	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getStarBrightness(float par1) {
		final float var2 = this.worldObj.getCelestialAngle(par1);
		float var3 = 1.0F - (MathHelper.cos(var2 * (float) Math.PI * 2.0F) * 2.0F + 0.25F);

		if (var3 < 0.0F) {
			var3 = 0.0F;
		}

		if (var3 > 1.0F) {
			var3 = 1.0F;
		}

		return var3 * var3 * 0.5F + 0.3F;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getSunBrightness(float par1) {
		float f1 = this.worldObj.getCelestialAngle(1.0F);
		float f2 = 1.25F - (MathHelper.cos(f1 * 3.1415927F * 2.0F) * 2.0F + 0.2F);
		if (f2 < 0.0F) {
			f2 = 0.0F;
		}

		if (f2 > 1.0F) {
			f2 = 1.0F;
		}

		f2 = 1.2F - f2;
		return f2 * 0.6F;
	}

	@SideOnly(Side.CLIENT)
	public IRenderHandler getCloudRenderer() {
		if (super.getCloudRenderer() == null) {
			this.setCloudRenderer(new CloudRenderer());
		}

		return super.getCloudRenderer();
	}

	@SideOnly(Side.CLIENT)
	public IRenderHandler getSkyRenderer() {
		if (super.getSkyRenderer() == null) {
			this.setSkyRenderer(new SkyProviderBarnardaC());
		}

		return super.getSkyRenderer();
	}

	@Override
	public double getSolarWindMultiplier() {
		double solarMultiplier = -1D;
		if (solarMultiplier < 0D) {
			double s = this.getSolarSize();
			solarMultiplier = s * s * s * ConfigManagerCore.spaceStationEnergyScalar;
		}
		return solarMultiplier;
	}

	/*
	@Override
	public float getFogDensity(int x, int y, int z) {
		//GalaxySpace.debug((float)this.getMoonPhase(this.getWorldTime()) / 10 + "");
		return 0.3F + ((float) this.getMoonPhase(this.getWorldTime()) / 10);
	}

	@Override
	public int getFogColor(int x, int y, int z) {
		int R = 255 * 256 * 256;
		int G = 167 * 256;
		int B = 87;
		int color = R + G + B;

		return color - 16777216;
	}*/
}
