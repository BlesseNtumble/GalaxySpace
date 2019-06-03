package galaxyspace.systems.SolarSystem.planets.overworld.inventory.schematics;

import galaxyspace.core.util.GSRecipeUtil;
import micdoodle8.mods.galacticraft.core.inventory.SlotRocketBenchResult;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerSchematicFins extends Container
{
    public InventorySchematicFins craftMatrix = new InventorySchematicFins(this);
    public IInventory craftResult = new InventoryCraftResult();
    private final World worldObj;

    public ContainerSchematicFins(InventoryPlayer par1InventoryPlayer, BlockPos pos)
    {
        final int change = 27;
        this.worldObj = par1InventoryPlayer.player.world;
        this.addSlotToContainer(new SlotRocketBenchResult(par1InventoryPlayer.player, this.craftMatrix, this.craftResult, 0, 140, 95 + change));
        int var6;
        int var7;
        
        
        for (var6 = 0; var6 < 2; ++var6)
        {
        	for (var7 = 0; var7 < 2; ++var7)            
       		this.addSlotToContainer(new SlotSchematicFins(this.craftMatrix, var6 + var7 * 2 + 1, 35 + var7 * 19, 30 + var6 * 19 + change, pos, par1InventoryPlayer.player));
        }
        
        // Body Center
        for (var6 = 0; var6 < 2; ++var6)
        {
        	for (var7 = 0; var7 < 4; ++var7)            
       		this.addSlotToContainer(new SlotSchematicFins(this.craftMatrix, var6 + var7 * 2 + 5, 16 + var7 * 19, 68 + var6 * 19 + change, pos, par1InventoryPlayer.player));
        }

        // Player inv:

        for (var6 = 0; var6 < 3; ++var6)
        {
            for (var7 = 0; var7 < 9; ++var7)
            {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, var7 + var6 * 9 + 9, 17 + var7 * 18, 144 + var6 * 18 + change));
            }
        }

        for (var6 = 0; var6 < 9; ++var6)
        {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, var6, 17 + var6 * 18, 18 + 184 + change));
        }

        this.onCraftMatrixChanged(this.craftMatrix);
    }

    @Override
    public void onContainerClosed(EntityPlayer par1EntityPlayer)
    {
        super.onContainerClosed(par1EntityPlayer);

        if (!this.worldObj.isRemote)
        {
            for (int var2 = 1; var2 < this.craftMatrix.getSizeInventory(); ++var2)
            {
                final ItemStack var3 = this.craftMatrix.removeStackFromSlot(var2);

                if (!var3.isEmpty())
                {
                    par1EntityPlayer.entityDropItem(var3, 0.0F);
                }
            }
        }
    }

    @Override
    public void onCraftMatrixChanged(IInventory par1IInventory)
    {
        this.craftResult.setInventorySlotContents(0, GSRecipeUtil.findMatchingFinsRecipe(this.craftMatrix));
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par1)
    {
        ItemStack var2 = ItemStack.EMPTY;
        final Slot slot = (Slot) this.inventorySlots.get(par1);
        int b = this.inventorySlots.size();

        if (slot != null && slot.getHasStack())
        {
            final ItemStack var4 = slot.getStack();
            var2 = var4.copy();

            if (par1 <= 12)
            {           
                if (!this.mergeItemStack(var4, 13, this.inventorySlots.size(), false))
                {
                    return ItemStack.EMPTY;
                }
                if (par1 == 0)
                {
                    slot.onSlotChange(var4, var2);
                }
            }
            else if(!this.mergeOneItem(var4, 1, 13, false))
            {
                return ItemStack.EMPTY;
            }
            
            if (var4.getCount() == 0)
            {
                if (par1 == 0)
                {
                    slot.onTake(par1EntityPlayer, var4);
                }
                slot.putStack(ItemStack.EMPTY);
                return var2;
            }

            if (var4.getCount() == var2.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(par1EntityPlayer, var4);
            if (par1 == 0)
            {
                slot.onSlotChanged();
            }

        }

        return var2;
    }
    
    protected boolean mergeOneItem(ItemStack par1ItemStack, int par2, int par3, boolean par4)
    {
        boolean flag1 = false;
        if (par1ItemStack.getCount() > 0)
        {
            Slot slot;
            ItemStack slotStack;

            for (int k = par2; k < par3; k++)
            {
                slot = (Slot) this.inventorySlots.get(k);
                slotStack = slot.getStack();

                if (slotStack == null)
                {
                    ItemStack stackOneItem = par1ItemStack.copy();
                    stackOneItem.setCount(1);
                    par1ItemStack.shrink(1);
                    slot.putStack(stackOneItem);
                    slot.onSlotChanged();
                    flag1 = true;
                    break;
                }
            }
        }

        return flag1;
    }
}
