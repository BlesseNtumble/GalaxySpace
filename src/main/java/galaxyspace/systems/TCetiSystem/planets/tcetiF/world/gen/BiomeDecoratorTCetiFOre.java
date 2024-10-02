package galaxyspace.systems.TCetiSystem.planets.tcetiF.world.gen;

import galaxyspace.systems.TCetiSystem.core.registers.blocks.TCBlocks;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class BiomeDecoratorTCetiFOre extends BiomeDecoratorSpace
{
	private World world;

	public BiomeDecoratorTCetiFOre()
	{

	}	

	@Override
	protected void decorate()
	{
		
		for (int i = 0; i < 1000; i++) 
		{

			int randPosX = chunkX + rand.nextInt(16);
		    int randPosY = rand.nextInt(100);
		    int randPosZ = chunkZ + rand.nextInt(16);
      
		    if(this.world.getBlock(randPosX, randPosY, randPosZ) == Blocks.water)
		    {
		        if (this.world.getBlock(randPosX, randPosY - 1, randPosZ) == TCBlocks.TCetiEBlocks)
		        {
		        	this.world.setBlock(randPosX, randPosY, randPosZ, TCBlocks.TCetiEDandelions, 0, 3);
		        	
		        	for (int j = 1; j < rand.nextInt(11); j++)
		        	{
	        			this.world.setBlock(randPosX, randPosY + j, randPosZ, TCBlocks.TCetiEDandelions, 1, 3);
	        			this.world.setBlock(randPosX, randPosY + j + 1, randPosZ, TCBlocks.TCetiEDandelions, 2, 3);
		        		
		        	}
		        	
		        	
		        }
		        
		        randPosX = chunkX + rand.nextInt(16);
		        randPosY = rand.nextInt(100);
			    randPosZ = chunkZ + rand.nextInt(16);
			    
			    if (this.world.getBlock(randPosX, randPosY - 1, randPosZ) == TCBlocks.TCetiEBlocks && this.world.getBlockMetadata(randPosX, randPosY - 1, randPosZ) == 0)
		        {
		        	if(i < 800) this.world.setBlock(randPosX, randPosY, randPosZ, TCBlocks.TCetiEDandelions, 3, 3);     	
		        }
			    
			    randPosX = chunkX + rand.nextInt(16);
		        randPosY = rand.nextInt(100);
			    randPosZ = chunkZ + rand.nextInt(16);
			    
			    if (this.world.getBlock(randPosX, randPosY - 1, randPosZ) == TCBlocks.TCetiEBlocks && this.world.getBlockMetadata(randPosX, randPosY - 1, randPosZ) == 0)
		        {
		        	if(i < 800) this.world.setBlock(randPosX, randPosY, randPosZ, TCBlocks.TCetiEDandelions, 4, 3);     	
		        }
			    
			    randPosX = chunkX + rand.nextInt(16);
		        randPosY = rand.nextInt(100);
			    randPosZ = chunkZ + rand.nextInt(16);
			    
			    if (this.world.getBlock(randPosX, randPosY - 1, randPosZ) == TCBlocks.TCetiEBlocks && this.world.getBlockMetadata(randPosX, randPosY - 1, randPosZ) == 0)
		        {
		        	if(i < 800) this.world.setBlock(randPosX, randPosY, randPosZ, TCBlocks.TCetiEDandelions, 5, 3);     	
		        }
		    }
		}
	}

	@Override
	protected void setCurrentWorld(World world)
	{
		this.world = world;
	}

	@Override
	protected World getCurrentWorld()
	{
		return this.world;
	}
}