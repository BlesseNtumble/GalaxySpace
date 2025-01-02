package galaxyspace.systems.SolarSystem.planets.overworld.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import galaxyspace.GalaxySpace;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.registers.fluids.GSFluids;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.systems.SolarSystem.moons.moon.recipe.AlienRecipes;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityHydroponicBase;
import ic2.api.item.IC2Items;
import micdoodle8.mods.galacticraft.api.recipe.CircuitFabricatorRecipes;
import micdoodle8.mods.galacticraft.api.recipe.CompressorRecipes;
import micdoodle8.mods.galacticraft.core.blocks.GCBlocks;
import micdoodle8.mods.galacticraft.core.items.GCItems;
import micdoodle8.mods.galacticraft.core.util.CompatibilityManager;
import micdoodle8.mods.galacticraft.core.util.RecipeUtil;
import micdoodle8.mods.galacticraft.planets.asteroids.items.AsteroidsItems;
import micdoodle8.mods.galacticraft.planets.mars.blocks.MarsBlocks;
import micdoodle8.mods.galacticraft.planets.mars.items.MarsItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;


public class CraftingRecipesOverworld {

	private static String plateIron = GSConfigCore.enablePlateOreDict ? "plateIron" : "compressedIron";
	private static String plateSteel = GSConfigCore.enablePlateOreDict ? "plateSteel" : "compressedSteel";
	private static String plateBronze = GSConfigCore.enablePlateOreDict ? "plateBronze" : "compressedBronze";
	private static String plateTitanium = GSConfigCore.enablePlateOreDict ? "plateTitanium" : "compressedTitanium";
	private static String plateAluminum = GSConfigCore.enablePlateOreDict ? "plateAluminum" : "compressedAluminum";
	private static String plateTin = GSConfigCore.enablePlateOreDict ? "plateTin" : "compressedTin";
	
	public static void loadRecipes() {
		addBlockRecipes();
		addItemRecipes();
		addBlockSmelting();
		addCompressor();
		addAssembly();
		addRocketAssembly();
		addRecycler();
		addCircuitFabricator();
		addOtherRecipes();
		parseRecipes();

	}

