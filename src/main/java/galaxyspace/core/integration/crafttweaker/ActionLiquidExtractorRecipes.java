package galaxyspace.core.integration.crafttweaker;

import crafttweaker.IAction;
import crafttweaker.api.block.IBlock;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import galaxyspace.systems.SolarSystem.planets.overworld.recipes.AssemblyRecipes;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityFuelGenerator;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityLiquidExtractor;
import net.minecraft.item.ItemStack;

public class ActionLiquidExtractorRecipes {

	static class Add implements IAction	
	{
		private final ILiquidStack liquid;
		private final IBlock block;
		
		public Add(IBlock block, ILiquidStack liquid)
		{
			this.block = block;
			this.liquid = liquid;
		}
		
		@Override
		public void apply() {
			TileEntityLiquidExtractor.addBlockAndFluid(CraftTweakerMC.getBlock(block), CraftTweakerMC.getLiquidStack(liquid));
		}

		@Override
		public String describe() {
			return "Added Liquid Extractor recipe for " + this.block;
		}
		
	}	
	/*
	static class Remove implements IAction
	{
		private final ILiquidStack liquid;
		public Remove(ILiquidStack fluid)
		{
			this.liquid = fluid;
		}
		
		@Override
		public void apply() {
		
		}

		@Override
		public String describe() {
			return "Removed Fuel Generator recipe fluid for " + this.liquid;
		}
		
	}*/
}
