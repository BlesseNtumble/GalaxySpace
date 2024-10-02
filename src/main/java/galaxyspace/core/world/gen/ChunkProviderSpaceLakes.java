package galaxyspace.core.world.gen;

 

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.MapGenBaseMeta;
import micdoodle8.mods.galacticraft.core.perlin.generator.Gradient;
import micdoodle8.mods.galacticraft.core.world.gen.EnumCraterSize;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;



 

public abstract class ChunkProviderSpaceLakes implements IChunkProvider
{	
	public enum GenType
	{
		GC,
		VANILLA,
		END
	}
	
    protected static final int CHUNK_SIZE_X = 16;
    private static final int CHUNK_SIZE_Y = 256;
    protected static final int CHUNK_SIZE_Z = 16;
    
    private final double TERRAIN_HEIGHT_MOD = this.getHeightModifier();
    private final double SMALL_FEATURE_HEIGHT_MOD = this.getSmallFeatureHeightModifier();
    private final double MOUNTAIN_HEIGHT_MOD = this.getMountainHeightModifier();
    private final double VALLEY_HEIGHT_MOD = this.getValleyHeightModifier();
    private final int CRATER_PROB = this.getCraterProbability();
    
    private final int MID_HEIGHT = this.getTerrainLevel();
    private static final double MAIN_FEATURE_FILTER_MOD = 4;
    private static final double LARGE_FEATURE_FILTER_MOD = 8;
    private static final double SMALL_FEATURE_FILTER_MOD = 8;
    
	protected Random rand;
	private BiomeCache myBiomeCache;
	private NoiseGeneratorOctaves noiseGen4;
	public NoiseGeneratorOctaves noiseGen5;
	public NoiseGeneratorOctaves noiseGen6;
	public NoiseGeneratorOctaves mobSpawnerNoise;
	protected World worldObj;
	private double[] stoneNoise;
	private BiomeGenBase[] biomesForGeneration = this.getBiomesForGeneration();
	double[] noise3;
	double[] noise1;
	double[] noise2;
	double[] noise5;
	double[] noise6;
	float[] squareTable;
	private NoiseGeneratorOctaves field_147431_j;
	private NoiseGeneratorOctaves field_147432_k;
	private NoiseGeneratorOctaves field_147429_l;
	private NoiseGeneratorPerlin field_147430_m;
	private double[] terrainCalcs;
	private float[] parabolicField;
	double[] field_147427_d;
	double[] field_147428_e;
	double[] field_147425_f;
	double[] field_147426_g;
	int[][] field_73219_j = new int[32][32];
	private final Gradient noiseGen8;
	
    private final Gradient gcnoiseGen1;
    private final Gradient gcnoiseGen2;
    private final Gradient gcnoiseGen3;
    private final Gradient gcnoiseGen4;
    private final Gradient gcnoiseGen5;
    private final Gradient gcnoiseGen6;
    private final Gradient gcnoiseGen7;

	public static List<GS_GenBlocks> genBlocks = new ArrayList();
	
	private List<MapGenBaseMeta> worldGenerators;

