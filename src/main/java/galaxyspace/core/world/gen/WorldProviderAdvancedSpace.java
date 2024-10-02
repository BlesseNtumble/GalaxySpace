package galaxyspace.core.world.gen;

import galaxyspace.api.BodiesHelper;
import galaxyspace.api.BodiesHelper.BodiesData;
import galaxyspace.api.dimension.IAdvancedSpace;
import micdoodle8.mods.galacticraft.api.galaxies.IChildBody;
import micdoodle8.mods.galacticraft.api.galaxies.Moon;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.WorldProviderSpace;
import micdoodle8.mods.galacticraft.api.world.IExitHeight;
import micdoodle8.mods.galacticraft.api.world.ISolarLevel;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;

public abstract class WorldProviderAdvancedSpace extends WorldProviderSpace implements ISolarLevel, IExitHeight, IAdvancedSpace {
    
	BodiesData data = BodiesHelper.data.get(this.getCelestialBody());
	
	@Override
    public float getGravity() {
        return data.getGravityPlanet();
    }

	@Override
	public float getThermalLevelModifier()
	{
		/*
	    float angle = this.world.getCelestialAngle(this.getDayLength());
	    float value = 1.0F - (MathHelper.cos(angle * (float) Math.PI * 2.0F) * 1.0F + 0.4F);
	    value = 1.0F - value;
	    return value * data.getTemperaturePlanet();
*/
	    return data.getTemperaturePlanet();
	}

	@Override
	public float getWindLevel() {
	    return data.getWindPlanet();
	}
	
	@Override
    public long getDayLength() {
        return data.getDayLengthPlanet();
    }

    @Override
    public boolean hasBreathableAtmosphere() {
        return data.getBreathablePlanet();
    }
    
    @Override
	public int AtmosphericPressure() {
		return data.getPressurePlanet();
	}
    
    @Override
	public boolean SolarRadiation() {
		return data.getSolarRadiationPlanet();
	}
    
    @Override
    public boolean canSpaceshipTierPass(int tier) {
        return tier >= this.getCelestialBody().getTierRequirement();
    }
    
	public float getSolarSizeForMoon()
	{
	    return 1.0F / ((Moon)this.getCelestialBody()).getParentPlanet().getRelativeDistanceFromCenter().unScaledDistance;
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
	
	@Override
	public double getSolarWindMultiplier() 
	{
		double solarMultiplier = -1D;
		if (solarMultiplier < 0D)
		{
			double s = this.getCelestialBody() instanceof IChildBody ? this.getSolarSizeForMoon() : this.getSolarSize();
			solarMultiplier = s * s * s;
		}
		return !this.getCelestialBody().atmosphere.isEmpty() ? -1D : solarMultiplier;
	}	
	
    @Override
    public double getYCoordinateToTeleport() {
        return 1000.0D;
    }
    
    @Override
    public ClassBody getClassBody()
    {
    	return ClassBody.STONE;
    }
    
    @Override
    public int getMoonPhase(long time)
    {
    	long timeday = this.getDayLength() > 0 ? this.getDayLength() : 1;
        return (int)(time / timeday % 8L + 8L) % 8;
    }
}
