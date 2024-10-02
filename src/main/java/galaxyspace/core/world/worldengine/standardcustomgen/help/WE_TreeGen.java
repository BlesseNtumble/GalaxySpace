//- By Vamig Aliev.
//- https://vk.com/win_vista.

package galaxyspace.core.world.worldengine.standardcustomgen.help;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.util.ForgeDirection;

public class WE_TreeGen extends WorldGenerator {
	public Block bWood = Blocks.log, bLeaves = Blocks.leaves, bSapling = Blocks.sapling, bVine = Blocks.vine, bCocoa = Blocks.cocoa;
	
	public int minTreeHeight = 4, metaWood = 0, metaLeaves = 0;
	public boolean vinesGrow = false;
	
	public List<Block   > ab = new ArrayList();
	public List<Material> am = new ArrayList();
	
	public boolean cb(Block b) {
		for(int i = 0; i < ab.size(); i++)
			if(b               == ab.get(i))
				return true;
		for(int i = 0; i < am.size(); i++)
			if(b.getMaterial() == am.get(i))
				return true;
		return false;
	}
	
	public boolean isReplaceable(World world, int x, int y, int z) {
		Block b = world.getBlock(x, y, z);
		return b.isAir(world, x, y, z) || b.isLeaves(world, x, y, z) || b.isWood(world, x, y, z) || cb(b);
	}
	
	public WE_TreeGen(boolean doBlockNotify) {
		super(doBlockNotify);
		
		ab.add(Blocks.grass  );
		ab.add(Blocks.dirt   );
		ab.add(Blocks.log    );
		ab.add(Blocks.log2   );
		ab.add(Blocks.sapling);
		ab.add(Blocks.vine   );
		//-//
		am.add(Material.air   );
		am.add(Material.leaves);
	}
	
