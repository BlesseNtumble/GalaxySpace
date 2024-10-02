package galaxyspace.systems.SolarSystem.planets.overworld.items.modules;

import galaxyspace.core.prefab.items.modules.ItemModule;
import galaxyspace.core.util.GSUtils.Module_Type;
import galaxyspace.systems.SolarSystem.planets.overworld.items.armor.ItemSpaceArmors;
import micdoodle8.mods.galacticraft.core.items.GCItems;
import net.minecraft.entity.player.EntityPlayer;
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
	public int getEquipmentSlot() {
		return 2;
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
		if(enable && ItemSpaceArmors.pressedKey[1])
		{
			float speed = 0.12f;
			if (player.onGround && !player.isInWater())		
				player.moveFlying(0.0F, 0.4F, speed);				
		}
	}

}

