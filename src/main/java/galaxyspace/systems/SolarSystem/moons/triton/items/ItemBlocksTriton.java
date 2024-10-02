package galaxyspace.systems.SolarSystem.moons.triton.items;

import galaxyspace.systems.SolarSystem.moons.triton.blocks.TritonBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlocksTriton extends ItemBlock 
{
    public ItemBlocksTriton(Block block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    public String getUnlocalizedName(ItemStack is) {
        int metadata = is.getItemDamage();
        if (metadata >= 0 && metadata < TritonBlocks.metadata.length) {
            return "tile." + TritonBlocks.metadata[metadata];
        }
        return super.getUnlocalizedName();
    }
    
}
