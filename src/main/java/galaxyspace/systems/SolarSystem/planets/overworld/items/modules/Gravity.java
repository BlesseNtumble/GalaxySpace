package galaxyspace.systems.SolarSystem.planets.overworld.items.modules;

import galaxyspace.core.GSItems;
import galaxyspace.core.prefab.items.modules.ItemModule;
import galaxyspace.core.util.GSUtils.Module_Type;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class Gravity extends ItemModule {

	@Override
	public String getName() {
		return "gravity";
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(GSItems.COMPRESSED_PLATES, 1, 1);
	}
	
	@Override
	public EntityEquipmentSlot getEquipmentSlot() {
		return EntityEquipmentSlot.FEET;
	}

	@Override
	public boolean isActiveModule() {
		return true;
	}

	@Override
	public ItemStack[] getItemsForModule() {
		return new ItemStack[] { new ItemStack(GSItems.COMPRESSED_PLATES, 2, 1) };		
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
