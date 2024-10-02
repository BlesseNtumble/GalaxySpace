package galaxyspace.systems.BarnardsSystem.planets.barnardaE.world.gen;

import java.util.Random;

import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.systems.BarnardsSystem.core.registers.blocks.BRBlocks;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.MapGenBaseMeta;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class MapGenRavineBarnardaE extends MapGenBaseMeta
{
	private float[] field_75046_d = new float[1024];

	protected void func_151540_a(long seed, int x, int z, Block[] block, double p_151540_6_, double p_151540_8_, double p_151540_10_, float p_151540_12_, float p_151540_13_, float p_151540_14_, int p_151540_15_, int p_151540_16_, double p_151540_17_)
	{
		Random rand = new Random(seed);
		double d4 = x * 16 + 8;
		double d5 = z * 16 + 8;
		float f3 = 0.0F;
		float f4 = 0.0F;

		if (p_151540_16_ <= 0)
		{
			int j1 = this.range * 460 - 16;
			p_151540_16_ = j1 - rand.nextInt(j1 / 4);
		}

		boolean flag1 = false;

		if (p_151540_15_ == -1)
		{
			p_151540_15_ = p_151540_16_ / 2;
			flag1 = true;
		}

		float f5 = 1.0F;

		for (int k1 = 0; k1 < 256; ++k1)
		{
			if (k1 == 0 || rand.nextInt(3) == 0)
			{
				f5 = 1.0F + rand.nextFloat() * rand.nextFloat() * 1.0F;
			}
			this.field_75046_d[k1] = f5 * f5;
		}

		for (; p_151540_15_ < p_151540_16_; ++p_151540_15_)
		{
			double d12 = 5.5D + MathHelper.sin(p_151540_15_ * (float)Math.PI / p_151540_16_) * p_151540_12_ * 1.0F;
			double d6 = d12 * p_151540_17_;
			d12 *= rand.nextFloat() * 0.25D + 0.75D;
			d6 *= rand.nextFloat() * 0.25D + 0.75D;
			float f6 = MathHelper.cos(p_151540_14_);
			float f7 = MathHelper.sin(p_151540_14_);
			p_151540_6_ += MathHelper.cos(p_151540_13_) * f6;
			p_151540_8_ += f7;
			p_151540_10_ += MathHelper.sin(p_151540_13_) * f6;
			p_151540_14_ *= 0.7F;
			p_151540_14_ += f4 * 0.05F;
			p_151540_13_ += f3 * 0.05F;
			f4 *= 0.8F;
			f3 *= 0.5F;
			f4 += (rand.nextFloat() - rand.nextFloat()) * rand.nextFloat() * 2.0F;
			f3 += (rand.nextFloat() - rand.nextFloat()) * rand.nextFloat() * 4.0F;

			if (flag1 || rand.nextInt(4) != 0)
			{
				double d7 = p_151540_6_ - d4;
				double d8 = p_151540_10_ - d5;
				double d9 = p_151540_16_ - p_151540_15_;
				double d10 = p_151540_12_ + 2.0F + 16.0F;

				if (d7 * d7 + d8 * d8 - d9 * d9 > d10 * d10)
				{
					return;
				}

				if (p_151540_6_ >= d4 - 16.0D - d12 * 2.0D && p_151540_10_ >= d5 - 16.0D - d12 * 2.0D && p_151540_6_ <= d4 + 16.0D + d12 * 2.0D && p_151540_10_ <= d5 + 16.0D + d12 * 2.0D)
				{
					int i4 = MathHelper.floor_double(p_151540_6_ - d12) - x * 16 - 1;
					int l1 = MathHelper.floor_double(p_151540_6_ + d12) - x * 16 + 1;
					int j4 = MathHelper.floor_double(p_151540_8_ - d6) - 1;
					int i2 = MathHelper.floor_double(p_151540_8_ + d6) + 1;
					int k4 = MathHelper.floor_double(p_151540_10_ - d12) - z * 16 - 1;
					int j2 = MathHelper.floor_double(p_151540_10_ + d12) - z * 16 + 1;

					if (i4 < 0)
					{
						i4 = 0;
					}
					if (l1 > 16)
					{
						l1 = 16;
					}
					if (j4 < 1)
					{
						j4 = 1;
					}
					if (i2 > 248)
					{
						i2 = 248;
					}
					if (k4 < 0)
					{
						k4 = 0;
					}
					if (j2 > 16)
					{
						j2 = 16;
					}

					boolean flag2 = false;
					int k2;
					int j3;

					for (k2 = i4; !flag2 && k2 < l1; ++k2)
					{
						for (int l2 = k4; !flag2 && l2 < j2; ++l2)
						{
							for (int i3 = i2 + 1; !flag2 && i3 >= j4 - 1; --i3)
							{
								j3 = (k2 * 16 + l2) * 256 + i3;

								if (i3 >= 0 && i3 < 256)
								{
									if (this.isOceanBlock(block, j3, k2, i3, l2, x, z))
									{
										flag2 = true;
									}
									if (i3 != j4 - 1 && k2 != i4 && k2 != l1 - 1 && l2 != k4 && l2 != j2 - 1)
									{
										i3 = j4;
									}
								}
							}
						}
					}

					if (!flag2)
					{
						for (k2 = i4; k2 < l1; ++k2)
						{
							double d13 = (k2 + x * 16 + 0.5D - p_151540_6_) / d12;

							for (j3 = k4; j3 < j2; ++j3)
							{
								double d14 = (j3 + z * 16 + 0.5D - p_151540_10_) / d12;
								int k3 = (k2 * 16 + j3) * 256 + i2;
								boolean flag = false;

								if (d13 * d13 + d14 * d14 < 1.0D)
								{
									for (int l3 = i2 - 1; l3 >= j4; --l3)
									{
										double d11 = (l3 + 0.5D - p_151540_8_) / d6;

										if ((d13 * d13 + d14 * d14) * this.field_75046_d[l3] + d11 * d11 / 6.0D < 1.0D)
										{
											if (this.isTopBlock(block, k3, k2, l3, j3, x, z))
											{
												flag = true;
											}
											this.digBlock(block, k3, k2, l3, j3, x, z, flag);
										}
										--k3;
									}
								}
							}
						}
						if (flag1)
						{
							break;
						}
					}
				}
			}
		}
	}

	@Override
	protected void recursiveGenerate(World p_151538_1_, int chunkX, int chunkZ, int x, int z, Block[] block, byte[] metaArray)
	{
		if (this.rand.nextInt(20) == 0)
		{
			double d0 = chunkX * 16 + this.rand.nextInt(16);
			double d1 = this.rand.nextInt(50) + 90;
			double d2 = chunkZ * 16 + this.rand.nextInt(16);
			byte b0 = 1;

			for (int i1 = 0; i1 < b0; ++i1)
			{
				float f = this.rand.nextFloat() * (float)Math.PI * 2.0F;
				float f1 = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
				float f2 = (this.rand.nextFloat() * 2.0F + this.rand.nextFloat()) * 2.0F;
				this.func_151540_a(this.rand.nextLong(), x, z, block, d0, d1, d2, f2, f, f1, 0, 0, 6.0D);
			}
		}
	}

	protected boolean isOceanBlock(Block[] data, int index, int x, int y, int z, int chunkX, int chunkZ)
	{
		return data[index] == Blocks.water || data[index] == Blocks.flowing_water;
	}

	private boolean isTopBlock(Block[] data, int index, int x, int y, int z, int chunkX, int chunkZ)
	{
		BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(x + chunkX * 16, z + chunkZ * 16);
		return data[index] == biome.topBlock;
	}

	protected void digBlock(Block[] data, int index, int x, int y, int z, int chunkX, int chunkZ, boolean foundTop)
	{
		//BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(x + chunkX * 16, z + chunkZ * 16);
		Block top = BRBlocks.BarnardaEBlocks;
		Block block = data[index];

		if (block == BRBlocks.BarnardaEBlocks || block == top)
		{
		
			data[index] = null;

			/*
			if (foundTop && data[index - 1] == filler)
			{
				data[index - 1] = top;
			}	*/		
		}
	}
}