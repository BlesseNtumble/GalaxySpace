package galaxyspace.systems.SolarSystem.planets.overworld.inventory;

import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityConverterSurface;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.inventory.SlotSpecific;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

public class ContainerConverterSurface extends Container
{
    private TileEntityConverterSurface tileEntity;

    public ContainerConverterSurface(InventoryPlayer par1InventoryPlayer, TileEntityConverterSurface tileEntity)
    {
        this.tileEntity = tileEntity;
        //this.addSlotToContainer(new SlotSpecific(tileEntity, 0, 7, 7, new ItemStack(GCItems.bucketFuel)));
        this.addSlotToContainer(new Slot(tileEntity, 1, 7, 7));
        int var3;

        this.addSlotToContainer(new SlotSpecific(tileEntity, 2, 41, 14, new ItemStack(Items.dye, 1, 15)));
        this.addSlotToContainer(new SlotSpecific(tileEntity, 3, 41, 41, new ItemStack(Items.wheat_seeds, 1, 0)));
        this.addSlotToContainer(new Slot(tileEntity, 4, 74, 27));
        this.addSlotToContainer(new SlotFurnace(par1InventoryPlayer.player, tileEntity, 5, 138, 27));
        
        // Battery Slot
        this.addSlotToContainer(new SlotSpecific(tileEntity, 0, 77, 93, ItemElectricBase.class));
        
        for (var3 = 0; var3 < 3; ++var3)
        {
            for (int var4 = 0; var4 < 9; ++var4)
            {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, var4 + var3 * 9 + 9, 8 + var4 * 18, 118 + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 9; ++var3)
        {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, var3, 8 + var3 * 18, 176));
        }
    }

    @Override
    public void onContainerClosed(EntityPlayer entityplayer)
    {
        super.onContainerClosed(entityplayer);
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.tileEntity.isUseableByPlayer(par1EntityPlayer);
    }

    /**
     * Called to transfer a stack from one inventory to the other eg. when shift
     * clicking.
     */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par1)
    {
        ItemStack var2 = null;
       /* Slot var3 = (Slot) this.inventorySlots.get(par1);

        if (var3 != null && var3.getHasStack())
        {
            ItemStack var4 = var3.getStack();
            var2 = var4.copy();

            if (par1 != 0)
            {
                if (var4.getItem() == GCItems.bucketFuel)
                {
                    if (!this.mergeItemStack(var4, 0, 1, false))
                    {
                        return null;
                    }
                }
                if (var4.getItem() == GCItems.fuelCanister && var4.getItemDamage() != 1001)
                {
                    if (!this.mergeItemStack(var4, 0, 1, false))
                    {
                        return null;
                    }
                }
                else if (par1 >= 28)
                {
                    if (!this.mergeItemStack(var4, 1, 28, false))
                    {
                        return null;
                    }
                }
                else if (!this.mergeItemStack(var4, 28, 37, false))
                {
                    return null;
                }

            }
            else if (!this.mergeItemStack(var4, 1, 37, false))
            {
                return null;
            }

            if (var4.stackSize == 0)
            {
                var3.putStack((ItemStack) null);
            }
            else
            {
                var3.onSlotChanged();
            }

            if (var4.stackSize == var2.stackSize)
            {
                return null;
            }

            var3.onPickupFromSlot(par1EntityPlayer, var4);
        }*/

        return var2;
    }
}
