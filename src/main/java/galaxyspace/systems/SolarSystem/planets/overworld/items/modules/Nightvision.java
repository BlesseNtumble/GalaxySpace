package galaxyspace.systems.SolarSystem.planets.overworld.items.modules;

import galaxyspace.core.prefab.items.modules.ItemModule;
import galaxyspace.core.util.GSUtils.Module_Type;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class Nightvision extends ItemModule {

	@Override
	public String getName() {
		return "nightvision";
	}

	@Override
	public ItemStack getIcon() {
		
		return new ItemStack(Items.potionitem, 1, 8262);
	}

	@Override
	public int getEquipmentSlot() {
		return 0;
	}

	@Override
	public boolean isActiveModule() {
		return true;
	}

	@Override
	public ItemStack[] getItemsForModule() {
		return new ItemStack[] { new ItemStack(Items.potionitem, 1, 8262) };
	}

	@Override
	public ItemModule[] getForrbidenModules() {
		return new ItemModule[] { new SensorLens() };
	}

	@Override
	public Module_Type getType() {
		return Module_Type.SPACESUIT;
	}

	@Override
	public int getDischargeCount() { return 4; }
	
	@Override
	public void onUpdate(World world, EntityPlayer player, ItemStack itemStack, boolean enable) 
	{
		if(enable)
			player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 20*15, 0, false));
		else {
			if(player.isPotionActive(Potion.nightVision.id)) 
				player.removePotionEffect(Potion.nightVision.id);
		}
	}
}

