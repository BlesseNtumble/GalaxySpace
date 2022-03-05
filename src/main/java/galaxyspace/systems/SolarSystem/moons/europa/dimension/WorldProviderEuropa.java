package galaxyspace.systems.SolarSystem.moons.europa.dimension;

 


import java.util.List;

import asmodeuscore.api.dimension.IProviderFreeze;
import asmodeuscore.core.astronomy.dimension.world.gen.WorldProviderAdvancedSpace;
import galaxyspace.core.util.GSDimensions;
import galaxyspace.systems.SolarSystem.SolarSystemBodies;
import galaxyspace.systems.SolarSystem.moons.europa.dimension.sky.SkyProviderEuropa;
import galaxyspace.systems.SolarSystem.moons.europa.world.gen.BiomeProviderEuropa;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.client.CloudRenderer;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

 

public class WorldProviderEuropa extends WorldProviderAdvancedSpace implements IProviderFreeze{
	
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
    public boolean isFreeze()
	{
		return false;
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

        return SolarSystemBodies.europaJupiter;

    }

 

     //Created later

    @Override

    public Class<? extends IChunkGenerator> getChunkProviderClass() {
        return ChunkProviderEuropa.class;
    }
    
    @Override 
    public Class<? extends BiomeProvider> getBiomeProviderClass() { 
    	return BiomeProviderEuropa.class; 
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
    @SideOnly(Side.CLIENT)
    public float getStarBrightness(float par1)
    {
        final float var2 = this.world.getCelestialAngle(par1);
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

    public IRenderHandler getCloudRenderer(){
        return new CloudRenderer();
    }

	@SideOnly(Side.CLIENT)
    public IRenderHandler getSkyRenderer()
    {
    	if (super.getSkyRenderer() == null)
		{
			this.setSkyRenderer(new SkyProviderEuropa());
		}

		return super.getSkyRenderer();
    }

	@Override
	public int getDungeonSpacing() {
		return 0;
	}

	@Override
	public ResourceLocation getDungeonChestType() {
		return null;
	}

	@Override
	public List<Block> getSurfaceBlocks() {
		return null;
	}

	@Override
	public DimensionType getDimensionType() {
	
		return GSDimensions.EUROPA;
	}

	@Override
	public boolean enableAdvancedThermalLevel() {
		return true;
	}
	
	@Override
	protected float getThermalValueMod()
	{
		return 0.01F;
	}

}