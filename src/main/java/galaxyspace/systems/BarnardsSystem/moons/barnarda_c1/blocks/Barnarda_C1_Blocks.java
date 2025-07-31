package galaxyspace.systems.BarnardsSystem.moons.barnarda_c1.blocks;

import galaxyspace.systems.BarnardsSystem.core.BRItems;
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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class Barnarda_C1_Blocks extends Block implements ISortableBlock, ITerraformableBlock, IDetectableResource {

    public static final PropertyEnum<EnumBlockBarnardaC1> BASIC_TYPE = PropertyEnum.create("type", EnumBlockBarnardaC1.class);

    public Barnarda_C1_Blocks() {
        super(Material.ROCK);
        this.setTranslationKey("barnarda_c1_blocks");
        this.setHarvestLevel("pickaxe", 2,  this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBarnardaC1.ONYX_ORE));
        this.setHarvestLevel("pickaxe", 2,  this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBarnardaC1.IRON_ORE));
        this.setHarvestLevel("pickaxe", 2,  this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBarnardaC1.COPPER_ORE));
        this.setHarvestLevel("pickaxe", 2,  this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBarnardaC1.TIN_ORE));
    }

    @Override
    public float getBlockHardness(IBlockState state, World world, BlockPos pos)
    {
        EnumBlockBarnardaC1 type = state.getValue(BASIC_TYPE);
        switch (type)
        {
            default: return 1.0F;
        }

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(state));
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, net.minecraftforge.common.IPlantable plantable)
    {
        return super.canSustainPlant(state, world, pos, direction, plantable);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (EnumBlockBarnardaC1 blockBasic : EnumBlockBarnardaC1.values())
            list.add(new ItemStack(this, 1, blockBasic.getMeta()));

    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        EnumBlockBarnardaC1 type = state.getValue(BASIC_TYPE);

        switch (type) {
            case ONYX_ORE:
                return BRItems.BASIC;
            default:
                return Item.getItemFromBlock(this);
        }
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        EnumBlockBarnardaC1 type = state.getValue(BASIC_TYPE);

        switch (type) {
            case ONYX_ORE: return 3;
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
                EnumBlockBarnardaC1 type = state.getValue(BASIC_TYPE);

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

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    public enum EnumBlockBarnardaC1 implements IStringSerializable {

        SNOWY_SURFACE(0, "barnarda_c1_snowy_surface", Material.GROUND, SoundType.SNOW),
        SNOWY_SUBSURFACE(1, "barnarda_c1_snowy_subsurface", Material.GROUND, SoundType.SNOW),
        ICY_SUBSURFACE(2, "barnarda_c1_icy_subsurface", Material.ICE, SoundType.GLASS),
        STONE(3, "barnarda_c1_stone", Material.ROCK, SoundType.STONE),
        ONYX_ORE(4, "barnarda_c1_onyx_ore", Material.ROCK, SoundType.STONE),
        IRON_ORE(5, "barnarda_c1_iron_ore", Material.ROCK, SoundType.STONE),
        COPPER_ORE(6, "barnarda_c1_copper_ore", Material.ROCK, SoundType.STONE),
        TIN_ORE(7, "barnarda_c1_tin_ore", Material.ROCK, SoundType.STONE);

        private final int meta;
        private final String name;
        private final Material material;
        private final SoundType sound;

        private EnumBlockBarnardaC1(int meta, String name, Material material, SoundType sound)
        {
            this.meta = meta;
            this.name = name;
            this.material = material;
            this.sound = sound;
        }

        public int getMeta() { return this.meta; }

        public Material getMaterial() { return this.material; }
        public SoundType getSoundType() {return this.sound; }

        public static EnumBlockBarnardaC1 byMetadata(int meta) { return values()[meta]; }

        @Override
        public String getName() { return this.name; }
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBarnardaC1.byMetadata(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(BASIC_TYPE).getMeta();
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
        return new BlockStateContainer(this, BASIC_TYPE);
    }

    @Override
    public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
        return state.getValue(BASIC_TYPE).getSoundType();
    }
}
