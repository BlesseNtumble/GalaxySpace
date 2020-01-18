package galaxyspace.core.integration.crafttweaker;

import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import galaxyspace.systems.SolarSystem.planets.overworld.recipes.AssemblyRecipes;
import galaxyspace.systems.SolarSystem.planets.overworld.recipes.RocketAssemblyRecipes;
import net.minecraft.item.ItemStack;

public class ActionRocketAssemblerRecipes {

	static class Add implements IAction	
	{
		private final IItemStack output;
		private final IItemStack[] inputs;
		public Add(IItemStack output, IItemStack... inputs)
		{
			this.output = output;
			this.inputs = inputs;
		}
		
		@Override
		public void apply() {
			ItemStack[] staks = CraftTweakerMC.getItemStacks(inputs);
			RocketAssemblyRecipes.addRecipe(CraftTweakerMC.getItemStack(output), (Object)staks);
		}

		@Override
		public String describe() {
			return "Added Rocket Assembly recipe for " + this.output;
		}
		
	}	
	
	static class Remove implements IAction
	{
		private final IItemStack item;
		public Remove(IItemStack item)
		{
			this.item = item;
		}
		
		@Override
		public void apply() {
			RocketAssemblyRecipes.removeRecipe(CraftTweakerMC.getItemStack(item));
		}

		@Override
		public String describe() {
			return "Removed Rocket Assembly recipe for " + this.item;
		}
		
	}
}
