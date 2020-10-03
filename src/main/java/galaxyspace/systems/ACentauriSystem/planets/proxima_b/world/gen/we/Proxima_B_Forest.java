package galaxyspace.systems.ACentauriSystem.planets.proxima_b.world.gen.we;

import java.util.Random;

import asmodeuscore.core.astronomy.dimension.world.gen.features.trees.WorldGenTree_BigJungle;
import asmodeuscore.core.astronomy.dimension.world.gen.features.trees.WorldGenTree_BigJungle2;
import asmodeuscore.core.astronomy.dimension.world.gen.features.trees.WorldGenTree_Forest;
import asmodeuscore.core.astronomy.dimension.world.gen.features.trees.WorldGenTree_Forest2;
import asmodeuscore.core.utils.worldengine.WE_Biome;
import asmodeuscore.core.utils.worldengine.WE_ChunkProvider;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_BiomeLayer;
import galaxyspace.core.prefab.world.gen.WorldGenPool;
import galaxyspace.systems.ACentauriSystem.core.registers.blocks.ACBlocks;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.blocks.Proxima_B_Blocks;
import micdoodle8.mods.galacticraft.core.GCFluids;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedEnderman;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSkeleton;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSpider;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedZombie;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class Proxima_B_Forest extends WE_Biome {

	public Proxima_B_Forest(WE_ChunkProvider chunk) {
		super(new BiomeProperties("proxima_b_forest"), new int[] {0x00FF00, 0xEEDD44, 0x00FF00});
		
		biomeMinValueOnMap      =  	-0.2D;
		biomeMaxValueOnMap      =   0.4D;
		biomePersistence        =   1.6D;
		biomeNumberOfOctaves    =      4;
		biomeScaleX             = 280.0D;
		biomeScaleY             =   1.7D;
		biomeSurfaceHeight      =     90;
		biomeInterpolateQuality =     15;
		
		//-//
		decorateChunkGen_List.clear();		
		createChunkGen_InXZ_List.clear();

		spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEvolvedSpider.class, 10, 1, 2));
		spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEvolvedSkeleton.class, 10, 1, 2));
		spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEvolvedZombie.class, 10, 1, 4));
		spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEvolvedEnderman.class, 10, 1, 2));
		
		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		standardBiomeLayers.add(ACBlocks.PROXIMA_B_BLOCKS.getStateFromMeta(1), ACBlocks.PROXIMA_B_BLOCKS.getStateFromMeta(2), -256, 0,   -4, -1,  true);
		standardBiomeLayers.add(ACBlocks.PROXIMA_B_BLOCKS.getStateFromMeta(0), ACBlocks.PROXIMA_B_BLOCKS.getStateFromMeta(1), -256, 0, -256,  0, false);
		standardBiomeLayers.add(Blocks.BEDROCK.getDefaultState(), 0, 2, 0, 0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);
	}

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
			if(cangen && world.getBlockState(pos.down()) == ACBlocks.PROXIMA_B_BLOCKS.getDefaultState().withProperty(Proxima_B_Blocks.BASIC_TYPE, Proxima_B_Blocks.EnumBlockProximaB.SURFACE))
			{
				switch(rand.nextInt(2))
				{
					case 0:
						new WorldGenTree_BigJungle(ACBlocks.PROXINA_B_LOG_1.getStateFromMeta(0), Blocks.AIR.getDefaultState(), rand.nextInt(3)).generate(world, rand, pos);
				    	break;
					case 1:
						new WorldGenTree_BigJungle2(ACBlocks.PROXINA_B_LOG_1.getStateFromMeta(0), Blocks.AIR.getDefaultState(), rand.nextInt(3)).generate(world, rand, pos);
						break;
				}
				//new WorldGenTree_BigJungle(ACBlocks.PROXINA_B_LOG_1.getStateFromMeta(0), Blocks.AIR.getDefaultState(), rand.nextInt(3)).generate(world, rand, pos);
			}

		for(int i = 0; i < 8; i++){
			randPosX = x + rand.nextInt(16) + 8;
			randPosZ = z + rand.nextInt(16) + 8;
			pos = world.getHeight(new BlockPos(randPosX, 0, randPosZ));
			
			cangen = true;
			for(BlockPos pos1 : pos.getAllInBox(pos.add(-3, -1, -3), pos.add(3, -1, 3)))
				if(world.isAirBlock(pos1) || world.getBlockState(pos1) == ACBlocks.PROXINA_B_LOG_1.getStateFromMeta(0)) 
					cangen = false;
			
    		if(!world.isAreaLoaded(pos, 13, false))
	    		if(cangen && world.getBlockState(pos.down()) == ACBlocks.PROXIMA_B_BLOCKS.getDefaultState().withProperty(Proxima_B_Blocks.BASIC_TYPE, Proxima_B_Blocks.EnumBlockProximaB.SURFACE))
	    		{
	    			switch(rand.nextInt(2))
					{
						case 0:						
							new WorldGenTree_Forest(ACBlocks.PROXINA_B_LOG_1.getStateFromMeta(0), Blocks.AIR.getDefaultState(), rand.nextInt(3)).generate(world, rand, pos);
							break;
						case 1:
							new WorldGenTree_Forest2(ACBlocks.PROXINA_B_LOG_1.getStateFromMeta(0), Blocks.AIR.getDefaultState(), rand.nextInt(3)).generate(world, rand, pos);
							break;
					}
				}
		}
		
		for(int i = 0; i < 3; i++){
    		randPosX = x + rand.nextInt(16) + 8;
			//randPosY = this.rand.nextInt(256);
			randPosZ = z + rand.nextInt(16) + 8;
			pos = world.getHeight(new BlockPos(randPosX, 0, randPosZ));
			
			if(world.getBlockState(pos.down()) == ACBlocks.PROXIMA_B_BLOCKS.getDefaultState().withProperty(Proxima_B_Blocks.BASIC_TYPE, Proxima_B_Blocks.EnumBlockProximaB.SURFACE))
    			world.setBlockState(pos, Blocks.DEADBUSH.getDefaultState());
    	}
    	
    	int kx = x + rand.nextInt(16) + 8;
		int kz = x + rand.nextInt(16) + 8;    
		
    	if (rand.nextInt(50) == 0)
        {
    		int l2 = world.getHeight(new BlockPos(kx, 0, kz)).getY() - 20 - rand.nextInt(25);
    		new WorldGenPool(ACBlocks.PROXIMA_B_BLOCKS.getDefaultState().withProperty(Proxima_B_Blocks.BASIC_TYPE, Proxima_B_Blocks.EnumBlockProximaB.STONE), GCFluids.fluidOil.getBlock().getDefaultState(), 5).generate(world, rand, new BlockPos(x, l2, z));
        }
	}
}
