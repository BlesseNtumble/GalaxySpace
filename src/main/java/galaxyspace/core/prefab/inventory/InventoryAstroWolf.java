package galaxyspace.core.prefab.inventory;

import galaxyspace.core.prefab.entities.EntityAstroWolf;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class InventoryAstroWolf implements IInventory
{
    private NonNullList<ItemStack> stacks = NonNullList.withSize(3, ItemStack.EMPTY);
    private EntityAstroWolf wolf;
    public Container currentContainer;

    public InventoryAstroWolf(EntityAstroWolf wolf)
    {
        this.wolf = wolf;
    }
    
    @Override
    public int getSizeInventory()
    {
        return this.stacks.size();
    }

    public NonNullList<ItemStack> getInventory()
    {
    	return this.stacks;
    }
    
    @Override
    public ItemStack getStackInSlot(int par1)
    {
        return par1 >= this.getSizeInventory() ? ItemStack.EMPTY : this.stacks.get(par1);
    }

    @Override
    public String getName()
    {
        return GCCoreUtil.translate("container.astro_wolf_inventory.name");
    }

    @Override
    public ItemStack removeStackFromSlot(int par1)
    {
    	 return ItemStackHelper.getAndRemove(this.stacks, par1);
    }

    @Override
    public ItemStack decrStackSize(int index, int count)
    {
    	 ItemStack itemstack = ItemStackHelper.getAndSplit(this.stacks, index, count);

         if (!itemstack.isEmpty())
         {
             this.markDirty();
         }

         return itemstack;
     
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack)
    {
    	 this.stacks.set(index, stack);

         if (stack.getCount() > this.getInventoryStackLimit())
         {
             stack.setCount(this.getInventoryStackLimit());
         }

         this.markDirty();
    }

    @Override
    public boolean isEmpty()
    {
        for (ItemStack itemstack : this.stacks)
        {
            if (!itemstack.isEmpty())
            {
                return false;
            }
        }

        return true;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 1;
    }

    @Override
    public void markDirty()
    {    	
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return !this.wolf.isDead && par1EntityPlayer.getDistanceSq(this.wolf) <= 64.0D;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack)
    {
        return false;
    }

    //We don't use these because we use forge containers
    @Override
    public void openInventory(EntityPlayer player)
    {
    }

    //We don't use these because we use forge containers
    @Override
    public void closeInventory(EntityPlayer player)
    {
    }

    @Override
    public int getField(int id)
    {
        return 0;
    }

    @Override
    public void setField(int id, int value)
    {
    }

    @Override
    public int getFieldCount()
    {
        return 0;
    }

    @Override
    public void clear()
    {

    }

    @Override
    public boolean hasCustomName()
    {
        return false;
    }

    @Override
    public ITextComponent getDisplayName()
    {
        return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName(), new Object[0]);
    }
}

