package galaxyspace.systems.SolarSystem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import asmodeuscore.api.IBodies;
import asmodeuscore.api.IBodiesHandler;
import asmodeuscore.api.dimension.IAdvancedSpace.ClassBody;
import asmodeuscore.api.dimension.IAdvancedSpace.StarClass;
import asmodeuscore.api.dimension.IAdvancedSpace.StarColor;
import asmodeuscore.api.dimension.IAdvancedSpace.TypeBody;
import asmodeuscore.core.astronomy.BodiesData;
import asmodeuscore.core.astronomy.BodiesRegistry;
import asmodeuscore.core.astronomy.dimension.world.OreGenerator;
import asmodeuscore.core.astronomy.dimension.world.gen.ACBiome;
import asmodeuscore.core.prefab.TeleportTypeBody;
import asmodeuscore.core.prefab.celestialbody.ExMoon;
import asmodeuscore.core.prefab.celestialbody.ExPlanet;
import galaxyspace.GalaxySpace;
import galaxyspace.core.GSBlocks;
import galaxyspace.core.GSItems;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.configs.GSConfigDimensions;
import galaxyspace.core.prefab.world.gen.WorldGenNaturalGas;
import galaxyspace.core.util.GSConstants;
import galaxyspace.core.util.GSDimensions;
import galaxyspace.systems.SolarSystem.moons.callisto.dimension.TeleportTypeCallisto;
import galaxyspace.systems.SolarSystem.moons.callisto.dimension.WorldProviderCallisto;
import galaxyspace.systems.SolarSystem.moons.enceladus.dimension.TeleportTypeEnceladus;
import galaxyspace.systems.SolarSystem.moons.enceladus.dimension.WorldProviderEnceladus;
import galaxyspace.systems.SolarSystem.moons.enceladus.dimension.WorldProviderEnceladus_WE;
import galaxyspace.systems.SolarSystem.moons.europa.dimension.TeleportTypeEuropa;
import galaxyspace.systems.SolarSystem.moons.europa.dimension.WorldProviderEuropa_WE;
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
import galaxyspace.systems.SolarSystem.moons.phobos.dimension.WorldProviderPhobos;
import galaxyspace.systems.SolarSystem.moons.phobos.recipes.CraftingRecipesPhobos;
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
import galaxyspace.systems.SolarSystem.planets.overworld.world.gen.NickelGenerator;
import galaxyspace.systems.SolarSystem.planets.pluto.dimension.TeleportTypePluto;
import galaxyspace.systems.SolarSystem.planets.pluto.dimension.WorldProviderPluto;
import galaxyspace.systems.SolarSystem.planets.test.dimension.WorldProviderTest_WE2;
import galaxyspace.systems.SolarSystem.satellites.jupiter.dimension.TeleportTypeJupiterSS;
import galaxyspace.systems.SolarSystem.satellites.jupiter.dimension.WorldProviderJupiterSS;
import galaxyspace.systems.SolarSystem.satellites.venus.dimension.TeleportTypeVenusSS;
import galaxyspace.systems.SolarSystem.satellites.venus.dimension.WorldProviderVenusSS;
import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody.ScalableDistance;
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
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@IBodiesHandler
public class SolarSystemBodies implements IBodies{
		
	public static Planet planetMercury = GalacticraftCore.planetMercury;

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
	
	public static Planet planetTest;
		
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
	
	public static Satellite venusSpaceStation;
	public static Satellite marsSpaceStation;
	public static Satellite jupiterSpaceStation;
	
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
		
		planetMercury = BodiesRegistry.registerExPlanet(sol, "mercury", GalaxySpace.ASSET_PREFIX, 0.5F);
		BodiesRegistry.setOrbitData(planetMercury, 1.45F, 0.5F, 0.24F, 0F, 0F, 4F, 0F);
		BodiesRegistry.setPlanetData(planetMercury, 0F, 176000L, BodiesRegistry.calculateGravity(3.8F), true);
		BodiesRegistry.setAtmosphere(planetMercury, false, false, false, 8.0F, 0.0F, 0.0F);
		BodiesRegistry.setProviderData(planetMercury, WorldProviderMercury.class, GSConfigDimensions.dimensionIDMercury, 3, ACBiome.ACSpace);
		GalaxyRegistry.registerPlanet(planetMercury);
		
