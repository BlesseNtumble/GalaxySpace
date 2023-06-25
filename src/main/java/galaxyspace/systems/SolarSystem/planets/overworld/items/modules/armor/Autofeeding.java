package galaxyspace.systems.SolarSystem.planets.overworld.items.modules.armor;

import galaxyspace.core.prefab.items.modules.ItemModule;
import galaxyspace.core.util.GSUtils.Module_Type;
import micdoodle8.mods.galacticraft.core.GCItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Autofeeding extends ItemModule{

	@Override
	public String getName() {
		return "autofeeding";
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(GCItems.foodItem, 1, 0);
	}

	@Override
	public EntityEquipmentSlot getEquipmentSlot() {
		return EntityEquipmentSlot.HEAD;
	}

	@Override
	public boolean isActiveModule() {
		return false;
	}

	@Override
	public ItemStack[] getItemsForModule() {
		return new ItemStack[] { new ItemStack(GCItems.foodItem, 1, 0) };
	}

	@Override
	public ItemModule[] getForrbidenModules() {
		return new ItemModule[] { };
	}
	
	@Override
	public int getDischargeCount() { return 4; }
	
	public void onUpdate(World world, EntityPlayer player, ItemStack itemStack, boolean enable) {
		
		if(!world.isRemote && player.getFoodStats().needFood()) {
			for(ItemStack stack : player.inventory.mainInventory) {			
				if(stack.getItem() instanceof ItemFood) {
					player.getFoodStats().addStats((ItemFood)stack.getItem(), stack);
					stack.shrink(1);										
					break;
				}
			}
		}
	}

	@Override
	public Module_Type getType() {
		return Module_Type.SPACESUIT;
	}

}
