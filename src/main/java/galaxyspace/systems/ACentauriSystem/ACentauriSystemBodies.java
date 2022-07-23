package galaxyspace.systems.ACentauriSystem;

import java.io.File;

import asmodeuscore.api.IBodies;
import asmodeuscore.api.IBodiesHandler;
import asmodeuscore.api.dimension.IAdvancedSpace.StarClass;
import asmodeuscore.api.dimension.IAdvancedSpace.StarColor;
import asmodeuscore.api.dimension.IAdvancedSpace.TypeBody;
import asmodeuscore.core.astronomy.BodiesData;
import asmodeuscore.core.astronomy.BodiesRegistry;
import asmodeuscore.core.astronomy.BodiesRegistry.Galaxies;
import galaxyspace.GalaxySpace;
import galaxyspace.core.proxy.ClientProxy;
import galaxyspace.core.util.GSDimensions;
import galaxyspace.systems.ACentauriSystem.core.ACBlocks;
import galaxyspace.systems.ACentauriSystem.core.configs.ACConfigCore;
import galaxyspace.systems.ACentauriSystem.core.configs.ACConfigDimensions;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.blocks.Proxima_B_Blocks.EnumBlockProximaB;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.blocks.Proxima_B_Dandelions;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.blocks.Proxima_B_Grass;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.dimension.TeleportTypeProxima_B;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.dimension.WorldProviderProxima_B_WE;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.recipes.CraftingRecipesProximaB;
import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.Planet;
import micdoodle8.mods.galacticraft.api.galaxies.SolarSystem;
import micdoodle8.mods.galacticraft.api.galaxies.Star;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.EnumAtmosphericGas;
import micdoodle8.mods.galacticraft.core.util.ClientUtil;
import micdoodle8.mods.galacticraft.core.util.WorldUtil;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@IBodiesHandler
public class ACentauriSystemBodies implements IBodies {

	public static SolarSystem aCentauriSystem;
	public static SolarSystem ProximaSystem;
	public static Star aCentauri;
    public static Planet centauri_b;
    
    public static Star proxima;
    public static Planet proxima_b;
    public static Planet proxima_c;
    public static Planet proxima_d;
    
    @Override
	public void preInitialization(FMLPreInitializationEvent event) 
	{
    	new ACConfigCore(new File(event.getModConfigurationDirectory(), "GalaxySpace/alpha_centauri/core.conf"));
		new ACConfigDimensions(new File(event.getModConfigurationDirectory(), "GalaxySpace/alpha_centauri/dimensions.conf"));	
	}
    
