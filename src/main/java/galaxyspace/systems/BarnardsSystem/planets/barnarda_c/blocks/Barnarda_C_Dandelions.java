package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks;

import java.util.List;
import java.util.Random;

import galaxyspace.systems.BarnardsSystem.core.registers.blocks.BRBlocks;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Blocks.EnumBlockBarnardaC;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Barnarda_C_Dandelions extends BlockBush implements IGrowable, IShearable{

	public static final PropertyEnum<EnumBlockDandelions> BASIC_TYPE = PropertyEnum.create("type", EnumBlockDandelions.class);
	
	protected static final AxisAlignedBB TALL_GRASS_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);

	public Barnarda_C_Dandelions()
	{
		super(Material.VINE);
		this.setUnlocalizedName("barnarda_c_dandelions");
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return TALL_GRASS_AABB;
    }
	
	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {		
		EnumBlockDandelions type = ((EnumBlockDandelions) state.getValue(BASIC_TYPE));
		
		switch (type)
        {			
			default:
				return Material.GRASS.getMaterialMapColor();
		
        }
    }
	
	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos)
    {
		EnumBlockDandelions type = ((EnumBlockDandelions) state.getValue(BASIC_TYPE));
		
		switch(type)
		{
			case LEAVES_BALLS: 
				return 8;
			case LIGHT_BALLS: 
				return 8;
			default:
				return state.getLightValue();
		}
    }
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(state));
	}
	
	@SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (EnumBlockDandelions blockBasic : EnumBlockDandelions.values())
        {
        	if(!blockBasic.equals(blockBasic.DESERT_UP))
        		list.add(new ItemStack(this, 1, blockBasic.getMeta()));
        }
    }
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
		if(state == this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.DESERT_DOWN))
		{
			if(world.isAirBlock(pos.up()))
				world.setBlockState(pos.up(), this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.DESERT_UP));
		}
		
		
    }
	
	@Override
	public boolean canBlockStay(World world, BlockPos pos, IBlockState state)
    {
		if(state == this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.DESERT_UP))
		{
			return world.getBlockState(pos.down()) == this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.DESERT_DOWN);
		}
		
		if(state == this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.DESERT_DOWN))
		{			
			return world.getBlockState(pos.down()) == BRBlocks.BARNARDA_C_BLOCKS.getDefaultState().withProperty(Barnarda_C_Blocks.BASIC_TYPE, Barnarda_C_Blocks.EnumBlockBarnardaC.SAND);				
		}
		
		if(state == this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.LEAVES_BALLS))
		{
			return world.getBlockState(pos.up()) == BRBlocks.BARNARDA_C_LEAVES.getStateFromMeta(0);
		}
		
		return super.canBlockStay(world, pos, state);
    }
	
	@Override
	protected boolean canSustainBush(IBlockState state)
    {
        return state == BRBlocks.BARNARDA_C_LEAVES.getStateFromMeta(0) || state.getBlock() == BRBlocks.BARNARDA_C_GRASS || state == BRBlocks.BARNARDA_C_BLOCKS.getDefaultState().withProperty(Barnarda_C_Blocks.BASIC_TYPE, EnumBlockBarnardaC.DIRT) || state == BRBlocks.BARNARDA_C_BLOCKS.getDefaultState().withProperty(Barnarda_C_Blocks.BASIC_TYPE, EnumBlockBarnardaC.DIRT_1);
    }
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {		
		IBlockState up = worldIn.getBlockState(pos.up());
        IBlockState down = worldIn.getBlockState(pos.down());

        return true;
    }
	
	@Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
		if(state == this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.DESERT_UP))
		{
			world.destroyBlock(pos.down(), true);
		}
		
		super.breakBlock(world, pos, state);
    }

	@Override
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos)
    {
        return true;
    }
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) 
	{
		return null;
	}
	
	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {

		return false;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return false;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		
	}

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		return null;
	}

	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	public static enum EnumBlockDandelions implements IStringSerializable
    {
		GRASS(0, "barnarda_c_tall_grass"),
		HOPPER(1, "barnarda_c_hopper_flower"),
		GREEN_FERN(2, "barnarda_c_green_fern"),
		RED_FERN(3, "barnarda_c_red_fern"),
		LIGHT_BALLS(4, "barnarda_c_light_balls"),
		LEAVES_BALLS(5, "barnarda_c_leaves_balls"),
		DESERT_DOWN(6, "barnarda_c_desert_down"),
		DESERT_UP(7, "barnarda_c_desert_up");
		
		private final int meta;
		private final String name;

		private EnumBlockDandelions(int meta, String name) {
			this.meta = meta;
			this.name = name;
		}
		
		public int getMeta() { return this.meta; }

		public static EnumBlockDandelions byMetadata(int meta) { return values()[meta]; }
		
		@Override
		public String getName() { return this.name; }

    }
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.byMetadata(meta));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumBlockDandelions) state.getValue(BASIC_TYPE)).getMeta();
	}	
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, BASIC_TYPE);
	}
}
