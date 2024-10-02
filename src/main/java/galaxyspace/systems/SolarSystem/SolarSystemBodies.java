package galaxyspace.systems.SolarSystem;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import galaxyspace.GalaxySpace;
import galaxyspace.api.BodiesHelper;
import galaxyspace.api.BodiesHelper.BodiesData;
import galaxyspace.api.IBodiesHandler;
import galaxyspace.api.dimension.world.OreGenerator;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.configs.GSConfigDimensions;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.SolarSystem.moons.callisto.dimension.WorldProviderCallisto;
import galaxyspace.systems.SolarSystem.moons.enceladus.dimension.WorldProviderEnceladus;
import galaxyspace.systems.SolarSystem.moons.enceladus.recipe.CraftingRecipesEnceladus;
import galaxyspace.systems.SolarSystem.moons.europa.dimension.WorldProviderEuropa;
import galaxyspace.systems.SolarSystem.moons.europa.recipe.CraftingRecipesEuropa;
import galaxyspace.systems.SolarSystem.moons.ganymede.dimension.WorldProviderGanymede;
import galaxyspace.systems.SolarSystem.moons.ganymede.recipe.CraftingRecipesGanymede;
import galaxyspace.systems.SolarSystem.moons.io.dimension.WorldProviderIo;
import galaxyspace.systems.SolarSystem.moons.io.recipe.CraftingRecipesIo;
import galaxyspace.systems.SolarSystem.moons.miranda.dimension.WorldProviderMiranda;
import galaxyspace.systems.SolarSystem.moons.miranda.recipe.CraftingRecipesMiranda;
import galaxyspace.systems.SolarSystem.moons.titan.dimension.WorldProviderTitan;
import galaxyspace.systems.SolarSystem.moons.titan.dimension.WorldProviderTitan_WE;
import galaxyspace.systems.SolarSystem.moons.triton.dimension.WorldProviderTriton;
import galaxyspace.systems.SolarSystem.moons.triton.recipe.CraftingRecipesTriton;
import galaxyspace.systems.SolarSystem.planets.ceres.dimension.TeleportTypeCeres;
import galaxyspace.systems.SolarSystem.planets.ceres.dimension.WorldProviderCeres;
import galaxyspace.systems.SolarSystem.planets.ceres.recipe.CraftingRecipesCeres;
import galaxyspace.systems.SolarSystem.planets.kuiperbelt.dimension.WorldProviderKuiper;
import galaxyspace.systems.SolarSystem.planets.mars.recipes.CraftingRecipesMars;
import galaxyspace.systems.SolarSystem.planets.mercury.dimension.TeleportTypeMercury;
import galaxyspace.systems.SolarSystem.planets.mercury.dimension.WorldProviderMercury;
import galaxyspace.systems.SolarSystem.planets.mercury.recipe.CraftingRecipesMercury;
import galaxyspace.systems.SolarSystem.planets.overworld.recipe.CraftingRecipesOverworld;
import galaxyspace.systems.SolarSystem.planets.overworld.recipe.schematic.SchematicBodyRecipe;
import galaxyspace.systems.SolarSystem.planets.overworld.recipe.schematic.SchematicBoosterRecipe;
import galaxyspace.systems.SolarSystem.planets.overworld.recipe.schematic.SchematicConeRecipe;
import galaxyspace.systems.SolarSystem.planets.overworld.recipe.schematic.SchematicEngineRecipe;
import galaxyspace.systems.SolarSystem.planets.overworld.recipe.schematic.SchematicFinsRecipe;
import galaxyspace.systems.SolarSystem.planets.overworld.recipe.schematic.SchematicOxTankRecipe;
import galaxyspace.systems.SolarSystem.planets.overworld.recipe.schematic.SchematicPortableNuclearRecipe;
import galaxyspace.systems.SolarSystem.planets.overworld.recipe.schematic.SchematicTier2Recipe;
import galaxyspace.systems.SolarSystem.planets.overworld.schematic.SchematicBody;
import galaxyspace.systems.SolarSystem.planets.overworld.schematic.SchematicBooster;
import galaxyspace.systems.SolarSystem.planets.overworld.schematic.SchematicCone;
import galaxyspace.systems.SolarSystem.planets.overworld.schematic.SchematicEngine;
import galaxyspace.systems.SolarSystem.planets.overworld.schematic.SchematicFins;
import galaxyspace.systems.SolarSystem.planets.overworld.schematic.SchematicOxTank;
import galaxyspace.systems.SolarSystem.planets.overworld.schematic.SchematicPortableNuclear;
import galaxyspace.systems.SolarSystem.planets.pluto.dimension.WorldProviderPluto;
import galaxyspace.systems.SolarSystem.planets.pluto.recipe.CraftingRecipesPluto;
import galaxyspace.systems.SolarSystem.planets.venus.dimension.TeleportTypeVenus;
import galaxyspace.systems.SolarSystem.planets.venus.dimension.WorldProviderVenus;
import galaxyspace.systems.SolarSystem.planets.venus.recipe.CraftingRecipesVenus;
import galaxyspace.systems.SolarSystem.satellites.TeleportTypeSS;
import galaxyspace.systems.SolarSystem.satellites.mars.dimension.WorldProviderMarsSS;
import galaxyspace.systems.SolarSystem.satellites.mars.recipe.CraftingRecipeMarsSS;
import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.Moon;
import micdoodle8.mods.galacticraft.api.galaxies.Planet;
import micdoodle8.mods.galacticraft.api.galaxies.Satellite;
import micdoodle8.mods.galacticraft.api.galaxies.SolarSystem;
import micdoodle8.mods.galacticraft.api.recipe.ISchematicPage;
import micdoodle8.mods.galacticraft.api.recipe.SchematicRegistry;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.IAtmosphericGas;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.planets.asteroids.AsteroidsModule;
import micdoodle8.mods.galacticraft.planets.asteroids.items.AsteroidsItems;
import micdoodle8.mods.galacticraft.planets.asteroids.schematic.SchematicTier3Rocket;
import micdoodle8.mods.galacticraft.planets.mars.ConfigManagerMars;
import micdoodle8.mods.galacticraft.planets.mars.MarsModule;
import micdoodle8.mods.galacticraft.planets.mars.blocks.MarsBlocks;
import micdoodle8.mods.galacticraft.planets.mars.items.MarsItems;
import micdoodle8.mods.galacticraft.planets.mars.schematic.SchematicTier2Rocket;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;


