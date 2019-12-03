package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen;

import java.util.Random;

import asmodeuscore.core.astronomy.dimension.world.gen.WorldGenCustomStructure;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Logs;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Logs.EnumAxis;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenTree_Small extends WorldGenCustomStructure {
	
	private IBlockState log, leaves;
	private int rotate;
	
	public WorldGenTree_Small(IBlockState log, IBlockState leaves, int rotate)
	{
		this.log = log;
		this.leaves = leaves;
		this.rotate = rotate;
	}
	
	byte[][][] MATRIX = new byte[][][] 
	{ 
		{
			{0,0,0,0,0,0},
			{0,0,0,0,0,0},
			{0,0,0,0,0,0},
			{0,0,2,0,0,0},
			{0,0,0,0,0,0},
			{0,0,0,0,0,0},
		},{
			{0,0,0,0,0,0},
			{0,0,0,0,0,0},
			{0,0,0,0,0,0},
			{0,0,2,0,0,0},
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
	protected void generateBlock(World world, Random random, BlockPos position, int matrixValue) {
		switch(matrixValue) {
			case 2:
				world.setBlockState(position, this.log.withProperty(Barnarda_C_Logs.LOG_AXIS, EnumAxis.Y));
				break;
			case 3:
				world.setBlockState(position, this.log.withProperty(Barnarda_C_Logs.LOG_AXIS, EnumAxis.X));
				break;
			case 1:
			case 7:
				world.setBlockState(position, this.leaves);
				break;
				
			default: 
				break;
		}
	}
}
