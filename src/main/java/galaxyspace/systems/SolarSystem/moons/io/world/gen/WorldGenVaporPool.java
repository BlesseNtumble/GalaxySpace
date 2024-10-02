package galaxyspace.systems.SolarSystem.moons.io.world.gen;

import java.util.Random;

import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.registers.fluids.GSFluids;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenVaporPool extends WorldGenerator{

	public WorldGenVaporPool()
    {
    }
	
	@Override
	public boolean generate(World world, Random rand, int x, int y, int z)
	{
		if (y <= 4)
        {
            return false;
        }
		
		Block block = GSBlocks.IoBlocks;
		int meta = 0;
		
		int radius = 5 + rand.nextInt(4);
	    int radiusSq = radius * radius;
	    for (int poolX = -radius; poolX <= radius; poolX++)
	    {
	    	for (int poolY = -radius; poolY <= radius; poolY++)
	    	{
	    		for (int poolZ = -radius; poolZ <= radius; poolZ++)
	    		{
	    			int distance = poolX * poolX + poolY * poolY + poolZ * poolZ;

	    			if (distance <= radiusSq)
	    			{	    				
	    				if(distance >= radiusSq - 16)
	    					world.setBlock(poolX + x, poolY + y, poolZ + z, block, meta, distance == radiusSq ? 3 : 2);
	    				else
	    					world.setBlock(poolX + x, poolY + y, poolZ + z, (poolY <= 0 ? GSFluids.BlockSulfurAcid : Blocks.air), 0, distance == radiusSq ? 3 : 2);
	    			}
	    		}
	    	}
	    }
	    
	    boolean firstSet = false;
        for (int i = 255; i >= y + 1; --i)
        {
            if (world.getBlock(x, i, z) != Blocks.air)
            {
                if (!firstSet)
                {
                    world.setBlock(x, i, z, GSBlocks.IoBlocks, 7, 3);
                    firstSet = true;
                }
                else
                {
                	 world.setBlock(x, i, z, Blocks.air, 0, 2);
                }
            }
        }
		return true;
	}
}
