package galaxyspace.core.registers.items;

import cpw.mods.fml.common.registry.GameRegistry;
import galaxyspace.GalaxySpace;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.prefab.energy.item.ItemElectricAxe;
import galaxyspace.core.prefab.energy.item.ItemElectricHoe;
import galaxyspace.core.prefab.energy.item.ItemElectricPickaxe;
import galaxyspace.core.prefab.energy.item.ItemElectricShovel;
import galaxyspace.core.prefab.energy.item.ItemElectricSword;
import galaxyspace.core.prefab.items.ItemAxeGS;
import galaxyspace.core.prefab.items.ItemHoeGS;
import galaxyspace.core.prefab.items.ItemPickaxeGS;
import galaxyspace.core.prefab.items.ItemShovelGS;
import galaxyspace.core.prefab.items.ItemSwordGS;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemBasicGS;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemBattery;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemCompressedPlates;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemGSOxygenTank;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemGlowstoneDusts;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemHeavyDutyPlates;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemIngots;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemModulesRocket;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemOxygenCanisterBase;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemRocketParts;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemSchematics;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemThermalClothTier2;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemTierKeysChest;
import galaxyspace.systems.SolarSystem.planets.overworld.items.armor.ItemArmors;
import galaxyspace.systems.SolarSystem.planets.overworld.items.armor.ItemJetPack;
import galaxyspace.systems.SolarSystem.planets.overworld.items.armor.ItemSpaceArmors;
import galaxyspace.systems.SolarSystem.planets.overworld.items.armor.ItemThermalPaddingTier2;
import galaxyspace.systems.SolarSystem.planets.overworld.items.rockets.ItemCargoFluidRocket;
import galaxyspace.systems.SolarSystem.planets.overworld.items.rockets.ItemTier4Rocket;
import galaxyspace.systems.SolarSystem.planets.overworld.items.rockets.ItemTier5Rocket;
import galaxyspace.systems.SolarSystem.planets.overworld.items.rockets.ItemTier6Rocket;
import galaxyspace.systems.SolarSystem.planets.overworld.items.tools.ItemAncientPickaxe;
import galaxyspace.systems.SolarSystem.planets.overworld.items.tools.ItemAncientSword;
import galaxyspace.systems.SolarSystem.planets.overworld.items.tools.ItemQuantBow;
import micdoodle8.mods.galacticraft.core.items.GCItems;
import micdoodle8.mods.galacticraft.planets.asteroids.items.AsteroidsItems;
import micdoodle8.mods.galacticraft.planets.mars.items.MarsItems;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

public class GSItems {
	
	public static Item BasicItems = new ItemBasicGS();
	
	public static Item Schematics = new ItemSchematics();
	public static Item TierKeys = new ItemTierKeysChest();
	
	public static Item CargoFluidRocket = new ItemCargoFluidRocket();
	public static Item Tier4Rocket = new ItemTier4Rocket();
	public static Item Tier5Rocket = new ItemTier5Rocket();
	public static Item Tier6Rocket = new ItemTier6Rocket();
	
	public static Item RocketModules = new ItemModulesRocket();	

	public static Item HeavyDutyPlates = new ItemHeavyDutyPlates();
    public static Item JetPack;

    public static Item ThermalPaddingTier2;
    public static Item ThermalClothTier2 = new ItemThermalClothTier2();
    
    public static Item PlasmaSword;
    public static Item PlasmaPickaxe;
    public static Item PlasmaAxe;
    public static Item PlasmaShovel;
    public static Item PlasmaHoe;
    
    public static Item CobaltSword;
    public static Item CobaltPickaxe;
    public static Item CobaltAxe;
    public static Item CobaltShovel;
    public static Item CobaltHoe;
    public static Item CobaltHelmet;
    public static Item CobaltPlate;
    public static Item CobaltLeg;
    public static Item CobaltBoots;
    
    public static Item AdvancedBattery = new ItemBattery("AdvancedBattery", 60000);
    public static Item ModernBattery = new ItemBattery("ModernBattery", 120000);
    public static Item ExtraBattery = new ItemBattery("ExtraBattery", 180000);
    public static Item UltraBattery = new ItemBattery("UltraBattery", 300000);
    
