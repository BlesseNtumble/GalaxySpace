package galaxyspace.systems.SolarSystem.moons.phobos.recipes;

import galaxyspace.core.GSBlocks;
import galaxyspace.core.GSFluids;
import galaxyspace.core.GSItems;
import galaxyspace.systems.SolarSystem.planets.overworld.recipes.RecyclerRecipes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CraftingRecipesPhobos {

	public static void loadRecipes() {
		addBlockSmelting();
		addRecycler();
	}
	
	private static void addBlockSmelting() {
		GameRegistry.addSmelting(new ItemStack(GSBlocks.PHOBOS_BLOCKS, 1, 2), new ItemStack(Items.IRON_INGOT, 1, 0), 1.0F); 
		GameRegistry.addSmelting(new ItemStack(GSBlocks.PHOBOS_BLOCKS, 1, 4), new ItemStack(GSItems.INGOTS, 1, 2), 1.0F); 
	}
	 
	private static void addRecycler() {
		RecyclerRecipes.recycling().addNewRecipe(new ItemStack(GSBlocks.PHOBOS_BLOCKS, 1, 0), ItemStack.EMPTY, new FluidStack(GSFluids.Helium3, 10));		
	}
}
