package galaxyspace.systems.SolarSystem.moons.miranda.blocks;

import java.util.Random;

import javax.annotation.Nullable;

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

public class MirandaBlocks extends Block implements ISortableBlock {

	public static final PropertyEnum<EnumMirandaBlocks> BASIC_TYPE = PropertyEnum.create("type", EnumMirandaBlocks.class);

	public MirandaBlocks()
    {
        super(Material.ROCK);
        this.setTranslationKey("mirandablocks");
        this.setSoundType(SoundType.STONE); 
        this.setHarvestLevel("pickaxe", 2);
    }
	
	@SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (EnumMirandaBlocks blockBasic : EnumMirandaBlocks.values())
        {
            list.add(new ItemStack(this, 1, blockBasic.getMeta()));
        }
    }
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(state));
	}
	
	public float getSlipperiness(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable Entity entity)
    {
		if(state == this.getDefaultState().withProperty(BASIC_TYPE, EnumMirandaBlocks.MIRANDA_ICE))
			return 0.98F;
		
		return slipperiness;		
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
	
	
	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
		EnumMirandaBlocks type = ((EnumMirandaBlocks) state.getValue(BASIC_TYPE));
        switch (type)
        {
        	case MIRANDA_ICE:
        		return Items.AIR;
        	case MIRANDA_DOLOMITE_ORE:
        		return GSItems.BASIC;
        	case MIRANDA_DIAMOND_ORE:
        		return Items.DIAMOND;
        	case MIRANDA_QUARTZ_ORE:
        		return Items.QUARTZ;
        	default:
        		return Item.getItemFromBlock(this);
        }
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
            	EnumMirandaBlocks type = ((EnumMirandaBlocks) state.getValue(BASIC_TYPE));
            	
            	switch (type)
                {
        			case MIRANDA_DOLOMITE_ORE: 
        				drops.add(new ItemStack(item, 1, 3));
        				break;
        			case MIRANDA_DIAMOND_ORE: 
        				drops.add(new ItemStack(item, 1, 0));
        				break;
        			case MIRANDA_QUARTZ_ORE: 
        				drops.add(new ItemStack(item, 1, 0));
        				break;
                	default:
                		drops.add(new ItemStack(item, 1, this.damageDropped(state)));
                		break;
                }
               
            }
        }
    }
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public enum EnumMirandaBlocks implements IStringSerializable {
		MIRANDA_GRUNT(0, "miranda_grunt"), 
		MIRANDA_SUBGRUNT(1, "miranda_subgrunt"), 
		MIRANDA_STONE(2, "miranda_stone"),	
		
		MIRANDA_IRON_ORE(3, "miranda_iron_ore"),
		MIRANDA_DOLOMITE_ORE(4, "miranda_dolomite_ore"),		
		MIRANDA_DIAMOND_ORE(5, "miranda_diamond_ore"),
		MIRANDA_QUARTZ_ORE(6, "miranda_quartz_ore"),
		MIRANDA_COBALT_ORE(7, "miranda_cobalt_ore"),
		MIRANDA_NICKEL_ORE(8, "miranda_nickel_ore"),
		
		MIRANDA_GRUNT_2(9, "miranda_grunt_2"),
		MIRANDA_GRUNT_3(10, "miranda_grunt_3"),
		MIRANDA_SUBGRUNT_2(11, "miranda_subgrunt_2"), 
		MIRANDA_SUBGRUNT_3(12, "miranda_subgrunt_3"), 
		MIRANDA_STONE_2(13, "miranda_stone_2"), 
		MIRANDA_STONE_3(14, "miranda_stone_3"),
		MIRANDA_ICE(15, "miranda_ice");
		
		private final int meta;
		private final String name;

		private EnumMirandaBlocks(int meta, String name) {
			this.meta = meta;
			this.name = name;
		}

		public int getMeta() {
			return this.meta;
		}

		private final static EnumMirandaBlocks[] values = values();
		public static EnumMirandaBlocks byMetadata(int meta) { return values[meta % values.length]; }

		@Override
		public String getName() {
			return this.name;
		}

	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(BASIC_TYPE, EnumMirandaBlocks.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumMirandaBlocks) state.getValue(BASIC_TYPE)).getMeta();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, BASIC_TYPE);
	}

}
