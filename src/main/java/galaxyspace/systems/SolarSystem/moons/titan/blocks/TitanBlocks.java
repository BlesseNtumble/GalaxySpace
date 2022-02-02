package galaxyspace.systems.SolarSystem.moons.titan.blocks;

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

public class TitanBlocks extends Block implements ISortableBlock{

	public static final PropertyEnum<EnumTitanBlocks> BASIC_TYPE = PropertyEnum.create("type", EnumTitanBlocks.class);

	public TitanBlocks()
    {
        super(Material.ROCK);
        this.setTranslationKey("titanblocks");
        this.setSoundType(SoundType.STONE); 
        this.setHarvestLevel("pickaxe", 2);
    }
	
	@SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (EnumTitanBlocks blockBasic : EnumTitanBlocks.values())
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
		EnumTitanBlocks type = state.getValue(BASIC_TYPE);
        switch (type)
        {
        	case TITAN_SAPPHIRE_ORE:
        		return GCItems.itemBasicMoon;
        	case TITAN_EMERALD_ORE:
        		return Items.EMERALD;
        	case TITAN_DIAMOND_ORE:
        		return Items.DIAMOND;
        	case TITAN_COAL_ORE:
        		return Items.COAL;
        	case TITAN_LAPIS_ORE:
        		return Items.DYE;
        	case TITAN_REDSTONE_ORE:
        		return Items.REDSTONE;
        	default:
        		return Item.getItemFromBlock(this);
        }
    }

    @Override
    public int damageDropped(IBlockState state)
    {
    	EnumTitanBlocks type = state.getValue(BASIC_TYPE);
        switch (type)
        {
	        case TITAN_SAPPHIRE_ORE:
	            return 2;
	        case TITAN_EMERALD_ORE:
	        case TITAN_DIAMOND_ORE:
	        case TITAN_COAL_ORE:
	        case TITAN_REDSTONE_ORE:
	            return 0;
	        case TITAN_LAPIS_ORE:
	        	return 4;
	        default:
	            return getMetaFromState(state);
        }
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random)
    {
        int bonus = 0;
        int default_count = 0;

        EnumTitanBlocks type = state.getValue(BASIC_TYPE);
        
        if (type == EnumTitanBlocks.TITAN_SAPPHIRE_ORE)
        {
            bonus = 2;
        }
       
        if (type == EnumTitanBlocks.TITAN_LAPIS_ORE)
        {
            bonus = 2;
            default_count = 8;
        }
        
        if (type == EnumTitanBlocks.TITAN_REDSTONE_ORE)
        {
            bonus = 2;
            default_count = 4;
        }

        
        if (fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped(state, random, fortune))
        {
            int j = random.nextInt(fortune + 2) - 1;

            if (j < 0)
            {
                j = 0;
            }

            return (this.quantityDropped(random) + default_count) * (j + 1) + bonus;
        }
        else
        {
            return this.quantityDropped(random) + default_count + random.nextInt(1 + bonus);
        }
    }
	
	@Override
	public EnumSortCategoryBlock getCategory(int meta) {
		return EnumSortCategoryBlock.GENERAL;
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public enum EnumTitanBlocks implements IStringSerializable
	{
		TITAN_GRUNT(0, "titan_grunt"),
		TITAN_SUBGRUNT(1, "titan_subgrunt"),
		TITAN_STONE(2, "titan_stone"),
		TITAN_SAPPHIRE_ORE(3, "titan_sapphire_ore"),
		TITAN_EMERALD_ORE(4, "titan_emerald_ore"),
		TITAN_DIAMOND_ORE(5, "titan_diamond_ore"),
		TITAN_COAL_ORE(6, "titan_coal_ore"),
		TITAN_LAPIS_ORE(7, "titan_lapis_ore"),
		TITAN_REDSTONE_ORE(8, "titan_redstone_ore");

		private final int meta;
		private final String name;

		private EnumTitanBlocks(int meta, String name)
		{
			this.meta = meta;
			this.name = name;
		}

		public int getMeta() { return this.meta; }       

		private final static EnumTitanBlocks[] values = values();
		public static EnumTitanBlocks byMetadata(int meta) { return values[meta % values.length]; }

		@Override
		public String getName() { return this.name; }

	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(BASIC_TYPE, EnumTitanBlocks.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumTitanBlocks) state.getValue(BASIC_TYPE)).getMeta();
	}	

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, BASIC_TYPE);
	}

}

