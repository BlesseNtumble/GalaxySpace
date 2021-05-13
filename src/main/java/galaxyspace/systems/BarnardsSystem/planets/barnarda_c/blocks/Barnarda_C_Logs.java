package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks;

import java.util.Random;

import galaxyspace.core.prefab.items.ItemAxeGS;
import galaxyspace.core.util.GSCreativeTabs;
import galaxyspace.systems.BarnardsSystem.core.BRBlocks;
import galaxyspace.systems.BarnardsSystem.core.BRItems;
import micdoodle8.mods.galacticraft.core.blocks.ISortableBlock;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Barnarda_C_Logs extends Block implements ISortableBlock {
	
	public static final PropertyEnum<EnumAxis> LOG_AXIS = PropertyEnum.<EnumAxis>create("axis", EnumAxis.class);

	public Barnarda_C_Logs(String name, int harvestlevel) {
		super(Material.WOOD);
		setUnlocalizedName(name);
		setHarvestLevel("axe", harvestlevel);
		setRegistryName(name);
		setCreativeTab(GSCreativeTabs.GSBlocksTab);
		setHardness(2.0F);
		setSoundType(SoundType.WOOD);
		setDefaultState(this.getStateFromMeta(0));
	}

	@SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        if(!this.getUnlocalizedName().equals("barnarda_c_test_glow_log"))
        {
            list.add(new ItemStack(this, 1, 0));
        }
    }
	
	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
		 if(!this.getUnlocalizedName().equals("barnarda_c_test_glow_log"))
			 return Item.getItemFromBlock(BRBlocks.BARNARDA_C_VIOLET_LOG.getDefaultState().getBlock());
		 
		 return super.getItemDropped(state, rand, fortune);
    }
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		if(!world.isRemote && world.getBlockState(pos) == BRBlocks.BARNARDA_C_VIOLET_GLOW_LOG.getStateFromMeta(0) && (player.getHeldItem(hand).getItem() instanceof ItemAxe || player.getHeldItem(hand).getItem() instanceof ItemAxeGS)) {
			world.setBlockState(pos, BRBlocks.BARNARDA_C_VIOLET_LOG.getStateFromMeta(0));
			world.spawnEntity(new EntityItem(world, player.posX, player.posY, player.posZ, new ItemStack(BRItems.BASIC, 1, 2)));
			return false;
		}
		return false;
	}
	 
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		int i = 4;
		int j = 5;

		if (worldIn.isAreaLoaded(pos.add(-5, -5, -5), pos.add(5, 5, 5))) {
			for (BlockPos blockpos : BlockPos.getAllInBox(pos.add(-4, -4, -4), pos.add(4, 4, 4))) {
				IBlockState iblockstate = worldIn.getBlockState(blockpos);

				if (iblockstate.getBlock().isLeaves(iblockstate, worldIn, blockpos)) {
					iblockstate.getBlock().beginLeavesDecay(iblockstate, worldIn, blockpos);
				}
			}
		}
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
    {
        EnumAxis enumfacing$axis = EnumAxis.Y;
        int i = meta & 12;

        if (i == 4)
        {
            enumfacing$axis = EnumAxis.X;
        }
        else if (i == 8)
        {
            enumfacing$axis = EnumAxis.Z;
        }
        else if (i == 12)
        {
        	enumfacing$axis = EnumAxis.NONE;
        }

        return this.getDefaultState().withProperty(LOG_AXIS, enumfacing$axis);
    }
	
	@Override
	public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        EnumAxis enumfacing$axis = (EnumAxis)state.getValue(LOG_AXIS);

        if (enumfacing$axis == EnumAxis.X)
        {
            i |= 4;
        }
        else if (enumfacing$axis == EnumAxis.Z)
        {
            i |= 8;
        }
        else if (enumfacing$axis == EnumAxis.NONE)
        {
            i |= 12;
        }

        return i;
    }
	
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(LOG_AXIS, EnumAxis.fromFacingAxis(facing.getAxis()));
    }
	
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		switch (rot) {
		case COUNTERCLOCKWISE_90:
		case CLOCKWISE_90:

			switch ((EnumAxis) state.getValue(LOG_AXIS)) {
			case X:
				return state.withProperty(LOG_AXIS, EnumAxis.X);
			case Z:
				return state.withProperty(LOG_AXIS, EnumAxis.Z);
			default:
				return state;
			}

		default:
			return state;
		}
	}
	
	@Override
	protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {LOG_AXIS});
    }
	
	@Override
	public boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
		return true;
	}

	@Override
	public boolean isWood(IBlockAccess world, BlockPos pos) {
		return true;
	}
	
	@Override
	public EnumSortCategoryBlock getCategory(int meta) {
		return EnumSortCategoryBlock.GENERAL;
	}

	@Override
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
	
	public static enum EnumAxis implements IStringSerializable
    {
        X("x"),
        Y("y"),
        Z("z"),
        NONE("none");

        private final String name;

        private EnumAxis(String name)
        {
            this.name = name;
        }

        public String toString()
        {
            return this.name;
        }

        public static EnumAxis fromFacingAxis(EnumFacing.Axis axis)
        {
            switch (axis)
            {
                case X:
                    return X;
                case Y:
                    return Y;
                case Z:
                    return Z;
                default:
                    return NONE;
            }
        }

        public String getName()
        {
            return this.name;
        }
    }
}
