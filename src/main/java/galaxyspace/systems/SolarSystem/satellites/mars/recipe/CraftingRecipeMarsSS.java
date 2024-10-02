package galaxyspace.systems.SolarSystem.satellites.mars.recipe;

import java.util.HashMap;

import galaxyspace.core.configs.GSConfigDimensions;
import galaxyspace.core.registers.blocks.GSBlocks;
import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.recipe.SpaceStationRecipe;
import micdoodle8.mods.galacticraft.api.world.SpaceStationType;
import micdoodle8.mods.galacticraft.planets.mars.ConfigManagerMars;
import net.minecraft.item.ItemStack;

public class CraftingRecipeMarsSS {
	
	public static void loadRecipes()
    {
		if(GSConfigDimensions.enableMarsSS) CraftingRecipeMarsSS.addUniversalRecipes();
 }

	@SuppressWarnings("unchecked")
    private static void addUniversalRecipes()
    {
		final HashMap<Object, Integer> inputMap = new HashMap<Object, Integer>();
		inputMap.put("ingotCopper", 64);
		inputMap.put("ingotTitanium", 4);
        inputMap.put("waferModern", 2);
        inputMap.put("ingotDesh", 12);        
        inputMap.put(new ItemStack(GSBlocks.MachineFrames, 1, 0), 1);        
        
        GalacticraftRegistry.registerSpaceStation(new SpaceStationType(GSConfigDimensions.dimensionIDMarsOrbit, ConfigManagerMars.dimensionIDMars, new SpaceStationRecipe(inputMap)));
    }
}
