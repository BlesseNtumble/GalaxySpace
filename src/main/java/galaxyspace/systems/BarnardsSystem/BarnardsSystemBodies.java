package galaxyspace.systems.BarnardsSystem;

import java.io.File;

import asmodeuscore.core.astronomy.BodiesData;
import asmodeuscore.core.astronomy.BodiesHelper;
import asmodeuscore.core.astronomy.BodiesHelper.Galaxies;
import galaxyspace.GalaxySpace;
import galaxyspace.api.IBodies;
import galaxyspace.api.IBodiesHandler;
import galaxyspace.core.proxy.ClientProxy;
import galaxyspace.core.util.GSDimensions;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.BarnardsSystem.core.configs.BRConfigCore;
import galaxyspace.systems.BarnardsSystem.core.configs.BRConfigDimensions;
import galaxyspace.systems.BarnardsSystem.core.events.BRClientEventHandler;
import galaxyspace.systems.BarnardsSystem.core.events.BREventHandler;
import galaxyspace.systems.BarnardsSystem.core.registers.BRBlocks;
import galaxyspace.systems.BarnardsSystem.core.registers.BRItems;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Blocks.EnumBlockBarnardaC;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Dandelions.EnumBlockDandelions;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Grass.EnumBlockGrass;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Leaves.EnumBlockLeaves;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.dimension.TeleportTypeBarnarda_C;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.dimension.WorldProviderBarnarda_C_WE;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.items.ItemBasicBR;
import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody.ScalableDistance;
import micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.Moon;
import micdoodle8.mods.galacticraft.api.galaxies.Planet;
import micdoodle8.mods.galacticraft.api.galaxies.SolarSystem;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.AtmosphereInfo;
import micdoodle8.mods.galacticraft.core.util.ClientUtil;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.util.WorldUtil;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

@IBodiesHandler
public class BarnardsSystemBodies implements IBodies {

	public static SolarSystem BarnardsSystem;
	public static Planet Barnarda_B, Barnarda_C, Barnarda_D, Barnarda_E;
	public static Moon Barnarda_B1, Barnarda_C1, Barnarda_C2;
	
	@Override
	public void preInitialization(FMLPreInitializationEvent event) 
	{
		new BRConfigCore(new File(event.getModConfigurationDirectory(), "GalaxySpace/barnards/core.conf"));		
		new BRConfigDimensions(new File(event.getModConfigurationDirectory(), "GalaxySpace/barnards/dimensions.conf"));		
	}
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
			
		BarnardsSystem = BodiesHelper.registerSolarSystem(GalaxySpace.ASSET_PREFIX, "barnards", Galaxies.MILKYWAY.getName(), new Vector3(1.0F, -2.0F, 0.0F), "barnarda_a", 0.8F);
		Barnarda_B = (Planet) BodiesHelper.registerPlanet(BarnardsSystem, "barnarda_b", GalaxySpace.ASSET_PREFIX, null, -1, 6, (float) Math.PI, 1.5F, 0.5F, 3.9F);
			Barnarda_B1 = (Moon) BodiesHelper.registerMoon(Barnarda_B, "barnarda_b1", GalaxySpace.ASSET_PREFIX, null, -1, 6, (float) Math.PI / 2, 1.0F, 14.75F, 105.5F).setRingColorRGB(1.0F, 0.0F, 0.0F);
		Barnarda_C = (Planet) BodiesHelper.registerPlanet(BarnardsSystem, "barnarda_c", GalaxySpace.ASSET_PREFIX, WorldProviderBarnarda_C_WE.class, BRConfigDimensions.dimensionIDBarnardaC, 6, (float) Math.PI * 2, 1.0F, 0.75F, 6.9F).setRingColorRGB(0.0F, 1.0F, 0.0F).setAtmosphere(new AtmosphereInfo(true, true, false, 0.0F, 1.0F, 1.0F));		
			Barnarda_C1 = (Moon) BodiesHelper.registerMoon(Barnarda_C, "barnarda_c1", GalaxySpace.ASSET_PREFIX, null, -1, 6, (float) Math.PI / 2, 1.0F, 10.75F, 25.5F);
			Barnarda_C2 = (Moon) BodiesHelper.registerMoon(Barnarda_C, "barnarda_c2", GalaxySpace.ASSET_PREFIX, null, -1, 6, (float) Math.PI / 2, 1.0F, 19.75F, 30.5F);
		Barnarda_D = (Planet) BodiesHelper.registerPlanet(BarnardsSystem, "barnarda_d", GalaxySpace.ASSET_PREFIX, null, -1, 6, (float) Math.PI / 2, 1.0F, 1.25F, 105.9F).setRelativeDistanceFromCenter(new ScalableDistance(1.25F, 1.0F)).setRingColorRGB(1.0F, 0.0F, 0.0F);
		Barnarda_E = (Planet) BodiesHelper.registerPlanet(BarnardsSystem, "barnarda_e", GalaxySpace.ASSET_PREFIX, null, -1, 6, (float) Math.PI, 1.0F, 1.75F, 15.9F);
			
		if(event.getSide() == Side.CLIENT)
			GalaxySpace.proxy.register_event(new BRClientEventHandler());		
		GalaxySpace.proxy.register_event(new BREventHandler());		
		
		BRBlocks.initialize();
		BRItems.initialize();
		
		registrycelestial();
	    registryteleport();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {		
		GSDimensions.BARNARDA_C = WorldUtil.getDimensionTypeById(BRConfigDimensions.dimensionIDBarnardaC);
	}

