package galaxyspace.systems.SolarSystem.planets.mars.world.gen.we;

import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_Biome;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_BiomeLayer;
import galaxyspace.core.prefab.entities.EntityEvolvedColdBlaze;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedEnderman;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSkeleton;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSpider;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedZombie;
import micdoodle8.mods.galacticraft.planets.mars.blocks.MarsBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;

public class Mars_Mountains extends WE_Biome {
	
	public Mars_Mountains(double min, double max) {
		super(new BiomeProperties("mars_mountains"), new int[] {0xFFFFFF, 0xFFFFFF, 0x00FF00});
				
		biomeMinValueOnMap      =  	min;
		biomeMaxValueOnMap      =   max;
		biomePersistence        =   1.2D;
		biomeNumberOfOctaves    =      4;
		biomeScaleX             = 280.0D;
		biomeScaleY             =   1.7D;
		biomeSurfaceHeight      =     140;
		biomeInterpolateQuality =     2;
		
		//-//
		decorateChunkGen_List.clear();		
		createChunkGen_InXZ_List.clear();
		mobs.clear();
		
		mobs.add(new Biome.SpawnListEntry(EntityEvolvedSpider.class, 10, 1, 2));
		mobs.add(new Biome.SpawnListEntry(EntityEvolvedSkeleton.class, 10, 1, 2));
		mobs.add(new Biome.SpawnListEntry(EntityEvolvedZombie.class, 10, 1, 4));
		mobs.add(new Biome.SpawnListEntry(EntityEvolvedEnderman.class, 10, 1, 2));
		mobs.add(new Biome.SpawnListEntry(EntityEvolvedColdBlaze.class, 10, 1, 4));
		
		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		standardBiomeLayers.add(MarsBlocks.marsBlock.getStateFromMeta(6), MarsBlocks.marsBlock.getStateFromMeta(9), -256, 0,   -4, -6,  true);
		standardBiomeLayers.add(MarsBlocks.marsBlock.getStateFromMeta(5), MarsBlocks.marsBlock.getStateFromMeta(6), -256, 0,   -2, -1,  true);
		standardBiomeLayers.add(Blocks.BEDROCK.getDefaultState(), 0, 2, 0, 0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);
	}
}
