package galaxyspace.core.prefab.items;

import galaxyspace.core.util.GSCreativeTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemHoe;

public class ItemHoeGS extends ItemHoe{

	public ItemHoeGS(String assetName, ToolMaterial material) {
		super(material);
		this.setUnlocalizedName(assetName);
	}
	
	@Override
    public CreativeTabs getCreativeTab()
    {
        return GSCreativeTabs.GSArmorTab;
    }
}