    public static Item RocketParts = new ItemRocketParts();
    public static Item CompressedPlates = new ItemCompressedPlates();
    public static Item Ingots = new ItemIngots();
    public static Item QuantBow = new ItemQuantBow();
    public static Item Helium3Canister;
    public static Item HydrogenCanister;
    public static Item HeliumHydrogenCanister;
    public static Item GlowstoneDusts = new ItemGlowstoneDusts();
    public static Item OxygenCanister = new ItemOxygenCanisterBase("small_oxygen_canister", 20000);
    
    public static Item SpacesuitHelmet;
    public static Item SpacesuitPlate;
    public static Item SpacesuitLeg;
    public static Item SpacesuitBoots;
    
    public static Item OxygenTankTier4 = new ItemGSOxygenTank(3, "oxygentank_t4", 3500, false);
    public static Item OxygenTankTier5 = new ItemGSOxygenTank(4, "oxygentank_t5", 4000, false);
    public static Item OxygenTankTier6 = new ItemGSOxygenTank(5, "oxygentank_t6", 4500, false);
    
    public static Item OxygenTankEPPTier1 = new ItemGSOxygenTank(6, "oxygentank_epp_t1", 3500, true);
    
    public static Item WaterBucket;
    public static Item IceBucket;
    public static Item DetheriumBucket;
    public static Item AncientPickaxe;
    public static Item AncientSword;
    
    public static Item EthaneMethaneBucket;
    public static Item HeliumHydrogenBucket;
    public static Item SulfurAcidBucket;
    
    public static ArmorMaterial JETPACK = EnumHelper.addArmorMaterial("JETPACK", 25, new int[] {0, 3, 0, 0}, 0);
    public static ArmorMaterial SPACESUIT = EnumHelper.addArmorMaterial("SPACESUIT", 30, new int[] {2, 4, 5, 3}, 0);
    public static ArmorMaterial ARMOR_COBALT = EnumHelper.addArmorMaterial("COBALT", 15, new int[]{2, 6, 5, 2}, 9);

    public static ToolMaterial PLASMA = EnumHelper.addToolMaterial("PLASMA", 3, 700, 8.2F, 4, 0);
    public static ToolMaterial ANCIENT = EnumHelper.addToolMaterial("ANCIENT", 3, 1700, 10.0F, 5, 0);
    public static ToolMaterial COBALT = EnumHelper.addToolMaterial("COBALT", 2, 700, 8.0F, 3, 0);
    
  	public static void initialize()
    {
		
		PlasmaSword = new ItemElectricSword("plasmasword", PLASMA, 5000);
		PlasmaPickaxe = new ItemElectricPickaxe("plasmapickaxe", PLASMA, 8000);
		PlasmaAxe = new ItemElectricAxe("plasmaaxe", PLASMA, 5000);
		PlasmaShovel = new ItemElectricShovel("plasmashovel", PLASMA, 5000);
		PlasmaHoe = new ItemElectricHoe("plasmahoe", PLASMA, 5000);		
		
		CobaltSword = new ItemSwordGS("cobalt_sword", COBALT);
		CobaltPickaxe = new ItemPickaxeGS("cobalt_pickaxe", COBALT, false);
		CobaltAxe = new ItemAxeGS("cobalt_axe", COBALT);
		CobaltShovel = new ItemShovelGS("cobalt_shovel", COBALT);
		CobaltHoe = new ItemHoeGS("cobalt_hoe", COBALT);

		//PlasmaGun = new ItemElectricGun("plasmagun", 3000);

		AncientPickaxe = new ItemAncientPickaxe("ancient_pickaxe", ANCIENT);
		AncientSword = new ItemAncientSword("ancient_sword", ANCIENT);
		
		
		registerItems();
    	oreDictRegistration();
    	registerArmors();
    }
	
