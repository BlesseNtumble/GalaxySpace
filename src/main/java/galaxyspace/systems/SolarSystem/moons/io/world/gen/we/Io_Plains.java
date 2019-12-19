package galaxyspace.systems.SolarSystem.moons.io.world.gen.we;

import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_Biome;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_BiomeLayer;
import galaxyspace.core.registers.blocks.GSBlocks;
import net.minecraft.init.Blocks;

public class Io_Plains extends WE_Biome{

	public Io_Plains() {
		super(new BiomeProperties("io_plains"), new int[] {0xFFFFFF, 0xFFFFFF, 0x00FF00});
		
		biomeMinValueOnMap      =   0.0D;
		biomeMaxValueOnMap      =   1.4D;
		biomePersistence        =   1.6D;
		biomeNumberOfOctaves    =      4;
		biomeScaleX             = 180.0D;
		biomeScaleY             =   1.7D;
		biomeSurfaceHeight      =     80;
		biomeInterpolateQuality =     25;
		
		decorateChunkGen_List.clear();
		createChunkGen_InXZ_List.clear();
/*
		WE_LakeGen lavaLakes = new WE_LakeGen();
		lavaLakes.lakeBlock = Blocks.LAVA;
		lavaLakes.fGen      =       false;
		lavaLakes.maxY      =          70;
		decorateChunkGen_List.add(lavaLakes);
			*/
		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		standardBiomeLayers.add(GSBlocks.IO_BLOCKS.getStateFromMeta(0), GSBlocks.IO_BLOCKS.getStateFromMeta(1), -256, 0, -256,  0, false);
		standardBiomeLayers.add(Blocks.BEDROCK.getDefaultState(), 0, 2, 0, 0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);
	}
}