		planetCeres = BodiesRegistry.registerExPlanet(sol, "ceres", GalaxySpace.ASSET_PREFIX, 1.7F);
		planetCeres.setRingColorRGB(0F, 0F, 0F);
		BodiesRegistry.setOrbitData(planetCeres, 2.0F, 0.5F, 15.0F);
		BodiesRegistry.setPlanetData(planetCeres, 0F, 10000L, BodiesRegistry.calculateGravity(2.37F), true);
		BodiesRegistry.setAtmosphere(planetCeres, false, false, false, -3.33F, 0.0F, 0.0F);
		BodiesRegistry.setProviderData(planetCeres, WorldProviderCeres.class, GSConfigDimensions.dimensionIDCeres, 3, ACBiome.ACSpace);
		GalaxyRegistry.registerPlanet(planetCeres);
		
		planetJupiter = BodiesRegistry.registerExPlanet(sol, "jupiter", GalaxySpace.ASSET_PREFIX, 2.0F);
		//BodiesRegistry.setOrbitData(planetJupiter, (float) Math.PI, 2.0F, 11.86F);
		//BodiesRegistry.setPlanetData(planetJupiter, 0F, 10000L, BodiesRegistry.calculateGravity(2.37F), true);
		//BodiesRegistry.setAtmosphere(planetJupiter, false, false, false, -5.0F, 0.0F, 0.0F);
		//BodiesRegistry.setProviderData(planetJupiter, WorldProviderCeres.class, GSConfigDimensions.dimensionIDJupiter, 100, ACBiome.ACSpace);
		//planetJupiter.setUnreachable();
		GalaxyRegistry.registerPlanet(planetJupiter);
		
		planetSaturn = BodiesRegistry.registerExPlanet(sol, "saturn", GalaxySpace.ASSET_PREFIX, 2.25F);
		BodiesRegistry.setOrbitData(planetSaturn, (float) Math.PI / 2, 1.5F, 29.46F);
		GalaxyRegistry.registerPlanet(planetSaturn);
		
		planetUranus = BodiesRegistry.registerExPlanet(sol, "uranus", GalaxySpace.ASSET_PREFIX, 2.5F);
		BodiesRegistry.setOrbitData(planetUranus, (float) Math.PI / 4, 1.2F, 84.06F);
		GalaxyRegistry.registerPlanet(planetUranus);
		
		planetNeptune = BodiesRegistry.registerExPlanet(sol, "neptune", GalaxySpace.ASSET_PREFIX, 2.75F);
		BodiesRegistry.setOrbitData(planetNeptune, (float) Math.PI, 1.2F, 164.84F);
		GalaxyRegistry.registerPlanet(planetNeptune);
		
		planetPluto = BodiesRegistry.registerExPlanet(sol, "pluto", GalaxySpace.ASSET_PREFIX, 3.0F);
		BodiesRegistry.setOrbitData(planetPluto, 0.0F, 0.5F, 250.0F, 0, 0, 25F, 0F);
		BodiesRegistry.setPlanetData(planetPluto, 0F, 98000L, BodiesRegistry.calculateGravity(2.62F), true);
		BodiesRegistry.setAtmosphere(planetPluto, false, false, false, -12.0F, 0.0F, 0.0F);
		BodiesRegistry.setProviderData(planetPluto, WorldProviderPluto.class, GSConfigDimensions.dimensionIDPluto, 6, ACBiome.ACSpace, ACBiome.ACSpaceLowPlains, ACBiome.ACSpaceLowHills, ACBiome.ACSpaceMidPlains);
		planetPluto.atmosphereComponent(EnumAtmosphericGas.NITROGEN);
		GalaxyRegistry.registerPlanet(planetPluto);
		
		planetKuiperBelt = (ExPlanet) BodiesRegistry.registerExPlanet(sol, "kuiperbelt", GalaxySpace.ASSET_PREFIX, 3.25F).setRingColorRGB(1.1F, 0.0F, 0.0F);
		planetKuiperBelt.setClassPlanet(ClassBody.ASTEROID);
		planetKuiperBelt.setRelativeDistanceFromCenter(new ScalableDistance(3.5F, 3.2F));
		BodiesRegistry.setOrbitData(planetKuiperBelt, 1.0F, 1.0F, 300F);
		BodiesRegistry.setAtmosphere(planetKuiperBelt, false, false, false, -12.0F, 0.0F, 0.0F);
		BodiesRegistry.setPlanetData(planetKuiperBelt, 0F, 0, 0, true);
		BodiesRegistry.setProviderData(planetKuiperBelt, WorldProviderKuiperBelt.class, GSConfigDimensions.dimensionIDKuiperBelt, 6, ACBiome.ACSpace);
		GalaxyRegistry.registerPlanet(planetKuiperBelt);
		
