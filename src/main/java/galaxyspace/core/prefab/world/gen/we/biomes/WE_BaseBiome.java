package galaxyspace.core.prefab.world.gen.we.biomes;

import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_Biome;
import asmodeuscore.core.astronomy.dimension.world.worldengine.additions.WE_CreateChunkGen_InXZ;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_BiomeLayer;
import galaxyspace.core.prefab.world.gen.we.WE_LakesGen;
import galaxyspace.systems.BarnardsSystem.core.registers.BRBlocks;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WE_BaseBiome extends WE_Biome {
	
	private static int[] colors = new int[] {0x00CC00, 0xFFFFFF, 0x00CC00};
	/**
	 * 
	 * @param 
	 *
	 */
	
	public WE_BaseBiome(double min, double max, double persistance, int octaves, int height, int intquility, WE_BiomeLayer layers, IWorldGenerator... gens) {
		super(new BiomeProperties("we_basebiome"), colors);
				
		biomeMinValueOnMap      =  	min;
		biomeMaxValueOnMap      =   max;
		biomePersistence        =   persistance;
		biomeNumberOfOctaves    =   octaves;
		biomeScaleX             = 	280.0D;
		biomeScaleY             =   1.5D;
		biomeSurfaceHeight      =   height;
		biomeInterpolateQuality =   intquility;
		
		//-//
		decorateChunkGen_List.clear();		
		createChunkGen_InXZ_List.clear();
		spawnableMonsterList.clear();
		spawnableCaveCreatureList.clear();
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		
		createChunkGen_InXZ_List.add(layers);
		
		for(IWorldGenerator gen : gens)
			decorateChunkGen_List.add(gen);
	}
	
	public WE_BaseBiome setColors(int grass, int water, int leaves)
	{
		biomeBlockGrassColor = grass;
		biomeBlockWaterColor = water;
		biomeBlockLeavesColor = leaves;
		return this;
	}
	
	
	public WE_BaseBiome setSize(double x, double y)
	{
		biomeScaleX = x;
		biomeScaleY = y;
		return this;
	}
	
	public WE_BaseBiome addChunkGen(WE_CreateChunkGen_InXZ gen)
	{
		createChunkGen_InXZ_List.add(gen);
		return this;
	}
	
	
}