public class SolarSystemBodies implements IBodiesHandler{

	public static Planet testPlanet;
	
	public static Planet planetMercury;
	public static Planet planetVenus;
	public static Planet planetMars;
	public static Planet planetCeres;	
	public static Planet planetJupiter = GalacticraftCore.planetJupiter;
	public static Planet planetSaturn = GalacticraftCore.planetSaturn;
	public static Planet planetUranus = GalacticraftCore.planetUranus;
	public static Planet planetNeptune = GalacticraftCore.planetNeptune;
	public static Planet planetPluto;
	public static Planet planetKuiperBelt;
	public static Planet planetHaumea;
	public static Planet planetMakemake;
	public static Planet planetEris;
	public static Planet planetDeeDee;
		
	public static Moon phobosMars;
	public static Moon deimosMars;
	
	public static Moon ioJupiter;
	public static Moon europaJupiter;
	public static Moon ganymedeJupiter;
	public static Moon callistoJupiter;
	
	public static Moon mimasSaturn;
	public static Moon enceladusSaturn;
	public static Moon tethysSaturn;
	public static Moon dioneSaturn;
	public static Moon rheyaSaturn;
	public static Moon titanSaturn;
	public static Moon iapetusSaturn;
	
	public static Moon mirandaUranus;
	public static Moon arielUranus;
	public static Moon umbrielUranus;
	public static Moon titaniaUranus;
	public static Moon oberonUranus;
	
	public static Moon proteusNeptune;
	public static Moon tritonNeptune;
	
	public static Moon charonPluto;
	
	public static Satellite marsSpaceStation;
	public static Satellite venusSpaceStation;
	
	public void preInit(FMLPreInitializationEvent event)
	{
		
	}
	
