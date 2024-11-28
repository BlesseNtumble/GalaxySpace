package galaxyspace.systems.SolarSystem.planets.overworld.inventory;

import galaxyspace.core.integration.minetweaker.handlers.MTHandler_RocketAssembly;
import galaxyspace.core.registers.items.GSItems;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

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
		List<ItemStack> list;
		switch (this.index)
        {
			//Cone
        	case 2:
					boolean isCone = false;
					list = MTHandler_RocketAssembly.material.get(0);
					for(ItemStack stack : list)
					{
						isCone = stack.isItemEqual(par1ItemStack);
						if(isCone)
							return true;
					}
					return par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 0
					|| par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 5
					|| par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 10
					|| par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 15
					|| par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 20;

        	//Body Right
        	case 3:
					boolean isBodyR = false;
					list = MTHandler_RocketAssembly.material.get(1);
					for(ItemStack stack : list)
					{
						isBodyR = stack.isItemEqual(par1ItemStack);
						if(isBodyR)
							return true;
					}
					return
        			par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 1
        			|| par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 6
        			|| par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 11
        			|| par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 16
        			|| par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 21;
        	//Body Left
        	case 4:
					boolean isBodyL = false;
					list = MTHandler_RocketAssembly.material.get(2);
					for(ItemStack stack : list)
					{
						isBodyL = stack.isItemEqual(par1ItemStack);
						if(isBodyL)
							return true;
					}
					return
        			par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 1
        			|| par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 6
        			|| par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 11
        			|| par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 16
        			|| par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 21;
        	//Engine
        	case 5:
					boolean isEngine = false;
					list = MTHandler_RocketAssembly.material.get(3);
					for(ItemStack stack : list)
					{
						isEngine = stack.isItemEqual(par1ItemStack);
						if(isEngine)
							return true;
					}
					return
        			par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 2
        			|| par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 7
        			|| par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 12
        			|| par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 17
        			|| par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 22;
        	//Booster Top
        	case 6:
					boolean isBoosterT = false;
					list = MTHandler_RocketAssembly.material.get(4);
					for(ItemStack stack : list)
					{
						isBoosterT = stack.isItemEqual(par1ItemStack);
						if(isBoosterT)
							return true;
					}
					return
        			par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 3
        			|| par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 8
        			|| par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 13
        			|| par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 18
        			|| par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 23;
        	//Booster Bottom
        	case 7:
					boolean isBoosterB = false;
					list = MTHandler_RocketAssembly.material.get(5);
					for(ItemStack stack : list)
					{
						isBoosterB = stack.isItemEqual(par1ItemStack);
						if(isBoosterB)
							return true;
					}

				return
        			par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 3
        			|| par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 8
        			|| par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 13
        			|| par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 18
        			|| par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 23;
        	//Vane Top
        	case 8:
					boolean isVaneT = false;
					list = MTHandler_RocketAssembly.material.get(6);
					for(ItemStack stack : list)
					{
						isVaneT = stack.isItemEqual(par1ItemStack);
						if(isVaneT)
							return true;
					}
				return
        			par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 4
        			|| par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 9
        			|| par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 14
        			|| par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 19
        			|| par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 24;
        	//Vane Bottom
        	case 9:
					boolean isVaneB = false;
					list = MTHandler_RocketAssembly.material.get(7);
					for(ItemStack stack : list)
					{
						isVaneB = stack.isItemEqual(par1ItemStack);
						if(isVaneB)
							return true;
					}
					return
					par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 4
					|| par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 9
					|| par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 14
					|| par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 19
					|| par1ItemStack.getItem() == GSItems.RocketParts && par1ItemStack.getItemDamage() == 24;

        	//Chests
        	case 10: return par1ItemStack.getItem() == Item.getItemFromBlock(Blocks.chest);
        	case 11: return par1ItemStack.getItem() == Item.getItemFromBlock(Blocks.chest);
        	case 12: return par1ItemStack.getItem() == Item.getItemFromBlock(Blocks.chest);
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
