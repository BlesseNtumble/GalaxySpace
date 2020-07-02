package galaxyspace.systems.SolarSystem.planets.overworld.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import galaxyspace.core.GSItems;
import micdoodle8.mods.galacticraft.api.block.ITerraformableBlock;
import micdoodle8.mods.galacticraft.core.blocks.ISortableBlock;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
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
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSurfaceIce extends BlockBreakable implements ITerraformableBlock, ISortableBlock{
	public static final PropertyEnum<EnumBlockIce> BASIC_TYPE = PropertyEnum.create("type", EnumBlockIce.class);
	
	public BlockSurfaceIce() {
		super(Material.ICE, false);
		this.setUnlocalizedName("surface_ice");
		this.setHardness(1.0F);
        this.setSoundType(SoundType.GLASS);
	}

	@Override
	public boolean isTerraformable(World world, BlockPos pos) {
		return false;
	}
	
	@SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (EnumBlockIce blockBasic : EnumBlockIce.values())
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
		EnumBlockIce type = ((EnumBlockIce) state.getValue(BASIC_TYPE));
        switch (type)
        {        	
        	default:
        		return GSItems.BASIC;//Item.getItemFromBlock(this);
        }
    }
	
	@Override
	public int damageDropped(IBlockState state)
	{
		EnumBlockIce type = ((EnumBlockIce) state.getValue(BASIC_TYPE));
        switch (type)
        {                	
        	default:
        		return 23 + this.getMetaFromState(state);        
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
            	EnumBlockIce type = ((EnumBlockIce) state.getValue(BASIC_TYPE));
            	
            	switch (type)
                {
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

	@Override
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

	@Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
	
	@Override
	public float getSlipperiness(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable Entity entity) {
		return 0.8F;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public enum EnumBlockIce implements IStringSerializable {
		OXYGEN_ICE(0, "oxygen_ice"),
		NITROGEN_ICE(1, "nitrogen_ice"),
		METHANE_ICE(2, "methane_ice"),
		HYDROGEN_ICE(3, "hydrogen_ice"),
		DRY_ICE(4, "dry_ice");

		private final int meta;
		private final String name;

		private EnumBlockIce(int meta, String name) {
			this.meta = meta;
			this.name = name;
		}

		public int getMeta() {
			return this.meta;
		}

		private final static EnumBlockIce[] values = values();

		public static EnumBlockIce byMetadata(int meta) {
			return values[meta % values.length];
		}

		@Override
		public String getName() {
			return this.name;
		}

	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockIce.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumBlockIce) state.getValue(BASIC_TYPE)).getMeta();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, BASIC_TYPE);
	}
}
