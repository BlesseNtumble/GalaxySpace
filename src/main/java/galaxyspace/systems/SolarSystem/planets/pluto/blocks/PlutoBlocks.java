package galaxyspace.systems.SolarSystem.planets.pluto.blocks;

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

public class PlutoBlocks extends Block implements ISortableBlock{

	public static final PropertyEnum<EnumPlutoBlocks> BASIC_TYPE = PropertyEnum.create("type", EnumPlutoBlocks.class);

	public PlutoBlocks()
    {
        super(Material.ROCK);
        this.setTranslationKey("plutoblocks");
        this.setSoundType(SoundType.STONE); 
        this.setHarvestLevel("pickaxe", 2);
    }
	
	@SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (EnumPlutoBlocks blockBasic : EnumPlutoBlocks.values())
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
		return getMetaFromState(state);        
    }
	
	@Override
	public EnumSortCategoryBlock getCategory(int meta) {
		return EnumSortCategoryBlock.GENERAL;
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public enum EnumPlutoBlocks implements IStringSerializable
	{
		PLUTO_GRUNT_1(0, "pluto_grunt_1"),
		PLUTO_GRUNT_2(1, "pluto_grunt_2"),
		PLUTO_GRUNT_3(2, "pluto_grunt_3"),
		PLUTO_GRUNT_4(3, "pluto_grunt_4"),
		PLUTO_SUBGRUNT(4, "pluto_subgrunt"),
		PLUTO_STONE(5, "pluto_stone");


		private final int meta;
		private final String name;

		private EnumPlutoBlocks(int meta, String name)
		{
			this.meta = meta;
			this.name = name;
		}

		public int getMeta() { return this.meta; }       

		public static EnumPlutoBlocks byMetadata(int meta) { return values()[meta]; }

		@Override
		public String getName() { return this.name; }

	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(BASIC_TYPE, EnumPlutoBlocks.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumPlutoBlocks) state.getValue(BASIC_TYPE)).getMeta();
	}	

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, BASIC_TYPE);
	}

}
