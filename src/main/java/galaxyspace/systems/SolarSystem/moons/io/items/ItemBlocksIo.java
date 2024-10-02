package galaxyspace.systems.SolarSystem.moons.io.items;

import galaxyspace.systems.SolarSystem.moons.io.blocks.IoBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlocksIo extends ItemBlock 
{
    public ItemBlocksIo(Block block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    public String getUnlocalizedName(ItemStack is) {
        int metadata = is.getItemDamage();
        if (metadata >= 0 && metadata < IoBlocks.metadata.length) {
            return "tile." + IoBlocks.metadata[metadata];
        }
        return super.getUnlocalizedName();
    }
    
}
