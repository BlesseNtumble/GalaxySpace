package galaxyspace.systems.SolarSystem.planets.overworld.inventory;

import galaxyspace.core.GSItems;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotRocketAssembly extends Slot
{
    private final int index;

    public SlotRocketAssembly(IInventory par2IInventory, int par3, int par4, int par5)
    {
        super(par2IInventory, par3, par4, par5);
        this.index = par3;

    }

   
    @Override
    public boolean isItemValid(ItemStack par1ItemStack)
    {
        switch (this.index)
        {
        	case 2: return 
        			par1ItemStack.getItem() == GSItems.ROCKET_PARTS && par1ItemStack.getItemDamage() == 0
        			|| par1ItemStack.getItem() == GSItems.ROCKET_PARTS && par1ItemStack.getItemDamage() == 5
        			|| par1ItemStack.getItem() == GSItems.ROCKET_PARTS && par1ItemStack.getItemDamage() == 10
        			|| par1ItemStack.getItem() == GSItems.ROCKET_PARTS && par1ItemStack.getItemDamage() == 15
        			|| par1ItemStack.getItem() == GSItems.ROCKET_PARTS && par1ItemStack.getItemDamage() == 20;
        	
        	case 3:
        	case 4: return 
        			par1ItemStack.getItem() == GSItems.ROCKET_PARTS && par1ItemStack.getItemDamage() == 1
        			|| par1ItemStack.getItem() == GSItems.ROCKET_PARTS && par1ItemStack.getItemDamage() == 6
        			|| par1ItemStack.getItem() == GSItems.ROCKET_PARTS && par1ItemStack.getItemDamage() == 11
        			|| par1ItemStack.getItem() == GSItems.ROCKET_PARTS && par1ItemStack.getItemDamage() == 16
        			|| par1ItemStack.getItem() == GSItems.ROCKET_PARTS && par1ItemStack.getItemDamage() == 21;
        	
        	case 5: return 
        			par1ItemStack.getItem() == GSItems.ROCKET_PARTS && par1ItemStack.getItemDamage() == 2
        			|| par1ItemStack.getItem() == GSItems.ROCKET_PARTS && par1ItemStack.getItemDamage() == 7
        			|| par1ItemStack.getItem() == GSItems.ROCKET_PARTS && par1ItemStack.getItemDamage() == 12
        			|| par1ItemStack.getItem() == GSItems.ROCKET_PARTS && par1ItemStack.getItemDamage() == 17
        			|| par1ItemStack.getItem() == GSItems.ROCKET_PARTS && par1ItemStack.getItemDamage() == 22;
        	
        	case 6: 
        	case 7: return 
        			par1ItemStack.getItem() == GSItems.ROCKET_PARTS && par1ItemStack.getItemDamage() == 3
        			|| par1ItemStack.getItem() == GSItems.ROCKET_PARTS && par1ItemStack.getItemDamage() == 8
        			|| par1ItemStack.getItem() == GSItems.ROCKET_PARTS && par1ItemStack.getItemDamage() == 13
        			|| par1ItemStack.getItem() == GSItems.ROCKET_PARTS && par1ItemStack.getItemDamage() == 18
        			|| par1ItemStack.getItem() == GSItems.ROCKET_PARTS && par1ItemStack.getItemDamage() == 23;
        	
        	case 8:
        	case 9: return 
        			par1ItemStack.getItem() == GSItems.ROCKET_PARTS && par1ItemStack.getItemDamage() == 4
        			|| par1ItemStack.getItem() == GSItems.ROCKET_PARTS && par1ItemStack.getItemDamage() == 9
        			|| par1ItemStack.getItem() == GSItems.ROCKET_PARTS && par1ItemStack.getItemDamage() == 14
        			|| par1ItemStack.getItem() == GSItems.ROCKET_PARTS && par1ItemStack.getItemDamage() == 19
        			|| par1ItemStack.getItem() == GSItems.ROCKET_PARTS && par1ItemStack.getItemDamage() == 24;
        	
        	case 10: return par1ItemStack.getItem() == Item.getItemFromBlock(Blocks.CHEST);
        	case 11: return par1ItemStack.getItem() == Item.getItemFromBlock(Blocks.CHEST);
        	case 12: return par1ItemStack.getItem() == Item.getItemFromBlock(Blocks.CHEST);
        	
        }

        return false;
    }

    /**
     * Returns the maximum stack size for a given slot (usually the same as
     * getInventoryStackLimit(), but 1 in the case of armor slots)
     */
    @Override
    public int getSlotStackLimit()
    {
        return 1;
    }
}
