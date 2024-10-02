package galaxyspace.core.configs;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.FMLLog;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.util.GCLog;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class GSConfigCore
{
    public static boolean loaded;

    static Configuration config;

    public GSConfigCore(File file)
    {
        if (!GSConfigCore.loaded)
        {
        	GSConfigCore.config = new Configuration(file);
        	GSConfigCore.syncConfig(true);
        }
    }

    public static boolean enableCheckVersion;
    public static boolean enableMusic;

    public static boolean enableOverworldGeneration;
    public static boolean enableOresGeneration;
    public static boolean enableDungeonsGeneration;
    public static boolean enableNewMenu;
    public static boolean enableFogVenus;
    public static boolean enableSkyOverworld;
    public static boolean enableGCMars;
    public static boolean enableAlienTradeSystem;
    public static boolean enableRenderAtmosphere;
    public static boolean enableOldSystems = false;

    public static int idSolarRadiation = 29;
    public static int idAntiRadiation = 30;
    public static int speedTimeTravel = 1;
    
    public static boolean enableMethaneParticle;
    public static boolean enableNewGalaxyMap;
    public static boolean enable2DGalaxyMap;
    public static boolean enableImgOnGalaxyMap;
    public static boolean enableTimeTravelSystem;
    public static boolean enableUnreachable;
    public static boolean enableHardMode;
    public static boolean enableWorldEngine;
    public static boolean enablePlateOreDict;
    public static boolean enableSpaceSuitHUD;
    public static boolean enableSolarRadiationSystem;
    public static boolean enableGenAncientAmulet;
    public static boolean enableDynamicImgOnMap;
    public static boolean enableRenderImgOnSky;
    public static boolean enableNewTierSystem;

    
    public static String keyOverrideToggleHelmet, keyOverrideToggleChest, keyOverrideToggleLegs, keyOverrideToggleBoots;
    public static int keyOverrideToggleHelmetI, keyOverrideToggleChestI, keyOverrideToggleLegsI, keyOverrideToggleBootsI;

    public static String spacesuit_pos = "bottom";
    public static String registeredRocket = "456";
    public static boolean enableDebug;
    
    public static String[] oregenIDs = {};    
    public static String[] tradeIDs = {};
    public static String[] protect_armor = {};

    public static void syncConfig(boolean load)
    {
        List<String> propOrder = new ArrayList<String>();

        try
        {
            Property prop = null;

            if (!config.isChild)
            {
                if (load)
                {
                    config.load();
                }
            }
            
          
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "enableOldSystems", enableOldSystems);
            prop.comment = "Enable/Disable Old Star Systems (Tau Ceti, Vega).";
            prop.setLanguageKey("gc.configgui.enableOldSystems").setRequiresMcRestart(true);
            enableOldSystems = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableMusic", true);
            prop.comment = "Enable/Disable new music on GS Planets/Moons.";
            prop.setLanguageKey("gc.configgui.enableMusic").setRequiresMcRestart(true);
            enableMusic = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "enableRenderAtmosphere", true);
            prop.comment = "Enable/Disable render planet atmosphere on sky.";
            prop.setLanguageKey("gc.configgui.enableRenderAtmosphere").setRequiresMcRestart(true);
            enableRenderAtmosphere = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableNewTierSystem", true);
            prop.comment = "Enable/Disable new tier system.";
            prop.setLanguageKey("gc.configgui.enableNewTierSystem").setRequiresMcRestart(true);
            enableNewTierSystem = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "enableSolarRadiationSystem", true);
            prop.comment = "Enable/Disable solar radiation system.";
            prop.setLanguageKey("gc.configgui.enableSolarRadiationSystem").setRequiresMcRestart(true);
            enableSolarRadiationSystem = prop.getBoolean(true);
            propOrder.add(prop.getName());   
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableCheckVersion", true);
            prop.comment = "Enable/Disable Check Version.";
            prop.setLanguageKey("gc.configgui.enableCheckVersion").setRequiresMcRestart(true);
            enableCheckVersion = prop.getBoolean(true);
            propOrder.add(prop.getName());            
       
            prop = config.get(Constants.CONFIG_CATEGORY_WORLDGEN, "enableOverworldGeneration", true);
            prop.comment = "Enable/Disable Generation Ores on Overworld.";
            prop.setLanguageKey("gc.configgui.enableOverworldGeneration").setRequiresMcRestart(true);
            enableOverworldGeneration = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_WORLDGEN, "enableOresGeneration", true);
            prop.comment = "Enable/Disable Generation Ores on Planets/Moons (Global Config).";
            prop.setLanguageKey("gc.configgui.enableOresGeneration").setRequiresMcRestart(true);
            enableOresGeneration = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_WORLDGEN, "enableDungeonsGeneration", true);
            prop.comment = "Enable/Disable Dungeons Generation on Planets/Moons (Global Config).";
            prop.setLanguageKey("gc.configgui.enableDungeonsGeneration").setRequiresMcRestart(true);
            enableDungeonsGeneration = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableNewMenu", true);
            prop.comment = "Enable/Disable new Main Menu.";
            prop.setLanguageKey("gc.configgui.enableNewMenu").setRequiresMcRestart(true);
            enableNewMenu = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "enableMethaneParticle", true);
            prop.comment = "Enable/Disable Ethane-Methane particles in world.";
            prop.setLanguageKey("gc.configgui.enableMethaneParticle").setRequiresMcRestart(true);
            enableMethaneParticle = prop.getBoolean(true);
            propOrder.add(prop.getName());   
            
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "enableGCMars", true);
            prop.comment = "Enable/Disable register Phobos and Deimos on GC Mars. (For sub-addons to make custom Mars)";
            prop.setLanguageKey("gc.configgui.enableGCMars").setRequiresMcRestart(true);
            enableGCMars = prop.getBoolean(true);
            propOrder.add(prop.getName());           
            
            prop = config.get(Constants.CONFIG_CATEGORY_WORLDGEN, "Other mods ores for GC to generate on GS planets", new String [] { });
            prop.comment = "Enter IDs of other mods' ores here for Galacticraft to generate them on GalaxySpace planets. Format is BlockName or BlockName:metadata. Use optional parameters at end of each line: /RARE /UNCOMMON or /COMMON for rarity in a chunk; /DEEP /SHALLOW or /BOTH for height; /SINGLE /STANDARD or /LARGE for clump size; /XTRARANDOM for ores sometimes there sometimes not at all.  /ONLYMOON or /ONLYMARS if wanted on one planet only.  If nothing specified, defaults are /COMMON, /BOTH and /STANDARD.  Repeat lines to generate a huge quantity of ores.";
            prop.comment = "/ONLYPHOBOS, /ONLYDEIMOS, /ONLYEUROPA, /ONLYIO, /ONLYENCELADUS, /ONLYVENUS, /ONLYMERCURY, /ONLYCERES if wanted on one planet only.";
            prop.setLanguageKey("gc.configgui.otherModOreGenIDs");
            oregenIDs = prop.getStringList();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "enableFogVenus", true);
            prop.comment = "Enable/Disable Fog on Venus.";
            prop.setLanguageKey("gc.configgui.enableFogVenus").setRequiresMcRestart(true);
            enableFogVenus = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "enableSkyOverworld", false);
            prop.comment = "Enable/Disable new render sky on Overworld.";
            prop.setLanguageKey("gc.configgui.enableSkyOverworld").setRequiresMcRestart(true);
            enableSkyOverworld = prop.getBoolean(false);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "idSolarRadiation", idSolarRadiation);
            prop.comment = "ID Potion 'Solar Radiation'";
            prop.setLanguageKey("gc.configgui.idSolarRadiation").setRequiresMcRestart(true);
            idSolarRadiation = prop.getInt();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "idAntiRadiation", idAntiRadiation);
            prop.comment = "ID Potion 'Anti Radiation'";
            prop.setLanguageKey("gc.configgui.idAntiRadiation").setRequiresMcRestart(true);
            idAntiRadiation = prop.getInt();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "speedTimeTravel", speedTimeTravel);
            prop.comment = "Time Travel speed factor.";
            prop.setLanguageKey("gc.configgui.speedTimeTravel").setRequiresMcRestart(true);
            speedTimeTravel = prop.getInt();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableNewGalaxyMap", true);
            prop.comment = "Enable/Disable New Galaxy Map.";
            prop.setLanguageKey("gc.configgui.enableNewGalaxyMap").setRequiresMcRestart(true);
            enableNewGalaxyMap = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enable2DGalaxyMap", false);
            prop.comment = "Enable/Disable 2D Galaxy Map. (like in GC2)";
            prop.setLanguageKey("gc.configgui.enable2DGalaxyMap").setRequiresMcRestart(true);
            enable2DGalaxyMap = prop.getBoolean(false);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableImgOnGalaxyMap", true);
            prop.comment = "Enable/Disable background image on Galaxy Map. (If false, returned black color and grid)";
            prop.setLanguageKey("gc.configgui.enableImgOnGalaxyMap").setRequiresMcRestart(true);
            enableImgOnGalaxyMap = prop.getBoolean(true);
            propOrder.add(prop.getName());

            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableTimeTravelSystem", true);
            prop.comment = "Enable/Disable Time Travel System on Galaxy Map.";
            prop.setLanguageKey("gc.configgui.enableTimeTravelSystem").setRequiresMcRestart(true);
            enableTimeTravelSystem = prop.getBoolean(true);
            propOrder.add(prop.getName());
   
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableUnreachable", true);
            prop.comment = "Enable/Disable Unreachable Planets/Moons";
            prop.setLanguageKey("gc.configgui.enableUnreachable").setRequiresMcRestart(true);
            enableUnreachable = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableHardMode", true);
            prop.comment = "Enable/Disable Hard Mode (Death of Atm. Pressure)";
            prop.setLanguageKey("gc.configgui.enableHardMode").setRequiresMcRestart(true);
            enableHardMode = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_WORLDGEN, "enableWorldEngine", true);
            prop.comment = "Enable/Disable 'World Engine' - advanced world generation";
            prop.setLanguageKey("gc.configgui.enableWorldEngine").setRequiresMcRestart(true);
            enableWorldEngine = prop.getBoolean(true);
            propOrder.add(prop.getName());            
           
            prop = config.get("development", "enableDebug", false);
            prop.comment = "Enable/Disable Debug mode";
            prop.setLanguageKey("gc.configgui.enableDebug").setRequiresMcRestart(true);
            enableDebug = prop.getBoolean(false);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "enableRenderImgOnSky", true);
            prop.comment = "Enable/Disable render additions images on sky. (ex. Andromeda)";
            prop.setLanguageKey("gc.configgui.enableRenderImgOnSky").setRequiresMcRestart(true);
            enableRenderImgOnSky = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableAlienTradeSystem", true);
            prop.comment = "Enable/Disable Alien Trade System";
            prop.setLanguageKey("gc.configgui.enableAlienTradeSystem").setRequiresMcRestart(true);
            enableAlienTradeSystem = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "Alien Trade Resources IDs", 
            		new String[] 
            		{            			
            			"GalacticraftCore:item.oxygenTankLightFull-1:900;GalaxySpace:item.BasicItems-2:6;GalacticraftCore:item.oxygenTankLightFull-1:720",
            			"GalacticraftCore:item.oxygenTankMedFull-1:1800;GalaxySpace:item.BasicItems-4:6;GalacticraftCore:item.oxygenTankMedFull-1:1440",
            			"GalacticraftCore:item.oxygenTankHeavyFull-1:2700;GalaxySpace:item.BasicItems-6:6;GalacticraftCore:item.oxygenTankHeavyFull-1:2160",
            			"minecraft:torch-10:0;GalaxySpace:item.BasicItems-2:6;GalacticraftCore:tile.glowstoneTorch-10:0",            			
            			"minecraft:emerald-2:0;GalaxySpace:item.BasicItems-1:6",
            			//"GalacticraftCore:item.battery-1:100;GalaxySpace:item.BasicItems-4:6;GalacticraftCore:item.battery-1:50",
            			"GalaxySpace:item.BasicItems-6:6;GalacticraftCore:item.fuelCanisterPartial-1:20",
            			"GalacticraftCore:item.schematic-1:0;GalaxySpace:item.BasicItems-32:6;GalaxySpace:item.BasicItems-1:12",
            			"GalaxySpace:item.BasicItems-2:6;GalaxySpace:item.BasicItems-1:11",
            			"GalaxySpace:item.BasicItems-12:6;GalacticraftCore:item.cheeseCurd-3:0;GalacticraftCore:tile.cheeseBlock-1:0"            			
            		});
            prop.comment = "List recipes for Alien Villagers. Format: 'component1-count:meta;component2-count:meta;result-count:meta' ";
            prop.setLanguageKey("gc.configgui.tradeIDs").setRequiresMcRestart(true);
            tradeIDs = prop.getStringList();
            propOrder.add(prop.getName());
            
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "Custom radiation and pressure armor list", 
            		new String[] 
            		{        
            			"IC2:itemArmorQuantumHelmet",
            			"IC2:itemArmorQuantumChestplate",
            			"IC2:itemArmorQuantumLegs",
            			"IC2:itemArmorQuantumBoots"   				
            		
            		});
            prop.comment = "List armor with protect radiation and pressure. Format: 'modid:item' ";
            prop.setLanguageKey("gc.configgui.tradeIDs").setRequiresMcRestart(true);
            protect_armor = prop.getStringList();
            propOrder.add(prop.getName());
            config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enablePlateOreDict", true);
            prop.comment = "Enable/Disable register OreDict for GC Compressed Plate";
            prop.setLanguageKey("gc.configgui.enablePlateOreDict").setRequiresMcRestart(true);
            enablePlateOreDict = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableSpaceSuitHUD", true);
            prop.comment = "Enable/Disable show Space Suit HUD (Sensor, Jetpack, etc)";
            prop.setLanguageKey("gc.configgui.enableSpaceSuitHUD").setRequiresMcRestart(true);
            enableSpaceSuitHUD = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableGenAncientAmulet", true);
            prop.comment = "Enable/Disable generate Ancient Amulet in dungeons.";
            prop.setLanguageKey("gc.configgui.enableGenAncientAmulet").setRequiresMcRestart(true);
            enableGenAncientAmulet = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableDynamicImgOnMap", true);
            prop.comment = "Enable/Disable dynamic image on Galaxy Map.";
            prop.setLanguageKey("gc.configgui.enableDynamicImgOnMap").setRequiresMcRestart(true);
            enableDynamicImgOnMap = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "Toggle Helmet Key", "KEY_F");
            prop.comment = "Leave 'KEY_' value, adding the intended keyboard character to replace the letter. Values 0-9 and A-Z are accepted";
            prop.setLanguageKey("gc.configgui.keyOverrideToggleHelmet").setRequiresMcRestart(false);
            keyOverrideToggleHelmet = prop.getString();
            keyOverrideToggleHelmetI = parseKeyValue(keyOverrideToggleHelmet);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "Toggle Chestplate Key", "KEY_G");
            prop.comment = "Leave 'KEY_' value, adding the intended keyboard character to replace the letter. Values 0-9 and A-Z are accepted";
            prop.setLanguageKey("gc.configgui.keyOverrideToggleChest").setRequiresMcRestart(false);
            keyOverrideToggleChest = prop.getString();
            keyOverrideToggleChestI = parseKeyValue(keyOverrideToggleChest);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "Toggle Legs Key", "KEY_H");
            prop.comment = "Leave 'KEY_' value, adding the intended keyboard character to replace the letter. Values 0-9 and A-Z are accepted";
            prop.setLanguageKey("gc.configgui.keyOverrideToggleLegs").setRequiresMcRestart(false);
            keyOverrideToggleLegs = prop.getString();
            keyOverrideToggleLegsI = parseKeyValue(keyOverrideToggleLegs);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "Toggle Boots Key", "KEY_J");
            prop.comment = "Leave 'KEY_' value, adding the intended keyboard character to replace the letter. Values 0-9 and A-Z are accepted";
            prop.setLanguageKey("gc.configgui.keyOverrideToggleBoots").setRequiresMcRestart(false);
            keyOverrideToggleBoots = prop.getString();
            keyOverrideToggleBootsI = parseKeyValue(keyOverrideToggleBoots);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "spacesuit_pos", spacesuit_pos);
            prop.comment = "Set HUD position. (up or top, center, down or bottom)";
            prop.setLanguageKey("gc.configgui.spacesuit_pos");
            spacesuit_pos = prop.getString();
            propOrder.add(prop.getName());  
            
            prop = config.get(Constants.CONFIG_CATEGORY_ENTITIES, "registeredRocket", registeredRocket);
            prop.comment = "Registered rcokets. (Ex. 456 - all tiers in GS, 45 - only 4 and 5 tier.)";
            prop.setLanguageKey("gc.configgui.registeredRocket");
            registeredRocket = prop.getString();
            propOrder.add(prop.getName());  
            
            config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

            if (config.hasChanged())
            {
                config.save();
            }
        }
        catch (final Exception e)
        {
            FMLLog.log(Level.ERROR, e, "GalaxySpace (Core) has a problem loading it's config");
        }
    }
    
    public static boolean getRegisterRocket(int tier)
    {
    	return registeredRocket.contains(Integer.toString(tier));
    }
    
    private static int parseKeyValue(String key)
    {
    	if (key.equals("KEY_A"))
    	{
    		return Keyboard.KEY_A;
    	}
    	else if (key.equals("KEY_B"))
    	{
    		return Keyboard.KEY_B;
    	}
    	else if (key.equals("KEY_C"))
    	{
    		return Keyboard.KEY_C;
    	}
    	else if (key.equals("KEY_D"))
    	{
    		return Keyboard.KEY_D;
    	}
    	else if (key.equals("KEY_E"))
    	{
    		return Keyboard.KEY_E;
    	}
    	else if (key.equals("KEY_F"))
    	{
    		return Keyboard.KEY_F;
    	}
    	else if (key.equals("KEY_G"))
    	{
    		return Keyboard.KEY_G;
    	}
    	else if (key.equals("KEY_H"))
    	{
    		return Keyboard.KEY_H;
    	}
    	else if (key.equals("KEY_I"))
    	{
    		return Keyboard.KEY_I;
    	}
    	else if (key.equals("KEY_J"))
    	{
    		return Keyboard.KEY_J;
    	}
    	else if (key.equals("KEY_K"))
    	{
    		return Keyboard.KEY_K;
    	}
    	else if (key.equals("KEY_L"))
    	{
    		return Keyboard.KEY_L;
    	}
    	else if (key.equals("KEY_M"))
    	{
    		return Keyboard.KEY_M;
    	}
    	else if (key.equals("KEY_N"))
    	{
    		return Keyboard.KEY_N;
    	}
    	else if (key.equals("KEY_O"))
    	{
    		return Keyboard.KEY_O;
    	}
    	else if (key.equals("KEY_P"))
    	{
    		return Keyboard.KEY_P;
    	}
    	else if (key.equals("KEY_Q"))
    	{
    		return Keyboard.KEY_Q;
    	}
    	else if (key.equals("KEY_R"))
    	{
    		return Keyboard.KEY_R;
    	}
    	else if (key.equals("KEY_S"))
    	{
    		return Keyboard.KEY_S;
    	}
    	else if (key.equals("KEY_T"))
    	{
    		return Keyboard.KEY_T;
    	}
    	else if (key.equals("KEY_U"))
    	{
    		return Keyboard.KEY_U;
    	}
    	else if (key.equals("KEY_V"))
    	{
    		return Keyboard.KEY_V;
    	}
    	else if (key.equals("KEY_W"))
    	{
    		return Keyboard.KEY_W;
    	}
    	else if (key.equals("KEY_X"))
    	{
    		return Keyboard.KEY_X;
    	}
    	else if (key.equals("KEY_Y"))
    	{
    		return Keyboard.KEY_Y;
    	}
    	else if (key.equals("KEY_Z"))
    	{
    		return Keyboard.KEY_Z;
    	}
    	else if (key.equals("KEY_1"))
    	{
    		return Keyboard.KEY_1;
    	}
    	else if (key.equals("KEY_2"))
    	{
    		return Keyboard.KEY_2;
    	}
    	else if (key.equals("KEY_3"))
    	{
    		return Keyboard.KEY_3;
    	}
    	else if (key.equals("KEY_4"))
    	{
    		return Keyboard.KEY_4;
    	}
    	else if (key.equals("KEY_5"))
    	{
    		return Keyboard.KEY_5;
    	}
    	else if (key.equals("KEY_6"))
    	{
    		return Keyboard.KEY_6;
    	}
    	else if (key.equals("KEY_7"))
    	{
    		return Keyboard.KEY_7;
    	}
    	else if (key.equals("KEY_8"))
    	{
    		return Keyboard.KEY_8;
    	}
    	else if (key.equals("KEY_9"))
    	{
    		return Keyboard.KEY_9;
    	}
    	else if (key.equals("KEY_0"))
    	{
    		return Keyboard.KEY_0;
    	}
    	
    	GCLog.severe("Failed to parse keyboard key: " + key + "... Use values A-Z or 0-9" );
    	
    	return 0;
    }
}