	private static void registrycelestial()
	{
		GalaxyRegistry.registerSolarSystem(BarnardsSystem);		
		
		BodiesData unreachableData = new BodiesData(null, 0F, 0, 0, false);
		BodiesHelper.registerBody(Barnarda_B, unreachableData, true);		
		BodiesHelper.registerBody(Barnarda_D, unreachableData, true);
		BodiesHelper.registerBody(Barnarda_E, unreachableData, true);
		
		BodiesHelper.registerBody(Barnarda_B1, unreachableData, true);
		BodiesHelper.registerBody(Barnarda_C1, unreachableData, true);
		BodiesHelper.registerBody(Barnarda_C2, unreachableData, true);
		
		BodiesData data = new BodiesData(null, BodiesHelper.calculateGravity(8.5F), 3, 29000, false);
		BodiesHelper.registerBody(Barnarda_C, data, true);
	}
	
	private static void registryteleport()
	{
		GalacticraftRegistry.registerTeleportType(WorldProviderBarnarda_C_WE.class, new TeleportTypeBarnarda_C());		
	}
	
	@Override
	public void registerRender() {
	//	if(BRConfigCore.enableBarnardsSystems) {	
		
		for (EnumBlockBarnardaC blockBasic : EnumBlockBarnardaC.values())        
			ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, BRBlocks.BARNARDA_C_BLOCKS, blockBasic.getMeta(), "barnarda/" + blockBasic.getName());
			
		for (EnumBlockDandelions blockBasic : EnumBlockDandelions.values())       
			ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, BRBlocks.BARNARDA_C_DANDELIONS, blockBasic.getMeta(), "barnarda/" + blockBasic.getName());
		
			
		//String[] name = new String[EnumBlockGrass.values().length];
		for (EnumBlockGrass blockBasic : EnumBlockGrass.values()) {       
			//if(blockBasic.getName() != null) name[blockBasic.getMeta()] = blockBasic.getName();
	    	ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, BRBlocks.BARNARDA_C_GRASS, blockBasic.getMeta(), "barnarda/" + blockBasic.getName());
		}
			
		for (EnumBlockLeaves blockBasic : EnumBlockLeaves.values())      
	    	ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, BRBlocks.BARNARDA_C_LEAVES, blockBasic.getMeta(), "barnarda/" + blockBasic.getName());
			
			
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX + "barnarda/",  BRBlocks.BARNARDA_C_FARMLAND);
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX + "barnarda/",  BRBlocks.BARNARDA_C_WATER_GRASS);
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX + "barnarda/",  BRBlocks.BARNARDA_C_TEST_LOG);
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX + "barnarda/",  BRBlocks.BARNARDA_C_TEST_GLOW_LOG);
			
		int i = 0;
		for(String basic : ItemBasicBR.names)
		{
			if(basic.equals("null")) { i++; continue; }
			ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, BRItems.BASIC, i++, "barnarda/basic/" + basic);
		}
			
		if(GCCoreUtil.isDeobfuscated()) {
			//GSUtils.addBlockJsonFiles(BRBlocks.BARNARDA_C_WATER_GRASS, "barnarda/");
			//GSUtils.addItemMetadataJsonFiles(BRItems.BASIC, ItemBasicBR.names, "barnarda/basic/");
		}
			//if(GCCoreUtil.isDeobfuscated()) 
				//GSUtils.addBlockMetadataJsonFiles(BRBlocks.BARNARDA_C_GRASS, name, Barnarda_C_Grass.BASIC_TYPE.getName(), "barnarda/");
			
	//	}
	}

	@Override
	public void registerVariant() {
		String[] blocks = new String[EnumBlockBarnardaC.values().length];
	    for(int i = 0; i < blocks.length; i++)
	    	blocks[i] = EnumBlockBarnardaC.byMetadata(i).getName(); 
	    	
		ClientProxy.addVariant("barnarda_c_blocks", "barnarda/", blocks);			
		ClientProxy.addVariant("barnarda_c_test_log", "barnarda/", "barnarda_c_test_log");		
		ClientProxy.addVariant("barnarda_c_test_glow_log", "barnarda/", "barnarda_c_test_glow_log");
		ClientProxy.addVariant("barnarda_c_farmland", "barnarda/", "barnarda_c_farmland");
		ClientProxy.addVariant("barnarda_c_water_grass", "barnarda/", "barnarda_c_water_grass");
			
		blocks = new String[EnumBlockDandelions.values().length];
	    for(int i = 0; i < blocks.length; i++)
	    	blocks[i] = EnumBlockDandelions.byMetadata(i).getName();
	    	
	    ClientProxy.addVariant("barnarda_c_dandelions", "barnarda/", blocks);
	    	
	    blocks = new String[EnumBlockGrass.values().length];
	    for(int i = 0; i < blocks.length; i++)
	    	blocks[i] = EnumBlockGrass.byMetadata(i).getName();
	    	
	    ClientProxy.addVariant("barnarda_c_grasses", "barnarda/", blocks);
	    	
	    blocks = new String[EnumBlockLeaves.values().length];
	    for(int i = 0; i < blocks.length; i++)
	    	blocks[i] = EnumBlockLeaves.byMetadata(i).getName();
	    	
	    ClientProxy.addVariant("barnarda_c_leaves", "barnarda/", blocks);

	    //ModelLoader.setCustomStateMapper(BRBlocks.BARNARDA_C_REEDS, new StateMap.Builder().ignore(BlockLiquid.LEVEL).build());
		

	    ClientProxy.addVariant("br_basic", "barnarda/basic/", ItemBasicBR.names);
	}
	
	@Override
	public void registerRecipes() {
		
	}

	@Override
	public boolean canRegister() {
		return BRConfigCore.enableBarnardsSystems;
	}
}
