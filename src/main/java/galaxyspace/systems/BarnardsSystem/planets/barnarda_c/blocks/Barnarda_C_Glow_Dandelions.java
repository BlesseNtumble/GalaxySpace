package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class Barnarda_C_Glow_Dandelions extends BlockBush implements IGrowable, IShearable {

	public static final PropertyEnum<EnumBlockGlowDandelions> BASIC_TYPE = PropertyEnum.create("type", EnumBlockGlowDandelions.class);

	protected static final AxisAlignedBB TALL_GRASS_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);

	public Barnarda_C_Glow_Dandelions()
	{
		super(Material.VINE);
		this.setTranslationKey("barnarda_c_glow_dandelions");
		this.setTickRandomly(true);
		this.setSoundType(SoundType.PLANT);
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return TALL_GRASS_AABB;
    }
	
	@Nullable
	@Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }
	
	@Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity)
    {

    }

	
	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
		EnumBlockGlowDandelions type = ((EnumBlockGlowDandelions) state.getValue(BASIC_TYPE));
		
		switch (type)
        {						
			default:
				return Material.GROUND.getMaterialMapColor();
		
        }
    }
	
	@Override
	public Material getMaterial(IBlockState state)
    {
		EnumBlockGlowDandelions type = ((EnumBlockGlowDandelions) state.getValue(BASIC_TYPE));
		switch(type)
		{
			default: return this.material;
		}      
    }
	
	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos)
    {
		EnumBlockGlowDandelions type = ((EnumBlockGlowDandelions) state.getValue(BASIC_TYPE));
		
		switch(type)
		{

			default:
				return 4;
		}
    }
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {

		EnumBlockGlowDandelions type = ((EnumBlockGlowDandelions) state.getValue(BASIC_TYPE));

		return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(state));
	}
	
	@SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (EnumBlockGlowDandelions blockBasic : EnumBlockGlowDandelions.values())
        {
			list.add(new ItemStack(this, 1, blockBasic.getMeta()));
        }
    }
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			EnumBlockGlowDandelions type = ((EnumBlockGlowDandelions) state.getValue(BASIC_TYPE));

		}
		return false;
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
		if (!world.isAreaLoaded(pos, 1)) return; // Forge: prevent growing cactus from loading unloaded chunks with block update
		
		if (!world.isRemote)
        {

        }
    }	
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos)
    {
		super.neighborChanged(state, world, pos, block, fromPos);
		//canPlaceAt(state, world, pos, EnumBlockDandelions.REEDS, BRBlocks.BARNARDA_C_GRASS.getDefaultState(), this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.REEDS));

    }
	
	private void canPlaceAt(IBlockState state, World world, BlockPos pos, EntityLivingBase placer, EnumBlockGlowDandelions type, IBlockState... valide)
	{
		
		if(!world.isRemote && state == this.getDefaultState().withProperty(BASIC_TYPE, type))
		{
			//GalaxySpace.debug("123");
			boolean is_forriden = true;
			for(IBlockState block : valide)
				if(world.getBlockState(pos.down()) == block)
					is_forriden = false;
				
			if(is_forriden) {
				world.destroyBlock(pos, true);
				if(placer instanceof EntityPlayer && !((EntityPlayer) placer).capabilities.isCreativeMode)
					world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this.getDefaultState().getBlock(), 1, this.getMetaFromState(state))));
			}
		}		
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {

    }
	
	@Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {

		super.breakBlock(world, pos, state);
    }

	@Override
    public boolean isReplaceable(IBlockAccess world, BlockPos pos)
    {
        return true;
    }

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {

		return false;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return false;
	}

	@Override
	public void grow(World world, Random rand, BlockPos pos, IBlockState state) {

	}
	
	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		return null;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {		
		return false;		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
	
	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        Random rand = world instanceof World ? ((World)world).rand : RANDOM;

        int count = quantityDropped(state, fortune, rand);
        for (int i = 0; i < count; i++)
        {
            Item item = this.getItemDropped(state, rand, fortune);
            if (item != Items.AIR && state.getMaterial() != Material.AIR)
            {
				EnumBlockGlowDandelions type = ((EnumBlockGlowDandelions) state.getValue(BASIC_TYPE));
            	
            	switch (type)
                {
                	default:
                		drops.add(new ItemStack(item, 1, this.damageDropped(state)));
                		break;
                }
               
            }
        }
    }
	
	private EnumBlockGlowDandelions getType(IBlockState state)
	{
		return ((EnumBlockGlowDandelions) state.getValue(BASIC_TYPE));
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////
	public static enum EnumBlockGlowDandelions implements IStringSerializable
    {
		GLOW_PLANT_1(0, "barnarda_c_glow_plant_1"),
		GLOW_PLANT_2(1, "barnarda_c_glow_plant_2"),
		GLOW_PLANT_3(2, "barnarda_c_glow_plant_3"),
		GLOW_PLANT_4(3, "barnarda_c_glow_plant_4");

		private final int meta;
		private final String name;

		private EnumBlockGlowDandelions(int meta, String name) {
			this.meta = meta;
			this.name = name;
		}
		
		public int getMeta() { return this.meta; }

		public static EnumBlockGlowDandelions byMetadata(int meta) { return values()[meta]; }
		
		@Override
		public String getName() { return this.name; }

    }
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockGlowDandelions.byMetadata(meta));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumBlockGlowDandelions) state.getValue(BASIC_TYPE)).getMeta();
	}	
	
	@Override
	protected BlockStateContainer createBlockState()
	{		
		return new BlockStateContainer(this, BASIC_TYPE);
	}
	
	@Override
	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
    {
		return true;
    }
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
		return true;
    }

}
