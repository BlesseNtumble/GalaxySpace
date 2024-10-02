/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  micdoodle8.mods.galacticraft.core.GalacticraftCore
 *  micdoodle8.mods.galacticraft.core.blocks.BlockTileGC
 *  micdoodle8.mods.galacticraft.core.blocks.GCBlocks
 *  micdoodle8.mods.galacticraft.core.util.GCCoreUtil
 *  net.minecraft.block.Block
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.IIcon
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package galaxyspace.systems.SolarSystem.planets.overworld.blocks.mashines;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.registers.items.GSItemBlockDesc;
import galaxyspace.core.util.GSCreativeTabs;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityHydroponicBase;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityHydroponicFarm;
import java.util.List;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.blocks.BlockTileGC;
import micdoodle8.mods.galacticraft.core.blocks.GCBlocks;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockHydroponicFarm
extends BlockTileGC
implements GSItemBlockDesc.IBlockShiftDesc {
    public static final int LAND_METADATA = 0;
    public static final int WATER_METADATA = 1;
    private IIcon[] icons = new IIcon[6];

    public BlockHydroponicFarm(String assetName) {
        super(GCBlocks.machine);
        this.setHardness(1.0f);
        this.setStepSound(Block.soundTypeMetal);
        this.setBlockTextureName(GalacticraftCore.TEXTURE_PREFIX + "machine");
        this.setBlockName(assetName);
    }

    @SideOnly(value=Side.CLIENT)
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        par3List.add(new ItemStack(par1, 1, 0));
    }

    public void registerBlockIcons(IIconRegister par1IconRegister) {
        this.icons[0] = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":overworld/machine");
        this.blockIcon = this.icons[0];
    }

    public CreativeTabs getCreativeTabToDisplayOn() {
        return GSCreativeTabs.GSBlocksTab;
    }

    public int damageDropped(int metadata) {
        return metadata;
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack is) {
        world.setBlockMetadataWithNotify(x, y, z, is.getItemDamage(), 3);
    }

    public int getRenderType() {
        return GalaxySpace.proxy.getBlockRender((Block)this);
    }

    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        return this.getIcon(side, world.getBlockMetadata(x, y, z));
    }

    @SideOnly(value=Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return this.blockIcon;
    }

    public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side) {
        return this.isValidPosition(world, x, y, z, -1);
    }

    public boolean isValidPosition(World world, int x, int y, int z, int metadata) {
        return world.getBlock(x, y - 3, z) != this;
    }

    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileEntityHydroponicBase) {
            ((TileEntityHydroponicBase)tile).checkBlock(world, x, y, z);
        }
        super.breakBlock(world, x, y, z, block, meta);
    }

    public boolean onUseWrench(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer, int side, float hitX, float hitY, float hitZ) {
        int metadata = par1World.getBlockMetadata(x, y, z);
        int original = metadata & 3;
        int change = 0;
        switch (original) {
            case 0: {
                change = 3;
                break;
            }
            case 3: {
                change = 1;
                break;
            }
            case 1: {
                change = 2;
                break;
            }
            case 2: {
                change = 0;
            }
        }
        return true;
    }

    public boolean onMachineActivated(World world, int x, int y, int z, EntityPlayer par5EntityPlayer, int side, float hitX, float hitY, float hitZ) {
        return true;
    }

    public TileEntity createTileEntity(World world, int metadata) {
        return new TileEntityHydroponicFarm(0);
    }

    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
        int metadata = this.getDamageValue(world, x, y, z);
        return new ItemStack((Block)this, 1, metadata);
    }

    @Override
    public String getShiftDescription(int meta) {
        return GCCoreUtil.translate((String)"tile.HydroponicFarm.desc");
    }

    @Override
    public boolean showDescription(int meta) {
        return true;
    }

    public boolean renderAsNormalBlock() {
        return true;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public int getRenderBlockPass() {
        return 0;
    }

    @Override
    public String getDescription(int meta) {
        return null;
    }
}

