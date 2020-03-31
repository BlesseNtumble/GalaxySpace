package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.we;

import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_Biome;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_BiomeLayer;
import galaxyspace.systems.BarnardsSystem.core.registers.BRBlocks;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;

public class Barnarda_C_Dunes extends WE_Biome {

	public Barnarda_C_Dunes(double min, double max) {
		super(new BiomeProperties("barnarda_c_dunes"), new int[] {0x89AC76, 0x11FF66, 0x00FF00});
		
		biomeMinValueOnMap      =   min;
		biomeMaxValueOnMap      =   max;
		biomePersistence        =   1.8D;
		biomeNumberOfOctaves    =      4;
		biomeScaleX             = 280.0D;
		biomeScaleY             =   1.7D;
		biomeSurfaceHeight      =     72;
		biomeInterpolateQuality =     15;		
		
		//-//
		decorateChunkGen_List.clear();		
		createChunkGen_InXZ_List.clear();
		
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityHusk.class, 10, 1, 3));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySpider.class, 10, 1, 1));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySkeleton.class, 10, 1, 2));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEnderman.class, 10, 1, 1));
		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		//standardBiomeLayers.add(Blocks.SANDSTONE, (byte)0, BRBlocks.BARNARDA_C_BLOCKS, (byte)1, -256, 0,   -4, -1,  true);
		standardBiomeLayers.add(BRBlocks.BARNARDA_C_FALLING_BLOCKS.getStateFromMeta(0), BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(1), -256, 0, -256,  0, false);
		standardBiomeLayers.add(Blocks.BEDROCK.getDefaultState(), 0, 2, 0, 0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);
	}

}
