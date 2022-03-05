package galaxyspace.core.integration.crafttweaker;

import crafttweaker.IAction;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityGasGenerator;

public class ActionGasGeneratorRecipes {

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
			TileEntityGasGenerator.registerNewFuel(CraftTweakerMC.getLiquidStack(liquid).getFluid(), burn_time, mod_energy);
		}

		@Override
		public String describe() {
			return "Added Gas Generator recipe fluid for " + this.liquid;
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
			TileEntityGasGenerator.removeFuel(CraftTweakerMC.getLiquidStack(liquid).getFluid());
		}

		@Override
		public String describe() {
			return "Removed Gas Generator recipe fluid for " + this.liquid;
		}
		
	}
}
