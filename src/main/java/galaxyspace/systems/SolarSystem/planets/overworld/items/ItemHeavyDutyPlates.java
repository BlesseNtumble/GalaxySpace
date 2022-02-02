package galaxyspace.systems.SolarSystem.planets.overworld.items;

import java.util.List;

import javax.annotation.Nullable;

import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.core.items.ISortableItem;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryItem;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemHeavyDutyPlates extends Item implements ISortableItem {

	public static String[] names = { 
		"hdp4", // 0
		"hdp5", // 1
		"hdp6"  // 2
	};
	
	public ItemHeavyDutyPlates()
	{
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.setMaxStackSize(64);
		this.setTranslationKey("hdp");
		this.setCreativeTab(GSCreativeTabs.GSItemsTab);
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> list, ITooltipFlag flagIn)
    {		
		int meta = stack.getItemDamage();
		
		switch(meta)
		{
			case 0: list.add(GCCoreUtil.translate("gui.tier4.desc")); break;
			case 1: list.add(GCCoreUtil.translate("gui.tier5.desc")); break;
			case 2: list.add(GCCoreUtil.translate("gui.tier6.desc")); break;
		}
    }
	
	@Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list)    
    {
    	if (tab == GSCreativeTabs.GSItemsTab || tab == CreativeTabs.SEARCH)
        {
	        for (int i = 0; i < this.names.length; i++)
	        {
	            list.add(new ItemStack(this, 1, i));
	        }
        }
    }
	
    @Override
    public String getTranslationKey(ItemStack par1ItemStack)
    {
    	return "item.hdp";
    }

    @Override
    public int getMetadata(int par1)
    {
        return par1;
    }

	@Override
	public EnumSortCategoryItem getCategory(int meta) {
		return EnumSortCategoryItem.PLATE;
	}
}
