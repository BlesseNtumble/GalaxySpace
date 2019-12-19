package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.we;

import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_Biome;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_BiomeLayer;
import galaxyspace.systems.BarnardsSystem.core.registers.BRBlocks;
import net.minecraft.init.Blocks;

public class Barnarda_C_Beach extends WE_Biome {

	public Barnarda_C_Beach(double min, double max, int tier) {
		super(new BiomeProperties("barnarda_c_beach" + tier), new int[] {0x89AC76, 0x11FF66, 0x00FF00});
		
		biomeMinValueOnMap      =   min;
		biomeMaxValueOnMap      =   max;
		biomePersistence        =   1.3D;
		biomeNumberOfOctaves    =      3;
		biomeScaleX             = 280.0D;
		biomeScaleY             =   1.7D;
		biomeSurfaceHeight      =     68;
		biomeInterpolateQuality =     20;
				
		//-//
		decorateChunkGen_List.clear();		
		createChunkGen_InXZ_List.clear();
		
		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		standardBiomeLayers.add(Blocks.SANDSTONE.getDefaultState(), BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(1), -256, 0,   -4, -1,  true);
		standardBiomeLayers.add(BRBlocks.BARNARDA_C_FALLING_BLOCKS.getStateFromMeta(0), Blocks.SANDSTONE.getDefaultState(), -256, 0, -256,  0, false);
		standardBiomeLayers.add(Blocks.BEDROCK.getDefaultState(), 0, 2, 0, 0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);
	}

}
