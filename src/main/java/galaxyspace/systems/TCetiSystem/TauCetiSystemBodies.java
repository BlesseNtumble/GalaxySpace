package galaxyspace.systems.TCetiSystem;

import java.io.File;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import galaxyspace.GalaxySpace;
import galaxyspace.api.BodiesHelper;
import galaxyspace.api.BodiesHelper.BodiesData;
import galaxyspace.api.BodiesHelper.Galaxies;
import galaxyspace.api.IBodiesHandler;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.systems.TCetiSystem.core.configs.TCConfigCore;
import galaxyspace.systems.TCetiSystem.core.configs.TCConfigDimensions;
import galaxyspace.systems.TCetiSystem.core.registers.blocks.TCBlocks;
import galaxyspace.systems.TCetiSystem.planets.tcetiF.dimension.WorldProviderTCetiF;
import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.Planet;
import micdoodle8.mods.galacticraft.api.galaxies.SolarSystem;
import micdoodle8.mods.galacticraft.api.galaxies.Star;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.IAtmosphericGas;
import net.minecraft.util.ResourceLocation;


public class TauCetiSystemBodies implements IBodiesHandler {
	
	public static SolarSystem tcetiSystem;
	public static Star TCetiA;

	public static Planet tcetiE;
	public static Planet tcetiF;
	public static Planet tcetiG;
	public static Planet tcetiH;

	
	@Override
	public void preInit(FMLPreInitializationEvent event) {

		new TCConfigCore(new File(event.getModConfigurationDirectory(), "GalaxySpace/tau_ceti/core.conf"));
    	new TCConfigDimensions(new File(event.getModConfigurationDirectory(), "GalaxySpace/tau_ceti/dimensions.conf"));
    
    	if(TCConfigCore.enableTauCetiSystem)
    		TCBlocks.initialize();
    }
    
	@Override
	public void init(FMLInitializationEvent event) {
		
		/*
		 * Star Distance: 0.0F
		 * Planet 1 Distance: 0.5F
		 * Planet 2 Distance: 0.75F
		 * Planet 3 Distance: 1.0F
		 * Planet 4 Distance: 1.25F
		 * Planet 5 Distance: 1.5F
		 * Planet 6 Distance: 1.75F
		 * Planet 7 Distance: 2.0F
		 * Planet 8 Distance: 2.25F
		 * Planet 9 Distance: 2.5F
		 * Planet 10 Distance: 2.75F
		 * Planet 11 Distance: 3.0F
		 * Planet 12 Distance: 3.25F
		 * Planet 13 Distance: 3.5F
		 * Planet 14 Distance: 3.75F
		 * Planet 15 Distance: 4.0F
		*/
		
		// TODO T Ceti System ----------------------------------------
		tcetiSystem = BodiesHelper.registerSolarSystem(GalaxySpace.ASSET_PREFIX, "tauceti", Galaxies.MILKYWAY, new Vector3(-2.0F, 1.0F, 0.0F), "TauCetiA", 1.2F);

		tcetiG = BodiesHelper.registerPlanet(tcetiSystem, "TauCetiG", GalaxySpace.ASSET_PREFIX, null, -1, -1, (float) Math.PI / 3, 1.0F, 0.25F, 2.3F);
		tcetiH = BodiesHelper.registerPlanet(tcetiSystem, "TauCetiH", GalaxySpace.ASSET_PREFIX, null, -1, -1, (float) Math.PI / 2, 1.0F, 0.75F, 15F);
		tcetiE = BodiesHelper.registerPlanet(tcetiSystem, "TauCetiE", GalaxySpace.ASSET_PREFIX, null, -1, -1, (float) Math.PI, 1.0F, 1.25F, 30F);
		
		tcetiF = BodiesHelper.registerPlanet(tcetiSystem, "TauCetiF", GalaxySpace.ASSET_PREFIX, WorldProviderTCetiF.class, TCConfigDimensions.dimensionIDTauCetiF, 6, (float) Math.PI / 3, 1.0F, 1.5F, 60F);
        tcetiF.atmosphereComponent(IAtmosphericGas.CO2).atmosphereComponent(IAtmosphericGas.HELIUM).atmosphereComponent(IAtmosphericGas.ARGON).atmosphereComponent(IAtmosphericGas.OXYGEN);

		// ---------------------------------------------
        
    	registrycelestial();
    	registryteleport();
	}
	
	private static void registrycelestial()
	{
		if(TCConfigCore.enableTauCetiSystem) {
			GalaxyRegistry.registerSolarSystem(tcetiSystem);
			if(TCConfigDimensions.enableTauCetiF)	{
				BodiesData data = new BodiesData(BodiesHelper.oceanide, BodiesHelper.calculateGravity(8.88F), 0, 0, 1, 28000L, false, false);
				BodiesHelper.registerBody(tcetiF, data, true);
			}
			
			if(GSConfigCore.enableUnreachable)
			{
				GalaxyRegistry.registerPlanet(tcetiG);		
				GalaxyRegistry.registerPlanet(tcetiH);
				GalaxyRegistry.registerPlanet(tcetiE);
			}
		}
	}
	
	private static void registryteleport()
	{
		if(TCConfigCore.enableTauCetiSystem)
			GalacticraftRegistry.registerTeleportType(WorldProviderTCetiF.class, new WorldProviderTCetiF());
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	
}
