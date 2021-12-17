package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.we;

import java.util.Random;

import asmodeuscore.core.utils.worldengine.WE_Biome;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_BiomeLayer;
import galaxyspace.core.prefab.world.gen.WorldGenCircleBlock;
import galaxyspace.systems.BarnardsSystem.core.BRBlocks;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Blocks;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Dandelions;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Grass;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.WorldGenTree_Swampland;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.WorldGenTree_Swampland_2;
import net.minecraft.block.material.Material;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class Barnarda_C_Swampland extends WE_Biome {
	
	public Barnarda_C_Swampland(double min, double max) {
		super(new BiomeProperties("barnarda_c_swampland"), new int[] {/*0x54732a*/ 0x822899, 0x11CC44, 0x985cff});
				
		biomeMinValueOnMap      =  	min;
		biomeMaxValueOnMap      =   max;
		biomePersistence        =   1.1D;
		biomeNumberOfOctaves    =      6;
		biomeScaleX             = 280.0D;
		biomeScaleY             =   1.7D;
		biomeSurfaceHeight      =     64;
		biomeInterpolateQuality =     5;
		biomeTemerature = 0.3F;
		//-//
		decorateChunkGen_List.clear();		
		createChunkGen_InXZ_List.clear();

		this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityPig.class, 10, 1, 4));

		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityZombie.class, 10, 1, 4));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySpider.class, 10, 1, 4));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySkeleton.class, 10, 1, 4));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySlime.class, 10, 1, 1));

		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		standardBiomeLayers.add(BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(3), BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(1), -256, 0,   -5, -1,  true);
		standardBiomeLayers.add(BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(0), BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(3), -256, 0,   -1, -1,  true);
		standardBiomeLayers.add(BRBlocks.BARNARDA_C_GRASS.getStateFromMeta(0), BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(0), -256, 0, -256,  0, false);
		standardBiomeLayers.add(Blocks.BEDROCK.getDefaultState(), 0, 2, 0, 0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);
	}
	
	@Override
	public void decorateBiome(World world, Random rand, int x, int z)
	{
		int randPosX = x + rand.nextInt(16) + 8;
		int randPosZ = z + rand.nextInt(16) + 8;
		BlockPos pos = world.getHeight(new BlockPos(randPosX, 0, randPosZ));
		
	
		WorldGenCircleBlock circle = new WorldGenCircleBlock(Blocks.CLAY.getDefaultState(), rand.nextInt(5) + 3, BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(0));
		if(rand.nextInt(8) == 0) {
			randPosX = x + rand.nextInt(8) + 8;
			randPosZ = z + rand.nextInt(8) + 8;
			pos = world.getHeight(new BlockPos(randPosX, 0, randPosZ));
			
			//if(world.getBlockState(pos).getMaterial() == Material.WATER)
			circle.generate(world, rand, pos.down());
			
			
		}
		circle = new WorldGenCircleBlock(Blocks.GRAVEL.getDefaultState(), rand.nextInt(8) + 5, BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(0));
		if(rand.nextInt(2) == 0) {
			randPosX = x + rand.nextInt(8) + 8;
			randPosZ = z + rand.nextInt(8) + 8;
			pos = world.getHeight(new BlockPos(randPosX, 0, randPosZ));			
			circle.generate(world, rand, pos.down());		
			
		}
		
		for(int i = 0; i < 5; i++){
			randPosX = x + rand.nextInt(16) + 8;
			randPosZ = z + rand.nextInt(16) + 8;
			pos = world.getHeight(new BlockPos(randPosX, 0, randPosZ));
		   
		
	    	boolean cangen = true;
	    	for(BlockPos pos1 : pos.getAllInBox(pos.add(-3, -1, -3), pos.add(3, -1, 3)))
				if(world.isAirBlock(pos1) || world.getBlockState(pos1) == BRBlocks.BARNARDA_C_VIOLET_LOG.getStateFromMeta(0)) 
					cangen = false;
	  
	    	while(!world.isBlockNormalCube(pos, true))
	    	{
	    		pos = pos.down();
	    	}
	    	if(!world.isAreaLoaded(pos, 10, false))
		    	if(cangen && (
		    			world.getBlockState(pos.down()) == BRBlocks.BARNARDA_C_GRASS.getDefaultState().withProperty(Barnarda_C_Grass.BASIC_TYPE, Barnarda_C_Grass.EnumBlockGrass.GRASS) 
		    			|| world.getBlockState(pos.down()) == BRBlocks.BARNARDA_C_BLOCKS.getDefaultState().withProperty(Barnarda_C_Blocks.BASIC_TYPE, Barnarda_C_Blocks.EnumBlockBarnardaC.DIRT) 
		    			|| world.getBlockState(pos.down()) == BRBlocks.BARNARDA_C_BLOCKS.getDefaultState().withProperty(Barnarda_C_Blocks.BASIC_TYPE, Barnarda_C_Blocks.EnumBlockBarnardaC.DIRT_1)) )
		    	{
		    		switch(rand.nextInt(2))
					{
						case 0:						
							new WorldGenTree_Swampland(BRBlocks.BARNARDA_C_VIOLET_LOG.getStateFromMeta(0), BRBlocks.BARNARDA_C_LEAVES.getStateFromMeta(0), rand.nextInt(3)).generate(world, rand, pos);
							break;
						case 1:
							new WorldGenTree_Swampland_2(BRBlocks.BARNARDA_C_VIOLET_LOG.getStateFromMeta(0), BRBlocks.BARNARDA_C_LEAVES.getStateFromMeta(0), rand.nextInt(3)).generate(world, rand, pos);
							break;
					}
				}
		}
		
		for(int i = 0; i < 16; i++){
    		for(int k = 0; k < 16; k++){
				randPosX = x + i + 8;
				randPosZ = z + k + 8;
				
				pos = world.getTopSolidOrLiquidBlock(new BlockPos(randPosX, 0, randPosZ));
				
				if(world.getBlockState(pos).getMaterial() == Material.WATER && rand.nextInt(2) == 0)
				{
					world.setBlockState(pos.up(), BRBlocks.BARNARDA_C_WATER_GRASS.getDefaultState());
				}
    		}
		}
		
		for(int i = 0; i < 30; i++){
    		randPosX = x + rand.nextInt(16) + 8;
			randPosZ = z + rand.nextInt(16) + 8;
			pos = world.getHeight(new BlockPos(randPosX, 0, randPosZ));
			
			boolean cangen = false;
			int range = 2;
			for(BlockPos pos1 : pos.getAllInBox(pos.add(-range, -range, -range), pos.add(range, range, range)))
				if(world.getBlockState(pos1).getMaterial() == Material.WATER) 
					cangen = true;
			
			if(cangen && 
					(world.getBlockState(pos.down()) == BRBlocks.BARNARDA_C_GRASS.getDefaultState().withProperty(Barnarda_C_Grass.BASIC_TYPE, Barnarda_C_Grass.EnumBlockGrass.GRASS) ||
					world.getBlockState(pos.down()) == BRBlocks.BARNARDA_C_BLOCKS.getDefaultState().withProperty(Barnarda_C_Blocks.BASIC_TYPE, Barnarda_C_Blocks.EnumBlockBarnardaC.DIRT)))
    		{
				if(rand.nextInt(15) == 0)
				{
					for(int size = 0; size < 2 + rand.nextInt(2); size++)
					{
						if(rand.nextBoolean())
							world.setBlockState(pos.up(size), BRBlocks.BARNARDA_C_DANDELIONS.getDefaultState().withProperty(Barnarda_C_Dandelions.BASIC_TYPE, Barnarda_C_Dandelions.EnumBlockDandelions.REEDS));
						else
							world.setBlockState(pos.up(size), BRBlocks.BARNARDA_C_DANDELIONS.getDefaultState().withProperty(Barnarda_C_Dandelions.BASIC_TYPE, Barnarda_C_Dandelions.EnumBlockDandelions.REEDS_FRUITS));
					}
				}
				else
					world.setBlockState(pos, BRBlocks.BARNARDA_C_DANDELIONS.getDefaultState().withProperty(Barnarda_C_Dandelions.BASIC_TYPE, Barnarda_C_Dandelions.EnumBlockDandelions.LIGHT_BALLS));
			}
    	}
	}
}
