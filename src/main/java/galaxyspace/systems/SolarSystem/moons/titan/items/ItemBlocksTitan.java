package galaxyspace.systems.SolarSystem.moons.titan.items;

import galaxyspace.systems.SolarSystem.moons.titan.blocks.TitanBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlocksTitan extends ItemBlock 
{
    public ItemBlocksTitan(Block block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    public String getUnlocalizedName(ItemStack is) {
        int metadata = is.getItemDamage();
        if (metadata >= 0 && metadata < TitanBlocks.metadata.length) {
            return "tile." + TitanBlocks.metadata[metadata];
        }
        return super.getUnlocalizedName();
    }
    
}
