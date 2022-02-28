package galaxyspace.systems.BarnardsSystem;

import java.io.File;

import asmodeuscore.api.IBodies;
import asmodeuscore.api.IBodiesHandler;
import asmodeuscore.api.dimension.IAdvancedSpace.StarClass;
import asmodeuscore.api.dimension.IAdvancedSpace.StarColor;
import asmodeuscore.api.dimension.IAdvancedSpace.TypeBody;
import asmodeuscore.core.astronomy.BodiesData;
import asmodeuscore.core.astronomy.BodiesRegistry;
import asmodeuscore.core.astronomy.BodiesRegistry.Galaxies;
import asmodeuscore.core.astronomy.dimension.world.gen.ACBiome;
import asmodeuscore.core.handler.ColorBlockHandler;
import asmodeuscore.core.prefab.TeleportTypeBody;
import galaxyspace.GalaxySpace;
import galaxyspace.core.proxy.ClientProxy;
import galaxyspace.core.util.GSDimensions;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.BarnardsSystem.core.BRBlocks;
import galaxyspace.systems.BarnardsSystem.core.BRItems;
import galaxyspace.systems.BarnardsSystem.core.configs.BRConfigCore;
import galaxyspace.systems.BarnardsSystem.core.configs.BRConfigDimensions;
import galaxyspace.systems.BarnardsSystem.core.events.BRClientEventHandler;
import galaxyspace.systems.BarnardsSystem.core.events.BREventHandler;
import galaxyspace.systems.BarnardsSystem.moons.barnarda_c1.dimension.WorldProviderBarnarda_C1_WE;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Blocks.EnumBlockBarnardaC;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Dandelions.EnumBlockDandelions;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Falling_Blocks.EnumFallingBlockBarnardaC;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Grass.EnumBlockGrass;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Leaves.EnumBlockLeaves;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Ores;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Ores.EnumBlockBarnardaCOres;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.dimension.TeleportTypeBarnarda_C;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.dimension.WorldProviderBarnarda_C_WE;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.items.ItemBasicBR;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.items.ItemFoodBR.BR_Food;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.recipes.CraftingRecipesBarnarda_C;
import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody.ScalableDistance;
import micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.Moon;
import micdoodle8.mods.galacticraft.api.galaxies.Planet;
import micdoodle8.mods.galacticraft.api.galaxies.SolarSystem;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.EnumAtmosphericGas;
import micdoodle8.mods.galacticraft.core.util.ClientUtil;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.util.WorldUtil;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

@IBodiesHandler
public class BarnardsSystemBodies implements IBodies {
	
	public static SolarSystem BarnardsSystem;
	public static Planet Barnarda_B, Barnarda_C, Barnarda_ABelt, Barnarda_E;
	public static Moon Barnarda_B1, Barnarda_C1, Barnarda_C2;
	
	@Override
	public void preInitialization(FMLPreInitializationEvent event) 
	{
		new BRConfigCore(new File(event.getModConfigurationDirectory(), "GalaxySpace/barnards/core.conf"));		
		new BRConfigDimensions(new File(event.getModConfigurationDirectory(), "GalaxySpace/barnards/dimensions.conf"));		
	}
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
			
		BarnardsSystem = BodiesRegistry.registerSolarSystem(GalaxySpace.ASSET_PREFIX, "barnards", Galaxies.MILKYWAY, new Vector3(1.0F, -2.0F, 0.0F), "barnarda_a", 0.8F);
		GalaxyRegistry.registerSolarSystem(BarnardsSystem);
		
		Barnarda_B = BodiesRegistry.registerExPlanet(BarnardsSystem, "barnarda_b", GalaxySpace.ASSET_PREFIX, 0.25F);
		BodiesRegistry.setOrbitData(Barnarda_B, (float) Math.PI, 2.0F, 3.9F);
		GalaxyRegistry.registerPlanet(Barnarda_B);
		
