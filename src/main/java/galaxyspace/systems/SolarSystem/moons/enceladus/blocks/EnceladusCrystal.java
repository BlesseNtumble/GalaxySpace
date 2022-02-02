package galaxyspace.systems.SolarSystem.moons.enceladus.blocks;

import java.util.Random;

import galaxyspace.core.GSItems;
import galaxyspace.systems.SolarSystem.moons.enceladus.tile.TileEntityBlockCrystallTE;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EnceladusCrystal extends Block implements ITileEntityProvider
{
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	
    public EnceladusCrystal()
    {
        super(Material.GLASS);    	
        this.setTranslationKey("enceladus_crystal");
        this.setHardness(2.0F);
        this.setSoundType(SoundType.GLASS);
        this.setLightLevel(3.0F);
    }
	
	@Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityBlockCrystallTE();
    }
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(Item.getItemFromBlock(this), 1, 0);
	}
	
	@Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        int angle = MathHelper.floor(placer.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        int change = EnumFacing.byHorizontalIndex(angle).getOpposite().getHorizontalIndex();
        worldIn.setBlockState(pos, getStateFromMeta(change), 3);
    }
	
	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        switch (getMetaFromState(state))
        {
        	default:
        		return GSItems.BASIC;
        }
    }
	
	@Override
    public int damageDropped(IBlockState state)
    {
        switch (getMetaFromState(state))
        {	        
	        default:
	            return 8;
        }
    }
	
	@Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.INVISIBLE;
    }
	
	@Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    
    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
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
