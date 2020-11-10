package galaxyspace.systems.SolarSystem.moons.europa.world.gen.we;

import java.util.Random;

import asmodeuscore.core.utils.worldengine.WE_Biome;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_BiomeLayer;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_TerrainGenerator;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_OreGen.BlockPredicate;
import galaxyspace.core.GSBlocks;
import galaxyspace.core.prefab.entities.EntityEvolvedColdBlaze;
import galaxyspace.systems.SolarSystem.moons.enceladus.blocks.EnceladusBlocks.EnumEnceladusBlocks;
import galaxyspace.systems.SolarSystem.moons.enceladus.blocks.EnceladusCrystal;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedEnderman;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSkeleton;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSpider;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedZombie;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class Europa_Spikes extends WE_Biome {
	
	public Europa_Spikes(WE_TerrainGenerator tg) {
		super(new BiomeProperties("europa_spikes"), new int[] {0x00CC00, 0xFFFFFF, 0x00CC00});
				
		//biomeMinValueOnMap      =  	-0.4D;
		biomeMaxValueOnMap      =   2.5D;
		biomePersistence        =   1.6D;
		biomeNumberOfOctaves    =      5;
		biomeScaleX             = 280.0D;
		biomeScaleY             =   1.7D;
		biomeSurfaceHeight      =     130;
		biomeInterpolateQuality =     1;
		
		biomeAbs = true;
		
		//-//
		decorateChunkGen_List.clear();		
		createChunkGen_InXZ_List.clear();		
		spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEvolvedColdBlaze.class, 10, 1, 4));
		
		WE_BiomeLayer layer = new WE_BiomeLayer();
		layer.add(Blocks.PACKED_ICE.getDefaultState(), tg.worldStoneBlock, -256, 0, 65, -3,  true);
		
		
		layer.add(GSBlocks.EUROPA_BLOCKS.getStateFromMeta(0), Blocks.PACKED_ICE.getDefaultState(), -256, 0,   -2, -1,  true);
		layer.add(Blocks.WATER.getDefaultState(), tg.worldStoneBlock, -256, 0,  -100, 0,  true);
		layer.add(Blocks.BEDROCK.getDefaultState(), 0, 2, 0, 0, true);
		
		
		createChunkGen_InXZ_List.add(layer);
	}
	
	@Override
	public void decorateBiome(World world, Random rand, int x, int z)
	{
		int randPosX;
		int randPosY;
		int randPosZ;

		for (int i = 0; i < 30; i++) {
    		randPosX = x + rand.nextInt(16);
	        //randPosY = rand.nextInt(120);
	        randPosZ = z + rand.nextInt(16);
	        BlockPos pos = world.getHeight(new BlockPos(randPosX, 0, randPosZ));
	        
	        if(world.isAreaLoaded(pos, 13, false)) {
	    		new WorldGenMinable(GSBlocks.SURFACE_ICE.getStateFromMeta(4), 45,
	    				new BlockPredicate(
	    						GSBlocks.EUROPA_BLOCKS.getStateFromMeta(0)
	    				)).generate(world, rand, pos);
	    	}
    	}
	}
}
