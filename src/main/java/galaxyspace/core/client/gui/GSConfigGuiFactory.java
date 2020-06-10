package galaxyspace.core.client.gui;

import java.util.Set;

import galaxyspace.GalaxySpace;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.configs.GSConfigDimensions;
import galaxyspace.core.configs.GSConfigEnergy;
import galaxyspace.core.configs.GSConfigSchematics;
import galaxyspace.systems.ACentauriSystem.core.configs.ACConfigCore;
import galaxyspace.systems.ACentauriSystem.core.configs.ACConfigDimensions;
import galaxyspace.systems.BarnardsSystem.core.configs.BRConfigCore;
import galaxyspace.systems.BarnardsSystem.core.configs.BRConfigDimensions;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;

public class GSConfigGuiFactory implements IModGuiFactory
{
    public static class CoreConfigGUI extends GuiConfig
    {
    	    	
        public CoreConfigGUI(GuiScreen parent)
        {        	
            super(parent, GalaxySpace.MODID, GCCoreUtil.translate("gs.configgui.title"));
            this.configElements.addAll(GSConfigCore.getConfigElements());
            this.configElements.addAll(GSConfigDimensions.getConfigElements());
            this.configElements.addAll(GSConfigSchematics.getConfigElements());
            this.configElements.addAll(ACConfigDimensions.getConfigElements());
            this.configElements.addAll(BRConfigDimensions.getConfigElements());
            this.configElements.addAll(GSConfigEnergy.getConfigElements());
            this.configElements.addAll(ACConfigCore.getConfigElements());
            this.configElements.addAll(BRConfigCore.getConfigElements());
        }
    }

    @Override
    public void initialize(Minecraft minecraftInstance)
    {

    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
    {
        return null;
    }

	public GuiScreen createConfigGui(GuiScreen arg0)
	{
		// TODO  Forge 2282 addition!
		return new CoreConfigGUI(arg0);
	}

	public boolean hasConfigGui()
	{		
		return true;
	}
}
