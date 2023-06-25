package galaxyspace.systems.SolarSystem.planets.overworld.items.armor;

import java.util.List;

import asmodeuscore.api.item.IItemPressurized;
import asmodeuscore.api.item.IItemRadiation;
import galaxyspace.core.prefab.items.modules.ItemModule;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.armor.Autofeeding;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.armor.Energy;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.armor.Gravity;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.armor.Jetpack;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.armor.Nightvision;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.armor.Protection;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.armor.SensorLens;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.armor.Stepassist;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.armor.WaterBreathing;
import micdoodle8.mods.galacticraft.api.item.IArmorCorrosionResistant;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;

public class ItemSpaceSuitTier1 extends ItemSpaceSuit implements IArmorCorrosionResistant, IItemPressurized, IItemRadiation{

	public ItemSpaceSuitTier1(ArmorMaterial materialIn, EntityEquipmentSlot armorIndex) {
		super("space_suit", materialIn, armorIndex);
	}

	@Override
	public ItemModule[] getAvailableModules() {
		return new ItemModule[] { new Autofeeding(), new SensorLens(), new WaterBreathing(), new Nightvision(), new Jetpack(), new Gravity(), new Stepassist(), new Energy(), new Protection() };
	}
	
	@Override
	public float getSolarRadiationProtectModify() {
		return 1.0F;
	}

	@Override
	public void addInfo(ItemStack stack, World world, List<String> list, ITooltipFlag flagIn) {
		list.add(GCCoreUtil.translate("gui.spacesuit.type") + " " + EnumColor.YELLOW +  GCCoreUtil.translate("gui.spacesuit.type.2"));
		list.addAll(FMLClientHandler.instance().getClient().fontRenderer.listFormattedStringToWidth(GCCoreUtil.translate("gui.spacesuit.desc.heavy"), 250));	
	}
	
	@Override
	public float getMaxElectricityStored(ItemStack stack) {		
		boolean energy = stack.hasTagCompound() && stack.getTagCompound().hasKey("energy");
		return energy ? 180000.0F : 90000.0F;		
	}
	
}
