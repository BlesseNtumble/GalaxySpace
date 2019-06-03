package galaxyspace.core.util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import galaxyspace.systems.SolarSystem.planets.overworld.inventory.schematics.InventorySchematicBody;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.schematics.InventorySchematicBooster;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.schematics.InventorySchematicCone;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.schematics.InventorySchematicEngine;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.schematics.InventorySchematicFins;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.schematics.InventorySchematicOxTank;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.schematics.InventorySchematicTier2Rocket;
import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.recipe.INasaWorkbenchRecipe;
import net.minecraft.item.ItemStack;

public class GSRecipeUtil
{
	private static List<INasaWorkbenchRecipe> rocketBenchConeRecipes = new ArrayList<INasaWorkbenchRecipe>();
	private static List<INasaWorkbenchRecipe> rocketBenchBodyRecipes = new ArrayList<INasaWorkbenchRecipe>();
	private static List<INasaWorkbenchRecipe> rocketBenchEngineRecipes = new ArrayList<INasaWorkbenchRecipe>();
	private static List<INasaWorkbenchRecipe> rocketBenchBoosterRecipes = new ArrayList<INasaWorkbenchRecipe>();
	private static List<INasaWorkbenchRecipe> rocketBenchFinsRecipes = new ArrayList<INasaWorkbenchRecipe>();
	private static List<INasaWorkbenchRecipe> rocketBenchOxTankRecipes = new ArrayList<INasaWorkbenchRecipe>();
	
	private static List<INasaWorkbenchRecipe> rocketBenchT2Recipes = new ArrayList<INasaWorkbenchRecipe>();
    
	public static ItemStack findMatchingConeRecipe(InventorySchematicCone craftMatrix)
    {
        for (INasaWorkbenchRecipe recipe : GSRecipeUtil.getConeRecipes())
        {
            if (recipe.matches(craftMatrix))
            {
                return recipe.getRecipeOutput();
            }
        }

        return ItemStack.EMPTY;
    }
	
	public static ItemStack findMatchingBodyRecipe(InventorySchematicBody craftMatrix)
    {
        for (INasaWorkbenchRecipe recipe : GSRecipeUtil.getBodyRecipes())
        {
            if (recipe.matches(craftMatrix))
            {
                return recipe.getRecipeOutput();
            }
        }

        return ItemStack.EMPTY;
    }
	
	public static ItemStack findMatchingEngineRecipe(InventorySchematicEngine craftMatrix)
    {
        for (INasaWorkbenchRecipe recipe : GSRecipeUtil.getEngineRecipes())
        {
            if (recipe.matches(craftMatrix))
            {
                return recipe.getRecipeOutput();
            }
        }

        return ItemStack.EMPTY;
    }
	
	public static ItemStack findMatchingBoosterRecipe(InventorySchematicBooster craftMatrix)
    {
        for (INasaWorkbenchRecipe recipe : GSRecipeUtil.getBoosterRecipes())
        {
            if (recipe.matches(craftMatrix))
            {
                return recipe.getRecipeOutput();
            }
        }

        return ItemStack.EMPTY;
    }
	
	public static ItemStack findMatchingFinsRecipe(InventorySchematicFins craftMatrix)
    {
        for (INasaWorkbenchRecipe recipe : GSRecipeUtil.getFinsRecipes())
        {
            if (recipe.matches(craftMatrix))
            {
                return recipe.getRecipeOutput();
            }
        }

        return ItemStack.EMPTY;
    }

	public static ItemStack findMatchingOxTankRecipe(InventorySchematicOxTank craftMatrix)
    {
        for (INasaWorkbenchRecipe recipe : GSRecipeUtil.getOxTankRecipes())
        {
            if (recipe.matches(craftMatrix))
            {
                return recipe.getRecipeOutput();
            }
        }

        return ItemStack.EMPTY;
    }
	
	@Nonnull
	public static ItemStack findMatchingSpaceshipT2Recipe(InventorySchematicTier2Rocket inventoryRocketBench)
	{
		for (INasaWorkbenchRecipe recipe : GSRecipeUtil.getRocketT2Recipes())
		{
			if (recipe.matches(inventoryRocketBench))
			{
				return recipe.getRecipeOutput();
			}
		}

		return ItemStack.EMPTY;
	}
    
    // -----------------------------------------------------------------------------------------
    public static void addConeRecipe(INasaWorkbenchRecipe recipe)
    {
    	GSRecipeUtil.rocketBenchConeRecipes.add(recipe);
    }
    
    public static List<INasaWorkbenchRecipe> getConeRecipes()
    {
        return GSRecipeUtil.rocketBenchConeRecipes;
    }
    
    public static void addBodyRecipe(INasaWorkbenchRecipe recipe)
    {
    	GSRecipeUtil.rocketBenchBodyRecipes.add(recipe);
    }
    
    public static List<INasaWorkbenchRecipe> getBodyRecipes()
    {
        return GSRecipeUtil.rocketBenchBodyRecipes;
    }
    
    public static void addEngineRecipe(INasaWorkbenchRecipe recipe)
    {
    	GSRecipeUtil.rocketBenchEngineRecipes.add(recipe);
    }
    
    public static List<INasaWorkbenchRecipe> getEngineRecipes()
    {
        return GSRecipeUtil.rocketBenchEngineRecipes;
    }
    
    public static void addBoosterRecipe(INasaWorkbenchRecipe recipe)
    {
    	GSRecipeUtil.rocketBenchBoosterRecipes.add(recipe);
    }
    
    public static List<INasaWorkbenchRecipe> getBoosterRecipes()
    {
        return GSRecipeUtil.rocketBenchBoosterRecipes;
    }
    
    public static void addFinsRecipe(INasaWorkbenchRecipe recipe)
    {
    	GSRecipeUtil.rocketBenchFinsRecipes.add(recipe);
    }
    
    public static List<INasaWorkbenchRecipe> getFinsRecipes()
    {
        return GSRecipeUtil.rocketBenchFinsRecipes;
    }
    
    public static void addOxTankRecipe(INasaWorkbenchRecipe recipe)
    {
    	GSRecipeUtil.rocketBenchOxTankRecipes.add(recipe);
    }
    
    public static List<INasaWorkbenchRecipe> getOxTankRecipes()
    {
        return GSRecipeUtil.rocketBenchOxTankRecipes;
    }

    public static void addT2RocketRecipe(INasaWorkbenchRecipe recipe)
    {
    	GSRecipeUtil.rocketBenchT2Recipes.add(recipe);
    }
    
    public static List<INasaWorkbenchRecipe> getRocketT2Recipes()
    {
        return GSRecipeUtil.rocketBenchT2Recipes;
    }
    
}
