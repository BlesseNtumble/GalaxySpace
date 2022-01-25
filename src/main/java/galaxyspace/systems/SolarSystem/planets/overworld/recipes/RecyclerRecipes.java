package galaxyspace.systems.SolarSystem.planets.overworld.recipes;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

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
	 
	 public void addNewRecipe(ItemStack stack, ItemStack result, ItemStack result_2, int chance, int chance_2, FluidStack fluidstack)
	 {
		 this.recipes.add(new RecycleRecipe(stack, result, result_2, fluidstack, chance, chance_2));
	 }
	 
	 public void removeRecipe(ItemStack stack)
	 {
		 recipes.remove(getRecipe(stack));
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
		 private ItemStack input, output, output_2;
		 private FluidStack fluid;
		 private int chance_procent = 100;
		 private int chance_procent_2 = 100;
		 
		 public RecycleRecipe(ItemStack input, ItemStack output, FluidStack fluid)
		 {
			 this(input, output, fluid, 100);
		 }
		 
		 public RecycleRecipe(ItemStack input, ItemStack output, FluidStack fluid, int chance)
		 {			 
			 this.input = input;
			 this.output = output;
			 this.fluid = fluid;
			 this.chance_procent = chance;
		 }
		 
		 public RecycleRecipe(ItemStack input, ItemStack output, ItemStack output_2, FluidStack fluid, int chance, int chance_2)
		 {			 
			 this.input = input;
			 this.output = output;
			 this.fluid = fluid;
			 this.chance_procent = chance;
			 this.output_2 = output_2;
			 this.chance_procent_2 = chance_2;
		 }
		 
		 public ItemStack getInput()
		 {
			 return this.input;
		 }	
		 
		 public ItemStack getOutput()
		 {
			 return this.output;
		 }
		 
		 public ItemStack getOutput_2()
		 {
			 return this.output_2;
		 }
		 
		 public FluidStack getFluidStack()
		 {
			 return this.fluid;
		 }
		 
		 public boolean hasChance()
		 {
			 return chance_procent != 100;
		 }
		 
		 public int getChance()
		 {
			 return this.chance_procent;
		 }
		 
		 public boolean hasChance_2()
		 {
			 return this.chance_procent_2 != 100;
		 }
		 
		 public int getChance_2()
		 {
			 return this.chance_procent_2;
		 }
	 }
}
