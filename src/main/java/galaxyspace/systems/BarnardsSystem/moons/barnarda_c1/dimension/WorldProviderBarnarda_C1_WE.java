package galaxyspace.systems.BarnardsSystem.moons.barnarda_c1.dimension;

import java.util.List;

import javax.annotation.Nullable;

import asmodeuscore.api.dimension.IProviderFog;
import asmodeuscore.api.dimension.IProviderFreeze;
import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_BiomeProvider;
import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_ChunkProviderSpace;
import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_WorldProviderSpace;
import asmodeuscore.core.utils.worldengine.WE_Biome;
import asmodeuscore.core.utils.worldengine.WE_ChunkProvider;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_BiomeLayer;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_CaveGen;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_RavineGen;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_TerrainGenerator;
import galaxyspace.core.prefab.world.gen.we.biomes.WE_BaseBiome;
import galaxyspace.core.util.GSDimensions;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.BarnardsSystem.BarnardsSystemBodies;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.dimension.sky.SkyProviderBarnarda_C;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.client.CloudRenderer;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderBarnarda_C1_WE extends WE_WorldProviderSpace implements IProviderFreeze, IProviderFog{
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
        return 0.9;
    }

    @Override
    public float getSoundVolReductionAmount() {
        return Float.MIN_VALUE;
    }

    @Override
    public boolean canSnowAt(BlockPos pos, boolean checkLight)
    {
    	return true;
    }
        
    @Override
    public CelestialBody getCelestialBody() {
        return BarnardsSystemBodies.Barnarda_C1;
    }

    @Override
    public Class<? extends IChunkGenerator> getChunkProviderClass() {
        return WE_ChunkProviderSpace.class;

    }
    
    @Override 
    public Class<? extends BiomeProvider> getBiomeProviderClass() { 
    	return WE_BiomeProvider.class; 
    }
    
    @Override 
    @SideOnly(Side.CLIENT)
    public float getCloudHeight()
    {
        return 180.0F;
    }
    
	@Override
	public boolean canRespawnHere()
	{
		return true;
	}
	
    @Nullable
    @SideOnly(Side.CLIENT)
    public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks)
    {
    	return super.calcSunriseSunsetColors(celestialAngle, partialTicks);
      /*  float f = 0.4F;
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
        }*/
    }
    /*
    @Override
    @SideOnly(Side.CLIENT)
    public Vector3 getFogColor() {
    	
    	float f = 1.0F - this.getStarBrightness(1.0F);
        return new Vector3(86 / 255.0F * f, 180 / 255.0F * f, 240 / 255.0F * f);
    }

    @Override
    public Vector3 getSkyColor() {
    	float f = 0.6F - this.getStarBrightness(1.0F);
        return new Vector3(100 / 255.0F * f, 220 / 255.0F * f, 250 / 255.0F * f);
    }*/
    
    @Override
    @SideOnly(Side.CLIENT)
    public Vector3 getFogColor() {
    	float f = 1.0F - this.getStarBrightness(1.0F);
        return new Vector3(140 / 255.0F * f, 167 / 255.0F * f, 207 / 255.0F * f);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Vector3 getSkyColor() {

    	float f = 0.5F - this.getStarBrightness(1.0F);
    	if(world.isRaining())
    	{
    		f = 1.0F;
    		return new Vector3(47 / 255.0F * f, 47 / 255.0F * f, 47 / 255.0F * f);
    	}
    	return new Vector3(161 / 255.0F * f, 146 / 255.0F * f, 175 / 255.0F * f);

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
    	float f = this.world.getCelestialAngle(par1);
        float f1 = 1.0F - (MathHelper.cos(f * ((float)Math.PI * 2F)) * 2.0F + 0.25F);
        f1 = MathHelper.clamp(f1, 0.0F, 1.0F);
        return f1 * f1 * 0.5F;   	
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public float getSunBrightness(float par1) {
       float f1 = this.world.getCelestialAngle(1.0F);
       float f2 = 1.0F - (MathHelper.cos(f1 * 3.1415927F * 2.0F) * 2.0F + 0.2F);
       f2 = MathHelper.clamp(f2, 0.0F, 1.0F);

       f2 = 1.2F - f2;
       return f2 * 0.8F;
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
			this.setSkyRenderer(new SkyProviderBarnarda_C());
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
	
		return GSDimensions.BARNARDA_C;
	}

	@Override
	public void genSettings(WE_ChunkProvider cp) {		

		cp.createChunkGen_List .clear(); 
		cp.createChunkGen_InXZ_List .clear(); 
		cp.createChunkGen_InXYZ_List.clear(); 
		cp.decorateChunkGen_List .clear(); 
		
		WE_Biome.setBiomeMap(cp, 1.4D, 4, 6400.0D, 1.0D);	

		WE_TerrainGenerator terrainGenerator = new WE_TerrainGenerator(); 
		terrainGenerator.worldStoneBlock = Blocks.STONE.getDefaultState(); 
		terrainGenerator.worldSeaGen = false;
		terrainGenerator.worldSeaGenBlock = Blocks.ICE.getDefaultState();
		terrainGenerator.worldSeaGenMaxY = 64;
		cp.createChunkGen_List.add(terrainGenerator);
		
		//-// 
		WE_CaveGen cg = new WE_CaveGen(); 
		cg.replaceBlocksList .clear(); 
		cg.addReplacingBlock(terrainGenerator.worldStoneBlock); 
		cg.lavaMaxY = 15;
		cg.range = 64;
		cp.createChunkGen_List.add(cg); 
		//-// 
		 
		WE_RavineGen rg = new WE_RavineGen();
		rg.replaceBlocksList    .clear();
		rg.addReplacingBlock(terrainGenerator.worldStoneBlock);
		rg.lavaBlock = Blocks.LAVA.getDefaultState();
		rg.lavaMaxY = 15;		
		rg.range = 32;
		cp.createChunkGen_List.add(rg);
		
		((WE_ChunkProviderSpace)cp).worldGenerators.clear();
		cp.biomesList.clear();
		
		WE_BiomeLayer layer = new WE_BiomeLayer();
		layer.add(Blocks.BEDROCK.getDefaultState(), 0, 0, 1, 2, true);
		layer.add(Blocks.PACKED_ICE.getDefaultState(), terrainGenerator.worldStoneBlock, -256, 0,   -4, -10,  true);
		layer.add(Blocks.SNOW.getDefaultState(), Blocks.PACKED_ICE.getDefaultState(), -256, 0,   -2, -1,  false);
		
		WE_Biome.addBiomeToGeneration(cp, new WE_BaseBiome(0D, 0D, 1.5F, 4, 90, 10, layer));	
		WE_Biome.addBiomeToGeneration(cp, new WE_BaseBiome(-0.5D, 0.5D, 2.0F, 6, 150, 3, layer));	
		WE_Biome.addBiomeToGeneration(cp, new WE_BaseBiome(-1.4D, 1.0D, 1.5F, 4, 90, 10, layer));	
		WE_Biome.addBiomeToGeneration(cp, new WE_BaseBiome(-1.0D, 1.5D, 1.5F, 4, 90, 20, layer));	
		WE_Biome.addBiomeToGeneration(cp, new WE_BaseBiome(-2.0D, 2.0D, 1.5F, 4, 40, 3, layer));
	}
	
	@Override
	public boolean enableAdvancedThermalLevel() {
		return true;
	}
	
	@Override
	protected float getThermalValueMod()
	{
		return 0.4F;
	}

	@Override
	public void onPopulate(int cX, int cZ) {
		
	}

	@Override
	public void onChunkProvider(int cX, int cZ, ChunkPrimer primer) {		
	}

	@Override
	public void recreateStructures(Chunk chunkIn, int x, int z) {		
	}

	@Override
	public float getFogDensity(int x, int y, int z) {
		if(this.world.isRaining()) return 0.1F;
		return 0.4F;
	}

	@Override
	public int getFogColor(int x, int y, int z) {
		return GSUtils.getColor((int)(this.getFogColor().x * 255), (int)(this.getFogColor().y * 255), (int)(this.getFogColor().z * 255), 100);
	}
}
