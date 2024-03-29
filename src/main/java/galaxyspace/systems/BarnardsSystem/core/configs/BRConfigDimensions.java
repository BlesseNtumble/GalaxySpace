package galaxyspace.systems.BarnardsSystem.core.configs;

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

public class BRConfigDimensions {
    public static boolean loaded;

    public static Configuration config;

    public BRConfigDimensions(File file)
    {
        if (!BRConfigDimensions.loaded)
        {
        	BRConfigDimensions.config = new Configuration(file);
        	BRConfigDimensions.syncConfig(true);
        }
    }
    
    // DIMENSIONS
    public static int dimensionIDBarnardaC;
    public static int dimensionIDBarnardaC1;
    public static int dimensionIDBarnardaC2;
    public static boolean enableBarnardaC;
    public static boolean enableBarnardaC1;
    public static boolean enableBarnardaC2;

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
            
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "dimensionIDBarnardaC", -1030);
            prop.setComment("Dimension ID for Barnarda C");
            prop.setLanguageKey("gc.configgui.dimensionIDBarnardaC").setRequiresMcRestart(true);
            dimensionIDBarnardaC = prop.getInt();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "dimensionIDBarnardaC1", -1031);
            prop.setComment("Dimension ID for Barnarda C1 (Moon)");
            prop.setLanguageKey("gc.configgui.dimensionIDBarnardaC1").setRequiresMcRestart(true);
            dimensionIDBarnardaC1 = prop.getInt();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "dimensionIDBarnardaC2", -1032);
            prop.setComment("Dimension ID for Barnarda C2 (Moon)");
            prop.setLanguageKey("gc.configgui.dimensionIDBarnardaC2").setRequiresMcRestart(true);
            dimensionIDBarnardaC2 = prop.getInt();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableBarnardaC", true);
            prop.setComment("Enable/Disable Barnarda C planet.");
            prop.setLanguageKey("gc.configgui.enableBarnardaC").setRequiresMcRestart(true);
            enableBarnardaC = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableBarnardaC1", true);
            prop.setComment("Enable/Disable Barnarda C1 moon.");
            prop.setLanguageKey("gc.configgui.enableBarnardaC1").setRequiresMcRestart(true);
            enableBarnardaC1 = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableBarnardaC2", true);
            prop.setComment("Enable/Disable Barnarda C2 moon.");
            prop.setLanguageKey("gc.configgui.enableBarnardaC2").setRequiresMcRestart(true);
            enableBarnardaC2 = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

            if (config.hasChanged())
            {
                config.save();
            }
        }
        catch (final Exception e)
        {
            FMLLog.log(Level.ERROR, e, "GalaxySpace (Barnards Module) has a problem loading it's config");
        }
    }
    
    public static List<IConfigElement> getConfigElements()
    {
        List<IConfigElement> list = new ArrayList<IConfigElement>();
        list.addAll(new ConfigElement(config.getCategory(Constants.CONFIG_CATEGORY_GENERAL)).getChildElements());       
        list.addAll(new ConfigElement(config.getCategory(Constants.CONFIG_CATEGORY_DIMENSIONS)).getChildElements());

        return list;
    }
}
