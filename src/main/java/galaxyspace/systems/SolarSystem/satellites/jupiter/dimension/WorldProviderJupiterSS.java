package galaxyspace.systems.SolarSystem.satellites.jupiter.dimension;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import galaxyspace.core.util.GSDimensions;
import galaxyspace.systems.SolarSystem.SolarSystemBodies;
import galaxyspace.systems.SolarSystem.planets.ceres.world.gen.BiomeProviderCeres;
import galaxyspace.systems.SolarSystem.satellites.mars.dimension.sky.SkyProviderMarsSS;
import galaxyspace.systems.SolarSystem.satellites.venus.dimension.ChunkProviderVenusSS;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.galaxies.IChildBody;
import micdoodle8.mods.galacticraft.api.galaxies.Moon;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.IExitHeight;
import micdoodle8.mods.galacticraft.api.world.IOrbitDimension;
import micdoodle8.mods.galacticraft.api.world.ISolarLevel;
import micdoodle8.mods.galacticraft.api.world.IZeroGDimension;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.client.CloudRenderer;
import micdoodle8.mods.galacticraft.core.dimension.WorldProviderSpaceStation;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.world.gen.dungeon.RoomTreasure;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderJupiterSS extends WorldProviderSpaceStation implements IOrbitDimension, IZeroGDimension, ISolarLevel, IExitHeight{

	Set<Entity> freefallingEntities = new HashSet<Entity>();
	

	@Override
    public boolean shouldForceRespawn()
    {
        return false;
    }
    
	@Override
    public DimensionType getDimensionType()
    {
        return GSDimensions.JUPITER_SS;
    }
	
	@Override
    public Vector3 getFogColor()
    {
        return new Vector3(0, 0, 0);
    }

    @Override
    public Vector3 getSkyColor()
    {
        return new Vector3(0, 0, 0);
    }

    @Override
    public boolean hasSunset()
    {
        return false;
    }

    @Override
    public long getDayLength()
    {
        return 48000L;
    }
    
    @Override
    public boolean isDaytime()
    {
        final float a = this.world.getCelestialAngle(0F);
        //TODO: adjust this according to size of planet below
        return a < 0.42F || a > 0.58F;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public float getStarBrightness(float par1)
    {
        final float var2 = this.world.getCelestialAngle(par1);
        float var3 = 1.0F - (MathHelper.cos(var2 * Constants.twoPI) * 2.0F + 0.25F);

        if (var3 < 0.0F)
        {
            var3 = 0.0F;
        }

        if (var3 > 1.0F)
        {
            var3 = 1.0F;
        }

        return var3 * var3 * 0.5F + 0.3F;
    }

    @Override
    public boolean isSkyColored()
    {
        return false;
    }

    @Override
    public double getHorizon()
    {
        return 44.0D;
    }

    @Override
    public int getAverageGroundLevel()
    {
        return 64;
    }

    @Override
    public boolean canCoordinateBeSpawn(int var1, int var2)
    {
        return true;
    }

    @Override
    public CelestialBody getCelestialBody()
    {
        return SolarSystemBodies.jupiterSpaceStation;
    }

    @Override
    public float getGravity()
    {
        return 0.075F;
    }

    @Override
    public double getMeteorFrequency()
    {
        return 0;
    }

    @Override
    public double getFuelUsageMultiplier()
    {
        return 0.5D;
    }

    @Override
    public Class<? extends IChunkGenerator> getChunkProviderClass()
    {
        return ChunkProviderJupiterSS.class;
    }
    
    @Override
    public Class<? extends BiomeProvider> getBiomeProviderClass()
    {
    	return BiomeProviderCeres.class;
    }
    
    @Override
    public String getPlanetToOrbit()
    {
        return "ceres";
    }

    @Override
    public int getYCoordToTeleportToPlanet()
    {
        return -500;
    }

    @Override
    public String getSaveFolder()
    {
        return "DIM_SPACESTATION" + this.getDimension();
    }

    @Override
	public double getSolarEnergyMultiplier()
	{
		double solarMultiplier = -1D;
		if (solarMultiplier < 0D)
		{
			double s = this.getCelestialBody() instanceof IChildBody ? this.getSolarSizeForMoon() : this.getSolarSize();
			solarMultiplier = s * s * s * ConfigManagerCore.spaceStationEnergyScalar;
		}
		return solarMultiplier;
	}
    
    public float getSolarSizeForMoon()
	{
	    return 1.0F / ((Moon)this.getCelestialBody()).getParentPlanet().getRelativeDistanceFromCenter().unScaledDistance;
	}

    @Override
    public double getYCoordinateToTeleport()
    {
        return 1000;
    }

    @Override
    public boolean canSpaceshipTierPass(int tier)
    {
        return tier > 2;
    }

    @Override
    public float getFallDamageModifier()
    {
        return 0.4F;
    }

    @Override
    public boolean inFreefall(Entity entity)
    {
        return freefallingEntities.contains(entity);
    }

    @Override
    public void setInFreefall(Entity entity)
    {
        freefallingEntities.add(entity);
    }
    
    @Override
    public void updateWeather()
    {
        freefallingEntities.clear();
        super.updateWeather();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setSpinDeltaPerTick(float angle)
    {
    	SkyProviderMarsSS skyProvider = ((SkyProviderMarsSS)this.getSkyRenderer());
        if (skyProvider != null)
            skyProvider.spinDeltaPerTick = angle;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getSkyRotation()
    {
    	SkyProviderMarsSS skyProvider = ((SkyProviderMarsSS)this.getSkyRenderer());
        return skyProvider.spinAngle;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void createSkyProvider()
    {
        this.setSkyRenderer(new SkyProviderMarsSS());
        this.setSpinDeltaPerTick(this.getSpinManager().getSpinRate());
        
        if (this.getCloudRenderer() == null)
            this.setCloudRenderer(new CloudRenderer());
    }
    
    @Override
    public int getDungeonSpacing()
    {
        return 0;
    }

    @Override
    public ResourceLocation getDungeonChestType()
    {
        return RoomTreasure.MOONCHEST;
    }

    @Override
    public List<Block> getSurfaceBlocks()
    {
        return null;
    }
}
