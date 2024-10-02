//- By Vamig Aliev.
//- https://vk.com/win_vista.

package galaxyspace.core.world.worldengine.standardcustomgen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class WE_GrassGen implements IWorldGenerator {
	public List<Block  > flowerList      = new ArrayList();
	public List<Byte   > flowerMetaList  = new ArrayList();
	public List<Integer> blocksForFlower = new ArrayList();
	//-//
	public List<Boolean> waterGen        = new ArrayList();
	public List<Block  > surface         = new ArrayList();
	public List<Byte   > surfaceMeta     = new ArrayList();
	
	public void add(Block f, byte fm, int bff, boolean wg, Block s, byte sm) {
		flowerList     .add(f  );
		flowerMetaList .add(fm );
		blocksForFlower.add(bff);
		//-//
		waterGen       .add(wg );
		surface        .add(s  );
		surfaceMeta    .add(sm );
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		for(int i = 0; i < flowerList.size(); i++)
			if(random.nextInt(blocksForFlower.get(i)) <= 255)
				for(int i2 = 0; i2 < MathHelper.ceiling_float_int(256.0F / (float)blocksForFlower.get(i)); i2++) {
					int x = chunkX * 16 + random.nextInt(16),
						z = chunkZ * 16 + random.nextInt(16),
						y = world.getHeightValue(x, z);
					//-//
					if(waterGen.get(i)) {
						if(world.getBlock(x, y - 1, z).getMaterial().isLiquid()) {
							--y;
							while(true)
								if(world.getBlock(x, y - 1, z).getMaterial().isLiquid())
									--y;
								else {
									if(world.getBlock(x, y - 1, z) == surface.get(i) && world.getBlockMetadata(x, y - 1, z) == surfaceMeta.get(i))
										world.setBlock(x, y, z, flowerList.get(i), flowerMetaList.get(i), 2);
									break;
								}
						}
					}else
						if(world.getBlock(x, y - 1, z) == surface.get(i) && world.getBlockMetadata(x, y - 1, z) == surfaceMeta.get(i))
							world.setBlock(x, y, z, flowerList.get(i), flowerMetaList.get(i), 2);
				}
	}
}