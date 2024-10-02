package galaxyspace.systems.SolarSystem.planets.overworld.inventory;

import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityHydroponicBase;
import micdoodle8.mods.galacticraft.api.item.IItemElectric;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.inventory.SlotSpecific;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

public class ContainerHydroponicBase extends Container
{
    private TileEntityHydroponicBase tileEntity;

    public ContainerHydroponicBase(InventoryPlayer par1InventoryPlayer, TileEntityHydroponicBase tileEntity)
    {
        this.tileEntity = tileEntity;
        //this.addSlotToContainer(new SlotSpecific(tileEntity, 0, 7, 7, new ItemStack(GCItems.bucketFuel)));
        // Battery Slot
        this.addSlotToContainer(new SlotSpecific(tileEntity, 0, 78, 102, ItemElectricBase.class));
        this.addSlotToContainer(new Slot(tileEntity, 1, 7, 23));
        int var3;

        //this.addSlotToContainer(new SlotSpecific(tileEntity, 2, 41, 14, new ItemStack(Items.dye, 1, 15)));
     	
        int x = tileEntity.moduleLevel;
        //GalaxySpace.debug("Inv: " + x);

	    for(int i = 0; i < x && x > 0; i++) {
	    	this.addSlotToContainer(new Slot(tileEntity, 2 + i * 2, 41, 57 - (22 * i)));
	       	this.addSlotToContainer(new SlotFurnace(par1InventoryPlayer.player, tileEntity, 3 + i*2, 108, 57 - (22 * i)));
	    	this.addSlotToContainer(new SlotFurnace(par1InventoryPlayer.player, tileEntity, 9 + i, 129, 57 - (22 * i)));
	    }       	
      
	    this.addSlotToContainer(new Slot(tileEntity, 8, 7, 1));
	    
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
    public void onCraftMatrixChanged(IInventory inventory)
    {
        this.detectAndSendChanges();
    }
    
    @Override
    public void detectAndSendChanges()
    {
        for (int i = 0; i < this.inventorySlots.size(); ++i)
        {
            ItemStack itemstack = ((Slot)this.inventorySlots.get(i)).getStack();
            ItemStack itemstack1 = (ItemStack)this.inventoryItemStacks.get(i);

            if (!ItemStack.areItemStacksEqual(itemstack1, itemstack))
            {
            	if(i == 2 || i == 4 || i == 6)
            		if(itemstack != null && itemstack1 != null && !itemstack.getItem().equals(itemstack1.getItem())) 
            			this.tileEntity.processTicks = 0;
            	
                itemstack1 = itemstack == null ? null : itemstack.copy();
                this.inventoryItemStacks.set(i, itemstack1);

                for (int j = 0; j < this.crafters.size(); ++j)
                {
                    ((ICrafting)this.crafters.get(j)).sendSlotContents(this, i, itemstack1);
                }
            }
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

            if (par1 <= 11)
            {
                if (!this.mergeItemStack(var4, 12, 35, true))
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
                else if (var4.getItem() == Items.water_bucket)
                {
                    if (!this.mergeItemStack(var4, 1, 2, false))
                    {
                        return null;
                    }
                }
                /*else if (var4.getItem() == GCItems.oilCanister && var4.getItemDamage() == GCItems.oilCanister.getMaxDamage())
                {
                    if (!this.mergeItemStack(var4, 2, 3, false))
                    {
                        return null;
                    }
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
