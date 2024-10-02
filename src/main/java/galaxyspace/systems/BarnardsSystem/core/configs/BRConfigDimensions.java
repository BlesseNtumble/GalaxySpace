package galaxyspace.systems.BarnardsSystem.core.configs;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import micdoodle8.mods.galacticraft.core.Constants;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class BRConfigDimensions 
{
    public static boolean loaded;

    static Configuration config;

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
      
    public static boolean enableBarnardaC;
   
    
    
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

                                
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "dimensionIDBarnarndaC", -1050);
            prop.comment = "Dimension ID for Barnarnda C";
            prop.setLanguageKey("gc.configgui.dimensionIDBarnarndaC").setRequiresMcRestart(true);
            dimensionIDBarnardaC = prop.getInt();
            propOrder.add(prop.getName());            
         
            //----------------------------------------------------------------------------------
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableBarnardaC", true);
            prop.comment = "Enable/Disable Barnarda C";
            prop.setLanguageKey("gc.configgui.enableBarnardaC").setRequiresMcRestart(true);
            enableBarnardaC = prop.getBoolean(true);
            propOrder.add(prop.getName());
          
            //----------------------------------------------------------------------------------
             
            config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

            if (config.hasChanged())
            {
                config.save();
            }
        }
        catch (final Exception e)
        {
            FMLLog.log(Level.ERROR, e, "GalaxySpace (Planets) has a problem loading it's config");
        }
    }  

}
