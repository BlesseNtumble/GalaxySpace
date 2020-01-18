package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import java.util.ArrayList;
import java.util.List;

import galaxyspace.api.item.IModificationItem;
import galaxyspace.core.prefab.items.modules.ItemModule;
import galaxyspace.systems.SolarSystem.planets.overworld.items.armor.ItemSpaceSuit;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseElectricBlockWithInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;

public class TileEntityModificationTable extends TileBaseElectricBlockWithInventory {
		
	public TileEntityModificationTable()
    {		
		super("tile.modification_table.name");
		this.storage.setCapacity(0);
		this.storage.setMaxExtract(0);
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
	public boolean shouldUseEnergy() {
		return false;
	}

	@Override
	public EnumFacing getFront() {
		return null;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return new int[] {0};
	}

}
