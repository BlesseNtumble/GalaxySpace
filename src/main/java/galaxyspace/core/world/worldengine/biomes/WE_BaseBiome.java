package galaxyspace.core.world.worldengine.biomes;

import cpw.mods.fml.common.IWorldGenerator;
import galaxyspace.core.world.worldengine.WE_Biome;
import galaxyspace.core.world.worldengine.standardcustomgen.WE_BiomeLayer;


public class WE_BaseBiome extends WE_Biome {
	
	
	public WE_BaseBiome(double min, double max, double persistance, int octaves, int height, int intquility, WE_BiomeLayer layers, IWorldGenerator... gens) {
		super(0, false);
				
		biomeMinValueOnMap      =  	min;
		biomeMaxValueOnMap      =   max;
		biomePersistence        =   persistance;
		biomeNumberOfOctaves    =   octaves;
		biomeScaleX             = 	280.0D;
		biomeScaleY             =   1.5D;
		biomeSurfaceHeight      =   height;
		biomeInterpolateQuality =   intquility;
		
		//-//
		decorateChunkGen_List.clear();		
		createChunkGen_InXZ_List.clear();
		spawnableMonsterList.clear();
		spawnableCaveCreatureList.clear();
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		
		createChunkGen_InXZ_List.add(layers);
		
		for(IWorldGenerator gen : gens)
			decorateChunkGen_List.add(gen);
	}	
	
	public WE_BaseBiome addChunkGenXZ()
	{
		return this;
	}
}
