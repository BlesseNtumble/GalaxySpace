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

public class GSConfigDimensions
{
    public static boolean loaded;

    static Configuration config;

    public GSConfigDimensions(File file)
    {
        if (!GSConfigDimensions.loaded)
        {
        	GSConfigDimensions.config = new Configuration(file);
        	GSConfigDimensions.syncConfig(true);
        }
    }
    
    // DIMENSIONS
    public static int dimensionIDMercury;
    public static int dimensionIDVenus;
    public static int dimensionIDCeres;
    public static int dimensionIDPluto;
    public static int dimensionIDKuiperBelt;
    public static int dimensionIDHaumea;
    public static int dimensionIDMakemake;
   // public static int dimensionIDEris;

    public static int dimensionIDPhobos;
    public static int dimensionIDDeimos;
    public static int dimensionIDIo;
    public static int dimensionIDEuropa;
    public static int dimensionIDGanymede;
    public static int dimensionIDCallisto;
    public static int dimensionIDEnceladus;
    public static int dimensionIDTitan;
    public static int dimensionIDOberon;
    public static int dimensionIDMiranda;
    public static int dimensionIDProteus;
    public static int dimensionIDTriton;
    
    public static int dimensionIDVenusOrbit;
    public static int dimensionIDVenusOrbitStatic;
    public static int dimensionIDMarsOrbit;
    public static int dimensionIDMarsOrbitStatic;
       
    
    public static boolean enableMercury;
    public static boolean enableVenus;
    public static boolean enableCeres;
    public static boolean enableKuiperBelt;
    public static boolean enablePluto;
    public static boolean enableHaumea;
    public static boolean enableMakemake;
   // public static boolean enableEris;
    
    public static boolean enablePhobos;
    public static boolean enableDeimos;
    public static boolean enableEuropa;
    public static boolean enableCallisto;
    public static boolean enableIo;
    public static boolean enableGanymede;
    public static boolean enableEnceladus;
    public static boolean enableTitan;
    public static boolean enableOberon;
    public static boolean enableMiranda;
    public static boolean enableProteus;
    public static boolean enableTriton;

