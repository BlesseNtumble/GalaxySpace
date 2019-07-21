package galaxyspace.systems.SolarSystem.planets.overworld.inventory.schematics;

import micdoodle8.mods.galacticraft.core.inventory.InventorySchematic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class InventorySchematicTier2Rocket extends InventorySchematic
{
    private final int size = 26;	

    public InventorySchematicTier2Rocket(Container container)
    {
    	super(container);
        this.stacks = NonNullList.withSize(size, ItemStack.EMPTY);
    }

    @Override
    public ItemStack getStackInSlot(int par1)
    {
        return par1 >= this.getSizeInventory() ? ItemStack.EMPTY : this.stacks.get(par1);
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
