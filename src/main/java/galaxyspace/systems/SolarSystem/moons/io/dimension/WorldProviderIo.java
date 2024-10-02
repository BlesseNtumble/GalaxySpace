package galaxyspace.systems.SolarSystem.moons.io.dimension;

 


import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.api.dimension.IAdvancedSpace;
import galaxyspace.api.dimension.IProviderFreeze;
import galaxyspace.core.world.gen.WorldProviderAdvancedSpace;
import galaxyspace.systems.SolarSystem.SolarSystemBodies;
import galaxyspace.systems.SolarSystem.moons.io.dimension.sky.SkyProviderIo;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.IExitHeight;
import micdoodle8.mods.galacticraft.api.world.ISolarLevel;
import micdoodle8.mods.galacticraft.api.world.ITeleportType;
import micdoodle8.mods.galacticraft.core.client.CloudRenderer;
import micdoodle8.mods.galacticraft.core.entities.EntityLander;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.IRenderHandler;

 

public class WorldProviderIo extends WorldProviderAdvancedSpace implements IProviderFreeze, IExitHeight, ISolarLevel, ITeleportType, IAdvancedSpace{

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

        return 0.8;

    }
 

    @Override

    public double getMeteorFrequency() {

        return 3.0;

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

        return SolarSystemBodies.ioJupiter;

    }

 

     //Created later

    @Override

    public Class<? extends IChunkProvider> getChunkProviderClass() {

        return ChunkProviderIo.class;

    }
    
    @Override
    public Vector3 getFogColor() {

    	return new Vector3(0, 0, 0);

    }

    @Override
    public Vector3 getSkyColor() {

    	return new Vector3(0, 0, 0);

    }

     
     @Override
     public boolean isSkyColored()
     {
         return false;
     }
 

     //Created Later

    @Override

    public Class<? extends WorldChunkManager> getWorldChunkManagerClass() {

        return WorldChunkManagerIo.class;

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
        return 1000.0D;
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

            EntityLander lander = new EntityLander(player);

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
       float f3 = this.worldObj.getWorldTime();
       if(f2 < 0.0F) {
          f2 = 0.0F;
       }

       if(f2 > 1.0F) {
          f2 = 1.0F;
       }

       f2 = 1.0F - f2;
       /*if(f3 > getDayLength() / 2 * 0.37 && f3 < getDayLength() / 2 * 0.64)
       {
    	   return f2 * 0.1F;
       }*/
       return f2 * 1.0F;
    }
    
    @Override
    public IRenderHandler getCloudRenderer(){

        //Removes clouds from dimension

        return new CloudRenderer();

    }

	@Override
	public void setupAdventureSpawn(EntityPlayerMP player) {
		// TODO Auto-generated method stub
		
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
	
	@SideOnly(Side.CLIENT)
	public IRenderHandler getSkyRenderer()
	{
		if (super.getSkyRenderer() == null)
		{
			this.setSkyRenderer(new SkyProviderIo());
		}

		return super.getSkyRenderer();
	}

}