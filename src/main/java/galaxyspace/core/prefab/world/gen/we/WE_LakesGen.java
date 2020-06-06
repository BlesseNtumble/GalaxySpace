package galaxyspace.core.prefab.world.gen.we;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WE_LakesGen implements IWorldGenerator {
	public IBlockState lakeBlock = Blocks.WATER.getDefaultState(), lakeBlock_ice = Blocks.ICE.getDefaultState();	
	public int chunksForLake = 12, minY = 0, maxY = 255, iceY = 111, random_iceY = 2;
	public boolean iceGen = true, underground = false, update = true;
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		if(random.nextInt(chunksForLake) == 0) {
			int x = chunkX + 8,
				z = chunkZ + 8,
				//y = minY + random.nextInt(maxY - minY + 1);
				y = underground ? minY + random.nextInt(maxY - minY + 1) : world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z)).getY();
			//-//
			if(y >= minY && y <= maxY) 
			{
				lake(world, random, x, y, z);
			}
		}
	}
	
	public void lake(World world, Random random, int x, int y, int z) {
		x -= 8;
		z -= 8;
		while(y > 5 && world.isAirBlock(new BlockPos(x, y, z)))
			--y;
		
		if(y <= 4)
			return;
		else {
			y -= 4;
			
			boolean[] aboolean = new boolean[2048];
			for(int w = 0; w < random.nextInt(4) + 4; ++w) {
				double
					d0 = random.nextDouble() * 6 + 3,
					d1 = random.nextDouble() * 4 + 2,
					d2 = random.nextDouble() * 6 + 3,
					d3 = random.nextDouble() * (14 - d0) + 1 + d0 / 2,
					d4 = random.nextDouble() * ( 4 - d1) + 2 + d1 / 2,
					d5 = random.nextDouble() * (14 - d2) + 1 + d2 / 2;
				//-//
				for(int bx = 1; bx < 15; ++bx)
					for(int bz = 1; bz < 15; ++bz)
						for(int by = 1; by < 7; ++by) {
							double
								d6 = ((double)bx - d3) * 2 / d0,
								d7 = ((double)by - d4) * 2 / d1,
								d8 = ((double)bz - d5) * 2 / d2,
								d9 = d6 * d6 + d7 * d7 + d8 * d8;
							//-//
							if(d9 < 1)
								aboolean[(bx * 16 + bz) * 8 + by] = true;
						}
			}
			
			for(int bx = 0; bx < 16; ++bx)
				for(int bz = 0; bz < 16; ++bz) {
					for(int by = 0; by < 8; ++by) {
						if(!aboolean[(bx * 16 + bz) * 8 + by] && (
							bx < 15 && aboolean[((bx + 1) * 16 + bz    ) * 8 + by    ] || bx > 0 && aboolean[((bx - 1) * 16 + bz    ) * 8 + by    ] ||
							bz < 15 && aboolean[( bx      * 16 + bz + 1) * 8 + by    ] || bz > 0 && aboolean[( bx      * 16 + bz - 1) * 8 + by    ] ||
							by <  7 && aboolean[( bx      * 16 + bz    ) * 8 + by + 1] || by > 0 && aboolean[( bx      * 16 + bz    ) * 8 + by - 1])) {
							Material material = world.getBlockState(new BlockPos(x + bx, y + by, z + bz)).getMaterial();
							if(by >= 4 &&  material.isLiquid()                                                                                           )
								return;
							if(by <  4 && !material.isSolid () &&
								!(world.getBlockState(new BlockPos(x + bx, y + by, z + bz)) == lakeBlock))
								return;
						}
						//-//
						if( aboolean[(bx * 16 + bz) * 8 + by]                                                                                       )
							world.setBlockState(new BlockPos(x + bx, y + by, z + bz), by >= 4 ? Blocks.AIR.getDefaultState() : lakeBlock, update ? 3 : 2);
					}
					if(y + 4 >= iceY + random.nextInt(random_iceY + 1))
						world.setBlockState(new BlockPos(x + bx, y +  4, z + bz), lakeBlock_ice, update ? 3 : 2);
				}
		}
	}
}
