package galaxyspace.core.integration.crafttweaker;

import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import galaxyspace.systems.SolarSystem.planets.overworld.recipes.RecyclerRecipes;

public class ActionRecyclerRecipes {

	static class Add implements IAction	
	{
		private final IItemStack stack, result;
		private final ILiquidStack liquid;
		private final int chance;
		
		public Add(IItemStack stack, IItemStack result, ILiquidStack liquid, int chances)
		{
			this.stack = stack;
			this.result = result;
			this.liquid = liquid;
			this.chance = chances;
		}
		
		@Override
		public void apply() {
			RecyclerRecipes.recycling().addNewRecipe(CraftTweakerMC.getItemStack(stack), CraftTweakerMC.getItemStack(result), chance, CraftTweakerMC.getLiquidStack(liquid));
		}

		@Override
		public String describe() {
			return "Added Recycler recipe for " + this.liquid;
		}
		
	}	
	
	static class Remove implements IAction
	{
		private final IItemStack stack;
		public Remove(IItemStack stack)
		{
			this.stack = stack;
		}
		
		@Override
		public void apply() {
			RecyclerRecipes.recycling().removeRecipe(CraftTweakerMC.getItemStack(stack));
		}

		@Override
		public String describe() {
			return "Removed Recycler recipe for " + this.stack;
		}
		
	}
}
