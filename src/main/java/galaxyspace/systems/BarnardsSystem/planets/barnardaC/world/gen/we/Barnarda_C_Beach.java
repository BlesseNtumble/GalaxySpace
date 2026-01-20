package galaxyspace.systems.BarnardsSystem.planets.barnardaC.world.gen.we;

import galaxyspace.core.world.worldengine.WE_Biome;
import galaxyspace.core.world.worldengine.standardcustomgen.WE_BiomeLayer;
import galaxyspace.systems.BarnardsSystem.core.registers.blocks.BRBlocks;
import net.minecraft.init.Blocks;

public class Barnarda_C_Beach extends WE_Biome {

	public Barnarda_C_Beach(double min, double max, int tier) {
		super(0, false);
		
		biomeMinValueOnMap      =   min;
		biomeMaxValueOnMap      =   max;
		biomePersistence        =   1.3D;
		biomeNumberOfOctaves    =      3;
		biomeScaleX             = 280.0D;
		biomeScaleY             =   1.7D;
		biomeSurfaceHeight      =     68;
		biomeInterpolateQuality =     20;
		
		//biomeGrassColor = 0x89AC76;
		biomeGrassColor = 0x44AC76;
				
		//-//
		decorateChunkGen_List.clear();		
		createChunkGen_InXZ_List.clear();
		
		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		standardBiomeLayers.add(Blocks.sandstone, (byte)0, BRBlocks.BarnardaCBlocks, (byte)1, -256, 0,   -4, -1,  true);
		standardBiomeLayers.add(BRBlocks.BarnardaCFallingBlocks, (byte)0, Blocks.sandstone, (byte)0, -256, 0, -256,  0, false);
		standardBiomeLayers.add(Blocks.bedrock, (byte)0, 0, 2, 0, 0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);
	}

}
