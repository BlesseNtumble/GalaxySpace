package galaxyspace.systems.SolarSystem.planets.overworld.recipe.schematic;

import java.util.HashMap;

import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.util.GSRecipeUtil;
import micdoodle8.mods.galacticraft.core.items.GCItems;
import micdoodle8.mods.galacticraft.core.recipe.NasaWorkbenchRecipe;
import micdoodle8.mods.galacticraft.planets.asteroids.items.AsteroidsItems;
import micdoodle8.mods.galacticraft.planets.mars.blocks.MarsBlocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SchematicEngineRecipe {

	public static void registerRecipeWorkBench()
    {
		for(int k = 0; k <= 3; k++)
        {
			HashMap<Integer, ItemStack> input = new HashMap<Integer, ItemStack>();
			
			ItemStack plate;
			if(k == 0) plate = new ItemStack(AsteroidsItems.basicItem, 1, 0);	
			else plate = new ItemStack(GSItems.HeavyDutyPlates, 1, k-1);
			
			input.put(1, plate);
			input.put(2, new ItemStack(Item.getItemFromBlock(MarsBlocks.hydrogenPipe), 1, 0));
			input.put(3, plate);
			input.put(4, new ItemStack(Item.getItemFromBlock(MarsBlocks.hydrogenPipe), 1, 0));
			input.put(5, plate);
			input.put(6, plate);
			input.put(7, plate);
			input.put(8, new ItemStack(GCItems.fuelCanister.setContainerItem(null), 1, 1));
			input.put(9, new ItemStack(GCItems.fuelCanister.setContainerItem(null), 1, 1));
			input.put(10, plate);
			input.put(11, plate);
			input.put(12, new ItemStack(GSItems.RocketParts, 1, k*5+3));
			input.put(13, new ItemStack(Item.getItemFromBlock(MarsBlocks.hydrogenPipe), 1, 0));
			
			input.put(14, new ItemStack(AsteroidsItems.basicItem, 1, 1));

			input.put(15, new ItemStack(Item.getItemFromBlock(MarsBlocks.hydrogenPipe), 1, 0));
			input.put(16, new ItemStack(GSItems.RocketParts, 1, k*5+3));
			input.put(17, new ItemStack(GSItems.RocketParts, 1, k*5+3));
			input.put(18, new ItemStack(GSItems.RocketParts, 1, k*5+3));
			GSRecipeUtil.addEngineRecipe(new NasaWorkbenchRecipe(new ItemStack(GSItems.RocketParts, 1, k*5 + 2), input));
        }
    }
}
