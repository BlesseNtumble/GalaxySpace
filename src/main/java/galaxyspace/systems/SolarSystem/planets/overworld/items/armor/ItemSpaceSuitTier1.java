package galaxyspace.systems.SolarSystem.planets.overworld.items.armor;

import galaxyspace.core.prefab.items.modules.ItemModule;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.Energy;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.Gravity;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.Jetpack;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.Jump;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.Nightvision;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.Protection;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.SensorLens;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.Speed;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.Stepassist;
import net.minecraft.inventory.EntityEquipmentSlot;

public class ItemSpaceSuitTier1 extends ItemSpaceSuit{

	public ItemSpaceSuitTier1(ArmorMaterial materialIn, EntityEquipmentSlot armorIndex) {
		super("space_suit", materialIn, armorIndex);
	}

	@Override
	public ItemModule[] getAvailableModules() {
		return new ItemModule[] { new SensorLens(), new Nightvision(), new Jetpack(), new Speed(), new Gravity(), new Stepassist(), new Jump(), new Energy(), new Protection() };
	}
}
