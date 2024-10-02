package galaxyspace.systems.SolarSystem.moons.europa.items;

import galaxyspace.systems.SolarSystem.moons.europa.blocks.EuropaBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlocksEuropa extends ItemBlock 
{
    public ItemBlocksEuropa(Block block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    public String getUnlocalizedName(ItemStack is) {
        int metadata = is.getItemDamage();
        if (metadata >= 0 && metadata < EuropaBlocks.metadata.length) {
            return "tile." + EuropaBlocks.metadata[metadata];
        }
        return super.getUnlocalizedName();
    }
    
}
