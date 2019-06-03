package galaxyspace.systems.SolarSystem.moons.europa.world.gen;

import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.registers.blocks.GSBlocks;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeDecoratorEuropa extends BiomeDecoratorSpace
{

    private World currentWorld;
    
    private WorldGenerator OreGenIce;
    private WorldGenerator OreGenBrownIce;
    
    private WorldGenerator OreGenEmerald;
    private WorldGenerator OreGenSilicon;
    private WorldGenerator OreGenAluminum;
    
    public BiomeDecoratorEuropa()
    {
        //this.nickelGen = new WorldGenMinableMeta(GSBlocks.MERCURY_BLOCKS, 4, 3, true, GSBlocks.MERCURY_BLOCKS, 2);
       
    	this.OreGenIce = new WorldGenMinableMeta(Blocks.PACKED_ICE, 20, 0, true, Blocks.WATER, 0);
    	
		this.OreGenBrownIce = new WorldGenMinableMeta(GSBlocks.EUROPA_BLOCKS, 40, 2, true, Blocks.PACKED_ICE, 0);
		this.OreGenEmerald = new WorldGenMinableMeta(GSBlocks.EUROPA_BLOCKS, 4, 3, true, GSBlocks.EUROPA_BLOCKS, 1);
		this.OreGenSilicon = new WorldGenMinableMeta(GSBlocks.EUROPA_BLOCKS, 4, 4, true, GSBlocks.EUROPA_BLOCKS, 1);
		this.OreGenAluminum = new WorldGenMinableMeta(GSBlocks.EUROPA_BLOCKS, 6, 5, true, GSBlocks.EUROPA_BLOCKS, 1);
    }

    @Override
    protected void decorate()
    {
    	if(GSConfigCore.enableOresGeneration) {
    		this.generateOre(2, this.OreGenIce, 10, 60);
    		this.generateOre(16, this.OreGenBrownIce, 40, 75);
    		
    		this.generateOre(2, this.OreGenEmerald, 0, 30);
    		this.generateOre(4, this.OreGenSilicon, 0, 30);
    		this.generateOre(8, this.OreGenAluminum, 0, 30);
    	}
    	
    	for (int i = 0; i < 40; i++) {
			
			 int randPosX = posX + rand.nextInt(16) + 8;
			 int randPosY = rand.nextInt(70);
			 int randPosZ = posZ + rand.nextInt(16) + 8;
			 
			 BlockPos pos = new BlockPos(randPosX, randPosY + 1, randPosZ);
			 BlockPos pos1 = new BlockPos(randPosX, randPosY, randPosZ);
			 BlockPos pos2 = new BlockPos(randPosX, randPosY - 1, randPosZ);
			 
			 if(this.getCurrentWorld().isAirBlock(pos) || this.getCurrentWorld().getBlockState(pos).getBlock() == Blocks.WATER)
			 {
				 IBlockState state = this.getCurrentWorld().getBlockState(pos1);
				 if(this.getCurrentWorld().getBlockState(pos1).getBlock() == Blocks.PACKED_ICE)					 
				 {
					 this.getCurrentWorld().setBlockState(pos1, GSBlocks.EUROPA_UWGEYSER.getDefaultState());
				 }
			 }
    	}
    	
    	for (int i = 0; i < 40; i++) {
    		 int randPosX = posX + rand.nextInt(16) + 8;
			 int randPosY = rand.nextInt(100);
			 int randPosZ = posZ + rand.nextInt(16) + 8;
			 
			 BlockPos pos = new BlockPos(randPosX, randPosY + 1, randPosZ);
			 BlockPos pos1 = new BlockPos(randPosX, randPosY - 1, randPosZ);
			 BlockPos pos2 = new BlockPos(randPosX, randPosY, randPosZ);
			
			 if(this.getCurrentWorld().isAirBlock(pos))
			 {
				 if(this.getCurrentWorld().getBlockState(pos1).getBlock() == Blocks.PACKED_ICE)
				 {
					 this.getCurrentWorld().setBlockState(pos1, GSBlocks.EUROPA_UWGEYSER.getDefaultState());
					 }
				 
				 IBlockState state = this.getCurrentWorld().getBlockState(pos1);
				 
				 if(state.getBlock() == GSBlocks.EUROPA_BLOCKS &&
						 this.getCurrentWorld().getBlockState(pos1).getBlock().getMetaFromState(state) == 0)
				 {
					 this.getCurrentWorld().setBlockState(pos2, GSBlocks.EUROPA_GEYSER.getDefaultState());					
				 }
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
}
