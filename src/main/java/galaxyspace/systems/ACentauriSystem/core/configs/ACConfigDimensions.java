package galaxyspace.systems.ACentauriSystem.core.configs;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;

import micdoodle8.mods.galacticraft.core.Constants;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.common.FMLLog;

public class ACConfigDimensions {
    public static boolean loaded;

    public static Configuration config;

    public ACConfigDimensions(File file)
    {
        if (!ACConfigDimensions.loaded)
        {
        	ACConfigDimensions.config = new Configuration(file);
        	ACConfigDimensions.syncConfig(true);
        }
    }
    
    // DIMENSIONS
    public static int dimensionIDProxima_B;

    
    public static boolean enableProxima_B;
    public static boolean enableProxima_C;
    public static boolean enableProxima_D;

    
    
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

                                
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "dimensionIDProxima_B", -1025);
            prop.setComment("Dimension ID for Proxima B");
            prop.setLanguageKey("gc.configgui.dimensionIDProxima_B").setRequiresMcRestart(true);
            dimensionIDProxima_B = prop.getInt();
            propOrder.add(prop.getName());
            
       
            // Space Stations -----------------------------------------------------------------
            
         
            //----------------------------------------------------------------------------------
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableProxima_B", true);
            prop.setComment("Enable/Disable Proxima B");
            prop.setLanguageKey("gc.configgui.enableProxima_B").setRequiresMcRestart(true);
            enableProxima_B = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableProxima_C", true);
            prop.setComment("Enable/Disable Proxima C");
            prop.setLanguageKey("gc.configgui.enableProxima_C").setRequiresMcRestart(true);
            enableProxima_C = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableProxima_D", true);
            prop.setComment("Enable/Disable Proxima D");
            prop.setLanguageKey("gc.configgui.enableProxima_D").setRequiresMcRestart(true);
            enableProxima_D = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
                                
            config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

            if (config.hasChanged())
            {
                config.save();
            }
        }
        catch (final Exception e)
        {
            FMLLog.log(Level.ERROR, e, "GalaxySpace has a problem loading it's config");
        }
    }  
    
    public static List<IConfigElement> getConfigElements()
    {
        List<IConfigElement> list = new ArrayList<IConfigElement>();
        list.addAll(new ConfigElement(config.getCategory(Constants.CONFIG_CATEGORY_DIFFICULTY)).getChildElements());
        list.addAll(new ConfigElement(config.getCategory(Constants.CONFIG_CATEGORY_GENERAL)).getChildElements());
        list.addAll(new ConfigElement(config.getCategory(Constants.CONFIG_CATEGORY_CLIENT)).getChildElements());
        list.addAll(new ConfigElement(config.getCategory(Constants.CONFIG_CATEGORY_CONTROLS)).getChildElements());
        list.addAll(new ConfigElement(config.getCategory(Constants.CONFIG_CATEGORY_COMPATIBILITY)).getChildElements());
        list.addAll(new ConfigElement(config.getCategory(Constants.CONFIG_CATEGORY_WORLDGEN)).getChildElements());
        list.addAll(new ConfigElement(config.getCategory(Constants.CONFIG_CATEGORY_SERVER)).getChildElements());
        list.addAll(new ConfigElement(config.getCategory(Constants.CONFIG_CATEGORY_DIMENSIONS)).getChildElements());
        list.addAll(new ConfigElement(config.getCategory(Constants.CONFIG_CATEGORY_SCHEMATIC)).getChildElements());
        list.addAll(new ConfigElement(config.getCategory(Constants.CONFIG_CATEGORY_ACHIEVEMENTS)).getChildElements());
        list.addAll(new ConfigElement(config.getCategory(Constants.CONFIG_CATEGORY_ENTITIES)).getChildElements());

        return list;
    }

}
