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

public class ItemRocketParts extends Item implements ISortableItem {
	public static String[] names = { 
		//"rocket_cone_tier_2", "rocket_body_tier_2", "rocket_engine_tier_2", "rocket_booster_tier_2", "rocket_stabiliser_tier_2",

		"rocket_cone_tier_3", "rocket_body_tier_3", "rocket_engine_tier_3", "rocket_booster_tier_3", "rocket_stabiliser_tier_3",

		"rocket_cone_tier_4", "rocket_body_tier_4", "rocket_engine_tier_4", "rocket_booster_tier_4", "rocket_stabiliser_tier_4",

		"rocket_cone_tier_5", "rocket_body_tier_5", "rocket_engine_tier_5", "rocket_booster_tier_5", "rocket_stabiliser_tier_5",

		"rocket_cone_tier_6", "rocket_body_tier_6", "rocket_engine_tier_6", "rocket_booster_tier_6", "rocket_stabiliser_tier_6"
	};
	
	public ItemRocketParts() {
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.setMaxStackSize(64);
		this.setUnlocalizedName("rocket_parts");
		this.setCreativeTab(GSCreativeTabs.GSItemsTab);		
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
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
		for(int i = 0; i < 5; i++)
        {
        	if(stack.getItemDamage() == i) list.add(GCCoreUtil.translate("gui.tier3.desc"));
        	else if(stack.getItemDamage() == i+5) list.add(GCCoreUtil.translate("gui.tier4.desc"));
        	else if(stack.getItemDamage() == i+10) list.add(GCCoreUtil.translate("gui.tier5.desc"));
        	else if(stack.getItemDamage() == i+15) list.add(GCCoreUtil.translate("gui.tier6.desc"));
        }
	}
	
	@Override
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
    	for(int k = 0; k <= 4; k++)
        {
    		if(par1ItemStack.getItemDamage() == k*5) return "item.rocket_cone";
    		if(par1ItemStack.getItemDamage() == k*5 + 1) return "item.rocket_body";
    		if(par1ItemStack.getItemDamage() == k*5 + 2) return "item.rocket_engine";
    		if(par1ItemStack.getItemDamage() == k*5 + 3) return "item.rocket_booster";
    		if(par1ItemStack.getItemDamage() == k*5 + 4) return "item.rocket_stabiliser";
        }
        return "unnamed";
    }
	
	@Override
    public int getMetadata(int par1)
    {
        return par1;
    }

	@Override
	public EnumSortCategoryItem getCategory(int meta) {
		return EnumSortCategoryItem.GENERAL;
	}
}
