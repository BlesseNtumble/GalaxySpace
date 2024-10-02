package galaxyspace.systems.SolarSystem.moons.europa.world.gen.we;

import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.world.worldengine.WE_Biome;
import galaxyspace.core.world.worldengine.standardcustomgen.WE_BiomeLayer;
import net.minecraft.init.Blocks;

public class WE_EuropaBiome extends WE_Biome{

	public WE_EuropaBiome() {
		super(0, false);
		this.setBiomeName("Europa");
		
		biomeMinValueOnMap      = -0.05D;
		biomeMaxValueOnMap      =  0.28D;
		biomePersistence        =   1.2D;
		biomeNumberOfOctaves    =      4;
		biomeScaleX             = 140.0D;
		biomeScaleY             =   2.0D;
		biomeSurfaceHeight      =     66;
		biomeInterpolateQuality =      8;
		
		decorateChunkGen_List.clear();
		createChunkGen_InXZ_List.clear();
		
		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		
		
		standardBiomeLayers.add(Blocks.packed_ice, (byte)0, GSBlocks.EuropaBlocks, (byte)1, -256, 0,   -4, -2,  true);
		standardBiomeLayers.add(Blocks.water, (byte)0, GSBlocks.EuropaBlocks , (byte)1, -256, 0, 0,  0, false);
		standardBiomeLayers.add(GSBlocks.EuropaBlocks, (byte)1, Blocks.packed_ice , (byte)0, -256, 0, -256,  0, false);
		
		//standardBiomeLayers.add(Blocks.snow_layer, (byte)0, 					 	-256, 0, -256,  0, false);
		
		
		standardBiomeLayers.add(Blocks.packed_ice, (byte)0,                                2, 4,  2,  0, true);
		standardBiomeLayers.add(Blocks.bedrock, (byte)0,                                0, 2,  0,  0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);		
		
	}

}
