package galaxyspace.systems.SolarSystem.planets.mercury.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.registers.fluids.GSFluids;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.systems.SolarSystem.planets.overworld.recipe.RecyclerRecipes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class CraftingRecipesMercury {

   public static void loadRecipes() {
      addBlockRecipes();
      addItemRecipes();
      addBlockSmelting();  
      addCompressor();
      addAssembly();
      addRecycle();
   }

   private static void addBlockRecipes() {}
   private static void addItemRecipes() {}
   private static void addBlockSmelting() {
	   GameRegistry.addSmelting(new ItemStack(GSBlocks.MercuryBlocks, 1, 3), new ItemStack(Items.iron_ingot, 1, 0), 1.0F);
	   GameRegistry.addSmelting(new ItemStack(GSBlocks.MercuryBlocks, 1, 4), new ItemStack(GSItems.Ingots, 1, 2), 1.0F);
	   GameRegistry.addSmelting(new ItemStack(GSBlocks.MercuryBlocks, 1, 5), new ItemStack(GSItems.Ingots, 1, 1), 1.0F);
   }   
   private static void addCompressor() {}   
   private static void addAssembly() {}
   private static void addRecycle() {
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(GSBlocks.MercuryBlocks, 1, 0), new ItemStack(GSBlocks.MercuryBlocks, 1, 1), new FluidStack(GSFluids.Helium3, 100));
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(GSBlocks.MercuryBlocks, 1, 2), new ItemStack(GSItems.BasicItems, 1, 9), 50, null);
	  
   }   
   
}
