package galaxyspace.core.integration.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlock;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.galaxyspace")
public class CraftTweakerHandler {

	//ADD
	@ZenMethod
	public static void addAssemblyRecipe(IItemStack output, IItemStack... input) {
		CraftTweakerAPI.apply(new ActionAssemblerRecipes.Add(output, input));
	}

	@ZenMethod
	public static void addFuelGeneratorRecipe(ILiquidStack fluid, int burn_time, float mod_energy) {
		CraftTweakerAPI.apply(new ActionFuelGeneratorRecipes.Add(fluid, burn_time, mod_energy));
	}
	
	@ZenMethod
	public static void addHydroponicFarmRecipe(IItemStack seed, IItemStack product, IItemStack secproduct, int secchances, int stages, IBlock block, boolean product_rand, boolean secproduct_rand) {
		CraftTweakerAPI.apply(new ActionHydroponicRecipes.Add(seed, product, secproduct, secchances, stages, block, product_rand, secproduct_rand));
	}
	
	@ZenMethod
	public static void addLiquidExtractorRecipe(IBlock block, ILiquidStack fluid) {
		CraftTweakerAPI.apply(new ActionLiquidExtractorRecipes.Add(block, fluid));
	}
	
	@ZenMethod
	public static void addRecyclerRecipe(IItemStack stack, IItemStack result, ILiquidStack liquid, int chances) {
		CraftTweakerAPI.apply(new ActionRecyclerRecipes.Add(stack, result, liquid, chances));
	}
	
	@ZenMethod
	public static void addRocketAssemblyRecipe(IItemStack output, IItemStack... input) {
		CraftTweakerAPI.apply(new ActionRocketAssemblerRecipes.Add(output, input));
	}
	/////////////////////////////////////////////////////////////////////////////////////////////
	//REMOVE
	@ZenMethod
	public static void removeAssemblyRecipe(IItemStack output) {
		CraftTweakerAPI.apply(new ActionAssemblerRecipes.Remove(output));
	}
	
	@ZenMethod
	public static void removeFuelGeneratorRecipe(ILiquidStack fluid) {
		CraftTweakerAPI.apply(new ActionFuelGeneratorRecipes.Remove(fluid));
	}
	
	@ZenMethod
	public static void removeHydroponicFarmRecipe(IItemStack seed) {
		CraftTweakerAPI.apply(new ActionHydroponicRecipes.Remove(seed));
	}
	
	@ZenMethod
	public static void removeRecyclerRecipe(IItemStack stack) {
		CraftTweakerAPI.apply(new ActionRecyclerRecipes.Remove(stack));
	}
	
	@ZenMethod
	public static void removeRocketAssemblyRecipe(IItemStack output) {
		CraftTweakerAPI.apply(new ActionRocketAssemblerRecipes.Remove(output));
	}
	
	
}
