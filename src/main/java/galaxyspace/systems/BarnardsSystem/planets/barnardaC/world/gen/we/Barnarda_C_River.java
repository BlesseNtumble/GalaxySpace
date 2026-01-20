package galaxyspace.systems.BarnardsSystem.planets.barnardaC.world.gen.we;

import galaxyspace.core.world.worldengine.WE_Biome;
import galaxyspace.core.world.worldengine.standardcustomgen.WE_BiomeLayer;
import galaxyspace.systems.BarnardsSystem.core.registers.blocks.BRBlocks;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.init.Blocks;

public class Barnarda_C_River extends WE_Biome {
	
	public Barnarda_C_River(double min, double max) {
		super(0, false);
				
		biomeMinValueOnMap      =  	min;
		biomeMaxValueOnMap      =   max;
		biomePersistence        =   1.1D;
		biomeNumberOfOctaves    =      4;
		biomeScaleX             = 280.0D;
		biomeScaleY             =   1.7D;
		biomeSurfaceHeight      =     55;
		biomeInterpolateQuality =     30;
		
		biomeGrassColor = 0x8E0F76;
		//-//
		decorateChunkGen_List.clear();		
		createChunkGen_InXZ_List.clear();

		this.spawnableWaterCreatureList.add(new SpawnListEntry(EntitySquid.class, 10, 1, 4));
		
		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		standardBiomeLayers.add(BRBlocks.BarnardaCBlocks, (byte)0, BRBlocks.BarnardaCBlocks, (byte)1, -256, 0,   -20, -3,  true);
		standardBiomeLayers.add(BRBlocks.BarnardaCGrass, (byte)0, BRBlocks.BarnardaCBlocks, (byte)0, -256, 0, -256,  0, false);
		standardBiomeLayers.add(Blocks.bedrock, (byte)0, 0, 2, 0, 0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);
	}
}
