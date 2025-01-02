package galaxyspace.systems.TCetiSystem.planets.tcetiF.dimension;

 


import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.api.dimension.IAdvancedSpace;
import galaxyspace.core.prefab.entity.EntityEntryPod;
import galaxyspace.core.world.gen.WorldProviderAdvancedSpace;
import galaxyspace.systems.ACentauriSystem.planets.proximaB.dimension.sky.CloudProviderProximaB;
import galaxyspace.systems.ACentauriSystem.planets.proximaB.dimension.sky.SkyProviderProximaB;
import galaxyspace.systems.TCetiSystem.TauCetiSystemBodies;
import galaxyspace.systems.TCetiSystem.planets.tcetiF.dimension.sky.CloudProviderTCetiF;
import galaxyspace.systems.TCetiSystem.planets.tcetiF.dimension.sky.SkyProviderTCetiF;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.IExitHeight;
import micdoodle8.mods.galacticraft.api.world.ISolarLevel;
import micdoodle8.mods.galacticraft.api.world.ITeleportType;
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

 

public class WorldProviderTCetiF extends WorldProviderAdvancedSpace implements IExitHeight, ISolarLevel, ITeleportType, IAdvancedSpace{
	
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
        return tier >= TauCetiSystemBodies.tcetiF.getTierRequirement();
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public float getCloudHeight() {
        return 240.0F;
    }
    
    @Override
    public double getHorizon() {
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
        return 0.0D;
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
    public boolean canBlockFreeze(int x, int y, int z, boolean byWater) {
		return false;
    }
 
    @Override
    public CelestialBody getCelestialBody() {
        return TauCetiSystemBodies.tcetiF;
    }

    @Override
    public Class<? extends IChunkProvider> getChunkProviderClass() {
        return ChunkProviderTCetiF.class;
    }

    @Override
    public Vector3 getFogColor() {
    	 float f = 1.0F - this.getStarBrightness(1.0F);
         return new Vector3(168 / 255.0F * f, 216 / 255.0F * f, 255 / 255.0F * f);
    }

    @Override
    public Vector3 getSkyColor() {
    	 float f = 1.0F - this.getStarBrightness(1.0F);
         return new Vector3(66 / 255.0F * f, 170 / 255.0F * f, 255 / 255.0F * f);
    }
     
    @Override
    public boolean isSkyColored() {
        return true;
    } 

    @Override
    public Class<? extends WorldChunkManager> getWorldChunkManagerClass() {
        return WorldChunkManagerTCetiF.class;
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
    public Vector3 getParaChestSpawnLocation(WorldServer arg0, EntityPlayerMP arg1, Random arg2) {
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

            EntityEntryPod lander = new EntityEntryPod(player);

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
    
    @SideOnly(Side.CLIENT)
    public IRenderHandler getCloudRenderer()
    {
    	if (super.getCloudRenderer() == null)
		{
			this.setCloudRenderer(new CloudProviderTCetiF());
		}

		return super.getCloudRenderer();
    }
    
    @SideOnly(Side.CLIENT)
    public IRenderHandler getSkyRenderer()
    {
    	if (super.getSkyRenderer() == null)
		{
			this.setSkyRenderer(new SkyProviderTCetiF());
		}

		return super.getSkyRenderer();
    }
	@Override
	public void setupAdventureSpawn(EntityPlayerMP player) {
		// TODO Auto-generated method stub		
	}

	@Override
	public double getSolarWindMultiplier() 
	{
		double solarMultiplier = -1D;
		return solarMultiplier;
	}
}