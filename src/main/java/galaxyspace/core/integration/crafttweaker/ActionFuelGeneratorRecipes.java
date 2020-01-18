package galaxyspace.core.integration.crafttweaker;

import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import galaxyspace.systems.SolarSystem.planets.overworld.recipes.AssemblyRecipes;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityFuelGenerator;
import net.minecraft.item.ItemStack;

public class ActionFuelGeneratorRecipes {

	static class Add implements IAction	
	{
		private final ILiquidStack liquid;
		private final int burn_time;
		private final float mod_energy;
		public Add(ILiquidStack liquid, int burn_time, float mod_energy)
		{
			this.liquid = liquid;
			this.burn_time = burn_time;
			this.mod_energy = mod_energy;
		}
		
		@Override
		public void apply() {
			TileEntityFuelGenerator.registerNewFuel(CraftTweakerMC.getLiquidStack(liquid).getFluid(), burn_time, mod_energy);
		}

		@Override
		public String describe() {
			return "Added Fuel Generator recipe fluid for " + this.liquid;
		}
		
	}	
	
	static class Remove implements IAction
	{
		private final ILiquidStack liquid;
		public Remove(ILiquidStack fluid)
		{
			this.liquid = fluid;
		}
		
		@Override
		public void apply() {
			TileEntityFuelGenerator.removeFuel(CraftTweakerMC.getLiquidStack(liquid).getFluid());
		}

		@Override
		public String describe() {
			return "Removed Fuel Generator recipe fluid for " + this.liquid;
		}
		
	}
}
