package galaxyspace.systems.SolarSystem.moons.io.world.gen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenIoLava extends WorldGenerator
{
	private Block block;
	private Block spreadBlock;
	private int spreadBlockMeta;

	public WorldGenIoLava(Block block, Block spreadBlock, int spreadmeta)
	{
		this.block = block;
		this.spreadBlock = spreadBlock;
		this.spreadBlockMeta = spreadmeta;
	}

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z)
	{
		x -= 16;
		z -= 16;

		while (y > 5 && world.isAirBlock(x, y, z))
		{
			y--;
		}
		if (y <= 4)
		{
			return false;
		}

		y -= 4;
		boolean[] arrayOfBoolean = new boolean[2048];
		int i = rand.nextInt(4) + 4;

		for (int j = 0; j < i; j++)
		{
			double d1 = rand.nextDouble() * 6.0D + 3.0D;
			double d2 = rand.nextDouble() * 4.0D + 2.0D;
			double d3 = rand.nextDouble() * 6.0D + 3.0D;
			double d4 = rand.nextDouble() * (16.0D - d1 - 2.0D) + 1.0D + d1 / 2.0D;
			double d5 = rand.nextDouble() * (8.0D - d2 - 4.0D) + 2.0D + d2 / 2.0D;
			double d6 = rand.nextDouble() * (16.0D - d3 - 2.0D) + 1.0D + d3 / 2.0D;

			for (int i2 = 1; i2 < 15; i2++)
			{
				for (int i3 = 1; i3 < 15; i3++)
				{
					for (int i4 = 1; i4 < 7; i4++)
					{
						double d7 = (i2 - d4) / (d1 / 2.0D);
						double d8 = (i4 - d5) / (d2 / 2.0D);
						double d9 = (i3 - d6) / (d3 / 2.0D);
						double d10 = d7 * d7 + d8 * d8 + d9 * d9;

						if (d10 < 1.0D)
						{
							arrayOfBoolean[(i2 * 16 + i3) * 8 + i4] = true;
						}
					}
				}
			}
		}

		int k;
		int m;

		for (int j = 0; j < 16; j++)
		{
			for (k = 0; k < 16; k++)
			{
				for (m = 0; m < 8; m++)
				{
					int n = arrayOfBoolean[(j * 16 + k) * 8 + m] && (j < 15 && arrayOfBoolean[((j + 1) * 16 + k) * 8 + m] || j > 0 && arrayOfBoolean[((j - 1) * 16 + k) * 8 + m] || k < 15 && arrayOfBoolean[(j * 16 + k + 1) * 8 + m] || k > 0 && arrayOfBoolean[(j * 16 + k - 1) * 8 + m] || m < 7 && arrayOfBoolean[(j * 16 + k) * 8 + m + 1] || m > 0 && arrayOfBoolean[(j * 16 + k) * 8 + m - 1]) ? 1 : 0;

					if (n != 0)
					{
						Material localMaterial = world.getBlock(x + j, y + m, z + k).getMaterial();

						if (m >= 4 && localMaterial.isLiquid())
						{
							return false;
						}
						if (m < 4 && !localMaterial.isSolid() && world.getBlock(x + j, y + m, z + k) != this.block)
						{
							return false;
						}
					}
				}
			}
		}
		for (int j = 0; j < 16; j++)
		{
			for (k = 0; k < 16; k++)
			{
				for (m = 0; m < 8; m++)
				{
					if (arrayOfBoolean[(j * 16 + k) * 8 + m])
					{
						world.setBlock(x + j, y + m, z + k, m >= 4 ? Blocks.air : this.block, 0, 2);
					}
				}
			}
		}
		for (int j = 0; j < 16; j++)
		{
			for (k = 0; k < 16; k++)
			{
				for (m = 4; m < 8; m++)
				{
					if (arrayOfBoolean[(j * 16 + k) * 8 + m] && world.getBlock(x + j, y + m - 1, z + k) == Blocks.dirt && world.getSavedLightValue(EnumSkyBlock.Sky, x + j, y + m, z + k) > 0)
					{
						world.setBlock(x + j, y + m - 1, z + k, Blocks.grass, 0, 2);
					}
				}
			}
		}
		if (this.block.getMaterial() == Material.lava)
		{
			for (int j = 0; j < 16; j++)
			{
				for (k = 0; k < 16; k++)
				{
					for (m = 0; m < 8; m++)
					{
						int i1 = arrayOfBoolean[(j * 16 + k) * 8 + m] && (j < 15 && arrayOfBoolean[((j + 1) * 16 + k) * 8 + m] || j > 0 && arrayOfBoolean[((j - 1) * 16 + k) * 8 + m] || k < 15 && arrayOfBoolean[(j * 16 + k + 1) * 8 + m] || k > 0 && arrayOfBoolean[(j * 16 + k - 1) * 8 + m] || m < 7 && arrayOfBoolean[(j * 16 + k) * 8 + m + 1] || m > 0 && arrayOfBoolean[(j * 16 + k) * 8 + m - 1]) ? 1 : 0;

						if (i1 != 0 && (m < 4 || rand.nextInt(2) != 0) && world.getBlock(x + j, y + m, z + k).getMaterial().isSolid())
						{
							world.setBlock(x + j, y + m, z + k, this.spreadBlock, this.spreadBlockMeta, 2);
						}
					}
				}
			}
		}
		return true;
	}
}