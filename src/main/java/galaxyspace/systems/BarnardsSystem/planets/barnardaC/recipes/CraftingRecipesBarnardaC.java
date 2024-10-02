package galaxyspace.systems.BarnardsSystem.planets.barnardaC.recipes;

import cpw.mods.fml.common.registry.GameRegistry;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.systems.BarnardsSystem.core.registers.blocks.BRBlocks;
import micdoodle8.mods.galacticraft.core.items.GCItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CraftingRecipesBarnardaC {

	public static void loadRecipes() {
		addBlockRecipes();
		addItemRecipes();
		addBlockSmelting();
		addCompressor();
		addAssembly();
		addRecycler();
	}
	
	private static void addBlockRecipes() {
		
	}
	
	private static void addItemRecipes() {
		
	}
	
	private static void addBlockSmelting() {
		GameRegistry.addSmelting(new ItemStack(BRBlocks.BarnardaCLog, 1, 0), new ItemStack(Items.coal, 1, 1), 0.1F);
		GameRegistry.addSmelting(new ItemStack(BRBlocks.BarnardaCFallingBlocks, 1, 0), new ItemStack(Blocks.glass, 1, 0), 0.1F);
		
		GameRegistry.addSmelting(new ItemStack(BRBlocks.BarnardaCOres, 1, 1), new ItemStack(Items.iron_ingot, 1, 0), 1.0F);
		GameRegistry.addSmelting(new ItemStack(BRBlocks.BarnardaCOres, 1, 2), new ItemStack(Items.gold_ingot, 1, 0), 1.0F);
		GameRegistry.addSmelting(new ItemStack(BRBlocks.BarnardaCOres, 1, 7), new ItemStack(GCItems.basicItem, 1, 3), 1.0F);
		GameRegistry.addSmelting(new ItemStack(BRBlocks.BarnardaCOres, 1, 8), new ItemStack(GCItems.basicItem, 1, 4), 1.0F);
		GameRegistry.addSmelting(new ItemStack(BRBlocks.BarnardaCOres, 1, 9), new ItemStack(GCItems.basicItem, 1, 5), 1.0F);
		GameRegistry.addSmelting(new ItemStack(BRBlocks.BarnardaCOres, 1, 11), new ItemStack(GSItems.Ingots, 1, 0), 1.0F);
		GameRegistry.addSmelting(new ItemStack(BRBlocks.BarnardaCOres, 1, 12), new ItemStack(GSItems.Ingots, 1, 2), 1.0F);
	}
	
	private static void addCompressor() {
		
	}
	
	private static void addAssembly() {
		
	}
	
	private static void addRecycler() {		
	}
}
