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

public class TCConfigCore {

	public static boolean loaded;

    static Configuration config;

    public TCConfigCore(File file)
    {
        if (!TCConfigCore.loaded)
        {
        	TCConfigCore.config = new Configuration(file);
        	TCConfigCore.syncConfig(true);
        }
    }
    
    public static boolean enableTauCetiSystem;
    
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
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableTauCetiSystem", true);
            prop.comment = "Enable/Disable Tau Ceti system.";
            prop.setLanguageKey("gc.configgui.enableAlphaCentauriSystem").setRequiresMcRestart(true);
            enableTauCetiSystem = prop.getBoolean(true);
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
