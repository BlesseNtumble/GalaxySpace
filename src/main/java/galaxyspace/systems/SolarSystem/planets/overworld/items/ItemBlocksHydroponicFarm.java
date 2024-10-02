package galaxyspace.systems.SolarSystem.planets.overworld.items;

import galaxyspace.core.registers.items.GSItemBlockDesc;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlocksHydroponicFarm extends GSItemBlockDesc 
{
    public ItemBlocksHydroponicFarm(Block block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    public String getUnlocalizedName(ItemStack is) {
        int metadata = is.getItemDamage();
        return super.getUnlocalizedName();
    }
    
}