		planetHaumea = BodiesRegistry.registerExPlanet(sol, "haumea", GalaxySpace.ASSET_PREFIX, 3.75F);
		BodiesRegistry.setOrbitData(planetHaumea, (float) Math.PI + 1.64F, 1.0F, 392.9F);
		BodiesRegistry.setAtmosphere(planetHaumea, false, false, false, -12.0F, 0.0F, 0.0F);
		BodiesRegistry.setPlanetData(planetHaumea, 0F, 12000, BodiesRegistry.calculateGravity(2.02F), true);
		BodiesRegistry.setProviderData(planetHaumea, WorldProviderHaumea_WE.class, GSConfigDimensions.dimensionIDHaumea, 6, ACBiome.ACSpace);
		GalaxyRegistry.registerPlanet(planetHaumea);
				
		phobosMars = BodiesRegistry.registerExMoon(MarsModule.planetMars, "phobos", GalaxySpace.ASSET_PREFIX, 8.0F);
		BodiesRegistry.setOrbitData(phobosMars, 1.0F, 0.0017F, 100F);
		BodiesRegistry.setAtmosphere(phobosMars, false, false, false, -1.2F, 0.0F, 0.0F);
		BodiesRegistry.setProviderData(phobosMars, WorldProviderPhobos.class, GSConfigDimensions.dimensionIDPhobos, 2, ACBiome.ACSpace);
		BodiesRegistry.setPlanetData(phobosMars, 0F, 12000, 0.066F, true);
		GalaxyRegistry.registerMoon(phobosMars);

					
		ioJupiter = BodiesRegistry.registerExMoon(planetJupiter, "io", GalaxySpace.ASSET_PREFIX, 10F);
		BodiesRegistry.setOrbitData(ioJupiter, 1.0F, 0.0017F, 50F);
		BodiesRegistry.setAtmosphere(ioJupiter, false, false, false, -3.2F, 0.0F, 0.0F);
		BodiesRegistry.setPlanetData(ioJupiter, 0F, 42000, 0.052F, true);
		BodiesRegistry.setProviderData(ioJupiter, WorldProviderIo.class, GSConfigDimensions.dimensionIDIo, 4, ACBiome.ACSpace, ACBiome.ACSpaceLowHills, ACBiome.ACSpaceLowPlains);
		GalaxyRegistry.registerMoon(ioJupiter);
		
		europaJupiter = BodiesRegistry.registerExMoon(planetJupiter, "europa", GalaxySpace.ASSET_PREFIX, 15F);
		BodiesRegistry.setOrbitData(europaJupiter, (float)Math.PI, 0.0017F, 100F);
		BodiesRegistry.setAtmosphere(europaJupiter, false, false, false, -3.0F, 0.0F, 0.0F);
		BodiesRegistry.setPlanetData(europaJupiter, 0.2F, 0F, 58000L, 0.062F, true);
		BodiesRegistry.setProviderData(europaJupiter, WorldProviderEuropa_WE.class, GSConfigDimensions.dimensionIDEuropa, 4);
		GalaxyRegistry.registerMoon(europaJupiter);
		
		ganymedeJupiter = BodiesRegistry.registerExMoon(planetJupiter, "ganymede", GalaxySpace.ASSET_PREFIX, 20F);
		BodiesRegistry.setOrbitData(ganymedeJupiter, (float)Math.PI / 2, 0.0017F, 150F);
		BodiesRegistry.setAtmosphere(ganymedeJupiter, false, false, false, -3.0F, 0.0F, 0.0F);
		BodiesRegistry.setPlanetData(ganymedeJupiter, 0F, 102000L, 0.057F, false);
		BodiesRegistry.setProviderData(ganymedeJupiter, WorldProviderGanymede.class, GSConfigDimensions.dimensionIDGanymede, 4);
		GalaxyRegistry.registerMoon(ganymedeJupiter);
		
		callistoJupiter = BodiesRegistry.registerExMoon(planetJupiter, "callisto", GalaxySpace.ASSET_PREFIX, 30F);
		BodiesRegistry.setOrbitData(callistoJupiter, (float)Math.PI / 3, 0.0017F, 200F);
		BodiesRegistry.setAtmosphere(callistoJupiter, false, false, false, -3.0F, 0.0F, 0.0F);
		BodiesRegistry.setPlanetData(callistoJupiter, 0F, 154000L, 0.054F, false);
		BodiesRegistry.setProviderData(callistoJupiter, WorldProviderCallisto.class, GSConfigDimensions.dimensionIDCallisto, 4);
		GalaxyRegistry.registerMoon(callistoJupiter);				
		
