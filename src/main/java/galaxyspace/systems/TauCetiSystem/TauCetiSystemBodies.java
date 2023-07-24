package galaxyspace.systems.TauCetiSystem;

import java.io.File;

import asmodeuscore.api.IBodies;
import asmodeuscore.api.IBodiesHandler;
import asmodeuscore.api.dimension.IAdvancedSpace.StarColor;
import asmodeuscore.api.dimension.IAdvancedSpace.StarType;
import asmodeuscore.api.dimension.IAdvancedSpace.TypeBody;
import asmodeuscore.core.astronomy.BodiesData;
import asmodeuscore.core.astronomy.BodiesRegistry;
import asmodeuscore.core.astronomy.BodiesRegistry.Galaxies;
import asmodeuscore.core.astronomy.dimension.world.gen.ACBiome;
import asmodeuscore.core.handler.ColorBlockHandler;
import galaxyspace.GalaxySpace;
import galaxyspace.core.proxy.ClientProxy;
import galaxyspace.core.util.GSDimensions;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.TauCetiSystem.core.TCBlocks;
import galaxyspace.systems.TauCetiSystem.core.configs.TCConfigCore;
import galaxyspace.systems.TauCetiSystem.planets.tauceti_f.blocks.TauCeti_F_Blocks.EnumBlockTauCetiF;
import galaxyspace.systems.TauCetiSystem.planets.tauceti_f.blocks.TauCeti_F_Corals;
import galaxyspace.systems.TauCetiSystem.planets.tauceti_f.blocks.TauCeti_F_Corals.EnumBlockCorals;
import galaxyspace.systems.TauCetiSystem.planets.tauceti_f.blocks.TauCeti_F_Watergrass.EnumBlockDandelions;
import galaxyspace.systems.TauCetiSystem.planets.tauceti_f.dimensions.TeleportTypeTauCeti_F;
import galaxyspace.systems.TauCetiSystem.planets.tauceti_f.dimensions.WorldProviderTauCeti_F_WE;
import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.Planet;
import micdoodle8.mods.galacticraft.api.galaxies.SolarSystem;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.util.ClientUtil;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.util.WorldUtil;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@IBodiesHandler
public class TauCetiSystemBodies implements IBodies{

	public static SolarSystem TauCetiSystem;
	public static Planet TauCeti_G, TauCeti_H, TauCeti_E, TauCeti_F;
	
	@Override
	public void preInitialization(FMLPreInitializationEvent event) 	{
		new TCConfigCore(new File(event.getModConfigurationDirectory(), "GalaxySpace/tau_ceti/core.conf"));	
	}
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {	
		TauCetiSystem = BodiesRegistry.registerSolarSystem(GalaxySpace.ASSET_PREFIX, "tauceti", Galaxies.MILKYWAY, new Vector3(-3.0F, 3.0F, 0.0F), "tauceti", 0.75F);
		GalaxyRegistry.registerSolarSystem(TauCetiSystem);
		
		TauCeti_G = BodiesRegistry.registerExPlanet(TauCetiSystem, "tauceti_g", GalaxySpace.ASSET_PREFIX, 0.25F);
		BodiesRegistry.setOrbitData(TauCeti_G, (float) Math.PI, 1.25F, 2F);
		GalaxyRegistry.registerPlanet(TauCeti_G);
		
		TauCeti_H = BodiesRegistry.registerExPlanet(TauCetiSystem, "tauceti_h", GalaxySpace.ASSET_PREFIX, 0.4F);
		BodiesRegistry.setOrbitData(TauCeti_H, (float) Math.PI / 2, 1.25F, 15F);
		GalaxyRegistry.registerPlanet(TauCeti_H);
		
		TauCeti_E = BodiesRegistry.registerExPlanet(TauCetiSystem, "tauceti_e", GalaxySpace.ASSET_PREFIX, 0.75F);
		BodiesRegistry.setOrbitData(TauCeti_E, (float) Math.PI * 3, 1.25F, 30F);
		GalaxyRegistry.registerPlanet(TauCeti_E);
		
		TauCeti_F = BodiesRegistry.registerExPlanet(TauCetiSystem, "tauceti_f", GalaxySpace.ASSET_PREFIX, 1.2F);
		BodiesRegistry.setOrbitData(TauCeti_F, (float) Math.PI / 4, 1.25F, 60F);
		BodiesRegistry.setPlanetData(TauCeti_F, 8.0F, 3, 36000L, BodiesRegistry.calculateGravity(7.8F), false);
		BodiesRegistry.setProviderData(TauCeti_F, WorldProviderTauCeti_F_WE.class, -1338, 6, ACBiome.ACSpace);
		BodiesRegistry.setAtmosphere(TauCeti_F, false, true, false, -0.2F, 3, 1.0F);
		GalaxyRegistry.registerPlanet(TauCeti_F);

		
		GalacticraftRegistry.registerTeleportType(WorldProviderTauCeti_F_WE.class, new TeleportTypeTauCeti_F());		
		
		TCBlocks.initialize();
		
		ColorBlockHandler.addBlock(TCBlocks.TAUCETI_F_BLOCKS.getStateFromMeta(1));
	}

