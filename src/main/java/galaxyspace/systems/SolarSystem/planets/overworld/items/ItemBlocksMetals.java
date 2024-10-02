package galaxyspace.systems.SolarSystem.planets.overworld.items;

import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockMetals;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlocksMetals extends ItemBlock 
{
    public ItemBlocksMetals(Block block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    public String getUnlocalizedName(ItemStack is) {
        int metadata = is.getItemDamage();
        if (metadata >= 0 && metadata < BlockMetals.metadata.length) {
            return "tile." + BlockMetals.metadata[metadata];
        }
        return super.getUnlocalizedName();
    }
    
}
