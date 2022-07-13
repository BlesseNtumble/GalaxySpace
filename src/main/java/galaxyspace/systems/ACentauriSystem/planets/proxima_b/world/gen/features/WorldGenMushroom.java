package galaxyspace.systems.ACentauriSystem.planets.proxima_b.world.gen.features;

import java.util.Random;

import asmodeuscore.core.astronomy.dimension.world.gen.WorldGenCustomStructure;
import galaxyspace.systems.ACentauriSystem.core.ACBlocks;
import micdoodle8.mods.galacticraft.planets.venus.VenusBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenMushroom extends WorldGenCustomStructure {

	byte[][][] MATRIX_SPHERE = new byte[][][] 
			{
				{
					{0, 0, 0, 0, 0}, 
					{0, 0, 1, 0, 0}, 
					{0, 1, 1, 1, 0}, 
					{0, 0, 1, 0, 0},           
					{0, 0, 0, 0, 0}      
			    },{
			    	{0, 0, 1, 0, 0}, 
					{0, 1, 1, 1, 0}, 
					{1, 1, 2, 1, 1}, 
					{0, 1, 1, 1, 0},           
					{0, 0, 1, 0, 0}  
			    },{
			    	{0, 0, 0, 0, 0}, 
					{0, 1, 1, 1, 0}, 
					{0, 1, 2, 1, 0}, 
					{0, 1, 1, 1, 0},           
					{0, 0, 0, 0, 0}   
			    },{
			    	{0, 0, 0, 0, 0}, 
					{0, 0, 1, 0, 0}, 
					{0, 1, 3, 1, 0}, 
					{0, 0, 1, 0, 0},           
					{0, 0, 0, 0, 0}   
			    }
			};
			
	@Override
	protected byte[][][] getLayersMatrix() {
		return MATRIX_SPHERE;
	}

	@Override
	protected void generateBlock(World world, Random random, BlockPos position, int matrixValue) {
		switch(matrixValue) {
	        case 1:
	            world.setBlockState(position, ACBlocks.PROXIMA_B_BLOCKS.getStateFromMeta(12));
	            break;
	        case 2:
	        	if(random.nextInt(5) == 0)
	        		world.setBlockState(position, VenusBlocks.sulphuricAcid.getDefaultState());
		        break;
	        case 3:
	        	world.setBlockState(position, ACBlocks.PROXIMA_B_BLOCKS.getStateFromMeta(13));
		        break;
	        default: break;
		}
		
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos position) {
		this.genLegs(world, rand, position);
		
		int yX = 5 + rand.nextInt(4);
		for(int i = 0; i < yX / 2; i++)
			world.setBlockState(position.up(yX-i), ACBlocks.PROXIMA_B_MUSHROOM_LOG.getStateFromMeta(0));			
		
		
		return super.generate(world, rand, position.up(yX));
	}

	private void genLegs(World world, Random rand, BlockPos position) {
		BlockPos pos = position;
		switch(rand.nextInt(2)) {
			case 0: {
				world.setBlockState(pos.east(), ACBlocks.PROXIMA_B_MUSHROOM_LOG.getStateFromMeta(0));
				world.setBlockState(pos.south(3), ACBlocks.PROXIMA_B_MUSHROOM_LOG.getStateFromMeta(0));
				world.setBlockState(pos.north(2).west(2), ACBlocks.PROXIMA_B_MUSHROOM_LOG.getStateFromMeta(0));
				pos = pos.up();
				world.setBlockState(pos.east(), ACBlocks.PROXIMA_B_MUSHROOM_LOG.getStateFromMeta(0));
				world.setBlockState(pos.south(2), ACBlocks.PROXIMA_B_MUSHROOM_LOG.getStateFromMeta(0));
				world.setBlockState(pos.north(2).west(2), ACBlocks.PROXIMA_B_MUSHROOM_LOG.getStateFromMeta(0));
				pos = pos.up();
				world.setBlockState(pos, ACBlocks.PROXIMA_B_MUSHROOM_LOG.getStateFromMeta(0));
				world.setBlockState(pos.east(), ACBlocks.PROXIMA_B_MUSHROOM_LOG.getStateFromMeta(4));
				world.setBlockState(pos.south(2), ACBlocks.PROXIMA_B_MUSHROOM_LOG.getStateFromMeta(0));
				world.setBlockState(pos.north(1).west(1), ACBlocks.PROXIMA_B_MUSHROOM_LOG.getStateFromMeta(8));
				pos = pos.up();
				world.setBlockState(pos, ACBlocks.PROXIMA_B_MUSHROOM_LOG.getStateFromMeta(0));
				world.setBlockState(pos.south(), ACBlocks.PROXIMA_B_MUSHROOM_LOG.getStateFromMeta(8));
				pos = pos.up();
				world.setBlockState(pos, ACBlocks.PROXIMA_B_MUSHROOM_LOG.getStateFromMeta(0));
				break;
			}
			case 1: {
				world.setBlockState(pos.south(2).east(2), ACBlocks.PROXIMA_B_MUSHROOM_LOG.getStateFromMeta(0));
				world.setBlockState(pos.west(2), ACBlocks.PROXIMA_B_MUSHROOM_LOG.getStateFromMeta(0));
				world.setBlockState(pos.north(3), ACBlocks.PROXIMA_B_MUSHROOM_LOG.getStateFromMeta(0));
				pos = pos.up();
				world.setBlockState(pos.south(2).east(2), ACBlocks.PROXIMA_B_MUSHROOM_LOG.getStateFromMeta(0));
				world.setBlockState(pos.west(1), ACBlocks.PROXIMA_B_MUSHROOM_LOG.getStateFromMeta(0));
				world.setBlockState(pos.north(3), ACBlocks.PROXIMA_B_MUSHROOM_LOG.getStateFromMeta(0));
				pos = pos.up();
				world.setBlockState(pos, ACBlocks.PROXIMA_B_MUSHROOM_LOG.getStateFromMeta(0));
				world.setBlockState(pos.south(1).east(1), ACBlocks.PROXIMA_B_MUSHROOM_LOG.getStateFromMeta(4));
				world.setBlockState(pos.west(1), ACBlocks.PROXIMA_B_MUSHROOM_LOG.getStateFromMeta(4));
				world.setBlockState(pos.north(3), ACBlocks.PROXIMA_B_MUSHROOM_LOG.getStateFromMeta(0));
				pos = pos.up();
				world.setBlockState(pos, ACBlocks.PROXIMA_B_MUSHROOM_LOG.getStateFromMeta(0));
				world.setBlockState(pos.north(2), ACBlocks.PROXIMA_B_MUSHROOM_LOG.getStateFromMeta(0));
				pos = pos.up();
				world.setBlockState(pos, ACBlocks.PROXIMA_B_MUSHROOM_LOG.getStateFromMeta(0));
				world.setBlockState(pos.north(), ACBlocks.PROXIMA_B_MUSHROOM_LOG.getStateFromMeta(4));
			}
		}
	}
}
