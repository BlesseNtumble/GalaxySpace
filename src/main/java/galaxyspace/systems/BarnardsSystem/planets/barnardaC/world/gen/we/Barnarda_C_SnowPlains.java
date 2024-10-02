package galaxyspace.systems.BarnardsSystem.planets.barnardaC.world.gen.we;

import galaxyspace.core.world.worldengine.WE_Biome;
import galaxyspace.core.world.worldengine.standardcustomgen.WE_BiomeLayer;
import galaxyspace.systems.BarnardsSystem.core.registers.blocks.BRBlocks;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.init.Blocks;

public class Barnarda_C_SnowPlains extends WE_Biome {
	
	public Barnarda_C_SnowPlains(double min, double max, int height) {
		super(0, false);
				
		biomeMinValueOnMap      =  	min;
		biomeMaxValueOnMap      =   max;
		biomePersistence        =   1.4D;
		biomeNumberOfOctaves    =      4;
		biomeScaleX             = 280.0D;
		biomeScaleY             =   1.7D;
		biomeSurfaceHeight      =     height;
		biomeInterpolateQuality =     25;
		
		biomeGrassColor = 0x89AC76;
		//-//
		decorateChunkGen_List.clear();		
		createChunkGen_InXZ_List.clear();
				
		this.spawnableCreatureList.add(new SpawnListEntry(EntityCow.class, 10, 1, 4));
		
		this.spawnableMonsterList.add(new SpawnListEntry(EntityZombie.class, 10, 1, 4));
		this.spawnableMonsterList.add(new SpawnListEntry(EntitySpider.class, 10, 1, 4));
		this.spawnableMonsterList.add(new SpawnListEntry(EntitySkeleton.class, 10, 1, 4));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityEnderman.class, 10, 1, 4));
		
		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		
		standardBiomeLayers.add(BRBlocks.BarnardaCBlocks, (byte)0, BRBlocks.BarnardaCBlocks, (byte)1, -256, 0,   -2, -3,  true);
		standardBiomeLayers.add(BRBlocks.BarnardaCGrass, (byte)2, BRBlocks.BarnardaCBlocks, (byte)0, -256, 0, -256,  0, false);
		standardBiomeLayers.add(Blocks.snow_layer, (byte)0, BRBlocks.BarnardaCGrass, (byte) 2, -256, 0, -1,  0, false);		
		standardBiomeLayers.add(Blocks.bedrock, (byte)0, 0, 2, 0, 0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);
	}
}
