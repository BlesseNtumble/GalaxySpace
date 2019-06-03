package galaxyspace.systems.SolarSystem.planets.overworld.blocks;

import micdoodle8.mods.galacticraft.core.blocks.ISortableBlock;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryBlock;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
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

public class BlockFutureGlasses extends BlockGlass implements ISortableBlock {

	public static final PropertyEnum<EnumBlockFutureGlass> BASIC_TYPE = PropertyEnum.create("type", EnumBlockFutureGlass.class);
	
	public BlockFutureGlasses()
    {
        super(Material.GLASS, false);
        this.setUnlocalizedName("futureglass");
        this.setSoundType(SoundType.GLASS); 
        //this.setCreativeTab(GSCreativeTabs.GSBlocksTab);
        
       // GSBlocks.GS_BLOCKS.add(this);
    }
	
	@Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {       
        return MapColor.AIR;
    }
	
	@SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (EnumBlockFutureGlass blockBasic : EnumBlockFutureGlass.values())
        {
            list.add(new ItemStack(this, 1, blockBasic.getMeta()));
        }
    }
	
	@Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockFutureGlass.byMetadata(meta));
    }
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumBlockFutureGlass) state.getValue(BASIC_TYPE)).getMeta();
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(state));
	}
	
	@Override
    public int damageDropped(IBlockState state)
    {
		EnumBlockFutureGlass type = ((EnumBlockFutureGlass) state.getValue(BASIC_TYPE));
		switch (type)
        {
        	default:
        		return getMetaFromState(state);
        }
    }
	
	@Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, BASIC_TYPE);
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
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public enum EnumBlockFutureGlass implements IStringSerializable
    {
        F_GLASS_BLACK(0, "futureglass_black"),
        F_GLASS_RED(1, "futureglass_red"),
        F_GLASS_GREEN(2, "futureglass_green"),		
		F_GLASS_BROWN(3, "futureglass_brown"),
		F_GLASS_BLUE(4, "futureglass_blue"),
		F_GLASS_PURPLE(5, "futureglass_purple"),		
		F_GLASS_CYAN(6, "futureglass_cyan"),
		F_GLASS_LIGHTGRAY(7, "futureglass_lightgray"),		
		F_GLASS_GRAY(8, "futureglass_gray"),
		F_GLASS_PINK(9, "futureglass_pink"),
		F_GLASS_LIME(10, "futureglass_lime"),
		F_GLASS_YELLOW(11, "futureglass_yellow"),
		F_GLASS_LIGHTBLUE(12, "futureglass_lightblue"),
		F_GLASS_MAGNETA(13, "futureglass_magneta"),
		F_GLASS_ORANGE(14, "futureglass_orange"),
		F_GLASS_WHITE(15, "futureglass_white");

        private final int meta;
        private final String name;

        private EnumBlockFutureGlass(int meta, String name)
        {
            this.meta = meta;
            this.name = name;
        }

        public int getMeta() { return this.meta; }       

        private final static EnumBlockFutureGlass[] values = values();
        public static EnumBlockFutureGlass byMetadata(int meta) { return values[meta % values.length]; }

        @Override
        public String getName() { return this.name; }
    }

}
