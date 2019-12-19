package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.we;

import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_Biome;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_BiomeLayer;
import galaxyspace.systems.BarnardsSystem.core.registers.BRBlocks;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;

public class Barnarda_C_River extends WE_Biome {
	
	public Barnarda_C_River(double min, double max) {
		super(new BiomeProperties("barnarda_c_river"), new int[] {0x88BB44, 0x11FF66, 0x00FF00});
				
		biomeMinValueOnMap      =  	min;
		biomeMaxValueOnMap      =   max;
		biomePersistence        =   1.1D;
		biomeNumberOfOctaves    =      4;
		biomeScaleX             = 280.0D;
		biomeScaleY             =   1.7D;
		biomeSurfaceHeight      =     50;
		biomeInterpolateQuality =     30;
		
		//-//
		decorateChunkGen_List.clear();		
		createChunkGen_InXZ_List.clear();

		this.watercreatures.add(new Biome.SpawnListEntry(EntitySquid.class, 10, 1, 4));
		
		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		standardBiomeLayers.add(BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(3), BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(1), -256, 0,   -5, -1,  true);
		standardBiomeLayers.add(BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(0), BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(3), -256, 0,   -1, -1,  true);
		standardBiomeLayers.add(BRBlocks.BARNARDA_C_GRASS.getStateFromMeta(0), BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(0), -256, 0, -256,  0, false);
		standardBiomeLayers.add(Blocks.BEDROCK.getDefaultState(), 0, 2, 0, 0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);
	}
}
