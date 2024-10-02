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
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemHeliumCanister extends ItemCanisterGeneric
{
    protected IIcon[] icons = new IIcon[7];

    public ItemHeliumCanister(String assetName)
    {
        super(assetName);
        this.setAllowedFluid("helium");
        this.setContainerItem(this);
        this.setTextureName(GalaxySpace.ASSET_PREFIX + ":" + assetName);
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
            return "item.heliumCanister";
        }

        return "item.heliumCanisterPartial";
    }

    @Override
    public IIcon getIconFromDamage(int par1)
    {
        final int damage = 6 * par1 / this.getMaxDamage();

        if (this.icons.length > damage)
        {
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
            par3List.add(GCCoreUtil.translate("gui.message.helium.name") + ": " + (par1ItemStack.getMaxDamage() - par1ItemStack.getItemDamage()));
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 1));
        //par3List.add(new ItemStack(par1, 1, this.getMaxDamage()));
    }

    @Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
    {
        if (ItemGSCanisterGeneric.EMPTY == par1ItemStack.getItemDamage())
        {
            par1ItemStack.stackTagCompound = null;
        }
        else if (par1ItemStack.getItemDamage() <= 0) par1ItemStack.setItemDamage(1);
    }
}
