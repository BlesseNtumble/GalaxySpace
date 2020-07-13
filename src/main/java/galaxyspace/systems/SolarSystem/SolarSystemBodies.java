package galaxyspace.systems.SolarSystem;

import java.util.HashMap;

import asmodeuscore.api.dimension.IAdvancedSpace.ClassBody;
import asmodeuscore.api.dimension.IAdvancedSpace.StarColor;
import asmodeuscore.api.dimension.IAdvancedSpace.TypeBody;
import asmodeuscore.core.astronomy.BodiesData;
import asmodeuscore.core.astronomy.BodiesHelper;
import asmodeuscore.core.astronomy.dimension.world.OreGenerator;
import asmodeuscore.core.astronomy.dimension.world.gen.ACBiome;
import asmodeuscore.core.prefab.celestialbody.ExPlanet;
import galaxyspace.GalaxySpace;
import galaxyspace.api.IBodies;
import galaxyspace.api.IBodiesHandler;
import galaxyspace.core.GSBlocks;
import galaxyspace.core.GSItems;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.configs.GSConfigDimensions;
import galaxyspace.core.util.GSConstants;
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
import galaxyspace.systems.SolarSystem.moons.triton.dimenson.TeleportTypeTriton;
import galaxyspace.systems.SolarSystem.moons.triton.dimenson.WorldProviderTriton_WE;
import galaxyspace.systems.SolarSystem.planets.ceres.dimension.TeleportTypeCeres;
import galaxyspace.systems.SolarSystem.planets.ceres.dimension.WorldProviderCeres;
import galaxyspace.systems.SolarSystem.planets.haumea.dimension.TeleportTypeHaumea;
import galaxyspace.systems.SolarSystem.planets.haumea.dimension.WorldProviderHaumea_WE;
import galaxyspace.systems.SolarSystem.planets.haumea.recipes.CraftingRecipesHaumea;
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
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.items.ItemBasic;
import micdoodle8.mods.galacticraft.core.util.CompatibilityManager;
import micdoodle8.mods.galacticraft.core.util.WorldUtil;
import micdoodle8.mods.galacticraft.planets.asteroids.AsteroidsModule;
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
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@IBodiesHandler
public class SolarSystemBodies implements IBodies{
		
	public static Planet planetMercury;

	public static Planet planetCeres;

	public static Planet planetJupiter = GalacticraftCore.planetJupiter;
	public static Planet planetSaturn = GalacticraftCore.planetSaturn;
	public static Planet planetUranus = GalacticraftCore.planetUranus;
	public static Planet planetNeptune = GalacticraftCore.planetNeptune;
	
	public static Planet planetPluto;
	public static ExPlanet planetKuiperBelt;
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
		
		planetMercury = BodiesHelper.registerExPlanet(sol, "mercury", GalaxySpace.ASSET_PREFIX, 0.5F);
		BodiesHelper.setOrbitData(planetMercury, 1.45F, 0.5F, 0.24F, 0F, 0F, 4F, 0F);
		BodiesHelper.setPlanetData(planetMercury, 0, 176000L, BodiesHelper.calculateGravity(3.8F), true);
		BodiesHelper.setAtmosphere(planetMercury, false, false, false, 8.0F, 0.0F, 0.0F);
		BodiesHelper.setProviderData(planetMercury, WorldProviderMercury.class, GSConfigDimensions.dimensionIDMercury, 3, ACBiome.ACSpace);
		GalaxyRegistry.registerPlanet(planetMercury);
		
		planetCeres = BodiesHelper.registerExPlanet(sol, "ceres", GalaxySpace.ASSET_PREFIX, 1.7F);
		planetCeres.setRingColorRGB(0F, 0F, 0F);
		BodiesHelper.setOrbitData(planetCeres, 2.0F, 0.5F, 15.0F);
		BodiesHelper.setPlanetData(planetCeres, 0, 10000L, BodiesHelper.calculateGravity(2.37F), true);
		BodiesHelper.setAtmosphere(planetCeres, false, false, false, -5.0F, 0.0F, 0.0F);
		BodiesHelper.setProviderData(planetCeres, WorldProviderCeres.class, GSConfigDimensions.dimensionIDCeres, 3, ACBiome.ACSpace);
		GalaxyRegistry.registerPlanet(planetCeres);
		
