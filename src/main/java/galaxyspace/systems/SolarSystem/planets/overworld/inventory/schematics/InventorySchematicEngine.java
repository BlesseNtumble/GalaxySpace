package galaxyspace.systems.SolarSystem.planets.overworld.inventory.schematics;

import micdoodle8.mods.galacticraft.core.inventory.InventorySchematic;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class InventorySchematicEngine extends InventorySchematic
{
	private final int size = 30;
	
    public InventorySchematicEngine(Container container)
    {
    	super(container);
        this.stacks = NonNullList.withSize(size, ItemStack.EMPTY);
       
    }

}
