package galaxyspace.core.client.gui.book.pages.bodies;

import galaxyspace.api.IPage;
import galaxyspace.core.GSBlocks;
import galaxyspace.core.GSItems;
import galaxyspace.core.client.gui.book.pages.Page_Systems;
import galaxyspace.systems.SolarSystem.SolarSystemBodies;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.planets.asteroids.AsteroidsModule;
import micdoodle8.mods.galacticraft.planets.asteroids.blocks.AsteroidBlocks;
import micdoodle8.mods.galacticraft.planets.mars.MarsModule;
import micdoodle8.mods.galacticraft.planets.mars.blocks.MarsBlocks;
import micdoodle8.mods.galacticraft.planets.venus.VenusBlocks;
import micdoodle8.mods.galacticraft.planets.venus.VenusModule;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

@IPage
public class Page_SolarSystem extends Page_Systems{

	public Page_SolarSystem()
	{
		super(GalacticraftCore.solarSystemSol);
		setResources(SolarSystemBodies.planetMercury, new ItemStack(GSBlocks.MERCURY_BLOCKS, 1, 3), new ItemStack(GSBlocks.MERCURY_BLOCKS, 1, 4), new ItemStack(GSBlocks.MERCURY_BLOCKS, 1, 5));
		setResources(VenusModule.planetVenus, new ItemStack(VenusBlocks.venusBlock, 1, 6), new ItemStack(VenusBlocks.venusBlock, 1, 7), new ItemStack(VenusBlocks.venusBlock, 1, 8), new ItemStack(VenusBlocks.venusBlock, 1, 9), new ItemStack(VenusBlocks.venusBlock, 1, 10), new ItemStack(VenusBlocks.venusBlock, 1, 11), new ItemStack(VenusBlocks.venusBlock, 1, 13)) ;
		//setResources(GalacticraftCore.planetOverworld, new ItemStack(Blocks.COAL_ORE, 1, 0), new ItemStack(Blocks.IRON_ORE, 1, 0), new ItemStack(Blocks.GOLD_ORE, 1, 0), new ItemStack(Blocks.REDSTONE_ORE, 1, 0), new ItemStack(Blocks.LAPIS_ORE, 1, 0), new ItemStack(Blocks.DIAMOND_ORE, 1, 0), new ItemStack(Blocks.EMERALD_ORE, 1, 0), new ItemStack(GCBlocks.basicBlock, 1, 5), new ItemStack(GCBlocks.basicBlock, 1, 6), new ItemStack(GCBlocks.basicBlock, 1, 7), new ItemStack(GCBlocks.basicBlock, 1, 8), new ItemStack(GSBlocks.OVERWORLD_ORES, 1, 0), new ItemStack(GSBlocks.OVERWORLD_ORES, 1, 1), new ItemStack(GSBlocks.OVERWORLD_ORES, 1, 2)) ;
		setResources(GalacticraftCore.moonMoon, new ItemStack(GCBlocks.blockMoon, 1, 0), new ItemStack(GCBlocks.blockMoon, 1, 1), new ItemStack(GCBlocks.blockMoon, 1, 2), new ItemStack(GCBlocks.blockMoon, 1, 6));
		setResources(MarsModule.planetMars, new ItemStack(MarsBlocks.marsBlock, 1, 0), new ItemStack(MarsBlocks.marsBlock, 1, 1), new ItemStack(MarsBlocks.marsBlock, 1, 2), new ItemStack(MarsBlocks.marsBlock, 1, 3), new ItemStack(GSBlocks.MARS_ORES, 1, 0), new ItemStack(GSBlocks.MARS_ORES, 1, 1), new ItemStack(GSBlocks.MARS_ORES, 1, 2), new ItemStack(GSBlocks.MARS_ORES, 1, 3), new ItemStack(GSBlocks.MARS_ORES, 1, 4), new ItemStack(GSBlocks.MARS_ORES, 1, 5));
		setResources(SolarSystemBodies.planetCeres, new ItemStack(GSBlocks.CERES_BLOCKS, 1, 2), new ItemStack(GSBlocks.CERES_BLOCKS, 1, 3));
		setResources(AsteroidsModule.planetAsteroids, new ItemStack(AsteroidBlocks.blockBasic, 1, 3), new ItemStack(AsteroidBlocks.blockBasic, 1, 4), new ItemStack(AsteroidBlocks.blockBasic, 1, 5), new ItemStack(GCBlocks.basicBlock, 1, 8), new ItemStack(GCBlocks.basicBlock, 1, 12), new ItemStack(Blocks.DIAMOND_ORE, 1, 0));
		setResources(SolarSystemBodies.ioJupiter, new ItemStack(GSBlocks.IO_BLOCKS, 1, 3), new ItemStack(GSBlocks.IO_BLOCKS, 1, 4), new ItemStack(GSBlocks.IO_BLOCKS, 1, 5));
		setResources(SolarSystemBodies.europaJupiter, new ItemStack(GSBlocks.EUROPA_BLOCKS, 1, 2), new ItemStack(GSBlocks.EUROPA_BLOCKS, 1, 3), new ItemStack(GSBlocks.EUROPA_BLOCKS, 1, 4), new ItemStack(GSBlocks.EUROPA_BLOCKS, 1, 5));
		setResources(SolarSystemBodies.ganymedeJupiter, new ItemStack(GSBlocks.GANYMEDE_BLOCKS, 1, 2), new ItemStack(GSBlocks.GANYMEDE_BLOCKS, 1, 3));
		//setResources(SolarSystemBodies.callistoJupiter, new ItemStack(GSBlocks.GANYMEDE_BLOCKS, 1, 2), new ItemStack(GSBlocks.GANYMEDE_BLOCKS, 1, 3));
		setResources(SolarSystemBodies.enceladusSaturn, new ItemStack(GSBlocks.ENCELADUS_BLOCKS, 1, 2), new ItemStack(GSBlocks.ENCELADUS_CRYSTAL, 1, 0));
		setResources(SolarSystemBodies.titanSaturn, new ItemStack(GSItems.EM_CANISTER, 1, 1));
		setResources(SolarSystemBodies.mirandaUranus, new ItemStack(GSBlocks.MIRANDA_BLOCKS, 1, 3), new ItemStack(GSBlocks.MIRANDA_BLOCKS, 1, 4));
		setResources(SolarSystemBodies.planetKuiperBelt, new ItemStack(GSBlocks.OVERWORLD_ORES, 1, 0), new ItemStack(GSBlocks.OVERWORLD_ORES, 1, 1), new ItemStack(GSBlocks.OVERWORLD_ORES, 1, 2), new ItemStack(Blocks.COAL_ORE, 1, 0));

	}
}
