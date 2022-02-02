package galaxyspace.systems.SolarSystem.planets.mercury.blocks;

import micdoodle8.mods.galacticraft.api.block.IDetectableResource;
import micdoodle8.mods.galacticraft.api.block.ITerraformableBlock;
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

public class MercuryBlocks extends Block implements ISortableBlock, ITerraformableBlock, IDetectableResource{
	
	public static final PropertyEnum<EnumBlockMercury> BASIC_TYPE = PropertyEnum.create("type", EnumBlockMercury.class);
	
	public MercuryBlocks()
    {
        super(Material.ROCK);
        this.setTranslationKey("mercuryblocks");
        this.setSoundType(SoundType.STONE); 
        this.setHarvestLevel("pickaxe", 2);
          
       // GSBlocks.GS_BLOCKS.add(this);
    }
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(state));
	}
	
	@SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (EnumBlockMercury blockBasic : EnumBlockMercury.values())
        {
            list.add(new ItemStack(this, 1, blockBasic.getMeta()));
        }
    }
	
	@Override
    public int damageDropped(IBlockState state)
    {
		EnumBlockMercury type = ((EnumBlockMercury) state.getValue(BASIC_TYPE));
		switch (type)
        {
        	default:
        		return getMetaFromState(state);
        }
    }
	
	@Override
	public EnumSortCategoryBlock getCategory(int meta) {
		return EnumSortCategoryBlock.GENERAL;
	}
	
	@Override
	public boolean isValueable(IBlockState metadata) {
		return false;
	}

	@Override
	public boolean isTerraformable(World world, BlockPos pos) {
		if(world.getBlockState(pos) == this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockMercury.SURFACE))
			return true;
		
		return false;
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public enum EnumBlockMercury implements IStringSerializable
    {
		SURFACE(0, "mercury_surface"),
        SUBSURFACE(1, "mercury_subsurface"),
		STONE(2, "mercury_stone"),
		NICKEL_ORE(3, "mercury_nickel_ore"),
		IRON_ORE(4, "mercury_iron_ore"),
		MAGNESIUM_ORE(5, "mercury_magnesium_ore");

        private final int meta;
        private final String name;

        private EnumBlockMercury(int meta, String name)
        {
            this.meta = meta;
            this.name = name;
        }

        public int getMeta() { return this.meta; }       

        private final static EnumBlockMercury[] values = values();
        public static EnumBlockMercury byMetadata(int meta) { return values[meta % values.length]; }

        @Override
        public String getName() { return this.name; }
		
    }
	
	@Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockMercury.byMetadata(meta));
    }
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumBlockMercury) state.getValue(BASIC_TYPE)).getMeta();
	}	
	
	@Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, BASIC_TYPE);
    }
}
