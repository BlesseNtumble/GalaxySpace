package galaxyspace.systems.SolarSystem.planets.overworld.items;

import java.util.List;

import javax.annotation.Nullable;

import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.api.item.IKeyItem;
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

public class ItemTierKeysChest extends Item implements IKeyItem , ISortableItem{
	
    public static String[] keys = { "key_tier_4" };
    
    public ItemTierKeysChest()
	{
		super();
		this.setUnlocalizedName("dungeon_keys");
	    this.setMaxStackSize(1);
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
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list)
	{
		if (tab == GSCreativeTabs.GSItemsTab || tab == CreativeTabs.SEARCH)
			for (int i = 0; i < this.keys.length; i++)	    
				list.add(new ItemStack(this, 1, i));
	    
	}
	
	@Override
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
         return "item.dungeon_keys";
      
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
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		int n = 4+stack.getItemDamage();
		tooltip.add(GCCoreUtil.translate("gui.tier"+n+".desc"));
	}

	@Override
    public EnumSortCategoryItem getCategory(int meta)
    {
        return EnumSortCategoryItem.KEYS;
    }

}
