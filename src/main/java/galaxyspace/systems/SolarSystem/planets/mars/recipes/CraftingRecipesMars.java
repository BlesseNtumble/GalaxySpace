package galaxyspace.systems.SolarSystem.planets.mars.recipes;

import cpw.mods.fml.common.registry.GameRegistry;
import galaxyspace.core.registers.blocks.GSBlocks;
import micdoodle8.mods.galacticraft.core.items.GCItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CraftingRecipesMars {

	 public static void loadRecipes() {
		 addBlockRecipes();
		 addBlockSmelting();
	 }
	 
	 private static void addBlockRecipes() {
				   
	 }
	 
	 private static void addBlockSmelting() {
		 GameRegistry.addSmelting(new ItemStack(GSBlocks.MarsOresBlocks, 1, 1), new ItemStack(Items.gold_ingot), 1.0F);
		 GameRegistry.addSmelting(new ItemStack(GSBlocks.MarsOresBlocks, 1, 5), new ItemStack(GCItems.basicItem, 1, 5), 1.0F);
	 }
}
