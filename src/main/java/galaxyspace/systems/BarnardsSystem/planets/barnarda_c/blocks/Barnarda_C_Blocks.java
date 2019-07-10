package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks;

import java.util.Random;

import micdoodle8.mods.galacticraft.api.block.IDetectableResource;
import micdoodle8.mods.galacticraft.api.block.ITerraformableBlock;
import micdoodle8.mods.galacticraft.core.blocks.ISortableBlock;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Barnarda_C_Blocks  extends Block implements ISortableBlock, ITerraformableBlock, IDetectableResource{

	public static final PropertyEnum<EnumBlockBarnardaC> BASIC_TYPE = PropertyEnum.create("type", EnumBlockBarnardaC.class);
	
	public Barnarda_C_Blocks()
    {
        super(Material.ROCK);
        this.setUnlocalizedName("barnarda_c_blocks");
        this.setSoundType(SoundType.STONE); 
        this.setHarvestLevel("pickaxe", 0, this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBarnardaC.STONE));
        this.setHarvestLevel("pickaxe", 0, this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBarnardaC.COBBLESTONE));
        this.setHarvestLevel("pickaxe", 0, this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBarnardaC.STONE_BRICKS));
        //this.setHarvestLevel("spade", 1, this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBarnardaC.DIRT));
        //this.setHarvestLevel("spade", 1, this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBarnardaC.DIRT_1));
        //this.setHarvestLevel("axe", 1, this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBarnardaC.OAK_PLANKS));
    }

	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {		
		EnumBlockBarnardaC type = ((EnumBlockBarnardaC) state.getValue(BASIC_TYPE));
		
		switch (type)
        {
			case DIRT: return Material.GRASS.getMaterialMapColor();
			case SAND: return Material.SAND.getMaterialMapColor();
			default:
				return Material.ROCK.getMaterialMapColor();
		
        }
    }
	
	@Override
	public float getBlockHardness(IBlockState state, World world, BlockPos pos)
    {
		EnumBlockBarnardaC type = ((EnumBlockBarnardaC) state.getValue(BASIC_TYPE));
		switch (type)
        {
			case DIRT: 
			case DIRT_1:
				return 0.6F;
			case STONE: 
			case COBBLESTONE:
			case STONE_BRICKS:
				return 1.0F;
			case OAK_PLANKS:
				return 0.5F;
			default: return this.blockHardness;
        }
        
    }
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(state));
	}
	
	@SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (EnumBlockBarnardaC blockBasic : EnumBlockBarnardaC.values())
        {
            list.add(new ItemStack(this, 1, blockBasic.getMeta()));
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
            	EnumBlockBarnardaC type = ((EnumBlockBarnardaC) state.getValue(BASIC_TYPE));
            	
            	switch (type)
                {        			
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
		EnumBlockBarnardaC type = ((EnumBlockBarnardaC) state.getValue(BASIC_TYPE));
		switch (type)
        {			
        	default:
        		return false;
        }
    }
    
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public enum EnumBlockBarnardaC implements IStringSerializable
	{		
		DIRT(0, "barnarda_c_dirt"),
		STONE(1, "barnarda_c_stone"),
		SAND(2, "barnarda_c_sand"),
		DIRT_1(3, "barnarda_c_dirt_1"),
		COBBLESTONE(4, "barnarda_c_cobblestone"),
		STONE_BRICKS(5, "barnarda_c_stone_bricks"),
		OAK_PLANKS(6, "barnarda_c_oak_planks");
		
		private final int meta;
		private final String name;
		
		private EnumBlockBarnardaC(int meta, String name)
		{
			this.meta = meta;
			this.name = name;
		}
	
		public int getMeta() { return this.meta; }       
		
		public static EnumBlockBarnardaC byMetadata(int meta) { return values()[meta]; }
	
		@Override
		public String getName() { return this.name; }
		
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBarnardaC.byMetadata(meta));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumBlockBarnardaC) state.getValue(BASIC_TYPE)).getMeta();
	}	
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, BASIC_TYPE);
	}
	
}
