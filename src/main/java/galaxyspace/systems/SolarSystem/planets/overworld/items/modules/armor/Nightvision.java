package galaxyspace.systems.SolarSystem.planets.overworld.items.modules.armor;

import galaxyspace.core.prefab.items.modules.ItemModule;
import galaxyspace.core.util.GSUtils.Module_Type;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.world.World;

public class Nightvision extends ItemModule {

	@Override
	public String getName() {
		return "nightvision";
	}

	@Override
	public ItemStack getIcon() {
		ItemStack potion = new ItemStack(Items.POTIONITEM);
        PotionUtils.addPotionToItemStack(potion, PotionType.getPotionTypeForName("long_night_vision"));
        
		return potion;
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
		ItemStack potion = new ItemStack(Items.POTIONITEM);
        PotionUtils.addPotionToItemStack(potion, PotionType.getPotionTypeForName("long_night_vision"));
		return new ItemStack[] { potion };
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
			player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 20*15, 0, true, false));
		else {
			if(player.isPotionActive(MobEffects.NIGHT_VISION)) 
				player.removeActivePotionEffect(MobEffects.NIGHT_VISION);
		}
	}
}

