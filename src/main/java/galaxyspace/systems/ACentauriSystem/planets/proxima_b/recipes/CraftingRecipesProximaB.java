package galaxyspace.systems.ACentauriSystem.planets.proxima_b.recipes;

import galaxyspace.core.GSItems;
import galaxyspace.systems.ACentauriSystem.core.ACBlocks;
import galaxyspace.systems.SolarSystem.planets.overworld.recipes.RecyclerRecipes;
import micdoodle8.mods.galacticraft.core.GCItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CraftingRecipesProximaB {

	public static void loadRecipes() {
		addBlockRecipes();
		addItemRecipes();
		addBlockSmelting();
		addCompressor();
		addAssembly();
		addRecycler();
	}
	
	private static void addBlockRecipes() {
		
	}
	
	private static void addItemRecipes() {
		
	}
	
	private static void addBlockSmelting() {
		GameRegistry.addSmelting(new ItemStack(ACBlocks.PROXIMA_B_BLOCKS, 1, 5), new ItemStack(Items.GOLD_INGOT, 1, 0), 1.0F);
		GameRegistry.addSmelting(new ItemStack(ACBlocks.PROXIMA_B_BLOCKS, 1, 6), new ItemStack(GCItems.basicItem, 1, 4), 1.0F);
		GameRegistry.addSmelting(new ItemStack(ACBlocks.PROXIMA_B_BLOCKS, 1, 7), new ItemStack(GCItems.basicItem, 1, 3), 1.0F);
	}
	
	private static void addCompressor() {
		
	}
	
	private static void addAssembly() {
		
	}
	
	private static void addRecycler() {
		RecyclerRecipes.recycling().addNewRecipe(new ItemStack(ACBlocks.PROXINA_B_LOG_2, 1, 0), new ItemStack(Blocks.LOG, 1, 0), new FluidStack(FluidRegistry.WATER, 10));
		RecyclerRecipes.recycling().addNewRecipe(new ItemStack(ACBlocks.PROXINA_B_LOG_1, 1, 0), new ItemStack(Items.COAL, 1, 1), 60, null);
		
		RecyclerRecipes.recycling().addNewRecipe(new ItemStack(ACBlocks.PROXIMA_B_BLOCKS, 1, 4), new ItemStack(Items.FLINT, 1, 0), 20, null);		
		RecyclerRecipes.recycling().addNewRecipe(new ItemStack(ACBlocks.PROXIMA_B_BLOCKS, 1, 3), new ItemStack(ACBlocks.PROXIMA_B_BLOCKS, 1, 0), new FluidStack(FluidRegistry.WATER, 10));
		RecyclerRecipes.recycling().addNewRecipe(new ItemStack(ACBlocks.PROXIMA_B_BLOCKS, 1, 0), new ItemStack(GSItems.BASIC, 1, 6), 30, null);
		
	}
}
