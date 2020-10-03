package galaxyspace.systems.SolarSystem.moons.europa.world.gen.we;

import java.util.Random;

import asmodeuscore.core.utils.worldengine.WE_Biome;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_BiomeLayer;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_TerrainGenerator;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_OreGen.BlockPredicate;
import galaxyspace.GalaxySpace;
import galaxyspace.core.GSBlocks;
import galaxyspace.core.prefab.entities.EntityEvolvedColdBlaze;
import galaxyspace.systems.SolarSystem.moons.enceladus.blocks.EnceladusBlocks.EnumEnceladusBlocks;
import galaxyspace.systems.BarnardsSystem.core.registers.BRBlocks;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Blocks;
import galaxyspace.systems.SolarSystem.moons.enceladus.blocks.EnceladusCrystal;
import galaxyspace.systems.SolarSystem.moons.europa.blocks.EuropaBlocks;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockSurfaceIce;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedEnderman;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSkeleton;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSpider;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedZombie;
import net.minecraft.block.BlockSnow;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class Europa_HillPlains extends WE_Biome {
	
	public Europa_HillPlains(WE_TerrainGenerator tg, double value) {
		super(new BiomeProperties("europa_hillplains"), new int[] {0x00CC00, 0xFFFFFF, 0x00CC00});
				
		//biomeMinValueOnMap      =  	-0.4D;
		biomeMaxValueOnMap      =   value;
		biomePersistence        =   1.9D;
		biomeNumberOfOctaves    =      4;
		biomeScaleX             = 280.0D;
		biomeScaleY             =   1.7D;
		biomeSurfaceHeight      =     140;
		biomeInterpolateQuality =     5;
		
		biomeAbs = true;
		
		//-//
		decorateChunkGen_List.clear();		
		createChunkGen_InXZ_List.clear();		
		spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEvolvedColdBlaze.class, 10, 1, 4));
		
		WE_BiomeLayer layer = new WE_BiomeLayer();
		layer.add(Blocks.PACKED_ICE.getDefaultState(), tg.worldStoneBlock, -256, 0,   65, -6,  true);
		layer.add(GSBlocks.EUROPA_BLOCKS.getStateFromMeta(6), Blocks.PACKED_ICE.getDefaultState(), -256, 0,   -2, -1,  true);
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
	    	if(world.isAreaLoaded(pos, 13, false)) {
	    		new WorldGenMinable(GSBlocks.SURFACE_ICE.getDefaultState().withProperty(BlockSurfaceIce.BASIC_TYPE, BlockSurfaceIce.EnumBlockIce.DRY_ICE), 35,
	    				new BlockPredicate(
	    						GSBlocks.EUROPA_BLOCKS.getStateFromMeta(6)
	    				)).generate(world, rand, pos);
	    	}
    	}
    	for (int i = 0; i < 30; i++) {
    		randPosX = x + rand.nextInt(16) + 8;
	        //randPosY = rand.nextInt(120);
	        randPosZ = z + rand.nextInt(16) + 8;
	        BlockPos pos = world.getHeight(new BlockPos(randPosX, 0, randPosZ));
	        
	        if (world.getBlockState(pos.down()) == GSBlocks.EUROPA_BLOCKS.getStateFromMeta(6) && world.isAirBlock(pos))
	        {	        	
	        	world.setBlockState(pos, Blocks.SNOW_LAYER.getDefaultState(), 2);	  
	        }
    	}
    	
    	for (int i = 0; i < 30; i++) {
    		randPosX = x + rand.nextInt(16);
	        //randPosY = rand.nextInt(120);
	        randPosZ = z + rand.nextInt(16);
	        BlockPos pos = world.getHeight(new BlockPos(randPosX, 0, randPosZ));
	        
	        if(world.isAreaLoaded(pos, 13, false)) {
	    		new WorldGenMinable(GSBlocks.EUROPA_BLOCKS.getStateFromMeta(0), 25,
	    				new BlockPredicate(
	    						GSBlocks.EUROPA_BLOCKS.getStateFromMeta(6)
	    				)).generate(world, rand, pos);
	    	}
    	}
    	
    	
	      /*  if (world.getBlockState(pos.down()) == GSBlocks.ENCELADUS_BLOCKS.getStateFromMeta(EnumEnceladusBlocks.ENCELADUS_GRUNT.getMeta()) && world.isAirBlock(pos))
	        {	        	
	        	world.setBlockState(pos, GSBlocks.ENCELADUS_CRYSTAL.getDefaultState().withProperty(EnceladusCrystal.FACING, EnumFacing.HORIZONTALS[rand.nextInt(4)]));	  
	        }
	        
	        if (world.getBlockState(pos.up()) == GSBlocks.ENCELADUS_BLOCKS.getStateFromMeta(EnumEnceladusBlocks.ENCELADUS_GRUNT.getMeta()) && world.isAirBlock(pos))
	        {	        	
	        	world.setBlockState(pos, GSBlocks.ENCELADUS_CRYSTAL.getDefaultState().withProperty(EnceladusCrystal.FACING, EnumFacing.HORIZONTALS[rand.nextInt(4)]));	  
	        	
	        	
	        }*/
        
		
	}
}
