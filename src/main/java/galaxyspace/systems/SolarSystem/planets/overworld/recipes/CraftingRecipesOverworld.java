package galaxyspace.systems.SolarSystem.planets.overworld.recipes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import galaxyspace.core.GSBlocks;
import galaxyspace.core.GSFluids;
import galaxyspace.core.GSItems;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.configs.GSConfigSchematics;
import galaxyspace.core.util.GSRecipeUtil;
import galaxyspace.systems.SolarSystem.moons.miranda.blocks.MirandaBlocks;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemBasicGS;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemBasicGS.BasicItems;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityFuelGenerator;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityHydroponicBase;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityLiquidExtractor;
import micdoodle8.mods.galacticraft.api.recipe.CircuitFabricatorRecipes;
import micdoodle8.mods.galacticraft.api.recipe.CompressorRecipes;
import micdoodle8.mods.galacticraft.api.recipe.INasaWorkbenchRecipe;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.recipe.NasaWorkbenchRecipe;
import micdoodle8.mods.galacticraft.core.recipe.ShapedRecipeNBT;
import micdoodle8.mods.galacticraft.core.tile.TileEntityDeconstructor;
import micdoodle8.mods.galacticraft.core.util.RecipeUtil;
import micdoodle8.mods.galacticraft.planets.asteroids.AsteroidsModule;
import micdoodle8.mods.galacticraft.planets.asteroids.blocks.AsteroidBlocks;
import micdoodle8.mods.galacticraft.planets.asteroids.items.AsteroidsItems;
import micdoodle8.mods.galacticraft.planets.mars.blocks.MarsBlocks;
import micdoodle8.mods.galacticraft.planets.mars.items.MarsItems;
import micdoodle8.mods.galacticraft.planets.venus.VenusBlocks;
import micdoodle8.mods.galacticraft.planets.venus.VenusItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;


public class CraftingRecipesOverworld {

