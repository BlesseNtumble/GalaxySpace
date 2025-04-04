package galaxyspace.core.configs;

import micdoodle8.mods.galacticraft.core.Constants;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.FMLLog;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

public class GSConfigWorld {

    public static Configuration config;



    public static boolean enableOresGeneration;
    public static boolean enableMarsNewOres;
    public static boolean enableOverworldOres;
    public static boolean enableNatureGasGen;

    public static String[] OREGEN_SETTINGS;

    public GSConfigWorld(File file)
    {
        GSConfigWorld.config = new Configuration(file);
        GSConfigWorld.syncConfig(true);
    }

    public static void syncConfig(boolean load)
    {
        List<String> propOrder = new ArrayList<String>();

        try {
            propOrder.clear();
            Property prop;
            if (!config.isChild) {
                if (load) {
                    config.load();
                }
            }

            prop = config.get(Constants.CONFIG_CATEGORY_WORLDGEN, "enableOverworldOres", true);
            prop.setComment("Enable/Disable Generation Ores on Overworld.");
            prop.setLanguageKey("gc.configgui.enableOverworldOres").setRequiresMcRestart(false);
            enableOverworldOres = prop.getBoolean(true);
            propOrder.add(prop.getName());

            prop = config.get(Constants.CONFIG_CATEGORY_WORLDGEN, "enableOresGeneration", true);
            prop.setComment("Enable/Disable Generation Ores on Planets/Moon (Global Config).");
            prop.setLanguageKey("gc.configgui.enableOresGeneration").setRequiresMcRestart(false);
            enableOresGeneration = prop.getBoolean(true);
            propOrder.add(prop.getName());

            prop = config.get(Constants.CONFIG_CATEGORY_WORLDGEN, "enableNatureGasGen", true);
            prop.setComment("Enable/Disable Generation Nature Gas on Overworld.");
            prop.setLanguageKey("gc.configgui.enableNatureGasGen").setRequiresMcRestart(false);
            enableNatureGasGen = prop.getBoolean(true);
            propOrder.add(prop.getName());

            prop = config.get(Constants.CONFIG_CATEGORY_WORLDGEN, "enableMarsNewOres", true);
            prop.setComment("Enable/Disable New Mars oregen (diamonds, coal, gold, etc).");
            prop.setLanguageKey("gc.configgui.enableMarsNewOres").setRequiresMcRestart(true);
            enableMarsNewOres = prop.getBoolean(true);
            propOrder.add(prop.getName());

            prop = config.get(Constants.CONFIG_CATEGORY_WORLDGEN, "oreGenSettings",
                    new String[]
                            {
                                    "-31:minecraft:ice:0:minecraft:ice:0:1:1:0:100"
                            });

            prop.setComment("!!BETA TEST!! Add custom oregen in celestial bodies (exclude Overworld, Asteroids.)  Example: dimensionid:modid:block:meta:replacemodid:replaceblock:replacemeta:veinsize:amountperchunk:minY:maxY");
            prop.setLanguageKey("gc.configgui.oreGenSettings").setRequiresMcRestart(true);
            OREGEN_SETTINGS = prop.getStringList();
            propOrder.add(prop.getName());

            config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);
            config.save();

        }
        catch (final Exception e)
        {
            FMLLog.log(Level.ERROR, e, "GalaxySpace (Core) has a problem loading it's config");
        }
    }
}
