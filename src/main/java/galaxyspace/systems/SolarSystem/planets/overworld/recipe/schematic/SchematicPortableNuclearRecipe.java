package galaxyspace.systems.SolarSystem.planets.overworld.recipe.schematic;

import java.util.HashMap;

import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.util.GSRecipeUtil;
import micdoodle8.mods.galacticraft.core.recipe.NasaWorkbenchRecipe;
import micdoodle8.mods.galacticraft.planets.asteroids.items.AsteroidsItems;
import micdoodle8.mods.galacticraft.planets.mars.items.MarsItems;
import net.minecraft.item.ItemStack;

public class SchematicPortableNuclearRecipe {

	public static void registerRecipeWorkBench()
    {

		HashMap<Integer, ItemStack> input = new HashMap<Integer, ItemStack>();

		ItemStack hdp = new ItemStack(GSItems.HeavyDutyPlates, 1, 1);
		ItemStack plate = new ItemStack(AsteroidsItems.basicItem, 1, 0);
		ItemStack titan = new ItemStack(AsteroidsItems.basicItem, 1, 6);
		ItemStack laser = new ItemStack(AsteroidsItems.basicItem, 1, 8);
		ItemStack deshstick = new ItemStack(MarsItems.marsItemBasic, 1, 1);

		input.put(1, plate);
		input.put(5, plate);
		input.put(21, plate);
		input.put(25, plate);
		
		input.put(6, titan);
		input.put(10, titan);
		input.put(16, titan);
		input.put(20, titan);
		
		input.put(2, deshstick);
		input.put(3, deshstick);
		input.put(4, deshstick);
		input.put(22, deshstick);
		input.put(23, deshstick);
		input.put(24, deshstick);
		
		input.put(7, hdp);
		input.put(8, hdp);
		input.put(9, hdp);
		input.put(17, hdp);
		input.put(18, hdp);
		input.put(19, hdp);
		
		input.put(12, laser);
		input.put(14, laser);
		
		input.put(13, new ItemStack(GSItems.BasicItems, 1, 0));
		
		input.put(11, new ItemStack(GSBlocks.FluidTank, 1, 0));
		
		input.put(15, new ItemStack(GSBlocks.FuelGenerator, 1, 0));

		GSRecipeUtil.addPNRRecipe(new NasaWorkbenchRecipe(new ItemStack(GSBlocks.PortableNuclearReactor, 1, 0), input));

	}
}
