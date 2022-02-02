package galaxyspace.systems.SolarSystem.planets.overworld.items.armor;

import java.util.List;

import javax.annotation.Nullable;

import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.api.item.IItemThermal;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.core.items.ISortableItem;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryItem;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemThermalPaddingBase extends Item implements IItemThermal, ISortableItem
{
	private int tier = 0;
	private boolean forFreeze = false;
    public static String[] names = { "thermal_helm", "thermal_chestplate", "thermal_leggings", "thermal_boots" };

    public ItemThermalPaddingBase(int tier, boolean isFreeze)
    {
        super();
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setMaxStackSize(1);
        this.setTranslationKey("thermal_padding_t" + tier);
        this.tier = tier;
        this.forFreeze = isFreeze;
    }

    public boolean isFreeze()
    {
    	return this.forFreeze;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public CreativeTabs getCreativeTab()
    {
        return GSCreativeTabs.GSArmorTab;
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(GCCoreUtil.translate("gui.tier" + this.tier + ".desc"));
        if(forFreeze)
        	 tooltip.add(GCCoreUtil.translate("gui.message.for_freeze"));
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems)
    {
        if (tab == getCreativeTab() || tab == CreativeTabs.SEARCH)
        {
            for (int i = 0; i < names.length; i++)
            {
                subItems.add(new ItemStack(this, 1, i));
            }
        }
    }

    @Override
    public String getTranslationKey(ItemStack par1ItemStack)
    {    	
        if (names.length > par1ItemStack.getItemDamage())
        {
            return "item." + names[par1ItemStack.getItemDamage()];
        }

        return "unnamed";
    }

    @Override
    public int getMetadata(int par1)
    {
        return par1;
    }

    @Override
    public int getThermalStrength()
    {    
        return tier;
    }

    @Override
    public boolean isValidForSlot(ItemStack stack, int armorSlot)
    {
        return stack.getItemDamage() == armorSlot;
    }

    @Override
    public EnumSortCategoryItem getCategory(int meta)
    {
        return EnumSortCategoryItem.ARMOR;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand)
    {
        ItemStack itemStack = player.getHeldItem(hand);

        if (player instanceof EntityPlayerMP)
        {
            GCPlayerStats stats = GCPlayerStats.get(player);
            ItemStack gear = stats.getExtendedInventory().getStackInSlot(6);
            ItemStack gear1 = stats.getExtendedInventory().getStackInSlot(7);
            ItemStack gear2 = stats.getExtendedInventory().getStackInSlot(8);
            ItemStack gear3 = stats.getExtendedInventory().getStackInSlot(9);

            if (itemStack.getItemDamage() == 0)
            {
                if (gear.isEmpty())
                {
                    stats.getExtendedInventory().setInventorySlotContents(6, itemStack.copy());
                    itemStack.setCount(0);
                }
            }
            else if (itemStack.getItemDamage() == 1)
            {
                if (gear1.isEmpty())
                {
                    stats.getExtendedInventory().setInventorySlotContents(7, itemStack.copy());
                    itemStack.setCount(0);
                }
            }
            else if (itemStack.getItemDamage() == 2)
            {
                if (gear2.isEmpty())
                {
                    stats.getExtendedInventory().setInventorySlotContents(8, itemStack.copy());
                    itemStack.setCount(0);
                }
            }
            else if (itemStack.getItemDamage() == 3)
            {
                if (gear3.isEmpty())
                {
                    stats.getExtendedInventory().setInventorySlotContents(9, itemStack.copy());
                    itemStack.setCount(0);
                }
            }
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
    }
}
