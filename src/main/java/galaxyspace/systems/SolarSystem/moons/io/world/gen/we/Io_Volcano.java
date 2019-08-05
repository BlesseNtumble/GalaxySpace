package galaxyspace.systems.SolarSystem.moons.io.world.gen.we;

import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_Biome;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_BiomeLayer;
import galaxyspace.core.registers.blocks.GSBlocks;
import net.minecraft.init.Blocks;

public class Io_Volcano extends WE_Biome {
	
	public Io_Volcano() {
		super(new BiomeProperties("io_volcano"), new int[] {0xFFFFFF, 0xFFFFFF, 0x00FF00});
		
		biomeMinValueOnMap      =   0.5D;
		biomeMaxValueOnMap      =   1.0D;
		biomePersistence        =   1.1D;
		biomeNumberOfOctaves    =      4;
		biomeScaleX             = 180.0D;
		biomeScaleY             =   1.9D;
		biomeSurfaceHeight      =     95;
		biomeInterpolateQuality =     35;
				
		decorateChunkGen_List.clear();
		createChunkGen_InXZ_List.clear();

		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();		
		standardBiomeLayers.add(GSBlocks.IO_BLOCKS, (byte)2, GSBlocks.IO_BLOCKS, (byte)1, -256, 0, -256,  0, false);
		standardBiomeLayers.add(Blocks.BEDROCK, (byte)0,                                0, 2,  0,  0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);

	}
}
