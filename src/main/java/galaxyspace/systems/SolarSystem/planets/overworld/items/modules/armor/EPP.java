package galaxyspace.systems.SolarSystem.planets.overworld.items.modules.armor;

import galaxyspace.core.GSItems;
import galaxyspace.core.prefab.items.modules.ItemModule;
import galaxyspace.core.util.GSUtils.Module_Type;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

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
		return new ItemStack[] { new ItemStack(GSItems.OXYGENTANK_TIER_EPP, 1, OreDictionary.WILDCARD_VALUE) };
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
