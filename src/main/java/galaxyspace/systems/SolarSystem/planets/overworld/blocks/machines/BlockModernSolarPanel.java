package galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines;

import asmodeuscore.api.item.IShiftDescription;
import galaxyspace.GalaxySpace;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityModernSolarPanel;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import micdoodle8.mods.galacticraft.core.blocks.BlockAdvancedTile;
import micdoodle8.mods.galacticraft.core.blocks.BlockMulti;
import micdoodle8.mods.galacticraft.core.blocks.ISortableBlock;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseUniversalElectrical;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryBlock;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockModernSolarPanel extends BlockAdvancedTile implements IShiftDescription, ISortableBlock{
	
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    
	public BlockModernSolarPanel()
    {
        super(GCBlocks.machine);
        this.setTranslationKey("modern_solarpanel");
        this.setHardness(1.0F);
        this.setSoundType(SoundType.METAL);
    }

	@Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
	
	@Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
	
	@Override
    public int getLightOpacity(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return 1;
    }
	
    @Override
    public boolean onMachineActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        playerIn.openGui(GalaxySpace.instance, -1, world, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }
    
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        int angle = MathHelper.floor(placer.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        int change = EnumFacing.byHorizontalIndex(angle).getOpposite().getHorizontalIndex();
        worldIn.setBlockState(pos, getStateFromMeta(change), 3);
        BlockMulti.onPlacement(worldIn, pos, placer, this);
    }
    
    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        final TileEntity var9 = worldIn.getTileEntity(pos);

        if (var9 instanceof TileEntityModernSolarPanel)
        {
            ((TileEntityModernSolarPanel) var9).onDestroy(var9);
        }

        super.breakBlock(worldIn, pos, state);
    }
    
    @Override
    public boolean onUseWrench(World world, BlockPos pos, EntityPlayer entityPlayer, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
    	int metadata = getMetaFromState(world.getBlockState(pos));
        int change = world.getBlockState(pos).getValue(FACING).rotateY().getHorizontalIndex();
        world.setBlockState(pos, this.getStateFromMeta(metadata - (metadata % 4) + change), 3);

		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileBaseUniversalElectrical) {
			((TileBaseUniversalElectrical) te).updateFacing();
		}
		
        return true;
    }
    
    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityModernSolarPanel();
    }
    
    @Override
    public String getShiftDescription(int meta)
    {
        return GCCoreUtil.translate(this.getTranslationKey() + ".desc");
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
    
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.byHorizontalIndex(meta % 4);
        return this.getDefaultState().withProperty(FACING, enumfacing);
    }
    
    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getHorizontalIndex();
    }
    
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING);
    }
}
