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
import net.minecraft.world.biome.Biome.BiomeProperties;

public class Enceladus_Mountains extends WE_Biome {
	
	public Enceladus_Mountains() {
		super(new BiomeProperties("enceladus_mountains"));
				
		biomeMinValueOnMap      =  	0.0D;
		biomeMaxValueOnMap      =   2.0D;
		biomePersistence        =   1.6D;
		biomeNumberOfOctaves    =      4;
		biomeScaleX             = 280.0D;
		biomeScaleY             =   1.7D;
		biomeSurfaceHeight      =     120;
		biomeInterpolateQuality =     2;
		//biomeBlockGrassColor    = 0x00FF00;
		//biomeBlockWaterColor 	= 0xEEDD44;
		
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
		standardBiomeLayers.add(Blocks.PACKED_ICE, (byte)0, GSBlocks.ENCELADUS_BLOCKS, (byte)1, -256, 0,   -40, -2,  true);
		standardBiomeLayers.add(GSBlocks.ENCELADUS_BLOCKS, (byte)0, Blocks.PACKED_ICE, (byte)0, -256, 0,   -4, -1,  true);
		//standardBiomeLayers.add(GSBlocks.ENCELADUS_BLOCKS, (byte)1, GSBlocks.ENCELADUS_BLOCKS, (byte)2, -256, 0, -256,  0, false);
		standardBiomeLayers.add(Blocks.BEDROCK, (byte)0,                                0, 2,  0,  0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);
	}
}
