package galaxyspace.systems.SolarSystem.planets.overworld.items.modules;

import asmodeuscore.core.astronomy.SpaceData.Engine_Type;
import galaxyspace.core.prefab.items.modules.ItemModule;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.util.GSUtils.Module_Type;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class SublightEngine extends ItemModule {

	@Override
	public String getName() {
		return Engine_Type.SUBLIGHT_ENGINE.getName();
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(GSItems.ROCKET_MODULES, 1, 6);
	}

	@Override
	public EntityEquipmentSlot getEquipmentSlot() {
		return null;
	}

	@Override
	public boolean isActiveModule() {
		return false;
	}

	@Override
	public ItemStack[] getItemsForModule() {
		return new ItemStack[] { new ItemStack(GSItems.ROCKET_MODULES, 1, 6) };
	}

	@Override
	public ItemModule[] getForrbidenModules() {
		return null;
	}

	@Override
	public Module_Type getType() {
		return Module_Type.ROCKET;
	}
}
