package galaxyspace.systems.BarnardsSystem.planets.barnardaC.world.gen.we;

import galaxyspace.core.world.worldengine.WE_Biome;
import galaxyspace.core.world.worldengine.standardcustomgen.WE_BiomeLayer;
import galaxyspace.core.world.worldengine.standardcustomgen.WE_SnowGen;
import galaxyspace.systems.BarnardsSystem.core.registers.blocks.BRBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;

public class Barnarda_C_Mountains extends WE_Biome {
	
	public Barnarda_C_Mountains(double min, double max, int height, double per, int octaves) {
		super(0, false);
			
		biomeMinValueOnMap      =  	   min;
		biomeMaxValueOnMap      =      max;
		biomePersistence        =     per;
		biomeNumberOfOctaves    =        octaves;
		biomeScaleX             =   280.0D;
		biomeScaleY             =     1.7D;
		biomeSurfaceHeight      =       height;
		biomeInterpolateQuality =       35;
		
		biomeGrassColor = 0x890076;
		//-//
		decorateChunkGen_List.clear();		
		createChunkGen_InXZ_List.clear();
		
		this.spawnableCreatureList.add(new SpawnListEntry(EntitySheep.class, 10, 1, 4));
		
		this.spawnableMonsterList.add(new SpawnListEntry(EntityZombie.class, 10, 1, 4));
		this.spawnableMonsterList.add(new SpawnListEntry(EntitySpider.class, 10, 1, 4));
		this.spawnableMonsterList.add(new SpawnListEntry(EntitySkeleton.class, 10, 1, 4));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityEnderman.class, 10, 1, 1));
		
		
		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		standardBiomeLayers.add(BRBlocks.BarnardaCBlocks, (byte)0, BRBlocks.BarnardaCBlocks, (byte)1, -256, 0,   -2, -3,  true);
		standardBiomeLayers.add(BRBlocks.BarnardaCGrass, (byte)0, BRBlocks.BarnardaCBlocks, (byte)0, -256, 0, -256,  0, false);
		standardBiomeLayers.add(Blocks.bedrock, (byte)0, 0, 2, 0, 0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);
		
		WE_SnowGen snowGen = new WE_SnowGen();
		snowGen.snowPoint       = 120;
		snowGen.randomSnowPoint = 8;
		snowGen.snowBlock       = Blocks.snow;
		snowGen.iceBlock        = Blocks.ice;
		snowGen.freezeMaterial  = Material.water;
		createChunkGen_InXZ_List.add(snowGen);
	}
}
