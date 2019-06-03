package galaxyspace.core.handler;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

public class GSFuelHandler implements IFuelHandler {

	@Override
	public int getBurnTime(ItemStack fuel) {
	//if (fuel.getItem() == GSItems.SulfurVenus) return 3200;
		return 0;
	}

}
