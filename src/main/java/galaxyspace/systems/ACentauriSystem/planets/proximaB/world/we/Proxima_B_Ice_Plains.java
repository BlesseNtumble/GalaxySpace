package galaxyspace.systems.ACentauriSystem.planets.proximaB.world.we;

import galaxyspace.core.world.worldengine.WE_Biome;
import galaxyspace.core.world.worldengine.WE_ChunkProvider;
import galaxyspace.core.world.worldengine.standardcustomgen.WE_BiomeLayer;
import galaxyspace.systems.ACentauriSystem.core.registers.blocks.ACBlocks;
import galaxyspace.systems.SolarSystem.moons.europa.entities.EntityEvolvedColdBlaze;
import net.minecraft.init.Blocks;

public class Proxima_B_Ice_Plains extends WE_Biome {

	public Proxima_B_Ice_Plains(WE_ChunkProvider chunk) {
		super(0, false);
		this.setBiomeName("proxima_b_ice_plains");
		
		biomeMinValueOnMap      =   0.6D;
		biomeMaxValueOnMap      =   1.2D;
		biomePersistence        =   1.2D;
		biomeNumberOfOctaves    =      4;
		biomeScaleX             = 280.0D;
		biomeScaleY             =   1.7D;
		biomeSurfaceHeight      =     68;
		biomeInterpolateQuality =      5;
		waterColorMultiplier 	= 0xEEDD44;
		//-//
		decorateChunkGen_List.clear();		
		createChunkGen_InXZ_List.clear();
		
		for(SpawnListEntry mob : this.getMonsters())
			spawnableMonsterList.add(mob);
		
		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		standardBiomeLayers.add(ACBlocks.ProximaBBlocks, (byte)1, ACBlocks.ProximaBBlocks, (byte)2, -256, 0,   -4, -1,  true);
		standardBiomeLayers.add(ACBlocks.ProximaBBlocks, (byte)3, ACBlocks.ProximaBBlocks, (byte)1, -256, 0, -256,  0, false);
		standardBiomeLayers.add(Blocks.bedrock, (byte)0,                                0, 2,  0,  0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);
	}
	
	protected SpawnListEntry[] getMonsters() {	
	    SpawnListEntry blaze = new SpawnListEntry(EntityEvolvedColdBlaze.class, 100, 4, 4);
	    
	    return new SpawnListEntry[]{blaze};
	}
}