	@Override
	public boolean canRegister() {
		return ACConfigCore.enableACentauriSystems;
	}
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {		
		
		aCentauriSystem = BodiesRegistry.registerSolarSystem(GalaxySpace.ASSET_PREFIX, "acentauri", Galaxies.MILKYWAY, new Vector3(1.5F, 0.0F, 0.0F), "centauri_a", 1.3F);
		GalaxyRegistry.registerSolarSystem(aCentauriSystem);
		
		centauri_b = BodiesRegistry.registerExPlanet(aCentauriSystem, "centauri_b", GalaxySpace.ASSET_PREFIX, 0.3F);
		centauri_b.setRingColorRGB(0.0F, 0.0F, 0.0F);
		BodiesRegistry.setOrbitData(centauri_b, (float) Math.PI, 1.5F, 1000F);
		GalaxyRegistry.registerPlanet(centauri_b);
		
        ProximaSystem = BodiesRegistry.registerSolarSystem(GalaxySpace.ASSET_PREFIX, "proxima", Galaxies.MILKYWAY, new Vector3(1.7F, -0.2F, 0.0F), "proxima", 0.8F);
        GalaxyRegistry.registerSolarSystem(ProximaSystem);		
       
        proxima_b = BodiesRegistry.registerExPlanet(ProximaSystem, "proxima_b", GalaxySpace.ASSET_PREFIX, 0.4F);
        BodiesRegistry.setAtmosphere(proxima_b, false, true, false, -0.05F, 0.4F, 0.0F);
        BodiesRegistry.setOrbitData(proxima_b, (float) Math.PI*3, 1.2F, 8.0F);
        BodiesRegistry.setPlanetData(proxima_b, 2F, 25000L * 7, BodiesRegistry.calculateGravity(8.0F), true);
        BodiesRegistry.setProviderData(proxima_b, WorldProviderProxima_B_WE.class, ACConfigDimensions.dimensionIDProxima_B, 6);
        proxima_b.setRingColorRGB(0.0F, 0.4F, 0.9F);
        proxima_b.atmosphere.composition.add(EnumAtmosphericGas.CO2);
        proxima_b.atmosphere.composition.add(EnumAtmosphericGas.OXYGEN);
        if(ACConfigDimensions.enableProxima_B) GalaxyRegistry.registerPlanet(proxima_b);	
        
        proxima_c = BodiesRegistry.registerExPlanet(ProximaSystem, "proxima_c", GalaxySpace.ASSET_PREFIX, 1.25F);
        BodiesRegistry.setOrbitData(proxima_c, (float) Math.PI / 2, 1.5F, 150F);
        if(ACConfigDimensions.enableProxima_C) GalaxyRegistry.registerPlanet(proxima_c);
        
        proxima_d = BodiesRegistry.registerExPlanet(ProximaSystem, "proxima_d", GalaxySpace.ASSET_PREFIX, 0.25F);
        BodiesRegistry.setOrbitData(proxima_d, (float) Math.PI / 3, 0.8F, 3.0F);
        if(ACConfigDimensions.enableProxima_D) GalaxyRegistry.registerPlanet(proxima_d);
        
        /*
        proxima_b = (Planet) BodiesHelper.registerPlanet(ProximaSystem, "proxima_b", GalaxySpace.ASSET_PREFIX, WorldProviderProxima_B_WE.class, ACConfigDimensions.dimensionIDProxima_B, 6, (float) Math.PI*3, 1.2F, 0.25F, 1.1F, ACBiome.ACSpace)
        		.setRingColorRGB(0.0F, 0.4F, 0.9F).atmosphereComponent(EnumAtmosphericGas.CO2);//.atmosphereComponent(EnumAtmosphericGas.OXYGEN);
        proxima_b.setAtmosphere(new AtmosphereInfo(false, true, false, 0.5F, 0.4F, 0.0F));
		  
        proxima_c = (Planet) BodiesHelper.registerPlanet(
        			ProximaSystem, 
        			"proxima_c", 
        			GalaxySpace.ASSET_PREFIX, 
        			null, -1, -1, (float) Math.PI*4, 1.9F, 0.75F, 15.2F);
        */
        //if(ACConfigCore.enableACentauriSystems) {
        	ACBlocks.initialize();
	        registrycelestial();
	    	registryteleport();
       // }
	}

