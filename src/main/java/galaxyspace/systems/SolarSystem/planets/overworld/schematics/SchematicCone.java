package galaxyspace.systems.SolarSystem.planets.overworld.schematics;

import galaxyspace.core.GSItems;
import galaxyspace.core.configs.GSConfigSchematics;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.schematics.GuiSchematicCone;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.schematics.ContainerSchematicCone;
import micdoodle8.mods.galacticraft.api.recipe.SchematicPage;
import micdoodle8.mods.galacticraft.core.Constants;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SchematicCone extends SchematicPage
{
    @Override
    public int getPageID()
    {
        return GSConfigSchematics.idSchematicCone;
    }

    @Override
    public int getGuiID()
    {
        return this.getPageID() + Constants.MOD_ID_PLANETS.hashCode();
    }

    @Override
    public ItemStack getRequiredItem()
    {
        return new ItemStack(GSItems.SCHEMATICS, 1, 0);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public GuiScreen getResultScreen(EntityPlayer player, BlockPos pos)
    {
        return new GuiSchematicCone(player.inventory, pos);
    }

    @Override
    public Container getResultContainer(EntityPlayer player, BlockPos pos)
    {
        return new ContainerSchematicCone(player.inventory, pos);
    }    
}
