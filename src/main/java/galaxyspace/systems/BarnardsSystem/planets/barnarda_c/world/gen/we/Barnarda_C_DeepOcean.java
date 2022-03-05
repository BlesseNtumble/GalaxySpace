package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.we;

import java.util.Random;

import asmodeuscore.core.utils.worldengine.WE_Biome;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_BiomeLayer;
import galaxyspace.systems.BarnardsSystem.core.BRBlocks;
import galaxyspace.systems.BarnardsSystem.core.configs.BRConfigCore;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class Barnarda_C_DeepOcean extends WE_Biome {
	private static final int grasscolor = BRConfigCore.enableGreenBarnardaC ? 0x89AC76 : 0xdf73ff;

	public Barnarda_C_DeepOcean(double min, double max) {
		super(new BiomeProperties("barnarda_b_deepocean"), new int[] {grasscolor, 0x116644, 0x985cff});
		
		biomeMinValueOnMap      =   min;
		biomeMaxValueOnMap      =   max;
		biomePersistence        =   1.4D;
		biomeNumberOfOctaves    =      4;
		biomeScaleX             = 280.0D;
		biomeScaleY             =   1.7D;
		biomeSurfaceHeight      =     35;
		biomeInterpolateQuality =     65;
		biomeTemerature 		=   0.0F;
		//-//
		decorateChunkGen_List.clear();		
		createChunkGen_InXZ_List.clear();
		
		this.spawnableWaterCreatureList.add(new Biome.SpawnListEntry(EntitySquid.class, 4, 1, 4));
		
		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		standardBiomeLayers.add(Blocks.GRAVEL.getDefaultState(), BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(1), -256, 0,   -4, -1,  true);
		//standardBiomeLayers.add(BRBlocks.BARNARDA_C_GRASS.getStateFromMeta(0), BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(0), -256, 0, -256,  0, false);
		standardBiomeLayers.add(Blocks.BEDROCK.getDefaultState(), 0, 2, 0, 0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);
	}
	
	@Override
	public void decorateBiome(World world, Random rand, int x, int z)
	{
		
	}
}
