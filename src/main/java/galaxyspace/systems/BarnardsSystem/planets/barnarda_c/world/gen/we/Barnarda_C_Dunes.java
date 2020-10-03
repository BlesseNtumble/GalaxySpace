package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.we;

import java.util.Random;

import asmodeuscore.core.utils.worldengine.WE_Biome;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_BiomeLayer;
import galaxyspace.systems.BarnardsSystem.core.registers.BRBlocks;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Dandelions;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Falling_Blocks;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class Barnarda_C_Dunes extends WE_Biome {

	public Barnarda_C_Dunes(double min, double max) {
		super(new BiomeProperties("barnarda_c_dunes"), new int[] {0x89AC76, 0x11FF66, 0x985cff});
		
		biomeMinValueOnMap      =   min;
		biomeMaxValueOnMap      =   max;
		biomePersistence        =   1.8D;
		biomeNumberOfOctaves    =      4;
		biomeScaleX             = 280.0D;
		biomeScaleY             =   1.7D;
		biomeSurfaceHeight      =     72;
		biomeInterpolateQuality =     15;		
		biomeTemerature = 0.8F;
		//-//
		decorateChunkGen_List.clear();		
		createChunkGen_InXZ_List.clear();
		
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityHusk.class, 10, 1, 3));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySpider.class, 10, 1, 1));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySkeleton.class, 10, 1, 2));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEnderman.class, 10, 1, 1));
		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		//standardBiomeLayers.add(Blocks.SANDSTONE, (byte)0, BRBlocks.BARNARDA_C_BLOCKS, (byte)1, -256, 0,   -4, -1,  true);
		standardBiomeLayers.add(BRBlocks.BARNARDA_C_FALLING_BLOCKS.getStateFromMeta(0), BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(1), -256, 0, -256,  0, false);
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
		int range = 2;
		for(BlockPos pos1 : pos.getAllInBox(pos.add(-range, -range, -range), pos.add(range, range, range))) 
			if(world.getBlockState(pos1) == Blocks.CACTUS.getDefaultState()) 
				cangen = false; 
		
		for(BlockPos pos1 : pos.getAllInBox(pos.add(-range, 0, -range), pos.add(range, 0, range))) 
			if(world.getBlockState(pos1) != Blocks.AIR.getDefaultState())
				cangen = false;
		
		if(cangen && world.getBlockState(pos.down()) == BRBlocks.BARNARDA_C_FALLING_BLOCKS.getDefaultState().withProperty(Barnarda_C_Falling_Blocks.BASIC_TYPE, Barnarda_C_Falling_Blocks.EnumFallingBlockBarnardaC.SAND)) {
			if(rand.nextInt(2) == 0)
			{
				for(int size = 0; size < 1 + rand.nextInt(3); size++)
				{
					world.setBlockState(pos.up(size), Blocks.CACTUS.getDefaultState());
				}
			}
			else {
				world.setBlockState(pos, BRBlocks.BARNARDA_C_DANDELIONS.getDefaultState().withProperty(Barnarda_C_Dandelions.BASIC_TYPE, Barnarda_C_Dandelions.EnumBlockDandelions.DESERT_DOWN));
				if(rand.nextInt(3) == 0)
					world.setBlockState(pos.up(), BRBlocks.BARNARDA_C_DANDELIONS.getDefaultState().withProperty(Barnarda_C_Dandelions.BASIC_TYPE, Barnarda_C_Dandelions.EnumBlockDandelions.DESERT_UP));
			}
		}
	}
	
}
