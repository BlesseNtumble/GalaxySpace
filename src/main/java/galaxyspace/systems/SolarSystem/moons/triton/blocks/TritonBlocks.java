package galaxyspace.systems.SolarSystem.moons.triton.blocks;

import java.util.Random;

import galaxyspace.GalaxySpace;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
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

public class TritonBlocks  extends Block implements ISortableBlock{
	
	public static final PropertyEnum<EnumTritonBlocks> BASIC_TYPE = PropertyEnum.create("type", EnumTritonBlocks.class);

	
	public TritonBlocks()
	{
		super(Material.ROCK);
		this.setUnlocalizedName("tritonblocks");
        this.setSoundType(SoundType.STONE); 
        this.setHarvestLevel("pickaxe", 2);	
        this.setTickRandomly(false);
	}

	@SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (EnumTritonBlocks blockBasic : EnumTritonBlocks.values())
        {
            list.add(new ItemStack(this, 1, blockBasic.getMeta()));
        }
    }
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
		if(!world.isAreaLoaded(pos, 1)) return;
		if(state == state.withProperty(BASIC_TYPE, EnumTritonBlocks.TRITON_GEYSER))
		{
			if(world.isAirBlock(pos.up())) {
				GalaxySpace.proxy.spawnParticle("waterbubbles1", new Vector3(pos.getX() + rand.nextDouble(), pos.getY() + 1.0D + rand.nextDouble(), pos.getZ() + rand.nextDouble()), new Vector3(0.0D + ((rand.nextFloat() / 10) * (rand.nextBoolean() ? -1 : 1)), 0.0001D, 0.0D + ((rand.nextFloat() / 20) * (rand.nextBoolean() ? -1 : 1))), new Object [] { 600 + rand.nextInt(400), 7, false, new Vector3(0.3F, 0.35F, 0.3F), 1.9D, 13.0D} );
				GalaxySpace.proxy.spawnParticle("waterbubbles1", new Vector3(pos.getX() + rand.nextDouble(), pos.getY() + 1.0D + rand.nextDouble(), pos.getZ() + rand.nextDouble()), new Vector3(0.0D + ((rand.nextFloat() / 20) * (rand.nextBoolean() ? -1 : 1)), 0.0001D, 0.0D + ((rand.nextFloat() / 20) * (rand.nextBoolean() ? -1 : 1))), new Object [] { 600 + rand.nextInt(400), 7, false, new Vector3(0.4F, 0.4F, 0.4F), 1.9D, 13.0D} );
    			GalaxySpace.proxy.spawnParticle("waterbubbles1", new Vector3(pos.getX() + rand.nextDouble(), pos.getY() + 1.0D + rand.nextDouble(), pos.getZ() + rand.nextDouble()), new Vector3(0.0D + ((rand.nextFloat() / 20) * (rand.nextBoolean() ? -1 : 1)), 0.0001D, 0.0D + ((rand.nextFloat() / 20) * (rand.nextBoolean() ? -1 : 1))), new Object [] { 600 + rand.nextInt(400), 7, false, new Vector3(0.1F, 0.1F, 0.1F), 1.9D, 13.0D} );
    		
			}
			world.scheduleBlockUpdate(pos, this, 0, 0);
		}
    }
	
	@Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state)
    {
		switch(state.getValue(BASIC_TYPE))
		{
			case TRITON_GEYSER:
				world.scheduleBlockUpdate(pos, this, this.tickRate(world) + world.rand.nextInt(10), 0);
				break;
			default:
				break;
		}
			
    }
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(state));
	}
	
	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
		EnumTritonBlocks type = state.getValue(BASIC_TYPE);
        switch (type)
        {
        	default:
        		return Item.getItemFromBlock(this);
        }
    }

    @Override
    public int damageDropped(IBlockState state)
    {
    	EnumTritonBlocks type = state.getValue(BASIC_TYPE);
        switch (type)
        {
	       
	        default:
	            return getMetaFromState(state);
        }
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random)
    {
        int bonus = 0;
        int default_count = 0;

        EnumTritonBlocks type = state.getValue(BASIC_TYPE);
/*
        if (type == EnumTritonBlocks.TITAN_LAPIS_ORE)
        {
            bonus = 2;
            default_count = 8;
        }


        */
        if (fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped(state, random, fortune))
        {
            int j = random.nextInt(fortune + 2) - 1;

            if (j < 0)
            {
                j = 0;
            }

            return (this.quantityDropped(random) + default_count) * (j + 1) + bonus;
        }
        else
        {
            return this.quantityDropped(random) + default_count + random.nextInt(1 + bonus);
        }
    }
	
	@Override
	public EnumSortCategoryBlock getCategory(int meta) {
		return EnumSortCategoryBlock.GENERAL;
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public enum EnumTritonBlocks implements IStringSerializable
	{
		TRITON_GRUNT(0, "triton_grunt"),
		TRITON_SUBGRUNT(1, "triton_subgrunt"),
		TRITON_STONE(2, "triton_stone"),
		TRITON_GEYSER(3, "triton_geyser");
		
		private final int meta;
		private final String name;

		private EnumTritonBlocks(int meta, String name)
		{
			this.meta = meta;
			this.name = name;
		}

		public int getMeta() { return this.meta; }       

		private final static EnumTritonBlocks[] values = values();
		public static EnumTritonBlocks byMetadata(int meta) { return values[meta % values.length]; }

		@Override
		public String getName() { return this.name; }

	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(BASIC_TYPE, EnumTritonBlocks.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumTritonBlocks) state.getValue(BASIC_TYPE)).getMeta();
	}	

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, BASIC_TYPE);
	}

}

