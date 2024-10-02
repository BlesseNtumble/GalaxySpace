package galaxyspace.api.item;

import galaxyspace.core.util.GSUtils.Module_Type;
import net.minecraft.item.ItemStack;

public interface IModificationItem {
	
	public Module_Type getType(ItemStack stack);	
	public int getModificationCount(ItemStack stack);
}