	private static void addBlockRecipes() {

	    /*RecipeUtil.addRecipe(new ItemStack(GSBlocks.MetalsBlock, 1, 0), new Object[] { "XXX", "XXX", "XXX", 'X', "ingotLead"});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.MetalsBlock, 1, 1), new Object[] { "XXX", "XXX", "XXX", 'X', "ingotAdamantite"});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.MetalsBlock, 1, 2), new Object[] { "XXX", "XXX", "XXX", 'X', "ingotCobalt"});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.MetalsBlock, 1, 3), new Object[] { "XXX", "XXX", "XXX", 'X', "ingotMagnesium"});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.MetalsBlock, 1, 4), new Object[] { "XXX", "XXX", "XXX", 'X', "ingotMithril"});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.MetalsBlock, 1, 5), new Object[] { "XXX", "XXX", "XXX", 'X', "ingotNickel"});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.MetalsBlock, 1, 6), new Object[] { "XXX", "XXX", "XXX", 'X', "ingotOriharukon"});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.MetalsBlock, 1, 7), new Object[] { "XXX", "XXX", "XXX", 'X', "ingotPlatinum"});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.MetalsBlock, 1, 8), new Object[] { "XXX", "XXX", "XXX", 'X', "ingotTungsten"});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.MetalsBlock, 1, 9), new Object[] { "XXX", "XXX", "XXX", 'X', "ingotDuralumin"});


	   RecipeUtil.addRecipe(new ItemStack(GSItems.Ingots, 9, 0), new Object[] { "X", 'X', new ItemStack(GSBlocks.MetalsBlock, 1, 1)});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.Ingots, 9, 1), new Object[] { "X", 'X', new ItemStack(GSBlocks.MetalsBlock, 1, 2)});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.Ingots, 9, 2), new Object[] { "X", 'X', new ItemStack(GSBlocks.MetalsBlock, 1, 9)});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.Ingots, 9, 3), new Object[] { "X", 'X', new ItemStack(GSBlocks.MetalsBlock, 1, 0)});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.Ingots, 9, 4), new Object[] { "X", 'X', new ItemStack(GSBlocks.MetalsBlock, 1, 3)});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.Ingots, 9, 5), new Object[] { "X", 'X', new ItemStack(GSBlocks.MetalsBlock, 1, 4)});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.Ingots, 9, 6), new Object[] { "X", 'X', new ItemStack(GSBlocks.MetalsBlock, 1, 5)});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.Ingots, 9, 7), new Object[] { "X", 'X', new ItemStack(GSBlocks.MetalsBlock, 1, 6)});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.Ingots, 9, 8), new Object[] { "X", 'X', new ItemStack(GSBlocks.MetalsBlock, 1, 7)});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.Ingots, 9, 9), new Object[] { "X", 'X', new ItemStack(GSBlocks.MetalsBlock, 1, 8)});
	   */
		
		RecipeUtil.addRecipe(new ItemStack(GSBlocks.Ores, 1, 4), new Object[] { "XXX", "XXX", "XXX", 'X', "sapphire"});
			
		RecipeUtil.addRecipe(new ItemStack(GSBlocks.DecoMetalsBlock, 4, 0), new Object[] { "   ", " X ", " Y ", 'X', "plateCobalt", 'Y', Blocks.stone});
		RecipeUtil.addRecipe(new ItemStack(GSBlocks.DecoMetalsBlock, 4, 1), new Object[] { "   ", " X ", " Y ", 'X', "plateMagnesium", 'Y', Blocks.stone});
		RecipeUtil.addRecipe(new ItemStack(GSBlocks.DecoMetalsBlock, 4, 2), new Object[] { "   ", " X ", " Y ", 'X', "plateNickel", 'Y', Blocks.stone});
		RecipeUtil.addRecipe(new ItemStack(GSBlocks.DecoMetalsBlock, 4, 3), new Object[] { "   ", " X ", " Y ", 'X', "plateCopper", 'Y', Blocks.stone});
	   
		RecipeUtil.addRecipe(new ItemStack(GSBlocks.FutureGlass, 8, 0), new Object[] { "XXX", "XWX", "XXX", 'W', new ItemStack(GCItems.flagPole, 1, 0), 'X', Blocks.glass});
		for (int var2 = 0; var2 < 16; ++var2)
		{
			RecipeUtil.addRecipe(new ItemStack(GSBlocks.FutureGlasses, 8, var2), new Object[] { "XXX", "XWX", "XXX", 'X', new ItemStack(GSBlocks.FutureGlass, 1, 0), 'W', new ItemStack(Items.dye, 1, var2)});
		}
	   
		RecipeUtil.addRecipe(new ItemStack(GSBlocks.SolarWindPanel, 1, 0), new Object[] { "XYX", "XZX", "VWV", 'V', new ItemStack(GCBlocks.aluminumWire, 1, 0), 'W', new ItemStack(GSBlocks.MachineFrames, 1, 0), 'X', plateSteel, 'Y', new ItemStack(GSItems.BasicItems, 1, 2), 'Z', GCItems.flagPole });
		RecipeUtil.addRecipe(new ItemStack(GSBlocks.FuelGenerator, 1, 0), new Object[] { "WYW", "WZW", "XXX", 'W', new ItemStack(GCItems.canister, 1, 1), 'X', plateSteel, 'Y', new ItemStack(Blocks.glass_pane, 1, 0), 'Z', new ItemStack(GCBlocks.machineBase, 1, 0)});
		RecipeUtil.addRecipe(new ItemStack(GSBlocks.AssemblyMachine, 1, 0), new Object[] { "VWV", "XYX", "ZWC", 'V', new ItemStack(GCItems.basicItem, 1, 9), 'W', new ItemStack(GCBlocks.aluminumWire, 1, 0), 'X', new ItemStack(GCItems.basicItem, 1, 13), 'Y', new ItemStack(Blocks.crafting_table, 1, 0), 'Z', new ItemStack(GCBlocks.machineBase, 1, 12), 'C', new ItemStack(GCBlocks.machineBase2, 1, 4)});
		RecipeUtil.addRecipe(new ItemStack(GSBlocks.ConverterSurface, 1, 0), new Object[] { "XVX", "XCX", "WZW", 'X', "ingotDesh", 'V', new ItemStack(AsteroidsItems.basicItem, 1, 8), 'C', new ItemStack(MarsBlocks.machine, 1, 0), 'W', plateSteel, 'Z', new ItemStack(MarsItems.marsItemBasic, 1, 6)});
		
		RecipeUtil.addRecipe(new ItemStack(GSBlocks.RocketAssembly, 1, 0), new Object[] { "VWV", "XYZ", "BNB", 'V', new ItemStack(GCItems.basicItem, 1, 14), 'W', new ItemStack(GCBlocks.nasaWorkbench, 1, 0), 'X', new ItemStack(GCBlocks.machineBase2, 1, 0), 'Y', new ItemStack(GSBlocks.MachineFrames, 1, 0), 'Z', new ItemStack(GCBlocks.machineBase2, 1, 4), 'B', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'N', GSBlocks.AssemblyMachine});
		  	   
		RecipeUtil.addRecipe(new ItemStack(GSBlocks.MachineFrames, 1, 0), new Object[] { "XYX", "WVW", "XYX", 'X', plateSteel, 'Y', new ItemStack(GCItems.basicItem, 1, 13), 'W', new ItemStack(GCBlocks.aluminumWire, 1, 0), 'V', new ItemStack(Blocks.redstone_block, 1, 0)});
		RecipeUtil.addRecipe(new ItemStack(GSBlocks.MachineFrames, 1, 2), new Object[] { "XYX", "WVW", "XYX", 'X', "plateMagnesium", 'Y', new ItemStack(GSItems.BasicItems, 1, 7), 'W', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'V', new ItemStack(GSBlocks.MachineFrames, 1, 0)});
	   
		RecipeUtil.addRecipe(new ItemStack(GSBlocks.StorageModuleT3, 1, 0), new Object[] { "XYX", "WVW", "XYX", 'X', new ItemStack(GSItems.Ingots, 1, 1), 'Y', new ItemStack(GCBlocks.machineTiered, 1, 8), 'W', new ItemStack(GSItems.BasicItems, 1, 7), 'V', new ItemStack(GSBlocks.MachineFrames, 1, 2)});
		RecipeUtil.addRecipe(new ItemStack(GSBlocks.OxStorageModuleT2, 1, 0), new Object[] { "XYX", "WVW", "XZX", 'X', new ItemStack(GCBlocks.machineBase2, 1, 8), 'Y', new ItemStack(GCItems.oxygenConcentrator, 1, 0), 'W', new ItemStack(GCBlocks.oxygenPipe, 1, 0), 'V', new ItemStack(GSBlocks.MachineFrames, 1, 2), 'Z', new ItemStack(GSItems.BasicItems, 1, 7)});
		RecipeUtil.addRecipe(new ItemStack(GSBlocks.SolarPanel, 1, 0), new Object[] { "XYX", "WVW", "CZC", 'X', new ItemStack(Items.gold_ingot, 1, 0), 'Y', new ItemStack(GCItems.basicItem, 1, 1), 'W', new ItemStack(GCItems.flagPole, 1, 0), 'V', new ItemStack(GSBlocks.MachineFrames, 1, 2), 'Z', new ItemStack(GCBlocks.solarPanel, 1, 4), 'C', new ItemStack(GCBlocks.aluminumWire, 1, 1)});
		RecipeUtil.addRecipe(new ItemStack(GSBlocks.GravitationModule, 1, 0), new Object[] { "XYX", "WVW", "CZC", 'X', new ItemStack(GSItems.CompressedPlates, 1, 4), 'Y', new ItemStack(GSItems.BasicItems, 1, 10), 'W', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'V', new ItemStack(GSBlocks.MachineFrames, 1, 0), 'Z', new ItemStack(GSBlocks.MachineFrames, 1, 2), 'C', new ItemStack(GCItems.basicItem, 1, 14)});
		RecipeUtil.addRecipe(new ItemStack(GSBlocks.WindTurbine, 1, 0), new Object[] { "XYX", "MVM", "CZC", 'X', "ingotAluminum", 'Y', new ItemStack(GCItems.oxygenFan, 1, 0), 'V', new ItemStack(GCItems.flagPole, 1, 0), 'M', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'Z', new ItemStack(GSBlocks.MachineFrames, 1, 0), 'C', new ItemStack(GCItems.meteoricIronIngot, 1, 1)});
		RecipeUtil.addRecipe(new ItemStack(GSBlocks.Recycler, 1, 0), new Object[] { "XYX", "MZM", "CVC", 'X', "ingotCobalt", 'Y', new ItemStack(GSBlocks.FluidTank, 1, 0), 'V', new ItemStack(GCBlocks.machineTiered, 1, 4), 'C', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'Z', new ItemStack(GSBlocks.MachineFrames, 1, 0), 'M', new ItemStack(GSItems.BasicItems, 1, 7)});
		RecipeUtil.addRecipe(new ItemStack(GSBlocks.FluidTank, 1, 0), new Object[] { "XZX", "MZM", "XZX", 'X', plateSteel, 'Z', new ItemStack(GSBlocks.FutureGlass, 1, 0), 'M', new ItemStack(GCItems.flagPole, 1, 0)});
		RecipeUtil.addRecipe(new ItemStack(GSBlocks.LiquidExtractor, 1, 0), new Object[] { "XZX", "MCM", "VBV", 'X', new ItemStack(MarsItems.marsItemBasic, 1, 6), 'Z', new ItemStack(GSBlocks.FluidTank, 1, 0), 'M', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'C', new ItemStack(GSBlocks.MachineFrames, 1, 0), 'V', new ItemStack(GSItems.BasicItems, 1, 7), 'B', new ItemStack(GCBlocks.oxygenPipe, 1, 0)});
		RecipeUtil.addRecipe(new ItemStack(GSBlocks.LiquidSeparator, 1, 0), new Object[] { "XZX", "ZCZ", "VBV", 'X', new ItemStack(MarsItems.marsItemBasic, 1, 6), 'Z', new ItemStack(GSBlocks.FluidTank, 1, 0), 'C', new ItemStack(GSBlocks.MachineFrames, 1, 0), 'V', new ItemStack(GSItems.BasicItems, 1, 7), 'B', new ItemStack(GCBlocks.aluminumWire, 1, 1)});

		RecipeUtil.addRecipe(new ItemStack(GSBlocks.AdvFuelLoader, 1, 0), new Object[] { "XZX", "ACA", "VBV", 'X', new ItemStack(GSItems.HeavyDutyPlates, 1, 0), 'Z', new ItemStack(GCItems.canister, 1, 0), 'C', new ItemStack(GSBlocks.MachineFrames, 1, 2), 'B', new ItemStack(GCItems.oilCanister, 1, GCItems.oilCanister.getMaxDamage()), 'V', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'A', new ItemStack(MarsBlocks.hydrogenPipe, 1, 0)});
	  
		RecipeUtil.addRecipe(new ItemStack(GSBlocks.HydroponicBase, 1, 0), new Object[] { "ABC", "DED", "DFD", 'A', new ItemStack(GCItems.basicItem, 1, 20), 'B', new ItemStack(GSBlocks.FluidTank, 1, 0), 'C', new ItemStack(GCItems.oxygenConcentrator, 1, 0), 'D', plateSteel, 'E', new ItemStack(GSBlocks.MachineFrames, 1, 0), 'F', new ItemStack(GCBlocks.aluminumWire, 1, 1)});
		RecipeUtil.addRecipe(new ItemStack(GSBlocks.HydroponicFarm, 1, 0), new Object[] { "XXX", "XCX", "VBV", 'X', new ItemStack(Blocks.glass, 1, 0), 'B', new ItemStack(GSBlocks.MachineFrames, 1, 0), 'C', new ItemStack(Blocks.dirt, 1, 0), 'V', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'A', new ItemStack(GCBlocks.aluminumWire, 1, 1)});

		RecipeUtil.addRecipe(new ItemStack(GSBlocks.RadiationStabiliser, 1, 0), new Object[] { "XYX", "ZCZ", "XBX", 'X', new ItemStack(GSItems.BasicItems, 1, 4), 'B', new ItemStack(GSItems.BasicItems, 1, 7), 'C', new ItemStack(GSBlocks.MachineFrames, 1, 0), 'Z', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'Y', new ItemStack(GCBlocks.basicBlock, 1, 9)});
		RecipeUtil.addRecipe(new ItemStack(GSBlocks.ModificationTable, 1, 0), new Object[] { "XYX", "ZCZ", "VBV", 'V', plateBronze, 'X', new ItemStack(GCItems.basicItem, 1, 14), 'B', new ItemStack(GSItems.BasicItems, 1, 7), 'C', new ItemStack(GSBlocks.MachineFrames, 1, 0), 'Z', new ItemStack(Items.redstone, 1, 0), 'Y', "plateCobalt"});
		RecipeUtil.addRecipe(new ItemStack(GSBlocks.OxygenFiller, 1, 0), new Object[] { "XYX", "ZZZ", "VBV", 'V', plateBronze, 'X', plateAluminum, 'B', new ItemStack(GSBlocks.MachineFrames, 1, 0), 'Z', new ItemStack(GCItems.canister, 1, 0), 'Y', new ItemStack(GCItems.oxygenConcentrator, 1, 0)});
		
   }

