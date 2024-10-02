package galaxyspace.systems.BarnardsSystem.planets.barnardaC.world.gen;

import galaxyspace.GalaxySpace;
import galaxyspace.core.world.gen.features.trees.WorldGenTree_Forest;
import galaxyspace.core.world.gen.features.trees.WorldGenTree_Forest2;
import galaxyspace.core.world.gen.features.trees.WorldGenTree_Small;
import galaxyspace.core.world.gen.features.trees.WorldGenTree_Swampland;
import galaxyspace.core.world.gen.features.trees.WorldGenTree_Swampland_2;
import galaxyspace.core.world.worldengine.WE_Biome;
import galaxyspace.systems.BarnardsSystem.core.registers.blocks.BRBlocks;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.world.gen.feature.WorldGenBarnardaCNewTree;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.world.gen.we.Barnarda_C_Dunes;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.world.gen.we.Barnarda_C_Forest;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.world.gen.we.Barnarda_C_Jungle;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.world.gen.we.Barnarda_C_Mountains;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.world.gen.we.Barnarda_C_Plains;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.world.gen.we.Barnarda_C_Swampland;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeDecoratorBarnardaC extends BiomeDecoratorSpace{
	
	private World world;
	private WorldGenerator dirtGen, gravelGen, coalGen, ironGen, goldGen, lapisGen, redstoneGen, diamondGen, siliconGen, copperGen, tinGen, aluminumGen, quartzGen, cobaltumGen, nickelGen;
	   

	public BiomeDecoratorBarnardaC()
	{
		dirtGen = new WorldGenMinableMeta(BRBlocks.BarnardaCBlocks, 30, 0, true, BRBlocks.BarnardaCBlocks, 1);
    	gravelGen = new WorldGenMinableMeta(Blocks.gravel, 15, 0, true, BRBlocks.BarnardaCBlocks, 1);
    
    	coalGen = new WorldGenMinableMeta(BRBlocks.BarnardaCOres, 15, 0, true, BRBlocks.BarnardaCBlocks, 1);
    	ironGen = new WorldGenMinableMeta(BRBlocks.BarnardaCOres, 6, 1, true, BRBlocks.BarnardaCBlocks, 1);
    	goldGen = new WorldGenMinableMeta(BRBlocks.BarnardaCOres, 6, 2, true, BRBlocks.BarnardaCBlocks, 1);
    	redstoneGen = new WorldGenMinableMeta(BRBlocks.BarnardaCOres, 6, 3, true, BRBlocks.BarnardaCBlocks, 1);
    	lapisGen = new WorldGenMinableMeta(BRBlocks.BarnardaCOres, 6, 4, true, BRBlocks.BarnardaCBlocks, 1);
    	diamondGen = new WorldGenMinableMeta(BRBlocks.BarnardaCOres, 6, 5, true, BRBlocks.BarnardaCBlocks, 1);
    	siliconGen = new WorldGenMinableMeta(BRBlocks.BarnardaCOres, 6, 6, true, BRBlocks.BarnardaCBlocks, 1);
    	copperGen = new WorldGenMinableMeta(BRBlocks.BarnardaCOres, 8, 7, true, BRBlocks.BarnardaCBlocks, 1);
    	tinGen = new WorldGenMinableMeta(BRBlocks.BarnardaCOres, 8, 8, true, BRBlocks.BarnardaCBlocks, 1);
    	aluminumGen = new WorldGenMinableMeta(BRBlocks.BarnardaCOres, 5, 9, true, BRBlocks.BarnardaCBlocks, 1);
    	quartzGen = new WorldGenMinableMeta(BRBlocks.BarnardaCOres, 4, 10, true, BRBlocks.BarnardaCBlocks, 1);
    	cobaltumGen = new WorldGenMinableMeta(BRBlocks.BarnardaCOres, 2, 11, true, BRBlocks.BarnardaCBlocks, 1);
    	nickelGen = new WorldGenMinableMeta(BRBlocks.BarnardaCOres, 4, 12, true, BRBlocks.BarnardaCBlocks, 1);
	}	

	@Override
	protected void decorate()
	{
		int randPosX = this.chunkX + this.rand.nextInt(16);
		int randPosZ = this.chunkZ + this.rand.nextInt(16);
		int y = this.world.getTopSolidOrLiquidBlock(randPosX, randPosZ);
		//BlockPos pos = this.currentWorld.getHeight(new BlockPos(randPosX, 0, randPosZ));
		
		this.generateOre(30, dirtGen, 5, 180);
		this.generateOre(15, gravelGen, 5, 180);
		
		this.generateOre(15, ironGen, 5, 100);
		this.generateOre(10, goldGen, 5, 45);
		this.generateOre(20, redstoneGen, 5, 25);
		this.generateOre(4, lapisGen, 5, 40);
		this.generateOre(8, diamondGen, 5, 15);
		this.generateOre(8, siliconGen, 5, 40);
		this.generateOre(20, copperGen, 5, 120);
		this.generateOre(15, tinGen, 5, 120);
		this.generateOre(10, aluminumGen, 5, 50);
		this.generateOre(8, quartzGen, 5, 15);
		this.generateOre(8, cobaltumGen, 5, 40);
		this.generateOre(8, nickelGen, 5, 15);
				
		for(int i = 0; i < 60; i++){
			randPosX = this.chunkX + this.rand.nextInt(16) + 8;
			randPosZ = this.chunkZ + this.rand.nextInt(16) + 8;
			y = this.world.getTopSolidOrLiquidBlock(randPosX, randPosZ);
			if(getBiome(randPosX, randPosZ) instanceof Barnarda_C_Forest)
	    	{
				if(world.getBlock(randPosX, y - 1, randPosZ) == BRBlocks.BarnardaCGrass)				
					world.setBlock(randPosX, y, randPosZ, BRBlocks.BarnardaCDandelions, (world.rand.nextInt(40) < 2) ? 0 : 3, 3);
				
			}
		}
			
			for(int i = 0; i < 6; i++){
				randPosX = this.chunkX + this.rand.nextInt(16) + 8 + 3;
				randPosZ = this.chunkZ + this.rand.nextInt(16) + 8 + 3;
				y = this.world.getTopSolidOrLiquidBlock(randPosX, randPosZ);
				boolean cangen = true;
				if(getBiome(randPosX, randPosZ) instanceof Barnarda_C_Forest)
		    	{
					for(int x = -3; x < 3; x++)
						for(int z = -3; z < 3; z++)
							if(world.getBlock(randPosX + x, y - 1, randPosZ + z) == BRBlocks.BarnardaCLog)
								cangen = false;
						
					if(cangen && y > 50 && world.getBlock(randPosX, y - 1, randPosZ) == BRBlocks.BarnardaCGrass && world.getBlockMetadata(randPosX, y - 1, randPosZ) == 1)
		    		{
						
						switch(rand.nextInt(2))
						{
							case 0:						
								new WorldGenTree_Forest(BRBlocks.BarnardaCLog, 0, BRBlocks.BarnardaCLeaves, 0, rand.nextInt(3)).generate(getCurrentWorld(), rand, randPosX, y, randPosZ);
								break;
							case 1:
								new WorldGenTree_Forest2(BRBlocks.BarnardaCLog, 0, BRBlocks.BarnardaCLeaves, 0, rand.nextInt(3)).generate(getCurrentWorld(), rand, randPosX, y, randPosZ);
								break;
						}
		    		}
		    	}
			}
			
			for(int i = 0; i < 10; i++){
				randPosX = this.chunkX + this.rand.nextInt(16) + 8;
				randPosZ = this.chunkZ + this.rand.nextInt(16) + 8;
				if(getBiome(randPosX, randPosZ) instanceof Barnarda_C_Forest || getBiome(randPosX, randPosZ) instanceof Barnarda_C_Jungle)
		    	{
					for(int yPos = 0; yPos < 255; yPos++)				
						if(world.getBlock(randPosX, yPos + 1, randPosZ) == BRBlocks.BarnardaCLeaves && world.isAirBlock(randPosX, yPos - 1, randPosZ))				
							world.setBlock(randPosX, yPos, randPosZ, BRBlocks.BarnardaCDandelions, 1, 3);
		    	}
			}
			
			for(int i = 0; i < 40; i++){
        		randPosX = this.chunkX + this.rand.nextInt(16) + 8;
    			//int randPosY = this.rand.nextInt(256);
    			randPosZ = this.chunkZ + this.rand.nextInt(16) + 8;
    			y = this.world.getTopSolidOrLiquidBlock(randPosX, randPosZ);
    			//for(y = 0; y < 255; y++) {
				boolean cangen = false;
				int range = 2;
				if(getBiome(randPosX, randPosZ) instanceof Barnarda_C_Forest)
		    	{
					for(int yPos = y - range; yPos < y + range; yPos++) {
						if(cangen) break;
						
						for (int x = -range; x < range; x++)					
							for (int z = -range; z < range; z++)
								if(x != 0 && z != 0)
								if (this.getCurrentWorld().getBlock(randPosX + x, yPos, randPosZ + z).getMaterial() == Material.water)
									cangen = true;
					}
	
					Block getBlock = this.getCurrentWorld().getBlock(randPosX, y - 1, randPosZ);
					int metaBlock = this.getCurrentWorld().getBlockMetadata(randPosX, y - 1, randPosZ);
					
					if (cangen && (getBlock == BRBlocks.BarnardaCGrass || (getBlock == BRBlocks.BarnardaCBlocks && metaBlock == 0))) {
						this.getCurrentWorld().setBlock(randPosX, y, randPosZ, BRBlocks.BarnardaCDandelions, 2, 3);
					}
		    	}
			}
		
		for(int i = 0; i < 180; i++){
		    	randPosX = this.chunkX + this.rand.nextInt(16) + 8;
					//int randPosY = this.rand.nextInt(256);
				randPosZ = this.chunkZ + this.rand.nextInt(16) + 8;
				y = this.world.getTopSolidOrLiquidBlock(randPosX, randPosZ);
		    		
			if(getBiome(randPosX, randPosZ) instanceof Barnarda_C_Plains)
		    {
	    			if(this.getCurrentWorld().getBlock(randPosX, y - 1, randPosZ) == BRBlocks.BarnardaCGrass)
	        			this.getCurrentWorld().setBlock(randPosX, y, randPosZ, BRBlocks.BarnardaCDandelions, 3, 3);
  
		    }
	    }
		
		if(getBiome(randPosX, randPosZ) instanceof Barnarda_C_Mountains)
    	{
			if(rand.nextInt(2) == 0) 
			{					
				randPosX = this.chunkX + this.rand.nextInt(16) + 8;
				randPosZ = this.chunkZ + this.rand.nextInt(16) + 8;
				y = this.world.getTopSolidOrLiquidBlock(randPosX, randPosZ);
				
				if(y > 120 && y < 125)
	    		{
	    			this.getCurrentWorld().setBlock(randPosX, y, randPosZ, Blocks.flowing_water);
	    		}	
			}
			
			for(int i = 0; i < 40; i++){
				randPosX = this.chunkX + this.rand.nextInt(16);
				randPosZ = this.chunkZ + this.rand.nextInt(16);
				y = this.world.getTopSolidOrLiquidBlock(randPosX, randPosZ);
	    		
	    		//if(this.getCurrentWorld().isAreaLoaded(pos, 13, false))
	    		new WorldGenMinableMeta(BRBlocks.BarnardaCBlocks, 25, 1, true, BRBlocks.BarnardaCGrass, 0).generate(getCurrentWorld(), rand, randPosX, y, randPosZ);
	    		new WorldGenMinableMeta(BRBlocks.BarnardaCBlocks, 15, 1, true, BRBlocks.BarnardaCBlocks, 0).generate(getCurrentWorld(), rand, randPosX, y, randPosZ);
	    	}
			
			for(int i = 0; i < 2; i++){
				randPosX = this.chunkX + this.rand.nextInt(16);
				randPosZ = this.chunkZ + this.rand.nextInt(16);
				y = this.world.getTopSolidOrLiquidBlock(randPosX, randPosZ);
	    		
				if(!world.getChunkFromBlockCoords(randPosX, randPosZ).isChunkLoaded && world.isAirBlock(randPosX, y, randPosZ) && world.getBlock(randPosX, y - 1, randPosZ) == BRBlocks.BarnardaCGrass)				
					world.setBlock(randPosX, y, randPosZ, BRBlocks.BarnardaCDandelions, 3, 3);
				
			}
			
			if(this.rand.nextInt(10) == 0) {
        		randPosX = this.chunkX + this.rand.nextInt(16) + 8;
    			randPosZ = this.chunkZ + this.rand.nextInt(16) + 8;
    			y = this.world.getTopSolidOrLiquidBlock(randPosX, randPosZ);
    			
    			if(this.getCurrentWorld().getBlock(randPosX, y - 1, randPosZ) == BRBlocks.BarnardaCGrass)
    				new WorldGenTree_Small(BRBlocks.BarnardaCLog, 0, BRBlocks.BarnardaCLeaves, 0, rand.nextInt(3)).generate(getCurrentWorld(), rand, randPosX, y, randPosZ);
	    	}
			
			for(int i = 0; i < 10; i++){
				randPosX = this.chunkX + this.rand.nextInt(16) + 8;
				randPosZ = this.chunkZ + this.rand.nextInt(16) + 8;
				y = this.world.getTopSolidOrLiquidBlock(randPosX, randPosZ);
				
				if(world.getBlock(randPosX, y - 1, randPosZ) == BRBlocks.BarnardaCGrass)				
					world.setBlock(randPosX, y, randPosZ, BRBlocks.BarnardaCDandelions, 3, 3);
				
			}
    	}
		
		if(getBiome(randPosX, randPosZ) instanceof Barnarda_C_Swampland)
    	{
			for(int i = 0; i < 2; i++){
				randPosX = this.chunkX + this.rand.nextInt(16) + 8 + 3;
				randPosZ = this.chunkZ + this.rand.nextInt(16) + 8 + 3;
				y = this.world.getTopSolidOrLiquidBlock(randPosX, randPosZ);
				boolean cangen = true;
				
				for(int x = -3; x < 3; x++)
					for(int z = -3; z < 3; z++)
						if(world.getBlock(randPosX + x, y - 1, randPosZ + z) == BRBlocks.BarnardaCLog)
							cangen = false;
					
				if(cangen && y > 50 && world.getBlock(randPosX, y - 1, randPosZ) == BRBlocks.BarnardaCGrass)
	    		{
					
					switch(rand.nextInt(2))
					{
						case 0:						
							new WorldGenTree_Swampland(BRBlocks.BarnardaCLog, 0, BRBlocks.BarnardaCLeaves, 0, rand.nextInt(3)).generate(getCurrentWorld(), rand, randPosX, y, randPosZ);
							break;
						case 1:
							new WorldGenTree_Swampland_2(BRBlocks.BarnardaCLog, 0, BRBlocks.BarnardaCLeaves, 0, rand.nextInt(3)).generate(getCurrentWorld(), rand, randPosX, y, randPosZ);
							break;
					}
	    		}
			}
			
			for(int i = 0; i < 16; i++){
	    		for(int k = 0; k < 16; k++){
					randPosX = this.chunkX + i + 8;
					randPosZ = this.chunkZ + k + 8;					
					y = this.world.getTopSolidOrLiquidBlock(randPosX, randPosZ);
					
					if(this.getCurrentWorld().isAirBlock(randPosX, y + 1, randPosZ) && this.getCurrentWorld().getBlock(randPosX, y, randPosZ).getMaterial() == Material.water && this.rand.nextInt(2) == 0)
					{
						this.getCurrentWorld().setBlock(randPosX, y + 1, randPosZ, BRBlocks.BarnardaCWaterGrass, 0, 3);
					}
	    		}
			}
			
			for(int i = 0; i < 40; i++){
        		randPosX = this.chunkX + this.rand.nextInt(16) + 8;
    			//int randPosY = this.rand.nextInt(256);
    			randPosZ = this.chunkZ + this.rand.nextInt(16) + 8;
    			y = this.world.getTopSolidOrLiquidBlock(randPosX, randPosZ);
    			//for(y = 0; y < 255; y++) {
				boolean cangen = false;
				int range = 2;
				
				
					
				for (int x = -range; x < range; x++)
				{
					if(cangen) break;
					for (int z = -range; z < range; z++)
						if (this.getCurrentWorld().getBlock(randPosX + x, y - 1, randPosZ + z)
								.getMaterial() == Material.water)
							cangen = true;
				}
				Block getBlock = this.getCurrentWorld().getBlock(randPosX, y - 1, randPosZ);
				int metaBlock = this.getCurrentWorld().getBlockMetadata(randPosX, y - 1, randPosZ);
				
				if (cangen &&  this.getCurrentWorld().isAirBlock(randPosX, y, randPosZ) && (getBlock == BRBlocks.BarnardaCGrass || (getBlock == BRBlocks.BarnardaCBlocks && metaBlock == 0))) {
					
					if(rand.nextInt(15) == 0)
    				{
						for(int size = 0; size < 2 + rand.nextInt(2); size++)
    					{
    						if(rand.nextBoolean())
    							this.getCurrentWorld().setBlock(randPosX, y + size, randPosZ, BRBlocks.BarnardaCDandelions, 6, 3);
    						else
    							this.getCurrentWorld().setBlock(randPosX, y + size, randPosZ, BRBlocks.BarnardaCDandelions, 7, 3);
    					}
    				}
					else
						this.getCurrentWorld().setBlock(randPosX, y, randPosZ, BRBlocks.BarnardaCDandelions, 2, 3);
				}
    			
        	}
    	}
		
			
		
				
			
		for (int i = 0; i < 4; i++) {

			randPosX = this.chunkX + this.rand.nextInt(16) + 8;
			randPosZ = this.chunkZ + this.rand.nextInt(16) + 8;
			y = this.world.getTopSolidOrLiquidBlock(randPosX, randPosZ);

			boolean cangen = false;
			int range = 2;
			for (int x = -range; x < range; x++)
			{
				if(cangen) break;
				for (int z = -range; z < range; z++)
					if (this.getCurrentWorld().getBlock(randPosX + x, y - 1, randPosZ + z)
							.getMaterial() == Material.air || y < 64)
						cangen = true;
			}
			
			if (!cangen && getBiome(randPosX, randPosZ) instanceof Barnarda_C_Jungle) {
				if (rand.nextInt(50) == 0)
					new WorldGenBarnardaCNewTree(BRBlocks.BarnardaCLog, BRBlocks.BarnardaCLeaves, 0, 0, 2, 1)
							.generate(this.getCurrentWorld(), this.rand, randPosX, y, randPosZ);
				else
					new WorldGenBarnardaCNewTree(BRBlocks.BarnardaCLog, BRBlocks.BarnardaCLeaves, 0, 0, 1,
							rand.nextInt(2) + 1).generate(this.getCurrentWorld(), this.rand, randPosX, y, randPosZ);

			}
		}
		for(int i = 0; i < 10; i++){
			randPosX = this.chunkX + this.rand.nextInt(16) + 8;
			randPosZ = this.chunkZ + this.rand.nextInt(16) + 8;
			y = this.world.getTopSolidOrLiquidBlock(randPosX, randPosZ);
			
			if (getBiome(randPosX, randPosZ) instanceof Barnarda_C_Jungle) {
				if(this.getCurrentWorld().getBlock(randPosX, y - 1, randPosZ) == BRBlocks.BarnardaCGrass)
					new WorldGenTree_Small(BRBlocks.BarnardaCLog, 0, BRBlocks.BarnardaCLeaves, 0, rand.nextInt(3)).generate(getCurrentWorld(), rand, randPosX, y, randPosZ);
			}
		}
		
		for(int i = 0; i < 60; i++){
			randPosX = this.chunkX + this.rand.nextInt(16) + 8;
			randPosZ = this.chunkZ + this.rand.nextInt(16) + 8;
			y = this.world.getTopSolidOrLiquidBlock(randPosX, randPosZ);
			
			boolean cangen = false;
			int range = 1;
			for (int x = -range; x < range; x++)
			{
				if(cangen) break;
				for (int z = -range; z < range; z++)
					if (this.getCurrentWorld().getBlock(randPosX + x, y - 1, randPosZ + z)
							.getMaterial() == Material.air || y < 64)
						cangen = true;
			}
			
			if(!cangen && getBiome(randPosX, randPosZ) instanceof Barnarda_C_Jungle)
			{
				if(world.getBlock(randPosX, y - 1, randPosZ) == BRBlocks.BarnardaCGrass)				
					world.setBlock(randPosX, y, randPosZ, BRBlocks.BarnardaCDandelions, (world.rand.nextInt(40) < 2) ? 0 : 3, 3);
			}
		}	
		
		//TODO DUNES FKING GEN!
		
		for(int i = 0; i < 1; i++){
			randPosX = this.chunkX + this.rand.nextInt(16) + 8;
			randPosZ = this.chunkZ + this.rand.nextInt(16) + 8;
			y = this.world.getTopSolidOrLiquidBlock(randPosX, randPosZ);
			
			if(getBiome(randPosX, randPosZ) instanceof Barnarda_C_Dunes)
			{
				if(rand.nextInt(10) == 0) {
					world.setBlock(randPosX, y, randPosZ, BRBlocks.BarnardaCDandelions, 4, 3);
					world.setBlock(randPosX, y + 1, randPosZ, BRBlocks.BarnardaCDandelions, 5, 3);
				}
				else world.setBlock(randPosX, y, randPosZ, BRBlocks.BarnardaCDandelions, 5, 3);
			}
		}
		/*
		for(int i = 0; i < 1; i++){*/
		randPosX = this.chunkX + this.rand.nextInt(16) + 7;
		randPosZ = this.chunkZ + this.rand.nextInt(16) + 7;
		y = this.world.getTopSolidOrLiquidBlock(randPosX, randPosZ);
			
		for(int xPos = -1; xPos < 1; xPos++)
			for(int zPos = -1; zPos < 1; zPos++)
				if(!world.isAirBlock(xPos, y + 1, zPos)) continue;
			
			if(getBiome(randPosX, randPosZ) instanceof Barnarda_C_Dunes)
			{
				for(int height = 0; height < rand.nextInt(5); height++)				
					world.setBlock(randPosX, y + height, randPosZ, Blocks.cactus);
				
			}
		//}
		
		this.generateOre(20, coalGen, 5, 180);		
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
	
	private WE_Biome getBiome(int x, int z)
    {
    	return WE_Biome.getBiomeAt(x, z);
    }
}
