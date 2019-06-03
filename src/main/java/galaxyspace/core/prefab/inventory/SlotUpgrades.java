package galaxyspace.core.prefab.inventory;

import micdoodle8.mods.galacticraft.core.inventory.SlotSpecific;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotUpgrades extends SlotSpecific{

	private int count = 0;
	
	public SlotUpgrades(IInventory par2IInventory, int id, int x, int y, int count, ItemStack... itemStacks)
    {
        super(par2IInventory, id, x, y, itemStacks);
        this.count = count;
    }
	
	public SlotUpgrades(IInventory inventory, int id, int x, int y, int count, Class... validClasses) {
		super(inventory, id, x, y, validClasses);
		this.count = count;
	}

	@Override
	public int getSlotStackLimit() {
		
		if(this.count > 0) 
			return this.count;
		
		return this.inventory.getInventoryStackLimit();
	}
}
