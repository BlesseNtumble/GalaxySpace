package galaxyspace.systems.SolarSystem.planets.pluto.recipe;

import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.systems.SolarSystem.planets.overworld.recipe.RecyclerRecipes;
import micdoodle8.mods.galacticraft.planets.asteroids.AsteroidsModule;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;


public class CraftingRecipesPluto {

   public static void loadRecipes() {
      addBlockRecipes();
      addItemRecipes();
      addBlockSmelting();  
      addCompressor();
      addAssembly();
      addRecycle();
   }

   private static void addBlockRecipes() {
	   //GameRegistry.addRecipe(new ItemStack(GSBlocks.PlutoGlowStone, 1), new Object[]{"##", "##", '#', new ItemStack(GSItems.GlowstoneDusts, 1, 4)});
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
   
   private static void addRecycle() {
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(GSBlocks.PlutoBlocks, 1, 1), null, new FluidStack(FluidRegistry.WATER, 10));
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(GSBlocks.PlutoBlocks, 1, 2), null, new FluidStack(AsteroidsModule.fluidMethaneGas, 2));
		
   }  
   
   
}
