package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen;

import java.util.ArrayList;
import java.util.Random;

import galaxyspace.systems.BarnardsSystem.core.registers.BRBlocks;
import micdoodle8.mods.galacticraft.core.Constants;
import net.minecraft.block.Block;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.IPlantable;

public class WorldGenTree_Jungle extends WorldGenAbstractTree {

	private final IBlockState wood;
	private final IBlockState leaves;

	private final int type;
	private final int typeTree;

	protected IBlockState[] GetValidSpawnBlocks() {
		return new IBlockState[] { BRBlocks.BARNARDA_C_GRASS.getDefaultState(),
				BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(0) };
	}

	public WorldGenTree_Jungle(IBlockState wood, IBlockState leaves, int type, int typetree) {
		this(wood, leaves, false, type, typetree);
	}

	public WorldGenTree_Jungle(IBlockState wood, IBlockState leaves, boolean doBlockNotify, int type, int typetree) {
		super(doBlockNotify);

		this.wood = wood;
		this.leaves = leaves;

		this.type = type;
		this.typeTree = typetree;
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos position) {

		boolean flag = true;

		if (position.getY() >= 1 && position.getY() + 1 <= 256) {
			byte b0;
			int k1;
			Block block;
			Block blockdown;

			for (int i1 = position.getY(); i1 <= position.getY() + 1; ++i1) {
				b0 = 1;

				if (i1 == position.getY()) {
					b0 = 0;
				}

				if (i1 >= position.getY() + 1 - 2) {
					b0 = 2;
				}

				for (int j1 = position.getX() - b0; j1 <= position.getX() + b0 && flag; ++j1) {
					for (k1 = position.getZ() - b0; k1 <= position.getZ() + b0 && flag; ++k1) {
						if (i1 >= 0 && i1 < 256) {
							block = world.getBlockState(new BlockPos(j1, i1, k1)).getBlock();

							if (!this.isReplaceable(world, new BlockPos(j1, i1, k1))) {
								flag = false;
							}
						} else {
							flag = false;
						}
					}
				}
			}

			if (!flag) {
				return false;
			} else {
				final Block block2 = world.getBlockState(position.down()).getBlock();
				final boolean isSoil = block2.canSustainPlant(world.getBlockState(position.down()), world,
						position.down(), EnumFacing.UP, (IPlantable) BRBlocks.BARNARDA_C_DANDELIONS);

				if (isSoil && position.getY() < 256 - 1) {
					block2.onPlantGrow(world.getBlockState(position.down()), world, position.down(), position);
					b0 = 3;
					final byte b1 = 0;
					int l1;
					int i2;
					int j2;
					int i3;

					for (k1 = position.getY() - b0; k1 <= position.getY(); ++k1) {
						i3 = k1 - (position.getY());
						l1 = b1 + 1 - i3 / 2;

						for (i2 = position.getX() - l1; i2 <= position.getX() + l1; ++i2) {
							j2 = i2 - position.getX();

							for (int k2 = position.getZ() - l1; k2 <= position.getZ() + l1; ++k2) {
								final int l2 = k2 - position.getZ();

								if (Math.abs(j2) != l1 || Math.abs(l2) != l1 || rand.nextInt(2) != 0 && i3 != 0) {
									final Block block1 = world.getBlockState(new BlockPos(i2, k1, k2)).getBlock();

									if (block1.isAir(world.getBlockState(new BlockPos(i2, k1, k2)), world,
											new BlockPos(i2, k1, k2))
											|| block1.isLeaves(world.getBlockState(new BlockPos(i2, k1, k2)), world,
													new BlockPos(i2, k1, k2))) {

										// this.setBlockAndNotifyAdequately(par1World, i2, k1, k2, this.leaves,
										// metadata);
									}
								}
							}
						}
					}

					for (k1 = 0; k1 < 3; ++k1) {
						block = world.getBlockState(position.up(k1)).getBlock();

						if (block.isAir(world.getBlockState(position.up(k1)), world, position.up(k1)) || block.isLeaves(world.getBlockState(position.up(k1)), world, position.up(k1))) {
							// this.setBlockAndNotifyAdequately(par1World, par3, par4 + k1, par5,// this.wood);
							roots(world, rand, position.getX(), position.getY(), position.getZ());
							
						}
					}
					
				}
				return true;
			}
		}
		return false;

	}

