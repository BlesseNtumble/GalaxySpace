package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.we;

import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_Biome;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_BiomeLayer;
import galaxyspace.core.prefab.world.gen.we.WE_LakesGen;
import galaxyspace.systems.BarnardsSystem.core.registers.BRBlocks;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;

public class Barnarda_C_Forest extends WE_Biome {
	
	public Barnarda_C_Forest(double min, double max) {
		super(new BiomeProperties("barnarda_c_forest"), new int[] {0x89AC76, 0x11FF66, 0x00FF00});
				
		biomeMinValueOnMap      =  	min;
		biomeMaxValueOnMap      =   max;
		biomePersistence        =   1.8D;
		biomeNumberOfOctaves    =      4;
		biomeScaleX             = 280.0D;
		biomeScaleY             =   1.7D;
		biomeSurfaceHeight      =     75;
		biomeInterpolateQuality =     30;
		
		//-//
		decorateChunkGen_List.clear();		
		createChunkGen_InXZ_List.clear();
		
		this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntitySheep.class, 10, 1, 2));
		this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityChicken.class, 10, 1, 2));
		this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityPig.class, 10, 1, 2));
		
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityZombie.class, 10, 1, 2));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySpider.class, 10, 1, 2));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySkeleton.class, 10, 1, 2));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEnderman.class, 10, 1, 1));
		
		this.spawnableWaterCreatureList.add(new Biome.SpawnListEntry(EntitySquid.class, 10, 1, 3));
		
		
		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		standardBiomeLayers.add(BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(3), BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(1), -256, 0,   -5, -1,  true);
		standardBiomeLayers.add(BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(0), BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(3), -256, 0,   -1, -1,  true);
		standardBiomeLayers.add(BRBlocks.BARNARDA_C_GRASS.getStateFromMeta(0), BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(0), -256, 0, -256,  0, false);
		
		standardBiomeLayers.add(Blocks.BEDROCK.getDefaultState(), 0, 2, 0, 0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);
		
		WE_LakesGen lakes = new WE_LakesGen();
		lakes.lakeBlock = Blocks.WATER.getDefaultState();
		lakes.iceGen = false;
		lakes.chunksForLake = 6;
		decorateChunkGen_List.add(lakes);
		
		lakes = new WE_LakesGen();
		lakes.lakeBlock = Blocks.LAVA.getDefaultState();
		lakes.iceGen = false;
		lakes.chunksForLake = 100;
		decorateChunkGen_List.add(lakes);
	}
}

