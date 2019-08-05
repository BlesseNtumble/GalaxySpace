package galaxyspace.systems.ACentauriSystem.planets.proxima_b.world.gen;

import asmodeuscore.core.astronomy.dimension.world.gen.features.trees.WorldGenTree_BigJungle;
import asmodeuscore.core.astronomy.dimension.world.gen.features.trees.WorldGenTree_BigJungle2;
import asmodeuscore.core.astronomy.dimension.world.gen.features.trees.WorldGenTree_Forest;
import asmodeuscore.core.astronomy.dimension.world.gen.features.trees.WorldGenTree_Forest2;
import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_Biome;
import galaxyspace.GalaxySpace;
import galaxyspace.core.prefab.world.gen.WorldGenPool;
import galaxyspace.systems.ACentauriSystem.core.registers.blocks.ACBlocks;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.blocks.Proxima_B_Blocks;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.dimension.WorldProviderProxima_B_WE;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.world.gen.features.WorldGenFrozenTree;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.world.gen.we.Proxima_B_Forest;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.world.gen.we.Proxima_B_Ice_Plains;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.core.GCFluids;
import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeDecoratorProxima_B extends BiomeDecoratorSpace
{
    private World currentWorld;
    private WorldGenerator tree_1, coalOre, goldOre, tinOre, copperOre, siliconOre, diamondOre, subsurfaceGen;
   
    public BiomeDecoratorProxima_B()
    {
    	tree_1 = new WorldGenFrozenTree(ACBlocks.PROXINA_B_LOG_2.getStateFromMeta(0), Blocks.AIR.getDefaultState(), 0);
    	goldOre = new WorldGenMinableMeta(ACBlocks.PROXIMA_B_BLOCKS, 5, 5, true, ACBlocks.PROXIMA_B_BLOCKS, 2);
    	tinOre = new WorldGenMinableMeta(ACBlocks.PROXIMA_B_BLOCKS, 6, 6, true, ACBlocks.PROXIMA_B_BLOCKS, 2);
		coalOre = new WorldGenMinableMeta(ACBlocks.PROXIMA_B_BLOCKS, 10, 8, true, ACBlocks.PROXIMA_B_BLOCKS, 2);
		copperOre = new WorldGenMinableMeta(ACBlocks.PROXIMA_B_BLOCKS, 6, 7, true, ACBlocks.PROXIMA_B_BLOCKS, 2);
		siliconOre = new WorldGenMinableMeta(ACBlocks.PROXIMA_B_BLOCKS, 4, 9, true, ACBlocks.PROXIMA_B_BLOCKS, 2);
		diamondOre = new WorldGenMinableMeta(ACBlocks.PROXIMA_B_BLOCKS, 3, 10, true, ACBlocks.PROXIMA_B_BLOCKS, 2);
		subsurfaceGen = new WorldGenMinableMeta(ACBlocks.PROXIMA_B_BLOCKS, 14, 1, true, ACBlocks.PROXIMA_B_BLOCKS, 2);
    }

    @Override
    protected void decorate()
    {
    	this.generateOre(15, coalOre, 20, 90);
		this.generateOre(8, tinOre, 10, 80);
		this.generateOre(10, copperOre, 20, 90);
		this.generateOre(5, siliconOre, 0, 30);
		this.generateOre(5, goldOre, 0, 30);
		this.generateOre(2, diamondOre, 0, 20);
		this.generateOre(150, subsurfaceGen, 30, 100);
		
		BlockPos blockpos = new BlockPos(this.posX, 0, this.posZ);
		
    	if(WE_Biome.getBiomeAt(this.posX, this.posZ) instanceof Proxima_B_Ice_Plains)
	    {
    		
    		int randPosX = this.posX + this.rand.nextInt(16) + 8;
			int randPosZ = this.posZ + this.rand.nextInt(16) + 8;
			BlockPos pos = this.currentWorld.getHeight(new BlockPos(randPosX, 0, randPosZ));
			
			if(!this.getCurrentWorld().isAreaLoaded(pos, 13, false))
			{
				boolean cangen = true;
				
				for(BlockPos pos1 : pos.getAllInBox(pos.add(-3, -1, -3), pos.add(3, -1, 3)))
					if(this.getCurrentWorld().isAirBlock(pos1)) 
						cangen = false;
				
				if(cangen && this.getCurrentWorld().getBlockState(pos.down()) == ACBlocks.PROXIMA_B_BLOCKS.getDefaultState().withProperty(Proxima_B_Blocks.BASIC_TYPE, Proxima_B_Blocks.EnumBlockProximaB.ICE_SURFACE))
    			{
    				new WorldGenTree_BigJungle(ACBlocks.PROXINA_B_LOG_2.getStateFromMeta(0), Blocks.AIR.getDefaultState(), rand.nextInt(3)).generate(getCurrentWorld(), rand, pos);
    			}
			}
	    	/*for(int i = 0; i < 4; i++) {
	    		int randPosX = this.posX + this.rand.nextInt(16) + 8;
				int randPosY = this.rand.nextInt(256);
				int randPosZ = this.posZ + this.rand.nextInt(16) + 8;
				BlockPos pos = this.currentWorld.getHeight(new BlockPos(randPosX, 0, randPosZ));
				
					if(!this.getCurrentWorld().isAreaLoaded(pos, 10, false) && this.getCurrentWorld().getBlockState(pos.down()) == ACBlocks.PROXIMA_B_BLOCKS.getDefaultState().withProperty(Proxima_B_Blocks.BASIC_TYPE, Proxima_B_Blocks.EnumBlockProximaB.ICE_SURFACE))
						new WorldGenFrozenTree(ACBlocks.PROXINA_B_LOG_2.getStateFromMeta(0), Blocks.AIR.getDefaultState(), 1 + rand.nextInt(2)).generate(getCurrentWorld(), rand, pos);
					
	    	}
	    	
	    	if(rand.nextInt(5) == 0){
	    		int randPosX = this.posX + this.rand.nextInt(16) + 8;
				int randPosY = this.rand.nextInt(256);
				int randPosZ = this.posZ + this.rand.nextInt(16) + 8;
				BlockPos pos = this.currentWorld.getHeight(new BlockPos(randPosX, 0, randPosZ));
				
				if(!this.getCurrentWorld().isAreaLoaded(pos, 14, false))
	    			if(this.getCurrentWorld().getBlockState(pos.down()) == ACBlocks.PROXIMA_B_BLOCKS.getDefaultState().withProperty(Proxima_B_Blocks.BASIC_TYPE, Proxima_B_Blocks.EnumBlockProximaB.ICE_SURFACE))
	    			{
	    				tree_1.generate(getCurrentWorld(), rand, pos);
	    			}
	    	}*/
	    }
    	    	
    	if(WE_Biome.getBiomeAt(this.posX, this.posZ) instanceof Proxima_B_Forest)
    	{
    		
    		int randPosX = this.posX + this.rand.nextInt(16) + 8;
			int randPosZ = this.posZ + this.rand.nextInt(16) + 8;
			BlockPos pos = this.currentWorld.getHeight(new BlockPos(randPosX, 0, randPosZ));
			
			boolean cangen = true;
			
			for(BlockPos pos1 : pos.getAllInBox(pos.add(-3, -1, -3), pos.add(3, -1, 3)))
				if(this.getCurrentWorld().isAirBlock(pos1)) 
					cangen = false;
			
			if(!this.getCurrentWorld().isAreaLoaded(pos, 13, false))
				if(cangen && this.getCurrentWorld().getBlockState(pos.down()) == ACBlocks.PROXIMA_B_BLOCKS.getDefaultState().withProperty(Proxima_B_Blocks.BASIC_TYPE, Proxima_B_Blocks.EnumBlockProximaB.SURFACE))
    			{
					switch(rand.nextInt(2))
					{
						case 0:
							new WorldGenTree_BigJungle(ACBlocks.PROXINA_B_LOG_1.getStateFromMeta(0), Blocks.AIR.getDefaultState(), rand.nextInt(3)).generate(getCurrentWorld(), rand, pos);
					    	break;
						case 1:
							new WorldGenTree_BigJungle2(ACBlocks.PROXINA_B_LOG_1.getStateFromMeta(0), Blocks.AIR.getDefaultState(), rand.nextInt(3)).generate(getCurrentWorld(), rand, pos);
							break;
					}
    				//new WorldGenTree_BigJungle(ACBlocks.PROXINA_B_LOG_1.getStateFromMeta(0), Blocks.AIR.getDefaultState(), rand.nextInt(3)).generate(getCurrentWorld(), rand, pos);
    			}

			for(int i = 0; i < 8; i++){
				randPosX = this.posX + this.rand.nextInt(16) + 8;
				randPosZ = this.posZ + this.rand.nextInt(16) + 8;
				pos = this.currentWorld.getHeight(new BlockPos(randPosX, 0, randPosZ));
				
				cangen = true;
				for(BlockPos pos1 : pos.getAllInBox(pos.add(-3, -1, -3), pos.add(3, -1, 3)))
					if(this.getCurrentWorld().isAirBlock(pos1) || this.getCurrentWorld().getBlockState(pos1) == ACBlocks.PROXINA_B_LOG_1.getStateFromMeta(0)) 
						cangen = false;
				
	    		if(!this.getCurrentWorld().isAreaLoaded(pos, 13, false))
		    		if(cangen && this.getCurrentWorld().getBlockState(pos.down()) == ACBlocks.PROXIMA_B_BLOCKS.getDefaultState().withProperty(Proxima_B_Blocks.BASIC_TYPE, Proxima_B_Blocks.EnumBlockProximaB.SURFACE))
		    		{
		    			switch(rand.nextInt(2))
						{
							case 0:						
								new WorldGenTree_Forest(ACBlocks.PROXINA_B_LOG_1.getStateFromMeta(0), Blocks.AIR.getDefaultState(), rand.nextInt(3)).generate(getCurrentWorld(), rand, pos);
								break;
							case 1:
								new WorldGenTree_Forest2(ACBlocks.PROXINA_B_LOG_1.getStateFromMeta(0), Blocks.AIR.getDefaultState(), rand.nextInt(3)).generate(getCurrentWorld(), rand, pos);
								break;
						}
					}
			}
    		/*
    		for(int i = 0; i < 1; i++){
    			int randPosX = this.posX + this.rand.nextInt(16);
				int randPosY = this.rand.nextInt(256);
				int randPosZ = this.posZ + this.rand.nextInt(16);
				BlockPos pos = this.currentWorld.getHeight(new BlockPos(randPosX, 0, randPosZ));
    			
    			if(!this.getCurrentWorld().isAreaLoaded(pos.add(-8, 0, -8), 14, false))
	    			if(this.getCurrentWorld().getBlockState(pos.down()) == ACBlocks.PROXIMA_B_BLOCKS.getDefaultState().withProperty(Proxima_B_Blocks.BASIC_TYPE, Proxima_B_Blocks.EnumBlockProximaB.SURFACE))
	    			{
	    				new WorldGenFrozenTree(ACBlocks.PROXINA_B_LOG_1.getStateFromMeta(0), Blocks.AIR.getDefaultState(), 0).generate(getCurrentWorld(), rand, pos);
	    			}
    		}*/
    	}
    	
    	for(int i = 0; i < 3; i++){
    		int randPosX = this.posX + this.rand.nextInt(16) + 8;
			int randPosY = this.rand.nextInt(256);
			int randPosZ = this.posZ + this.rand.nextInt(16) + 8;
			BlockPos pos = this.currentWorld.getHeight(new BlockPos(randPosX, 0, randPosZ));
			
			if(this.getCurrentWorld().getBlockState(pos.down()) == ACBlocks.PROXIMA_B_BLOCKS.getDefaultState().withProperty(Proxima_B_Blocks.BASIC_TYPE, Proxima_B_Blocks.EnumBlockProximaB.SURFACE))
    			this.getCurrentWorld().setBlockState(pos, Blocks.DEADBUSH.getDefaultState());
    	}
    	
    	int x = posX + rand.nextInt(16) + 8;
		int z = posZ + rand.nextInt(16) + 8;    
		
    	if (this.rand.nextInt(50) == 0)
        {
    		int l2 = this.getCurrentWorld().getTopSolidOrLiquidBlock(new BlockPos(x, 0, z)).getY() - 20 - this.rand.nextInt(25);
    		GalaxySpace.debug(new BlockPos(x, l2, z) + "");
    		//(new WorldGenVaporPool()).generate(this.getCurrentWorld(), this.rand, new BlockPos(x, l2, z));
            new WorldGenPool(ACBlocks.PROXIMA_B_BLOCKS.getDefaultState().withProperty(Proxima_B_Blocks.BASIC_TYPE, Proxima_B_Blocks.EnumBlockProximaB.STONE), GCFluids.fluidOil.getBlock().getDefaultState(), 5).generate(this.getCurrentWorld(), this.rand, new BlockPos(x, l2, z));
        }
    }

    
    protected void generateOre(int amountPerChunk, WorldGenerator worldGenerator, int minY, int maxY)
    {
        World currentWorld = this.getCurrentWorld();
		for (int var5 = 0; var5 < amountPerChunk; ++var5)
        {
            final int var6 = this.posX + this.rand.nextInt(16);
            final int var7 = this.rand.nextInt(maxY - minY) + minY;
            final int var8 = this.posZ + this.rand.nextInt(16);

            worldGenerator.generate(currentWorld, this.rand, new BlockPos(var6, var7, var8));
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
