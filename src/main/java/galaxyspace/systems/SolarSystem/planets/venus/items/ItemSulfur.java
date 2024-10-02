package galaxyspace.systems.SolarSystem.planets.venus.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemSulfur extends Item{
	
	public ItemSulfur()
	{
		super();
		this.setUnlocalizedName("Sulfur");
        this.setMaxStackSize(64);
        this.setMaxDamage(0);
        this.setTextureName(GalaxySpace.ASSET_PREFIX + ":" + "sulfur");
	}
 
    @SideOnly(Side.CLIENT)
    @Override
    public CreativeTabs getCreativeTab()
    {
        return GSCreativeTabs.GSItemsTab;
    }

}
