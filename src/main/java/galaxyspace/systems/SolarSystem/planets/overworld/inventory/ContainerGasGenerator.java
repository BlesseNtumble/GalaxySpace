package galaxyspace.systems.SolarSystem.planets.overworld.inventory;

import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityGasGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerGasGenerator extends Container
{
    private TileEntityGasGenerator tileEntity;

    public ContainerGasGenerator(InventoryPlayer par1InventoryPlayer, TileEntityGasGenerator tileEntity)
    {
        this.tileEntity = tileEntity;
        //this.addSlotToContainer(new SlotSpecific(tileEntity, 0, 7, 7, new ItemStack(GCItems.bucketFuel)));
        this.addSlotToContainer(new Slot(tileEntity, 0, 16, 14));
        int var3;

        for (var3 = 0; var3 < 3; ++var3)
        {
            for (int var4 = 0; var4 < 9; ++var4)
            {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, var4 + var3 * 9 + 9, 8 + var4 * 18, 102 + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 9; ++var3)
        {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, var3, 8 + var3 * 18, 160));
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
        return this.tileEntity.isUsableByPlayer(par1EntityPlayer);
    }

    /**
     * Called to transfer a stack from one inventory to the other eg. when shift
     * clicking.
     */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par1)
    {
        ItemStack var2 = ItemStack.EMPTY;
        final Slot slot = this.inventorySlots.get(par1);

        if (slot != null && slot.getHasStack())
        {
            ItemStack var4 = slot.getStack();
            var2 = var4.copy();

            if (par1 != 0)
            {
                
                if (!this.mergeItemStack(var4, 0, 1, false))
                {
                	return ItemStack.EMPTY;
                }                             
                else if (par1 >= 28)
                {
                    if (!this.mergeItemStack(var4, 1, 28, false))
                    {
                    	return ItemStack.EMPTY;
                    }
                }
                else if (!this.mergeItemStack(var4, 28, 37, false))
                {
                	return ItemStack.EMPTY;
                }

            }
            else if (!this.mergeItemStack(var4, 1, 37, false))
            {
            	return ItemStack.EMPTY;
            }

            if (var4.getCount() == 0)
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (var4.getCount() == var2.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(par1EntityPlayer, var4);
        }

        return var2;
    }
}
