package galaxyspace.core.client.jei.recycler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import galaxyspace.GalaxySpace;
import galaxyspace.systems.SolarSystem.planets.overworld.recipes.RecyclerRecipes;
import galaxyspace.systems.SolarSystem.planets.overworld.recipes.RecyclerRecipes.RecycleRecipe;
import net.minecraft.item.ItemStack;

public class UniversalRecyclerRecipeMaker
{
    public static List<UniversalRecyclerRecipeWrapper> getRecipesList()
    {
       
    	List<UniversalRecyclerRecipeWrapper> recipes = new ArrayList<>();
    	    	
    	for(RecycleRecipe entry : RecyclerRecipes.recycling().getRecipes())
    	{    
    		recipes.add(new UniversalRecyclerRecipeWrapper(entry.getInput(), entry.getOutput(), entry.getFluidStack(), entry.getChance()));
    	}
    	
        return recipes;
    }
}
