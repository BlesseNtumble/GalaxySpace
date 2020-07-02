package galaxyspace.systems.SolarSystem.planets.mars.recipes;

import galaxyspace.core.GSBlocks;
import galaxyspace.systems.SolarSystem.planets.overworld.recipes.RecyclerRecipes;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.planets.mars.blocks.MarsBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CraftingRecipesMars {
	
	public static void loadRecipes() {
		addBlockSmelting();
	}

	private static void addBlockSmelting() {
		 GameRegistry.addSmelting(new ItemStack(GSBlocks.MARS_ORES, 1, 1), new ItemStack(Items.GOLD_INGOT, 1, 0), 1.0F); 
		 GameRegistry.addSmelting(new ItemStack(GSBlocks.MARS_ORES, 1, 5), new ItemStack(GCItems.basicItem, 1, 5), 1.0F); 
	
		 RecyclerRecipes.recycling().addNewRecipe(new ItemStack(MarsBlocks.marsBlock, 1, 4), new ItemStack(Blocks.COBBLESTONE, 1, 0), null);
		 RecyclerRecipes.recycling().addNewRecipe(new ItemStack(MarsBlocks.marsBlock, 1, 9), new ItemStack(Blocks.STONE, 1, 0), null);
	}
}
