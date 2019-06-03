package galaxyspace.systems.ACentauriSystem.planets.proxima_b.world.gen.features;

import java.util.Random;

import galaxyspace.systems.ACentauriSystem.core.registers.blocks.ACBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenFrozenTree extends WorldGenerator
{
	private IBlockState log, leaves;
	private int type;
	
	public WorldGenFrozenTree(IBlockState log, IBlockState leaves, int type)
	{
		this.log = log;		
		this.leaves = leaves;		
		this.type = type;
	}
	
	protected Block[] GetValidSpawnBlocks()
	{
		return new Block[]
		{
			ACBlocks.PROXIMA_B_BLOCKS
		};
	}

	public boolean LocationIsValidSpawn(World world, int x, int y, int z)
	{

		BlockPos pos = new BlockPos(x,y,z);
		
		Block checkBlock = world.getBlockState(pos.down()).getBlock();
		Block blockAbove = world.getBlockState(pos).getBlock();
		Block blockBelow = world.getBlockState(pos.down(2)).getBlock();

		for (Block i : GetValidSpawnBlocks())
		{
			if (blockAbove != Blocks.AIR)
			{
				return false;
			}
			else if(checkBlock == i && blockAbove == this.log)
			{
				return false;
			}
			if (checkBlock == i)
			{
				return true;
			}
			else if (checkBlock == Blocks.SNOW_LAYER && blockBelow == i)
			{
				return true;
			}
			else if (checkBlock.getDefaultState().getMaterial() == Material.PLANTS && blockBelow == i)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos)
	{
		// do not grow next to another tree
		/*for (EnumFacing e : EnumFacing.HORIZONTALS) {
			if (world.getBlockState(pos.offset(e)).getMaterial() == Material.WOOD)
				return false;
		}*/
				
		switch(this.type)
		{
			case 0: 
				return generate_r0(world, rand, pos.getX(), pos.getY(), pos.getZ());
			case 1:
				return generate_r1(world, rand, pos.getX(), pos.getY(), pos.getZ());
			case 2:
				return generate_r2(world, rand, pos.getX(), pos.getY(), pos.getZ());
		}

       return true;

	}

	public boolean generate_r0(World world, Random rand, int x, int y, int z)
	{
		if(!LocationIsValidSpawn(world, x, y, z) /*|| !LocationIsValidSpawn(world, x + 11, y, z) || !LocationIsValidSpawn(world, x + 11, y, z + 11) || !LocationIsValidSpawn(world, x, y, z + 11)*/)
		{
			return false;
		}

		world.setBlockState(new BlockPos(x + 6, y + 0, z + 1), this.log, 3);
		world.setBlockState(new BlockPos(x + 7, y + 0, z + 1), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 0, z + 2), this.log, 3);
		world.setBlockState(new BlockPos(x + 3, y + 0, z + 3), this.log, 3);
		world.setBlockState(new BlockPos(x + 4, y + 0, z + 3), this.log, 3);
		world.setBlockState(new BlockPos(x + 4, y + 0, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 5, y + 0, z + 5), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 0, z + 5), this.log, 3);
		world.setBlockState(new BlockPos(x + 7, y + 0, z + 5), this.log, 3);
		world.setBlockState(new BlockPos(x + 8, y + 0, z + 5), this.log, 3);
		world.setBlockState(new BlockPos(x + 4, y + 0, z + 6), this.log, 3);
		world.setBlockState(new BlockPos(x + 5, y + 0, z + 6), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 0, z + 6), this.log, 3);
		world.setBlockState(new BlockPos(x + 7, y + 0, z + 6), this.log, 3);
		world.setBlockState(new BlockPos(x + 3, y + 0, z + 7), this.log, 3);
		world.setBlockState(new BlockPos(x + 4, y + 0, z + 7), this.log, 3);
		world.setBlockState(new BlockPos(x + 7, y + 0, z + 7), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 1, z + 2), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 1, z + 3), this.log, 3);
		world.setBlockState(new BlockPos(x + 4, y + 1, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 5, y + 1, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 1, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 7, y + 1, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 5, y + 1, z + 5), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 1, z + 5), this.log, 3);
		world.setBlockState(new BlockPos(x + 7, y + 1, z + 5), this.log, 3);
		world.setBlockState(new BlockPos(x + 8, y + 1, z + 5), this.log, 3);
		world.setBlockState(new BlockPos(x + 5, y + 1, z + 6), this.log, 3);
		world.setBlockState(new BlockPos(x + 7, y + 1, z + 6), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 2, z + 3), this.log, 3);
		world.setBlockState(new BlockPos(x + 5, y + 2, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 2, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 7, y + 2, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 2, z + 5), this.log, 3);
		world.setBlockState(new BlockPos(x + 7, y + 2, z + 5), this.log, 3);

		world.setBlockState(new BlockPos(x + 6, y + 3, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 7, y + 3, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 7, y + 3, z + 5), this.log, 3);

		world.setBlockState(new BlockPos(x + 7, y + 4, z + 3), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 4, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 7, y + 4, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 4, z + 5), this.log, 3);

		world.setBlockState(new BlockPos(x + 6, y + 5, z + 3), this.log, 3);
		world.setBlockState(new BlockPos(x + 7, y + 5, z + 3), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 5, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 7, y + 5, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 5, z + 5), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 6, z + 3), this.log, 3);
		world.setBlockState(new BlockPos(x + 7, y + 6, z + 3), this.log, 3);
		world.setBlockState(new BlockPos(x + 5, y + 6, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 6, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 7, y + 6, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 7, z + 3), this.log, 3);
		world.setBlockState(new BlockPos(x + 5, y + 7, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 7, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 7, y + 7, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 5, y + 8, z + 3), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 8, z + 3), this.log, 3);
		world.setBlockState(new BlockPos(x + 5, y + 8, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 8, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 8, z + 5), this.log, 3);
		world.setBlockState(new BlockPos(x + 5, y + 9, z + 3), this.log, 3);
		world.setBlockState(new BlockPos(x + 5, y + 9, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 9, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 9, z + 5), this.log, 3);
		world.setBlockState(new BlockPos(x + 5, y + 10, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 10, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 5, y + 10, z + 5), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 10, z + 5), this.log, 3);
		world.setBlockState(new BlockPos(x + 5, y + 11, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 5, y + 11, z + 5), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 11, z + 5), this.log, 3);
		world.setBlockState(new BlockPos(x + 1, y + 12, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 12, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 4, y + 12, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 12, z + 5), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 12, z + 5), this.log, 3);
		world.setBlockState(new BlockPos(x + 8, y + 13, z + 0), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 1, y + 13, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 13, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 13, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 13, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 5, y + 13, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 3, y + 13, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 13, z + 5), this.log, 3);
		world.setBlockState(new BlockPos(x + 5, y + 13, z + 5), this.log, 3);
		world.setBlockState(new BlockPos(x + 4, y + 13, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 13, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 1, y + 13, z + 9), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 8, y + 14, z + 0), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 1, y + 14, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 1, y + 14, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 14, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 14, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 14, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 14, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 14, z + 3), this.log, 3);
		world.setBlockState(new BlockPos(x + 2, y + 14, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 14, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 14, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 5, y + 14, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 9, y + 14, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 14, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 14, z + 5), this.log, 3);
		world.setBlockState(new BlockPos(x + 5, y + 14, z + 5), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 14, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 14, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 14, z + 6), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 14, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 8, y + 14, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 14, z + 7), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 14, z + 7), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 1, y + 14, z + 9), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 9, y + 14, z + 9), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 8, y + 15, z + 0), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 9, y + 15, z + 0), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 1, y + 15, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 15, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 15, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 1, y + 15, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 15, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 15, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 15, z + 2), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 15, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 15, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 15, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 15, z + 3), this.log, 3);
		world.setBlockState(new BlockPos(x + 4, y + 15, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 15, z + 3), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 15, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 15, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 15, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 15, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 4, y + 15, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 5, y + 15, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 15, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 9, y + 15, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 15, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 15, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 15, z + 5), this.log, 3);
		world.setBlockState(new BlockPos(x + 5, y + 15, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 15, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 15, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 8, y + 15, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 15, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 15, z + 6), this.log, 3);
		world.setBlockState(new BlockPos(x + 4, y + 15, z + 6), this.log, 3);
		world.setBlockState(new BlockPos(x + 5, y + 15, z + 6), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 15, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 15, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 8, y + 15, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 15, z + 7), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 15, z + 7), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 15, z + 7), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 15, z + 7), this.log, 3);
		world.setBlockState(new BlockPos(x + 7, y + 15, z + 7), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 0, y + 15, z + 8), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 15, z + 8), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 15, z + 8), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 15, z + 8), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 8, y + 15, z + 8), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 1, y + 15, z + 9), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 9, y + 15, z + 9), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 16, z + 0), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 16, z + 0), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 16, z + 0), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 8, y + 16, z + 0), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 9, y + 16, z + 0), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 16, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 16, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 16, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 16, z + 1), this.log, 3);
		world.setBlockState(new BlockPos(x + 7, y + 16, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 1, y + 16, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 16, z + 2), this.log, 3);
		world.setBlockState(new BlockPos(x + 3, y + 16, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 16, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 16, z + 2), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 16, z + 2), this.log, 3);
		world.setBlockState(new BlockPos(x + 7, y + 16, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 1, y + 16, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 16, z + 3), this.log, 3);
		world.setBlockState(new BlockPos(x + 3, y + 16, z + 3), this.log, 3);
		world.setBlockState(new BlockPos(x + 4, y + 16, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 16, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 16, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 16, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 16, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 16, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 16, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 16, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 16, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 8, y + 16, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 9, y + 16, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 16, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 16, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 16, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 16, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 16, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 1, y + 16, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 16, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 16, z + 6), this.log, 3);
		world.setBlockState(new BlockPos(x + 4, y + 16, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 16, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 16, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 16, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 8, y + 16, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 1, y + 16, z + 7), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 16, z + 7), this.log, 3);
		world.setBlockState(new BlockPos(x + 3, y + 16, z + 7), this.log, 3);
		world.setBlockState(new BlockPos(x + 4, y + 16, z + 7), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 16, z + 7), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 16, z + 7), this.log, 3);
		world.setBlockState(new BlockPos(x + 7, y + 16, z + 7), this.log, 3);
		world.setBlockState(new BlockPos(x + 8, y + 16, z + 7), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 9, y + 16, z + 7), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 0, y + 16, z + 8), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 1, y + 16, z + 8), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 16, z + 8), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 16, z + 8), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 16, z + 8), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 16, z + 8), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 16, z + 8), this.log, 3);
		world.setBlockState(new BlockPos(x + 8, y + 16, z + 8), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 9, y + 16, z + 8), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 1, y + 16, z + 9), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 16, z + 9), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 16, z + 9), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 8, y + 16, z + 9), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 8, y + 16, z + 10), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 17, z + 0), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 17, z + 0), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 8, y + 17, z + 0), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 17, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 17, z + 1), this.log, 3);
		world.setBlockState(new BlockPos(x + 7, y + 17, z + 1), this.log, 3);
		world.setBlockState(new BlockPos(x + 8, y + 17, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 17, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 17, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 17, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 17, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 17, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 17, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 17, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 17, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 17, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 17, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 17, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 17, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 17, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 17, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 17, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 17, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 8, y + 17, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 17, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 17, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 17, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 17, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 17, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 17, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 17, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 17, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 0, y + 17, z + 7), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 1, y + 17, z + 7), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 17, z + 7), this.log, 3);
		world.setBlockState(new BlockPos(x + 3, y + 17, z + 7), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 17, z + 7), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 17, z + 7), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 17, z + 7), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 17, z + 7), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 8, y + 17, z + 7), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 0, y + 17, z + 8), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 1, y + 17, z + 8), this.log, 3);
		world.setBlockState(new BlockPos(x + 2, y + 17, z + 8), this.log, 3);
		world.setBlockState(new BlockPos(x + 3, y + 17, z + 8), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 17, z + 8), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 17, z + 8), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 17, z + 8), this.log, 3);
		world.setBlockState(new BlockPos(x + 8, y + 17, z + 8), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 1, y + 17, z + 9), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 17, z + 9), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 17, z + 9), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 17, z + 9), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 17, z + 9), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 8, y + 17, z + 9), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 18, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 18, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 18, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 18, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 18, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 18, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 18, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 18, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 18, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 18, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 18, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 18, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 18, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 18, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 18, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 18, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 1, y + 18, z + 7), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 18, z + 7), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 18, z + 7), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 18, z + 7), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 18, z + 7), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 1, y + 18, z + 8), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 18, z + 8), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 18, z + 8), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 18, z + 8), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 19, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 19, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 19, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 19, z + 6), this.leaves, 3);
		return true;

	}
	
	public boolean generate_r1(World world, Random rand, int x, int y, int z)
	{
		if(!LocationIsValidSpawn(world, x, y, z) /*|| !LocationIsValidSpawn(world, x + 6, y, z) || !LocationIsValidSpawn(world, x + 6, y, z + 6) || !LocationIsValidSpawn(world, x, y, z + 6)*/)
		{
			return false;
		}

		world.setBlockState(new BlockPos(x + 2, y + 0, z + 2), this.log, 3);
		world.setBlockState(new BlockPos(x + 3, y + 0, z + 2), this.log, 3);
		//world.setBlockState(new BlockPos(x + 0, y + 0, z + 3), this.log, 3);
		world.setBlockState(new BlockPos(x + 1, y + 0, z + 3), this.log, 3);
		world.setBlockState(new BlockPos(x + 2, y + 0, z + 3), this.log, 3);
		world.setBlockState(new BlockPos(x + 2, y + 0, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 3, y + 0, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 3, y + 0, z + 5), this.log, 3);
		world.setBlockState(new BlockPos(x + 1, y + 1, z + 3), this.log, 3);
		world.setBlockState(new BlockPos(x + 2, y + 1, z + 3), this.log, 3);
		
		world.setBlockState(new BlockPos(x + 2, y + 2, z + 3), this.log, 3);
		
		world.setBlockState(new BlockPos(x + 2, y + 3, z + 3), this.log, 3);
		world.setBlockState(new BlockPos(x + 3, y + 3, z + 3), this.log, 3);
		
		world.setBlockState(new BlockPos(x + 3, y + 4, z + 3), this.log, 3);
		world.setBlockState(new BlockPos(x + 1, y + 5, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 5, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 5, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 5, z + 2), this.leaves, 3);
		
		world.setBlockState(new BlockPos(x + 3, y + 5, z + 3), this.log, 3);
		
		world.setBlockState(new BlockPos(x + 2, y + 5, z + 4), this.leaves, 3);
		
		world.setBlockState(new BlockPos(x + 4, y + 5, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 5, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 6, z + 0), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 1, y + 6, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 6, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 6, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 6, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 1, y + 6, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 6, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 6, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 6, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 6, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 1, y + 6, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 6, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 6, z + 3), this.log, 3);
		world.setBlockState(new BlockPos(x + 4, y + 6, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 6, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 0, y + 6, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 1, y + 6, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 6, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 6, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 4, y + 6, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 1, y + 6, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 6, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 6, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 6, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 6, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 6, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 7, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 7, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 7, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 7, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 7, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 7, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 7, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 7, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 7, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 0, y + 7, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 1, y + 7, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 7, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 7, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 7, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 7, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 1, y + 7, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 7, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 7, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 7, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 7, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 7, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 7, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 7, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 7, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 7, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 7, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 8, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 8, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 8, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 8, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 8, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 8, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 8, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 8, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 8, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 8, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 8, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 8, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 8, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 8, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 8, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 8, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 8, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 8, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 9, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 9, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 9, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 9, z + 4), this.leaves, 3);
		return true;

	}

	public boolean generate_r2(World world, Random rand, int x, int y, int z)
	{
		if(!LocationIsValidSpawn(world, x, y, z) /*|| !LocationIsValidSpawn(world, x + 7, y, z) || !LocationIsValidSpawn(world, x + 7, y, z + 7) || !LocationIsValidSpawn(world, x, y, z + 7)*/)
		{
			return false;
		}

		world.setBlockState(new BlockPos(x + 3, y + 0, z + 2), this.log, 3);
		world.setBlockState(new BlockPos(x + 7, y + 0, z + 2), this.log, 3);
		world.setBlockState(new BlockPos(x + 4, y + 0, z + 3), this.log, 3);
		world.setBlockState(new BlockPos(x + 5, y + 0, z + 3), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 0, z + 3), this.log, 3);
		world.setBlockState(new BlockPos(x + 4, y + 0, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 5, y + 0, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 0, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 7, y + 0, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 3, y + 0, z + 5), this.log, 3);
		world.setBlockState(new BlockPos(x + 4, y + 0, z + 5), this.log, 3);
		world.setBlockState(new BlockPos(x + 5, y + 0, z + 5), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 0, z + 5), this.log, 3);
		//world.setBlockState(new BlockPos(x + 8, y + 0, z + 5), this.log, 3);
		world.setBlockState(new BlockPos(x + 5, y + 0, z + 6), this.log, 3);
		world.setBlockState(new BlockPos(x + 5, y + 0, z + 7), this.log, 3);
		world.setBlockState(new BlockPos(x + 5, y + 1, z + 3), this.log, 3);
		world.setBlockState(new BlockPos(x + 4, y + 1, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 5, y + 1, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 1, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 5, y + 1, z + 5), this.log, 3);
		world.setBlockState(new BlockPos(x + 5, y + 1, z + 6), this.log, 3);
		world.setBlockState(new BlockPos(x + 4, y + 2, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 5, y + 2, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 3, y + 3, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 3, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 1, y + 3, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 4, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 4, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 4, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 4, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 8, y + 4, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 1, y + 4, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 4, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 4, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 5, z + 0), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 5, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 5, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 8, y + 5, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 5, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 5, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 5, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 5, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 5, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 7, y + 5, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 8, y + 5, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 1, y + 5, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 5, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 5, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 5, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 5, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 5, z + 7), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 6, z + 0), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 6, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 6, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 6, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 6, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 6, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 6, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 8, y + 6, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 6, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 6, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 6, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 6, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 8, y + 6, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 6, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 6, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 6, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 7, y + 6, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 8, y + 6, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 1, y + 6, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 6, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 6, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 6, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 6, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 6, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 6, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 6, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 6, z + 7), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 7, z + 0), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 7, z + 0), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 7, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 7, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 7, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 7, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 7, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 8, y + 7, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 7, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 7, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 7, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 7, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 8, y + 7, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 7, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 7, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 7, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 7, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 7, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 7, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 7, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 7, y + 7, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 1, y + 7, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 7, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 7, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 7, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 7, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 7, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 7, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 7, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 7, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 7, z + 7), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 8, z + 0), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 8, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 8, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 8, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 8, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 8, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 8, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 8, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 8, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 8, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 8, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 8, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 8, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 8, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 8, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 8, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 8, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 8, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 8, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 8, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 8, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 8, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 8, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 1, y + 8, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 8, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 8, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 8, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 8, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 8, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 8, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 8, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 8, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 8, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 8, z + 7), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 9, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 9, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 9, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 9, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 9, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 9, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 9, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 9, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 9, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 9, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 9, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 9, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 9, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 9, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 9, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 9, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 9, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 9, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 9, z + 4), this.log, 3);
		world.setBlockState(new BlockPos(x + 6, y + 9, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 9, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 9, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 9, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 9, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 9, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 9, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 9, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 9, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 9, z + 7), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 9, z + 7), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 10, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 10, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 10, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 10, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 10, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 10, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 10, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 10, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 10, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 10, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 10, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 10, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 2, y + 10, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 10, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 10, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 10, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 10, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 10, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 10, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 10, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 10, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 10, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 7, y + 10, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 10, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 10, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 10, z + 6), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 10, z + 7), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 10, z + 7), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 11, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 11, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 11, z + 1), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 11, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 11, z + 2), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 11, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 11, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 11, z + 3), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 3, y + 11, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 11, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 11, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 6, y + 11, z + 4), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 4, y + 11, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 11, z + 5), this.leaves, 3);
		world.setBlockState(new BlockPos(x + 5, y + 11, z + 7), this.leaves, 3);
		return true;

	}
}
