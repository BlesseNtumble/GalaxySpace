package galaxyspace.systems.SolarSystem.planets.overworld.inventory;

import galaxyspace.core.prefab.inventory.SlotUpgrades;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemUpgrades;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityLiquidSeparator;
import micdoodle8.mods.galacticraft.api.item.IItemElectric;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.inventory.SlotSpecific;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerLiquidSeparator extends Container
{
    private TileEntityLiquidSeparator tileEntity;

    public ContainerLiquidSeparator(InventoryPlayer par1InventoryPlayer, TileEntityLiquidSeparator tileEntity)
    {
        this.tileEntity = tileEntity;

        // Battery Slot
        this.addSlotToContainer(new SlotSpecific(tileEntity, 0, 79, 91, ItemElectricBase.class));
        this.addSlotToContainer(new Slot(tileEntity, 1, 10, 19));
        this.addSlotToContainer(new Slot(tileEntity, 2, 79, 19));
        this.addSlotToContainer(new Slot(tileEntity, 3, 145, 19));
        
        //this.addSlotToContainer(new SlotSpecific(tileEntity, 2, 10, 40, new ItemStack(GSItems.BasicItems, 1, 3), new ItemStack(GSItems.BasicItems, 1, 12)));
       
        // Smelting result
        //this.addSlotToContainer(new SlotFurnace(par1InventoryPlayer.player, tileEntity, 1, 15, 36));


        int var3;
        for(var3 = 0; var3 < 4; ++var3)        
        	this.addSlotToContainer(new SlotUpgrades(tileEntity, 4 + var3, 179, 18 + (21 * var3), 1, ItemUpgrades.class));
       
        for (var3 = 0; var3 < 3; ++var3)
        {
            for (int var4 = 0; var4 < 9; ++var4)
            {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, var4 + var3 * 9 + 9, 8 + var4 * 18, 137 + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 9; ++var3)
        {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, var3, 8 + var3 * 18, 195));
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

            if (par1 <= 3)
            {
                if (!this.mergeItemStack(var4, 4, 35, true))
                {
                    return ItemStack.EMPTY;
                }

                if (par1 == 1)
                {
                	var3.onSlotChange(var4, var2);
                }
            }
            else
            {
                if (var4.getItem() instanceof IItemElectric)
                {
                    if (!this.mergeItemStack(var4, 0, 1, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                /*
                else if (par1 < 38)
                {
                    if (!this.mergeItemStack(var4, 0, 9, false) && !this.mergeItemStack(var4, 38, 48, false))
                    {
                        return null;
                    }
                }
                else if (!this.mergeItemStack(var4, 0, 9, false) && !this.mergeItemStack(var4, 11, 38, false))
                {
                    return null;
                }*/
            }

            if (var4.getCount() == 0)
            {
                var3.putStack((ItemStack) ItemStack.EMPTY);
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
