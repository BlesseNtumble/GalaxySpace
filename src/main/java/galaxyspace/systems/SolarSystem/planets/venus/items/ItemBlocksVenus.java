package galaxyspace.systems.SolarSystem.planets.venus.items;

import galaxyspace.systems.SolarSystem.planets.venus.blocks.VenusBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlocksVenus extends ItemBlock 
{
    public ItemBlocksVenus(Block block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    public String getUnlocalizedName(ItemStack is) {
        int metadata = is.getItemDamage();
        if (metadata >= 0 && metadata < VenusBlocks.metadata.length) {
            return "tile." + VenusBlocks.metadata[metadata];
        }
        return super.getUnlocalizedName();
    }
    
}
