package galaxyspace.systems.SolarSystem.satellites.mars.dimension;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.api.dimension.IAdvancedSpace;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.systems.SolarSystem.SolarSystemBodies;
import galaxyspace.systems.SolarSystem.satellites.mars.dimension.sky.SkyProviderMarsSS;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.IExitHeight;
import micdoodle8.mods.galacticraft.api.world.IOrbitDimension;
import micdoodle8.mods.galacticraft.api.world.ISolarLevel;
import micdoodle8.mods.galacticraft.api.world.IZeroGDimension;
import micdoodle8.mods.galacticraft.core.client.CloudRenderer;
import micdoodle8.mods.galacticraft.core.dimension.WorldProviderSpaceStation;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.planets.mars.MarsModule;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderMarsSS extends WorldProviderSpaceStation implements IAdvancedSpace, IOrbitDimension, IZeroGDimension, ISolarLevel, IExitHeight
{
    public int spaceStationDimensionID;

    @Override
    public void setDimension(int var1)
    {
        this.spaceStationDimensionID = var1;
        super.setDimension(var1);
    }


    @Override
    public CelestialBody getCelestialBody()
    {
        return SolarSystemBodies.marsSpaceStation;
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
    public boolean canRainOrSnow()
    {
        return false;
    }

    @Override
    public boolean hasSunset()
    {
        return false;
    }

    @Override
    public long getDayLength()
    {
        return 24000L;
    }

    @Override
    public boolean shouldForceRespawn()
    {
        return !ConfigManagerCore.forceOverworldRespawn;
    }

    @Override
    public Class<? extends IChunkProvider> getChunkProviderClass()
    {
        return ChunkProviderMarsSS.class;
    }

    @Override
    public Class<? extends WorldChunkManager> getWorldChunkManagerClass()
    {
        return WorldChunkManagerMarsSS.class;
    }

    public boolean isDaytime()
    {
        final float a = this.worldObj.getCelestialAngle(0F);
        //TODO: adjust this according to size of planet below
        return a < 0.42F || a > 0.58F;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getStarBrightness(float par1)
    {
        final float var2 = this.worldObj.getCelestialAngle(par1);
        float var3 = 1.0F - (MathHelper.cos(var2 * (float) Math.PI * 2.0F) * 2.0F + 0.25F);

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

	//Overriding only in case the Galacticraft API is not up-to-date
    //(with up-to-date API this makes zero difference)
    @Override
    public boolean isSurfaceWorld()
    {
        return (this.worldObj == null) ? false : this.worldObj.isRemote;
    }

	//Overriding only in case the Galacticraft API is not up-to-date
    //(with up-to-date API this makes zero difference)
	@Override
	public boolean canRespawnHere()
	{
		return false;
	}
	
	//Overriding only in case the Galacticraft API is not up-to-date
    //(with up-to-date API this makes zero difference)
    @Override
    public int getRespawnDimension(EntityPlayerMP player)
    {
        return this.shouldForceRespawn() ? this.dimensionId : 0;
    }

    @Override
    public String getDimensionName()
    {
        return "Space Station " + this.spaceStationDimensionID;
    }

    @Override
    public float getGravity()
    {
        return 0.075F;//0.073F;
    }

    @Override
    public boolean hasBreathableAtmosphere()
    {
        return false;
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
    public String getPlanetToOrbit()
    {
        return GSConfigCore.enableGCMars ? MarsModule.planetMars.getUnlocalizedName() : SolarSystemBodies.planetMars.getUnlocalizedName();
    }

    @Override
    public int getYCoordToTeleportToPlanet()
    {
        return 30;
    }

    @Override
    public String getSaveFolder()
    {
        return "DIM_SPACESTATION" + this.spaceStationDimensionID;
    }

    @Override
    public double getSolarEnergyMultiplier()
    {
        return ConfigManagerCore.spaceStationEnergyScalar;
    }

    @Override
    public double getYCoordinateToTeleport()
    {
        return 1200;
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
    public float getSoundVolReductionAmount()
    {
        return 50.0F;
    }

    @Override
    public float getThermalLevelModifier()
    {
        return 1;
    }

    @Override
    public float getWindLevel()
    {
        return 0.0F;
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
	public void createSkyProvider()
	{
		this.setSkyRenderer(new SkyProviderMarsSS());
		this.setSpinDeltaPerTick(this.getSpinManager().getSpinRate());
		
		if (this.getCloudRenderer() == null)
			this.setCloudRenderer(new CloudRenderer());
	}


	@Override
	public int AtmosphericPressure() {
		return 0;
	}


	@Override
	public boolean SolarRadiation() {
		return true;
	}


	@Override
	public double getSolarWindMultiplier() 
	{
		double solarMultiplier = -1D;
		if (solarMultiplier < 0D)
		{
			double s = this.getCelestialBody().getRelativeDistanceFromCenter().unScaledDistance;
			solarMultiplier = s * ConfigManagerCore.spaceStationEnergyScalar;
		}
		return solarMultiplier;
	}
	
	@Override
	public ClassBody getClassBody() {
		return ClassBody.SPACESTATION;
	} 
}