	@Override
	public void init(FMLInitializationEvent event) {
		
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {		
		GSDimensions.PROXIMA_B = WorldUtil.getDimensionTypeById(ACConfigDimensions.dimensionIDProxima_B);
	}
	
	private static void registrycelestial()
	{		
		BodiesData data = new BodiesData(TypeBody.STAR).setStarClass(StarClass.DWARF).setStarColor(StarColor.YELLOW);
		BodiesRegistry.registerBodyData(centauri_b, data);
	
		data = new BodiesData(TypeBody.STAR).setStarClass(StarClass.DWARF).setStarColor(StarColor.YELLOW);
		BodiesRegistry.registerBodyData(aCentauriSystem.getMainStar(), data);
		
		data = new BodiesData(TypeBody.STAR).setStarClass(StarClass.DWARF).setStarColor(StarColor.ORANGE);
		data.setStarHabitableZone(0.4F, 0.1F);
		BodiesRegistry.registerBodyData(ProximaSystem.getMainStar(), data);
	}
	
	private static void registryteleport()
	{
		GalacticraftRegistry.registerTeleportType(WorldProviderProxima_B_WE.class, new TeleportTypeProxima_B());
	}

	@Override
	public void registerRender() {
		
		//if(ACConfigCore.enableACentauriSystems) {
		for (EnumBlockProximaB blockBasic : EnumBlockProximaB.values())        
	    	ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, ACBlocks.PROXIMA_B_BLOCKS, blockBasic.getMeta(), "proxima/" + blockBasic.getName());
		
		
		
		for (Proxima_B_Dandelions.EnumBlockDandelions blockBasic : Proxima_B_Dandelions.EnumBlockDandelions.values())
			ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, ACBlocks.PROXIMA_B_DANDELIONS, blockBasic.getMeta(), "proxima/" + blockBasic.getName());
		
		
		String[] name = new String[Proxima_B_Grass.EnumBlockGrass.values().length];
		for (Proxima_B_Grass.EnumBlockGrass blockBasic : Proxima_B_Grass.EnumBlockGrass.values()) {  
			if(blockBasic.getName() != null) name[blockBasic.getMeta()] = blockBasic.getName();
			ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, ACBlocks.PROXIMA_B_GRASS, blockBasic.getMeta(), "proxima/" + blockBasic.getName());
		}
		//if(GCCoreUtil.isDeobfuscated()) 
			//GSUtils.addBlockMetadataJsonFiles(ACBlocks.PROXIMA_B_GRASS, name, Proxima_B_Grass.BASIC_TYPE.getName(), "proxima/");
		
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX + "proxima/", ACBlocks.PROXIMA_B_LOG_1);
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX + "proxima/", ACBlocks.PROXIMA_B_LOG_2);
		ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX + "proxima/", ACBlocks.PROXIMA_B_MUSHROOM_LOG);
			
		
		//}
		/*	
		String[] name = new String[EnumBlockProximaB.values().length];
		for(EnumBlockProximaB block : EnumBlockProximaB.values())
		{
			if(block.getName() != null) name[block.getMeta()] = block.getName();
			ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, ACBlocks.PROXIMA_B_BLOCKS, block.getMeta(), block.getName());
		}
		
		if(GCCoreUtil.isDeobfuscated()) 
			GSUtils.addBlockMetadataJsonFiles(ACBlocks.PROXIMA_B_BLOCKS, name, Proxima_B_Blocks.BASIC_TYPE.getName(), "");
		*/
	}

	@Override
	public void registerVariant() {
		//if(ACConfigCore.enableACentauriSystems) {
			String[] blocks = new String[EnumBlockProximaB.values().length];
	    	for(int i = 0; i < blocks.length; i++)
	    		blocks[i] = EnumBlockProximaB.byMetadata(i).getName();
	
			ClientProxy.addVariant("proxima_b_blocks", "proxima/", blocks);
			ClientProxy.addVariant("proxima_b_log_1", "proxima/", "proxima_b_log_1");
			ClientProxy.addVariant("proxima_b_log_2", "proxima/", "proxima_b_log_2");
			ClientProxy.addVariant("proxima_b_mushroom_log", "proxima/", "proxima_b_mushroom_log");
			
			blocks = new String[Proxima_B_Dandelions.EnumBlockDandelions.values().length];
		    for(int i = 0; i < blocks.length; i++)
		    	blocks[i] = Proxima_B_Dandelions.EnumBlockDandelions.byMetadata(i).getName();
		    	
		    ClientProxy.addVariant("proxima_b_dandelions", "proxima/", blocks);
		    
		    blocks = new String[Proxima_B_Grass.EnumBlockGrass.values().length];
		    for(int i = 0; i < blocks.length; i++)
		    	blocks[i] = Proxima_B_Grass.EnumBlockGrass.byMetadata(i).getName();
		    	
		    ClientProxy.addVariant("proxima_b_grasses", "proxima/", blocks);
		//}
	}
	
	@Override
	public void registerRecipes() {
		//if(ACConfigCore.enableACentauriSystems) {
			CraftingRecipesProximaB.loadRecipes();
		//}
	}

	@Override
	public void registerItems(RegistryEvent.Register<Item> event) {
		ACBlocks.oreDictRegistration();
	}
}
