package galaxyspace.core.prefab.items;

import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemAxe;

public class ItemAxeGS extends ItemAxe
{
    public ItemAxeGS(String assetName, ToolMaterial material)
    {
        super(material);
        this.setUnlocalizedName(assetName);
        this.setTextureName(GalaxySpace.ASSET_PREFIX + ":tools/" + assetName);
    }

    @Override
    public CreativeTabs getCreativeTab()
    {
        return GSCreativeTabs.GSArmorTab;
    }

}
