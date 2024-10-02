package galaxyspace.core.configs;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import micdoodle8.mods.galacticraft.core.Constants;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class GSConfigBiomes
{
    public static boolean loaded;

    static Configuration config;

    public GSConfigBiomes(File file)
    {
        if (!GSConfigBiomes.loaded)
        {
        	GSConfigBiomes.config = new Configuration(file);
        	GSConfigBiomes.syncConfig(true);
        }
    }

    // BIOMES
   
    public static int IDSpaceBiome = 200;
    public static int IDSpaceShallowWatersBiome = 201;
    public static int IDSpaceOceansBiome = 202;
    public static int IDSpaceDeepOceansBiome = 203;
    public static int IDSpaceLowPlainsBiome = 204;
    public static int IDSpaceMidPlainsBiome = 205;
    public static int IDSpaceLowHillsBiome = 206;
    public static int IDSpaceHighPlateausBiome = 207;
    public static int IDSpaceMidHillsBiome = 208;
    public static int IDSpaceRockyWatersBiome = 209;
    public static int IDSpaceLowIslandsBiome = 210;
    public static int IDSpacePartiallySubmergedBiome = 211;
    public static int IDSpaceBeachBiome = 212;
    
    public static int IDWorldEngineBiome = 213;
    
    public static void syncConfig(boolean load)
    {
        List<String> propOrder = new ArrayList<String>();

        try
        {
            Property prop;

            if (!config.isChild)
            {
                if (load)
                {
                    config.load();
                }
            }
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "IDSpaceBiome", IDSpaceBiome);
            prop.comment = "Global ID Biome for Planets/Moons";
            prop.setLanguageKey("gc.configgui.IDBiomeSpace").setRequiresMcRestart(true);
            IDSpaceBiome = prop.getInt();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "IDSpaceShallowWatersBiome", IDSpaceShallowWatersBiome);
            prop.comment = "Global ID Biome for Planets/Moons (Shallow Waters Biome)";
            prop.setLanguageKey("gc.configgui.IDSpaceShallowWatersBiome").setRequiresMcRestart(true);
            IDSpaceShallowWatersBiome = prop.getInt();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "IDSpaceOceansBiome", IDSpaceOceansBiome);
            prop.comment = "Global ID Biome for Planets/Moons (Oceans Biome)";
            prop.setLanguageKey("gc.configgui.IDSpaceOceansBiome").setRequiresMcRestart(true);
            IDSpaceOceansBiome = prop.getInt();
            propOrder.add(prop.getName());

            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "IDSpaceDeepOceansBiome", IDSpaceDeepOceansBiome);
            prop.comment = "Global ID Biome for Planets/Moons (Deep Oceans Biome)";
            prop.setLanguageKey("gc.configgui.IDSpaceDeepOceansBiome").setRequiresMcRestart(true);
            IDSpaceDeepOceansBiome = prop.getInt();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "IDSpaceLowPlainsBiome", IDSpaceLowPlainsBiome);
            prop.comment = "Global ID Biome for Planets/Moons (Low Plains Biome)";
            prop.setLanguageKey("gc.configgui.IDSpaceLowPlainsBiome").setRequiresMcRestart(true);
            IDSpaceLowPlainsBiome = prop.getInt();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "IDSpaceMidPlainsBiome", IDSpaceMidPlainsBiome);
            prop.comment = "Global ID Biome for Planets/Moons (Mid Plains Biome)";
            prop.setLanguageKey("gc.configgui.IDSpaceMidPlainsBiome").setRequiresMcRestart(true);
            IDSpaceMidPlainsBiome = prop.getInt();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "IDSpaceLowHillsBiome", IDSpaceLowHillsBiome);
            prop.comment = "Global ID Biome for Planets/Moons (Low Hills Biome)";
            prop.setLanguageKey("gc.configgui.IDSpaceLowHillsBiome").setRequiresMcRestart(true);
            IDSpaceLowHillsBiome = prop.getInt();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "IDSpaceHighPlateausBiome", IDSpaceHighPlateausBiome);
            prop.comment = "Global ID Biome for Planets/Moons (High Plateaus Biome)";
            prop.setLanguageKey("gc.configgui.IDSpaceHighPlateausBiome").setRequiresMcRestart(true);
            IDSpaceHighPlateausBiome = prop.getInt();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "IDSpaceMidHillsBiome", IDSpaceMidHillsBiome);
            prop.comment = "Global ID Biome for Planets/Moons (Mid Hills Biome)";
            prop.setLanguageKey("gc.configgui.IDSpaceMidHillsBiome").setRequiresMcRestart(true);
            IDSpaceMidHillsBiome = prop.getInt();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "IDSpaceRockyWatersBiome", IDSpaceRockyWatersBiome);
            prop.comment = "Global ID Biome for Planets/Moons (Rocky Waters Biome)";
            prop.setLanguageKey("gc.configgui.IDSpaceRockyWatersBiome").setRequiresMcRestart(true);
            IDSpaceRockyWatersBiome = prop.getInt();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "IDSpaceLowIslandsBiome", IDSpaceLowIslandsBiome);
            prop.comment = "Global ID Biome for Planets/Moons (Low Islands Biome)";
            prop.setLanguageKey("gc.configgui.IDSpaceLowIslandsBiome").setRequiresMcRestart(true);
            IDSpaceLowIslandsBiome = prop.getInt();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "IDSpacePartiallySubmergedBiome", IDSpacePartiallySubmergedBiome);
            prop.comment = "Global ID Biome for Planets/Moons (Partially Submerged Biome)";
            prop.setLanguageKey("gc.configgui.IDSpacePartiallySubmergedBiome").setRequiresMcRestart(true);
            IDSpacePartiallySubmergedBiome = prop.getInt();
            propOrder.add(prop.getName());

            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "IDSpaceBeachBiome", IDSpaceBeachBiome);
            prop.comment = "Global ID Biome for Planets/Moons (Beach Biome)";
            prop.setLanguageKey("gc.configgui.IDSpaceBeachBiome").setRequiresMcRestart(true);
            IDSpaceBeachBiome = prop.getInt();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "IDWorldEngineBiome", IDWorldEngineBiome);
            prop.comment = "Biome ID for World Engine";
            prop.setLanguageKey("gc.configgui.IDWorldEngineBiome").setRequiresMcRestart(true);
            IDWorldEngineBiome = prop.getInt();
            propOrder.add(prop.getName());

            //-----------------------------------------------------------------------------------

  
            config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

            if (config.hasChanged())
            {
                config.save();
            }
        }
        catch (final Exception e)
        {
            FMLLog.log(Level.ERROR, e, "GalaxySpace (Biomes) has a problem loading it's config");
        }
    }
}
