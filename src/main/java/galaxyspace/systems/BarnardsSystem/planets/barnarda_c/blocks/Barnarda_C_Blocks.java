package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks;

import java.util.Random;

import javax.annotation.Nullable;

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
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
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
        this.setTranslationKey("barnarda_c_blocks");
        this.setHarvestLevel("pickaxe", 0, this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBarnardaC.STONE));
        this.setHarvestLevel("pickaxe", 0, this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBarnardaC.COBBLESTONE));
        this.setHarvestLevel("pickaxe", 0, this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBarnardaC.STONE_BRICKS));
        this.setHarvestLevel("shovel", 0, this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBarnardaC.DIRT));
        this.setHarvestLevel("shovel", 0, this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBarnardaC.DIRT_1));
        this.setHarvestLevel("pickaxe", 0, this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBarnardaC.SANDSTONE));
        this.setHarvestLevel("axe", 0, this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBarnardaC.OAK_PLANKS));
        
    }


	@Override
	public float getBlockHardness(IBlockState state, World world, BlockPos pos)
    {
		EnumBlockBarnardaC type = ((EnumBlockBarnardaC) state.getValue(BASIC_TYPE));
		switch (type)
        {
			case DIRT: 
			case DIRT_1:
				return 0.5F;
			case SANDSTONE:
				return 1.0F;
			case STONE: 
			case COBBLESTONE:
			case STONE_BRICKS:
				return 1.0F;
			case OAK_PLANKS:
				return 1.5F;
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
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(this);
	}
	
	@Override
	public int damageDropped(IBlockState state)
	{
		EnumBlockBarnardaC type = ((EnumBlockBarnardaC) state.getValue(BASIC_TYPE));
    	
    	switch (type) {
    		case STONE: return 4;
			default: return this.getMetaFromState(state);
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
	
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public enum EnumBlockBarnardaC implements IStringSerializable
	{		
		DIRT(0, "barnarda_c_dirt", Material.GROUND, SoundType.GROUND),
		STONE(1, "barnarda_c_stone", Material.ROCK, SoundType.STONE),
		SANDSTONE(2, "barnarda_c_sandstone", Material.ROCK, SoundType.STONE),
		DIRT_1(3, "barnarda_c_dirt_1", Material.GROUND, SoundType.GROUND),
		COBBLESTONE(4, "barnarda_c_cobblestone", Material.ROCK, SoundType.STONE),
		STONE_BRICKS(5, "barnarda_c_stone_bricks", Material.ROCK, SoundType.STONE),
		OAK_PLANKS(6, "barnarda_c_oak_planks", Material.WOOD, SoundType.WOOD);
		
		private final int meta;
		private final String name;
		private final Material material;
		private final SoundType sound;
		
		private EnumBlockBarnardaC(int meta, String name, Material material, SoundType sound)
		{
			this.meta = meta;
			this.name = name;
			this.material = material;
			this.sound = sound;
		}
	
		public int getMeta() { return this.meta; }   
		
		public Material getMaterial() { return this.material; } 
		public SoundType getSoundType() {return this.sound; }
		
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
	public Material getMaterial(IBlockState state) {
		return ((EnumBlockBarnardaC) state.getValue(BASIC_TYPE)).getMaterial();
	}
	
	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {		
		return ((EnumBlockBarnardaC) state.getValue(BASIC_TYPE)).getMaterial().getMaterialMapColor();
    }
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, BASIC_TYPE);
	}
	
	@Override
	public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
		return ((EnumBlockBarnardaC) state.getValue(BASIC_TYPE)).getSoundType();
    }
	
}
