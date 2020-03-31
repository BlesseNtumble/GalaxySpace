package galaxyspace.systems.SolarSystem.moons.io.recipes;

import galaxyspace.core.registers.blocks.GSBlocks;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.util.RecipeUtil;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CraftingRecipesIo {

	public static void loadRecipes() {
		addBlockSmelting();
		addBlockRecipes();
	}
	
	private static void addBlockRecipes() {
		RecipeUtil.addRecipe(new ItemStack(GSBlocks.DUNGEON_BLOCKS, 8, 1), new Object[] { "XXX", "XYX", "XXX", 'X', new ItemStack(GSBlocks.IO_BLOCKS, 1, 10), 'Y', Items.LAVA_BUCKET});
		  
	}
	
	private static void addBlockSmelting() {
		 GameRegistry.addSmelting(new ItemStack(GSBlocks.IO_BLOCKS, 1, 3), new ItemStack(GCItems.basicItem, 1, 3), 1.0F); 
	}
}
