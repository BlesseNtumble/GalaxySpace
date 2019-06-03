package galaxyspace.systems.SolarSystem.planets.overworld.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;

public class RocketAssemblyInventory  extends InventoryCrafting
{
    /**
     * List of the stacks in the crafting matrix.
     */
	public NonNullList<ItemStack> stacks;
    /**
     * the width of the crafting inventory
     */
    private int inventoryWidth;
    private int inventoryHeight;

    /**
     * Class containing the callbacks for the events on_GUIClosed and
     * on_CraftMaxtrixChanged.
     */
    public Container eventHandler;

    public RocketAssemblyInventory()
    {
    	super(null, 2, 8);
        this.stacks = NonNullList.withSize(16, ItemStack.EMPTY);
        this.inventoryWidth = 2;
        this.inventoryHeight = 8;
    }

    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getSizeInventory()
    {
        return this.stacks.size();
    }

    /**
     * Returns the stack in slot i
     */
    @Override
    public ItemStack getStackInSlot(int index)
    {
        return this.stacks.get(index);
    }

    /**
     * Returns the itemstack in the slot specified (Top left is 0, 0). Args:
     * row, column
     */
    @Override
    public ItemStack getStackInRowAndColumn(int row, int column)
    {
        return row >= 0 && row < this.inventoryWidth && column >= 0 && column < this.inventoryHeight ? this.getStackInSlot(row + column * this.inventoryWidth) : ItemStack.EMPTY;
    }

    /**
     * Returns the name of the inventory.
     */
    @Override
    public String getName()
    {
        return "container.crafting";
    }

    @Override
    public ItemStack removeStackFromSlot(int index)
    {
    	return ItemStackHelper.getAndRemove(this.stacks, index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count)
    {
        ItemStack itemstack = ItemStackHelper.getAndSplit(this.stacks, index, count);

        if (!itemstack.isEmpty())
        {            
        	
        	if (this.eventHandler != null)
        		this.eventHandler.onCraftMatrixChanged(this);
            this.markDirty();
        }

        return itemstack;
    }

    /**
     * Sets the given item stack to the specified slot in the crafting inventory
     * Updates recipe matching in the containing machine.
     */
    @Override
    public void setInventorySlotContents(int index, ItemStack stack)
    {
        this.stacks.set(index, stack);

        if (stack.getCount() > this.getInventoryStackLimit())
        {
            stack.setCount(this.getInventoryStackLimit());
        }

        this.markDirty();
        if (this.eventHandler != null)
        {
            this.eventHandler.onCraftMatrixChanged(this);
        }
    }

    /**
     * Sets the given item stack to the specified slot in the crafting inventory.
     * No update to the containing machine.
     */
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
        return 64;
    }

    @Override
    public void markDirty()
    {
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return true;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack)
    {
        return true;
    }

	@Override
	public boolean hasCustomName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		return null;
	}

	@Override
	public void openInventory(EntityPlayer player) {		
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		this.stacks.clear();
	}
}

