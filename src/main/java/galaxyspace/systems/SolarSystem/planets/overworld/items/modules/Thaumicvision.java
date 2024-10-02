package galaxyspace.systems.SolarSystem.planets.overworld.items.modules;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import galaxyspace.core.prefab.items.modules.ItemModule;
import galaxyspace.core.util.GSUtils.Module_Type;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Thaumicvision extends ItemModule {
	Item i = GameRegistry.findItem("Thaumcraft", "ItemGoggles");

	@Override
	public String getName() {
		return "thaumicvision";
	}

	@Override
	public ItemStack getIcon() {

		return new ItemStack(i);
	}

	@Override
	public int getEquipmentSlot() {
		return 0;
	}

	@Override
	public boolean isActiveModule() {
		return false;
	}

	@Override
	public ItemStack[] getItemsForModule() {
		return new ItemStack[] { new ItemStack(i) };
	}

	@Override
	public ItemModule[] getForrbidenModules() {
		return null;
	}

	@Override
	public Module_Type getType() {
		return Module_Type.SPACESUIT;
	}
	
	@Override
	public boolean isLoading()
	{
		return Loader.isModLoaded("Thaumcraft");
	}
}
