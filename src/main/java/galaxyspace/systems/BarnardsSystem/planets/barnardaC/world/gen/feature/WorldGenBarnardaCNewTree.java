package galaxyspace.systems.BarnardsSystem.planets.barnardaC.world.gen.feature;

import java.util.ArrayList;
import java.util.Random;

import galaxyspace.systems.BarnardsSystem.core.registers.blocks.BRBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Direction;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class WorldGenBarnardaCNewTree extends WorldGenAbstractTree
{

	private final Block wood;
	private final Block leaves;
	
	private final int metaWood;
	private final int metaLeaves;
	private final int type;
	private final int typeTree;


    private final ArrayList<ChunkCoordinates> chests = new ArrayList<ChunkCoordinates>();
    private final ArrayList<ChunkCoordinates> spawners = new ArrayList<ChunkCoordinates>();
    
	protected Block[] GetValidSpawnBlocks()
	{
		return new Block[]
		{
			BRBlocks.BarnardaCGrass,
			BRBlocks.BarnardaCBlocks,
			BRBlocks.BarnardaCLog
		};
	}
	
	public WorldGenBarnardaCNewTree(Block wood, Block leaves, int metaWood, int metaLeaves, int type, int typetree)
	{
		this(wood, leaves, metaWood, metaLeaves, false, type, typetree);
	}

	public WorldGenBarnardaCNewTree(Block wood, Block leaves, int metaWood, int metaLeaves, boolean doBlockNotify, int type, int typetree)
	{
		super(doBlockNotify);

		this.wood = wood;
		this.leaves = leaves;
		this.metaWood = metaWood;
		this.metaLeaves = metaLeaves;
		this.type = type;
		this.typeTree = typetree;
	}

	@Override
	public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
	{
		
				
		boolean flag = true;

		if (par4 >= 1 && par4 + 1 <= 256)
		{
			byte b0;
			int k1;
			Block block;
			Block blockdown;

			for (int i1 = par4; i1 <= par4 + 1; ++i1)
			{
				b0 = 1;

				if (i1 == par4)
				{
					b0 = 0;
				}

				if (i1 >= par4 + 1 - 2)
				{
					b0 = 2;
				}

				for (int j1 = par3 - b0; j1 <= par3 + b0 && flag; ++j1)
				{
					for (k1 = par5 - b0; k1 <= par5 + b0 && flag; ++k1)
					{
						if (i1 >= 0 && i1 < 256)
						{
							block = par1World.getBlock(j1, i1, k1);

							if (!this.isReplaceable(par1World, j1, i1, k1))
							{
								flag = false;
							}
						}
						else
						{
							flag = false;
						}
					}
				}
			}

			if (!flag)
			{
				return false;
			}
			else
			{
				final Block block2 = par1World.getBlock(par3, par4 - 1, par5);
				final boolean isSoil = block2.canSustainPlant(par1World, par3, par4 - 1, par5, ForgeDirection.UP, (IPlantable) BRBlocks.BarnardaCDandelions);

				if (isSoil && par4 < 256 - 1)
				{
					block2.onPlantGrow(par1World, par3, par4 - 1, par5, par3, par4, par5);
					b0 = 3;
					final byte b1 = 0;
					int l1;
					int i2;
					int j2;
					int i3;

					for (k1 = par4 - b0; k1 <= par4; ++k1)
					{
						i3 = k1 - (par4);
						l1 = b1 + 1 - i3 / 2;

						for (i2 = par3 - l1; i2 <= par3 + l1; ++i2)
						{
							j2 = i2 - par3;

							for (int k2 = par5 - l1; k2 <= par5 + l1; ++k2)
							{
								final int l2 = k2 - par5;

								if (Math.abs(j2) != l1 || Math.abs(l2) != l1 || par2Random.nextInt(2) != 0 && i3 != 0)
								{
									final Block block1 = par1World.getBlock(i2, k1, k2);

									if (block1.isAir(par1World, i2, k1, k2) || block1.isLeaves(par1World, i2, k1, k2))
									{
									
										//this.setBlockAndNotifyAdequately(par1World, i2, k1, k2, this.leaves, metadata);
									}
								}
							}
						}
					}

					for (k1 = 0; k1 < 3; ++k1)
					{
						block = par1World.getBlock(par3, par4 + k1, par5);

						if (block.isAir(par1World, par3, par4 + k1, par5) || block.isLeaves(par1World, par3, par4 + k1, par5))
						{
							//this.setBlockAndNotifyAdequately(par1World, par3, par4 + k1, par5, this.wood, this.metaWood);
							roots(par1World, par2Random, par3 , par4, par5);
											
						}
					}

						
					}
					return true;
				}				
			}
		return false;
		
		
	}

	private void growVines(World world, int x, int y, int z, int flag)
	{
		this.setBlockAndNotifyAdequately(world, x, y, z, Blocks.vine, flag);
		int i1 = 4;

		while (true)
		{
			--y;

			if (world.getBlock(x, y, z).isAir(world, x, y, z) || i1 <= 0)
			{
				return;
			}
			this.setBlockAndNotifyAdequately(world, x, y, z, Blocks.vine, flag);
			--i1;
		}
	}
	
	public boolean LocationIsValidSpawn(World world, int x, int y, int z)
 {

		Block checkBlock = world.getBlock(x, y - 1, z);
		Block blockAbove = world.getBlock(x, y , z);
		Block blockBelow = world.getBlock(x, y - 2, z);

		for (Block i : GetValidSpawnBlocks())
		{
			if (blockAbove != Blocks.air)
			{
				return false;
			}
			if (checkBlock == i)
			{
				return true;
			}
			else if (checkBlock == Blocks.snow_layer && blockBelow == i)
			{
				return true;
			}
			else if (checkBlock.getMaterial() == Material.plants && blockBelow == i)
			{
				return true;
			}
		}
		return false;
	}
	
	private void roots(World par1World, Random rand, int i, int j, int k)
	{

		if(this.type == 1)
		{
			setBlockAndNotifyAdequately(par1World, i + 0, j + 0, k + 3, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 0, j + 0, k + 4, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 0, j + 1, k + 4, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 1, k + 3, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 1, k + 4, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 2, k + 3, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 2, k + 4, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 3, k + 4, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 0, k + 0, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 0, k + 1, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 0, k + 7, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 1, k + 1, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 1, k + 6, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 2, k + 2, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 2, k + 3, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 2, k + 4, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 2, k + 5, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 2, k + 6, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 3, k + 3, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 3, k + 4, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 3, k + 5, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 4, k + 3, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 4, k + 4, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 5, k + 3, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 5, k + 4, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 0, k + 0, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 0, k + 5, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 0, k + 7, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 1, k + 1, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 1, k + 2, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 1, k + 6, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 2, k + 2, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 2, k + 3, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 2, k + 4, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 2, k + 5, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 3, k + 3, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 3, k + 4, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 4, k + 3, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 4, k + 4, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 5, k + 3, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 5, k + 4, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 2, k + 3, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 2, k + 4, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 0, k + 4, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 1, k + 3, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 1, k + 4, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 0, k + 2, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 0, k + 4, wood, metaWood);
			
		}
		if(this.type == 2)
		{
			
			//setBlockAndNotifyAdequately(par1World, i + 2, j + 0, k + 3, Blocks.chest, 0);
			setBlockAndNotifyAdequately(par1World, i + 0, j + 0, k + 1, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 0, j + 0, k + 3, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 0, j + 0, k + 5, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 0, j + 1, k + 1, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 0, j + 1, k + 3, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 0, j + 1, k + 4, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 0, j + 2, k + 4, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 0, j + 3, k + 4, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 0, j + 4, k + 4, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 0, k + 5, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 1, k + 5, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 2, k + 1, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 2, k + 2, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 2, k + 5, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 3, k + 2, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 3, k + 3, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 4, k + 3, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 4, k + 4, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 0, k + 0, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 1, k + 1, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 2, k + 1, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 3, k + 1, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 3, k + 5, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 5, k + 3, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 5, k + 4, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 6, k + 3, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 6, k + 4, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 0, k + 0, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 0, k + 6, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 1, k + 0, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 1, k + 6, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 2, k + 0, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 2, k + 5, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 2, k + 6, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 3, k + 0, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 3, k + 1, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 3, k + 3, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 3, k + 5, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 4, k + 2, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 4, k + 4, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 5, k + 3, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 5, k + 4, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 6, k + 3, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 6, k + 4, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 0, k + 5, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 1, k + 5, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 2, k + 1, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 2, k + 3, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 2, k + 4, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 3, k + 2, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 3, k + 3, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 0, k + 0, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 0, k + 2, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 0, k + 3, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 1, k + 1, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 1, k + 2, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 1, k + 3, wood, metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 2, k + 2, wood, metaWood);
			
			this.spawners.add(new ChunkCoordinates(i + 2, j, k + 3));
			setBlockAndNotifyAdequately(par1World, i + 2, j - 1, k + 3, wood, metaWood);
			this.chests.add(new ChunkCoordinates(i + 2, j - 2, k + 3));			
			
			if (!this.chests.isEmpty())
	        {
	            if(par1World.getBlock(this.chests.get(0).posX, this.chests.get(0).posY, this.chests.get(0).posZ) != Blocks.chest) par1World.setBlock(this.chests.get(0).posX, this.chests.get(0).posY, this.chests.get(0).posZ, Blocks.chest, 0, 2);
	            TileEntityChest chest = (TileEntityChest) par1World.getTileEntity(this.chests.get(0).posX, this.chests.get(0).posY, this.chests.get(0).posZ);

	            if (chest != null)
	            {
	                for (int o = 0; o < chest.getSizeInventory(); o++)
	                {
	                    chest.setInventorySlotContents(o, null);
	                }

	                ChestGenHooks info = ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST);

	                WeightedRandomChestContent.generateChestContents(rand, info.getItems(rand), chest, info.getCount(rand));
	            }

	            this.chests.clear();
	        }
			
			for (final ChunkCoordinates spawnerCoords : this.spawners)
	        {
				if(par1World.getBlock(this.spawners.get(0).posX, this.spawners.get(0).posY, this.spawners.get(0).posZ) != Blocks.mob_spawner) par1World.setBlock(this.spawners.get(0).posX, this.spawners.get(0).posY, this.spawners.get(0).posZ, Blocks.mob_spawner, 0, 2);
		           
	            if (par1World.getBlock(spawnerCoords.posX, spawnerCoords.posY, spawnerCoords.posZ) == Blocks.mob_spawner)
	            {
	                final TileEntityMobSpawner spawner = (TileEntityMobSpawner) par1World.getTileEntity(spawnerCoords.posX, spawnerCoords.posY, spawnerCoords.posZ);
	                if (spawner != null)
	                {
	                    spawner.func_145881_a().setEntityName(this.getMob(rand));
	                }
	            }
	        }
		}
		trees(par1World, i, j, k);
		
	}
	
	private static String getMob(Random rand)
    {
        switch (rand.nextInt(1))
        {
        case 0:
            return "GalacticraftCore.EvolvedSkeleton";
        default:
            return "GalacticraftCore.EvolvedCreeper";
        }
    }
	
	private void trees(World par1World, int i, int j, int k)
	{
		j += 6;
		
		if(typeTree == 1)
		{
			
			k -= 1;
			//TODO �����
			setBlockAndNotifyAdequately(par1World, i + 2, j + 4, k + 3, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 5, k + 2, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 6, k + 2, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 9, k + 7, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 0, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 0, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 1, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 1, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 2, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 2, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 3, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 3, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 4, k + 3, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 4, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 5, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 6, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 7, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 8, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 8, k + 6, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 9, k + 6, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 9, k + 7, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 9, k + 8, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 0, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 0, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 1, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 1, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 2, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 2, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 3, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 3, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 4, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 4, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 5, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 5, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 6, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 6, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 7, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 7, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 8, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 8, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 9, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 9, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 9, k + 7, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 10, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 10, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 10, k + 8, this.wood, this.metaWood);
			//setBlockAndNotifyAdequately(par1World, i + 4, j + 10, k + 9, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 11, k + 3, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 11, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 11, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 12, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 13, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 14, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 15, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 16, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 17, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 12, k + 2, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 12, k + 3, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 17, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 17, k + 6, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 11, k + 1, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 12, k + 1, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 18, k + 6, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 18, k + 7, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 19, k + 7, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 11, k + 2, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 18, k + 8, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 11, k + 3, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 12, k + 3, this.wood, this.metaWood);
			//TODO ������
			
			setBlockAndNotifyAdequately(par1World, i + 0, j + 5, k + 2, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 4, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 5, k + 1, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 5, k + 2, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 5, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 6, k + 2, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 9, k + 7, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 10, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 10, k + 7, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 4, k + 2, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 5, k + 1, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 5, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 6, k + 1, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 6, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 7, k + 2, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 8, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 8, k + 7, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 9, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 9, k + 8, this.leaves, this.metaLeaves);
			
			setBlockAndNotifyAdequately(par1World, i + 2, j + 10, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 10, k + 7, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 10, k + 8, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 10, k + 9, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 11, k + 7, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 11, k + 8, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 11, k + 9, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 16, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 16, k + 4, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 16, k + 5, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 4, k + 2, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 5, k + 1, this.leaves, this.metaLeaves);
			
			setBlockAndNotifyAdequately(par1World, i + 3, j + 5, k + 2, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 5, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 6, k + 1, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 6, k + 2, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 6, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 7, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 8, k + 7, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 8, k + 8, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 8, k + 9, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 9, k + 5, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 9, k + 9, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 10, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 10, k + 7, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 10, k + 8, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 10, k + 9, this.leaves, this.metaLeaves);
			//setBlockAndNotifyAdequately(par1World, i + 3, j + 10, k + 10, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 11, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 11, k + 8, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 12, k + 8, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 16, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 16, k + 4, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 16, k + 5, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 16, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 17, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 17, k + 4, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 17, k + 5, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 17, k + 6, this.leaves, this.metaLeaves);
			
			setBlockAndNotifyAdequately(par1World, i + 4, j + 4, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 5, k + 2, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 8, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 8, k + 7, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 8, k + 8, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 8, k + 9, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 9, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 9, k + 8, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 9, k + 9, this.leaves, this.metaLeaves);
			//setBlockAndNotifyAdequately(par1World, i + 4, j + 9, k + 10, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 10, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 10, k + 7, this.leaves, this.metaLeaves);
			//setBlockAndNotifyAdequately(par1World, i + 4, j + 10, k + 10, this.leaves, this.metaLeaves);
			//setBlockAndNotifyAdequately(par1World, i + 4, j + 10, k + 11, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 11, k + 1, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 11, k + 2, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 11, k + 8, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 11, k + 9, this.leaves, this.metaLeaves);
			//setBlockAndNotifyAdequately(par1World, i + 4, j + 11, k + 10, this.leaves, this.metaLeaves);
			
			setBlockAndNotifyAdequately(par1World, i + 4, j + 12, k + 1, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 12, k + 2, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 12, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 12, k + 4, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 15, k + 4, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 16, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 16, k + 4, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 16, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 17, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 17, k + 4, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 17, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 17, k + 7, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 18, k + 5, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 18, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 9, k + 7, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 9, k + 9, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 10, k + 8, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 10, k + 9, this.leaves, this.metaLeaves);
			//setBlockAndNotifyAdequately(par1World, i + 5, j + 10, k + 10, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 11, k + 0, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 11, k + 1, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 11, k + 2, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 11, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 11, k + 8, this.leaves, this.metaLeaves);
			//setBlockAndNotifyAdequately(par1World, i + 5, j + 11, k + 10, this.leaves, this.metaLeaves);
			
			setBlockAndNotifyAdequately(par1World, i + 5, j + 12, k + 1, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 13, k + 2, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 13, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 15, k + 5, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 16, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 16, k + 4, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 16, k + 5, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 16, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 17, k + 4, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 17, k + 7, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 17, k + 8, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 18, k + 5, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 18, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 18, k + 7, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 18, k + 8, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 19, k + 7, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 10, k + 1, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 11, k + 0, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 12, k + 0, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 12, k + 2, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 12, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 13, k + 0, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 13, k + 1, this.leaves, this.metaLeaves);
			
			setBlockAndNotifyAdequately(par1World, i + 6, j + 13, k + 2, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 13, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 14, k + 2, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 16, k + 4, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 16, k + 5, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 17, k + 5, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 17, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 17, k + 7, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 17, k + 8, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 17, k + 9, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 18, k + 5, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 18, k + 8, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 18, k + 9, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 19, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 19, k + 8, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 19, k + 9, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 20, k + 7, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 10, k + 1, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 10, k + 2, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 11, k + 0, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 11, k + 1, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 11, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 12, k + 1, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 12, k + 2, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 12, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 13, k + 2, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 13, k + 3, this.leaves, this.metaLeaves);
			
			
			setBlockAndNotifyAdequately(par1World, i + 7, j + 16, k + 5, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 17, k + 7, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 17, k + 8, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 18, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 18, k + 7, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 18, k + 9, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 19, k + 7, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 19, k + 8, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 10, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 11, k + 1, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 11, k + 2, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 11, k + 4, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 12, k + 1, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 12, k + 2, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 13, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 17, k + 8, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 17, k + 9, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 18, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 18, k + 7, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 18, k + 8, this.leaves, this.metaLeaves);
			//setBlockAndNotifyAdequately(par1World, i + 9, j + 11, k + 3, this.leaves, this.metaLeaves);
			//setBlockAndNotifyAdequately(par1World, i + 9, j + 12, k + 3, this.leaves, this.metaLeaves);
		}
		if(typeTree == 2)
		{
			k -= 1;
			i -= 1;
			setBlockAndNotifyAdequately(par1World, i + 0, j + 13, k + 4, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 0, j + 14, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 11, k + 4, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 11, k + 5, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 12, k + 4, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 12, k + 5, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 13, k + 4, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 13, k + 5, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 14, k + 4, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 14, k + 5, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 14, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 1, j + 15, k + 5, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 10, k + 4, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 10, k + 5, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 11, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 11, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 12, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 12, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 13, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 13, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 14, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 14, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 15, k + 4, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 15, k + 5, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 15, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 9, k + 4, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 10, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 10, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 11, k + 2, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 11, k + 3, this.leaves, this.metaLeaves);
			//setBlockAndNotifyAdequately(par1World, i + 3, j + 11, k + 4, Blocks.chest, 5);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 11, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 11, k + 7, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 12, k + 1, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 12, k + 2, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 12, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 12, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 12, k + 7, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 13, k + 2, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 13, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 13, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 13, k + 7, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 13, k + 8, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 14, k + 2, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 14, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 14, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 14, k + 7, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 15, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 15, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 15, k + 7, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 16, k + 4, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 16, k + 5, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 16, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 9, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 10, k + 7, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 11, k + 1, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 11, k + 8, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 12, k + 1, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 12, k + 8, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 13, k + 0, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 13, k + 1, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 13, k + 8, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 14, k + 1, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 14, k + 8, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 15, k + 2, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 15, k + 7, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 16, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 16, k + 4, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 16, k + 5, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 16, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 17, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 9, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 10, k + 2, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 10, k + 7, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 11, k + 1, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 11, k + 8, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 12, k + 1, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 12, k + 8, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 13, k + 1, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 13, k + 8, this.leaves, this.metaLeaves);
			//setBlockAndNotifyAdequately(par1World, i + 5, j + 13, k + 9, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 14, k + 1, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 14, k + 8, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 15, k + 2, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 15, k + 7, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 15, k + 8, this.leaves, this.metaLeaves);
			//setBlockAndNotifyAdequately(par1World, i + 5, j + 15, k + 9, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 16, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 16, k + 4, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 16, k + 5, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 16, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 16, k + 7, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 16, k + 8, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 17, k + 4, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 9, k + 4, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 10, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 10, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 10, k + 7, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 11, k + 2, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 11, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 11, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 11, k + 7, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 12, k + 2, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 12, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 12, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 12, k + 7, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 13, k + 2, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 13, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 13, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 13, k + 7, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 14, k + 2, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 14, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 14, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 14, k + 7, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 14, k + 8, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 15, k + 2, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 15, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 15, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 15, k + 7, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 16, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 16, k + 4, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 16, k + 5, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 10, k + 4, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 10, k + 5, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 11, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 12, k + 2, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 12, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 12, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 13, k + 2, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 13, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 13, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 14, k + 2, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 14, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 14, k + 6, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 15, k + 2, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 15, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 15, k + 4, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 15, k + 5, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 15, k + 7, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 11, k + 4, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 11, k + 5, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 12, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 12, k + 4, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 12, k + 5, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 13, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 13, k + 4, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 13, k + 5, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 14, k + 4, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 14, k + 5, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 15, k + 3, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 15, k + 4, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 8, j + 15, k + 5, this.leaves, this.metaLeaves);
			//setBlockAndNotifyAdequately(par1World, i + 9, j + 13, k + 4, this.leaves, this.metaLeaves);
			//setBlockAndNotifyAdequately(par1World, i + 9, j + 13, k + 5, this.leaves, this.metaLeaves);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 11, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 11, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 12, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 12, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 13, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 13, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 14, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 2, j + 14, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 10, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 10, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 15, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 3, j + 15, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 0, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 0, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 1, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 1, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 2, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 2, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 3, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 3, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 4, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 4, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 5, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 5, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 6, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 6, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 7, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 7, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 8, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 8, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 9, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 9, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 10, k + 3, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 10, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 10, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 10, k + 6, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 11, k + 2, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 11, k + 7, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 12, k + 2, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 12, k + 7, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 13, k + 2, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 13, k + 7, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 14, k + 2, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 14, k + 7, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 15, k + 3, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 15, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 15, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 4, j + 15, k + 6, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 0, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 0, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 1, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 1, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 2, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 2, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 3, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 3, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 4, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 4, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 5, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 5, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 6, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 6, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 7, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 7, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 8, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 8, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 9, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 9, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 10, k + 3, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 10, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 10, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 10, k + 6, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 11, k + 2, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 11, k + 7, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 12, k + 2, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 12, k + 7, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 13, k + 2, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 13, k + 7, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 14, k + 2, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 14, k + 7, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 15, k + 3, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 15, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 15, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 5, j + 15, k + 6, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 10, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 10, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 15, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 6, j + 15, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 11, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 11, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 12, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 12, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 13, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 13, k + 5, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 14, k + 4, this.wood, this.metaWood);
			setBlockAndNotifyAdequately(par1World, i + 7, j + 14, k + 5, this.wood, this.metaWood);
		}
		
	}

		
}