package galaxyspace.systems.SolarSystem.planets.overworld.items.modules;

import galaxyspace.core.prefab.items.modules.ItemModule;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.util.GSUtils.Module_Type;
import net.minecraft.item.ItemStack;

public class Gravity extends ItemModule {

	@Override
	public String getName() {
		return "gravity";
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(GSItems.CompressedPlates, 1, 1);
	}
	
	@Override
	public int getEquipmentSlot() {
		return 3;
	}

	@Override
	public boolean isActiveModule() {
		return true;
	}

	@Override
	public ItemStack[] getItemsForModule() {
		return new ItemStack[] { new ItemStack(GSItems.CompressedPlates, 2, 1) };		
	}

	@Override
	public ItemModule[] getForrbidenModules() {
		return new ItemModule[] {new Jump() };
	}

	@Override
	public Module_Type getType() {
		return Module_Type.SPACESUIT;
	}
	
	@Override
	public int getDischargeCount() { return 2; }
	
}
