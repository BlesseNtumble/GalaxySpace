package galaxyspace.systems.SolarSystem.moons.miranda.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import galaxyspace.core.registers.blocks.GSBlocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CraftingRecipesMiranda {

	public static void loadRecipes() {
		addBlockSmelting();
	}
	
	private static void addBlockSmelting() {
		  GameRegistry.addSmelting(new ItemStack(GSBlocks.MirandaBlocks, 1, 3), new ItemStack(Items.iron_ingot, 1, 0), 1.0F);
			 
	}
}
