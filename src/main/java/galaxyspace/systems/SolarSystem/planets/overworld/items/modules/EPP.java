package galaxyspace.systems.SolarSystem.planets.overworld.items.modules;

import galaxyspace.core.prefab.items.modules.ItemModule;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.util.GSUtils.Module_Type;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EPP extends ItemModule{

	@Override
	public String getName() {
		return "epp";
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(GSItems.OXYGENTANK_TIER_EPP, 1, 1);
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
		return new ItemStack[] { new ItemStack(GSItems.OXYGENTANK_TIER_EPP, 1, GSItems.OXYGENTANK_TIER_EPP.getMaxDamage()) };
	}

	@Override
	public ItemModule[] getForrbidenModules() {
		return null;
	}

	@Override
	public Module_Type getType() {
		return Module_Type.OXYGEN_TANK;
	}

}
