package galaxyspace.systems.SolarSystem.planets.overworld.inventory.schematics;

import galaxyspace.core.util.GSRecipeUtil;
import micdoodle8.mods.galacticraft.core.inventory.SlotRocketBenchResult;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerSchematicTier1Rocket extends Container {

	public InventorySchematicTier1Rocket craftMatrix = new InventorySchematicTier1Rocket(this);
    public IInventory craftResult = new InventoryCraftResult();
    private final World worldObj;

    public ContainerSchematicTier1Rocket(InventoryPlayer par1InventoryPlayer, int x, int y, int z)
    {
        final int change = 27;
        final int xOffset = -10;
        final int yOffset = 18;
        this.worldObj = par1InventoryPlayer.player.worldObj;
        this.addSlotToContainer(new SlotRocketBenchResult(par1InventoryPlayer.player, this.craftMatrix, this.craftResult, 0, 145, 95 + change));
        int var6;
        int var7;

        // Cone
        this.addSlotToContainer(new SlotSchematicTier1Rocket(this.craftMatrix, 1, 48 + xOffset, change + yOffset, x, y, z, par1InventoryPlayer.player));

        // Body Left
        for (var6 = 0; var6 < 4; ++var6)
        {
            this.addSlotToContainer(new SlotSchematicTier1Rocket(this.craftMatrix, 2 + var6, 39 + xOffset, -6 + var6 * 18 + 25 + change + yOffset, x, y, z, par1InventoryPlayer.player));
        }

        // Body Right
        for (var6 = 0; var6 < 4; ++var6)
        {
            this.addSlotToContainer(new SlotSchematicTier1Rocket(this.craftMatrix, 6 + var6, 57 + xOffset, -6 + var6 * 18 + 25 + change + yOffset, x, y, z, par1InventoryPlayer.player));
        }

        // Left fins
        this.addSlotToContainer(new SlotSchematicTier1Rocket(this.craftMatrix, 10, 20 + xOffset, 73 + change + yOffset, x, y, z, par1InventoryPlayer.player));
        this.addSlotToContainer(new SlotSchematicTier1Rocket(this.craftMatrix, 11, 20 + xOffset, 92 + change + yOffset, x, y, z, par1InventoryPlayer.player));

        // Engine
        this.addSlotToContainer(new SlotSchematicTier1Rocket(this.craftMatrix, 12, 48 + xOffset, 92 + change + yOffset, x, y, z, par1InventoryPlayer.player));

        // Right fins
        this.addSlotToContainer(new SlotSchematicTier1Rocket(this.craftMatrix, 13, 76 + xOffset, 73 + change + yOffset, x, y, z, par1InventoryPlayer.player));
        this.addSlotToContainer(new SlotSchematicTier1Rocket(this.craftMatrix, 14, 76 + xOffset, 92 + change + yOffset, x, y, z, par1InventoryPlayer.player));

        // Addons
        for (int var8 = 0; var8 < 3; var8++)
        {
            this.addSlotToContainer(new SlotSchematicTier1Rocket(this.craftMatrix, 15 + var8, 113 + var8 * 24, 11 + change, x, y, z, par1InventoryPlayer.player));
        }
        
        // Addons GS
        for (int var8 = 0; var8 < 3; var8++)
        {
            this.addSlotToContainer(new SlotSchematicTier1Rocket(this.craftMatrix, 18 + var8, 80, 9 + var8 * 21 + change, x, y, z, par1InventoryPlayer.player));
        }


        // Player inv:

        for (var6 = 0; var6 < 3; ++var6)
        {
            for (var7 = 0; var7 < 9; ++var7)
            {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, var7 + (var6 * 9)+9, 17 + var7 * 18, 144 + var6 * 18 + change));
            }
        }

        for (var6 = 27; var6 < 36; ++var6)
        {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, var6-27, var6*18-469, 18 + 184 + change));
        }

        this.onCraftMatrixChanged(this.craftMatrix);
    }

    @Override
    public void onContainerClosed(EntityPlayer par1EntityPlayer)
    {
        super.onContainerClosed(par1EntityPlayer);

        if (!this.worldObj.isRemote)
        {
            for (int var2 = 1; var2 < this.craftMatrix.getSizeInventory(); ++var2)
            {
                final ItemStack var3 = this.craftMatrix.getStackInSlotOnClosing(var2);

                if (var3 != null)
                {
                    par1EntityPlayer.entityDropItem(var3, 0.0F);
                }
            }
        }
    }

    @Override
    public void onCraftMatrixChanged(IInventory par1IInventory)
    {
        this.craftResult.setInventorySlotContents(0, GSRecipeUtil.findMatchingSpaceshipT1Recipe(this.craftMatrix));
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return true;
    }


    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index)
    {
        ItemStack itemStack = null;
        final Slot slot = (Slot) this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack stackInSlot = slot.getStack();
            itemStack = stackInSlot.copy();

            boolean done = false;
            int matrixSlots = this.craftMatrix.getSizeInventory();
            System.out.println("Matrix size: " + matrixSlots);
            if (index < matrixSlots)
            {
                if (!this.mergeItemStack(stackInSlot, matrixSlots, this.inventorySlots.size(), true))
                {
                    return null;
                }
                if (index == 0)
                {
                    slot.onSlotChange(stackInSlot, itemStack);
                }
            }
            else {
                for (int i = 1; i < matrixSlots; i++) {
                    Slot testSlot = (Slot) this.inventorySlots.get(i);
                    if (!testSlot.getHasStack() && testSlot.isItemValid(itemStack)) {
                        if (i < 15 || i > 17) {
                            if (!this.mergeOneItem(stackInSlot, i, i + 1, false)) {
                                return null;
                            }
                            done = true;
                            System.out.println("Placing " + stackInSlot.getDisplayName() + " in slot " + i);
                            break;
                        }
                    }
                }
                if (!done) {
                    if (itemStack.getItem() == Item.getItemFromBlock(Blocks.chest) && !((Slot) this.inventorySlots.get(15)).getHasStack()) {
                        if (!this.mergeOneItem(stackInSlot, 15, 16, false)) {
                            return null;
                        }
                    } else if (itemStack.getItem() == Item.getItemFromBlock(Blocks.chest) && !((Slot) this.inventorySlots.get(16)).getHasStack()) {
                        if (!this.mergeOneItem(stackInSlot, 16, 17, false)) {
                            return null;
                        }
                    } else if (itemStack.getItem() == Item.getItemFromBlock(Blocks.chest) && !((Slot) this.inventorySlots.get(17)).getHasStack()) {
                        if (!this.mergeOneItem(stackInSlot, 17, 18, false)) {
                            return null;
                        }
                    }
                }
            }
            if (stackInSlot.stackSize == 0)
            {
                slot.putStack(null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (stackInSlot.stackSize == itemStack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(player, stackInSlot);
        }

        return itemStack;

    }

    protected boolean mergeOneItem(ItemStack parItemStack, int start, int end, boolean par4)
    {
        boolean flag1 = false;
        if (parItemStack.stackSize > 0)
        {
            Slot slot;
            ItemStack slotStack;

            for (int k = start; k < end; k++)
            {
                slot = (Slot) this.inventorySlots.get(k);
                slotStack = slot.getStack();

                if (slotStack == null)
                {
                    ItemStack stackOneItem = parItemStack.copy();
                    stackOneItem.stackSize = 1;
                    parItemStack.stackSize--;
                    slot.putStack(stackOneItem);
                    slot.onSlotChanged();
                    flag1 = true;
                    break;
                }
            }
        }

        return flag1;
    }
}
