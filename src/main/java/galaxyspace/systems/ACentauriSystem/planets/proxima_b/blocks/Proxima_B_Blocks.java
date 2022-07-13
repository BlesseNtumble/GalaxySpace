package galaxyspace.systems.ACentauriSystem.planets.proxima_b.blocks;

import java.util.Random;

import galaxyspace.GalaxySpace;
import galaxyspace.api.block.IEnergyGeyser;
import galaxyspace.systems.SolarSystem.moons.triton.blocks.TritonBlocks.EnumTritonBlocks;
import micdoodle8.mods.galacticraft.api.block.IDetectableResource;
import micdoodle8.mods.galacticraft.api.block.ITerraformableBlock;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.blocks.ISortableBlock;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryBlock;
import micdoodle8.mods.galacticraft.planets.venus.VenusBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Proxima_B_Blocks extends Block implements ISortableBlock, ITerraformableBlock, IDetectableResource, IEnergyGeyser{
	
	public static final PropertyEnum<EnumBlockProximaB> BASIC_TYPE = PropertyEnum.create("type", EnumBlockProximaB.class);
	
	public Proxima_B_Blocks()
    {
        super(Material.ROCK);
        this.setTranslationKey("proxima_b_blocks");
        this.setSoundType(SoundType.STONE); 
        this.setHarvestLevel("pickaxe", 2);
        this.setTickRandomly(false);
          
    }
	
	@Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state)
    {
		switch(state.getValue(BASIC_TYPE))
		{
			case MUSHROOM_GEYSER:
				world.scheduleBlockUpdate(pos, this, this.tickRate(world) + world.rand.nextInt(10), 0);
				break;
			default:
				break;
		}
			
    }
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
		if(!world.isAreaLoaded(pos, 1)) return;
		
		int lifetime = 1;
		if(rand.nextInt(20) == 0 && state == state.withProperty(BASIC_TYPE, EnumBlockProximaB.MUSHROOM_GEYSER))
		{
			if(world.isAirBlock(pos.up()) && world.getBlockState(pos.down()).getBlock() == VenusBlocks.sulphuricAcid) {
				GalaxySpace.proxy.spawnParticle("waterbubbles1", new Vector3(pos.getX() + rand.nextDouble(), pos.getY() + 1.0D + rand.nextDouble(), pos.getZ() + rand.nextDouble()), new Vector3(0.0D + ((rand.nextFloat() / 10) * (rand.nextBoolean() ? -1 : 1)), 0.01D, 0.0D + ((rand.nextFloat() / 20) * (rand.nextBoolean() ? -1 : 1))), new Object [] { 25 + rand.nextInt(5) + lifetime, 7, false, new Vector3(0.6F, 0.6F, 0.2F), 0.9D, 0.4D} );
			
			}
		}
		
		world.scheduleBlockUpdate(pos, this, 0, 0);
    }
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(state));
	}
	
	@SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (EnumBlockProximaB blockBasic : EnumBlockProximaB.values())
        {
            list.add(new ItemStack(this, 1, blockBasic.getMeta()));
        }
    }
	
	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
		EnumBlockProximaB type = ((EnumBlockProximaB) state.getValue(BASIC_TYPE));
        switch (type)
        {
        	case COAL_ORE:
        		return Items.COAL;
        	case SILICON_ORE:
        		return GCItems.basicItem;
        	case DIAMOND_ORE:
        		return Items.DIAMOND;
        	default:
        		return Item.getItemFromBlock(this);
        }
    }
	
	@Override
    public int damageDropped(IBlockState state)
    {
		EnumBlockProximaB type = ((EnumBlockProximaB) state.getValue(BASIC_TYPE));
		switch (type)
        {
        	default:
        		return getMetaFromState(state);
        }
    }
	
	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        Random rand = world instanceof World ? ((World)world).rand : RANDOM;

        int count = quantityDropped(state, fortune, rand);
        for (int i = 0; i < count; i++)
        {
            Item item = this.getItemDropped(state, rand, fortune);
            if (item != Items.AIR)
            {
            	EnumBlockProximaB type = ((EnumBlockProximaB) state.getValue(BASIC_TYPE));
            	
            	switch (type)
                {
        			case COAL_ORE:
        			case DIAMOND_ORE:
        				drops.add(new ItemStack(item, 1, 0));
        				break;
        			case SILICON_ORE: 
        				drops.add(new ItemStack(item, 1, 2));
        				break;
                	default:
                		drops.add(new ItemStack(item, 1, this.damageDropped(state)));
                		break;
                }
               
            }
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
		return false;
	}
	
	public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, net.minecraftforge.common.IPlantable plantable)
    {
		EnumBlockProximaB type = ((EnumBlockProximaB) state.getValue(BASIC_TYPE));
		switch (type)
        {
			case ICE_SURFACE: 
				return true;
			
        	default:
        		return true;
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public enum EnumBlockProximaB implements IStringSerializable
    {
		SURFACE(0, "proxima_b_surface"),
        SUBSURFACE(1, "proxima_b_subsurface"),
		STONE(2, "proxima_b_stone"),
		ICE_SURFACE(3, "proxima_b_ice_surface"),
		ASH_ROCK(4, "proxima_b_ash_rock"),
		GOLD_ORE(5, "proxima_b_gold_ore"),
		TIN_ORE(6, "proxima_b_tin_ore"),
		COPPER_ORE(7, "proxima_b_copper_ore"),
		COAL_ORE(8, "proxima_b_coal_ore"),
		SILICON_ORE(9, "proxima_b_silicon_ore"),
		DIAMOND_ORE(10, "proxima_b_diamond_ore"),
		ROCKY_SURFACE(11, "proxima_b_rocky_surface"),
		MUSHROOM_SKIN(12, "proxima_b_mushroom_skin"),
		MUSHROOM_GEYSER(13, "proxima_b_mushroom_geyser"),
		MUD(14, "proxima_b_mud");

        private final int meta;
        private final String name;

        private EnumBlockProximaB(int meta, String name)
        {
            this.meta = meta;
            this.name = name;
        }

        public int getMeta() { return this.meta; }       

        public static EnumBlockProximaB byMetadata(int meta) { return values()[meta]; }

        @Override
        public String getName() { return this.name; }
		
    }
	
	@Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockProximaB.byMetadata(meta));
    }
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumBlockProximaB) state.getValue(BASIC_TYPE)).getMeta();
	}	
	
	@Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, BASIC_TYPE);
    }

	@Override
	public boolean isWorkGeyser(World world, IBlockState state, BlockPos pos) {
		// TODO Auto-generated method stub
		return false;
	}
}