		Barnarda_B1 = BodiesRegistry.registerExMoon(Barnarda_B, "barnarda_b1", GalaxySpace.ASSET_PREFIX, 15F);
		Barnarda_B1.setRingColorRGB(1.1F, 0.0F, 0.0F);
		BodiesRegistry.setOrbitData(Barnarda_B1, (float) Math.PI / 2, 1.0F, 105.5F);
		GalaxyRegistry.registerMoon(Barnarda_B1);
		
		Barnarda_C = BodiesRegistry.registerExPlanet(BarnardsSystem, "barnarda_c", GalaxySpace.ASSET_PREFIX, 0.7F);
		BodiesRegistry.setOrbitData(Barnarda_C, (float) Math.PI * 2, 1.0F, 6.9F);
		BodiesRegistry.setAtmosphere(Barnarda_C, true, true, false, 0.0F, 1.0F, 1.0F);
		BodiesRegistry.setPlanetData(Barnarda_C, 3F, 24500, BodiesRegistry.calculateGravity(8.5F), false);
		BodiesRegistry.setProviderData(Barnarda_C, WorldProviderBarnarda_C_WE.class, BRConfigDimensions.dimensionIDBarnardaC, BRConfigCore.survivalModeOnBarnarda ? 1 : 6, ACBiome.ACSpace, ACBiome.ACSpaceLowHills);
		Barnarda_C.atmosphereComponent(EnumAtmosphericGas.CO2).atmosphereComponent(EnumAtmosphericGas.OXYGEN).atmosphereComponent(EnumAtmosphericGas.ARGON);
		GalaxyRegistry.registerPlanet(Barnarda_C);
		
		Barnarda_C1 = BodiesRegistry.registerExMoon(Barnarda_C, "barnarda_c1", GalaxySpace.ASSET_PREFIX, 10.75F);
		BodiesRegistry.setOrbitData(Barnarda_C1, (float) Math.PI / 2, 1.0F, 25.5F);
		if(GalaxySpace.debug) {
			BodiesRegistry.setAtmosphere(Barnarda_C1, false, false, false, -4.0F, 3.0F, 0.0F);
			BodiesRegistry.setPlanetData(Barnarda_C1, 15F, 45000, BodiesRegistry.calculateGravity(6.5F), false);
			BodiesRegistry.setProviderData(Barnarda_C1, WorldProviderBarnarda_C1_WE.class, BRConfigDimensions.dimensionIDBarnardaC1, BRConfigCore.survivalModeOnBarnarda ? 1 : 6, ACBiome.ACSpace);
		}
		Barnarda_C1.atmosphereComponent(EnumAtmosphericGas.CO2).atmosphereComponent(EnumAtmosphericGas.NITROGEN).atmosphereComponent(EnumAtmosphericGas.ARGON);
		GalaxyRegistry.registerMoon(Barnarda_C1);
		
		Barnarda_C2 = BodiesRegistry.registerExMoon(Barnarda_C, "barnarda_c2", GalaxySpace.ASSET_PREFIX, 19.75F);
		BodiesRegistry.setOrbitData(Barnarda_C2, (float) Math.PI / 2, 1.0F, 60.5F);
		BodiesRegistry.setAtmosphere(Barnarda_C2, false, false, false, -2.0F, 0.0F, 0.0F);
		BodiesRegistry.setPlanetData(Barnarda_C2, 15F, 45000, BodiesRegistry.calculateGravity(6.5F), false);
		//BodiesHelper.setProviderData(Barnarda_C1, WorldProviderBarnarda_C2_WE.class, BRConfigDimensions.dimensionIDBarnardaC2, 1);
		GalaxyRegistry.registerMoon(Barnarda_C2);
		
		Barnarda_ABelt = BodiesRegistry.registerExPlanet(BarnardsSystem, "barnarda_d", GalaxySpace.ASSET_PREFIX, 1.25F);
		Barnarda_ABelt.setRelativeDistanceFromCenter(new ScalableDistance(1.25F, 1.0F)).setRingColorRGB(1.1F, 0.0F, 0.0F);
		BodiesRegistry.setOrbitData(Barnarda_ABelt, (float) Math.PI / 2, 1.0F, 105.9F);
		GalaxyRegistry.registerPlanet(Barnarda_ABelt);
		
