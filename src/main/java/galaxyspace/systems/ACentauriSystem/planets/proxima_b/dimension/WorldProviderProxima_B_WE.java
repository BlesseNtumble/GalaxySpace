package galaxyspace.systems.ACentauriSystem.planets.proxima_b.dimension;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import asmodeuscore.api.dimension.IProviderFog;
import asmodeuscore.core.astronomy.dimension.world.gen.features.trees.WorldGenTree_BigJungle;
import asmodeuscore.core.astronomy.dimension.world.gen.features.trees.WorldGenTree_BigJungle2;
import asmodeuscore.core.astronomy.dimension.world.gen.features.trees.WorldGenTree_Forest;
import asmodeuscore.core.astronomy.dimension.world.gen.features.trees.WorldGenTree_Forest2;
import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_ChunkProviderSpace;
import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_WorldProviderSpace;
import asmodeuscore.core.astronomy.dimension.world.worldengine.biome.WE_BaseBiome;
import asmodeuscore.core.utils.worldengine.WE_Biome;
import asmodeuscore.core.utils.worldengine.WE_ChunkProvider;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_BiomeLayer;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_CaveGen;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_OreGen;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_RavineGen;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_TerrainGenerator;
import galaxyspace.core.util.GSDimensions;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.ACentauriSystem.ACentauriSystemBodies;
import galaxyspace.systems.ACentauriSystem.core.ACBlocks;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.blocks.Proxima_B_Blocks;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.blocks.Proxima_B_Dandelions;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.blocks.Proxima_B_Grass;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.dimension.sky.SkyProviderProxima_B;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.world.gen.features.WorldGenMushroom;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.world.gen.we.Proxima_B_Beach;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.world.gen.we.Proxima_B_Forest;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.world.gen.we.Proxima_B_Ice_Plains;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.world.gen.we.Proxima_B_Mountains;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.world.gen.we.Proxima_B_Ocean;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.world.gen.we.Proxima_B_Plains;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.galaxies.IChildBody;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderProxima_B_WE extends WE_WorldProviderSpace implements IProviderFog {
	
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
    public CelestialBody getCelestialBody() {
        return ACentauriSystemBodies.proxima_b;
    }

    @Override
    public Class<? extends IChunkGenerator> getChunkProviderClass() {
        return WE_ChunkProviderSpace.class;

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
    	float f = 0.7F - this.getStarBrightness(1.0F);
    	f = Math.max(f, 0.0F);
        return new Vector3(226 / 255.0F * f, 145 / 255.0F * f, 90 / 255.0F * f);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Vector3 getSkyColor() {
    	float f = 0.5F - this.getStarBrightness(1.0F);
    	f = Math.max(f, 0.0F);
        return new Vector3(226 / 255.0F * f, 145 / 255.0F * f, 90 / 255.0F * f);
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

        return var3 * var3 * 0.1F + 0.1F;    	
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
		
		
		
		WE_TerrainGenerator terrainGenerator = new WE_TerrainGenerator(); 
		terrainGenerator.worldStoneBlock = ACBlocks.PROXIMA_B_BLOCKS.getStateFromMeta(2);
		terrainGenerator.worldSeaGen = true;
		terrainGenerator.worldSeaGenBlock = Blocks.WATER.getDefaultState();
		terrainGenerator.worldSeaGenMaxY = 70;
		cp.createChunkGen_List.add(terrainGenerator);
		
		//-// 
		WE_CaveGen cg = new WE_CaveGen(); 
		cg.replaceBlocksList .clear(); 
		cg.addReplacingBlock(terrainGenerator.worldStoneBlock); 
		cp.createChunkGen_List.add(cg); 
		//-// 
		 
		WE_RavineGen rg = new WE_RavineGen();
		rg.replaceBlocksList    .clear();
		rg.addReplacingBlock(terrainGenerator.worldStoneBlock);
		rg.lavaBlock = Blocks.LAVA.getDefaultState();
		cp.createChunkGen_List.add(rg);
		
		((WE_ChunkProviderSpace)cp).worldGenerators.clear();
		cp.biomesList.clear();
		
		WE_OreGen standardOres = new WE_OreGen();
		standardOres.add(ACBlocks.PROXIMA_B_BLOCKS.getStateFromMeta(1), terrainGenerator.worldStoneBlock, 14, 30, 100, 150);
		standardOres.add(ACBlocks.PROXIMA_B_BLOCKS.getStateFromMeta(5), terrainGenerator.worldStoneBlock, 6, 10, 80, 8);
		standardOres.add(ACBlocks.PROXIMA_B_BLOCKS.getStateFromMeta(6), terrainGenerator.worldStoneBlock, 6, 20, 90, 10);
		standardOres.add(ACBlocks.PROXIMA_B_BLOCKS.getStateFromMeta(7), terrainGenerator.worldStoneBlock, 6, 20, 90, 15);
		standardOres.add(ACBlocks.PROXIMA_B_BLOCKS.getStateFromMeta(8), terrainGenerator.worldStoneBlock, 10, 20, 90, 15);
		standardOres.add(ACBlocks.PROXIMA_B_BLOCKS.getStateFromMeta(9), terrainGenerator.worldStoneBlock, 4, 5, 20, 5);
		standardOres.add(ACBlocks.PROXIMA_B_BLOCKS.getStateFromMeta(10), terrainGenerator.worldStoneBlock, 4, 3, 15, 4);
		
		cp.decorateChunkGen_List.add(standardOres);
		
		//WE_Biome.addBiomeToGeneration(cp, new Proxima_B_Plains());
		
		WE_BiomeLayer layer = new WE_BiomeLayer();
		layer.add(ACBlocks.PROXIMA_B_BLOCKS.getStateFromMeta(11), terrainGenerator.worldStoneBlock, -256, 0, -4, -1, true);
		layer.add(Blocks.BEDROCK.getDefaultState(), 0, 0, 1, 2, true);
		
		WE_Biome.addBiomeToGeneration(cp, new WE_BaseBiome(-0.4D, 0.0D, 1.4D, 4, 78, 10, layer) {
			@Override
			public void decorateBiome(World world, Random rand, int x, int z)
			{
				int randPosX;
				int randPosZ;
				BlockPos pos;
				
				for(int i = 0; i < 80; i++) {
					randPosX = x + rand.nextInt(16) + 8;
					randPosZ = z + rand.nextInt(16) + 8;
					pos = world.getHeight(new BlockPos(randPosX, 0, randPosZ));

					if (world.getBlockState(pos.down()) == ACBlocks.PROXIMA_B_BLOCKS.getDefaultState().withProperty(Proxima_B_Blocks.BASIC_TYPE, Proxima_B_Blocks.EnumBlockProximaB.ROCKY_SURFACE)) {
						world.setBlockState(pos.down(), ACBlocks.PROXIMA_B_BLOCKS.getDefaultState().withProperty(Proxima_B_Blocks.BASIC_TYPE, Proxima_B_Blocks.EnumBlockProximaB.SURFACE));
						world.setBlockState(pos, ACBlocks.PROXIMA_B_DANDELIONS.getDefaultState().withProperty(Proxima_B_Dandelions.BASIC_TYPE, Proxima_B_Dandelions.EnumBlockDandelions.GRASS));
					}
			
				}
				
				if(rand.nextInt(2) == 0)
				{
					randPosX = x + rand.nextInt(16) + 8;
					randPosZ = z + rand.nextInt(16) + 8;
					pos = world.getHeight(new BlockPos(randPosX, 0, randPosZ));
					
					if (world.getBlockState(pos.down()) == ACBlocks.PROXIMA_B_BLOCKS.getDefaultState().withProperty(Proxima_B_Blocks.BASIC_TYPE, Proxima_B_Blocks.EnumBlockProximaB.ROCKY_SURFACE)) {						
						new WorldGenMushroom().generate(world, rand, pos);
					}
				}
			}
		});
		
				
		//WE_Biome.addBiomeToGeneration(cp, new Proxima_B_Forest(cp)); 
		layer = new WE_BiomeLayer();
		layer.add(ACBlocks.PROXIMA_B_BLOCKS.getStateFromMeta(14), terrainGenerator.worldStoneBlock, -256, 0, -4, -1, true);
		layer.add(ACBlocks.PROXIMA_B_GRASS.getDefaultState(), ACBlocks.PROXIMA_B_BLOCKS.getStateFromMeta(14), -256, 0, -256, 0, false);
		layer.add(Blocks.BEDROCK.getDefaultState(), 0, 0, 1, 2, true);
		
		WE_Biome.addBiomeToGeneration(cp, new WE_BaseBiome(0.0D, 0.8D, 1.5D, 4, 72, 15, layer) {
			@Override
			public void decorateBiome(World world, Random rand, int x, int z)
			{
				int randPosX = x + rand.nextInt(16) + 8;
				int randPosZ = z + rand.nextInt(16) + 8;
				BlockPos pos = world.getHeight(new BlockPos(randPosX, 0, randPosZ));
				
				boolean cangen = true;
				
				for(BlockPos pos1 : pos.getAllInBox(pos.add(-3, -1, -3), pos.add(3, -1, 3)))
					if(world.isAirBlock(pos1)) 
						cangen = false;

				if(!world.isAreaLoaded(pos, 13, false))
					if(cangen && world.getBlockState(pos.down()) == ACBlocks.PROXIMA_B_GRASS.getDefaultState().withProperty(Proxima_B_Grass.BASIC_TYPE, Proxima_B_Grass.EnumBlockGrass.GRASS))
					{
						switch(rand.nextInt(2))
						{
							case 0:
								new WorldGenTree_BigJungle(ACBlocks.PROXIMA_B_LOG_1.getStateFromMeta(0), Blocks.AIR.getDefaultState(), rand.nextInt(3)).generate(world, rand, pos);
						    	break;
							case 1:
								new WorldGenTree_BigJungle2(ACBlocks.PROXIMA_B_LOG_1.getStateFromMeta(0), Blocks.AIR.getDefaultState(), rand.nextInt(3)).generate(world, rand, pos);
								break;
						}
					}
				
				for(int i = 0; i < 80; i++) {
					randPosX = x + rand.nextInt(16) + 8;
					randPosZ = z + rand.nextInt(16) + 8;
					pos = world.getHeight(new BlockPos(randPosX, 0, randPosZ));

					if (world.getBlockState(pos.down()) == ACBlocks.PROXIMA_B_GRASS.getDefaultState().withProperty(Proxima_B_Grass.BASIC_TYPE, Proxima_B_Grass.EnumBlockGrass.GRASS)) {
						world.setBlockState(pos, ACBlocks.PROXIMA_B_DANDELIONS.getDefaultState().withProperty(Proxima_B_Dandelions.BASIC_TYPE, Proxima_B_Dandelions.EnumBlockDandelions.GRASS_2));
					}
			
				}
				
				if(rand.nextInt(2) == 0){
					randPosX = x + rand.nextInt(16) + 8;
					randPosZ = z + rand.nextInt(16) + 8;
					pos = world.getHeight(new BlockPos(randPosX, 0, randPosZ));
					
					if (world.getBlockState(pos.down()) == ACBlocks.PROXIMA_B_GRASS.getDefaultState().withProperty(Proxima_B_Grass.BASIC_TYPE, Proxima_B_Grass.EnumBlockGrass.GRASS)) {						
						for(int y = 0; y < 6 + rand.nextInt(5); y++) {
							
							int rand_pos = rand.nextInt(100);
							if(rand_pos > 20) 
								pos = pos.east(1);
							if(rand_pos > 50)
								pos = pos.east(-1).west(1);
							
							world.setBlockState(pos.up(y), ACBlocks.PROXIMA_B_LOG_1.getStateFromMeta(0));
						}
					}
				}
				
				for(int i = 0; i < 6; i++){
					randPosX = x + rand.nextInt(16) + 8;
					randPosZ = z + rand.nextInt(16) + 8;
					pos = world.getHeight(new BlockPos(randPosX, 0, randPosZ));
					
					if (world.getBlockState(pos.down()) == ACBlocks.PROXIMA_B_GRASS.getDefaultState().withProperty(Proxima_B_Grass.BASIC_TYPE, Proxima_B_Grass.EnumBlockGrass.GRASS)) {						
						for(int y = 0; y < 6 + rand.nextInt(5); y++) {					
							
							if(y % 3 == 0) 
								pos = pos.offset(EnumFacing.byHorizontalIndex(rand.nextInt(4)));
								
							world.setBlockState(pos.up(y), ACBlocks.PROXIMA_B_LOG_1.getStateFromMeta(0));
						}
					}
				}
			}
		}.setColors(GSUtils.getColor(150, 20, 20, 100), GSUtils.getColor(40, 20, 20, 100), GSUtils.getColor(150, 20, 20, 100)));
		WE_Biome.addBiomeToGeneration(cp, new Proxima_B_Mountains()); 
		WE_Biome.addBiomeToGeneration(cp, new Proxima_B_Ice_Plains());
		WE_Biome.addBiomeToGeneration(cp, new Proxima_B_Beach());  
		WE_Biome.addBiomeToGeneration(cp, new Proxima_B_Ocean()); 
		
	
				
		WE_Biome.setBiomeMap(cp, 1.0D, 4, cp.biomesList.size() * 450D, 2.0D);
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
			
			if(sunBrightness > 0.4f && phase != 0 && phase != 6 && !this.world.isRaining()) {
								
				//colors[0] = colors[0] + skyLight + 0.8F;				
				colors[1] = colors[1] - skyLight / 1.6F;	
				colors[2] = colors[2] - skyLight / 1.6F;	
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
	@SideOnly(Side.CLIENT)
	public float getFogDensity(int arg0, int arg1, int arg2) {
				
		switch(this.getMoonPhase(this.getWorldTime()))
		{
			case 0: return 0.8F;
			case 1: return 0.95F;			
			case 3: return 0.85F;			
			
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
		return true;
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

}