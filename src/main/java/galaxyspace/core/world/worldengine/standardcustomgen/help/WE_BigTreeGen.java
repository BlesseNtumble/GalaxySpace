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
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.util.ForgeDirection;

public class WE_BigTreeGen extends WorldGenerator {
	public Block bWood = Blocks.log, bLeaves = Blocks.leaves, bSapling = Blocks.sapling;
	
	public byte[] otherCoordPairs = new byte[] {2, 0, 0, 1, 2, 1};
	public int trunkSize = 1, heightLimitLimit = 12, leafDistanceLimit = 4, metaLeaves = 0;
	public double heightAttenuation = 0.618D, branchSlope = 0.381D, scaleWidth = 1.0D, leafDensity = 1.0D;
	
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
	
	public WE_BigTreeGen(boolean doBlockNotify) {
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
		int heightLimit = 5 + p_76484_2_.nextInt(heightLimitLimit), height;
		int[] basePos = new int[] {p_76484_3_, p_76484_4_, p_76484_5_};
		int[][] leafNodes;
		
		int[]
			_aint  = new int[] {basePos[0], basePos[1]                  , basePos[2]},
			_aint1 = new int[] {basePos[0], basePos[1] + heightLimit - 1, basePos[2]};
		Block block = p_76484_1_.getBlock(basePos[0], basePos[1] - 1, basePos[2]);
		boolean br;
		//-//
		if(!block.canSustainPlant(p_76484_1_, basePos[0], basePos[1] - 1, basePos[2], ForgeDirection.UP, (BlockSapling)bSapling))
			br = false;
		else {
			int i = checkBlockLine(p_76484_1_, _aint, _aint1);
			if(i == -1)
				br =  true;
			else if (i < 6)
				br = false;
			else {
				heightLimit = i;
				br =  true;
			}
		}
		
		if(!br)
			return false;
		else {
			height = (int)((double)heightLimit * heightAttenuation);
			if(height >= heightLimit)
				height = heightLimit - 1;
			//-//
			int i = (int)(1.382D + Math.pow(leafDensity * (double)heightLimit / 13.0D, 2.0D));
			if(i < 1)
				i = 1;
			
			int[][] aint = new int[i * heightLimit][4];
			int j = basePos[1] + heightLimit - leafDistanceLimit, k = 1, l = basePos[1] + height, i1 = j - basePos[1];
			aint[0][0] = basePos[0];
			aint[0][1] =          j;
			aint[0][2] = basePos[2];
			aint[0][3] =          l;
			--j;
			//-//
			while(i1 >= 0) {
				int j1 = 0;
				//-//
				float f2;
				if((double)i1 < (double)heightLimit * 0.3D)
					f2 = -1.618F;
				else {
					float f = (float)heightLimit / 2.0F, f1 = (float)heightLimit / 2.0F - (float)i1;
					if(f1 == 0.0F)
						f2 = f;
					else if(Math.abs(f1) >= f)
						f2 = 0.0F;
					else
						f2 = (float)Math.sqrt(Math.pow((double)Math.abs(f), 2.0D) - Math.pow((double)Math.abs(f1), 2.0D));
					f2 *= 0.5F;
				}
				//-//
				if(f2 < 0.0F) {
					-- j;
					--i1;
				}else {
					for(double d0 = 0.5D; j1 < i; ++j1) {
						double d1 = scaleWidth * (double)f2 * ((double)p_76484_2_.nextFloat() + 0.328D), d2 = (double)p_76484_2_.nextFloat() * 2.0D * Math.PI;
						int k1 = MathHelper.floor_double(d1 * Math.sin(d2) + (double)basePos[0] + d0),
							l1 = MathHelper.floor_double(d1 * Math.cos(d2) + (double)basePos[2] + d0);
						int[] aint1 = new int[] {k1, j, l1}, aint2 = new int[] {k1, j + leafDistanceLimit, l1};
						//-//
						if(checkBlockLine(p_76484_1_, aint1, aint2) == -1) {
							int[] aint3 = new int[] {basePos[0], basePos[1], basePos[2]};
							double d3 = Math.sqrt(Math.pow((double)Math.abs(basePos[0] - aint1[0]), 2.0D)
								+ Math.pow((double)Math.abs(basePos[2] - aint1[2]), 2.0D)),
								d4 = d3 * branchSlope;
							
							if((double)aint1[1] - d4 > (double)l)
								aint3[1] =                            l;
							else
								aint3[1] = (int)((double)aint1[1] - d4);
							
							if(checkBlockLine(p_76484_1_, aint3, aint1) == -1) {
								aint[k][0] =       k1;
								aint[k][1] =        j;
								aint[k][2] =       l1;
								aint[k][3] = aint3[1];
								++k;
							}
						}
					}
					
					-- j;
					--i1;
				}
			}
			
			leafNodes = new int[k][4];
			System.arraycopy(aint, 0, leafNodes, 0, k);
			
			int i_1 = 0;
			for(int j_1 = leafNodes.length; i_1 < j_1; ++i_1) {
				int l_2 = leafNodes[i_1][1];
				for(int i1_2 = leafNodes[i_1][1] + leafDistanceLimit; l_2 < i1_2; ++l_2) {
					float p1 = l_2 - leafNodes[i_1][1], p2 = p1 >= 0 && p1 < leafDistanceLimit ? (p1 != 0 && p1 != leafDistanceLimit - 1 ? 3.0F : 2.0F) : -1.0F;
					byte b1_3 = otherCoordPairs[1], b2_3 = otherCoordPairs[4];
					int[] aint_3 = new int[] {leafNodes[i_1][0], l_2, leafNodes[i_1][2]}, aint1_3 = new int[] {0, 0, 0};
					int l_3 = (int)((double)p2 + 0.618D), i1_3 = -l_3, j1_3 = -l_3;
					//-//
					for(aint1_3[1] = aint_3[1]; i1_3 <= l_3; ++i1_3) {
						aint1_3[b1_3] = aint_3[b1_3] + i1_3;
						j1_3 = -l_3;
						while(j1_3 <= l_3) {
							double d0_3 = Math.pow((double)Math.abs(i1_3) + 0.5D, 2.0D) + Math.pow((double)Math.abs(j1_3) + 0.5D, 2.0D);
							if(d0_3 > (double)(p2 * p2))
								++j1_3;
							else {
								aint1_3[b2_3] = aint_3[b2_3] + j1_3;
								Block block1 = p_76484_1_.getBlock(aint1_3[0], aint1_3[1], aint1_3[2]);
								if( !block1.isAir   (p_76484_1_, aint1_3[0], aint1_3[1], aint1_3[2]) &&
									!block1.isLeaves(p_76484_1_, aint1_3[0], aint1_3[1], aint1_3[2]))
									++j1_3;
								else {
									setBlockAndNotifyAdequately(p_76484_1_, aint1_3[0], aint1_3[1], aint1_3[2], bLeaves, metaLeaves);
									++j1_3;
								}
							}
						}
					}
				}
			}
			
			int[]
				aint_4  = new int[] {basePos[0], basePos[1]         , basePos[2]},
				aint1_4 = new int[] {basePos[0], basePos[1] + height, basePos[2]};
			func_150530_a(p_76484_1_, aint_4, aint1_4, bWood);
			//-//
			if(trunkSize == 2) {
				++aint_4 [0];
				++aint1_4[0];
				func_150530_a(p_76484_1_, aint_4, aint1_4, bWood);
				//-//
				++aint_4 [2];
				++aint1_4[2];
				func_150530_a(p_76484_1_, aint_4, aint1_4, bWood);
				//-//
				--aint_4 [0];
				--aint1_4[0];
				func_150530_a(p_76484_1_, aint_4, aint1_4, bWood);
			}
			
			int i_5 = 0;
			for(int[] aint_5 = new int[] {basePos[0], basePos[1], basePos[2]}; i_5 < leafNodes.length; ++i_5) {
				int[] aint1_5 = leafNodes[i_5], aint2_5 = new int[] {aint1_5[0], aint1_5[1], aint1_5[2]};
				aint_5[1] = aint1_5[3];
				//-//
				int k_5 = aint_5[1] - basePos[1];
				if((double)k_5 >= (double)heightLimit * 0.2D)
					func_150530_a(p_76484_1_, aint_5, aint2_5, bWood);
			}
			//-//
			return true;
		}
	}
	