	private void growVines(World world, int x, int y, int z, int flag) {
		this.setBlockAndNotifyAdequately(world, new BlockPos(x, y, z), Blocks.VINE.getStateFromMeta(flag));
		int i1 = 4;

		while (true) {
			--y;

			if (world.isAirBlock(new BlockPos(x, y, z)) || i1 <= 0) {
				return;
			}
			this.setBlockAndNotifyAdequately(world, new BlockPos(x, y, z), Blocks.VINE.getStateFromMeta(flag));
			--i1;
		}
	}

	public boolean LocationIsValidSpawn(World world, int x, int y, int z) {

		IBlockState checkBlock = world.getBlockState(new BlockPos(x, y - 1, z));
		IBlockState blockAbove = world.getBlockState(new BlockPos(x, y, z));
		IBlockState blockBelow = world.getBlockState(new BlockPos(x, y - 2, z));

		for (IBlockState i : GetValidSpawnBlocks()) {
			if (blockAbove != Blocks.AIR.getDefaultState()) {
				return false;
			}
			if (checkBlock == i) {
				return true;
			} else if (checkBlock == Blocks.SNOW_LAYER.getDefaultState() && blockBelow == i) {
				return true;
			} else if (checkBlock.getMaterial() == Material.PLANTS && blockBelow == i) {
				return true;
			}
		}
		return false;
	}

	private void setBlockAndNotifyAdequately(World world, int x, int y, int z, IBlockState state) {
		setBlockAndNotifyAdequately(world, new BlockPos(x, y, z), state);
	}

