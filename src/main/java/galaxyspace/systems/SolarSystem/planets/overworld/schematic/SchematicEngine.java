package galaxyspace.systems.SolarSystem.planets.overworld.schematic;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.core.configs.GSConfigSchematics;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.schematics.GuiSchematicEngine;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.schematics.ContainerSchematicEngine;
import micdoodle8.mods.galacticraft.api.recipe.ISchematicPage;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

public class SchematicEngine implements ISchematicPage
{
    @Override
    public int getPageID()
    {
        return GSConfigSchematics.idSchematicEngine;
    }

    @Override
    public int getGuiID()
    {
        return this.getPageID();
    }

    @Override
    public ItemStack getRequiredItem()
    {
    	 return new ItemStack(GSItems.Schematics, 1, 2);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public GuiScreen getResultScreen(EntityPlayer player, int x, int y, int z)
    {
        return new GuiSchematicEngine(player.inventory, x, y, z);
    }

    @Override
    public Container getResultContainer(EntityPlayer player, int x, int y, int z)
    {
        return new ContainerSchematicEngine(player.inventory, x, y, z);
    }

    @Override
    public int compareTo(ISchematicPage o)
    {
        if (this.getPageID() > o.getPageID())
        {
            return 1;
        }
        else
        {
            return -1;
        }
    }
}
