package galaxyspace.systems.SolarSystem.planets.overworld.items.modules;

import galaxyspace.GalaxySpace;
import galaxyspace.core.prefab.items.modules.ItemModule;
import galaxyspace.core.util.GSUtils.Module_Type;
import galaxyspace.systems.SolarSystem.planets.overworld.items.armor.ItemSpaceSuit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class Jump extends ItemModule {
		
	@Override
	public String getName() {
		return "jump";
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Blocks.PISTON, 1, 0);
	}

	@Override
	public EntityEquipmentSlot getEquipmentSlot() {
		return EntityEquipmentSlot.FEET;
	}

	@Override
	public boolean isActiveModule() {
		return true;
	}

	@Override
	public ItemStack[] getItemsForModule() {
		return new ItemStack[] { new ItemStack(Blocks.PISTON, 2, 0) };
	}

	@Override
	public ItemModule[] getForrbidenModules() {
		return new ItemModule[] {new Gravity() };
	}

	@Override
	public Module_Type getType() {
		return Module_Type.SPACESUIT;
	}
}
