package galaxyspace.systems.SolarSystem.planets.overworld.recipes.schematic;

import java.util.HashMap;

import galaxyspace.core.GSItems;
import galaxyspace.core.util.GSRecipeUtil;
import micdoodle8.mods.galacticraft.core.recipe.NasaWorkbenchRecipe;
import micdoodle8.mods.galacticraft.planets.asteroids.items.AsteroidsItems;
import net.minecraft.item.ItemStack;

public class SchematicFinsRecipe {

	public static void registerRecipeWorkBench()
    {
		for(int k = 0; k <= 3; k++)
        {
			HashMap<Integer, ItemStack> input = new HashMap<Integer, ItemStack>();
			
			ItemStack plate;
			if(k == 0) plate = new ItemStack(AsteroidsItems.basicItem, 1, 5);	
			else plate = new ItemStack(GSItems.HDP, 1, k-1);
			
			input.put(1, plate);
			input.put(2, plate);
			input.put(3, plate);
			input.put(4, plate);
			input.put(5, plate);
			input.put(6, plate);
			input.put(7, plate);
			input.put(8, plate);
			input.put(9, plate);
			input.put(10, plate);
			input.put(11, plate);
			input.put(12, plate);
			GSRecipeUtil.addFinsRecipe(new NasaWorkbenchRecipe(new ItemStack(GSItems.ROCKET_PARTS, 1, k*5 + 4), input));
        }
    }
}
