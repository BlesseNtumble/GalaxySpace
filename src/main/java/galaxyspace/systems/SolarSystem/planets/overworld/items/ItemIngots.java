/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.IIcon
 */
package galaxyspace.systems.SolarSystem.planets.overworld.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemIngots
extends Item {
    public static String[] names = new String[]{"CobaltumIngot", "MagnesiumIngot", "NickelIngot"};
    protected IIcon[] icons = new IIcon[names.length];

    public ItemIngots() {
        this.setHasSubtypes(true);
        this.setMaxStackSize(64);
        this.setUnlocalizedName("Ingots");
    }

    @SideOnly(value=Side.CLIENT)
    public CreativeTabs getCreativeTab() {
        return GSCreativeTabs.GSItemsTab;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        int i = 0;
        for (String name : names) {
            this.icons[i++] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":ingots/" + name);
        }
    }

    public IIcon getIconFromDamage(int damage) {
        if (this.icons.length > damage) {
            return this.icons[damage];
        }
        return super.getIconFromDamage(damage);
    }

    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        for (int i = 0; i < names.length; ++i) {
            par3List.add(new ItemStack(par1, 1, i));
        }
    }

    public String getUnlocalizedName(ItemStack par1ItemStack) {
        if (this.icons.length > par1ItemStack.getItemDamage()) {
            return "item." + names[par1ItemStack.getItemDamage()];
        }
        return "unnamed";
    }

    public int getMetadata(int par1) {
        return par1;
    }
}

