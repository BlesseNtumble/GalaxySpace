package galaxyspace.core.proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.google.common.collect.Maps;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import galaxyspace.GalaxySpace;
import galaxyspace.core.client.GSKeyHandlerClient;
import galaxyspace.core.client.particles.JPParticles;
import galaxyspace.core.client.render.entity.RenderEntryPod;
import galaxyspace.core.client.render.entity.RenderIceSpike;
import galaxyspace.core.client.render.item.ItemRenderBow;
import galaxyspace.core.client.render.item.ItemRenderKey;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.events.GSClientTickHandler;
import galaxyspace.core.handler.GSColorRingClient;
import galaxyspace.core.handler.GSEffectHandler;
import galaxyspace.core.handler.GSSkyProviderHandler;
import galaxyspace.core.prefab.entity.EntityEntryPod;
import galaxyspace.core.prefab.entity.EntityIceSpike;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.registers.fluids.FluidTexturesGS;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.util.GSMusicTicker;
import galaxyspace.systems.SolarSystem.moons.enceladus.render.items.ItemRendererEnceladusCrystal;
import galaxyspace.systems.SolarSystem.moons.enceladus.tile.TileEntityBlockCrystall;
import galaxyspace.systems.SolarSystem.moons.enceladus.tile.TileEntityBlockCrystallTE;
import galaxyspace.systems.SolarSystem.moons.europa.entities.EntityEvolvedColdBlaze;
import galaxyspace.systems.SolarSystem.moons.europa.render.entities.RenderEvolvedColdBlaze;
import galaxyspace.systems.SolarSystem.moons.io.entities.EntityBossGhast;
import galaxyspace.systems.SolarSystem.moons.io.render.block.BlockRendererIoTreasureChest;
import galaxyspace.systems.SolarSystem.moons.io.render.entities.RenderBossGhast;
import galaxyspace.systems.SolarSystem.moons.io.render.tile.TileEntityIoTreasureChestRenderer;
import galaxyspace.systems.SolarSystem.moons.io.tile.TileEntityIoTreasureChest;
import galaxyspace.systems.SolarSystem.planets.ceres.entities.EntityBossBlaze;
import galaxyspace.systems.SolarSystem.planets.ceres.render.block.BlockRendererCeresTreasureChest;
import galaxyspace.systems.SolarSystem.planets.ceres.render.entities.RenderBossBlaze;
import galaxyspace.systems.SolarSystem.planets.ceres.render.tile.TileEntityCeresTreasureChestRenderer;
import galaxyspace.systems.SolarSystem.planets.ceres.tile.TileEntityCeresTreasureChest;
import galaxyspace.systems.SolarSystem.planets.overworld.entities.EntityCargoFluidRocket;
import galaxyspace.systems.SolarSystem.planets.overworld.entities.EntityTier4Rocket;
import galaxyspace.systems.SolarSystem.planets.overworld.entities.EntityTier5Rocket;
import galaxyspace.systems.SolarSystem.planets.overworld.entities.EntityTier6Rocket;
import galaxyspace.systems.SolarSystem.planets.overworld.render.block.BlockRendererAdvLandingPad;
import galaxyspace.systems.SolarSystem.planets.overworld.render.entities.RenderCargoFluidRocket;
import galaxyspace.systems.SolarSystem.planets.overworld.render.entities.RenderRockets;
import galaxyspace.systems.SolarSystem.planets.overworld.render.item.ItemRendererCargoFluidRocket;
import galaxyspace.systems.SolarSystem.planets.overworld.render.item.ItemRendererFluidTank;
import galaxyspace.systems.SolarSystem.planets.overworld.render.item.ItemRendererHydroponicFarm;
import galaxyspace.systems.SolarSystem.planets.overworld.render.item.ItemRendererJetPack;
import galaxyspace.systems.SolarSystem.planets.overworld.render.item.ItemRendererThermalPaddingT2;
import galaxyspace.systems.SolarSystem.planets.overworld.render.item.ItemRendererThermodynamicReactor;
import galaxyspace.systems.SolarSystem.planets.overworld.render.item.ItemRendererTier4Rocket;
import galaxyspace.systems.SolarSystem.planets.overworld.render.item.ItemRendererTier5Rocket;
import galaxyspace.systems.SolarSystem.planets.overworld.render.item.ItemRendererTier6Rocket;
import galaxyspace.systems.SolarSystem.planets.overworld.render.tile.TileEntityFluidTankRenderer;
import galaxyspace.systems.SolarSystem.planets.overworld.render.tile.TileEntityHydroponicFarmRenderer;
import galaxyspace.systems.SolarSystem.planets.overworld.render.tile.TileEntityPortableNuclearReactorRenderer;
import galaxyspace.systems.SolarSystem.planets.overworld.render.tile.TileEntitySolarPanelRenderer;
import galaxyspace.systems.SolarSystem.planets.overworld.render.tile.TileEntitySolarWindPanelRenderer;
import galaxyspace.systems.SolarSystem.planets.overworld.render.tile.TileEntityWindTurbineRenderer;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityFluidTank;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityHydroponicFarm;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityPortableNuclearReactor;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityRadiationStabiliser;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntitySolarPanel;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntitySolarWind;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityWindTurbine;
import galaxyspace.systems.SolarSystem.planets.venus.entities.EntityEvolvedFireBlaze;
import galaxyspace.systems.SolarSystem.planets.venus.entities.EntityEvolvedFireCreeper;
import galaxyspace.systems.SolarSystem.planets.venus.entities.EntityEvolvedFireSkeleton;
import galaxyspace.systems.SolarSystem.planets.venus.entities.EntityEvolvedFireSpider;
import galaxyspace.systems.SolarSystem.planets.venus.render.entities.RenderEvolvedBlaze;
import galaxyspace.systems.SolarSystem.planets.venus.render.entities.RenderEvolvedFireCreeper;
import galaxyspace.systems.SolarSystem.planets.venus.render.entities.RenderEvolvedFireSkeleton;
import galaxyspace.systems.SolarSystem.planets.venus.render.entities.RenderEvolvedFireSpider;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.client.render.block.BlockRendererMachine;
import micdoodle8.mods.galacticraft.core.client.render.tile.TileEntityBubbleProviderRenderer;
import micdoodle8.mods.galacticraft.core.util.VersionUtil;
import micdoodle8.mods.galacticraft.planets.mars.MarsModule;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;


