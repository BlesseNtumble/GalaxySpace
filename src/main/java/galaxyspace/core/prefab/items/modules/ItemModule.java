package galaxyspace.core.prefab.items.modules;

import galaxyspace.core.util.GSUtils.Module_Type;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class ItemModule {

	public abstract String getName();
	public abstract ItemStack getIcon();
	public abstract EntityEquipmentSlot getEquipmentSlot();
	public abstract boolean isActiveModule();
	public abstract ItemStack[] getItemsForModule();
	public abstract ItemModule[] getForrbidenModules();
	public abstract Module_Type getType();
	
	public int getDischargeCount() { return 0; }
	public void onUpdate(World world, EntityPlayer player, ItemStack itemStack) {}
	public void onUpdate(World world, EntityPlayer player, ItemStack itemStack, boolean enable) {}
	public boolean isLoading() { return true; }
}
