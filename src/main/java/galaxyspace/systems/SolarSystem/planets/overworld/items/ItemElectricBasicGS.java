package galaxyspace.systems.SolarSystem.planets.overworld.items;

import java.util.List;

import javax.annotation.Nullable;

import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.items.ISortableItem;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemElectricBasicGS extends ItemElectricBase implements ISortableItem {
	private float energyMax = 0;
	
	
	public ItemElectricBasicGS(String name, float energy)
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
		return energyMax;
	}

	@Override
	public EnumSortCategoryItem getCategory(int meta) {
		return EnumSortCategoryItem.GENERAL;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
				
	}
}
