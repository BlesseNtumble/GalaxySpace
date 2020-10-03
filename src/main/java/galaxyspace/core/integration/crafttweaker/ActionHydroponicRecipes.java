package galaxyspace.core.integration.crafttweaker;

import crafttweaker.IAction;
import crafttweaker.api.block.IBlock;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.item.MCItemStack;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityHydroponicBase;

public class ActionHydroponicRecipes {

	static class Add implements IAction	
	{
		private final IItemStack seed, product, secproduct;
		private final int secchance, stages;
		private final IBlock block;
		private final boolean[] rand_counts = new boolean[2];
		
		public Add(IItemStack seed, IItemStack product, int stages, IBlock block, boolean product_rand)
		{
			this(seed, product, MCItemStack.EMPTY, 0, stages, block, product_rand, false);
		}
		public Add(IItemStack seed, IItemStack product, IItemStack secproduct, int secchances, int stages, IBlock block, boolean product_rand, boolean secproduct_rand)
		{
			this.seed = seed;
			this.product = product;
			this.secproduct = secproduct;
			this.secchance = secchances;
			this.stages = stages;
			this.block = block;
			this.rand_counts[0] = product_rand;
			this.rand_counts[1] = secproduct_rand;
		}
		
		@Override
		public void apply() {
			TileEntityHydroponicBase.addPlant(CraftTweakerMC.getItemStack(seed), CraftTweakerMC.getItemStack(product), CraftTweakerMC.getItemStack(secproduct), secchance, CraftTweakerMC.getBlock(block), stages, rand_counts);
		}

		@Override
		public String describe() {
			return "Added Hydroponic Farm plant recipe for " + this.seed;
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
			TileEntityHydroponicBase.removePlant(CraftTweakerMC.getItemStack(item));
		}

		@Override
		public String describe() {
			return "Removed Hydroponic Farm plant recipe for " + this.item;
		}
		
	}
}
