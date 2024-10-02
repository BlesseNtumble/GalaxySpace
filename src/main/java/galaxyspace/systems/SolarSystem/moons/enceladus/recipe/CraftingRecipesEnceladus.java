package galaxyspace.systems.SolarSystem.moons.enceladus.recipe;

import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.systems.SolarSystem.planets.overworld.recipe.RecyclerRecipes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;


public class CraftingRecipesEnceladus {

   public static void loadRecipes() {
      addBlockRecipes();
      addItemRecipes();
      addBlockSmelting();  
      addCompressor();
      addAssembly();
      addRecycler();
   }

   private static void addBlockRecipes() {
      //GameRegistry.addRecipe(new ItemStack(BlocksAddon.EuropaGlowStone, 1), new Object[]{"##", "##", '#', new ItemStack(ItemsAddon.BlueCrystallEuropa, 1)});
     	   
   }

   private static void addItemRecipes()
   {
	 
   }

   private static void addBlockSmelting() {
	   
	   //GameRegistry.addSmelting(new ItemStack(GSBlocks.GanymedeBlocks, 1, 2), new ItemStack(GSItems.Ingots, 1, 2), 1.0F);
   }
   
   private static void addCompressor() {
  
	   //CompressorRecipes.addShapelessRecipe(new ItemStack(GSItems.CompressedPlates, 1, 4), "ingotMagnesium", "ingotMagnesium");
		  
   }
   
   private static void addAssembly() {
	   
	 	    
   }
   private static void addRecycler() {
	   
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(GSBlocks.EnceladusBlocks, 1, 0), null, new FluidStack(FluidRegistry.WATER, 100));
  
   }
}
