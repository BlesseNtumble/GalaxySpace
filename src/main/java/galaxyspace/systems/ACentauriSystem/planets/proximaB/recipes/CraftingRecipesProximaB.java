package galaxyspace.systems.ACentauriSystem.planets.proximaB.recipes;

import cpw.mods.fml.common.registry.GameRegistry;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.systems.ACentauriSystem.core.registers.blocks.ACBlocks;
import galaxyspace.systems.SolarSystem.planets.overworld.recipe.RecyclerRecipes;
import micdoodle8.mods.galacticraft.core.items.GCItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

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
		GameRegistry.addSmelting(new ItemStack(ACBlocks.ProximaBBlocks, 1, 5), new ItemStack(Items.gold_ingot, 1, 0), 1.0F);
		GameRegistry.addSmelting(new ItemStack(ACBlocks.ProximaBBlocks, 1, 6), new ItemStack(GCItems.basicItem, 1, 4), 1.0F);
		GameRegistry.addSmelting(new ItemStack(ACBlocks.ProximaBBlocks, 1, 7), new ItemStack(GCItems.basicItem, 1, 3), 1.0F);
	}
	
	private static void addCompressor() {
		
	}
	
	private static void addAssembly() {
		
	}
	
	private static void addRecycler() {
		RecyclerRecipes.recycling().addNewRecipe(new ItemStack(ACBlocks.ProximaBFrozenLogs, 1, 0), new ItemStack(Blocks.log, 1, 0), new FluidStack(FluidRegistry.WATER, 10));
		RecyclerRecipes.recycling().addNewRecipe(new ItemStack(ACBlocks.ProximaBBurntLogs, 1, 0), new ItemStack(Items.coal, 1, 1), 60, null);
		
		RecyclerRecipes.recycling().addNewRecipe(new ItemStack(ACBlocks.ProximaBBlocks, 1, 4), new ItemStack(Items.flint, 1, 0), 20, null);		
		RecyclerRecipes.recycling().addNewRecipe(new ItemStack(ACBlocks.ProximaBBlocks, 1, 3), new ItemStack(ACBlocks.ProximaBBlocks, 1, 0), new FluidStack(FluidRegistry.WATER, 10));
		RecyclerRecipes.recycling().addNewRecipe(new ItemStack(ACBlocks.ProximaBBlocks, 1, 0), new ItemStack(GSItems.BasicItems, 1, 8), 30, null);
		
	}
}
