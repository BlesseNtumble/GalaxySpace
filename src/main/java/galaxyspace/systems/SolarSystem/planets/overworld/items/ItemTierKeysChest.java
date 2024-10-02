package galaxyspace.systems.SolarSystem.planets.overworld.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.api.item.IKeyItem;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemTierKeysChest extends Item implements IKeyItem{
	
    public static String[] keyTypes = { "4", "5" };
    public IIcon[] keyIcons = new IIcon[this.keyTypes.length];
	
	public ItemTierKeysChest()
	{
		super();
		this.setUnlocalizedName("TierKeys");
	    this.setMaxStackSize(1);
	    this.setMaxDamage(0);
	    this.setHasSubtypes(true);
	    this.setTextureName("arrow");
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public CreativeTabs getCreativeTab()
	{
	    return GSCreativeTabs.GSItemsTab;
	}
	/*
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister)
	{
	    int i = 0;
	
	    for (final String name : this.keyTypes)
	    {
	        this.keyIcons[i++] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + name + "KeyChest");
	    }
	}
	
	@Override
	public IIcon getIconFromDamage(int damage)
	{
	    if (this.keyIcons.length > damage)
	    {
	        return this.keyIcons[damage];
	    }
	
	    return super.getIconFromDamage(damage);
	}*/
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
	{
	    for (int i = 0; i < this.keyTypes.length; i++)
	    {
	        par3List.add(new ItemStack(par1, 1, i));
	    }
	}
	
	@Override
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
         return "item.TierKeys";
      
    }
	
	@Override
	public int getMetadata(int par1)
	{
	    return par1;
	}
	
	@Override
	public int getTier(ItemStack stack) {
		
		return 4 + stack.getItemDamage();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack is, EntityPlayer player, List par2List, boolean b)
	{
		int n = 4+is.getItemDamage();
	    par2List.add(GCCoreUtil.translate("gui.tier"+n+".desc"));
	}

}
