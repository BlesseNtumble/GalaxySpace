package galaxyspace.systems.SolarSystem.planets.ceres.recipe;

import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.registers.fluids.GSFluids;
import galaxyspace.systems.SolarSystem.planets.overworld.recipe.RecyclerRecipes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class CraftingRecipesCeres {

   public static void loadRecipes() {
      addBlockRecipes();
      addItemRecipes();
      addBlockSmelting();  
      addCompressor();
      addAssembly();
      addRecycler();
   }

   private static void addBlockRecipes() {
     // GameRegistry.addRecipe(new ItemStack(GSBlocks.CeresGlowStone, 1), new Object[]{"##", "##", '#', new ItemStack(GSItems.GlowstoneDusts, 1, 0)});
   }

   private static void addItemRecipes()
   {


   }

   private static void addBlockSmelting() {
	   
  
   }
   
   private static void addCompressor() {

   }
   
   private static void addAssembly() {
	    	   
   }
   
   private static void addRecycler()
   {
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(GSBlocks.CeresBlocks, 1, 0), new ItemStack(GSBlocks.CeresBlocks, 1, 1), new FluidStack(GSFluids.Helium3, 100));
   }
   
}
