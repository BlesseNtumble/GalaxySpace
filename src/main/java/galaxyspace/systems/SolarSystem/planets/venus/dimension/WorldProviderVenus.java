package galaxyspace.systems.SolarSystem.planets.venus.dimension;

 


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.api.dimension.IAdvancedSpace;
import galaxyspace.api.dimension.ILightning;
import galaxyspace.api.dimension.IPlanetFog;
import galaxyspace.api.dimension.IProviderFreeze;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.world.gen.WorldProviderAdvancedSpace;
import galaxyspace.systems.SolarSystem.SolarSystemBodies;
import galaxyspace.systems.SolarSystem.planets.venus.dimension.sky.CloudProviderVenus;
import galaxyspace.systems.SolarSystem.planets.venus.dimension.sky.SkyProviderVenus;
import galaxyspace.systems.SolarSystem.planets.venus.dimension.sky.WeatherProviderVenus;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.IExitHeight;
import micdoodle8.mods.galacticraft.api.world.ISolarLevel;
import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.IRenderHandler;

 

public class WorldProviderVenus extends WorldProviderAdvancedSpace implements IProviderFreeze, IExitHeight, ISolarLevel, ILightning, IAdvancedSpace, IPlanetFog
{
    private float prevRainingStrength;
    private float rainingStrength;
    private boolean raining = false;
    private int rainTime = 100;
    private int rainChange = 100;
    private float targetRain = 0.0F;
	    
    @Override
	public double getSolarEnergyMultiplier()
	{
		double solarMultiplier = -1D;
		if (solarMultiplier < 0D)
		{
			double s = this.getSolarSize();
			solarMultiplier = s / 4;
		}
		return solarMultiplier;
	}

    @Override
    public double getHorizon() {
        return 44.0D;
    }

    @Override
    public float getFallDamageModifier() {
        return 0.8F;
    } 

    @Override
    public double getFuelUsageMultiplier() {
        return 0.8;
    } 

    @Override
    public double getMeteorFrequency() {
        return 10.0;
    } 

    @Override
    public float getSoundVolReductionAmount() {
        return Float.MAX_VALUE;
    } 
   
    @SideOnly(Side.CLIENT)
    public IRenderHandler getCloudRenderer()
    {
    	if (super.getCloudRenderer() == null)
		{
			this.setCloudRenderer(new CloudProviderVenus());
		}

		return super.getCloudRenderer();
    }
    
    @SideOnly(Side.CLIENT)
    public IRenderHandler getSkyRenderer()
    {
    	if (super.getSkyRenderer() == null)
		{
			this.setSkyRenderer(new SkyProviderVenus());
		}

		return super.getSkyRenderer();
    }
    
    @SideOnly(Side.CLIENT)
    public IRenderHandler getWeatherRenderer()
    {
    	if (super.getWeatherRenderer() == null)
		{
			this.setWeatherRenderer(new WeatherProviderVenus());
		}

		return super.getWeatherRenderer();
    }
    
    public float getRainStrength(float var1)
    {
		return 1.0F;
    	
    }
    
    @Override
    public boolean canRainOrSnow() {
        return true;
    }
    
    @Override
    public boolean canBlockFreeze(int x, int y, int z, boolean byWater) {
        return false;
    }

    @Override
    public boolean canDoLightning(Chunk chunk) {
        return true;
    }

    @Override
    public CelestialBody getCelestialBody() {
        return SolarSystemBodies.planetVenus;
    }

    @Override
    public Class<? extends IChunkProvider> getChunkProviderClass() {
        return ChunkProviderVenus.class;
    }
    
    @Override
    public Vector3 getFogColor() {
    	float f = 0.5F;
    	return new Vector3(194 / 255.0F * f, 113 / 255.0F * f, 32 / 255.0F * f);
    }

    @Override
    public Vector3 getSkyColor() {
        float f = 0.5F - this.getStarBrightness(1.0F);
        return new Vector3(238 / 255.0F * f, 140 / 255.0F * f, 41 / 255.0F * f);
    }
    
    @Override
    public boolean isSkyColored() {
        return true;
    }

    @Override
    public Class<? extends WorldChunkManager> getWorldChunkManagerClass() {
        return WorldChunkManagerVenus.class;
    } 

    @Override
    public boolean hasSunset() {
        return false;
    }

    @Override
    public boolean shouldForceRespawn() {
        return false;
    }

    @Override
    public double getYCoordinateToTeleport() {
        return 1000.0D;
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
    @SideOnly(Side.CLIENT)
    public float getSunBrightness(float par1) {
       float f1 = this.worldObj.getCelestialAngle(1.0F);
       float f2 = 1.25F - (MathHelper.cos(f1 * 3.1415927F * 2.0F) * 2.0F + 0.2F);
       if(f2 < 0.0F) {
          f2 = 0.0F;
       }

       if(f2 > 1.0F) {
          f2 = 1.0F;
       }

       f2 = 1.2F - f2;
       return f2 * 1.0F;
    }
    
    @Override
	public double getLightningStormFrequency()
	{
		return 0.8D;
	}

	@Override
	public float getFogDensity(int x, int y, int z) {
		return GSConfigCore.enableFogVenus ? 0.25F : 1.0F;
	}

	@Override
	public int getFogColor(int x, int y, int z) {
	
		int R = 194 * 256 * 256;
		int G = 152 * 256;
		int B = 44;
		int color = R+G+B;	
		
	    return color - 16777216;
	}

	@Override
	public int getYPosLightning() {	
		
		return 20;
	}
	
	@Override
	public double getSolarWindMultiplier() 
	{
		double solarMultiplier = -1D;
		return solarMultiplier;
	}

	@Override
    public void updateWeather()
    {
		if (!this.worldObj.isRemote)
        {
			if(--this.rainTime <= 0)
			{
				this.raining = !this.raining;
				if(this.raining)
				{
					 this.rainTime = (this.worldObj.rand.nextInt(3600) + 1000);
				}
				else
                {
                    this.rainTime = (this.worldObj.rand.nextInt(2000) + 1000);
                }
			}
			
			if (--this.rainChange <= 0) 
			{
				this.targetRain = 0.15F + this.worldObj.rand.nextFloat() * 0.45F;
				this.rainChange = (this.worldObj.rand.nextInt(200) + 100);
			}
			
			float strength = this.worldObj.rainingStrength;
			this.worldObj.prevRainingStrength = strength;
			if (this.raining && strength < this.targetRain) {
				strength += 0.004F;
			} else if (!this.raining || strength > this.targetRain) {
				strength -= 0.004F;
			}
			this.worldObj.rainingStrength = MathHelper.clamp_float(strength, 0.0F, 0.6F);
        }
    }
}