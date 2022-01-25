package galaxyspace.systems.SolarSystem.planets.overworld.items;

import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.core.items.ISortableItem;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemNuggets extends Item implements ISortableItem
{

    public ItemNuggets()
    {
        super();
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setMaxStackSize(64);
        this.setUnlocalizedName("nuggets");  
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
    	return "item." + ItemIngots.names[par1ItemStack.getItemDamage()] + "_nugget";
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
