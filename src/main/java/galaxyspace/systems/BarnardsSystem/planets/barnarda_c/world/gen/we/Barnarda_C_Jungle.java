package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.we;

import java.util.Random;

import asmodeuscore.core.astronomy.dimension.world.gen.features.trees.WorldGenTree_Forest;
import asmodeuscore.core.astronomy.dimension.world.gen.features.trees.WorldGenTree_Forest2;
import asmodeuscore.core.utils.worldengine.WE_Biome;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_BiomeLayer;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_OreGen;
import galaxyspace.GalaxySpace;
import galaxyspace.core.prefab.world.gen.we.WE_LakesGen;
import galaxyspace.systems.BarnardsSystem.core.registers.BRBlocks;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Blocks;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Dandelions;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Grass;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.WorldGenTree_Jungle;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.WorldGenTree_Small;
import net.minecraft.block.material.Material;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class Barnarda_C_Jungle extends WE_Biome {
	
	public Barnarda_C_Jungle(double min, double max) {
		super(new BiomeProperties("barnarda_c_jungle"), new int[] {0x45AC33, 0x11FF66, 0x985cff});
				
		biomeMinValueOnMap      =  	min;
		biomeMaxValueOnMap      =   max;
		biomePersistence        =   1.4D;
		biomeNumberOfOctaves    =      4;
		biomeScaleX             = 280.0D;
		biomeScaleY             =   1.7D;
		biomeSurfaceHeight      =     68;
		biomeInterpolateQuality =     30;
		biomeTemerature 		=   0.9F;
		//-//
		decorateChunkGen_List.clear();		
		createChunkGen_InXZ_List.clear();
		
		this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityChicken.class, 10, 4, 4));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityOcelot.class, 2, 1, 1));
		
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityZombie.class, 10, 1, 2));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySpider.class, 10, 1, 2));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySkeleton.class, 10, 1, 2));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEnderman.class, 10, 1, 1));
		
		this.spawnableWaterCreatureList.add(new Biome.SpawnListEntry(EntitySquid.class, 10, 1, 3));
		
		
		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		standardBiomeLayers.add(BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(3), BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(1), -256, 0,   -5, -1,  true);
		standardBiomeLayers.add(BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(0), BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(3), -256, 0,   -1, -1,  true);
		standardBiomeLayers.add(BRBlocks.BARNARDA_C_GRASS.getStateFromMeta(0), BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(0), -256, 0, -256,  0, false);
		
		standardBiomeLayers.add(Blocks.BEDROCK.getDefaultState(), 0, 2, 0, 0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);
	/*	
		WE_LakesGen lakes = new WE_LakesGen();
		lakes.lakeBlock = Blocks.WATER.getDefaultState();
		lakes.iceGen = false;
		lakes.chunksForLake = 20;
		decorateChunkGen_List.add(lakes);
		
		lakes = new WE_LakesGen();
		lakes.lakeBlock = Blocks.LAVA.getDefaultState();
		lakes.iceGen = false;
		lakes.chunksForLake = 100;
		decorateChunkGen_List.add(lakes);	*/
		
	}
	
	@Override
	public void decorateBiome(World world, Random rand, int x, int z)
	{	
		
		int randPosX = x + rand.nextInt(16) + 8;
		int randPosZ = z + rand.nextInt(16) + 8;
		BlockPos pos = world.getHeight(new BlockPos(randPosX, 0, randPosZ));
		
		for (int i = 0; i < 2; i++) {

			randPosX = x + rand.nextInt(16) + 8;
			randPosZ = z + rand.nextInt(16) + 8;
			pos = world.getHeight(new BlockPos(randPosX, 0, randPosZ));

			boolean cangen = false;
			int range = 5;
			for (int kx = -range; kx < range; kx++)
			{
				if(cangen) break;
				for (int kz = -range; kz < range; kz++)
					if (world.isAirBlock(pos.down()) || pos.getY() < 64)
						cangen = true;
			}
			
			if (!cangen) {
				if (rand.nextInt(50) == 0)
					new WorldGenTree_Jungle(BRBlocks.BARNARDA_C_VIOLET_LOG.getStateFromMeta(0), BRBlocks.BARNARDA_C_LEAVES.getStateFromMeta(0), 2, 1)
							.generate(world, rand, pos);
				else
					new WorldGenTree_Jungle(BRBlocks.BARNARDA_C_VIOLET_LOG.getStateFromMeta(0), BRBlocks.BARNARDA_C_LEAVES.getStateFromMeta(0), 1, rand.nextInt(2) + 1).generate(world, rand, pos);

			}
		}
		
		for(int i = 0; i < 16; i++){
			randPosX = x + rand.nextInt(16) + 10;
			randPosZ = z + rand.nextInt(16) + 10;
			pos = world.getHeight(new BlockPos(randPosX, 0, randPosZ));
			
			if(world.getBlockState(pos.down()) == BRBlocks.BARNARDA_C_GRASS.getDefaultState().withProperty(Barnarda_C_Grass.BASIC_TYPE, Barnarda_C_Grass.EnumBlockGrass.GRASS))
			{
				world.setBlockState(pos, BRBlocks.BARNARDA_C_VIOLET_LOG.getStateFromMeta(0));
				
				if(rand.nextInt(10) == 0) {
					world.setBlockState(pos.up(), BRBlocks.BARNARDA_C_VIOLET_LOG.getStateFromMeta(0));
					pos = pos.add(0, 1, 0);
				}
				
				world.setBlockState(pos.east(), BRBlocks.BARNARDA_C_LEAVES.getStateFromMeta(0));
				world.setBlockState(pos.west(), BRBlocks.BARNARDA_C_LEAVES.getStateFromMeta(0));
				world.setBlockState(pos.north(), BRBlocks.BARNARDA_C_LEAVES.getStateFromMeta(0));
				world.setBlockState(pos.south(), BRBlocks.BARNARDA_C_LEAVES.getStateFromMeta(0));
				
				world.setBlockState(pos.east(2), BRBlocks.BARNARDA_C_LEAVES.getStateFromMeta(0));
				world.setBlockState(pos.west(2), BRBlocks.BARNARDA_C_LEAVES.getStateFromMeta(0));
				world.setBlockState(pos.north(2), BRBlocks.BARNARDA_C_LEAVES.getStateFromMeta(0));
				world.setBlockState(pos.south(2), BRBlocks.BARNARDA_C_LEAVES.getStateFromMeta(0));
				
				world.setBlockState(pos.east().north(), BRBlocks.BARNARDA_C_LEAVES.getStateFromMeta(0));
				world.setBlockState(pos.east().south(), BRBlocks.BARNARDA_C_LEAVES.getStateFromMeta(0));
				world.setBlockState(pos.west().north(), BRBlocks.BARNARDA_C_LEAVES.getStateFromMeta(0));
				world.setBlockState(pos.west().south(), BRBlocks.BARNARDA_C_LEAVES.getStateFromMeta(0));
			
				world.setBlockState(pos.up(), BRBlocks.BARNARDA_C_LEAVES.getStateFromMeta(0));
				
			}
		}
		
		for(int i = 0; i < 2; i++){
			randPosX = x + rand.nextInt(16) + 12;
			randPosZ = z + rand.nextInt(16) + 12;
			pos = world.getHeight(new BlockPos(randPosX, 0, randPosZ));
			
			if(world.getBlockState(pos.down()) == BRBlocks.BARNARDA_C_GRASS.getDefaultState().withProperty(Barnarda_C_Grass.BASIC_TYPE, Barnarda_C_Grass.EnumBlockGrass.GRASS))
				new WorldGenTree_Small(BRBlocks.BARNARDA_C_VIOLET_LOG.getDefaultState(), BRBlocks.BARNARDA_C_LEAVES.getStateFromMeta(0), rand.nextInt(3)).generate(world, rand, pos);
    	}
		
		for(int i = 0; i < 20; i++){			
			randPosX = x + rand.nextInt(16) + 8;
			randPosZ = z + rand.nextInt(16) + 8;
			pos = world.getHeight(new BlockPos(randPosX, 0, randPosZ));
			
			if(world.getBlockState(pos.down()) == BRBlocks.BARNARDA_C_GRASS.getDefaultState().withProperty(Barnarda_C_Grass.BASIC_TYPE, Barnarda_C_Grass.EnumBlockGrass.GRASS))
				world.setBlockState(pos, BRBlocks.BARNARDA_C_DANDELIONS.getDefaultState().withProperty(Barnarda_C_Dandelions.BASIC_TYPE, Barnarda_C_Dandelions.EnumBlockDandelions.GRASS));
		}
		
		for(int i = 0; i < 8; i++){
			for(int y = 40; y < 150; y++) {
				randPosX = x + rand.nextInt(16) + 8;
				randPosZ = z + rand.nextInt(16) + 8;
				pos = new BlockPos(randPosX, y, randPosZ);
    			
    			if(world.isAirBlock(pos.down()) && world.getBlockState(pos.up()) == BRBlocks.BARNARDA_C_LEAVES.getStateFromMeta(0))
        		{
    				world.setBlockState(pos, BRBlocks.BARNARDA_C_DANDELIONS.getDefaultState().withProperty(Barnarda_C_Dandelions.BASIC_TYPE, Barnarda_C_Dandelions.EnumBlockDandelions.LEAVES_BALLS));
        		}
			}
    	}
		
		for(int i = 0; i < 36; i++){
			randPosX = x + rand.nextInt(16) + 8;
			randPosZ = z + rand.nextInt(16) + 8;
			pos = new BlockPos(randPosX, 64 + rand.nextInt(50), randPosZ);
			
			if(world.getBlockState(pos) == BRBlocks.BARNARDA_C_VIOLET_LOG.getStateFromMeta(0))			
				world.setBlockState(pos, BRBlocks.BARNARDA_C_VIOLET_GLOW_LOG.getStateFromMeta(0));
			
		}
		
		for(int i = 0; i < 12; i++){
			for(int y = 40; y < 150; y++) {
				randPosX = x + rand.nextInt(16) + 8;
				randPosZ = z + rand.nextInt(16) + 8;
				pos = new BlockPos(randPosX, y, randPosZ);
				
					if(world.getBlockState(pos.west()) == BRBlocks.BARNARDA_C_LEAVES.getStateFromMeta(0) || world.getBlockState(pos.west()) == BRBlocks.BARNARDA_C_VIOLET_LOG.getStateFromMeta(0))
						for(int size = 0; size < rand.nextInt(15) + 5; size++)
							if (world.isAirBlock(pos.down(size)))
								world.setBlockState(pos.down(size), Blocks.VINE.getStateFromMeta(2));
							else break;
										
					if(world.getBlockState(pos.east()) == BRBlocks.BARNARDA_C_LEAVES.getStateFromMeta(0) || world.getBlockState(pos.east()) == BRBlocks.BARNARDA_C_VIOLET_LOG.getStateFromMeta(0)) 
						for(int size = 0; size < rand.nextInt(15) + 5; size++)
							if (world.isAirBlock(pos.down(size)))
								world.setBlockState(pos.down(size), Blocks.VINE.getStateFromMeta(8));
							else break;
					
					if(world.getBlockState(pos.north()) == BRBlocks.BARNARDA_C_LEAVES.getStateFromMeta(0) || world.getBlockState(pos.north()) == BRBlocks.BARNARDA_C_VIOLET_LOG.getStateFromMeta(0))
						for(int size = 0; size < rand.nextInt(15) + 5; size++)
							if (world.isAirBlock(pos.down(size)))
								world.setBlockState(pos.down(size), Blocks.VINE.getStateFromMeta(4));
							else break;
					
					if(world.getBlockState(pos.south()) == BRBlocks.BARNARDA_C_LEAVES.getStateFromMeta(0) || world.getBlockState(pos.south()) == BRBlocks.BARNARDA_C_VIOLET_LOG.getStateFromMeta(0))
						for(int size = 0; size < rand.nextInt(15) + 5; size++)
							if (world.isAirBlock(pos.down(size)))
								world.setBlockState(pos.down(size), Blocks.VINE.getStateFromMeta(1));
							else break;
			}
        }
			
		
	}
}

