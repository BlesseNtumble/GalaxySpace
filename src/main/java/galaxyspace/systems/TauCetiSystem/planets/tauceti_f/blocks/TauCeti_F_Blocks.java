package galaxyspace.systems.TauCetiSystem.planets.tauceti_f.blocks;

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

public class TauCeti_F_Blocks  extends Block implements ISortableBlock, ITerraformableBlock, IDetectableResource {

	public static final PropertyEnum<EnumBlockTauCetiF> BASIC_TYPE = PropertyEnum.create("type", EnumBlockTauCetiF.class);
	
	public TauCeti_F_Blocks()
    {
        super(Material.ROCK);
        this.setTranslationKey("tauceti_f_blocks");
        /*this.setHarvestLevel("pickaxe", 0, this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockTauCetiF.STONE));
        this.setHarvestLevel("pickaxe", 0, this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockTauCetiF.COBBLESTONE));
        this.setHarvestLevel("pickaxe", 0, this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockTauCetiF.STONE_BRICKS));
        //this.setHarvestLevel("shovel", 0, this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBarnardaC.DIRT));
        //this.setHarvestLevel("shovel", 0, this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBarnardaC.DIRT_1));
        this.setHarvestLevel("pickaxe", 0, this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockTauCetiF.SANDSTONE));
        this.setHarvestLevel("axe", 0, this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockTauCetiF.OAK_PLANKS));
        */
    }
		
	@Override
	public float getBlockHardness(IBlockState state, World world, BlockPos pos)
    {
		EnumBlockTauCetiF type = ((EnumBlockTauCetiF) state.getValue(BASIC_TYPE));
		switch (type)
        {
			case DIRT: 
			//case DIRT_1:
				return 0.2F;
			/*case SANDSTONE:
				return 1.0F;
			case STONE: 
			case COBBLESTONE:
			case STONE_BRICKS:
				return 1.0F;
			case OAK_PLANKS:
				return 1.5F;*/
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
        for (EnumBlockTauCetiF blockBasic : EnumBlockTauCetiF.values())
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
		EnumBlockTauCetiF type = ((EnumBlockTauCetiF) state.getValue(BASIC_TYPE));
    	
    	switch (type) {
    		//case STONE: return 4;
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
            	EnumBlockTauCetiF type = ((EnumBlockTauCetiF) state.getValue(BASIC_TYPE));
            	
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
	
	public enum EnumBlockTauCetiF implements IStringSerializable
	{		
		DIRT(0, "tauceti_f_dirt", Material.GROUND, SoundType.GROUND),
		GRASS(1, "tauceti_f_grass", Material.GRASS, SoundType.GROUND),
		STONE(2, "tauceti_f_stone", Material.ROCK, SoundType.STONE);
		
		private final int meta;
		private final String name;
		private final Material material;
		private final SoundType sound;
		
		private EnumBlockTauCetiF(int meta, String name, Material material, SoundType sound)
		{
			this.meta = meta;
			this.name = name;
			this.material = material;
			this.sound = sound;
		}
	
		public int getMeta() { return this.meta; }   
		
		public Material getMaterial() { return this.material; } 
		public SoundType getSoundType() {return this.sound; }
		
		public static EnumBlockTauCetiF byMetadata(int meta) { return values()[meta]; }
	
		@Override
		public String getName() { return this.name; }
		
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockTauCetiF.byMetadata(meta));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumBlockTauCetiF) state.getValue(BASIC_TYPE)).getMeta();
	}	
	
	@Override
	public Material getMaterial(IBlockState state) {
		return ((EnumBlockTauCetiF) state.getValue(BASIC_TYPE)).getMaterial();
	}
	
	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {		
		return ((EnumBlockTauCetiF) state.getValue(BASIC_TYPE)).getMaterial().getMaterialMapColor();
    }
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, BASIC_TYPE);
	}
	
	@Override
	public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
		return ((EnumBlockTauCetiF) state.getValue(BASIC_TYPE)).getSoundType();
    }
	
}
