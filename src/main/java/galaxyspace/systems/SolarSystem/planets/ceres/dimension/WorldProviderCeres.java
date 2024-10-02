package galaxyspace.systems.SolarSystem.planets.ceres.dimension;

 


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.api.dimension.IAdvancedSpace;
import galaxyspace.api.dimension.IProviderFreeze;
import galaxyspace.core.world.gen.WorldProviderAdvancedSpace;
import galaxyspace.systems.SolarSystem.SolarSystemBodies;
import galaxyspace.systems.SolarSystem.planets.ceres.dimension.sky.SkyProviderCeres;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.IExitHeight;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.api.world.ISolarLevel;
import micdoodle8.mods.galacticraft.core.client.CloudRenderer;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.IRenderHandler;

 

public class WorldProviderCeres extends WorldProviderAdvancedSpace implements IGalacticraftWorldProvider, IProviderFreeze, IExitHeight, ISolarLevel, IAdvancedSpace{
		 	
	@Override
	public double getSolarEnergyMultiplier()
	{
		
		return super.getSolarEnergyMultiplier();
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
    //Gravity is tricky. The only working values I have found are between 0.04 and 0.075 exclusive.
    //Anything else tends to be a bit wonky. Feel free to experiment.
    //Also a higher value means less gravity
    
	@Override
	public CelestialBody getCelestialBody() {
	    return SolarSystemBodies.planetCeres;
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
    //Created later
    @Override
    public Class<? extends IChunkProvider> getChunkProviderClass() {
        return ChunkProviderCeres.class;
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
    public boolean isSkyColored() {
        return false;
    }
    //Created Later
    @Override
    public Class<? extends WorldChunkManager> getWorldChunkManagerClass() {
        return WorldChunkManagerCeres.class;
    }

    @Override
    public boolean hasSunset() {
        return false;
    }
 
    @Override
    public boolean canCoordinateBeSpawn(int var1, int var2) {
        return true;
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
  
    @SideOnly(Side.CLIENT)
    public IRenderHandler getCloudRenderer()
    {
    	if (super.getCloudRenderer() == null)
		{
			this.setCloudRenderer(new CloudRenderer());
		}

		return super.getCloudRenderer();
    }
    
    @SideOnly(Side.CLIENT)
    public IRenderHandler getSkyRenderer()
    {
    	if (super.getSkyRenderer() == null)
		{
			this.setSkyRenderer(new SkyProviderCeres());
		}

		return super.getSkyRenderer();
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
}