package galaxyspace.systems.SolarSystem.moons.ganymede.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.registers.items.GSItems;
import net.minecraft.item.ItemStack;


public class CraftingRecipesGanymede {

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
	   
	   GameRegistry.addSmelting(new ItemStack(GSBlocks.GanymedeBlocks, 1, 2), new ItemStack(GSItems.Ingots, 1, 1), 1.0F);
   }
   
   private static void addCompressor() {
  
	   //CompressorRecipes.addShapelessRecipe(new ItemStack(GSItems.CompressedPlates, 1, 4), "ingotMagnesium", "ingotMagnesium");
		  
   }
   
   private static void addAssembly() {
	   
	 	    
   }
   
   private static void addRecycler() {
	  	   
   }
}
