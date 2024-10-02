package galaxyspace.systems.SolarSystem.planets.overworld.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.items.ItemCanisterGeneric;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemHydrogenCanister extends ItemCanisterGeneric{

	protected IIcon[] icons = new IIcon[7];

	public ItemHydrogenCanister(String assetName) {
		super(assetName);
		this.setAllowedFluid("hydrogen");
		this.setContainerItem(this);
		this.setTextureName(GalaxySpace.ASSET_PREFIX + ":" + assetName);
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack par1ItemStack)
    {
        return EnumRarity.common;
    }
	
	@Override
    public CreativeTabs getCreativeTab()
    {
    	return GSCreativeTabs.GSItemsTab;
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
    	this.icons[0] = iconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "emptyLiquidCanister");
        for (int i = 1; i < this.icons.length; i++)
        {
            this.icons[i] = iconRegister.registerIcon(this.getIconString() + "_" + i);
        }
    }
	
	@Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        if (itemStack.getMaxDamage() - itemStack.getItemDamage() == 0)
        {
            return "item.emptyLiquidCanister";
        }

        if (itemStack.getItemDamage() == 1)
        {
            return "item.hydrogenCanister";
        }

        return "item.hydrogenCanisterPartial";
    }
	
	@Override
	public IIcon getIconFromDamage(int par1) {
		final int damage = 6 * par1 / this.getMaxDamage();

		if (this.icons.length > damage) {
			return this.icons[this.icons.length - damage - 1];
		}

		return super.getIconFromDamage(damage);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        if (par1ItemStack.getMaxDamage() - par1ItemStack.getItemDamage() > 0)
        {
            par3List.add(GCCoreUtil.translate("gui.message.hydrogen.name") + ": " + (par1ItemStack.getMaxDamage() - par1ItemStack.getItemDamage()));
        }
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 1));
    }
}
