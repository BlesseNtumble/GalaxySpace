package galaxyspace.systems.SolarSystem.moons.europa.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.blocks.ISortableBlock;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EuropaBlocks extends Block implements ISortableBlock{

	public static final PropertyEnum<EnumEuropaBlocks> BASIC_TYPE = PropertyEnum.create("type", EnumEuropaBlocks.class);

	public EuropaBlocks()
    {
        super(Material.ROCK);
        this.setTranslationKey("europablocks");
        this.setSoundType(SoundType.STONE); 
        this.setHarvestLevel("pickaxe", 2);
        this.setHardness(0.4F);
    }

	@SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (EnumEuropaBlocks blockBasic : EnumEuropaBlocks.values())
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
        switch (getMetaFromState(state))
        {
        	case 3:
        		return Items.EMERALD;
        	case 4:
        		return GCItems.basicItem;
        	default:
        		return Item.getItemFromBlock(this);
        }
    }
	
	@Override
    public int damageDropped(IBlockState state)
    {
        switch (getMetaFromState(state))
        {	        
	        case 3:
	            return 0;
	        case 4:
	        	return 2;
	        default:
	            return getMetaFromState(state);
        }
    }
	
	@Override
	public EnumSortCategoryBlock getCategory(int meta) {
		return EnumSortCategoryBlock.GENERAL;
	}
	
	@Override
	public float getSlipperiness(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable Entity entity) {
		if(state.getValue(BASIC_TYPE) == EnumEuropaBlocks.EUROPA_BROWN_ICE) 
			return 0.8F;
		
		return super.getSlipperiness(state, world, pos, entity);
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public enum EnumEuropaBlocks implements IStringSerializable
	{
		EUROPA_GRUNT(0, "europa_grunt"),
		EUROPA_STONE(1, "europa_stone"),
		EUROPA_BROWN_ICE(2, "europa_brown_ice"),
		EUROPA_EMERALD_ORE(3, "europa_emerald_ore"),
		EUROPA_SILICON_ORE(4, "europa_silicon_ore"),
		EUROPA_ALUMINUM_ORE(5, "europa_aluminum_ore"),
		EUROPA_SURFACE_ICE2(6, "europa_surface_ice_2");


		private final int meta;
		private final String name;

		private EnumEuropaBlocks(int meta, String name)
		{
			this.meta = meta;
			this.name = name;
		}

		public int getMeta() { return this.meta; }       

		private final static EnumEuropaBlocks[] values = values();
		public static EnumEuropaBlocks byMetadata(int meta) { return values[meta % values.length]; }

		@Override
		public String getName() { return this.name; }

	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(BASIC_TYPE, EnumEuropaBlocks.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumEuropaBlocks) state.getValue(BASIC_TYPE)).getMeta();
	}	

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, BASIC_TYPE);
	}

}
