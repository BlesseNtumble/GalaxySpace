package galaxyspace.systems.SolarSystem.planets.haumea.recipes;

import galaxyspace.core.GSBlocks;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.util.RecipeUtil;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CraftingRecipesHaumea {

	public static void loadRecipes() {
		addBlockSmelting();
		addBlockRecipes();
	}
	
	private static void addBlockRecipes() {
		  
	}
	
	private static void addBlockSmelting() {
		GameRegistry.addSmelting(new ItemStack(GSBlocks.HAUMEA_BLOCKS, 1, 2), new ItemStack(GSBlocks.HAUMEA_BLOCKS, 1, 1), 1.0F); 
		GameRegistry.addSmelting(new ItemStack(GSBlocks.HAUMEA_BLOCKS, 1, 3), new ItemStack(GCItems.basicItem, 1, 5), 1.0F); 
	}
}