		enceladusSaturn = BodiesRegistry.registerExMoon(planetSaturn, "enceladus", GalaxySpace.ASSET_PREFIX, 15F);
		BodiesRegistry.setOrbitData(enceladusSaturn, (float)Math.PI / 3, 0.0017F, 50F);
		BodiesRegistry.setAtmosphere(enceladusSaturn, false, false, false, -4.0F, 0.0F, 0.0F);
		BodiesRegistry.setPlanetData(enceladusSaturn, 0F, 32000L, 0.058F, true);
		BodiesRegistry.setProviderData(enceladusSaturn, GSConfigCore.enableWorldEngine ? WorldProviderEnceladus_WE.class : WorldProviderEnceladus.class, GSConfigDimensions.dimensionIDEnceladus, 5);
		GalaxyRegistry.registerMoon(enceladusSaturn);	
		
		titanSaturn = BodiesRegistry.registerExMoon(planetSaturn, "titan", GalaxySpace.ASSET_PREFIX, 35F);
		BodiesRegistry.setClassBody(titanSaturn, ClassBody.TITAN);
		BodiesRegistry.setOrbitData(titanSaturn, (float)Math.PI / 5, 0.0017F, 280F);
		BodiesRegistry.setAtmosphere(titanSaturn, false, false, false, -4.0F, 1.0F, 0.0F);
		BodiesRegistry.setPlanetData(titanSaturn, 0F, 105500L, 0.058F, false);
		BodiesRegistry.setProviderData(titanSaturn, WorldProviderTitan.class, GSConfigDimensions.dimensionIDTitan, 5);
		titanSaturn.atmosphereComponent(EnumAtmosphericGas.NITROGEN);
		GalaxyRegistry.registerMoon(titanSaturn);		
		
		mirandaUranus = BodiesRegistry.registerExMoon(planetUranus, "miranda", GalaxySpace.ASSET_PREFIX, 10F);
		BodiesRegistry.setOrbitData(mirandaUranus, (float)Math.PI, 0.0017F, 20F);
		BodiesRegistry.setAtmosphere(mirandaUranus, false, false, false, -5.0F, 0.0F, 0.0F);
		BodiesRegistry.setPlanetData(mirandaUranus, 0F, 33500L, 0.057F, true);
		BodiesRegistry.setProviderData(mirandaUranus, WorldProviderMiranda.class, GSConfigDimensions.dimensionIDMiranda, 5, ACBiome.ACSpace, ACBiome.ACSpaceLowPlains, ACBiome.ACSpaceMidHills);
		GalaxyRegistry.registerMoon(mirandaUranus);		
		
		tritonNeptune = BodiesRegistry.registerExMoon(planetNeptune, "triton", GalaxySpace.ASSET_PREFIX, 25F);
		BodiesRegistry.setOrbitData(tritonNeptune, (float)Math.PI, 0.0017F, -200F);
		BodiesRegistry.setAtmosphere(tritonNeptune, false, false, false, -11.2F, 0.0F, 0.0F);
		BodiesRegistry.setPlanetData(tritonNeptune, 0F, 145200L, 0.059F, true);
		BodiesRegistry.setProviderData(tritonNeptune, WorldProviderTriton_WE.class, GSConfigDimensions.dimensionIDTriton, 6);
		GalaxyRegistry.registerMoon(tritonNeptune);		

				
		
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
		
