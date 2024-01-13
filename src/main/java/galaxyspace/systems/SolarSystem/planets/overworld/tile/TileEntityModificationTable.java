package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import galaxyspace.api.item.IModificationItem;
import galaxyspace.systems.SolarSystem.planets.overworld.items.armor.ItemSpaceSuit;
import micdoodle8.mods.galacticraft.core.inventory.IInventoryDefaults;
import micdoodle8.mods.galacticraft.core.tile.TileEntityAdvanced;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;

public class TileEntityModificationTable extends TileEntityAdvanced implements IInventoryDefaults, ISidedInventory {
		
	public TileEntityModificationTable()
    {		
		super("tile.modification_table.name");
		this.inventory = NonNullList.withSize(1, ItemStack.EMPTY);
    }
	
	@Override
    public void update()
    {
		super.update();
		
		ItemStack stack = this.getInventory().get(0);
		
		if(!stack.isEmpty() && stack.getItem() instanceof IModificationItem)
		{
			if(!stack.hasTagCompound()) {
				ItemStack copied = stack;
				copied.setTagCompound(new NBTTagCompound());
				copied.getTagCompound().setInteger(ItemSpaceSuit.mod_count, 1);
				this.getInventory().set(0, copied);
			}			
			
		}
    }
	
	public NonNullList<ItemStack> readStandardItemsFromNBT(NBTTagCompound nbt) {
		NonNullList<ItemStack> stacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(nbt, stacks);
		return stacks;
	}

	public void writeStandardItemsToNBT(NBTTagCompound nbt, NonNullList<ItemStack> stacks) {
		ItemStackHelper.saveAllItems(nbt, stacks);
	}
	    
	@Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        ItemStackHelper.loadAllItems(par1NBTTagCompound, this.getInventory());               
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
    	super.writeToNBT(par1NBTTagCompound);     
    	ItemStackHelper.saveAllItems(par1NBTTagCompound, this.getInventory());		
        return par1NBTTagCompound;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 1;
    }

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return false;
	}

	@Override
	protected boolean handleInventory()
	{
		return false;
	}

	@Override
	public int getSizeInventory()
	{
		return super.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int par1)
	{
		return super.getStackInSlot(par1);
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount)
	{
		return super.decrStackSize(slot, amount);
	}

	@Override
	public ItemStack removeStackFromSlot(int slot)
	{
		return super.removeStackFromSlot(slot);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		super.setInventorySlotContents(slot, stack);
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return !isInvalid() && this.world.isBlockLoaded(this.pos);
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return new int[] {0};
	}

	@Override
	public boolean canInsertItem(int slotID, ItemStack par2ItemStack, EnumFacing par3)
	{
		return this.isItemValidForSlot(slotID, par2ItemStack);
	}

	@Override
	public boolean canExtractItem(int slotID, ItemStack par2ItemStack, EnumFacing par3)
	{
		return slotID == 1;
	}

	@Override
	public boolean isEmpty()
	{
		for (ItemStack itemstack : this.getInventory())
		{
			if (!itemstack.isEmpty())
			{
				return false;
			}
		}

		return true;
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

	// We don't use these because we use forge containers
	@Override
	public void openInventory(EntityPlayer player)
	{
	}

	// We don't use these because we use forge containers
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
		for (int i = 0; i < this.getInventory().size(); ++i)
		{
			this.getInventory().set(i, ItemStack.EMPTY);
		}
	}

	@Override
	public String getName()
	{
		return super.getName();
	}

	@Override
	public boolean hasCustomName()
	{
		return true;
	}
}
