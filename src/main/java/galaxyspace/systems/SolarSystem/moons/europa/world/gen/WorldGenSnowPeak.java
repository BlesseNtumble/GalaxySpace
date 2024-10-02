package galaxyspace.systems.SolarSystem.moons.europa.world.gen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;


public class WorldGenSnowPeak extends WorldGenerator
{
	
	private Block snowGenEuropa;
	private Block iceGenEuropa;
	private int number;
	private int metadata1;
	private int metadata2;
	
 public WorldGenSnowPeak(Block par1, int meta1, Block par2, int meta2, int par3)
 {
	 this.snowGenEuropa = par1;
	 this.metadata1 = meta1;
	 this.iceGenEuropa = par2;
	 this.metadata2 = meta2;
	 this.number = par3;
 }

 public boolean generate(World world, Random par2Random, int x, int y, int z)
 {
	 
 int i = x;
 int j = y;
 int k = z;

 switch (this.number)
 {                //Args: X, Y, Z, new block ID, new metadata, flags.
 	case 1:
 		world.setBlock( i + 0, j + 3, k + 4, this.iceGenEuropa, this.metadata2, 0);
 		world.setBlock( i + 0, j + 4, k + 4, this.snowGenEuropa, this.metadata1, 0);
 		world.setBlock( i + 1, j + 2, k + 2, this.iceGenEuropa, this.metadata2, 0);
 		world.setBlock( i + 1, j + 3, k + 2, this.snowGenEuropa, this.metadata1, 0);
 		world.setBlock( i + 1, j + 3, k + 3, this.iceGenEuropa, this.metadata2, 0);
 		world.setBlock( i + 1, j + 4, k + 3, this.snowGenEuropa, this.metadata1, 0);
 		world.setBlock( i + 2, j + 0, k + 0, this.snowGenEuropa, this.metadata1, 0);
 		world.setBlock( i + 2, j + 0, k + 1, this.iceGenEuropa, this.metadata2, 0);
 		world.setBlock( i + 2, j + 1, k + 0, this.snowGenEuropa, this.metadata1, 0);
 		world.setBlock( i + 2, j + 1, k + 1, this.iceGenEuropa, this.metadata2, 0);
 		world.setBlock( i + 2, j + 2, k + 1, this.snowGenEuropa, this.metadata1, 0);
 		world.setBlock( i + 2, j + 2, k + 2, this.iceGenEuropa, this.metadata2, 0);
 		world.setBlock( i + 2, j + 2, k + 3, this.iceGenEuropa, this.metadata2, 0);
 		world.setBlock( i + 2, j + 3, k + 2, this.snowGenEuropa, this.metadata1, 0);
 		world.setBlock( i + 2, j + 3, k + 3, this.snowGenEuropa, this.metadata1, 0);
 		world.setBlock( i + 3, j + 0, k + 0, this.snowGenEuropa, this.metadata1, 0);
		world.setBlock( i + 3, j + 0, k + 1, this.iceGenEuropa, this.metadata2, 0);
		world.setBlock( i + 3, j + 0, k + 2, this.iceGenEuropa, this.metadata2, 0);
		world.setBlock( i + 3, j + 1, k + 0, this.snowGenEuropa, this.metadata1, 0);
		world.setBlock( i + 3, j + 1, k + 1, this.snowGenEuropa, this.metadata1, 0);
		world.setBlock( i + 3, j + 1, k + 2, this.iceGenEuropa, this.metadata2, 0);
		world.setBlock( i + 3, j + 2, k + 1, this.snowGenEuropa, this.metadata1, 0);
		world.setBlock( i + 3, j + 2, k + 2, this.snowGenEuropa, this.metadata1, 0);
		world.setBlock( i + 4, j + 0, k + 1, this.snowGenEuropa, this.metadata1, 0);
		world.setBlock( i + 4, j + 0, k + 2, this.snowGenEuropa, this.metadata1, 0);
		world.setBlock( i + 4, j + 1, k + 1, this.snowGenEuropa, this.metadata1, 0);
		world.setBlock( i + 4, j + 1, k + 2, this.snowGenEuropa, this.metadata1, 0);
	break;

 }
 return true;
 }
}