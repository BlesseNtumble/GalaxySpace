/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  micdoodle8.mods.galacticraft.api.item.IItemElectric
 *  micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase
 *  micdoodle8.mods.galacticraft.core.inventory.SlotSpecific
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.inventory.Container
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.inventory.Slot
 *  net.minecraft.inventory.SlotFurnace
 *  net.minecraft.item.ItemStack
 */
package galaxyspace.systems.SolarSystem.planets.overworld.inventory;

import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityAssemblyMachine;
import micdoodle8.mods.galacticraft.api.item.IItemElectric;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.inventory.SlotSpecific;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

public class ContainerAssemblyMachine
extends Container {
    private TileEntityAssemblyMachine tileEntity;

    public ContainerAssemblyMachine(InventoryPlayer par1InventoryPlayer, TileEntityAssemblyMachine tileEntity) {
        int var3;
        this.tileEntity = tileEntity;
        tileEntity.testCraftMatrix.eventHandler = this;
        for (int x = 0; x < 3; ++x) {
            for (int y = 0; y < 3; ++y) {
                this.addSlotToContainer(new Slot((IInventory)tileEntity.testCraftMatrix, y + x * 3, 19 + y * 18, 18 + x * 18));
            }
        }
        this.addSlotToContainer((Slot)new SlotSpecific((IInventory)tileEntity, 0, 77, 93, new Class[]{ItemElectricBase.class}));
        this.addSlotToContainer((Slot)new SlotFurnace(par1InventoryPlayer.player, (IInventory)tileEntity, 1, 138, 36));
        for (var3 = 0; var3 < 3; ++var3) {
            for (int var4 = 0; var4 < 9; ++var4) {
                this.addSlotToContainer(new Slot((IInventory)par1InventoryPlayer, var4 + var3 * 9 + 9, 8 + var4 * 18, 117 + var3 * 18));
            }
        }
        for (var3 = 0; var3 < 9; ++var3) {
            this.addSlotToContainer(new Slot((IInventory)par1InventoryPlayer, var3, 8 + var3 * 18, 175));
        }
    }

    public void onContainerClosed(EntityPlayer entityplayer) {
        super.onContainerClosed(entityplayer);
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
        return this.tileEntity.isUseableByPlayer(par1EntityPlayer);
    }

    public void onCraftMatrixChanged(IInventory par1IInventory) {
        super.onCraftMatrixChanged(par1IInventory);
        this.tileEntity.updateInput();
    }

    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par1) {
        ItemStack var2 = null;
        Slot var3 = (Slot)this.inventorySlots.get(par1);
        if (var3 != null && var3.getHasStack()) {
            ItemStack var4 = var3.getStack();
            var2 = var4.copy();
            if (par1 <= 10 ? !this.mergeItemStack(var4, 11, 47, true) : (var4.getItem() instanceof IItemElectric ? !this.mergeItemStack(var4, 9, 10, false) : (par1 < 39 ? !this.mergeItemStack(var4, 0, 9, false) && !this.mergeItemStack(var4, 39, 48, false) : !this.mergeItemStack(var4, 0, 9, false) && !this.mergeItemStack(var4, 12, 39, false)))) {
                return null;
            }
            if (var4.stackSize == 0) {
                var3.putStack((ItemStack)null);
            } else {
                var3.onSlotChanged();
            }
            if (var4.stackSize == var2.stackSize) {
                return null;
            }
            var3.onPickupFromSlot(par1EntityPlayer, var4);
        }
        return var2;
    }
}

