/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  micdoodle8.mods.galacticraft.core.util.GCCoreUtil
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.player.EntityPlayer
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
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemModulesRocket
extends Item {
    public static String[] names = new String[]{"ModuleLander", "ModuleLander2", "ModuleLander3", "ModuleSmallFuelCanister", "ModuleTurboPump"};
    protected IIcon[] icons = new IIcon[names.length];

    public ItemModulesRocket() {
        this.setUnlocalizedName("Modules");
        this.setMaxStackSize(1);
        this.setMaxDamage(0);
    }

    @SideOnly(value=Side.CLIENT)
    public CreativeTabs getCreativeTab() {
        return GSCreativeTabs.GSItemsTab;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        int i = 0;
        for (String name : names) {
            this.icons[i++] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":modules/" + name);
        }
    }

    public IIcon getIconFromDamage(int damage) {
        if (this.icons.length > damage) {
            return this.icons[damage];
        }
        return super.getIconFromDamage(damage);
    }

    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        int i = 0;
        while (true) {
            if (i >= names.length) break;
            par3List.add(new ItemStack(par1, 1, i));
            ++i;
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

    @SideOnly(value=Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer player, List par2List, boolean par4) {
        par2List.add(GCCoreUtil.translate((String)"gui.module.desc"));
    }
}

