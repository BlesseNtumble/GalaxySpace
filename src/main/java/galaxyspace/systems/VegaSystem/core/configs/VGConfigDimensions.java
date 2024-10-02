package galaxyspace.systems.VegaSystem.core.configs;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import micdoodle8.mods.galacticraft.core.Constants;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class VGConfigDimensions {

	public static boolean loaded;

    static Configuration config;
    
    

    public VGConfigDimensions(File file)
    {
        if (!VGConfigDimensions.loaded)
        {
        	VGConfigDimensions.config = new Configuration(file);
        	VGConfigDimensions.syncConfig(true);
        }
    }
    
    public static int dimensionIDVegaB;
    public static boolean enableVegaB;
    
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
            
            ///////////////////////////////////////START/////////////////////////////////////////////
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "dimensionIDVegaB", -1029);
            prop.comment = "Dimension ID for Vega B";
            prop.setLanguageKey("gc.configgui.dimensionIDVegaB").setRequiresMcRestart(true);
            dimensionIDVegaB = prop.getInt();
            propOrder.add(prop.getName());
            
            //////////////////////////////////////BOOLEAN////////////////////////////////////////////
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableVegaB", true);
            prop.comment = "Enable/Disable Vega B planet.";
            prop.setLanguageKey("gc.configgui.enableVegaB").setRequiresMcRestart(true);
            enableVegaB = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            ////////////////////////////////////////END//////////////////////////////////////////////
            
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
}
