package galaxyspace.systems.VegaSystem.planets.vegaB.dimension;

 

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.api.dimension.IAdvancedSpace;
import galaxyspace.core.world.gen.WorldProviderAdvancedSpace;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.dimension.sky.SkyProviderBarnardaC;
import galaxyspace.systems.VegaSystem.VegaSystemBodies;
import galaxyspace.systems.VegaSystem.planets.vegaB.dimension.sky.SkyProviderVegaB;
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

 

public class WorldProviderVegaB extends WorldProviderAdvancedSpace implements IExitHeight, ISolarLevel, ITeleportType, IAdvancedSpace{

    @Override

    public boolean canSpaceshipTierPass(int tier) {

        return tier >= VegaSystemBodies.planetVega1.getTierRequirement();

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

 

    //Gravity is tricky. The only working values I have found are between 0.04 and 0.075 exclusive.

    //Anything else tends to be a bit wonky. Feel free to experiment.

    //Also a higher value means less gravity

    @Override

    public float getGravity() {

        return 0.058F;

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

    public float getThermalLevelModifier() {

        return 3;

    }

 

    @Override

    public float getWindLevel() {

        return 0;

    }

 

    @Override

    public boolean canRainOrSnow() {

        return false;

    }

 

    @Override

    public CelestialBody getCelestialBody() {

        return VegaSystemBodies.planetVega1;

    }

 

     //Created later

    @Override

    public Class<? extends IChunkProvider> getChunkProviderClass() {

        return ChunkProviderVegaB.class;

    }

 

    @Override

    public long getDayLength() {
        return 48000L;
    }

    @Override
    public boolean hasBreathableAtmosphere()
    {
        return false;
    }
    
    @Override
    public Vector3 getFogColor() {

    	float f = 0.3F;
        return new Vector3(0 / 255.0F * f, 0 / 255.0F * f, 0 / 255.0F * f);
    }

    @Override
    public Vector3 getSkyColor() {

    	 float f = 0.05F;
         return new Vector3(0 / 255.0F * f, 0 / 255.0F * f, 0 / 255.0F * f);

    }

     
     @Override
     public boolean isSkyColored()
     {
         return true;
     }
 

     //Created Later

    @Override

    public Class<? extends WorldChunkManager> getWorldChunkManagerClass() {

        return WorldChunkManagerVegaB.class;

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

    public double getSolarEnergyMultiplier() {

        return 1.1;

    }

 

    @Override

    public double getYCoordinateToTeleport() {

        return 1200;

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

    public Vector3 getPlayerSpawnLocation(WorldServer arg0, EntityPlayerMP arg1) {

    	 if (arg1 != null)
         {
             GCPlayerStats stats = GCPlayerStats.get(arg1);
             return new Vector3(stats.coordsTeleportedFromX, ConfigManagerCore.disableLander ? 250.0 : 900.0, stats.coordsTeleportedFromZ);
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

        return var3 * var3 * 0.5F + 0.5F;
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
       return f2 * 0.7F;
    }
    
    @SideOnly(Side.CLIENT)
	@Override
	public IRenderHandler getCloudRenderer() {		
		return new CloudRenderer();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IRenderHandler getSkyRenderer() {
		if (super.getSkyRenderer() == null) {
			this.setSkyRenderer(new SkyProviderVegaB());
		}

		return super.getSkyRenderer();
	}

	@Override
	public void setupAdventureSpawn(EntityPlayerMP player) {
		// TODO Auto-generated method stub
		
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
			double s = this.getSolarSize();
			solarMultiplier = s * s * s * ConfigManagerCore.spaceStationEnergyScalar;
		}
		if(!getSkyColor().isZero()) return 0D;
		return solarMultiplier;
	}

}