	public void init(FMLInitializationEvent event)
	{
		/*
		 * Sun Distance: 0.0F
		 * Mercury Distance: 0.5F
		 * Venus Distance: 0.75F
		 * Overworld Distance: 1.0F
		 		*Moon Distance: 13.0F	 
		 * Mars Distance: 1.25F
		 		*Phobos Distance: 8.0F
		 		*Deimos Distance: 16.0F 
		 * Ceres Distance: 1.7F
		 * Asteroids Distance: 1.75F
		 * Jupiter Distance: 2.0F
		 		*Io Distance: 10.0F
		 		*Europa Distance: 15.0F
		 		*Ganymede Distance: 20.0F
		 		*Callisto Distance: 30.0F
		 * Saturn Distance: 2.25F
		 		*Enceladus Distance: 15.0F
		 		*Titan Distance: 35.0F 
		 * Uranus Distance: 2.5F
		 * Neptune Distance: 2.75F
		 * Pluto Distance: 3.0F
		 * Kuiper Belt Distance: 3.25F
		 * Haumea Distance: 3.5F
		 * Makemake Distance: 3.75F
		 * Eris Distance: 4.0F
		 */
		
		// TODO Planets -------------------------------
		SolarSystem sol = GalacticraftCore.solarSystemSol;
		sol.setMapPosition(new Vector3(0.0F, 0.0F, 0.0F));

		planetMercury = (Planet) BodiesHelper.registerPlanet(sol, "mercury", GalaxySpace.ASSET_PREFIX, WorldProviderMercury.class, GSConfigDimensions.dimensionIDMercury, 4, 1.45F, 0.5F, 0.5F, 0.24096385542168674698795180722892F);
		planetVenus  = (Planet) BodiesHelper.registerPlanet(sol, "venus", GalaxySpace.ASSET_PREFIX, WorldProviderVenus.class, GSConfigDimensions.dimensionIDVenus, 4, 2.0F, 1.0F, 0.75F, 0.61527929901423877327491785323111F).atmosphereComponent(IAtmosphericGas.CO2).atmosphereComponent(IAtmosphericGas.NITROGEN);
		planetCeres = (Planet) BodiesHelper.registerPlanet(sol, "ceres", GalaxySpace.ASSET_PREFIX, WorldProviderCeres.class, GSConfigDimensions.dimensionIDCeres, 3, 2.0F, 0.5F, 1.7F, 15.0F).setRingColorRGB(0.0F, 0.0F, 0.0F);
		
		planetJupiter = (Planet) BodiesHelper.registerPlanet(sol, "jupiter", GalaxySpace.ASSET_PREFIX, null, -1, -1, (float) Math.PI, 1.0F, 2.0F, 11.861993428258488499452354874042F);
		planetSaturn = (Planet) BodiesHelper.registerPlanet(sol, "saturn", GalaxySpace.ASSET_PREFIX, null, -1, -1, (float) Math.PI / 2, 1.0F, 2.25F, 29.463307776560788608981380065717F);
		planetUranus = (Planet) BodiesHelper.registerPlanet(sol, "uranus", GalaxySpace.ASSET_PREFIX, null, -1, -1, (float) Math.PI, 1.0F, 2.5F, 84.063526834611171960569550930997F);
		planetNeptune = (Planet) BodiesHelper.registerPlanet(sol, "neptune", GalaxySpace.ASSET_PREFIX, null, -1, -1, 1.0F, 1.0F, 2.75F, 164.84118291347207009857612267251F);
		
		planetPluto = (Planet) BodiesHelper.registerPlanet(sol, "pluto", GalaxySpace.ASSET_PREFIX, WorldProviderPluto.class, GSConfigDimensions.dimensionIDPluto, 8, 0.1F, 0.5F, 3.0F, 250.0F).atmosphereComponent(IAtmosphericGas.NITROGEN);
		planetKuiperBelt = (Planet) BodiesHelper.registerPlanet(sol,"kuiperbelt", GalaxySpace.ASSET_PREFIX, WorldProviderKuiper.class, GSConfigDimensions.dimensionIDKuiperBelt, 8, 1.5F, 0.5F, 3.25F, 300.0F).setRingColorRGB(1.1F, 0.0F, 0.0F);;	
		// --------------------------------------------
		// Moons --------------------------------------
		phobosMars = (Moon) BodiesHelper.registerMoon(GSConfigCore.enableGCMars ? MarsModule.planetMars : planetMars, "phobos", GalaxySpace.ASSET_PREFIX, null, GSConfigDimensions.dimensionIDPhobos, 2, 1.0F, 0.0017F, 8.0F, 100F);
		deimosMars = (Moon) BodiesHelper.registerMoon(GSConfigCore.enableGCMars ? MarsModule.planetMars : planetMars, "deimos", GalaxySpace.ASSET_PREFIX, null, -1, -1, 1.0F, 0.0017F, 16.0F, 300F);
	
		ioJupiter = (Moon) BodiesHelper.registerMoon(planetJupiter, "io", GalaxySpace.ASSET_PREFIX, WorldProviderIo.class, GSConfigDimensions.dimensionIDIo, 3, 1.0F, 0.0017F, 10.0F, 50F);
		europaJupiter = (Moon) BodiesHelper.registerMoon(planetJupiter, "europa", GalaxySpace.ASSET_PREFIX, WorldProviderEuropa.class, GSConfigDimensions.dimensionIDEuropa, 3, 1.0F, 0.0017F, 15.0F, 100F);
		ganymedeJupiter = (Moon) BodiesHelper.registerMoon(planetJupiter, "ganymede", GalaxySpace.ASSET_PREFIX, WorldProviderGanymede.class, GSConfigDimensions.dimensionIDGanymede, 3, 1.0F, 0.0017F, 20.0F, 150F);
		callistoJupiter = (Moon) BodiesHelper.registerMoon(planetJupiter, "callisto", GalaxySpace.ASSET_PREFIX, WorldProviderCallisto.class, GSConfigDimensions.dimensionIDCallisto, 3, 1.0F, 0.0017F, 30.0F, 200F);
		
		mimasSaturn = (Moon) BodiesHelper.registerMoon(planetSaturn, "mimas", GalaxySpace.ASSET_PREFIX, null, -1, -1, (float) Math.PI / 2, 0.0017F, 10.0F, 20F);
		enceladusSaturn = (Moon) BodiesHelper.registerMoon(planetSaturn, "enceladus", GalaxySpace.ASSET_PREFIX, WorldProviderEnceladus.class, GSConfigDimensions.dimensionIDEnceladus, 5, (float) Math.PI / 3, 0.0017F, 15.0F, 50F);
		tethysSaturn = (Moon) BodiesHelper.registerMoon(planetSaturn, "tethys", GalaxySpace.ASSET_PREFIX, null, -1, -1, (float) Math.PI, 0.0017F, 20.0F, 120F);
		dioneSaturn = (Moon) BodiesHelper.registerMoon(planetSaturn, "dione", GalaxySpace.ASSET_PREFIX, null, -1, -1, (float) Math.PI / 4, 0.0017F, 25.0F, 180F);
		rheyaSaturn = (Moon) BodiesHelper.registerMoon(planetSaturn, "rheya", GalaxySpace.ASSET_PREFIX, null, -1, -1, (float) Math.PI / 3, 0.0017F, 30.0F, 220F);
		titanSaturn = (Moon) BodiesHelper.registerMoon(planetSaturn, "titan", GalaxySpace.ASSET_PREFIX, GSConfigCore.enableWorldEngine ? WorldProviderTitan_WE.class : WorldProviderTitan.class, GSConfigDimensions.dimensionIDTitan, 5, (float) Math.PI / 5, 0.0017F, 35.0F, 280F).atmosphereComponent(IAtmosphericGas.NITROGEN).atmosphereComponent(IAtmosphericGas.METHANE).atmosphereComponent(IAtmosphericGas.HYDROGEN);
		iapetusSaturn = (Moon) BodiesHelper.registerMoon(planetSaturn, "iapetus", GalaxySpace.ASSET_PREFIX, null, -1, -1, (float) Math.PI, 0.0017F, 40.0F, 350F);
		
		mirandaUranus = (Moon) BodiesHelper.registerMoon(planetUranus, "miranda", GalaxySpace.ASSET_PREFIX, WorldProviderMiranda.class, GSConfigDimensions.dimensionIDMiranda, 6, (float) Math.PI, 0.0017F, 10.0F, 20F);
		arielUranus = (Moon) BodiesHelper.registerMoon(planetUranus, "ariel", GalaxySpace.ASSET_PREFIX, null, -1, -1, (float) Math.PI / 2, 0.0017F, 15.0F, 50F);
		umbrielUranus = (Moon) BodiesHelper.registerMoon(planetUranus, "umbriel", GalaxySpace.ASSET_PREFIX, null, -1, -1, (float) Math.PI / 3, 0.0017F, 20.0F, 120F);
		titaniaUranus = (Moon) BodiesHelper.registerMoon(planetUranus, "titania", GalaxySpace.ASSET_PREFIX, null, -1, -1, (float) Math.PI / 4, 0.0017F, 25.0F, 180F);
		oberonUranus = (Moon) BodiesHelper.registerMoon(planetUranus, "oberon", GalaxySpace.ASSET_PREFIX, null, -1, -1, (float) Math.PI / 5, 0.0017F, 30.0F, 200F);
	
		proteusNeptune = (Moon) BodiesHelper.registerMoon(planetNeptune, "proteus", GalaxySpace.ASSET_PREFIX, null, -1, -1, (float) Math.PI, 0.0017F, 10.0F, 50F);
		tritonNeptune = (Moon) BodiesHelper.registerMoon(planetNeptune, "triton", GalaxySpace.ASSET_PREFIX, WorldProviderTriton.class, GSConfigDimensions.dimensionIDTriton, 6, (float) Math.PI / 2, 0.0017F, 25.0F, -200F).atmosphereComponent(IAtmosphericGas.NITROGEN);
		
		charonPluto = (Moon) BodiesHelper.registerMoon(planetPluto, "charon", GalaxySpace.ASSET_PREFIX, null, -1, -1, (float) Math.PI / 2, 0.0017F, 15.0F, 50F);
		// --------------------------------------------
		
		venusSpaceStation = (Satellite) new Satellite("spaceStation.venus").setParentBody(SolarSystemBodies.planetVenus);
		venusSpaceStation.setRingColorRGB(0.0F, 0.4F, 0.9F);
		venusSpaceStation.setRelativeSize(0.2667F);
		venusSpaceStation.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(10F, 10F));
		venusSpaceStation.setRelativeOrbitTime(1 / 0.05F);
		//venusSpaceStation.setDimensionInfo(GSConfigDimensions.dimensionIDVenusOrbit, GSConfigDimensions.dimensionIDVenusOrbitStatic, WorldProviderVenusSS.class);
		venusSpaceStation.setTierRequired(4);
		venusSpaceStation.setBodyIcon(new ResourceLocation(GalacticraftCore.ASSET_PREFIX, "textures/gui/celestialbodies/spaceStation.png"));
		
