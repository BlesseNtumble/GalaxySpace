package galaxyspace.systems.SolarSystem.planets.overworld.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class RecyclerRecipes {
	
	 private static final RecyclerRecipes recyclerBase = new RecyclerRecipes();

	 private List<RecycleRecipe> recipes = new ArrayList<RecycleRecipe>();

	 public static RecyclerRecipes recycling()
	 {
		 return recyclerBase;
	 }

	 public void addNewRecipe(ItemStack stack, ItemStack result, FluidStack fluidstack)
	 {
		 this.recipes.add(new RecycleRecipe(stack, result, fluidstack));
	 }
 
	 public void addNewRecipe(ItemStack stack, ItemStack result, int chance, FluidStack fluidstack)
	 {
		 this.recipes.add(new RecycleRecipe(stack, result, fluidstack, chance));
	 }
	 	 	 
	 public List<RecycleRecipe> getRecipes()
	 {
		 return this.recipes;
	 }	
	 
	 public RecycleRecipe getRecipe(ItemStack stack)
	 {
		 for(RecycleRecipe recipe : this.recipes)
			 if(recipe.getInput().isItemEqual(stack))
				 return recipe;
		 
		 return null;
	 }

	 public class RecycleRecipe
	 {
		 private ItemStack input, output;
		 private FluidStack fluid;
		 private int chance_procent = 100;
		 private boolean chance = false;
		 
		 public RecycleRecipe(ItemStack input, ItemStack output, FluidStack fluid)
		 {
			 this.input = input;
			 this.output = output;
			 this.fluid = fluid;
		 }
		 
		 public RecycleRecipe(ItemStack input, ItemStack output, FluidStack fluid, int chance)
		 {			 
			 this.input = input;
			 this.output = output;
			 this.fluid = fluid;
			 this.chance_procent = chance;
			 this.chance = true;
		 }
		 
		 public ItemStack getInput()
		 {
			 return this.input;
		 }
		 
		 public ItemStack getOutput()
		 {
			 return this.output;
		 }
		 
		 public FluidStack getFluidStack()
		 {
			 return this.fluid;
		 }
		 
		 public boolean hasChance()
		 {
			 return this.chance;
		 }
		 
		 public int getChance()
		 {
			 return this.chance_procent;
		 }
	 }
}
