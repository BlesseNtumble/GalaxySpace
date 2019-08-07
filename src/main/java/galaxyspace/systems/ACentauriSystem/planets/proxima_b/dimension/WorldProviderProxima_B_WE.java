package galaxyspace.systems.ACentauriSystem.planets.proxima_b.dimension;

import java.util.List;

import javax.annotation.Nullable;

import asmodeuscore.api.dimension.IProviderFog;
import asmodeuscore.api.dimension.IProviderFreeze;
import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_Biome;
import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_ChunkProvider;
import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_WorldProvider;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_CaveGen;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_RavineGen;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_TerrainGenerator;
import galaxyspace.core.util.GSDimensions;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.ACentauriSystem.ACentauriSystemBodies;
import galaxyspace.systems.ACentauriSystem.core.registers.blocks.ACBlocks;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.dimension.sky.SkyProviderProxima_B;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.world.gen.BiomeDecoratorProxima_B;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.world.gen.BiomeProviderProxima_B;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.world.gen.we.Proxima_B_Beach;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.world.gen.we.Proxima_B_Forest;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.world.gen.we.Proxima_B_Ice_Plains;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.world.gen.we.Proxima_B_Mountains;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.world.gen.we.Proxima_B_Ocean;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.world.gen.we.Proxima_B_Plains;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.galaxies.IChildBody;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.client.CloudRenderer;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderProxima_B_WE extends WE_WorldProvider implements IProviderFreeze, IProviderFog {
	
	private final float[] colorsSunriseSunset = new float[4];
	public static WE_ChunkProvider chunk;
	
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
        return 0.0;
    }

    @Override
    public float getSoundVolReductionAmount() {
        return Float.MIN_VALUE;
    }

    @Override
    public boolean canRainOrSnow() {
        return true;
    }

    @Override
    public CelestialBody getCelestialBody() {
        return ACentauriSystemBodies.proxima_b;
    }

    @Override
    public Class<? extends IChunkGenerator> getChunkProviderClass() {
        return WE_ChunkProvider.class;

    }
    
    @Override 
    public Class<? extends BiomeProvider> getBiomeProviderClass() { 
    	return BiomeProviderProxima_B.class; 
    }
    
    @Nullable
    @SideOnly(Side.CLIENT)
    public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks)
    {
        float f = 0.4F;
        float f1 = MathHelper.cos(celestialAngle * ((float)Math.PI * 2F)) - 0.0F;
        float f2 = -0.0F;

        if (f1 >= -0.4F && f1 <= 0.4F)
        {
            float f3 = (f1 - -0.0F) / 0.4F * 0.5F + 0.5F;
            float f4 = 1.0F - (1.0F - MathHelper.sin(f3 * (float)Math.PI)) * 0.99F;
            f4 = f4 * f4;
            this.colorsSunriseSunset[0] = f3 * 0.3F + 0.7F;
            this.colorsSunriseSunset[1] = f3 * f3 * 0.7F + 0.2F;
            this.colorsSunriseSunset[2] = f3 * f3 * 0.0F + 0.2F;
            this.colorsSunriseSunset[3] = f4;
            return this.colorsSunriseSunset;
        }
        else
        {
            return null;
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public Vector3 getFogColor() {
    	float f = 0.6F - this.getStarBrightness(1.0F);
        return new Vector3(66 / 255.0F * f, 90 / 255.0F * f, 110 / 255.0F * f);
    }

    @Override
    public Vector3 getSkyColor() {
    	float f = 0.7F - this.getStarBrightness(1.0F);
        return new Vector3(80 / 255.0F * f, 93 / 255.0F * f, 100 / 255.0F * f);
    }
     
	@Override
	public boolean isSkyColored() {
		return true;
	}
 
	@Override
	public boolean hasSunset() {
		return true;
	}

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

        return var3 * var3 * 0.1F + 0.3F;    	
    }
   
    @Override
    @SideOnly(Side.CLIENT)
    public float getSunBrightness(float par1) {
       float f1 = this.world.getCelestialAngle(1.0F);
       float f2 = 1.0F - (MathHelper.cos(f1 * 3.1415927F * 2.0F) * 2.0F + 0.2F);
       f2 = MathHelper.clamp(f2, 0.0F, 1.0F);

       f2 = 1.2F - f2;
       return f2 * 0.3F;
    }
    
    @Override
    public IRenderHandler getCloudRenderer(){
        return super.getCloudRenderer();
    }
    
    @Override 
    @SideOnly(Side.CLIENT)
    public float getCloudHeight()
    {
        return 180.0F;
    }

	@SideOnly(Side.CLIENT)
    public IRenderHandler getSkyRenderer()
    {
    	if (super.getSkyRenderer() == null)
		{
			this.setSkyRenderer(new SkyProviderProxima_B());
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
	
		return GSDimensions.PROXIMA_B;
	}

	@Override
	public void genSettings(WE_ChunkProvider cp) {

		chunk = cp;
		
		cp.createChunkGen_List .clear(); 
		cp.createChunkGen_InXZ_List .clear(); 
		cp.createChunkGen_InXYZ_List.clear(); 
		cp.decorateChunkGen_List .clear(); 
		
		WE_Biome.setBiomeMap(cp, 1.2D, 4, 1400.0D, 0.675D);	
		
		WE_TerrainGenerator terrainGenerator = new WE_TerrainGenerator(); 
		terrainGenerator.worldStoneBlock = ACBlocks.PROXIMA_B_BLOCKS; 
		terrainGenerator.worldStoneBlockMeta = 2;
		terrainGenerator.worldSeaGen = true;
		terrainGenerator.worldSeaGenBlock = Blocks.WATER;
		terrainGenerator.worldSeaGenMaxY = 64;
		cp.createChunkGen_List.add(terrainGenerator);
		
		//-// 
		WE_CaveGen cg = new WE_CaveGen(); 
		cg.replaceBlocksList .clear(); 
		cg.replaceBlocksMetaList.clear(); 
		cg.addReplacingBlock(terrainGenerator.worldStoneBlock, (byte)terrainGenerator.worldStoneBlockMeta); 
		//cg.lavaBlock = CW_Main.bfLava2; 
		cp.createChunkGen_List.add(cg); 
		//-// 
		 
		WE_RavineGen rg = new WE_RavineGen();
		rg.replaceBlocksList    .clear();
		rg.replaceBlocksMetaList.clear();
		rg.addReplacingBlock(ACBlocks.PROXIMA_B_BLOCKS, (byte)2);
		rg.lavaBlock = Blocks.LAVA;
		cp.createChunkGen_List.add(rg);
		
		cp.biomesList.clear();
		
		WE_Biome.addBiomeToGeneration(cp, new Proxima_B_Plains());
		WE_Biome.addBiomeToGeneration(cp, new Proxima_B_Forest(cp)); 
		WE_Biome.addBiomeToGeneration(cp, new Proxima_B_Ice_Plains());
		WE_Biome.addBiomeToGeneration(cp, new Proxima_B_Mountains()); 
		WE_Biome.addBiomeToGeneration(cp, new Proxima_B_Ocean()); 
		WE_Biome.addBiomeToGeneration(cp, new Proxima_B_Beach());  
	}
	
	@Override
	public BiomeDecoratorSpace getDecorator() {
		return new BiomeDecoratorProxima_B();
	}
	
	@Override
    public float getSolarSize()
    {
        return 0.3F / this.getCelestialBody().getRelativeDistanceFromCenter().unScaledDistance;
    }
	
	@Override
	public int getMoonPhase(long worldTime)
    {
        return (int)(worldTime / this.getDayLength() % 8L + 8L) % 8;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getLightmapColors(float partialTicks, float sunBrightness, float skyLight, float blockLight, float[] colors) 
	{
		EntityPlayer player = FMLClientHandler.instance().getClientPlayerEntity();
		
		if (player != null)
		{
			int phase = this.getMoonPhase(this.getWorldTime());
			if(skyLight > 0 && sunBrightness > 0.07f && phase != 0 && phase != 6) {
								
				colors[0] = colors[0] + skyLight + 0.3F;				
				colors[1] = colors[1] + skyLight / 6;	
			}				
		}
	}

	@Override
	public int getFogColor(int arg0, int arg1, int arg2) {
		int A = 10 * 256 * 256 * 256;
		int R = 66 * 256 * 256;
		int G = 70 * 256;
		int B = 80;
		int color = A+R+G+B;	
		
	    return GSUtils.getColor(66, 70, 80, 10);
	}

	@Override
	public float getFogDensity(int arg0, int arg1, int arg2) {
		
		//if(this.world == null) return 1.0F;
		
		switch(this.getMoonPhase(this.getWorldTime()))
		{
			case 0: return 0.35F;
			case 1: return 0.75F;
			
			case 3: return 0.95F;
			
			case 5: return 0.5F;
			case 6: return 0.2F;
			
			default: return 1.0F;
		}
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
	
		if(this.world == null) return solarMultiplier;
		
		switch(this.getMoonPhase(this.getWorldTime()))
		{
			case 0: return solarMultiplier * 0.35F;
			case 1: return solarMultiplier * 0.95F;
				
			case 3: return solarMultiplier * 0.95F;
				
			case 5: return solarMultiplier * 0.5F;
			case 6: return solarMultiplier * 0.2F;
			default: return solarMultiplier;
		}	
		
		
	}

	@Override
	public boolean enableAdvancedThermalLevel() {
		return false;
	}

}