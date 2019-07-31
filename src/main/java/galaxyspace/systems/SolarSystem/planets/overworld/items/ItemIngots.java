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
    	"cobaltum_ingot",  	//0    	
    	"magnesium_ingot",	//1
    	"nickel_ingot"		//2
    };

    public ItemIngots()
    {
        super();
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setMaxStackSize(64);
        this.setUnlocalizedName("ingots");  
        this.setCreativeTab(GSCreativeTabs.GSItemsTab);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list)    
    {
    	if (tab == GSCreativeTabs.GSItemsTab || tab == CreativeTabs.SEARCH)
        {
	        for (int i = 0; i < ItemIngots.names.length; i++)
	        {
	            list.add(new ItemStack(this, 1, i));
	        }
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
    	return "item." + ItemIngots.names[par1ItemStack.getItemDamage()];
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
