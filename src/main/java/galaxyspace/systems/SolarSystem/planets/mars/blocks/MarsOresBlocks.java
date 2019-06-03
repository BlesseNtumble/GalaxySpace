package galaxyspace.systems.SolarSystem.planets.mars.blocks;

import java.util.Random;

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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MarsOresBlocks extends Block implements ISortableBlock{
	
	public static final PropertyEnum<EnumMarsOresBlocks> BASIC_TYPE = PropertyEnum.create("type", EnumMarsOresBlocks.class);

	public MarsOresBlocks()
    {
        super(Material.ROCK);
        this.setUnlocalizedName("marsores");
        this.setSoundType(SoundType.STONE); 
        this.setHarvestLevel("pickaxe", 2);
    }
	
	@SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (EnumMarsOresBlocks blockBasic : EnumMarsOresBlocks.values())
        {
            list.add(new ItemStack(this, 1, blockBasic.getMeta()));
        }
    }
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(state));
	}
	
	@Override
	public EnumSortCategoryBlock getCategory(int meta) {
		return EnumSortCategoryBlock.GENERAL;
	}
	
	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        switch (getMetaFromState(state))
        {
        	case 0:
        		return Items.DIAMOND;
        	case 2:
        		return Items.COAL;
        	case 3:
        		return Items.REDSTONE;
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
	        case 0:
	            return 0;
	        case 2:
	            return 0;
	        case 3:
	            return 0;
	        case 4:
	        	return 2;
	        default:
	            return getMetaFromState(state);
        }
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random)
    {
        int bonus = 0;

        if(this.getMetaFromState(state) == 3)
        {
        	bonus = 4;
        }
        
        if (this.getMetaFromState(state) == 4)
        {
            bonus = 2;
        }
        
        if (fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped(state, random, fortune))
        {
            int j = random.nextInt(fortune + 2) - 1;

            if (j < 0)
            {
                j = 0;
            }

            return this.quantityDropped(random) * (j + 1) + bonus;
        }
        else
        {
            return this.quantityDropped(random) + bonus;
        }
    }
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public enum EnumMarsOresBlocks implements IStringSerializable
	{
		MARS_DIAMOND(0, "mars_diamond"),
		MARS_GOLD(1, "mars_gold"),
		MARS_COAL(2, "mars_coal"),
		MARS_REDSTONE(3, "mars_redstone"),
		MARS_SILICON(4, "mars_silicon"),
		MARS_ALUMINUM(5, "mars_aluminum");

		private final int meta;
		private final String name;

		private EnumMarsOresBlocks(int meta, String name)
		{
			this.meta = meta;
			this.name = name;
		}

		public int getMeta() { return this.meta; }       

		private final static EnumMarsOresBlocks[] values = values();
		public static EnumMarsOresBlocks byMetadata(int meta) { return values[meta % values.length]; }

		@Override
		public String getName() { return this.name; }

	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(BASIC_TYPE, EnumMarsOresBlocks.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumMarsOresBlocks) state.getValue(BASIC_TYPE)).getMeta();
	}	

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, BASIC_TYPE);
	}

}
