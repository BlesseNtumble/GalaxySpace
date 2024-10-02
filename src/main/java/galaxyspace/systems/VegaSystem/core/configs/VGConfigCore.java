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

public class VGConfigCore {

	public static boolean loaded;

    static Configuration config;

    public VGConfigCore(File file)
    {
        if (!VGConfigCore.loaded)
        {
        	VGConfigCore.config = new Configuration(file);
        	VGConfigCore.syncConfig(true);
        }
    }
    
    public static boolean enableVegaSystem;
    
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
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableVegaSystem", true);
            prop.comment = "Enable/Disable Vega system.";
            prop.setLanguageKey("gc.configgui.enableVegaSystem").setRequiresMcRestart(true);
            enableVegaSystem = prop.getBoolean(true);
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
}
