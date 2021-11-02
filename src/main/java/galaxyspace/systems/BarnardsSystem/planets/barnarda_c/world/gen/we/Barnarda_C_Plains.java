package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.we;

import java.util.Random;

import asmodeuscore.core.astronomy.dimension.world.gen.features.trees.WorldGenTree_Forest;
import asmodeuscore.core.astronomy.dimension.world.gen.features.trees.WorldGenTree_Forest2;
import asmodeuscore.core.utils.worldengine.WE_Biome;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_BiomeLayer;
import galaxyspace.core.prefab.world.gen.we.WE_LakesGen;
import galaxyspace.systems.BarnardsSystem.core.BRBlocks;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Dandelions;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Grass;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class Barnarda_C_Plains extends WE_Biome {
	
	public Barnarda_C_Plains(double min, double max) {
		super(new BiomeProperties("barnarda_c_plains"), new int[] {/*0x88BB44*/0x822899, 0x11FF66, 0x985cff});
				
		biomeMinValueOnMap      =  	min;
		biomeMaxValueOnMap      =   max;
		biomePersistence        =   1.4D;
		biomeNumberOfOctaves    =      4;
		biomeScaleX             = 280.0D;
		biomeScaleY             =   1.7D;
		biomeSurfaceHeight      =     69;
		biomeInterpolateQuality =     25;
		biomeTemerature 		= 	0.4F;
		//-//
		decorateChunkGen_List.clear();		
		createChunkGen_InXZ_List.clear();
		
		this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntitySheep.class, 4, 3, 7));
		this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityChicken.class, 4, 3, 7));
		
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityZombie.class, 10, 2, 6));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySpider.class, 10, 1, 4));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySkeleton.class, 10, 2, 6));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEnderman.class, 10, 1, 1));
						
		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		standardBiomeLayers.add(BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(3), BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(1), -256, 0,   -5, -1,  true);
		standardBiomeLayers.add(BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(0), BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(3), -256, 0,   -1, -1,  true);
		standardBiomeLayers.add(BRBlocks.BARNARDA_C_GRASS.getStateFromMeta(0), BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(0), -256, 0, -256,  0, false);
		standardBiomeLayers.add(Blocks.BEDROCK.getDefaultState(), 0, 2, 0, 0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);
		
		WE_LakesGen lakes = new WE_LakesGen();
		lakes.lakeBlock = Blocks.WATER.getDefaultState();
		lakes.iceGen = false;
		lakes.chunksForLake = 8;
		decorateChunkGen_List.add(lakes);
	}
	
	@Override
	public void decorateBiome(World world, Random rand, int x, int z)
	{
		int randPosX;
		int randPosZ;
		BlockPos pos;
		
		for (int i = 0; i < 180; i++) {
			randPosX = x + rand.nextInt(16) + 8;
			randPosZ = z + rand.nextInt(16) + 8;
			pos = world.getHeight(new BlockPos(randPosX, 0, randPosZ));

			if (world.getBlockState(pos.down()) == BRBlocks.BARNARDA_C_GRASS.getDefaultState().withProperty(Barnarda_C_Grass.BASIC_TYPE, Barnarda_C_Grass.EnumBlockGrass.GRASS))
					world.setBlockState(pos, BRBlocks.BARNARDA_C_DANDELIONS.getDefaultState().withProperty(Barnarda_C_Dandelions.BASIC_TYPE, Barnarda_C_Dandelions.EnumBlockDandelions.GRASS));
		}
		
		if(rand.nextInt(5) == 0){
			randPosX = x + rand.nextInt(16) + 8;
			randPosZ = z + rand.nextInt(16) + 8;
			pos = world.getHeight(new BlockPos(randPosX, 0, randPosZ));
				
			boolean cangen = true;
					
			cangen = true;
			for(BlockPos pos1 : pos.getAllInBox(pos.add(-3, -1, -3), pos.add(3, -1, 3)))
				if(world.isAirBlock(pos1) || world.getBlockState(pos1) == BRBlocks.BARNARDA_C_VIOLET_GLOW_LOG.getStateFromMeta(0)) 
						cangen = false;
					
		    if(!world.isAreaLoaded(pos, 13, false))
			    if(cangen && world.getBlockState(pos.down()) == BRBlocks.BARNARDA_C_GRASS.getDefaultState().withProperty(Barnarda_C_Grass.BASIC_TYPE, Barnarda_C_Grass.EnumBlockGrass.GRASS))
			    {
			    	switch(rand.nextInt(2))
					{
						case 0:						
							new WorldGenTree_Forest(BRBlocks.BARNARDA_C_VIOLET_LOG.getStateFromMeta(0), BRBlocks.BARNARDA_C_LEAVES.getStateFromMeta(0), rand.nextInt(3)).generate(world, rand, pos);
							break;
						case 1:
							new WorldGenTree_Forest2(BRBlocks.BARNARDA_C_VIOLET_LOG.getStateFromMeta(0), BRBlocks.BARNARDA_C_LEAVES.getStateFromMeta(0), rand.nextInt(3)).generate(world, rand, pos);
							break;
					}
				}
	    }
	}
}
