package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import galaxyspace.core.registers.blocks.GSBlocks;
import micdoodle8.mods.galacticraft.core.tile.TileEntityAdvanced;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileEntityModificationTable extends TileEntityAdvanced implements IInventory {

	private ItemStack[] containingItems = new ItemStack[1];
	
	public TileEntityModificationTable()
    {		
    }
		
	@Override
    public void updateEntity()
    {
    	super.updateEntity();
    }
	
	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}
	
	@Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        
        NBTTagList var2 = par1NBTTagCompound.getTagList("Items", 10);
        this.containingItems = new ItemStack[this.getSizeInventory()];

        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            NBTTagCompound var4 = var2.getCompoundTagAt(var3);
            int var5 = var4.getByte("Slot") & 255;

            if (var5 < this.containingItems.length)
            {
                this.containingItems[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }
        
    }
	
	@Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
      
        NBTTagList var2 = new NBTTagList();

        for (int var3 = 0; var3 < this.containingItems.length; ++var3)
        {
            if (this.containingItems[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte) var3);
                this.containingItems[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }
        
        par1NBTTagCompound.setTag("Items", var2);
    }
	
    @Override
    public double getPacketRange()
    {
        return 12.0D;
    }

    @Override
    public int getPacketCooldown()
    {
        return 3;
    }

    @Override
    public boolean isNetworkedTile()
    {
        return true;
    }


	@Override
	public int getSizeInventory() {
		return this.containingItems.length;
	}


	@Override
	public ItemStack getStackInSlot(int slot) {
		return this.containingItems[slot];
	}


	@Override
	public ItemStack decrStackSize(int slot, int count)  {
        if (this.containingItems[slot] != null)
        {
            ItemStack var3;

            if (this.containingItems[slot].stackSize <= count)
            {
                var3 = this.containingItems[slot];
                this.containingItems[slot] = null;
                return var3;
            }
            else
            {
                var3 = this.containingItems[slot].splitStack(count);

                if (this.containingItems[slot].stackSize == 0)
                {
                    this.containingItems[slot] = null;
                }

                return var3;
            }
        }
        else
        {
            return null;
        }
    }


	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (this.containingItems[slot] != null) 
		{
			ItemStack var2 = this.containingItems[slot];
			this.containingItems[slot] = null;
			return var2;
		} 
		else 
		{
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		this.containingItems[slot] = stack;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
			stack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return GSBlocks.ModificationTable.getLocalizedName();
	}


	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}


	@Override
	public int getInventoryStackLimit() {
		return 1;
	}


	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}


	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return true;
	}

}
