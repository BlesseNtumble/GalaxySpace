package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen;

import asmodeuscore.core.astronomy.dimension.world.gen.features.trees.WorldGenTree_BigJungle;
import asmodeuscore.core.astronomy.dimension.world.gen.features.trees.WorldGenTree_Forest;
import asmodeuscore.core.astronomy.dimension.world.gen.features.trees.WorldGenTree_Forest2;
import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_Biome;
import galaxyspace.systems.ACentauriSystem.core.registers.blocks.ACBlocks;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.blocks.Proxima_B_Blocks;
import galaxyspace.systems.BarnardsSystem.core.registers.blocks.BRBlocks;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Dandelions;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Grass;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.dimension.WorldProviderBarnarda_C_WE;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.we.Barnarda_C_Forest;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.we.Barnarda_C_Plains;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BiomeDecoratorBarnarda_C extends BiomeDecoratorSpace
{
    private World currentWorld;  
   
    public BiomeDecoratorBarnarda_C()
    {
    }

    @Override
    protected void decorate()
    {
    	
    	int randPosX = this.posX + this.rand.nextInt(16) + 8;
		int randPosZ = this.posZ + this.rand.nextInt(16) + 8;
		
		if(getBiome(randPosX, randPosZ) instanceof Barnarda_C_Forest)
    	{
    		for(int i = 0; i < 70; i++){
        		randPosX = this.posX + this.rand.nextInt(16) + 8;
    			//int randPosY = this.rand.nextInt(256);
    			randPosZ = this.posZ + this.rand.nextInt(16) + 8;
    			BlockPos pos = this.currentWorld.getHeight(new BlockPos(randPosX, 0, randPosZ));
    			
    			if(this.getCurrentWorld().getBlockState(pos.down()) == BRBlocks.BARNARDA_C_GRASS.getDefaultState().withProperty(Barnarda_C_Grass.BASIC_TYPE, Barnarda_C_Grass.EnumBlockGrass.GRASS))
        			this.getCurrentWorld().setBlockState(pos, BRBlocks.BARNARDA_C_DANDELIONS.getDefaultState().withProperty(Barnarda_C_Dandelions.BASIC_TYPE, Barnarda_C_Dandelions.EnumBlockDandelions.GRASS));
        	}
    		
    		if(rand.nextInt(5) == 0){
        		randPosX = this.posX + this.rand.nextInt(16) + 8;
    			//int randPosY = this.rand.nextInt(256);
    			randPosZ = this.posZ + this.rand.nextInt(16) + 8;
    			BlockPos pos = this.currentWorld.getHeight(new BlockPos(randPosX, 0, randPosZ));
    			
    			if(this.getCurrentWorld().getBlockState(pos.down()) == BRBlocks.BARNARDA_C_GRASS.getDefaultState().withProperty(Barnarda_C_Grass.BASIC_TYPE, Barnarda_C_Grass.EnumBlockGrass.GRASS))
        			this.getCurrentWorld().setBlockState(pos, BRBlocks.BARNARDA_C_DANDELIONS.getDefaultState().withProperty(Barnarda_C_Dandelions.BASIC_TYPE, Barnarda_C_Dandelions.EnumBlockDandelions.HOPPER));
        	}
    		
    		BlockPos pos = this.currentWorld.getHeight(new BlockPos(randPosX, 0, randPosZ));
			
			if(!this.getCurrentWorld().isAreaLoaded(pos, 13, false))
			{
				boolean cangen = true;
				
				for(BlockPos pos1 : pos.getAllInBox(pos.add(-3, -1, -3), pos.add(3, -1, 3)))
					if(this.getCurrentWorld().isAirBlock(pos1)) 
						cangen = false;
				
				if(cangen && this.getCurrentWorld().getBlockState(pos.down()) == BRBlocks.BARNARDA_C_GRASS.getDefaultState().withProperty(Barnarda_C_Grass.BASIC_TYPE, Barnarda_C_Grass.EnumBlockGrass.GRASS))
    			{
    				new WorldGenTree_BigJungle(BRBlocks.BARNARDA_C_TEST_GLOW_LOG.getStateFromMeta(0), BRBlocks.BARNARDA_C_LEAVES.getStateFromMeta(0), rand.nextInt(3)).generate(getCurrentWorld(), rand, pos);
    			}
			}
			
			for(int i = 0; i < 35; i++){
				randPosX = this.posX + this.rand.nextInt(16) + 8;
				randPosZ = this.posZ + this.rand.nextInt(16) + 8;
				pos = this.currentWorld.getHeight(new BlockPos(randPosX, 0, randPosZ));
				
				boolean cangen = true;
				
				cangen = true;
				for(BlockPos pos1 : pos.getAllInBox(pos.add(-3, -1, -3), pos.add(3, -1, 3)))
					if(this.getCurrentWorld().isAirBlock(pos1) || this.getCurrentWorld().getBlockState(pos1) == BRBlocks.BARNARDA_C_TEST_GLOW_LOG.getStateFromMeta(0)) 
						cangen = false;
				
	    		if(!this.getCurrentWorld().isAreaLoaded(pos, 13, false))
		    		if(cangen && this.getCurrentWorld().getBlockState(pos.down()) == BRBlocks.BARNARDA_C_GRASS.getDefaultState().withProperty(Barnarda_C_Grass.BASIC_TYPE, Barnarda_C_Grass.EnumBlockGrass.GRASS))
		    		{
		    			switch(rand.nextInt(2))
						{
							case 0:						
								new WorldGenTree_Forest(BRBlocks.BARNARDA_C_TEST_GLOW_LOG.getStateFromMeta(0), BRBlocks.BARNARDA_C_LEAVES.getStateFromMeta(0), rand.nextInt(3)).generate(getCurrentWorld(), rand, pos);
								break;
							case 1:
								new WorldGenTree_Forest2(BRBlocks.BARNARDA_C_TEST_GLOW_LOG.getStateFromMeta(0), BRBlocks.BARNARDA_C_LEAVES.getStateFromMeta(0), rand.nextInt(3)).generate(getCurrentWorld(), rand, pos);
								break;
						}
					}
			}
	    }
    	
    	for(int i = 0; i < 180; i++){
    		randPosX = this.posX + this.rand.nextInt(16) + 8;
			//int randPosY = this.rand.nextInt(256);
			randPosZ = this.posZ + this.rand.nextInt(16) + 8;
			BlockPos pos = this.currentWorld.getHeight(new BlockPos(randPosX, 0, randPosZ));
	    	
			if(getBiome(randPosX, randPosZ) instanceof Barnarda_C_Plains)
		    {
    			if(this.getCurrentWorld().getBlockState(pos.down()) == BRBlocks.BARNARDA_C_GRASS.getDefaultState().withProperty(Barnarda_C_Grass.BASIC_TYPE, Barnarda_C_Grass.EnumBlockGrass.GRASS))
        			this.getCurrentWorld().setBlockState(pos, BRBlocks.BARNARDA_C_DANDELIONS.getDefaultState().withProperty(Barnarda_C_Dandelions.BASIC_TYPE, Barnarda_C_Dandelions.EnumBlockDandelions.GRASS));
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
    
    private WE_Biome getBiome(int x, int z)
    {
    	return WE_Biome.getBiomeAt(((WorldProviderBarnarda_C_WE)getCurrentWorld().provider).chunk, x, z);
    }
    
}
