package galaxyspace.systems.SolarSystem.planets.overworld.blocks;

import micdoodle8.mods.galacticraft.core.blocks.ISortableBlock;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockDecoMetals extends Block implements ISortableBlock{
	public static final PropertyEnum<EnumBlockDecoMetals> BASIC_TYPE = PropertyEnum.create("type", EnumBlockDecoMetals.class);

	public BlockDecoMetals()
    {
        super(Material.ROCK);
        this.setTranslationKey("decoblocks");
        this.setSoundType(SoundType.STONE); 
        this.setHarvestLevel("pickaxe", 2);
    }
	
	@SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (EnumBlockDecoMetals blockBasic : EnumBlockDecoMetals.values())
        {
            list.add(new ItemStack(this, 1, blockBasic.getMeta()));
        }
    }
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(state));
	}
	
	@Override
    public int damageDropped(IBlockState state)
    {
		EnumBlockDecoMetals type = ((EnumBlockDecoMetals) state.getValue(BASIC_TYPE));
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
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public enum EnumBlockDecoMetals implements IStringSerializable
	{
		DECO_COBALT_1(0, "deco_cobaltum_1"),
		DECO_MAGNESIUM_1(1, "deco_magnesium_1"),
		DECO_NICKEL_1(2, "deco_nickel_1"),
		DECO_COOPER_1(3, "deco_copper_1"),
		DECO_COBALT_2(4, "deco_cobaltum_2"),
		DECO_MAGNESIUM_2(5, "deco_magnesium_2"),
		DECO_NICKEL_2(6, "deco_nickel_2"),
		DECO_COOPER_2(7, "deco_copper_2"),
		COBALT_BLOCK(8, "cobalt_block"),
		NICKEL_BLOCK(9, "nickel_block"),
		MAGNESIUM_BLOCK(10, "magnesium_block");

		private final int meta;
		private final String name;

		private EnumBlockDecoMetals(int meta, String name)
		{
			this.meta = meta;
			this.name = name;
		}

		public int getMeta() { return this.meta; }       

		private final static EnumBlockDecoMetals[] values = values();
		public static EnumBlockDecoMetals byMetadata(int meta) { return values[meta % values.length]; }

		@Override
		public String getName() { return this.name; }

	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDecoMetals.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumBlockDecoMetals) state.getValue(BASIC_TYPE)).getMeta();
	}	

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, BASIC_TYPE);
	}
}

