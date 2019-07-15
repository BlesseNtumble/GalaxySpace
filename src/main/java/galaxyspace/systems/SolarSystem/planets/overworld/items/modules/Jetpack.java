package galaxyspace.systems.SolarSystem.planets.overworld.items.modules;

import galaxyspace.core.prefab.items.modules.ItemModule;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.util.GSUtils.Module_Type;
import micdoodle8.mods.galacticraft.core.GCItems;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class Jetpack extends ItemModule {

	@Override
	public String getName() {
		return "jetpack";
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(GSItems.JETPACK, 1, GSItems.JETPACK.getMaxDamage());
	}

	@Override
	public EntityEquipmentSlot getEquipmentSlot() {
		return EntityEquipmentSlot.CHEST;
	}

	@Override
	public boolean isActiveModule() {
		return true;
	}

	@Override
	public ItemStack[] getItemsForModule() {
		return new ItemStack[] { new ItemStack(GSItems.JETPACK, 1, GSItems.JETPACK.getMaxDamage()) };
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