package galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines;

import asmodeuscore.api.item.IShiftDescription;
import micdoodle8.mods.galacticraft.core.blocks.ISortableBlock;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryBlock;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSingleSolarPanel extends Block implements IShiftDescription, ISortableBlock{

	protected static final AxisAlignedBB AABB_BOTTOM = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D);
	private int tier;	
	
	public BlockSingleSolarPanel(String name, int tier)
    {
        super(Material.IRON);    	
        this.setUnlocalizedName(name);
        this.setHardness(2.0F);
        this.setSoundType(SoundType.METAL);
        this.tier = tier;        
    }
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
		return AABB_BOTTOM;		
    }
	
	@Override
	public int getLightOpacity(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return 1;
    }
	
	@Override
	public boolean isFullCube(IBlockState state)
    {
		return false;
    }
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        
    }
	
	@Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
    {
		return false;		
    }
	
	@Override
	public boolean isTopSolid(IBlockState state)
    {
		return true;		
    }
	
	public String getShiftDescription(int meta)
    {
        return GCCoreUtil.translate(this.getUnlocalizedName() + ".desc");
    }

	@Override
	public String getDescription(int meta) {
		return null;
	}
	
    @Override
    public boolean showDescription(int meta)
    {
        return true;
    }
    
    @Override
    public EnumSortCategoryBlock getCategory(int meta)
    {
        return EnumSortCategoryBlock.MACHINE;
    }
    
    public int getTier()
    {
    	return this.tier;
    }
}