		planetJupiter = BodiesHelper.registerExPlanet(sol, "jupiter", GalaxySpace.ASSET_PREFIX, 2.0F);
		BodiesHelper.setOrbitData(planetJupiter, (float) Math.PI, 2.0F, 11.86F);
		GalaxyRegistry.registerPlanet(planetJupiter);
		
		planetSaturn = BodiesHelper.registerExPlanet(sol, "saturn", GalaxySpace.ASSET_PREFIX, 2.25F);
		BodiesHelper.setOrbitData(planetSaturn, (float) Math.PI / 2, 1.5F, 29.46F);
		GalaxyRegistry.registerPlanet(planetSaturn);
		
		planetUranus = BodiesHelper.registerExPlanet(sol, "uranus", GalaxySpace.ASSET_PREFIX, 2.5F);
		BodiesHelper.setOrbitData(planetUranus, (float) Math.PI / 4, 1.2F, 84.06F);
		GalaxyRegistry.registerPlanet(planetUranus);
		
		planetNeptune = BodiesHelper.registerExPlanet(sol, "neptune", GalaxySpace.ASSET_PREFIX, 2.75F);
		BodiesHelper.setOrbitData(planetNeptune, (float) Math.PI, 1.2F, 164.84F);
		GalaxyRegistry.registerPlanet(planetNeptune);
		
		planetPluto = BodiesHelper.registerExPlanet(sol, "pluto", GalaxySpace.ASSET_PREFIX, 3.0F);
		planetPluto.atmosphereComponent(EnumAtmosphericGas.NITROGEN);
		BodiesHelper.setOrbitData(planetPluto, 0.0F, 0.5F, 250.0F, 0, 0, 25F, 0F);
		BodiesHelper.setPlanetData(planetPluto, 0, 98000L, BodiesHelper.calculateGravity(2.62F), true);
		BodiesHelper.setAtmosphere(planetPluto, false, false, false, -12.0F, 0.0F, 0.0F);
		BodiesHelper.setProviderData(planetPluto, WorldProviderPluto.class, GSConfigDimensions.dimensionIDPluto, 6, ACBiome.ACSpace, ACBiome.ACSpaceLowPlains, ACBiome.ACSpaceLowHills, ACBiome.ACSpaceMidPlains);
		GalaxyRegistry.registerPlanet(planetPluto);
		
		planetKuiperBelt = (ExPlanet) BodiesHelper.registerExPlanet(sol, "kuiperbelt", GalaxySpace.ASSET_PREFIX, 3.25F).setRingColorRGB(1.1F, 0.0F, 0.0F);
		planetKuiperBelt.setClassPlanet(ClassBody.ASTEROID);
		BodiesHelper.setOrbitData(planetKuiperBelt, 1.0F, 1.0F, 300F);
		BodiesHelper.setAtmosphere(planetKuiperBelt, false, false, false, -12.0F, 0.0F, 0.0F);
		BodiesHelper.setPlanetData(planetKuiperBelt, 0, 0, 0, true);
		BodiesHelper.setProviderData(planetKuiperBelt, WorldProviderKuiperBelt.class, GSConfigDimensions.dimensionIDKuiperBelt, 6, ACBiome.ACSpace);
		GalaxyRegistry.registerPlanet(planetKuiperBelt);
		
		planetHaumea = BodiesHelper.registerExPlanet(sol, "haumea", GalaxySpace.ASSET_PREFIX, 3.5F);
		BodiesHelper.setOrbitData(planetHaumea, (float) Math.PI + 1.64F, 1.0F, 392.9F);
		BodiesHelper.setAtmosphere(planetHaumea, false, false, false, -12.0F, 0.0F, 0.0F);
		BodiesHelper.setPlanetData(planetHaumea, 0, 12000, BodiesHelper.calculateGravity(2.02F), true);
		BodiesHelper.setProviderData(planetHaumea, WorldProviderHaumea_WE.class, GSConfigDimensions.dimensionIDHaumea, 6, ACBiome.ACSpace);
		GalaxyRegistry.registerPlanet(planetHaumea);
				
		phobosMars = BodiesHelper.registerExMoon(MarsModule.planetMars, "phobos", GalaxySpace.ASSET_PREFIX, 8.0F);
		BodiesHelper.setOrbitData(phobosMars, 1.0F, 0.0017F, 100F);
		GalaxyRegistry.registerMoon(phobosMars);
		
