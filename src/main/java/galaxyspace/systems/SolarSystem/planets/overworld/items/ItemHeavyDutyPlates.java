package galaxyspace.systems.SolarSystem.planets.overworld.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemHeavyDutyPlates extends Item{
	
	public static String[] names = 
	    	{ 
	    		"HeavyDutyPlate4", 		//0 
	    		"HeavyDutyPlate5", 		//1 
	    		"HeavyDutyPlate6" 		//2
	    	};
	protected IIcon[] icons = new IIcon[this.names.length];

	public ItemHeavyDutyPlates()
	{
		super();
		this.setUnlocalizedName("HeavyDutyPlate");
        this.setMaxStackSize(64);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
	}
 
    @SideOnly(Side.CLIENT)
    @Override
    public CreativeTabs getCreativeTab()
    {
        return GSCreativeTabs.GSItemsTab;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        int i = 0;
        int n;
        for (String name : this.names)
        {
        	n = 4 + i;
            this.icons[i++] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "hdp" + n);
        }
    }

    @Override
    public IIcon getIconFromDamage(int damage)
    {
        if (this.icons.length > damage)
        {
            return this.icons[damage];
        }

        return super.getIconFromDamage(damage);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int i = 0; i < this.names.length; i++)
        {
            par3List.add(new ItemStack(par1, 1, i));
        }
    }

  /*  @Override
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        if (this.icons.length > par1ItemStack.getItemDamage())
        {
            return "item." + this.names[par1ItemStack.getItemDamage()];
        }

        return "unnamed";
    }*/

    @Override
    public int getMetadata(int par1)
    {
        return par1;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer player, List par2List, boolean b)
    {
    	int n = is.getItemDamage() + 4; 
        par2List.add(GCCoreUtil.translate("gui.tier" + n + ".desc"));
    }
    
}
