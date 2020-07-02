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

public class SchematicEngineRecipe {

	public static void registerRecipeWorkBench()
    {
		for(int k = 0; k <= 3; k++)
        {
			HashMap<Integer, ItemStack> input = new HashMap<Integer, ItemStack>();
			
			ItemStack plate;
			if(k == 0) plate = new ItemStack(AsteroidsItems.basicItem, 1, 5);	
			else plate = new ItemStack(GSItems.HDP, 1, k-1);
			
			input.put(1, plate);
			input.put(2, new ItemStack(Item.getItemFromBlock(GCBlocks.oxygenPipe), 1, 0));
			input.put(3, plate);
			input.put(4, new ItemStack(Item.getItemFromBlock(GCBlocks.oxygenPipe), 1, 0));
			input.put(5, plate);
			input.put(6, plate);
			input.put(7, plate);
			input.put(8, new ItemStack(GCItems.fuelCanister.setContainerItem(null), 1, 1));
			input.put(9, new ItemStack(GCItems.fuelCanister.setContainerItem(null), 1, 1));
			input.put(10, plate);
			input.put(11, plate);
			input.put(12, new ItemStack(GSItems.ROCKET_PARTS, 1, k*5+3));
			input.put(13, new ItemStack(Item.getItemFromBlock(GCBlocks.oxygenPipe), 1, 0));
			
			input.put(14, new ItemStack(AsteroidsItems.basicItem, 1, 1));

			input.put(15, new ItemStack(Item.getItemFromBlock(GCBlocks.oxygenPipe), 1, 0));
			input.put(16, new ItemStack(GSItems.ROCKET_PARTS, 1, k*5+3));
			input.put(17, new ItemStack(GSItems.ROCKET_PARTS, 1, k*5+3));
			input.put(18, new ItemStack(GSItems.ROCKET_PARTS, 1, k*5+3));
			GSRecipeUtil.addEngineRecipe(new NasaWorkbenchRecipe(new ItemStack(GSItems.ROCKET_PARTS, 1, k*5 + 2), input));
        }
    }
}
