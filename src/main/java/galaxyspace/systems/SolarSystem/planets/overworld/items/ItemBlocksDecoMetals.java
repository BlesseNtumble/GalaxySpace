package galaxyspace.systems.SolarSystem.planets.overworld.items;

import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockDecoMetals;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlocksDecoMetals extends ItemBlock 
{
    public ItemBlocksDecoMetals(Block block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    public String getUnlocalizedName(ItemStack is) {
        int metadata = is.getItemDamage();
        if (metadata >= 0 && metadata < BlockDecoMetals.metadata.length) {
            return "tile." + BlockDecoMetals.metadata[metadata];
        }
        return super.getUnlocalizedName();
    }
    
}
