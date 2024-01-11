package galaxyspace;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import asmodeuscore.AsmodeusCore;
import asmodeuscore.api.IBodies;
import asmodeuscore.api.space.IBookPage;
import asmodeuscore.core.astronomy.BodiesRegistry;
import asmodeuscore.core.utils.BookUtils;
import galaxyspace.api.IPage;
import galaxyspace.core.GSBlocks;
import galaxyspace.core.GSFluids;
import galaxyspace.core.GSItems;
import galaxyspace.core.GSPotions;
import galaxyspace.core.client.gui.book.BookRegister;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.configs.GSConfigDimensions;
import galaxyspace.core.configs.GSConfigEnergy;
import galaxyspace.core.configs.GSConfigSchematics;
import galaxyspace.core.events.GSEventHandler;
import galaxyspace.core.handler.GSGuiHandler;
import galaxyspace.core.handler.capabilities.GSCapabilityStatsHandler;
import galaxyspace.core.network.packet.GalaxySpaceChannelHandler;
import galaxyspace.core.prefab.entities.EntityAstroWolf;
import galaxyspace.core.prefab.entities.EntityCustomCargoRocket;
import galaxyspace.core.prefab.entities.EntityEvolvedColdBlaze;
import galaxyspace.core.prefab.entities.EntityIceSpike;
import galaxyspace.core.prefab.entities.EntityLaserBeam;
import galaxyspace.core.prefab.entities.EntityMultiSeatRocketTest;
import galaxyspace.core.prefab.entities.EntityTier4Rocket;
import galaxyspace.core.prefab.entities.EntityTier5Rocket;
import galaxyspace.core.prefab.entities.EntityTier6Rocket;
import galaxyspace.core.proxy.CommonProxy;
import galaxyspace.core.util.GSCreativeTabs;
import galaxyspace.core.util.GSThreadVersionCheck;
import galaxyspace.core.util.researches.ResearchUtil;
import galaxyspace.systems.SolarSystem.moons.enceladus.tile.TileEntityBlockCrystallTE;
import galaxyspace.systems.SolarSystem.moons.io.entities.EntityBossGhast;
import galaxyspace.systems.SolarSystem.moons.io.tile.TileEntityDungeonSpawnerIo;
import galaxyspace.systems.SolarSystem.moons.io.tile.TileEntityTreasureChestIo;
import galaxyspace.systems.SolarSystem.planets.ceres.entities.EntityBossBlaze;
import galaxyspace.systems.SolarSystem.planets.ceres.tile.TileEntityDungeonSpawnerCeres;
import galaxyspace.systems.SolarSystem.planets.ceres.tile.TileEntityTreasureChestCeres;
import galaxyspace.systems.SolarSystem.planets.mars.entities.EntityMarsRover;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityAdvCircuitFabricator;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityAdvLandingPad;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityAdvLandingPadSingle;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityAdvOxygenStorageModule;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityAssembler;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityEnergyPad;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityFuelGenerator;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityGasBurner;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityGasCollector;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityGasGenerator;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityGravitationModule;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityHydroponicBase;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityHydroponicFarm;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityLiquidExtractor;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityLiquidSeparator;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityModernSolarPanel;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityModernStorageModule;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityModificationTable;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityPanelController;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityPlanetShield;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityRadiationStabiliser;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityResearchTable;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityRocketAssembler;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityUniversalRecycler;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityWindGenerator;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityWindSolarPanel;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.util.CreativeTabGC;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.discovery.ASMDataTable.ASMData;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(
		   modid = GalaxySpace.MODID,
		   version = GalaxySpace.VERSION,
		   dependencies = Constants.DEPENDENCIES_FORGE + "required-after:galacticraftcore@[4.0.2.282,); required-after:galacticraftplanets; required-after:asmodeuscore@[1.0.0,)",
		   acceptedMinecraftVersions = "[1.12.2]",
		   name = GalaxySpace.NAME,
		   guiFactory = "galaxyspace.core.client.gui.GSConfigGuiFactory"
		)
