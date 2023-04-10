package galaxyspace.systems.SolarSystem.planets.overworld.items.modules.armor;

import galaxyspace.core.prefab.items.modules.ItemModule;
import galaxyspace.core.util.GSUtils.Module_Type;
import galaxyspace.systems.SolarSystem.planets.overworld.items.armor.ItemSpaceSuit;
import micdoodle8.mods.galacticraft.core.GCItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Speed extends ItemModule {

	@Override
	public String getName() {
		return "speed";
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(GCItems.rocketEngine, 1, 1);
	}

	@Override
	public EntityEquipmentSlot getEquipmentSlot() {
		return EntityEquipmentSlot.LEGS;
	}

	@Override
	public boolean isActiveModule() {
		return true;
	}

	@Override
	public ItemStack[] getItemsForModule() {
		return new ItemStack[] { new ItemStack(GCItems.rocketEngine, 2, 1) };
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
	public int getDischargeCount() { return 4; }
	
	@Override
	public void onUpdate(World world, EntityPlayer player, ItemStack itemStack, boolean enable) 
	{
		if(enable && ItemSpaceSuit.pressedKey[1])
		{
			float speed = 0.12f;
			if (player.onGround && !player.isInWater())								
				player.moveRelative(0.0f, 0.0f, 1.0f, speed);
		}
	}

}

