/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  micdoodle8.mods.galacticraft.api.item.IItemElectric
 *  micdoodle8.mods.galacticraft.api.item.IItemOxygenSupply
 *  micdoodle8.mods.galacticraft.core.energy.tile.TileBaseElectricBlock
 *  micdoodle8.mods.galacticraft.core.inventory.SlotSpecific
 *  micdoodle8.mods.galacticraft.core.items.GCItems
 *  micdoodle8.mods.galacticraft.core.tile.TileEntityOxygenSealer
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.inventory.Container
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.inventory.Slot
 *  net.minecraft.item.ItemStack
 */
package galaxyspace.systems.SolarSystem.planets.overworld.inventory;

import micdoodle8.mods.galacticraft.api.item.IItemElectric;
import micdoodle8.mods.galacticraft.api.item.IItemOxygenSupply;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseElectricBlock;
import micdoodle8.mods.galacticraft.core.inventory.SlotSpecific;
import micdoodle8.mods.galacticraft.core.items.GCItems;
import micdoodle8.mods.galacticraft.core.tile.TileEntityOxygenSealer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAdvOxygenSealer
extends Container {
    private TileBaseElectricBlock tileEntity;

    public ContainerAdvOxygenSealer(InventoryPlayer par1InventoryPlayer, TileEntityOxygenSealer sealer) {
        int var6;
        this.tileEntity = sealer;
        this.addSlotToContainer((Slot)new SlotSpecific((IInventory)sealer, 0, 79, 106, new Class[]{IItemElectric.class}));
        this.addSlotToContainer((Slot)new SlotSpecific((IInventory)sealer, 1, 10, 27, new Class[]{IItemOxygenSupply.class}));
        this.addSlotToContainer((Slot)new SlotSpecific((IInventory)sealer, 2, 56, 27, new ItemStack[]{new ItemStack(GCItems.basicItem, 1, 20)}));
        for (var6 = 0; var6 < 3; ++var6) {
            for (int var7 = 0; var7 < 9; ++var7) {
                this.addSlotToContainer(new Slot((IInventory)par1InventoryPlayer, var7 + var6 * 9 + 9, 8 + var7 * 18, 132 + var6 * 18));
            }
        }
        for (var6 = 0; var6 < 9; ++var6) {
            this.addSlotToContainer(new Slot((IInventory)par1InventoryPlayer, var6, 8 + var6 * 18, 190));
        }
    }

    public boolean canInteractWith(EntityPlayer var1) {
        return this.tileEntity.isUseableByPlayer(var1);
    }

    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par1) {
        ItemStack var2 = null;
        Slot slot = (Slot)this.inventorySlots.get(par1);
        int b = this.inventorySlots.size();
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            var2 = stack.copy();
            if (par1 <= 2 ? !this.mergeItemStack(stack, b - 36, b, true) : (stack.getItem() instanceof IItemElectric ? !this.mergeItemStack(stack, 0, 1, false) : (stack.getItem() instanceof IItemOxygenSupply ? !this.mergeItemStack(stack, 1, 2, false) : (stack.getItem() == GCItems.basicItem && stack.getItemDamage() == 20 ? !this.mergeItemStack(stack, 2, 3, false) : (par1 < b - 9 ? !this.mergeItemStack(stack, b - 9, b, false) : !this.mergeItemStack(stack, b - 36, b - 9, false)))))) {
                return null;
            }
            if (stack.stackSize == 0) {
                slot.putStack((ItemStack)null);
            } else {
                slot.onSlotChanged();
            }
            if (stack.stackSize == var2.stackSize) {
                return null;
            }
            slot.onPickupFromSlot(par1EntityPlayer, stack);
        }
        return var2;
    }
}

