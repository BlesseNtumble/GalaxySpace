package galaxyspace.systems.SolarSystem;

import java.util.HashMap;

import asmodeuscore.api.dimension.IAdvancedSpace.ClassBody;
import asmodeuscore.api.dimension.IAdvancedSpace.StarColor;
import asmodeuscore.api.dimension.IAdvancedSpace.TypeBody;
import asmodeuscore.core.astronomy.BodiesData;
import asmodeuscore.core.astronomy.BodiesHelper;
import asmodeuscore.core.astronomy.dimension.world.OreGenerator;
import asmodeuscore.core.astronomy.dimension.world.gen.ACBiome;
import galaxyspace.GalaxySpace;
import galaxyspace.api.IBodies;
import galaxyspace.api.IBodiesHandler;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.configs.GSConfigDimensions;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.util.GSDimensions;
import galaxyspace.systems.SolarSystem.moons.callisto.dimension.TeleportTypeCallisto;
import galaxyspace.systems.SolarSystem.moons.callisto.dimension.WorldProviderCallisto;
import galaxyspace.systems.SolarSystem.moons.enceladus.dimension.TeleportTypeEnceladus;
import galaxyspace.systems.SolarSystem.moons.enceladus.dimension.WorldProviderEnceladus;
import galaxyspace.systems.SolarSystem.moons.enceladus.dimension.WorldProviderEnceladus_WE;
import galaxyspace.systems.SolarSystem.moons.europa.dimension.TeleportTypeEuropa;
import galaxyspace.systems.SolarSystem.moons.europa.dimension.WorldProviderEuropa;
import galaxyspace.systems.SolarSystem.moons.europa.recipes.CraftingRecipesEuropa;
import galaxyspace.systems.SolarSystem.moons.ganymede.dimension.TeleportTypeGanymede;
import galaxyspace.systems.SolarSystem.moons.ganymede.dimension.WorldProviderGanymede;
import galaxyspace.systems.SolarSystem.moons.ganymede.recipes.CraftingRecipesGanymede;
import galaxyspace.systems.SolarSystem.moons.io.dimension.TeleportTypeIo;
import galaxyspace.systems.SolarSystem.moons.io.dimension.WorldProviderIo;
import galaxyspace.systems.SolarSystem.moons.io.recipes.CraftingRecipesIo;
import galaxyspace.systems.SolarSystem.moons.miranda.dimension.TeleportTypeMiranda;
import galaxyspace.systems.SolarSystem.moons.miranda.dimension.WorldProviderMiranda;
import galaxyspace.systems.SolarSystem.moons.miranda.recipes.CraftingRecipesMiranda;
import galaxyspace.systems.SolarSystem.moons.titan.dimension.TeleportTypeTitan;
import galaxyspace.systems.SolarSystem.moons.titan.dimension.WorldProviderTitan;
import galaxyspace.systems.SolarSystem.planets.ceres.dimension.TeleportTypeCeres;
import galaxyspace.systems.SolarSystem.planets.ceres.dimension.WorldProviderCeres;
import galaxyspace.systems.SolarSystem.planets.kuiperbelt.dimension.TeleportTypeKuiperBelt;
import galaxyspace.systems.SolarSystem.planets.kuiperbelt.dimension.WorldProviderKuiperBelt;
import galaxyspace.systems.SolarSystem.planets.mars.dimension.WorldProviderMars_WE;
import galaxyspace.systems.SolarSystem.planets.mars.recipes.CraftingRecipesMars;
import galaxyspace.systems.SolarSystem.planets.mercury.dimension.TeleportTypeMercury;
import galaxyspace.systems.SolarSystem.planets.mercury.dimension.WorldProviderMercury;
import galaxyspace.systems.SolarSystem.planets.mercury.recipes.CraftingRecipesMercury;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockOres;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemSchematics;
import galaxyspace.systems.SolarSystem.planets.overworld.recipes.CraftingRecipesOverworld;
import galaxyspace.systems.SolarSystem.planets.overworld.recipes.schematic.SchematicBodyRecipe;
import galaxyspace.systems.SolarSystem.planets.overworld.recipes.schematic.SchematicBoosterRecipe;
import galaxyspace.systems.SolarSystem.planets.overworld.recipes.schematic.SchematicConeRecipe;
import galaxyspace.systems.SolarSystem.planets.overworld.recipes.schematic.SchematicEngineRecipe;
import galaxyspace.systems.SolarSystem.planets.overworld.recipes.schematic.SchematicFinsRecipe;
import galaxyspace.systems.SolarSystem.planets.overworld.recipes.schematic.SchematicOxTankRecipe;
import galaxyspace.systems.SolarSystem.planets.overworld.recipes.schematic.SchematicTier2Recipe;
import galaxyspace.systems.SolarSystem.planets.overworld.schematics.SchematicBody;
import galaxyspace.systems.SolarSystem.planets.overworld.schematics.SchematicBooster;
import galaxyspace.systems.SolarSystem.planets.overworld.schematics.SchematicCone;
import galaxyspace.systems.SolarSystem.planets.overworld.schematics.SchematicEngine;
import galaxyspace.systems.SolarSystem.planets.overworld.schematics.SchematicFins;
import galaxyspace.systems.SolarSystem.planets.overworld.schematics.SchematicOxTank;
import galaxyspace.systems.SolarSystem.planets.pluto.dimension.TeleportTypePluto;
import galaxyspace.systems.SolarSystem.planets.pluto.dimension.WorldProviderPluto;
import galaxyspace.systems.SolarSystem.satellites.venus.dimension.TeleportTypeVenusSS;
import galaxyspace.systems.SolarSystem.satellites.venus.dimension.WorldProviderVenusSS;
import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.Moon;
import micdoodle8.mods.galacticraft.api.galaxies.Planet;
import micdoodle8.mods.galacticraft.api.galaxies.Satellite;
import micdoodle8.mods.galacticraft.api.galaxies.SolarSystem;
import micdoodle8.mods.galacticraft.api.item.EnumExtendedInventorySlot;
import micdoodle8.mods.galacticraft.api.recipe.ISchematicPage;
import micdoodle8.mods.galacticraft.api.recipe.SchematicRegistry;
import micdoodle8.mods.galacticraft.api.recipe.SpaceStationRecipe;
import micdoodle8.mods.galacticraft.api.world.AtmosphereInfo;
import micdoodle8.mods.galacticraft.api.world.EnumAtmosphericGas;
import micdoodle8.mods.galacticraft.api.world.SpaceStationType;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.items.ItemBasic;
import micdoodle8.mods.galacticraft.core.util.WorldUtil;
import micdoodle8.mods.galacticraft.core.world.gen.BiomeOrbit;
import micdoodle8.mods.galacticraft.planets.asteroids.AsteroidsModule;
import micdoodle8.mods.galacticraft.planets.asteroids.items.AsteroidsItems;
import micdoodle8.mods.galacticraft.planets.asteroids.schematic.SchematicTier3Rocket;
import micdoodle8.mods.galacticraft.planets.mars.ConfigManagerMars;
import micdoodle8.mods.galacticraft.planets.mars.MarsModule;
import micdoodle8.mods.galacticraft.planets.mars.dimension.TeleportTypeMars;
import micdoodle8.mods.galacticraft.planets.mars.items.MarsItems;
import micdoodle8.mods.galacticraft.planets.mars.schematic.SchematicTier2Rocket;
import micdoodle8.mods.galacticraft.planets.venus.ConfigManagerVenus;
import micdoodle8.mods.galacticraft.planets.venus.VenusModule;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@IBodiesHandler
public class SolarSystemBodies implements IBodies{
	public static Planet planetMercury;

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
	
