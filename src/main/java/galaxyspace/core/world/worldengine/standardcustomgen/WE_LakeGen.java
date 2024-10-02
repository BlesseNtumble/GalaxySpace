//- By Vamig Aliev.
//- https://vk.com/win_vista.

package galaxyspace.core.world.worldengine.standardcustomgen;

import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class WE_LakeGen implements IWorldGenerator {
	public Block lakeBlock     = Blocks.water, lakeBlock_f     = Blocks.ice;
	public byte  lakeBlockMeta =            0, lakeBlockMeta_f =          0;
	public int chunksForLake = 12, minY = 0, maxY = 255, fY = 111, random_fY = 2;
	public boolean fGen = true, u = true;
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if(random.nextInt(chunksForLake) == 0) {
			int x = chunkX * 16 + random.nextInt(16),
				z = chunkZ * 16 + random.nextInt(16),
				y = minY + random.nextInt(maxY - minY + 1);
			//-//
			lake(world, random, x, y, z);
		}
	}
	
	public void lake(World world, Random random, int x, int y, int z) {
		x -= 8;
		z -= 8;
		while(y > 5 && world.isAirBlock(x, y, z))
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
							Material material = world.getBlock(x + bx, y + by, z + bz).getMaterial();
							if(by >= 4 &&  material.isLiquid()                                                                                           )
								return;
							if(by <  4 && !material.isSolid () &&
								!(world.getBlock(x + bx, y + by, z + bz) == lakeBlock && world.getBlockMetadata(x + bx, y + by, z + bz) == lakeBlockMeta))
								return;
						}
						//-//
						if( aboolean[(bx * 16 + bz) * 8 + by]                                                                                       )
							world.setBlock(x + bx, y + by, z + bz, by >= 4 ? Blocks.air : lakeBlock,   lakeBlockMeta, u ? 3 : 2);
					}
					if(y + 4 >= fY + random.nextInt(random_fY + 1))
						world    .setBlock(x + bx, y +  4, z + bz,                      lakeBlock_f, lakeBlockMeta_f, u ? 3 : 2);
				}
		}
	}
}