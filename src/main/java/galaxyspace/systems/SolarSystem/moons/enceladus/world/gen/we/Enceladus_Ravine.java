package galaxyspace.systems.SolarSystem.moons.enceladus.world.gen.we;

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

public class Enceladus_Ravine extends WE_Biome {
	
	public Enceladus_Ravine() {
		super(new BiomeProperties("enceladus_ravine"), new int[] {0xFFFFFF, 0xFFFFFF, 0x00FF00});
				
		biomeMinValueOnMap      =  	-4.0D;
		biomeMaxValueOnMap      =   -4.4D;
		biomePersistence        =   1.0D;
		biomeNumberOfOctaves    =      4;
		biomeScaleX             = 280.0D;
		biomeScaleY             =   1.7D;
		biomeSurfaceHeight      =     60;
		biomeInterpolateQuality =     5;
		
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
		standardBiomeLayers.add(Blocks.PACKED_ICE.getDefaultState(), GSBlocks.ENCELADUS_BLOCKS.getStateFromMeta(1), -256, 0,   -10, -2,  true);
		standardBiomeLayers.add(GSBlocks.ENCELADUS_BLOCKS.getStateFromMeta(0), Blocks.PACKED_ICE.getDefaultState(), -256, 0,   -2, -1,  true);
		//standardBiomeLayers.add(GSBlocks.ENCELADUS_BLOCKS, (byte)1, GSBlocks.ENCELADUS_BLOCKS, (byte)2, -256, 0, -256,  0, false);
		standardBiomeLayers.add(Blocks.BEDROCK.getDefaultState(), 0, 2, 0, 0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);
	}
}
