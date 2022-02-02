package galaxyspace.systems.SolarSystem.planets.overworld.items.armor;

import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.items.ISortableItem;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemArmorGS extends ItemArmor implements ISortableItem {

	protected String textureName;
	
	public ItemArmorGS(String assetSuffix, ArmorMaterial material, EntityEquipmentSlot equipmentSlotIn) {
		super(material, 0, equipmentSlotIn);
		this.setTranslationKey(assetSuffix);
		this.textureName = GalaxySpace.ASSET_PREFIX + ":" + "textures/model/armor/" +( (equipmentSlotIn == EntityEquipmentSlot.HEAD || equipmentSlotIn == EntityEquipmentSlot.CHEST || equipmentSlotIn == EntityEquipmentSlot.FEET) ? material.toString().toLowerCase() + "_1.png" : material.toString().toLowerCase() + "_2.png");
	}
	
	@Override
    public CreativeTabs getCreativeTab()
    {
        return GSCreativeTabs.GSArmorTab;
    }
	
	@Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    {
		return textureName;
    }
	
	@Override
    public EnumSortCategoryItem getCategory(int meta)
    {
        return EnumSortCategoryItem.ARMOR;
    }
	
	@Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return repair.getItem() == GCItems.basicItem && repair.getItemDamage() == 9;
    }
	
}
