package galaxyspace.systems.SolarSystem.moons.titan.world.gen.we;

import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.world.worldengine.WE_Biome;
import galaxyspace.core.world.worldengine.standardcustomgen.WE_BiomeLayer;
import net.minecraft.init.Blocks;

public class WE_TitanOcean extends WE_Biome{

	public WE_TitanOcean() {
		super(0, false);
		this.setBiomeName("TitanOcean");
		
		biomeMinValueOnMap      =  -1.0D;
		biomeMaxValueOnMap      =  -0.6D;
		biomePersistence        =   1.5D;
		biomeNumberOfOctaves    =      4;
		biomeScaleX             = 140.0D;
		biomeScaleY             =   2.0D;
		biomeSurfaceHeight      =     48;
		biomeInterpolateQuality =      8;
		
		decorateChunkGen_List.clear();
		createChunkGen_InXZ_List.clear();
		
		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		standardBiomeLayers.add(GSBlocks.TitanBlocks   , (byte)1, GSBlocks.TitanBlocks, (byte)2, -256, 0,   -4, -2,  true);
		//standardBiomeLayers.add(GSBlocks.TitanBlocks , (byte)0, GSBlocks.TitanBlocks , (byte)1, -256, 0, -256,  0, true);
		standardBiomeLayers.add(Blocks.packed_ice, (byte)0,                                3, 6,  -3,  -2, true);
		standardBiomeLayers.add(Blocks.bedrock, (byte)0,                                0, 2,  0,  0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);		
		
	}
}
