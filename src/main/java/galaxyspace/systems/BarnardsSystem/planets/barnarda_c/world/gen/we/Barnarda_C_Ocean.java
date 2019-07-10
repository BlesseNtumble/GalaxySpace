package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.we;

import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_Biome;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_BiomeLayer;
import galaxyspace.systems.BarnardsSystem.core.registers.BRBlocks;
import net.minecraft.init.Blocks;

public class Barnarda_C_Ocean extends WE_Biome {

	public Barnarda_C_Ocean(double min, double max, boolean frozen) {		
		super(new BiomeProperties("barnarda_c_ocean" + (frozen ? "_frozen" : "")));
		
		biomeMinValueOnMap      =   min;
		biomeMaxValueOnMap      =   max;
		biomePersistence        =   1.4D;
		biomeNumberOfOctaves    =      4;
		biomeScaleX             = 280.0D;
		biomeScaleY             =   1.7D;
		biomeSurfaceHeight      =     55;
		biomeInterpolateQuality =     65;
		biomeBlockGrassColor    = 0x00FF00;
		biomeBlockWaterColor 	= 0x11FF66;
				
		//-//
		decorateChunkGen_List.clear();		
		createChunkGen_InXZ_List.clear();
		
		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		standardBiomeLayers.add(BRBlocks.BARNARDA_C_BLOCKS, (byte)0, BRBlocks.BARNARDA_C_BLOCKS, (byte)1, -256, 0,   -5, -1,  true);
		standardBiomeLayers.add(BRBlocks.BARNARDA_C_BLOCKS, (byte)3, BRBlocks.BARNARDA_C_BLOCKS, (byte)0, -256, 0,   -1, -1,  true);
		standardBiomeLayers.add(BRBlocks.BARNARDA_C_GRASS, (byte)0, BRBlocks.BARNARDA_C_BLOCKS, (byte)3, -256, 0, -256,  0, false);
		
		if(frozen)
			standardBiomeLayers.add(Blocks.ICE, (byte)0, 		-256, 0,   -1, -1,  true);
		
		standardBiomeLayers.add(Blocks.BEDROCK, (byte)0,		0, 2,  0,  0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);
	}
}
