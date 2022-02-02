package galaxyspace.core.prefab.items;

import galaxyspace.core.util.GSCreativeTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSword;

public class ItemSwordGS extends ItemSword {

	public ItemSwordGS(String assetName, ToolMaterial material) {
		super(material);
		this.setTranslationKey(assetName);
	}

	@Override
    public CreativeTabs getCreativeTab()
    {
        return GSCreativeTabs.GSArmorTab;
    }

}
