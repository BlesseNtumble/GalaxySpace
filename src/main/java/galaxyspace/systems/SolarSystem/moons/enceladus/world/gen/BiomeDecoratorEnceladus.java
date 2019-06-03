package galaxyspace.systems.SolarSystem.moons.enceladus.world.gen;

import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.systems.SolarSystem.moons.enceladus.blocks.EnceladusBlocks.EnumEnceladusBlocks;
import galaxyspace.systems.SolarSystem.moons.enceladus.blocks.EnceladusCrystal;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeDecoratorEnceladus extends BiomeDecoratorSpace
{

    private World currentWorld;
    private WorldGenerator coalGen;


    public BiomeDecoratorEnceladus()
    {
        this.coalGen = new WorldGenMinableMeta(GSBlocks.ENCELADUS_BLOCKS, 10, 2, true, GSBlocks.ENCELADUS_BLOCKS, 1);
    }

    @Override
    protected void decorate()
    {
    	if(GSConfigCore.enableOresGeneration) {
    		this.generateOre(10, coalGen, 10, 60);
    	}    	
    	
    	int j = rand.nextInt(4);
    	for (int i = 0; i < 50; i++) {
			 
	        int randPosX = posX + rand.nextInt(16) + 8;
	        int randPosY = rand.nextInt(80);
	        int randPosZ = posZ + rand.nextInt(16) + 8;
       
	        BlockPos pos = new BlockPos(randPosX, randPosY, randPosZ);
	        if (this.getCurrentWorld().getBlockState(pos.down()) == GSBlocks.ENCELADUS_BLOCKS.getStateFromMeta(EnumEnceladusBlocks.ENCELADUS_GRUNT.getMeta()) && this.getCurrentWorld().isAirBlock(pos))
	        {	        	
	        	this.getCurrentWorld().setBlockState(pos, GSBlocks.ENCELADUS_CRYSTAL.getDefaultState().withProperty(EnceladusCrystal.FACING, EnumFacing.HORIZONTALS[j]));	  
	        }
	        
	        if (this.getCurrentWorld().getBlockState(pos.up()) == GSBlocks.ENCELADUS_BLOCKS.getStateFromMeta(EnumEnceladusBlocks.ENCELADUS_GRUNT.getMeta()) && this.getCurrentWorld().isAirBlock(pos))
	        {	        	
	        	this.getCurrentWorld().setBlockState(pos, GSBlocks.ENCELADUS_CRYSTAL.getDefaultState().withProperty(EnceladusCrystal.FACING, EnumFacing.HORIZONTALS[j]));	  
	        	
	        	
	        }
        
		}
    }

    @Override
    protected void setCurrentWorld(World world)
    {
        this.currentWorld = world;
    }

    @Override
    protected World getCurrentWorld()
    {
        return this.currentWorld;
    }
}
