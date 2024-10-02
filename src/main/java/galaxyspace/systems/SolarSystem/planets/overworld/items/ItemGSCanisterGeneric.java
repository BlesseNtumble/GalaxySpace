/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.Loader
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  micdoodle8.mods.galacticraft.core.GalacticraftCore
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.Entity
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.world.World
 *  net.minecraftforge.fluids.Fluid
 *  net.minecraftforge.fluids.FluidRegistry
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.ItemFluidContainer
 */
package galaxyspace.systems.SolarSystem.planets.overworld.items;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.util.GSCreativeTabs;
import java.util.List;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ItemFluidContainer;

public abstract class ItemGSCanisterGeneric
extends ItemFluidContainer {
    private String allowedFluid = null;
    public static final int EMPTY = 1001;
    private static boolean isTELoaded = Loader.isModLoaded((String)"ThermalExpansion");

    public ItemGSCanisterGeneric(String assetName) {
        super(0, 1000);
        this.setMaxDamage(1001);
        this.setMaxStackSize(1);
        this.setNoRepair();
        this.setUnlocalizedName(assetName);
        this.setContainerItem(GSItems.Helium3Canister);
    }

    public CreativeTabs getCreativeTab() {
        return GSCreativeTabs.GSItemsTab;
    }

    @SideOnly(value=Side.CLIENT)
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        par3List.add(new ItemStack(par1, 1, 1));
    }

    public ItemStack getContainerItem(ItemStack itemStack) {
        if (isTELoaded) {
            StackTraceElement[] st = Thread.currentThread().getStackTrace();
            int imax = Math.max(st.length, 5);
            for (int i = 1; i < imax; ++i) {
                String ste = st[i].getClassName();
                if (!ste.equals("thermalexpansion.block.machine.TileTransposer")) continue;
                return null;
            }
        }
        return new ItemStack(this.getContainerItem(), 1, 1001);
    }

    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
        if (1001 == par1ItemStack.getItemDamage()) {
            if (par1ItemStack.getItem() != GSItems.Helium3Canister) {
                this.replaceEmptyCanisterItem(par1ItemStack, GSItems.Helium3Canister);
            }
            par1ItemStack.stackTagCompound = null;
        } else if (par1ItemStack.getItemDamage() <= 0) {
            par1ItemStack.setItemDamage(1);
        }
    }

    public void setAllowedFluid(String name) {
        this.allowedFluid = new String(name);
    }

    public String getAllowedFluid() {
        return this.allowedFluid;
    }

    public int fill(ItemStack container, FluidStack resource, boolean doFill) {
        if (resource == null || resource.getFluid() == null || resource.amount == 0 || container == null || container.getItemDamage() <= 1 || !(container.getItem() instanceof ItemGSCanisterGeneric)) {
            return 0;
        }
        String fluidName = resource.getFluid().getName();
        if (container.getItemDamage() == 1001) {
            for (String key : GalacticraftCore.itemList.keySet()) {
                Item i;
                if (!key.contains("CanisterFull") || !((i = ((ItemStack)GalacticraftCore.itemList.get(key)).getItem()) instanceof ItemGSCanisterGeneric) || !fluidName.equalsIgnoreCase(((ItemGSCanisterGeneric)i).allowedFluid)) continue;
                if (!doFill) {
                    return Math.min(resource.amount, this.capacity);
                }
                this.replaceEmptyCanisterItem(container, i);
                break;
            }
            container.stackTagCompound = null;
        } else {
            container.stackTagCompound = null;
            super.fill(container, this.getFluid(container), true);
        }
        if (fluidName.equalsIgnoreCase(((ItemGSCanisterGeneric)container.getItem()).allowedFluid)) {
            int added = super.fill(container, resource, doFill);
            if (doFill && added > 0) {
                container.setItemDamage(Math.max(1, container.getItemDamage() - added));
            }
            return added;
        }
        return 0;
    }

    public FluidStack drain(ItemStack container, int maxDrain, boolean doDrain) {
        if (this.allowedFluid == null || container.getItemDamage() >= 1001) {
            return null;
        }
        container.stackTagCompound = null;
        super.fill(container, this.getFluid(container), true);
        FluidStack used = super.drain(container, maxDrain, doDrain);
        if (doDrain && used != null && used.amount > 0) {
            this.setNewDamage(container, container.getItemDamage() + used.amount);
        }
        return used;
    }

    protected void setNewDamage(ItemStack container, int newDamage) {
        if ((newDamage = Math.min(newDamage, 1001)) == 1001) {
            container.stackTagCompound = null;
            if (container.getItem() != GSItems.Helium3Canister) {
                this.replaceEmptyCanisterItem(container, GSItems.Helium3Canister);
                return;
            }
        }
        container.setItemDamage(newDamage);
    }

    private void replaceEmptyCanisterItem(ItemStack container, Item newItem) {
        int stackSize = container.stackSize;
        NBTTagCompound tag = new NBTTagCompound();
        tag.setShort("id", (short)Item.getIdFromItem((Item)newItem));
        tag.setByte("Count", (byte)stackSize);
        tag.setShort("Damage", (short)1001);
        container.readFromNBT(tag);
    }

    public FluidStack getFluid(ItemStack container) {
        String fluidName = ((ItemGSCanisterGeneric)container.getItem()).allowedFluid;
        if (fluidName == null || 1001 == container.getItemDamage()) {
            return null;
        }
        Fluid fluid = FluidRegistry.getFluid((String)fluidName);
        if (fluid == null) {
            return null;
        }
        return new FluidStack(fluid, 1001 - container.getItemDamage());
    }
}

