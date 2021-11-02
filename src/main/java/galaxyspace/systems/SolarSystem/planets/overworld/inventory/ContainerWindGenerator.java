package galaxyspace.systems.SolarSystem.planets.overworld.inventory;

import galaxyspace.core.GSItems;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemBasicGS;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityWindGenerator;
import micdoodle8.mods.galacticraft.api.item.IItemElectric;
import micdoodle8.mods.galacticraft.core.inventory.SlotSpecific;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerWindGenerator extends Container
{
    private TileEntityWindGenerator tileEntity;

    public ContainerWindGenerator(InventoryPlayer par1InventoryPlayer, TileEntityWindGenerator solarGen)
    {
        this.tileEntity = solarGen;
        this.addSlotToContainer(new SlotSpecific(solarGen, 0, 152, 83, IItemElectric.class));
        ItemStack[] stacks = {
        		ItemBasicGS.BasicItems.IRON_FAN.getItemStack(), 
        		ItemBasicGS.BasicItems.STEEL_FAN.getItemStack(),
        		ItemBasicGS.BasicItems.PLASTIC_FAN.getItemStack(),
        		ItemBasicGS.BasicItems.CARBON_FAN.getItemStack()};
        this.addSlotToContainer(new SlotSpecific(tileEntity, 1, 7, 22, stacks));

        // Player inv:

        for (int var6 = 0; var6 < 3; ++var6)
        {
            for (int var7 = 0; var7 < 9; ++var7)
            {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, var7 + var6 * 9 + 9, 8 + var7 * 18, 51 + 68 + var6 * 18));
            }
        }

        for (int var6 = 0; var6 < 9; ++var6)
        {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, var6, 8 + var6 * 18, 61 + 116));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer var1)
    {
        return this.tileEntity.isUsableByPlayer(var1);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par1)
    {
        ItemStack var2 = ItemStack.EMPTY;
        final Slot slot = (Slot) this.inventorySlots.get(par1);
        final int b = this.inventorySlots.size();

        if (slot != null && slot.getHasStack())
        {
            final ItemStack stack = slot.getStack();
            var2 = stack.copy();

            if (par1 == 0 || par1 == 1)
            {
                if (!this.mergeItemStack(stack, b - 36, b, true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else
            {
                if (stack.getItem() instanceof IItemElectric)
                {
                    if (!this.mergeItemStack(stack, 0, 1, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (stack.getItem() == GSItems.BASIC && stack.getItemDamage() >= 33 && stack.getItemDamage() <= 36)
                {
                    if (!this.mergeItemStack(stack, 1, 2, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else
                {
                    if (par1 < b - 9)
                    {
                        if (!this.mergeItemStack(stack, b - 9, b, false))
                        {
                            return ItemStack.EMPTY;
                        }
                    }
                    else if (!this.mergeItemStack(stack, b - 36, b - 9, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (stack.getCount() == 0)
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (stack.getCount() == var2.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(par1EntityPlayer, stack);
        }

        return var2;
    }
}
