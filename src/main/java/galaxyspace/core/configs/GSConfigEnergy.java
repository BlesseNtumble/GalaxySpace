package galaxyspace.core.configs;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import micdoodle8.mods.galacticraft.core.Constants;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class GSConfigEnergy
{
    public static boolean loaded;

    static Configuration config;

    public GSConfigEnergy(File file)
    {
        if (!GSConfigEnergy.loaded)
        {
        	GSConfigEnergy.config = new Configuration(file);
        	GSConfigEnergy.syncConfig(true);
        }
    }

    public static float coefficientFuelGenerator = 1.0F;
    public static float coefficientNuclearGenerator = 1;
    public static float coefficientSolarWindPanel = 1;
    public static float coefficientSolarPanel = 1;
    public static float coefficientWindTurbine = 1;
    public static float coefficientSpeedRocketAssembly = 1;
    
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

            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "coefficientFuelGenerator", coefficientFuelGenerator);
            prop.comment = "Fuel Generator power factor";
            prop.setLanguageKey("gc.configgui.coefficientFuelGenerator").setRequiresMcRestart(true);
            coefficientFuelGenerator = (float) prop.getDouble();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "coefficientNuclearGenerator", coefficientNuclearGenerator);
            prop.comment = "Nuclear Generator power factor";
            prop.setLanguageKey("gc.configgui.coefficientNuclearGenerator").setRequiresMcRestart(true);
            coefficientNuclearGenerator = (float) prop.getDouble();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "coefficientSolarWindPanel", coefficientSolarWindPanel);
            prop.comment = "Solar Wind Panel power factor";
            prop.setLanguageKey("gc.configgui.coefficientSolarWindPanel").setRequiresMcRestart(true);
            coefficientSolarWindPanel = (float) prop.getDouble();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "coefficientSolarPanel", coefficientSolarPanel);
            prop.comment = "Gybrid Solar Panel power factor";
            prop.setLanguageKey("gc.configgui.coefficientSolarPanel").setRequiresMcRestart(true);
            coefficientSolarPanel = (float) prop.getDouble();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "coefficientWindTurbine", coefficientWindTurbine);
            prop.comment = "Wind Turbine power factor";
            prop.setLanguageKey("gc.configgui.coefficientWindTurbine").setRequiresMcRestart(true);
            coefficientWindTurbine = (float) prop.getDouble();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "coefficientSpeedRocketAssembly", coefficientSpeedRocketAssembly);
            prop.comment = "Rocket Assembly craft speed factor";
            prop.setLanguageKey("gc.configgui.coefficientSpeedRocketAssembly").setRequiresMcRestart(true);
            coefficientSpeedRocketAssembly = (float) prop.getDouble();
            propOrder.add(prop.getName());
            
            config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

            if (config.hasChanged())
            {
                config.save();
            }
        }
        catch (final Exception e)
        {
            FMLLog.log(Level.ERROR, e, "GalaxySpace (Energy) has a problem loading it's config");
        }
    }
}
