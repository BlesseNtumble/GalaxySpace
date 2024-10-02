package galaxyspace.systems.SolarSystem.planets.overworld.inventory.schematics;

import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.util.GSRecipeUtil;
import micdoodle8.mods.galacticraft.core.inventory.SlotRocketBenchResult;
import micdoodle8.mods.galacticraft.planets.asteroids.items.AsteroidsItems;
import micdoodle8.mods.galacticraft.planets.mars.items.MarsItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerSchematicPortNuclearReactor extends Container
{
    public InventorySchematicPortNuclearReactor craftMatrix = new InventorySchematicPortNuclearReactor(this);
    public IInventory craftResult = new InventoryCraftResult();
    private final World worldObj;

    public ContainerSchematicPortNuclearReactor(InventoryPlayer par1InventoryPlayer, int x, int y, int z)
    {
        final int change = 27;
        this.worldObj = par1InventoryPlayer.player.worldObj;
        this.addSlotToContainer(new SlotRocketBenchResult(par1InventoryPlayer.player, this.craftMatrix, this.craftResult, 0, 140, 95 + change));
        int var6;
        int var7;
        
        
        // Body Center
        for (var6 = 0; var6 < 5; ++var6)
        {
        	for (var7 = 0; var7 < 5; ++var7)            
       		this.addSlotToContainer(new SlotSchematicPortNuclearReactor(this.craftMatrix, var6 + var7 * 5 + 1, 6 + var7 * 19, 16 + var6 * 19 + change, x, y, z, par1InventoryPlayer.player));
        }

        // Player inv:

        for (var6 = 0; var6 < 3; ++var6)
        {
            for (var7 = 0; var7 < 9; ++var7)
            {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, var7 + var6 * 9 + 9, 17 + var7 * 18, 144 + var6 * 18 + change));
            }
        }

        for (var6 = 0; var6 < 9; ++var6)
        {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, var6, 17 + var6 * 18, 18 + 184 + change));
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
        this.craftResult.setInventorySlotContents(0, GSRecipeUtil.findMatchingPNRRecipe(this.craftMatrix));
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return true;
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par1)
    {
        ItemStack var2 = null;
        final Slot slot = (Slot) this.inventorySlots.get(par1);
        int b = this.inventorySlots.size();

        if (slot != null && slot.getHasStack())
        {
            final ItemStack var4 = slot.getStack();
            var2 = var4.copy();

            if (par1 <= 25)
            {           
            	if (!this.mergeItemStack(var4, 26, this.inventorySlots.size(), false))
                {
                    return null;
                }
                if (par1 == 0)
                {
                    slot.onSlotChange(var4, var2);
                }
            }
            else if (var2.getItem() == AsteroidsItems.basicItem && var2.getItemDamage() == 0)
            {
	            if(!this.mergeOneItem(var4, 1, 2, false) 
	            		&& !this.mergeOneItem(var4, 5, 6, false) 
	            		&& !this.mergeOneItem(var4, 21, 22, false)
	            		&& !this.mergeOneItem(var4, 25, 26, false))
	            {
	                return null;
	            }
            }
            else if (var2.getItem() == GSItems.HeavyDutyPlates && var2.getItemDamage() == 1)
            {
	            if(!this.mergeOneItem(var4, 7, 8, false) 
	            		&& !this.mergeOneItem(var4, 9, 10, false) 
	            		&& !this.mergeOneItem(var4, 12, 13, false)
	            		&& !this.mergeOneItem(var4, 14, 15, false)
	            		&& !this.mergeOneItem(var4, 17, 18, false)
	            		&& !this.mergeOneItem(var4, 19, 20, false))
	            {
	                return null;
	            }
            }
            else if (var2.getItem() == MarsItems.marsItemBasic && var2.getItemDamage() == 1)
            {
	            if(!this.mergeOneItem(var4, 6, 7, false)
	            		&& !this.mergeOneItem(var4, 10, 11, false)
	            		&& !this.mergeOneItem(var4, 11, 12, false)
	            		&& !this.mergeOneItem(var4, 15, 16, false)
	            		&& !this.mergeOneItem(var4, 16, 17, false)
	            		&& !this.mergeOneItem(var4, 20, 21, false))
	            {
	                return null;
	            }
            }
            else if (var2.getItem() == Item.getItemFromBlock(GSBlocks.FluidTank))
            {
	            if(!this.mergeOneItem(var4, 3, 4, false))
	            {
	                return null;
	            }
            }
            else if (var2.getItem() == Item.getItemFromBlock(GSBlocks.FuelGenerator))
            {
	            if(!this.mergeOneItem(var4, 23, 24, false))
	            {
	                return null;
	            }
            }
            else if (var2.getItem() == AsteroidsItems.basicItem && var2.getItemDamage() == 6)
            {
	            if(!this.mergeOneItem(var4, 2, 3, false)
	            		&& !this.mergeOneItem(var4, 4, 5, false)
	            		&& !this.mergeOneItem(var4, 22, 23, false)
	            		&& !this.mergeOneItem(var4, 24, 25, false))
	            {
	                return null;
	            }
            }
            else if (var2.getItem() == AsteroidsItems.basicItem && var2.getItemDamage() == 8)
            {
	            if(!this.mergeOneItem(var4, 8, 9, false)
	            		&& !this.mergeOneItem(var4, 18, 19, false))
	            {
	                return null;
	            }
            }
            else if (var2.getItem() == GSItems.BasicItems && var2.getItemDamage() == 0)
            {
	            if(!this.mergeOneItem(var4, 13, 14, false))
	            {
	                return null;
	            }
            }
            if (var4.stackSize == 0)
            {
            	if (par1 == 0)
                {
                    slot.onPickupFromSlot(par1EntityPlayer, var4);
                }
                slot.putStack((ItemStack) null);
                return var2;
            }
            else slot.onSlotChange(var4, var2);

            if (var4.stackSize == var2.stackSize)
            {
                return null;
            }
            
            slot.onPickupFromSlot(par1EntityPlayer, var4);
            if (par1 == 0)
            {
                slot.onSlotChanged();
            }

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

