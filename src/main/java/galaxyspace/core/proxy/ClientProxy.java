package galaxyspace.core.proxy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

import asmodeuscore.AsmodeusCore;
import asmodeuscore.api.IBodies;
import galaxyspace.GalaxySpace;
import galaxyspace.core.GSBlocks;
import galaxyspace.core.GSFluids;
import galaxyspace.core.GSItems;
import galaxyspace.core.client.GSKeyHandlerClient;
import galaxyspace.core.client.fx.GSEffectHandler;
import galaxyspace.core.client.models.BakedModelBrightFour;
import galaxyspace.core.client.models.BakedModelFullbright;
import galaxyspace.core.client.render.entity.RenderAstroWolf;
import galaxyspace.core.client.render.entity.RenderCargoRockets;
import galaxyspace.core.client.render.entity.RenderEvolvedColdBlaze;
import galaxyspace.core.client.render.entity.RenderIceSpike;
import galaxyspace.core.client.render.entity.RenderRockets;
import galaxyspace.core.client.render.entity.layers.LayerOxygenTank;
import galaxyspace.core.client.render.entity.layers.LayerThermalPadding;
import galaxyspace.core.client.render.item.ItemModelRocketT4;
import galaxyspace.core.client.render.item.ItemModelRocketT5;
import galaxyspace.core.client.render.item.ItemModelRocketT6;
import galaxyspace.core.client.render.tile.TileEntityTreasureChestRenderer;
import galaxyspace.core.events.GSClientTickHandler;
import galaxyspace.core.handler.GSMapHandler;
import galaxyspace.core.handler.GSSkyProviderHandler;
import galaxyspace.core.prefab.blocks.DungeonBlocks;
import galaxyspace.core.prefab.entities.EntityAstroWolf;
import galaxyspace.core.prefab.entities.EntityCustomCargoRocket;
import galaxyspace.core.prefab.entities.EntityEvolvedColdBlaze;
import galaxyspace.core.prefab.entities.EntityIceSpike;
import galaxyspace.core.prefab.entities.EntityTier4Rocket;
import galaxyspace.core.prefab.entities.EntityTier5Rocket;
import galaxyspace.core.prefab.entities.EntityTier6Rocket;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.SolarSystem.moons.callisto.blocks.CallistoBlocks;
import galaxyspace.systems.SolarSystem.moons.enceladus.blocks.EnceladusBlocks;
import galaxyspace.systems.SolarSystem.moons.enceladus.tile.TileEntityBlockCrystall;
import galaxyspace.systems.SolarSystem.moons.enceladus.tile.TileEntityBlockCrystallTE;
import galaxyspace.systems.SolarSystem.moons.europa.blocks.EuropaBlocks;
import galaxyspace.systems.SolarSystem.moons.ganymede.blocks.GanymedeBlocks;
import galaxyspace.systems.SolarSystem.moons.io.blocks.IoBlocks;
import galaxyspace.systems.SolarSystem.moons.io.entities.EntityBossGhast;
import galaxyspace.systems.SolarSystem.moons.io.renderer.entities.RenderBossGhast;
import galaxyspace.systems.SolarSystem.moons.io.tile.TileEntityTreasureChestIo;
import galaxyspace.systems.SolarSystem.moons.miranda.blocks.MirandaBlocks;
import galaxyspace.systems.SolarSystem.moons.phobos.blocks.PhobosBlocks;
import galaxyspace.systems.SolarSystem.moons.titan.blocks.TitanBlocks;
import galaxyspace.systems.SolarSystem.moons.triton.blocks.TritonBlocks;
import galaxyspace.systems.SolarSystem.planets.ceres.blocks.CeresBlocks;
import galaxyspace.systems.SolarSystem.planets.ceres.entities.EntityBossBlaze;
import galaxyspace.systems.SolarSystem.planets.ceres.renderer.entities.RenderBossBlaze;
import galaxyspace.systems.SolarSystem.planets.ceres.tile.TileEntityTreasureChestCeres;
import galaxyspace.systems.SolarSystem.planets.haumea.blocks.HaumeaBlocks;
import galaxyspace.systems.SolarSystem.planets.mars.blocks.MarsOresBlocks;
import galaxyspace.systems.SolarSystem.planets.mars.entities.EntityMarsRover;
import galaxyspace.systems.SolarSystem.planets.mars.render.entities.RenderMarsRover;
import galaxyspace.systems.SolarSystem.planets.mars.render.item.ItemModelMarsRover;
import galaxyspace.systems.SolarSystem.planets.mercury.blocks.MercuryBlocks;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockDecoMetals;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockFutureGlasses;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockMachineFrames;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockOres;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockSurfaceIce;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemBasicGS;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemBasicGS.BasicItems;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemCompressedPlates;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemHeavyDutyPlates;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemIngots;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemRocketModules;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemRocketParts;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemSchematics;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemTierKeysChest;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemUpgrades;
import galaxyspace.systems.SolarSystem.planets.overworld.items.armor.ItemThermalPaddingBase;
import galaxyspace.systems.SolarSystem.planets.overworld.render.item.ItemRendererHydroponicFarm;
import galaxyspace.systems.SolarSystem.planets.overworld.render.item.ItemRendererJetpack;
import galaxyspace.systems.SolarSystem.planets.overworld.render.tile.TileEntityHydroponicFarmRenderer;
import galaxyspace.systems.SolarSystem.planets.overworld.render.tile.TileEntityModernSolarPanelRenderer;
import galaxyspace.systems.SolarSystem.planets.overworld.render.tile.TileEntitySolarWindPanelRenderer;
import galaxyspace.systems.SolarSystem.planets.overworld.render.tile.TileEntityWindGeneratorRenderer;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityHydroponicFarm;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityModernSolarPanel;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityWindGenerator;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityWindSolarPanel;
import galaxyspace.systems.SolarSystem.planets.pluto.blocks.PlutoBlocks;
import micdoodle8.mods.galacticraft.api.client.IItemMeshDefinitionCustom;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.util.ClientUtil;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.wrappers.ModelTransformWrapper;
import micdoodle8.mods.galacticraft.planets.asteroids.client.render.item.ItemModelCargoRocket;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.event.TextureStitchEvent.Pre;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class ClientProxy extends CommonProxy{
	
	public static Map<String, String> GScapeMap = new HashMap<String, String>();
	public static Map<String, ResourceLocation> GScapesMap = Maps.newHashMap();
	
	public static Minecraft mc = FMLClientHandler.instance().getClient();
	//public static MusicTicker.MusicType GS_MUSIC;
	
	private static ModelResourceLocation LEMethaneLocation = new ModelResourceLocation(GalaxySpace.TEXTURE_PREFIX + "liquid_ethanemethane", "fluid");
	private static ModelResourceLocation HeliumLocation = new ModelResourceLocation(GalaxySpace.TEXTURE_PREFIX + "liquid_helium", "fluid");
	private static ModelResourceLocation HeliumHydrogenLocation = new ModelResourceLocation(GalaxySpace.TEXTURE_PREFIX + "liquid_heliumhydrogen", "fluid");
	private static ModelResourceLocation EthaneLocation = new ModelResourceLocation(GalaxySpace.TEXTURE_PREFIX + "liquid_ethane", "fluid");
	private static ModelResourceLocation NatureGasLocation = new ModelResourceLocation(GalaxySpace.TEXTURE_PREFIX + "liquid_naturegas", "fluid");
	
	@Override
    public void preload(FMLPreInitializationEvent event) {
		
		register_event(new GSSkyProviderHandler());
		register_event(new GSClientTickHandler());
		register_event(new GSMapHandler());	
		//register_event(new ColorBlockHandler());	
		register_event(new GSKeyHandlerClient());
		
		ClientRegistry.registerKeyBinding(GSKeyHandlerClient.toggleHelmet);
		ClientRegistry.registerKeyBinding(GSKeyHandlerClient.toggleChest);
		ClientRegistry.registerKeyBinding(GSKeyHandlerClient.toggleLegs);
		ClientRegistry.registerKeyBinding(GSKeyHandlerClient.toggleBoots);
		
		//ClientProxy.setupCapes();
		registerEntityRenderers();
		MinecraftForge.EVENT_BUS.register(this);			
		
	}

    @Override
    public void load()
    {     	
    	
    	Minecraft.getMinecraft().getRenderManager().getSkinMap().get("default").addLayer(new LayerOxygenTank());
    	Minecraft.getMinecraft().getRenderManager().getSkinMap().get("slim").addLayer(new LayerOxygenTank());
    	
    	Minecraft.getMinecraft().getRenderManager().getSkinMap().get("default").addLayer(new LayerThermalPadding(Minecraft.getMinecraft().getRenderManager().playerRenderer));
    	Minecraft.getMinecraft().getRenderManager().getSkinMap().get("slim").addLayer(new LayerThermalPadding(Minecraft.getMinecraft().getRenderManager().playerRenderer));
    	
    	//GS_MUSIC = EnumHelper.addEnum(MusicTicker.MusicType.class, "GS_MUSIC", new Class[] { SoundEvent.class, Integer.TYPE, Integer.TYPE }, GSSounds.music, 12000, 24000);
    	   
    /*
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySolarWind.class, new TileEntitySolarWindPanelRenderer());
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDuraluminumWire.class, new TileEntityDuraluminumWireRenderer());
     	
    	//ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFuelGenerator.class, new TileEntityGeneratorRenderer());
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityThermodynamicReactor.class, new TileEntityThermodynamicReactorRenderer());
     	 */
    	
    		
    }
	
    @Override
    public void postload() {   			
    	/*try {
            Field ftc = Minecraft.getMinecraft().getClass().getDeclaredField(GCCoreUtil.isDeobfuscated() ? "mcMusicTicker" : "field_147126_aw");
            ftc.setAccessible(true);
            ftc.set(Minecraft.getMinecraft(), new GSMusicTicker(Minecraft.getMinecraft()));
        } catch (Exception e) {e.printStackTrace();} 
    	*/
    	ItemSchematics.registerTextures();
    	
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityModernSolarPanel.class, new TileEntityModernSolarPanelRenderer());
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWindGenerator.class, new TileEntityWindGeneratorRenderer());
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHydroponicFarm.class, new TileEntityHydroponicFarmRenderer());
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTreasureChestCeres.class, new TileEntityTreasureChestRenderer(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/model/treasure_ceres.png")));
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTreasureChestIo.class, new TileEntityTreasureChestRenderer( new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/model/treasure_io.png")));
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlockCrystallTE.class, new TileEntityBlockCrystall());
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWindSolarPanel.class, new TileEntitySolarWindPanelRenderer());
    
	}
    
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onModelBakeEvent(ModelBakeEvent event)
    {
    	Quat4f rot = TRSRTransformation.quatFromXYZDegrees(new Vector3f(30, 225, 0));
        replaceModelDefault(event, "hydroponic_farm", "hydroponic_farm.obj", ImmutableList.of("ferma_2"), ItemRendererHydroponicFarm.class, new TRSRTransformation(new javax.vecmath.Vector3f(0.7F, 0.1F, 0.0F), rot, new javax.vecmath.Vector3f(0.2604F, 0.2604F, 0.2604F), new javax.vecmath.Quat4f()), "inventory", "normal");
        replaceModelDefault(event, "rockets/rocket_t4", "tier4rocket.obj", ImmutableList.of("Base"), ItemModelRocketT4.class, TRSRTransformation.identity());
        //replaceModelDefault(event, "rockets/rocket_t4", "tier4rocketGS.obj", ImmutableList.of("Base", "NoseCone", "Rocket", "Booster1", "Booster2", "Booster3", "Booster4"), ItemModelRocketT4.class, TRSRTransformation.identity());
        replaceModelDefault(event, "rockets/rocket_t5", "tier5rocket.obj", ImmutableList.of("Base"), ItemModelRocketT5.class, TRSRTransformation.identity());
        replaceModelDefault(event, "rockets/rocket_t6", "tier6rocket.obj", ImmutableList.of("Base"), ItemModelRocketT6.class, TRSRTransformation.identity());
        replaceModelDefault(event, "rockets/rocket_cargo", "cargo_rocket.obj", ImmutableList.of("Rocket"), ItemModelCargoRocket.class, TRSRTransformation.identity());
        replaceModelDefault(event, "mars_rover", "mars_rover.obj", ImmutableList.of("base", "ltR", "ltL", "ltR.001", "ltL.001", "frontbase", "backbase", "frontWheelL", "frontWheelR", "backWheelL", "backWheelR"), ItemModelMarsRover.class, TRSRTransformation.identity());
        
        replaceModelDefault(event, "armor/jetpack", "jetpack.obj", ImmutableList.of("wing1", "wing2", "corp"), ItemRendererJetpack.class, TRSRTransformation.identity());
        //replaceModelDefault(event, "tools/matter_manipulator", "matter_manipulator.obj", ImmutableList.of("Up", "Down", "Mid"), ItemRendererMatterManipulator.class, TRSRTransformation.identity());

        
        if(!FMLClientHandler.instance().hasOptifine())
	        for (ModelResourceLocation resource : event.getModelRegistry().getKeys()) {
	            if (resource.getNamespace().equals(GalaxySpace.MODID)) {
	            	
	            	if(resource.getPath().equals("dungeon_blocks")) {
	            		
		            	if(resource.getVariant().contains("ceres_bricks"))	{            	
		            		event.getModelRegistry().putObject(resource, new BakedModelFullbright(event.getModelRegistry().getObject(resource), "galaxyspace:blocks/solarsystem/ceres/ceres_bricks_layer", 250, 0.45D));
		            		continue;
		            	}
		            	
		            	if(resource.getVariant().contains("io_bricks")) {
		            		event.getModelRegistry().putObject(resource, new BakedModelFullbright(event.getModelRegistry().getObject(resource), "galaxyspace:blocks/solarsystem/io/io_bricks_layer", 250, 1.0D));
		            		continue;
		            	}
		            }
	            	
	            	if(resource.getPath().equals("barnarda_c_test_glow_log")) {
	            		String top = "galaxyspace:blocks/barnardssystem/barnarda_c/log_oak_top";            		
	            		event.getModelRegistry().putObject(resource, new BakedModelBrightFour(event.getModelRegistry().getObject(resource), "galaxyspace:blocks/barnardssystem/barnarda_c/log_oak_layer", top, 250, 1.0D));
	            		continue;
	            	}
	            }
	        }
    }
    
    @SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void loadTextures(TextureStitchEvent.Pre event) {
		GalaxySpace.proxy.registerTexture(event, "model/modern_solarpanel");
		GalaxySpace.proxy.registerTexture(event, "model/armor/spacesuit");
		GalaxySpace.proxy.registerTexture(event, "model/armor/jetpack");
		GalaxySpace.proxy.registerTexture(event, "model/hydroponic_farm");	
		//GalaxySpace.proxy.registerTexture(event, "model/matter_manipulator");	
		GalaxySpace.proxy.registerTexture(event, "model/rocket_tier_4");
		GalaxySpace.proxy.registerTexture(event, "model/rocket_tier_4_launch");
		GalaxySpace.proxy.registerTexture(event, "model/rocket_tier_5");	
		GalaxySpace.proxy.registerTexture(event, "model/rocket_tier_6");			
		GalaxySpace.proxy.registerTexture(event, "model/cargo_rocket");	
		GalaxySpace.proxy.registerTexture(event, "model/mars_rover");	
		
		if(!FMLClientHandler.instance().hasOptifine()) {
			GalaxySpace.proxy.registerTexture(event, "blocks/solarsystem/ceres/ceres_bricks_layer");	
			GalaxySpace.proxy.registerTexture(event, "blocks/solarsystem/io/io_bricks_layer");
			GalaxySpace.proxy.registerTexture(event, "blocks/barnardssystem/barnarda_c/log_oak_layer");
		}
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onStitch(TextureStitchEvent.Post event) {
		GSUtils.initFluidTextures(event.getMap());
	}
	
    public void registerTexture(Pre event, String texture) {
		event.getMap().registerSprite(new ResourceLocation(GalaxySpace.TEXTURE_PREFIX + texture));
	}
    
    private void replaceModelDefault(ModelBakeEvent event, String resLoc, String objLoc, List<String> visibleGroups, Class<? extends ModelTransformWrapper> clazz, IModelState parentState, String... variants)
    {
        ClientUtil.replaceModel(GalaxySpace.ASSET_PREFIX, event, resLoc, objLoc, visibleGroups, clazz, parentState, variants);
    }
        
    @Override
    public void registerRender()
    {			

    	String[] name = new String[4];
    	
    	//BLOCKS META
    	for (MercuryBlocks.EnumBlockMercury blockBasic : MercuryBlocks.EnumBlockMercury.values())        
    		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.MERCURY_BLOCKS, blockBasic.getMeta(), blockBasic.getName());
    	
    	for (BlockFutureGlasses.EnumBlockFutureGlass blockBasic : BlockFutureGlasses.EnumBlockFutureGlass.values())
		    ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.FUTURE_GLASS_COLORED, blockBasic.getMeta(), blockBasic.getName());

		for(BlockOres.EnumBlockOres block : BlockOres.EnumBlockOres.values())
			ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.OVERWORLD_ORES, block.getMeta(), block.getName());
		
		for(BlockDecoMetals.EnumBlockDecoMetals block : BlockDecoMetals.EnumBlockDecoMetals.values())
			ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.DECO_METALS, block.getMeta(), block.getName());

		for(MarsOresBlocks.EnumMarsOresBlocks block : MarsOresBlocks.EnumMarsOresBlocks.values())
			ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.MARS_ORES, block.getMeta(), block.getName());
				
		for(CeresBlocks.EnumCeresBlocks block : CeresBlocks.EnumCeresBlocks.values())		
			ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.CERES_BLOCKS, block.getMeta(), block.getName());			
						
		for(PlutoBlocks.EnumPlutoBlocks block : PlutoBlocks.EnumPlutoBlocks.values())
			ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.PLUTO_BLOCKS, block.getMeta(), block.getName());
		
		for(HaumeaBlocks.EnumHaumeaBlocks block : HaumeaBlocks.EnumHaumeaBlocks.values())
			ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.HAUMEA_BLOCKS, block.getMeta(), block.getName());
		
		for(IoBlocks.EnumIoBlocks block : IoBlocks.EnumIoBlocks.values())
			ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.IO_BLOCKS, block.getMeta(), block.getName());
		
		for(EuropaBlocks.EnumEuropaBlocks block : EuropaBlocks.EnumEuropaBlocks.values())
			ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.EUROPA_BLOCKS, block.getMeta(), block.getName());
		
		for(GanymedeBlocks.EnumGanymedeBlocks block : GanymedeBlocks.EnumGanymedeBlocks.values())
			ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.GANYMEDE_BLOCKS, block.getMeta(), block.getName());
		
		for(CallistoBlocks.EnumCallistoBlocks block : CallistoBlocks.EnumCallistoBlocks.values())
			ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.CALLISTO_BLOCKS, block.getMeta(), block.getName());
		
		for(EnceladusBlocks.EnumEnceladusBlocks block : EnceladusBlocks.EnumEnceladusBlocks.values())
			ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.ENCELADUS_BLOCKS, block.getMeta(), block.getName());
		
		for(TitanBlocks.EnumTitanBlocks block : TitanBlocks.EnumTitanBlocks.values())
			ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.TITAN_BLOCKS, block.getMeta(), block.getName());
			
		for(MirandaBlocks.EnumMirandaBlocks block : MirandaBlocks.EnumMirandaBlocks.values())
			ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.MIRANDA_BLOCKS, block.getMeta(), block.getName());
			
		for(TritonBlocks.EnumTritonBlocks block : TritonBlocks.EnumTritonBlocks.values())
			ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.TRITON_BLOCKS, block.getMeta(), block.getName());
		
		for(BlockMachineFrames.EnumBlockMachineFrames block : BlockMachineFrames.EnumBlockMachineFrames.values())
			ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.MACHINE_FRAMES, block.getMeta(), block.getName());
		
		for(DungeonBlocks.EnumDungeonBlocks block : DungeonBlocks.EnumDungeonBlocks.values())
			ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.DUNGEON_BLOCKS, block.getMeta(), block.getName());
		
		for(BlockSurfaceIce.EnumBlockIce block : BlockSurfaceIce.EnumBlockIce.values())
			ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.SURFACE_ICE, block.getMeta(), block.getName());
		
		name = new String[PhobosBlocks.EnumPhobosBlocks.values().length];
		for(PhobosBlocks.EnumPhobosBlocks block : PhobosBlocks.EnumPhobosBlocks.values())
		{
			if(block.getName() != null) name[block.getMeta()] = block.getName();
			ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.PHOBOS_BLOCKS, block.getMeta(), block.getName());
		}
		
		if(GCCoreUtil.isDeobfuscated()) 
			GSUtils.addBlockMetadataJsonFiles(GSBlocks.PHOBOS_BLOCKS, name, PhobosBlocks.BASIC_TYPE.getName(), "");
		
		
		//--------------------------- BLOCKS -----------------------------------
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.ASSEMBLER);		
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.FUEL_GENERATOR);	
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.MODERN_SOLAR_PANEL);
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.FUTURE_GLASS_BASIC);		
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.EUROPA_GEYSER);		
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.EUROPA_UWGEYSER);	
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.WIND_GENERATOR);	
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.ROCKET_ASSEMBLER);	
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.UNIVERSAL_RECYCLER);	
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.LIQUID_EXTRACTOR);	
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.LIQUID_SEPARATOR);
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.HYDROPONIC_BASE);	
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.HYDROPONIC_FARM);	
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.GRAVITATION_MODULE);	
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.BOSS_SPAWNER_CERES);
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.BOSS_SPAWNER_IO);
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.TREASURE_CHEST_TIER_4, 0, "treasure_t4");	
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.TREASURE_CHEST_TIER_5, 0, "treasure_t5");	
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.RADIATION_STABILISER);
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.ENCELADUS_CRYSTAL);	
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.PANEL_CONTROLLER);	
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.SINGLE_SOLARPANEL);
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.MODERN_SINGLE_SOLARPANEL);	
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.PLANET_SHIELD);	
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.MODERN_STORAGE_MODULE);
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.ADVANCED_LANDING_PAD_SINGLE);
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.ADVANCED_LANDING_PAD);
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.FAKE_BLOCK);
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.MODIFICATION_TABLE);
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.GAS_BURNER);
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.OXYGEN_STORAGE_MODULE);
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.SOLARWIND_PANEL);
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.ADVANCED_CIRCUIT_FABRICATOR);
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.ENERGY_PAD);
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.GAS_COLLECTOR);
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.GAS_GENERATOR);
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.DRY_LEAVES);
		
		if(GCCoreUtil.isDeobfuscated()) 
		{
			//GSUtils.addBlockJsonFiles(GSBlocks.SOLARWIND_PANEL, "overworld/");
			//GSUtils.addBlockJsonFiles(GSBlocks.ENERGY_PAD, "overworld/");
		}
		
		// -------------------------- ITEMS ------------------------------------
		int i = 0;
		for(BasicItems basic : ItemBasicGS.BasicItems.values())
		{
			if(basic.getName().equals("null")) { i++; continue; }
			ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.BASIC, i++, "basic/" + basic.getName());
		}
		
		i = 0;
		for(String ingots : ItemIngots.names) {		
			ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.INGOTS, i, "ingots/" + ingots);		
			ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.NUGGETS, i++, "nuggets/" + ingots);
		}
		
		i = 0;
		for(String hdp : ItemHeavyDutyPlates.names)		
			ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.HDP, i++, "hdp/" + hdp);
		
		i = 0;
		for(String plates : ItemCompressedPlates.names)		
			ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.COMPRESSED_PLATES, i++, "compressed_plates/" + plates);
		
		i = 0;
		for(String modules : ItemRocketModules.names)		
			ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.ROCKET_MODULES, i++, "rocket_modules/" + modules);
		
		i = 0;
		for(String parts : ItemRocketParts.names)		
			ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.ROCKET_PARTS, i++, "rocket_parts/" + parts);
		
		i = 0;
		for(String keys : ItemTierKeysChest.keys)		
			ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.DUNGEON_KEYS, i++, "keys/" + keys);
		
		i = 0;
		for(String upgrades : ItemUpgrades.names)		
			ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.UPGRADES, i++, "upgrades/" + upgrades);
		
		i = 0;
		for(String thermal_padding : ItemThermalPaddingBase.names) {		
			ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.THERMAL_PADDING_3, i, "thermal_padding/" + thermal_padding + "_t" + ((ItemThermalPaddingBase) GSItems.THERMAL_PADDING_3).getThermalStrength());
			ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.THERMAL_PADDING_4, i, "thermal_padding/" + thermal_padding + "_t" + ((ItemThermalPaddingBase) GSItems.THERMAL_PADDING_4).getThermalStrength());
			i++;
		}
		
		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.OXYGENTANK_TIER_4);
		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.OXYGENTANK_TIER_5);
		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.OXYGENTANK_TIER_6);
		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.OXYGENTANK_TIER_EPP);
		
		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.ADVANCED_BATTERY, 0, "batteries/" + GSItems.ADVANCED_BATTERY.getTranslationKey().substring(5));
		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.MODERN_BATTERY, 0, "batteries/" + GSItems.MODERN_BATTERY.getTranslationKey().substring(5));
		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.EXTRA_BATTERY, 0, "batteries/" + GSItems.EXTRA_BATTERY.getTranslationKey().substring(5));
		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.ULTIMATE_BATTERY, 0, "batteries/" + GSItems.ULTIMATE_BATTERY.getTranslationKey().substring(5));
		
		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.SPACE_SUIT_HELMET, 0, "armor/" + GSItems.SPACE_SUIT_HELMET.getTranslationKey().substring(5));
		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.SPACE_SUIT_BODY, 0, "armor/" + GSItems.SPACE_SUIT_BODY.getTranslationKey().substring(5));
		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.SPACE_SUIT_LEGGINS, 0, "armor/" + GSItems.SPACE_SUIT_LEGGINS.getTranslationKey().substring(5));
		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.SPACE_SUIT_BOOTS, 0, "armor/" + GSItems.SPACE_SUIT_BOOTS.getTranslationKey().substring(5));
		
		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.SPACE_SUIT_LIGHT_HELMET, 0, "armor/" + GSItems.SPACE_SUIT_LIGHT_HELMET.getTranslationKey().substring(5));
		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.SPACE_SUIT_LIGHT_BODY, 0, "armor/" + GSItems.SPACE_SUIT_LIGHT_BODY.getTranslationKey().substring(5));
		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.SPACE_SUIT_LIGHT_LEGGINS, 0, "armor/" + GSItems.SPACE_SUIT_LIGHT_LEGGINS.getTranslationKey().substring(5));
		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.SPACE_SUIT_LIGHT_BOOTS, 0, "armor/" + GSItems.SPACE_SUIT_LIGHT_BOOTS.getTranslationKey().substring(5));
		
		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.JETPACK, 0, "armor/" + GSItems.JETPACK.getTranslationKey().substring(5));
		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.MATTER_MANIPULATOR, 0, "tools/" + GSItems.MATTER_MANIPULATOR.getTranslationKey().substring(5));
		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.GEOLOGICAL_SCANNER, 0, "tools/" + GSItems.GEOLOGICAL_SCANNER.getTranslationKey().substring(5));
		
		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.COBALT_HELMET, 0, "armor/" + GSItems.COBALT_HELMET.getTranslationKey().substring(5));
		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.COBALT_CHEST, 0, "armor/" + GSItems.COBALT_CHEST.getTranslationKey().substring(5));
		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.COBALT_LEGS, 0, "armor/" + GSItems.COBALT_LEGS.getTranslationKey().substring(5));
		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.COBALT_BOOTS, 0, "armor/" + GSItems.COBALT_BOOTS.getTranslationKey().substring(5));
			
		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.COBALT_SWORD, 0, "tools/" + GSItems.COBALT_SWORD.getTranslationKey().substring(5));
		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.COBALT_AXE, 0, "tools/" + GSItems.COBALT_AXE.getTranslationKey().substring(5));
		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.COBALT_PICKAXE, 0, "tools/" + GSItems.COBALT_PICKAXE.getTranslationKey().substring(5));
		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.COBALT_SPADE, 0, "tools/" + GSItems.COBALT_SPADE.getTranslationKey().substring(5));
		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.COBALT_HOE, 0, "tools/" + GSItems.COBALT_HOE.getTranslationKey().substring(5));
		
		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.PLASMA_SWORD, 0, "tools/" + GSItems.PLASMA_SWORD.getTranslationKey().substring(5));
		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.PLASMA_AXE, 0, "tools/" + GSItems.PLASMA_AXE.getTranslationKey().substring(5));
		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.PLASMA_PICKAXE, 0, "tools/" + GSItems.PLASMA_PICKAXE.getTranslationKey().substring(5));
	
		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.SCHEMATICS, 0, "schematics/" + "schematic_cone");
		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.SCHEMATICS, 1, "schematics/" + "schematic_body");
		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.SCHEMATICS, 2, "schematics/" + "schematic_engine");
		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.SCHEMATICS, 3, "schematics/" + "schematic_booster");
		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.SCHEMATICS, 4, "schematics/" + "schematic_stabiliser");
		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.SCHEMATICS, 5, "schematics/" + "schematic_oxygen_tank");
		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.SCHEMATICS, 6, "schematics/" + "schematic_port_nuclear_reactor");
						
		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.MARS_ROVER, 0, "mars_rover");
		
		if(GCCoreUtil.isDeobfuscated()) {
			//GSUtils.addItemJsonFiles(GSItems.JETPACK, "armor/", GSItems.JETPACK.getUnlocalizedName().substring(5));		
			//GSUtils.addItemJsonFiles(GSItems.PLASMA_SWORD, "tools/", GSItems.PLASMA_SWORD.getUnlocalizedName().substring(5));		
			//GSUtils.addItemJsonFiles(GSItems.COBALT_HOE, "tools/", GSItems.COBALT_HOE.getUnlocalizedName().substring(5));		
			//GSUtils.addItemJsonFiles(GSItems.COBALT_LEGS, "armor/", GSItems.COBALT_LEGS.getUnlocalizedName().substring(5));		
			//GSUtils.addItemJsonFiles(GSItems.COBALT_BOOTS, "armor/", GSItems.COBALT_BOOTS.getUnlocalizedName().substring(5));		
			GSUtils.addItemMetadataJsonFiles(GSItems.BASIC, ItemBasicGS.getEnumNames(), "basic/");
			GSUtils.addItemMetadataJsonFiles(GSItems.ROCKET_MODULES, ItemRocketModules.names, "rocket_modules/");
		}
		
		for(IBodies list : AsmodeusCore.bodies)
			if(list.canRegister()) 
				list.registerRender();

    }
    
    @Override
    public void registerVariants()
    {
    	//Blocks
    	String[] blocks = new String[BlockFutureGlasses.EnumBlockFutureGlass.values().length];
    	for(int i = 0; i < blocks.length; i++)
    		blocks[i] = BlockFutureGlasses.EnumBlockFutureGlass.byMetadata(i).getName();
    	
    	addVariant("futureglass", "", blocks);
    	addVariant("mercuryblocks", "", "mercury_surface", "mercury_subsurface", "mercury_stone", "mercury_nickel_ore", "mercury_iron_ore", "mercury_magnesium_ore");
    	addVariant("gsores", "", "cobaltum_ore", "nickel_ore", "uranium_ore");
    	addVariant("decoblocks", "", "deco_cobaltum", "deco_magnesium", "deco_nickel", "deco_copper", "cobalt_block", "nickel_block", "magnesium_block");
    	addVariant("marsores", "", "mars_diamond", "mars_gold", "mars_coal", "mars_redstone", "mars_silicon", "mars_aluminum");
    	addVariant("ceresblocks", "", "ceres_grunt", "ceres_subgrunt", "ceres_dolomite_ore", "ceres_meteoriciron_ore", "ceres_dungeon_top", "ceres_dungeon_floor");
    	addVariant("plutoblocks", "", "pluto_grunt_1", "pluto_grunt_2", "pluto_grunt_3", "pluto_grunt_4", "pluto_subgrunt", "pluto_stone");
    	addVariant("phobosblocks", "", "phobos_regolite", "phobos_stone", "phobos_iron_ore", "phobos_meteoriciron_ore", "phobos_nickel_ore", "phobos_desh_ore");
    	addVariant("ioblocks", "", "io_grunt", "io_stone", "io_ash", "io_copper_ore", "io_sulfur_ore", "io_volcanic_ore", "io_lava_geyser", "io_sulfur_geyser", "io_top", "io_floor", "io_dungeon_bricks");
    	//addVariant("europablocks", "", "europa_grunt", "europa_stone", "europa_brown_ice", "europa_emerald_ore", "europa_silicon_ore", "europa_aluminum_ore");
    	addVariant("ganymedeblocks", "", "ganymede_grunt", "ganymede_stone", "ganymede_magnesium_ore", "ganymede_titanium_ore");
    	addVariant("callistoblocks", "", "callisto_grunt", "callisto_stone");
    	addVariant("enceladusblocks", "", "enceladus_snow", "enceladus_grunt", "enceladus_coal_ore");
    	
    	
    	blocks = new String[EuropaBlocks.EnumEuropaBlocks.values().length];
    	for(int i = 0; i < blocks.length; i++)
    		blocks[i] = EuropaBlocks.EnumEuropaBlocks.byMetadata(i).getName();
    	
    	addVariant("europablocks", "", blocks);
    	
    	blocks = new String[TitanBlocks.EnumTitanBlocks.values().length];
    	for(int i = 0; i < blocks.length; i++)
    		blocks[i] = TitanBlocks.EnumTitanBlocks.byMetadata(i).getName();
    	
    	addVariant("titanblocks", "", blocks);
    	
    	blocks = new String[MirandaBlocks.EnumMirandaBlocks.values().length];
    	for(int i = 0; i < blocks.length; i++)
    		blocks[i] = MirandaBlocks.EnumMirandaBlocks.byMetadata(i).getName();
    	
    	addVariant("mirandablocks", "", blocks);
    	
    	blocks = new String[TritonBlocks.EnumTritonBlocks.values().length];
    	for(int i = 0; i < blocks.length; i++)
    		blocks[i] = TritonBlocks.EnumTritonBlocks.byMetadata(i).getName();
    	
    	addVariant("tritonblocks", "", blocks);
    	
    	blocks = new String[HaumeaBlocks.EnumHaumeaBlocks.values().length];
    	for(int i = 0; i < blocks.length; i++)
    		blocks[i] = HaumeaBlocks.EnumHaumeaBlocks.byMetadata(i).getName();
    	
    	addVariant("haumeablocks", "", blocks);
    	
    	
    	blocks = new String[BlockSurfaceIce.EnumBlockIce.values().length];
    	for(int i = 0; i < blocks.length; i++)
    		blocks[i] = BlockSurfaceIce.EnumBlockIce.byMetadata(i).getName();    	
    	addVariant("surface_ice", "", blocks);
    	
    	addVariant("machineframes", "", "basic_frame", "advance_frame", "modern_frame");
    	
    	addVariant("dungeon_blocks", "", "ceres_bricks", "io_bricks");
    	
    	//addVariant("advanced_landing_pad", "", "rocket_pad");
    	
    	//Fluids
    	Item sludge = Item.getItemFromBlock(GSFluids.BLOCK_LEMETHANE);
    	registerLiquid(sludge, GSFluids.BLOCK_LEMETHANE, "liquid_ethanemethane", LEMethaneLocation);
        
        sludge = Item.getItemFromBlock(GSFluids.BLOCK_HELIUM3);
        registerLiquid(sludge, GSFluids.BLOCK_HELIUM3, "liquid_helium", HeliumLocation);

        sludge = Item.getItemFromBlock(GSFluids.BLOCK_HELIUM_HYDROGEN);
        registerLiquid(sludge, GSFluids.BLOCK_HELIUM_HYDROGEN, "liquid_heliumhydrogen", HeliumHydrogenLocation);       
        
        sludge = Item.getItemFromBlock(GSFluids.BLOCK_ETHANE);
        registerLiquid(sludge, GSFluids.BLOCK_ETHANE, "liquid_ethane", EthaneLocation);
        
        sludge = Item.getItemFromBlock(GSFluids.BLOCK_NATURE_GAS);
        registerLiquid(sludge, GSFluids.BLOCK_NATURE_GAS, "liquid_naturegas", NatureGasLocation);
        
        //Items
        addVariant("cobalt_sword", "tools/", "cobalt_sword");
        addVariant("cobalt_axe", "tools/", "cobalt_axe");
        addVariant("cobalt_pickaxe", "tools/", "cobalt_pickaxe");
        addVariant("cobalt_spade", "tools/", "cobalt_spade");
        addVariant("cobalt_hoe", "tools/", "cobalt_hoe");
        
        addVariant("plasma_sword", "tools/", "plasma_sword");
        addVariant("plasma_axe", "tools/", "plasma_axe");
        addVariant("plasma_pickaxe", "tools/", "plasma_pickaxe");
        
        addVariant("advanced_battery", "batteries/", "advanced_battery");
        addVariant("modern_battery", "batteries/", "modern_battery");
        addVariant("extra_battery", "batteries/", "extra_battery");
        addVariant("ultimate_battery", "batteries/", "ultimate_battery");
        
        addVariant("space_suit_head", "armor/", "space_suit_head");
        addVariant("space_suit_chest", "armor/", "space_suit_chest");
        addVariant("space_suit_legs", "armor/", "space_suit_legs");
        addVariant("space_suit_feet", "armor/", "space_suit_feet");
        
        addVariant("space_suit_light_head", "armor/", "space_suit_light_head");
        addVariant("space_suit_light_chest", "armor/", "space_suit_light_chest");
        addVariant("space_suit_light_legs", "armor/", "space_suit_light_legs");
        addVariant("space_suit_light_feet", "armor/", "space_suit_light_feet");
        
        addVariant("jetpack", "armor/", "jetpack");
        //addVariant("matter_manipulator", "tools/", "matter_manipulator");
        addVariant("geo_scanner", "tools/", "geo_scanner");
        
        addVariant("cobalt_helmet", "armor/", "cobalt_helmet");
        addVariant("cobalt_chest", "armor/", "cobalt_chest");
        addVariant("cobalt_legs", "armor/", "cobalt_legs");
        addVariant("cobalt_boots", "armor/", "cobalt_boots");
        
        
        addVariant("gs_basic", "basic/", ItemBasicGS.getEnumNames());
        addVariant("ingots", "ingots/", ItemIngots.names);        
        addVariant("nuggets", "nuggets/", ItemIngots.names);        
        addVariant("hdp", "hdp/", ItemHeavyDutyPlates.names);
        addVariant("compressed_plates", "compressed_plates/", ItemCompressedPlates.names);
        addVariant("rocket_modules", "rocket_modules/", ItemRocketModules.names);
        addVariant("rocket_parts", "rocket_parts/", ItemRocketParts.names);
        addVariant("schematics", "schematics/", "schematic_cone", "schematic_body", "schematic_engine", "schematic_booster", "schematic_stabiliser", "schematic_oxygen_tank", "schematic_port_nuclear_reactor");
        addVariant("dungeon_keys", "keys/", ItemTierKeysChest.keys);
        addVariant("upgrades", "upgrades/", ItemUpgrades.names);
        
        addVariant("thermal_padding_t3", "thermal_padding/", "thermal_helm_t3", "thermal_chestplate_t3", "thermal_leggings_t3", "thermal_boots_t3");
        addVariant("thermal_padding_t4", "thermal_padding/", "thermal_helm_t4", "thermal_chestplate_t4", "thermal_leggings_t4", "thermal_boots_t4");
        
        ModelResourceLocation modelResourceLocation = new ModelResourceLocation("galaxyspace:rockets/rocket_t4", "inventory");
        for (int i = 0; i < 5; ++i)
        {
            ModelLoader.setCustomModelResourceLocation(GSItems.ROCKET_TIER_4, i, modelResourceLocation);
        }
    
        modelResourceLocation = new ModelResourceLocation("galaxyspace:rockets/rocket_t5", "inventory");
        for (int i = 0; i < 5; ++i)
        {
            ModelLoader.setCustomModelResourceLocation(GSItems.ROCKET_TIER_5, i, modelResourceLocation);
        }
        
        modelResourceLocation = new ModelResourceLocation("galaxyspace:rockets/rocket_t6", "inventory");
        for (int i = 0; i < 5; ++i)
        {
            ModelLoader.setCustomModelResourceLocation(GSItems.ROCKET_TIER_6, i, modelResourceLocation);
        }
        
        modelResourceLocation = new ModelResourceLocation("galaxyspace:rockets/rocket_cargo", "inventory");
        for (int i = 0; i < 5; ++i)
        {
            ModelLoader.setCustomModelResourceLocation(GSItems.ROCKET_FLUID_CRAGO, i, modelResourceLocation);
        }
        
        modelResourceLocation = new ModelResourceLocation("galaxyspace:mars_rover", "inventory");
        //for (int i = 0; i < 5; ++i)
        //{
            ModelLoader.setCustomModelResourceLocation(GSItems.MARS_ROVER, 0, modelResourceLocation);
       // }
        
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(GSBlocks.DUNGEON_BLOCKS), 0, new ModelResourceLocation("galaxyspace:ceres_bricks", "inventory"));
        ModelLoader.setCustomModelResourceLocation(GSItems.JETPACK, 0, new ModelResourceLocation("galaxyspace:armor/jetpack", "inventory"));
   
        ModelLoader.setCustomModelResourceLocation(GSItems.MATTER_MANIPULATOR, 0, new ModelResourceLocation("galaxyspace:tools/matter_manipulator", "inventory"));
        
        for(IBodies list : AsmodeusCore.bodies)
			if(list.canRegister()) 
				list.registerVariant();
    }

    
    @Override
    public void registerItemRenderer(Item item, int meta, String id) {
    	ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
    }
    
    @Override
    public void spawnParticle(String particleID, Vector3 position, Vector3 motion, Object[] otherInfo)
    {
       GSEffectHandler.spawnParticle(particleID, position, motion, otherInfo);
    }

	public static void addVariant(String name, String folder, String... variants)
    {
		Item itemBlockVariants = Item.REGISTRY.getObject(new ResourceLocation(GalaxySpace.MODID, name));
		ResourceLocation[] variants0 = new ResourceLocation[variants.length];
		for (int i = 0; i < variants.length; ++i) {
			variants0[i] = new ResourceLocation(GalaxySpace.MODID + ":" + folder + variants[i]);
		}
		ModelBakery.registerItemVariants(itemBlockVariants, variants0);        
    }
	
	public static void registerLiquid(Item sludge, Block liquid_block, String liquid_name, ModelResourceLocation model) {
		ModelBakery.registerItemVariants(sludge, new ResourceLocation(GalaxySpace.TEXTURE_PREFIX + liquid_name));
		ModelLoader.setCustomMeshDefinition(sludge,	IItemMeshDefinitionCustom.create((ItemStack stack) -> model));
		ModelLoader.setCustomStateMapper(liquid_block, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return model;
			}
		});
	}
	
	public static void registerEntityRenderers()
    {
		RenderingRegistry.registerEntityRenderingHandler(EntityTier4Rocket.class, (RenderManager manager) -> new RenderRockets(manager, "tier4rocket", "Base"));
		//RenderingRegistry.registerEntityRenderingHandler(EntityTier4Rocket.class, (RenderManager manager) -> new RenderRockets(manager, "tier4rocketGS", "Base", "NoseCone", "Rocket", "Booster1", "Booster2", "Booster3", "Booster4"));
		RenderingRegistry.registerEntityRenderingHandler(EntityTier5Rocket.class, (RenderManager manager) -> new RenderRockets(manager, "tier5rocket", "Base"));
		RenderingRegistry.registerEntityRenderingHandler(EntityTier6Rocket.class, (RenderManager manager) -> new RenderRockets(manager, "tier6rocket", "Base"));
		RenderingRegistry.registerEntityRenderingHandler(EntityCustomCargoRocket.class, (RenderManager manager) -> new RenderCargoRockets(manager, "rockets/rocket_cargo"));
		RenderingRegistry.registerEntityRenderingHandler(EntityBossBlaze.class, (RenderManager manager) -> new RenderBossBlaze(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityIceSpike.class, (RenderManager manager) -> new RenderIceSpike(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityEvolvedColdBlaze.class, (RenderManager manager) -> new RenderEvolvedColdBlaze(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityBossGhast.class, (RenderManager manager) -> new RenderBossGhast(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityMarsRover.class, (RenderManager manager) -> new RenderMarsRover(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityAstroWolf.class, (RenderManager manager) -> new RenderAstroWolf(manager));
		
		//RenderingRegistry.registerEntityRenderingHandler(EntityLaserBeam.class, (RenderManager manager) -> new RenderLaserBeam(manager));	
    }
	
	public void register_event(Object obj)
	{
    	MinecraftForge.EVENT_BUS.register(obj);
	}
 }