   private static void addItemRecipes()
   {

	   RecipeUtil.addRecipe(new ItemStack(GSItems.CobaltHelmet, 1), new Object[] { "XXX", "X X", 'X', "ingotCobalt"});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.CobaltPlate, 1), new Object[] { "X X", "XXX", "XXX", 'X', "ingotCobalt" });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.CobaltLeg, 1), new Object[] { "XXX", "X X", "X X",'X', "ingotCobalt"});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.CobaltBoots, 1), new Object[] { "X X", "X X", 'X', "ingotCobalt" });
	   
	   RecipeUtil.addRecipe(new ItemStack(GSItems.CobaltSword, 1), new Object[] { " X ", " X ", " Y ", 'X', "ingotCobalt", 'Y', "stickWood"});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.CobaltAxe, 1), new Object[] { "XX ", "XY ", " Y ", 'X', "ingotCobalt", 'Y', "stickWood"});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.CobaltPickaxe, 1), new Object[] { "XXX", " Y ", " Y ", 'X', "ingotCobalt", 'Y', "stickWood"});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.CobaltShovel, 1), new Object[] { " X ", " Y ", " Y ", 'X', "ingotCobalt", 'Y', "stickWood"});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.CobaltHoe, 1), new Object[] { "XX ", " Y ", " Y ", 'X', "ingotCobalt", 'Y', "stickWood"});
		  
	   
	   RecipeUtil.addRecipe(new ItemStack(GSItems.ThermalPaddingTier2, 1, 0), new Object[] { "XXX", "XYX", 'X', GSItems.ThermalClothTier2, 'Y', new ItemStack(AsteroidsItems.thermalPadding, 1, 0)});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.ThermalPaddingTier2, 1, 1), new Object[] { "X X", "XYX", "XXX", 'X', GSItems.ThermalClothTier2, 'Y', new ItemStack(AsteroidsItems.thermalPadding, 1, 1)});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.ThermalPaddingTier2, 1, 2), new Object[] { "XXX", "XYX", "X X",'X', GSItems.ThermalClothTier2, 'Y', new ItemStack(AsteroidsItems.thermalPadding, 1, 2)});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.ThermalPaddingTier2, 1, 3), new Object[] { "X X", "XYX", 'X', GSItems.ThermalClothTier2, 'Y', new ItemStack(AsteroidsItems.thermalPadding, 1, 3)});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.ThermalClothTier2, 4), new Object[] { "XZX", "ZYZ", "XZX", 'X', Blocks.wool, 'Y', new ItemStack(GSItems.BasicItems, 1, 6), 'Z', "ingotCobalt" });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.BasicItems, 1, 3), new Object[] { "XZX", "CYC", "BBB", 'X', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'Y', new ItemStack(MarsItems.marsItemBasic, 1, 6), 'Z', Items.redstone, 'C', new ItemStack(GSItems.Ingots, 1, 0), 'B', plateSteel });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.BasicItems, 3, 11), new Object[] { "XXX", "CYC", "XXX", 'X', plateIron, 'C', new ItemStack(GCItems.canister, 1, 0), 'Y', Items.milk_bucket});
		  
	   RecipeUtil.addRecipe(new ItemStack(GSItems.BasicItems, 1, 14), new Object[] { "XZX", "CYC", "BBB", 'X', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'Y', new ItemStack(GSItems.BasicItems, 1, 13), 'Z', Items.redstone, 'C', new ItemStack(Items.gold_ingot, 1, 0), 'B', plateSteel });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.BasicItems, 1, 15), new Object[] { "XZX", "CYC", "BBB", 'X', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'Y', new ItemStack(GSItems.BasicItems, 1, 10), 'Z', new ItemStack(GSItems.BasicItems, 1, 7), 'C', new ItemStack(Items.gold_ingot, 1, 0), 'B', plateSteel });
		  
	   int var2 = 100;

	   RecipeUtil.addRecipe(new ItemStack(GSItems.JetPack, 1, 100), new Object[] { "ABA", "CDC", "EFE", 'A', MarsBlocks.hydrogenPipe, 'B', new ItemStack(GCItems.battery, 1, var2), 'C', "compressedMeteoricIron", 'D', GCItems.steelChestplate, 'E', new ItemStack(GCItems.fuelCanister, 1, 1), 'F', new ItemStack(GCBlocks.aluminumWire, 1, 1) });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.PlasmaSword, 1), new Object[] { " X ", " X ", "ZVZ", 'X', "plateCobalt", 'Z', new ItemStack(GCBlocks.aluminumWire, 1, 0), 'V', new ItemStack(GCItems.battery, 1, var2) });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.PlasmaAxe, 1), new Object[] { "XX ", "XY ", "ZVZ", 'X', "plateCobalt", 'Z', new ItemStack(GCBlocks.aluminumWire, 1, 0), 'V', new ItemStack(GCItems.battery, 1, var2), 'Y', GCItems.flagPole });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.PlasmaPickaxe, 1), new Object[] { "XXX", " Y ", "ZVZ", 'X', "plateCobalt", 'Z', new ItemStack(GCBlocks.aluminumWire, 1, 0), 'V', new ItemStack(GCItems.battery, 1, var2), 'Y', GCItems.flagPole });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.PlasmaShovel, 1), new Object[] { " X ", " Y ", "ZVZ", 'X', "plateCobalt", 'Z', new ItemStack(GCBlocks.aluminumWire, 1, 0), 'V', new ItemStack(GCItems.battery, 1, var2), 'Y', GCItems.flagPole });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.PlasmaHoe, 1), new Object[] { "XX ", " Y ", "ZVZ", 'X', "plateCobalt", 'Z', new ItemStack(GCBlocks.aluminumWire, 1, 0), 'V', new ItemStack(GCItems.battery, 1, var2), 'Y', GCItems.flagPole });
	  
	   RecipeUtil.addRecipe(new ItemStack(GSItems.AdvancedBattery, 1, GSItems.AdvancedBattery.getMaxDamage()), new Object[] { " T ", "TRT", "TCT", 'T', "plateNickel", 'R', new ItemStack(GCItems.battery, 1, GCItems.battery.getMaxDamage()), 'C', Items.redstone });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.ModernBattery, 1, GSItems.ModernBattery.getMaxDamage()), new Object[] { "RTR", "TYT", "TCT", 'T', plateTitanium, 'R', new ItemStack(GSItems.AdvancedBattery, 1, GSItems.AdvancedBattery.getMaxDamage()), 'C', Items.redstone, 'Y', new ItemStack(GSItems.BasicItems, 1, 9) });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.ExtraBattery, 1, GSItems.ExtraBattery.getMaxDamage()), new Object[] { "RTR", "TYT", "TCT", 'T', new ItemStack(GSItems.HeavyDutyPlates, 1, 0), 'R', new ItemStack(GSItems.ModernBattery, 1, GSItems.ModernBattery.getMaxDamage()), 'C', Items.redstone, 'Y', new ItemStack(GSItems.BasicItems, 1, 9) });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.UltraBattery, 1, GSItems.UltraBattery.getMaxDamage()), new Object[] { "RTR", "TYT", "TCT", 'T', new ItemStack(GSItems.HeavyDutyPlates, 1, 0), 'R', new ItemStack(GSItems.ExtraBattery, 1, GSItems.ExtraBattery.getMaxDamage()), 'C', Items.redstone, 'Y', new ItemStack(GSItems.BasicItems, 1, 10) });

	   RecipeUtil.addRecipe(new ItemStack(GSItems.QuantBow, 1), new Object[] { " XY", "XTY", " XY", 'X', "ingotCobalt", 'Y', Items.string, 'T', Items.blaze_rod });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.BasicItems, 1, 1), new Object[] { "X  ", "YX ", "VZX", 'Y', plateSteel, 'X', GCItems.flagPole, 'V', new ItemStack(GCItems.basicItem, 1, 1), 'Z', plateIron});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.BasicItems, 1, 2), new Object[] { "YXY", "XYX", "YXY", 'X', new ItemStack(GSItems.BasicItems, 1, 1), 'Y', GCItems.flagPole });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.RocketModules, 1, 0), new Object[] { "ZXX", "XYV", "BNB", 'Z', new ItemStack(GCItems.basicItem, 1, 19), 'X', new ItemStack(GCItems.basicItem, 1, 9), 'Y', new ItemStack(GCItems.partBuggy, 1, 1), 'V', new ItemStack(GCItems.basicItem, 1, 6), 'B', GCItems.flagPole, 'N', new ItemStack(GCItems.rocketEngine, 1, 0)});
	   for (int var3 = 0; var3 < 16; ++var3)
       {
		   RecipeUtil.addRecipe(new ItemStack(GSItems.RocketModules, 1, 1), new Object[] { "ZXZ", "YVY", "ZYZ", 'Z', new ItemStack(Items.string, 1, 0), 'X', new ItemStack(GCItems.parachute, 1, var3), 'Y', GCItems.canvas, 'V', new ItemStack(GSItems.RocketModules, 1, 0)});
       }
	   RecipeUtil.addRecipe(new ItemStack(GSItems.RocketModules, 1, 2), new Object[] { "ZXZ", "YWY", "ZZZ", 'Z', plateSteel, 'X', new ItemStack(Blocks.chest, 1, 0), 'Y', new ItemStack(GCItems.flagPole, 1, 0), 'W', new ItemStack(GSItems.RocketModules, 1, 1)});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.OxygenTankTier4, 1, GSItems.OxygenTankTier4.getMaxDamage()), new Object[] { "XYX", "ZCZ", "ZZZ", 'X', new ItemStack(Blocks.wool, 1, 11), 'Y', GCBlocks.oxygenPipe, 'C', new ItemStack(GCItems.oxTankHeavy, 1, GCItems.oxTankHeavy.getMaxDamage()), 'Z', new ItemStack(AsteroidsItems.basicItem , 1, 6) });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.OxygenTankTier5, 1, GSItems.OxygenTankTier5.getMaxDamage()), new Object[] { "XYX", "ZCZ", "ZZZ", 'X', new ItemStack(GCItems.oxygenConcentrator, 1, 0), 'Y', GCBlocks.oxygenPipe, 'C', new ItemStack(GSItems.OxygenTankTier4, 1, GSItems.OxygenTankTier4.getMaxDamage()), 'Z', new ItemStack(AsteroidsItems.basicItem , 1, 6) });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.OxygenTankTier6, 1, GSItems.OxygenTankTier6.getMaxDamage()), new Object[] { "XYA", "ZCZ", "ZZZ", 'X', new ItemStack(GCItems.oxygenVent, 1, 0), 'Y', GCBlocks.oxygenPipe, 'C', new ItemStack(GSItems.OxygenTankTier5, 1, GSItems.OxygenTankTier5.getMaxDamage()), 'Z', new ItemStack(AsteroidsItems.basicItem , 1, 6), 'A', GCItems.oxygenConcentrator });
	   
	   RecipeUtil.addRecipe(new ItemStack(GSItems.OxygenCanister, 1, GSItems.OxygenCanister.getMaxDamage()), new Object[] { "ZAZ", "ZBZ", "ZCZ", 'Z', plateTin, 'A', new ItemStack(GCItems.oxygenConcentrator, 1, 0), 'B', new ItemStack(GCItems.canister , 1, 0), 'C', new ItemStack(Blocks.wool, 1, 5) });
	   
	   GameRegistry.addShapelessRecipe(new ItemStack(GSItems.BasicItems, 9, 6), new Object[] { new ItemStack(GSBlocks.Ores, 1, 4) });
	  
	   for (int var3 = 0; var3 < 2; ++var3)
		   GameRegistry.addRecipe(new ItemStack(GSBlocks.DungeonGlowstones, 1, var3), new Object[]{"##", "##", '#', new ItemStack(GSItems.GlowstoneDusts, 1, var3)});
   }

   private static void addBlockSmelting() {
	   
	   GameRegistry.addSmelting(new ItemStack(GSBlocks.Ores, 1, 0), new ItemStack(GSItems.Ingots, 1, 0), 1.0F);
	   GameRegistry.addSmelting(new ItemStack(GSBlocks.Ores, 1, 1), new ItemStack(GSItems.Ingots, 1, 2), 1.0F);
	   GameRegistry.addSmelting(GSItems.IceBucket, new ItemStack(Items.water_bucket), 0.0F);
	   GameRegistry.addSmelting(new ItemStack(GSItems.BasicItems, 1, 8), new ItemStack(Items.iron_ingot, 2), 1.0F);
	   		  
   }
   
   private static void addCompressor() {
  
	   CompressorRecipes.addShapelessRecipe(new ItemStack(GSItems.CompressedPlates, 1, 0), "ingotCobalt", "ingotCobalt");
	   CompressorRecipes.addShapelessRecipe(new ItemStack(GSItems.CompressedPlates, 1, 1), "ingotMagnesium", "ingotMagnesium");
	   CompressorRecipes.addShapelessRecipe(new ItemStack(GSItems.CompressedPlates, 1, 2), "ingotNickel", "ingotNickel");
	   CompressorRecipes.addShapelessRecipe(new ItemStack(GSItems.CompressedPlates, 1, 3), Blocks.coal_block);
	 
	   CompressorRecipes.addShapelessRecipe(new ItemStack(GSItems.BasicItems, 1, 19), plateIron, plateTitanium, plateIron, plateIron, "uranium", plateIron, plateIron, plateTitanium, plateIron);
	   
	   if(CompatibilityManager.isIc2Loaded())
		   CompressorRecipes.addShapelessRecipe(new ItemStack(GSItems.BasicItems, 1, 19), plateIron, plateTitanium, plateIron, plateIron, IC2Items.getItem("UranFuel"), plateIron, plateIron, plateTitanium, plateIron);
		  
   }
   
   private static void addCircuitFabricator() {
	   ItemStack modernWafers = new ItemStack(GSItems.BasicItems, 1, 7);
	   
	   CircuitFabricatorRecipes.addRecipe(modernWafers, 
			   new ItemStack[] 
					   { 
							   new ItemStack(Items.diamond), 
							   new ItemStack(GSItems.BasicItems, 1, 6), 
							   new ItemStack(GSItems.BasicItems, 1, 6), 
							   new ItemStack(Items.redstone), 
							   new ItemStack(GCItems.basicItem, 1, 14) 
					   });		
   }

   private static void addAssembly() {
	   
	   AssemberRecipes.instance.addShapelessRecipe(new ItemStack(GSItems.CompressedPlates, 1, 4), "plateBronze", "plateCoal", "plateAluminum");
	   AssemberRecipes.instance.addShapelessRecipe(new ItemStack(GSItems.BasicItems, 1, 0), plateSteel, plateSteel, plateSteel, plateSteel, Blocks.glass_pane, plateSteel, plateSteel, plateSteel, plateSteel);
	   AssemberRecipes.instance.addShapelessRecipe(new ItemStack(GSItems.RocketModules, 1, 3), new ItemStack(GSItems.CompressedPlates, 1, 4), new ItemStack(GSItems.BasicItems, 1, 0), new ItemStack(GSItems.CompressedPlates, 1, 4));
	   AssemberRecipes.instance.addShapelessRecipe(new ItemStack(GSItems.RocketModules, 1, 4), new ItemStack(GSItems.CompressedPlates, 1, 2), new ItemStack(GSItems.RocketModules, 1, 3), new ItemStack(GSItems.CompressedPlates, 1, 2), new ItemStack(GCItems.basicItem, 1, 13), new ItemStack(GCItems.oxygenFan), new ItemStack(GCItems.basicItem, 1, 13), new ItemStack(GCItems.basicItem, 1, 8), new ItemStack(GCItems.basicItem, 1, 6), new ItemStack(GCItems.basicItem, 1, 8));

	   AssemberRecipes.instance.addShapelessRecipe(new ItemStack(GSItems.SpacesuitHelmet, 1), new ItemStack(GSItems.CompressedPlates, 1, 4), plateSteel, new ItemStack(GSItems.CompressedPlates, 1, 4), plateSteel, new ItemStack(Blocks.stained_glass_pane, 1, 1), plateSteel, new ItemStack(GSItems.CompressedPlates, 1, 4), GCItems.oxygenConcentrator, new ItemStack(GSItems.CompressedPlates, 1, 4));
	   AssemberRecipes.instance.addShapelessRecipe(new ItemStack(GSItems.SpacesuitPlate, 1), plateSteel, "ingotNickel", plateSteel, new ItemStack(GSItems.CompressedPlates, 1, 4), new ItemStack(GCItems.steelChestplate, 1, 0), new ItemStack(GSItems.CompressedPlates, 1, 4), plateSteel, new ItemStack(GSItems.CompressedPlates, 1, 4), plateSteel);
	   AssemberRecipes.instance.addShapelessRecipe(new ItemStack(GSItems.SpacesuitLeg, 1), new ItemStack(GSItems.CompressedPlates, 1, 4), plateSteel, new ItemStack(GSItems.CompressedPlates, 1, 4), plateSteel, "ingotNickel", plateSteel, plateSteel, "ingotNickel", plateSteel);
	   AssemberRecipes.instance.addShapelessRecipe(new ItemStack(GSItems.SpacesuitBoots, 1), plateSteel, new ItemStack(GSItems.CompressedPlates, 1, 4), plateSteel, plateSteel, new ItemStack(GSItems.CompressedPlates, 1, 4), plateSteel, "ingotNickel", new ItemStack(GSItems.CompressedPlates, 1, 4), "ingotNickel");
	  
	   AssemberRecipes.instance.addShapelessRecipe(new ItemStack(GSItems.HeavyDutyPlates, 1, 0), "plateMagnesium", new ItemStack(GSItems.CompressedPlates, 1, 4), new ItemStack(AsteroidsItems.basicItem, 1, 0));
	   AssemberRecipes.instance.addShapelessRecipe(new ItemStack(GSItems.HeavyDutyPlates, 1, 1), "plateCobalt", new ItemStack(GSItems.HeavyDutyPlates, 1, 0), "plateNickel", new ItemStack(GSItems.BasicItems, 1, 4));
	   AssemberRecipes.instance.addShapelessRecipe(new ItemStack(GSItems.HeavyDutyPlates, 1, 2), new ItemStack(GSItems.CompressedPlates, 1, 4), new ItemStack(GSItems.HeavyDutyPlates, 1, 1), new ItemStack(GSItems.BasicItems, 1, 10));
		  
	   AssemberRecipes.instance.addShapelessRecipe(new ItemStack(GSBlocks.AdvLandingPad, 5, 0), "compressedTitanium", "compressedTitanium", "compressedTitanium", new ItemStack(GSItems.CompressedPlates, 1, 4), new ItemStack(GSItems.CompressedPlates, 1, 4), new ItemStack(GSItems.CompressedPlates, 1, 4), new ItemStack(GCBlocks.basicBlock, 1, 12), new ItemStack(GCBlocks.basicBlock, 1, 12), new ItemStack(GCBlocks.basicBlock, 1, 12));
		
   }
   
   private static void addRocketAssembly() {
	   addRocketRecipe(AsteroidsItems.tier3Rocket, GSItems.RocketParts, 0);
	   addRocketRecipe(GSItems.Tier4Rocket, GSItems.RocketParts, 5);
	   addRocketRecipe(GSItems.Tier5Rocket, GSItems.RocketParts, 10);
	   addRocketRecipe(GSItems.Tier6Rocket, GSItems.RocketParts, 15);
   }
   
   private static void addRecycler() {
	   
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(GCBlocks.blockMoon, 1, 4), new ItemStack(GSItems.BasicItems, 1, 6), 25, null);	 
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(GCBlocks.blockMoon, 1, 5), new ItemStack(GCBlocks.blockMoon, 1, 3), new FluidStack(GSFluids.Helium3, 10));	   
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(GSItems.BasicItems, 1, 4), new ItemStack(GSItems.BasicItems, 6, 5), null);
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(MarsBlocks.marsBlock, 20, 5), new ItemStack(GSItems.BasicItems, 1, 8), null);
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(MarsBlocks.marsBlock, 1, 4), new ItemStack(Blocks.cobblestone, 1, 0), null);
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(GSItems.BasicItems, 1, 9), new ItemStack(Items.gunpowder, 1, 0), new FluidStack(GSFluids.SulfurAcid, 5));
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(Blocks.packed_ice, 1), null, new FluidStack(FluidRegistry.WATER, 100));  
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(GSBlocks.SurfaceIce, 1), null, new FluidStack(FluidRegistry.WATER, 100));
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(GSBlocks.Ores, 1, 3), new ItemStack(GSItems.BasicItems, 1, 16), null);
   }
   
   private static void addRocketRecipe(Item rocket, Item parts, int metafirstparts)
   {
	   /**
		* Meta first parts:
		* +0: Cone
		* +1: Body
		* +2: Engine
		* +3: Boosters
		* +4: Vanes/Fins
		*/
	   RocketAssemblyRecipes.addShapelessRecipe(new ItemStack(rocket, 1, 0), new ItemStack(parts, 1, metafirstparts), new ItemStack(parts, 1, metafirstparts + 1), new ItemStack(parts, 1, metafirstparts + 1), new ItemStack(parts, 1, metafirstparts + 2), new ItemStack(parts, 1, metafirstparts + 3), new ItemStack(parts, 1, metafirstparts + 3), new ItemStack(parts, 1, metafirstparts + 4), new ItemStack(parts, 1, metafirstparts + 4));
	   RocketAssemblyRecipes.addShapelessRecipe(new ItemStack(rocket, 1, 1), new ItemStack(parts, 1, metafirstparts), new ItemStack(parts, 1, metafirstparts + 1), new ItemStack(parts, 1, metafirstparts + 1), new ItemStack(parts, 1, metafirstparts + 2), new ItemStack(parts, 1, metafirstparts + 3), new ItemStack(parts, 1, metafirstparts + 3), new ItemStack(parts, 1, metafirstparts + 4), new ItemStack(parts, 1, metafirstparts + 4), new ItemStack(Blocks.chest, 1, 0));
	   RocketAssemblyRecipes.addShapelessRecipe(new ItemStack(rocket, 1, 2), new ItemStack(parts, 1, metafirstparts), new ItemStack(parts, 1, metafirstparts + 1), new ItemStack(parts, 1, metafirstparts + 1), new ItemStack(parts, 1, metafirstparts + 2), new ItemStack(parts, 1, metafirstparts + 3), new ItemStack(parts, 1, metafirstparts + 3), new ItemStack(parts, 1, metafirstparts + 4), new ItemStack(parts, 1, metafirstparts + 4), new ItemStack(Blocks.chest, 1, 0), new ItemStack(Blocks.chest, 1, 0));
	   RocketAssemblyRecipes.addShapelessRecipe(new ItemStack(rocket, 1, 3), new ItemStack(parts, 1, metafirstparts), new ItemStack(parts, 1, metafirstparts + 1), new ItemStack(parts, 1, metafirstparts + 1), new ItemStack(parts, 1, metafirstparts + 2), new ItemStack(parts, 1, metafirstparts + 3), new ItemStack(parts, 1, metafirstparts + 3), new ItemStack(parts, 1, metafirstparts + 4), new ItemStack(parts, 1, metafirstparts + 4), new ItemStack(Blocks.chest, 1, 0), new ItemStack(Blocks.chest, 1, 0), new ItemStack(Blocks.chest, 1, 0));
   }
   
   private static void addOtherRecipes()
   {
	   TileEntityHydroponicBase.addPlant(new ItemStack(Items.wheat_seeds), new ItemStack(Items.wheat), new ItemStack(Items.wheat_seeds), 100, Blocks.wheat, 7, new boolean[] {false, true});
	   TileEntityHydroponicBase.addPlant(new ItemStack(Items.carrot), new ItemStack(Items.carrot), null, 100, Blocks.carrots, 3, new boolean[] {true, false});
	   TileEntityHydroponicBase.addPlant(new ItemStack(Items.potato), new ItemStack(Items.potato), new ItemStack(Items.poisonous_potato), 2, Blocks.potatoes, 3, new boolean[] {true, false});
	   TileEntityHydroponicBase.addPlant(new ItemStack(Items.melon_seeds), new ItemStack(Blocks.melon_block), null, 100, Blocks.melon_stem, 1, new boolean[] {false, false});
	   TileEntityHydroponicBase.addPlant(new ItemStack(Items.pumpkin_seeds), new ItemStack(Blocks.pumpkin), null, 100, Blocks.pumpkin_stem, 3, new boolean[] {false, false});
	
   }
   public static void parseRecipes() 
   {
       // ���� ������ ����� � ��������� �� �������.
       String[] config = GSConfigCore.tradeIDs;
       
       if (config != null)
           for (String recipe : config) {
               // ������ �������: "����������;����������;���������" ���
               // "����������;���������".
               String[] info = recipe.split(";");
               if (info.length == 2 || info.length == 3) {
                   ItemStack first = parseStack(info[0]);
                   if (first != null) {
                       ItemStack result = parseStack(info[info.length == 3 ? 2 : 1]);
                       if (result != null) {
                           ItemStack second = null;
                           if (info.length == 3)
                               second = parseStack(info[1]);
                           if (second != null) {
                               // ����������� �������, ��� ��� �����������.
                        	   AlienRecipes.getInstance().addRecipe(result, first, second);
                        	   //GalaxySpace.debug(first + " " + second + " " + result);                        	   
                           } else {
                               // ����������� �������, ��� ���� ����������.
                        	   AlienRecipes.getInstance().addRecipe(result, first, null);
                        	   //GalaxySpace.debug(first + " " + result);
                           }
                       } else {
                           // ���, ������� �����������, ���� ��������� ��
                           // ������.
                    	   throw new RuntimeException("[GalaxySpace] Error Alien trace recipe! Result not found!");
                       }
                   } else {
                       // ���, ������� �����������, ���� ������ ���������� ��
                       // ������.
                	   throw new RuntimeException("[GalaxySpace] Error Alien trace recipe! First component not found!");
                       
                   }
               } else {
                   // ���, ������� �����������, ���� ������ ��������� �������.
            	   throw new RuntimeException("[GalaxySpace] Error Alien trace recipe! Inncorect format!");
                   
               }
           }
   }
   
   public static ItemStack parseStack(String input) {
       if (input != null) {
           Item item = null;
           int count = 1;
           int meta = OreDictionary.WILDCARD_VALUE;
           // ������ �����: "�������-�����".
           String[] stackInfo = input.split("-");
           if (stackInfo.length == 2) {
               // ������ ��������: "modid:name" ��� "name" ��� �������
               // ���������.
               String[] itemInfo = stackInfo[0].split(":");
               item = GameRegistry.findItem(itemInfo.length == 1 ? "minecraft" : itemInfo[0],
                       itemInfo[itemInfo.length == 1 ? 0 : 1]);
               if (item != null) {
                   // ������ �������� � ������� �����: "������_�����:��������",
                   // ��������� "������_�����" ��� "������_�����:-1" ��� �����
                   // ��������.
                   String[] metaSize = stackInfo[1].split(":");
                   try {
                       count = Integer.valueOf(metaSize[0]);
                   } catch (Exception e) {
                       count = 1;
                       // ������ � ���, ��� ������ ���������� ������������.
                       GalaxySpace.debug("[GalaxySpace] Error Alien trace recipe! Inncorect count format!");
                   }
                   if (metaSize.length == 2)
                       try {
                           meta = Integer.valueOf(metaSize[1]);
                       } catch (Exception e) {
                           meta = OreDictionary.WILDCARD_VALUE;
                           // ������ � ���, ��� ������ �������� ������������.
                           GalaxySpace.debug("[GalaxySpace] Error Alien trace recipe! Inncorect metadata format!");                           
                       }
                   if (count < 1)
                       count = 1;
                   if (meta < 0)
                       meta = OreDictionary.WILDCARD_VALUE;
                   return new ItemStack(item, count, meta);
               } else {
                   // ���, ������� �����������, ���� ������� �� ������.
            	   throw new RuntimeException("[GalaxySpace] Error Alien trace recipe! Item " + item + " not found!");
               }
           } else {
               // ���, ������� �����������, ���� ������ ������������.
        	   throw new RuntimeException("[GalaxySpace] Error Alien trace recipe! Inncorect format!");               
           }
       }
       return null;
   }
   
   
}
