package galaxyspace.systems.SolarSystem.planets.overworld.schematics;

import galaxyspace.core.configs.GSConfigSchematics;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.schematics.GuiSchematicBooster;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.schematics.ContainerSchematicBooster;
import micdoodle8.mods.galacticraft.api.recipe.SchematicPage;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SchematicBooster extends SchematicPage
{
    @Override
    public int getPageID()
    {
        return GSConfigSchematics.idSchematicBooster;
    }

    @Override
    public int getGuiID()
    {
        return this.getPageID();
    }

    @Override
    public ItemStack getRequiredItem()
    {
    	 return new ItemStack(GSItems.SCHEMATICS, 1, 3);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public GuiScreen getResultScreen(EntityPlayer player, BlockPos pos)
    {
        return new GuiSchematicBooster(player.inventory, pos);
    }

    @Override
    public Container getResultContainer(EntityPlayer player, BlockPos pos)
    {
        return new ContainerSchematicBooster(player.inventory, pos);
    }
}
