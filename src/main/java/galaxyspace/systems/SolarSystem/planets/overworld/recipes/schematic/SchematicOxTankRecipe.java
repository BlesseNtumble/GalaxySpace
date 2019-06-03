package galaxyspace.systems.SolarSystem.planets.overworld.recipes.schematic;

import java.util.HashMap;

import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.util.GSRecipeUtil;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.recipe.NasaWorkbenchRecipe;
import micdoodle8.mods.galacticraft.planets.asteroids.items.AsteroidsItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class SchematicOxTankRecipe {

	public static void registerRecipeWorkBench()
    {
			HashMap<Integer, ItemStack> input = new HashMap<Integer, ItemStack>();
			input.put(1, new ItemStack(Blocks.WOOL, 1, 14));
			input.put(2, new ItemStack(AsteroidsItems.basicItem, 1, 6));
			input.put(3, new ItemStack(AsteroidsItems.basicItem, 1, 6));
			input.put(4, new ItemStack(AsteroidsItems.basicItem, 1, 6));
			input.put(5, new ItemStack(GCBlocks.oxygenPipe, 1, 0));
			input.put(6, new ItemStack(GCItems.oxygenConcentrator, 1, 0));
			input.put(7, new ItemStack(GCItems.oxTankLight, 1, GCItems.oxTankLight.getMaxDamage()));
			input.put(8, new ItemStack(AsteroidsItems.basicItem, 1, 6));
			input.put(9, new ItemStack(Blocks.WOOL, 1, 14));
			input.put(10, new ItemStack(AsteroidsItems.basicItem, 1, 6));
			input.put(11, new ItemStack(AsteroidsItems.basicItem, 1, 6));
			input.put(12, new ItemStack(AsteroidsItems.basicItem, 1, 6));
			GSRecipeUtil.addOxTankRecipe(new NasaWorkbenchRecipe(new ItemStack(GSItems.OXYGENTANK_TIER_EPP, 1, GSItems.OXYGENTANK_TIER_EPP.getMaxDamage()), input));
    }
}
