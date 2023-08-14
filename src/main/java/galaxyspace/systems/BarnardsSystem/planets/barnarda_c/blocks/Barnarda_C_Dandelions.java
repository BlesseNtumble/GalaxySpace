package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks;

import asmodeuscore.core.astronomy.dimension.world.gen.features.trees.WorldGenTree_Forest;
import asmodeuscore.core.astronomy.dimension.world.gen.features.trees.WorldGenTree_Forest2;
import galaxyspace.systems.BarnardsSystem.core.BRBlocks;
import galaxyspace.systems.BarnardsSystem.core.BRItems;
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
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class Barnarda_C_Dandelions extends BlockBush implements IGrowable, IShearable {

	public static final PropertyEnum<EnumBlockDandelions> BASIC_TYPE = PropertyEnum.create("type", EnumBlockDandelions.class);
	
	protected static final AxisAlignedBB TALL_GRASS_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);

	public Barnarda_C_Dandelions()
	{
		super(Material.VINE);
		this.setTranslationKey("barnarda_c_dandelions");
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
		if(getType(state) == EnumBlockDandelions.DESERT_DOWN)
		{
			if(entity instanceof EntityLivingBase)
				entity.attackEntityFrom(DamageSource.CACTUS, 0.5F);
		}
    }

	
	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {		
		EnumBlockDandelions type = ((EnumBlockDandelions) state.getValue(BASIC_TYPE));
		
		switch (type)
        {						
			default:
				return Material.GROUND.getMaterialMapColor();
		
        }
    }
	
	@Override
	public Material getMaterial(IBlockState state)
    {
		EnumBlockDandelions type = ((EnumBlockDandelions) state.getValue(BASIC_TYPE));
		switch(type)
		{
			default: return this.material;
		}      
    }
	
	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos)
    {
		EnumBlockDandelions type = ((EnumBlockDandelions) state.getValue(BASIC_TYPE));
		
		switch(type)
		{
			case LEAVES_BALLS:
			case LIGHT_BALLS:
				return 8;
			default:
				return state.getLightValue();
		}
    }
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		
		EnumBlockDandelions type = ((EnumBlockDandelions) state.getValue(BASIC_TYPE));
		if(type == EnumBlockDandelions.REEDS || type == EnumBlockDandelions.REEDS_FRUITS)
			return new ItemStack(BRItems.BASIC, 1, 0);
		
		return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(state));
	}
	
	@SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (EnumBlockDandelions blockBasic : EnumBlockDandelions.values())
        {
        	if(!blockBasic.equals(blockBasic.DESERT_UP) 
        			&& !blockBasic.equals(blockBasic.REEDS) 
        			&& !blockBasic.equals(blockBasic.REEDS_FRUITS)
        			&& !blockBasic.equals(blockBasic.YELLOW_GRASS_UP))
        		list.add(new ItemStack(this, 1, blockBasic.getMeta()));
        }
    }
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		EnumBlockDandelions type = ((EnumBlockDandelions) state.getValue(BASIC_TYPE));
		if(!world.isRemote) {	
			if(type == EnumBlockDandelions.REEDS_FRUITS) {
				world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(BRItems.FOODS, 1, 0)));
				world.setBlockState(pos, this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.REEDS), 3);
				return true;	
			}
		}
		
		return false;
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
		if (!world.isAreaLoaded(pos, 1)) return; // Forge: prevent growing cactus from loading unloaded chunks with block update
		
		if (!world.isRemote)
        {
			if(state == this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.REEDS) || state == this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.REEDS_FRUITS))
			{
				if(world.isAirBlock(pos.up())) {
					int length = 1;
					IBlockState reed1 = this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.REEDS);
					IBlockState reed2 = this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.REEDS_FRUITS);
					
					while(world.getBlockState(pos.down(length)) == reed1 || world.getBlockState(pos.down(length)) == reed2) 
						length++;
							
					if(length < 4)
						world.setBlockState(pos.up(), this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.REEDS), 3);
				}
				if(state == this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.REEDS))
					if(rand.nextInt(40) == 0)
					{
						world.setBlockState(pos, this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.REEDS_FRUITS), 3);
					}
				
							
			}
			
			if(state == this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.VIOLET_TREE_SAPLING))
			{
				if (world.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0) {
					this.grow(world, rand, pos, state);
				}
			}
        }
    }	
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos)
    {
		//super.neighborChanged(state, world, pos, block, fromPos);
		//canPlaceAt(state, world, pos, EnumBlockDandelions.REEDS, BRBlocks.BARNARDA_C_GRASS.getDefaultState(), this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.REEDS));
		
		if(state == this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.YELLOW_GRASS_UP))
		{
			if(!world.getBlockState(pos.down()).equals(this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.YELLOW_GRASS_DOWN)))
			{
				this.dropBlockAsItem(world, pos, world.getBlockState(pos), 0);
				world.destroyBlock(pos, false);
			}
		}
		
		
		if(state == this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.REEDS))
		{			
			if(world.isAirBlock(pos.down()))
			{
				this.dropBlockAsItem(world, pos, world.getBlockState(pos), 0);
				world.destroyBlock(pos, false);
			}
		}
		
		if(state != this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.LEAVES_BALLS) && world.isAirBlock(pos.down()))
		{
			this.dropBlockAsItem(world, pos, world.getBlockState(pos), 0);
			world.destroyBlock(pos, false);
		}
		
		if(state == this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.LEAVES_BALLS) && world.isAirBlock(pos.up()))
		{
			this.dropBlockAsItem(world, pos, world.getBlockState(pos), 0);
			world.destroyBlock(pos, false);
		}
		
    }
	
	private void canPlaceAt(IBlockState state, World world, BlockPos pos, EntityLivingBase placer, EnumBlockDandelions type, IBlockState... valide)
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
		canPlaceAt(state, world, pos, placer, EnumBlockDandelions.REEDS, BRBlocks.BARNARDA_C_GRASS.getDefaultState(), this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.REEDS));
		
		canPlaceAt(state, world, pos, placer, EnumBlockDandelions.VIOLET_TREE_SAPLING, BRBlocks.BARNARDA_C_GRASS.getDefaultState(), BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(0));
		
		if(state == this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.DESERT_DOWN))
		{
			if(world.isAirBlock(pos.up()))
				world.setBlockState(pos.up(), this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.DESERT_UP));
		}
		
		if(state == this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.YELLOW_GRASS_DOWN))
		{
			if(world.isAirBlock(pos.up()))
				world.setBlockState(pos.up(), this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.YELLOW_GRASS_UP));
		}
		
		
    }
	
	@Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
		if(state == this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.DESERT_UP))
		{
			world.destroyBlock(pos.down(), true);
		}
		
		if(state == this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.DESERT_DOWN))
		{
			if(world.getBlockState(pos.up()) == this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.DESERT_UP))
				world.destroyBlock(pos.up(), true);
		}
		
		if(state == this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.YELLOW_GRASS_UP))
		{
			if(world.getBlockState(pos.down()) == this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.YELLOW_GRASS_DOWN))
				world.destroyBlock(pos.down(), true);
		}
		
		if(state == this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.YELLOW_GRASS_DOWN))
		{
			if(world.getBlockState(pos.up()) == this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.YELLOW_GRASS_UP))
				world.destroyBlock(pos.up(), true);
		}
		super.breakBlock(world, pos, state);
    }

	@Override
    public boolean isReplaceable(IBlockAccess world, BlockPos pos)
    {
		if(world.getBlockState(pos) == this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.REEDS))
			return false;
        return true;
    }
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) 
	{
		if(state.getMaterial() != Material.AIR) {
			EnumBlockDandelions type = ((EnumBlockDandelions) state.getValue(BASIC_TYPE));
			
			switch(type)
			{
				case GRASS:
					return Items.WHEAT_SEEDS;
				case REEDS: 
				case REEDS_FRUITS: 
					return BRItems.BASIC;
				default: return null;
			}
		}
		return null;
	}
	
	@Override
	public int damageDropped(IBlockState state) 
	{
		EnumBlockDandelions type = ((EnumBlockDandelions) state.getValue(BASIC_TYPE));
		
		switch(type)
		{
			case GRASS:
			case REEDS: 
			case REEDS_FRUITS: 
				return 0;
			default: return 0;
		}
	}
	
	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {

		if(state == this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.VIOLET_TREE_SAPLING))		
			return true;
		
		return false;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		
		if(state == this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.VIOLET_TREE_SAPLING))		
			return true;		
		
		return false;
	}

	@Override
	public void grow(World world, Random rand, BlockPos pos, IBlockState state) {
		
		if(rand.nextInt(7) == 0)
        {
            this.generateTree(world, pos, state, rand);
        }
	}

	public void generateTree(World world, BlockPos pos, IBlockState state, Random rand)
    {
		if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(world, rand, pos)) return;
		
		WorldGenerator tree = new WorldGenTree_Forest(BRBlocks.BARNARDA_C_VIOLET_LOG.getStateFromMeta(0), BRBlocks.BARNARDA_C_LEAVES.getStateFromMeta(0), rand.nextInt(3));
		WorldGenerator tree_2 = new WorldGenTree_Forest2(BRBlocks.BARNARDA_C_VIOLET_LOG.getStateFromMeta(0), BRBlocks.BARNARDA_C_LEAVES.getStateFromMeta(0), rand.nextInt(3));
		
		boolean check = true;
		for(BlockPos poscheck : BlockPos.getAllInBox(pos.add(-5, 2, -5), pos.add(5, 10, 5)))
		{
			if(!world.isAirBlock(poscheck))
				check = false;
		}
		
		if(check)
		{
			if(rand.nextInt(2) == 0)
				tree.generate(world, rand, pos);
			else
				tree_2.generate(world, rand, pos);
		}
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
            	EnumBlockDandelions type = ((EnumBlockDandelions) state.getValue(BASIC_TYPE));
            	
            	switch (type)
                {
        			case GRASS: 
        				if(rand.nextInt(100) >= 10) break;
        				drops.add(new ItemStack(item, 1, this.damageDropped(state)));
        				break;
        			case LIGHT_BALLS:
        			case LEAVES_BALLS:
        				if(rand.nextInt(100) >= 80) break;
        				drops.add(new ItemStack(Items.GLOWSTONE_DUST, 1, 0));
        				break;
                	default:
                		drops.add(new ItemStack(item, 1, this.damageDropped(state)));
                		break;
                }
               
            }
        }
    }
	
	private EnumBlockDandelions getType(IBlockState state)
	{
		return ((EnumBlockDandelions) state.getValue(BASIC_TYPE));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getAmbientOcclusionLightValue(IBlockState state)
	{
		return 1.0F;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////
	public static enum EnumBlockDandelions implements IStringSerializable
    {
		GRASS(0, "barnarda_c_tall_grass"),
		HOPPER(1, "barnarda_c_hopper_flower"),
		GREEN_FERN(2, "barnarda_c_green_fern"),
		RED_FERN(3, "barnarda_c_red_fern"),
		LIGHT_BALLS(4, "barnarda_c_light_balls"),
		LEAVES_BALLS(5, "barnarda_c_leaves_balls"),
		DESERT_DOWN(6, "barnarda_c_desert_down"),
		DESERT_UP(7, "barnarda_c_desert_up"),
		YELLOW_GRASS_DOWN(8, "barnarda_c_yellow_grass_down"),
		YELLOW_GRASS_UP(9, "barnarda_c_yellow_grass_up"),
		REEDS(10, "barnarda_c_reeds"),
		REEDS_FRUITS(11, "barnarda_c_reeds_fruits"),
		VIOLET_TREE_SAPLING(12, "barnarda_c_violet_sapling"),
		TALLGRASS_DARK(13, "barnarda_c_tallgrass_dark");

		private final int meta;
		private final String name;

		private EnumBlockDandelions(int meta, String name) {
			this.meta = meta;
			this.name = name;
		}
		
		public int getMeta() { return this.meta; }

		public static EnumBlockDandelions byMetadata(int meta) { return values()[meta]; }
		
		@Override
		public String getName() { return this.name; }

    }
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockDandelions.byMetadata(meta));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumBlockDandelions) state.getValue(BASIC_TYPE)).getMeta();
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
