package galaxyspace.systems.SolarSystem.planets.overworld.schematics;

import galaxyspace.core.GSItems;
import galaxyspace.core.configs.GSConfigSchematics;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.schematics.GuiSchematicFins;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.schematics.ContainerSchematicFins;
import micdoodle8.mods.galacticraft.api.recipe.SchematicPage;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SchematicFins extends SchematicPage
{
    @Override
    public int getPageID()
    {
        return GSConfigSchematics.idSchematicFins;
    }

    @Override
    public int getGuiID()
    {
        return this.getPageID();
    }

    @Override
    public ItemStack getRequiredItem()
    {
    	return new ItemStack(GSItems.SCHEMATICS, 1, 4);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public GuiScreen getResultScreen(EntityPlayer player, BlockPos pos)
    {
        return new GuiSchematicFins(player.inventory, pos);
    }

    @Override
    public Container getResultContainer(EntityPlayer player, BlockPos pos)
    {
        return new ContainerSchematicFins(player.inventory, pos);
    }

    
}