		// --------------------------------------------
		// TODO Overworld -----------------------------
		if(GSConfigCore.enableOverworldGeneration) 
		{
			GameRegistry.registerWorldGenerator(new OreGenerator(GSBlocks.Ores, 0, 12, 0, 60, 5, Blocks.stone, 0, 0), 4);
			GameRegistry.registerWorldGenerator(new OreGenerator(GSBlocks.Ores, 1, 6, 0, 45, 4, Blocks.stone, 0, 0), 4);
			//GameRegistry.registerWorldGenerator(new OreGenerator(GSBlocks.Ores, 2, 8, 0, 25, 4, Blocks.stone, 0, 0), 4);
			GameRegistry.registerWorldGenerator(new OreGenerator(GSBlocks.Ores, 3, 6, 0, 40, 4, Blocks.stone, 0, 0), 4);
		}
		GalacticraftCore.satelliteSpaceStation.setRingColorRGB(0.0F, 0.4F, 0.9F);		
		// --------------------------------------------
		// TODO Moon ----------------------------------
		GalacticraftCore.moonMoon.setRingColorRGB(0.0F, 0.4F, 0.9F);

		// --------------------------------------------
		// TODO Mars ----------------------------------
		
		MarsModule.planetMars.setRingColorRGB(0.0F, 0.4F, 0.9F);

