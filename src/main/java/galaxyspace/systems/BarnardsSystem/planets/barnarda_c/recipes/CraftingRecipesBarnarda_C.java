package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.recipes;

import galaxyspace.core.registers.items.GSItems;
import galaxyspace.systems.BarnardsSystem.core.registers.BRBlocks;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.util.RecipeUtil;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CraftingRecipesBarnarda_C {

	public static void loadRecipes() {
		addBlockRecipes();
		addItemRecipes();
		addBlockSmelting();
	}
	
	private static void addBlockRecipes() {
		 RecipeUtil.addRecipe(new ItemStack(BRBlocks.BARNARDA_C_BLOCKS, 1, 5), new Object[] { "XX", "XX", 'X', new ItemStack(BRBlocks.BARNARDA_C_BLOCKS, 1, 1)});
		 RecipeUtil.addRecipe(new ItemStack(BRBlocks.BARNARDA_C_BLOCKS, 4, 6), new Object[] { "X", 'X', new ItemStack(BRBlocks.BARNARDA_C_VIOLET_LOG, 1, 0)});
		  
	}
	
	private static void addItemRecipes() {

	}
	
	private static void addBlockSmelting() {
		 GameRegistry.addSmelting(new ItemStack(BRBlocks.BARNARDA_C_ORES, 1, 1), new ItemStack(Items.IRON_INGOT, 1, 0), 1.0F);
		 GameRegistry.addSmelting(new ItemStack(BRBlocks.BARNARDA_C_ORES, 1, 2), new ItemStack(Items.GOLD_INGOT, 1, 0), 1.0F);
		 GameRegistry.addSmelting(new ItemStack(BRBlocks.BARNARDA_C_ORES, 1, 7), new ItemStack(GCItems.basicItem, 1, 3), 1.0F);
		 GameRegistry.addSmelting(new ItemStack(BRBlocks.BARNARDA_C_ORES, 1, 8), new ItemStack(GCItems.basicItem, 1, 4), 1.0F);
		 GameRegistry.addSmelting(new ItemStack(BRBlocks.BARNARDA_C_ORES, 1, 9), new ItemStack(GCItems.basicItem, 1, 5), 1.0F);
		 GameRegistry.addSmelting(new ItemStack(BRBlocks.BARNARDA_C_ORES, 1, 11), new ItemStack(GSItems.INGOTS, 1, 0), 1.0F);
		 GameRegistry.addSmelting(new ItemStack(BRBlocks.BARNARDA_C_ORES, 1, 12), new ItemStack(GSItems.INGOTS, 1, 2), 1.0F);
		
		 GameRegistry.addSmelting(new ItemStack(BRBlocks.BARNARDA_C_FALLING_BLOCKS, 1, 0), new ItemStack(Blocks.GLASS, 1, 0), 1.0F);
		 GameRegistry.addSmelting(new ItemStack(BRBlocks.BARNARDA_C_BLOCKS, 1, 4), new ItemStack(BRBlocks.BARNARDA_C_BLOCKS, 1, 1), 0.1F);
		 
		 GameRegistry.addSmelting(new ItemStack(BRBlocks.BARNARDA_C_VIOLET_LOG, 1, 0), new ItemStack(Items.COAL, 1, 1), 0.1F);
		  
	}
}
