package galaxyspace.core.world.gen.features.trees;

import java.util.Random;

import galaxyspace.core.world.gen.WorldGenCustomStructure;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public class WorldGenTree_Small extends WorldGenCustomStructure {
	
	private Block log, leaves;
	private int log_meta, leaves_meta, rotate;
	
	public WorldGenTree_Small(Block log, int log_meta, Block leaves, int leaves_meta, int rotate)
	{
		this.log = log;
		this.leaves = leaves;
		this.rotate = rotate;
		this.log_meta = log_meta;
		this.leaves_meta = leaves_meta;		
	}
	
	byte[][][] MATRIX = new byte[][][] 
	{ 
		{
			{0,0,0,0,0,0},
			{0,0,0,0,0,0},
			{0,0,0,0,0,0},
			{0,0,5,0,0,0},
			{0,0,0,0,0,0},
			{0,0,0,0,0,0},
		},{
			{0,0,0,0,0,0},
			{0,0,0,0,0,0},
			{0,0,0,0,0,0},
			{0,0,5,0,0,0},
			{0,0,0,0,0,0},
			{0,0,0,0,0,0},
		},{
			{0,0,0,0,0,0},
			{0,0,0,0,0,0},
			{0,0,0,1,0,0},
			{0,0,0,2,0,0},
			{0,0,0,1,0,0},
			{0,0,0,0,0,0},
		},{
			{0,0,0,1,0,0},
			{0,0,0,0,1,0},
			{0,0,3,2,3,1},
			{0,0,0,0,1,0},
			{0,0,0,0,0,0},
			{0,0,0,0,0,0},
		},{
			{0,0,0,0,0,0},
			{0,1,1,1,0,0},
			{1,2,1,1,1,0},
			{1,2,1,0,0,0},
			{1,3,1,0,0,0},
			{0,1,0,0,0,0},
		},{
			{0,0,0,0,0,0},
			{0,0,0,0,0,0},
			{0,1,1,1,0,0},
			{1,2,1,0,0,0},
			{0,1,0,0,0,0},
			{0,0,0,0,0,0},
		},{
			{0,0,0,0,0,0},
			{0,0,0,0,0,0},
			{0,0,0,0,0,0},
			{0,1,0,0,0,0},
			{0,0,0,0,0,0},
			{0,0,0,0,0,0},
		}
	};
	
	@Override
	protected byte[][][] getLayersMatrix() {
		return MATRIX;
	}

	@Override
	protected int rotate()
	{
		return this.rotate;
	}
	
	@Override
	protected void generateBlock(World world, Random random, int x, int y, int z, int matrixValue) {
		switch(matrixValue) {
			case 2:
				world.setBlock(x, y, z, this.log, 4, 3);
				break;
			case 5:
				world.setBlock(x, y, z, this.log, 0, 3);
				break;
			case 3:			
				world.setBlock(x, y, z, this.log, 8, 3);
				break;
			case 1:
			case 7:
				world.setBlock(x, y, z, this.leaves, this.leaves_meta, 3);
				break;
		}
	}
}