	public void func_150530_a(World worldObj, int[] p_150530_1_, int[] p_150530_2_, Block p_150530_3_) {
		int[] aint2 = new int[] {0, 0, 0};
		byte b0 = 0, b1;
		for(b1 = 0; b0 < 3; ++b0) {
			aint2[b0] = p_150530_2_[b0] - p_150530_1_[b0];
			if(Math.abs(aint2[b0]) > Math.abs(aint2[b1]))
				b1 = b0;
		}
		
		if(aint2[b1] != 0) {
			byte b2 = otherCoordPairs[b1], b3 = otherCoordPairs[b1 + 3], b4;
			if(aint2[b1] > 0)
				b4 =  1;
			else
				b4 = -1;
			
			double d0 = (double)aint2[b2] / (double)aint2[b1], d1 = (double)aint2[b3] / (double)aint2[b1];
			int[] aint3 = new int[] {0, 0, 0};
			int i = 0;
			//-//
			for(int j = aint2[b1] + b4; i != j; i += b4) {
				aint3[b1] = MathHelper.floor_double((double)(p_150530_1_[b1] + i) + 0.5D);
				aint3[b2] = MathHelper.floor_double((double)p_150530_1_[b2] + (double)i * d0 + 0.5D);
				aint3[b3] = MathHelper.floor_double((double)p_150530_1_[b3] + (double)i * d1 + 0.5D);
				//-//
				byte b5 = 0;
				int k = Math.abs(aint3[0] - p_150530_1_[0]), l = Math.abs(aint3[2] - p_150530_1_[2]), i1 = Math.max(k, l);
				
				if(i1 > 0)
					if(k == i1)
						b5 = 4;
					else if(l == i1)
						b5 = 8;
				
				setBlockAndNotifyAdequately(worldObj, aint3[0], aint3[1], aint3[2], p_150530_3_, b5);
			}
		}
	}
	
