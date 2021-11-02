package galaxyspace.systems.ACentauriSystem.planets.proxima_b.world.gen.we;

import java.util.Random;

import asmodeuscore.core.utils.worldengine.WE_Biome;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_BiomeLayer;
import galaxyspace.core.prefab.world.gen.WorldGenPool;
import galaxyspace.systems.ACentauriSystem.core.ACBlocks;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.blocks.Proxima_B_Blocks;
import micdoodle8.mods.galacticraft.core.GCFluids;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Proxima_B_Ocean extends WE_Biome {

	public Proxima_B_Ocean() {
		super(new BiomeProperties("proxima_b_ocean"), new int[] {0x00FF00, 0xEEDD44, 0x00FF00});
		
		biomeMinValueOnMap      =   -0.6D;
		biomeMaxValueOnMap      =   -0.4D;
		biomePersistence        =   1.4D;
		biomeNumberOfOctaves    =      4;
		biomeScaleX             = 280.0D;
		biomeScaleY             =   1.7D;
		biomeSurfaceHeight      =     55;
		biomeInterpolateQuality =     15;
		
		//-//
		decorateChunkGen_List.clear();		
		createChunkGen_InXZ_List.clear();
		
		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		standardBiomeLayers.add(ACBlocks.PROXIMA_B_BLOCKS.getStateFromMeta(1), ACBlocks.PROXIMA_B_BLOCKS.getStateFromMeta(2), -256, 0,   -4, -1,  true);
		standardBiomeLayers.add(ACBlocks.PROXIMA_B_BLOCKS.getStateFromMeta(0), ACBlocks.PROXIMA_B_BLOCKS.getStateFromMeta(1), -256, 0, -256,  0, false);
		standardBiomeLayers.add(Blocks.BEDROCK.getDefaultState(), 0, 2, 0, 0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);
	}
	
	@Override
	public void decorateBiome(World world, Random rand, int x, int z)
	{
		int randPosX = x + rand.nextInt(16) + 8;
		int randPosZ = z + rand.nextInt(16) + 8;
		BlockPos pos = world.getHeight(new BlockPos(randPosX, 0, randPosZ));
		
		for(int i = 0; i < 3; i++){
    		randPosX = x + rand.nextInt(16) + 8;
			//randPosY = this.rand.nextInt(256);
			randPosZ = z + rand.nextInt(16) + 8;
			pos = world.getHeight(new BlockPos(randPosX, 0, randPosZ));
			
			if(world.getBlockState(pos.down()) == ACBlocks.PROXIMA_B_BLOCKS.getDefaultState().withProperty(Proxima_B_Blocks.BASIC_TYPE, Proxima_B_Blocks.EnumBlockProximaB.SURFACE))
    			world.setBlockState(pos, Blocks.DEADBUSH.getDefaultState());
    	}
    	
    	int kx = x + rand.nextInt(16) + 8;
		int kz = x + rand.nextInt(16) + 8;    
		
    	if (rand.nextInt(50) == 0)
        {
    		int l2 = world.getTopSolidOrLiquidBlock(new BlockPos(kx, 0, kz)).getY() - 20 - rand.nextInt(25);
    		new WorldGenPool(ACBlocks.PROXIMA_B_BLOCKS.getDefaultState().withProperty(Proxima_B_Blocks.BASIC_TYPE, Proxima_B_Blocks.EnumBlockProximaB.STONE), GCFluids.fluidOil.getBlock().getDefaultState(), 5).generate(world, rand, new BlockPos(x, l2, z));
        }
	}
}
