package galaxyspace.systems.SolarSystem.planets.overworld.items;

import java.util.List;

import javax.annotation.Nullable;

import galaxyspace.api.item.IModificationItem;
import galaxyspace.core.GSItems;
import galaxyspace.core.prefab.items.modules.ItemModule;
import galaxyspace.core.util.GSCreativeTabs;
import galaxyspace.core.util.GSUtils.Module_Type;
import galaxyspace.systems.SolarSystem.planets.overworld.items.modules.EPP;
import micdoodle8.mods.galacticraft.core.items.ItemOxygenTank;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemGSOxygenTank extends ItemOxygenTank implements IModificationItem{

	private boolean epp = false;
    public ItemGSOxygenTank(int tier, String assetName, int damage, boolean epp)
    {
    	 super(tier, assetName);
    	 this.setMaxStackSize(1);
    	 this.setMaxDamage(damage);
    	 this.setUnlocalizedName(assetName);
    	 this.setCreativeTab(GSCreativeTabs.GSItemsTab);
    	 this.setNoRepair();
         this.epp = epp;
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
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
    
    	if(epp || stack.hasTagCompound() && stack.getTagCompound().hasKey("epp"))
    	{
    		list.add(EnumColor.DARK_GREEN + GCCoreUtil.translate("gui.tank.passive_oxygen_regen.desc1"));
    		list.add(EnumColor.DARK_GREEN + GCCoreUtil.translate("gui.tank.passive_oxygen_regen.desc2"));    
    	}
    	list.add(GCCoreUtil.translate("gui.tank.oxygen_remaining") + ": " + (stack.getMaxDamage() - stack.getItemDamage()));
        
    }

	@Override
	public Module_Type getType(ItemStack stack) {
		if(stack.getItem() != GSItems.OXYGENTANK_TIER_EPP)
			return Module_Type.OXYGEN_TANK;
		
		return null;
	}

	@Override
	public ItemModule[] getAvailableModules() {
		return new ItemModule[] { new EPP() };
	}

	@Override
	public int getModificationCount(ItemStack stack) {
		return 1;
	}     	
}
