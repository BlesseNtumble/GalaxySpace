package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.we;

import java.util.Random;

import asmodeuscore.core.utils.worldengine.WE_Biome;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_BiomeLayer;
import galaxyspace.core.prefab.world.gen.WorldGenCircleBlock;
import galaxyspace.systems.BarnardsSystem.core.BRBlocks;
import galaxyspace.systems.BarnardsSystem.core.configs.BRConfigCore;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class Barnarda_C_Ocean extends WE_Biome {

	private static final int grasscolor = BRConfigCore.enableGreenBarnardaC ? 0x89AC76 : 0xdf73ff;
	
	public Barnarda_C_Ocean(double min, double max, boolean frozen) {		
		super(new BiomeProperties("barnarda_c_ocean" + (frozen ? "_frozen" : "")), new int[] {grasscolor, 0x11FF66, 0x985cff});
		
		biomeMinValueOnMap      =   min;
		biomeMaxValueOnMap      =   max;
		biomePersistence        =   1.4D;
		biomeNumberOfOctaves    =      4;
		biomeScaleX             = 280.0D;
		biomeScaleY             =   1.7D;
		biomeSurfaceHeight      =     55;
		biomeInterpolateQuality =     65;
		biomeTemerature 		=  frozen ? -0.5F : 0.35F;
		//-//
		decorateChunkGen_List.clear();		
		createChunkGen_InXZ_List.clear();

		this.spawnableWaterCreatureList.add(new Biome.SpawnListEntry(EntitySquid.class, 10, 1, 4));
		
		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		standardBiomeLayers.add(BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(0), BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(1), -256, 0,   -5, -1,  true);
		standardBiomeLayers.add(BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(3), BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(0), -256, 0,   -1, -1,  true);
		standardBiomeLayers.add(BRBlocks.BARNARDA_C_GRASS.getStateFromMeta(0), BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(3), -256, 0, -256,  0, false);
		
		if(frozen)
			standardBiomeLayers.add(Blocks.ICE.getDefaultState(), 64, 0, -1, 0, true);
		
		standardBiomeLayers.add(Blocks.BEDROCK.getDefaultState(), 0, 2, 0, 0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);
	}
	
	@Override
	public void decorateBiome(World world, Random rand, int x, int z)
	{
		int randPosX = x + rand.nextInt(16) + 8;
		int randPosZ = z + rand.nextInt(16) + 8;
		BlockPos pos = world.getHeight(new BlockPos(randPosX, 0, randPosZ));
		
		WorldGenCircleBlock circle = new WorldGenCircleBlock(Blocks.CLAY.getDefaultState(), rand.nextInt(5) + 3, BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(0));
		if(rand.nextInt(5) == 0) {
			randPosX = x + rand.nextInt(8) + 8;
			randPosZ = z + rand.nextInt(8) + 8;
			pos = world.getHeight(new BlockPos(randPosX, 0, randPosZ));
			
			circle.generate(world, rand, pos.down());
			
			
		}
		circle = new WorldGenCircleBlock(Blocks.GRAVEL.getDefaultState(), rand.nextInt(16) + 5, BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(0));
		if(rand.nextInt(2) == 0) {
			randPosX = x + rand.nextInt(8) + 8;
			randPosZ = z + rand.nextInt(8) + 8;
			pos = world.getHeight(new BlockPos(randPosX, 0, randPosZ));			
			circle.generate(world, rand, pos.down());		
			
		}
	}
}