		deimosMars = BodiesHelper.registerExMoon(MarsModule.planetMars, "deimos", GalaxySpace.ASSET_PREFIX, 16.0F);
		BodiesHelper.setOrbitData(deimosMars, 1.0F, 0.0017F, 300F);
		GalaxyRegistry.registerMoon(deimosMars);
					
		ioJupiter = BodiesHelper.registerExMoon(planetJupiter, "io", GalaxySpace.ASSET_PREFIX, 10F);
		BodiesHelper.setOrbitData(ioJupiter, 1.0F, 0.0017F, 50F);
		BodiesHelper.setAtmosphere(ioJupiter, false, false, false, -4.2F, 0.0F, 0.0F);
		BodiesHelper.setPlanetData(ioJupiter, 0, 42000, 0.052F, true);
		BodiesHelper.setProviderData(ioJupiter, WorldProviderIo.class, GSConfigDimensions.dimensionIDIo, 4, ACBiome.ACSpace, ACBiome.ACSpaceLowHills, ACBiome.ACSpaceLowPlains);
		GalaxyRegistry.registerMoon(ioJupiter);
		
		europaJupiter = BodiesHelper.registerExMoon(planetJupiter, "europa", GalaxySpace.ASSET_PREFIX, 15F);
		BodiesHelper.setOrbitData(europaJupiter, (float)Math.PI, 0.0017F, 100F);
		BodiesHelper.setAtmosphere(europaJupiter, false, false, false, -2.0F, 0.0F, 0.0F);
		BodiesHelper.setPlanetData(europaJupiter, 0, 58000L, 0.062F, true);
		BodiesHelper.setProviderData(europaJupiter, WorldProviderEuropa.class, GSConfigDimensions.dimensionIDEuropa, 4);
		GalaxyRegistry.registerMoon(europaJupiter);
		
		ganymedeJupiter = BodiesHelper.registerExMoon(planetJupiter, "ganymede", GalaxySpace.ASSET_PREFIX, 20F);
		BodiesHelper.setOrbitData(ganymedeJupiter, (float)Math.PI / 2, 0.0017F, 150F);
		BodiesHelper.setAtmosphere(ganymedeJupiter, false, false, false, -2.0F, 0.0F, 0.0F);
		BodiesHelper.setPlanetData(ganymedeJupiter, 0, 102000L, 0.057F, false);
		BodiesHelper.setProviderData(ganymedeJupiter, WorldProviderGanymede.class, GSConfigDimensions.dimensionIDGanymede, 4);
		GalaxyRegistry.registerMoon(ganymedeJupiter);
		
		callistoJupiter = BodiesHelper.registerExMoon(planetJupiter, "callisto", GalaxySpace.ASSET_PREFIX, 30F);
		BodiesHelper.setOrbitData(callistoJupiter, (float)Math.PI / 3, 0.0017F, 200F);
		BodiesHelper.setAtmosphere(callistoJupiter, false, false, false, -2.0F, 0.0F, 0.0F);
		BodiesHelper.setPlanetData(callistoJupiter, 0, 154000L, 0.054F, false);
		BodiesHelper.setProviderData(callistoJupiter, WorldProviderCallisto.class, GSConfigDimensions.dimensionIDCallisto, 4);
		GalaxyRegistry.registerMoon(callistoJupiter);
				
		mimasSaturn = BodiesHelper.registerExMoon(planetSaturn, "mimas", GalaxySpace.ASSET_PREFIX, 10F);
		BodiesHelper.setOrbitData(mimasSaturn, (float) Math.PI / 2, 0.0017F, 20F);
		GalaxyRegistry.registerMoon(mimasSaturn);
		
		enceladusSaturn = BodiesHelper.registerExMoon(planetSaturn, "enceladus", GalaxySpace.ASSET_PREFIX, 15F);
		BodiesHelper.setOrbitData(enceladusSaturn, (float)Math.PI / 3, 0.0017F, 50F);
		BodiesHelper.setAtmosphere(enceladusSaturn, false, false, false, -4.0F, 0.0F, 0.0F);
		BodiesHelper.setPlanetData(enceladusSaturn, 0, 32000L, 0.058F, false);
		BodiesHelper.setProviderData(enceladusSaturn, GSConfigCore.enableWorldEngine ? WorldProviderEnceladus_WE.class : WorldProviderEnceladus.class, GSConfigDimensions.dimensionIDEnceladus, 5);
		GalaxyRegistry.registerMoon(enceladusSaturn);
	
