package galaxyspace.systems.ACentauriSystem.core.configs;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import micdoodle8.mods.galacticraft.core.Constants;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ACConfigDimensions {

	public static boolean loaded;

    static Configuration config;
    
    

    public ACConfigDimensions(File file)
    {
        if (!ACConfigDimensions.loaded)
        {
        	ACConfigDimensions.config = new Configuration(file);
        	ACConfigDimensions.syncConfig(true);
        }
    }
    
    public static int dimensionIDProximaB;
    public static boolean enableProximaB;
    
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
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "dimensionIDProximaB", -1025);
            prop.comment = "Dimension ID for Proxima B";
            prop.setLanguageKey("gc.configgui.dimensionIDProximaB").setRequiresMcRestart(true);
            dimensionIDProximaB = prop.getInt();
            propOrder.add(prop.getName());
            
            //////////////////////////////////////BOOLEAN////////////////////////////////////////////
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableProximaB", true);
            prop.comment = "Enable/Disable Proxima B planet.";
            prop.setLanguageKey("gc.configgui.enableProximaB").setRequiresMcRestart(true);
            enableProximaB = prop.getBoolean(true);
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
