package galaxyspace.systems.SolarSystem.planets.overworld.inventory;

import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemGSCanisterGeneric;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityLiquidSeparator;
import micdoodle8.mods.galacticraft.api.item.IItemElectric;
import micdoodle8.mods.galacticraft.core.blocks.GCBlocks;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.inventory.SlotSpecific;
import micdoodle8.mods.galacticraft.core.items.GCItems;
import micdoodle8.mods.galacticraft.core.items.ItemCanisterGeneric;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;

public class ContainerLiquidSeparator extends Container
{
    private TileEntityLiquidSeparator tileEntity;

    public ContainerLiquidSeparator(InventoryPlayer par1InventoryPlayer, TileEntityLiquidSeparator tileEntity)
    {
        this.tileEntity = tileEntity;

        // Battery Slot
        this.addSlotToContainer(new SlotSpecific(tileEntity, 0, 79, 101, ItemElectricBase.class));
        this.addSlotToContainer(new SlotSpecific(tileEntity, 1, 10, 19, ItemCanisterGeneric.class, ItemBucket.class, ItemGSCanisterGeneric.class));
        this.addSlotToContainer(new SlotSpecific(tileEntity, 2, 80, 19, ItemCanisterGeneric.class, ItemBucket.class, ItemGSCanisterGeneric.class));
        this.addSlotToContainer(new SlotSpecific(tileEntity, 3, 145, 19, ItemCanisterGeneric.class, ItemBucket.class, ItemGSCanisterGeneric.class));
        
        //this.addSlotToContainer(new SlotSpecific(tileEntity, 2, 10, 40, new ItemStack(GSItems.BasicItems, 1, 3), new ItemStack(GSItems.BasicItems, 1, 12)));
       
        // Smelting result
        //this.addSlotToContainer(new SlotFurnace(par1InventoryPlayer.player, tileEntity, 1, 15, 36));


        int var3;

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
        Slot var3 = (Slot) this.inventorySlots.get(par1);

        if (var3 != null && var3.getHasStack())
        {
            ItemStack var4 = var3.getStack();
            var2 = var4.copy();

            if (par1 <= 3)
            {
                if (!this.mergeItemStack(var4, 4, 35, true))
                {
                    return null;
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
                        return null;
                    }
                }
                else if (var4.getItem() == Item.getItemFromBlock(GCBlocks.blockMoon) && var4.getItemDamage() == 5)
                {
                    if (!this.mergeItemStack(var4, 1, 2, false))
                    {
                        return null;
                    }
                }
                else if (var4.getItem() == GCItems.oilCanister && var4.getItemDamage() == GCItems.oilCanister.getMaxDamage())
                {
                    if (!this.mergeItemStack(var4, 2, 3, false))
                    {
                        return null;
                    }
                }/*
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
        }

        return var2;
    }
}
