package galaxyspace.core.prefab.blocks;

import java.util.Random;

import micdoodle8.mods.galacticraft.core.blocks.ISortableBlock;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
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

public class DungeonBlocks extends Block implements ISortableBlock{

	public static final PropertyEnum<EnumDungeonBlocks> BASIC_TYPE = PropertyEnum.create("type", EnumDungeonBlocks.class);

	public DungeonBlocks()
    {
        super(Material.ROCK);
        this.setTranslationKey("dungeon_blocks");
        this.setSoundType(SoundType.STONE); 
        this.setHarvestLevel("pickaxe", 3);
    }
	
	@SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (EnumDungeonBlocks blockBasic : EnumDungeonBlocks.values())
        {
            list.add(new ItemStack(this, 1, blockBasic.getMeta()));
        }         
    }
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(state));
	}
	
	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
		EnumDungeonBlocks type = ((EnumDungeonBlocks) state.getValue(BASIC_TYPE));
		switch (type)
        {        	
        	default:
        		return Item.getItemFromBlock(this);
        }
    }
	
	@Override
    public int damageDropped(IBlockState state)
    {
		EnumDungeonBlocks type = ((EnumDungeonBlocks) state.getValue(BASIC_TYPE));
		switch (type)
        {	        
	        default:
	        	return getMetaFromState(state);
        }
    }
	
	@Override
	public EnumSortCategoryBlock getCategory(int meta) {
		return EnumSortCategoryBlock.GENERAL;
	}
	
	@Override
	public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		return true;
	}
	
	@Override
	public boolean isBlockNormalCube(IBlockState state)
	{
		return true;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return true;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullBlock(IBlockState state)
	{
		return false;
	}
	 
	@Override
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.SOLID;
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public enum EnumDungeonBlocks implements IStringSerializable
	{
		CERES_BRICKS(0, "ceres_bricks"),
		IO_BRICKS(1, "io_bricks");

		private final int meta;
		private final String name;

		private EnumDungeonBlocks(int meta, String name)
		{
			this.meta = meta;
			this.name = name;
		}
		
		public int getMeta() { return this.meta; }       

		private final static EnumDungeonBlocks[] values = values();
		public static EnumDungeonBlocks byMetadata(int meta) { return values[meta % values.length]; }

		@Override
		public String getName() { return this.name; }

	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(BASIC_TYPE, EnumDungeonBlocks.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumDungeonBlocks) state.getValue(BASIC_TYPE)).getMeta();
	}	

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, BASIC_TYPE);
	}

}
