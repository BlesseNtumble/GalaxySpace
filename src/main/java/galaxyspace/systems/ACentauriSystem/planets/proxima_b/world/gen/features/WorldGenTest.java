package galaxyspace.systems.ACentauriSystem.planets.proxima_b.world.gen.features;

import java.util.Random;

import asmodeuscore.core.astronomy.dimension.world.gen.WorldGenCustomStructure;
import galaxyspace.systems.ACentauriSystem.core.registers.blocks.ACBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenTest extends WorldGenCustomStructure{

	private int mode = 0;
	public WorldGenTest(int mode)
	{
		this.mode = mode;
	}
	
	byte[][][] MATRIX = new byte[][][] 
	{
		{
			{0, 1, 1, 0, 1}, 
			{0, 1, 1, 1, 0}, 
			{1, 0, 0, 1, 1}, 
			{1, 1, 1, 1, 1}, 
			{0, 1, 0, 1, 0}          
	    },{
	    	{0, 0, 0, 0, 1}, 
	    	{0, 1, 1, 0, 0}, 
	    	{0, 1, 0, 1, 0}, 
	    	{0, 1, 1, 0, 0}, 
	    	{0, 0, 0, 0, 0}
	    },{
	    	{0, 0, 1, 0, 0},
	    	{0, 1, 0, 1, 0},
	    	{0, 0, 1, 0, 0},
	    	{0, 0, 1, 0, 0},
	    	{0, 0, 0, 0, 0}
	    },{
	    	{0, 0, 0, 0, 0},
	    	{0, 0, 1, 1, 0},
	    	{0, 1, 1, 1, 0},
	    	{0, 0, 0, 0, 0},
	    	{0, 0, 0, 0, 0}
	    }
	};

	byte[][][] MATRIX_1 = new byte[][][] 
	{
		{
			{0, 1, 0, 0, 0}, 
			{1, 0, 1, 1, 0}, 
			{1, 1, 1, 1, 1}, 
			{1, 0, 1, 1, 1}, 
			{0, 1, 1, 0, 1}
		},{
			{0, 1, 0, 0, 0}, 
			{0, 0, 1, 1, 0}, 
			{1, 1, 1, 0, 1}, 
			{0, 1, 0, 1, 0}, 
			{0, 0, 1, 0, 0}
		},{
			{0, 0, 0, 0, 0}, 
			{0, 1, 1, 1, 0}, 
			{0, 1, 1, 0, 1}, 
			{0, 1, 1, 0, 0}, 
			{0, 0, 0, 0, 0} 
		},{
			{0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0}, 
			{0, 0, 1, 0, 0}, 
			{0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0} 
		}
	};
	    
	byte[][][] MATRIX_2 = new byte[][][] 
	{
		{
			{0, 1, 1, 0, 1}, 
			{1, 1, 1, 1, 1}, 
			{0, 1, 1, 1, 1}, 
			{1, 1, 1, 1, 1}, 
			{0, 1, 1, 0, 0} 
		},{
			{0, 0, 1, 0, 0}, 
			{0, 1, 1, 1, 0}, 
			{0, 1, 1, 1, 1}, 
			{0, 1, 1, 1, 1}, 
			{0, 0, 0, 0, 0} 
		},{
			{0, 0, 0, 0, 0}, 
			{0, 0, 1, 0, 0}, 
			{0, 0, 1, 1, 0}, 
			{0, 1, 0, 1, 0}, 
			{0, 0, 0, 0, 0} 
		},{
			{0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0}, 
			{0, 0, 1, 1, 0}, 
			{0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0} 
		}
	};
	
	byte[][][] MATRIX_3 = new byte[][][] 
	{
		{
			{1, 0, 0, 1, 1}, 
			{0, 1, 1, 0, 1}, 
			{1, 0, 1, 1, 0}, 
			{0, 0, 1, 1, 0}, 
			{1, 1, 0, 0, 1} 			
		},{
			{0, 1, 0, 0, 0}, 
			{0, 1, 1, 1, 1}, 
			{0, 1, 1, 1, 0}, 
			{1, 1, 1, 1, 0}, 
			{0, 0, 0, 1, 0} 			
		},{
			{0, 0, 0, 0, 0}, 
			{0, 0, 1, 0, 0}, 
			{0, 1, 1, 1, 0}, 
			{0, 1, 1, 0, 0}, 
			{0, 0, 0, 0, 0}
		},{
			{0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0}, 
			{0, 0, 1, 0, 0}, 
			{0, 0, 0, 0, 0}, 
			{0, 0, 0, 0, 0} 
		}
	};
	@Override
	protected byte[][][] getLayersMatrix() {
		switch(this.mode)
		{
			case 0: return MATRIX;
			case 1: return MATRIX_1;
			case 2: return MATRIX_2;
			case 3: return MATRIX_3;
			default: return MATRIX;
		}
	}

	@Override
	protected void generateBlock(World world, Random random, BlockPos position, int matrixValue) {	
		switch(matrixValue) {
        case 1:
            world.setBlockState(position, ACBlocks.PROXINA_B_LOG_1.getStateFromMeta(0));
            break;
        case 2:
            /*//Если на данной координате будет блок с материалом isReplaceable ...
            if (world.getBlock(x, y, z).isReplaceable(world, x, y, z)) {
                //то будет поставлен блок.
                world.setBlock(x, y, z, какой_нибудьБлок1, 1, 3);
            }*/
            break;

        }
	}

}
