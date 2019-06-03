package galaxyspace.systems.SolarSystem.moons.miranda.recipes;

import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.systems.SolarSystem.planets.overworld.recipes.RecyclerRecipes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CraftingRecipesMiranda {

	public static void loadRecipes() {
		addBlockSmelting();
		addRecycler();
	}
	
	private static void addBlockSmelting() {
		 GameRegistry.addSmelting(new ItemStack(GSBlocks.MIRANDA_BLOCKS, 1, 3), new ItemStack(Items.IRON_INGOT, 1, 0), 1.0F); 
		 GameRegistry.addSmelting(new ItemStack(GSBlocks.MIRANDA_BLOCKS, 1, 5), new ItemStack(Items.DIAMOND, 1, 0), 1.0F); 
		 GameRegistry.addSmelting(new ItemStack(GSBlocks.MIRANDA_BLOCKS, 1, 6), new ItemStack(Items.QUARTZ, 1, 0), 1.0F); 
		 GameRegistry.addSmelting(new ItemStack(GSBlocks.MIRANDA_BLOCKS, 1, 7), new ItemStack(GSItems.INGOTS, 1, 0), 1.0F); 
		 GameRegistry.addSmelting(new ItemStack(GSBlocks.MIRANDA_BLOCKS, 1, 8), new ItemStack(GSItems.INGOTS, 1, 2), 1.0F); 
	}
	 
	private static void addRecycler() {
		RecyclerRecipes.recycling().addNewRecipe(new ItemStack(GSBlocks.MIRANDA_BLOCKS, 1, 15), new ItemStack(Items.COAL, 1, 0), 50, new FluidStack(FluidRegistry.WATER, 10));		
	}
}
