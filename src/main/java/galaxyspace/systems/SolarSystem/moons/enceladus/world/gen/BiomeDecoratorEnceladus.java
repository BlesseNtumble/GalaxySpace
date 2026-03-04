package galaxyspace.systems.SolarSystem.moons.enceladus.world.gen;

import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.registers.blocks.GSBlocks;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeDecoratorEnceladus extends BiomeDecoratorSpace
{
	private World world;
	private WorldGenerator OreGenCoal;
	
	public BiomeDecoratorEnceladus()
	{
		this.OreGenCoal = new WorldGenMinableMeta(GSBlocks.EnceladusBlocks, 8, 2, true, GSBlocks.EnceladusBlocks, 1);
		
	}	

	@Override
	protected void decorate()
	{

		if(GSConfigCore.enableOresGeneration) this.generateOre(24, this.OreGenCoal, 10, 60);
		
		for (int i = 0; i < 50; i++) {
			 
	        int randPosX = this.chunkX + rand.nextInt(16);
	        int randPosY = rand.nextInt(80);
	        int randPosZ = chunkZ + rand.nextInt(16);
			for (int attempts = 0; attempts < 4; attempts++) {
				int side = rand.nextInt(6);
				if (GSBlocks.EnceladusCrystal.canPlaceBlockOnSide(world, randPosX, randPosY, randPosZ, side) && this.world.isAirBlock(randPosX, randPosY, randPosZ)) {
					this.world.setBlock(randPosX, randPosY, randPosZ, GSBlocks.EnceladusCrystal, side, 3);

					break;
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