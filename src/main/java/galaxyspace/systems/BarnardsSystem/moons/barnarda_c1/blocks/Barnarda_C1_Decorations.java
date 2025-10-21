package galaxyspace.systems.BarnardsSystem.moons.barnarda_c1.blocks;


import galaxyspace.systems.BarnardsSystem.core.BRBlocks;
import micdoodle8.mods.galacticraft.core.blocks.ISortableBlock;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class Barnarda_C1_Decorations extends Block implements ISortableBlock {

    public static final PropertyEnum<EnumBlockBarnardaC1Decoration> BASIC_TYPE = PropertyEnum.create("type", EnumBlockBarnardaC1Decoration.class);
    public static final PropertyBool UP = PropertyBool.create("up");

    public Barnarda_C1_Decorations() {
        super(Material.ROCK);
        this.setTranslationKey("barnarda_c1_decorations");
        this.setDefaultState(this.blockState.getBaseState().withProperty(BASIC_TYPE, EnumBlockBarnardaC1Decoration.ICY_DRIPSTONE_BASE).withProperty(UP, false));
    }

    @Override
    public float getBlockHardness(IBlockState state, World world, BlockPos pos)
    {
        EnumBlockBarnardaC1Decoration type = state.getValue(BASIC_TYPE);
        switch (type)
        {
            default: return this.blockHardness;
        }

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
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(state) % 5);
    }

    @Override
    public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side)
    {
        return (world.getBlockState(pos.up()) == BRBlocks.BARNARDA_C1_BLOCKS.getDefaultState().withProperty(Barnarda_C1_Blocks.BASIC_TYPE, Barnarda_C1_Blocks.EnumBlockBarnardaC1.ICY_SUBSURFACE)
                || world.getBlockState(pos.down()) == BRBlocks.BARNARDA_C1_BLOCKS.getDefaultState().withProperty(Barnarda_C1_Blocks.BASIC_TYPE, Barnarda_C1_Blocks.EnumBlockBarnardaC1.ICY_SUBSURFACE)
                || world.getBlockState(pos.up()).getBlock() == this
                || world.getBlockState(pos.down()).getBlock() == this)
                && (side == EnumFacing.UP || side == EnumFacing.DOWN);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos)
    {
        if(state.getValue(UP)) {
            if(world.isAirBlock(pos.up()))
                world.destroyBlock(pos, false);
        }
        else {
            if(world.isAirBlock(pos.down()))
                world.destroyBlock(pos, false);
        }
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
    {
        IBlockState state = getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer);
        return facing != EnumFacing.DOWN && (facing == EnumFacing.UP || (double)hitY <= 0.5D) ? state.withProperty(UP, false) : state.withProperty(UP, true);
       // return getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (EnumBlockBarnardaC1Decoration blockBasic : EnumBlockBarnardaC1Decoration.values())
            list.add(new ItemStack(this, 1, blockBasic.getMeta()));

    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return null;
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player)
    {
       return true;
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        EnumBlockBarnardaC1Decoration type = state.getValue(BASIC_TYPE);

        switch (type) {
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
                EnumBlockBarnardaC1Decoration type = state.getValue(BASIC_TYPE);

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
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    public enum EnumBlockBarnardaC1Decoration implements IStringSerializable {

        ICY_DRIPSTONE_BASE(0, "barnarda_c1_dripstone_base", Material.ROCK, SoundType.STONE),
        ICY_DRIPSTONE_LARGE(1, "barnarda_c1_dripstone_large", Material.ROCK, SoundType.STONE),
        ICY_DRIPSTONE_MEDIUM(2, "barnarda_c1_dripstone_medium", Material.ROCK, SoundType.STONE),
        ICY_DRIPSTONE_SMALL(3, "barnarda_c1_dripstone_small", Material.ROCK, SoundType.STONE),
        ICY_DRIPSTONE_THIN(4, "barnarda_c1_dripstone_thin", Material.ROCK, SoundType.STONE);

        private final int meta;
        private final String name;
        private final Material material;
        private final SoundType sound;

        EnumBlockBarnardaC1Decoration(int meta, String name, Material material, SoundType sound)
        {
            this.meta = meta;
            this.name = name;
            this.material = material;
            this.sound = sound;
        }

        public int getMeta() { return this.meta; }

        public Material getMaterial() { return this.material; }
        public SoundType getSoundType() {return this.sound; }

        public static EnumBlockBarnardaC1Decoration byMetadata(int meta) { return values()[meta]; }

        @Override
        public String getName() { return this.name; }
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBarnardaC1Decoration.values()[meta % 5]).withProperty(UP, meta - 5 >= 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(BASIC_TYPE).getMeta() + (state.getValue(UP) ? 5 : 0);
    }

    @Override
    public Material getMaterial(IBlockState state) {
        return state.getValue(BASIC_TYPE).getMaterial();
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state.getValue(BASIC_TYPE).getMaterial().getMaterialMapColor();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, BASIC_TYPE, UP);
    }

    @Override
    public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
        return state.getValue(BASIC_TYPE).getSoundType();
    }
}