		tethysSaturn = BodiesHelper.registerExMoon(planetSaturn, "tethys", GalaxySpace.ASSET_PREFIX, 20F);
		BodiesHelper.setOrbitData(tethysSaturn, (float) Math.PI, 0.0017F, 120F);
		GalaxyRegistry.registerMoon(tethysSaturn);
		
		dioneSaturn = BodiesHelper.registerExMoon(planetSaturn, "dione", GalaxySpace.ASSET_PREFIX, 25F);
		BodiesHelper.setOrbitData(dioneSaturn, (float) Math.PI / 4, 0.0017F, 180F);
		GalaxyRegistry.registerMoon(dioneSaturn);
		
		rheyaSaturn = BodiesHelper.registerExMoon(planetSaturn, "rheya", GalaxySpace.ASSET_PREFIX, 30F);
		BodiesHelper.setOrbitData(rheyaSaturn, (float) Math.PI / 3, 0.0017F, 220F);
		GalaxyRegistry.registerMoon(rheyaSaturn);
		
		titanSaturn = BodiesHelper.registerExMoon(planetSaturn, "titan", GalaxySpace.ASSET_PREFIX, 35F);
		titanSaturn.atmosphereComponent(EnumAtmosphericGas.NITROGEN);
		BodiesHelper.setOrbitData(titanSaturn, (float)Math.PI / 5, 0.0017F, 280F);
		BodiesHelper.setAtmosphere(titanSaturn, false, false, false, -4.0F, 1.0F, 0.0F);
		BodiesHelper.setPlanetData(titanSaturn, 0, 105500L, 0.058F, false);
		BodiesHelper.setProviderData(titanSaturn, WorldProviderTitan.class, GSConfigDimensions.dimensionIDTitan, 5);
		GalaxyRegistry.registerMoon(titanSaturn);
		
		iapetusSaturn = BodiesHelper.registerExMoon(planetSaturn, "iapetus", GalaxySpace.ASSET_PREFIX, 40F);
		BodiesHelper.setOrbitData(iapetusSaturn, (float) Math.PI, 0.0017F, 350F);
		GalaxyRegistry.registerMoon(iapetusSaturn);		
		
		mirandaUranus = BodiesHelper.registerExMoon(planetUranus, "miranda", GalaxySpace.ASSET_PREFIX, 10F);
		BodiesHelper.setOrbitData(mirandaUranus, (float)Math.PI, 0.0017F, 20F);
		BodiesHelper.setAtmosphere(mirandaUranus, false, false, false, -5.0F, 0.0F, 0.0F);
		BodiesHelper.setPlanetData(mirandaUranus, 0, 33500L, 0.057F, true);
		BodiesHelper.setProviderData(mirandaUranus, WorldProviderMiranda.class, GSConfigDimensions.dimensionIDMiranda, 5, ACBiome.ACSpace, ACBiome.ACSpaceLowPlains, ACBiome.ACSpaceMidHills);
		GalaxyRegistry.registerMoon(mirandaUranus);
			
		arielUranus = BodiesHelper.registerExMoon(planetUranus, "ariel", GalaxySpace.ASSET_PREFIX, 15F);
		BodiesHelper.setOrbitData(arielUranus, (float) Math.PI / 2, 0.0017F, 50F);
		GalaxyRegistry.registerMoon(arielUranus);
		
		umbrielUranus = BodiesHelper.registerExMoon(planetUranus, "umbriel", GalaxySpace.ASSET_PREFIX, 20F);
		BodiesHelper.setOrbitData(umbrielUranus, (float) Math.PI / 3, 0.0017F, 120F);
		GalaxyRegistry.registerMoon(umbrielUranus);
		
		titaniaUranus = BodiesHelper.registerExMoon(planetUranus, "titania", GalaxySpace.ASSET_PREFIX, 25F);
		BodiesHelper.setOrbitData(titaniaUranus, (float) Math.PI / 4, 0.0017F, 180F);
		GalaxyRegistry.registerMoon(titaniaUranus);
		
		oberonUranus = BodiesHelper.registerExMoon(planetUranus, "oberon", GalaxySpace.ASSET_PREFIX, 30F);
		BodiesHelper.setOrbitData(oberonUranus, (float) Math.PI / 4, 0.0017F, 220F);
		GalaxyRegistry.registerMoon(oberonUranus);
		
