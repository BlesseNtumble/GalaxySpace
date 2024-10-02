package galaxyspace.systems.SolarSystem.moons.titan.world.gen.we;

import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.world.worldengine.WE_Biome;
import galaxyspace.core.world.worldengine.WE_ChunkProvider;
import galaxyspace.core.world.worldengine.standardcustomgen.WE_BiomeLayer;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedCreeper;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSkeleton;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSpider;
import net.minecraft.init.Blocks;

public class WE_TitanHills extends WE_Biome{

	public WE_TitanHills(WE_ChunkProvider chunk) {
		super(0, false);
		this.setBiomeName("TitanHills");
		
		biomeMinValueOnMap      =  0.2D;
		biomeMaxValueOnMap      =  0.6D;
		biomePersistence        =   1.5D;
		biomeNumberOfOctaves    =      5;
		biomeScaleX             = 140.0D;
		biomeScaleY             =   2.0D;
		biomeSurfaceHeight      =     104;
		biomeInterpolateQuality =      8;
		
		decorateChunkGen_List.clear();
		createChunkGen_InXZ_List.clear();

		for(SpawnListEntry mob : this.getMonsters())
			spawnableMonsterList.add(mob);
		
		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		//standardBiomeLayers.add(GSBlocks.TitanBlocks   , (byte)1, GSBlocks.TitanBlocks, (byte)2, -256, 0,   -4, -2,  true);
		standardBiomeLayers.add(GSBlocks.TitanBlocks , (byte)0, GSBlocks.TitanBlocks , (byte)2, -256, 0, -256,  0, false);
		standardBiomeLayers.add(Blocks.packed_ice, (byte)0,                                3, 6,  -3,  -2, true);
		standardBiomeLayers.add(Blocks.bedrock, (byte)0,                                0, 2,  0,  0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);		
		
	}
	
	protected SpawnListEntry[] getMonsters() {	
	    SpawnListEntry skele = new SpawnListEntry(EntityEvolvedSkeleton.class, 100, 4, 4);
	    SpawnListEntry creeper = new SpawnListEntry(EntityEvolvedCreeper.class, 100, 4, 4);
	    SpawnListEntry spider = new SpawnListEntry(EntityEvolvedSpider.class, 100, 4, 4);
	    
	    return new SpawnListEntry[]{skele, creeper, spider};
	}
}
