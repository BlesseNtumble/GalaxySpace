package galaxyspace.systems.SolarSystem.planets.overworld.items.modules;

import galaxyspace.core.prefab.items.modules.ItemModule;
import galaxyspace.core.util.GSUtils.Module_Type;
import micdoodle8.mods.galacticraft.core.GCItems;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class SensorLens extends ItemModule {

	@Override
	public String getName() {
		return "sensor";
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(GCItems.sensorGlasses);
	}

	@Override
	public EntityEquipmentSlot getEquipmentSlot() {
		return EntityEquipmentSlot.HEAD;
	}

	@Override
	public boolean isActiveModule() {
		return true;
	}

	@Override
	public ItemStack[] getItemsForModule() {
		return new ItemStack[] { new ItemStack(GCItems.sensorGlasses, 1, 0) };
	}

	@Override
	public ItemModule[] getForrbidenModules() {
		return new ItemModule[] { new Nightvision() };
	}

	@Override
	public Module_Type getType() {
		return Module_Type.SPACESUIT;
	}
	
	@Override
	public int getDischargeCount() { return 2; }
}
