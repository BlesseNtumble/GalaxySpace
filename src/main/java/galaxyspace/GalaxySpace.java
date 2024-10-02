package galaxyspace;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.discovery.ASMDataTable.ASMData;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.FMLRelaunchLog;
import cpw.mods.fml.relauncher.Side;
import galaxyspace.api.IBodiesHandler;
import galaxyspace.api.IBookPage;
import galaxyspace.api.IPage;
import galaxyspace.core.achievements.AchEvent;
import galaxyspace.core.achievements.AchievementList;
import galaxyspace.core.configs.GSConfigBiomes;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.configs.GSConfigDimensions;
import galaxyspace.core.configs.GSConfigEnergy;
import galaxyspace.core.configs.GSConfigSchematics;
import galaxyspace.core.events.GSEventHandler;
import galaxyspace.core.handler.GSFuelHandler;
import galaxyspace.core.handler.GSGuiHandler;
import galaxyspace.core.handler.GSOreGenOtherMods;
import galaxyspace.core.handler.GSPlanetFogHandler;
import galaxyspace.core.integration.minetweaker.GSMinetweakerConfig;
import galaxyspace.core.network.packet.GalaxySpaceChannelHandler;
import galaxyspace.core.prefab.entity.EntityEntryPod;
import galaxyspace.core.prefab.entity.EntityIceSpike;
import galaxyspace.core.prefab.schematics.SchematicAdd;
import galaxyspace.core.proxy.CommonProxy;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.registers.fluids.GSFluids;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.registers.potions.GSPotions;
import galaxyspace.core.util.BookRegister;
import galaxyspace.core.util.BookUtils;
import galaxyspace.core.util.BookUtils.Book_Cateroies;
import galaxyspace.core.util.GSCreativeTabs;
import galaxyspace.core.util.GSThreadVersionCheck;
import galaxyspace.systems.ACentauriSystem.ACentauriSystemBodies;
import galaxyspace.systems.BarnardsSystem.BarnardsSystemBodies;
import galaxyspace.systems.SolarSystem.SolarSystemBodies;
import galaxyspace.systems.SolarSystem.moons.enceladus.tile.TileEntityBlockCrystallTE;
import galaxyspace.systems.SolarSystem.moons.europa.entities.EntityEvolvedColdBlaze;
import galaxyspace.systems.SolarSystem.moons.io.entities.EntityBossGhast;
import galaxyspace.systems.SolarSystem.moons.io.tile.TileEntityDungeonSpawnerIo;
import galaxyspace.systems.SolarSystem.moons.io.tile.TileEntityIoTreasureChest;
import galaxyspace.systems.SolarSystem.planets.ceres.entities.EntityBossBlaze;
import galaxyspace.systems.SolarSystem.planets.ceres.tile.TileEntityCeresTreasureChest;
import galaxyspace.systems.SolarSystem.planets.ceres.tile.TileEntityDungeonSpawnerCeres;
import galaxyspace.systems.SolarSystem.planets.overworld.entities.EntityCargoFluidRocket;
import galaxyspace.systems.SolarSystem.planets.overworld.entities.EntityTier4Rocket;
import galaxyspace.systems.SolarSystem.planets.overworld.entities.EntityTier5Rocket;
import galaxyspace.systems.SolarSystem.planets.overworld.entities.EntityTier6Rocket;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityAdvFuelLoader;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityAdvLandingPad;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityAdvLandingPadSingle;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityAdvOxygenSealer;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityAssemblyMachine;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityCargoFluidLoader;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityConverterSurface;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityFluidTank;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityFuelGenerator;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityGravitationModule;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityHydroponicBase;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityHydroponicFarm;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityLiquidExtractor;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityLiquidSeparator;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityModificationTable;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityOxStorageModule;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityOxygenFiller;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityPortableNuclearReactor;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityRadiationStabiliser;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityRecycler;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityRocketAssemblyMachine;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntitySolarPanel;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntitySolarWind;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityStorageModule;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityWindTurbine;
import galaxyspace.systems.SolarSystem.planets.venus.entities.EntityEvolvedFireBlaze;
import galaxyspace.systems.SolarSystem.planets.venus.entities.EntityEvolvedFireCreeper;
import galaxyspace.systems.SolarSystem.planets.venus.entities.EntityEvolvedFireSkeleton;
import galaxyspace.systems.SolarSystem.planets.venus.entities.EntityEvolvedFireSpider;
import galaxyspace.systems.TCetiSystem.TauCetiSystemBodies;
import galaxyspace.systems.VegaSystem.VegaSystemBodies;
import micdoodle8.mods.galacticraft.api.recipe.SchematicRegistry;
import micdoodle8.mods.galacticraft.core.util.CreativeTabGC;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.planets.asteroids.items.AsteroidsItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;

 

