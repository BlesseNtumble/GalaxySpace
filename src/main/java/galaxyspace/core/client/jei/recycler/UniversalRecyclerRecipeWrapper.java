package galaxyspace.core.client.jei.recycler;

import javax.annotation.Nonnull;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class UniversalRecyclerRecipeWrapper implements IRecipeWrapper
{
    @Nonnull
    private final ItemStack input;
    @Nonnull
    private final ItemStack output;
    
    private final FluidStack fluid;

    private final int chance;

    public UniversalRecyclerRecipeWrapper(@Nonnull ItemStack input, @Nonnull ItemStack output, FluidStack fluid, int chance)
    {
    	this.input = input;
        this.output = output;
        this.fluid = fluid;
        this.chance = chance;
    }

    @Override
    public void getIngredients(IIngredients ingredients)
    {
    	 ingredients.setInput(ItemStack.class, this.input);
         ingredients.setOutput(ItemStack.class, this.output);
    }
    
    public FluidStack getFluidStack() {
    	return this.fluid;
    }
    
    public int getChance()
    {
    	return this.chance;
    }
}