	public ChunkProviderSpaceLakes(World world, long seed, boolean flag)
	{
		super();
		this.stoneNoise = new double[256];
		this.worldObj = world;
		this.rand = new Random(seed);
		this.noiseGen4 = new NoiseGeneratorOctaves(this.rand, 4);
		this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 10);
		this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 16);
		this.noiseGen8 = new Gradient(this.rand.nextLong(), 2, 0.25F);
		this.mobSpawnerNoise = new NoiseGeneratorOctaves(this.rand, 8);
		this.field_147431_j = new NoiseGeneratorOctaves(this.rand, 16);
		this.field_147432_k = new NoiseGeneratorOctaves(this.rand, 16);
		this.field_147429_l = new NoiseGeneratorOctaves(this.rand, 8);
		this.field_147430_m = new NoiseGeneratorPerlin(this.rand, 4);
		this.terrainCalcs = new double[825];
		this.parabolicField = new float[25];

		for (int j = -2; j <= 2; j++)
		{
			for (int k = -2; k <= 2; k++)
			{
				float f = 10.0F / MathHelper.sqrt_float(j * j + k * k + 0.2F);
				this.parabolicField[j + 2 + (k + 2) * 5] = f;
			}
		}
		
		this.gcnoiseGen1 = new Gradient(this.rand.nextLong(), 4, 0.25F);
        this.gcnoiseGen2 = new Gradient(this.rand.nextLong(), 4, 0.25F);
        this.gcnoiseGen3 = new Gradient(this.rand.nextLong(), 4, 0.25F);
        this.gcnoiseGen4 = new Gradient(this.rand.nextLong(), 2, 0.25F);
        this.gcnoiseGen5 = new Gradient(this.rand.nextLong(), 1, 0.25F);
        this.gcnoiseGen6 = new Gradient(this.rand.nextLong(), 1, 0.25F);
        this.gcnoiseGen7 = new Gradient(this.rand.nextLong(), 1, 0.25F);
       
       // this.setBlocks(GSBiomeGenBase.GSSpace,	Blocks.grass, (byte) 0, Blocks.dirt, (byte) 0);
        
    	
	}

	@Override
	public Chunk provideChunk(int x, int z)
	{
		this.rand.setSeed(x * 341873128712L + z * 132897987541L);
		Block[] blockStorage = new Block[256 * 256];
		byte[] metaStorage = new byte[256 * 256];
		this.generateTerrain(x, z, blockStorage, metaStorage);
		if(this.getCraterProbability() > 0)	this.createCraters(x, z, blockStorage, metaStorage);
				
		this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, x * 16, z * 16, 16, 16);
		this.replaceBlocksForBiome(x, z, blockStorage, metaStorage, this.biomesForGeneration);
		
		if (this.worldGenerators == null)
        {
            this.worldGenerators = this.getWorldGenerators();
        }

        for (MapGenBaseMeta generator : this.worldGenerators)
        {
            generator.generate(this, this.worldObj, x, z, blockStorage, metaStorage);
        }
        
        this.onChunkProvider(x, z, blockStorage, metaStorage);
		Chunk chunk = new Chunk(this.worldObj, blockStorage, metaStorage, x, z);
		byte[] chunkBiomes = chunk.getBiomeArray();
		
		for (int i = 0; i < chunkBiomes.length; i++)
		{
			chunkBiomes[i] = (byte)this.biomesForGeneration[i].biomeID;
		}
		chunk.generateSkylightMap();
		return chunk;
	}
	
	public void generateTerrain(int chunkX, int chunkZ, Block[] blockStorage, byte[] metaStorage)
	{
		int seaLevel = this.getWaterLevel();
		metaStorage = new byte[256 * 256];

		this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, chunkX * 4 - 2, chunkZ * 4 - 2, 10, 10);
		if(this.getGenType() != GenType.GC) 
		{
			this.makeLandPerBiome2(chunkX * 4, 0, chunkZ * 4);	

			for (int k = 0; k < 4; k++)
			{
				int l = k * 5;
				int i1 = (k + 1) * 5;
	
				for (int j1 = 0; j1 < 4; j1++)
				{
					int k1 = (l + j1) * 33;
					int l1 = (l + j1 + 1) * 33;
					int i2 = (i1 + j1) * 33;
					int j2 = (i1 + j1 + 1) * 33;
	
					for (int k2 = 0; k2 < 32; k2++)
					{
						double d0 = 0.125D;
						double d1 = this.terrainCalcs[k1 + k2];
						double d2 = this.terrainCalcs[l1 + k2];
						double d3 = this.terrainCalcs[i2 + k2];
						double d4 = this.terrainCalcs[j2 + k2];
						double d5 = (this.terrainCalcs[k1 + k2 + 1] - d1) * d0;
						double d6 = (this.terrainCalcs[l1 + k2 + 1] - d2) * d0;
						double d7 = (this.terrainCalcs[i2 + k2 + 1] - d3) * d0;
						double d8 = (this.terrainCalcs[j2 + k2 + 1] - d4) * d0;
	
						for (int l2 = 0; l2 < 8; l2++)
						{
							double d9 = 0.25D;
							double d10 = d1;
							double d11 = d2;
							double d12 = (d3 - d1) * d9;
							double d13 = (d4 - d2) * d9;
	
							for (int i3 = 0; i3 < 4; ++i3)
							{
								int j3 = i3 + k * 4 << 12 | 0 + j1 * 4 << 8 | k2 * 8 + l2;
								short short1 = 256;
								j3 -= short1;
								double d14 = 0.25D;
								double d16 = (d11 - d10) * d14;
								double d15 = d10 - d16;
	
								for (int k3 = 0; k3 < 4; ++k3)
								{
									if ((d15 += d16) > 0.0D)
									{
										blockStorage[j3 += short1] = this.getStoneBlock().getBlock();									
									}
									else if (k2 * 8 + l2 < seaLevel && this.canGenerateWaterBlock())
									{
										blockStorage[j3 += short1] = this.getWaterBlock().getBlock();
									}
									else
									{
										blockStorage[j3 += short1] = null;
									}
								}
								d10 += d12;
								d11 += d13;
							}
							d1 += d5;
							d2 += d6;
							d3 += d7;
							d4 += d8;
						}
					}
				}
			}
		}		
		else this.makeGCland(chunkX, chunkZ, blockStorage, metaStorage);
	}	
	
	public void makeGCland(int chunkX, int chunkZ, Block[] idArray, byte[] metaArray)
    {
    	
        this.gcnoiseGen1.setFrequency(0.015F);
        this.gcnoiseGen2.setFrequency(0.01F);
        this.gcnoiseGen3.setFrequency(0.01F);
        this.gcnoiseGen4.setFrequency(0.01F);
        this.gcnoiseGen5.setFrequency(0.01F);
        this.gcnoiseGen6.setFrequency(0.001F);
        this.gcnoiseGen7.setFrequency(0.005F);
        
        for (int x = 0; x < this.CHUNK_SIZE_X; x++)
        {
            for (int z = 0; z < this.CHUNK_SIZE_Z; z++)
            {
                final double baseHeight = this.gcnoiseGen1.getNoise(chunkX * 16 + x, chunkZ * 16 + z) * this.TERRAIN_HEIGHT_MOD;
                final double smallHillHeight = this.gcnoiseGen2.getNoise(chunkX * 16 + x, chunkZ * 16 + z) * this.SMALL_FEATURE_HEIGHT_MOD;
                double mountainHeight = Math.abs(this.gcnoiseGen3.getNoise(chunkX * 16 + x, chunkZ * 16 + z));
                double valleyHeight = Math.abs(this.gcnoiseGen4.getNoise(chunkX * 16 + x, chunkZ * 16 + z));
                final double featureFilter = this.gcnoiseGen5.getNoise(chunkX * 16 + x, chunkZ * 16 + z) * this.MAIN_FEATURE_FILTER_MOD;
                final double largeFilter = this.gcnoiseGen6.getNoise(chunkX * 16 + x, chunkZ * 16 + z) * this.LARGE_FEATURE_FILTER_MOD;
                final double smallFilter = this.gcnoiseGen7.getNoise(chunkX * 16 + x, chunkZ * 16 + z) * this.SMALL_FEATURE_FILTER_MOD - 0.5;
                mountainHeight = this.lerp(smallHillHeight, mountainHeight * this.MOUNTAIN_HEIGHT_MOD, this.fade(this.clamp(mountainHeight * 2, 0, 1)));
                valleyHeight = this.lerp(smallHillHeight, valleyHeight * this.VALLEY_HEIGHT_MOD - this.VALLEY_HEIGHT_MOD + 9, this.fade(this.clamp((valleyHeight + 2) * 4, 0, 1)));

                double yDev = this.lerp(valleyHeight, mountainHeight, this.fade(largeFilter));
                yDev = this.lerp(smallHillHeight, yDev, smallFilter);
                yDev = this.lerp(baseHeight, yDev, featureFilter);

                BiomeGenBase biomes = this.worldObj.getBiomeGenForCoords(x + chunkX * 16, z + chunkZ * 16);
               
                for (int y = 0; y < this.CHUNK_SIZE_Y; y++)
                {
                    if (y < this.MID_HEIGHT + yDev)
                    { 
                    	if(this.enableBiomeGenBaseBlock() && !genBlocks.isEmpty())
                    	{
                    		//idArray[this.getIndex(x, y, z)] = blockStone.get(biome.indexOf(biomes));
     		               	//metaArray[this.getIndex(x, y, z)] = metaStone.get(biome.indexOf(biomes)); 
                    		
                    		int index = -1; //array index start with 0;
                    		for(GS_GenBlocks genBlock : genBlocks)
                    		{
                    			index++;
                    			if(this.worldObj.provider == genBlock.getWorldProvider() && biomes == genBlock.getBiome())
                    			{
                    				break;
                    			}
                    		}
                    		idArray[this.getIndex(x, y, z)] = genBlocks.get(index).getStoneBlock().getBlock();
                    		metaArray[this.getIndex(x, y, z)] = genBlocks.get(index).getStoneBlock().getMetadata();
                    	}
                    	else
                    	{
                    		idArray[this.getIndex(x, y, z)] = this.getStoneBlock().getBlock();
                    		metaArray[this.getIndex(x, y, z)] = this.getStoneBlock().getMetadata();  
                    	}
		                                  	
                    }
                    
                  
                    
                }
            }
        }
    }
	
	private void makeLandPerBiome2(int x, int zero, int z)
	{
		this.field_147426_g = this.noiseGen6.generateNoiseOctaves(this.field_147426_g, x, z, 5, 5, 200.0D, 200.0D, 0.5D);
		this.field_147427_d = this.field_147429_l.generateNoiseOctaves(this.field_147427_d, x, zero, z, 5, 33, 5, 8.555150000000001D, 4.277575000000001D, 8.555150000000001D);
		this.field_147428_e = this.field_147431_j.generateNoiseOctaves(this.field_147428_e, x, zero, z, 5, 33, 5, 684.41200000000003D, 684.41200000000003D, 684.41200000000003D);
		this.field_147425_f = this.field_147432_k.generateNoiseOctaves(this.field_147425_f, x, zero, z, 5, 33, 5, 684.41200000000003D, 684.41200000000003D, 684.41200000000003D);
		int terrainIndex = 0;
		int noiseIndex = 0;

		for (int ax = 0; ax < 5; ax++)
		{
			for (int az = 0; az < 5; az++)
			{
				float totalVariation = 0.0F;
				float totalHeight = 0.0F;
				float totalFactor = 0.0F;
				byte two = 2;
				BiomeGenBase biomegenbase = this.biomesForGeneration[ax + 2 + (az + 2) * 10];

				for (int ox = -two; ox <= two; ox++)
				{
					for (int oz = -two; oz <= two; oz++)
					{
						BiomeGenBase biomegenbase1 = this.biomesForGeneration[ax + ox + 2 + (az + oz + 2) * 10];
						float rootHeight = biomegenbase1.rootHeight;
						float heightVariation = biomegenbase1.heightVariation;
						float heightFactor = this.parabolicField[ox + 2 + (oz + 2) * 5] / (rootHeight + 2.0F);

						if (biomegenbase1.rootHeight > biomegenbase.rootHeight)
						{
							heightFactor /= 2.0F;
						}
						totalVariation += heightVariation * heightFactor;
						totalHeight += rootHeight * heightFactor;
						totalFactor += heightFactor;
					}
				}
				totalVariation /= totalFactor;
				totalHeight /= totalFactor;
				totalVariation = totalVariation * 0.9F + 0.1F;
				totalHeight = (totalHeight * 4.0F - 1.0F) / 8.0F;
				double terrainNoise = this.field_147426_g[noiseIndex] / 8000.0D;

				if (terrainNoise < 0.0D)
				{
					terrainNoise = -terrainNoise * 0.3D;
				}

				terrainNoise = terrainNoise * 3.0D - 2.0D;

				if (terrainNoise < 0.0D)
				{
					terrainNoise /= 2.0D;

					if (terrainNoise < -1.0D)
					{
						terrainNoise = -1.0D;
					}
					terrainNoise /= 1.4D;
					terrainNoise /= 2.0D;
				}
				else
				{
					if (terrainNoise > 1.0D)
					{
						terrainNoise = 1.0D;
					}
					terrainNoise /= 8.0D;
				}
				noiseIndex++;
				double heightCalc = totalHeight;
				double variationCalc = totalVariation * this.getHeightModifier() / 10D;
				heightCalc += terrainNoise * 0.2D;
				heightCalc = heightCalc * 8.5D / 8.0D;
				double d5 = 8.5D + heightCalc * 4.0D;

				for (int ay = 0; ay < 33; ay++)
				{
					double d6 = (ay - d5) * 12.0D * 128.0D / 256.0D / variationCalc;

					if (d6 < 0.0D)
					{
						d6 *= 4.0D;
					}
					double d7 = this.field_147428_e[terrainIndex] / 512.0D;
					double d8 = this.field_147425_f[terrainIndex] / 512.0D;
					double d9 = (this.field_147427_d[terrainIndex] / 10.0D + 1.0D) / 2.0D;
					double terrainCalc = MathHelper.denormalizeClamp(d7, d8, d9) - d6;

					if (ay > 29)
					{
						double d11 = (ay - 29) / 3.0F;
						terrainCalc = terrainCalc * (1.0D - d11) + -10.0D * d11;
					}
					this.terrainCalcs[terrainIndex] = terrainCalc;
					terrainIndex++;
				}
			}
		}
	}

	public void replaceBlocksForBiome(int par1, int par2, Block[] arrayOfIDs, byte[] arrayOfMeta, BiomeGenBase[] par4ArrayOfBiomeGenBase)
    {
        final int var5 = 20;
        final float var6 = 0.03125F;
        this.noiseGen8.setFrequency(var6 * 2);
        if(this.getGenType() != GenType.END)
        {
	        for (int var8 = 0; var8 < 16; ++var8)
	        {
	            for (int var9 = 0; var9 < 16; ++var9)
	            {
	           	
	            	GSBiomeGenBase biomegenbase = (GSBiomeGenBase) par4ArrayOfBiomeGenBase[var8 + var9 * 16];
	            
	                final int var12 = (int) (this.noiseGen8.getNoise(par1 * 16 + var8, par2 * 16 + var9) / 3.0D + getDirtLayerSize() + this.rand.nextDouble() * 0.25D);
	                int var13 = -1;
	               /* 
	                Block var14 = this.enableBiomeGenBaseBlock() ? biomegenbase.topBlock : this.getGrassBlock().getBlock();
	                byte var14m = this.enableBiomeGenBaseBlock() ? biomegenbase.topMeta : this.getGrassBlock().getMetadata();
	                Block var15 = this.enableBiomeGenBaseBlock() ? biomegenbase.fillerBlock : this.getDirtBlock().getBlock();
	                byte var15m = this.enableBiomeGenBaseBlock() ? biomegenbase.fillerMeta : this.getDirtBlock().getMetadata();
	               */
	                Block var14;
	                byte var14m;
	                Block var15;
	                byte var15m;
	                
	                Block stone;
	                byte mStone;
	               
	                if(this.enableBiomeGenBaseBlock() && !genBlocks.isEmpty())
	                {
	                	//GalaxySpace.debug("Chunk: " + biome.indexOf(par4ArrayOfBiomeGenBase[var8 + var9 * 16]) + "|" + block.get(biome.indexOf(par4ArrayOfBiomeGenBase[var8 + var9 * 16]))+ "|" + block.size() + "|" + biome.indexOf(par4ArrayOfBiomeGenBase[var8 + var9 * 16]));
	                   
	                	int index = -1;
                		for(GS_GenBlocks genBlock : genBlocks)
                		{
                			index++;
                			if(this.worldObj.provider == genBlock.getWorldProvider() && par4ArrayOfBiomeGenBase[var8 + var9 * 16] == genBlock.getBiome())
                			{
                				break;
                			}
                		}
                		//GalaxySpace.debug(index + " | " + genBlocks.get(index).getGrassBlock().getBlock());
	                	var14 = genBlocks.get(index).getGrassBlock().getBlock();
	                	var14m = genBlocks.get(index).getGrassBlock().getMetadata();
	                	var15 = genBlocks.get(index).getDirtBlock().getBlock();
	                	var15m = genBlocks.get(index).getDirtBlock().getMetadata();
	                	
	                	stone = genBlocks.get(index).getStoneBlock().getBlock();
	                	mStone = genBlocks.get(index).getStoneBlock().getMetadata();
	                }
	                else
	                {
	                	var14 = this.getGrassBlock().getBlock();
	                	var14m = this.getGrassBlock().getMetadata();
	                	var15 = this.getDirtBlock().getBlock();
	                	var15m = this.getDirtBlock().getMetadata();
	                	
	                	stone = this.getStoneBlock().getBlock();
	                	mStone = this.getStoneBlock().getMetadata();
	                }
	                
	                for (int var16 = 256 - 1; var16 >= 0; --var16)
	                {
	                    final int index = this.getIndex(var8, var16, var9);
	                    
	                    if(this.canGenerateIceBlock())
	                    {
	                    	if(var16 == 5 || var16 == 6 + this.rand.nextInt(3))
	                    	{
	                    		arrayOfIDs[index] = this.getIceBlock().getBlock();
	                    		arrayOfMeta[index] = this.getIceBlock().getMetadata();
	                    	}
	                    }
	                    
	                    if (var16 <= 0 + this.rand.nextInt(5))
	                    {
	                        arrayOfIDs[index] = Blocks.bedrock;
	                    }	                   
	                    else
	                    {
	                        final Block var18 = arrayOfIDs[index];
	
	                        
	                        if (Blocks.air == var18)
	                        {
	                            var13 = -1;
	                        }
	                        else if (var18 == stone)
	                        {
	                        	arrayOfMeta[index] = mStone;
	
	                        	    
	                            if (var13 == -1)
	                            {
	                                if (var12 <= 0)
	                                {
	                                    var14 = Blocks.air;
	                                    var14m = 0;
	                                    var15 = stone;
	                              		var15m = mStone;
	                                                						
	                                }
	                                else if (var16 >= var5 - -16 && var16 <= var5 + 1)
	                                {
	                                	  if(this.enableBiomeGenBaseBlock() && !genBlocks.isEmpty())
	                                      {
	                                		var14 = genBlocks.get(index).getGrassBlock().getBlock();
	                  	                	var14m = genBlocks.get(index).getGrassBlock().getMetadata();
	                  	                	var14 = genBlocks.get(index).getDirtBlock().getBlock();
	                  	                	var14m = genBlocks.get(index).getDirtBlock().getMetadata();	                                      
	                                      }
	                                	  else
	                                	  {
	                                      	var14 = this.getGrassBlock().getBlock();
	                                		var14m = this.getGrassBlock().getMetadata();
	                                		var14 = this.getDirtBlock().getBlock();
	                                		var14m = this.getDirtBlock().getMetadata(); 
	                                	  }
	                                	  
	                                	     
	                                }
	                                var13 = var12;
	
	                              /*  if (var16 >= this.getWaterLevel() - 1)
	                                {
	                                	*/
	                                    arrayOfIDs[index] = var14;
	                                    arrayOfMeta[index] = var14m;
	                                /*}
	                                else
	                                {
	                                    arrayOfIDs[index] = var15;
	                                    arrayOfMeta[index] = var15m;
	                                }*/
	                            }
	                            else if (var13 > 0)
	                            {
	                                --var13;
	                                arrayOfIDs[index] = var15;
	                                arrayOfMeta[index] = var15m;
	                            }
	                        }
	                        else if (var16 < this.getWaterLevel() && this.getGenType() == GenType.GC && this.canGenerateWaterBlock())
	                        {                             	
	                        	arrayOfIDs[index] = this.getWaterBlock().getBlock();
	                        	arrayOfMeta[index] = this.getWaterBlock().getMetadata();
	                        }
	                    }
	                }
	            }
	        }
        }
        else
        {
        	 for (int k = 0; k < 16; ++k)
             {
                 for (int l = 0; l < 16; ++l)
                 {
                     byte b0 = 1;
                     int i1 = -1;
                     Block block = Blocks.end_stone;
                     Block block1 = Blocks.end_stone;

                     for (int j1 = 127; j1 >= 0; --j1)
                     {
                         int k1 = (l * 16 + k) * 128 + j1;
                         Block block2 = arrayOfIDs[k1];

                         if (block2 != null && block2.getMaterial() != Material.air)
                         {
                             if (block2 == Blocks.end_stone)
                             {
                                 if (i1 == -1)
                                 {
                                     if (b0 <= 0)
                                     {
                                         block = null;
                                         block1 = Blocks.end_stone;
                                     }

                                     i1 = b0;

                                     if (j1 >= 0)
                                     {
                                    	 arrayOfIDs[k1] = block;
                                     }
                                     else
                                     {
                                    	 arrayOfIDs[k1] = block1;
                                     }
                                 }
                                 else if (i1 > 0)
                                 {
                                     --i1;
                                     arrayOfIDs[k1] = block1;
                                 }
                             }
                         }
                         else
                         {
                             i1 = -1;
                         }
                     }
                 }
             }
        }
    }

	public void createCraters(int chunkX, int chunkZ, Block[] chunkArray, byte[] metaArray)
	{
		this.gcnoiseGen5.setFrequency(0.015F);
		for (int cx = chunkX - 2; cx <= chunkX + 2; cx++)
		{
			for (int cz = chunkZ - 2; cz <= chunkZ + 2; cz++)
			{
				for (int x = 0; x < this.CHUNK_SIZE_X; x++)
				{
					for (int z = 0; z < this.CHUNK_SIZE_Z; z++)
					{
						if (Math.abs(this.randFromPoint(cx * 16 + x, (cz * 16 + z) * 1000)) < this.gcnoiseGen5.getNoise(cx * 16 + x, cz * 16 + z) / this.CRATER_PROB)
						{
							
							final Random random = new Random(cx * 16 + x + (cz * 16 + z) * 5000);
							final EnumCraterSize cSize = EnumCraterSize.sizeArray[random.nextInt(EnumCraterSize.sizeArray.length)];
							final int size = random.nextInt(cSize.MAX_SIZE - cSize.MIN_SIZE) + cSize.MIN_SIZE + 15;
							this.makeCrater(cx * 16 + x, cz * 16 + z, chunkX * 16, chunkZ * 16, size, chunkArray, metaArray);
						}
					}
				}
			}
		}
	}

	public void makeCrater(int craterX, int craterZ, int chunkX, int chunkZ, int size, Block[] chunkArray, byte[] metaArray)
	{		
		for (int x = 0; x < this.CHUNK_SIZE_X; x++)
		{
			for (int z = 0; z < this.CHUNK_SIZE_Z; z++)
			{
				double xDev = craterX - (chunkX + x);
				double zDev = craterZ - (chunkZ + z);
				if (xDev * xDev + zDev * zDev < size * size)
				{
					xDev /= size;
					zDev /= size;
					final double sqrtY = xDev * xDev + zDev * zDev;
					double yDev = sqrtY * sqrtY * 6;
					yDev = 5 - yDev;
					int helper = 0;
					for (int y = 127; y > 0; y--)
					{
						if (helper > yDev)
						{
							break;
						}
						if (chunkArray[this.getIndex(x, y, z)] != null)
						{
							
							this.getCraterAdditions(chunkArray, metaArray, x, y, z);

							chunkArray[this.getIndex(x, y, z)] = Blocks.air;
							metaArray[this.getIndex(x, y, z)] = 0;
							helper++;
						}
					}
				}
			}
		}
	}	
	
	private double randFromPoint(int x, int z)
	{
		int n;
		n = x + z * 57;
		n = n << 13 ^ n;
		return 1.0 - (n * (n * n * 15731 + 789221) + 1376312589 & 0x7fffffff) / 1073741824.0;
	}
	    
	protected int getIndex(int x, int y, int z)
    {
        return (x * 16 + z) * 256 + y;
    }
	
	@Override
	public Chunk loadChunk(int x, int z)
	{
		return this.provideChunk(x, z);
	}

	@Override
	public boolean chunkExists(int x, int z)
	{
		return true;
	}

	@Override
	public void populate(IChunkProvider chunk, int x, int z)
	{
		BlockFalling.fallInstantly = true;
		int var4 = x * 16;
		int var5 = z * 16;
		BiomeGenBase biomeGen = this.worldObj.getBiomeGenForCoords(var4 + 16, var5 + 16);
		this.worldObj.getBiomeGenForCoords(var4 + 16, var5 + 16);
		this.rand.setSeed(this.worldObj.getSeed());
		long var7 = this.rand.nextLong() / 2L * 2L + 1L;
		long var9 = this.rand.nextLong() / 2L * 2L + 1L;
		this.rand.setSeed(x * var7 + z * var9 ^ this.worldObj.getSeed());
		biomeGen.decorate(this.worldObj, this.rand, var4, var5);
		this.decoratePlanet(this.worldObj, this.rand, var4, var5);
		SpawnerAnimals.performWorldGenSpawning(this.worldObj, biomeGen, var4 + 8, var5 + 8, 16, 16, this.rand);
		this.onPopulate(chunk, x, z);
		BlockFalling.fallInstantly = false;
	}

	public void decoratePlanet(World world, Random rand, int x, int z)
	{
		this.getBiomeGenerator().decorate(world, rand, x, z);
	}

	@Override
	public boolean saveChunks(boolean flag, IProgressUpdate progress)
	{
		return true;
	}

	@Override
	public boolean canSave()
	{
		return true;
	}

	@Override
	public String makeString()
	{
		return "RandomLevelSource";
	}

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int i, int j, int k)
    {
        if (par1EnumCreatureType == EnumCreatureType.monster)
        {
            final List monsters = new ArrayList();

            for (SpawnListEntry monster : this.getMonsters())
            {
                monsters.add(monster);
            }

            return monsters;
        }
        else if (par1EnumCreatureType == EnumCreatureType.creature)
        {
            final List creatures = new ArrayList();

            for (SpawnListEntry creature : this.getCreatures())
            {
                creatures.add(creature);
            }

            return creatures;
        }
        else if (par1EnumCreatureType == EnumCreatureType.waterCreature)
        {
            final List watercreatures = new ArrayList();

            for (SpawnListEntry watercreature : this.getWaterCreatures())
            {
                watercreatures.add(watercreature);
            }

            return watercreatures;
        }
        else
        {
            return null;
        }
    }

	@Override
	public int getLoadedChunkCount()
	{
		return 0;
	}

	@Override
	public void recreateStructures(int x, int z)
	{
		
	}

	@Override
	public boolean unloadQueuedChunks()
	{
		return false;
	}

	@Override
	public void saveExtraData() {}

	@Override
	public ChunkPosition func_147416_a(World world, String string, int x, int y, int z)
	{
		return null;
	}
