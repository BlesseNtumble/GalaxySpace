package galaxyspace.systems.TauCetiSystem.planets.tauceti_f.dimensions;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_ChunkProviderSpace;
import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_WorldProviderSpace;
import asmodeuscore.core.astronomy.dimension.world.worldengine.biome.WE_BaseBiome;
import asmodeuscore.core.utils.worldengine.WE_Biome;
import asmodeuscore.core.utils.worldengine.WE_ChunkProvider;
import asmodeuscore.core.utils.worldengine.perlinnoise.PerlinNoise;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_BiomeLayer;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_CaveGen;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_RavineGen;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_TerrainGenerator;
import galaxyspace.GalaxySpace;
import galaxyspace.core.prefab.world.gen.WorldGenCircleBlock;
import galaxyspace.core.util.GSDimensions;
import galaxyspace.systems.BarnardsSystem.core.registers.BRBlocks;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Falling_Blocks;
import galaxyspace.systems.TauCetiSystem.TauCetiSystemBodies;
import galaxyspace.systems.TauCetiSystem.core.TCBlocks;
import galaxyspace.systems.TauCetiSystem.planets.tauceti_f.blocks.TauCeti_F_Blocks;
import galaxyspace.systems.TauCetiSystem.planets.tauceti_f.dimensions.sky.SkyProviderTauCeti_F;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.client.CloudRenderer;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderTauCeti_F_WE extends WE_WorldProviderSpace {
	@Override
    public double getHorizon() {
        return 44.0D;
    }

    @Override
    public float getFallDamageModifier() {
        return 1.0F;
    }

    @Override
    public double getFuelUsageMultiplier() {
        return 1.1;
    }

    @Override
    public double getMeteorFrequency() {
        return 10;
    }

    @Override
    public float getSoundVolReductionAmount() {
        return Float.MAX_VALUE;
    }

    @Override
    public boolean canSnowAt(BlockPos pos, boolean checkLight)
    {
    	return true;
    }
        
    @Override
    public CelestialBody getCelestialBody() {
        return TauCetiSystemBodies.TauCeti_F;
    }

    @Override
    public Class<? extends IChunkGenerator> getChunkProviderClass() {
        return WE_ChunkProviderSpace.class;

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
			this.setSkyRenderer(new SkyProviderTauCeti_F());
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
	
		return GSDimensions.TAU_CETI_F;
	}

	@Override
	public void genSettings(WE_ChunkProvider cp) {		

		cp.createChunkGen_List .clear(); 
		cp.createChunkGen_InXZ_List .clear(); 
		cp.createChunkGen_InXYZ_List.clear(); 
		cp.decorateChunkGen_List .clear(); 
		
		WE_Biome.setBiomeMap(cp, 1.8D, 4, 2500.0D, 2.0D);	
		WE_Biome.addPrelinNoise(cp, new PerlinNoise(cp.worldObj.getSeed(), 1.5D, cp.biomemapNumberOfOctaves, 400, cp.biomemapScaleY, 0));

		WE_TerrainGenerator terrainGenerator = new WE_TerrainGenerator(); 
		terrainGenerator.worldStoneBlock = TCBlocks.TAUCETI_F_BLOCKS.getStateFromMeta(0); 
		terrainGenerator.worldSeaGen = true;
		terrainGenerator.worldSeaGenBlock = Blocks.WATER.getDefaultState();
		terrainGenerator.worldSeaGenMaxY = 100;
		cp.createChunkGen_List.add(terrainGenerator);
		
		//-// 
		WE_CaveGen cg = new WE_CaveGen(); 
		cg.replaceBlocksList .clear(); 
		cg.addReplacingBlock(terrainGenerator.worldStoneBlock); 
		cg.lavaMaxY = 0;
		cg.range = 32;
		cg.d6_range = 4.5D;
		cg.frequency = 5;
		cg.maxY = 70;
		cg.caveBlock = terrainGenerator.worldSeaGenBlock;
		cg.ignoreLiquid = true;
		cp.createChunkGen_List.add(cg); 
		//-// 
		 
		WE_RavineGen rg = new WE_RavineGen();
		rg.replaceBlocksList.clear();
		rg.addReplacingBlock(terrainGenerator.worldStoneBlock);
		rg.lavaBlock = Blocks.LAVA.getDefaultState();
		rg.lavaMaxY = 0;		
		rg.range = 32;
		rg.caveBlock = terrainGenerator.worldSeaGenBlock;
		rg.ignoreLiquid = true;
		cp.createChunkGen_List.add(rg);
		
		((WE_ChunkProviderSpace)cp).worldGenerators.clear();
		cp.biomesList.clear();
		
		WE_BiomeLayer layer = new WE_BiomeLayer();
		layer.add(TCBlocks.TAUCETI_F_BLOCKS.getStateFromMeta(2), terrainGenerator.worldStoneBlock, -256, 0, -4, -1, true);
		layer.add(Blocks.BEDROCK.getDefaultState(), 0, 0, 1, 2, true);
		//layer.add(Blocks.PACKED_ICE.getDefaultState(), terrainGenerator.worldStoneBlock, -256, 0,   -4, -10,  true);
		//layer.add(Blocks.SNOW.getDefaultState(), Blocks.PACKED_ICE.getDefaultState(), -256, 0,   -2, -1,  false);
		
		
		WE_Biome.addBiomeToGeneration(cp, new WE_BaseBiome(0D, 1.4F, 4, 90, 35, layer) {
			@Override
			public void decorateBiome(World world, Random rand, int x, int z)
			{
				for(int i = 0; i < 80; i++) {
					int randPosX = x + rand.nextInt(16) + 8;
					int randPosZ = z + rand.nextInt(16) + 8;
					for(int y = terrainGenerator.worldSeaGenMaxY; y > 0; y--) {	
						BlockPos pos = new BlockPos(randPosX, y, randPosZ);
						if(y < 91 && world.getBlockState(pos).getMaterial() == Material.WATER && world.getBlockState(pos.down()) == TCBlocks.TAUCETI_F_BLOCKS.getDefaultState().withProperty(TauCeti_F_Blocks.BASIC_TYPE, TauCeti_F_Blocks.EnumBlockTauCetiF.STONE))
							world.setBlockState(pos, TCBlocks.TAUCETI_F_WATERGRASS.getStateFromMeta(0));
					
					}
				}
				
				for(int i = 0; i < 2; i++) {
					int randPosX = x + rand.nextInt(16) + 8;
					int randPosZ = z + rand.nextInt(16) + 8;
					for(int y = terrainGenerator.worldSeaGenMaxY; y > 0; y--) {	
						BlockPos pos = new BlockPos(randPosX, y, randPosZ);
						if(y < 90 && world.getBlockState(pos).getMaterial() == Material.WATER && world.getBlockState(pos.down()) == TCBlocks.TAUCETI_F_BLOCKS.getDefaultState().withProperty(TauCeti_F_Blocks.BASIC_TYPE, TauCeti_F_Blocks.EnumBlockTauCetiF.STONE))
						{
							world.setBlockState(pos, TCBlocks.TAUCETI_F_WATERGRASS.getStateFromMeta(4));
							world.setBlockState(pos.up(), TCBlocks.TAUCETI_F_WATERGRASS.getStateFromMeta(5));
						}
					
					}
				}
			}
		}.setColors(0x00FF00, 0x11FF66, 0x00FF00));	
		WE_Biome.addBiomeToGeneration(cp, new WE_BaseBiome(0.8D, 2.4F, 4, 80, 35, layer) {
			@Override
			public void decorateBiome(World world, Random rand, int x, int z)
			{
				for(int i = 0; i < 10; i++) {
					int randPosX = x + rand.nextInt(16) + 8;
					int randPosZ = z + rand.nextInt(16) + 8;
					for(int y = terrainGenerator.worldSeaGenMaxY; y > 0; y--) {					
						BlockPos pos = new BlockPos(randPosX, y, randPosZ);					
						if(world.getBlockState(pos).getMaterial() == Material.WATER && world.getBlockState(pos.down()) == TCBlocks.TAUCETI_F_BLOCKS.getDefaultState().withProperty(TauCeti_F_Blocks.BASIC_TYPE, TauCeti_F_Blocks.EnumBlockTauCetiF.STONE))
						{
							int size = rand.nextInt(15) + 1;
							world.setBlockState(pos, TCBlocks.TAUCETI_F_WATERGRASS.getStateFromMeta(1));
							
							
							for(int l = 1; l < size - 1; l++) 
								if(l < size - 1 && world.getBlockState(pos.up(l + 1)).getMaterial() == Material.WATER) 
									world.setBlockState(pos.up(l),  TCBlocks.TAUCETI_F_WATERGRASS.getStateFromMeta(2));
						
							if(world.getBlockState(pos.up(size)).getMaterial() == Material.WATER)
								world.setBlockState(pos.up(size-1),  TCBlocks.TAUCETI_F_WATERGRASS.getStateFromMeta(3));
							

							break;
						}
					}
				}
			}
		}.setColors(0x00FF00, 0x11FF66, 0x00FF00));	
		WE_Biome.addBiomeToGeneration(cp, new WE_BaseBiome(2.6D, 2.6F, 4, 60, 10, layer) {
			@Override
			public void decorateBiome(World world, Random rand, int x, int z)
			{}
		}.setColors(0x00FF00, 0x11FF66, 0x00FF00));	
		WE_Biome.addBiomeToGeneration(cp, new WE_BaseBiome(4.0D, 1.4F, 4, 40, 15, layer) {
			@Override
			public void decorateBiome(World world, Random rand, int x, int z)
			{
				for(int i = 0; i < 1; i++) {
					int randPosX = x + rand.nextInt(16) + 8;
					int randPosZ = z + rand.nextInt(16) + 8;
					for(int y = terrainGenerator.worldSeaGenMaxY; y > 0; y--) {	
						BlockPos pos = new BlockPos(randPosX, y, randPosZ);
						if(world.getBlockState(pos).getMaterial() == Material.WATER && world.getBlockState(pos.down()) == TCBlocks.TAUCETI_F_BLOCKS.getDefaultState().withProperty(TauCeti_F_Blocks.BASIC_TYPE, TauCeti_F_Blocks.EnumBlockTauCetiF.STONE))
							new WorldGenCircleBlock(Blocks.DIRT.getDefaultState(), 8, TCBlocks.TAUCETI_F_BLOCKS.getStateFromMeta(2)).generate(world, rand, pos);
					}		
				}
				
				for(int i = 0; i < 2; i++) {
					int randPosX = x + rand.nextInt(16) + 8;
					int randPosZ = z + rand.nextInt(16) + 8;
					for(int y = terrainGenerator.worldSeaGenMaxY; y > 0; y--) {					
						BlockPos pos = new BlockPos(randPosX, y, randPosZ);					
						if(y > 35 && world.getBlockState(pos).getMaterial() == Material.WATER && world.getBlockState(pos.down()) == TCBlocks.TAUCETI_F_BLOCKS.getDefaultState().withProperty(TauCeti_F_Blocks.BASIC_TYPE, TauCeti_F_Blocks.EnumBlockTauCetiF.STONE))
						{
							int size = rand.nextInt(15) + 1;
							world.setBlockState(pos, TCBlocks.TAUCETI_F_WATERGRASS.getStateFromMeta(1));
							
							
							for(int l = 1; l < size - 1; l++) 
								if(l < size - 1 && world.getBlockState(pos.up(l + 1)).getMaterial() == Material.WATER) 
									world.setBlockState(pos.up(l),  TCBlocks.TAUCETI_F_WATERGRASS.getStateFromMeta(2));
						
							if(world.getBlockState(pos.up(size)).getMaterial() == Material.WATER)
								world.setBlockState(pos.up(size-1),  TCBlocks.TAUCETI_F_WATERGRASS.getStateFromMeta(3));
							

							break;
						}
					}
				}
				
				for(int i = 0; i < 50; i++) {
					int randPosX = x + rand.nextInt(16) + 8;
					int randPosZ = z + rand.nextInt(16) + 8;
					for(int y = terrainGenerator.worldSeaGenMaxY; y > 0; y--) {	
						BlockPos pos = new BlockPos(randPosX, y, randPosZ);
						if(y > 35 && world.getBlockState(pos).getMaterial() == Material.WATER && world.getBlockState(pos.down()) == TCBlocks.TAUCETI_F_BLOCKS.getDefaultState().withProperty(TauCeti_F_Blocks.BASIC_TYPE, TauCeti_F_Blocks.EnumBlockTauCetiF.STONE))
							world.setBlockState(pos, TCBlocks.TAUCETI_F_WATERGRASS.getStateFromMeta(6));
					
					}
				}
				
				
			}
		}.setColors(0x00FF00, 0x11FF66, 0x00FF00));	
		//WE_Biome.addBiomeToGeneration(cp, new WE_BaseBiome(1.0D, 1.6F, 4, 40, 15, layer).setPrelinNoiseID(1).setColors(0x00FF00, 0xFF00FF, 0x00FF00));	
		
		//WE_Biome.addBiomeToGeneration(cp, new WE_BaseBiome(-0.3D, 0.3D, 1.9F, 4, 90, 3, layer).setColors(0x00FF00, 0xFF0000, 0x00FF00));	
		//WE_Biome.addBiomeToGeneration(cp, new WE_BaseBiome(-0.9D, 0.9D, 1.5F, 4, 135, 15, layer).setColors(0x00FF00, 0xFF0000, 0x00FF00));	
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
	public float getThermalLevelModifier(EntityPlayer player) { 
		//if(player.posY < 80) 
			//return 1.0F;
		
		return this.getThermalLevelModifier();
	};

}
