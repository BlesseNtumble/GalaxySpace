package galaxyspace.systems.SolarSystem.moons.europa.world.gen;

import galaxyspace.core.registers.blocks.GSBlocks;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeDecoratorEuropaOre extends BiomeDecoratorSpace
{
	private World world;
    private WorldGenerator OreGenIce;
    private WorldGenerator OreGenBrownIce;
    
    private WorldGenerator OreGenEmerald;
    private WorldGenerator OreGenSilicon;
    private WorldGenerator OreGenAluminum;

	public BiomeDecoratorEuropaOre()
	{
		this.OreGenIce = new WorldGenMinableMeta(Blocks.packed_ice, 20, 0, true, Blocks.water, 0);
		this.OreGenBrownIce = new WorldGenMinableMeta(GSBlocks.EuropaBlocks, 40, 2, true, Blocks.packed_ice, 0);
		this.OreGenEmerald = new WorldGenMinableMeta(GSBlocks.EuropaBlocks, 4, 3, true, GSBlocks.EuropaBlocks, 1);
		this.OreGenSilicon = new WorldGenMinableMeta(GSBlocks.EuropaBlocks, 4, 4, true, GSBlocks.EuropaBlocks, 1);
		this.OreGenAluminum = new WorldGenMinableMeta(GSBlocks.EuropaBlocks, 6, 5, true, GSBlocks.EuropaBlocks, 1);

	}	

	@Override
	protected void decorate()
	{
		this.generateOre(2, this.OreGenIce, 10, 60);
		this.generateOre(16, this.OreGenBrownIce, 40, 75);
		
		this.generateOre(2, this.OreGenEmerald, 0, 30);
		this.generateOre(4, this.OreGenSilicon, 0, 30);
		this.generateOre(8, this.OreGenAluminum, 0, 30);
		/*
		for (int i = 0; i < 1200; i++) {

			int randPosY = rand.nextInt(140);
			
			for (int x = 0; x < 16; x++)
				for(int z = 0; z < 16; z++)
				{
					//int randPosX = chunkX + rand.nextInt(16);
					
					//int randPosZ = chunkZ + rand.nextInt(16);
					
					int randPosX = chunkX + x;
					int randPosZ = chunkZ + z;
					
					
       
					if(randPosY >= 71 || randPosY >= 67 && randPosY <= 69)
					{
	        
						if (this.world.getBlock(randPosX, randPosY - 1, randPosZ) == GSBlocks.EuropaBlocks && this.world.getBlockMetadata(randPosX, randPosY - 1, randPosZ) == 0 && this.world.isAirBlock(randPosX, randPosY, randPosZ))
						{
							this.world.setBlock(randPosX, randPosY, randPosZ, Blocks.snow_layer);	        	
						}
					}	

					else if(randPosY < 69 )
					{
						if (this.world.getBlock(randPosX, randPosY - 1, randPosZ) == GSBlocks.EuropaBlocks && this.world.getBlockMetadata(randPosX, randPosY - 1, randPosZ) == 0 && this.world.isAirBlock(randPosX, randPosY, randPosZ))
						{
							this.world.setBlock(randPosX, randPosY - 1, randPosZ, GSBlocks.EuropaBlocks, 2, 1);
						}
					}
										
				}
				/*
				int randPosXX = chunkX + rand.nextInt(16);
				int randPosZZ = chunkZ + rand.nextInt(16);

				if (this.world.getBlock(randPosXX, randPosY - 1, randPosZZ) == GSBlocks.EuropaBlocks && this.world.getBlockMetadata(randPosXX, randPosY - 1, randPosZZ) == 0 && this.world.isAirBlock(randPosXX, randPosY, randPosZZ))
				{
					this.world.setBlock(randPosXX, randPosY, randPosZZ, Blocks.snow_layer);	        	
				}
			
					
		}
		*/
		/*
		for (int i = 0; i < 1600; i++) {

			int randPosY = rand.nextInt(140);
			
			
			int randPosXX = chunkX + rand.nextInt(16);
			int randPosZZ = chunkZ + rand.nextInt(16);

			if (this.world.getBlock(randPosXX, randPosY - 1, randPosZZ) == GSBlocks.EuropaBlocks && this.world.getBlockMetadata(randPosXX, randPosY - 1, randPosZZ) == 0 && this.world.isAirBlock(randPosXX, randPosY, randPosZZ))
			{
				this.world.setBlock(randPosXX, randPosY, randPosZZ, Blocks.snow_layer);	        	
			}		
		}	*/	
			/*
		for (int i = 0; i < 8; i++) {
		 
	        int randPosX = chunkX + rand.nextInt(16);
	        int randPosY = rand.nextInt(80);
	        int randPosZ = chunkZ + rand.nextInt(16);
       
	        if (this.world.getBlock(randPosX, randPosY - 1, randPosZ) == GSBlocks.EuropaBlocks && this.world.getBlockMetadata(randPosX, randPosY - 1, randPosZ) == 0 && this.world.isAirBlock(randPosX, randPosY, randPosZ))
	        {
	        	
	        	this.world.setBlock(randPosX, randPosY - 1, randPosZ, GSBlocks.EuropaGeyser);
	        	this.world.setBlock(randPosX, randPosY - 2, randPosZ, Blocks.water);
	        	this.world.setBlock(randPosX, randPosY - 3, randPosZ, Blocks.water);
	        	this.world.setBlock(randPosX, randPosY - 4, randPosZ, Blocks.water);
	        	
	        }
		}
		*/
		for (int i = 0; i < 400; i++) {
			
			 int randPosX = chunkX + rand.nextInt(16);
			 int randPosY = rand.nextInt(70);
			 int randPosZ = chunkZ + rand.nextInt(16);
			 
			 if(this.world.isAirBlock(randPosX, randPosY + 1, randPosZ) || this.world.getBlock(randPosX, randPosY + 1, randPosZ).getMaterial() == Material.water)
			 {
				 if (this.world.getBlock(randPosX, randPosY, randPosZ) == Blocks.packed_ice || this.world.getBlock(randPosX, randPosY, randPosZ) == GSBlocks.EuropaBlocks && this.world.getBlockMetadata(randPosX, randPosY, randPosZ) == 1)
				 {
					 this.world.setBlock(randPosX, randPosY, randPosZ, GSBlocks.EuropaGeyser);	
				 }				 
			 }
		}
		
		for (int i = 0; i < 40; i++) {
			
			 int randPosX = chunkX + rand.nextInt(16);
			 int randPosY = rand.nextInt(100);
			 int randPosZ = chunkZ + rand.nextInt(16);		
			 
			 if(this.world.isAirBlock(randPosX, randPosY + 1, randPosZ) 
					 && !this.world.isAirBlock(randPosX + 1, randPosY, randPosZ)
					 && !this.world.isAirBlock(randPosX - 1, randPosY, randPosZ)
					 && !this.world.isAirBlock(randPosX, randPosY, randPosZ + 1)
					 && !this.world.isAirBlock(randPosX, randPosY, randPosZ - 1))
			 {
				 if (this.world.getBlock(randPosX, randPosY, randPosZ) == Blocks.packed_ice)
				 {
					 this.world.setBlock(randPosX, randPosY, randPosZ, GSBlocks.EuropaUnderwaterGeyser);
					 this.world.setBlock(randPosX, randPosY - 1, randPosZ, Blocks.water);	
					 this.world.setBlock(randPosX, randPosY - 2, randPosZ, Blocks.water);
					 this.world.setBlock(randPosX, randPosY - 3, randPosZ, Blocks.water);	
					 this.world.setBlock(randPosX, randPosY - 4, randPosZ, Blocks.water);
				 }
				 
				 if (this.world.getBlock(randPosX, randPosY, randPosZ) == GSBlocks.EuropaBlocks && this.world.getBlockMetadata(randPosX, randPosY, randPosZ) == 0)
				 {
					 this.world.setBlock(randPosX, randPosY, randPosZ, GSBlocks.EuropaGeyser);	
					 this.world.setBlock(randPosX, randPosY - 1, randPosZ, Blocks.water);	
					 this.world.setBlock(randPosX, randPosY - 2, randPosZ, Blocks.water);
					 this.world.setBlock(randPosX, randPosY - 3, randPosZ, Blocks.water);	
					 this.world.setBlock(randPosX, randPosY - 4, randPosZ, Blocks.water);
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