package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.we;

import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_Biome;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_BiomeLayer;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_LakeGen;
import galaxyspace.core.prefab.world.gen.we.WE_LakesGen;
import galaxyspace.systems.BarnardsSystem.core.registers.blocks.BRBlocks;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;

public class Barnarda_C_Forest extends WE_Biome {
	
	public Barnarda_C_Forest(double min, double max) {
		super(new BiomeProperties("barnarda_c_forest"));
				
		biomeMinValueOnMap      =  	min;
		biomeMaxValueOnMap      =   max;
		biomePersistence        =   1.8D;
		biomeNumberOfOctaves    =      4;
		biomeScaleX             = 280.0D;
		biomeScaleY             =   1.7D;
		biomeSurfaceHeight      =     75;
		biomeInterpolateQuality =     30;
		biomeBlockGrassColor    = 0x89AC76;
		biomeBlockWaterColor 	= 0x11FF66;
		
		//-//
		decorateChunkGen_List.clear();		
		createChunkGen_InXZ_List.clear();
		
		//this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEnderman.class, 10, 1, 4));
		
		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		standardBiomeLayers.add(BRBlocks.BARNARDA_C_BLOCKS, (byte)3, BRBlocks.BARNARDA_C_BLOCKS, (byte)1, -256, 0,   -5, -1,  true);
		standardBiomeLayers.add(BRBlocks.BARNARDA_C_BLOCKS, (byte)0, BRBlocks.BARNARDA_C_BLOCKS, (byte)3, -256, 0,   -1, -1,  true);
		standardBiomeLayers.add(BRBlocks.BARNARDA_C_GRASS, (byte)0, BRBlocks.BARNARDA_C_BLOCKS, (byte)0, -256, 0, -256,  0, false);
		
		standardBiomeLayers.add(Blocks.BEDROCK, (byte)0,                      0, 2,  0,  0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);
		
		WE_LakesGen lakes = new WE_LakesGen();
		lakes.lakeBlock = Blocks.WATER.getDefaultState();
		lakes.iceGen = false;
		lakes.chunksForLake = 6;
		decorateChunkGen_List.add(lakes);
		
		lakes = new WE_LakesGen();
		lakes.lakeBlock = Blocks.LAVA.getDefaultState();
		lakes.iceGen = false;
		lakes.chunksForLake = 100;
		decorateChunkGen_List.add(lakes);
	}
}