	/*
	 * Sun Distance: 0.0F
	 * Mercury Distance: 0.5F
	 * Venus Distance: 0.74F
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

	public void preInit(FMLPreInitializationEvent event)
	{
		SolarSystem sol = GalacticraftCore.solarSystemSol;
		
		//Planets
		//body, name, prefix, provider, dimID, tier, phase, size, distancefromcenter, relativetime, biomes
		planetMercury = (Planet) BodiesHelper.registerPlanet(sol, "mercury", GalaxySpace.ASSET_PREFIX, WorldProviderMercury.class, GSConfigDimensions.dimensionIDMercury, 3, 1.45F, 0.5F, 0.5F, 0.24096385542168674698795180722892F, ACBiome.ACSpace);
		planetMercury.setAtmosphere(new AtmosphereInfo(false, false, false, 16.0F, 0.0F, 0.0F));
		
		planetCeres = (Planet) BodiesHelper.registerPlanet(sol, "ceres", GalaxySpace.ASSET_PREFIX, WorldProviderCeres.class, GSConfigDimensions.dimensionIDCeres, 3, 2.0F, 0.5F, 1.7F, 15.0F, ACBiome.ACSpace).setRingColorRGB(0.0F, 0.0F, 0.0F);
		planetCeres.setAtmosphere(new AtmosphereInfo(false, false, false, -1.5F, 0.0F, 0.0F));
		
		planetJupiter = BodiesHelper.registerPlanet(sol,"jupiter", GalaxySpace.ASSET_PREFIX, null, -1, -1, (float) Math.PI, 1.0F, 2.0F, 11.861993428258488499452354874042F);
		planetSaturn = BodiesHelper.registerPlanet(sol,"saturn", GalaxySpace.ASSET_PREFIX, null, -1, -1, (float) Math.PI / 2, 1.0F, 2.25F, 29.463307776560788608981380065717F);
		planetUranus = BodiesHelper.registerPlanet(sol,"uranus", GalaxySpace.ASSET_PREFIX, null, -1, -1, (float) Math.PI, 1.0F, 2.5F, 84.063526834611171960569550930997F);
		planetNeptune = BodiesHelper.registerPlanet(sol,"neptune", GalaxySpace.ASSET_PREFIX, null, -1, -1, 1.0F, 1.0F, 2.75F, 164.84118291347207009857612267251F);
		planetPluto = (Planet) BodiesHelper.registerPlanet(sol,"pluto", GalaxySpace.ASSET_PREFIX, WorldProviderPluto.class, GSConfigDimensions.dimensionIDPluto, 6, 0.1F, 0.5F, 3.0F, 250.0F, ACBiome.ACSpace, ACBiome.ACSpaceLowPlains, ACBiome.ACSpaceLowHills, ACBiome.ACSpaceMidPlains).atmosphereComponent(EnumAtmosphericGas.NITROGEN);
		planetPluto.setAtmosphere(new AtmosphereInfo(false, false, false, -5.0F, 0.0F, 0.0F));
		planetKuiperBelt = (Planet) BodiesHelper.registerPlanet(sol,"kuiperbelt", GalaxySpace.ASSET_PREFIX, WorldProviderKuiperBelt.class, GSConfigDimensions.dimensionIDKuiperBelt, 6, 1.5F, 0.5F, 3.25F, 300.0F, ACBiome.ACSpace).setRingColorRGB(1.0F, 0.0F, 0.0F);;	
		planetKuiperBelt.setAtmosphere(new AtmosphereInfo(false, false, false, -5.0F, 0.0F, 0.0F));
		//
		
		phobosMars = BodiesHelper.registerMoon(GSConfigCore.enableGCMars ? MarsModule.planetMars : planetMars, "phobos", GalaxySpace.ASSET_PREFIX, null, -1, -1, 1.0F, 0.0017F, 8.0F, 100F);
		deimosMars = BodiesHelper.registerMoon(GSConfigCore.enableGCMars ? MarsModule.planetMars : planetMars, "deimos", GalaxySpace.ASSET_PREFIX, null, -1, -1, 1.0F, 0.0017F, 16.0F, 300F);
		
		ioJupiter = BodiesHelper.registerMoon(planetJupiter, "io", GalaxySpace.ASSET_PREFIX, WorldProviderIo.class, GSConfigDimensions.dimensionIDIo, 4, 1.0F, 0.0017F, 10.0F, 50F, ACBiome.ACSpace, ACBiome.ACSpaceLowHills, ACBiome.ACSpaceLowPlains);
		ioJupiter.setAtmosphere(new AtmosphereInfo(false, false, false, -2.0F, 0.0F, 0.0F));
		europaJupiter = BodiesHelper.registerMoon(planetJupiter, "europa", GalaxySpace.ASSET_PREFIX, WorldProviderEuropa.class, GSConfigDimensions.dimensionIDEuropa, 4, 1.0F, 0.0017F, 15.0F, 100F);
		europaJupiter.setAtmosphere(new AtmosphereInfo(false, false, false, -2.0F, 0.0F, 0.0F));
		ganymedeJupiter = BodiesHelper.registerMoon(planetJupiter, "ganymede", GalaxySpace.ASSET_PREFIX, WorldProviderGanymede.class, GSConfigDimensions.dimensionIDGanymede, 4, 1.0F, 0.0017F, 20.0F, 150F);
		ganymedeJupiter.setAtmosphere(new AtmosphereInfo(false, false, false, -2.0F, 0.0F, 0.0F));
		callistoJupiter = BodiesHelper.registerMoon(planetJupiter, "callisto", GalaxySpace.ASSET_PREFIX, WorldProviderCallisto.class, GSConfigDimensions.dimensionIDCallisto, 4, 1.0F, 0.0017F, 30.0F, 200F);
		callistoJupiter.setAtmosphere(new AtmosphereInfo(false, false, false, -2.0F, 0.0F, 0.0F));
		
		mimasSaturn = BodiesHelper.registerMoon(planetSaturn, "mimas", GalaxySpace.ASSET_PREFIX, null, -1, -1, (float) Math.PI / 2, 0.0017F, 10.0F, 20F);
		enceladusSaturn = BodiesHelper.registerMoon(planetSaturn, "enceladus", GalaxySpace.ASSET_PREFIX, GSConfigCore.enableWorldEngine ? WorldProviderEnceladus_WE.class : WorldProviderEnceladus.class, GSConfigDimensions.dimensionIDEnceladus, 5, (float) Math.PI / 3, 0.0017F, 15.0F, 50F);
		enceladusSaturn.setAtmosphere(new AtmosphereInfo(false, false, false, -4.0F, 0.0F, 0.0F));
		tethysSaturn = BodiesHelper.registerMoon(planetSaturn, "tethys", GalaxySpace.ASSET_PREFIX, null, -1, -1, (float) Math.PI, 0.0017F, 20.0F, 120F);
		dioneSaturn = BodiesHelper.registerMoon(planetSaturn, "dione", GalaxySpace.ASSET_PREFIX, null, -1, -1, (float) Math.PI / 4, 0.0017F, 25.0F, 180F);
		rheyaSaturn = BodiesHelper.registerMoon(planetSaturn, "rheya", GalaxySpace.ASSET_PREFIX, null, -1, -1, (float) Math.PI / 3, 0.0017F, 30.0F, 220F);
		titanSaturn = (Moon) BodiesHelper.registerMoon(planetSaturn, "titan", GalaxySpace.ASSET_PREFIX, WorldProviderTitan.class, GSConfigDimensions.dimensionIDTitan, 5, (float) Math.PI / 5, 0.0017F, 35.0F, 280F).atmosphereComponent(EnumAtmosphericGas.NITROGEN).atmosphereComponent(EnumAtmosphericGas.METHANE).atmosphereComponent(EnumAtmosphericGas.HYDROGEN);
		titanSaturn.setAtmosphere(new AtmosphereInfo(false, true, true, -4.0F, 1.0F, 0.0F));
		iapetusSaturn = BodiesHelper.registerMoon(planetSaturn, "iapetus", GalaxySpace.ASSET_PREFIX, null, -1, -1, (float) Math.PI, 0.0017F, 40.0F, 350F);
		
		mirandaUranus = BodiesHelper.registerMoon(planetUranus, "miranda", GalaxySpace.ASSET_PREFIX, WorldProviderMiranda.class, GSConfigDimensions.dimensionIDMiranda, 5, (float) Math.PI, 0.0017F, 10.0F, 20F, ACBiome.ACSpace, ACBiome.ACSpaceLowPlains, ACBiome.ACSpaceMidHills);
		mirandaUranus.setAtmosphere(new AtmosphereInfo(false, true, false, -5.0F, 0.0F, 0.0F));		
		arielUranus = BodiesHelper.registerMoon(planetUranus, "ariel", GalaxySpace.ASSET_PREFIX, null, -1, -1, (float) Math.PI / 2, 0.0017F, 15.0F, 50F);
		umbrielUranus = BodiesHelper.registerMoon(planetUranus, "umbriel", GalaxySpace.ASSET_PREFIX, null, -1, -1, (float) Math.PI / 3, 0.0017F, 20.0F, 120F);
		titaniaUranus = BodiesHelper.registerMoon(planetUranus, "titania", GalaxySpace.ASSET_PREFIX, null, -1, -1, (float) Math.PI / 4, 0.0017F, 25.0F, 180F);
		oberonUranus = BodiesHelper.registerMoon(planetUranus, "oberon", GalaxySpace.ASSET_PREFIX, null, -1, -1, (float) Math.PI / 5, 0.0017F, 30.0F, 200F);
		
		proteusNeptune = BodiesHelper.registerMoon(planetNeptune, "proteus", GalaxySpace.ASSET_PREFIX, null, -1, -1, (float) Math.PI, 0.0017F, 10.0F, 50F);
		tritonNeptune = BodiesHelper.registerMoon(planetNeptune, "triton", GalaxySpace.ASSET_PREFIX, null, -1, -1, (float) Math.PI / 2, 0.0017F, 25.0F, -200F);
		
		charonPluto = BodiesHelper.registerMoon(planetPluto, "charon", GalaxySpace.ASSET_PREFIX, null, -1, -1, (float) Math.PI / 2, 0.0017F, 15.0F, 50F);

		//venusSpaceStation = BodiesHelper.registerSatellite(VenusModule.planetVenus, GalaxySpace.ASSET_PREFIX, WorldProviderVenusSS.class, GSConfigDimensions.idDimensionVenusOrbit, GSConfigDimensions.idDimensionMarsOrbitStatic, (float) Math.PI, 0.2667F, 5.5F, 20.0F);
		/*
		venusSpaceStation = new Satellite("spacestation.venus").setParentBody(VenusModule.planetVenus);
		venusSpaceStation.setRelativeSize(0.2667F);
		venusSpaceStation.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(5.5F, 5.5F));
		venusSpaceStation.setRelativeOrbitTime(20.0F);
		venusSpaceStation.setTierRequired(VenusModule.planetVenus.getTierRequirement());
		venusSpaceStation.setDimensionInfo(GSConfigDimensions.idDimensionVenusOrbit, GSConfigDimensions.idDimensionVenusOrbitStatic, WorldProviderVenusSS.class);
		venusSpaceStation.setBodyIcon(new ResourceLocation("galacticraftcore:textures/gui/celestialbodies/space_station.png"));
		venusSpaceStation.addChecklistKeys("equip_oxygen_suit", "equip_parachute");
		venusSpaceStation.setBiomeInfo(ACBiome.ACSpace);
		*/
		//GalaxyRegistry.getRegisteredPlanets().remove(MarsModule.planetMars.getName());
		//GalaxyRegistry.getRegisteredPlanetIDs().remove(MarsModule.planetMars.getName());
	}
	
	public void init(FMLInitializationEvent event)
	{
		
    	// TODO Rings ---------------------------------
		VenusModule.planetVenus.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(0.745F, 0.74F)).setRingColorRGB(0.0F, 0.4F, 0.9F);
		GalacticraftCore.satelliteSpaceStation.setRingColorRGB(0.0F, 0.4F, 0.9F);	
		GalacticraftCore.moonMoon.setRingColorRGB(0.0F, 0.4F, 0.9F);
		MarsModule.planetMars.setRingColorRGB(0.0F, 0.4F, 0.9F);
		AsteroidsModule.planetAsteroids.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(1.75F, 1.75F));
				
		// --------------------------------------------
		// TODO Overworld -----------------------------
		if(GSConfigCore.enableOverworldOres) {
			if(!Loader.isModLoaded("Tinkers' Construct"))
				GameRegistry.registerWorldGenerator(new OreGenerator(GSBlocks.OVERWORLD_ORES.getDefaultState().withProperty(BlockOres.BASIC_TYPE, BlockOres.EnumBlockOres.COBALT), 12, 0, 60, 5, Blocks.STONE.getDefaultState(), 0), 0);
			
			GameRegistry.registerWorldGenerator(new OreGenerator(GSBlocks.OVERWORLD_ORES.getDefaultState().withProperty(BlockOres.BASIC_TYPE, BlockOres.EnumBlockOres.NICKEL), 6, 0, 45, 4, Blocks.STONE.getDefaultState(), 0), 1);
		}			
		
		MarsModule.planetMars.setDimensionInfo(ConfigManagerMars.dimensionIDMars, WorldProviderMars_WE.class, true);
	
		// --------------------------------------------		
		/*	if(GSConfigCore.enableOresGeneration) 
		{
			// TODO Moon ----------------------------------
			GameRegistry.registerWorldGenerator(new OreGenerator(GSBlocks.OVERWORLD_ORES.getStateFromMeta(2), 4, 0, 45, 4, GCBlocks.blockMoon.getStateFromMeta(4), ConfigManagerCore.idDimensionMoon), 4);
			// --------------------------------------------
		}*/
		registrycelestial();
    	registryteleport();    	
    	registerDungeonLoot();
    	
    	//GalaxyRegistry.getRegisteredPlanets().forEach((s, p) -> GalaxySpace.debug("Planet: " + s  + " | " + p.getWorldProvider()));
        
    	SchematicRegistry.registerSchematicRecipe(new SchematicCone());
		SchematicRegistry.registerSchematicRecipe(new SchematicBody());
		SchematicRegistry.registerSchematicRecipe(new SchematicEngine());
		SchematicRegistry.registerSchematicRecipe(new SchematicBooster());
		SchematicRegistry.registerSchematicRecipe(new SchematicFins());
		SchematicRegistry.registerSchematicRecipe(new SchematicOxTank());
		
		if(GSConfigCore.enableAdvancedRocketCraft) {
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
			SchematicRegistry.registerSchematicRecipe(new galaxyspace.systems.SolarSystem.planets.overworld.schematics.SchematicTier2Rocket());
			
			GCItems.hiddenItems.add(new ItemStack(MarsItems.schematic, 1, 0).getItem());
		}
		
        GalacticraftRegistry.registerGear(40, EnumExtendedInventorySlot.LEFT_TANK, GSItems.OXYGENTANK_TIER_4);
        GalacticraftRegistry.registerGear(40, EnumExtendedInventorySlot.RIGHT_TANK, GSItems.OXYGENTANK_TIER_4);
        GalacticraftRegistry.registerGear(41, EnumExtendedInventorySlot.LEFT_TANK, GSItems.OXYGENTANK_TIER_5);
        GalacticraftRegistry.registerGear(41, EnumExtendedInventorySlot.RIGHT_TANK, GSItems.OXYGENTANK_TIER_5);
        GalacticraftRegistry.registerGear(42, EnumExtendedInventorySlot.LEFT_TANK, GSItems.OXYGENTANK_TIER_6);
        GalacticraftRegistry.registerGear(42, EnumExtendedInventorySlot.RIGHT_TANK, GSItems.OXYGENTANK_TIER_6);
        GalacticraftRegistry.registerGear(43, EnumExtendedInventorySlot.LEFT_TANK, GSItems.OXYGENTANK_TIER_EPP);
        GalacticraftRegistry.registerGear(43, EnumExtendedInventorySlot.RIGHT_TANK, GSItems.OXYGENTANK_TIER_EPP);
        
        GalacticraftRegistry.registerGear(44, EnumExtendedInventorySlot.THERMAL_HELMET, new ItemStack(GSItems.THERMAL_PADDING_3, 1, 0));
        GalacticraftRegistry.registerGear(45, EnumExtendedInventorySlot.THERMAL_CHESTPLATE, new ItemStack(GSItems.THERMAL_PADDING_3, 1, 1));
        GalacticraftRegistry.registerGear(46, EnumExtendedInventorySlot.THERMAL_LEGGINGS, new ItemStack(GSItems.THERMAL_PADDING_3, 1, 2));
        GalacticraftRegistry.registerGear(47, EnumExtendedInventorySlot.THERMAL_BOOTS, new ItemStack(GSItems.THERMAL_PADDING_3, 1, 3));
        
        GalacticraftRegistry.registerGear(101, EnumExtendedInventorySlot.SHIELD_CONTROLLER, new ItemStack(GSItems.BASIC, 1, 16));
	}
	
	private static void registerDungeonLoot()
	{	    
		GalacticraftRegistry.addDungeonLoot(1, new ItemStack(GSItems.BASIC, 1, 10));
		GalacticraftRegistry.addDungeonLoot(1, new ItemStack(GSItems.SCHEMATICS, 1, 5));
		
	    GalacticraftRegistry.getDungeonLoot(2).clear();
	    GalacticraftRegistry.addDungeonLoot(2, new ItemStack(MarsItems.schematic, 1, 1));	    
	    GalacticraftRegistry.addDungeonLoot(2, new ItemStack(MarsItems.schematic, 1, 2)); 
	    GalacticraftRegistry.addDungeonLoot(2, new ItemStack(GSItems.BASIC, 1, 10));
	    	    
	    GalacticraftRegistry.addDungeonLoot(4, new ItemStack(GSItems.BASIC, 1, 16));
	    GalacticraftRegistry.addDungeonLoot(4, new ItemStack(GSItems.BASIC, 1, 18));
	    
	    GalacticraftRegistry.addDungeonLoot(3, new ItemStack(GSItems.ROCKET_MODULES, 1, 6));
	    GalacticraftRegistry.addDungeonLoot(4, new ItemStack(GSItems.ROCKET_MODULES, 1, 6));
	}
	
	private static void registrycelestial()
	{
		ItemStack[] suit = new ItemStack[] 
		{
			new ItemStack(GSItems.SPACE_SUIT_HELMET, 1, 1),
			new ItemStack(GSItems.SPACE_SUIT_BODY, 1, 1),
			new ItemStack(GSItems.SPACE_SUIT_LEGGINS, 1, 1),
			new ItemStack(GSItems.SPACE_SUIT_BOOTS, 1, 1)
		};
		
		BodiesData data = new BodiesData(TypeBody.STAR, ClassBody.DWARF).setStarColor(StarColor.YELLOW);
		BodiesHelper.registerBodyWithClass(GalacticraftCore.solarSystemSol.getMainStar(), data);
		
		data = new BodiesData(null, BodiesHelper.calculateGravity(3.8F), 0, 176000, true);
		BodiesHelper.registerBody(planetMercury, data, GSConfigDimensions.enableMercury);
		
		data = new BodiesData(null, BodiesHelper.calculateGravity(8.88F), 92, 182000, false);
		BodiesHelper.registerBody(VenusModule.planetVenus, data, GSConfigDimensions.enableVenus);
		
		data = new BodiesData(null, BodiesHelper.calculateGravity(10.0F), 1, 24000, false);
		BodiesHelper.registerBody(GalacticraftCore.planetOverworld, data, false);		
		
		data = new BodiesData(null, BodiesHelper.calculateGravity(5.37F), 0, 24660, false);
		BodiesHelper.registerBody(MarsModule.planetMars, data, false);
		
		data = new BodiesData(null, BodiesHelper.calculateGravity(2.37F), 0, 10000, true);
		BodiesHelper.registerBody(planetCeres, data, GSConfigDimensions.enableCeres);
    	
		data = new BodiesData(null, 0, 0, 0,  true);
		BodiesHelper.registerBody(AsteroidsModule.planetAsteroids, data, false);
    	
		data = new BodiesData(ClassBody.GASGIANT, BodiesHelper.calculateGravity(8.375F), 100, 9000, false);		
		BodiesHelper.registerBody(planetJupiter, data, true);
		
		data = new BodiesData(ClassBody.GASGIANT, BodiesHelper.calculateGravity(7.37F), 100, 11000, false);		
		BodiesHelper.registerBody(planetSaturn, data, true);
    	
		data = new BodiesData(ClassBody.ICEGIANT, BodiesHelper.calculateGravity(8.61F), 100, 16000, false);		
		BodiesHelper.registerBody(planetUranus, data, true);
		
		data = new BodiesData(ClassBody.ICEGIANT, BodiesHelper.calculateGravity(8.547F), 100, 18000, false);		
	   	BodiesHelper.registerBody(planetNeptune, data, true);
	
	   	data = new BodiesData(null, BodiesHelper.calculateGravity(2.62F), 0, 98000, true);
	   	BodiesHelper.registerBody(planetPluto, data, GSConfigDimensions.enablePluto);
	   	
	   	data = new BodiesData(null, 0F, 0, 0, true);
	   	BodiesHelper.registerBody(planetKuiperBelt, data, GSConfigDimensions.enableKuiperBelt);
					
		//BodiesInfo.registerBody(testPlanet, 0.04F, 0, -2, 0, 48000, false, true, GalaxySpace.debug);
		
	   	data = new BodiesData(null, 0.062F, 0, 192000, true);
		BodiesHelper.registerBody(GalacticraftCore.moonMoon, data, false);

	   	data = new BodiesData(null, 0.068F, 0, 12000, false);   	
		BodiesHelper.registerBody(phobosMars, data, GSConfigDimensions.enablePhobos);
		
		data = new BodiesData(null, 0.064F, 0, 24000, false); 
		BodiesHelper.registerBody(deimosMars, data, GSConfigDimensions.enableDeimos);
		
		data = new BodiesData(null, 0.052F, 0, 42000, true);
		BodiesHelper.registerBody(ioJupiter, data, GSConfigDimensions.enableIo);
		
		data = new BodiesData(null, 0.062F, 0, 58000, true);
		BodiesHelper.registerBody(europaJupiter, data, GSConfigDimensions.enableEuropa);
		
		data = new BodiesData(null, 0.057F, 0, 102000, false);		
		BodiesHelper.registerBody(ganymedeJupiter, data, GSConfigDimensions.enableGanymede);
		
		data = new BodiesData(null, 0.054F, 0, 154000, true);
		BodiesHelper.registerBody(callistoJupiter, data, GSConfigDimensions.enableCallisto);
		
		data = new BodiesData(null, 0.058F, 0, 32000, true);
		BodiesHelper.registerBody(enceladusSaturn, data, GSConfigDimensions.enableEnceladus);
		
		data = new BodiesData(null, 0.058F, 5, 105500, false);			
		BodiesHelper.registerBody(titanSaturn, data, GSConfigDimensions.enableTitan);
		
		data = new BodiesData(null, 0.057F, 0, 33500, true);
		BodiesHelper.registerBody(mirandaUranus, data, GSConfigDimensions.enableMiranda);
		
		BodiesData unreachableData = new BodiesData(null, 0F, 0, 0, false);	
		BodiesHelper.registerBody(oberonUranus, unreachableData, GSConfigCore.enableUnreachable);
		BodiesHelper.registerBody(proteusNeptune, unreachableData, GSConfigCore.enableUnreachable);
		BodiesHelper.registerBody(tritonNeptune, unreachableData, GSConfigCore.enableUnreachable); 
		BodiesHelper.registerBody(mimasSaturn, unreachableData, GSConfigCore.enableUnreachable); 	
		BodiesHelper.registerBody(tethysSaturn, unreachableData, GSConfigCore.enableUnreachable); 	
		BodiesHelper.registerBody(dioneSaturn, unreachableData, GSConfigCore.enableUnreachable); 	
		BodiesHelper.registerBody(rheyaSaturn, unreachableData, GSConfigCore.enableUnreachable); 	
		BodiesHelper.registerBody(iapetusSaturn, unreachableData, GSConfigCore.enableUnreachable); 	
		BodiesHelper.registerBody(arielUranus, unreachableData, GSConfigCore.enableUnreachable); 	
		BodiesHelper.registerBody(umbrielUranus, unreachableData, GSConfigCore.enableUnreachable); 	
		BodiesHelper.registerBody(titaniaUranus, unreachableData, GSConfigCore.enableUnreachable); 	
		BodiesHelper.registerBody(charonPluto, unreachableData, GSConfigCore.enableUnreachable); 
		

		//if(GSConfigDimensions.enableMarsSS) GalaxyRegistry.registerSatellite(marsSpaceStation);
		//if(GSConfigDimensions.enableVenusSpaceStation) GalaxyRegistry.registerSatellite(venusSpaceStation);

	}
	
	private static void registryteleport()
	{
	
		GalacticraftRegistry.registerTeleportType(WorldProviderMercury.class, new TeleportTypeMercury());	
		
		GalacticraftRegistry.registerTeleportType(WorldProviderMars_WE.class, new TeleportTypeMars());
		
		
		GalacticraftRegistry.registerTeleportType(WorldProviderCeres.class, new TeleportTypeCeres());
		GalacticraftRegistry.registerTeleportType(WorldProviderPluto.class, new TeleportTypePluto());
		GalacticraftRegistry.registerTeleportType(WorldProviderKuiperBelt.class, new TeleportTypeKuiperBelt());
		/*GalacticraftRegistry.registerTeleportType(WorldProviderHaumea.class, new TeleportTypeHaumea());
		GalacticraftRegistry.registerTeleportType(WorldProviderMakemake.class, new TeleportTypeMakemake());*/
		//GalacticraftRegistry.registerTeleportType(WorldProviderEris.class, new TeleportTypeEris());
		
		//if(GalaxySpace.debug) GalacticraftRegistry.registerTeleportType(WorldProviderZTest.class, new WorldProviderZTest());
		
		//GalacticraftRegistry.registerTeleportType(WorldProviderPhobos.class, new WorldProviderPhobos());
		//GalacticraftRegistry.registerTeleportType(WorldProviderDeimos.class, new WorldProviderDeimos());
		
		GalacticraftRegistry.registerTeleportType(WorldProviderIo.class, new TeleportTypeIo());		
		GalacticraftRegistry.registerTeleportType(WorldProviderEuropa.class, new TeleportTypeEuropa());	
		GalacticraftRegistry.registerTeleportType(WorldProviderGanymede.class, new TeleportTypeGanymede());		
		GalacticraftRegistry.registerTeleportType(WorldProviderCallisto.class, new TeleportTypeCallisto());
		
		GalacticraftRegistry.registerTeleportType(GSConfigCore.enableWorldEngine ? WorldProviderEnceladus_WE.class : WorldProviderEnceladus.class, new TeleportTypeEnceladus());
		GalacticraftRegistry.registerTeleportType(WorldProviderTitan.class, new TeleportTypeTitan());
		
		GalacticraftRegistry.registerTeleportType(WorldProviderMiranda.class, new TeleportTypeMiranda());
		
		/*GalacticraftRegistry.registerTeleportType(WorldProviderOberon.class, new WorldProviderOberon());
		
		GalacticraftRegistry.registerTeleportType(WorldProviderProteus.class, new WorldProviderProteus());
		GalacticraftRegistry.registerTeleportType(WorldProviderTriton.class, new WorldProviderTriton());*/
		  
		
		/*
		if(GSConfigDimensions.enableMarsSS)
		{ */
			//GalacticraftRegistry.registerTeleportType(WorldProviderMarsSS.class, new TeleportTypeMarsSS());
		/*	GalacticraftRegistry.registerProvider(GSConfigDimensions.dimensionIDMarsOrbit, WorldProviderMarsSS.class, false, -40);
			GalacticraftRegistry.registerProvider(GSConfigDimensions.dimensionIDMarsOrbitStatic, WorldProviderMarsSS.class, true, -41);
		}*/
		if(GSConfigDimensions.enableVenusSpaceStation)
		{
			//GalaxyRegistry.registerSatellite(venusSpaceStation);
			//GalacticraftRegistry.registerTeleportType(WorldProviderVenusSS.class, new TeleportTypeVenusSS());
		}
		
	}
	
	public void postInit(FMLPostInitializationEvent event)
	{
		ItemSchematics.registerSchematicItems();
		
		GSDimensions.MERCURY = WorldUtil.getDimensionTypeById(GSConfigDimensions.dimensionIDMercury);
		GSDimensions.CERES = WorldUtil.getDimensionTypeById(GSConfigDimensions.dimensionIDCeres);
		GSDimensions.PLUTO = WorldUtil.getDimensionTypeById(GSConfigDimensions.dimensionIDPluto);
		
		GSDimensions.IO = WorldUtil.getDimensionTypeById(GSConfigDimensions.dimensionIDIo);
		GSDimensions.EUROPA = WorldUtil.getDimensionTypeById(GSConfigDimensions.dimensionIDEuropa);
		GSDimensions.GANYMEDE = WorldUtil.getDimensionTypeById(GSConfigDimensions.dimensionIDGanymede);
		GSDimensions.CALLISTO = WorldUtil.getDimensionTypeById(GSConfigDimensions.dimensionIDCallisto);

		GSDimensions.ENCELADUS = WorldUtil.getDimensionTypeById(GSConfigDimensions.dimensionIDEnceladus);
		GSDimensions.TITAN = WorldUtil.getDimensionTypeById(GSConfigDimensions.dimensionIDTitan);
		GSDimensions.MIRANDA = WorldUtil.getDimensionTypeById(GSConfigDimensions.dimensionIDMiranda);
		
		GSDimensions.KUIPER_BELT = WorldUtil.getDimensionTypeById(GSConfigDimensions.dimensionIDKuiperBelt);

		/*GSDimensions.VENUS_SS = GalacticraftRegistry.registerDimension("Venus Space Station", "_venus_orbit", GSConfigDimensions.idDimensionVenusOrbit, WorldProviderVenusSS.class, false);
		if (GSDimensions.VENUS_SS == null)
        {
            GalaxySpace.debug("Failed to register space station dimension type with ID " + GSConfigDimensions.idDimensionVenusOrbit);
        }
		GSDimensions.VENUS_SS_KEEPLOADED = GalacticraftRegistry.registerDimension("Venus Space Station", "_venus_orbit", GSConfigDimensions.idDimensionVenusOrbitStatic, WorldProviderVenusSS.class, true);
	*/
		GalaxyRegistry.refreshGalaxies();
	}
	
	private static void registerRecipesWorkBench()
    {
    	SchematicConeRecipe.registerRecipeWorkBench();
    	SchematicBodyRecipe.registerRecipeWorkBench();
    	SchematicEngineRecipe.registerRecipeWorkBench();
    	SchematicBoosterRecipe.registerRecipeWorkBench();
    	SchematicFinsRecipe.registerRecipeWorkBench();
    	SchematicOxTankRecipe.registerRecipeWorkBench();
    	SchematicTier2Recipe.registerRecipeWorkBench();
    	
    	GalaxySpace.debug("Reg schem recipe");
    }
	
	@Override
	public void registerRender() {		
	}
	
	@Override
	public void registerVariant() {		
	}

	@Override
	public void registerRecipes() {
		CraftingRecipesMercury.loadRecipes();
		CraftingRecipesOverworld.loadRecipes();		
    	CraftingRecipesMars.loadRecipes();
    	CraftingRecipesIo.loadRecipes();
    	CraftingRecipesEuropa.loadRecipes();
    	CraftingRecipesGanymede.loadRecipes();
    	CraftingRecipesMiranda.loadRecipes();
    	
    	registerRecipesWorkBench();
    	
		final HashMap<Object, Integer> spaceStationRequirements = new HashMap<Object, Integer>(6, 1.0F);
		spaceStationRequirements.put("ingotTin", 32);
		spaceStationRequirements.put("ingotCopper", 64);
		spaceStationRequirements.put(new ItemStack(GCItems.basicItem, 1, ItemBasic.WAFER_ADVANCED), 1);
		spaceStationRequirements.put(Items.IRON_INGOT, 24);
		spaceStationRequirements.put(new ItemStack(GSItems.HDP, 1, 0), 10);
		spaceStationRequirements.put(new ItemStack(GSItems.BASIC, 1, 6), 10);
		/*
		if(GSConfigDimensions.enableVenusSpaceStation)
			GalacticraftRegistry.registerSpaceStation(new SpaceStationType(GSConfigDimensions.idDimensionVenusOrbit,
				ConfigManagerVenus.dimensionIDVenus, new SpaceStationRecipe(spaceStationRequirements)));
    	 */
	}

	@Override
	public boolean canRegister() {
		return true;
	}
	

}
