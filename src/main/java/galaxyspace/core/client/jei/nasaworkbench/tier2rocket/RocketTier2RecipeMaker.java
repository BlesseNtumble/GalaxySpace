package galaxyspace.core.client.jei.nasaworkbench.tier2rocket;

import java.util.ArrayList;
import java.util.List;

import galaxyspace.core.util.GSRecipeUtil;
import micdoodle8.mods.galacticraft.api.recipe.INasaWorkbenchRecipe;
import micdoodle8.mods.galacticraft.core.client.jei.tier1rocket.Tier1RocketRecipeMaker;

public class RocketTier2RecipeMaker
{
    public static List<INasaWorkbenchRecipe> getRecipesList()
    {
        List<INasaWorkbenchRecipe> recipes = new ArrayList<>();

        int chestCount = -1;
        for (INasaWorkbenchRecipe recipe : GSRecipeUtil.getRocketT2Recipes())
        {
            int chests = Tier1RocketRecipeMaker.countChests(recipe); 
            if (chests == chestCount)
                continue;
            chestCount = chests;
            recipes.add(recipe);
        }

        return recipes;
    }
}