	private static String plateIron = GSConfigCore.enablePlateOreDict ? "plateIron" : "compressedIron";
	private static String plateSteel = GSConfigCore.enablePlateOreDict ? "plateSteel" : "compressedSteel";
	private static String plateTitanium = GSConfigCore.enablePlateOreDict ? "plateTitanium" : "compressedTitanium";
	private static String plateBronze = GSConfigCore.enablePlateOreDict ? "plateBronze" : "compressedBronze";
	private static String plateTin = GSConfigCore.enablePlateOreDict ? "plateTin" : "compressedTin";
	private static String plateDesh = GSConfigCore.enablePlateOreDict ? "plateDesh" : "compressedDesh";
	private static String plateAluminum = GSConfigCore.enablePlateOreDict ? "plateAluminum" : "compressedAluminum";
	private static String plateCopper = GSConfigCore.enablePlateOreDict ? "plateCopper" : "compressedCopper";
	private static String blockCopper = "blockCopper";
	
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
	}

	private static void addBlockRecipes() {

	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.DECO_METALS, 4, 0), new Object[] { "   ", " X ", " Y ", 'X', "plateCobalt", 'Y', Blocks.STONE});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.DECO_METALS, 4, 1), new Object[] { "   ", " X ", " Y ", 'X', "plateMagnesium", 'Y', Blocks.STONE});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.DECO_METALS, 4, 2), new Object[] { "   ", " X ", " Y ", 'X', "plateNickel", 'Y', Blocks.STONE});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.DECO_METALS, 4, 3), new Object[] { "   ", " X ", " Y ", 'X', plateCopper, 'Y', Blocks.STONE});
		   
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.DECO_METALS, 1, 4), new Object[] { "XXX", "XXX", "XXX", 'X', "ingotCobalt"});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.DECO_METALS, 1, 5), new Object[] { "XXX", "XXX", "XXX", 'X', "ingotNickel"});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.DECO_METALS, 1, 6), new Object[] { "XXX", "XXX", "XXX", 'X', "ingotMagnesium"});

	   RecipeUtil.addRecipe(new ItemStack(GSItems.INGOTS, 9, 0), new Object[] { "X", 'X', new ItemStack(GSBlocks.DECO_METALS, 1, 4)});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.INGOTS, 9, 1), new Object[] { "X", 'X', new ItemStack(GSBlocks.DECO_METALS, 1, 6)});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.INGOTS, 9, 2), new Object[] { "X", 'X', new ItemStack(GSBlocks.DECO_METALS, 1, 5)});
		
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.FUTURE_GLASS_BASIC, 8, 0), new Object[] { "XXX", "XWX", "XXX", 'W', new ItemStack(GCItems.flagPole, 1, 0), 'X', Blocks.GLASS});
	   
	   for (int var2 = 0; var2 < 16; ++var2)       
		   RecipeUtil.addRecipe(new ItemStack(GSBlocks.FUTURE_GLASS_COLORED, 8, var2), new Object[] { "XXX", "XWX", "XXX", 'X', new ItemStack(GSBlocks.FUTURE_GLASS_BASIC, 1, 0), 'W', new ItemStack(Items.DYE, 1, var2)});
	   
	   
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.SOLARWIND_PANEL, 1, 0), new Object[] { "XYX", "XZX", "VWV", 'V', new ItemStack(GCBlocks.aluminumWire, 1, 0), 'W', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 0), 'X', plateSteel, 'Y', BasicItems.SOLARFLARES.getItemStack(), 'Z', GCItems.flagPole });
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.FUEL_GENERATOR, 1, 0), new Object[] { "WYW", "WZW", "XXX", 'W', new ItemStack(GCItems.canister, 1, 1), 'X', plateSteel, 'Y', new ItemStack(Blocks.GLASS_PANE, 1, 0), 'Z', new ItemStack(GCBlocks.machineBase, 1, 0)});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.ASSEMBLER, 1, 0), new Object[] { "VWV", "XYX", "ZWC", 'V', plateSteel, 'W', new ItemStack(GCBlocks.aluminumWire, 1, 0), 'X', new ItemStack(GCItems.basicItem, 1, 13), 'Y', new ItemStack(Blocks.CRAFTING_TABLE, 1, 0), 'Z', new ItemStack(GCBlocks.machineBase, 1, 12), 'C', new ItemStack(GCBlocks.machineBase2, 1, 4)});
		
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.ROCKET_ASSEMBLER, 1, 0), new Object[] { "VWV", "XYZ", "BNB", 'V', new ItemStack(GCItems.basicItem, 1, 14), 'W', new ItemStack(GCBlocks.nasaWorkbench, 1, 0), 'X', new ItemStack(GCBlocks.machineBase2, 1, 0), 'Y', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 0), 'Z', new ItemStack(GCBlocks.machineBase2, 1, 4), 'B', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'N', new ItemStack(GSBlocks.ASSEMBLER)});
		  	   
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 0), new Object[] { "XYX", "WVW", "XYX", 'X', plateSteel, 'Y', new ItemStack(GCItems.basicItem, 1, 13), 'W', new ItemStack(GCBlocks.aluminumWire, 1, 0), 'V', new ItemStack(Blocks.REDSTONE_BLOCK, 1, 0)});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 1), new Object[] { "XYX", "WVW", "XYX", 'X', plateTitanium, 'Y', new ItemStack(GCItems.basicItem, 1, 14), 'W', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'V', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 0)});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 2), new Object[] { "XYX", "WVW", "XYX", 'X', "plateMagnesium", 'Y', BasicItems.WAFER_MODERN.getItemStack(), 'W', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'V', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 1)});
	   
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.MODERN_STORAGE_MODULE, 1, 0), new Object[] { "XYX", "WVW", "XYX", 'X', new ItemStack(GSItems.INGOTS, 1, 1), 'Y', new ItemStack(GCBlocks.machineTiered, 1, 8), 'W', BasicItems.WAFER_MODERN.getItemStack(), 'V', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 2)});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.OXYGEN_STORAGE_MODULE, 1, 0), new Object[] { "XYX", "WVW", "XZX", 'X', new ItemStack(GCBlocks.machineBase2, 1, 8), 'Y', new ItemStack(GCItems.oxygenConcentrator, 1, 0), 'W', new ItemStack(GCBlocks.oxygenPipe, 1, 0), 'V', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 1), 'Z', BasicItems.WAFER_MODERN.getItemStack()});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.MODERN_SOLAR_PANEL, 1, 0), new Object[] { "XYX", "WVW", "CZC", 'X', new ItemStack(Items.GOLD_INGOT, 1, 0), 'Y', new ItemStack(GCItems.basicItem, 1, 1), 'W', new ItemStack(GCItems.flagPole, 1, 0), 'V', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 2), 'Z', new ItemStack(GCBlocks.solarPanel, 1, 4), 'C', new ItemStack(GCBlocks.aluminumWire, 1, 1)});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.GRAVITATION_MODULE, 1, 0), new Object[] { "XYX", "WVW", "CZC", 'X', new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4), 'Y', BasicItems.UNKNOW_CRYSTAL.getItemStack(), 'W', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'V', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 0), 'Z', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 2), 'C', new ItemStack(GCItems.basicItem, 1, 14)});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.WIND_GENERATOR, 1, 0), new Object[] { "XYX", "MVM", "CZC", 'X', "ingotAluminum", 'Y', new ItemStack(GCItems.oxygenFan, 1, 0), 'V', new ItemStack(GCItems.flagPole, 1, 0), 'M', new ItemStack(GCBlocks.aluminumWire, 1, 0), 'Z', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 0), 'C', "compressedMeteoricIron"});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.UNIVERSAL_RECYCLER, 1, 0), new Object[] { "XYX", "MZM", "CVC", 'X', "ingotCobalt", 'Y', new ItemStack(GCBlocks.fluidTank, 1, 0), 'V', new ItemStack(GCBlocks.machineTiered, 1, 4), 'C', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'Z', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 0), 'M', BasicItems.WAFER_MODERN.getItemStack()});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.LIQUID_EXTRACTOR, 1, 0), new Object[] { "XZX", "MCM", "VBV", 'X', new ItemStack(MarsItems.marsItemBasic, 1, 6), 'Z', new ItemStack(GCBlocks.fluidTank, 1, 0), 'M', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'C', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 0), 'V', BasicItems.WAFER_MODERN.getItemStack(), 'B', new ItemStack(GCBlocks.oxygenPipe, 1, 0)});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.LIQUID_SEPARATOR, 1, 0), new Object[] { "XZX", "ZCZ", "VBV", 'X', new ItemStack(MarsItems.marsItemBasic, 1, 6), 'Z', new ItemStack(GCBlocks.fluidTank, 1, 0), 'C', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 0), 'V', BasicItems.WAFER_MODERN.getItemStack(), 'B', new ItemStack(GCBlocks.aluminumWire, 1, 1)});

	   //RecipeUtil.addRecipe(new ItemStack(GSBlocks.AdvFuelLoader, 1, 0), new Object[] { "XZX", "ACA", "VBV", 'X', new ItemStack(GSItems.HeavyDutyPlates, 1, 0), 'Z', new ItemStack(GCItems.canister, 1, 0), 'C', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 2), 'B', new ItemStack(GCItems.oilCanister, 1, GCItems.oilCanister.getMaxDamage()), 'V', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'A', new ItemStack(MarsBlocks.hydrogenPipe, 1, 0)});
	  
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.HYDROPONIC_BASE, 1, 0), new Object[] { "ABC", "DED", "DFD", 'A', new ItemStack(GCItems.basicItem, 1, 20), 'B', new ItemStack(GCBlocks.fluidTank, 1, 0), 'C', new ItemStack(GCItems.oxygenConcentrator, 1, 0), 'D', plateSteel, 'E', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 0), 'F', new ItemStack(GCBlocks.aluminumWire, 1, 1)});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.HYDROPONIC_FARM, 1, 0), new Object[] { "XXX", "XCX", "VBV", 'X', new ItemStack(Blocks.GLASS, 1, 0), 'B', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 0), 'C', "dirt", 'V', new ItemStack(GCBlocks.aluminumWire, 1, 1)});

	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.RADIATION_STABILISER, 1, 0), new Object[] { "XYX", "ZCZ", "XBX", 'X', BasicItems.DOLOMITE_CRYSTAL.getItemStack(), 'B', BasicItems.WAFER_MODERN.getItemStack(), 'C', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 0), 'Z', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'Y', blockCopper});
	  
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.PANEL_CONTROLLER, 1, 0), new Object[] { "XXX", "ZCV", "NBN", 'X', plateTitanium, 'B', BasicItems.DOLOMITE_CRYSTAL.getItemStack(), 'C', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 2), 'N', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'Z', BasicItems.WAFER_MODERN.getItemStack(), 'V', new ItemStack(GCBlocks.screen, 1, 0)});

	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.MODIFICATION_TABLE, 1, 0), new Object[] { "XYX", "ZCZ", "VBV", 'V', plateBronze, 'X', new ItemStack(GCItems.basicItem, 1, 14), 'B', BasicItems.WAFER_MODERN.getItemStack(), 'C', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 0), 'Z', new ItemStack(Items.REDSTONE, 1, 0), 'Y', "plateCobalt"});
		
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.GAS_BURNER, 1, 0), new Object[] { "XYX", "ZCZ", "VBV", 'V', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'X', new ItemStack(GCBlocks.fluidTank, 1, 0), 'B', BasicItems.WAFER_MODERN.getItemStack(), 'C', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 0), 'Z', new ItemStack(Items.FLINT_AND_STEEL, 1, 0), 'Y', new ItemStack(GCItems.oxygenVent, 1, 0)});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.ADVANCED_CIRCUIT_FABRICATOR, 1, 0), new Object[] { "XYX", "ZCZ", "XBX", 'X', plateDesh, 'B', BasicItems.WAFER_MODERN.getItemStack(), 'C', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 1), 'Z', BasicItems.DOLOMITE_CRYSTAL.getItemStack(), 'Y', new ItemStack(GCBlocks.machineBase2, 1, 4)});
	  
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.PLANET_SHIELD, 1, 0), new Object[] { "XYX", "VCV", "ZZZ", 'V', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'X', new ItemStack(MarsItems.marsItemBasic, 1, 6), 'C', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 2), 'Z', new ItemStack(GSItems.HDP, 1, 0), 'Y', new ItemStack(GSBlocks.GRAVITATION_MODULE, 1, 0)});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.ENERGY_PAD, 1, 0), new Object[] { "XYX", "VCV", "ZWZ", 'W', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'Z', new ItemStack(MarsItems.marsItemBasic, 1, 5), 'C', BasicItems.WAFER_MODERN.getItemStack(), 'X', new ItemStack(GSBlocks.FUTURE_GLASS_BASIC, 1, 0), 'Y', new ItemStack(GCBlocks.platform, 1, 0), 'V', Items.REDSTONE});
   
	}

   private static void addItemRecipes()
   {

	   RecipeUtil.addRecipe(new ItemStack(GSItems.COBALT_HELMET, 1), new Object[] { "XXX", "X X", 'X', "ingotCobalt"});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.COBALT_CHEST, 1), new Object[] { "X X", "XXX", "XXX", 'X', "ingotCobalt" });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.COBALT_LEGS, 1), new Object[] { "XXX", "X X", "X X",'X', "ingotCobalt"});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.COBALT_BOOTS, 1), new Object[] { "X X", "X X", 'X', "ingotCobalt" });
	   
	   RecipeUtil.addRecipe(new ItemStack(GSItems.COBALT_SWORD, 1), new Object[] { " X ", " X ", " Y ", 'X', "ingotCobalt", 'Y', "stickWood"});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.COBALT_AXE, 1), new Object[] { "XX ", "XY ", " Y ", 'X', "ingotCobalt", 'Y', "stickWood"});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.COBALT_PICKAXE, 1), new Object[] { "XXX", " Y ", " Y ", 'X', "ingotCobalt", 'Y', "stickWood"});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.COBALT_SPADE, 1), new Object[] { " X ", " Y ", " Y ", 'X', "ingotCobalt", 'Y', "stickWood"});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.COBALT_HOE, 1), new Object[] { "XX ", " Y ", " Y ", 'X', "ingotCobalt", 'Y', "stickWood"});
		  
	     //RecipeUtil.addRecipe(BasicItems.DOLOMITE_CRYSTAL.getItemStack(), new Object[] { "XZX", "CYC", "BBB", 'X', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'Y', new ItemStack(MarsItems.marsItemBasic, 1, 6), 'Z', Items.REDSTONE, 'C', new ItemStack(GSItems.INGOTS, 1, 0), 'B', plateSteel });
	   RecipeUtil.addRecipe(BasicItems.ANTIRADIATION_TABLETS.getItemStack(3), new Object[] { "XXX", "CYC", "XXX", 'X', plateIron, 'C', new ItemStack(GCItems.canister, 1, 0), 'Y', Items.MILK_BUCKET});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.JETPACK, 1, GSItems.JETPACK.getMaxDamage()), new Object[] { "ABA", "CDC", "EFE", 'A', GCBlocks.oxygenPipe, 'B', new ItemStack(GCItems.battery, 1, OreDictionary.WILDCARD_VALUE), 'C', plateSteel, 'D', GCItems.steelChestplate, 'E', new ItemStack(GCItems.fuelCanister, 1, 1), 'F', new ItemStack(GCBlocks.aluminumWire, 1, 1) });
		 
	   
	   RecipeUtil.addRecipe(new ItemStack(GSItems.THERMAL_PADDING_3, 1, 0), new Object[] { "XXX", "X X", 'X', BasicItems.THERMAL_CLOTH_T3.getItemStack()});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.THERMAL_PADDING_3, 1, 1), new Object[] { "X X", "XXX", "XXX", 'X', BasicItems.THERMAL_CLOTH_T3.getItemStack() });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.THERMAL_PADDING_3, 1, 2), new Object[] { "XXX", "X X", "X X",'X', BasicItems.THERMAL_CLOTH_T3.getItemStack()});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.THERMAL_PADDING_3, 1, 3), new Object[] { "X X", "X X", 'X', BasicItems.THERMAL_CLOTH_T3.getItemStack() });
	   
	   RecipeUtil.addRecipe(new ItemStack(GSItems.THERMAL_PADDING_4, 1, 0), new Object[] { "XXX", "X X", 'X', BasicItems.THERMAL_CLOTH_T4.getItemStack()});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.THERMAL_PADDING_4, 1, 1), new Object[] { "X X", "XXX", "XXX", 'X', BasicItems.THERMAL_CLOTH_T4.getItemStack() });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.THERMAL_PADDING_4, 1, 2), new Object[] { "XXX", "X X", "X X",'X', BasicItems.THERMAL_CLOTH_T4.getItemStack()});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.THERMAL_PADDING_4, 1, 3), new Object[] { "X X", "X X", 'X', BasicItems.THERMAL_CLOTH_T4.getItemStack() });
	   
	   /* 
	  // int var2 = 100;
	   for (int var2 = 0; var2 < 100; var2 += 100)
       {

			   RecipeUtil.addRecipe(new ItemStack(GSItems.JetPack, 1, 100), new Object[] { "ABA", "CDC", "EFE", 'A', MarsBlocks.hydrogenPipe, 'B', new ItemStack(GCItems.battery, 1, var2), 'C', "compressedMeteoricIron", 'D', GCItems.steelChestplate, 'E', new ItemStack(GCItems.fuelCanister, 1, 1), 'F', new ItemStack(GCBlocks.aluminumWire, 1, 1) });
			   RecipeUtil.addRecipe(new ItemStack(GSItems.PlasmaSword, 1), new Object[] { " X ", " X ", "ZVZ", 'X', "plateCobalt", 'Z', new ItemStack(GCBlocks.aluminumWire, 1, 0), 'V', new ItemStack(GCItems.battery, 1, var2) });
			   RecipeUtil.addRecipe(new ItemStack(GSItems.PlasmaAxe, 1), new Object[] { "XX ", "XY ", "ZVZ", 'X', "plateCobalt", 'Z', new ItemStack(GCBlocks.aluminumWire, 1, 0), 'V', new ItemStack(GCItems.battery, 1, var2), 'Y', GCItems.flagPole });
			   RecipeUtil.addRecipe(new ItemStack(GSItems.PlasmaPickaxe, 1), new Object[] { "XXX", " Y ", "ZVZ", 'X', "plateCobalt", 'Z', new ItemStack(GCBlocks.aluminumWire, 1, 0), 'V', new ItemStack(GCItems.battery, 1, var2), 'Y', GCItems.flagPole });
			   RecipeUtil.addRecipe(new ItemStack(GSItems.PlasmaShovel, 1), new Object[] { " X ", " Y ", "ZVZ", 'X', "plateCobalt", 'Z', new ItemStack(GCBlocks.aluminumWire, 1, 0), 'V', new ItemStack(GCItems.battery, 1, var2), 'Y', GCItems.flagPole });
			   RecipeUtil.addRecipe(new ItemStack(GSItems.PlasmaHoe, 1), new Object[] { "XX ", " Y ", "ZVZ", 'X', "plateCobalt", 'Z', new ItemStack(GCBlocks.aluminumWire, 1, 0), 'V', new ItemStack(GCItems.battery, 1, var2), 'Y', GCItems.flagPole });

       }
	  */

	   RecipeUtil.addRecipe(new ItemStack(GSItems.ADVANCED_BATTERY, 1, GSItems.ADVANCED_BATTERY.getMaxDamage()), new Object[] { " T ", "TRT", "TCT", 'T', "plateNickel", 'R', new ItemStack(GCItems.battery, 1, GCItems.battery.getMaxDamage()), 'C', Items.REDSTONE });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.MODERN_BATTERY, 1, GSItems.MODERN_BATTERY.getMaxDamage()), new Object[] { "RTR", "TYT", "TCT", 'T', plateTitanium, 'R', new ItemStack(GSItems.ADVANCED_BATTERY, 1, GSItems.ADVANCED_BATTERY.getMaxDamage()), 'C', Items.REDSTONE, 'Y', BasicItems.SULFUR.getItemStack() });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.EXTRA_BATTERY, 1, GSItems.EXTRA_BATTERY.getMaxDamage()), new Object[] { "RTR", "TYT", "TCT", 'T', new ItemStack(GSItems.HDP, 1, 0), 'R', new ItemStack(GSItems.MODERN_BATTERY, 1, GSItems.MODERN_BATTERY.getMaxDamage()), 'C', Items.REDSTONE, 'Y', BasicItems.SULFUR.getItemStack() });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.ULTIMATE_BATTERY, 1, GSItems.ULTIMATE_BATTERY.getMaxDamage()), new Object[] { "RTR", "TYT", "TCT", 'T', new ItemStack(GSItems.HDP, 1, 0), 'R', new ItemStack(GSItems.EXTRA_BATTERY, 1, GSItems.EXTRA_BATTERY.getMaxDamage()), 'C', Items.REDSTONE, 'Y', BasicItems.UNKNOW_CRYSTAL.getItemStack() });
	  
	   //RecipeUtil.addRecipe(new ItemStack(GSItems.QuantBow, 1), new Object[] { " XY", "XTY", " XY", 'X', "ingotCobalt", 'Y', Items.string, 'T', Items.blaze_rod });
	   RecipeUtil.addRecipe(BasicItems.PART_SOLARFLARES.getItemStack(), new Object[] { "X  ", "YX ", "VZX", 'Y', plateSteel, 'X', GCItems.flagPole, 'V', new ItemStack(GCItems.basicItem, 1, 0), 'Z', plateIron});
	   RecipeUtil.addRecipe(BasicItems.SOLARFLARES.getItemStack(), new Object[] { "YXY", "XYX", "YXY", 'X', BasicItems.PART_SOLARFLARES.getItemStack(), 'Y', GCItems.flagPole });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.ROCKET_MODULES, 1, 0), new Object[] { "ZXX", "XYV", "BNB", 'Z', new ItemStack(GCItems.basicItem, 1, 19), 'X', new ItemStack(GCItems.basicItem, 1, 9), 'Y', new ItemStack(GCItems.partBuggy, 1, 1), 'V', new ItemStack(GCItems.basicItem, 1, 6), 'B', GCItems.flagPole, 'N', new ItemStack(GCItems.rocketEngine, 1, 0)});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.ROCKET_MODULES, 1, 1), new Object[] { "ZXZ", "YVY", "ZYZ", 'Z', new ItemStack(Items.STRING, 1, 0), 'X', new ItemStack(GCItems.parachute, 1, OreDictionary.WILDCARD_VALUE), 'Y', GCItems.canvas, 'V', new ItemStack(GSItems.ROCKET_MODULES, 1, 0)});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.ROCKET_MODULES, 1, 2), new Object[] { "ZXZ", "YWY", "ZZZ", 'Z', plateSteel, 'X', new ItemStack(Blocks.CHEST, 1, 0), 'Y', new ItemStack(GCItems.flagPole, 1, 0), 'W', new ItemStack(GCItems.partBuggy, 1, 1)});
	   
	   RecipeUtil.addRecipe(new ItemStack(GSItems.OXYGENTANK_TIER_4, 1, GSItems.OXYGENTANK_TIER_4.getMaxDamage()), new Object[] { "XYX", "ZCZ", "ZZZ", 'X', new ItemStack(Blocks.WOOL, 1, 11), 'Y', GCBlocks.oxygenPipe, 'C', new ItemStack(GCItems.oxTankHeavy, 1, GCItems.oxTankHeavy.getMaxDamage()), 'Z', new ItemStack(AsteroidsItems.basicItem , 1, 6) });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.OXYGENTANK_TIER_5, 1, GSItems.OXYGENTANK_TIER_5.getMaxDamage()), new Object[] { "XYX", "ZCZ", "ZZZ", 'X', new ItemStack(GCItems.oxygenConcentrator, 1, 0), 'Y', GCBlocks.oxygenPipe, 'C', new ItemStack(GSItems.OXYGENTANK_TIER_4, 1, GSItems.OXYGENTANK_TIER_4.getMaxDamage()), 'Z', new ItemStack(AsteroidsItems.basicItem , 1, 6) });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.OXYGENTANK_TIER_6, 1, GSItems.OXYGENTANK_TIER_6.getMaxDamage()), new Object[] { "XYA", "ZCZ", "ZZZ", 'X', new ItemStack(GCItems.oxygenVent, 1, 0), 'Y', GCBlocks.oxygenPipe, 'C', new ItemStack(GSItems.OXYGENTANK_TIER_5, 1, GSItems.OXYGENTANK_TIER_5.getMaxDamage()), 'Z', new ItemStack(AsteroidsItems.basicItem , 1, 6), 'A', GCItems.oxygenConcentrator });
		
	   RecipeUtil.addRecipe(BasicItems.MODULE_BASE.getItemStack(), new Object[] { "XYX", "CZC", "XBX", 'X', plateTin, 'Y', Items.GOLD_INGOT, 'Z', new ItemStack(GSItems.COMPRESSED_PLATES, 1, 1), 'C', Items.REDSTONE, 'B', new ItemStack(GCItems.basicItem, 1, 13) });
		
	   RecipeUtil.addRecipe(new ItemStack(GSItems.UPGRADES, 1, 0), new Object[] { " Z ", "XYX", "BCB", 'X', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'Y', BasicItems.MODULE_BASE.getItemStack(), 'Z', BasicItems.DOLOMITE_CRYSTAL.getItemStack(), 'C', BasicItems.VOLCANIC_STONE.getItemStack(), 'B', "ingotCobalt" });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.UPGRADES, 1, 1), new Object[] { " Z ", "XYX", "BCB", 'X', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'Y', BasicItems.MODULE_BASE.getItemStack(), 'Z', BasicItems.DOLOMITE_CRYSTAL.getItemStack(), 'C', BasicItems.UNKNOW_CRYSTAL.getItemStack(), 'B', "ingotMagnesium" });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.UPGRADES, 1, 2), new Object[] { " Z ", "XYX", "BCB", 'X', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'Y', BasicItems.MODULE_BASE.getItemStack(), 'Z', new ItemStack(AsteroidsItems.canisterLN2, 1, 1), 'C', BasicItems.WAFER_MODERN.getItemStack(), 'B', "ingotNickel" });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.UPGRADES, 1, 3), new Object[] { " Z ", "XYX", "BCB", 'X', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'Y', BasicItems.MODULE_BASE.getItemStack(), 'Z', new ItemStack(VenusItems.basicItem, 1, 4), 'C', BasicItems.WAFER_MODERN.getItemStack(), 'B', "ingotCobalt" });
	  
	   RecipeUtil.addRecipe(BasicItems.THERMAL_CLOTH_T3.getItemStack(), new Object[] { "XZX", "CYC", "XZX", 'X', BasicItems.DOLOMITE_MEAL.getItemStack(), 'Y', new ItemStack(AsteroidsItems.canisterLN2, 1, 1), 'Z', BasicItems.VOLCANIC_STONE.getItemStack(), 'C', new ItemStack(VenusItems.basicItem, 1, 3)});
	   RecipeUtil.addRecipe(BasicItems.THERMAL_CLOTH_T4.getItemStack(), new Object[] { "XZX", "CYC", "XZX", 'X', new ItemStack(VenusItems.basicItem, 1, 4), 'Y', BasicItems.ICE_BUCKET.getItemStack(), 'Z', BasicItems.UNKNOW_CRYSTAL.getItemStack(), 'C', BasicItems.THERMAL_CLOTH_T3.getItemStack()});
	  
	   RecipeUtil.addRecipe(BasicItems.WOLF_THERMAL_SUIT.getItemStack(), new Object[] { "X X", "XXX", "X X", 'X', new ItemStack(AsteroidsItems.basicItem, 1, 7)});
	   RecipeUtil.addRecipe(BasicItems.ANIMAL_CAGE.getItemStack(), new Object[] { "XXX", "ZYX", "XXX", 'X', plateSteel, 'Z', new ItemStack(GCBlocks.grating, 1, 0), 'Y', new ItemStack(Blocks.CARPET, 1, OreDictionary.WILDCARD_VALUE)});
		  
	   RecipeUtil.addCustomRecipe(new ShapedRecipeNBT(BasicItems.ADVANCED_EMERGENCY_KIT.getItemStack(), ItemBasicGS.getRecipe()));
	   RecipeUtil.addCustomRecipe(new ShapedRecipeNBT(BasicItems.COLONIST_KIT.getItemStack(), ItemBasicGS.getColonistKitRecipe()));
  
	   RecipeUtil.addRecipe(new ItemStack(GSItems.ROCKET_MODULES, 1, 8), new Object[] {"XY ", "   ", "   ", 'X', BasicItems.BLANK_SCHEMATIC.getItemStack(), 'Y', new ItemStack(AsteroidsItems.tier3Rocket, 1, OreDictionary.WILDCARD_VALUE)});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.ROCKET_MODULES, 1, 9), new Object[] {"XY ", "   ", "   ", 'X', BasicItems.BLANK_SCHEMATIC.getItemStack(), 'Y', new ItemStack(GSItems.ROCKET_TIER_4, 1, OreDictionary.WILDCARD_VALUE)});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.ROCKET_MODULES, 1, 10), new Object[] {"XY ", "   ", "   ", 'X', BasicItems.BLANK_SCHEMATIC.getItemStack(), 'Y', new ItemStack(GSItems.ROCKET_TIER_5, 1, OreDictionary.WILDCARD_VALUE)});
   }

   private static void addBlockSmelting() {
	   
	   GameRegistry.addSmelting(new ItemStack(GSBlocks.OVERWORLD_ORES, 1, 0), new ItemStack(GSItems.INGOTS, 1, 0), 1.0F);
	   GameRegistry.addSmelting(new ItemStack(GSBlocks.OVERWORLD_ORES, 1, 1), new ItemStack(GSItems.INGOTS, 1, 2), 1.0F);
	   GameRegistry.addSmelting(BasicItems.ICE_BUCKET.getItemStack(), new ItemStack(Items.WATER_BUCKET), 0.0F);
	   GameRegistry.addSmelting(BasicItems.HEMATITE.getItemStack(), new ItemStack(Items.IRON_INGOT, 2), 1.0F);
   }
   
   private static void addCompressor() {
  
	   CompressorRecipes.addShapelessRecipe(new ItemStack(GSItems.COMPRESSED_PLATES, 1, 0), Items.COAL, Items.COAL, Items.COAL, Items.COAL, Items.COAL, Items.COAL, Items.COAL, Items.COAL, Items.COAL);
	   CompressorRecipes.addShapelessRecipe(new ItemStack(GCItems.itemBasicMoon, 1, 0), BasicItems.METEORIC_IRON_FRAGMENTS.getItemStack(), BasicItems.METEORIC_IRON_FRAGMENTS.getItemStack(), BasicItems.METEORIC_IRON_FRAGMENTS.getItemStack(), BasicItems.METEORIC_IRON_FRAGMENTS.getItemStack(),BasicItems.METEORIC_IRON_FRAGMENTS.getItemStack(),BasicItems.METEORIC_IRON_FRAGMENTS.getItemStack(),BasicItems.METEORIC_IRON_FRAGMENTS.getItemStack(),BasicItems.METEORIC_IRON_FRAGMENTS.getItemStack(),BasicItems.METEORIC_IRON_FRAGMENTS.getItemStack());
		  
	   CompressorRecipes.addShapelessRecipe(new ItemStack(GSItems.COMPRESSED_PLATES, 1, 0), Blocks.COAL_BLOCK);
	   CompressorRecipes.addShapelessRecipe(new ItemStack(GSItems.COMPRESSED_PLATES, 1, 1), "ingotCobalt", "ingotCobalt");
	   CompressorRecipes.addShapelessRecipe(new ItemStack(GSItems.COMPRESSED_PLATES, 1, 2), "ingotMagnesium", "ingotMagnesium");
	   CompressorRecipes.addShapelessRecipe(new ItemStack(GSItems.COMPRESSED_PLATES, 1, 3), "ingotNickel", "ingotNickel");
	   CompressorRecipes.addShapelessRecipe(new ItemStack(Items.COAL), MarsItems.carbonFragments, MarsItems.carbonFragments, MarsItems.carbonFragments, MarsItems.carbonFragments, MarsItems.carbonFragments, MarsItems.carbonFragments, MarsItems.carbonFragments, MarsItems.carbonFragments, MarsItems.carbonFragments);
   }
   
   private static void addCircuitFabricator() {
	   ItemStack modernWafers = BasicItems.WAFER_MODERN.getItemStack();
	   
	   CircuitFabricatorRecipes.addRecipe(modernWafers, 
			   Arrays.asList( 
					    
							   new ItemStack(Items.DIAMOND), 
							   new ItemStack(GCItems.itemBasicMoon, 1, 2), 
							   new ItemStack(GCItems.itemBasicMoon, 1, 2),
							   new ItemStack(Items.REDSTONE), 
							   new ItemStack(GCItems.basicItem, 1, 14) 
					  ));		
   }

   private static void addAssembly() {
	   
	   if(GSConfigSchematics.enableDuplicateSchematic) {
		   AssemblyRecipes.addShapelessRecipe(BasicItems.BLANK_SCHEMATIC.getItemStack(), "compressedMeteoricIron", new ItemStack(Items.DYE, 1, 0), "compressedMeteoricIron", new ItemStack(Items.DYE, 1, 5), new ItemStack(Items.PAPER, 1, 0), new ItemStack(Items.DYE, 1, 5), "compressedMeteoricIron", new ItemStack(Items.DYE, 1, 0), "compressedMeteoricIron");
		  
		   for(int i = 0; i < 6; i++)
			   AssemblyRecipes.addShapelessRecipe(new ItemStack(GSItems.SCHEMATICS, 2, i), BasicItems.BLANK_SCHEMATIC.getItemStack(), new ItemStack(GSItems.SCHEMATICS, 1, i));

		   AssemblyRecipes.addShapelessRecipe(new ItemStack(GCItems.schematic, 2, 0), BasicItems.BLANK_SCHEMATIC.getItemStack(), new ItemStack(GCItems.schematic, 1, 0));
		   AssemblyRecipes.addShapelessRecipe(new ItemStack(GCItems.schematic, 2, 1), BasicItems.BLANK_SCHEMATIC.getItemStack(), new ItemStack(GCItems.schematic, 1, 1));   
	   }
	   
	   AssemblyRecipes.addShapelessRecipe(new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4), plateBronze, "plateCoal", plateAluminum);
	   AssemblyRecipes.addShapelessRecipe(BasicItems.MODULE_SMALLCANISTER.getItemStack(), plateSteel, plateSteel, plateSteel, plateSteel, Blocks.GLASS_PANE, plateSteel, plateSteel, plateSteel, plateSteel);
	   AssemblyRecipes.addShapelessRecipe(new ItemStack(GSItems.ROCKET_MODULES, 1, 3), new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4), BasicItems.MODULE_SMALLCANISTER.getItemStack(), new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4));
	   
	   AssemblyRecipes.addShapelessRecipe(new ItemStack(GSItems.SPACE_SUIT_HELMET, 1), new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4), plateDesh, new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4), plateDesh, new ItemStack(Blocks.STAINED_GLASS_PANE, 1, 1), plateDesh, new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4), GCItems.oxygenConcentrator, new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4));
	   AssemblyRecipes.addShapelessRecipe(new ItemStack(GSItems.SPACE_SUIT_BODY, 1), plateDesh, "ingotNickel", plateDesh, new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4), new ItemStack(GCItems.steelChestplate, 1, 0), new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4), plateDesh, new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4), plateDesh);
	   AssemblyRecipes.addShapelessRecipe(new ItemStack(GSItems.SPACE_SUIT_LEGGINS, 1), new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4), plateDesh, new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4), plateDesh, "ingotNickel", plateDesh, plateDesh, "ingotNickel", plateDesh);
	   AssemblyRecipes.addShapelessRecipe(new ItemStack(GSItems.SPACE_SUIT_BOOTS, 1), plateDesh, new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4), plateDesh, plateDesh, new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4), plateDesh, "ingotNickel", new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4), "ingotNickel");
	  
	   AssemblyRecipes.addShapelessRecipe(new ItemStack(GSItems.HDP, 1, 0), "plateMagnesium", new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4), new ItemStack(AsteroidsItems.basicItem, 1, 5));
	   AssemblyRecipes.addShapelessRecipe(new ItemStack(GSItems.HDP, 1, 1), "plateCobalt", new ItemStack(GSItems.HDP, 1, 0), "plateNickel", BasicItems.DOLOMITE_MEAL.getItemStack());
	   AssemblyRecipes.addShapelessRecipe(new ItemStack(GSItems.HDP, 1, 2), new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4), new ItemStack(GSItems.HDP, 1, 1), BasicItems.VOLCANIC_STONE.getItemStack());
		  
	   AssemblyRecipes.addShapelessRecipe(new ItemStack(GSBlocks.SINGLE_SOLARPANEL, 1, 0), "blockGlass", "blockGlass", "blockGlass", new ItemStack(GCItems.basicItem, 1, 0), new ItemStack(GCItems.basicItem, 1, 0), new ItemStack(GCItems.basicItem, 1, 0), new ItemStack(MarsItems.marsItemBasic, 1, 5), BasicItems.WAFER_MODERN.getItemStack(), new ItemStack(MarsItems.marsItemBasic, 1, 5));
	   AssemblyRecipes.addShapelessRecipe(new ItemStack(GSBlocks.MODERN_SINGLE_SOLARPANEL, 1, 0), BasicItems.DOLOMITE_MEAL.getItemStack(), new ItemStack(Items.GOLD_INGOT, 1, 0), BasicItems.DOLOMITE_MEAL.getItemStack(), new ItemStack(GSItems.COMPRESSED_PLATES, 1, 1), new ItemStack(GSBlocks.SINGLE_SOLARPANEL, 1, 0), new ItemStack(GSItems.COMPRESSED_PLATES, 1, 1));
		
	   AssemblyRecipes.addShapelessRecipe(new ItemStack(GSBlocks.ADVANCED_LANDING_PAD_SINGLE, 5, 0), plateTitanium, plateTitanium, plateTitanium, new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4), new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4), new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4), new ItemStack(GCBlocks.basicBlock, 1, 12), new ItemStack(GCBlocks.basicBlock, 1, 12), new ItemStack(GCBlocks.basicBlock, 1, 12));
		
   }
   
   private static void addRocketAssembly() {
	   if(GSConfigCore.enableAdvancedRocketCraft)
		   addRocketRecipe(AsteroidsItems.tier3Rocket, GSItems.ROCKET_PARTS, 0, new ItemStack(GSItems.ROCKET_MODULES, 1, 2));
	   addRocketRecipe(GSItems.ROCKET_TIER_4, GSItems.ROCKET_PARTS, 5, new ItemStack(GSItems.ROCKET_MODULES, 1, 8));
	   addRocketRecipe(GSItems.ROCKET_TIER_5, GSItems.ROCKET_PARTS, 10, new ItemStack(GSItems.ROCKET_MODULES, 1, 9));
	   addRocketRecipe(GSItems.ROCKET_TIER_6, GSItems.ROCKET_PARTS, 15, new ItemStack(GSItems.ROCKET_MODULES, 1, 10));	  
   }
   
   private static void addRecycler() {
   
	   RecyclerRecipes.recycling().addNewRecipe(BasicItems.DOLOMITE_CRYSTAL.getItemStack(), BasicItems.DOLOMITE_MEAL.getItemStack(6), null);
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(Blocks.PACKED_ICE, 1), ItemStack.EMPTY, new FluidStack(FluidRegistry.WATER, 500));
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(GCBlocks.blockMoon, 1, 4), new ItemStack(GCItems.itemBasicMoon, 1, 2), 5, null);	 
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(GCBlocks.blockMoon, 1, 5), new ItemStack(GCBlocks.blockMoon, 1, 3), new FluidStack(GSFluids.Helium3, 10));
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(MarsBlocks.marsBlock, 20, 5), BasicItems.HEMATITE.getItemStack(), null);
	   RecyclerRecipes.recycling().addNewRecipe(BasicItems.SULFUR.getItemStack(), new ItemStack(Items.GUNPOWDER, 1, 0), new FluidStack(FluidRegistry.getFluid("sulphuricacid"), 20));
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(GSBlocks.SURFACE_ICE, 1), ItemStack.EMPTY, new FluidStack(FluidRegistry.WATER, 100));
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(GSBlocks.EUROPA_BLOCKS, 1, 2), BasicItems.HEMATITE.getItemStack(), 20, new FluidStack(FluidRegistry.WATER, 100));
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(GSBlocks.MERCURY_BLOCKS, 1, 0), ItemStack.EMPTY, new FluidStack(GSFluids.Helium3, 100));
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(GSBlocks.MERCURY_BLOCKS, 1, 2), BasicItems.SULFUR.getItemStack(), 35, null);
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(VenusBlocks.venusBlock, 20, 0), new ItemStack(VenusBlocks.venusBlock, 1, 8), 50, null);
	   RecyclerRecipes.recycling().addNewRecipe(BasicItems.OXYGEN_ICE_CRYSTAL.getItemStack(), ItemStack.EMPTY, FluidRegistry.getFluidStack("oxygen", 25));
	   RecyclerRecipes.recycling().addNewRecipe(BasicItems.NITROGEN_ICE_CRYSTAL.getItemStack(), ItemStack.EMPTY, FluidRegistry.getFluidStack("nitrogen", 20));
	   RecyclerRecipes.recycling().addNewRecipe(BasicItems.METHANE_ICE_CRYSTAL.getItemStack(), ItemStack.EMPTY, FluidRegistry.getFluidStack("methane", 20));
	   RecyclerRecipes.recycling().addNewRecipe(BasicItems.HYDROGEN_ICE_CRYSTAL.getItemStack(), ItemStack.EMPTY, FluidRegistry.getFluidStack("hydrogen", 20));
	   RecyclerRecipes.recycling().addNewRecipe(BasicItems.DRY_ICE_CRYSTAL.getItemStack(), ItemStack.EMPTY, new FluidStack(FluidRegistry.WATER, 50));
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(GSBlocks.ENCELADUS_BLOCKS, 1, 0), new ItemStack(Items.SNOWBALL, 4, 0), new FluidStack(FluidRegistry.WATER, 200));
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(GSBlocks.ENCELADUS_CRYSTAL, 1, 0), BasicItems.UNKNOW_CRYSTAL.getItemStack(4), null);
		  
   }
      
   
   private static void addOtherRecipes()
   {
	   TileEntityLiquidExtractor.addBlockAndFluid(Blocks.ICE, new FluidStack(FluidRegistry.WATER, 50));
	   TileEntityLiquidExtractor.addBlockAndFluid(Blocks.PACKED_ICE, new FluidStack(FluidRegistry.WATER, 30));
	   TileEntityLiquidExtractor.addBlockAndFluid(AsteroidBlocks.blockDenseIce, new FluidStack(FluidRegistry.WATER, 100));
	   TileEntityLiquidExtractor.addBlockAndFluid(GSBlocks.SURFACE_ICE, new FluidStack(FluidRegistry.WATER, 100));
	   TileEntityLiquidExtractor.addBlockAndFluid(GSBlocks.MIRANDA_BLOCKS.getDefaultState().withProperty(MirandaBlocks.BASIC_TYPE, MirandaBlocks.EnumMirandaBlocks.MIRANDA_ICE).getBlock(), new FluidStack(FluidRegistry.WATER, 100));
	   
	   TileEntityHydroponicBase.addPlant(new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.WHEAT), new ItemStack(Items.WHEAT_SEEDS), 100, Blocks.WHEAT, 7, new boolean[] {false, true});
	   TileEntityHydroponicBase.addPlant(new ItemStack(Items.CARROT), new ItemStack(Items.CARROT), ItemStack.EMPTY, 100, Blocks.CARROTS, 3, new boolean[] {true, false});
	   TileEntityHydroponicBase.addPlant(new ItemStack(Items.POTATO), new ItemStack(Items.POTATO), new ItemStack(Items.POISONOUS_POTATO), 2, Blocks.POTATOES, 3, new boolean[] {true, false});
	   TileEntityHydroponicBase.addPlant(new ItemStack(Items.MELON_SEEDS), new ItemStack(Blocks.MELON_BLOCK), ItemStack.EMPTY, 100, Blocks.MELON_STEM, 1, new boolean[] {false, false});
	   TileEntityHydroponicBase.addPlant(new ItemStack(Items.PUMPKIN_SEEDS), new ItemStack(Blocks.PUMPKIN), ItemStack.EMPTY, 100, Blocks.PUMPKIN_STEM, 3, new boolean[] {false, false});
	   TileEntityHydroponicBase.addPlant(new ItemStack(Items.BEETROOT_SEEDS), new ItemStack(Items.BEETROOT), new ItemStack(Items.BEETROOT_SEEDS), 100, Blocks.BEETROOTS, 3, new boolean[] {false, true});
	   TileEntityHydroponicBase.addPlant(new ItemStack(Blocks.CACTUS), new ItemStack(Blocks.CACTUS, 2, 0), null, 100, Blocks.CACTUS, 0, new boolean[] {false, false});
	   TileEntityHydroponicBase.addPlant(new ItemStack(Blocks.BROWN_MUSHROOM), new ItemStack(Blocks.BROWN_MUSHROOM, 2, 0), null, 100, Blocks.BROWN_MUSHROOM, 0, new boolean[] {false, false});
	   
	   
	   TileEntityFuelGenerator.registerNewFuel(FluidRegistry.LAVA, 5, 0.6F);
	   
	   FluidRegistry.getRegisteredFluids().forEach((name, fluid) -> {
		   if(name.contains("fuel"))
			   TileEntityFuelGenerator.registerNewFuel(fluid, 20, 1.2F); 
		   if(name.contains("oil"))
			   TileEntityFuelGenerator.registerNewFuel(fluid, 10, 0.8F);
	   });	   
		   
	   TileEntityFuelGenerator.registerNewFuel(GSFluids.LiquidEthaneMethane, 20, 1.3F);
	   TileEntityFuelGenerator.registerNewFuel(AsteroidsModule.fluidLiquidMethane, 50, 1.4F);
	   TileEntityFuelGenerator.registerNewFuel(GSFluids.HeliumHydrogen, 50, 2.0F);

	   TileEntityDeconstructor.knownRecipes.addAll(GSRecipeUtil.getBodyRecipes());
	   TileEntityDeconstructor.knownRecipes.addAll(GSRecipeUtil.getBoosterRecipes());
	   TileEntityDeconstructor.knownRecipes.addAll(GSRecipeUtil.getConeRecipes());
	   TileEntityDeconstructor.knownRecipes.addAll(GSRecipeUtil.getEngineRecipes());
	   TileEntityDeconstructor.knownRecipes.addAll(GSRecipeUtil.getFinsRecipes());   

	   
	   for(int i = 1; i < 3; i++) {

		   List<INasaWorkbenchRecipe> knownRecipes = new LinkedList<>();
		   HashMap<Integer, ItemStack> input = new HashMap<Integer, ItemStack>();
		   
		   input.put(0, new ItemStack(GSItems.ROCKET_PARTS, 1, 5 * i));
		   input.put(1, new ItemStack(GSItems.ROCKET_PARTS, 1, 6 * i));
		   input.put(2, new ItemStack(GSItems.ROCKET_PARTS, 1, 7 * i));
		   input.put(3, new ItemStack(GSItems.ROCKET_PARTS, 1, 8 * i));
		   input.put(4, new ItemStack(GSItems.ROCKET_PARTS, 1, 9 * i));
		   
		   Item rocket = GSItems.ROCKET_TIER_4;
		   if(i == 2) rocket = GSItems.ROCKET_TIER_5;
		   if(i == 3) rocket = GSItems.ROCKET_TIER_6;
		   for(int k = 0; k < 3; k++)
			   knownRecipes.add(new NasaWorkbenchRecipe(new ItemStack(rocket, 1, k), input));		   

		   TileEntityDeconstructor.addSalvage(new ItemStack(GSItems.ROCKET_PARTS, 1, 5 * i));
		   TileEntityDeconstructor.addSalvage(new ItemStack(GSItems.ROCKET_PARTS, 1, 6 * i));
		   TileEntityDeconstructor.addSalvage(new ItemStack(GSItems.ROCKET_PARTS, 1, 7 * i));
		   TileEntityDeconstructor.addSalvage(new ItemStack(GSItems.ROCKET_PARTS, 1, 8 * i));
		   TileEntityDeconstructor.addSalvage(new ItemStack(GSItems.ROCKET_PARTS, 1, 9 * i));
		   TileEntityDeconstructor.knownRecipes.addAll(knownRecipes);
	   }
	  
	   
	   TileEntityDeconstructor.addSalvage(new ItemStack(GSItems.HDP, 1, 0));
	   TileEntityDeconstructor.addSalvage(new ItemStack(GSItems.HDP, 1, 1));
	   TileEntityDeconstructor.addSalvage(new ItemStack(GSItems.HDP, 1, 2));

   }
   
   private static void addRocketRecipe(Item rocket, Item parts, int metafirstparts, ItemStack key)
   {
	   RocketAssemblyRecipes.addShapelessRecipe(new ItemStack(rocket, 1, 0), new ItemStack(parts, 1, metafirstparts), new ItemStack(parts, 1, metafirstparts + 1), new ItemStack(parts, 1, metafirstparts + 1), new ItemStack(parts, 1, metafirstparts + 2), new ItemStack(parts, 1, metafirstparts + 3), new ItemStack(parts, 1, metafirstparts + 3), new ItemStack(parts, 1, metafirstparts + 4), new ItemStack(parts, 1, metafirstparts + 4), key);
	   RocketAssemblyRecipes.addShapelessRecipe(new ItemStack(rocket, 1, 1), new ItemStack(parts, 1, metafirstparts), new ItemStack(parts, 1, metafirstparts + 1), new ItemStack(parts, 1, metafirstparts + 1),	new ItemStack(parts, 1, metafirstparts + 2), new ItemStack(parts, 1, metafirstparts + 3), new ItemStack(parts, 1, metafirstparts + 3), new ItemStack(parts, 1, metafirstparts + 4),	new ItemStack(parts, 1, metafirstparts + 4), key, new ItemStack(Blocks.CHEST, 1, 0)); 
	   RocketAssemblyRecipes.addShapelessRecipe(new ItemStack(rocket, 1, 2), new ItemStack(parts, 1, metafirstparts), new ItemStack(parts, 1, metafirstparts + 1), new ItemStack(parts, 1, metafirstparts + 1),	new ItemStack(parts, 1, metafirstparts + 2), new ItemStack(parts, 1, metafirstparts + 3), new ItemStack(parts, 1, metafirstparts + 3), new ItemStack(parts, 1, metafirstparts + 4), new ItemStack(parts, 1, metafirstparts + 4), key, new ItemStack(Blocks.CHEST, 1, 0), new ItemStack(Blocks.CHEST, 1, 0)); 
	   RocketAssemblyRecipes.addShapelessRecipe(new ItemStack(rocket, 1, 3), new ItemStack(parts, 1, metafirstparts), new ItemStack(parts, 1, metafirstparts + 1), new ItemStack(parts, 1, metafirstparts + 1),	new ItemStack(parts, 1, metafirstparts + 2), new ItemStack(parts, 1, metafirstparts + 3), new ItemStack(parts, 1, metafirstparts + 3), new ItemStack(parts, 1, metafirstparts + 4), new ItemStack(parts, 1, metafirstparts + 4), key, new ItemStack(Blocks.CHEST, 1, 0), new ItemStack(Blocks.CHEST, 1, 0), new ItemStack(Blocks.CHEST, 1, 0)); 
   }
}
