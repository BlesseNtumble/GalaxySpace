package galaxyspace.systems.SolarSystem.moons.europa.world.gen.we;

import java.util.Random;

import asmodeuscore.core.utils.worldengine.WE_Biome;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_BiomeLayer;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_TerrainGenerator;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_OreGen.BlockPredicate;
import galaxyspace.core.GSBlocks;
import galaxyspace.core.prefab.entities.EntityEvolvedColdBlaze;
import galaxyspace.systems.SolarSystem.moons.enceladus.blocks.EnceladusBlocks.EnumEnceladusBlocks;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockSurfaceIce;
import galaxyspace.systems.SolarSystem.moons.enceladus.blocks.EnceladusCrystal;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedEnderman;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSkeleton;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSpider;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedZombie;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class Europa_Ravine extends WE_Biome {
	
	public Europa_Ravine(WE_TerrainGenerator tg) {
		super(new BiomeProperties("europa_ravine"), new int[] {0x00CC00, 0xFFFFFF, 0x00CC00});
				
		//biomeMinValueOnMap      =  	-0.4D;
		biomeMaxValueOnMap      =   0.0D;
		biomePersistence        =   1.2D;
		biomeNumberOfOctaves    =      4;
		biomeScaleX             = 280.0D;
		biomeScaleY             =   1.7D;
		biomeSurfaceHeight      =     70;
		biomeInterpolateQuality =     2;
		
		biomeAbs = true;
		
		//-//
		decorateChunkGen_List.clear();		
		createChunkGen_InXZ_List.clear();		
		spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEvolvedColdBlaze.class, 10, 1, 4));
		
		WE_BiomeLayer layer = new WE_BiomeLayer();
		layer.add(Blocks.PACKED_ICE.getDefaultState(), tg.worldStoneBlock, -256, 0,   65, -6,  true);
		layer.add(Blocks.PACKED_ICE.getDefaultState(), Blocks.PACKED_ICE.getDefaultState(), -256, 0,   -2, -1,  true);
		layer.add(Blocks.BEDROCK.getDefaultState(), 0, 2, 0, 0, true);
		
		
		createChunkGen_InXZ_List.add(layer);
	}
	
	@Override
	public void decorateBiome(World world, Random rand, int x, int z)
	{
		int randPosX;
		int randPosY;
		int randPosZ;

		for (int i = 0; i < 30; i++) {
			 
	        randPosX = x + rand.nextInt(16);
	        //randPosY = rand.nextInt(120);
	        randPosZ = z + rand.nextInt(16);
	        BlockPos pos = world.getHeight(new BlockPos(randPosX, 0, randPosZ));
	    	if(world.isAreaLoaded(pos, 13, false) && world.isAirBlock(pos.up())) {
	    		new WorldGenMinable(GSBlocks.SURFACE_ICE.getDefaultState().withProperty(BlockSurfaceIce.BASIC_TYPE, BlockSurfaceIce.EnumBlockIce.DRY_ICE), 35,
	    				new BlockPredicate(
	    						Blocks.PACKED_ICE.getDefaultState()
	    				)).generate(world, rand, pos);
	    	}
    	}
		
		for (int i = 0; i < 5; i++) {
    		randPosX = x + rand.nextInt(16) + 8;
	        //randPosY = rand.nextInt(120);
	        randPosZ = z + rand.nextInt(16) + 8;
	        BlockPos pos = world.getHeight(new BlockPos(randPosX, 0, randPosZ));
	        
	        
	        
	        if (world.getBlockState(pos.down()) == Blocks.PACKED_ICE.getDefaultState() && world.isAirBlock(pos))
	        {	        	
	        	//or(BlockPos pos1 : pos.getAllInBox(1, 0, 1, -1, 0, -1))
	        		//world.setBlockState(pos1, Blocks.BEDROCK.getDefaultState(), 2);
	        	
	        	//world.setBlockState(pos.down(), GSBlocks.EUROPA_UWGEYSER.getDefaultState(), 2);	  
	        }
    	}
	}
}
