package galaxyspace.core.registers.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import galaxyspace.core.prefab.blocks.BlockDungeonBricks;
import galaxyspace.core.prefab.blocks.BlockDungeonGlowstone;
import galaxyspace.core.prefab.items.ItemBlockDungeonBricks;
import galaxyspace.core.prefab.items.ItemBlockDungeonGlowstone;
import galaxyspace.core.registers.items.GSItemBlockDesc;
import galaxyspace.core.registers.items.GSItemBlockMachine;
import galaxyspace.systems.SolarSystem.moons.callisto.blocks.CallistoBlocks;
import galaxyspace.systems.SolarSystem.moons.callisto.items.ItemBlocksCallisto;
import galaxyspace.systems.SolarSystem.moons.enceladus.blocks.EnceladusBlockCrystal;
import galaxyspace.systems.SolarSystem.moons.enceladus.blocks.EnceladusBlocks;
import galaxyspace.systems.SolarSystem.moons.enceladus.items.ItemBlocksEnceladus;
import galaxyspace.systems.SolarSystem.moons.europa.blocks.EuropaBlockGeyzer;
import galaxyspace.systems.SolarSystem.moons.europa.blocks.EuropaBlockUnderwaterGeyser;
import galaxyspace.systems.SolarSystem.moons.europa.blocks.EuropaBlocks;
import galaxyspace.systems.SolarSystem.moons.europa.items.ItemBlocksEuropa;
import galaxyspace.systems.SolarSystem.moons.ganymede.blocks.GanymedeBlocks;
import galaxyspace.systems.SolarSystem.moons.ganymede.items.ItemBlocksGanymede;
import galaxyspace.systems.SolarSystem.moons.io.blocks.IoBlockT5TreasureChest;
import galaxyspace.systems.SolarSystem.moons.io.blocks.IoBlocks;
import galaxyspace.systems.SolarSystem.moons.io.items.ItemBlocksIo;
import galaxyspace.systems.SolarSystem.moons.miranda.blocks.MirandaBlocks;
import galaxyspace.systems.SolarSystem.moons.miranda.items.ItemBlocksMiranda;
import galaxyspace.systems.SolarSystem.moons.phobos.blocks.PhobosBlocks;
import galaxyspace.systems.SolarSystem.moons.titan.blocks.TitanBlocks;
import galaxyspace.systems.SolarSystem.moons.titan.items.ItemBlocksTitan;
import galaxyspace.systems.SolarSystem.moons.triton.blocks.TritonBlocks;
import galaxyspace.systems.SolarSystem.moons.triton.items.ItemBlocksTriton;
import galaxyspace.systems.SolarSystem.planets.ceres.blocks.CeresBlockT4TreasureChest;
import galaxyspace.systems.SolarSystem.planets.ceres.blocks.CeresBlockWeb;
import galaxyspace.systems.SolarSystem.planets.ceres.blocks.CeresBlocks;
import galaxyspace.systems.SolarSystem.planets.ceres.items.ItemBlocksCeres;
import galaxyspace.systems.SolarSystem.planets.mars.blocks.MarsOresBlocks;
import galaxyspace.systems.SolarSystem.planets.mars.items.ItemBlocksMarsOres;
import galaxyspace.systems.SolarSystem.planets.mercury.blocks.MercuryBlocks;
import galaxyspace.systems.SolarSystem.planets.mercury.items.ItemBlocksMercury;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockAdvLandingPad;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockAdvLandingPadFull;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockDecoMetals;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockFutureGlass;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockFutureGlasses;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockFutureLamp;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockMachineFrames;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockOres;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockSurfaceIce;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.mashines.BlockAdvFuelLoader;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.mashines.BlockAdvOxygenSealer;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.mashines.BlockAssemblyMachine;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.mashines.BlockCargoFluidLoader;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.mashines.BlockConverterSurface;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.mashines.BlockFluidTank;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.mashines.BlockFuelGenerator;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.mashines.BlockGravitationModule;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.mashines.BlockHydroponicBase;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.mashines.BlockHydroponicFarm;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.mashines.BlockLiquidExtractor;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.mashines.BlockLiquidSeparator;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.mashines.BlockModificationTable;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.mashines.BlockOxStorageModuleT2;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.mashines.BlockOxygenFiller;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.mashines.BlockPortableNuclearReactor;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.mashines.BlockRadiationStabiliser;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.mashines.BlockRecycler;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.mashines.BlockRocketAssembly;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.mashines.BlockSolarPanel;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.mashines.BlockSolarWindPanel;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.mashines.BlockStorageModuleT3;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.mashines.BlockWindTurbine;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemBlocksDecoMetals;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemBlocksFutureGlasses;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemBlocksHydroponicFarm;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemBlocksMachineFrames;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemBlocksOres;
import galaxyspace.systems.SolarSystem.planets.pluto.blocks.PlutoBlocks;
import galaxyspace.systems.SolarSystem.planets.pluto.items.ItemBlocksPluto;
import galaxyspace.systems.SolarSystem.planets.venus.blocks.VenusBlocks;
import galaxyspace.systems.SolarSystem.planets.venus.items.ItemBlocksVenus;
import micdoodle8.mods.galacticraft.planets.mars.blocks.MarsBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class GSBlocks {
		// Mercury ------------------
		public static Block MercuryBlocks = new MercuryBlocks();
		// --------------------------
		// Venus --------------------
		public static Block VenusBlocks = new VenusBlocks();
		// --------------------------
		// Overworld ----------------
		public static Block DungeonBricks = new BlockDungeonBricks();
		public static Block DungeonGlowstones = new BlockDungeonGlowstone();
		public static Block Ores = new BlockOres();
		public static Block SurfaceIce = new BlockSurfaceIce();
		public static Block DecoMetalsBlock = new BlockDecoMetals();
		public static Block FuelGenerator = new BlockFuelGenerator("FuelGenerator");
		public static Block SolarWindPanel = new BlockSolarWindPanel();
		public static Block AssemblyMachine = new BlockAssemblyMachine();
		public static Block MachineFrames = new BlockMachineFrames();
		public static Block FutureGlass = new BlockFutureGlass();;
		public static Block FutureGlasses = new BlockFutureGlasses();
		public static Block ConverterSurface = new BlockConverterSurface("ConverterSurface");
		public static Block StorageModuleT3 = new BlockStorageModuleT3("StorageModuleT3");
		public static Block OxStorageModuleT2 = new BlockOxStorageModuleT2("OxygenStorageModuleT2");
		public static Block SolarPanel = new BlockSolarPanel("SolarPanel");
		public static Block FutureLamp = new BlockFutureLamp();
		public static Block WindTurbine = new BlockWindTurbine("WindTurbine");
		public static Block AdvOxygenSealer = new BlockAdvOxygenSealer("AdvOxygenSealer");;
		public static Block AdvFuelLoader = new BlockAdvFuelLoader("AdvFuelLoader");
		public static Block RocketAssembly = new BlockRocketAssembly();
		public static Block Recycler = new BlockRecycler();
		public static Block LiquidExtractor = new BlockLiquidExtractor();
		public static Block PortableNuclearReactor = new BlockPortableNuclearReactor("PortableNuclearReactor");
		public static Block FluidTank = new BlockFluidTank("FluidTank");		
		public static Block GravitationModule = new BlockGravitationModule();
		public static Block AdvLandingPad = new BlockAdvLandingPad("AdvLandingPad");
		public static Block AdvLandingPadFull = new BlockAdvLandingPadFull("AdvLandingPadFull");
		public static Block CargoFluidLoader = new BlockCargoFluidLoader("CargoFluidLoader");
		public static Block HydroponicBase = new BlockHydroponicBase("HydroponicBase");
		public static Block HydroponicFarm = new BlockHydroponicFarm("HydroponicFarm");
		public static Block LiquidSeparator = new BlockLiquidSeparator();
		public static Block RadiationStabiliser = new BlockRadiationStabiliser();
		public static Block ModificationTable = new BlockModificationTable();
		public static Block OxygenFiller = new BlockOxygenFiller("OxygenFiller");
		// --------------------------
		// Mars ---------------------
		public static Block MarsOresBlocks = new MarsOresBlocks();
		// --------------------------
		// Ceres --------------------
		public static Block CeresBlocks = new CeresBlocks();
		//public static CeresBlockGlowStone CeresGlowStone;
		public static CeresBlockT4TreasureChest CeresTChestT4 = new CeresBlockT4TreasureChest("T4Chest");
		public static CeresBlockWeb CeresWeb = new CeresBlockWeb();
		// --------------------------
		// Pluto --------------------
		public static Block PlutoBlocks = new PlutoBlocks();
		//public static PlutoBlockGlowStone PlutoGlowStone;
		//public static PlutoBlockT8TreasureChest PlutoTChestT8;
		// --------------------------
		// Makemake -----------------
		//public static HaumeaBlocks HaumeaBlocks;
		// --------------------------
		// Makemake -----------------
		//public static MakemakeBlocks MakemakeBlocks;
		// --------------------------
		// Eris -----------------
		//public static ErisBlocks ErisBlocks;
		// --------------------------
		// Phobos/Deimos ------------
		public static Block PhobosBlocks = new PhobosBlocks();
		
		//public static DeimosBlocks DeimosBlocks;
		// --------------------------
		// Io -----------------------
		public static Block IoBlocks = new IoBlocks();
		//public static IOBlockGlowStone IoGlowStone;
		public static IoBlockT5TreasureChest IoTChestT5 = new IoBlockT5TreasureChest("T5Chest");
		//public static IOBlockWeb IoWeb;
		//public static IOBlockDualStone IoDualStone;
		// --------------------------
		// Europa -------------------
		public static Block EuropaBlocks = new EuropaBlocks();
		public static Block EuropaGeyser = new EuropaBlockGeyzer();
		public static Block EuropaUnderwaterGeyser = new EuropaBlockUnderwaterGeyser();
		// --------------------------
		// Ganymede -----------------
		public static Block GanymedeBlocks = new GanymedeBlocks();			
		// --------------------------
		// Callisto -----------------
		public static Block CallistoBlocks = new CallistoBlocks();
		// --------------------------
		// Enceladus ----------------
		public static Block EnceladusBlocks = new EnceladusBlocks();
		public static Block EnceladusCrystal = new EnceladusBlockCrystal();
		//public static EnceladusBlockGlowStone EnceladusGlowStone;
		//public static EnceladusBlockT6TreasureChest EnceladusTChestT6;
		// --------------------------
		// Titan --------------------
		public static Block TitanBlocks = new TitanBlocks();
		// --------------------------
		// Miranda ------------------
		public static Block MirandaBlocks = new MirandaBlocks();
		// --------------------------
		// Oberon -------------------
		//public static OberonBlocks OberonBlocks;
		// --------------------------
		// Proteus ------------------
		//public static ProteusBlocks ProteusBlocks;
		//public static ProteusBlockGlowStone ProteusGlowStone;
		//public static ProteusBlockT7TreasureChest ProteusTChestT7;
		// --------------------------
		// Triton ------------------
		public static Block TritonBlocks = new TritonBlocks();
		// --------------------------
		
		public static void initialize() 
		{

			GameRegistry.registerBlock(MachineFrames, ItemBlocksMachineFrames.class, "machineframes"); 
			GameRegistry.registerBlock(FuelGenerator, GSItemBlockDesc.class, "fuelgenerator");			
			GameRegistry.registerBlock(SolarWindPanel, GSItemBlockDesc.class, "solarwindpanel");
			GameRegistry.registerBlock(AssemblyMachine, GSItemBlockDesc.class, "assemblymachine");
			GameRegistry.registerBlock(ConverterSurface, GSItemBlockDesc.class, "convertersurface");
			GameRegistry.registerBlock(StorageModuleT3, GSItemBlockMachine.class, "storagemoduleT3");
			GameRegistry.registerBlock(OxStorageModuleT2, GSItemBlockMachine.class, "oxstoragemoduleT2");
			GameRegistry.registerBlock(SolarPanel, GSItemBlockDesc.class, "solarPanel");
			GameRegistry.registerBlock(WindTurbine, GSItemBlockDesc.class, "windTurbine");
			//GameRegistry.registerBlock(AdvOxygenSealer, GSItemBlockDesc.class, "advOxygenSealer");
			GameRegistry.registerBlock(AdvFuelLoader, GSItemBlockDesc.class, "advFuelLoader");
			GameRegistry.registerBlock(RocketAssembly, GSItemBlockDesc.class, "rocketAssembly");
			GameRegistry.registerBlock(Recycler, GSItemBlockDesc.class, "recycler");
			GameRegistry.registerBlock(LiquidExtractor, GSItemBlockDesc.class, "liquidextractor");
			GameRegistry.registerBlock(PortableNuclearReactor, GSItemBlockDesc.class, "portablenuclearreactor");
			GameRegistry.registerBlock(FluidTank, GSItemBlockDesc.class, "fluidtank");
			GameRegistry.registerBlock(AdvLandingPad, GSItemBlockDesc.class, "advlandingpad");
			GameRegistry.registerBlock(AdvLandingPadFull, GSItemBlockDesc.class, "advlandingpadfull");
			GameRegistry.registerBlock(GravitationModule, GSItemBlockMachine.class, "gravitationmoduleon");		
			//GameRegistry.registerBlock(CargoFluidLoader, GSItemBlockDesc.class, "cargofluidloader");
			GameRegistry.registerBlock(HydroponicBase, GSItemBlockDesc.class, "hydroponicbase");
			GameRegistry.registerBlock(HydroponicFarm, ItemBlocksHydroponicFarm.class, "hydroponicfarm");
			GameRegistry.registerBlock(LiquidSeparator, GSItemBlockDesc.class, "liquidseparator");
			GameRegistry.registerBlock(RadiationStabiliser, GSItemBlockDesc.class, "radiationstabiliser");
			GameRegistry.registerBlock(ModificationTable, GSItemBlockDesc.class, "modificationtable");
			GameRegistry.registerBlock(OxygenFiller, GSItemBlockDesc.class, "oxygenfiller");
			
			GameRegistry.registerBlock(DecoMetalsBlock, ItemBlocksDecoMetals.class, "decometalsblock");			
			GameRegistry.registerBlock(FutureGlass, "futureglass");
			GameRegistry.registerBlock(FutureGlasses, ItemBlocksFutureGlasses.class, "futureglasses");
			//GameRegistry.registerBlock(FutureLamp, "futurelamp");
			//GameRegistry.registerBlock(DuraluminumWire, ItemBlockDuraluminumWire.class, "duraluminumwire");			
			
			GameRegistry.registerBlock(MercuryBlocks, ItemBlocksMercury.class, "mercuryblocks");   
			GameRegistry.registerBlock(VenusBlocks, ItemBlocksVenus.class, "venusblocks");
			
			GameRegistry.registerBlock(Ores, ItemBlocksOres.class, "ores");
			GameRegistry.registerBlock(SurfaceIce, "surfaceice");
						
			GameRegistry.registerBlock(DungeonBricks, ItemBlockDungeonBricks.class, "dungeonbricks");
			GameRegistry.registerBlock(DungeonGlowstones, ItemBlockDungeonGlowstone.class, "dungeonglowstone");
			
			GameRegistry.registerBlock(MarsOresBlocks, ItemBlocksMarsOres.class, "marsoresblocks");
			
			GameRegistry.registerBlock(CeresBlocks, ItemBlocksCeres.class, "ceresblocks");
			GameRegistry.registerBlock(CeresTChestT4, GSItemBlockDesc.class, "ceresTChestT4");
			GameRegistry.registerBlock(CeresWeb, "ceresweb");
					
			GameRegistry.registerBlock(PlutoBlocks, ItemBlocksPluto.class, "plutoblocks");/*
			GameRegistry.registerBlock(PlutoGlowStone, "plutoglowstone");	
			GameRegistry.registerBlock(PlutoTChestT8, GSItemBlockDesc.class, "plutoTChestT8");
						
			GameRegistry.registerBlock(HaumeaBlocks, ItemBlocksHaumea.class, "haumeablocks");
			
			GameRegistry.registerBlock(MakemakeBlocks, ItemBlocksMakemake.class, "makemakegrunt");
			//GameRegistry.registerBlock(ErisBlocks, ItemBlocksEris.class, "erisgrunt");
			*/
			//GameRegistry.registerBlock(PhobosBlocks, ItemBlocksPhobos.class, "phobosblocks");
			/*
			GameRegistry.registerBlock(DeimosBlocks, ItemBlocksDeimos.class, "deimosblocks");
			*/
			GameRegistry.registerBlock(IoBlocks, ItemBlocksIo.class, "ioblocks");
			
			GameRegistry.registerBlock(IoTChestT5, GSItemBlockDesc.class, "ioTChestT5");		
			/*
			 * GameRegistry.registerBlock(IoWeb, "ioweb");
			GameRegistry.registerBlock(IoDualStone, "iodualstone");
			*/		
			GameRegistry.registerBlock(EuropaBlocks, ItemBlocksEuropa.class, "europablocks");
			GameRegistry.registerBlock(EuropaGeyser, "europageyser");
			GameRegistry.registerBlock(EuropaUnderwaterGeyser, "europaunderwatergeyser");
					
			GameRegistry.registerBlock(GanymedeBlocks, ItemBlocksGanymede.class, "ganymedeblocks");
			
			
			GameRegistry.registerBlock(CallistoBlocks, ItemBlocksCallisto.class, "callistoblocks");
			
			GameRegistry.registerBlock(EnceladusBlocks, ItemBlocksEnceladus.class, "enceladusblocks");
			GameRegistry.registerBlock(EnceladusCrystal, "enceladuscrystal");
			/*
			GameRegistry.registerBlock(EnceladusGlowStone, "enceladusglowstone");
			GameRegistry.registerBlock(EnceladusTChestT6, GSItemBlockDesc.class, "enceladusTChestT6");
			*/									
			GameRegistry.registerBlock(TitanBlocks, ItemBlocksTitan.class, "titanblocks");
			
			GameRegistry.registerBlock(MirandaBlocks, ItemBlocksMiranda.class, "mirandablocks");
			/*
			GameRegistry.registerBlock(OberonBlocks, ItemBlocksOberon.class, "oberonblocks");
						
			GameRegistry.registerBlock(ProteusBlocks, ItemBlocksProteus.class, "proteusblocks");
			GameRegistry.registerBlock(ProteusGlowStone, "proteusglowstone");
			GameRegistry.registerBlock(ProteusTChestT7, GSItemBlockDesc.class, "proteusTChestT7");
			*/				
			GameRegistry.registerBlock(TritonBlocks, ItemBlocksTriton.class, "tritonblocks");
					
			oreDictRegistration();
		   
	   }
	   
	   private static void oreDictRegistration()
	   {
		   
		   OreDictionary.registerOre("oreIron", new ItemStack(MercuryBlocks, 1, 3));
		   OreDictionary.registerOre("oreNickel", new ItemStack(MercuryBlocks, 1, 4));
		   
		   OreDictionary.registerOre("oreCobalt", new ItemStack(Ores, 1, 0));
		   OreDictionary.registerOre("oreNickel", new ItemStack(Ores, 1, 1));
		   OreDictionary.registerOre("oreUranium", new ItemStack(Ores, 1, 3));
		   
		   OreDictionary.registerOre("oreSulfur", new ItemStack(VenusBlocks, 1, 2));
		   OreDictionary.registerOre("oreDiamond", new ItemStack(VenusBlocks, 1, 3));
			  
		   OreDictionary.registerOre("oreDiamond", new ItemStack(MarsOresBlocks, 1, 0));
		   OreDictionary.registerOre("oreGold", new ItemStack(MarsOresBlocks, 1, 1));
		   OreDictionary.registerOre("oreCoal", new ItemStack(MarsOresBlocks, 1, 2));
		   OreDictionary.registerOre("oreRedstone", new ItemStack(MarsOresBlocks, 1, 3));
		   OreDictionary.registerOre("oreSilicon", new ItemStack(MarsOresBlocks, 1, 4));
		   OreDictionary.registerOre("oreAluminum", new ItemStack(MarsOresBlocks, 1, 5));
		   
		   OreDictionary.registerOre("oreDolomite", new ItemStack(CeresBlocks, 1, 2));
		   
		   OreDictionary.registerOre("oreCopper", new ItemStack(IoBlocks, 1, 3));
		   OreDictionary.registerOre("oreSulfur", new ItemStack(IoBlocks, 1, 4));
		   
		   OreDictionary.registerOre("oreEmerald", new ItemStack(EuropaBlocks, 1, 3));
		   OreDictionary.registerOre("oreSilicon", new ItemStack(EuropaBlocks, 1, 4));
		   OreDictionary.registerOre("oreAluminum", new ItemStack(EuropaBlocks, 1, 5));
		   
		   OreDictionary.registerOre("oreMagnesium", new ItemStack(GanymedeBlocks, 1, 2));
		   OreDictionary.registerOre("oreIlmenite", new ItemStack(GanymedeBlocks, 1, 3));
		   
		   OreDictionary.registerOre("oreCoal", new ItemStack(EnceladusBlocks, 1, 2));
		   
		   OreDictionary.registerOre("oreIron", new ItemStack(MirandaBlocks, 1, 3));
		   OreDictionary.registerOre("oreDolomite", new ItemStack(MirandaBlocks, 1, 4));
		   
		   OreDictionary.registerOre("oreUranium", new ItemStack(TritonBlocks, 1, 5));
		   
		   OreDictionary.registerOre("blockGlass", new ItemStack(FutureGlass, 1, 0));
		   OreDictionary.registerOre("blockGlass", new ItemStack(FutureGlasses, 1));
		   
		   OreDictionary.registerOre("cobblestone", new ItemStack(MarsBlocks.marsBlock, 1, 4));
		   OreDictionary.registerOre("glowstone", new ItemStack(DungeonGlowstones, 1, 0));
		   OreDictionary.registerOre("glowstone", new ItemStack(DungeonGlowstones, 1, 1));
		  /*OreDictionary.registerOre("glowstone", new ItemStack(EnceladusGlowStone, 1, 0));
		   OreDictionary.registerOre("glowstone", new ItemStack(ProteusGlowStone, 1, 0));
		   OreDictionary.registerOre("glowstone", new ItemStack(PlutoGlowStone, 1, 0));*/
	   }
	}
