package galaxyspace.core.configs;

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

public class GSConfigSchematics
{
    public static boolean loaded;

    static Configuration config;

    public GSConfigSchematics(File file)
    {
        if (!GSConfigSchematics.loaded)
        {
        	GSConfigSchematics.config = new Configuration(file);
        	GSConfigSchematics.syncConfig(true);
        }
    }

    // DIMENSIONS

    public static int idSchematicCone = 11;    
    public static int idSchematicBody = 12; 
    public static int idSchematicEngine = 13; 
    public static int idSchematicBooster = 14; 
    public static int idSchematicFins = 15; 
    public static int idSchematicOxTank = 16;
    
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
            
            prop = config.get(Constants.CONFIG_CATEGORY_SCHEMATIC, "idSchematicCone", 11);
            prop.setComment("Schematic ID for Cone (Rocket Detail), must be unique.");
            prop.setLanguageKey("gc.configgui.idSchematicCone");
            idSchematicCone = prop.getInt(11);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_SCHEMATIC, "idSchematicBody", 12);
            prop.setComment("Schematic ID for Body (Rocket Detail), must be unique.");
            prop.setLanguageKey("gc.configgui.idSchematicBody");
            idSchematicBody = prop.getInt(12);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_SCHEMATIC, "idSchematicEngine", 13);
            prop.setComment("Schematic ID for Engine (Rocket Detail), must be unique.");
            prop.setLanguageKey("gc.configgui.idSchematicEngine");
            idSchematicEngine = prop.getInt(13);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_SCHEMATIC, "idSchematicBooster", 14);
            prop.setComment("Schematic ID for Booster (Rocket Detail), must be unique.");
            prop.setLanguageKey("gc.configgui.idSchematicBooster");
            idSchematicBooster = prop.getInt(14);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_SCHEMATIC, "idSchematicFins", 15);
            prop.setComment("Schematic ID for Fins (Rocket Detail), must be unique.");
            prop.setLanguageKey("gc.configgui.idSchematicFins");
            idSchematicFins = prop.getInt(15);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_SCHEMATIC, "idSchematicOxTank", 16);
            prop.setComment("Schematic ID for Oxygen PPE Tank, must be unique.");
            prop.setLanguageKey("gc.configgui.idSchematicOxTank");
            idSchematicOxTank = prop.getInt(16);
            propOrder.add(prop.getName());
            
            config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

            if (config.hasChanged())
            {
                config.save();
            }
        }
        catch (final Exception e)
        {
            FMLLog.log(Level.ERROR, e, "GalaxySpace (Schematics) has a problem loading it's config");
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
