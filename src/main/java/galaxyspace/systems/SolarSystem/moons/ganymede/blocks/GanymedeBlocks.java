package galaxyspace.systems.SolarSystem.moons.ganymede.blocks;

import java.util.Random;

import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.blocks.ISortableBlock;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryBlock;
import micdoodle8.mods.galacticraft.planets.asteroids.items.AsteroidsItems;
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

public class GanymedeBlocks extends Block implements ISortableBlock{

	public static final PropertyEnum<EnumGanymedeBlocks> BASIC_TYPE = PropertyEnum.create("type", EnumGanymedeBlocks.class);

	public GanymedeBlocks()
    {
        super(Material.ROCK);
        this.setUnlocalizedName("ganymedeblocks");
        this.setSoundType(SoundType.STONE); 
        this.setHarvestLevel("pickaxe", 2);
    }
	
	@SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (EnumGanymedeBlocks blockBasic : EnumGanymedeBlocks.values())
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
		switch (getMetaFromState(state))
		{
			case 3:
				return 4;
			default:
				return getMetaFromState(state);  
		}
    }
	
	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        switch (getMetaFromState(state))
        {
        	case 3:
        		return AsteroidsItems.basicItem;
        	default:
        		return Item.getItemFromBlock(this);
        }
    }
	
	@Override
	public EnumSortCategoryBlock getCategory(int meta) {
		return EnumSortCategoryBlock.GENERAL;
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public enum EnumGanymedeBlocks implements IStringSerializable
	{
		GANYMEDE_GRUNT(0, "ganymede_grunt"),
		GANYMEDE_STONE(1, "ganymede_stone"),
		GANYMEDE_MAGNESIUM_ORE(2, "ganymede_magnesium_ore"),
		GANYMEDE_TITANIUM_ORE(3, "ganymede_titanium_ore");


		private final int meta;
		private final String name;

		private EnumGanymedeBlocks(int meta, String name)
		{
			this.meta = meta;
			this.name = name;
		}

		public int getMeta() { return this.meta; }       

		private final static EnumGanymedeBlocks[] values = values();
		public static EnumGanymedeBlocks byMetadata(int meta) { return values[meta % values.length]; }

		@Override
		public String getName() { return this.name; }

	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(BASIC_TYPE, EnumGanymedeBlocks.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumGanymedeBlocks) state.getValue(BASIC_TYPE)).getMeta();
	}	

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, BASIC_TYPE);
	}

}

