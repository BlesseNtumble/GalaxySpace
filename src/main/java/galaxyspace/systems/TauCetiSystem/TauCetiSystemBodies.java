package galaxyspace.systems.TauCetiSystem;

import java.io.File;

import asmodeuscore.api.dimension.IAdvancedSpace.ClassBody;
import asmodeuscore.api.dimension.IAdvancedSpace.StarColor;
import asmodeuscore.api.dimension.IAdvancedSpace.TypeBody;
import asmodeuscore.core.astronomy.BodiesData;
import asmodeuscore.core.astronomy.BodiesHelper;
import asmodeuscore.core.astronomy.BodiesHelper.Galaxies;
import asmodeuscore.core.astronomy.dimension.world.gen.ACBiome;
import galaxyspace.GalaxySpace;
import galaxyspace.api.IBodies;
import galaxyspace.api.IBodiesHandler;
import galaxyspace.core.util.GSDimensions;
import galaxyspace.systems.TauCetiSystem.core.configs.TCConfigCore;
import galaxyspace.systems.TauCetiSystem.planets.tauceti_f.dimensions.TeleportTypeTauCeti_F;
import galaxyspace.systems.TauCetiSystem.planets.tauceti_f.dimensions.WorldProviderTauCeti_F_WE;
import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.Planet;
import micdoodle8.mods.galacticraft.api.galaxies.SolarSystem;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.util.WorldUtil;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

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
		TauCetiSystem = BodiesHelper.registerSolarSystem(GalaxySpace.ASSET_PREFIX, "tauceti", Galaxies.MILKYWAY, new Vector3(-3.0F, 3.0F, 0.0F), "tauceti", 0.75F);
		GalaxyRegistry.registerSolarSystem(TauCetiSystem);
		
		TauCeti_G = BodiesHelper.registerExPlanet(TauCetiSystem, "tauceti_g", GalaxySpace.ASSET_PREFIX, 0.25F);
		BodiesHelper.setOrbitData(TauCeti_G, (float) Math.PI, 1.25F, 2F);
		GalaxyRegistry.registerPlanet(TauCeti_G);
		
		TauCeti_H = BodiesHelper.registerExPlanet(TauCetiSystem, "tauceti_h", GalaxySpace.ASSET_PREFIX, 0.75F);
		BodiesHelper.setOrbitData(TauCeti_H, (float) Math.PI / 2, 1.25F, 15F);
		GalaxyRegistry.registerPlanet(TauCeti_H);
		
		TauCeti_E = BodiesHelper.registerExPlanet(TauCetiSystem, "tauceti_e", GalaxySpace.ASSET_PREFIX, 1.25F);
		BodiesHelper.setOrbitData(TauCeti_E, (float) Math.PI * 3, 1.25F, 30F);
		GalaxyRegistry.registerPlanet(TauCeti_E);
		
		TauCeti_F = BodiesHelper.registerExPlanet(TauCetiSystem, "tauceti_f", GalaxySpace.ASSET_PREFIX, 1.5F);
		BodiesHelper.setOrbitData(TauCeti_F, (float) Math.PI / 4, 1.25F, 60F);
		BodiesHelper.setPlanetData(TauCeti_F, 3, 36000L, BodiesHelper.calculateGravity(7.8F), false);
		BodiesHelper.setProviderData(TauCeti_F, WorldProviderTauCeti_F_WE.class, -1338, 6, ACBiome.ACSpace);
		GalaxyRegistry.registerPlanet(TauCeti_F);
		
		GalacticraftRegistry.registerTeleportType(WorldProviderTauCeti_F_WE.class, new TeleportTypeTauCeti_F());		
		
	}

	@Override
	public void init(FMLInitializationEvent event) {		
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {	
		BodiesData data = new BodiesData(TypeBody.STAR, ClassBody.DWARF).setStarColor(StarColor.YELLOW);
		BodiesHelper.registerBodyData(TauCetiSystem.getMainStar(), data);		
		

		GSDimensions.TAU_CETI_F = WorldUtil.getDimensionTypeById(-1338);
	}

	@Override
	public void registerRecipes() {
	}

	@Override
	public void registerRender() {		
	}

	@Override
	public void registerVariant() {		
	}
	
	@Override
	public boolean canRegister() {
		return TCConfigCore.enableTauCetiSystems;
	}

}
