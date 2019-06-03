package galaxyspace.core.prefab.items;

import galaxyspace.core.util.GSCreativeTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSpade;

public class ItemSpadeGS extends ItemSpade {

	public ItemSpadeGS(String assetName, ToolMaterial material) {
		super(material);
		this.setUnlocalizedName(assetName);
	}
	
	@Override
    public CreativeTabs getCreativeTab()
    {
        return GSCreativeTabs.GSArmorTab;
    }

}
