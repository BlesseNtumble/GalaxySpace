package galaxyspace.systems.SolarSystem.planets.overworld.inventory;

import galaxyspace.core.registers.items.GSItems;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityRocketAssemblyMachine;
import micdoodle8.mods.galacticraft.api.item.IItemElectric;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.inventory.SlotSpecific;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ContainerRocketAssemblyMachine extends Container
{
    private TileEntityRocketAssemblyMachine tileEntity;

    public ContainerRocketAssemblyMachine(InventoryPlayer par1InventoryPlayer, TileEntityRocketAssemblyMachine tileEntity)
    {
        this.tileEntity = tileEntity;
        tileEntity.rocketCraftMatrix.eventHandler = this;
        
        // Battery Slot
        this.addSlotToContainer(new SlotSpecific(tileEntity, 0, 185, 112, ItemElectricBase.class));

        // Smelting result
        this.addSlotToContainer(new SlotFurnace(par1InventoryPlayer.player, tileEntity, 1, 173, 69));
        
        //Cone
        this.addSlotToContainer(new SlotRocketAssembly(tileEntity.rocketCraftMatrix, 2, 130, 69));
        //Bodies
        this.addSlotToContainer(new SlotRocketAssembly(tileEntity.rocketCraftMatrix, 3, 96, 69));        
        this.addSlotToContainer(new SlotRocketAssembly(tileEntity.rocketCraftMatrix, 4, 64, 69));
        //Engine
        this.addSlotToContainer(new SlotRocketAssembly(tileEntity.rocketCraftMatrix, 5, 22, 69));
        //Boosters
        this.addSlotToContainer(new SlotRocketAssembly(tileEntity.rocketCraftMatrix, 6, 22, 34));        
        this.addSlotToContainer(new SlotRocketAssembly(tileEntity.rocketCraftMatrix, 7, 22, 104));
        //Stabilizers
        this.addSlotToContainer(new SlotRocketAssembly(tileEntity.rocketCraftMatrix, 8, 52, 34));        
        this.addSlotToContainer(new SlotRocketAssembly(tileEntity.rocketCraftMatrix, 9, 52, 104));
        //Chests
        this.addSlotToContainer(new SlotRocketAssembly(tileEntity.rocketCraftMatrix, 10, 140, 35));        
        this.addSlotToContainer(new SlotRocketAssembly(tileEntity.rocketCraftMatrix, 11, 159, 35));
        this.addSlotToContainer(new SlotRocketAssembly(tileEntity.rocketCraftMatrix, 12, 178, 35));
        
        int var3;

        for (var3 = 0; var3 < 3; ++var3)
        {
            for (int var4 = 0; var4 < 9; ++var4)
            {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, var4 + var3 * 9 + 9, 24 + var4 * 18, 146 + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 9; ++var3)
        {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, var3, 24 + var3 * 18, 204));
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

    @Override
    public void onCraftMatrixChanged(IInventory par1IInventory)
    {
        super.onCraftMatrixChanged(par1IInventory);
        this.tileEntity.updateInput();
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

            if (par1 <= 12)
            {
                if (!this.mergeItemStack(var4, 12, this.inventorySlots.size(), true))
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
                else if (var4.getItem() == GSItems.RocketParts)
                {
                	if(var4.getItemDamage() == 0 
                			|| var4.getItemDamage() == 5 
                			|| var4.getItemDamage() == 10 
                			|| var4.getItemDamage() == 15
                			|| var4.getItemDamage() == 20)
                	{
	                    if (!this.mergeItemStack(var4, 2, 3, false))
	                    {
	                        return null;
	                    }
                	}
                	
                	if(var4.getItemDamage() == 1 
                			|| var4.getItemDamage() == 6 
                			|| var4.getItemDamage() == 11 
                			|| var4.getItemDamage() == 16
                			|| var4.getItemDamage() == 21)
                	{
	                    if (!this.mergeItemStack(var4, 3, 5, false))
	                    {
	                        return null;
	                    }
                	}
                	
                	if(var4.getItemDamage() == 2 
                			|| var4.getItemDamage() == 7 
                			|| var4.getItemDamage() == 12 
                			|| var4.getItemDamage() == 17
                			|| var4.getItemDamage() == 22)
                	{
	                    if (!this.mergeItemStack(var4, 5, 6, false))
	                    {
	                        return null;
	                    }
                	}
                	
                	if(var4.getItemDamage() == 3 
                			|| var4.getItemDamage() == 8 
                			|| var4.getItemDamage() == 13 
                			|| var4.getItemDamage() == 18
                			|| var4.getItemDamage() == 23)
                	{
	                    if (!this.mergeItemStack(var4, 6, 8, false))
	                    {
	                        return null;
	                    }
                	}
                	
                	if(var4.getItemDamage() == 4 
                			|| var4.getItemDamage() == 9 
                			|| var4.getItemDamage() == 14 
                			|| var4.getItemDamage() == 19
                			|| var4.getItemDamage() == 24)
                	{
	                    if (!this.mergeItemStack(var4, 8, 10, false))
	                    {
	                        return null;
	                    }
                	}
                }  
                else if (var4.getItem() == Item.getItemFromBlock(Blocks.chest))
                {
                	if (!this.mergeOneItem(var4, 10, 13, false))
                    {
                        return null;
                    }
                }
 
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
