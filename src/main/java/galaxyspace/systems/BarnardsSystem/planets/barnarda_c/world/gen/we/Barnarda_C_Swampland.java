package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.we;

import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_Biome;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_BiomeLayer;
import galaxyspace.systems.BarnardsSystem.core.registers.BRBlocks;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;

public class Barnarda_C_Swampland extends WE_Biome {
	
	public Barnarda_C_Swampland(double min, double max) {
		super(new BiomeProperties("barnarda_c_swampland"), new int[] {0x54732a, 0x11CC44, 0x00FF00});
				
		biomeMinValueOnMap      =  	min;
		biomeMaxValueOnMap      =   max;
		biomePersistence        =   1.1D;
		biomeNumberOfOctaves    =      6;
		biomeScaleX             = 280.0D;
		biomeScaleY             =   1.7D;
		biomeSurfaceHeight      =     64;
		biomeInterpolateQuality =     5;
		
		//-//
		decorateChunkGen_List.clear();		
		createChunkGen_InXZ_List.clear();

		this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityPig.class, 10, 1, 4));

		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityZombie.class, 10, 1, 4));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySpider.class, 10, 1, 4));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySkeleton.class, 10, 1, 4));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySlime.class, 10, 1, 1));

		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		standardBiomeLayers.add(BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(3), BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(1), -256, 0,   -5, -1,  true);
		standardBiomeLayers.add(BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(0), BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(3), -256, 0,   -1, -1,  true);
		standardBiomeLayers.add(BRBlocks.BARNARDA_C_GRASS.getStateFromMeta(0), BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(0), -256, 0, -256,  0, false);
		standardBiomeLayers.add(Blocks.BEDROCK.getDefaultState(), 0, 2, 0, 0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);
	}
}
