package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.we;

import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_Biome;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_BiomeLayer;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_SnowGen;
import galaxyspace.systems.BarnardsSystem.core.registers.BRBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;

public class Barnarda_C_Mountains extends WE_Biome {
	
	public Barnarda_C_Mountains(double min, double max, int height, double per, int octaves) {
		super(new BiomeProperties("barnarda_c_mountains_" + height), new int[] {0x55BB44, 0x44FFAA, 0x00FF00});
			
		biomeMinValueOnMap      =  	   min;
		biomeMaxValueOnMap      =      max;
		biomePersistence        =     per;
		biomeNumberOfOctaves    =        octaves;
		biomeScaleX             =   280.0D;
		biomeScaleY             =     1.7D;
		biomeSurfaceHeight      =       height;
		biomeInterpolateQuality =       35;
		
		//-//
		decorateChunkGen_List.clear();		
		createChunkGen_InXZ_List.clear();
		
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEnderman.class, 10, 1, 2));
		
		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		standardBiomeLayers.add(BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(3), BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(1), -256, 0,   -5, -1,  true);
		standardBiomeLayers.add(BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(0), BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(3), -256, 0,   -1, -1,  true);
		standardBiomeLayers.add(BRBlocks.BARNARDA_C_GRASS.getStateFromMeta(0), BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(0), -256, 0, -256,  0, false);
		standardBiomeLayers.add(Blocks.BEDROCK.getDefaultState(),                      0, 2,  0,  0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);
		
		WE_SnowGen snowGen = new WE_SnowGen();
		snowGen.snowPoint       = 120;
		snowGen.randomSnowPoint = 8;
		snowGen.snowBlock       = Blocks.SNOW.getDefaultState();
		snowGen.iceBlock        = Blocks.ICE.getDefaultState();
		snowGen.freezeMaterial  = Material.WATER;
		createChunkGen_InXZ_List.add(snowGen);
	}
}