		jupiterSpaceStation = new Satellite("spacestation.jupiter").setParentBody(planetJupiter);
		jupiterSpaceStation.setRelativeSize(0.2667F);
		jupiterSpaceStation.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(5.5F, 5.5F));
		jupiterSpaceStation.setRelativeOrbitTime(20.0F);
		if(GSConfigDimensions.enableJupiterSpaceStation) {
			jupiterSpaceStation.setTierRequired(VenusModule.planetVenus.getTierRequirement());
			jupiterSpaceStation.setDimensionInfo(GSConfigDimensions.idDimensionJupiterOrbit, GSConfigDimensions.idDimensionJupiterOrbitStatic, WorldProviderJupiterSS.class);
			jupiterSpaceStation.setBodyIcon(new ResourceLocation("galacticraftcore:textures/gui/celestialbodies/space_station.png"));
			jupiterSpaceStation.addChecklistKeys("equip_oxygen_suit", "equip_parachute");
			jupiterSpaceStation.setBiomeInfo(ACBiome.ACSpace);
			jupiterSpaceStation.atmosphereComponent(EnumAtmosphericGas.CO2).atmosphereComponent(EnumAtmosphericGas.NITROGEN);
			jupiterSpaceStation.setAtmosphere(new AtmosphereInfo(false, false, false, 0.8F, 0.4F, 0.0F));
		}
			
		//GalaxyRegistry.getRegisteredPlanets().remove(MarsModule.planetMars.getName());
		//GalaxyRegistry.getRegisteredPlanetIDs().remove(MarsModule.planetMars.getName());
		
		if(GalaxySpace.debug) {
			planetTest = BodiesRegistry.registerExPlanet(sol, "test_planet", GalaxySpace.ASSET_PREFIX, 5.0F);
			BodiesRegistry.setOrbitData(planetTest, (float) Math.PI + 1.64F, 1.0F, 392.9F);
			BodiesRegistry.setAtmosphere(planetTest, false, false, false, -12.0F, 0.0F, 0.0F);
			BodiesRegistry.setPlanetData(planetTest, 0F, 12000, BodiesRegistry.calculateGravity(2.02F), true);
			BodiesRegistry.setProviderData(planetTest, WorldProviderTest_WE2.class, GSConfigDimensions.dimensionIDTest, 6, ACBiome.ACSpace);
			GalaxyRegistry.registerPlanet(planetTest);
		}
		registryteleport();		
		
	}
	
	public void init(FMLInitializationEvent event)
	{
		
    	// TODO Rings ---------------------------------
		VenusModule.planetVenus.setAtmosphere(new AtmosphereInfo(false, true, true, 5.0F, 2.0F, 54.0F));
	    VenusModule.planetVenus.atmosphereComponent(EnumAtmosphericGas.CO2).atmosphereComponent(EnumAtmosphericGas.NITROGEN);
	        
		VenusModule.planetVenus.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(0.745F, 0.74F)).setRingColorRGB(0.0F, 0.4F, 0.9F);
		GalacticraftCore.satelliteSpaceStation.setRingColorRGB(0.0F, 0.4F, 0.9F);	
		GalacticraftCore.planetOverworld.setRingColorRGB(0.0F, 1.1F, 0.0F);
		GalacticraftCore.moonMoon.setRingColorRGB(0.0F, 0.4F, 0.9F);	
		MarsModule.planetMars.setRingColorRGB(0.0F, 0.4F, 0.9F);
		if(GSConfigCore.enableWorldEngine && GSConfigCore.enableMarsWorldEngine) {
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
			
			//GameRegistry.registerWorldGenerator(new OreGenerator(GSBlocks.OVERWORLD_ORES.getDefaultState().withProperty(BlockOres.BASIC_TYPE, BlockOres.EnumBlockOres.NICKEL), 6, 0, 45, 4, Blocks.STONE.getDefaultState(), 0), 1);
			GameRegistry.registerWorldGenerator(new NickelGenerator(), 1);
		}			
		GameRegistry.registerWorldGenerator(new WorldGenNaturalGas(6, 65, 45, Blocks.STONE.getDefaultState(), 10, 0), 1);
		
		// --------------------------------------------		
		/*	if(GSConfigCore.enableOresGeneration) 
		{
			// TODO Moon ----------------------------------
			GameRegistry.registerWorldGenerator(new OreGenerator(GSBlocks.OVERWORLD_ORES.getStateFromMeta(2), 4, 0, 45, 4, GCBlocks.blockMoon.getStateFromMeta(4), ConfigManagerCore.idDimensionMoon), 4);
			// --------------------------------------------
		}*/
		   	   	
    	registerDungeonLoot();
    	
    	//GalaxyRegistry.getRegisteredPlanets().forEach((s, p) -> GalaxySpace.debug("Planet: " + s  + " | " + p.getWorldProvider()));
        
    	
		SchematicRegistry.registerSchematicRecipe(new SchematicOxTank());
		
		if(GSConfigCore.enableAdvancedRocketCraft) {
			SchematicRegistry.registerSchematicRecipe(new SchematicCone());
			SchematicRegistry.registerSchematicRecipe(new SchematicBody());
			SchematicRegistry.registerSchematicRecipe(new SchematicEngine());
			SchematicRegistry.registerSchematicRecipe(new SchematicBooster());
			SchematicRegistry.registerSchematicRecipe(new SchematicFins());
			
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
        
        if(GSConfigDimensions.enableJupiterSpaceStation) {
        	GSDimensions.JUPITER_SS = GalacticraftRegistry.registerDimension("Jupiter Space Station", "_jupiter_orbit", GSConfigDimensions.idDimensionJupiterOrbit, WorldProviderJupiterSS.class, false);
        	if (GSDimensions.JUPITER_SS == null)
        	{
        		GalaxySpace.instance.debug("Failed to register space station dimension type with ID " + GSConfigDimensions.idDimensionJupiterOrbit);
        	}
        	GSDimensions.JUPITER_SS_KEEPLOADED = GalacticraftRegistry.registerDimension("Jupiter Space Station", "_jupiter_orbit", GSConfigDimensions.idDimensionJupiterOrbitStatic, WorldProviderJupiterSS.class, true);
        }
        

		BodiesData data = new BodiesData(TypeBody.STAR).setStarClass(StarClass.DWARF).setStarColor(StarColor.YELLOW);
		BodiesRegistry.registerBodyData(GalacticraftCore.solarSystemSol.getMainStar(), data);
	}
	
	private static void registerDungeonLoot()
	{	    
		//SCHEMATIC BOX
		if(GSConfigCore.enableAdvancedRocketCraft) {
			GalacticraftRegistry.addDungeonLoot(1, new ItemStack(GSItems.BASIC, 1, 10)); //MOON
			GalacticraftRegistry.addDungeonLoot(2, new ItemStack(GSItems.BASIC, 1, 10)); //MARS
		}
		//EPO
		GalacticraftRegistry.addDungeonLoot(1, new ItemStack(GSItems.SCHEMATICS, 1, 5));
	    	    
		GalacticraftRegistry.addDungeonLoot(2, new ItemStack(GSItems.PLASMA_SWORD, 1, 0));
		GalacticraftRegistry.addDungeonLoot(2, new ItemStack(GSItems.PLASMA_PICKAXE, 1, 0));
		GalacticraftRegistry.addDungeonLoot(2, new ItemStack(GSItems.PLASMA_AXE, 1, 0));
		
		GalacticraftRegistry.addDungeonLoot(3, new ItemStack(GSItems.PLASMA_SWORD, 1, 0));
		GalacticraftRegistry.addDungeonLoot(3, new ItemStack(GSItems.PLASMA_PICKAXE, 1, 0));
		GalacticraftRegistry.addDungeonLoot(3, new ItemStack(GSItems.PLASMA_AXE, 1, 0));
		
	    GalacticraftRegistry.addDungeonLoot(4, new ItemStack(GSItems.BASIC, 1, 16));
	    GalacticraftRegistry.addDungeonLoot(4, new ItemStack(GSItems.BASIC, 1, 18));
	    
	    //GalacticraftRegistry.addDungeonLoot(4, new ItemStack(GSItems.GEOLOGICAL_SCANNER, 1, 100));
	    
	    GalacticraftRegistry.addDungeonLoot(5, new ItemStack(GSItems.ROCKET_MODULES, 1, 4));
	    GalacticraftRegistry.addDungeonLoot(5, new ItemStack(GSItems.ROCKET_MODULES, 1, 6));
	    
	}
	
	private static void registrycelestial()
	{

		BodiesData data = new BodiesData(null, /*BodiesRegistry.calculateGravity(8.88F),*/ 92, /*182000,*/ false);
		BodiesRegistry.registerBodyData(VenusModule.planetVenus, data);
		
		data = new BodiesData(null, /*BodiesRegistry.calculateGravity(10.0F),*/ 1, /*24000,*/false);
		BodiesRegistry.registerBodyData(GalacticraftCore.planetOverworld, data);		
		
		data = new BodiesData(null, /*BodiesRegistry.calculateGravity(5.37F),*/ 0, /*24660,*/ false);
		BodiesRegistry.registerBodyData(MarsModule.planetMars, data);
		    	
		data = new BodiesData(TypeBody.ASTEROID, ClassBody.ASTEROID, 0, true);
		BodiesRegistry.registerBodyData(AsteroidsModule.planetAsteroids, data);
    	
	   	////MOONS
	   	data = new BodiesData(TypeBody.MOON, null, 0, GSConfigCore.enableSolarRadiationOnMoon);
		BodiesRegistry.registerBodyData(GalacticraftCore.moonMoon, data);
		
		data = new BodiesData(TypeBody.MOON);
		BodiesRegistry.registerBodyData(phobosMars, data);
		
		if(GSConfigCore.enableUnreachable) {
			BodiesData unreachableData = new BodiesData(TypeBody.MOON);	
			BodiesRegistry.registerBodyData(oberonUranus, unreachableData);
			BodiesRegistry.registerBodyData(proteusNeptune, unreachableData);
			BodiesRegistry.registerBodyData(mimasSaturn, unreachableData); 	
			BodiesRegistry.registerBodyData(tethysSaturn, unreachableData); 	
			BodiesRegistry.registerBodyData(dioneSaturn, unreachableData); 	
			BodiesRegistry.registerBodyData(rheyaSaturn, unreachableData); 	
			BodiesRegistry.registerBodyData(iapetusSaturn, unreachableData); 	
			BodiesRegistry.registerBodyData(arielUranus, unreachableData); 	
			BodiesRegistry.registerBodyData(umbrielUranus, unreachableData); 	
			BodiesRegistry.registerBodyData(titaniaUranus, unreachableData); 	
			BodiesRegistry.registerBodyData(charonPluto, unreachableData); 
		}
			
	}
	
	private static void registryteleport()
	{
	
		GalacticraftRegistry.registerTeleportType(WorldProviderMercury.class, new TeleportTypeMercury());	
		
		if(GSConfigCore.enableWorldEngine && GSConfigCore.enableMarsWorldEngine)
			GalacticraftRegistry.registerTeleportType(WorldProviderMars_WE.class, new TeleportTypeMars());
		
		
		GalacticraftRegistry.registerTeleportType(WorldProviderCeres.class, new TeleportTypeCeres());
		GalacticraftRegistry.registerTeleportType(WorldProviderPluto.class, new TeleportTypePluto());
		GalacticraftRegistry.registerTeleportType(WorldProviderKuiperBelt.class, new TeleportTypeKuiperBelt());
		GalacticraftRegistry.registerTeleportType(WorldProviderHaumea_WE.class, new TeleportTypeHaumea());
		
		GalacticraftRegistry.registerTeleportType(WorldProviderTest_WE2.class, new TeleportTypeBody());
		/*GalacticraftRegistry.registerTeleportType(WorldProviderMakemake.class, new TeleportTypeMakemake());*/
		//GalacticraftRegistry.registerTeleportType(WorldProviderEris.class, new TeleportTypeEris());
		
		//if(GalaxySpace.debug) GalacticraftRegistry.registerTeleportType(WorldProviderZTest.class, new WorldProviderZTest());
		
		GalacticraftRegistry.registerTeleportType(WorldProviderPhobos.class, new TeleportTypeBody());
		//GalacticraftRegistry.registerTeleportType(WorldProviderDeimos.class, new WorldProviderDeimos());
		
		GalacticraftRegistry.registerTeleportType(WorldProviderIo.class, new TeleportTypeIo());		
		GalacticraftRegistry.registerTeleportType(WorldProviderEuropa_WE.class, new TeleportTypeEuropa());	
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
		if(GSConfigDimensions.enableJupiterSpaceStation)
		{
			GalaxyRegistry.registerSatellite(jupiterSpaceStation);
			
			GalacticraftRegistry.registerTeleportType(WorldProviderJupiterSS.class, new TeleportTypeJupiterSS());
		}
		
	}
	
	public void postInit(FMLPostInitializationEvent event)
	{
		
		if(GSConfigCore.enableUnreachable) {
			
			//MARS
			deimosMars = registerDummyMoon("deimos", MarsModule.planetMars,  16.0F);
			if(deimosMars != null)
				BodiesRegistry.setOrbitData(deimosMars, 1.0F, 0.0017F, 300F);
			
			//SATURN MOONS
			mimasSaturn = registerDummyMoon("mimas", planetSaturn, 10F);
			if(mimasSaturn != null)
				BodiesRegistry.setOrbitData(mimasSaturn, (float) Math.PI / 2, 0.0017F, 20F);
			
			tethysSaturn = registerDummyMoon("tethys", planetSaturn, 20F);
			if(tethysSaturn != null)
				BodiesRegistry.setOrbitData(tethysSaturn, (float) Math.PI, 0.0017F, 120F);			
			
			dioneSaturn = registerDummyMoon("dione", planetSaturn, 25F);
			if(dioneSaturn != null)
				BodiesRegistry.setOrbitData(dioneSaturn, (float) Math.PI / 4, 0.0017F, 180F);
	
			rheyaSaturn = registerDummyMoon("rheya", planetSaturn, 30F);
			if(rheyaSaturn != null)
				BodiesRegistry.setOrbitData(rheyaSaturn, (float) Math.PI / 3, 0.0017F, 220F);			

			iapetusSaturn = registerDummyMoon("iapetus", planetSaturn, 40F);
			if(iapetusSaturn != null)
				BodiesRegistry.setOrbitData(iapetusSaturn, (float) Math.PI, 0.0017F, 350F);	
				
			//URANUS
			arielUranus = registerDummyMoon("ariel", planetUranus, 15F);
			if(arielUranus != null)
				BodiesRegistry.setOrbitData(arielUranus, (float) Math.PI / 2, 0.0017F, 50F);
			
			umbrielUranus = registerDummyMoon("umbriel", planetUranus, 20F);
			if(umbrielUranus != null)
				BodiesRegistry.setOrbitData(umbrielUranus, (float) Math.PI / 3, 0.0017F, 120F);
			
			titaniaUranus = registerDummyMoon("titania", planetUranus, 25F);
			if(titaniaUranus != null)
				BodiesRegistry.setOrbitData(titaniaUranus, (float) Math.PI / 4, 0.0017F, 180F);
			
			oberonUranus = registerDummyMoon("oberon", planetUranus,  30F);
			if(oberonUranus != null)
				BodiesRegistry.setOrbitData(oberonUranus, (float) Math.PI / 4, 0.0017F, 220F);
			
			proteusNeptune = registerDummyMoon("proteus", planetNeptune, 10F);
			if(proteusNeptune != null)
				BodiesRegistry.setOrbitData(proteusNeptune, (float) Math.PI, 0.0017F, 50F);
			
			//PLUTO
			charonPluto = registerDummyMoon("charon", planetPluto, 15F);
			if(charonPluto != null)						
				BodiesRegistry.setOrbitData(charonPluto, (float) Math.PI, 0.0017F, 50F);
			
		}
		
		ItemSchematics.registerSchematicItems();
		
		GSDimensions.MERCURY = WorldUtil.getDimensionTypeById(GSConfigDimensions.dimensionIDMercury);
		GSDimensions.CERES = WorldUtil.getDimensionTypeById(GSConfigDimensions.dimensionIDCeres);
		GSDimensions.PLUTO = WorldUtil.getDimensionTypeById(GSConfigDimensions.dimensionIDPluto);
		
		GSDimensions.PHOBOS = WorldUtil.getDimensionTypeById(GSConfigDimensions.dimensionIDPhobos);
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
		
		GSDimensions.TEST = WorldUtil.getDimensionTypeById(GSConfigDimensions.dimensionIDTest);
		
		
		if(event.getSide() == Side.CLIENT) {
			for(Entry<String, Planet> bodies : GalaxyRegistry.getRegisteredPlanets().entrySet())
			{
				ResourceLocation img = new ResourceLocation(GalaxySpace.TEXTURE_PREFIX + "textures/gui/backgrounds/bg_" + bodies.getValue().getName() + ".png");
				
				try {
					if(Minecraft.getMinecraft().getResourceManager().getResource(img) != null)
					{
						BodiesRegistry.addTeleportData(bodies.getValue().getDimensionID(), img);
					}
				} catch (IOException e) {}
				
			}
			
			for(Entry<String, Moon> bodies : GalaxyRegistry.getRegisteredMoons().entrySet())
			{
				ResourceLocation img = new ResourceLocation(GalaxySpace.TEXTURE_PREFIX + "textures/gui/backgrounds/bg_" + bodies.getValue().getName() + ".png");
				
				try {
					if(Minecraft.getMinecraft().getResourceManager().getResource(img) != null)
					{
						BodiesRegistry.addTeleportData(bodies.getValue().getDimensionID(), img);
					}
				} catch (IOException e) {}
				
			}
		}

		registrycelestial(); 
		GalaxyRegistry.refreshGalaxies();
	}
	
	
	private ExMoon registerDummyMoon(String name, Planet parent, float distancefromcenter) {

		for (CelestialBody body : new ArrayList<>(GalaxyRegistry.getRegisteredMoons().values())) {
			if (body instanceof Moon && name.equals(body.getName())) {
				if (((Moon) body).getParentPlanet() == parent) {
					return null;
				}
			}
		}

		ExMoon moon =  BodiesRegistry.registerExMoon(parent, name, GalaxySpace.ASSET_PREFIX, distancefromcenter);
		GalaxyRegistry.registerMoon(moon);
		
		return moon;
	}
	
	private static void registerRecipesWorkBench()
    {
    	SchematicConeRecipe.registerRecipeWorkBench();
    	SchematicBodyRecipe.registerRecipeWorkBench();
    	SchematicEngineRecipe.registerRecipeWorkBench();
    	SchematicBoosterRecipe.registerRecipeWorkBench();
    	SchematicFinsRecipe.registerRecipeWorkBench();
    	SchematicOxTankRecipe.registerRecipeWorkBench();
    	if(GSConfigCore.enableAdvancedRocketCraft)
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
    	CraftingRecipesPhobos.loadRecipes();
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
		
		spaceStationRequirements.clear();
		spaceStationRequirements.put("ingotTin", 32);
		spaceStationRequirements.put("ingotCopper", 64);
		spaceStationRequirements.put(new ItemStack(GCItems.basicItem, 1, ItemBasic.WAFER_ADVANCED), 1);
		spaceStationRequirements.put(Items.IRON_INGOT, 24);
		spaceStationRequirements.put(new ItemStack(GSItems.HDP, 1, 0), 10);
		spaceStationRequirements.put(new ItemStack(GSItems.BASIC, 1, 6), 10);
		
		if(GSConfigDimensions.enableJupiterSpaceStation)
			GalacticraftRegistry.registerSpaceStation(new SpaceStationType(GSConfigDimensions.idDimensionJupiterOrbit,
					GSConfigDimensions.dimensionIDJupiter, new SpaceStationRecipe(spaceStationRequirements)));
    	 
	}

	@Override
	public boolean canRegister() {
		return true;
	}
	
	@Override
	public int getPriority() { return 100; }
	

}
