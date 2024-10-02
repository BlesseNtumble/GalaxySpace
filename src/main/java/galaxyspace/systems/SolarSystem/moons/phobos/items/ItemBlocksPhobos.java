package galaxyspace.systems.SolarSystem.moons.phobos.items;

import galaxyspace.systems.SolarSystem.moons.phobos.blocks.PhobosBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlocksPhobos extends ItemBlock 
{
    public ItemBlocksPhobos(Block block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    public String getUnlocalizedName(ItemStack is) {
        int metadata = is.getItemDamage();
        if (metadata >= 0 && metadata < PhobosBlocks.metadata.length) {
            return "tile." + PhobosBlocks.metadata[metadata];
        }
        return super.getUnlocalizedName();
    }
    
}
