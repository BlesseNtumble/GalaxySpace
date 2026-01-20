package galaxyspace.systems.BarnardsSystem.planets.barnardaC.world.gen.we;

import galaxyspace.core.world.worldengine.WE_Biome;
import galaxyspace.core.world.worldengine.standardcustomgen.WE_BiomeLayer;
import galaxyspace.core.world.worldengine.standardcustomgen.WE_LakeGen;
import galaxyspace.systems.BarnardsSystem.core.registers.blocks.BRBlocks;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.init.Blocks;

public class Barnarda_C_Forest extends WE_Biome {
	
	public Barnarda_C_Forest(double min, double max) {
		super(0, false);
				
		biomeMinValueOnMap      =  	min;
		biomeMaxValueOnMap      =   max;
		biomePersistence        =   1.8D;
		biomeNumberOfOctaves    =      4;
		biomeScaleX             = 280.0D;
		biomeScaleY             =   1.7D;
		biomeSurfaceHeight      =     75;
		biomeInterpolateQuality =     30;
		
		//biomeGrassColor = 0x89AC76;
		biomeGrassColor = 0x89A316;

		//-//
		decorateChunkGen_List.clear();		
		createChunkGen_InXZ_List.clear();
		
		this.spawnableCreatureList.add(new SpawnListEntry(EntityCow.class, 10, 1, 4));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityChicken.class, 10, 1, 4));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityPig.class, 10, 1, 4));
		
		this.spawnableMonsterList.add(new SpawnListEntry(EntityZombie.class, 10, 1, 4));
		this.spawnableMonsterList.add(new SpawnListEntry(EntitySpider.class, 10, 1, 4));
		this.spawnableMonsterList.add(new SpawnListEntry(EntitySkeleton.class, 10, 1, 4));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityEnderman.class, 10, 1, 1));
		
		this.spawnableWaterCreatureList.add(new SpawnListEntry(EntitySquid.class, 10, 1, 4));
		
		
		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		standardBiomeLayers.add(BRBlocks.BarnardaCBlocks, (byte)0, BRBlocks.BarnardaCBlocks, (byte)1, -256, 0,   -2, -3,  true);
		standardBiomeLayers.add(BRBlocks.BarnardaCGrass, (byte)1, BRBlocks.BarnardaCBlocks, (byte)0, -256, 0, -256,  0, false);
		standardBiomeLayers.add(Blocks.bedrock, (byte)0, 0, 2, 0, 0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);
		
		WE_LakeGen lakes = new WE_LakeGen();
		lakes.lakeBlock = Blocks.water;
		lakes.chunksForLake = 6;
		decorateChunkGen_List.add(lakes);
		
		lakes = new WE_LakeGen();
		lakes.lakeBlock = Blocks.lava;
		lakes.chunksForLake = 100;
		decorateChunkGen_List.add(lakes);
	}
}

