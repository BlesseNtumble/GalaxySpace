/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  micdoodle8.mods.galacticraft.core.GalacticraftCore
 *  micdoodle8.mods.galacticraft.core.blocks.BlockTileGC
 *  micdoodle8.mods.galacticraft.core.blocks.GCBlocks
 *  micdoodle8.mods.galacticraft.core.items.GCItems
 *  micdoodle8.mods.galacticraft.core.items.ItemCanisterGeneric
 *  micdoodle8.mods.galacticraft.core.util.GCCoreUtil
 *  micdoodle8.mods.galacticraft.planets.asteroids.items.AsteroidsItems
 *  net.minecraft.block.Block
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.ChatComponentText
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraft.util.IChatComponent
 *  net.minecraft.util.IIcon
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraftforge.common.util.ForgeDirection
 *  net.minecraftforge.fluids.FluidContainerRegistry
 *  net.minecraftforge.fluids.FluidStack
 */
package galaxyspace.systems.SolarSystem.planets.overworld.blocks.mashines;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.registers.items.GSItemBlockDesc;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.util.GSCreativeTabs;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityFluidTank;
import java.util.List;
import java.util.Random;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.blocks.BlockTileGC;
import micdoodle8.mods.galacticraft.core.blocks.GCBlocks;
import micdoodle8.mods.galacticraft.core.items.GCItems;
import micdoodle8.mods.galacticraft.core.items.ItemCanisterGeneric;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.planets.asteroids.items.AsteroidsItems;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class BlockFluidTank
extends BlockTileGC
implements GSItemBlockDesc.IBlockShiftDesc {
    public static final int TANK_METADATA = 0;
    private IIcon iconMachineSide;

    public BlockFluidTank(String assetName) {
        super(GCBlocks.machine);
        this.setHardness(1.0f);
        this.setStepSound(Block.soundTypeMetal);
        this.setBlockTextureName(GalacticraftCore.TEXTURE_PREFIX + "machine");
        this.setBlockName(assetName);
    }

    public void breakBlock(World world, int x, int y, int z, Block block, int var6) {
        TileEntityFluidTank tile = (TileEntityFluidTank)world.getTileEntity(x, y, z);
        if (tile != null) {
            // empty if block
        }
        super.breakBlock(world, x, y, z, block, var6);
    }

    public CreativeTabs getCreativeTabToDisplayOn() {
        return GSCreativeTabs.GSBlocksTab;
    }

    public int getRenderType() {
        return GalaxySpace.proxy.getBlockRender((Block)this);
    }

    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "machine");
        this.iconMachineSide = iconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "machine_side");
    }

    public void randomDisplayTick(World par1World, int x, int y, int z, Random par5Random) {
        TileEntity tile = par1World.getTileEntity(x, y, z);
    }

    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        return this.getIcon(side, world.getBlockMetadata(x, y, z));
    }

    @SideOnly(value=Side.CLIENT)
    public IIcon getIcon(int side, int metadata) {
        return this.blockIcon;
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
        world.setBlockMetadataWithNotify(x, y, z, 0, 3);
    }

    public boolean onUseWrench(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer, int side, float hitX, float hitY, float hitZ) {
        int metadata = par1World.getBlockMetadata(x, y, z);
        int original = metadata & 1;
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
        par1World.setBlockMetadataWithNotify(x, y, z, (metadata & 0xC) + change - 2, 3);
        return true;
    }

    public boolean onMachineActivated(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer, int side, float hitX, float hitY, float hitZ) {
        int metadata = par1World.getBlockMetadata(x, y, z);
        if (!par1World.isRemote) {
            ItemStack current = par5EntityPlayer.inventory.getCurrentItem();
            TileEntityFluidTank tile = (TileEntityFluidTank)par1World.getTileEntity(x, y, z);
            if (current != null) {
                if (FluidContainerRegistry.isContainer((ItemStack)current)) {
                    FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem((ItemStack)current);
                    if (liquid != null) {
                        int qty = tile.fill(par1World.getBlockMetadata(x, y, z) == 1 ? ForgeDirection.DOWN : ForgeDirection.UP, liquid, true);
                        if (qty != 0 && !par5EntityPlayer.capabilities.isCreativeMode) {
                            if (current.stackSize > 1) {
                                if (!par5EntityPlayer.inventory.addItemStackToInventory(FluidContainerRegistry.drainFluidContainer((ItemStack)current))) {
                                    par5EntityPlayer.dropPlayerItemWithRandomChoice(FluidContainerRegistry.drainFluidContainer((ItemStack)current), false);
                                }
                                --current.stackSize;
                                par5EntityPlayer.inventoryContainer.detectAndSendChanges();
                            } else {
                                par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, FluidContainerRegistry.drainFluidContainer((ItemStack)current));
                            }
                        }
                    } else if (tile.waterTank.getFluid() != null && !par5EntityPlayer.capabilities.isCreativeMode) {
                        if (current.stackSize > 1) {
                            if (!par5EntityPlayer.inventory.addItemStackToInventory(FluidContainerRegistry.fillFluidContainer((FluidStack)tile.waterTank.getFluid(), (ItemStack)current))) {
                                par5EntityPlayer.dropPlayerItemWithRandomChoice(FluidContainerRegistry.fillFluidContainer((FluidStack)tile.waterTank.getFluid(), (ItemStack)current), false);
                            }
                            --current.stackSize;
                            par5EntityPlayer.inventoryContainer.detectAndSendChanges();
                        } else if (current.getItem() instanceof ItemCanisterGeneric) {
                            if (tile.waterTank.getFluidAmount() >= 1000) {
                                switch (tile.waterTank.getFluid().getFluid().getName()) {
                                    case "oil": 
                                    case "gcoil": {
                                        par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, new ItemStack(GCItems.oilCanister, 1, 1));
                                        break;
                                    }
                                    case "fuel": 
                                    case "gcfuel": {
                                        par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, new ItemStack(GCItems.fuelCanister, 1, 1));
                                        break;
                                    }
                                    case "liquidoxygen": 
                                    case "oxygen": {
                                        par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, new ItemStack(AsteroidsItems.canisterLOX, 1, 1));
                                        break;
                                    }
                                    case "liquidnitrogen": 
                                    case "nitrogen": {
                                        par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, new ItemStack(AsteroidsItems.canisterLN2, 1, 1));
                                        break;
                                    }
                                    case "liquidmethane": 
                                    case "methane": {
                                        par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, new ItemStack(AsteroidsItems.methaneCanister, 1, 1));
                                        break;
                                    }
                                    case "helium": {
                                        par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, new ItemStack(GSItems.Helium3Canister, 1, 1));
                                        break;
                                    }
                                    case "hydrogen": {
                                        par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, new ItemStack(GSItems.HydrogenCanister, 1, 1));
                                    }
                                }
                            }
                        } else {
                            par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, FluidContainerRegistry.fillFluidContainer((FluidStack)tile.waterTank.getFluid(), (ItemStack)current));
                        }
                        tile.drain(tile.blockMetadata == 1 ? ForgeDirection.UP : ForgeDirection.DOWN, 1000, true);
                    }
                }
            } else if (tile.waterTank.getFluid() != null) {
                par5EntityPlayer.addChatComponentMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.DARK_GREEN + tile.waterTank.getFluid().getLocalizedName() + ": " + tile.waterTank.getFluidAmount() + "/" + tile.waterTank.getCapacity()));
            } else {
                par5EntityPlayer.addChatComponentMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.DARK_GREEN + "Empty"));
            }
            return true;
        }
        return true;
    }

    public TileEntity createTileEntity(World world, int metadata) {
        if ((metadata &= 0xC) == 0) {
            return new TileEntityFluidTank();
        }
        return null;
    }

    public ItemStack getGenerator() {
        return new ItemStack((Block)this, 1, 0);
    }

    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        par3List.add(this.getGenerator());
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
        return GCCoreUtil.translate((String)"tile.FluidTank.desc");
    }

    @Override
    public boolean showDescription(int meta) {
        return true;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public int getRenderBlockPass() {
        return 1;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }
}

