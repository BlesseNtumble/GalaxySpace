package galaxyspace.systems.TauCetiSystem.core.configs;

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
    
    public static boolean enableTauCetiSystems;

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
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableTauCetiSystems", true);
            prop.setComment("Enable/Disable Tau Ceti System.");
            prop.setLanguageKey("gc.configgui.enableTauCetiSystems").setRequiresMcRestart(true);
            enableTauCetiSystems = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

            if (config.hasChanged())
            {
                config.save();
            }
        }
        catch (final Exception e)
        {
            FMLLog.log(Level.ERROR, e, "GalaxySpace (Tau Ceti Module) has a problem loading it's config");
        }
    }
    
    public static List<IConfigElement> getConfigElements()
    {
        List<IConfigElement> list = new ArrayList<IConfigElement>();
        list.addAll(new ConfigElement(config.getCategory(Constants.CONFIG_CATEGORY_GENERAL)).getChildElements());

        return list;
    }
}
