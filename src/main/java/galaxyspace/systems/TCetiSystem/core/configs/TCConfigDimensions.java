package galaxyspace.systems.TCetiSystem.core.configs;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import micdoodle8.mods.galacticraft.core.Constants;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class TCConfigDimensions {

	public static boolean loaded;

    static Configuration config;
    
    

    public TCConfigDimensions(File file)
    {
        if (!TCConfigDimensions.loaded)
        {
        	TCConfigDimensions.config = new Configuration(file);
        	TCConfigDimensions.syncConfig(true);
        }
    }
    
    public static int dimensionIDTauCetiF;
    public static boolean enableTauCetiF;
    
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
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "dimensionIDTauCetiF", -1028);
            prop.comment = "Dimension ID for Tau Ceti F";
            prop.setLanguageKey("gc.configgui.dimensionIDTauCetiF").setRequiresMcRestart(true);
            dimensionIDTauCetiF = prop.getInt();
            propOrder.add(prop.getName());
            
            //////////////////////////////////////BOOLEAN////////////////////////////////////////////
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableTauCetiF", true);
            prop.comment = "Enable/Disable Tau Ceti F planet.";
            prop.setLanguageKey("gc.configgui.enableTauCetiF").setRequiresMcRestart(true);
            enableTauCetiF = prop.getBoolean(true);
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