@Mod(
		   modid = GalaxySpace.MODID,
		   version = GalaxySpace.VERSION,
		   dependencies = "required-after:GalacticraftCore@[3.0.12,); required-after:GalacticraftMars;",
		   name = GalaxySpace.MODID
		)

public class GalaxySpace

{

	public static final int major_version = 1;
	public static final int minor_version = 2;
	public static final int build_version = 15;
	
    public static final String MODID = "GalaxySpace";
    public static final String VERSION = major_version + "." + minor_version + "." + build_version + " Pre-Final";
    public static final String ASSET_PREFIX = MODID.toLowerCase();

    public static boolean debug;
    
    public static GalaxySpaceChannelHandler packetPipeline;
    //---------------------------------------------
    
    @Instance("GalaxySpace")
    public static GalaxySpace instance;

    @SidedProxy(clientSide="galaxyspace.core.proxy.ClientProxy", serverSide="galaxyspace.core.proxy.CommonProxy")
    public static CommonProxy proxy;

	public static List<IBodiesHandler> bodies = new ArrayList<IBodiesHandler>();
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) 
    {    	
    	new GSConfigDimensions(new File(event.getModConfigurationDirectory(), "GalaxySpace/dimensions.conf"));
    	new GSConfigBiomes(new File(event.getModConfigurationDirectory(), "GalaxySpace/biomes.conf"));
    	new GSConfigSchematics(new File(event.getModConfigurationDirectory(), "GalaxySpace/schematics.conf"));
    	new GSConfigCore(new File(event.getModConfigurationDirectory(), "GalaxySpace/core.conf"));
    	new GSConfigEnergy(new File(event.getModConfigurationDirectory(), "GalaxySpace/energy.conf"));    	
    	
    	this.initModInfo(event.getModMetadata());
    	
    	bodies.add(new SolarSystemBodies());
    	bodies.add(new ACentauriSystemBodies());
    	bodies.add(new BarnardsSystemBodies());
    	if(GSConfigCore.enableOldSystems) {
    		bodies.add(new TauCetiSystemBodies());
    		bodies.add(new VegaSystemBodies());
    	}
    	
    	debug = GSConfigCore.enableDebug;
    	 
    	GSBlocks.initialize();
    	GSItems.initialize();
    	GSPotions.initialize();
    	GSFluids.initialize();
    	
		proxy.preload();
						
		GameRegistry.registerFuelHandler(new GSFuelHandler());
		SchematicRegistry.registerSchematicRecipe(new SchematicAdd());
		// --------------------------------
		
		proxy.register_event(new GSEventHandler());
		proxy.register_event(new GSPlanetFogHandler());
		
		for(IBodiesHandler list : bodies)
    		list.preInit(event);
						
		if(event.getSide() == Side.CLIENT)
			for (ASMData data : event.getAsmData().getAll(IPage.class.getName())) {
				IBookPage page;
				try {
					page = (IBookPage) Class.forName(data.getClassName()).newInstance();				
					String category = page.getCategory() == null ? Book_Cateroies.GENERAL.getName() : page.getCategory();
					BookUtils.addGuideBookPage(category, page);
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		
	}

    @EventHandler
    public void init(FMLInitializationEvent event)
    {  
    	proxy.load();    	
    	proxy.register_event(new GSOreGenOtherMods());
    	proxy.register_event(new AchEvent());
    	
    	this.packetPipeline = GalaxySpaceChannelHandler.init();

    	this.registerEntities();
		AchievementList.load();
		
        // TODO Register Planets: -------------------------------
   		
       	GSCreativeTabs.GSBlocksTab = new CreativeTabGC(CreativeTabs.getNextID(), "AddonsBlocks", Item.getItemFromBlock(GSBlocks.AssemblyMachine), 0);
        GSCreativeTabs.GSItemsTab = new CreativeTabGC(CreativeTabs.getNextID(), "AddonsItems", GSItems.RocketParts, 0);
        GSCreativeTabs.GSArmorTab = new CreativeTabGC(CreativeTabs.getNextID(), "AddonsArmor", GSItems.JetPack, 0);
        
        if(!GSConfigCore.registeredRocket.isEmpty())
        	GSCreativeTabs.GSRocketTab = new CreativeTabGC(CreativeTabs.getNextID(), "AddonsRocket", GSConfigCore.getRegisterRocket(4) ? GSItems.Tier4Rocket : AsteroidsItems.tier3Rocket, 0);
              
        for(IBodiesHandler list : bodies)
    		list.init(event);
        
        BookRegister.registerCatergories();        
    }
	
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    	proxy.postload();
    	
    	
    	NetworkRegistry.INSTANCE.registerGuiHandler(GalaxySpace.instance, new GSGuiHandler()); 
    	GSThreadVersionCheck.startCheck();
    	

    	for(IBodiesHandler list : bodies)
    		list.postInit(event);
    	if(Loader.isModLoaded("MineTweaker3")) GSMinetweakerConfig.loadConfig();
    	
    	//GSBookComponent.load();
    	for(String string : GSConfigCore.protect_armor)
		{
    		String[] itemInfo = string.split(":");
			String prefix = itemInfo.length == 1 ? "minecraft" : itemInfo[0];
			Item item = GameRegistry.findItem(prefix, itemInfo[1]);
			
			if(item == null) 
			{
				GalaxySpace.debug("Warning! Item: \"" + string + "\" not found!");
				//break;
			}
		}
    	
    }
    
	private void registerEntities()
    {
        this.registerCreatures();
        this.registerNonMobEntities();
        this.registerTileEntities();
    }
    
    public void registerCreatures()
	{
    	int back = -16777216;
    	int fore = -8388608;
    	GCCoreUtil.registerGalacticraftCreature(EntityBossBlaze.class, "EvolvedBossBlaze", back, fore);
    	GCCoreUtil.registerGalacticraftCreature(EntityEvolvedFireCreeper.class, "EvolvedFireCreeper", back, fore);
    	GCCoreUtil.registerGalacticraftCreature(EntityEvolvedFireSkeleton.class, "EvolvedFireSkeleton", back, fore);
    	GCCoreUtil.registerGalacticraftCreature(EntityEvolvedFireSpider.class, "EvolvedFireSpider", back, fore);
    	GCCoreUtil.registerGalacticraftCreature(EntityEvolvedFireBlaze.class, "EvolvedBlaze", back, fore);
    	GCCoreUtil.registerGalacticraftCreature(EntityEvolvedColdBlaze.class, "EvolvedColdBlaze", back, fore);
    	GCCoreUtil.registerGalacticraftCreature(EntityBossGhast.class, "EvolvedBossGhast", back, fore);
    	    	
	}

    private void registerNonMobEntities()
    {
    	if(GSConfigCore.getRegisterRocket(4)) 
    		GCCoreUtil.registerGalacticraftNonMobEntity(EntityTier4Rocket.class, "SpaceshipT4", 150, 1, false);
    	if(GSConfigCore.getRegisterRocket(5)) 
    		GCCoreUtil.registerGalacticraftNonMobEntity(EntityTier5Rocket.class, "SpaceshipT5", 150, 1, false);
    	if(GSConfigCore.getRegisterRocket(6)) 
    		GCCoreUtil.registerGalacticraftNonMobEntity(EntityTier6Rocket.class, "SpaceshipT6", 150, 1, false);
    	
    	GCCoreUtil.registerGalacticraftNonMobEntity(EntityCargoFluidRocket.class, "Cargo Fluid Spaceship", 150, 1, false);
    	
    	GCCoreUtil.registerGalacticraftNonMobEntity(EntityEntryPod.class, "FlameEntryPod", 150, 1, true);
    	GCCoreUtil.registerGalacticraftNonMobEntity(EntityIceSpike.class, "IceSpike", 40, 100, true);
    	//GCCoreUtil.registerGalacticraftNonMobEntity(GSEntityMeteor.class, "GS Meteor", 150, 5, true);    	
    }

    private void registerTileEntities()
    {
    	GameRegistry.registerTileEntity(TileEntityAssemblyMachine.class, "GS Assembly Machine");
    	GameRegistry.registerTileEntity(TileEntityFuelGenerator.class, "GS Fuel Generator");
    	GameRegistry.registerTileEntity(TileEntityConverterSurface.class, "GS Converter Surface");
    	GameRegistry.registerTileEntity(TileEntitySolarWind.class, "GS Solar Wind Panel");
    	GameRegistry.registerTileEntity(TileEntityStorageModule.class, "GS Storage Module");
    	GameRegistry.registerTileEntity(TileEntityOxStorageModule.class, "GS Oxygen Storage Module");
    	GameRegistry.registerTileEntity(TileEntitySolarPanel.class, "GS Solar Panel");
    	GameRegistry.registerTileEntity(TileEntityGravitationModule.class, "GS Gravitation Module");
    	GameRegistry.registerTileEntity(TileEntityWindTurbine.class, "GS Wind Turbine");
    	GameRegistry.registerTileEntity(TileEntityAdvOxygenSealer.class, "GS Oxygen Sealer");
    	GameRegistry.registerTileEntity(TileEntityAdvFuelLoader.class, "GS Fuel Loader");
    	GameRegistry.registerTileEntity(TileEntityRocketAssemblyMachine.class, "GS Rocket Assembly");
    	GameRegistry.registerTileEntity(TileEntityRecycler.class, "GS Recycler");
    	GameRegistry.registerTileEntity(TileEntityLiquidExtractor.class, "GS Liquid Extractor");
    	GameRegistry.registerTileEntity(TileEntityPortableNuclearReactor.class, "GS Thermodynamic Reactor");
    	GameRegistry.registerTileEntity(TileEntityFluidTank.class, "GS Fluid Tank");
    	GameRegistry.registerTileEntity(TileEntityBlockCrystallTE.class, "GS Crystal");
        GameRegistry.registerTileEntity(TileEntityAdvLandingPadSingle.class, "GS Adv Landing Pad");
        GameRegistry.registerTileEntity(TileEntityAdvLandingPad.class, "GS Adv Landing Pad Full");
        GameRegistry.registerTileEntity(TileEntityCargoFluidLoader.class, "GS Cargo Fluid Loader");
        GameRegistry.registerTileEntity(TileEntityHydroponicBase.class, "GS Gydroponic Base");
        GameRegistry.registerTileEntity(TileEntityHydroponicFarm.class, "GS Gydroponic Farm");
        GameRegistry.registerTileEntity(TileEntityLiquidSeparator.class, "GS Liquid Separator");
        GameRegistry.registerTileEntity(TileEntityRadiationStabiliser.class, "GS Radiation Stabiliser");
        GameRegistry.registerTileEntity(TileEntityModificationTable.class, "GS Modification Table");
        GameRegistry.registerTileEntity(TileEntityOxygenFiller.class, "GS Oxygen Filler");
        
        GameRegistry.registerTileEntity(TileEntityCeresTreasureChest.class, "GS Tier 4 Treasure Chest");
        GameRegistry.registerTileEntity(TileEntityDungeonSpawnerCeres.class, "GS Ceres Dungeon Spawner");
        GameRegistry.registerTileEntity(TileEntityIoTreasureChest.class, "GS Tier 5 Treasure Chest");
        GameRegistry.registerTileEntity(TileEntityDungeonSpawnerIo.class, "GS Io Dungeon Spawner");
    }

  	public static void info(String message)
	{ 
		FMLRelaunchLog.log("Galaxy Space", Level.INFO, message);
	}
    
    public static void debug(String message)
   	{ 
   		if(debug) FMLRelaunchLog.log("[DEBUG] Galaxy Space", Level.INFO, message);
   	}    
    
    private void initModInfo(ModMetadata info)
    {
        info.autogenerated = false;
        info.modId = MODID;
        info.name = MODID;
        info.version = VERSION;
        info.description = "Global addon for Galacticraft 3.";
        info.authorList = Arrays.asList("ViTold Dev Team");
    }
}
