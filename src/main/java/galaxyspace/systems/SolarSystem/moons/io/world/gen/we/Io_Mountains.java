package galaxyspace.systems.SolarSystem.moons.io.world.gen.we;

import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_Biome;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_BiomeLayer;
import galaxyspace.core.registers.blocks.GSBlocks;
import net.minecraft.init.Blocks;

public class Io_Mountains extends WE_Biome {
	public Io_Mountains(int index, double min, double max, int y, int interpolate) {
		super(new BiomeProperties("io_mountains_" + index), new int[] {0xFFFFFF, 0xFFFFFF, 0x00FF00});
		
		biomeMinValueOnMap      =  min;
		biomeMaxValueOnMap      =  max;
		biomePersistence        =  1.1D;
		biomeNumberOfOctaves    =     4;
		biomeScaleX             = 180.0D;
		biomeScaleY             =   1.8D;
		biomeSurfaceHeight      =    y;
		biomeInterpolateQuality =    interpolate;
				
		decorateChunkGen_List.clear();
		createChunkGen_InXZ_List.clear();

		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();		
		standardBiomeLayers.add(GSBlocks.IO_BLOCKS, (byte)0, GSBlocks.IO_BLOCKS, (byte)1, -256, 0, -256,  0, false);
		standardBiomeLayers.add(Blocks.BEDROCK, (byte)0,                                0, 2,  0,  0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);

	}
}
