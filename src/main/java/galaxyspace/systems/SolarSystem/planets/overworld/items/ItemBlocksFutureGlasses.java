package galaxyspace.systems.SolarSystem.planets.overworld.items;

import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockFutureGlasses;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlocksFutureGlasses extends ItemBlock 
{
    public ItemBlocksFutureGlasses(Block block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    public String getUnlocalizedName(ItemStack is) {
        int metadata = is.getItemDamage();
        if (metadata >= 0 && metadata < BlockFutureGlasses.metadata.length) {
            return "tile." + BlockFutureGlasses.metadata[metadata];
        }
        return super.getUnlocalizedName();
    }
    
}
