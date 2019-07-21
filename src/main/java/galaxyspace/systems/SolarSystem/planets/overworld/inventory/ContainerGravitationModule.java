package galaxyspace.systems.SolarSystem.planets.overworld.inventory;

import galaxyspace.core.prefab.inventory.SlotUpgrades;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemUpgrades;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityGravitationModule;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.inventory.SlotSpecific;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerGravitationModule extends Container
{
    private TileEntityGravitationModule tileEntity;

    public ContainerGravitationModule(InventoryPlayer par1InventoryPlayer, TileEntityGravitationModule tileEntity)
    {
        this.tileEntity = tileEntity;
        //this.addSlotToContainer(new SlotSpecific(tileEntity, 0, 7, 7, new ItemStack(GCItems.bucketFuel)));
        //this.addSlotToContainer(new Slot(tileEntity, 1, 7, 7));
        int var3;

         
        // Battery Slot
        this.addSlotToContainer(new SlotSpecific(tileEntity, 0, 78, 102, ItemElectricBase.class));
        //this.addSlotToContainer(new SlotSpecific(tileEntity, 1, 7, 20, new ItemStack(GSItems.UPGRADES, 1, 1)));
        
        for(var3 = 0; var3 < 4; ++var3)        
        	this.addSlotToContainer(new SlotUpgrades(tileEntity, 1 + var3, 177, 18 + (21 * var3), 1, ItemUpgrades.class));
       
        
        for (var3 = 0; var3 < 3; ++var3)
        {
            for (int var4 = 0; var4 < 9; ++var4)
            {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, var4 + var3 * 9 + 9, 8 + var4 * 18, 127 + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 9; ++var3)
        {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, var3, 8 + var3 * 18, 185));
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
        Slot var3 = (Slot) this.inventorySlots.get(par1);

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
                        return ItemStack.EMPTY;
                    }
                }
                if (var4.getItem() == GCItems.fuelCanister && var4.getItemDamage() != 1001)
                {
                    if (!this.mergeItemStack(var4, 0, 1, false))
                    {
                        return ItemStack.EMPTY;
                    }
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
                var3.putStack(ItemStack.EMPTY);
            }
            else
            {
                var3.onSlotChanged();
            }

            if (var4.getCount() == var2.getCount())
            {
                return ItemStack.EMPTY;
            }

            var3.onTake(par1EntityPlayer, var4);
        }

        return var2;
    }
}
