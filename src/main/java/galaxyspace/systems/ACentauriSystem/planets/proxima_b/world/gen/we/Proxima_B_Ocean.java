package galaxyspace.systems.ACentauriSystem.planets.proxima_b.world.gen.we;

import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_Biome;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_BiomeLayer;
import galaxyspace.systems.ACentauriSystem.core.registers.blocks.ACBlocks;
import net.minecraft.init.Blocks;

public class Proxima_B_Ocean extends WE_Biome {

	public Proxima_B_Ocean() {
		super(new BiomeProperties("proxima_b_ocean"), new int[] {0x00FF00, 0xEEDD44, 0x00FF00});
		
		biomeMinValueOnMap      =   -0.6D;
		biomeMaxValueOnMap      =   -0.4D;
		biomePersistence        =   1.4D;
		biomeNumberOfOctaves    =      4;
		biomeScaleX             = 280.0D;
		biomeScaleY             =   1.7D;
		biomeSurfaceHeight      =     55;
		biomeInterpolateQuality =     15;
		
		//-//
		decorateChunkGen_List.clear();		
		createChunkGen_InXZ_List.clear();
		
		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		standardBiomeLayers.add(ACBlocks.PROXIMA_B_BLOCKS.getStateFromMeta(1), ACBlocks.PROXIMA_B_BLOCKS.getStateFromMeta(2), -256, 0,   -4, -1,  true);
		standardBiomeLayers.add(ACBlocks.PROXIMA_B_BLOCKS.getStateFromMeta(0), ACBlocks.PROXIMA_B_BLOCKS.getStateFromMeta(1), -256, 0, -256,  0, false);
		standardBiomeLayers.add(Blocks.BEDROCK.getDefaultState(), 0, 2, 0, 0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);
	}
}
