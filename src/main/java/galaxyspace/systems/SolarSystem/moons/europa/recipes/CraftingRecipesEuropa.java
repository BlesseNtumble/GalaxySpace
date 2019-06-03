package galaxyspace.systems.SolarSystem.moons.europa.recipes;

import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.registers.items.GSItems;
import micdoodle8.mods.galacticraft.core.GCItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CraftingRecipesEuropa {

	public static void loadRecipes() {
		addBlockSmelting();
	}
	
	 private static void addBlockSmelting() {
		 GameRegistry.addSmelting(new ItemStack(GSBlocks.EUROPA_BLOCKS, 1, 5), new ItemStack(GCItems.basicItem, 1, 5), 1.0F); 
	 }
}
