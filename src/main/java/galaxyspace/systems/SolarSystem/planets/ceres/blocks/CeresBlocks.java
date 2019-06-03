package galaxyspace.systems.SolarSystem.planets.ceres.blocks;

import java.util.Random;

import galaxyspace.core.registers.items.GSItems;
import micdoodle8.mods.galacticraft.api.block.ITerraformableBlock;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CeresBlocks extends Block implements ISortableBlock, ITerraformableBlock{

	public static final PropertyEnum<EnumCeresBlocks> BASIC_TYPE = PropertyEnum.create("type", EnumCeresBlocks.class);

	public CeresBlocks()
    {
        super(Material.ROCK);
        this.setUnlocalizedName("ceresblocks");
        this.setSoundType(SoundType.STONE); 
        this.setHarvestLevel("pickaxe", 2);
    }
	
	@SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (EnumCeresBlocks blockBasic : EnumCeresBlocks.values())
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
        	case 2:
        		return GSItems.BASIC;
        	case 3:
        		return GCItems.meteoricIronRaw;
        	default:
        		return Item.getItemFromBlock(this);
        }
    }
	
	@Override
    public int damageDropped(IBlockState state)
    {
        switch (getMetaFromState(state))
        {
	        case 2:
	            return 3;
	        case 3:
	            return 0;
	        default:
	            return getMetaFromState(state);
        }
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random)
    {
        int bonus = 0;

        if (this.getMetaFromState(state) == 3)
        {
            bonus = 1;
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
	
	public enum EnumCeresBlocks implements IStringSerializable
	{
		CERES_GRUNT(0, "ceres_grunt"),
		CERES_SUBGRUNT(1, "ceres_subgrunt"),
		CERES_DOLOMITE_ORE(2, "ceres_dolomite_ore"),
		CERES_METEORICIRON_ORE(3, "ceres_meteoriciron_ore"),
		CERES_DUNGEON_TOP(4, "ceres_dungeon_top"),
		CERES_DUNGEON_FLOOR(5, "ceres_dungeon_floor");

		private final int meta;
		private final String name;

		private EnumCeresBlocks(int meta, String name)
		{
			this.meta = meta;
			this.name = name;
		}

		public int getMeta() { return this.meta; }       

		private final static EnumCeresBlocks[] values = values();
		public static EnumCeresBlocks byMetadata(int meta) { return values[meta % values.length]; }

		@Override
		public String getName() { return this.name; }

	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(BASIC_TYPE, EnumCeresBlocks.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumCeresBlocks) state.getValue(BASIC_TYPE)).getMeta();
	}	

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, BASIC_TYPE);
	}

	@Override
	public boolean isTerraformable(World world, BlockPos pos) {
		
		if(world.getBlockState(pos) == this.getDefaultState().withProperty(BASIC_TYPE, EnumCeresBlocks.CERES_GRUNT))
			return true;
		
		return false;
	}

}
