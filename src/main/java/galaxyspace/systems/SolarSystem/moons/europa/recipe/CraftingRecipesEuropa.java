package galaxyspace.systems.SolarSystem.moons.europa.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.systems.SolarSystem.planets.overworld.recipe.RecyclerRecipes;
import micdoodle8.mods.galacticraft.core.items.GCItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;


public class CraftingRecipesEuropa {

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
	   GameRegistry.addSmelting(new ItemStack(GSBlocks.EuropaBlocks, 1, 5), new ItemStack(GCItems.basicItem, 1, 5), 1.0F);
		 
   }
   
   private static void addCompressor() {  
	   //CompressorRecipes.addShapelessRecipe(new ItemStack(GSItems.CompressedPlates, 1, 4), "ingotMagnesium", "ingotMagnesium");
		  
   }
   
   private static void addAssembly() {
	   
	 	    
   }
   private static void addRecycler() {
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(GSBlocks.EuropaBlocks, 1, 2), new ItemStack(GSItems.BasicItems, 1, 8), new FluidStack(FluidRegistry.WATER, 10));
			  
   }
}
