package galaxyspace.systems.SolarSystem.moons.io.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import galaxyspace.GalaxySpace;
import galaxyspace.api.block.IEnergyGeyser;
import galaxyspace.core.GSItems;
import micdoodle8.mods.galacticraft.api.block.ITerraformableBlock;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.blocks.ISortableBlock;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryBlock;
import micdoodle8.mods.galacticraft.planets.venus.VenusModule;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class IoBlocks extends Block implements ISortableBlock, ITerraformableBlock, IEnergyGeyser{

	public static final PropertyEnum<EnumIoBlocks> BASIC_TYPE = PropertyEnum.create("type", EnumIoBlocks.class);

	protected static final AxisAlignedBB SOUL_SAND_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.9D, 1.0D);

	public IoBlocks()
    {
        super(Material.ROCK);
        this.setUnlocalizedName("ioblocks");
        this.setSoundType(SoundType.STONE); 
        this.setHarvestLevel("pickaxe", 2);
        this.setTickRandomly(false);
    }

	@SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (EnumIoBlocks blockBasic : EnumIoBlocks.values())
        {
            list.add(new ItemStack(this, 1, blockBasic.getMeta()));
        }
    }
	
	@Nullable
	@Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
		if(state == state.withProperty(BASIC_TYPE, EnumIoBlocks.IO_ASH))	
			return SOUL_SAND_AABB;
		
		return super.getCollisionBoundingBox(state, worldIn, pos);
    }
	
	@Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state)
    {
		switch(state.getValue(BASIC_TYPE))
		{
			case IO_LAVA_GEYSER:
			case IO_SULFUR_GEYSER:
				world.scheduleBlockUpdate(pos, this, this.tickRate(world) + world.rand.nextInt(10), 0);
				break;
			default:
				break;
		}
			
    }
	
	@Override
	public void onEntityWalk(World world, BlockPos pos, Entity entity)
    {
		if(world.getBlockState(pos) == world.getBlockState(pos).withProperty(BASIC_TYPE, EnumIoBlocks.IO_LAVA_GEYSER))
		{
			if(entity instanceof EntityLivingBase)
				entity.setFire(5);
		}
		
		if(world.getBlockState(pos) == world.getBlockState(pos).withProperty(BASIC_TYPE, EnumIoBlocks.IO_SULFUR_GEYSER))
		{
			if(entity instanceof EntityPlayer)
				((EntityPlayer) entity).addPotionEffect(new PotionEffect(MobEffects.POISON, 40 * 20));
		}
    }
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
		if(!world.isAreaLoaded(pos, 16)) return;

		if(state == state.withProperty(BASIC_TYPE, EnumIoBlocks.IO_LAVA_GEYSER))
		{
			if(world.isAirBlock(pos.up()) && world.getBlockState(pos.down()).getMaterial() == Material.LAVA && !world.isBlockNormalCube(pos.up(), true) && world.rand.nextInt(4) == 0)
			{
				GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(pos.getX() + rand.nextDouble(), pos.getY() + 1.0D + rand.nextDouble(), pos.getZ() + rand.nextDouble()), new Vector3(0.0D + ((rand.nextFloat() / 10) * (rand.nextBoolean() ? -1 : 1)), 0.01D, 0.0D + ((rand.nextFloat() / 10) * (rand.nextBoolean() ? -1 : 1))), new Object [] { 200 + rand.nextInt(40), 4, false, new Vector3(0.1F, 0.1F, 0.1F), 1.0D} );
	    		GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(pos.getX() + rand.nextDouble(), pos.getY() + 1.0D + rand.nextDouble(), pos.getZ() + rand.nextDouble()), new Vector3(0.0D + ((rand.nextFloat() / 10) * (rand.nextBoolean() ? -1 : 1)), 0.01D, 0.0D + ((rand.nextFloat() / 10) * (rand.nextBoolean() ? -1 : 1))), new Object [] { 200 + rand.nextInt(40), 4, false, new Vector3(0.1F, 0.1F, 0.1F), 1.0D} );
	    		GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(pos.getX() + rand.nextDouble(), pos.getY() + 1.0D + rand.nextDouble(), pos.getZ() + rand.nextDouble()), new Vector3(0.0D + ((rand.nextFloat() / 10) * (rand.nextBoolean() ? -1 : 1)), 0.01D, 0.0D + ((rand.nextFloat() / 10) * (rand.nextBoolean() ? -1 : 1))), new Object [] { 200 + rand.nextInt(40), 6, false, new Vector3(0.1F, 0.1F, 0.1F), 1.0D} );
	    		if(world.rand.nextInt(2) == 0) {
	    			GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(pos.getX() + rand.nextDouble(), pos.getY() + 1.0D + rand.nextDouble(), pos.getZ() + rand.nextDouble()), new Vector3(0.0D + ((rand.nextFloat() / 5) * (rand.nextBoolean() ? -1 : 1)), 0.8D, 0.0D + ((rand.nextFloat() / 5) * (rand.nextBoolean() ? -1 : 1))), new Object [] { 100 + rand.nextInt(40), 48, true, new Vector3(1.0F, 1.0F, 1.0F), 1.0D} );
		    		GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(pos.getX() + rand.nextDouble(), pos.getY() + 1.0D + rand.nextDouble(), pos.getZ() + rand.nextDouble()), new Vector3(0.0D + ((rand.nextFloat() / 5) * (rand.nextBoolean() ? -1 : 1)), 0.8D, 0.0D + ((rand.nextFloat() / 5) * (rand.nextBoolean() ? -1 : 1))), new Object [] { 100 + rand.nextInt(40), 48, true, new Vector3(1.0F, 1.0F, 1.0F), 2.0D} );
		    		GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(pos.getX() + rand.nextDouble(), pos.getY() + 1.0D + rand.nextDouble(), pos.getZ() + rand.nextDouble()), new Vector3(0.0D + ((rand.nextFloat() / 5) * (rand.nextBoolean() ? -1 : 1)), 0.8D, 0.0D + ((rand.nextFloat() / 5) * (rand.nextBoolean() ? -1 : 1))), new Object [] { 100 + rand.nextInt(40), 48, true, new Vector3(1.0F, 1.0F, 1.0F), 2.0D} );
		    		GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(pos.getX() + rand.nextDouble(), pos.getY() + 1.0D + rand.nextDouble(), pos.getZ() + rand.nextDouble()), new Vector3(0.0D + ((rand.nextFloat() / 5) * (rand.nextBoolean() ? -1 : 1)), 1.2D + rand.nextDouble(), 0.0D + ((rand.nextFloat() / 5) * (rand.nextBoolean() ? -1 : 1))), new Object [] { 100 + rand.nextInt(40), 48, true, new Vector3(1.0F, 1.0F, 1.0F), 2.0D} );
	    		}
			}
		}
		
		if(state == state.withProperty(BASIC_TYPE, EnumIoBlocks.IO_SULFUR_GEYSER))
		{
			boolean flag = false;
			for (int i = 0; i < 15; i++) {
				if(world.getBlockState(pos.down(i)).getBlock() == VenusModule.sulphuricAcid.getBlock()) {
					flag = true; 
					break;
				}
			}
			
			if(world.isAirBlock(pos.up()) && flag && world.rand.nextInt(10) == 0)
			{
				GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(pos.getX() + rand.nextDouble(), pos.getY() + 1.0D + rand.nextDouble(), pos.getZ() + rand.nextDouble()), new Vector3(0.0D + ((rand.nextFloat() / 10) * (rand.nextBoolean() ? -1 : 1)), 0.25D, 0.0D + ((rand.nextFloat() / 10) * (rand.nextBoolean() ? -1 : 1))), new Object [] { 20, 4, false, new Vector3(0.6F, 0.8F, 0.2F), 1.0D} );
		    	GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(pos.getX() + rand.nextDouble(), pos.getY() + 1.0D + rand.nextDouble(), pos.getZ() + rand.nextDouble()), new Vector3(0.0D + ((rand.nextFloat() / 10) * (rand.nextBoolean() ? -1 : 1)), 0.15D, 0.0D + ((rand.nextFloat() / 10) * (rand.nextBoolean() ? -1 : 1))), new Object [] { 10, 5, false, new Vector3(0.6F, 0.8F, 0.2F), 1.0D} );
		    	GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(pos.getX() + rand.nextDouble(), pos.getY() + 1.0D + rand.nextDouble(), pos.getZ() + rand.nextDouble()), new Vector3(0.0D + ((rand.nextFloat() / 10) * (rand.nextBoolean() ? -1 : 1)), 0.15D, 0.0D + ((rand.nextFloat() / 10) * (rand.nextBoolean() ? -1 : 1))), new Object [] { 10, 6, false, new Vector3(0.6F, 0.8F, 0.2F), 1.0D} );
		    
			}			
		}
		
		
		world.scheduleBlockUpdate(pos, this, 0, 0);
    }
	
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		if(state == state.withProperty(BASIC_TYPE, EnumIoBlocks.IO_ASH)) {
			entityIn.motionX *= 0.4D;
			entityIn.motionZ *= 0.4D;
		}
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(state));
	}
	
	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        switch (state.getValue(BASIC_TYPE))
        {
        	case IO_SULFUR_ORE:
        	case IO_VOLCANIC_ORE:
        		return GSItems.BASIC;
        	default:
        		return Item.getItemFromBlock(state.getBlock());
        }
    }
	
	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        Random rand = world instanceof World ? ((World)world).rand : RANDOM;

        int count = quantityDropped(state, fortune, rand);
        drops.clear();
        for (int i = 0; i < 1; i++)
        {
            Item item = this.getItemDropped(state, rand, fortune);
            if (item != Items.AIR)
            {      
            	
            	switch (state.getValue(BASIC_TYPE))
                {            		
        			case IO_SULFUR_ORE: 
        				drops.add(new ItemStack(item, 1, 7));
        				break;
        			case IO_VOLCANIC_ORE: 
        				drops.add(new ItemStack(item, 1, 12));
        				break;
                	default:
                		drops.add(new ItemStack(item, 1, this.damageDropped(state)));
                		break;
                }
               
            }
        }
    }
	
	@Override
    public int damageDropped(IBlockState state)
    {
        switch (state.getValue(BASIC_TYPE))
        {	 
        	case IO_SULFUR_ORE:
        		return 7;
        	case IO_VOLCANIC_ORE:
	            return 12;
	        default:
	            return getMetaFromState(state);
        }
    }
	
	@Override
	public EnumSortCategoryBlock getCategory(int meta) {
		return EnumSortCategoryBlock.GENERAL;
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public enum EnumIoBlocks implements IStringSerializable
	{
		IO_GRUNT(0, "io_grunt"),
		IO_STONE(1, "io_stone"),
		IO_ASH(2, "io_ash"),
		IO_COPPER_ORE(3, "io_copper_ore"),
		IO_SULFUR_ORE(4, "io_sulfur_ore"),
		IO_VOLCANIC_ORE(5, "io_volcanic_ore"),
		IO_LAVA_GEYSER(6, "io_lava_geyser"),
		IO_SULFUR_GEYSER(7, "io_sulfur_geyser"),
		IO_DUNGEON_TOP(8, "io_top"),
		IO_DUNGEON_FLOOR(9, "io_floor"),
		IO_DUNGEON_BRICKS(10, "io_dungeon_bricks");

		private final int meta;
		private final String name;

		private EnumIoBlocks(int meta, String name)
		{
			this.meta = meta;
			this.name = name;
		}

		public int getMeta() { return this.meta; }       

		private final static EnumIoBlocks[] values = values();
		public static EnumIoBlocks byMetadata(int meta) { return values[meta % values.length]; }

		@Override
		public String getName() { return this.name; }

	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(BASIC_TYPE, EnumIoBlocks.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumIoBlocks) state.getValue(BASIC_TYPE)).getMeta();
	}	

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, BASIC_TYPE);
	}

	@Override
	public boolean isTerraformable(World world, BlockPos pos) {
		if(world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos)) <= 2) return true;
		return false;
	}

	@Override
	public boolean isWorkGeyser(World world, IBlockState state, BlockPos pos) {
		
		if(state == state.withProperty(BASIC_TYPE, EnumIoBlocks.IO_SULFUR_GEYSER)) {
			boolean flag = false;
			for (int i = 0; i < 15; i++) {
				if(world.getBlockState(pos.down(i)).getBlock() == VenusModule.sulphuricAcid.getBlock()) {
					flag = true; 
					break;
				}
			}
			return flag;
		}
		if(state == state.withProperty(BASIC_TYPE, EnumIoBlocks.IO_LAVA_GEYSER))
			return world.getBlockState(pos.down()).getBlock() == FluidRegistry.LAVA.getBlock();
		return false;
	}

}

