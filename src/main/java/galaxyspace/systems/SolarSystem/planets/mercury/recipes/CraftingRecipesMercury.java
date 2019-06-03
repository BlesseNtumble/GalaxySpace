package galaxyspace.systems.SolarSystem.planets.mercury.recipes;

import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.registers.items.GSItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CraftingRecipesMercury {

	public static void loadRecipes() {
		addBlockSmelting();
	}
	
	 private static void addBlockSmelting() {
		 GameRegistry.addSmelting(new ItemStack(GSBlocks.MERCURY_BLOCKS, 1, 3), new ItemStack(GSItems.INGOTS, 1, 2), 1.0F); 
		 GameRegistry.addSmelting(new ItemStack(GSBlocks.MERCURY_BLOCKS, 1, 4), new ItemStack(Items.IRON_INGOT, 1, 0), 1.0F); 
		 GameRegistry.addSmelting(new ItemStack(GSBlocks.MERCURY_BLOCKS, 1, 5), new ItemStack(GSItems.INGOTS, 1, 1), 1.0F); 
	 }
}
