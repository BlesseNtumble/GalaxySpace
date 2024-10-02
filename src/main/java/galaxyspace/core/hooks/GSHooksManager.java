package galaxyspace.core.hooks;

import galaxyspace.GalaxySpace;
import galaxyspace.core.events.SetBlockEvent;
import galaxyspace.core.hooklib.asm.Hook;
import galaxyspace.core.hooklib.asm.ReturnCondition;
import galaxyspace.core.registers.fluids.GSFluids;
import galaxyspace.core.registers.items.GSItems;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.items.GCItems;
import micdoodle8.mods.galacticraft.core.util.FluidUtil;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidContainerItem;

public class GSHooksManager {

	//Регистрируем эвент установки блока.
	@Hook(returnCondition = ReturnCondition.ON_TRUE, booleanReturnConstant = false) 
    public static boolean setBlock(World world, int x, int y, int z, Block block, int meta, int flags) { 
    	return MinecraftForge.EVENT_BUS.post(new SetBlockEvent(world, x, y, z, block, meta, flags)); 
    }
	
	@Hook(returnCondition = ReturnCondition.ALWAYS, booleanReturnConstant = false) 
	public static void tryFillContainerFuel(FluidUtil util, FluidTank tank, ItemStack[] inventory, int slot)
	{
		if (FluidUtil.isValidContainer(inventory[slot]))
		{
			FluidStack liquid = tank.getFluid();

			if (liquid != null && liquid.amount > 0)
			{
				String liquidname = liquid.getFluid().getName();

				//Test for the GC fuels (though anything similarly named would also pass here)
				if (liquidname.startsWith("fuel"))
				{
					//Make sure it is the current GC fuel
					if (!liquidname.equals(GalacticraftCore.fluidFuel.getName()))
						liquid = new FluidStack(GalacticraftCore.fluidFuel, liquid.amount);
					
					//But match any existing fuel fluid in the container
					ItemStack stack = inventory[slot];
					//(No null check necessary here: it cannot be a null ItemStack thanks to the .isValidContainer() check above
					if (stack.getItem() instanceof IFluidContainerItem)
					{
						FluidStack existingFluid = ((IFluidContainerItem)stack.getItem()).getFluid(stack); 
						if (existingFluid != null && !existingFluid.getFluid().getName().equals(GalacticraftCore.fluidFuel.getName()))
							liquid = new FluidStack(existingFluid, liquid.amount);
					}
					
					FluidUtil.tryFillContainer(tank, liquid, inventory, slot, GCItems.fuelCanister);
				}
				
				if (liquidname.startsWith("heliumhydrogen"))
				{
					//Make sure it is the current GC fuel
					if (!liquidname.equals(GSFluids.HeliumHydrogen.getName()))
						liquid = new FluidStack(GSFluids.HeliumHydrogen, liquid.amount);
					
					//But match any existing fuel fluid in the container
					ItemStack stack = inventory[slot];
					//(No null check necessary here: it cannot be a null ItemStack thanks to the .isValidContainer() check above
					if (stack.getItem() instanceof IFluidContainerItem)
					{
						FluidStack existingFluid = ((IFluidContainerItem)stack.getItem()).getFluid(stack); 
						if (existingFluid != null && !existingFluid.getFluid().getName().equals(GSFluids.HeliumHydrogen.getName()))
							liquid = new FluidStack(existingFluid, liquid.amount);
					}
					
					FluidUtil.tryFillContainer(tank, liquid, inventory, slot, GSItems.HeliumHydrogenCanister);
				}
			}
		}
	}
}
