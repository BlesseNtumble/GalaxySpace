package galaxyspace.systems.SolarSystem.planets.overworld.blocks;

import java.util.Random;

import galaxyspace.core.GSItems;
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
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockOres extends Block implements ISortableBlock{
	public static final PropertyEnum<EnumBlockOres> BASIC_TYPE = PropertyEnum.create("type", EnumBlockOres.class);
	
	public BlockOres()
    {
        super(Material.ROCK);
        this.setUnlocalizedName("gsores");
        this.setSoundType(SoundType.STONE); 
        this.setHarvestLevel("pickaxe", 2);
    }

	@SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (EnumBlockOres blockBasic : EnumBlockOres.values())
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
		EnumBlockOres type = ((EnumBlockOres) state.getValue(BASIC_TYPE));
        switch (type)
        {
        	case URANIUM:
        		return GSItems.BASIC;
        	default:
        		return Item.getItemFromBlock(this);
        }
    }
	
	@Override
	public int damageDropped(IBlockState state)
	{
		return this.getMetaFromState(state);
	}
	
	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        Random rand = world instanceof World ? ((World)world).rand : RANDOM;

        int count = quantityDropped(state, fortune, rand);
        for (int i = 0; i < count; i++)
        {
            Item item = this.getItemDropped(state, rand, fortune);
            if (item != Items.AIR)
            {
            	EnumBlockOres type = ((EnumBlockOres) state.getValue(BASIC_TYPE));
            	
            	switch (type)
                {
        			case URANIUM: 
        				drops.add(new ItemStack(item, 1, 15));
        				break;
                	default:
                		drops.add(new ItemStack(item, 1, this.damageDropped(state)));
                		break;
                }
               
            }
        }
    }
	
	@Override
	public EnumSortCategoryBlock getCategory(int meta) {
		return EnumSortCategoryBlock.GENERAL;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public enum EnumBlockOres implements IStringSerializable
	{
		COBALT(0, "cobaltum_ore"),
		NICKEL(1, "nickel_ore"),
		URANIUM(2, "uranium_ore");
	
		private final int meta;
		private final String name;
	
		private EnumBlockOres(int meta, String name)
		{
			this.meta = meta;
			this.name = name;
		}
	
		public int getMeta() { return this.meta; }       
	
		private final static EnumBlockOres[] values = values();
		public static EnumBlockOres byMetadata(int meta) { return values[meta % values.length]; }
	
		@Override
		public String getName() { return this.name; }
	
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockOres.byMetadata(meta));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumBlockOres) state.getValue(BASIC_TYPE)).getMeta();
	}	
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, BASIC_TYPE);
	}
}