public class GalaxySpace
{
	public static final int major_version = 2;
	public static final int minor_version = 1;
	public static final int build_version = 2;
	
	public static final String NAME = "GalaxySpace";
	public static final String MODID = "galaxyspace";
    public static final String VERSION = major_version + "." + minor_version + "." + build_version;
    public static final String ASSET_PREFIX = MODID;
    public static final String TEXTURE_PREFIX = ASSET_PREFIX + ":";

    public static boolean debug;
    public static GalaxySpaceChannelHandler packetPipeline;
    private static Logger log;
    //---------------------------------------------
    
    @Instance(GalaxySpace.MODID)
    public static GalaxySpace instance;
    
    @SidedProxy(clientSide="galaxyspace.core.proxy.ClientProxy", serverSide="galaxyspace.core.proxy.CommonProxy")
    public static CommonProxy proxy;

    //public static List<IBodies> bodies = new ArrayList<IBodies>();
    
    private List<IBookPage> pages = new ArrayList<IBookPage>();
    
    static {    	
        FluidRegistry.enableUniversalBucket();
    }
   
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) 
    {    	
    	new GSConfigDimensions(new File(event.getModConfigurationDirectory(), "GalaxySpace/dimensions.conf"));
    	new GSConfigSchematics(new File(event.getModConfigurationDirectory(), "GalaxySpace/schematics.conf"));
    	new GSConfigCore(new File(event.getModConfigurationDirectory(), "GalaxySpace/core.conf"));
    	new GSConfigEnergy(new File(event.getModConfigurationDirectory(), "GalaxySpace/energy.conf"));
    	
    	this.initModInfo(event.getModMetadata());
    	
    	debug = GSConfigCore.enableDebug;
    	log = event.getModLog();
    	 
    	BodiesRegistry.setMaxTier(6);
    	
    	GSBlocks.initialize();
    	GSFluids.initialize(); 
    	GSItems.initialize();
    	GSPotions.initialize();

    	proxy.preload(event);		
    	proxy.register_event(new GSEventHandler());		

    	for(IBodies list : AsmodeusCore.bodies)
		{
			list.preInitialization(event);
			
    		if(list.canRegister()) 
    			list.preInit(event);
		}
    	
		if(event.getSide() == Side.CLIENT)
	    	for (ASMData data : event.getAsmData().getAll(IPage.class.getName())) {
				IBookPage page;
				try {				
					page = (IBookPage) Class.forName(data.getClassName()).newInstance();	
					pages.add(page);
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		GSCapabilityStatsHandler.register();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {  
    	
    	proxy.registerRender();    	
    	proxy.load();    	
    	//proxy.register_event(new GSOreGenOtherMods());
    	//proxy.register_event(new AchEvent());
    	
    	this.packetPipeline = GalaxySpaceChannelHandler.init();
    	this.registerEntities();
    	ResearchUtil.initResearches();
		//AchievementList.load();

        // TODO Register Planets: -------------------------------
    	for(IBodies list : AsmodeusCore.bodies)
    		if(list.canRegister()) 
    			list.init(event);
    	
     	    	
    	if(event.getSide() == Side.CLIENT) {
    		BookRegister.registerCatergories();
	    	pages.forEach(BookUtils::addGuideBookPage);
    	}
    	
    	GSCreativeTabs.GSBlocksTab = new CreativeTabGC(CreativeTabs.getNextID(), "galaxyspace_blocks", new ItemStack(GSBlocks.ASSEMBLER), null);
        GSCreativeTabs.GSItemsTab = new CreativeTabGC(CreativeTabs.getNextID(), "galaxyspace_items", new ItemStack(GSItems.INGOTS), null);
        GSCreativeTabs.GSArmorTab = new CreativeTabGC(CreativeTabs.getNextID(), "galaxyspace_armor", new ItemStack(GSItems.SPACE_SUIT_BODY), null);
        GSCreativeTabs.GSVehiclesTab = new CreativeTabGC(CreativeTabs.getNextID(), "galaxyspace_rocket", new ItemStack(GSItems.ROCKET_TIER_4), null);
    }
	
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	proxy.postload();
    	

    	for(IBodies list : AsmodeusCore.bodies)
    		if(list.canRegister()) 
    			list.postInit(event);
    	
    	NetworkRegistry.INSTANCE.registerGuiHandler(GalaxySpace.instance, new GSGuiHandler());    
    	GSThreadVersionCheck.startCheck();     	
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
    	GCCoreUtil.registerGalacticraftCreature(EntityBossBlaze.class, "evolved_boss_blaze", back, fore);
    	GCCoreUtil.registerGalacticraftCreature(EntityEvolvedColdBlaze.class, "evolved_coldblaze", back, fore);
    	GCCoreUtil.registerGalacticraftCreature(EntityBossGhast.class, "evolved_boss_ghast", back, fore);
    	GCCoreUtil.registerGalacticraftCreature(EntityAstroWolf.class, "astro_wolf", back, fore);
    	//GCCoreUtil.registerGalacticraftCreature(EntityEvolvedFireCreeper.class, "EvolvedFireCreeper", back, fore);
    	//GCCoreUtil.registerGalacticraftCreature(EntityEvolvedFireSkeleton.class, "EvolvedFireSkeleton", back, fore);
    	//GCCoreUtil.registerGalacticraftCreature(EntityEvolvedFireSpider.class, "EvolvedFireSpider", back, fore);    	
	}

    private void registerNonMobEntities()
    {    	
    	GCCoreUtil.registerGalacticraftNonMobEntity(EntityTier4Rocket.class, "rocket_tier_4", 150, 1, false);
    	GCCoreUtil.registerGalacticraftNonMobEntity(EntityTier5Rocket.class, "rocket_tier_5", 150, 1, false);
    	GCCoreUtil.registerGalacticraftNonMobEntity(EntityTier6Rocket.class, "rocket_tier_6", 150, 1, false);
    	GCCoreUtil.registerGalacticraftNonMobEntity(EntityMultiSeatRocketTest.class, "rocket_multi_seat", 150, 1, false);
    	GCCoreUtil.registerGalacticraftNonMobEntity(EntityCustomCargoRocket.class, "rocket_fluid_cargo", 150, 1, false);
    	GCCoreUtil.registerGalacticraftNonMobEntity(EntityIceSpike.class, "ice_spike", 40, 100, true);
    	GCCoreUtil.registerGalacticraftNonMobEntity(EntityMarsRover.class, "mars_rover", 150, 1, false);
    	GCCoreUtil.registerGalacticraftNonMobEntity(EntityLaserBeam.class, "laser_beam", 40, 100, false);
    	//GCCoreUtil.registerGalacticraftNonMobEntity(GSEntityMeteor.class, "GS Meteor", 150, 5, true);    	
    }

    private void registerTileEntities()
    {
    	GameRegistry.registerTileEntity(TileEntityAssembler.class, "GS Assembler");
    	GameRegistry.registerTileEntity(TileEntityFuelGenerator.class, "GS Fuel Generator");
    	GameRegistry.registerTileEntity(TileEntityModernSolarPanel.class, "GS Modern Solar Panel");
    	GameRegistry.registerTileEntity(TileEntityWindGenerator.class, "GS Wind Generator");
    	GameRegistry.registerTileEntity(TileEntityRocketAssembler.class, "GS Rocket Assembler");
    	GameRegistry.registerTileEntity(TileEntityUniversalRecycler.class, "GS Universal Recycler");
    	GameRegistry.registerTileEntity(TileEntityLiquidExtractor.class, "GS Liquid Extractor");
    	GameRegistry.registerTileEntity(TileEntityLiquidSeparator.class, "GS Liquid Separator");
    	GameRegistry.registerTileEntity(TileEntityHydroponicBase.class, "GS Hydroponic Base");
    	GameRegistry.registerTileEntity(TileEntityHydroponicFarm.class, "GS Hydroponic Farm");
    	GameRegistry.registerTileEntity(TileEntityGravitationModule.class, "GS Gravitation Module");    	
    	GameRegistry.registerTileEntity(TileEntityBlockCrystallTE.class, "GS Crystal");
    	GameRegistry.registerTileEntity(TileEntityTreasureChestCeres.class, "GS Treasure Ceres");
    	GameRegistry.registerTileEntity(TileEntityDungeonSpawnerCeres.class, "GS Spawner Boss Ceres");
    	GameRegistry.registerTileEntity(TileEntityTreasureChestIo.class, "GS Treasure Io");
    	GameRegistry.registerTileEntity(TileEntityDungeonSpawnerIo.class, "GS Spawner Boss Io");
    	GameRegistry.registerTileEntity(TileEntityRadiationStabiliser.class, "GS Radiation Stabiliser");
    	GameRegistry.registerTileEntity(TileEntityPanelController.class, "GS Panel Controller");
    	GameRegistry.registerTileEntity(TileEntityPlanetShield.class, "GS Planet Shield");
    	GameRegistry.registerTileEntity(TileEntityModernStorageModule.class, "GS Advanced Storage Module");
    	GameRegistry.registerTileEntity(TileEntityAdvLandingPad.class, "GS Advanced Landing Pad Full");
    	GameRegistry.registerTileEntity(TileEntityAdvLandingPadSingle.class, "GS Advanced Landing Pad");
    	GameRegistry.registerTileEntity(TileEntityModificationTable.class, "GS Modification Table");
    	GameRegistry.registerTileEntity(TileEntityGasBurner.class, "GS Gas Burner");
    	GameRegistry.registerTileEntity(TileEntityAdvOxygenStorageModule.class, "GS Oxygen Storage Module");
    	GameRegistry.registerTileEntity(TileEntityWindSolarPanel.class, "GS Solar Wind Panel");
    	GameRegistry.registerTileEntity(TileEntityAdvCircuitFabricator.class, "GS Advanced Circuit Fabricator");
    	GameRegistry.registerTileEntity(TileEntityEnergyPad.class, "GS Energy Pad");
    	GameRegistry.registerTileEntity(TileEntityResearchTable.class, "GS Research Table");
    	GameRegistry.registerTileEntity(TileEntityGasCollector.class, "GS Gas Collector");
    	GameRegistry.registerTileEntity(TileEntityGasGenerator.class, "GS Gas Generator");
/*    	
    	GameRegistry.registerTileEntity(TileEntityAdvOxygenSealer.class, "GS Oxygen Sealer");
    	GameRegistry.registerTileEntity(TileEntityAdvFuelLoader.class, "GS Fuel Loader");
    	
    	GameRegistry.registerTileEntity(TileEntityThermodynamicReactor.class, "GS Thermodynamic Reactor");*/
    	
    }
 
    public static void info(String message)
	{ 
    	if(log != null) 
   		{
   			log.log(Level.INFO, "[INFO]: " + message);
   		}		
	}
    
    public void debug(Object message)
   	{     	
   		if(debug && log != null) 
   		{
   			log.log(Level.INFO, "[DEBUG]: " + message.toString());
   		}
   	}   
    
    private void initModInfo(ModMetadata info)
    {
        info.autogenerated = false;
        info.modId = MODID;
        info.name = NAME;
        info.version = VERSION;
        info.description = "Global addon for Galacticraft 4.";
        info.authorList = Arrays.asList("Vi[Told]#1308", "AstroMaster#5045");
    }
    
    @EventBusSubscriber(modid = MODID)
    public static class RegistrationHandler
    {    	
    	
    	@SubscribeEvent
    	public static void registerModels(ModelRegistryEvent event) {
    		proxy.registerVariants();
    	}    
    	
    	@SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event)
        {
    		
    		GSBlocks.oreDictRegistration();
    		GSItems.oreDictRegistration();
    		
    		for(IBodies list : AsmodeusCore.bodies)
    			if(list.canRegister()) 
    				list.registerItems(event);
        }
    	
    	@SubscribeEvent(priority = EventPriority.LOWEST)
        public static void registerRecipes(RegistryEvent.Register<IRecipe> event)
        {
    		for(IBodies list : AsmodeusCore.bodies)
    			if(list.canRegister()) 
    				list.registerRecipes();
        }
    }
}
