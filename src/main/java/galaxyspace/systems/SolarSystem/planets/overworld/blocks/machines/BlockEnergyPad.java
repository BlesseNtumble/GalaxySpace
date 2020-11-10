package galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines;

import asmodeuscore.api.item.IShiftDescription;
import galaxyspace.GalaxySpace;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityEnergyPad;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityGravitationModule;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import micdoodle8.mods.galacticraft.core.blocks.BlockAdvancedTile;
import micdoodle8.mods.galacticraft.core.blocks.ISortableBlock;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryBlock;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEnergyPad extends BlockAdvancedTile implements IShiftDescription, ISortableBlock{

	protected static final AxisAlignedBB AABB_BOTTOM = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D);
	public static final PropertyBool STATUS = PropertyBool.create("status");
	
	public BlockEnergyPad()
    {
        super(GCBlocks.machine);
        this.setUnlocalizedName("energy_pad");
        this.setHardness(1.0F);
        this.setSoundType(SoundType.METAL);       
    }

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
		return AABB_BOTTOM;		
    }
	
	@Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {        
        worldIn.setBlockState(pos, getStateFromMeta(0), 3);
    }
	
	@Override
	public boolean isTopSolid(IBlockState state)
    {
		return true;		
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
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
    {
		if(world.isRemote)
		{
			if(entity instanceof EntityPlayer)
			{
				TileEntityEnergyPad tile = (TileEntityEnergyPad) world.getTileEntity(pos);
				
				EntityPlayer player = (EntityPlayer) entity;
				if(tile.hasEnoughEnergyToRun)
					for(int i = 0; i < 5; i++)
						Minecraft.getMinecraft().world.spawnParticle(EnumParticleTypes.REDSTONE, pos.getX() + world.rand.nextDouble(), pos.getY() + world.rand.nextDouble() + 0.2D, pos.getZ() + world.rand.nextDouble(), 0.5D, 0.9D, 0);
			}
		}
		else
		{
			if(entity instanceof EntityPlayer)
			{
				TileEntityEnergyPad tile = (TileEntityEnergyPad) world.getTileEntity(pos);    
				EntityPlayer player = (EntityPlayer) entity;
				
				tile.smeltItem(player);			
			}
		}
    }
	
	@Override
    public boolean onMachineActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        //playerIn.openGui(GalaxySpace.instance, -1, world, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }
	
	@Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityEnergyPad();
    }
    
    @Override
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
    
    @Override
    public int getMetaFromState(IBlockState state)
    {
        return 0;
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta)
    {    	
        return this.getDefaultState().withProperty(STATUS, false);
    }
    
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, STATUS);
    }
    
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        TileEntity tile = worldIn.getTileEntity(pos);        
        
        if(tile != null) {
			
        	boolean energyLevel = ((TileEntityEnergyPad) tile).isCollide;
        	return state.withProperty(STATUS, energyLevel);
        }
        return state;
    }
}
