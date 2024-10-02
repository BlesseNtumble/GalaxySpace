package galaxyspace.core.handler;

import cpw.mods.fml.common.IFuelHandler;
import galaxyspace.core.registers.items.GSItems;
import net.minecraft.item.ItemStack;

public class GSFuelHandler implements IFuelHandler {

	@Override
	public int getBurnTime(ItemStack fuel) {		
		if (fuel.getItem() == GSItems.BasicItems && fuel.getItemDamage() == 13) return 5200;
		return 0;
	}

}
