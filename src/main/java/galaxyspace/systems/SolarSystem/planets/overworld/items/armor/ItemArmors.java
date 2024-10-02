package galaxyspace.systems.SolarSystem.planets.overworld.items.armor;

import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemArmors extends ItemArmor
{
	protected String textureName = GalaxySpace.ASSET_PREFIX + ":" + "textures/model/armor/";
	
    public ItemArmors(ArmorMaterial material, int proxyIndex, int armorIndex, String assetSuffix)
    {
        super(material, proxyIndex, armorIndex);
        this.setUnlocalizedName(assetSuffix);
        this.setTextureName(GalaxySpace.ASSET_PREFIX + ":" + "armors/" + assetSuffix);
        this.textureName += (armorType == 0 || armorType == 1 || armorType == 3) ? material.name().toLowerCase() + "_1.png" : material.name().toLowerCase() + "_2.png";
    }

    @Override
    public CreativeTabs getCreativeTab()
    {
        return GSCreativeTabs.GSArmorTab;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {       
        return this.textureName;
    }	
	
}
