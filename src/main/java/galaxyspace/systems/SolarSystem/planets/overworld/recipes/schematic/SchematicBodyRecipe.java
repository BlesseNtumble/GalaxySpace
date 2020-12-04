package galaxyspace.systems.SolarSystem.planets.overworld.recipes.schematic;

import java.util.HashMap;

import galaxyspace.core.GSItems;
import galaxyspace.core.util.GSRecipeUtil;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.recipe.NasaWorkbenchRecipe;
import micdoodle8.mods.galacticraft.planets.asteroids.items.AsteroidsItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SchematicBodyRecipe {

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
			input.put(3, new ItemStack(Item.getItemFromBlock(GCBlocks.spinThruster), 1, 0));
			input.put(4, plate);
			input.put(5, plate);
			input.put(6, plate);
			input.put(7, new ItemStack(GCItems.basicItem, 1, 20));
			input.put(8, new ItemStack(GCItems.basicItem, 1, 19));
			input.put(9, plate);
			input.put(10, new ItemStack(GSItems.ROCKET_MODULES, 1, 3));
			input.put(11, new ItemStack(GSItems.ROCKET_MODULES, 1, 3));
			
			if(k == 0) input.put(12, new ItemStack(GCItems.rocketEngine, 1, 0));
			else input.put(12, new ItemStack(AsteroidsItems.basicItem, 1, 1));

			input.put(13, new ItemStack(GCItems.basicItem, 1, 14));
			input.put(14, new ItemStack(GCItems.oxygenConcentrator, 1, 0));
			input.put(15, plate);
			input.put(16, new ItemStack(GSItems.ROCKET_MODULES, 1, 3));
			input.put(17, new ItemStack(GSItems.ROCKET_MODULES, 1, 3));

			if(k == 0) input.put(18, new ItemStack(GCItems.rocketEngine, 1, 0));
			else input.put(18, new ItemStack(AsteroidsItems.basicItem, 1, 1));
			
			input.put(19, plate);
			input.put(20, plate);
			input.put(21, new ItemStack(Item.getItemFromBlock(GCBlocks.spinThruster), 1, 0));
			input.put(22, plate);
			input.put(23, plate);
			input.put(24, plate);
			GSRecipeUtil.addBodyRecipe(new NasaWorkbenchRecipe(new ItemStack(GSItems.ROCKET_PARTS, 1, k*5 + 1), input));
        }
    }
}
