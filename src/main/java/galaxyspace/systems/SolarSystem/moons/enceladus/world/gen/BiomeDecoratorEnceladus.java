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
			 
	        int randPosX = chunkX + rand.nextInt(16);
	        int randPosY = rand.nextInt(80);
	        int randPosZ = chunkZ + rand.nextInt(16);
       
	        if (this.world.getBlock(randPosX, randPosY - 1, randPosZ) == GSBlocks.EnceladusBlocks && this.world.getBlockMetadata(randPosX, randPosY - 1, randPosZ) == 1 && this.world.isAirBlock(randPosX, randPosY, randPosZ))
	        {
	        	
	        	this.world.setBlock(randPosX, randPosY, randPosZ, GSBlocks.EnceladusCrystal);
	        	
	        }
	        
	        if (this.world.getBlock(randPosX, randPosY + 1, randPosZ) == GSBlocks.EnceladusBlocks && this.world.getBlockMetadata(randPosX, randPosY + 1, randPosZ) == 1 && this.world.isAirBlock(randPosX, randPosY, randPosZ))
	        {	        	
	        	this.world.setBlock(randPosX, randPosY, randPosZ, GSBlocks.EnceladusCrystal);	        	
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