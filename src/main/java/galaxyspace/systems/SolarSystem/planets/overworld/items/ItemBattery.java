package galaxyspace.systems.SolarSystem.planets.overworld.items;

import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.items.ISortableItem;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemBattery extends ItemElectricBase implements ISortableItem {

	private float energyMax = 0;
	public ItemBattery(String name, float energy)
	{
		super();
		this.setTranslationKey(name);
		this.setMaxStackSize(1);
		this.setCreativeTab(GSCreativeTabs.GSItemsTab);
	    this.energyMax = energy;
	}
	
	@Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list)    
    {
    	if (tab == GSCreativeTabs.GSItemsTab || tab == CreativeTabs.SEARCH)
        {
    		list.add(new ItemStack(this, 1, 0));
    		list.add(new ItemStack(this, 1, this.getMaxDamage()));
        }
    }
	
	@Override
	public float getMaxElectricityStored(ItemStack theItem) {
		return this.energyMax;
	}

	@Override
	public EnumSortCategoryItem getCategory(int meta) {
		return EnumSortCategoryItem.GENERAL;
	}

	@Override
	public int getItemStackLimit(ItemStack stack) {
		if (stack.getItemDamage() < 100 || stack.hasTagCompound() && stack.getTagCompound().hasKey("electricity")) {
			return 1;
		}
		return this.getItemStackLimit();
	}
}
