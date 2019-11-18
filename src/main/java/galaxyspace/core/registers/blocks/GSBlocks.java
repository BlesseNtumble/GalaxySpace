package galaxyspace.core.registers.blocks;

import galaxyspace.core.prefab.blocks.DungeonBlocks;
import galaxyspace.core.prefab.blocks.GSBlockMulti;
import galaxyspace.core.prefab.items.GSItemBlockDesc;
import galaxyspace.core.prefab.items.ItemBlockDungeonBlocks;
import galaxyspace.systems.SolarSystem.moons.callisto.blocks.CallistoBlocks;
import galaxyspace.systems.SolarSystem.moons.callisto.items.ItemBlocksCallisto;
import galaxyspace.systems.SolarSystem.moons.enceladus.blocks.EnceladusBlocks;
import galaxyspace.systems.SolarSystem.moons.enceladus.blocks.EnceladusCrystal;
import galaxyspace.systems.SolarSystem.moons.enceladus.items.ItemBlocksEnceladus;
import galaxyspace.systems.SolarSystem.moons.europa.blocks.EuropaBlockGeyser;
import galaxyspace.systems.SolarSystem.moons.europa.blocks.EuropaBlockUWGeyser;
import galaxyspace.systems.SolarSystem.moons.europa.blocks.EuropaBlocks;
import galaxyspace.systems.SolarSystem.moons.europa.items.ItemBlocksEuropa;
import galaxyspace.systems.SolarSystem.moons.ganymede.blocks.GanymedeBlocks;
import galaxyspace.systems.SolarSystem.moons.ganymede.items.ItemBlocksGanymede;
import galaxyspace.systems.SolarSystem.moons.io.blocks.IoBlocks;
import galaxyspace.systems.SolarSystem.moons.io.items.ItemBlocksIo;
import galaxyspace.systems.SolarSystem.moons.miranda.blocks.MirandaBlocks;
import galaxyspace.systems.SolarSystem.moons.miranda.items.ItemBlocksMiranda;
import galaxyspace.systems.SolarSystem.moons.titan.blocks.TitanBlocks;
import galaxyspace.systems.SolarSystem.moons.titan.items.ItemBlocksTitan;
import galaxyspace.systems.SolarSystem.moons.triton.blocks.TritonBlocks;
import galaxyspace.systems.SolarSystem.moons.triton.items.ItemBlocksTriton;
import galaxyspace.systems.SolarSystem.planets.ceres.blocks.BlockBossSpawnerCeres;
import galaxyspace.systems.SolarSystem.planets.ceres.blocks.BlockTier4TreasureChest;
import galaxyspace.systems.SolarSystem.planets.ceres.blocks.CeresBlocks;
import galaxyspace.systems.SolarSystem.planets.ceres.items.ItemBlocksCeres;
import galaxyspace.systems.SolarSystem.planets.mars.blocks.MarsOresBlocks;
import galaxyspace.systems.SolarSystem.planets.mars.items.ItemBlocksMarsOres;
import galaxyspace.systems.SolarSystem.planets.mercury.blocks.MercuryBlocks;
import galaxyspace.systems.SolarSystem.planets.mercury.items.ItemBlocksMercury;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockAdvancedLandingPad;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockAdvancedLandingPadFull;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockDecoMetals;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockFutureGlass;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockFutureGlasses;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockMachineFrames;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockOres;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockSurfaceIce;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines.BlockAdvCircuitFabricator;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines.BlockAdvElectricCompressor;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines.BlockAssembler;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines.BlockFuelGenerator;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines.BlockGasBurner;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines.BlockGravitationModule;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines.BlockHydroponicBase;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines.BlockHydroponicFarm;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines.BlockLiquidExtractor;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines.BlockLiquidSeparator;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines.BlockModernSolarPanel;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines.BlockModernStorageModule;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines.BlockModificationTable;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines.BlockOxygenStorageModule;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines.BlockPanelController;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines.BlockPlanetShield;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines.BlockRadiationStabiliser;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines.BlockResearchTable;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines.BlockRocketAssembler;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines.BlockSingleSolarPanel;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines.BlockSolarWindPanel;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines.BlockUniversalRecycler;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines.BlockWindGenerator;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemBlocksDecoMetals;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemBlocksFutureGlasses;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemBlocksIce;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemBlocksMachineFrames;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemBlocksOres;
import galaxyspace.systems.SolarSystem.planets.pluto.blocks.PlutoBlocks;
import galaxyspace.systems.SolarSystem.planets.pluto.items.ItemBlocksPluto;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class GSBlocks {

	//public static final List<Block> GS_BLOCKS = new ArrayList<Block>();
	
	public static final Block MERCURY_BLOCKS = new MercuryBlocks().setHardness(3.0F);
	
	public static final Block OVERWORLD_ORES = new BlockOres().setHardness(2.0F);
	public static final Block DECO_METALS = new BlockDecoMetals().setHardness(1.0F);
	public static final Block SURFACE_ICE = new BlockSurfaceIce();
	public static final Block FUTURE_GLASS_COLORED = new BlockFutureGlasses().setHardness(0.2F);
	public static final Block FUTURE_GLASS_BASIC = new BlockFutureGlass().setHardness(0.2F);
	public static final Block MACHINE_FRAMES = new BlockMachineFrames().setHardness(1.0F);
	public static final Block FUEL_GENERATOR = new BlockFuelGenerator().setHardness(1.0F);
	public static final Block ASSEMBLER = new BlockAssembler();
	public static final Block MODERN_SOLAR_PANEL = new BlockModernSolarPanel();
	public static final Block WIND_GENERATOR = new BlockWindGenerator();
	public static final Block ROCKET_ASSEMBLER = new BlockRocketAssembler();
	public static final Block UNIVERSAL_RECYCLER = new BlockUniversalRecycler();
	public static final Block LIQUID_EXTRACTOR = new BlockLiquidExtractor();
	public static final Block LIQUID_SEPARATOR = new BlockLiquidSeparator();
	public static final Block HYDROPONIC_BASE = new BlockHydroponicBase();
	public static final Block HYDROPONIC_FARM = new BlockHydroponicFarm();
	public static final Block GRAVITATION_MODULE = new BlockGravitationModule();
	public static final Block RADIATION_STABILISER = new BlockRadiationStabiliser();
	public static final Block PANEL_CONTROLLER = new BlockPanelController();
	public static final Block SINGLE_SOLARPANEL = new BlockSingleSolarPanel("single_solarpanel", 1);
	public static final Block MODERN_SINGLE_SOLARPANEL = new BlockSingleSolarPanel("modern_single_solarpanel", 2);
	public static final Block PLANET_SHIELD = new BlockPlanetShield();
	public static final Block MODERN_STORAGE_MODULE = new BlockModernStorageModule();
	public static final Block ADVANCED_LANDING_PAD_SINGLE = new BlockAdvancedLandingPad();
	public static final Block ADVANCED_LANDING_PAD = new BlockAdvancedLandingPadFull();
	public static final Block MODIFICATION_TABLE = new BlockModificationTable();
	public static final Block GAS_BURNER = new BlockGasBurner();
	public static final Block OXYGEN_STORAGE_MODULE = new BlockOxygenStorageModule(1);
	public static final Block SOLARWIND_PANEL = new BlockSolarWindPanel();
	public static final Block ADVANCED_ELECTRIC_COMPRESSOR = new BlockAdvElectricCompressor();
	public static final Block ADVANCED_CIRCUIT_FABRICATOR = new BlockAdvCircuitFabricator();
	public static final Block RESEARCH_TABLE = new BlockResearchTable();
	
	public static final Block FAKE_BLOCK = new GSBlockMulti();
	
	public static final Block DUNGEON_BLOCKS = new DungeonBlocks().setHardness(1.0F);
	public static final Block TREASURE_CHEST_TIER_4 = new BlockTier4TreasureChest("treasure_t4");
	public static final Block BOSS_SPAWNER_CERES = new BlockBossSpawnerCeres("boss_spawner_ceres"); 
	// MARS --------------------------------------------------------------------
	public static final Block MARS_ORES = new MarsOresBlocks().setHardness(2.0F);
	
	public static final Block CERES_BLOCKS = new CeresBlocks().setHardness(3.0F);	
	
	public static final Block PLUTO_BLOCKS = new PlutoBlocks().setHardness(3.0F);
	
	public static final Block IO_BLOCKS = new IoBlocks().setHardness(3.0F);
	
	public static final Block EUROPA_BLOCKS = new EuropaBlocks().setHardness(3.0F);
	public static final Block EUROPA_GEYSER = new EuropaBlockGeyser();
	public static final Block EUROPA_UWGEYSER = new EuropaBlockUWGeyser();
	
	public static final Block GANYMEDE_BLOCKS = new GanymedeBlocks().setHardness(3.0F);
	
	public static final Block CALLISTO_BLOCKS = new CallistoBlocks().setHardness(3.0F);
	
	public static final Block ENCELADUS_BLOCKS = new EnceladusBlocks().setHardness(3.0F);
	public static final Block ENCELADUS_CRYSTAL = new EnceladusCrystal().setHardness(1.0F);
	
	public static final Block TITAN_BLOCKS = new TitanBlocks().setHardness(3.0F);
	
	public static final Block MIRANDA_BLOCKS = new MirandaBlocks().setHardness(3.0F);
	public static final Block TRITON_BLOCKS = new TritonBlocks().setHardness(3.0F);

	public static void initialize() 
	{				
		registerBlock(MACHINE_FRAMES, ItemBlocksMachineFrames.class);
		registerBlock(ASSEMBLER, GSItemBlockDesc.class);
		registerBlock(FUEL_GENERATOR, GSItemBlockDesc.class);
		registerBlock(WIND_GENERATOR, GSItemBlockDesc.class);
		registerBlock(MODERN_SOLAR_PANEL, GSItemBlockDesc.class);
		registerBlock(ROCKET_ASSEMBLER, GSItemBlockDesc.class);
		registerBlock(UNIVERSAL_RECYCLER, GSItemBlockDesc.class);
		registerBlock(LIQUID_EXTRACTOR, GSItemBlockDesc.class);
		registerBlock(LIQUID_SEPARATOR, GSItemBlockDesc.class);
		registerBlock(HYDROPONIC_BASE, GSItemBlockDesc.class);
		registerBlock(HYDROPONIC_FARM, GSItemBlockDesc.class);
		registerBlock(GRAVITATION_MODULE, GSItemBlockDesc.class);
		registerBlock(RADIATION_STABILISER, GSItemBlockDesc.class);
		registerBlock(PANEL_CONTROLLER, GSItemBlockDesc.class);
		registerBlock(SINGLE_SOLARPANEL, GSItemBlockDesc.class);
		registerBlock(MODERN_SINGLE_SOLARPANEL, GSItemBlockDesc.class);		
		registerBlock(MODERN_STORAGE_MODULE, GSItemBlockDesc.class);
		registerBlock(ADVANCED_LANDING_PAD_SINGLE, GSItemBlockDesc.class);
		registerBlock(ADVANCED_LANDING_PAD, null);
		registerBlock(FAKE_BLOCK, null);
		registerBlock(MODIFICATION_TABLE, GSItemBlockDesc.class);
		registerBlock(GAS_BURNER, GSItemBlockDesc.class);
		registerBlock(OXYGEN_STORAGE_MODULE, GSItemBlockDesc.class);
		registerBlock(SOLARWIND_PANEL, GSItemBlockDesc.class);
		registerBlock(ADVANCED_ELECTRIC_COMPRESSOR, GSItemBlockDesc.class);
		registerBlock(ADVANCED_CIRCUIT_FABRICATOR, GSItemBlockDesc.class);
		registerBlock(PLANET_SHIELD, GSItemBlockDesc.class);
		registerBlock(RESEARCH_TABLE, GSItemBlockDesc.class);
		
		registerBlock(DUNGEON_BLOCKS, ItemBlockDungeonBlocks.class);
		registerBlock(TREASURE_CHEST_TIER_4, GSItemBlockDesc.class);		
		registerBlock(BOSS_SPAWNER_CERES, null);
		
		registerBlock(FUTURE_GLASS_BASIC, GSItemBlockDesc.class);
		registerBlock(FUTURE_GLASS_COLORED, ItemBlocksFutureGlasses.class);
		
		registerBlock(OVERWORLD_ORES, ItemBlocksOres.class);
		registerBlock(DECO_METALS, ItemBlocksDecoMetals.class);
		registerBlock(SURFACE_ICE, ItemBlocksIce.class);
		
		registerBlock(MERCURY_BLOCKS, ItemBlocksMercury.class);
		registerBlock(MARS_ORES, ItemBlocksMarsOres.class);
		
		registerBlock(CERES_BLOCKS, ItemBlocksCeres.class);
		
		registerBlock(PLUTO_BLOCKS, ItemBlocksPluto.class);
		registerBlock(IO_BLOCKS, ItemBlocksIo.class);
		
		registerBlock(EUROPA_BLOCKS, ItemBlocksEuropa.class);
		registerBlock(EUROPA_GEYSER, GSItemBlockDesc.class);
		registerBlock(EUROPA_UWGEYSER, GSItemBlockDesc.class);
		
		registerBlock(GANYMEDE_BLOCKS, ItemBlocksGanymede.class);
		
		registerBlock(CALLISTO_BLOCKS, ItemBlocksCallisto.class);
		
		registerBlock(ENCELADUS_BLOCKS, ItemBlocksEnceladus.class);
		registerBlock(ENCELADUS_CRYSTAL, GSItemBlockDesc.class);
		
		registerBlock(TITAN_BLOCKS, ItemBlocksTitan.class);
		
		registerBlock(MIRANDA_BLOCKS, ItemBlocksMiranda.class);		
		registerBlock(TRITON_BLOCKS, ItemBlocksTriton.class);		
    }
	   
	public static void oreDictRegistration() 
	{

		OreDictionary.registerOre("oreNickel", new ItemStack(MERCURY_BLOCKS, 1, 3));
		OreDictionary.registerOre("oreIron", new ItemStack(MERCURY_BLOCKS, 1, 4));
		OreDictionary.registerOre("oreMagnesium", new ItemStack(MERCURY_BLOCKS, 1, 5));
		
		OreDictionary.registerOre("oreCobalt", new ItemStack(OVERWORLD_ORES, 1, 0));
		OreDictionary.registerOre("oreNickel", new ItemStack(OVERWORLD_ORES, 1, 1));
		OreDictionary.registerOre("oreUranium", new ItemStack(OVERWORLD_ORES, 1, 2));

		OreDictionary.registerOre("oreDiamond", new ItemStack(MARS_ORES, 1, 0));
		OreDictionary.registerOre("oreGold", new ItemStack(MARS_ORES, 1, 1));
		OreDictionary.registerOre("oreCoal", new ItemStack(MARS_ORES, 1, 2));
		OreDictionary.registerOre("oreRedstone", new ItemStack(MARS_ORES, 1, 3));
		OreDictionary.registerOre("oreSilicon", new ItemStack(MARS_ORES, 1, 4));
		OreDictionary.registerOre("oreAluminum", new ItemStack(MARS_ORES, 1, 5));
		
		OreDictionary.registerOre("oreDolomite", new ItemStack(CERES_BLOCKS, 1, 2));
		OreDictionary.registerOre("oreMeteroricIron", new ItemStack(CERES_BLOCKS, 1, 3));
		
		OreDictionary.registerOre("oreCopper", new ItemStack(IO_BLOCKS, 1, 3));
		OreDictionary.registerOre("oreSulfur", new ItemStack(IO_BLOCKS, 1, 4));
		
		OreDictionary.registerOre("oreEmerald", new ItemStack(EUROPA_BLOCKS, 1, 3));
		OreDictionary.registerOre("oreSilicon", new ItemStack(EUROPA_BLOCKS, 1, 4));
		OreDictionary.registerOre("oreAluminum", new ItemStack(EUROPA_BLOCKS, 1, 5));
		
		OreDictionary.registerOre("oreMagnesium", new ItemStack(GANYMEDE_BLOCKS, 1, 2));
		OreDictionary.registerOre("oreIlmenite", new ItemStack(GANYMEDE_BLOCKS, 1, 3));
		
		OreDictionary.registerOre("oreCoal", new ItemStack(ENCELADUS_BLOCKS, 1, 2));
		
		OreDictionary.registerOre("oreIron", new ItemStack(MIRANDA_BLOCKS, 1, 3));
		OreDictionary.registerOre("oreDolomite", new ItemStack(MIRANDA_BLOCKS, 1, 4));
/*
	
		OreDictionary.registerOre("oreUranium", new ItemStack(TritonBlocks, 1, 5));
*/
		OreDictionary.registerOre("blockGlass", new ItemStack(FUTURE_GLASS_BASIC, 1, 0));
		OreDictionary.registerOre("blockGlass", new ItemStack(FUTURE_GLASS_COLORED, 1, OreDictionary.WILDCARD_VALUE));
	}
	
	public static void registerBlock(Block block, Class<? extends ItemBlock> itemClass)
    {
        GCBlocks.registerBlock(block, itemClass);
    }

}
