package galaxyspace.systems.SolarSystem.planets.overworld.recipes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.configs.GSConfigSchematics;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.registers.fluids.GSFluids;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.util.GSRecipeUtil;
import galaxyspace.systems.SolarSystem.moons.miranda.blocks.MirandaBlocks;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemBasicGS;
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
import micdoodle8.mods.galacticraft.planets.venus.VenusModule;
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
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.DECO_METALS, 4, 3), new Object[] { "   ", " X ", " Y ", 'X', "plateCopper", 'Y', Blocks.STONE});
	   
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.DECO_METALS, 1, 4), new Object[] { "XXX", "XXX", "XXX", 'X', "ingotCobalt"});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.DECO_METALS, 1, 5), new Object[] { "XXX", "XXX", "XXX", 'X', "ingotNickel"});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.DECO_METALS, 1, 6), new Object[] { "XXX", "XXX", "XXX", 'X', "ingotMagnesium"});

	   RecipeUtil.addRecipe(new ItemStack(GSItems.INGOTS, 9, 0), new Object[] { "X", 'X', new ItemStack(GSBlocks.DECO_METALS, 1, 4)});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.INGOTS, 9, 1), new Object[] { "X", 'X', new ItemStack(GSBlocks.DECO_METALS, 1, 6)});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.INGOTS, 9, 2), new Object[] { "X", 'X', new ItemStack(GSBlocks.DECO_METALS, 1, 5)});
		
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.FUTURE_GLASS_BASIC, 8, 0), new Object[] { "XXX", "XWX", "XXX", 'W', new ItemStack(GCItems.flagPole, 1, 0), 'X', Blocks.GLASS});
	   
	   for (int var2 = 0; var2 < 16; ++var2)       
		   RecipeUtil.addRecipe(new ItemStack(GSBlocks.FUTURE_GLASS_COLORED, 8, var2), new Object[] { "XXX", "XWX", "XXX", 'X', new ItemStack(GSBlocks.FUTURE_GLASS_BASIC, 1, 0), 'W', new ItemStack(Items.DYE, 1, var2)});
	   
	   
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.SOLARWIND_PANEL, 1, 0), new Object[] { "XYX", "XZX", "VWV", 'V', new ItemStack(GCBlocks.aluminumWire, 1, 0), 'W', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 0), 'X', plateSteel, 'Y', new ItemStack(GSItems.BASIC, 1, 2), 'Z', GCItems.flagPole });
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.FUEL_GENERATOR, 1, 0), new Object[] { "WYW", "WZW", "XXX", 'W', new ItemStack(GCItems.canister, 1, 1), 'X', plateSteel, 'Y', new ItemStack(Blocks.GLASS_PANE, 1, 0), 'Z', new ItemStack(GCBlocks.machineBase, 1, 0)});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.ASSEMBLER, 1, 0), new Object[] { "VWV", "XYX", "ZWC", 'V', "plateSteel", 'W', new ItemStack(GCBlocks.aluminumWire, 1, 0), 'X', new ItemStack(GCItems.basicItem, 1, 13), 'Y', new ItemStack(Blocks.CRAFTING_TABLE, 1, 0), 'Z', new ItemStack(GCBlocks.machineBase, 1, 12), 'C', new ItemStack(GCBlocks.machineBase2, 1, 4)});
		
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.ROCKET_ASSEMBLER, 1, 0), new Object[] { "VWV", "XYZ", "BNB", 'V', new ItemStack(GCItems.basicItem, 1, 14), 'W', new ItemStack(GCBlocks.nasaWorkbench, 1, 0), 'X', new ItemStack(GCBlocks.machineBase2, 1, 0), 'Y', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 0), 'Z', new ItemStack(GCBlocks.machineBase2, 1, 4), 'B', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'N', new ItemStack(GSBlocks.ASSEMBLER)});
		  	   
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 0), new Object[] { "XYX", "WVW", "XYX", 'X', plateSteel, 'Y', new ItemStack(GCItems.basicItem, 1, 13), 'W', new ItemStack(GCBlocks.aluminumWire, 1, 0), 'V', new ItemStack(Blocks.REDSTONE_BLOCK, 1, 0)});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 1), new Object[] { "XYX", "WVW", "XYX", 'X', plateTitanium, 'Y', new ItemStack(GCItems.basicItem, 1, 14), 'W', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'V', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 0)});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 2), new Object[] { "XYX", "WVW", "XYX", 'X', "plateMagnesium", 'Y', new ItemStack(GSItems.BASIC, 1, 5), 'W', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'V', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 1)});
	   
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.MODERN_STORAGE_MODULE, 1, 0), new Object[] { "XYX", "WVW", "XYX", 'X', new ItemStack(GSItems.INGOTS, 1, 1), 'Y', new ItemStack(GCBlocks.machineTiered, 1, 8), 'W', new ItemStack(GSItems.BASIC, 1, 5), 'V', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 2)});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.OXYGEN_STORAGE_MODULE, 1, 0), new Object[] { "XYX", "WVW", "XZX", 'X', new ItemStack(GCBlocks.machineBase2, 1, 8), 'Y', new ItemStack(GCItems.oxygenConcentrator, 1, 0), 'W', new ItemStack(GCBlocks.oxygenPipe, 1, 0), 'V', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 1), 'Z', new ItemStack(GSItems.BASIC, 1, 5)});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.MODERN_SOLAR_PANEL, 1, 0), new Object[] { "XYX", "WVW", "CZC", 'X', new ItemStack(Items.GOLD_INGOT, 1, 0), 'Y', new ItemStack(GCItems.basicItem, 1, 1), 'W', new ItemStack(GCItems.flagPole, 1, 0), 'V', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 2), 'Z', new ItemStack(GCBlocks.solarPanel, 1, 4), 'C', new ItemStack(GCBlocks.aluminumWire, 1, 1)});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.GRAVITATION_MODULE, 1, 0), new Object[] { "XYX", "WVW", "CZC", 'X', new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4), 'Y', new ItemStack(GSItems.BASIC, 1, 8), 'W', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'V', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 0), 'Z', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 2), 'C', new ItemStack(GCItems.basicItem, 1, 14)});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.WIND_GENERATOR, 1, 0), new Object[] { "XYX", "MVM", "CZC", 'X', new ItemStack(GCItems.basicItem, 1, 5), 'Y', new ItemStack(GCItems.oxygenFan, 1, 0), 'V', new ItemStack(GCItems.flagPole, 1, 0), 'M', new ItemStack(GCBlocks.aluminumWire, 1, 0), 'Z', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 0), 'C', "compressedMeteoricIron"});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.UNIVERSAL_RECYCLER, 1, 0), new Object[] { "XYX", "MZM", "CVC", 'X', "ingotCobalt", 'Y', new ItemStack(GCBlocks.fluidTank, 1, 0), 'V', new ItemStack(GCBlocks.machineTiered, 1, 4), 'C', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'Z', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 0), 'M', new ItemStack(GSItems.BASIC, 1, 5)});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.LIQUID_EXTRACTOR, 1, 0), new Object[] { "XZX", "MCM", "VBV", 'X', new ItemStack(MarsItems.marsItemBasic, 1, 6), 'Z', new ItemStack(GCBlocks.fluidTank, 1, 0), 'M', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'C', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 0), 'V', new ItemStack(GSItems.BASIC, 1, 5), 'B', new ItemStack(GCBlocks.oxygenPipe, 1, 0)});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.LIQUID_SEPARATOR, 1, 0), new Object[] { "XZX", "ZCZ", "VBV", 'X', new ItemStack(MarsItems.marsItemBasic, 1, 6), 'Z', new ItemStack(GCBlocks.fluidTank, 1, 0), 'C', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 0), 'V', new ItemStack(GSItems.BASIC, 1, 5), 'B', new ItemStack(GCBlocks.aluminumWire, 1, 1)});

	   //RecipeUtil.addRecipe(new ItemStack(GSBlocks.AdvFuelLoader, 1, 0), new Object[] { "XZX", "ACA", "VBV", 'X', new ItemStack(GSItems.HeavyDutyPlates, 1, 0), 'Z', new ItemStack(GCItems.canister, 1, 0), 'C', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 2), 'B', new ItemStack(GCItems.oilCanister, 1, GCItems.oilCanister.getMaxDamage()), 'V', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'A', new ItemStack(MarsBlocks.hydrogenPipe, 1, 0)});
	  
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.HYDROPONIC_BASE, 1, 0), new Object[] { "ABC", "DED", "DFD", 'A', new ItemStack(GCItems.basicItem, 1, 20), 'B', new ItemStack(GCBlocks.fluidTank, 1, 0), 'C', new ItemStack(GCItems.oxygenConcentrator, 1, 0), 'D', plateSteel, 'E', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 0), 'F', new ItemStack(GCBlocks.aluminumWire, 1, 1)});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.HYDROPONIC_FARM, 1, 0), new Object[] { "XXX", "XCX", "VBV", 'X', new ItemStack(Blocks.GLASS, 1, 0), 'B', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 0), 'C', "dirt", 'V', new ItemStack(GCBlocks.aluminumWire, 1, 1)});

	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.RADIATION_STABILISER, 1, 0), new Object[] { "XYX", "ZCZ", "XBX", 'X', new ItemStack(GSItems.BASIC, 1, 3), 'B', new ItemStack(GSItems.BASIC, 1, 5), 'C', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 0), 'Z', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'Y', new ItemStack(GCBlocks.basicBlock, 1, 9)});
	  
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.PANEL_CONTROLLER, 1, 0), new Object[] { "XXX", "ZCV", "NBN", 'X', plateTitanium, 'B', new ItemStack(GSItems.BASIC, 1, 3), 'C', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 2), 'N', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'Z', new ItemStack(GSItems.BASIC, 1, 5), 'V', new ItemStack(GCBlocks.screen, 1, 0)});

	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.MODIFICATION_TABLE, 1, 0), new Object[] { "XYX", "ZCZ", "VBV", 'V', plateBronze, 'X', new ItemStack(GCItems.basicItem, 1, 14), 'B', new ItemStack(GSItems.BASIC, 1, 5), 'C', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 0), 'Z', new ItemStack(Items.REDSTONE, 1, 0), 'Y', "plateCobalt"});
		
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.GAS_BURNER, 1, 0), new Object[] { "XYX", "ZCZ", "VBV", 'V', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'X', new ItemStack(GCBlocks.fluidTank, 1, 0), 'B', new ItemStack(GSItems.BASIC, 1, 5), 'C', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 0), 'Z', new ItemStack(Items.FLINT_AND_STEEL, 1, 0), 'Y', new ItemStack(GCItems.oxygenVent, 1, 0)});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.ADVANCED_CIRCUIT_FABRICATOR, 1, 0), new Object[] { "XYX", "ZCZ", "XBX", 'X', plateDesh, 'B', new ItemStack(GSItems.BASIC, 1, 5), 'C', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 1), 'Z', new ItemStack(GSItems.BASIC, 1, 3), 'Y', new ItemStack(GCBlocks.machineBase2, 1, 4)});
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.ADVANCED_ELECTRIC_COMPRESSOR, 1, 0), new Object[] { "XYX", "ZCZ", "XBX", 'X', plateDesh, 'B', new ItemStack(GSItems.BASIC, 1, 5), 'C', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 1), 'Z', new ItemStack(MarsItems.marsItemBasic, 1, 1), 'Y', new ItemStack(GCBlocks.machineBase2, 1, 0)});
	   
	   RecipeUtil.addRecipe(new ItemStack(GSBlocks.PLANET_SHIELD, 1, 0), new Object[] { "XYX", "VCV", "ZZZ", 'V', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'X', new ItemStack(MarsItems.marsItemBasic, 1, 6), 'C', new ItemStack(GSBlocks.MACHINE_FRAMES, 1, 2), 'Z', new ItemStack(GSItems.HDP, 1, 0), 'Y', new ItemStack(GSBlocks.GRAVITATION_MODULE, 1, 0)});
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
		  
	     //RecipeUtil.addRecipe(new ItemStack(GSItems.BASIC, 1, 3), new Object[] { "XZX", "CYC", "BBB", 'X', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'Y', new ItemStack(MarsItems.marsItemBasic, 1, 6), 'Z', Items.REDSTONE, 'C', new ItemStack(GSItems.INGOTS, 1, 0), 'B', plateSteel });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.BASIC, 3, 9), new Object[] { "XXX", "CYC", "XXX", 'X', plateIron, 'C', new ItemStack(GCItems.canister, 1, 0), 'Y', Items.MILK_BUCKET});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.JETPACK, 1, GSItems.JETPACK.getMaxDamage()), new Object[] { "ABA", "CDC", "EFE", 'A', GCBlocks.oxygenPipe, 'B', new ItemStack(GCItems.battery, 1, OreDictionary.WILDCARD_VALUE), 'C', plateSteel, 'D', GCItems.steelChestplate, 'E', new ItemStack(GCItems.fuelCanister, 1, 1), 'F', new ItemStack(GCBlocks.aluminumWire, 1, 1) });
		 
	   
	   RecipeUtil.addRecipe(new ItemStack(GSItems.THERMAL_PADDING_3, 1, 0), new Object[] { "XXX", "X X", 'X', new ItemStack(GSItems.BASIC, 1, 21)});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.THERMAL_PADDING_3, 1, 1), new Object[] { "X X", "XXX", "XXX", 'X', new ItemStack(GSItems.BASIC, 1, 21) });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.THERMAL_PADDING_3, 1, 2), new Object[] { "XXX", "X X", "X X",'X', new ItemStack(GSItems.BASIC, 1, 21)});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.THERMAL_PADDING_3, 1, 3), new Object[] { "X X", "X X", 'X', new ItemStack(GSItems.BASIC, 1, 21) });
	   
	   RecipeUtil.addRecipe(new ItemStack(GSItems.THERMAL_PADDING_4, 1, 0), new Object[] { "XXX", "X X", 'X', new ItemStack(GSItems.BASIC, 1, 22)});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.THERMAL_PADDING_4, 1, 1), new Object[] { "X X", "XXX", "XXX", 'X', new ItemStack(GSItems.BASIC, 1, 22) });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.THERMAL_PADDING_4, 1, 2), new Object[] { "XXX", "X X", "X X",'X', new ItemStack(GSItems.BASIC, 1, 22)});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.THERMAL_PADDING_4, 1, 3), new Object[] { "X X", "X X", 'X', new ItemStack(GSItems.BASIC, 1, 22) });
	   
	   /* 
	  // int var2 = 100;
	   for (int var2 = 0; var2 < 100; var2 += 100)
       {
r
			   RecipeUtil.addRecipe(new ItemStack(GSItems.JetPack, 1, 100), new Object[] { "ABA", "CDC", "EFE", 'A', MarsBlocks.hydrogenPipe, 'B', new ItemStack(GCItems.battery, 1, var2), 'C', "compressedMeteoricIron", 'D', GCItems.steelChestplate, 'E', new ItemStack(GCItems.fuelCanister, 1, 1), 'F', new ItemStack(GCBlocks.aluminumWire, 1, 1) });
			   RecipeUtil.addRecipe(new ItemStack(GSItems.PlasmaSword, 1), new Object[] { " X ", " X ", "ZVZ", 'X', "plateCobalt", 'Z', new ItemStack(GCBlocks.aluminumWire, 1, 0), 'V', new ItemStack(GCItems.battery, 1, var2) });
			   RecipeUtil.addRecipe(new ItemStack(GSItems.PlasmaAxe, 1), new Object[] { "XX ", "XY ", "ZVZ", 'X', "plateCobalt", 'Z', new ItemStack(GCBlocks.aluminumWire, 1, 0), 'V', new ItemStack(GCItems.battery, 1, var2), 'Y', GCItems.flagPole });
			   RecipeUtil.addRecipe(new ItemStack(GSItems.PlasmaPickaxe, 1), new Object[] { "XXX", " Y ", "ZVZ", 'X', "plateCobalt", 'Z', new ItemStack(GCBlocks.aluminumWire, 1, 0), 'V', new ItemStack(GCItems.battery, 1, var2), 'Y', GCItems.flagPole });
			   RecipeUtil.addRecipe(new ItemStack(GSItems.PlasmaShovel, 1), new Object[] { " X ", " Y ", "ZVZ", 'X', "plateCobalt", 'Z', new ItemStack(GCBlocks.aluminumWire, 1, 0), 'V', new ItemStack(GCItems.battery, 1, var2), 'Y', GCItems.flagPole });
			   RecipeUtil.addRecipe(new ItemStack(GSItems.PlasmaHoe, 1), new Object[] { "XX ", " Y ", "ZVZ", 'X', "plateCobalt", 'Z', new ItemStack(GCBlocks.aluminumWire, 1, 0), 'V', new ItemStack(GCItems.battery, 1, var2), 'Y', GCItems.flagPole });

       }
	  */

	   RecipeUtil.addRecipe(new ItemStack(GSItems.ADVANCED_BATTERY, 1, GSItems.ADVANCED_BATTERY.getMaxDamage()), new Object[] { " T ", "TRT", "TCT", 'T', "plateNickel", 'R', new ItemStack(GCItems.battery, 1, GCItems.battery.getMaxDamage()), 'C', Items.REDSTONE });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.MODERN_BATTERY, 1, GSItems.MODERN_BATTERY.getMaxDamage()), new Object[] { "RTR", "TYT", "TCT", 'T', plateTitanium, 'R', new ItemStack(GSItems.ADVANCED_BATTERY, 1, GSItems.ADVANCED_BATTERY.getMaxDamage()), 'C', Items.REDSTONE, 'Y', new ItemStack(GSItems.BASIC, 1, 7) });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.EXTRA_BATTERY, 1, GSItems.EXTRA_BATTERY.getMaxDamage()), new Object[] { "RTR", "TYT", "TCT", 'T', new ItemStack(GSItems.HDP, 1, 0), 'R', new ItemStack(GSItems.MODERN_BATTERY, 1, GSItems.MODERN_BATTERY.getMaxDamage()), 'C', Items.REDSTONE, 'Y', new ItemStack(GSItems.BASIC, 1, 7) });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.ULTIMATE_BATTERY, 1, GSItems.ULTIMATE_BATTERY.getMaxDamage()), new Object[] { "RTR", "TYT", "TCT", 'T', new ItemStack(GSItems.HDP, 1, 0), 'R', new ItemStack(GSItems.EXTRA_BATTERY, 1, GSItems.EXTRA_BATTERY.getMaxDamage()), 'C', Items.REDSTONE, 'Y', new ItemStack(GSItems.BASIC, 1, 8) });
	  
	   //RecipeUtil.addRecipe(new ItemStack(GSItems.QuantBow, 1), new Object[] { " XY", "XTY", " XY", 'X', "ingotCobalt", 'Y', Items.string, 'T', Items.blaze_rod });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.BASIC, 1, 1), new Object[] { "X  ", "YX ", "VZX", 'Y', plateSteel, 'X', GCItems.flagPole, 'V', new ItemStack(GCItems.basicItem, 1, 0), 'Z', plateIron});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.BASIC, 1, 2), new Object[] { "YXY", "XYX", "YXY", 'X', new ItemStack(GSItems.BASIC, 1, 1), 'Y', GCItems.flagPole });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.ROCKET_MODULES, 1, 0), new Object[] { "ZXX", "XYV", "BNB", 'Z', new ItemStack(GCItems.basicItem, 1, 19), 'X', new ItemStack(GCItems.basicItem, 1, 9), 'Y', new ItemStack(GCItems.partBuggy, 1, 1), 'V', new ItemStack(GCItems.basicItem, 1, 6), 'B', GCItems.flagPole, 'N', new ItemStack(GCItems.rocketEngine, 1, 0)});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.ROCKET_MODULES, 1, 1), new Object[] { "ZXZ", "YVY", "ZYZ", 'Z', new ItemStack(Items.STRING, 1, 0), 'X', new ItemStack(GCItems.parachute, 1, OreDictionary.WILDCARD_VALUE), 'Y', GCItems.canvas, 'V', new ItemStack(GSItems.ROCKET_MODULES, 1, 0)});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.ROCKET_MODULES, 1, 2), new Object[] { "ZXZ", "YWY", "ZZZ", 'Z', plateSteel, 'X', new ItemStack(Blocks.CHEST, 1, 0), 'Y', new ItemStack(GCItems.flagPole, 1, 0), 'W', new ItemStack(GCItems.partBuggy, 1, 1)});
	   
	   RecipeUtil.addRecipe(new ItemStack(GSItems.OXYGENTANK_TIER_4, 1, GSItems.OXYGENTANK_TIER_4.getMaxDamage()), new Object[] { "XYX", "ZCZ", "ZZZ", 'X', new ItemStack(Blocks.WOOL, 1, 11), 'Y', GCBlocks.oxygenPipe, 'C', new ItemStack(GCItems.oxTankHeavy, 1, GCItems.oxTankHeavy.getMaxDamage()), 'Z', new ItemStack(AsteroidsItems.basicItem , 1, 6) });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.OXYGENTANK_TIER_5, 1, GSItems.OXYGENTANK_TIER_5.getMaxDamage()), new Object[] { "XYX", "ZCZ", "ZZZ", 'X', new ItemStack(GCItems.oxygenConcentrator, 1, 0), 'Y', GCBlocks.oxygenPipe, 'C', new ItemStack(GSItems.OXYGENTANK_TIER_4, 1, GSItems.OXYGENTANK_TIER_4.getMaxDamage()), 'Z', new ItemStack(AsteroidsItems.basicItem , 1, 6) });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.OXYGENTANK_TIER_6, 1, GSItems.OXYGENTANK_TIER_6.getMaxDamage()), new Object[] { "XYA", "ZCZ", "ZZZ", 'X', new ItemStack(GCItems.oxygenVent, 1, 0), 'Y', GCBlocks.oxygenPipe, 'C', new ItemStack(GSItems.OXYGENTANK_TIER_5, 1, GSItems.OXYGENTANK_TIER_5.getMaxDamage()), 'Z', new ItemStack(AsteroidsItems.basicItem , 1, 6), 'A', GCItems.oxygenConcentrator });
		
	   RecipeUtil.addRecipe(new ItemStack(GSItems.BASIC, 1, 11), new Object[] { "XYX", "CZC", "XBX", 'X', plateTin, 'Y', Items.GOLD_INGOT, 'Z', new ItemStack(GSItems.COMPRESSED_PLATES, 1, 1), 'C', Items.REDSTONE, 'B', new ItemStack(GCItems.basicItem, 1, 13) });
		
	   RecipeUtil.addRecipe(new ItemStack(GSItems.UPGRADES, 1, 0), new Object[] { " Z ", "XYX", "BCB", 'X', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'Y', new ItemStack(GSItems.BASIC, 1, 11), 'Z', new ItemStack(GSItems.BASIC, 1, 3), 'C', new ItemStack(GSItems.BASIC, 1, 12), 'B', new ItemStack(GSItems.INGOTS, 1, 0) });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.UPGRADES, 1, 1), new Object[] { " Z ", "XYX", "BCB", 'X', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'Y', new ItemStack(GSItems.BASIC, 1, 11), 'Z', new ItemStack(GSItems.BASIC, 1, 3), 'C', new ItemStack(GSItems.BASIC, 1, 8), 'B', new ItemStack(GSItems.INGOTS, 1, 1) });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.UPGRADES, 1, 2), new Object[] { " Z ", "XYX", "BCB", 'X', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'Y', new ItemStack(GSItems.BASIC, 1, 11), 'Z', new ItemStack(AsteroidsItems.canisterLN2, 1, 1), 'C', new ItemStack(GSItems.BASIC, 1, 5), 'B', new ItemStack(GSItems.INGOTS, 1, 2) });
	   RecipeUtil.addRecipe(new ItemStack(GSItems.UPGRADES, 1, 3), new Object[] { " Z ", "XYX", "BCB", 'X', new ItemStack(GCBlocks.aluminumWire, 1, 1), 'Y', new ItemStack(GSItems.BASIC, 1, 11), 'Z', new ItemStack(VenusItems.basicItem, 1, 4), 'C', new ItemStack(GSItems.BASIC, 1, 5), 'B', new ItemStack(GSItems.INGOTS, 1, 0) });
	  
	   RecipeUtil.addRecipe(new ItemStack(GSItems.BASIC, 1, 21), new Object[] { "XZX", "CYC", "XZX", 'X', new ItemStack(GSItems.BASIC, 1, 4), 'Y', new ItemStack(AsteroidsItems.canisterLN2, 1, 1), 'Z', new ItemStack(GSItems.BASIC, 1, 12), 'C', new ItemStack(VenusItems.basicItem, 1, 3)});
	   RecipeUtil.addRecipe(new ItemStack(GSItems.BASIC, 1, 22), new Object[] { "XZX", "CYC", "XZX", 'X', new ItemStack(VenusItems.basicItem, 1, 4), 'Y', new ItemStack(GSItems.BASIC, 1, 17), 'Z', new ItemStack(GSItems.BASIC, 1, 8), 'C', new ItemStack(GSItems.BASIC, 1, 21)});
		  
	   RecipeUtil.addCustomRecipe(new ShapedRecipeNBT(new ItemStack(GSItems.BASIC, 1, 20), ItemBasicGS.getRecipe()));
   }

   private static void addBlockSmelting() {
	   
	   GameRegistry.addSmelting(new ItemStack(GSBlocks.OVERWORLD_ORES, 1, 0), new ItemStack(GSItems.INGOTS, 1, 0), 1.0F);
	   GameRegistry.addSmelting(new ItemStack(GSBlocks.OVERWORLD_ORES, 1, 1), new ItemStack(GSItems.INGOTS, 1, 2), 1.0F);
	   GameRegistry.addSmelting(new ItemStack(GSItems.BASIC, 1, 17), new ItemStack(Items.WATER_BUCKET), 0.0F);
	   GameRegistry.addSmelting(new ItemStack(GSItems.BASIC, 1, 6), new ItemStack(Items.IRON_INGOT, 2), 1.0F);
	   //GameRegistry.addSmelting(new ItemStack(GSItems.BASIC, 1, 1), new ItemStack(Items.IRON_INGOT, 3), 1.0F);
   }
   
   private static void addCompressor() {
  
	   CompressorRecipes.addShapelessRecipe(new ItemStack(GSItems.COMPRESSED_PLATES, 1, 0), Items.COAL, Items.COAL, Items.COAL, Items.COAL, Items.COAL, Items.COAL, Items.COAL, Items.COAL, Items.COAL);
	   CompressorRecipes.addShapelessRecipe(new ItemStack(GCItems.itemBasicMoon, 1, 0), new ItemStack(GSItems.BASIC, 1, 13), new ItemStack(GSItems.BASIC, 1, 13), new ItemStack(GSItems.BASIC, 1, 13), new ItemStack(GSItems.BASIC, 1, 13),new ItemStack(GSItems.BASIC, 1, 13),new ItemStack(GSItems.BASIC, 1, 13),new ItemStack(GSItems.BASIC, 1, 13),new ItemStack(GSItems.BASIC, 1, 13),new ItemStack(GSItems.BASIC, 1, 13));
		  
	   CompressorRecipes.addShapelessRecipe(new ItemStack(GSItems.COMPRESSED_PLATES, 1, 0), Blocks.COAL_BLOCK);
	   CompressorRecipes.addShapelessRecipe(new ItemStack(GSItems.COMPRESSED_PLATES, 1, 1), "ingotCobalt", "ingotCobalt");
	   CompressorRecipes.addShapelessRecipe(new ItemStack(GSItems.COMPRESSED_PLATES, 1, 2), "ingotMagnesium", "ingotMagnesium");
	   CompressorRecipes.addShapelessRecipe(new ItemStack(GSItems.COMPRESSED_PLATES, 1, 3), "ingotNickel", "ingotNickel");
	   CompressorRecipes.addShapelessRecipe(new ItemStack(Items.COAL), MarsItems.carbonFragments, MarsItems.carbonFragments, MarsItems.carbonFragments, MarsItems.carbonFragments, MarsItems.carbonFragments, MarsItems.carbonFragments, MarsItems.carbonFragments, MarsItems.carbonFragments, MarsItems.carbonFragments);
   }
   
   private static void addCircuitFabricator() {
	   ItemStack modernWafers = new ItemStack(GSItems.BASIC, 1, 5);
	   
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
		   AssemblyRecipes.addShapelessRecipe(new ItemStack(GSItems.BASIC, 1, 14), "compressedMeteoricIron", new ItemStack(Items.DYE, 1, 0), "compressedMeteoricIron", new ItemStack(Items.DYE, 1, 5), new ItemStack(Items.PAPER, 1, 0), new ItemStack(Items.DYE, 1, 5), "compressedMeteoricIron", new ItemStack(Items.DYE, 1, 0), "compressedMeteoricIron");
		  
		   for(int i = 0; i < 6; i++)
			   AssemblyRecipes.addShapelessRecipe(new ItemStack(GSItems.SCHEMATICS, 2, i), new ItemStack(GSItems.BASIC, 1, 14), new ItemStack(GSItems.SCHEMATICS, 1, i));
	   }
	   
	   AssemblyRecipes.addShapelessRecipe(new ItemStack(GCItems.schematic, 2, 0), new ItemStack(GSItems.BASIC, 1, 14), new ItemStack(GCItems.schematic, 1, 0));
	   AssemblyRecipes.addShapelessRecipe(new ItemStack(GCItems.schematic, 2, 1), new ItemStack(GSItems.BASIC, 1, 14), new ItemStack(GCItems.schematic, 1, 1));
	  
	   AssemblyRecipes.addShapelessRecipe(new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4), plateBronze, "plateCoal", plateAluminum);
	   AssemblyRecipes.addShapelessRecipe(new ItemStack(GSItems.BASIC, 1, 0), plateSteel, plateSteel, plateSteel, plateSteel, Blocks.GLASS_PANE, plateSteel, plateSteel, plateSteel, plateSteel);
	   AssemblyRecipes.addShapelessRecipe(new ItemStack(GSItems.ROCKET_MODULES, 1, 3), new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4), new ItemStack(GSItems.BASIC, 1, 0), new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4));
	   
	   AssemblyRecipes.addShapelessRecipe(new ItemStack(GSItems.SPACE_SUIT_HELMET, 1), new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4), plateSteel, new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4), plateSteel, new ItemStack(Blocks.STAINED_GLASS_PANE, 1, 1), plateSteel, new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4), GCItems.oxygenConcentrator, new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4));
	   AssemblyRecipes.addShapelessRecipe(new ItemStack(GSItems.SPACE_SUIT_BODY, 1), plateSteel, "ingotNickel", plateSteel, new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4), new ItemStack(GCItems.steelChestplate, 1, 0), new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4), plateSteel, new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4), plateSteel);
	   AssemblyRecipes.addShapelessRecipe(new ItemStack(GSItems.SPACE_SUIT_LEGGINS, 1), new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4), plateSteel, new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4), plateSteel, "ingotNickel", plateSteel, plateSteel, "ingotNickel", plateSteel);
	   AssemblyRecipes.addShapelessRecipe(new ItemStack(GSItems.SPACE_SUIT_BOOTS, 1), plateSteel, new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4), plateSteel, plateSteel, new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4), plateSteel, "ingotNickel", new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4), "ingotNickel");
	  
	   AssemblyRecipes.addShapelessRecipe(new ItemStack(GSItems.HDP, 1, 0), "plateMagnesium", new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4), new ItemStack(AsteroidsItems.basicItem, 1, 5));
	   AssemblyRecipes.addShapelessRecipe(new ItemStack(GSItems.HDP, 1, 1), "plateCobalt", new ItemStack(GSItems.HDP, 1, 0), "plateNickel", new ItemStack(GSItems.BASIC, 1, 4));
	   AssemblyRecipes.addShapelessRecipe(new ItemStack(GSItems.HDP, 1, 2), new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4), new ItemStack(GSItems.HDP, 1, 1), new ItemStack(GSItems.BASIC, 1, 12));
		  
	   AssemblyRecipes.addShapelessRecipe(new ItemStack(GSBlocks.SINGLE_SOLARPANEL, 1, 0), "blockGlass", "blockGlass", "blockGlass", new ItemStack(GCItems.basicItem, 1, 0), new ItemStack(GCItems.basicItem, 1, 0), new ItemStack(GCItems.basicItem, 1, 0), new ItemStack(MarsItems.marsItemBasic, 1, 5), new ItemStack(GSItems.BASIC, 1, 5), new ItemStack(MarsItems.marsItemBasic, 1, 5));
	   AssemblyRecipes.addShapelessRecipe(new ItemStack(GSBlocks.MODERN_SINGLE_SOLARPANEL, 1, 0), new ItemStack(GSItems.BASIC, 1, 4), new ItemStack(Items.GOLD_INGOT, 1, 0), new ItemStack(GSItems.BASIC, 1, 4), new ItemStack(GSItems.COMPRESSED_PLATES, 1, 1), new ItemStack(GSBlocks.SINGLE_SOLARPANEL, 1, 0), new ItemStack(GSItems.COMPRESSED_PLATES, 1, 1));
		
	   AssemblyRecipes.addShapelessRecipe(new ItemStack(GSBlocks.ADVANCED_LANDING_PAD_SINGLE, 5, 0), plateTitanium, plateTitanium, plateTitanium, new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4), new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4), new ItemStack(GSItems.COMPRESSED_PLATES, 1, 4), new ItemStack(GCBlocks.basicBlock, 1, 12), new ItemStack(GCBlocks.basicBlock, 1, 12), new ItemStack(GCBlocks.basicBlock, 1, 12));
		
   }
   
   private static void addRocketAssembly() {
	   if(GSConfigCore.enableAdvancedRocketCraft)
		   addRocketRecipe(AsteroidsItems.tier3Rocket, GSItems.ROCKET_PARTS, 0);
	   addRocketRecipe(GSItems.ROCKET_TIER_4, GSItems.ROCKET_PARTS, 5);
	   addRocketRecipe(GSItems.ROCKET_TIER_5, GSItems.ROCKET_PARTS, 10);
	   addRocketRecipe(GSItems.ROCKET_TIER_6, GSItems.ROCKET_PARTS, 15);	  
   }
   
   private static void addRecycler() {
   
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(GSItems.BASIC, 1, 3), new ItemStack(GSItems.BASIC, 6, 4), null);
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(Blocks.PACKED_ICE, 1), ItemStack.EMPTY, new FluidStack(FluidRegistry.WATER, 500));
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(GCBlocks.blockMoon, 1, 4), new ItemStack(GCItems.itemBasicMoon, 1, 2), 5, null);	 
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(GCBlocks.blockMoon, 1, 5), new ItemStack(GCBlocks.blockMoon, 1, 3), new FluidStack(GSFluids.Helium3, 10));
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(MarsBlocks.marsBlock, 20, 5), new ItemStack(GSItems.BASIC, 1, 6), null);
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(GSItems.BASIC, 1, 7), new ItemStack(Items.GUNPOWDER, 1, 0), new FluidStack(VenusModule.sulphuricAcid, 20));
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(GSBlocks.SURFACE_ICE, 1), ItemStack.EMPTY, new FluidStack(FluidRegistry.WATER, 100));
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(GSBlocks.EUROPA_BLOCKS, 1, 2), new ItemStack(GSItems.BASIC, 1, 6), 50, new FluidStack(FluidRegistry.WATER, 100));
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(GSBlocks.MERCURY_BLOCKS, 1, 0), ItemStack.EMPTY, new FluidStack(GSFluids.Helium3, 100));
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(GSBlocks.MERCURY_BLOCKS, 1, 2), new ItemStack(GSItems.BASIC, 1, 7), 35, null);
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(VenusBlocks.venusBlock, 20, 0), new ItemStack(VenusBlocks.venusBlock, 1, 8), 50, null);
	   //RecyclerRecipes.recycling().addNewRecipe(FluidUtil.getFilledBucket(FluidRegistry.getFluidStack("oil", 1)), new ItemStack(GSItems.BASIC, 1, 20), 50, FluidRegistry.getFluidStack("fuel", 1000));
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(GSItems.BASIC, 1, 23), ItemStack.EMPTY, FluidRegistry.getFluidStack("oxygen", 25));
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(GSItems.BASIC, 1, 24), ItemStack.EMPTY, FluidRegistry.getFluidStack("nitrogen", 20));
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(GSItems.BASIC, 1, 25), ItemStack.EMPTY, FluidRegistry.getFluidStack("methane", 20));
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(GSItems.BASIC, 1, 26), ItemStack.EMPTY, FluidRegistry.getFluidStack("hydrogen", 20));
	   RecyclerRecipes.recycling().addNewRecipe(new ItemStack(GSItems.BASIC, 1, 27), ItemStack.EMPTY, new FluidStack(FluidRegistry.WATER, 50));
   }
      
   
   private static void addOtherRecipes()
   {
	   TileEntityLiquidExtractor.addBlockAndFluid(Blocks.PACKED_ICE, new FluidStack(FluidRegistry.WATER, 50));
	   TileEntityLiquidExtractor.addBlockAndFluid(AsteroidBlocks.blockDenseIce, new FluidStack(FluidRegistry.WATER, 100));
	   TileEntityLiquidExtractor.addBlockAndFluid(GSBlocks.SURFACE_ICE, new FluidStack(FluidRegistry.WATER, 100));
	   TileEntityLiquidExtractor.addBlockAndFluid(GSBlocks.MIRANDA_BLOCKS.getDefaultState().withProperty(MirandaBlocks.BASIC_TYPE, MirandaBlocks.EnumMirandaBlocks.MIRANDA_ICE).getBlock(), new FluidStack(FluidRegistry.WATER, 100));
	   
	   TileEntityHydroponicBase.addPlant(new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.WHEAT), new ItemStack(Items.WHEAT_SEEDS), 100, Blocks.WHEAT, 7, new boolean[] {false, true});
	   TileEntityHydroponicBase.addPlant(new ItemStack(Items.CARROT), new ItemStack(Items.CARROT), ItemStack.EMPTY, 100, Blocks.CARROTS, 3, new boolean[] {true, false});
	   TileEntityHydroponicBase.addPlant(new ItemStack(Items.POTATO), new ItemStack(Items.POTATO), new ItemStack(Items.POISONOUS_POTATO), 2, Blocks.POTATOES, 3, new boolean[] {true, false});
	   TileEntityHydroponicBase.addPlant(new ItemStack(Items.MELON_SEEDS), new ItemStack(Blocks.MELON_BLOCK), ItemStack.EMPTY, 100, Blocks.MELON_STEM, 1, new boolean[] {false, false});
	   TileEntityHydroponicBase.addPlant(new ItemStack(Items.PUMPKIN_SEEDS), new ItemStack(Blocks.PUMPKIN), ItemStack.EMPTY, 100, Blocks.PUMPKIN_STEM, 3, new boolean[] {false, false});
	   TileEntityHydroponicBase.addPlant(new ItemStack(Items.BEETROOT_SEEDS), new ItemStack(Items.BEETROOT), new ItemStack(Items.BEETROOT_SEEDS), 100, Blocks.BEETROOTS, 3, new boolean[] {false, true});
	   
	   TileEntityFuelGenerator.registerNewFuel(FluidRegistry.LAVA, 5, 0.6F);
	   
	   FluidRegistry.getRegisteredFluids().forEach((name, fluid) -> {
		   if(name.contains("fuel"))
			   TileEntityFuelGenerator.registerNewFuel(fluid, 20, 1.5F); 
		   if(name.contains("oil"))
			   TileEntityFuelGenerator.registerNewFuel(fluid, 10, 1.0F);
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
   
   private static void addRocketRecipe(Item rocket, Item parts, int metafirstparts)
   {
	   RocketAssemblyRecipes.addShapelessRecipe(new ItemStack(rocket, 1, 0), new ItemStack(parts, 1, metafirstparts), new ItemStack(parts, 1, metafirstparts + 1), new ItemStack(parts, 1, metafirstparts + 1), new ItemStack(parts, 1, metafirstparts + 2), new ItemStack(parts, 1, metafirstparts + 3), new ItemStack(parts, 1, metafirstparts + 3), new ItemStack(parts, 1, metafirstparts + 4), new ItemStack(parts, 1, metafirstparts + 4));
	   RocketAssemblyRecipes.addShapelessRecipe(new ItemStack(rocket, 1, 1), new ItemStack(parts, 1, metafirstparts), new ItemStack(parts, 1, metafirstparts + 1), new ItemStack(parts, 1, metafirstparts + 1),	new ItemStack(parts, 1, metafirstparts + 2), new ItemStack(parts, 1, metafirstparts + 3), new ItemStack(parts, 1, metafirstparts + 3), new ItemStack(parts, 1, metafirstparts + 4),	new ItemStack(parts, 1, metafirstparts + 4), new ItemStack(Blocks.CHEST, 1, 0)); 
	   RocketAssemblyRecipes.addShapelessRecipe(new ItemStack(rocket, 1, 2), new ItemStack(parts, 1, metafirstparts), new ItemStack(parts, 1, metafirstparts + 1), new ItemStack(parts, 1, metafirstparts + 1),	new ItemStack(parts, 1, metafirstparts + 2), new ItemStack(parts, 1, metafirstparts + 3), new ItemStack(parts, 1, metafirstparts + 3), new ItemStack(parts, 1, metafirstparts + 4), new ItemStack(parts, 1, metafirstparts + 4), new ItemStack(Blocks.CHEST, 1, 0), new ItemStack(Blocks.CHEST, 1, 0)); 
	   RocketAssemblyRecipes.addShapelessRecipe(new ItemStack(rocket, 1, 3), new ItemStack(parts, 1, metafirstparts), new ItemStack(parts, 1, metafirstparts + 1), new ItemStack(parts, 1, metafirstparts + 1),	new ItemStack(parts, 1, metafirstparts + 2), new ItemStack(parts, 1, metafirstparts + 3), new ItemStack(parts, 1, metafirstparts + 3), new ItemStack(parts, 1, metafirstparts + 4), new ItemStack(parts, 1, metafirstparts + 4), new ItemStack(Blocks.CHEST, 1, 0), new ItemStack(Blocks.CHEST, 1, 0), new ItemStack(Blocks.CHEST, 1, 0)); 
   }
}
