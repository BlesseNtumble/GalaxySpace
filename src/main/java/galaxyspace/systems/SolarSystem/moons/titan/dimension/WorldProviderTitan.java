package galaxyspace.systems.SolarSystem.moons.titan.dimension;

 


import java.util.List;
import java.util.Random;

import asmodeuscore.api.dimension.IProviderFog;
import asmodeuscore.api.dimension.IProviderFreeze;
import asmodeuscore.core.astronomy.dimension.world.gen.WorldProviderAdvancedSpace;
import galaxyspace.core.client.fx.ParticleRainCustom;
import galaxyspace.core.util.GSDimensions;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.SolarSystem.SolarSystemBodies;
import galaxyspace.systems.SolarSystem.moons.titan.dimension.sky.SkyProviderTitan;
import galaxyspace.systems.SolarSystem.moons.titan.dimension.sky.WeatherProviderTitan;
import galaxyspace.systems.SolarSystem.moons.titan.world.gen.BiomeProviderTitan;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.IWeatherProvider;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

 

public class WorldProviderTitan extends WorldProviderAdvancedSpace implements IProviderFreeze, IProviderFog, IWeatherProvider{
	
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
    public CelestialBody getCelestialBody() {
        return SolarSystemBodies.titanSaturn;
    }
    
    @Override
    public Class<? extends IChunkGenerator> getChunkProviderClass() {
        return ChunkProviderTitan.class;
    }
    
    @Override 
    public Class<? extends BiomeProvider> getBiomeProviderClass() { 
    	return BiomeProviderTitan.class; 
    }
    
    @Override
    public Vector3 getFogColor() {
    	float f = 1.0F;
        return new Vector3(224 / 255.0F * f, 174 / 255.0F * f, 132 / 255.0F * f);
    }
    
    @Override
    public Vector3 getSkyColor() {
    	float f = 1.0F - this.getStarBrightness(1.0F) - (this.world.getRainStrength(1.0F) / 4);
    	return new Vector3(204 / 255.0F * f, 121 / 255.0F * f, 53 / 255.0F * f);
    }

     
	@Override
	public boolean isSkyColored() {
		return true;
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
    @SideOnly(Side.CLIENT)
    public IRenderHandler getCloudRenderer(){
        return super.getCloudRenderer();
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public Vec3d getCloudColor(float partialTicks)
    {
    	float f = this.world.getCelestialAngle(partialTicks);
        float f1 = MathHelper.cos(f * ((float)Math.PI * 2F)) * 2.0F + 0.5F;
        f1 = MathHelper.clamp(f1, 0.0F, 1.0F);
        float r = 1.0F;
        float g = 1.0F;
        float b = 1.0F;
        float a = this.world.getRainStrength(partialTicks);

        if (a > 0.0F)
        {
            float f6 = (r * 0.3F + g * 0.59F + b * 0.11F) * 0.6F;
            float f7 = 1.0F - a * 0.95F;
            r = r * f7 + f6 * (1.0F - f7);
            g = g * f7 + f6 * (1.0F - f7);
            b = b * f7 + f6 * (1.0F - f7);
        }

        r = r * (f1 * 0.9F + 0.1F);
        g = g * (f1 * 0.9F + 0.1F);
        b = b * (f1 * 0.85F + 0.15F);
        float f9 = this.world.getThunderStrength(partialTicks);

        if (f9 > 0.0F)
        {
            float f10 = (r * 0.3F + g * 0.59F + b * 0.11F) * 0.2F;
            float f8 = 1.0F - f9 * 0.95F;
            r = r * f8 + f10 * (1.0F - f8);
            g = g * f8 + f10 * (1.0F - f8);
            b = b * f8 + f10 * (1.0F - f8);
        }

        return new Vec3d((double)r, (double)g, (double)b);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public float getCloudHeight()
    {
        return 180.0F;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public IRenderHandler getWeatherRenderer()
    {
        return new WeatherProviderTitan();
    }

    @Override
	@SideOnly(Side.CLIENT)
    public IRenderHandler getSkyRenderer()
    {
    	if (super.getSkyRenderer() == null)
		{
			this.setSkyRenderer(new SkyProviderTitan());
		}

		return super.getSkyRenderer();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Particle getParticle(WorldClient world, double x, double y, double z)
    {
        return new ParticleRainCustom(world, x, y + 0.1D, z, 0.0D, 0.0D, 0.0D, EnumParticleTypes.SMOKE_NORMAL.getParticleID(), 1.0F, new Vector3(1F, 0.4F, 0.0F));
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
	
		return GSDimensions.TITAN;
	}

	@Override
	public float getFogDensity(int x, int y, int z) {		
		return 0.3F;
	}

	@Override
	public int getFogColor(int x, int y, int z) {
		return GSUtils.getColor(224, 174, 132, 1);
	}
	
	@Override
    public TypeBody getClassBody()
    {
    	return TypeBody.TITAN;
    }

	@Override
	public void weatherSounds(int j, Minecraft mc, World world, BlockPos blockpos, double xx, double yy, double zz,	Random random) {
		if ((int) yy >= blockpos.getY() + 1 && world.getPrecipitationHeight(blockpos).getY() > blockpos.getY()) {
			mc.world.playSound(xx, yy, zz, SoundEvents.WEATHER_RAIN_ABOVE, SoundCategory.WEATHER, 0.025F, 0.6F + random.nextFloat() * 0.2F, false);
		} else {
			mc.world.playSound(xx, yy, zz, SoundEvents.WEATHER_RAIN, SoundCategory.WEATHER, 0.04F, 0.8F + random.nextFloat() * 0.06F + random.nextFloat() * 0.06F, false);
		}
	}

	@Override
	public int getSoundInterval(float rainStrength) {
		return 80 - (int) (rainStrength * 8F);
	}

	@Override
	public boolean enableAdvancedThermalLevel() {
		return false;
	}

}