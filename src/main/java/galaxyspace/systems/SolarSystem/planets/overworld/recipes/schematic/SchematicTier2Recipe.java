package galaxyspace.systems.SolarSystem.planets.overworld.recipes.schematic;

import java.util.HashMap;

import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.util.GSRecipeUtil;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.recipe.NasaWorkbenchRecipe;
import micdoodle8.mods.galacticraft.planets.mars.items.MarsItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class SchematicTier2Recipe {

	public static void registerRecipeWorkBench()
    {
		// Schematic
        HashMap<Integer, ItemStack> input = new HashMap<Integer, ItemStack>();
        input.put(1, new ItemStack(GCItems.partNoseCone));
        input.put(2, new ItemStack(MarsItems.marsItemBasic, 1, 3));
        input.put(3, new ItemStack(MarsItems.marsItemBasic, 1, 3));
        input.put(4, new ItemStack(MarsItems.marsItemBasic, 1, 3));
        input.put(5, new ItemStack(MarsItems.marsItemBasic, 1, 3));
        input.put(6, new ItemStack(MarsItems.marsItemBasic, 1, 3));
        input.put(7, new ItemStack(MarsItems.marsItemBasic, 1, 3));
        input.put(8, new ItemStack(MarsItems.marsItemBasic, 1, 3));
        input.put(9, new ItemStack(MarsItems.marsItemBasic, 1, 3));
        input.put(10, new ItemStack(MarsItems.marsItemBasic, 1, 3));
        input.put(11, new ItemStack(MarsItems.marsItemBasic, 1, 3));
        input.put(12, new ItemStack(GCItems.rocketEngine, 1, 1));
        input.put(13, new ItemStack(GCItems.partFins));
        input.put(14, new ItemStack(GCItems.partFins));
        input.put(15, new ItemStack(GCItems.rocketEngine));
        input.put(16, new ItemStack(GCItems.rocketEngine, 1, 1));
        input.put(17, new ItemStack(GCItems.partFins));
        input.put(18, new ItemStack(GCItems.partFins));
        input.put(19, ItemStack.EMPTY);
        input.put(20, ItemStack.EMPTY);
        input.put(21, ItemStack.EMPTY);
        input.put(22, new ItemStack(GSItems.ROCKET_MODULES, 1, 1));
        input.put(23, new ItemStack(GSItems.ROCKET_MODULES, 1, 3));
        input.put(24, new ItemStack(GSItems.ROCKET_MODULES, 1, 3));
        input.put(25, new ItemStack(GCItems.rocketTier1, 1, 0));
        GSRecipeUtil.addT2RocketRecipe(new NasaWorkbenchRecipe(new ItemStack(MarsItems.rocketMars, 1, 0), input));

        HashMap<Integer, ItemStack> input2 = new HashMap<Integer, ItemStack>(input);
        input2.put(25, new ItemStack(GCItems.rocketTier1, 1, 1));
        GSRecipeUtil.addT2RocketRecipe(new NasaWorkbenchRecipe(new ItemStack(MarsItems.rocketMars, 1, 0), input2));
        
        input2 = new HashMap<Integer, ItemStack>(input);
        input2.put(25, new ItemStack(GCItems.rocketTier1, 1, 2));
        GSRecipeUtil.addT2RocketRecipe(new NasaWorkbenchRecipe(new ItemStack(MarsItems.rocketMars, 1, 0), input2));
        
        input2 = new HashMap<Integer, ItemStack>(input);
        input2.put(25, new ItemStack(GCItems.rocketTier1, 1, 3));
        GSRecipeUtil.addT2RocketRecipe(new NasaWorkbenchRecipe(new ItemStack(MarsItems.rocketMars, 1, 0), input2));
        
        for(int i = 0; i <= 3; i++) {
	        input2 = new HashMap<Integer, ItemStack>(input);
	        input2.put(19, new ItemStack(Blocks.CHEST));
	        input2.put(20, ItemStack.EMPTY);
	        input2.put(21, ItemStack.EMPTY);
	        input2.put(25, new ItemStack(GCItems.rocketTier1, 1, i));
	        GSRecipeUtil.addT2RocketRecipe(new NasaWorkbenchRecipe(new ItemStack(MarsItems.rocketMars, 1, 1), input2));
	
	        input2 = new HashMap<Integer, ItemStack>(input);
	        input2.put(19, ItemStack.EMPTY);
	        input2.put(20, new ItemStack(Blocks.CHEST));
	        input2.put(21, ItemStack.EMPTY);
	        input2.put(25, new ItemStack(GCItems.rocketTier1, 1, i));
	        GSRecipeUtil.addT2RocketRecipe(new NasaWorkbenchRecipe(new ItemStack(MarsItems.rocketMars, 1, 1), input2));
	
	        input2 = new HashMap<Integer, ItemStack>(input);
	        input2.put(19, ItemStack.EMPTY);
	        input2.put(20, ItemStack.EMPTY);
	        input2.put(21, new ItemStack(Blocks.CHEST));
	        input2.put(25, new ItemStack(GCItems.rocketTier1, 1, i));
	        GSRecipeUtil.addT2RocketRecipe(new NasaWorkbenchRecipe(new ItemStack(MarsItems.rocketMars, 1, 1), input2));
	
	        input2 = new HashMap<Integer, ItemStack>(input);
	        input2.put(19, new ItemStack(Blocks.CHEST));
	        input2.put(20, new ItemStack(Blocks.CHEST));
	        input2.put(21, ItemStack.EMPTY);
	        input2.put(25, new ItemStack(GCItems.rocketTier1, 1, i));
	        GSRecipeUtil.addT2RocketRecipe(new NasaWorkbenchRecipe(new ItemStack(MarsItems.rocketMars, 1, 2), input2));
	
	        input2 = new HashMap<Integer, ItemStack>(input);
	        input2.put(19, new ItemStack(Blocks.CHEST));
	        input2.put(20, ItemStack.EMPTY);
	        input2.put(21, new ItemStack(Blocks.CHEST));
	        input2.put(25, new ItemStack(GCItems.rocketTier1, 1, i));
	        GSRecipeUtil.addT2RocketRecipe(new NasaWorkbenchRecipe(new ItemStack(MarsItems.rocketMars, 1, 2), input2));
	
	        input2 = new HashMap<Integer, ItemStack>(input);
	        input2.put(19, ItemStack.EMPTY);
	        input2.put(20, new ItemStack(Blocks.CHEST));
	        input2.put(21, new ItemStack(Blocks.CHEST));
	        input2.put(25, new ItemStack(GCItems.rocketTier1, 1, i));
	        GSRecipeUtil.addT2RocketRecipe(new NasaWorkbenchRecipe(new ItemStack(MarsItems.rocketMars, 1, 2), input2));
	
	        input2 = new HashMap<Integer, ItemStack>(input);
	        input2.put(19, new ItemStack(Blocks.CHEST));
	        input2.put(20, new ItemStack(Blocks.CHEST));
	        input2.put(21, new ItemStack(Blocks.CHEST));
	        input2.put(25, new ItemStack(GCItems.rocketTier1, 1, i));
	        GSRecipeUtil.addT2RocketRecipe(new NasaWorkbenchRecipe(new ItemStack(MarsItems.rocketMars, 1, 3), input2));
        }
    }
}
