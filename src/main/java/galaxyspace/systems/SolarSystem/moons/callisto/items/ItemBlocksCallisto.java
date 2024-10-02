package galaxyspace.systems.SolarSystem.moons.callisto.items;

import galaxyspace.systems.SolarSystem.moons.callisto.blocks.CallistoBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlocksCallisto extends ItemBlock 
{
    public ItemBlocksCallisto(Block block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    public String getUnlocalizedName(ItemStack is) {
        int metadata = is.getItemDamage();
        if (metadata >= 0 && metadata < CallistoBlocks.metadata.length) {
            return "tile." + CallistoBlocks.metadata[metadata];
        }
        return super.getUnlocalizedName();
    }
    
}
