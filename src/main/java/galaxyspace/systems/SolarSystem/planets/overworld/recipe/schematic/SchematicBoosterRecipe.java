package galaxyspace.systems.SolarSystem.planets.overworld.recipe.schematic;

import java.util.HashMap;

import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.util.GSRecipeUtil;
import micdoodle8.mods.galacticraft.core.items.GCItems;
import micdoodle8.mods.galacticraft.core.recipe.NasaWorkbenchRecipe;
import micdoodle8.mods.galacticraft.planets.asteroids.items.AsteroidsItems;
import micdoodle8.mods.galacticraft.planets.mars.blocks.MarsBlocks;
import net.minecraft.item.ItemStack;

public class SchematicBoosterRecipe {

	public static void registerRecipeWorkBench()
    {		
		for(int k = 0; k <= 3; k++)
        {			
			HashMap<Integer, ItemStack> input = new HashMap<Integer, ItemStack>();
			
			ItemStack plate;
			if(k == 0) plate = new ItemStack(AsteroidsItems.basicItem, 1, 0);
			else plate = new ItemStack(GSItems.HeavyDutyPlates, 1, k-1);
			
			input.put(1, plate);
			input.put(2, plate);
			input.put(3, plate);
			input.put(4, plate);
			input.put(5, new ItemStack(GCItems.fuelCanister.setContainerItem(null), 1, 1));
			input.put(6, new ItemStack(MarsBlocks.hydrogenPipe, 1, 0));
			
			input.put(7, new ItemStack(AsteroidsItems.basicItem, 1, 1));
			
			input.put(8, plate);
			input.put(9, plate);
			input.put(10, plate);
			GSRecipeUtil.addBoosterRecipe(new NasaWorkbenchRecipe(new ItemStack(GSItems.RocketParts, 1, k*5 + 3), input));
        }
    }
}
