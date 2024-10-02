package galaxyspace.systems.SolarSystem.planets.overworld.items.modules;

import galaxyspace.core.prefab.items.modules.ItemModule;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.util.GSUtils.Module_Type;
import net.minecraft.item.ItemStack;

public class Energy extends ItemModule {

	@Override
	public String getName() {
		return "energy";
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(GSItems.AdvancedBattery, 1, GSItems.AdvancedBattery.getMaxDamage());
	}

	@Override
	public int getEquipmentSlot() {
		return -1;
	}

	@Override
	public boolean isActiveModule() {
		return false;
	}

	@Override
	public ItemStack[] getItemsForModule() {
		return new ItemStack[] { new ItemStack(GSItems.AdvancedBattery, 1, GSItems.AdvancedBattery.getMaxDamage()) };
	}

	@Override
	public ItemModule[] getForrbidenModules() {
		return null;
	}

	@Override
	public Module_Type getType() {
		return Module_Type.SPACESUIT;
	}
}
