package galaxyspace.systems.SolarSystem.planets.overworld.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.api.item.IItemOxygenSupply;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.items.GCItems;
import micdoodle8.mods.galacticraft.core.items.ItemCanisterGeneric;
import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemOxygenCanisterBase extends Item implements IItemOxygenSupply{

	private final int ox_storage;
	private final String name;
	public ItemOxygenCanisterBase(String assetName, int storage)
    {
        super();
        this.name = assetName;
        this.setMaxDamage(ItemCanisterGeneric.EMPTY - 1);
        this.setMaxStackSize(1);
        this.setNoRepair();
        this.setUnlocalizedName(this.name);
        this.setContainerItem(GCItems.oilCanister);
        this.ox_storage = storage / 1000;
    }

	@SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        if (par1ItemStack.getMaxDamage() - par1ItemStack.getItemDamage() > 0)
        {
            par3List.add(GCCoreUtil.translate("gui.tank.oxygenRemaining") + ": " + (par1ItemStack.getMaxDamage() - par1ItemStack.getItemDamage()) * this.ox_storage);
        }
    }
	
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        this.itemIcon = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + this.name);
    }

    @Override
    public CreativeTabs getCreativeTab()
    {
    	return GSCreativeTabs.GSItemsTab;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemstack)
    {
        if (super.getContainerItem(itemstack) == null)
        	return null;
    	return itemstack;
    }

    public float recharge(ItemStack itemStack)
    {
    	return this.ox_storage;
    }
    
	@Override
	public float discharge(ItemStack itemStack, float amount)
	{
		
		if(itemStack.getItemDamage() < itemStack.getMaxDamage()) {
			itemStack.setItemDamage(itemStack.getItemDamage() + 1);
			return this.ox_storage;
		}
		
		return 0.0F;
	}

	@Override
	public int getOxygenStored(ItemStack par1ItemStack)
	{
		return par1ItemStack.getMaxDamage();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, ItemCanisterGeneric.EMPTY - 1));
    }
}
