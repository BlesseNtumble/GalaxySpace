package galaxyspace.systems.SolarSystem.moons.triton.world.gen.we;

import java.util.Random;

import asmodeuscore.core.utils.worldengine.WE_Biome;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_BiomeLayer;
import galaxyspace.core.prefab.entities.EntityEvolvedColdBlaze;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.systems.SolarSystem.moons.triton.blocks.TritonBlocks;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedEnderman;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSkeleton;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSpider;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedZombie;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class Triton_Mountains extends WE_Biome {
	
	public Triton_Mountains(double min, double max) {
		super(new BiomeProperties("triton_mountains"), new int[] {0x00CC00, 0xFFFFFF, 0x00CC00});
				
		biomeMinValueOnMap      =  	min;
		biomeMaxValueOnMap      =   max;
		biomePersistence        =   2.5D;
		biomeNumberOfOctaves    =      4;
		biomeScaleX             = 280.0D;
		biomeScaleY             =   1.7D;
		biomeSurfaceHeight      =    120;
		biomeInterpolateQuality =     25;
		
		//-//
		decorateChunkGen_List.clear();		
		createChunkGen_InXZ_List.clear();
		
		spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEvolvedSpider.class, 10, 1, 2));
		spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEvolvedSkeleton.class, 10, 1, 2));
		spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEvolvedZombie.class, 10, 1, 4));
		spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEvolvedEnderman.class, 10, 1, 2));
		spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEvolvedColdBlaze.class, 10, 1, 4));
		
		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		standardBiomeLayers.add(GSBlocks.TRITON_BLOCKS.getStateFromMeta(1), GSBlocks.TRITON_BLOCKS.getStateFromMeta(2), -256, 0,   -4, -6,  true);
		standardBiomeLayers.add(GSBlocks.TRITON_BLOCKS.getStateFromMeta(0), GSBlocks.TRITON_BLOCKS.getStateFromMeta(1), -256, 0,   -2, -1,  true);
		standardBiomeLayers.add(Blocks.BEDROCK.getDefaultState(), 0, 2, 0, 0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);
	}

	@Override
	public void decorateBiome(World world, Random rand, int x, int z)
	{
		int randPosX;
		int randPosY;
		int randPosZ;
		
		for (int i = 0; i < 1; i++) {
			
			 randPosX = x + rand.nextInt(16) + 8;
			 randPosY = rand.nextInt(70);
			 randPosZ = z + rand.nextInt(16) + 8;
			 
			 BlockPos pos = new BlockPos(randPosX, randPosY, randPosZ);
			 
			 if(world.isAirBlock(pos))
			 {
				 IBlockState state = world.getBlockState(pos);
				 if(world.getBlockState(pos) == GSBlocks.TRITON_BLOCKS.getDefaultState())					 
				 {
					 world.setBlockState(pos, GSBlocks.TRITON_BLOCKS.getDefaultState().withProperty(TritonBlocks.BASIC_TYPE, TritonBlocks.EnumTritonBlocks.TRITON_GEYSER), 3);
					 world.scheduleBlockUpdate(pos, GSBlocks.TRITON_BLOCKS.getDefaultState().withProperty(TritonBlocks.BASIC_TYPE, TritonBlocks.EnumTritonBlocks.TRITON_GEYSER).getBlock(), 0, 0);
				 }
			 }
		}
		
		for (int i = 0; i < 2; i++) {
			
			 randPosX = x + rand.nextInt(16) + 8;
			 randPosY = rand.nextInt(70);
			 randPosZ = z + rand.nextInt(16) + 8;
			 
			 BlockPos pos = new BlockPos(randPosX, randPosY, randPosZ);
			 
			 if(world.isAirBlock(pos.up()) && randPosX % 50 == 0)
			 {
				 IBlockState state = world.getBlockState(pos);
				 if(world.getBlockState(pos) == GSBlocks.TRITON_BLOCKS.getDefaultState())					 
				 {
					 world.setBlockState(pos, GSBlocks.TRITON_BLOCKS.getDefaultState().withProperty(TritonBlocks.BASIC_TYPE, TritonBlocks.EnumTritonBlocks.TRITON_GEYSER_2), 3);
					 world.scheduleBlockUpdate(pos, GSBlocks.TRITON_BLOCKS.getDefaultState().withProperty(TritonBlocks.BASIC_TYPE, TritonBlocks.EnumTritonBlocks.TRITON_GEYSER_2).getBlock(), 0, 0);
						
				 }
			 }
		}
	}
}