/*
    public static void setBlocks(BiomeGenBase biomes, Block blocks, byte metas, Block blocks2, byte metas2, Block blocks3, byte metas3)
    {
    	biome.add(biomes);
    	block.add(blocks);
    	meta.add(metas);
    	block2.add(blocks2);
    	meta2.add(metas2);
    	blockStone.add(blocks3);
    	metaStone.add(metas3);
    };
    */
    public static void setBlocks(GS_GenBlocks array)
    {
    	genBlocks.add(array);
    }
	
    protected double lerp(double d1, double d2, double t)
    {
        if (t < 0.0)
        {
            return d1;
        }
        else if (t > 1.0)
        {
            return d2;
        }
        else
        {
            return d1 + (d2 - d1) * t;
        }
    }

    protected double fade(double n)
    {
        return n * n * n * (n * (n * 6 - 15) + 10);
    }

    protected double clamp(double x, double min, double max)
    {
        if (x < min)
        {
            return min;
        }
        if (x > max)
        {
            return max;
        }
        return x;
    }
    /**
     * Do not return null
     *
     * @return The biome generator for this world, handles ore, flower, etc
     * generation. See GCBiomeDecoratorBase.
     */
    protected abstract BiomeDecoratorSpace getBiomeGenerator();
    
    /**
     * Do not return null, have at least one biome for generation
     *
     * @return Biome instance for generation
     */
    protected abstract BiomeGenBase[] getBiomesForGeneration();
    
    public abstract void onChunkProvider(int cX, int cZ, Block[] blocks, byte[] metadata);

    public abstract void onPopulate(IChunkProvider provider, int cX, int cZ);
    
    /**
     * @return List of spawn list entries for monsters
     */
    protected abstract SpawnListEntry[] getMonsters();

    /**
     * @return List of spawn list entries for creatures
     */
    protected abstract SpawnListEntry[] getCreatures();
    
    /**
     * @return List of spawn list entries for water creatures
     */
    protected abstract SpawnListEntry[] getWaterCreatures();
    
    /**
     * List of all world generators to use. Caves, ravines, structures, etc.
     * <p/>
     * Return an empty list for no world generators. Do not return null.
     *
     * @return
     */
    protected abstract List<MapGenBaseMeta> getWorldGenerators();
    
    /**
     * @return Base height modifier
     */
    public abstract double getHeightModifier();
    
    /**
     * @return Height modifier for small hills
     */
    public abstract double getSmallFeatureHeightModifier();

    /**
     * @return Height modifier for mountains
     */
    public abstract double getMountainHeightModifier();

    /**
     * @return Height modifier for valleys
     */
    public abstract double getValleyHeightModifier();

    /**
     * @return Base height modifier
     */
    public abstract int getWaterLevel();
    
    /**
     * @return Generate Water Block on sealevel.
     */
    public abstract boolean canGenerateWaterBlock();
    
    /**
     * @return Generate Ice Block on bedrocklevel.
     */
    public abstract boolean canGenerateIceBlock();
    
    /**
     * @return Probability that craters will be generated
     */
    public abstract int getCraterProbability();
    
    /**
     * The water block to be generated.
     *
     * @return BlockMetaPair instance containing ID and metadata for water
     * block.
     */
    protected abstract BlockMetaPair getWaterBlock();
    protected abstract BlockMetaPair getGrassBlock();
    protected abstract BlockMetaPair getDirtBlock();
    protected abstract BlockMetaPair getStoneBlock();
    
    protected abstract boolean enableBiomeGenBaseBlock();
    protected abstract GenType getGenType();
    
    public int getCraterSize()
    {
    	return 0;
    }
   
    public int getTerrainLevel()
    {
    	return this.getWaterLevel();
    }
    
    public double getDirtLayerSize()
    {
    	return 3.0D;
    }
    
    protected BlockMetaPair getIceBlock()
    {
    	return new BlockMetaPair(Blocks.packed_ice, (byte) 0);
    }
    
    public void getCraterAdditions(Block[] chunkArray, byte[] metaArray, int x, int y, int z) {}
}