package galaxyspace.systems.SolarSystem.moons.triton.dimension;

 


import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.api.dimension.IAdvancedSpace;
import galaxyspace.core.world.gen.WorldProviderAdvancedSpace;
import galaxyspace.systems.SolarSystem.SolarSystemBodies;
import galaxyspace.systems.SolarSystem.moons.triton.dimension.sky.SkyProviderTriton;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.IExitHeight;
import micdoodle8.mods.galacticraft.api.world.ISolarLevel;
import micdoodle8.mods.galacticraft.api.world.ITeleportType;
import micdoodle8.mods.galacticraft.core.client.CloudRenderer;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.planets.mars.entities.EntityLandingBalloons;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.IRenderHandler;

 

public class WorldProviderTriton extends WorldProviderAdvancedSpace implements IExitHeight, ISolarLevel, ITeleportType, IAdvancedSpace{

	private float[] colorsSunriseSunset = new float[4];
		
    @Override
	public double getSolarEnergyMultiplier()
	{
		double solarMultiplier = -1D;
		if (solarMultiplier < 0D)
		{
			double s = this.getSolarSizeForMoon();
			solarMultiplier = s * s * s * ConfigManagerCore.spaceStationEnergyScalar;
		}
		return solarMultiplier;
	}

    @Override
    public double getHorizon()
    {
        return 44.0D;
    }

    @Override

    public float getFallDamageModifier() {

        return 0.16F;

    }

 

    @Override

    public double getFuelUsageMultiplier() {

        return 0.8D;

    }

 

    //Gravity is tricky. The only working values I have found are between 0.04 and 0.075 exclusive.

    //Anything else tends to be a bit wonky. Feel free to experiment.

    //Also a higher value means less gravity

   
    @Override
    public double getMeteorFrequency() {
        return 9.0D;
    }

 

    @Override

    public float getSoundVolReductionAmount() {
        return Float.MAX_VALUE;
    }

    @Override
    public boolean canRainOrSnow() {
        return false;
    } 

    @Override

    public CelestialBody getCelestialBody() {
        return SolarSystemBodies.tritonNeptune;
    }

 

     //Created later

    @Override

    public Class<? extends IChunkProvider> getChunkProviderClass() {

        return ChunkProviderTriton.class;

    }

    
    @Override
    public Vector3 getFogColor() {

    	float f = 0.4F - this.getSunBrightness(1.0F);
    	return new Vector3(104 / 255.0F * f, 104 / 255.0F * f, 104 / 255.0F * f);
    }

    @Override
    public Vector3 getSkyColor() {
    	return new Vector3(0, 0, 0);
    }

    @SideOnly(Side.CLIENT)
    public float[] calcSunriseSunsetColors(float p_76560_1_, float p_76560_2_)
    {
       /* float f2 = 0.4F;
        float f3 = MathHelper.cos(p_76560_1_ * (float)Math.PI * 2.0F) - 0.0F;
        float f4 = -0.0F;

        if (f3 >= f4 - f2 && f3 <= f4 + f2)
        {
            float f5 = (f3 - f4) / f2 * 0.5F + 0.5F;
            float f6 = 1.0F - (1.0F - MathHelper.sin(f5 * (float)Math.PI)) * 0.99F;
            f6 *= f6;
            this.colorsSunriseSunset[0] = f5 * 0.3F + 0.7F;
            this.colorsSunriseSunset[1] = f5 * f5 * 0.7F + 0.2F;
            this.colorsSunriseSunset[2] = f5 * f5 * 0.0F + 0.2F;
            this.colorsSunriseSunset[3] = f6;
            return this.colorsSunriseSunset;
        }
        else
        {*/
            return null;
       // }
    }
     
     @Override
     public boolean isSkyColored() {
         return true;
     }
 

     //Created Later

    @Override

    public Class<? extends WorldChunkManager> getWorldChunkManagerClass() {

        return WorldChunkManagerTriton.class;

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

    public double getYCoordinateToTeleport() {

        return 800;

    }

 

    @Override

    public Vector3 getEntitySpawnLocation(WorldServer arg0, Entity arg1) {

    	return new Vector3(arg1.posX, ConfigManagerCore.disableLander ? 250.0 : 900.0, arg1.posZ);

    }

 

    @Override

    public Vector3 getParaChestSpawnLocation(WorldServer arg0,

            EntityPlayerMP arg1, Random arg2) {

        //Randomize parachest spawn location

    	if (ConfigManagerCore.disableLander)
        {
            final double x = (arg2.nextDouble() * 2 - 1.0D) * 5.0D;
            final double z = (arg2.nextDouble() * 2 - 1.0D) * 5.0D;
            return new Vector3(x, 220.0D, z);
        }

        return null;

    }

 

    @Override
    public Vector3 getPlayerSpawnLocation(WorldServer world, EntityPlayerMP player)
    {
        if (player != null)
        {
            GCPlayerStats stats = GCPlayerStats.get(player);
            double x = stats.coordsTeleportedFromX;
            double z = stats.coordsTeleportedFromZ;
            int limit = ConfigManagerCore.otherPlanetWorldBorders - 2;
            if (limit > 20)
            {
                if (x > limit)
                {
                    z *= limit / x;
                    x = limit;
                }
                else if (x < -limit)
                {   
                    z *= -limit / x;
                    x = -limit;
                }
                if (z > limit)
                {
                    x *= limit / z;
                    z = limit;
                }
                else if (z < -limit)
                {
                    x *= - limit / z;
                    z = -limit;
                }
            }
            return new Vector3(x, ConfigManagerCore.disableLander ? 250.0 : 900.0, z);
        }

        return null;
    }

 

    @Override

    public void onSpaceDimensionChanged(World arg0, EntityPlayerMP player, boolean arg2) {
    	
        if (player != null && GCPlayerStats.get(player).teleportCooldown <= 0)
        {
            if (player.capabilities.isFlying)
            {
                player.capabilities.isFlying = false;
            }

            EntityLandingBalloons lander = new EntityLandingBalloons(player);

            if (!arg0.isRemote)
            {
                arg0.spawnEntityInWorld(lander);
            }

            GCPlayerStats.get(player).teleportCooldown = 10;
        }
    }

    @Override

    public boolean useParachute() {

    	return ConfigManagerCore.disableLander;

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
       return f2 * 0.2F;
    }

	@Override
	public void setupAdventureSpawn(EntityPlayerMP player) {

	}
	
	@Override
	public double getSolarWindMultiplier() 
	{
		double solarMultiplier = -1D;
		if (solarMultiplier < 0D)
		{
			double s = this.getSolarSizeForMoon();
			solarMultiplier = s * s * s * ConfigManagerCore.spaceStationEnergyScalar;
		}
		return solarMultiplier;
	}

	@Override
	public IRenderHandler getCloudRenderer() {
		return new CloudRenderer();
	}
	 
	@SideOnly(Side.CLIENT)
	public IRenderHandler getSkyRenderer()
	{
		if (super.getSkyRenderer() == null)
		{
			this.setSkyRenderer(new SkyProviderTriton());
		}

		return super.getSkyRenderer();
	}
}