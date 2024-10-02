package galaxyspace.core.prefab.items;

import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSword;

public class ItemSwordGS extends ItemSword
{
    public ItemSwordGS(String assetName, ToolMaterial material)
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