	private static void registerItems()
    {
		registerItem(BasicItems);
		
		registerItem(Schematics);
		registerItem(TierKeys);
		if(GSConfigCore.getRegisterRocket(4)) 
			registerItem(Tier4Rocket);
		if(GSConfigCore.getRegisterRocket(5)) 
			registerItem(Tier5Rocket);
		if(GSConfigCore.getRegisterRocket(6)) 
			registerItem(Tier6Rocket);
		//registerItem(CargoFluidRocket);
		
		registerItem(HeavyDutyPlates);		
			
		registerItem(ThermalClothTier2);
		
		registerItem(CobaltSword);
		registerItem(CobaltPickaxe);
		registerItem(CobaltAxe);
		registerItem(CobaltShovel);
		registerItem(CobaltHoe);
		registerItem(PlasmaSword);
		registerItem(PlasmaPickaxe);
		registerItem(PlasmaAxe);
		registerItem(PlasmaShovel);
		registerItem(PlasmaHoe);
		
		registerItem(AdvancedBattery);
		registerItem(ModernBattery);
		registerItem(ExtraBattery);
		registerItem(UltraBattery);

		registerItem(RocketModules);
		registerItem(RocketParts);	
		registerItem(CompressedPlates);	
		registerItem(Ingots);
		registerItem(QuantBow);
		//registerItem(PlasmaGun);
		registerItem(GlowstoneDusts);				
		registerItem(OxygenCanister);				

		registerItem(OxygenTankTier4);
		registerItem(OxygenTankTier5);
		registerItem(OxygenTankTier6);
		registerItem(OxygenTankEPPTier1);	
		registerItem(AncientPickaxe);
		registerItem(AncientSword);
    }
	
	public static void registerItem(Item item)
    {
		GameRegistry.registerItem(item, item.getUnlocalizedName(), GalaxySpace.MODID);
    }
	
	public static void oreDictRegistration()
    {
		 OreDictionary.registerOre("ingotCobalt", new ItemStack(GSItems.Ingots, 1, 0));
		 OreDictionary.registerOre("ingotMagnesium", new ItemStack(GSItems.Ingots, 1, 1));
		 OreDictionary.registerOre("ingotNickel", new ItemStack(GSItems.Ingots, 1, 2));
		 OreDictionary.registerOre("dustSulfur", new ItemStack(GSItems.BasicItems, 1, 9));
		 OreDictionary.registerOre("waferModern", new ItemStack(GSItems.BasicItems, 1, 7));
		 OreDictionary.registerOre("sapphire", new ItemStack(GSItems.BasicItems, 1, 6));
		 OreDictionary.registerOre("uranium", new ItemStack(GSItems.BasicItems, 1, 16));
		 
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
		 
		 OreDictionary.registerOre("plateCobalt", new ItemStack(GSItems.CompressedPlates, 1, 0));
		 OreDictionary.registerOre("plateMagnesium", new ItemStack(GSItems.CompressedPlates, 1, 1));
		 OreDictionary.registerOre("plateNickel", new ItemStack(GSItems.CompressedPlates, 1, 2));
		 OreDictionary.registerOre("plateCoal",new ItemStack(GSItems.CompressedPlates, 1, 3));

    }

	static void registerArmors()
	{
		CobaltHelmet = new ItemArmors(ARMOR_COBALT, GalaxySpace.proxy.getArmorRenderIndex(), 0, "cobalt_helmet");
		CobaltPlate = new ItemArmors(ARMOR_COBALT, GalaxySpace.proxy.getArmorRenderIndex(), 1, "cobalt_plate");
		CobaltLeg = new ItemArmors(ARMOR_COBALT, GalaxySpace.proxy.getArmorRenderIndex(), 2, "cobalt_leg");
		CobaltBoots = new ItemArmors(ARMOR_COBALT, GalaxySpace.proxy.getArmorRenderIndex(), 3, "cobalt_boots");

		JetPack = new ItemJetPack(1);
		
		SpacesuitHelmet = new ItemSpaceArmors(SPACESUIT, GalaxySpace.proxy.getArmorRenderIndex(), 0, "spacesuit_helmet");
		SpacesuitPlate = new ItemSpaceArmors(SPACESUIT, GalaxySpace.proxy.getArmorRenderIndex(), 1, "spacesuit_plate");
		SpacesuitLeg = new ItemSpaceArmors(SPACESUIT, GalaxySpace.proxy.getArmorRenderIndex(), 2, "spacesuit_leg");
		SpacesuitBoots = new ItemSpaceArmors(SPACESUIT, GalaxySpace.proxy.getArmorRenderIndex(), 3, "spacesuit_boots");

		ThermalPaddingTier2 = new ItemThermalPaddingTier2();
		
		registerItem(CobaltHelmet);
		registerItem(CobaltPlate);
		registerItem(CobaltLeg);
		registerItem(CobaltBoots);		
		registerItem(SpacesuitHelmet);
		registerItem(SpacesuitPlate);
		registerItem(SpacesuitLeg);
		registerItem(SpacesuitBoots);
		registerItem(ThermalPaddingTier2);
		registerItem(JetPack);
			
	}
}
