package galaxyspace.core.client.jei.recycler;

import java.util.Arrays;

import javax.annotation.Nonnull;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class UniversalRecyclerRecipeWrapper implements IRecipeWrapper
{
    @Nonnull
    private final ItemStack input;
    @Nonnull
    private final ItemStack output;
    
    private final ItemStack output_2;
    
    private final FluidStack fluid;

    private final int chance, chance_2;

    public UniversalRecyclerRecipeWrapper(@Nonnull ItemStack input, @Nonnull ItemStack output, ItemStack output_2, FluidStack fluid, int chance, int chance_2)
    {
    	this.input = input;
        this.output = output;
        this.fluid = fluid;
        this.chance = chance;
        
        this.output_2 = output_2;
        this.chance_2 = chance_2;
    }

    @Override
    public void getIngredients(IIngredients ingredients)
    {
    	 ingredients.setInput(ItemStack.class, this.input);
    	 ingredients.setOutputs(ItemStack.class, Arrays.asList(this.output, this.output_2));
    }
    
    public FluidStack getFluidStack() {
    	return this.fluid;
    }
    
    public int getChance()
    {
    	return this.chance;
    }
    
    public int getChance_2()
    {
    	return this.chance_2;
    }
    
    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
    	if(getChance() < 100 || getChance_2() < 100) {
    		minecraft.fontRenderer.drawString("Chances: ", 36, 4, 0xFFFFFF);
    		minecraft.fontRenderer.drawString("Primary slot: " + getChance() + "%", 36, 13, 0xFFFFFF);
        	
    		if(this.output_2 != null)
    			minecraft.fontRenderer.drawString("Secondary slot: " + getChance_2() + "%", 36, 17 + 5, 0xFFFFFF);
    	}
    	
    	if(getFluidStack() != null) {
    		minecraft.fontRenderer.drawString("Fluid: " + getFluidStack().getLocalizedName(), 36, 64, 0xFFFFFF);    		
    	}
    }
}