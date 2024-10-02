package galaxyspace.systems.SolarSystem.moons.io.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.registers.items.GSItems;
import micdoodle8.mods.galacticraft.core.items.GCItems;
import net.minecraft.item.ItemStack;


public class CraftingRecipesIo {

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
	   
	   GameRegistry.addSmelting(new ItemStack(GSBlocks.IoBlocks, 1, 3), new ItemStack(GCItems.basicItem, 1, 3), 1.0F);
	   GameRegistry.addSmelting(new ItemStack(GSBlocks.IoBlocks, 1, 4), new ItemStack(GSItems.BasicItems, 1, 9), 1.0F);
   }
   
   private static void addCompressor() {
  
	   //CompressorRecipes.addShapelessRecipe(new ItemStack(GSItems.CompressedPlates, 1, 4), "ingotMagnesium", "ingotMagnesium");
		  
   }
   
   private static void addAssembly() {
	   
	 	    
   }
   private static void addRecycler() {
	   
	  
   }
}
