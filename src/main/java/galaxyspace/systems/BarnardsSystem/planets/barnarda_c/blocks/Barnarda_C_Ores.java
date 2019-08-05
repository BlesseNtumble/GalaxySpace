package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks;

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

public class Barnarda_C_Ores extends Block implements ISortableBlock{
	public static final PropertyEnum<EnumBlockBarnardaCOres> BASIC_TYPE = PropertyEnum.create("type", EnumBlockBarnardaCOres.class);
	
	public Barnarda_C_Ores()
    {
        super(Material.ROCK);
        this.setUnlocalizedName("barnarda_c_ores");
        this.setSoundType(SoundType.STONE); 
        this.setHarvestLevel("pickaxe", 1,  this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBarnardaCOres.COAL));
        this.setHarvestLevel("pickaxe", 1,  this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBarnardaCOres.IRON));
        this.setHarvestLevel("pickaxe", 1,  this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBarnardaCOres.COPPER));
        this.setHarvestLevel("pickaxe", 1,  this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBarnardaCOres.TIN));
        this.setHarvestLevel("pickaxe", 1,  this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBarnardaCOres.LAPIS));
        this.setHarvestLevel("pickaxe", 2,  this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBarnardaCOres.GOLD));
        this.setHarvestLevel("pickaxe", 2,  this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBarnardaCOres.REDSTONE));
        this.setHarvestLevel("pickaxe", 2,  this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBarnardaCOres.DIAMOND));
        this.setHarvestLevel("pickaxe", 2,  this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBarnardaCOres.NICKEL));
        this.setHarvestLevel("pickaxe", 2,  this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBarnardaCOres.COBALT));
        this.setHarvestLevel("pickaxe", 2,  this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBarnardaCOres.QUARTZ));
        this.setHarvestLevel("pickaxe", 2,  this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBarnardaCOres.SILICON));
    }

	@Override
	public float getBlockHardness(IBlockState state, World world, BlockPos pos)
    {
		EnumBlockBarnardaCOres type = ((EnumBlockBarnardaCOres) state.getValue(BASIC_TYPE));
		switch (type)
        {
			
			default: return 1.0F;
        }
        
    }
	@SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (EnumBlockBarnardaCOres blockBasic : EnumBlockBarnardaCOres.values())
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
		EnumBlockBarnardaCOres type = ((EnumBlockBarnardaCOres) state.getValue(BASIC_TYPE));
        switch (type)
        {
        	case COAL:
        		return Items.COAL;
        	case REDSTONE:
        		return Items.REDSTONE;
        	case LAPIS:
        		return Items.DYE;
        	case DIAMOND:
        		return Items.DIAMOND;
        	case SILICON:
        		return GCItems.basicItem;
        	case QUARTZ:
        		return Items.QUARTZ;
        	default:
        		return Item.getItemFromBlock(this);
        }
    }
	
	@Override
	public int damageDropped(IBlockState state)
	{
		EnumBlockBarnardaCOres type = ((EnumBlockBarnardaCOres) state.getValue(BASIC_TYPE));
        switch (type)
        {
        	case COAL:
        	case REDSTONE:
        	case DIAMOND:
        	case QUARTZ:
        		return 0;
        	case LAPIS:
        		return 4;
        	case SILICON:
        		return 2;
        	default: return this.getMetaFromState(state);
        }
	}
	
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		int bonus = 0;
		int default_count = 0;

		EnumBlockBarnardaCOres type = state.getValue(BASIC_TYPE);
		
		if (type == EnumBlockBarnardaCOres.REDSTONE) {
			bonus = 2;
            default_count = 4;
		}

		if (type  == EnumBlockBarnardaCOres.SILICON) {
			bonus = 1;
		}
		
		if (type  == EnumBlockBarnardaCOres.LAPIS) {
			bonus = 2;
            default_count = 8;
		}

		if (fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped(state, random, fortune)) {
			int j = random.nextInt(fortune + 2) - 1;

			if (j < 0) {
				j = 0;
			}

			return this.quantityDropped(random) * (j + 1) + bonus;
		} else {
			return this.quantityDropped(random) + bonus;
		}
	}
	/*
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
            	EnumBlockBarnardaCOres type = ((EnumBlockBarnardaCOres) state.getValue(BASIC_TYPE));
            	
            	switch (type)
                {
                	default:
                		drops.add(new ItemStack(item, 1, this.damageDropped(state)));
                		break;
                }
               
            }
        }
    }
	*/
	@Override
	public EnumSortCategoryBlock getCategory(int meta) {
		return EnumSortCategoryBlock.GENERAL;
	}
	
	public enum EnumBlockBarnardaCOres implements IStringSerializable
	{
		COAL(0, "coal_ore"),
		IRON(1, "iron_ore"),
		GOLD(2, "gold_ore"),
		REDSTONE(3, "redstone_ore"),
		LAPIS(4, "lapis_ore"),
		DIAMOND(5, "diamond_ore"),
		SILICON(6, "silicon_ore"),
		COPPER(7, "copper_ore"),
		TIN(8, "tin_ore"),
		ALUMINUM(9, "aluminum_ore"),
		QUARTZ(10, "quartz_ore"),
		COBALT(11, "cobaltum_ore"),
		NICKEL(12, "nickel_ore");
	
		private final int meta;
		private final String name;
	
		private EnumBlockBarnardaCOres(int meta, String name)
		{
			this.meta = meta;
			this.name = name;
		}
	
		public int getMeta() { return this.meta; }       
	
		private final static EnumBlockBarnardaCOres[] values = values();
		public static EnumBlockBarnardaCOres byMetadata(int meta) { return values[meta % values.length]; }
	
		@Override
		public String getName() { return this.name; }
	
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBarnardaCOres.byMetadata(meta));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumBlockBarnardaCOres) state.getValue(BASIC_TYPE)).getMeta();
	}	
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, BASIC_TYPE);
	}
}