public class ClientProxy extends CommonProxy {
	
	public static Map<String, String> GScapeMap = new HashMap<String, String>();
	public static Map<String, ResourceLocation> GScapesMap = Maps.newHashMap();
	
	public static Minecraft mc = FMLClientHandler.instance().getClient();
	public static MusicTicker.MusicType GS_MUSIC;

	private static int ceresMachineRenderID;
	private static int fuelGeneratorRenderID;
	private static int solarWindPanelRenderID;
	private static int convertersurfaceRenderID;
	private static int storagemoduleRenderID;
	private static int oxstoragemoduleRenderID;
	private static int solarPanelRenderID;
	private static int gravitationmoduleRenderID;
	public static int futureLampRenderID;
	private static int windTurbineRenderID;
	private static int advOxygenSealerRenderID;
	private static int advFuelLoaderRenderID;
	private static int rocketAssemblyRenderID;
	private static int recyclerRenderID;
	private static int liquidextractorRenderID;
	private static int portablenuclearreactorRenderID;
	private static int fluidtankRenderID;
	private static int advlandingpadRenderID;
	private static int cargofluidRenderId;
	private static int gydroponicbaseRenderId;
	private static int gydroponicfarmRenderId;
	private static int liquidseparatorRenderID;
	private static int radiationstabiliserRenderID;
	private static int modificationtableRenderID;
	private static int oxygenfillerRenderID;
	
	private static int ceresTreasureChestRenderID;
	private static int ioTreasureChestRenderID;
	private static int enceladusTreasureChestRenderID;
	private static int proteusTreasureChestRenderID;
	private static int plutoTreasureChestRenderID;
	
	private static int renderIndexJetpackArmor;
	private static int renderIndexArmor;
	

