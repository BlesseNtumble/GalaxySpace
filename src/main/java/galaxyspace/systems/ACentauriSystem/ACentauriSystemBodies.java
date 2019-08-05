package galaxyspace.systems.ACentauriSystem;

import java.io.File;

import asmodeuscore.api.dimension.IAdvancedSpace.ClassBody;
import asmodeuscore.core.astronomy.BodiesData;
import asmodeuscore.core.astronomy.BodiesHelper;
import asmodeuscore.core.astronomy.BodiesHelper.Galaxies;
import asmodeuscore.core.astronomy.dimension.world.gen.ACBiome;
import galaxyspace.GalaxySpace;
import galaxyspace.api.IBodies;
import galaxyspace.api.IBodiesHandler;
import galaxyspace.core.proxy.ClientProxy;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.util.GSDimensions;
import galaxyspace.systems.ACentauriSystem.core.configs.ACConfigCore;
import galaxyspace.systems.ACentauriSystem.core.configs.ACConfigDimensions;
import galaxyspace.systems.ACentauriSystem.core.registers.blocks.ACBlocks;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.blocks.Proxima_B_Blocks.EnumBlockProximaB;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.dimension.TeleportTypeProxima_B;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.dimension.WorldProviderProxima_B_WE;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.recipes.CraftingRecipesProximaB;
import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.Planet;
import micdoodle8.mods.galacticraft.api.galaxies.SolarSystem;
import micdoodle8.mods.galacticraft.api.galaxies.Star;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.AtmosphereInfo;
import micdoodle8.mods.galacticraft.api.world.EnumAtmosphericGas;
import micdoodle8.mods.galacticraft.core.util.ClientUtil;
import micdoodle8.mods.galacticraft.core.util.WorldUtil;
import net.minecraft.item.ItemStack;
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
		
		aCentauriSystem = BodiesHelper.registerSolarSystem(GalaxySpace.ASSET_PREFIX, "acentauri", Galaxies.MILKYWAY.getName(), new Vector3(1.5F, 0.0F, 0.0F), "centauri_a", 1.3F);
        centauri_b = (Planet) BodiesHelper.registerPlanet(aCentauriSystem, "centauri_b", GalaxySpace.ASSET_PREFIX, null, -1, -1, (float) Math.PI, 1.5F, 0.3F, 1000F).setRingColorRGB(0.0F, 0.0F, 0.0F);;
        
        ProximaSystem = BodiesHelper.registerSolarSystem(GalaxySpace.ASSET_PREFIX, "proxima", Galaxies.MILKYWAY.getName(), new Vector3(1.7F, -0.2F, 0.0F), "proxima", 0.8F);
        proxima_b = (Planet) BodiesHelper.registerPlanet(ProximaSystem, "proxima_b", GalaxySpace.ASSET_PREFIX, WorldProviderProxima_B_WE.class, ACConfigDimensions.dimensionIDProxima_B, 6, (float) Math.PI*3, 1.2F, 0.25F, 1.1F, ACBiome.ACSpace)
        		.setRingColorRGB(0.0F, 0.4F, 0.9F).atmosphereComponent(EnumAtmosphericGas.CO2);//.atmosphereComponent(EnumAtmosphericGas.OXYGEN);
        proxima_b.setAtmosphere(new AtmosphereInfo(false, true, false, 0.5F, 0.4F, 0.0F));
		  
        proxima_c = (Planet) BodiesHelper.registerPlanet(
        			ProximaSystem, 
        			"proxima_c", 
        			GalaxySpace.ASSET_PREFIX, 
        			null, -1, -1, (float) Math.PI*4, 1.9F, 0.75F, 15.2F);
        
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
		
		//if(ACConfigCore.enableACentauriSystems)
			GSDimensions.PROXIMA_B = WorldUtil.getDimensionTypeById(ACConfigDimensions.dimensionIDProxima_B);
				
	}
	
	private static void registrycelestial()
	{
		GalaxyRegistry.registerSolarSystem(aCentauriSystem);
		GalaxyRegistry.registerSolarSystem(ProximaSystem);		
		BodiesData unreachableData = new BodiesData(null, 0F, 0, 0, false);
		BodiesHelper.registerBody(centauri_b, unreachableData, true);
		//BodiesHelper.registerBody(proxima_c, unreachableData, true);
		
		BodiesData data = new BodiesData(null, BodiesHelper.calculateGravity(8.0F), 2, 35050, true);
		data.addItemStack(new ItemStack(GSItems.SPACE_SUIT_HELMET, 1, 1));
		data.addItemStack(new ItemStack(GSItems.SPACE_SUIT_BODY, 1, 1));
		data.addItemStack(new ItemStack(GSItems.SPACE_SUIT_LEGGINS, 1, 1));
		data.addItemStack(new ItemStack(GSItems.SPACE_SUIT_BOOTS, 1, 1));
		BodiesHelper.registerBody(proxima_b, data, ACConfigDimensions.enableProxima_B);
		
		data = new BodiesData(BodiesHelper.yellow + " " + BodiesHelper.getClassBody(ClassBody.DWARF), 20.336F, 0, 0, false);
		BodiesHelper.registerBodyWithClass(aCentauriSystem.getMainStar(), data);
		
		data = new BodiesData(BodiesHelper.orange + " " + BodiesHelper.getClassBody(ClassBody.DWARF), 18.124F, 0, 0, false);
		BodiesHelper.registerBodyWithClass(ProximaSystem.getMainStar(), data);
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
		
			ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX + "proxima/", ACBlocks.PROXINA_B_LOG_1);
			ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX + "proxima/", ACBlocks.PROXINA_B_LOG_2);
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
		//}
	}
	
	@Override
	public void registerRecipes() {
		//if(ACConfigCore.enableACentauriSystems) {
			CraftingRecipesProximaB.loadRecipes();
		//}
	}

}