	public boolean generate(World p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_) {
		int l = p_76484_2_.nextInt(3) + minTreeHeight;
		boolean flag = true;
		if(p_76484_4_ >= 1 && p_76484_4_ + l + 1 <= 256) {
			byte b0;
			int k1;
			Block block;
			for(int i1 = p_76484_4_; i1 <= p_76484_4_ + 1 + l; ++i1) {
				b0 = 1;
				if(i1 == p_76484_4_            )
					b0 = 0;
				if(i1 >= p_76484_4_ + 1 + l - 2)
					b0 = 2;
				
				for(int j1 = p_76484_3_ - b0; j1 <= p_76484_3_ + b0 && flag; ++j1)
					for(k1 = p_76484_5_ - b0; k1 <= p_76484_5_ + b0 && flag; ++k1)
						if(i1 >= 0 && i1 < 256) {
							block = p_76484_1_.getBlock(j1, i1, k1);
							if(!isReplaceable(p_76484_1_, j1, i1, k1))
								flag = false;
						}else
							flag = false;
			}
			
			if(!flag)
				return false;
			else {
				Block block2 = p_76484_1_.getBlock(p_76484_3_, p_76484_4_ - 1, p_76484_5_);
				
				boolean isSoil = block2.canSustainPlant(p_76484_1_, p_76484_3_, p_76484_4_ - 1, p_76484_5_, ForgeDirection.UP, (BlockSapling)bSapling);
				if(isSoil && p_76484_4_ < 256 - l - 1) {
					block2.onPlantGrow(p_76484_1_, p_76484_3_, p_76484_4_ - 1, p_76484_5_, p_76484_3_, p_76484_4_, p_76484_5_);
					
					b0 = 3;
					byte b1 = 0;
					int l1, i2, j2, i3;
					for(k1 = p_76484_4_ - b0 + l; k1 <= p_76484_4_ + l; ++k1) {
						i3 = k1 - p_76484_4_ - l;
						l1 = b1 + 1 - i3 / 2;
						//-//
						for(i2 = p_76484_3_ - l1; i2 <= p_76484_3_ + l1; ++i2) {
							j2 = i2 - p_76484_3_;
							for(int k2 = p_76484_5_ - l1; k2 <= p_76484_5_ + l1; ++k2) {
								int l2 = k2 - p_76484_5_;
								if(Math.abs(j2) != l1 || Math.abs(l2) != l1 || p_76484_2_.nextInt(2) != 0 && i3 != 0) {
									Block block1 = p_76484_1_.getBlock(i2, k1, k2);
									if(block1.isAir(p_76484_1_, i2, k1, k2) || block1.isLeaves(p_76484_1_, i2, k1, k2))
										setBlockAndNotifyAdequately(p_76484_1_, i2, k1, k2, bLeaves, metaLeaves);
								}
							}
						}
					}
					
					for(k1 = 0; k1 < l; ++k1) {
						block = p_76484_1_.getBlock(p_76484_3_, p_76484_4_ + k1, p_76484_5_);
						if(block.isAir(p_76484_1_, p_76484_3_, p_76484_4_ + k1, p_76484_5_) || block.isLeaves(p_76484_1_, p_76484_3_, p_76484_4_ + k1, p_76484_5_)) {
							setBlockAndNotifyAdequately(p_76484_1_, p_76484_3_, p_76484_4_ + k1, p_76484_5_, bWood, metaWood);
							if(vinesGrow && k1 > 0) {
								if(p_76484_2_.nextInt(3) > 0 && p_76484_1_.isAirBlock(p_76484_3_ - 1, p_76484_4_ + k1, p_76484_5_))
									setBlockAndNotifyAdequately(p_76484_1_, p_76484_3_ - 1, p_76484_4_ + k1, p_76484_5_, bVine, 8);
								if(p_76484_2_.nextInt(3) > 0 && p_76484_1_.isAirBlock(p_76484_3_ + 1, p_76484_4_ + k1, p_76484_5_))
									setBlockAndNotifyAdequately(p_76484_1_, p_76484_3_ + 1, p_76484_4_ + k1, p_76484_5_, bVine, 2);
								if(p_76484_2_.nextInt(3) > 0 && p_76484_1_.isAirBlock(p_76484_3_, p_76484_4_ + k1, p_76484_5_ - 1))
									setBlockAndNotifyAdequately(p_76484_1_, p_76484_3_, p_76484_4_ + k1, p_76484_5_ - 1, bVine, 1);
								if(p_76484_2_.nextInt(3) > 0 && p_76484_1_.isAirBlock(p_76484_3_, p_76484_4_ + k1, p_76484_5_ + 1))
									setBlockAndNotifyAdequately(p_76484_1_, p_76484_3_, p_76484_4_ + k1, p_76484_5_ + 1, bVine, 4);
							}
						}
					}
					
					if(vinesGrow) {
						for(k1 = p_76484_4_ - 3 + l; k1 <= p_76484_4_ + l; ++k1) {
							i3 = k1 - p_76484_4_ - l;
							l1 = 2 - i3 / 2;
							//-//
							for(i2 = p_76484_3_ - l1; i2 <= p_76484_3_ + l1; ++i2)
								for(j2 = p_76484_5_ - l1; j2 <= p_76484_5_ + l1; ++j2)
									if(p_76484_1_.getBlock(i2, k1, j2).isLeaves(p_76484_1_, i2, k1, j2)) {
										if(p_76484_2_.nextInt(4) == 0 && p_76484_1_.getBlock(i2 - 1, k1, j2).isAir(p_76484_1_, i2 - 1, k1, j2))
											growVines(p_76484_1_, i2 - 1, k1, j2, 8);
										if(p_76484_2_.nextInt(4) == 0 && p_76484_1_.getBlock(i2 + 1, k1, j2).isAir(p_76484_1_, i2 + 1, k1, j2))
											growVines(p_76484_1_, i2 + 1, k1, j2, 2);
										if(p_76484_2_.nextInt(4) == 0 && p_76484_1_.getBlock(i2, k1, j2 - 1).isAir(p_76484_1_, i2, k1, j2 - 1))
											growVines(p_76484_1_, i2, k1, j2 - 1, 1);
										if(p_76484_2_.nextInt(4) == 0 && p_76484_1_.getBlock(i2, k1, j2 + 1).isAir(p_76484_1_, i2, k1, j2 + 1))
											growVines(p_76484_1_, i2, k1, j2 + 1, 4);
									}
						}
						
						if(p_76484_2_.nextInt(5) == 0 && l > 5)
							for(k1 = 0; k1 < 2; ++k1)
								for(i3 = 0; i3 < 4; ++i3)
									if(p_76484_2_.nextInt(4 - k1) == 0) {
										l1 = p_76484_2_.nextInt(3);
										setBlockAndNotifyAdequately(p_76484_1_, p_76484_3_ + Direction.offsetX[Direction.rotateOpposite[i3]], p_76484_4_ + l - 5 + k1, p_76484_5_ + Direction.offsetZ[Direction.rotateOpposite[i3]], bCocoa, l1 << 2 | i3);
									}
					}
					
					return true;
				}else
					return false;
			}
		}else
			return false;
	}
	
	public void growVines(World p_76529_1_, int p_76529_2_, int p_76529_3_, int p_76529_4_, int p_76529_5_) {
		setBlockAndNotifyAdequately(p_76529_1_, p_76529_2_, p_76529_3_, p_76529_4_, bVine, p_76529_5_);
		int i1 = 4;
		//-//
		while(true) {
			--p_76529_3_;
			//-//
			if(!p_76529_1_.getBlock(p_76529_2_, p_76529_3_, p_76529_4_).isAir(p_76529_1_, p_76529_2_, p_76529_3_, p_76529_4_) || i1 <= 0)
				return;
			//-//
			setBlockAndNotifyAdequately(p_76529_1_, p_76529_2_, p_76529_3_, p_76529_4_, bVine, p_76529_5_);
			--i1;
		}
	}
}