package galaxyspace.core.client.jei.recycler;

import java.util.ArrayList;
import java.util.List;

import galaxyspace.systems.SolarSystem.planets.overworld.recipes.RecyclerRecipes;
import galaxyspace.systems.SolarSystem.planets.overworld.recipes.RecyclerRecipes.RecycleRecipe;

public class UniversalRecyclerRecipeMaker
{
    public static List<UniversalRecyclerRecipeWrapper> getRecipesList()
    {
       
    	List<UniversalRecyclerRecipeWrapper> recipes = new ArrayList<>();
    	    	
    	for(RecycleRecipe entry : RecyclerRecipes.recycling().getRecipes())
    	{    
    		recipes.add(new UniversalRecyclerRecipeWrapper(entry.getInput(), entry.getOutput(), entry.getOutput_2(), entry.getFluidStack(), entry.getChance(), entry.getChance_2()));
    	}
    	
        return recipes;
    }
}