    public static boolean enableMarsSS;
    public static boolean enableVenusSS; 
    
    
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

                                
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "dimensionIDMercury", -1005);
            prop.comment = "Dimension ID for Mercury";
            prop.setLanguageKey("gc.configgui.dimensionIDMerucry").setRequiresMcRestart(true);
            dimensionIDMercury = prop.getInt();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "dimensionIDVenus", -1006);
            prop.comment = "Dimension ID for Venus";
            prop.setLanguageKey("gc.configgui.dimensionIDVenus").setRequiresMcRestart(true);
            dimensionIDVenus = prop.getInt();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "dimensionIDCeres", -1007);
            prop.comment = "Dimension ID for Ceres";
            prop.setLanguageKey("gc.configgui.dimensionIDCeres").setRequiresMcRestart(true);
            dimensionIDCeres = prop.getInt();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "dimensionIDPluto", -1008);
            prop.comment = "Dimension ID for Pluto";
            prop.setLanguageKey("gc.configgui.dimensionIDPluto").setRequiresMcRestart(true);
            dimensionIDPluto = prop.getInt();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "dimensionIDKuiperBelt", -1009);
            prop.comment = "Dimension ID for Kuiper Belt";
            prop.setLanguageKey("gc.configgui.dimensionIDKuiperBelt").setRequiresMcRestart(true);
            dimensionIDKuiperBelt = prop.getInt();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "dimensionIDKuiperBelt", -10010);
            prop.comment = "Dimension ID for Kuiper Belt";
            prop.setLanguageKey("gc.configgui.dimensionIDKuiperBelt").setRequiresMcRestart(true);
            dimensionIDKuiperBelt = prop.getInt();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "dimensionIDMakemake", -1011);
            prop.comment = "Dimension ID for Makemake";
            prop.setLanguageKey("gc.configgui.dimensionIDMakemake").setRequiresMcRestart(true);
            dimensionIDMakemake = prop.getInt();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "dimensionIDPhobos", -1012);
            prop.comment = "Dimension ID for Phobos (Mars moon)";
            prop.setLanguageKey("gc.configgui.dimensionIDPhobos").setRequiresMcRestart(true);
            dimensionIDPhobos = prop.getInt();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "dimensionIDDeimos", -1013);
            prop.comment = "Dimension ID for Deimos (Mars moon)";
            prop.setLanguageKey("gc.configgui.dimensionIDDeimos").setRequiresMcRestart(true);
            dimensionIDDeimos = prop.getInt();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "dimensionIDIo", -1014);
            prop.comment = "Dimension ID for Io (Jupiter moon)";
            prop.setLanguageKey("gc.configgui.dimensionIDIo").setRequiresMcRestart(true);
            dimensionIDIo = prop.getInt();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "dimensionIDEuropa", -1015);
            prop.comment = "Dimension ID for Europa (Jupiter moon)";
            prop.setLanguageKey("gc.configgui.dimensionIDEuropa").setRequiresMcRestart(true);
            dimensionIDEuropa = prop.getInt();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "dimensionIDGanymede", -1016);
            prop.comment = "Dimension ID for Ganymede (Jupiter moon)";
            prop.setLanguageKey("gc.configgui.dimensionIDGanymede").setRequiresMcRestart(true);
            dimensionIDGanymede = prop.getInt();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "dimensionIDEnceladus", -1017);
            prop.comment = "Dimension ID for Enceladus (Saturn moon)";
            prop.setLanguageKey("gc.configgui.dimensionIDEnceladus").setRequiresMcRestart(true);
            dimensionIDEnceladus = prop.getInt();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "dimensionIDTitan", -1018);
            prop.comment = "Dimension ID for Titan (Saturn moon)";
            prop.setLanguageKey("gc.configgui.dimensionIDTitan").setRequiresMcRestart(true);
            dimensionIDTitan = prop.getInt();
            propOrder.add(prop.getName());

            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "dimensionIDOberon", -1019);
            prop.comment = "Dimension ID for Oberon (Uranus moon)";
            prop.setLanguageKey("gc.configgui.dimensionIDOberon").setRequiresMcRestart(true);
            dimensionIDOberon = prop.getInt();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "dimensionIDProteus", -1020);
            prop.comment = "Dimension ID for Proteus (Neptune moon)";
            prop.setLanguageKey("gc.configgui.dimensionIDProteus").setRequiresMcRestart(true);
            dimensionIDProteus = prop.getInt();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "dimensionIDTriton", -1021);
            prop.comment = "Dimension ID for Triton (Neptune moon)";
            prop.setLanguageKey("gc.configgui.dimensionIDTriton").setRequiresMcRestart(true);
            dimensionIDTriton = prop.getInt();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "dimensionIDCallisto", -1022);
            prop.comment = "Dimension ID for Callisto (Jupiter Moon)";
            prop.setLanguageKey("gc.configgui.dimensionIDCallisto").setRequiresMcRestart(true);
            dimensionIDCallisto = prop.getInt();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "dimensionIDHaumea", -1023);
            prop.comment = "Dimension ID for Haumea";
            prop.setLanguageKey("gc.configgui.dimensionIDHaumea").setRequiresMcRestart(true);
            dimensionIDHaumea = prop.getInt();
            propOrder.add(prop.getName());
            
            //NOT USE -26, -27, -28, -29, -30
            
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "dimensionIDMiranda", -1024);
            prop.comment = "Dimension ID for Miranda (Uranus moon)";
            prop.setLanguageKey("gc.configgui.dimensionIDMiranda").setRequiresMcRestart(true);
            dimensionIDMiranda = prop.getInt();
            propOrder.add(prop.getName());
            /*
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "dimensionIDEris", -1030);
            prop.comment = "Dimension ID for Eris";
            prop.setLanguageKey("gc.configgui.dimensionIDEris").setRequiresMcRestart(true);
            dimensionIDEris = prop.getInt();
            propOrder.add(prop.getName());
            */
           
            
            // Space Stations -----------------------------------------------------------------
            
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "dimensionIDMarsOrbitStatic", -1040);
            prop.comment = "Dimension ID for Static Mars Space Stations";
            prop.setLanguageKey("gc.configgui.dimensionIDMarsOrbitStatic").setRequiresMcRestart(true);
            dimensionIDMarsOrbitStatic = prop.getInt();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "dimensionIDMarsOrbit", -1041);
            prop.comment = "Dimension ID for Mars Space Station";
            prop.setLanguageKey("gc.configgui.dimensionIDMarsOrbit").setRequiresMcRestart(true);
            dimensionIDMarsOrbit = prop.getInt();
            propOrder.add(prop.getName());

            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "dimensionIDVenusOrbitStatic", -1042);
            prop.comment = "Dimension ID for Static Venus Space Stations";
            prop.setLanguageKey("gc.configgui.dimensionIDVenusOrbitStatic").setRequiresMcRestart(true);
            dimensionIDVenusOrbitStatic = prop.getInt();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "dimensionIDVenusOrbit", -1043);
            prop.comment = "Dimension ID for Venus Space Station";
            prop.setLanguageKey("gc.configgui.dimensionIDVenusOrbit").setRequiresMcRestart(true);
            dimensionIDVenusOrbit = prop.getInt();
            propOrder.add(prop.getName());
            
            //----------------------------------------------------------------------------------
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableMercury", true);
            prop.comment = "Enable/Disable Mercury";
            prop.setLanguageKey("gc.configgui.enableMercury").setRequiresMcRestart(true);
            enableMercury = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableVenus", true);
            prop.comment = "Enable/Disable Venus";
            prop.setLanguageKey("gc.configgui.enableVenus").setRequiresMcRestart(true);
            enableVenus = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableCeres", true);
            prop.comment = "Enable/Disable Ceres";
            prop.setLanguageKey("gc.configgui.enableCeres").setRequiresMcRestart(true);
            enableCeres = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enablePluto", true);
            prop.comment = "Enable/Disable Pluto";
            prop.setLanguageKey("gc.configgui.enablePluto").setRequiresMcRestart(true);
            enablePluto = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableKuiperBelt", true);
            prop.comment = "Enable/Disable Kuiper Belt";
            prop.setLanguageKey("gc.configgui.enableKuiperBelt").setRequiresMcRestart(true);
            enableKuiperBelt = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableHaumea", true);
            prop.comment = "Enable/Disable Haumea";
            prop.setLanguageKey("gc.configgui.enableHaumea").setRequiresMcRestart(true);
            enableHaumea = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableMakemake", true);
            prop.comment = "Enable/Disable Makemake";
            prop.setLanguageKey("gc.configgui.enableMakemake").setRequiresMcRestart(true);
            enableMakemake = prop.getBoolean(true);
            propOrder.add(prop.getName());
            /*
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableEris", true);
            prop.comment = "Enable/Disable Eris";
            prop.setLanguageKey("gc.configgui.enableEris").setRequiresMcRestart(true);
            enableEris = prop.getBoolean(true);
            propOrder.add(prop.getName());
            */
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enablePhobos", true);
            prop.comment = "Enable/Disable Phobos (Mars Moon)";
            prop.setLanguageKey("gc.configgui.enablePhobos").setRequiresMcRestart(true);
            enablePhobos = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableDeimos", true);
            prop.comment = "Enable/Disable Deimos (Mars Moon)";
            prop.setLanguageKey("gc.configgui.enableDeimos").setRequiresMcRestart(true);
            enableDeimos = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableEuropa", true);
            prop.comment = "Enable/Disable Europa (Jupiter Moon)";
            prop.setLanguageKey("gc.configgui.enableEuropa").setRequiresMcRestart(true);
            enableEuropa = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableIo", true);
            prop.comment = "Enable/Disable Io (Jupiter Moon)";
            prop.setLanguageKey("gc.configgui.enableIo").setRequiresMcRestart(true);
            enableIo = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableGanymede", true);
            prop.comment = "Enable/Disable Ganymede (Jupiter Moon)";
            prop.setLanguageKey("gc.configgui.enableGanymede").setRequiresMcRestart(true);
            enableGanymede = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableCallisto", true);
            prop.comment = "Enable/Disable Callisto (Jupiter Moon)";
            prop.setLanguageKey("gc.configgui.enableCallisto").setRequiresMcRestart(true);
            enableCallisto = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableEnceladus", true);
            prop.comment = "Enable/Disable Enceladus (Saturn Moon)";
            prop.setLanguageKey("gc.configgui.enableEnceladus").setRequiresMcRestart(true);
            enableEnceladus = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableTitan", true);
            prop.comment = "Enable/Disable Titan (Saturn Moon)";
            prop.setLanguageKey("gc.configgui.enableTitan").setRequiresMcRestart(true);
            enableTitan = prop.getBoolean(true);
            propOrder.add(prop.getName());
            /*
            config("Miranda", "Uranus Moon", enableMiranda);
            enableMiranda = prop.getBoolean(true);
            */
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableMiranda", true);
            prop.comment = "Enable/Disable Miranda (Uranus Moon)";
            prop.setLanguageKey("gc.configgui.enableMiranda").setRequiresMcRestart(true);
            enableMiranda = prop.getBoolean(true);
            propOrder.add(prop.getName());            
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableOberon", true);
            prop.comment = "Enable/Disable Oberon (Uranus Moon)";
            prop.setLanguageKey("gc.configgui.enableOberon").setRequiresMcRestart(true);
            enableOberon = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableProteus", true);
            prop.comment = "Enable/Disable Proteus (Neptune Moon)";
            prop.setLanguageKey("gc.configgui.enableProteus").setRequiresMcRestart(true);
            enableProteus = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableTriton", true);
            prop.comment = "Enable/Disable Triton (Neptune Moon)";
            prop.setLanguageKey("gc.configgui.enableTriton").setRequiresMcRestart(true);
            enableTriton = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            // Space Stations -----------------------------------------------------------------
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableMarsSS", true);
            prop.comment = "Enable/Disable Mars Space Station";
            prop.setLanguageKey("gc.configgui.enableMarsSS").setRequiresMcRestart(true);
            enableMarsSS = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableVenusSS", true);
            prop.comment = "Enable/Disable Venus Space Station";
            prop.setLanguageKey("gc.configgui.enableVenusSS").setRequiresMcRestart(true);
            enableVenusSS = prop.getBoolean(true);
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
