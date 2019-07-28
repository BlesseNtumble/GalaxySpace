package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.items;

import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.core.items.ISortableItem;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;

public class ItemFoodBR extends ItemFood implements ISortableItem {

	public enum BR_Food implements IStringSerializable{
		
		YELLOW_FRUITS("yellow_fruits", 4, 0.4F);
		
		private String name;
		private int amount;
		private float saturation;
		
		BR_Food(String name, int amount, float saturation)
		{
			this.name = name;
			this.amount = amount;
			this.saturation = saturation;
		}
		
		@Override
		public String getName() { return this.name; }
		public int getAmount() { return this.amount; }  
		public float getSaturation() { return this.saturation; }  
		public static BR_Food byMetadata(int meta) { return values()[meta]; }
	}
	
	public ItemFoodBR() {
		super(0, false);
		
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.setMaxStackSize(64);
		this.setUnlocalizedName("br_foods");
		this.setCreativeTab(GSCreativeTabs.GSItemsTab);
	}

	@Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list)    
    {
    	if (tab == GSCreativeTabs.GSItemsTab || tab == CreativeTabs.SEARCH)
        {
	        for (int i = 0; i < BR_Food.values().length; i++)
	        {
	        	if(!BR_Food.byMetadata(i).getName().equals("null"))
	        		list.add(new ItemStack(this, 1, i));
	        }
        }
    }
	
	@Override
	public int getHealAmount(ItemStack stack)
    {
        return BR_Food.byMetadata(stack.getItemDamage()).getAmount();
    }

	@Override
    public float getSaturationModifier(ItemStack stack)
    {
        return BR_Food.byMetadata(stack.getItemDamage()).getSaturation();
    }
    
	@Override
    public String getUnlocalizedName(ItemStack stack)
    {
    	return "item." + BR_Food.byMetadata(stack.getItemDamage()).getName();
    }
	
	@Override
	public int getMetadata(int meta) {
		return meta;
	}
	
	@Override
	public EnumSortCategoryItem getCategory(int meta) {
		return EnumSortCategoryItem.GENERAL;
	}	
}
