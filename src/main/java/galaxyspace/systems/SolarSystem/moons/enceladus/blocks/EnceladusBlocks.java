package galaxyspace.systems.SolarSystem.moons.enceladus.blocks;

import java.util.Random;

import micdoodle8.mods.galacticraft.api.block.IDetectableResource;
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

public class EnceladusBlocks extends Block implements IDetectableResource, ISortableBlock{

	public static final PropertyEnum<EnumEnceladusBlocks> BASIC_TYPE = PropertyEnum.create("type", EnumEnceladusBlocks.class);

	public EnceladusBlocks()
    {
        super(Material.ROCK);
        this.setUnlocalizedName("enceladusblocks");
        this.setSoundType(SoundType.STONE); 
        this.setHarvestLevel("shovel", 1, this.getDefaultState().withProperty(BASIC_TYPE, EnumEnceladusBlocks.ENCELADUS_SNOW));
        this.setHarvestLevel("pickaxe", 2, this.getDefaultState().withProperty(BASIC_TYPE, EnumEnceladusBlocks.ENCELADUS_GRUNT));
        this.setHarvestLevel("pickaxe", 2, this.getDefaultState().withProperty(BASIC_TYPE, EnumEnceladusBlocks.ENCELADUS_COAL_ORE));
    }

	@Override
	public float getBlockHardness(IBlockState state, World worldIn, BlockPos pos)
    {

		EnumEnceladusBlocks type = ((EnumEnceladusBlocks) state.getValue(BASIC_TYPE));
		
		switch (type)
        {
			case ENCELADUS_SNOW: return 0.2F;
			default: return this.blockHardness;        
        }
    }
	
	@SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (EnumEnceladusBlocks blockBasic : EnumEnceladusBlocks.values())
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
		return this.getMetaFromState(state);
	}
	
	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state,	int fortune) {
		Random rand = world instanceof World ? ((World) world).rand : RANDOM;

		int count = quantityDropped(state, fortune, rand);
		for (int i = 0; i < count; i++) {
			Item item = this.getItemDropped(state, rand, fortune);
			if (item != Items.AIR) {
				EnumEnceladusBlocks type = ((EnumEnceladusBlocks) state.getValue(BASIC_TYPE));
				
				switch(type)
				{
					case ENCELADUS_COAL_ORE:
						drops.add(new ItemStack(Items.COAL, 1, 0));
						break;
					default:
						drops.add(new ItemStack(item, 1, damageDropped(state)));
						break;
				}
			}
		}
	}
	
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random)
    {
		EnumEnceladusBlocks type = ((EnumEnceladusBlocks) state.getValue(BASIC_TYPE));
		
		switch(type)
		{
			case ENCELADUS_COAL_ORE:
				return 5 + random.nextInt(2);
			default:
				return 1;
		}
    }
	
	@Override
	public EnumSortCategoryBlock getCategory(int meta) {
		return EnumSortCategoryBlock.GENERAL;
	}
	
	@Override
	public boolean isValueable(IBlockState state) {
		return true;
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public enum EnumEnceladusBlocks implements IStringSerializable
	{
		ENCELADUS_SNOW(0, "enceladus_snow"),
		ENCELADUS_GRUNT(1, "enceladus_grunt"),
		ENCELADUS_COAL_ORE(2, "enceladus_coal_ore");

		private final int meta;
		private final String name;

		private EnumEnceladusBlocks(int meta, String name)
		{
			this.meta = meta;
			this.name = name;
		}

		public int getMeta() { return this.meta; }       

		private final static EnumEnceladusBlocks[] values = values();
		public static EnumEnceladusBlocks byMetadata(int meta) { return values[meta % values.length]; }

		@Override
		public String getName() { return this.name; }

	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(BASIC_TYPE, EnumEnceladusBlocks.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumEnceladusBlocks) state.getValue(BASIC_TYPE)).getMeta();
	}	

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, BASIC_TYPE);
	}


}

