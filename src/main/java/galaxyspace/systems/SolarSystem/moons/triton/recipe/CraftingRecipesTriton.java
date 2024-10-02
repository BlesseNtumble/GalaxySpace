package galaxyspace.systems.SolarSystem.moons.triton.recipe;

import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.systems.SolarSystem.planets.overworld.recipe.RecyclerRecipes;
import micdoodle8.mods.galacticraft.planets.asteroids.AsteroidsModule;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class CraftingRecipesTriton {

	public static void loadRecipes() {
		addRecycler();
	}
	
	private static void addBlockRecipes() {
		
	}
	
	private static void addRecycler() {
		  RecyclerRecipes.recycling().addNewRecipe(new ItemStack(GSBlocks.TritonBlocks, 1, 0), null, new FluidStack(AsteroidsModule.fluidLiquidNitrogen, 10));
		  RecyclerRecipes.recycling().addNewRecipe(new ItemStack(GSBlocks.TritonBlocks, 1, 5), new ItemStack(GSItems.BasicItems, 1, 16), null);
			
	}
}
