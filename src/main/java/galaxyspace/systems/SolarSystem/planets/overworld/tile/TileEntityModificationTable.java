package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import java.util.ArrayList;
import java.util.List;

import galaxyspace.api.item.IModificationItem;
import galaxyspace.core.prefab.items.modules.ItemModule;
import galaxyspace.systems.SolarSystem.planets.overworld.items.armor.ItemSpaceSuit;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseElectricBlockWithInventory;
import micdoodle8.mods.galacticraft.core.inventory.IInventoryDefaults;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;

public class TileEntityModificationTable extends TileBaseElectricBlockWithInventory implements IInventoryDefaults {
	
	private NonNullList<ItemStack> stacks = NonNullList.withSize(1, ItemStack.EMPTY);
	
	private List<ItemModule> modules = new ArrayList<ItemModule>();
	
	public TileEntityModificationTable()
    {		
		this.storage.setCapacity(0);
		this.storage.setMaxExtract(0);
		
    }
	
	@Override
    public void update()
    {
		super.update();
		//modules.clear();
		
		ItemStack stack = stacks.get(0);
		
		if(!stack.isEmpty() && stack.getItem() instanceof IModificationItem)
		{
			if(!stack.hasTagCompound()) {
				ItemStack copied = stack;
				copied.setTagCompound(new NBTTagCompound());
				copied.getTagCompound().setInteger(ItemSpaceSuit.mod_count, 1);
				stacks.set(0, copied);
			}			
			
			//for(ItemModule module : ((IModificationItem)stack.getItem()).getAvailableModules())			
				//modules.add(module);
			
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
              
        this.stacks = this.readStandardItemsFromNBT(par1NBTTagCompound);     
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
    	super.writeToNBT(par1NBTTagCompound);     
        this.writeStandardItemsToNBT(par1NBTTagCompound, this.stacks);
       
        return par1NBTTagCompound;
    }
    
    @Override
    public String getName()
    {
        return GCCoreUtil.translate("tile.modification_table.name");
    }
    
    @Override
    public int getSizeInventory()
    {
        return this.stacks.size();
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
	protected NonNullList<ItemStack> getContainingItems() {
		return this.stacks;
	}

	@Override
	public boolean shouldUseEnergy() {
		return false;
	}

	@Override
	public EnumFacing getFront() {
		return null;
	}

}
