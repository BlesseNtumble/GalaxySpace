package galaxyspace.core.client.jei.nasaworkbench.engine;

import java.util.ArrayList;
import java.util.List;

import galaxyspace.core.util.GSRecipeUtil;
import micdoodle8.mods.galacticraft.api.recipe.INasaWorkbenchRecipe;

public class EngineRecipeMaker
{
     public static List<INasaWorkbenchRecipe> getRecipesList()
    {
        List<INasaWorkbenchRecipe> recipes = new ArrayList<>();

        int chestCount = -1;
        for (INasaWorkbenchRecipe recipe : GSRecipeUtil.getEngineRecipes())
        {
            //int chests = NoseConeRecipeMaker.countChests(recipe); 
          //  if (chests == chestCount)
               // continue;
          //  chestCount = chests;
            recipes.add(recipe);
        }

        return recipes;
    }
/*
    public static int countChests(INasaWorkbenchRecipe recipe)
    {
        int count = 0;
        ItemStack chest = new ItemStack(Blocks.CHEST);
        for (Entry<Integer, ItemStack> e : recipe.getRecipeInput().entrySet())
        {
            if (ItemStack.areItemsEqual(chest, e.getValue()))
                count++;
        }
        return count;
    }*/
}
