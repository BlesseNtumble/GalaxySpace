package galaxyspace.systems.SolarSystem.planets.overworld.schematics;

import galaxyspace.core.registers.items.GSItems;
import galaxyspace.systems.SolarSystem.planets.overworld.gui.schematics.GuiSchematicOxTank;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.schematics.ContainerSchematicOxTank;
import micdoodle8.mods.galacticraft.api.recipe.SchematicPage;
import micdoodle8.mods.galacticraft.planets.asteroids.ConfigManagerAsteroids;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SchematicOxTank extends SchematicPage
{
    @Override
    public int getPageID()
    {
        return ConfigManagerAsteroids.idSchematicRocketT3;//GSConfigSchematics.idSchematicOxTank;
    }

    @Override
    public int getGuiID()
    {
        return this.getPageID();
    }

    @Override
    public ItemStack getRequiredItem()
    {
    	return new ItemStack(GSItems.SCHEMATICS, 1, 5);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public GuiScreen getResultScreen(EntityPlayer player, BlockPos pos)
    {
        return new GuiSchematicOxTank(player.inventory, pos);
    }

    @Override
    public Container getResultContainer(EntityPlayer player, BlockPos pos)
    {
        return new ContainerSchematicOxTank(player.inventory, pos);
    }   
}
