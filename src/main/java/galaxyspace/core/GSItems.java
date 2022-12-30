package galaxyspace.core;

import galaxyspace.GalaxySpace;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.prefab.items.ItemAxeGS;
import galaxyspace.core.prefab.items.ItemFluidCanisterGS;
import galaxyspace.core.prefab.items.ItemHoeGS;
import galaxyspace.core.prefab.items.ItemPickaxeGS;
import galaxyspace.core.prefab.items.ItemSpadeGS;
import galaxyspace.core.prefab.items.ItemSwordGS;
import galaxyspace.core.prefab.items.rockets.ItemFluidCargoRocket;
import galaxyspace.core.prefab.items.rockets.ItemMultiSeatRocketTest;
import galaxyspace.core.prefab.items.rockets.ItemTier4Rocket;
import galaxyspace.core.prefab.items.rockets.ItemTier5Rocket;
import galaxyspace.core.prefab.items.rockets.ItemTier6Rocket;
import galaxyspace.systems.SolarSystem.planets.mars.items.ItemMarsRover;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemBasicGS;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemBattery;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemCompressedPlates;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemGSOxygenTank;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemHeavyDutyPlates;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemIngots;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemNuggets;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemRocketModules;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemRocketParts;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemSchematics;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemTierKeysChest;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemUpgrades;
import galaxyspace.systems.SolarSystem.planets.overworld.items.armor.ItemArmorGS;
import galaxyspace.systems.SolarSystem.planets.overworld.items.armor.ItemJetpack;
import galaxyspace.systems.SolarSystem.planets.overworld.items.armor.ItemSpaceSuitLight;
import galaxyspace.systems.SolarSystem.planets.overworld.items.armor.ItemSpaceSuitTier1;
import galaxyspace.systems.SolarSystem.planets.overworld.items.armor.ItemThermalPaddingBase;
import galaxyspace.systems.SolarSystem.planets.overworld.items.tools.ItemGeologicalScanner;
import galaxyspace.systems.SolarSystem.planets.overworld.items.tools.ItemMatterManipulator;
import galaxyspace.systems.SolarSystem.planets.overworld.items.tools.ItemPlasmaAxe;
import galaxyspace.systems.SolarSystem.planets.overworld.items.tools.ItemPlasmaPickaxe;
import galaxyspace.systems.SolarSystem.planets.overworld.items.tools.ItemPlasmaSword;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.wrappers.PartialCanister;
import micdoodle8.mods.galacticraft.planets.asteroids.items.AsteroidsItems;
import micdoodle8.mods.galacticraft.planets.mars.items.MarsItems;
import micdoodle8.mods.galacticraft.planets.venus.VenusItems;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class GSItems {
	
	public static ArmorMaterial SPACESUIT_LIGHT = EnumHelper.addArmorMaterial("SPACESUIT_LIGHT", "", 15, new int[] {3, 6, 7, 3}, 0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);
	public static ArmorMaterial SPACESUIT_TIER_1 = EnumHelper.addArmorMaterial("SPACESUIT", "", 18, new int[] {6, 13, 11, 6}, 0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);
	public static ArmorMaterial COBALT = EnumHelper.addArmorMaterial("COBALT", "", 13, new int[]{2, 5, 6, 2}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);
	public static ToolMaterial COBALT_TOOLS = EnumHelper.addToolMaterial("cobalt", 2, 250, 6.0F, 2.0F, 14);
	public static ToolMaterial PLASMA_TOOLS = EnumHelper.addToolMaterial("plasma", 3, 1500, 8.0F, 8.0F, 0);
	
	
	public static Item BASIC = new ItemBasicGS();
	
	public static Item INGOTS = new ItemIngots();
	public static Item NUGGETS = new ItemNuggets();
	
	public static Item HDP = new ItemHeavyDutyPlates();
	public static Item COMPRESSED_PLATES = new ItemCompressedPlates();
	public static Item ROCKET_MODULES = new ItemRocketModules();
	public static Item ROCKET_PARTS = new ItemRocketParts();
	public static Item SCHEMATICS = new ItemSchematics();
	public static Item DUNGEON_KEYS = new ItemTierKeysChest();
	public static Item UPGRADES = new ItemUpgrades();
	
	public static Item OXYGENTANK_TIER_4 = new ItemGSOxygenTank(3, "oxygen_tank_4", 3500, false);
	public static Item OXYGENTANK_TIER_5 = new ItemGSOxygenTank(4, "oxygen_tank_5", 4000, false);
	public static Item OXYGENTANK_TIER_6 = new ItemGSOxygenTank(5, "oxygen_tank_6", 4500, false);
	public static Item OXYGENTANK_TIER_EPP = new ItemGSOxygenTank(6, "oxygen_tank_epp_1", 2500, true);
	
	public static Item ROCKET_TIER_4 = new ItemTier4Rocket("rocket_tier_4");
	public static Item ROCKET_TIER_5 = new ItemTier5Rocket("rocket_tier_5");
	public static Item ROCKET_TIER_6 = new ItemTier6Rocket("rocket_tier_6");
	public static Item ROCKET_MULTI_SEAT = new ItemMultiSeatRocketTest("rocket_multi_seat");
	public static Item ROCKET_FLUID_CRAGO = new ItemFluidCargoRocket("rocket_fluid_cargo");
	public static Item MARS_ROVER = new ItemMarsRover("mars_rover");
	
	public static Item ADVANCED_BATTERY = new ItemBattery("advanced_battery", 60000);
	public static Item MODERN_BATTERY = new ItemBattery("modern_battery", 120000);
	public static Item EXTRA_BATTERY = new ItemBattery("extra_battery", 180000);	
	public static Item ULTIMATE_BATTERY = new ItemBattery("ultimate_battery", 300000);	  
	
	public static Item HYDROGEN_CANISTER = new ItemFluidCanisterGS("hydrogen_canister", FluidRegistry.getFluid("hydrogen"));
	public static Item ETHANE_CANISTER = new ItemFluidCanisterGS("ethane_canister", FluidRegistry.getFluid("ethane"));
	public static Item EM_CANISTER = new ItemFluidCanisterGS("em_canister", GSFluids.LiquidEthaneMethane);
	public static Item HELIUM_CANISTER = new ItemFluidCanisterGS("helium_canister", GSFluids.Helium3);
	public static Item HELIUM_HYDROGEN_CANISTER = new ItemFluidCanisterGS("hh_canister", GSFluids.HeliumHydrogen);
	public static Item NATURE_GAS_CANISTER = new ItemFluidCanisterGS("naturegas_canister", GSFluids.NatureGas);
	
	//public static Item PLASTIC_OIL_CANISTER = new ItemFluidCanisterGS("plastic_oil_canister", FluidRegistry.getFluid("oil"), 10001);
		
	//ARMOR	
	public static Item SPACE_SUIT_HELMET = new ItemSpaceSuitTier1(SPACESUIT_TIER_1, EntityEquipmentSlot.HEAD);
	public static Item SPACE_SUIT_BODY = new ItemSpaceSuitTier1(SPACESUIT_TIER_1, EntityEquipmentSlot.CHEST);
	public static Item SPACE_SUIT_LEGGINS = new ItemSpaceSuitTier1(SPACESUIT_TIER_1, EntityEquipmentSlot.LEGS);
	public static Item SPACE_SUIT_BOOTS = new ItemSpaceSuitTier1(SPACESUIT_TIER_1, EntityEquipmentSlot.FEET);
	
	public static Item SPACE_SUIT_LIGHT_HELMET = new ItemSpaceSuitLight(SPACESUIT_LIGHT, EntityEquipmentSlot.HEAD);
	public static Item SPACE_SUIT_LIGHT_BODY = new ItemSpaceSuitLight(SPACESUIT_LIGHT, EntityEquipmentSlot.CHEST);
	public static Item SPACE_SUIT_LIGHT_LEGGINS = new ItemSpaceSuitLight(SPACESUIT_LIGHT, EntityEquipmentSlot.LEGS);
	public static Item SPACE_SUIT_LIGHT_BOOTS = new ItemSpaceSuitLight(SPACESUIT_LIGHT, EntityEquipmentSlot.FEET);
	
	public static Item COBALT_HELMET = new ItemArmorGS("cobalt_helmet", COBALT, EntityEquipmentSlot.HEAD);
	public static Item COBALT_CHEST = new ItemArmorGS("cobalt_chest", COBALT, EntityEquipmentSlot.CHEST);
	public static Item COBALT_LEGS = new ItemArmorGS("cobalt_legs", COBALT, EntityEquipmentSlot.LEGS);
	public static Item COBALT_BOOTS = new ItemArmorGS("cobalt_boots", COBALT, EntityEquipmentSlot.FEET);
	
	public static Item THERMAL_PADDING_3 = new ItemThermalPaddingBase(3, false);
	public static Item THERMAL_PADDING_4 = new ItemThermalPaddingBase(4, false);
	//TOOLS
	public static Item COBALT_SWORD = new ItemSwordGS("cobalt_sword", COBALT_TOOLS);
	public static Item COBALT_AXE = new ItemAxeGS("cobalt_axe", COBALT_TOOLS);
	public static Item COBALT_SPADE = new ItemSpadeGS("cobalt_spade", COBALT_TOOLS);
	public static Item COBALT_HOE = new ItemHoeGS("cobalt_hoe", COBALT_TOOLS);
	public static Item COBALT_PICKAXE = new ItemPickaxeGS("cobalt_pickaxe", COBALT_TOOLS, false);
	
	public static Item PLASMA_SWORD = new ItemPlasmaSword();
	public static Item PLASMA_AXE = new ItemPlasmaAxe();
	public static Item PLASMA_PICKAXE = new ItemPlasmaPickaxe();
	
	public static Item JETPACK = new ItemJetpack(SPACESUIT_TIER_1, EntityEquipmentSlot.CHEST);
	public static Item MATTER_MANIPULATOR = new ItemMatterManipulator();
	public static Item GEOLOGICAL_SCANNER = new ItemGeologicalScanner();
	
	public static void initialize()
	{		
		registerItem(BASIC);
		registerItem(INGOTS);
		registerItem(NUGGETS);
		registerItem(HDP);		
		registerItem(COMPRESSED_PLATES);
		registerItem(ROCKET_MODULES);
		registerItem(ROCKET_PARTS);
		registerItem(SCHEMATICS);
		registerItem(DUNGEON_KEYS);
		registerItem(UPGRADES);
		
		registerItem(ADVANCED_BATTERY);
		registerItem(MODERN_BATTERY);
		registerItem(EXTRA_BATTERY);
		registerItem(ULTIMATE_BATTERY);
		
		registerItem(HYDROGEN_CANISTER);
		registerItem(ETHANE_CANISTER);
		registerItem(EM_CANISTER);
		registerItem(HELIUM_CANISTER);
		registerItem(HELIUM_HYDROGEN_CANISTER);
		registerItem(NATURE_GAS_CANISTER);
		
		registerItem(OXYGENTANK_TIER_4);
		registerItem(OXYGENTANK_TIER_5);
		registerItem(OXYGENTANK_TIER_6);
		registerItem(OXYGENTANK_TIER_EPP);
		
		registerItem(ROCKET_TIER_4);
		registerItem(ROCKET_TIER_5);
		registerItem(ROCKET_TIER_6);
		registerItem(ROCKET_MULTI_SEAT);
		//registerItem(ROCKET_FLUID_CRAGO);
		registerItem(MARS_ROVER);
		
		registerItem(SPACE_SUIT_HELMET);
		registerItem(SPACE_SUIT_BODY);
		registerItem(SPACE_SUIT_LEGGINS);
		registerItem(SPACE_SUIT_BOOTS);	
		
		registerItem(SPACE_SUIT_LIGHT_HELMET);
		registerItem(SPACE_SUIT_LIGHT_BODY);
		registerItem(SPACE_SUIT_LIGHT_LEGGINS);
		registerItem(SPACE_SUIT_LIGHT_BOOTS);	
		
		registerItem(COBALT_HELMET);
		registerItem(COBALT_CHEST);
		registerItem(COBALT_LEGS);
		registerItem(COBALT_BOOTS);
		
		registerItem(THERMAL_PADDING_3);
		registerItem(THERMAL_PADDING_4);
		
		registerItem(COBALT_SWORD);
		registerItem(COBALT_AXE);
		registerItem(COBALT_PICKAXE);
		registerItem(COBALT_SPADE);
		registerItem(COBALT_HOE);
		
		registerItem(PLASMA_SWORD);
		registerItem(PLASMA_AXE);
		registerItem(PLASMA_PICKAXE);
		
		registerItem(JETPACK);
		//registerItem(MATTER_MANIPULATOR);
		registerItem(GEOLOGICAL_SCANNER);
		
		GalacticraftCore.proxy.registerCanister(new PartialCanister(HYDROGEN_CANISTER, GalaxySpace.MODID, "hydrogen_canister", 6));
		GalacticraftCore.proxy.registerCanister(new PartialCanister(ETHANE_CANISTER, GalaxySpace.MODID, "ethane_canister", 6));
		GalacticraftCore.proxy.registerCanister(new PartialCanister(EM_CANISTER, GalaxySpace.MODID, "em_canister", 6));
		GalacticraftCore.proxy.registerCanister(new PartialCanister(HELIUM_CANISTER, GalaxySpace.MODID, "helium_canister", 6));
		GalacticraftCore.proxy.registerCanister(new PartialCanister(HELIUM_HYDROGEN_CANISTER, GalaxySpace.MODID, "hh_canister", 6));
		GalacticraftCore.proxy.registerCanister(new PartialCanister(NATURE_GAS_CANISTER, GalaxySpace.MODID, "naturegas_canister", 6));
		
	}
	
	public static void oreDictRegistration()
    {
		 OreDictionary.registerOre("ingotCobalt", new ItemStack(GSItems.INGOTS, 1, 0));
		 OreDictionary.registerOre("ingotMagnesium", new ItemStack(GSItems.INGOTS, 1, 1));
		 OreDictionary.registerOre("ingotNickel", new ItemStack(GSItems.INGOTS, 1, 2));
		 OreDictionary.registerOre("dustSulfur", new ItemStack(GSItems.BASIC, 1, 7));
		 OreDictionary.registerOre("waferModern", new ItemStack(GSItems.BASIC, 1, 5));
		 
		 if(GSConfigCore.enablePlateOreDict) {
			 OreDictionary.registerOre("plateCopper", new ItemStack(GCItems.basicItem, 1, 6));
			 OreDictionary.registerOre("plateTin", new ItemStack(GCItems.basicItem, 1, 7));
			 OreDictionary.registerOre("plateAluminum", new ItemStack(GCItems.basicItem, 1, 8));
			 OreDictionary.registerOre("plateSteel", new ItemStack(GCItems.basicItem, 1, 9));
			 OreDictionary.registerOre("plateBronze", new ItemStack(GCItems.basicItem, 1, 10));
			 OreDictionary.registerOre("plateIron",new ItemStack(GCItems.basicItem, 1, 11));
			 OreDictionary.registerOre("plateDesh",new ItemStack(MarsItems.marsItemBasic, 1, 5));
			 OreDictionary.registerOre("plateTitanium",new ItemStack(AsteroidsItems.basicItem, 1, 6));			 
		 }
		 
		 OreDictionary.registerOre("plateCoal",new ItemStack(GSItems.COMPRESSED_PLATES, 1, 0));
		 OreDictionary.registerOre("plateCobalt", new ItemStack(GSItems.COMPRESSED_PLATES, 1, 1));
		 OreDictionary.registerOre("plateMagnesium", new ItemStack(GSItems.COMPRESSED_PLATES, 1, 2));
		 OreDictionary.registerOre("plateNickel", new ItemStack(GSItems.COMPRESSED_PLATES, 1, 3));
		 
		 OreDictionary.registerOre("ingotLead", new ItemStack(VenusItems.basicItem, 1, 1));
    }
	
	public static void registerItem(Item item)
    {
        String name = item.getTranslationKey().substring(5);
        if (item.getRegistryName() == null)
        {
            item.setRegistryName(name);
        }
        GCCoreUtil.registerGalacticraftItem(name, item);
        GalacticraftCore.itemListTrue.add(item);
        GalacticraftCore.proxy.postRegisterItem(item);
    }
	
}