		proteusNeptune = BodiesHelper.registerExMoon(planetNeptune, "proteus", GalaxySpace.ASSET_PREFIX, 10F);
		BodiesHelper.setOrbitData(proteusNeptune, (float) Math.PI, 0.0017F, 50F);
		GalaxyRegistry.registerMoon(proteusNeptune);
		
		tritonNeptune = BodiesHelper.registerExMoon(planetNeptune, "triton", GalaxySpace.ASSET_PREFIX, 25F);
		BodiesHelper.setOrbitData(tritonNeptune, (float)Math.PI, 0.0017F, -200F);
		BodiesHelper.setAtmosphere(tritonNeptune, false, false, false, -11.2F, 0.0F, 0.0F);
		BodiesHelper.setPlanetData(tritonNeptune, 0, 145200L, 0.059F, true);
		BodiesHelper.setProviderData(tritonNeptune, WorldProviderTriton_WE.class, GSConfigDimensions.dimensionIDTriton, 6);
		GalaxyRegistry.registerMoon(tritonNeptune);		
		
		charonPluto = BodiesHelper.registerExMoon(planetPluto, "charon", GalaxySpace.ASSET_PREFIX, 15F);
		BodiesHelper.setOrbitData(charonPluto, (float) Math.PI, 0.0017F, 50F);
		GalaxyRegistry.registerMoon(charonPluto);
				
		//venusSpaceStation = BodiesHelper.registerSatellite(VenusModule.planetVenus, GalaxySpace.ASSET_PREFIX, WorldProviderVenusSS.class, GSConfigDimensions.idDimensionVenusOrbit, GSConfigDimensions.idDimensionMarsOrbitStatic, (float) Math.PI, 0.2667F, 5.5F, 20.0F);
		
