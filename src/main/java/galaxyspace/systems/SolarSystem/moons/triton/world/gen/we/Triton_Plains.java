package galaxyspace.systems.SolarSystem.moons.triton.world.gen.we;

import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_Biome;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_BiomeLayer;
import galaxyspace.core.prefab.entities.EntityEvolvedColdBlaze;
import galaxyspace.core.registers.blocks.GSBlocks;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedEnderman;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSkeleton;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSpider;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedZombie;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;

public class Triton_Plains extends WE_Biome {
	
	public Triton_Plains(double min, double max, double per, int height, int interpolate) {
		super(new BiomeProperties("triton_plains"), new int[] {0xFFFFFF, 0xFFFFFF, 0x00FF00});
				
		biomeMinValueOnMap      =  	 min;
		biomeMaxValueOnMap      =    max;
		biomePersistence        =    per;
		biomeNumberOfOctaves    =      4;
		biomeScaleX             = 280.0D;
		biomeScaleY             =   1.7D;
		biomeSurfaceHeight      =     height;
		biomeInterpolateQuality =     interpolate;
		
		//-//
		decorateChunkGen_List.clear();		
		createChunkGen_InXZ_List.clear();
		spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEvolvedSpider.class, 10, 1, 2));
		spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEvolvedSkeleton.class, 10, 1, 2));
		spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEvolvedZombie.class, 10, 1, 4));
		spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEvolvedEnderman.class, 10, 1, 2));
		spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEvolvedColdBlaze.class, 10, 1, 4));
		
		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		standardBiomeLayers.add(GSBlocks.TRITON_BLOCKS.getStateFromMeta(1), GSBlocks.TRITON_BLOCKS.getStateFromMeta(2), -256, 0,   -4, -6,  true);
		standardBiomeLayers.add(GSBlocks.TRITON_BLOCKS.getStateFromMeta(0), GSBlocks.TRITON_BLOCKS.getStateFromMeta(1), -256, 0,   -2, -1,  true);
		standardBiomeLayers.add(Blocks.BEDROCK.getDefaultState(), 0, 2, 0, 0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);
	}

}
