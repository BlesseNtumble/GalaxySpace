package galaxyspace.core.prefab.inventory;

import galaxyspace.core.prefab.entities.EntityAstroWolf;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class InventoryAstroWolf implements IInventory
{
    private NonNullList<ItemStack> stacks = NonNullList.withSize(4, ItemStack.EMPTY);
    private EntityAstroWolf wolf;
    public Container currentContainer;

    public InventoryAstroWolf(EntityAstroWolf wolf)
    {
        this.wolf = wolf;
        
        System.out.println(wolf.world.isRemote + " | " + FMLCommonHandler.instance().getSide());
    }
    
    @Override
    public int getSizeInventory()
    {
        return this.stacks.size();
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
        if (!this.stacks.get(par1).isEmpty())
        {
            final ItemStack var2 = this.stacks.get(par1);
            this.stacks.set(par1, ItemStack.EMPTY);
            this.markDirty();
            return var2;
        }
        else
        {
            return ItemStack.EMPTY;
        }
    }
/*
    private void removeInventoryBagContents()
    {
        if (this.currentContainer instanceof ContainerSlimeling)
        {
        	ContainerSlimeling.removeSlots((ContainerSlimeling) this.currentContainer);
        }

        for (int i = 2; i < this.stacks.size(); i++)
        {
            if (!this.stacks.get(i).isEmpty())
            {
                if (!this.wolf.world.isRemote)
                {
                    this.wolf.entityDropItem(this.stacks.get(i), 0.5F);
                }

                this.stacks.set(i, ItemStack.EMPTY);
            }
        }
    }
    */
    @Override
    public ItemStack decrStackSize(int par1, int par2)
    {
    	if (!this.stacks.get(par1).isEmpty())
        {
            ItemStack var3;

            //It's a removal of the Slimeling Inventory Bag
          /*  if (par1 == 1 && this.stacks.get(par1).getCount() <= par2)
            {
            	//this.removeInventoryBagContents();
                var3 = this.stacks.get(par1);
                this.stacks.set(par1, ItemStack.EMPTY);
                this.markDirty();
                return var3;
            }
            else*/
            //Normal case of decrStackSize for a slot
            {
                var3 = this.stacks.get(par1).splitStack(par2);

                if (this.stacks.get(par1).isEmpty())
                {
                	//Not sure if this is necessary again, given the above?
                	/*if (par1 == 1)
                    {
                		this.removeInventoryBagContents();
                    }*/

                    this.stacks.set(par1, ItemStack.EMPTY);
                }

                this.markDirty();
                return var3;
            }
        }
        else
        {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
    	/*
        if (par1 == 1 && (par2ItemStack.isEmpty() && !this.stacks.get(par1).isEmpty() || !ItemStack.areItemStacksEqual(par2ItemStack, this.stacks.get(par1))))
        {
            ContainerSlimeling.addAdditionalSlots((ContainerSlimeling) this.currentContainer, this.wolf, par2ItemStack);
        }
*/
        this.stacks.set(par1, par2ItemStack);
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

    public void readFromNBT(NBTTagList tagList)
    {
        if (tagList == null || tagList.tagCount() <= 0)
        {
            return;
        }

        this.stacks = NonNullList.withSize(this.stacks.size(), ItemStack.EMPTY);

        for (int i = 0; i < tagList.tagCount(); ++i)
        {
            final NBTTagCompound nbttagcompound = tagList.getCompoundTagAt(i);
            final int j = nbttagcompound.getByte("Slot") & 255;
            final ItemStack itemstack = new ItemStack(nbttagcompound);

            if (!itemstack.isEmpty())
            {
                this.stacks.set(j, itemstack);
            }
        }
        
        System.out.println(stacks);
    }

    public NBTTagList writeToNBT(NBTTagList tagList)
    {
        NBTTagCompound nbttagcompound;

        for (int i = 0; i < this.stacks.size(); ++i)
        {
            if (!this.stacks.get(i).isEmpty())
            {
                nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte) i);
                this.stacks.get(i).writeToNBT(nbttagcompound);
                tagList.appendTag(nbttagcompound);
            }
        }

        System.out.println(stacks);
        return tagList;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
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