	private void roots(World par1World, Random rand, int i, int j, int k) {

		if (this.type == 1) {
			setBlockAndNotifyAdequately(par1World, i + 0, j + 0, k + 3, wood);
			setBlockAndNotifyAdequately(par1World, i + 0, j + 0, k + 4, wood);
			setBlockAndNotifyAdequately(par1World, i + 0, j + 1, k + 4, wood);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 1, k + 3, wood);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 1, k + 4, wood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 2, k + 3, wood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 2, k + 4, wood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 3, k + 4, wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 0, k + 0, wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 0, k + 1, wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 0, k + 7, wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 1, k + 1, wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 1, k + 6, wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 2, k + 2, wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 2, k + 3, wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 2, k + 4, wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 2, k + 5, wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 2, k + 6, wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 3, k + 3, wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 3, k + 4, wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 3, k + 5, wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 4, k + 3, wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 4, k + 4, wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 5, k + 3, wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 5, k + 4, wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 0, k + 0, wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 0, k + 5, wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 0, k + 7, wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 1, k + 1, wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 1, k + 2, wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 1, k + 6, wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 2, k + 2, wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 2, k + 3, wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 2, k + 4, wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 2, k + 5, wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 3, k + 3, wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 3, k + 4, wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 4, k + 3, wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 4, k + 4, wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 5, k + 3, wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 5, k + 4, wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 2, k + 3, wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 2, k + 4, wood);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 0, k + 4, wood);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 1, k + 3, wood);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 1, k + 4, wood);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 0, k + 2, wood);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 0, k + 4, wood);

		}
		if (this.type == 2) {

			// setBlockAndNotifyAdequately(par1World, i + 2, j + 0, k + 3, Blocks.chest, 0);
			setBlockAndNotifyAdequately(par1World, i + 0, j + 0, k + 1, wood);
			setBlockAndNotifyAdequately(par1World, i + 0, j + 0, k + 3, wood);
			setBlockAndNotifyAdequately(par1World, i + 0, j + 0, k + 5, wood);
			setBlockAndNotifyAdequately(par1World, i + 0, j + 1, k + 1, wood);
			setBlockAndNotifyAdequately(par1World, i + 0, j + 1, k + 3, wood);
			setBlockAndNotifyAdequately(par1World, i + 0, j + 1, k + 4, wood);
			setBlockAndNotifyAdequately(par1World, i + 0, j + 2, k + 4, wood);
			setBlockAndNotifyAdequately(par1World, i + 0, j + 3, k + 4, wood);
			setBlockAndNotifyAdequately(par1World, i + 0, j + 4, k + 4, wood);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 0, k + 5, wood);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 1, k + 5, wood);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 2, k + 1, wood);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 2, k + 2, wood);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 2, k + 5, wood);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 3, k + 2, wood);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 3, k + 3, wood);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 4, k + 3, wood);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 4, k + 4, wood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 0, k + 0, wood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 1, k + 1, wood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 2, k + 1, wood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 3, k + 1, wood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 3, k + 5, wood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 5, k + 3, wood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 5, k + 4, wood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 6, k + 3, wood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 6, k + 4, wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 0, k + 0, wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 0, k + 6, wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 1, k + 0, wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 1, k + 6, wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 2, k + 0, wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 2, k + 5, wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 2, k + 6, wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 3, k + 0, wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 3, k + 1, wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 3, k + 3, wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 3, k + 5, wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 4, k + 2, wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 4, k + 4, wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 5, k + 3, wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 5, k + 4, wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 6, k + 3, wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 6, k + 4, wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 0, k + 5, wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 1, k + 5, wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 2, k + 1, wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 2, k + 3, wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 2, k + 4, wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 3, k + 2, wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 3, k + 3, wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 0, k + 0, wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 0, k + 2, wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 0, k + 3, wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 1, k + 1, wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 1, k + 2, wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 1, k + 3, wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 2, k + 2, wood);

			setBlockAndNotifyAdequately(par1World, i + 2, j - 1, k + 3, wood);
			this.setBlockAndNotifyAdequately(par1World, new BlockPos(i + 2, j, k + 3),
					Blocks.MOB_SPAWNER.getDefaultState());
			TileEntityMobSpawner spawner = (TileEntityMobSpawner) par1World
					.getTileEntity(new BlockPos(i + 2, j, k + 3));

			if (spawner != null) {
				spawner.getSpawnerBaseLogic().setEntityId(getMob(rand));
			}

			this.setBlockAndNotifyAdequately(par1World, new BlockPos(i + 2, j - 2, k + 3),
					Blocks.CHEST.getDefaultState());
			TileEntityChest chest = (TileEntityChest) par1World.getTileEntity(new BlockPos(i + 2, j - 2, k + 3));
			if (chest != null) {
				chest.setLootTable(LootTableList.CHESTS_JUNGLE_TEMPLE, rand.nextLong());
			}
		}
		trees(par1World, i, j, k);

	}

	private static ResourceLocation getMob(Random rand) {
		switch (rand.nextInt(4)) {
		case 0:
			return new ResourceLocation(Constants.MOD_ID_CORE, "evolved_spider");
		case 1:
			return new ResourceLocation(Constants.MOD_ID_CORE, "evolved_creeper");
		case 2:
			return new ResourceLocation(Constants.MOD_ID_CORE, "evolved_skeleton");
		case 3:
		default:
			return new ResourceLocation(Constants.MOD_ID_CORE, "evolved_zombie");
		}
	}

	private void trees(World par1World, int i, int j, int k) {
		j += 6;

		if (typeTree == 1) {

			k -= 1;
			// TODO �����
			setBlockAndNotifyAdequately(par1World, i + 2, j + 4, k + 3, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 5, k + 2, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 6, k + 2, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 9, k + 7, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 0, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 0, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 1, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 1, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 2, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 2, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 3, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 3, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 4, k + 3, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 4, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 5, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 6, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 7, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 8, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 8, k + 6, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 9, k + 6, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 9, k + 7, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 9, k + 8, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 0, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 0, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 1, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 1, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 2, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 2, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 3, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 3, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 4, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 4, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 5, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 5, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 6, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 6, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 7, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 7, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 8, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 8, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 9, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 9, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 9, k + 7, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 10, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 10, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 10, k + 8, this.wood);
			// setBlockAndNotifyAdequately(par1World, i + 4, j + 10, k + 9, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 11, k + 3, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 11, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 11, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 12, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 13, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 14, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 15, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 16, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 17, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 12, k + 2, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 12, k + 3, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 17, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 17, k + 6, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 11, k + 1, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 12, k + 1, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 18, k + 6, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 18, k + 7, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 19, k + 7, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 11, k + 2, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 18, k + 8, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 11, k + 3, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 12, k + 3, this.wood);
			// TODO ������

			setBlockAndNotifyAdequately(par1World, i + 0, j + 5, k + 2, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 4, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 5, k + 1, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 5, k + 2, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 5, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 6, k + 2, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 9, k + 7, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 10, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 10, k + 7, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 4, k + 2, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 5, k + 1, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 5, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 6, k + 1, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 6, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 7, k + 2, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 8, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 8, k + 7, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 9, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 9, k + 8, this.leaves);

			setBlockAndNotifyAdequately(par1World, i + 2, j + 10, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 10, k + 7, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 10, k + 8, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 10, k + 9, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 11, k + 7, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 11, k + 8, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 11, k + 9, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 16, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 16, k + 4, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 16, k + 5, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 4, k + 2, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 5, k + 1, this.leaves);

			setBlockAndNotifyAdequately(par1World, i + 3, j + 5, k + 2, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 5, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 6, k + 1, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 6, k + 2, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 6, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 7, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 8, k + 7, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 8, k + 8, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 8, k + 9, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 9, k + 5, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 9, k + 9, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 10, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 10, k + 7, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 10, k + 8, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 10, k + 9, this.leaves);
			// setBlockAndNotifyAdequately(par1World, i + 3, j + 10, k + 10, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 11, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 11, k + 8, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 12, k + 8, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 16, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 16, k + 4, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 16, k + 5, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 16, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 17, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 17, k + 4, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 17, k + 5, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 17, k + 6, this.leaves);

			setBlockAndNotifyAdequately(par1World, i + 4, j + 4, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 5, k + 2, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 8, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 8, k + 7, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 8, k + 8, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 8, k + 9, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 9, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 9, k + 8, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 9, k + 9, this.leaves);
			// setBlockAndNotifyAdequately(par1World, i + 4, j + 9, k + 10, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 10, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 10, k + 7, this.leaves);
			// setBlockAndNotifyAdequately(par1World, i + 4, j + 10, k + 10, this.leaves);
			// setBlockAndNotifyAdequately(par1World, i + 4, j + 10, k + 11, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 11, k + 1, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 11, k + 2, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 11, k + 8, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 11, k + 9, this.leaves);
			// setBlockAndNotifyAdequately(par1World, i + 4, j + 11, k + 10, this.leaves);

			setBlockAndNotifyAdequately(par1World, i + 4, j + 12, k + 1, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 12, k + 2, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 12, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 12, k + 4, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 15, k + 4, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 16, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 16, k + 4, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 16, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 17, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 17, k + 4, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 17, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 17, k + 7, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 18, k + 5, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 18, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 9, k + 7, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 9, k + 9, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 10, k + 8, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 10, k + 9, this.leaves);
			// setBlockAndNotifyAdequately(par1World, i + 5, j + 10, k + 10, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 11, k + 0, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 11, k + 1, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 11, k + 2, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 11, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 11, k + 8, this.leaves);
			// setBlockAndNotifyAdequately(par1World, i + 5, j + 11, k + 10, this.leaves);

			setBlockAndNotifyAdequately(par1World, i + 5, j + 12, k + 1, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 13, k + 2, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 13, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 15, k + 5, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 16, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 16, k + 4, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 16, k + 5, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 16, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 17, k + 4, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 17, k + 7, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 17, k + 8, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 18, k + 5, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 18, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 18, k + 7, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 18, k + 8, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 19, k + 7, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 10, k + 1, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 11, k + 0, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 12, k + 0, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 12, k + 2, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 12, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 13, k + 0, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 13, k + 1, this.leaves);

			setBlockAndNotifyAdequately(par1World, i + 6, j + 13, k + 2, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 13, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 14, k + 2, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 16, k + 4, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 16, k + 5, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 17, k + 5, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 17, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 17, k + 7, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 17, k + 8, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 17, k + 9, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 18, k + 5, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 18, k + 8, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 18, k + 9, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 19, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 19, k + 8, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 19, k + 9, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 20, k + 7, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 10, k + 1, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 10, k + 2, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 11, k + 0, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 11, k + 1, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 11, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 12, k + 1, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 12, k + 2, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 12, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 13, k + 2, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 13, k + 3, this.leaves);

			setBlockAndNotifyAdequately(par1World, i + 7, j + 16, k + 5, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 17, k + 7, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 17, k + 8, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 18, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 18, k + 7, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 18, k + 9, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 19, k + 7, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 19, k + 8, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 10, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 11, k + 1, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 11, k + 2, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 11, k + 4, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 12, k + 1, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 12, k + 2, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 13, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 17, k + 8, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 17, k + 9, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 18, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 18, k + 7, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 18, k + 8, this.leaves);
			// setBlockAndNotifyAdequately(par1World, i + 9, j + 11, k + 3, this.leaves);
			// setBlockAndNotifyAdequately(par1World, i + 9, j + 12, k + 3, this.leaves);
		}
		if (typeTree == 2) {
			k -= 1;
			i -= 1;
			setBlockAndNotifyAdequately(par1World, i + 0, j + 13, k + 4, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 0, j + 14, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 11, k + 4, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 11, k + 5, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 12, k + 4, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 12, k + 5, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 13, k + 4, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 13, k + 5, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 14, k + 4, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 14, k + 5, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 14, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 15, k + 5, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 10, k + 4, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 10, k + 5, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 11, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 11, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 12, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 12, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 13, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 13, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 14, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 14, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 15, k + 4, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 15, k + 5, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 15, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 9, k + 4, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 10, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 10, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 11, k + 2, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 11, k + 3, this.leaves);
			// setBlockAndNotifyAdequately(par1World, i + 3, j + 11, k + 4, Blocks.chest,
			// 5);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 11, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 11, k + 7, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 12, k + 1, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 12, k + 2, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 12, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 12, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 12, k + 7, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 13, k + 2, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 13, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 13, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 13, k + 7, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 13, k + 8, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 14, k + 2, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 14, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 14, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 14, k + 7, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 15, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 15, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 15, k + 7, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 16, k + 4, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 16, k + 5, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 16, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 9, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 10, k + 7, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 11, k + 1, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 11, k + 8, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 12, k + 1, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 12, k + 8, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 13, k + 0, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 13, k + 1, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 13, k + 8, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 14, k + 1, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 14, k + 8, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 15, k + 2, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 15, k + 7, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 16, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 16, k + 4, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 16, k + 5, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 16, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 17, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 9, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 10, k + 2, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 10, k + 7, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 11, k + 1, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 11, k + 8, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 12, k + 1, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 12, k + 8, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 13, k + 1, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 13, k + 8, this.leaves);
			// setBlockAndNotifyAdequately(par1World, i + 5, j + 13, k + 9, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 14, k + 1, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 14, k + 8, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 15, k + 2, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 15, k + 7, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 15, k + 8, this.leaves);
			// setBlockAndNotifyAdequately(par1World, i + 5, j + 15, k + 9, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 16, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 16, k + 4, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 16, k + 5, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 16, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 16, k + 7, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 16, k + 8, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 17, k + 4, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 9, k + 4, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 10, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 10, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 10, k + 7, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 11, k + 2, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 11, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 11, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 11, k + 7, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 12, k + 2, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 12, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 12, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 12, k + 7, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 13, k + 2, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 13, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 13, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 13, k + 7, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 14, k + 2, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 14, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 14, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 14, k + 7, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 14, k + 8, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 15, k + 2, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 15, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 15, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 15, k + 7, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 16, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 16, k + 4, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 16, k + 5, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 10, k + 4, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 10, k + 5, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 11, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 12, k + 2, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 12, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 12, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 13, k + 2, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 13, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 13, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 14, k + 2, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 14, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 14, k + 6, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 15, k + 2, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 15, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 15, k + 4, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 15, k + 5, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 15, k + 7, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 11, k + 4, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 11, k + 5, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 12, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 12, k + 4, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 12, k + 5, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 13, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 13, k + 4, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 13, k + 5, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 14, k + 4, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 14, k + 5, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 15, k + 3, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 15, k + 4, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 15, k + 5, this.leaves);
			// setBlockAndNotifyAdequately(par1World, i + 9, j + 13, k + 4, this.leaves);
			// setBlockAndNotifyAdequately(par1World, i + 9, j + 13, k + 5, this.leaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 11, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 11, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 12, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 12, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 13, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 13, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 14, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 14, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 10, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 10, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 15, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 15, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 0, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 0, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 1, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 1, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 2, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 2, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 3, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 3, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 4, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 4, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 5, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 5, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 6, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 6, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 7, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 7, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 8, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 8, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 9, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 9, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 10, k + 3, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 10, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 10, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 10, k + 6, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 11, k + 2, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 11, k + 7, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 12, k + 2, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 12, k + 7, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 13, k + 2, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 13, k + 7, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 14, k + 2, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 14, k + 7, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 15, k + 3, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 15, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 15, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 15, k + 6, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 0, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 0, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 1, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 1, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 2, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 2, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 3, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 3, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 4, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 4, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 5, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 5, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 6, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 6, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 7, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 7, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 8, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 8, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 9, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 9, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 10, k + 3, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 10, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 10, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 10, k + 6, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 11, k + 2, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 11, k + 7, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 12, k + 2, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 12, k + 7, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 13, k + 2, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 13, k + 7, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 14, k + 2, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 14, k + 7, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 15, k + 3, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 15, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 15, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 15, k + 6, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 10, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 10, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 15, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 15, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 11, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 11, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 12, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 12, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 13, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 13, k + 5, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 14, k + 4, this.wood);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 14, k + 5, this.wood);
		}

	}

}