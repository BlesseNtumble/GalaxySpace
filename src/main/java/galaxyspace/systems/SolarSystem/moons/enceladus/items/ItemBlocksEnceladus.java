package galaxyspace.systems.SolarSystem.moons.enceladus.items;

import galaxyspace.systems.SolarSystem.moons.enceladus.blocks.EnceladusBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlocksEnceladus extends ItemBlock 
{
    public ItemBlocksEnceladus(Block block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    public String getUnlocalizedName(ItemStack is) {
        int metadata = is.getItemDamage();
        if (metadata >= 0 && metadata < EnceladusBlocks.metadata.length) {
            return "tile." + EnceladusBlocks.metadata[metadata];
        }
        return super.getUnlocalizedName();
    }
    
}