		Barnarda_E = BodiesRegistry.registerExPlanet(BarnardsSystem, "barnarda_e", GalaxySpace.ASSET_PREFIX, 1.75F);
		BodiesRegistry.setOrbitData(Barnarda_E, (float) Math.PI, 1.0F, 35.9F);
		GalaxyRegistry.registerPlanet(Barnarda_E);
		
		if(event.getSide() == Side.CLIENT)
			GalaxySpace.proxy.register_event(new BRClientEventHandler());		
		GalaxySpace.proxy.register_event(new BREventHandler());		
		
		BRBlocks.initialize();
		BRItems.initialize();
		
		registrycelestial();
	    registryteleport();
	    
	    ColorBlockHandler.addBlock(BRBlocks.BARNARDA_C_GRASS.getDefaultState());
	    ColorBlockHandler.addLeavesBlock(Blocks.LEAVES.getDefaultState());
	    ColorBlockHandler.addLeavesBlock(Blocks.LEAVES2.getDefaultState());
	    ColorBlockHandler.addLeavesBlock(Blocks.VINE.getDefaultState());
	    ColorBlockHandler.addLeavesBlock(BRBlocks.BARNARDA_C_LEAVES.getDefaultState());
		ColorBlockHandler.addBlockWithColor(BRBlocks.BARNARDA_C_WATER_GRASS.getDefaultState(), 0x985cff);
		ColorBlockHandler.addWaterBlock(Blocks.WATER.getDefaultState());
		ColorBlockHandler.addWaterBlock(Blocks.FLOWING_WATER.getDefaultState());
	}

	@Override
	public void init(FMLInitializationEvent event) {
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {		
		GSDimensions.BARNARDA_C = WorldUtil.getDimensionTypeById(BRConfigDimensions.dimensionIDBarnardaC);
		GSDimensions.BARNARDA_C1 = WorldUtil.getDimensionTypeById(BRConfigDimensions.dimensionIDBarnardaC1);
		
	}

	private static void registrycelestial()
	{
		BodiesData data = new BodiesData(TypeBody.STAR).setStarClass(StarClass.DWARF).setStarColor(StarColor.ORANGE);
		data.setStarHabitableZone(0.7F, 0.1F);
		BodiesRegistry.registerBodyData(BarnardsSystem.getMainStar(), data);
	}
	
	private static void registryteleport()
	{
		GalacticraftRegistry.registerTeleportType(WorldProviderBarnarda_C_WE.class, new TeleportTypeBarnarda_C());		
		
		if(GalaxySpace.debug) GalacticraftRegistry.registerTeleportType(WorldProviderBarnarda_C1_WE.class, new TeleportTypeBody());		
	}
	
	@Override
	public void registerRender() {
	//	if(BRConfigCore.enableBarnardsSystems) {	
		
		for (EnumBlockBarnardaC blockBasic : EnumBlockBarnardaC.values())        
			ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, BRBlocks.BARNARDA_C_BLOCKS, blockBasic.getMeta(), "barnarda/" + blockBasic.getName());
		
		for (EnumFallingBlockBarnardaC blockBasic : EnumFallingBlockBarnardaC.values()) 
			ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, BRBlocks.BARNARDA_C_FALLING_BLOCKS, blockBasic.getMeta(), "barnarda/" + blockBasic.getName());
		
		for (EnumBlockDandelions blockBasic : EnumBlockDandelions.values())       
			ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, BRBlocks.BARNARDA_C_DANDELIONS, blockBasic.getMeta(), "barnarda/" + blockBasic.getName());
					
		for (EnumBlockGrass blockBasic : EnumBlockGrass.values())        
			ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, BRBlocks.BARNARDA_C_GRASS, blockBasic.getMeta(), "barnarda/" + blockBasic.getName());
				
		for (EnumBlockLeaves blockBasic : EnumBlockLeaves.values())      
	    	ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, BRBlocks.BARNARDA_C_LEAVES, blockBasic.getMeta(), "barnarda/" + blockBasic.getName());
			
		String[] name = new String[EnumBlockBarnardaCOres.values().length];
		for (EnumBlockBarnardaCOres blockBasic : EnumBlockBarnardaCOres.values()) {
			if(blockBasic.getName() != null) name[blockBasic.getMeta()] = blockBasic.getName();
			ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, BRBlocks.BARNARDA_C_ORES, blockBasic.getMeta(), "barnarda/" + blockBasic.getName());
		}
		GSUtils.addBlockMetadataJsonFiles(BRBlocks.BARNARDA_C_ORES, name, Barnarda_C_Ores.BASIC_TYPE.getName(), "barnarda/");
					
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX + "barnarda/",  BRBlocks.BARNARDA_C_FARMLAND);
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX + "barnarda/",  BRBlocks.BARNARDA_C_WATER_GRASS);
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX + "barnarda/",  BRBlocks.BARNARDA_C_VIOLET_LOG);
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX + "barnarda/",  BRBlocks.BARNARDA_C_VIOLET_GLOW_LOG);
			
		int i = 0;
		for(String basic : ItemBasicBR.names)
		{
			if(basic.equals("null")) { i++; continue; }
			ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, BRItems.BASIC, i++, "barnarda/basic/" + basic);
		}
		i = 0;	
		for (BR_Food food : BR_Food.values())     
			ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, BRItems.FOODS, i++, "barnarda/foods/" + food.getName());
			
		if(GCCoreUtil.isDeobfuscated()) {
			//GSUtils.addBlockJsonFiles(BRBlocks.BARNARDA_C_WATER_GRASS, "barnarda/");
			GSUtils.addItemMetadataJsonFiles(BRItems.BASIC, ItemBasicBR.names, "barnarda/basic/");
			//GSUtils.addBlockMetadataJsonFiles(BRBlocks.BARNARDA_C_GRASS, name, Barnarda_C_Grass.BASIC_TYPE.getName(), "barnarda/");
		}
	}

	@Override
	public void registerVariant() {
		String[] blocks = new String[EnumBlockBarnardaC.values().length];
	    for(int i = 0; i < blocks.length; i++)
	    	blocks[i] = EnumBlockBarnardaC.byMetadata(i).getName(); 
	    	
		ClientProxy.addVariant("barnarda_c_blocks", "barnarda/", blocks);		
		
		blocks = new String[EnumFallingBlockBarnardaC.values().length];
	    for(int i = 0; i < blocks.length; i++)
	    	blocks[i] = EnumFallingBlockBarnardaC.byMetadata(i).getName();
	    	
	    ClientProxy.addVariant("barnarda_c_falling_blocks", "barnarda/", blocks);
	    
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
	    
	    blocks = new String[EnumBlockBarnardaCOres.values().length];
	    for(int i = 0; i < blocks.length; i++)
	    	blocks[i] = EnumBlockBarnardaCOres.byMetadata(i).getName();
	    	
	    ClientProxy.addVariant("barnarda_c_ores", "barnarda/", blocks);

	    //ModelLoader.setCustomStateMapper(BRBlocks.BARNARDA_C_REEDS, new StateMap.Builder().ignore(BlockLiquid.LEVEL).build());
		
	    ClientProxy.addVariant("br_basic", "barnarda/basic/", ItemBasicBR.names);
	    
	    String[] foods = new String[BR_Food.values().length];
	    for(int i = 0; i < foods.length; i++)
	    	foods[i] = BR_Food.byMetadata(i).getName();
	    ClientProxy.addVariant("br_foods", "barnarda/foods/", foods);	    
	   
	}
	
	@Override
	public void registerRecipes() {
		CraftingRecipesBarnarda_C.loadRecipes();
	}

	@Override
	public boolean canRegister() {
		return BRConfigCore.enableBarnardsSystems;
	}
	
	@Override
	public void registerItems(RegistryEvent.Register<Item> event) {
		BRBlocks.oreDictRegistration();
		BRItems.oreDictRegistration();
	}
	
}
