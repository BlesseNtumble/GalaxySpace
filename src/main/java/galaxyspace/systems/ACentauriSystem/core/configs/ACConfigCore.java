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

public class ACConfigCore {

	public static boolean loaded;

    static Configuration config;

    public ACConfigCore(File file)
    {
        if (!ACConfigCore.loaded)
        {
        	ACConfigCore.config = new Configuration(file);
        	ACConfigCore.syncConfig(true);
        }
    }
    
    public static boolean enableAlphaCentauriSystem;
    
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
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableAlphaCentauriSystem", true);
            prop.comment = "Enable/Disable Alpha Centauri and Proxima Centauri Systems.";
            prop.setLanguageKey("gc.configgui.enableAlphaCentauriSystem").setRequiresMcRestart(true);
            enableAlphaCentauriSystem = prop.getBoolean(true);
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
