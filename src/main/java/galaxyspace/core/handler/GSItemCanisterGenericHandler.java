package galaxyspace.core.handler;

import javax.annotation.Nonnull;

import galaxyspace.core.prefab.items.ItemFluidCanisterGS;
import micdoodle8.mods.galacticraft.core.items.ItemCanisterGeneric;
import micdoodle8.mods.galacticraft.core.items.ItemCanisterGenericHandler;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class GSItemCanisterGenericHandler extends ItemCanisterGenericHandler {

	private int amount;
	
	public GSItemCanisterGenericHandler(@Nonnull ItemStack container, int amount) {
		super(container);
		this.amount = amount;
	}

	@Override
	protected void setFluid(FluidStack fluid) {
		if (this.canFillFluidType(fluid)) {
			((ItemCanisterGeneric) container.getItem()).setDamage(container, this.amount - fluid.amount);
		}
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		if (container.getCount() > 0 && container.getItem() instanceof ItemFluidCanisterGS) {
			return new FluidTankProperties[] { new FluidTankProperties(getFluid(), this.amount - 1) };
		}
		return new FluidTankProperties[0];
	}
	
	@Override
	protected void setContainerToEmpty()
    {
        if (container.getCount() > 0 && container.getItem() instanceof ItemCanisterGeneric)
        {
        	((ItemCanisterGeneric)container.getItem()).setDamage(container, this.amount);
        }
    }
}