	public int checkBlockLine(World worldObj, int[] p_76496_1_, int[] p_76496_2_) {
		int[] aint2 = new int[] {0, 0, 0};
		byte b0 = 0, b1;
		for(b1 = 0; b0 < 3; ++b0) {
			aint2[b0] = p_76496_2_[b0] - p_76496_1_[b0];
			if(Math.abs(aint2[b0]) > Math.abs(aint2[b1]))
				b1 = b0;
		}
		
		if(aint2[b1] == 0)
			return -1;
		else {
			byte b2 = otherCoordPairs[b1], b3 = otherCoordPairs[b1 + 3], b4;
			if(aint2[b1] > 0)
				b4 =  1;
			else
				b4 = -1;
			
			double d0 = (double)aint2[b2] / (double)aint2[b1], d1 = (double)aint2[b3] / (double)aint2[b1];
			int[] aint3 = new int[] {0, 0, 0};
			int i = 0, j;
			//-//
			for(j = aint2[b1] + b4; i != j; i += b4) {
				aint3[b1] = p_76496_1_[b1] + i;
				aint3[b2] = MathHelper.floor_double((double)p_76496_1_[b2] + (double)i * d0);
				aint3[b3] = MathHelper.floor_double((double)p_76496_1_[b3] + (double)i * d1);
				//-//
				Block block = worldObj.getBlock(aint3[0], aint3[1], aint3[2]);
				
				if(!isReplaceable(worldObj, aint3[0], aint3[1], aint3[2]))
					break;
			}
			//-//
			return i == j ? -1 : Math.abs(i);
		}
	}
}