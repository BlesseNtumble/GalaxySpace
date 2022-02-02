package galaxyspace.systems.SolarSystem.planets.overworld.items;

import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.core.items.ISortableItem;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemIngots extends Item implements ISortableItem
{
    public static String[] names = 
    { 
    	"cobaltum",  	//0    	
    	"magnesium",	//1
    	"nickel"		//2
    };

    public ItemIngots()
    {
        super();
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setMaxStackSize(64);
        this.setTranslationKey("ingots");  
        this.setCreativeTab(GSCreativeTabs.GSItemsTab);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list)    
    {
    	if (tab == GSCreativeTabs.GSItemsTab || tab == CreativeTabs.SEARCH)
        {
	        for (int i = 0; i < names.length; i++)
	        {
	            list.add(new ItemStack(this, 1, i));
	        }
        }
    }

    @Override
    public String getTranslationKey(ItemStack par1ItemStack)
    {
    	return "item." + names[par1ItemStack.getItemDamage()] + "_ingot";
    }

    @Override
    public int getMetadata(int par1)
    {
        return par1;
    }

	@Override
	public EnumSortCategoryItem getCategory(int meta) {
		return EnumSortCategoryItem.INGOT;
	}

}
