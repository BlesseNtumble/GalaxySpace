package galaxyspace.core.world.gen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;


public class WorldGenPeak extends WorldGenerator
{
	
	private Block peakGenBlock;
	
	public WorldGenPeak(Block par1)
	{
		this.peakGenBlock = par1;
	}

	public boolean generate(World world, Random par2Random, int x, int y, int z)
	{
	 
		int i = x;
		int j = y;
		int k = z;

		//layer 1
		world.setBlock( i + 0, j + 0, k + 1, this.peakGenBlock);
		world.setBlock( i + 0, j + 0, k + 2, this.peakGenBlock);
		world.setBlock( i + 0, j + 0, k + 3, this.peakGenBlock);
		world.setBlock( i + 1, j + 0, k + 0, this.peakGenBlock);
		world.setBlock( i + 1, j + 0, k + 1, this.peakGenBlock);
		world.setBlock( i + 1, j + 0, k + 2, this.peakGenBlock);
		world.setBlock( i + 1, j + 0, k + 3, this.peakGenBlock);
		world.setBlock( i + 1, j + 1, k + 1, this.peakGenBlock);
		world.setBlock( i + 1, j + 1, k + 2, this.peakGenBlock);
		world.setBlock( i + 1, j + 1, k + 3, this.peakGenBlock);
		world.setBlock( i + 1, j + 2, k + 1, this.peakGenBlock);
		world.setBlock( i + 1, j + 2, k + 2, this.peakGenBlock);
		world.setBlock( i + 1, j + 2, k + 3, this.peakGenBlock);
		world.setBlock( i + 1, j + 3, k + 2, this.peakGenBlock);
		world.setBlock( i + 1, j + 3, k + 3, this.peakGenBlock);
		world.setBlock( i + 2, j + 0, k + 0, this.peakGenBlock);
		world.setBlock( i + 2, j + 0, k + 1, this.peakGenBlock);
		world.setBlock( i + 2, j + 0, k + 2, this.peakGenBlock);
		world.setBlock( i + 2, j + 1, k + 1, this.peakGenBlock);
		world.setBlock( i + 2, j + 1, k + 2, this.peakGenBlock);
		world.setBlock( i + 2, j + 2, k + 1, this.peakGenBlock);
		world.setBlock( i + 2, j + 2, k + 2, this.peakGenBlock);
		world.setBlock( i + 2, j + 2, k + 3, this.peakGenBlock);
		world.setBlock( i + 2, j + 3, k + 1, this.peakGenBlock);
		world.setBlock( i + 2, j + 3, k + 2, this.peakGenBlock);
		world.setBlock( i + 2, j + 3, k + 3, this.peakGenBlock);
		world.setBlock( i + 2, j + 4, k + 2, this.peakGenBlock);
		world.setBlock( i + 2, j + 4, k + 3, this.peakGenBlock);
		world.setBlock( i + 2, j + 5, k + 3, this.peakGenBlock);
		world.setBlock( i + 3, j + 0, k + 0, this.peakGenBlock);
		world.setBlock( i + 3, j + 0, k + 1, this.peakGenBlock);
		world.setBlock( i + 3, j + 1, k + 1, this.peakGenBlock);
		world.setBlock( i + 3, j + 2, k + 1, this.peakGenBlock);
		world.setBlock( i + 3, j + 2, k + 2, this.peakGenBlock);
		world.setBlock( i + 3, j + 3, k + 1, this.peakGenBlock);
		world.setBlock( i + 3, j + 3, k + 2, this.peakGenBlock);
		world.setBlock( i + 3, j + 3, k + 3, this.peakGenBlock);
		world.setBlock( i + 3, j + 4, k + 2, this.peakGenBlock);
		world.setBlock( i + 3, j + 4, k + 3, this.peakGenBlock);
		world.setBlock( i + 3, j + 5, k + 2, this.peakGenBlock);
		world.setBlock( i + 3, j + 5, k + 3, this.peakGenBlock);
		world.setBlock( i + 3, j + 5, k + 4, this.peakGenBlock);
		world.setBlock( i + 3, j + 6, k + 3, this.peakGenBlock);
		world.setBlock( i + 4, j + 5, k + 3, this.peakGenBlock);
		world.setBlock( i + 4, j + 6, k + 4, this.peakGenBlock);
		world.setBlock( i + 4, j + 7, k + 4, this.peakGenBlock);
		world.setBlock( i + 5, j + 7, k + 5, this.peakGenBlock);
	 
		return true;
 
 
	}
}