package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.we;

import java.util.Random;

import asmodeuscore.core.utils.worldengine.WE_Biome;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_BiomeLayer;
import galaxyspace.systems.BarnardsSystem.core.BRBlocks;
import galaxyspace.systems.BarnardsSystem.core.configs.BRConfigCore;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class Barnarda_C_SnowPlains extends WE_Biome {
	
	private static final int grasscolor = BRConfigCore.enableGreenBarnardaC ? 0x88BB44 : 0x822899;
	
	public Barnarda_C_SnowPlains(double min, double max, int height) {
		super(new BiomeProperties("barnarda_c_snowplains"), new int[] {grasscolor, 0x11FF66, 0x985cff});
				
		biomeMinValueOnMap      =  	min;
		biomeMaxValueOnMap      =   max;
		biomePersistence        =   1.4D;
		biomeNumberOfOctaves    =      4;
		biomeScaleX             = 280.0D;
		biomeScaleY             =   1.7D;
		biomeSurfaceHeight      =     height;
		biomeInterpolateQuality =     25;
		biomeTemerature = -0.1F;
		//-//
		decorateChunkGen_List.clear();		
		createChunkGen_InXZ_List.clear();
				
		this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityCow.class, 10, 1, 4));
		this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityPolarBear.class, 10, 1, 3));
		
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityZombie.class, 10, 1, 4));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySpider.class, 10, 1, 4));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySkeleton.class, 10, 1, 4));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEnderman.class, 10, 1, 4));
		
		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		standardBiomeLayers.add(BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(0), BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(1), -256, 0,   -5, -1,  true);
		standardBiomeLayers.add(BRBlocks.BARNARDA_C_GRASS.getStateFromMeta(0), BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(0), -256, 0, -256,  0, false);
		standardBiomeLayers.add(Blocks.SNOW_LAYER.getDefaultState(), BRBlocks.BARNARDA_C_GRASS.getStateFromMeta(0), -256, 0, -1,  0, false);
		standardBiomeLayers.add(Blocks.BEDROCK.getDefaultState(), 0, 2, 0, 0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);
	}
	
	@Override
	public void decorateBiome(World world, Random rand, int x, int z)
	{
		
	}
}
