package galaxyspace.systems.SolarSystem.planets.overworld.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemThermalClothTier2 extends Item{
	
	public ItemThermalClothTier2()
	{
		super();
		this.setUnlocalizedName("ThermalClothT2");
        this.setMaxStackSize(64);
        this.setMaxDamage(0);
        this.setTextureName(GalaxySpace.ASSET_PREFIX + ":" + "thermalClothT2");
	}
 
    @SideOnly(Side.CLIENT)
    @Override
    public CreativeTabs getCreativeTab()
    {
        return GSCreativeTabs.GSItemsTab;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer player, List par2List, boolean b)
    {
         //par2List.add(GCCoreUtil.translate("gui.thermal2.desc1"));
    }
}
