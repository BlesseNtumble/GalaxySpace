package galaxyspace.systems.BarnardsSystem;

import java.io.File;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import galaxyspace.GalaxySpace;
import galaxyspace.api.BodiesHelper;
import galaxyspace.api.BodiesHelper.BodiesData;
import galaxyspace.api.BodiesHelper.Galaxies;
import galaxyspace.api.IBodiesHandler;
import galaxyspace.systems.BarnardsSystem.core.configs.BRConfigDimensions;
import galaxyspace.systems.BarnardsSystem.core.registers.blocks.BRBlocks;
import galaxyspace.systems.BarnardsSystem.core.registers.blocks.BRItems;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.dimension.TeleportTypeBarnardaC;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.dimension.WorldProviderBarnardaC_WE;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.recipes.CraftingRecipesBarnardaC;
import galaxyspace.systems.BarnardsSystem.planets.barnardaE.dimension.TeleportTypeBarnardaE;
import galaxyspace.systems.BarnardsSystem.planets.barnardaE.dimension.WorldProviderBarnardaE_WE;
import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.Planet;
import micdoodle8.mods.galacticraft.api.galaxies.SolarSystem;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.IAtmosphericGas;


public class BarnardsSystemBodies implements IBodiesHandler{
	
	public static SolarSystem BarnardsSystem;
    public static Planet barnardaB;
    public static Planet barnardaC;
	public static Planet barnardaE;
    
    @Override
    public void preInit(FMLPreInitializationEvent event) 
    {
    	new BRConfigDimensions(new File(event.getModConfigurationDirectory(), "GalaxySpace/barnards/dimensions.conf"));
    	
    	BRBlocks.initialize();
    	BRItems.initialize();
    	//BRFluids.initialize();
    }
    
    @Override
	public void init(FMLInitializationEvent event)
	{
		
        // TODO Barnards System ----------------------------        
        BarnardsSystem = BodiesHelper.registerSolarSystem(GalaxySpace.ASSET_PREFIX, "barnards", Galaxies.MILKYWAY, new Vector3(1.0F, -2.0F, 0.0F), "barnarda_a", 1.0F);
        barnardaB = (Planet) BodiesHelper.registerPlanet(BarnardsSystem, "barnarda_b", GalaxySpace.ASSET_PREFIX, null, -1, -1, (float) Math.PI, 1.0F, 0.5F, 2.5F);
        barnardaC = (Planet) BodiesHelper.registerPlanet(BarnardsSystem, "barnarda_c", GalaxySpace.ASSET_PREFIX, WorldProviderBarnardaC_WE.class, BRConfigDimensions.dimensionIDBarnardaC, 6, (float) Math.PI / 2, 1.0F, 0.75F, 6.5F).setRingColorRGB(0.0F, 1.1F, 0.0F).atmosphereComponent(IAtmosphericGas.CO2).atmosphereComponent(IAtmosphericGas.OXYGEN).atmosphereComponent(IAtmosphericGas.ARGON);
        barnardaE = (Planet) BodiesHelper.registerPlanet(BarnardsSystem, "barnarda_e", GalaxySpace.ASSET_PREFIX, WorldProviderBarnardaE_WE.class, BRConfigDimensions.dimensionIDBarnardaC-1, 6, (float) Math.PI / 4, 1.0F, 2.3F, 12.5F);
        
        // ---------------------------------------------
        
    	registrycelestial();
    	registryteleport();
	}
	
	private static void registrycelestial()
	{
		GalaxyRegistry.registerSolarSystem(BarnardsSystem);
		BodiesData star = new BodiesData(BodiesHelper.orange + " " + BodiesHelper.dwarf, 28.088F, 0, 999, 0, 0, false, false);
		BodiesHelper.registerBodyWithClass(BarnardsSystem.getMainStar(), star);
		
		//BodiesHelper.registerBody(planetBarnardaC, 0.03F, 0, 0, 1, 32000, true, false, BRConfigDimensions.enableBarnardaC);		
		//BodiesHelper.registerBody(planetBarnardaB, 0.058F, 150, 100, 100, 48000, false, false, GSConfigCore.enableUnreachable);			
		
		BodiesData unreachableData = new BodiesData(null, 0F, 0, 0, 0, 0, false, false);
		BodiesHelper.registerBody(barnardaB, unreachableData, true);
		
		BodiesData data = new BodiesData(null, BodiesHelper.calculateGravity(8.5F), 1, 0, 1.0F, 36000L, true, false);
		BodiesHelper.registerBody(barnardaC, data, true);
		
		data = new BodiesData(null, BodiesHelper.calculateGravity(4.5F), 6, -2, 0.4F, 68000L, false, false);
		BodiesHelper.registerBody(barnardaE, data, true);
	}
	
	private static void registryteleport()
	{
		GalacticraftRegistry.registerTeleportType(WorldProviderBarnardaC_WE.class, new TeleportTypeBarnardaC());
		GalacticraftRegistry.registerTeleportType(WorldProviderBarnardaE_WE.class, new TeleportTypeBarnardaE());
		//GalacticraftRegistry.registerTeleportType(WorldProviderBarnardaF.class, new WorldProviderBarnardaF());
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		CraftingRecipesBarnardaC.loadRecipes();
	}
	
	
}
