/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  micdoodle8.mods.galacticraft.core.GalacticraftCore
 *  micdoodle8.mods.galacticraft.core.blocks.BlockTileGC
 *  micdoodle8.mods.galacticraft.core.blocks.GCBlocks
 *  micdoodle8.mods.galacticraft.core.energy.tile.TileBaseUniversalElectrical
 *  micdoodle8.mods.galacticraft.core.util.GCCoreUtil
 *  net.minecraft.block.Block
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.IIcon
 *  net.minecraft.util.MathHelper
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
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityHydroponicBase;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.blocks.BlockTileGC;
import micdoodle8.mods.galacticraft.core.blocks.GCBlocks;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseUniversalElectrical;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockHydroponicBase
extends BlockTileGC
implements GSItemBlockDesc.IBlockShiftDesc {
    private IIcon[] icons = new IIcon[6];

    public BlockHydroponicBase(String assetName) {
        super(GCBlocks.machine);
        this.setHardness(1.0f);
        this.setStepSound(Block.soundTypeMetal);
        this.setBlockTextureName(GalacticraftCore.TEXTURE_PREFIX + "machine");
        this.setBlockName(assetName);
    }

    public CreativeTabs getCreativeTabToDisplayOn() {
        return GSCreativeTabs.GSBlocksTab;
    }

    public int getRenderType() {
        return GalaxySpace.proxy.getBlockRender((Block)this);
    }

    public void registerBlockIcons(IIconRegister iconRegister) {
        this.icons[0] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":overworld/machine_water_input");
        this.icons[1] = iconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "machine_item_input");
        this.icons[2] = iconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "machine_item_output");
        this.icons[3] = iconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "machine_input");
        this.icons[4] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":overworld/hydroponic_base");
        this.blockIcon = iconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "machine");
    }

    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        return this.getIcon(side, world.getBlockMetadata(x, y, z));
    }

    @SideOnly(value=Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (side == 1) {
            return this.blockIcon;
        }
        if (side == 0) {
            return this.icons[3];
        }
        if (side == meta + 2) {
            return this.icons[2];
        }
        if (side == ForgeDirection.getOrientation((int)(meta + 2)).getOpposite().ordinal()) {
            return this.icons[1];
        }
        if (meta == 0 && side == 4 || meta == 1 && side == 5 || meta == 2 && side == 3 || meta == 3 && side == 2) {
            return this.icons[4];
        }
        return this.icons[0];
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack) {
        int metadata = itemStack.getItemDamage();
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
        world.setBlockMetadataWithNotify(x, y, z, 0 + change, 3);
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
        TileEntity te = par1World.getTileEntity(x, y, z);
        if (te instanceof TileBaseUniversalElectrical) {
            ((TileBaseUniversalElectrical)te).updateFacing();
        }
        par1World.setBlockMetadataWithNotify(x, y, z, (metadata & 0xC) + change, 3);
        return true;
    }

    public boolean onMachineActivated(World world, int x, int y, int z, EntityPlayer par5EntityPlayer, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote && world.getBlockMetadata(x, y, z) < 4) {
            par5EntityPlayer.openGui((Object)GalaxySpace.instance, -1, world, x, y, z);
            return true;
        }
        return true;
    }

    public TileEntity createTileEntity(World world, int metadata) {
        return new TileEntityHydroponicBase();
    }

    @Override
    public String getShiftDescription(int meta) {
        return GCCoreUtil.translate((String)"tile.HydroponicBase.desc");
    }

    @Override
    public boolean showDescription(int meta) {
        return true;
    }

    @Override
    public String getDescription(int meta) {
        return null;
    }
}

