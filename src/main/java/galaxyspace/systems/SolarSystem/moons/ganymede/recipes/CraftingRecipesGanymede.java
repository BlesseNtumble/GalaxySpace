package galaxyspace.systems.SolarSystem.moons.ganymede.recipes;

import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.registers.items.GSItems;
import micdoodle8.mods.galacticraft.planets.asteroids.items.AsteroidsItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CraftingRecipesGanymede {

	public static void loadRecipes() {
		addBlockSmelting();
	}
	
	 private static void addBlockSmelting() {
		 GameRegistry.addSmelting(new ItemStack(GSBlocks.GANYMEDE_BLOCKS, 1, 2), new ItemStack(GSItems.INGOTS, 1, 1), 1.0F); 
		 GameRegistry.addSmelting(new ItemStack(GSBlocks.GANYMEDE_BLOCKS, 1, 3), new ItemStack(AsteroidsItems.basicItem, 1, 0), 1.0F); 
	 }
}
