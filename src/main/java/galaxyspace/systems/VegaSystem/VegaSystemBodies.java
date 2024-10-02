package galaxyspace.systems.VegaSystem;

import java.io.File;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import galaxyspace.GalaxySpace;
import galaxyspace.api.IBodiesHandler;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.configs.GSConfigDimensions;
import galaxyspace.systems.TCetiSystem.core.configs.TCConfigCore;
import galaxyspace.systems.TCetiSystem.core.configs.TCConfigDimensions;
import galaxyspace.systems.VegaSystem.core.configs.VGConfigCore;
import galaxyspace.systems.VegaSystem.core.configs.VGConfigDimensions;
import galaxyspace.systems.VegaSystem.core.registers.blocks.VGBlocks;
import galaxyspace.systems.VegaSystem.planets.vegaB.dimension.WorldProviderVegaB;
import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.Planet;
import micdoodle8.mods.galacticraft.api.galaxies.SolarSystem;
import micdoodle8.mods.galacticraft.api.galaxies.Star;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.util.ResourceLocation;


public class VegaSystemBodies implements IBodiesHandler{
	
	public static SolarSystem vegaSystem;
	public static Star Vega;
    public static Planet planetVega1;
    public static Planet planetVega2;
    public static Planet planetVegaasteroids;
    public static Planet planetVegaasteroids2;
    

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		new VGConfigCore(new File(event.getModConfigurationDirectory(), "GalaxySpace/vega/core.conf"));
    	new VGConfigDimensions(new File(event.getModConfigurationDirectory(), "GalaxySpace/vega/dimensions.conf"));
    
		if(VGConfigCore.enableVegaSystem) 
			VGBlocks.initialize();
    }
    
	@Override
	public void init(FMLInitializationEvent event) {
		
		// TODO Vega System ----------------------------------------
		vegaSystem = new SolarSystem("vega", "milkyWay").setMapPosition(new Vector3(-1.5F, -3.5F, 0.0F));
        Vega = (Star) new Star("Vega").setParentSolarSystem(vegaSystem).setTierRequired(-1);
        Vega.setBodyIcon(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/vega/Vega.png"));
        vegaSystem.setMainStar(Vega);
        
        planetVega1 = (Planet) new Planet("Vega1").setParentSolarSystem(vegaSystem);
        planetVega1.setRingColorRGB(0.0F, 0.4F, 0.9F);
        planetVega1.setPhaseShift((float) Math.PI);
        planetVega1.setTierRequired(8);
        planetVega1.setBodyIcon(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/vega/Vega1.png"));
        planetVega1.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(0.5F, 0.5F));
        planetVega1.setRelativeOrbitTime(1.861993428258488499452354874042F);
        planetVega1.setDimensionInfo(VGConfigDimensions.dimensionIDVegaB, WorldProviderVegaB.class);
       
        planetVegaasteroids = (Planet) new Planet("VegaAsteroids").setParentSolarSystem(vegaSystem);
        planetVegaasteroids.setRingColorRGB(1.1F, 0.0F, 0.0F);
        planetVegaasteroids.setPhaseShift((float) Math.PI / 4);
        planetVegaasteroids.setBodyIcon(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/vega/VegaAsteroids.png"));
        planetVegaasteroids.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(1.6F, 1.0F));
        planetVegaasteroids.setRelativeOrbitTime(11.861993428258488499452354874042F);
     
        planetVega2 = (Planet) new Planet("Vega2").setParentSolarSystem(vegaSystem);
        planetVega2.setRingColorRGB(0.0F, 0.4F, 0.9F);
        planetVega2.setPhaseShift((float) Math.PI / 2);
        planetVega2.setBodyIcon(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/vega/Vega2.png"));
        planetVega2.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(1.75F, 1.75F));
        planetVega2.setRelativeOrbitTime(5.861993428258488499452354874042F);
           
        planetVegaasteroids2 = (Planet) new Planet("VegaAsteroids2").setParentSolarSystem(vegaSystem);
        planetVegaasteroids2.setRingColorRGB(1.1F, 0.0F, 0.0F);
        planetVegaasteroids2.setPhaseShift((float) Math.PI / 8);
        planetVegaasteroids2.setBodyIcon(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/vega/VegaAsteroids.png"));
        planetVegaasteroids2.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(2.5F, 2.5F));
        planetVegaasteroids2.setRelativeOrbitTime(11.861993428258488499452354874042F);
		
		// ---------------------------------------------
        
    	registrycelestial();
    	registryteleport();
	}
	
	private static void registrycelestial()
	{
		if(VGConfigCore.enableVegaSystem) {
			GalaxyRegistry.registerSolarSystem(vegaSystem);
			if(VGConfigDimensions.enableVegaB) GalaxyRegistry.registerPlanet(planetVega1);
			if(GSConfigCore.enableUnreachable)
			{
				GalaxyRegistry.registerPlanet(planetVega2);
				GalaxyRegistry.registerPlanet(planetVegaasteroids);
				GalaxyRegistry.registerPlanet(planetVegaasteroids2);
			}
		}
	}
	
	private static void registryteleport()
	{
		if(VGConfigDimensions.enableVegaB)
			GalacticraftRegistry.registerTeleportType(WorldProviderVegaB.class, new WorldProviderVegaB());
	}


	@Override
	public void postInit(FMLPostInitializationEvent event) {
		
	}
	
	
}
