/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.Container
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.item.ItemStack
 */
package galaxyspace.systems.SolarSystem.moons.moon.inventory;

import galaxyspace.systems.SolarSystem.moons.moon.recipe.AlienRecipes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryAlienTrade
implements IInventory {
    private ItemStack[] theInventory = new ItemStack[3];
    private final EntityPlayer thePlayer;
    private Container eventHandler;

    public InventoryAlienTrade(EntityPlayer player, Container cont) {
        this.thePlayer = player;
        this.eventHandler = cont;
    }

    public int getSizeInventory() {
        return this.theInventory.length;
    }

    public ItemStack getStackInSlot(int p_70301_1_) {
        return this.theInventory[p_70301_1_];
    }

    public ItemStack decrStackSize(int slot, int stackSize) {
        if (this.theInventory[slot] != null) {
            if (slot == 2) {
                ItemStack itemstack = this.theInventory[slot];
                this.theInventory[slot] = null;
                return itemstack;
            }
            if (this.theInventory[slot].stackSize <= stackSize) {
                ItemStack itemstack = this.theInventory[slot];
                this.theInventory[slot] = null;
                if (this.inventoryResetNeededOnSlotChange(slot)) {
                    this.resetRecipeAndSlots();
                }
                return itemstack;
            }
            ItemStack itemstack = this.theInventory[slot].splitStack(stackSize);
            if (this.theInventory[slot].stackSize == 0) {
                this.theInventory[slot] = null;
            }
            if (this.inventoryResetNeededOnSlotChange(slot)) {
                this.resetRecipeAndSlots();
            }
            return itemstack;
        }
        return null;
    }

    public ItemStack getStackInSlotOnClosing(int slot) {
        if (this.theInventory[slot] != null) {
            ItemStack itemstack = this.theInventory[slot];
            this.theInventory[slot] = null;
            return itemstack;
        }
        return null;
    }

    public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
        this.theInventory[par1] = par2ItemStack;
        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
        if (this.inventoryResetNeededOnSlotChange(par1)) {
            this.resetRecipeAndSlots();
        }
    }

    private boolean inventoryResetNeededOnSlotChange(int slot) {
        return slot == 0 || slot == 1;
    }

    public String getInventoryName() {
        return "mob.villager";
    }

    public boolean hasCustomInventoryName() {
        return true;
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public void markDirty() {
        this.resetRecipeAndSlots();
    }

    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        return true;
    }

    public void openInventory() {
    }

    public void closeInventory() {
    }

    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
        return true;
    }

    public void resetRecipeAndSlots() {
        ItemStack component1 = this.theInventory[0];
        ItemStack component2 = this.theInventory[1];
        ItemStack resultslot = this.theInventory[2];
        if (component1 != null && resultslot == null) {
            ItemStack result = AlienRecipes.getInstance().getResult(component1, component2);
            ItemStack[] components = AlienRecipes.getInstance().getComponents(result);
            if (components[0] != null && components[1] == null) {
                if (component1.stackSize >= components[0].stackSize) {
                    if (component1.stackSize > components[0].stackSize) {
                        component1.stackSize -= components[0].stackSize;
                    } else {
                        this.setInventorySlotContents(0, null);
                    }
                    this.setInventorySlotContents(2, result.copy());
                } else {
                    this.setInventorySlotContents(2, null);
                }
            } else if (components[0] != null && components[1] != null) {
                if (component1 != null && component1.stackSize >= components[0].stackSize && component2 != null && component2.stackSize >= components[1].stackSize) {
                    if (component1.stackSize > components[0].stackSize) {
                        component1.stackSize -= components[0].stackSize;
                    } else {
                        this.setInventorySlotContents(0, null);
                    }
                    if (component2.stackSize > components[1].stackSize) {
                        component2.stackSize -= components[1].stackSize;
                    } else {
                        this.setInventorySlotContents(1, null);
                    }
                    this.setInventorySlotContents(2, result.copy());
                } else {
                    this.setInventorySlotContents(2, null);
                }
            }
        }
    }
}

