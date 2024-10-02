package galaxyspace.systems.SolarSystem.moons.triton.world.gen;

import galaxyspace.GalaxySpace;
import galaxyspace.core.registers.blocks.GSBlocks;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeDecoratorTriton extends BiomeDecoratorSpace
{
	private World world;
	private WorldGenerator OreGenUranium;

	public BiomeDecoratorTriton()
	{
		this.OreGenUranium = new WorldGenMinableMeta(GSBlocks.TritonBlocks, 5, 5, true, GSBlocks.TritonBlocks, 3);
		
	}	

	@Override
	protected void decorate()
	{
		this.generateOre(6, OreGenUranium, 0, 40);
		if(this.getCurrentWorld().rand.nextInt(100) == 0) {
			
			
			 int randPosX = chunkX + rand.nextInt(16);
			 int randPosZ = chunkZ + rand.nextInt(16);
			 
			 int randPosY = this.world.getTopSolidOrLiquidBlock(randPosX, randPosZ) - 1;
			 
			 
			
			 
			 if(this.world.isAirBlock(randPosX, randPosY + 1, randPosZ))
			 {
					 GalaxySpace.debug(randPosX + " | " + randPosY + " | " + randPosZ);
					 this.world.setBlock(randPosX, randPosY, randPosZ, GSBlocks.TritonBlocks, 4, 3);	
				 			 
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