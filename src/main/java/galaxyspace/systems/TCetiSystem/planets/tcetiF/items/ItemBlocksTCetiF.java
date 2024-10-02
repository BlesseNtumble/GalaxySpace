package galaxyspace.systems.TCetiSystem.planets.tcetiF.items;

import galaxyspace.systems.TCetiSystem.planets.tcetiF.blocks.TCetiFBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlocksTCetiF extends ItemBlock 
{
    public ItemBlocksTCetiF(Block block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    public String getUnlocalizedName(ItemStack is) {
        int metadata = is.getItemDamage();
        if (metadata >= 0 && metadata < TCetiFBlocks.metadata.length) {
            return "tile." + TCetiFBlocks.metadata[metadata];
        }
        return super.getUnlocalizedName();
    }
    
}
