package galaxyspace.systems.SolarSystem.moons.io.recipes;

import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.registers.items.GSItems;
import micdoodle8.mods.galacticraft.core.GCItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CraftingRecipesIo {

	public static void loadRecipes() {
		addBlockSmelting();
	}
	
	 private static void addBlockSmelting() {
		 GameRegistry.addSmelting(new ItemStack(GSBlocks.IO_BLOCKS, 1, 3), new ItemStack(GCItems.basicItem, 1, 3), 1.0F); 
	 }
}