		venusSpaceStation = new Satellite("spacestation.venus").setParentBody(VenusModule.planetVenus);
		venusSpaceStation.setRelativeSize(0.2667F);
		venusSpaceStation.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(5.5F, 5.5F));
		venusSpaceStation.setRelativeOrbitTime(20.0F);
		if(GSConfigDimensions.enableVenusSpaceStation) {
			venusSpaceStation.setTierRequired(VenusModule.planetVenus.getTierRequirement());
			venusSpaceStation.setDimensionInfo(GSConfigDimensions.idDimensionVenusOrbit, GSConfigDimensions.idDimensionVenusOrbitStatic, WorldProviderVenusSS.class);
			venusSpaceStation.setBodyIcon(new ResourceLocation("galacticraftcore:textures/gui/celestialbodies/space_station.png"));
			venusSpaceStation.addChecklistKeys("equip_oxygen_suit", "equip_parachute");
			venusSpaceStation.setBiomeInfo(ACBiome.ACSpace);
			venusSpaceStation.atmosphereComponent(EnumAtmosphericGas.CO2).atmosphereComponent(EnumAtmosphericGas.NITROGEN);
			venusSpaceStation.setAtmosphere(new AtmosphereInfo(false, false, false, 0.8F, 0.4F, 0.0F));
		}
		//GalaxyRegistry.getRegisteredPlanets().remove(MarsModule.planetMars.getName());
		//GalaxyRegistry.getRegisteredPlanetIDs().remove(MarsModule.planetMars.getName());
		
		registryteleport();
		registrycelestial(); 		
		
	}
	
	public void init(FMLInitializationEvent event)
	{
		
    	// TODO Rings ---------------------------------
		VenusModule.planetVenus.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(0.745F, 0.74F)).setRingColorRGB(0.0F, 0.4F, 0.9F);
		GalacticraftCore.satelliteSpaceStation.setRingColorRGB(0.0F, 0.4F, 0.9F);	
		GalacticraftCore.moonMoon.setRingColorRGB(0.0F, 0.4F, 0.9F);	
		MarsModule.planetMars.setRingColorRGB(0.0F, 0.4F, 0.9F);
		if(GSConfigCore.enableWorldEngine && GSConfigCore.enableGSMars) {
			MarsModule.planetMars.setDimensionInfo(ConfigManagerMars.dimensionIDMars, WorldProviderMars_WE.class, true);
			MarsModule.planetMars.setAtmosphere(new AtmosphereInfo(false, false, false, -2.0F, 1.0F, 0.1F));
			MarsModule.planetMars.atmosphereComponent(EnumAtmosphericGas.CO2).atmosphereComponent(EnumAtmosphericGas.ARGON).atmosphereComponent(EnumAtmosphericGas.NITROGEN);
		}
		AsteroidsModule.planetAsteroids.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(1.75F, 1.75F));
				
		// --------------------------------------------
		// TODO Overworld -----------------------------
		if(GSConfigCore.enableOverworldOres) {
			if(!CompatibilityManager.isTConstructLoaded)
				GameRegistry.registerWorldGenerator(new OreGenerator(GSBlocks.OVERWORLD_ORES.getDefaultState().withProperty(BlockOres.BASIC_TYPE, BlockOres.EnumBlockOres.COBALT), 6, 0, 60, 5, Blocks.STONE.getDefaultState(), 0), 0);
			
			GameRegistry.registerWorldGenerator(new OreGenerator(GSBlocks.OVERWORLD_ORES.getDefaultState().withProperty(BlockOres.BASIC_TYPE, BlockOres.EnumBlockOres.NICKEL), 6, 0, 45, 4, Blocks.STONE.getDefaultState(), 0), 1);
		}			
		
		// --------------------------------------------		
		/*	if(GSConfigCore.enableOresGeneration) 
		{
			// TODO Moon ----------------------------------
			GameRegistry.registerWorldGenerator(new OreGenerator(GSBlocks.OVERWORLD_ORES.getStateFromMeta(2), 4, 0, 45, 4, GCBlocks.blockMoon.getStateFromMeta(4), ConfigManagerCore.idDimensionMoon), 4);
			// --------------------------------------------
		}*/
		   	   	
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
		
        GalacticraftRegistry.registerGear(GSConstants.GEAR_ID_OXYGEN_TANK_4, EnumExtendedInventorySlot.LEFT_TANK, GSItems.OXYGENTANK_TIER_4);
        GalacticraftRegistry.registerGear(GSConstants.GEAR_ID_OXYGEN_TANK_4, EnumExtendedInventorySlot.RIGHT_TANK, GSItems.OXYGENTANK_TIER_4);
        GalacticraftRegistry.registerGear(GSConstants.GEAR_ID_OXYGEN_TANK_5, EnumExtendedInventorySlot.LEFT_TANK, GSItems.OXYGENTANK_TIER_5);
        GalacticraftRegistry.registerGear(GSConstants.GEAR_ID_OXYGEN_TANK_5, EnumExtendedInventorySlot.RIGHT_TANK, GSItems.OXYGENTANK_TIER_5);
        GalacticraftRegistry.registerGear(GSConstants.GEAR_ID_OXYGEN_TANK_6, EnumExtendedInventorySlot.LEFT_TANK, GSItems.OXYGENTANK_TIER_6);
        GalacticraftRegistry.registerGear(GSConstants.GEAR_ID_OXYGEN_TANK_6, EnumExtendedInventorySlot.RIGHT_TANK, GSItems.OXYGENTANK_TIER_6);
        GalacticraftRegistry.registerGear(GSConstants.GEAR_ID_OXYGEN_TANK_EPP, EnumExtendedInventorySlot.LEFT_TANK, GSItems.OXYGENTANK_TIER_EPP);
        GalacticraftRegistry.registerGear(GSConstants.GEAR_ID_OXYGEN_TANK_EPP, EnumExtendedInventorySlot.RIGHT_TANK, GSItems.OXYGENTANK_TIER_EPP);
        
        GalacticraftRegistry.registerGear(GSConstants.GEAR_ID_TEMP_SHIELD_CONTROLLER, EnumExtendedInventorySlot.SHIELD_CONTROLLER, new ItemStack(GSItems.BASIC, 1, 16));

        GalacticraftRegistry.registerGear(GSConstants.GEAR_ID_THERMAL_PADDING_T3_HELMET, EnumExtendedInventorySlot.THERMAL_HELMET, new ItemStack(GSItems.THERMAL_PADDING_3, 1, 0));
        GalacticraftRegistry.registerGear(GSConstants.GEAR_ID_THERMAL_PADDING_T3_CHESTPLATE, EnumExtendedInventorySlot.THERMAL_CHESTPLATE, new ItemStack(GSItems.THERMAL_PADDING_3, 1, 1));
        GalacticraftRegistry.registerGear(GSConstants.GEAR_ID_THERMAL_PADDING_T3_LEGGINGS, EnumExtendedInventorySlot.THERMAL_LEGGINGS, new ItemStack(GSItems.THERMAL_PADDING_3, 1, 2));
        GalacticraftRegistry.registerGear(GSConstants.GEAR_ID_THERMAL_PADDING_T3_BOOTS, EnumExtendedInventorySlot.THERMAL_BOOTS, new ItemStack(GSItems.THERMAL_PADDING_3, 1, 3));
        
        GalacticraftRegistry.registerGear(GSConstants.GEAR_ID_THERMAL_PADDING_T4_HELMET, EnumExtendedInventorySlot.THERMAL_HELMET, new ItemStack(GSItems.THERMAL_PADDING_4, 1, 0));
        GalacticraftRegistry.registerGear(GSConstants.GEAR_ID_THERMAL_PADDING_T4_CHESTPLATE, EnumExtendedInventorySlot.THERMAL_CHESTPLATE, new ItemStack(GSItems.THERMAL_PADDING_4, 1, 1));
        GalacticraftRegistry.registerGear(GSConstants.GEAR_ID_THERMAL_PADDING_T4_LEGGINGS, EnumExtendedInventorySlot.THERMAL_LEGGINGS, new ItemStack(GSItems.THERMAL_PADDING_4, 1, 2));
        GalacticraftRegistry.registerGear(GSConstants.GEAR_ID_THERMAL_PADDING_T4_BOOTS, EnumExtendedInventorySlot.THERMAL_BOOTS, new ItemStack(GSItems.THERMAL_PADDING_4, 1, 3));
        
        if(GSConfigDimensions.enableVenusSpaceStation) {
	        GSDimensions.VENUS_SS = GalacticraftRegistry.registerDimension("Venus Space Station", "_venus_orbit", GSConfigDimensions.idDimensionVenusOrbit, WorldProviderVenusSS.class, false);
			if (GSDimensions.VENUS_SS == null)
	        {
	            GalaxySpace.instance.debug("Failed to register space station dimension type with ID " + GSConfigDimensions.idDimensionVenusOrbit);
	        }
			GSDimensions.VENUS_SS_KEEPLOADED = GalacticraftRegistry.registerDimension("Venus Space Station", "_venus_orbit", GSConfigDimensions.idDimensionVenusOrbitStatic, WorldProviderVenusSS.class, true);
	    }
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
	    GalacticraftRegistry.addDungeonLoot(4, new ItemStack(GSItems.GEOLOGICAL_SCANNER, 1, 100));
	    
	    GalacticraftRegistry.addDungeonLoot(5, new ItemStack(GSItems.ROCKET_MODULES, 1, 4));
	    GalacticraftRegistry.addDungeonLoot(5, new ItemStack(GSItems.ROCKET_MODULES, 1, 6));
	}
	
	private static void registrycelestial()
	{
		/*
		ItemStack[] suit = new ItemStack[] 
		{
			new ItemStack(GSItems.SPACE_SUIT_HELMET, 1, 1),
			new ItemStack(GSItems.SPACE_SUIT_BODY, 1, 1),
			new ItemStack(GSItems.SPACE_SUIT_LEGGINS, 1, 1),
			new ItemStack(GSItems.SPACE_SUIT_BOOTS, 1, 1)
		};
		*/
		BodiesData data = new BodiesData(TypeBody.STAR, ClassBody.DWARF).setStarColor(StarColor.YELLOW);
		BodiesHelper.registerBodyData(GalacticraftCore.solarSystemSol.getMainStar(), data);

		data = new BodiesData(null, BodiesHelper.calculateGravity(8.88F), 92, 182000, false);
		BodiesHelper.registerBodyData(VenusModule.planetVenus, data);
		
		data = new BodiesData(null, BodiesHelper.calculateGravity(10.0F), 1, 24000, false);
		BodiesHelper.registerBodyData(GalacticraftCore.planetOverworld, data);		
		
		data = new BodiesData(null, BodiesHelper.calculateGravity(5.37F), 0, 24660, false);
		BodiesHelper.registerBodyData(MarsModule.planetMars, data);
		    	
		data = new BodiesData(null, 0, 0, 0, true);
		BodiesHelper.registerBodyData(AsteroidsModule.planetAsteroids, data);
    	
	   	////MOONS
	   	data = new BodiesData(null, 0.062F, 0, 192000, false);
		BodiesHelper.registerBodyData(GalacticraftCore.moonMoon, data);
		
		BodiesData unreachableData = new BodiesData(null, 0F, 0, 0, false);	
		BodiesHelper.registerBodyData(oberonUranus, unreachableData);
		BodiesHelper.registerBodyData(proteusNeptune, unreachableData);
		BodiesHelper.registerBodyData(mimasSaturn, unreachableData); 	
		BodiesHelper.registerBodyData(tethysSaturn, unreachableData); 	
		BodiesHelper.registerBodyData(dioneSaturn, unreachableData); 	
		BodiesHelper.registerBodyData(rheyaSaturn, unreachableData); 	
		BodiesHelper.registerBodyData(iapetusSaturn, unreachableData); 	
		BodiesHelper.registerBodyData(arielUranus, unreachableData); 	
		BodiesHelper.registerBodyData(umbrielUranus, unreachableData); 	
		BodiesHelper.registerBodyData(titaniaUranus, unreachableData); 	
		BodiesHelper.registerBodyData(charonPluto, unreachableData); 
			
	}
	
	private static void registryteleport()
	{
	
		GalacticraftRegistry.registerTeleportType(WorldProviderMercury.class, new TeleportTypeMercury());	
		
		if(GSConfigCore.enableWorldEngine && GSConfigCore.enableGSMars)
			GalacticraftRegistry.registerTeleportType(WorldProviderMars_WE.class, new TeleportTypeMars());
		
		
		GalacticraftRegistry.registerTeleportType(WorldProviderCeres.class, new TeleportTypeCeres());
		GalacticraftRegistry.registerTeleportType(WorldProviderPluto.class, new TeleportTypePluto());
		GalacticraftRegistry.registerTeleportType(WorldProviderKuiperBelt.class, new TeleportTypeKuiperBelt());
		GalacticraftRegistry.registerTeleportType(WorldProviderHaumea_WE.class, new TeleportTypeHaumea());
		/*GalacticraftRegistry.registerTeleportType(WorldProviderMakemake.class, new TeleportTypeMakemake());*/
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
		
		GalacticraftRegistry.registerTeleportType(WorldProviderProteus.class, new WorldProviderProteus());*/
		GalacticraftRegistry.registerTeleportType(WorldProviderTriton_WE.class, new TeleportTypeTriton());
		  
		
		/*
		if(GSConfigDimensions.enableMarsSS)
		{ */
			//GalacticraftRegistry.registerTeleportType(WorldProviderMarsSS.class, new TeleportTypeMarsSS());
		/*	GalacticraftRegistry.registerProvider(GSConfigDimensions.dimensionIDMarsOrbit, WorldProviderMarsSS.class, false, -40);
			GalacticraftRegistry.registerProvider(GSConfigDimensions.dimensionIDMarsOrbitStatic, WorldProviderMarsSS.class, true, -41);
		}*/
		if(GSConfigDimensions.enableVenusSpaceStation)
		{
			GalaxyRegistry.registerSatellite(venusSpaceStation);
			
			GalacticraftRegistry.registerTeleportType(WorldProviderVenusSS.class, new TeleportTypeVenusSS());
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
		GSDimensions.TRITON = WorldUtil.getDimensionTypeById(GSConfigDimensions.dimensionIDTriton);
		
		GSDimensions.KUIPER_BELT = WorldUtil.getDimensionTypeById(GSConfigDimensions.dimensionIDKuiperBelt);
		GSDimensions.HAUMEA = WorldUtil.getDimensionTypeById(GSConfigDimensions.dimensionIDHaumea);
		
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
    	
    	//GalaxySpace.debug("Reg schem recipe");
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
    	CraftingRecipesHaumea.loadRecipes();
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
		
		if(GSConfigDimensions.enableVenusSpaceStation)
			GalacticraftRegistry.registerSpaceStation(new SpaceStationType(GSConfigDimensions.idDimensionVenusOrbit,
				ConfigManagerVenus.dimensionIDVenus, new SpaceStationRecipe(spaceStationRequirements)));
    	 
	}

	@Override
	public boolean canRegister() {
		return true;
	}
	

}
