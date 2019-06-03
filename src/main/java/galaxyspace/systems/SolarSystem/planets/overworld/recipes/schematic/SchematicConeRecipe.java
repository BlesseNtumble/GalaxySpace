package galaxyspace.systems.SolarSystem.planets.overworld.recipes.schematic;

import java.util.HashMap;

import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.util.GSRecipeUtil;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.recipe.NasaWorkbenchRecipe;
import micdoodle8.mods.galacticraft.planets.asteroids.items.AsteroidsItems;
import net.minecraft.item.ItemStack;

public class SchematicConeRecipe {

	public static void registerRecipeWorkBench()
    {
		for(int k = 0; k <= 3; k++)
        {
			ItemStack plate;
			if(k == 0) plate = new ItemStack(AsteroidsItems.basicItem, 1, 5);	
			else plate = new ItemStack(GSItems.HDP, 1, k-1);
			
			HashMap<Integer, ItemStack> input = new HashMap<Integer, ItemStack>();
			input.put(1, plate);
			input.put(2, plate);
			input.put(3, new ItemStack(GSBlocks.FUTURE_GLASS_BASIC, 1, 0));
			input.put(4, plate);
			input.put(5, plate);
			input.put(6, plate);
			input.put(7, plate);
			input.put(8, plate);
			input.put(9, new ItemStack(GCItems.oxygenVent, 1, 0));
			input.put(10, new ItemStack(GCItems.oxygenFan, 1, 0));
			input.put(11, new ItemStack(GCBlocks.sealableBlock, 1, 14));
			input.put(12, new ItemStack(GCItems.rocketEngine, 1, 0));
			input.put(13, new ItemStack(GCBlocks.screen, 1, 0));
			input.put(14, new ItemStack(GCItems.partBuggy, 1, 1));
			input.put(15, new ItemStack(GCItems.oxTankHeavy, 1, 0));
			input.put(16, new ItemStack(GSItems.ROCKET_MODULES, 1, 2));
			input.put(17, new ItemStack(GCItems.oxygenVent, 1, 0));
			input.put(18, new ItemStack(GCItems.oxygenFan, 1, 0));
			input.put(19, new ItemStack(GCBlocks.sealableBlock, 1, 14));
			input.put(20, new ItemStack(GCItems.rocketEngine, 1, 0));
			input.put(21, plate);
			input.put(22, plate);
			input.put(23, plate);
			input.put(24, plate);
			GSRecipeUtil.addConeRecipe(new NasaWorkbenchRecipe(new ItemStack(GSItems.ROCKET_PARTS, 1, k*5), input));
        }
    }
}