	@Override
    public void preload() {
		
		register_event(new GSSkyProviderHandler());
		register_event(new GSColorRingClient());
		register_event(new GSClientTickHandler());		
		register_event(new GSKeyHandlerClient());
		
		ClientRegistry.registerKeyBinding(GSKeyHandlerClient.toggleHelmet);
		ClientRegistry.registerKeyBinding(GSKeyHandlerClient.toggleChest);
		ClientRegistry.registerKeyBinding(GSKeyHandlerClient.toggleLegs);
		ClientRegistry.registerKeyBinding(GSKeyHandlerClient.toggleBoots);
		//ClientRegistry.registerKeyBinding(GSKeyHandlerClient.toggleJetpack);
		
		//GSLightningStormHandler lightning = new GSLightningStormHandler();
		//register_event(lightning);
		
		ClientProxy.renderIndexJetpackArmor = RenderingRegistry.addNewArmourRendererPrefix("Jetpack");
		ClientProxy.renderIndexArmor = RenderingRegistry.addNewArmourRendererPrefix("Armor") + RenderingRegistry.getNextAvailableRenderId();
		ClientProxy.setupCapes();
    	FluidTexturesGS.init();
    	//AnimTickHandler.init();
	}

    @Override
    public void load()
    {  
    	registerEntityRenderers();
    	registerItemRenderers();
    	
    	Class[][] commonTypes =
            {
                    { MusicTicker.MusicType.class, ResourceLocation.class, int.class, int.class },
            };
    	
    	GS_MUSIC = EnumHelper.addEnum(commonTypes, MusicTicker.MusicType.class, "GS_MUSIC", new ResourceLocation(GalaxySpace.ASSET_PREFIX, "galaxyspace.musicSpace"), 1000, 2000);
    
    
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCeresTreasureChest.class, new TileEntityCeresTreasureChestRenderer());
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityIoTreasureChest.class, new TileEntityIoTreasureChestRenderer());
    	
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySolarWind.class, new TileEntitySolarWindPanelRenderer());
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySolarPanel.class, new TileEntitySolarPanelRenderer());
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWindTurbine.class, new TileEntityWindTurbineRenderer());
    	
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHydroponicFarm.class, new TileEntityHydroponicFarmRenderer());
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPortableNuclearReactor.class, new TileEntityPortableNuclearReactorRenderer());
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFluidTank.class, new TileEntityFluidTankRenderer());
    	 
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRadiationStabiliser.class, new TileEntityBubbleProviderRenderer(0.45F, 0.0F, 0.1F));
    }
	
    @Override
    public void postload() {   	
    	if(GSConfigCore.enableMusic) {
	    	try {
				Field ftc = Minecraft.getMinecraft().getClass().getDeclaredField(VersionUtil.getNameDynamic(VersionUtil.KEY_FIELD_MUSICTICKER));
				ftc.setAccessible(true);
				ftc.set(Minecraft.getMinecraft(), new GSMusicTicker(Minecraft.getMinecraft()));
	        } catch (Exception e) {e.printStackTrace();}     	
    	}    		
	}

    @Override
    public void spawnParticle(String particleID, Vector3 position, Vector3 motion, Object[] otherInfo)
    {
        GSEffectHandler.spawnParticle(particleID, position, motion, otherInfo);
    }
    
	public static void registerEntityRenderers()
    {
		ClientProxy.ceresMachineRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new BlockRendererMachine(ClientProxy.ceresMachineRenderID));
		
		ClientProxy.storagemoduleRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new BlockRendererMachine(ClientProxy.storagemoduleRenderID ));
		
		ClientProxy.fuelGeneratorRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new BlockRendererMachine(ClientProxy.fuelGeneratorRenderID));
		
		ClientProxy.solarWindPanelRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new BlockRendererMachine(ClientProxy.solarWindPanelRenderID));
		
		ClientProxy.convertersurfaceRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new BlockRendererMachine(ClientProxy.convertersurfaceRenderID));
		
		ClientProxy.oxstoragemoduleRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new BlockRendererMachine(ClientProxy.oxstoragemoduleRenderID ));
		
		ClientProxy.solarPanelRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new BlockRendererMachine(ClientProxy.solarPanelRenderID));
		
		ClientProxy.gravitationmoduleRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new BlockRendererMachine(ClientProxy.gravitationmoduleRenderID));
		
		ClientProxy.windTurbineRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new BlockRendererMachine(ClientProxy.windTurbineRenderID));
		
		ClientProxy.advOxygenSealerRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new BlockRendererMachine(ClientProxy.advOxygenSealerRenderID));
		
		ClientProxy.advFuelLoaderRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new BlockRendererMachine(ClientProxy.advFuelLoaderRenderID));
		
		ClientProxy.rocketAssemblyRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new BlockRendererMachine(ClientProxy.rocketAssemblyRenderID));
		
		ClientProxy.recyclerRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new BlockRendererMachine(ClientProxy.recyclerRenderID));
		
		ClientProxy.liquidextractorRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new BlockRendererMachine(ClientProxy.liquidextractorRenderID));
		
		ClientProxy.portablenuclearreactorRenderID = RenderingRegistry.getNextAvailableRenderId();
		//RenderingRegistry.registerBlockHandler(new BlockRendererMachine(ClientProxy.thermodynamicreactorRenderID));
		ClientProxy.fluidtankRenderID = RenderingRegistry.getNextAvailableRenderId();
		
		ClientProxy.advlandingpadRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new BlockRendererAdvLandingPad(ClientProxy.advlandingpadRenderID));
		
		ClientProxy.cargofluidRenderId = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new BlockRendererMachine(ClientProxy.cargofluidRenderId));
		
		ClientProxy.gydroponicfarmRenderId = RenderingRegistry.getNextAvailableRenderId();			
		
		ClientProxy.liquidseparatorRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new BlockRendererMachine(ClientProxy.liquidseparatorRenderID));
	
		ClientProxy.gydroponicbaseRenderId = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new BlockRendererMachine(ClientProxy.gydroponicbaseRenderId));
	
		ClientProxy.radiationstabiliserRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new BlockRendererMachine(ClientProxy.radiationstabiliserRenderID));
	
		ClientProxy.modificationtableRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new BlockRendererMachine(ClientProxy.modificationtableRenderID));
		
		ClientProxy.oxygenfillerRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new BlockRendererMachine(ClientProxy.oxygenfillerRenderID));
		
		ClientProxy.ceresTreasureChestRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new BlockRendererCeresTreasureChest(ClientProxy.ceresTreasureChestRenderID));
		
		ClientProxy.ioTreasureChestRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new BlockRendererIoTreasureChest(ClientProxy.ioTreasureChestRenderID));
		
		/*
		ClientProxy.enceladusTreasureChestRenderID = RenderingRegistry.getNextAvailableRenderId();
		ClientProxy.proteusTreasureChestRenderID = RenderingRegistry.getNextAvailableRenderId();
		ClientProxy.plutoTreasureChestRenderID = RenderingRegistry.getNextAvailableRenderId();		*/
		
		/*RenderingRegistry.registerBlockHandler(new BlockRendererIOTreasureChest(ClientProxy.ioTreasureChestRenderID));
		RenderingRegistry.registerBlockHandler(new BlockRendererEnceladusTreasureChest(ClientProxy.enceladusTreasureChestRenderID));
		RenderingRegistry.registerBlockHandler(new BlockRendererProteusTreasureChest(ClientProxy.proteusTreasureChestRenderID));
		RenderingRegistry.registerBlockHandler(new BlockRendererPlutoTreasureChest(ClientProxy.plutoTreasureChestRenderID));
		*/
		
		RenderingRegistry.registerEntityRenderingHandler(EntityBossBlaze.class, new RenderBossBlaze());
		RenderingRegistry.registerEntityRenderingHandler(EntityBossGhast.class, new RenderBossGhast());
		/*RenderingRegistry.registerEntityRenderingHandler(EntityCrystalBoss.class, new RenderCrystalBoss());
		RenderingRegistry.registerEntityRenderingHandler(EntityEvolvedBossSlime.class, new RenderBossSlime(new ModelSlime(16), new ModelSlime(0)));
		RenderingRegistry.registerEntityRenderingHandler(EntityEvolvedBossWolf.class, new RenderEvolvedBossWolf());
		*/
		RenderingRegistry.registerEntityRenderingHandler(EntityEvolvedFireBlaze.class, new RenderEvolvedBlaze());
		RenderingRegistry.registerEntityRenderingHandler(EntityEvolvedFireCreeper.class, new RenderEvolvedFireCreeper());
		RenderingRegistry.registerEntityRenderingHandler(EntityEvolvedFireSkeleton.class, new RenderEvolvedFireSkeleton());
		RenderingRegistry.registerEntityRenderingHandler(EntityEvolvedFireSpider.class, new RenderEvolvedFireSpider());
		/*RenderingRegistry.registerEntityRenderingHandler(EntityEvolvedEnderman.class, new RenderEvolvedEnderman());
		*/
		RenderingRegistry.registerEntityRenderingHandler(EntityEvolvedColdBlaze.class, new RenderEvolvedColdBlaze());
		//RenderingRegistry.registerEntityRenderingHandler(EntityEvolvedGuardian.class, new RenderEvolvedGuardian(new ModelEvolvedGuardian(), 1.0F));
		
		//RenderingRegistry.registerEntityRenderingHandler(EntityPlayer.class, new RenderPlayerGS());
    	
		RenderingRegistry.registerEntityRenderingHandler(EntityIceSpike.class, new RenderIceSpike(2.0F));
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlockCrystallTE.class, new TileEntityBlockCrystall());
		
    }
	
	public static void registerItemRenderers()
    {
		IModelCustom rocketModeltier4 = AdvancedModelLoader.loadModel(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "models/tier4rocket.obj"));
		IModelCustom rocketModeltier5 = AdvancedModelLoader.loadModel(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "models/tier6rocket.obj"));
		IModelCustom rocketModeltier6 = AdvancedModelLoader.loadModel(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "models/tier8rocket.obj"));

		//IModelCustom plasmagun = AdvancedModelLoader.loadModel(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "models/PlasmaGun.obj"));
        
		IModelCustom cargoRocketModel = AdvancedModelLoader.loadModel(new ResourceLocation(MarsModule.ASSET_PREFIX, "models/cargoRocket.obj"));
		
		RenderingRegistry.registerEntityRenderingHandler(EntityTier4Rocket.class, new RenderRockets(rocketModeltier4, GalaxySpace.ASSET_PREFIX, "tier4rocket"));
		RenderingRegistry.registerEntityRenderingHandler(EntityTier5Rocket.class, new RenderRockets(rocketModeltier5, GalaxySpace.ASSET_PREFIX, "tier6rocket"));
		RenderingRegistry.registerEntityRenderingHandler(EntityTier6Rocket.class, new RenderRockets(rocketModeltier6, GalaxySpace.ASSET_PREFIX, "tier8rocket"));
		RenderingRegistry.registerEntityRenderingHandler(EntityCargoFluidRocket.class, new RenderCargoFluidRocket(cargoRocketModel));
		
		RenderingRegistry.registerEntityRenderingHandler(EntityEntryPod.class, new RenderEntryPod());
	 
		MinecraftForgeClient.registerItemRenderer(GSItems.Tier4Rocket, new ItemRendererTier4Rocket(rocketModeltier4));
		MinecraftForgeClient.registerItemRenderer(GSItems.Tier5Rocket, new ItemRendererTier5Rocket(rocketModeltier5));
		MinecraftForgeClient.registerItemRenderer(GSItems.Tier6Rocket, new ItemRendererTier6Rocket(rocketModeltier6));
		
		MinecraftForgeClient.registerItemRenderer(GSItems.CargoFluidRocket, new ItemRendererCargoFluidRocket(cargoRocketModel));
        MinecraftForgeClient.registerItemRenderer(GSItems.TierKeys, new ItemRenderKey());
          
        MinecraftForgeClient.registerItemRenderer(GSItems.ThermalPaddingTier2, new ItemRendererThermalPaddingT2());
        MinecraftForgeClient.registerItemRenderer(GSItems.JetPack, new ItemRendererJetPack());
        MinecraftForgeClient.registerItemRenderer(GSItems.QuantBow, new ItemRenderBow());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(GSBlocks.PortableNuclearReactor), new ItemRendererThermodynamicReactor());
        //MinecraftForgeClient.registerItemRenderer(GSItems.PlasmaGun, new ItemRendererPlasmaGun(plasmagun));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(GSBlocks.FluidTank), new ItemRendererFluidTank());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(GSBlocks.EnceladusCrystal), new ItemRendererEnceladusCrystal());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(GSBlocks.HydroponicFarm), new ItemRendererHydroponicFarm());
        
    
    }
 
  
	@Override
	public int getBlockRender(Block block)
	{
		if (block == GSBlocks.AssemblyMachine) return ClientProxy.ceresMachineRenderID;
		if (block == GSBlocks.StorageModuleT3) return ClientProxy.storagemoduleRenderID;
		if (block == GSBlocks.FuelGenerator) return ClientProxy.fuelGeneratorRenderID;
		if (block == GSBlocks.SolarWindPanel) return ClientProxy.solarWindPanelRenderID;
		if (block == GSBlocks.ConverterSurface)	return ClientProxy.convertersurfaceRenderID;
		if (block == GSBlocks.OxStorageModuleT2) return ClientProxy.oxstoragemoduleRenderID;
		if (block == GSBlocks.SolarPanel) return ClientProxy.solarPanelRenderID;
		if (block == GSBlocks.GravitationModule) return ClientProxy.gravitationmoduleRenderID;
		if (block == GSBlocks.FutureLamp) return ClientProxy.futureLampRenderID;
		if (block == GSBlocks.WindTurbine) return ClientProxy.windTurbineRenderID;
		if (block == GSBlocks.AdvOxygenSealer) return ClientProxy.advOxygenSealerRenderID;
		if (block == GSBlocks.AdvFuelLoader) return ClientProxy.advFuelLoaderRenderID;
		if (block == GSBlocks.RocketAssembly) return ClientProxy.rocketAssemblyRenderID;
		if (block == GSBlocks.Recycler) return ClientProxy.recyclerRenderID;
		if (block == GSBlocks.LiquidExtractor) return ClientProxy.liquidextractorRenderID;
		if (block == GSBlocks.PortableNuclearReactor) return ClientProxy.portablenuclearreactorRenderID;
		if (block == GSBlocks.FluidTank) return ClientProxy.fluidtankRenderID;
		if (block == GSBlocks.AdvLandingPadFull) return ClientProxy.advlandingpadRenderID;
		if (block == GSBlocks.CargoFluidLoader) return ClientProxy.cargofluidRenderId;
		if (block == GSBlocks.HydroponicBase) return ClientProxy.gydroponicbaseRenderId;
		if (block == GSBlocks.HydroponicFarm) return ClientProxy.gydroponicfarmRenderId;
		if (block == GSBlocks.LiquidSeparator) return ClientProxy.liquidseparatorRenderID;
		if (block == GSBlocks.RadiationStabiliser) return ClientProxy.radiationstabiliserRenderID;
		if (block == GSBlocks.ModificationTable) return ClientProxy.modificationtableRenderID;
		if (block == GSBlocks.OxygenFiller) return ClientProxy.oxygenfillerRenderID;
		
		if (block == GSBlocks.CeresTChestT4) return ClientProxy.ceresTreasureChestRenderID;
		if (block == GSBlocks.IoTChestT5) return ClientProxy.ioTreasureChestRenderID;
		return -1;		
	}
	
    @Override
    public int getJetpackArmorRenderIndex()
    {
        return ClientProxy.renderIndexJetpackArmor;
    }
    
    @Override
    public int getArmorRenderIndex()
    {
        return ClientProxy.renderIndexArmor;
    }
    
	public void register_event(Object obj)
	{
    	FMLCommonHandler.instance().bus().register(obj);
    	MinecraftForge.EVENT_BUS.register(obj);
	}
	

	@Override
	public void showJetpackParticles(World world, EntityLivingBase wearer, int streams) {
		if (streams == 0) return;
		Minecraft mc = Minecraft.getMinecraft();
		if ((mc.gameSettings.particleSetting == 0) || ((mc.gameSettings.particleSetting == 1) && (mc.theWorld.getTotalWorldTime() % 4L == 0L))) {
			Vec3 userPos = Vec3.createVectorHelper(wearer.posX, wearer.posY, wearer.posZ);
			if (!wearer.equals(mc.thePlayer)) {
				userPos = userPos.addVector(0.0D, 1.6D, 0.0D);
			}
			Random rand = new Random();

			Vec3 vLeft = Vec3.createVectorHelper(-0.28D, -0.95D, -0.38D);
			vLeft.rotateAroundY((float) Math.toRadians(-wearer.renderYawOffset));

			Vec3 vRight = Vec3.createVectorHelper(0.28D, -0.95D, -0.38D);
			vRight.rotateAroundY((float) Math.toRadians(-wearer.renderYawOffset));

			Vec3 vCenter = Vec3.createVectorHelper((rand.nextFloat() - 0.5F) * 0.25F, -0.95D, -0.38D);
			vCenter.rotateAroundY((float) Math.toRadians(-wearer.renderYawOffset));

			vLeft = vLeft.addVector(-wearer.motionX * 0.2D, -wearer.motionY * 0.2D, -wearer.motionZ * 0.2D);
			vRight = vRight.addVector(-wearer.motionX * 0.2D, -wearer.motionY * 0.2D, -wearer.motionZ * 0.2D);
			vCenter = vCenter.addVector(-wearer.motionX * 0.2D, -wearer.motionY * 0.2D, -wearer.motionZ * 0.2D);

			Vec3 v = userPos.addVector(vLeft.xCoord, vLeft.yCoord, vLeft.zCoord);
			if (streams == 1) JPParticles.spawnParticle(mc, world, v.xCoord, v.yCoord, v.zCoord, rand.nextDouble() * 0.05D - 0.025D, -0.2D, rand.nextDouble() * 0.05D - 0.025D);

			v = userPos.addVector(vRight.xCoord, vRight.yCoord, vRight.zCoord);
			if (streams == 2) JPParticles.spawnParticle(mc, world, v.xCoord, v.yCoord, v.zCoord, rand.nextDouble() * 0.05D - 0.025D, -0.2D, rand.nextDouble() * 0.05D - 0.025D);

			v = userPos.addVector(vCenter.xCoord, vCenter.yCoord, vCenter.zCoord);
			if (streams == 2) JPParticles.spawnParticle(mc, world, v.xCoord, v.yCoord, v.zCoord, rand.nextDouble() * 0.05D - 0.025D, -0.2D, rand.nextDouble() * 0.05D - 0.025D);
		}
	}
	
	public static void setupCapes()
    {
        try
        {
            ClientProxy.updateCapeList();
        }
        catch (Exception e)
        {
            FMLLog.severe("Error while setting up Galaxy Space donate capes/skins");
            e.printStackTrace();
        }

    }
    
    private static void updateCapeList()
    {
        int timeout = 10000;
        URL capeListUrl = null;
        
		try 
		{
			capeListUrl = new URL("https://raw.githubusercontent.com/BlesseNtumble/GalaxySpace/master/capes/capes.txt");
		} 
		catch (MalformedURLException e) 
		{
            FMLLog.severe("Error getting capes list URL");
			e.printStackTrace();
			return;
		}
		
        URLConnection connection = null;
        
		try 
		{
			connection = capeListUrl.openConnection();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return;
		}
		
        connection.setConnectTimeout(timeout);
        connection.setReadTimeout(timeout);
        InputStream stream = null;
        
		try 
		{
			stream = connection.getInputStream();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return;
		}
		
        InputStreamReader streamReader = new InputStreamReader(stream);
        BufferedReader reader = new BufferedReader(streamReader);
        
        String line;
        try 
        {
			while ((line = reader.readLine()) != null)
			{
			    if (line.contains(":"))
			    {
			        int splitLocation = line.indexOf(":");
			        String username = line.substring(0, splitLocation);
			        
			        // String capeUrl = "https://raw.github.com/micdoodle8/Galacticraft/master/capes/" + line.substring(splitLocation + 1) + ".png";
			        String capeUrl = "https://raw.githubusercontent.com/BlesseNtumble/GalaxySpace/master/capes/" + line.substring(splitLocation + 1) + ".png";
			        ClientProxy.GScapeMap.put(username, capeUrl);
			     
			    }
			}
		} 
        catch (IOException e)
        {
			e.printStackTrace();
		}
        
        try 
        {
			reader.close();
		} 
        catch (IOException e)
        {
			e.printStackTrace();
		}
        try 
        {
			streamReader.close();
		} 
        catch (IOException e) 
        {
			e.printStackTrace();
		}
        try 
        {
			stream.close();
		} 
        catch (IOException e) 
        {
			e.printStackTrace();
		}
    }
   
 
 }
