/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.inventory.Container
 *  net.minecraft.inventory.ICrafting
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.inventory.Slot
 *  net.minecraft.inventory.SlotFurnace
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.World
 */
package galaxyspace.systems.SolarSystem.moons.moon.inventory;

import galaxyspace.systems.SolarSystem.moons.moon.inventory.InventoryAlienTrade;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerAlienTrade
extends Container {
    InventoryAlienTrade trader;
    InventoryPlayer playerInv;
    World world;

    public ContainerAlienTrade(InventoryPlayer par1InventoryPlayer) {
        int var3;
        this.world = par1InventoryPlayer.player.getEntityWorld();
        this.playerInv = par1InventoryPlayer;
        this.trader = new InventoryAlienTrade(par1InventoryPlayer.player, this);
        this.addSlotToContainer(new Slot((IInventory)this.trader, 0, 30, 45));
        this.addSlotToContainer(new Slot((IInventory)this.trader, 1, 52, 45));
        this.addSlotToContainer((Slot)new SlotFurnace(par1InventoryPlayer.player, (IInventory)this.trader, 2, 135, 45));
        for (var3 = 0; var3 < 3; ++var3) {
            for (int var4 = 0; var4 < 9; ++var4) {
                this.addSlotToContainer(new Slot((IInventory)par1InventoryPlayer, var4 + var3 * 9 + 9, 8 + var4 * 18, 127 + var3 * 18));
            }
        }
        for (var3 = 0; var3 < 9; ++var3) {
            this.addSlotToContainer(new Slot((IInventory)par1InventoryPlayer, var3, 8 + var3 * 18, 185));
        }
    }

    public void addCraftingToCrafters(ICrafting p_75132_1_) {
        super.addCraftingToCrafters(p_75132_1_);
    }

    public void detectAndSendChanges() {
        super.detectAndSendChanges();
    }

    public void onCraftMatrixChanged(IInventory p_75130_1_) {
        this.trader.resetRecipeAndSlots();
        super.onCraftMatrixChanged(p_75130_1_);
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
        return this.trader.isUseableByPlayer(par1EntityPlayer);
    }

    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);
        if (!this.world.isRemote) {
            ItemStack itemstack = this.trader.getStackInSlotOnClosing(0);
            if (itemstack != null) {
                player.dropPlayerItemWithRandomChoice(itemstack, false);
            }
            if ((itemstack = this.trader.getStackInSlotOnClosing(1)) != null) {
                player.dropPlayerItemWithRandomChoice(itemstack, false);
            }
            if ((itemstack = this.trader.getStackInSlotOnClosing(2)) != null) {
                if (itemstack.stackSize <= 0) {
                    itemstack.stackSize = 1;
                }
                player.dropPlayerItemWithRandomChoice(itemstack, false);
            }
        }
    }

    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_) {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(p_82846_2_);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (p_82846_2_ == 2) {
                if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
                    return null;
                }
                slot.onSlotChange(itemstack1, itemstack);
            } else if (p_82846_2_ != 0 && p_82846_2_ != 1 ? (p_82846_2_ >= 3 && p_82846_2_ < 30 ? !this.mergeItemStack(itemstack1, 30, 39, false) : p_82846_2_ >= 30 && p_82846_2_ < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)) : !this.mergeItemStack(itemstack1, 3, 39, false)) {
                return null;
            }
            if (itemstack1.stackSize == 0) {
                slot.putStack((ItemStack)null);
            } else {
                slot.onSlotChanged();
            }
            if (itemstack1.stackSize == itemstack.stackSize) {
                return null;
            }
            slot.onPickupFromSlot(p_82846_1_, itemstack1);
        }
        return itemstack;
    }
}

