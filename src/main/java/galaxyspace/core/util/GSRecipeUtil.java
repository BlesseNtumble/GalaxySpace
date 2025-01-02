package galaxyspace.core.util;

import java.util.ArrayList;
import java.util.List;

import galaxyspace.systems.SolarSystem.planets.overworld.inventory.schematics.*;
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
	private static List<INasaWorkbenchRecipe> rocketBenchPNRRecipes = new ArrayList<INasaWorkbenchRecipe>();
    private static List<INasaWorkbenchRecipe> rocketBenchT1Recipes = new ArrayList<INasaWorkbenchRecipe>();
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

        return null;
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

        return null;
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

        return null;
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

        return null;
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

        return null;
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

        return null;
    }
	
	public static ItemStack findMatchingPNRRecipe(InventorySchematicPortNuclearReactor craftMatrix)
    {
        for (INasaWorkbenchRecipe recipe : GSRecipeUtil.getPNRRecipes())
        {
            if (recipe.matches(craftMatrix))
            {
                return recipe.getRecipeOutput();
            }
        }

        return null;
    }
    public static ItemStack findMatchingSpaceshipT1Recipe(InventorySchematicTier1Rocket inventoryRocketBench)
    {
        for (INasaWorkbenchRecipe recipe : GSRecipeUtil.getRocketT1Recipes())
        {
            if (recipe.matches(inventoryRocketBench))
            {
                return recipe.getRecipeOutput();
            }
        }

        return null;
    }
    
	public static ItemStack findMatchingSpaceshipT2Recipe(InventorySchematicTier2Rocket inventoryRocketBench)
    {
        for (INasaWorkbenchRecipe recipe : GSRecipeUtil.getRocketT2Recipes())
        {
            if (recipe.matches(inventoryRocketBench))
            {
                return recipe.getRecipeOutput();
            }
        }

        return null;
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
    
    public static void addPNRRecipe(INasaWorkbenchRecipe recipe)
    {
    	GSRecipeUtil.rocketBenchPNRRecipes.add(recipe);
    }
    
    public static List<INasaWorkbenchRecipe> getPNRRecipes()
    {
        return GSRecipeUtil.rocketBenchPNRRecipes;
    }

    public static void addT1RocketRecipe(INasaWorkbenchRecipe recipe)
    {
        GSRecipeUtil.rocketBenchT1Recipes.add(recipe);
    }
    public static void addT2RocketRecipe(INasaWorkbenchRecipe recipe)
    {
    	GSRecipeUtil.rocketBenchT2Recipes.add(recipe);
    }
    public static List<INasaWorkbenchRecipe> getRocketT1Recipes()
    {
        return GSRecipeUtil.rocketBenchT1Recipes;
    }
    public static List<INasaWorkbenchRecipe> getRocketT2Recipes()
    {
        return GSRecipeUtil.rocketBenchT2Recipes;
    }
    
}
