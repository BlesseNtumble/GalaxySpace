package galaxyspace.systems.ACentauriSystem;

import java.io.File;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import galaxyspace.GalaxySpace;
import galaxyspace.api.BodiesHelper;
import galaxyspace.api.BodiesHelper.BodiesData;
import galaxyspace.api.BodiesHelper.Galaxies;
import galaxyspace.api.IBodiesHandler;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.systems.ACentauriSystem.core.configs.ACConfigCore;
import galaxyspace.systems.ACentauriSystem.core.configs.ACConfigDimensions;
import galaxyspace.systems.ACentauriSystem.core.registers.blocks.ACBlocks;
import galaxyspace.systems.ACentauriSystem.planets.proximaB.dimension.TeleportTypeProximaB;
import galaxyspace.systems.ACentauriSystem.planets.proximaB.dimension.WorldProviderProximaB;
import galaxyspace.systems.ACentauriSystem.planets.proximaB.recipes.CraftingRecipesProximaB;
import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.Planet;
import micdoodle8.mods.galacticraft.api.galaxies.SolarSystem;
import micdoodle8.mods.galacticraft.api.galaxies.Star;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.IAtmosphericGas;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;


public class ACentauriSystemBodies implements IBodiesHandler{
	
	public static SolarSystem aCentauriSystem;
	public static SolarSystem ProximaSystem;
	public static Star aCentauri;
    public static Planet centauriB;
    
    public static Star proxima;
    public static Planet proximaB;
	
    public void preInit(FMLPreInitializationEvent event) 
    {
    	new ACConfigCore(new File(event.getModConfigurationDirectory(), "GalaxySpace/alpha_centauri/core.conf"));
    	new ACConfigDimensions(new File(event.getModConfigurationDirectory(), "GalaxySpace/alpha_centauri/dimensions.conf"));
    	
    	if(ACConfigCore.enableAlphaCentauriSystem) 
    		ACBlocks.initialize();
    }
    
    public void init(FMLInitializationEvent event)
	{
		
		// TODO aCentauri System ----------------------------------------
    	aCentauriSystem = BodiesHelper.registerSolarSystem(GalaxySpace.ASSET_PREFIX, "aсentauri", Galaxies.MILKYWAY, new Vector3(1.5F, 0.0F, 0.0F), "centauri_a", 1.3F);
    	ProximaSystem = BodiesHelper.registerSolarSystem(GalaxySpace.ASSET_PREFIX, "proxima", Galaxies.MILKYWAY, new Vector3(1.7F, -0.2F, 0.0F), "proxima", 0.8F);
    	
        centauriB = (Planet) BodiesHelper.registerPlanet(aCentauriSystem, "centauri_b", GalaxySpace.ASSET_PREFIX, null, -1, -1, (float) Math.PI, 1.5F, 0.3F, 1000F).setRingColorRGB(0.0F, 0.0F, 0.0F);
		
        proximaB = (Planet) BodiesHelper.registerPlanet(ProximaSystem, "proxima_b", GalaxySpace.ASSET_PREFIX, WorldProviderProximaB.class, ACConfigDimensions.dimensionIDProximaB, 6, (float) Math.PI * 3, 1.2F, 0.25F, 1.1F).setRingColorRGB(0.0F, 0.4F, 0.9F);
        proximaB.atmosphereComponent(IAtmosphericGas.OXYGEN).atmosphereComponent(IAtmosphericGas.CO2).atmosphereComponent(IAtmosphericGas.ARGON).atmosphereComponent(IAtmosphericGas.WATER);
		// ---------------------------------------------
        
        if(ACConfigCore.enableAlphaCentauriSystem) 
        {	
        	registrycelestial();
        	registryteleport();
        }
	}
	
	private static void registrycelestial()
	{
		GalaxyRegistry.registerSolarSystem(aCentauriSystem);
		GalaxyRegistry.registerSolarSystem(ProximaSystem);

		
		BodiesData unreachableData = new BodiesData(null, 0F, 0, 0, 0, 0, false, false);
		BodiesHelper.registerBody(centauriB, unreachableData, true);
		
		BodiesData data = new BodiesData(null, BodiesHelper.calculateGravity(9.4F), 1, 0.1F, 1.0F, 35050, false, true);
		data.addItemStack(new ItemStack(GSItems.SpacesuitHelmet, 1, OreDictionary.WILDCARD_VALUE));
		data.addItemStack(new ItemStack(GSItems.SpacesuitPlate, 1, OreDictionary.WILDCARD_VALUE));
		data.addItemStack(new ItemStack(GSItems.SpacesuitLeg, 1, OreDictionary.WILDCARD_VALUE));
		data.addItemStack(new ItemStack(GSItems.SpacesuitBoots, 1, OreDictionary.WILDCARD_VALUE));
		BodiesHelper.registerBody(proximaB, data, ACConfigDimensions.enableProximaB);
		
		data = new BodiesData(BodiesHelper.yellow + " " + BodiesHelper.dwarf, 20.336F, 0, 999, 0, 0, false, false);
		BodiesHelper.registerBodyWithClass(aCentauriSystem.getMainStar(), data);
		
		data = new BodiesData(BodiesHelper.orange + " " + BodiesHelper.dwarf, 18.124F, 0, 999, 0, 0, false, false);
		BodiesHelper.registerBodyWithClass(ProximaSystem.getMainStar(), data);
		
	}
	
	private static void registryteleport()
	{
		GalacticraftRegistry.registerTeleportType(WorldProviderProximaB.class, new TeleportTypeProximaB());
	}
	
	public void postInit(FMLPostInitializationEvent event)
	{
		CraftingRecipesProximaB.loadRecipes();
	}
}
