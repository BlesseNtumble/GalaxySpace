package galaxyspace.systems.SolarSystem.planets.overworld.inventory;

import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityHydroponicBase;
import micdoodle8.mods.galacticraft.api.item.IItemElectric;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.inventory.SlotSpecific;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceOutput;
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
	       	this.addSlotToContainer(new SlotFurnaceOutput(par1InventoryPlayer.player, tileEntity, 3 + i*2, 108, 57 - (22 * i)));
	       	this.addSlotToContainer(new SlotFurnaceOutput(par1InventoryPlayer.player, tileEntity, 9 + i, 129, 57 - (22 * i)));
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
    public void onContainerClosed(EntityPlayer entityplayer)
    {
        super.onContainerClosed(entityplayer);
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.tileEntity.isUsableByPlayer(par1EntityPlayer);
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
            	if(!itemstack.isEmpty() && !itemstack1.isEmpty() && !itemstack.getItem().equals(itemstack1.getItem())) 
            	{
            		if(i == 2)            			
            			this.tileEntity.processTicks_0 = 0;
            		if(i == 4)  
            			this.tileEntity.processTicks_1 = 0;
            		if(i == 6) 
            			this.tileEntity.processTicks_2 = 0;
            	}
            			
            	
                boolean clientStackChanged = !ItemStack.areItemStacksEqualUsingNBTShareTag(itemstack1, itemstack);
                itemstack1 = itemstack.isEmpty() ? ItemStack.EMPTY : itemstack.copy();
                this.inventoryItemStacks.set(i, itemstack1);

                if (clientStackChanged)
                for (int j = 0; j < this.listeners.size(); ++j)
                {
                    ((IContainerListener)this.listeners.get(j)).sendSlotContents(this, i, itemstack1);
                }
            }
        }
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

            if (par1 <= 9)
            {
                if (!this.mergeItemStack(var4, 9, 35, true))
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
                else if (var4.getItem() == Items.WATER_BUCKET)
                {
                    if (!this.mergeItemStack(var4, 1, 2, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }               
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
