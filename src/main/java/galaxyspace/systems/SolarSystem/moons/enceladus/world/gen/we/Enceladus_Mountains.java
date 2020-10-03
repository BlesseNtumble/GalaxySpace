package galaxyspace.systems.SolarSystem.moons.enceladus.world.gen.we;

import java.util.Random;

import asmodeuscore.core.utils.worldengine.WE_Biome;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_BiomeLayer;
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

public class Enceladus_Mountains extends WE_Biome {
	
	public Enceladus_Mountains() {
		super(new BiomeProperties("enceladus_mountains"), new int[] {0x00CC00, 0xFFFFFF, 0x00CC00});
				
		biomeMinValueOnMap      =  	0.0D;
		biomeMaxValueOnMap      =   2.0D;
		biomePersistence        =   1.6D;
		biomeNumberOfOctaves    =      4;
		biomeScaleX             = 280.0D;
		biomeScaleY             =   1.7D;
		biomeSurfaceHeight      =     120;
		biomeInterpolateQuality =     10;
		
		//-//
		decorateChunkGen_List.clear();		
		createChunkGen_InXZ_List.clear();
		
		spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEvolvedSpider.class, 10, 1, 2));
		spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEvolvedSkeleton.class, 10, 1, 2));
		spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEvolvedZombie.class, 10, 1, 4));
		spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEvolvedEnderman.class, 10, 1, 2));
		spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEvolvedColdBlaze.class, 10, 1, 4));
		
		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		standardBiomeLayers.add(Blocks.PACKED_ICE.getDefaultState(), GSBlocks.ENCELADUS_BLOCKS.getStateFromMeta(1), -256, 0,   -40, -2,  true);
		standardBiomeLayers.add(GSBlocks.ENCELADUS_BLOCKS.getStateFromMeta(0), Blocks.PACKED_ICE.getDefaultState(), -256, 0,   -4, -1,  true);
		//standardBiomeLayers.add(GSBlocks.ENCELADUS_BLOCKS, (byte)1, GSBlocks.ENCELADUS_BLOCKS, (byte)2, -256, 0, -256,  0, false);
		standardBiomeLayers.add(Blocks.BEDROCK.getDefaultState(), 0, 2, 0, 0, true);
		createChunkGen_InXZ_List.add(standardBiomeLayers);
	}
	
	@Override
	public void decorateBiome(World world, Random rand, int x, int z)
	{
		int randPosX;
		int randPosY;
		int randPosZ;

		//GalaxySpace.instance.debug(x + " | " + z);
    	for (int i = 0; i < 50; i++) {
			 
	        randPosX = x + rand.nextInt(16) + 8;
	        randPosY = rand.nextInt(120);
	        randPosZ = z + rand.nextInt(16) + 8;
       
	        BlockPos pos = new BlockPos(randPosX, randPosY, randPosZ);
	        
	        if (world.getBlockState(pos.down()) == GSBlocks.ENCELADUS_BLOCKS.getStateFromMeta(EnumEnceladusBlocks.ENCELADUS_GRUNT.getMeta()) && world.isAirBlock(pos))
	        {	        	
	        	world.setBlockState(pos, GSBlocks.ENCELADUS_CRYSTAL.getDefaultState().withProperty(EnceladusCrystal.FACING, EnumFacing.HORIZONTALS[rand.nextInt(4)]));	  
	        }
	        
	        if (world.getBlockState(pos.up()) == GSBlocks.ENCELADUS_BLOCKS.getStateFromMeta(EnumEnceladusBlocks.ENCELADUS_GRUNT.getMeta()) && world.isAirBlock(pos))
	        {	        	
	        	world.setBlockState(pos, GSBlocks.ENCELADUS_CRYSTAL.getDefaultState().withProperty(EnceladusCrystal.FACING, EnumFacing.HORIZONTALS[rand.nextInt(4)]));	  
	        	
	        	
	        }
        
		}
	}
}