		marsSpaceStation = (Satellite) new Satellite("spaceStation.mars").setParentBody(GSConfigCore.enableGCMars ? MarsModule.planetMars : planetMars);
		marsSpaceStation.setRingColorRGB(0.0F, 0.4F, 0.9F);
		marsSpaceStation.setRelativeSize(0.2667F);
		marsSpaceStation.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(10F, 10F));
		marsSpaceStation.setRelativeOrbitTime(1 / 0.05F);
		marsSpaceStation.setDimensionInfo(GSConfigDimensions.dimensionIDMarsOrbit, GSConfigDimensions.dimensionIDMarsOrbitStatic, WorldProviderMarsSS.class);
		marsSpaceStation.setTierRequired(2);
		marsSpaceStation.setBodyIcon(new ResourceLocation(GalacticraftCore.ASSET_PREFIX, "textures/gui/celestialbodies/spaceStation.png"));
		// --------------------------------------------
		AsteroidsModule.planetAsteroids.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(1.75F, 1.75F));
    	registrycelestial();
    	registryteleport();
    	registryDungeonLoot();
    	registrySchematic();
    	registerRecipesWorkBench();
    	
    	GSUtils.registerModules();
	}
	
	private static void registrySchematic()
	{
		if(!GSConfigCore.registeredRocket.isEmpty()) {
			SchematicRegistry.registerSchematicRecipe(new SchematicCone());
			SchematicRegistry.registerSchematicRecipe(new SchematicBody());
			SchematicRegistry.registerSchematicRecipe(new SchematicEngine());
			SchematicRegistry.registerSchematicRecipe(new SchematicBooster());
			SchematicRegistry.registerSchematicRecipe(new SchematicFins());
		}
		SchematicRegistry.registerSchematicRecipe(new SchematicOxTank());
		SchematicRegistry.registerSchematicRecipe(new SchematicPortableNuclear());
		
		for(ISchematicPage page : SchematicRegistry.schematicRecipes)
		{
			if(page instanceof SchematicTier2Rocket) {
				SchematicRegistry.schematicRecipes.remove(page);	
				break;
			}			
		}
		
		for(ISchematicPage page : SchematicRegistry.schematicRecipes)
		{
			if(page instanceof SchematicTier3Rocket) {
				SchematicRegistry.schematicRecipes.remove(page);	
				break;
			}			
		}
		
		SchematicRegistry.registerSchematicRecipe(new galaxyspace.systems.SolarSystem.planets.overworld.schematic.SchematicTier2Rocket());
	}
	
	private static void registerRecipesWorkBench()
    {
		if(!GSConfigCore.registeredRocket.isEmpty()) {
	    	SchematicConeRecipe.registerRecipeWorkBench();
	    	SchematicBodyRecipe.registerRecipeWorkBench();
	    	SchematicEngineRecipe.registerRecipeWorkBench();
	    	SchematicBoosterRecipe.registerRecipeWorkBench();
	    	SchematicFinsRecipe.registerRecipeWorkBench();
		}
    	SchematicOxTankRecipe.registerRecipeWorkBench();
    	SchematicPortableNuclearRecipe.registerRecipeWorkBench();
    	SchematicTier2Recipe.registerRecipeWorkBench();
    }
	
	private static void registryDungeonLoot()
	{
		//GalacticraftRegistry.getDungeonLoot(1).clear();
	    //GalacticraftRegistry.addDungeonLoot(1, new ItemStack(GCItems.schematic, 1, 0));
		if(!GSConfigCore.registeredRocket.isEmpty()) 
			GalacticraftRegistry.addDungeonLoot(1, new ItemStack(GSItems.BasicItems, 1, 12));
		
	    GalacticraftRegistry.addDungeonLoot(1, new ItemStack(GSItems.Schematics, 1, 5));
	    
	    GalacticraftRegistry.getDungeonLoot(2).clear();
	    GalacticraftRegistry.addDungeonLoot(2,new ItemStack(MarsItems.schematic, 1, 1));	    
	    GalacticraftRegistry.addDungeonLoot(2,new ItemStack(MarsItems.schematic, 1, 2));
	    GalacticraftRegistry.addDungeonLoot(2,new ItemStack(GSItems.AncientPickaxe, 1, 0));	
	    	
	    
	    GalacticraftRegistry.addDungeonLoot(4,new ItemStack(GSItems.Schematics, 1, 6));	  
	    GalacticraftRegistry.addDungeonLoot(4,new ItemStack(GSItems.AncientSword, 1, 0));
	    
	    GalacticraftRegistry.addDungeonLoot(5,new ItemStack(GSItems.AncientSword, 1, 0));
	    GalacticraftRegistry.addDungeonLoot(5,new ItemStack(GSItems.AncientPickaxe, 1, 0));	
	    
	    if(GSConfigCore.enableGenAncientAmulet)
	    	GalacticraftRegistry.addDungeonLoot(5,new ItemStack(GSItems.BasicItems, 1, 17));	
	}
	
	private static void registrycelestial()
	{
		BodiesData data = new BodiesData(BodiesHelper.yellow + " " + BodiesHelper.dwarf, 28.088F, 0, 999, 0, 0, false, false);
		BodiesHelper.registerBodyWithClass(GalacticraftCore.solarSystemSol.getMainStar(), data);
		
		data = new BodiesData(null, BodiesHelper.calculateGravity(3.8F), 0, 6, 0.0F, 176000, false, true);
		data.addItemStack(new ItemStack(GSItems.SpacesuitHelmet, 1, OreDictionary.WILDCARD_VALUE));
		data.addItemStack(new ItemStack(GSItems.SpacesuitPlate, 1, OreDictionary.WILDCARD_VALUE));
		data.addItemStack(new ItemStack(GSItems.SpacesuitLeg, 1, OreDictionary.WILDCARD_VALUE));
		data.addItemStack(new ItemStack(GSItems.SpacesuitBoots, 1, OreDictionary.WILDCARD_VALUE));
		BodiesHelper.registerBody(planetMercury, data, GSConfigDimensions.enableMercury);
		
		data = new BodiesData(null, BodiesHelper.calculateGravity(8.88F), 92, 12, 1.2F, 182000, false, false);
		data.addItemStack(new ItemStack(GSItems.SpacesuitHelmet, 1, OreDictionary.WILDCARD_VALUE));
		data.addItemStack(new ItemStack(GSItems.SpacesuitPlate, 1, OreDictionary.WILDCARD_VALUE));
		data.addItemStack(new ItemStack(GSItems.SpacesuitLeg, 1, OreDictionary.WILDCARD_VALUE));
		data.addItemStack(new ItemStack(GSItems.SpacesuitBoots, 1, OreDictionary.WILDCARD_VALUE));
		BodiesHelper.registerBody(planetVenus, data, GSConfigDimensions.enableVenus);
	
		data = new BodiesData(null, BodiesHelper.calculateGravity(10.0F), 1, 1, 1, 24000, true, false);
		BodiesHelper.registerBody(GalacticraftCore.planetOverworld, data, false);
		
		data = new BodiesData(null, 0.062F, 0, 0, 0, 192000, false, true);
		data.addItemStack(new ItemStack(GSItems.SpacesuitHelmet, 1, OreDictionary.WILDCARD_VALUE));
		data.addItemStack(new ItemStack(GSItems.SpacesuitPlate, 1, OreDictionary.WILDCARD_VALUE));
		data.addItemStack(new ItemStack(GSItems.SpacesuitLeg, 1, OreDictionary.WILDCARD_VALUE));
		data.addItemStack(new ItemStack(GSItems.SpacesuitBoots, 1, OreDictionary.WILDCARD_VALUE));
		BodiesHelper.registerBody(GalacticraftCore.moonMoon, data, false);
		
		data = new BodiesData(null, BodiesHelper.calculateGravity(2.37F), 0, -1.5F, 0, 10000, false, true);
		data.addItemStack(new ItemStack(GSItems.SpacesuitHelmet, 1, OreDictionary.WILDCARD_VALUE));
		data.addItemStack(new ItemStack(GSItems.SpacesuitPlate, 1, OreDictionary.WILDCARD_VALUE));
		data.addItemStack(new ItemStack(GSItems.SpacesuitLeg, 1, OreDictionary.WILDCARD_VALUE));
		data.addItemStack(new ItemStack(GSItems.SpacesuitBoots, 1, OreDictionary.WILDCARD_VALUE));
		BodiesHelper.registerBody(planetCeres, data, GSConfigDimensions.enableCeres);
		
		data = new BodiesData(null, 0F, 0, -2, 0, 1, false, false);
		BodiesHelper.registerBody(AsteroidsModule.planetAsteroids, data, false);
		
		data = new BodiesData(null, BodiesHelper.calculateGravity(8.375F), 100, -5, 10, 9000, false, false);		
		BodiesHelper.registerBody(planetJupiter, data, true);
		
		data = new BodiesData(null, BodiesHelper.calculateGravity(7.37F), 100, -5, 10, 11000, false, false);		
		BodiesHelper.registerBody(planetSaturn, data, true);
    	
		data = new BodiesData(null, BodiesHelper.calculateGravity(8.61F), 100, -5, 10, 16000, false, false);		
		BodiesHelper.registerBody(planetUranus, data, true);
		
		data = new BodiesData(null, BodiesHelper.calculateGravity(8.547F), 100, -8, 10, 18000, false, false);		
	   	BodiesHelper.registerBody(planetNeptune, data, true);
	  
	   	data = new BodiesData(null, BodiesHelper.calculateGravity(2.62F), 0, -5, 0, 98000, false, true);
	   	data.addItemStack(new ItemStack(GSItems.SpacesuitHelmet, 1, OreDictionary.WILDCARD_VALUE));
		data.addItemStack(new ItemStack(GSItems.SpacesuitPlate, 1, OreDictionary.WILDCARD_VALUE));
		data.addItemStack(new ItemStack(GSItems.SpacesuitLeg, 1, OreDictionary.WILDCARD_VALUE));
		data.addItemStack(new ItemStack(GSItems.SpacesuitBoots, 1, OreDictionary.WILDCARD_VALUE));
	   	BodiesHelper.registerBody(planetPluto, data, GSConfigDimensions.enablePluto);
	   	
	   	data = new BodiesData(null, 0F, 0, -6, 0, 0, false, true);	
	   	data.addItemStack(new ItemStack(GSItems.SpacesuitHelmet, 1, OreDictionary.WILDCARD_VALUE));
		data.addItemStack(new ItemStack(GSItems.SpacesuitPlate, 1, OreDictionary.WILDCARD_VALUE));
		data.addItemStack(new ItemStack(GSItems.SpacesuitLeg, 1, OreDictionary.WILDCARD_VALUE));
		data.addItemStack(new ItemStack(GSItems.SpacesuitBoots, 1, OreDictionary.WILDCARD_VALUE));
		data.addItemStack(new ItemStack(AsteroidsItems.grapple, 1, 0));
		data.addItemStack(new ItemStack(Items.string, 16, 0));
	   	BodiesHelper.registerBody(planetKuiperBelt, data, GSConfigDimensions.enableKuiperBelt);
	   	
		data = new BodiesData(null, 0.068F, 0, -1, 0, 12000, false, false);   	
		BodiesHelper.registerBody(phobosMars, data, GSConfigDimensions.enablePhobos);
		
		data = new BodiesData(null, 0.064F, 0, -1, 0, 24000, false, false); 
		BodiesHelper.registerBody(deimosMars, data, GSConfigDimensions.enableDeimos);
		
		data = new BodiesData(null, 0.052F, 0, 2, 0, 42000, false, true); 
		BodiesHelper.registerBody(ioJupiter, data, GSConfigDimensions.enableIo);
		
		data = new BodiesData(null, 0.062F, 0, -3, 0, 58000, false, true);		
		BodiesHelper.registerBody(europaJupiter, data, GSConfigDimensions.enableEuropa);
		
		data = new BodiesData(null, 0.057F, 0, -2, 0, 102000, false, false);		
		BodiesHelper.registerBody(ganymedeJupiter, data, GSConfigDimensions.enableGanymede);
		
		data = new BodiesData(null, 0.054F, 0, -3, 0, 154000, false, true);		
		BodiesHelper.registerBody(callistoJupiter, data, GSConfigDimensions.enableCallisto);
		
		data = new BodiesData(null, 0.058F, 0, -3, 0, 32000, false, true);		
		BodiesHelper.registerBody(enceladusSaturn, data, GSConfigDimensions.enableEnceladus);
		
		data = new BodiesData(null, 0.058F, 5, -3, 0, 105500, false, false);			
		BodiesHelper.registerBody(titanSaturn, data, GSConfigDimensions.enableTitan);
		
		data = new BodiesData(null, 0.057F, 0, -4, 0, 33500, false, true);
		BodiesHelper.registerBody(mirandaUranus, data, GSConfigDimensions.enableMiranda);
		
		data = new BodiesData(null, 0.052F, 0, -4, 0, 28500, false, false);
		BodiesHelper.registerBody(tritonNeptune, data, GSConfigDimensions.enableTriton);
		
		BodiesData unreachableData = new BodiesData(null, 0F, 0, 0, 0, 0, false, false);	
		BodiesHelper.registerBody(oberonUranus, unreachableData, GSConfigCore.enableUnreachable);
    	BodiesHelper.registerBody(proteusNeptune, unreachableData, GSConfigCore.enableUnreachable);
		BodiesHelper.registerBody(mimasSaturn, unreachableData, GSConfigCore.enableUnreachable); 	
		BodiesHelper.registerBody(tethysSaturn, unreachableData, GSConfigCore.enableUnreachable); 	
		BodiesHelper.registerBody(dioneSaturn, unreachableData, GSConfigCore.enableUnreachable); 	
		BodiesHelper.registerBody(rheyaSaturn, unreachableData, GSConfigCore.enableUnreachable); 	
		BodiesHelper.registerBody(iapetusSaturn, unreachableData, GSConfigCore.enableUnreachable); 	
		BodiesHelper.registerBody(arielUranus, unreachableData, GSConfigCore.enableUnreachable); 	
		BodiesHelper.registerBody(umbrielUranus, unreachableData, GSConfigCore.enableUnreachable); 	
		BodiesHelper.registerBody(titaniaUranus, unreachableData, GSConfigCore.enableUnreachable); 	
		BodiesHelper.registerBody(charonPluto, unreachableData, GSConfigCore.enableUnreachable); 	
		
		if(GSConfigDimensions.enableMarsSS) GalaxyRegistry.registerSatellite(marsSpaceStation);
		//if(GSConfigDimensions.enableVenusSS) GalaxyRegistry.registerSatellite(venusSpaceStation);

	}
	
	private static void registryteleport()
	{
		GalacticraftRegistry.registerTeleportType(WorldProviderMercury.class, new TeleportTypeMercury());
		GalacticraftRegistry.registerTeleportType(WorldProviderVenus.class, new TeleportTypeVenus());
		GalacticraftRegistry.registerTeleportType(WorldProviderCeres.class, new TeleportTypeCeres());
		GalacticraftRegistry.registerTeleportType(WorldProviderPluto.class, new WorldProviderPluto());
		GalacticraftRegistry.registerTeleportType(WorldProviderKuiper.class, new WorldProviderKuiper());
		/*GalacticraftRegistry.registerTeleportType(WorldProviderHaumea.class, new TeleportTypeHaumea());
		GalacticraftRegistry.registerTeleportType(WorldProviderMakemake.class, new TeleportTypeMakemake());*/
		//GalacticraftRegistry.registerTeleportType(WorldProviderEris.class, new TeleportTypeEris());
		
		//GalacticraftRegistry.registerTeleportType(WorldProviderPhobos.class, new WorldProviderPhobos());
		//GalacticraftRegistry.registerTeleportType(WorldProviderDeimos.class, new WorldProviderDeimos());
		
		GalacticraftRegistry.registerTeleportType(WorldProviderIo.class, new WorldProviderIo());		
		GalacticraftRegistry.registerTeleportType(WorldProviderEuropa.class, new WorldProviderEuropa());	
		GalacticraftRegistry.registerTeleportType(WorldProviderGanymede.class, new WorldProviderGanymede());		
		GalacticraftRegistry.registerTeleportType(WorldProviderCallisto.class, new WorldProviderCallisto());
		
		GalacticraftRegistry.registerTeleportType(WorldProviderEnceladus.class, new WorldProviderEnceladus());
		GalacticraftRegistry.registerTeleportType(GSConfigCore.enableWorldEngine ? WorldProviderTitan_WE.class : WorldProviderTitan.class, new WorldProviderTitan_WE());
		
		GalacticraftRegistry.registerTeleportType(WorldProviderMiranda.class, new WorldProviderMiranda());
		/*GalacticraftRegistry.registerTeleportType(WorldProviderOberon.class, new WorldProviderOberon());
		
		GalacticraftRegistry.registerTeleportType(WorldProviderProteus.class, new WorldProviderProteus());*/
		GalacticraftRegistry.registerTeleportType(WorldProviderTriton.class, new WorldProviderTriton());
		  
		
		
		if(GSConfigDimensions.enableMarsSS)
		{ 
			GalacticraftRegistry.registerTeleportType(WorldProviderMarsSS.class, new TeleportTypeSS());
			GalacticraftRegistry.registerProvider(GSConfigDimensions.dimensionIDMarsOrbit, WorldProviderMarsSS.class, false, -1040);
			GalacticraftRegistry.registerProvider(GSConfigDimensions.dimensionIDMarsOrbitStatic, WorldProviderMarsSS.class, true, -1041);
		}/*
		if(GSConfigDimensions.enableVenusSS)
		{
			GalacticraftRegistry.registerTeleportType(WorldProviderVenusSS.class, new TeleportTypeSS());
			GalacticraftRegistry.registerProvider(GSConfigDimensions.dimensionIDVenusOrbit, WorldProviderVenusSS.class, false, -42);
			GalacticraftRegistry.registerProvider(GSConfigDimensions.dimensionIDVenusOrbitStatic, WorldProviderVenusSS.class, true, -43);
		}
		*/
	}
	
	public void postInit(FMLPostInitializationEvent event)
	{
		if(GSConfigDimensions.enableMarsSS) CraftingRecipeMarsSS.loadRecipes();
		
		CraftingRecipesMercury.loadRecipes();
		CraftingRecipesVenus.loadRecipes();
    	CraftingRecipesOverworld.loadRecipes();
    	CraftingRecipesMars.loadRecipes();
    	CraftingRecipesCeres.loadRecipes();
    	CraftingRecipesPluto.loadRecipes();
    	
    	CraftingRecipesIo.loadRecipes();
    	CraftingRecipesEuropa.loadRecipes();
    	CraftingRecipesGanymede.loadRecipes();
    	CraftingRecipesEnceladus.loadRecipes();
    	CraftingRecipesMiranda.loadRecipes();
    	CraftingRecipesTriton.loadRecipes();
    	
	}

}