	@Override
	public void init(FMLInitializationEvent event) {		
		
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {		
		
		BodiesData data = new BodiesData(TypeBody.STAR).setStarType(StarType.DWARF).setStarColor(StarColor.YELLOW);
		data.setStarHabitableZone(1.1F, 0.22F);
		BodiesRegistry.registerBodyData(TauCetiSystem.getMainStar(), data);		
		
		GSDimensions.TAU_CETI_F = WorldUtil.getDimensionTypeById(-1338);
	}

	@Override
	public void registerRecipes() {
	}

	@Override
	public void registerRender() {	

		String[] name = new String[EnumBlockTauCetiF.values().length];
		for (EnumBlockTauCetiF blockBasic : EnumBlockTauCetiF.values()) {        
			if(blockBasic.getName() != null) name[blockBasic.getMeta()] = blockBasic.getName();
			ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, TCBlocks.TAUCETI_F_BLOCKS, blockBasic.getMeta(), "tauceti/" + blockBasic.getName());
		}
		
		name = new String[EnumBlockDandelions.values().length];
		for (EnumBlockDandelions blockBasic : EnumBlockDandelions.values()) {        
			if(blockBasic.getName() != null) name[blockBasic.getMeta()] = blockBasic.getName();
			ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, TCBlocks.TAUCETI_F_WATERGRASS, blockBasic.getMeta(), "tauceti/" + blockBasic.getName());
		}
		
		name = new String[EnumBlockCorals.values().length];
		for (EnumBlockCorals blockBasic : EnumBlockCorals.values()) {        
			if(blockBasic.getName() != null) name[blockBasic.getMeta()] = blockBasic.getName();
			ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, TCBlocks.TAUCETI_F_CORALS, blockBasic.getMeta(), "tauceti/" + blockBasic.getName());
		}
		if(GCCoreUtil.isDeobfuscated())
			GSUtils.addBlockMetadataJsonFiles(TCBlocks.TAUCETI_F_CORALS, name, TauCeti_F_Corals.BASIC_TYPE.getName(), "tauceti/");
		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerVariant() {	
		
		String[] blocks = new String[EnumBlockTauCetiF.values().length];
	    for(int i = 0; i < blocks.length; i++)
	    	blocks[i] = EnumBlockTauCetiF.byMetadata(i).getName(); 
	    	
		ClientProxy.addVariant("tauceti_f_blocks", "tauceti/", blocks);
		
		blocks = new String[EnumBlockDandelions.values().length];
	    for(int i = 0; i < blocks.length; i++)
	    	blocks[i] = EnumBlockDandelions.byMetadata(i).getName(); 
	    
	    ClientProxy.addVariant("tauceti_f_watergrass", "tauceti/", blocks);
	    
	    blocks = new String[EnumBlockCorals.values().length];
	    for(int i = 0; i < blocks.length; i++)
	    	blocks[i] = EnumBlockCorals.byMetadata(i).getName(); 
	    
	    ClientProxy.addVariant("tauceti_f_corals", "tauceti/", blocks);
	    
	    ModelLoader.setCustomStateMapper(TCBlocks.TAUCETI_F_WATERGRASS, new StateMap.Builder().ignore(BlockLiquid.LEVEL).build());
	    ModelLoader.setCustomStateMapper(TCBlocks.TAUCETI_F_CORALS, new StateMap.Builder().ignore(BlockLiquid.LEVEL).build());
	}
	
	@Override
	public boolean canRegister() {
		return TCConfigCore.enableTauCetiSystems;
	}

}
