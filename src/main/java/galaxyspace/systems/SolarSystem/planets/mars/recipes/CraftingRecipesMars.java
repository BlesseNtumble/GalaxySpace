package galaxyspace.systems.SolarSystem.planets.mars.recipes;

import galaxyspace.core.registers.blocks.GSBlocks;
import micdoodle8.mods.galacticraft.core.GCItems;
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
	}
}
