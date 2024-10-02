package galaxyspace.systems.ACentauriSystem.planets.proximaB.items;

import galaxyspace.systems.ACentauriSystem.planets.proximaB.blocks.ProximaBBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlocksProximaB extends ItemBlock 
{
    public ItemBlocksProximaB(Block block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    public String getUnlocalizedName(ItemStack is) {
        int metadata = is.getItemDamage();
        if (metadata >= 0 && metadata < ProximaBBlocks.metadata.length) {
            return "tile." + ProximaBBlocks.metadata[metadata];
        }
        return super.getUnlocalizedName();
    }
    
}

