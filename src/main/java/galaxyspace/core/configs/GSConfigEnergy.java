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

public class GSConfigEnergy
{
    public static boolean loaded;

    public static Configuration config;

    public GSConfigEnergy(File file)
    {
        if (!GSConfigEnergy.loaded)
        {
        	GSConfigEnergy.config = new Configuration(file);
        	GSConfigEnergy.syncConfig(true);
        }
    }

    public static float coefficientFuelGenerator = 1.0F;
    public static float coefficientThermodynamicGenerator = 1;
    public static float coefficientSolarWindPanel = 1;
    public static float coefficientSolarPanel = 1;
    public static float coefficientWindTurbine = 1;
    public static float coefficientSpeedRocketAssembly = 1;
    public static float coefficientGasGenerator = 1.0F;
    
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

            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "coefficientGasGenerator", coefficientGasGenerator);
            prop.setComment("Gaseous Generator power factor");
            prop.setLanguageKey("gc.configgui.coefficientGasGenerator").setRequiresMcRestart(false);
            coefficientGasGenerator = (float) prop.getDouble();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "coefficientFuelGenerator", coefficientFuelGenerator);
            prop.setComment("Fuel Generator power factor");
            prop.setLanguageKey("gc.configgui.coefficientFuelGenerator").setRequiresMcRestart(false);
            coefficientFuelGenerator = (float) prop.getDouble();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "coefficientThermodynamicGenerator", coefficientThermodynamicGenerator);
            prop.setComment("Thermodynamic Generator power factor");
            prop.setLanguageKey("gc.configgui.coefficientThermodynamicGenerator").setRequiresMcRestart(false);
            coefficientThermodynamicGenerator = (float) prop.getDouble();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "coefficientSolarWindPanel", coefficientSolarWindPanel);
            prop.setComment("Solar Wind Panel power factor");
            prop.setLanguageKey("gc.configgui.coefficientSolarWindPanel").setRequiresMcRestart(false);
            coefficientSolarWindPanel = (float) prop.getDouble();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "coefficientSolarPanel", coefficientSolarPanel);
            prop.setComment("Gybrid Solar Panel power factor");
            prop.setLanguageKey("gc.configgui.coefficientSolarPanel").setRequiresMcRestart(false);
            coefficientSolarPanel = (float) prop.getDouble();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "coefficientWindTurbine", coefficientWindTurbine);
            prop.setComment("Wind Turbine power factor");
            prop.setLanguageKey("gc.configgui.coefficientWindTurbine").setRequiresMcRestart(false);
            coefficientWindTurbine = (float) prop.getDouble();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "coefficientSpeedRocketAssembly", coefficientSpeedRocketAssembly);
            prop.setComment("Rocket Assembly craft speed factor");
            prop.setLanguageKey("gc.configgui.coefficientSpeedRocketAssembly").setRequiresMcRestart(false);
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
