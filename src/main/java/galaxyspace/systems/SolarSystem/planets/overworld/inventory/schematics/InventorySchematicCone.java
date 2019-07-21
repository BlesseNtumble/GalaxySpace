package galaxyspace.systems.SolarSystem.planets.overworld.inventory.schematics;

import micdoodle8.mods.galacticraft.core.inventory.InventorySchematic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class InventorySchematicCone extends InventorySchematic
{
	private final int size = 30;

    public InventorySchematicCone(Container container)
    {
    	super(container);
        this.stacks = NonNullList.withSize(size, ItemStack.EMPTY);
    }

    @Override
    public String getName()
    {
        return "container.crafting";
    }
   
    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return true;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack)
    {
        return false;
    }
}
