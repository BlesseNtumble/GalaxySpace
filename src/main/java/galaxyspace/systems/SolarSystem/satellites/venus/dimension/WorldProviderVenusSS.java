package galaxyspace.systems.SolarSystem.satellites.venus.dimension;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import asmodeuscore.core.astronomy.BodiesHelper;
import galaxyspace.core.util.GSDimensions;
import galaxyspace.systems.SolarSystem.SolarSystemBodies;
import galaxyspace.systems.SolarSystem.satellites.venus.dimension.sky.SkyProviderVenusSS;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.galaxies.IChildBody;
import micdoodle8.mods.galacticraft.api.galaxies.Moon;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.IExitHeight;
import micdoodle8.mods.galacticraft.api.world.IOrbitDimension;
import micdoodle8.mods.galacticraft.api.world.ISolarLevel;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.client.CloudRenderer;
import micdoodle8.mods.galacticraft.core.dimension.WorldProviderSpaceStation;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.world.gen.dungeon.RoomTreasure;
import micdoodle8.mods.galacticraft.planets.venus.VenusModule;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderVenusSS extends WorldProviderSpaceStation implements IOrbitDimension, ISolarLevel, IExitHeight{

	//Set<Entity> freefallingEntities = new HashSet<Entity>();
	
	@Override
    public DimensionType getDimensionType()
    {
        return GSDimensions.VENUS_SS;
    }
	
    @Override
    @SideOnly(Side.CLIENT)
    public Vector3 getFogColor() {
    	float night = this.getStarBrightness(1.0F);
        float day = 1.0F - this.getStarBrightness(1.0F);
        float dayColR = 203.0F / 255.0F;
        float dayColG = 147.0F / 255.0F;
        float dayColB = 0.0F / 255.0F;
        float nightColR = 131.0F / 255.0F;
        float nightColG = 108.0F / 255.0F;
        float nightColB = 46.0F / 255.0F;
        return new Vector3(dayColR * day + nightColR * night,
                dayColG * day + nightColG * night,
                dayColB * day + nightColB * night);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Vector3 getSkyColor() {

    	float night = this.getStarBrightness(1.0F);
        float day = 1.0F - this.getStarBrightness(1.0F);
        float dayColR = 105.0F / 255.0F;
        float dayColG = 107.0F / 255.0F;
        float dayColB = 281.0F / 255.0F;
        float nightColR = 118.0F / 255.0F;
        float nightColG = 89.0F / 255.0F;
        float nightColB = 61.0F / 255.0F;
        return new Vector3(dayColR * day + nightColR * night,
                dayColG * day + nightColG * night,
                dayColB * day + nightColB * night);
    }

    @Override
    public boolean hasSunset()
    {
        return true;
    }

    @Override
    public long getDayLength()
    {
        return 198000L;
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
        return true;
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
        return SolarSystemBodies.venusSpaceStation;
    }

    @Override
    public float getGravity()
    {
        return BodiesHelper.calculateGravity(8.8F);
    }

    @Override
    public double getMeteorFrequency()
    {
        return 0;
    }

    @Override
    public double getFuelUsageMultiplier()
    {
        return 1.0D;
    }

    @Override
    public String getPlanetToOrbit()
    {
        return VenusModule.planetVenus.getUnlocalizedName();
    }

    @Override
    public int getYCoordToTeleportToPlanet()
    {
        return 20;
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
        return tier >= VenusModule.planetVenus.getTierRequirement();
    }

    @Override
    public float getFallDamageModifier()
    {
        return 0.4F;
    }

    @Override
    public void updateWeather()
    {
        //freefallingEntities.clear();
        super.updateWeather();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setSpinDeltaPerTick(float angle)
    {
    	SkyProviderVenusSS skyProvider = ((SkyProviderVenusSS)this.getSkyRenderer());
        if (skyProvider != null)
            skyProvider.spinDeltaPerTick = angle;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getSkyRotation()
    {
    	SkyProviderVenusSS skyProvider = ((SkyProviderVenusSS)this.getSkyRenderer());
        return skyProvider.spinAngle;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void createSkyProvider()
    {
        this.setSkyRenderer(new SkyProviderVenusSS());
        this.setSpinDeltaPerTick(this.getSpinManager().getSpinRate());
        
       // if (this.getCloudRenderer() == null)
            //this.setCloudRenderer(new CloudRenderer());
    }
    
    @Override
    public IRenderHandler getCloudRenderer(){
        return super.getCloudRenderer();
    }
    
    @SideOnly(Side.CLIENT)
    public float getCloudHeight()
    {
        return 0;
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
