package galaxyspace.systems.SolarSystem.planets.overworld.items.modules;

import galaxyspace.core.GSItems;
import galaxyspace.core.prefab.items.modules.ItemModule;
import galaxyspace.core.util.GSUtils.Module_Type;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class Protection extends ItemModule {

	@Override
	public String getName() {
		return "protection";
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(GSItems.COBALT_CHEST, 1, 0);
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
		return new ItemStack[] { new ItemStack(GSItems.INGOTS, 16, 0)};		
	}

	@Override
	public ItemModule[] getForrbidenModules() {
		return new ItemModule[] {new Energy()};
	}

	@Override
	public Module_Type getType() {
		return Module_Type.SPACESUIT;
	}
	
	@Override
	public int getDischargeCount() { return 0; }
	
}
