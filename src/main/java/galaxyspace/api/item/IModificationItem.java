package galaxyspace.api.item;

import galaxyspace.core.prefab.items.modules.ItemModule;
import galaxyspace.core.util.GSUtils.Module_Type;
import net.minecraft.item.ItemStack;

public interface IModificationItem {
	
	public Module_Type getType(ItemStack stack);	
	public ItemModule[] getAvailableModules();	
	public int getModificationCount(ItemStack stack);
	
	default public boolean hasModule(ItemModule module, ItemStack stack)
	{
		return stack.hasTagCompound() && stack.getTagCompound().hasKey(module.getName());
	}
}
