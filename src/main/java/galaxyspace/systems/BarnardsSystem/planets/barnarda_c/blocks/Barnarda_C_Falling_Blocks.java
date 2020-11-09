package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks;

import micdoodle8.mods.galacticraft.core.blocks.ISortableBlock;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryBlock;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Barnarda_C_Falling_Blocks extends BlockFalling implements ISortableBlock{

	public static final PropertyEnum<EnumFallingBlockBarnardaC> BASIC_TYPE = PropertyEnum.create("type", EnumFallingBlockBarnardaC.class);
		
	public Barnarda_C_Falling_Blocks()
    {
		super();
		this.setUnlocalizedName("barnarda_c_falling_blocks");
		this.setSoundType(SoundType.SAND);
		this.setHardness(0.6F);
    }
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(state));
	}
	
	@SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (EnumFallingBlockBarnardaC blockBasic : EnumFallingBlockBarnardaC.values())
        {
            list.add(new ItemStack(this, 1, blockBasic.getMeta()));
        }
    }
	
	@Override
	public int damageDropped(IBlockState state)
	{
		return this.getMetaFromState(state);
	}
	
	@Override
	public EnumSortCategoryBlock getCategory(int meta) {
		return EnumSortCategoryBlock.GENERAL;
	}
	
	@Override
	public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, net.minecraftforge.common.IPlantable plantable)	
    {	
		EnumFallingBlockBarnardaC type = ((EnumFallingBlockBarnardaC) state.getValue(BASIC_TYPE));	
		EnumPlantType plantType = plantable.getPlantType(world, pos.offset(direction));

		switch (type)	
        {		
			case SAND:
				if(plantType == EnumPlantType.Desert) 
					return true;
        	default:	
        		return false;	
        }	
    }
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public enum EnumFallingBlockBarnardaC implements IStringSerializable {
		 SAND(0,"barnarda_c_sand", Material.SAND);

		private final int meta;
		private final String name;
		private final Material material;

		private EnumFallingBlockBarnardaC(int meta, String name, Material material) {
			this.meta = meta;
			this.name = name;
			this.material = material;
		}

		public int getMeta() {
			return this.meta;
		}

		public Material getMaterial() {
			return this.material;
		}

		public static EnumFallingBlockBarnardaC byMetadata(int meta) {
			return values()[meta];
		}

		@Override
		public String getName() {
			return this.name;
		}

	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(BASIC_TYPE, EnumFallingBlockBarnardaC.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFallingBlockBarnardaC) state.getValue(BASIC_TYPE)).getMeta();
	}

	@Override
	public Material getMaterial(IBlockState state) {
		return ((EnumFallingBlockBarnardaC) state.getValue(BASIC_TYPE)).getMaterial();
	}

	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return ((EnumFallingBlockBarnardaC) state.getValue(BASIC_TYPE)).getMaterial().getMaterialMapColor();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, BASIC_TYPE);
	}
}
