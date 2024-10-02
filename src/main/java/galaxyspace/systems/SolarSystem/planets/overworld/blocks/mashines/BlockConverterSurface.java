/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  micdoodle8.mods.galacticraft.core.GalacticraftCore
 *  micdoodle8.mods.galacticraft.core.blocks.BlockTileGC
 *  micdoodle8.mods.galacticraft.core.blocks.GCBlocks
 *  micdoodle8.mods.galacticraft.core.tile.IMultiBlock
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
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraftforge.common.util.ForgeDirection
 */
package galaxyspace.systems.SolarSystem.planets.overworld.blocks.mashines;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.registers.items.GSItemBlockDesc;
import galaxyspace.core.util.GSCreativeTabs;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityConverterSurface;
import java.util.List;
import java.util.Random;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.blocks.BlockTileGC;
import micdoodle8.mods.galacticraft.core.blocks.GCBlocks;
import micdoodle8.mods.galacticraft.core.tile.IMultiBlock;
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
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockConverterSurface
extends BlockTileGC
implements GSItemBlockDesc.IBlockShiftDesc {
    public static final int CONVERTER_SURFACE_METADATA = 0;
    private IIcon iconMachineSide;
    private IIcon iconInput;
    private IIcon iconFuelInput;
    private IIcon[] iconConverterSurface = new IIcon[1];

    public BlockConverterSurface(String assetName) {
        super(GCBlocks.machine);
        this.setHardness(1.0f);
        this.setStepSound(Block.soundTypeMetal);
        this.setBlockTextureName(GalacticraftCore.TEXTURE_PREFIX + assetName);
        this.setBlockName(assetName);
    }

    public void breakBlock(World var1, int var2, int var3, int var4, Block var5, int var6) {
        TileEntity var9 = var1.getTileEntity(var2, var3, var4);
        if (var9 instanceof IMultiBlock) {
            ((IMultiBlock)var9).onDestroy(var9);
        }
        super.breakBlock(var1, var2, var3, var4, var5, var6);
    }

    public CreativeTabs getCreativeTabToDisplayOn() {
        return GSCreativeTabs.GSBlocksTab;
    }

    public int getRenderType() {
        return GalaxySpace.proxy.getBlockRender((Block)this);
    }

    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "machine");
        this.iconInput = iconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "machine_input");
        this.iconMachineSide = iconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "machine_side");
        this.iconFuelInput = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":overworld/machine_water_input");
        this.iconConverterSurface[0] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":overworld/converter_surface");
    }

    public void randomDisplayTick(World par1World, int x, int y, int z, Random par5Random) {
        TileEntity tile = par1World.getTileEntity(x, y, z);
    }

    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        int metadata = world.getBlockMetadata(x, y, z);
        int type = metadata & 4;
        int metaside = (metadata & 3) + 2;
        TileEntity tile = world.getTileEntity(x, y, z);
        if (metadata == 0 && side == 4 || metadata == 1 && side == 5 || metadata == 2 && side == 3 || metadata == 3 && side == 2) {
            return this.iconConverterSurface[0];
        }
        return this.getIcon(side, world.getBlockMetadata(x, y, z));
    }

    @SideOnly(value=Side.CLIENT)
    public IIcon getIcon(int side, int metadata) {
        if (side == 0 || side == 1) {
            return this.blockIcon;
        }
        if (metadata >= 0) {
            if (side == metadata + 2) {
                return this.iconInput;
            }
            if (side == ForgeDirection.getOrientation((int)(metadata + 2)).getOpposite().ordinal()) {
                return this.iconFuelInput;
            }
            if (metadata == 0 && side == 4 || metadata == 1 && side == 5 || metadata == 2 && side == 3 || metadata == 3 && side == 2) {
                return this.iconConverterSurface[0];
            }
        }
        return this.iconMachineSide;
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack) {
        int metadata = world.getBlockMetadata(x, y, z);
        int angle = MathHelper.floor_double((double)((double)(entityLiving.rotationYaw * 4.0f / 360.0f) + 0.5)) & 3;
        int change = 0;
        switch (angle) {
            case 0: {
                change = 3;
                break;
            }
            case 1: {
                change = 1;
                break;
            }
            case 2: {
                change = 2;
                break;
            }
            case 3: {
                change = 0;
            }
        }
        world.setBlockMetadataWithNotify(x, y, z, (metadata & 0xC) + change, 3);
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
        par1World.setBlockMetadataWithNotify(x, y, z, (metadata & 0xC) + change, 3);
        return true;
    }

    public boolean onMachineActivated(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer, int side, float hitX, float hitY, float hitZ) {
        int metadata = par1World.getBlockMetadata(x, y, z);
        if (!par1World.isRemote) {
            par5EntityPlayer.openGui((Object)GalaxySpace.instance, -1, par1World, x, y, z);
            return true;
        }
        return true;
    }

    public TileEntity createTileEntity(World world, int metadata) {
        if ((metadata &= 0xC) == 0) {
            return new TileEntityConverterSurface();
        }
        return null;
    }

    public ItemStack getFuelGenerator() {
        return new ItemStack((Block)this, 1, 0);
    }

    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        par3List.add(this.getFuelGenerator());
    }

    public int damageDropped(int metadata) {
        return metadata & 0xC;
    }

    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
        int metadata = this.getDamageValue(world, x, y, z);
        return new ItemStack((Block)this, 1, metadata);
    }

    @Override
    public String getDescription(int meta) {
        return null;
    }

    @Override
    public String getShiftDescription(int meta) {
        return GCCoreUtil.translate((String)"tile.ConverterSurface.desc");
    }

    @Override
    public boolean showDescription(int meta) {
        return true;
    }
}

