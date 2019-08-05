package galaxyspace.systems.SolarSystem.planets.overworld.items.modules;

import galaxyspace.core.prefab.items.modules.ItemModule;
import galaxyspace.core.util.GSUtils.Module_Type;
import galaxyspace.systems.SolarSystem.planets.overworld.items.armor.ItemSpaceSuit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Stepassist extends ItemModule {

	private int energy_cost = 0;
	
	@Override
	public String getName() {
		return "stepassist";
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Items.SLIME_BALL, 1, 0);
	}

	@Override
	public EntityEquipmentSlot getEquipmentSlot() {
		return EntityEquipmentSlot.FEET;
	}

	@Override
	public boolean isActiveModule() {
		return false;
	}

	@Override
	public ItemStack[] getItemsForModule() {
		return new ItemStack[] { new ItemStack(Items.SLIME_BALL, 4, 0) };
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
	public int getDischargeCount() { return energy_cost; }
	
	@Override
	public void onUpdate(World world, EntityPlayer player, ItemStack itemStack, boolean enable) 
	{
		float step = 0F;		
		
		if(ItemSpaceSuit.pressedKey[1]) {
			step = 0.5F;
			energy_cost = 4;
		}
		else {
			step = 0F;
			energy_cost = 0;
		}
		
						
		player.stepHeight += step;
	}

}
