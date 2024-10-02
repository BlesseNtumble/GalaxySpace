package galaxyspace.systems.SolarSystem.moons.callisto.world.gen;

import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import net.minecraft.world.World;

public class BiomeDecoratorCallisto extends BiomeDecoratorSpace
{
	private World world;


	public BiomeDecoratorCallisto()
	{

	}	

	@Override
	protected void decorate()
	{
		/*for (int i = 0; i < 10; i++) {
			 
	        int randPosX = chunkX + rand.nextInt(16);
	        int randPosY = rand.nextInt(80);
	        int randPosZ = chunkZ + rand.nextInt(16);
	        
	        if (this.world.getBlock(randPosX, randPosY - 1, randPosZ) == GSBlocks.CallistoBlocks && this.world.getBlockMetadata(randPosX, randPosY - 1, randPosZ) == 0 && this.world.isAirBlock(randPosX, randPosY, randPosZ))
	        {
	        	new WorldGenPeak(GSBlocks.CallistoGrunt).generate(this.world, this.rand, randPosX, randPosY, randPosZ);
	        }
		}*/
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