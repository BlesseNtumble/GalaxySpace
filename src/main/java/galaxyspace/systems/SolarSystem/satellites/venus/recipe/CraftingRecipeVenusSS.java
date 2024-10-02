package galaxyspace.systems.SolarSystem.satellites.venus.recipe;

import java.util.HashMap;

import galaxyspace.core.configs.GSConfigDimensions;
import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.recipe.SpaceStationRecipe;
import micdoodle8.mods.galacticraft.api.world.SpaceStationType;

public class CraftingRecipeVenusSS {
	
	public static void loadRecipes()
    {
		if(GSConfigDimensions.enableVenusSS) CraftingRecipeVenusSS.addUniversalRecipes();
    }

	@SuppressWarnings("unchecked")
    private static void addUniversalRecipes()
    {
		final HashMap<Object, Integer> inputMap = new HashMap<Object, Integer>();
		inputMap.put("ingotCopper", 64);
		inputMap.put("ingotTitanium", 16);
        inputMap.put("waferAdvanced", 2);
        inputMap.put("ingotDesh", 12);
        
        
        GalacticraftRegistry.registerSpaceStation(new SpaceStationType(GSConfigDimensions.dimensionIDVenusOrbit, GSConfigDimensions.dimensionIDVenus, new SpaceStationRecipe(inputMap)));
    
    }
}
