package galaxyspace.systems.SolarSystem.planets.overworld.items.armor;

import java.util.List;

import asmodeuscore.api.item.IItemRadiation;
import galaxyspace.GalaxySpace;
import galaxyspace.core.prefab.items.modules.ItemModule;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.armor.Autofeeding;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.armor.Energy;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.armor.Jetpack;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.armor.Jump;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.armor.Nightvision;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.armor.SensorLens;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.armor.Speed;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.armor.WaterBreathing;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSpaceSuitLight extends ItemSpaceSuit implements IItemRadiation{

	protected String textureName;
	
	public ItemSpaceSuitLight(ArmorMaterial materialIn, EntityEquipmentSlot armorIndex) {
		super("space_suit_light", materialIn, armorIndex);
		this.textureName = GalaxySpace.ASSET_PREFIX + ":" + "textures/model/armor/" +( (armorIndex == EntityEquipmentSlot.HEAD || armorIndex == EntityEquipmentSlot.CHEST || armorIndex == EntityEquipmentSlot.FEET) ? materialIn.toString().toLowerCase() + "_1.png" : materialIn.toString().toLowerCase() + "_2.png");
		
	}

	@Override
	public ItemModule[] getAvailableModules() {
		return new ItemModule[] { new Autofeeding(), new WaterBreathing(), new SensorLens(), new Nightvision(), new Jetpack(), new Jump(), new Energy(), new Speed() };
	}
	
	@Override
	public int getModificationCount(ItemStack stack) {
		return 1;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
		return null;
	}
	
	@Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    {
		return textureName;
    }

	@Override
	public float getSolarRadiationProtectModify() {
		return 1.0F;
	}

	@Override
	public void addInfo(ItemStack stack, World world, List<String> list, ITooltipFlag flagIn) {
		list.add(GCCoreUtil.translate("gui.spacesuit.type") + " " + EnumColor.YELLOW +  GCCoreUtil.translate("gui.spacesuit.type.1"));
		list.addAll(FMLClientHandler.instance().getClient().fontRenderer.listFormattedStringToWidth(GCCoreUtil.translate("gui.spacesuit.desc.light"), 250));	
				
	}
}
