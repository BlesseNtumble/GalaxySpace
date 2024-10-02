package galaxyspace.systems.SolarSystem.moons.enceladus.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemUnknowCrystal extends Item {
	
public ItemUnknowCrystal()
	{
		super();
		this.setUnlocalizedName("UnknowCrystal");
	    this.setMaxStackSize(64);
	    this.setMaxDamage(0);
	    this.setTextureName(GalaxySpace.ASSET_PREFIX + ":" + "unknowcrystal");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public CreativeTabs getCreativeTab()
	{
	    return GSCreativeTabs.GSItemsTab;
	}

}
