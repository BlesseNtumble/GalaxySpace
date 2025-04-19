package galaxyspace.systems.SolarSystem.planets.overworld.inventory.schematics;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
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
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slotNum)
    {
        ItemStack var2 = null;
        final Slot slotVar = (Slot) this.inventorySlots.get(slotNum);
        if (slotVar != null && slotVar.getHasStack())
        {
            final ItemStack var4 = slotVar.getStack();
            var2 = var4.copy();

            boolean done = false;
            if (slotNum <= 21)
            {
                if (!this.mergeItemStack(var4, 21, 57, false))
                {
                    return null;
                }

                if (slotNum == 0)
                {
                    slotVar.onSlotChange(var4, var2);
                }
            }
            else
            {
                for (int i = 1; i < 21; i++)
                {
                    Slot testSlot = (Slot) this.inventorySlots.get(i);
                    if (!testSlot.getHasStack() && testSlot.isItemValid(var2))
                    {
                        if(i < 15 || i > 17)
                        {
                            if (!this.mergeOneItem(var4, i, i + 1, false)) {
                                return null;
                            }
                            done = true;
                            System.out.println("Placing " + var4.getDisplayName() + " in slot " + i);
                            break;
                        }
                    }
                }

                if (!done)
                {
                    if (var2.getItem() == Item.getItemFromBlock(Blocks.chest) && !((Slot) this.inventorySlots.get(15)).getHasStack())
                    {
                        if (!this.mergeOneItem(var4, 15, 16, false))
                        {
                            return null;
                        }
                    }
                    else if (var2.getItem() == Item.getItemFromBlock(Blocks.chest) && !((Slot) this.inventorySlots.get(16)).getHasStack())
                    {
                        if (!this.mergeOneItem(var4, 16, 17, false))
                        {
                            return null;
                        }
                    }
                    else if (var2.getItem() == Item.getItemFromBlock(Blocks.chest) && !((Slot) this.inventorySlots.get(17)).getHasStack())
                    {
                        if (!this.mergeOneItem(var4, 17, 18, false))
                        {
                            return null;
                        }
                    }
                    else if (slotNum >= 21 && slotNum < 46)
                    {
                        if (!this.mergeItemStack(var4, 46, 55, false))
                        {
                            return null;
                        }
                    }
                    else if (slotNum >= 46 && slotNum < 55)
                    {
                        if (!this.mergeItemStack(var4, 21, 49, false))
                        {
                            return null;
                        }
                    }
                    else if (!this.mergeItemStack(var4, 21, 58, false))
                    {
                        return null;
                    }
                }
            }

            if (var4.stackSize == 0)
            {
                slotVar.putStack((ItemStack) null);
            }
            else
            {
                slotVar.onSlotChanged();
            }

            if (var4.stackSize == var2.stackSize)
            {
                return null;
            }

            slotVar.onPickupFromSlot(par1EntityPlayer, var4);
        }

        return var2;

    }

    protected boolean mergeOneItem(ItemStack par1ItemStack, int par2, int par3, boolean par4)
    {
        boolean flag1 = false;
        if (par1ItemStack.stackSize > 0)
        {
            Slot slot;
            ItemStack slotStack;

            for (int k = par2; k < par3; k++)
            {
                slot = (Slot) this.inventorySlots.get(k);
                slotStack = slot.getStack();

                if (slotStack == null)
                {
                    ItemStack stackOneItem = par1ItemStack.copy();
                    stackOneItem.stackSize = 1;
                    par1ItemStack.stackSize--;
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
