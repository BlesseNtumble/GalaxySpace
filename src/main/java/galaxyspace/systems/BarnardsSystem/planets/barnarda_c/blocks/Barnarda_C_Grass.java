package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks;

import java.util.Random;

import galaxyspace.core.util.GSCreativeTabs;
import galaxyspace.systems.BarnardsSystem.core.BRBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Barnarda_C_Grass extends Block implements IGrowable{

	public static final PropertyEnum<EnumBlockGrass> BASIC_TYPE = PropertyEnum.create("type", EnumBlockGrass.class);
	
	public Barnarda_C_Grass() {
		super(Material.GRASS);
		this.setTranslationKey("barnarda_c_grasses");
		this.setHardness(0.8F);
        this.setSoundType(SoundType.PLANT);
        this.setHarvestLevel("shovel", 0);
	}

	@Override
	public CreativeTabs getCreativeTab()
    {
        return GSCreativeTabs.GSBlocksTab;
    }
	
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(state));
	}
	
	@SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (EnumBlockGrass blockBasic : EnumBlockGrass.values())
        {
            list.add(new ItemStack(this, 1, blockBasic.getMeta()));
        }
    }
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        //Block block = worldIn.getBlockState(pos.up()).getBlock();
        return state;//state.withProperty(SNOWY, Boolean.valueOf(block == Blocks.SNOW || block == Blocks.SNOW_LAYER));
    }

	@Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!worldIn.isRemote)
        {
            if (!worldIn.isAreaLoaded(pos, 3)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
            if (worldIn.getLightFromNeighbors(pos.up()) < 4 && worldIn.getBlockState(pos.up()).getLightOpacity(worldIn, pos.up()) > 2)
            {
                worldIn.setBlockState(pos, BRBlocks.BARNARDA_C_BLOCKS.getDefaultState().withProperty(Barnarda_C_Blocks.BASIC_TYPE, Barnarda_C_Blocks.EnumBlockBarnardaC.DIRT));
            }
            else
            {
                if (worldIn.getLightFromNeighbors(pos.up()) >= 9)
                {
                    for (int i = 0; i < 4; ++i)
                    {
                        BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);

                        if (blockpos.getY() >= 0 && blockpos.getY() < 256 && !worldIn.isBlockLoaded(blockpos))
                        {
                            return;
                        }

                        IBlockState iblockstate = worldIn.getBlockState(blockpos.up());
                        IBlockState iblockstate1 = worldIn.getBlockState(blockpos);
/*
                        if (iblockstate1.getBlock() ==  BRBlocks.BARNARDA_C_GRASS && iblockstate1.getValue(Barnarda_C_Blocks.BASIC_TYPE) == Barnarda_C_Blocks.EnumBlockBarnardaC.DIRT && worldIn.getLightFromNeighbors(blockpos.up()) >= 4 && iblockstate.getLightOpacity(worldIn, pos.up()) <= 2)
                        {
                            worldIn.setBlockState(blockpos, BRBlocks.BARNARDA_C_GRASS.getDefaultState());
                        }*/
                    }
                }
            }
        }
    }
	
	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return BRBlocks.BARNARDA_C_BLOCKS.getItemDropped(BRBlocks.BARNARDA_C_BLOCKS.getDefaultState().withProperty(Barnarda_C_Blocks.BASIC_TYPE, Barnarda_C_Blocks.EnumBlockBarnardaC.DIRT), rand, fortune);
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

	@Override
	public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, net.minecraftforge.common.IPlantable plantable)
    {
		return true;
    }
	
	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return true;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return true;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		BlockPos blockpos = pos.up();

        for (int i = 0; i < 128; ++i)
        {
            BlockPos blockpos1 = blockpos;
            int j = 0;

            while (true)
            {
                if (j >= i / 16)
                {
                    if (worldIn.isAirBlock(blockpos1))
                    {
                        /*if (rand.nextInt(8) == 0)
                        {
                            worldIn.getBiome(blockpos1).plantFlower(worldIn, rand, blockpos1);
                        }
                        else*/
                        {
                            IBlockState iblockstate1 = BRBlocks.BARNARDA_C_DANDELIONS.getDefaultState().withProperty(Barnarda_C_Dandelions.BASIC_TYPE, Barnarda_C_Dandelions.EnumBlockDandelions.GRASS);

                           if (((Barnarda_C_Dandelions) BRBlocks.BARNARDA_C_DANDELIONS).canPlaceBlockAt(worldIn, blockpos1))
                            {
                                worldIn.setBlockState(blockpos1, iblockstate1, 3);
                            }
                        }
                    }

                    break;
                }

                blockpos1 = blockpos1.add(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);

                if (worldIn.getBlockState(blockpos1.down()).getBlock() != BRBlocks.BARNARDA_C_GRASS || worldIn.getBlockState(blockpos1).isNormalCube())
                {
                    break;
                }

                ++j;
            }
        }
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	public static enum EnumBlockGrass implements IStringSerializable {
		GRASS(0, "barnarda_c_grass"),
		GRASS_1(1, "barnarda_c_grass_1");

		private final int meta;
		private final String name;

		private EnumBlockGrass(int meta, String name) {
			this.meta = meta;
			this.name = name;
		}

		public int getMeta() {
			return this.meta;
		}

		public static EnumBlockGrass byMetadata(int meta) {
			return values()[meta];
		}

		@Override
		public String getName() {
			return this.name;
		}

	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockGrass.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumBlockGrass) state.getValue(BASIC_TYPE)).getMeta();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, BASIC_TYPE);
	}
}
