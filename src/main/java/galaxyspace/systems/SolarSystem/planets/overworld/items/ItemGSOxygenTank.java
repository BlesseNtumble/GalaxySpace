package galaxyspace.systems.SolarSystem.planets.overworld.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.core.items.ItemOxygenTank;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemGSOxygenTank extends ItemOxygenTank
{
	private boolean epp = false;
	private int tier;
    public ItemGSOxygenTank(int tier, String assetName, int damage, boolean epp)
    {
        super(tier, assetName);
        this.setMaxStackSize(1);
        this.setMaxDamage(damage);
        this.setUnlocalizedName(assetName);
        this.setTextureName(GalaxySpace.ASSET_PREFIX + ":" + assetName);
        this.setNoRepair();
        this.epp = epp;
        this.tier = tier;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, this.getMaxDamage()));
    }

    @Override
    public CreativeTabs getCreativeTab()
    {
        return GSCreativeTabs.GSItemsTab;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer player, List par2List, boolean b)
    {
    	if(epp)
    	{
    		par2List.add(EnumColor.DARK_GREEN + GCCoreUtil.translate("gui.tank.passiveOxygenRegen.desc1"));
    		par2List.add(EnumColor.DARK_GREEN + GCCoreUtil.translate("gui.tank.passiveOxygenRegen.desc2"));        		
    	}
        par2List.add(GCCoreUtil.translate("gui.tank.oxygenRemaining") + ": " + (par1ItemStack.getMaxDamage() - par1ItemStack.getItemDamage()));
    }
    
    public int getTier()
    {
    	return tier;
    }
    
    public boolean getEPP()
    {
    	return epp;
    }
}
