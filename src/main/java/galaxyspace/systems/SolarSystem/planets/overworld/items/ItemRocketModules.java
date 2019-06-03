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

public class ItemRocketModules extends Item implements ISortableItem {

	public static String[] names = { 
		"module_lander_1",
		"module_lander_2",
		"module_lander_3",
		"module_small_fuelcanister",
		"ion_engine",
		"plasma_engine",
		"sublight_engine",
		"blackhole_engine"
	};
		
	public ItemRocketModules() {
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.setMaxStackSize(64);
		this.setUnlocalizedName("rocket_modules");
		this.setCreativeTab(GSCreativeTabs.GSItemsTab);
		
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
		if (tab == GSCreativeTabs.GSItemsTab || tab == CreativeTabs.SEARCH) {
			for (int i = 0; i < this.names.length; i++) {
				list.add(new ItemStack(this, 1, i));
			}
		}
	}

	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> list, ITooltipFlag flagIn)
    {		
		list.add(GCCoreUtil.translate("gui.module.desc"));	
    }
	
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		return "item." + this.names[par1ItemStack.getItemDamage()];
	}

	@Override
	public int getMetadata(int par1) {
		return par1;
	}

	@Override
	public EnumSortCategoryItem getCategory(int meta) {
		return EnumSortCategoryItem.GENERAL;
	}
}
