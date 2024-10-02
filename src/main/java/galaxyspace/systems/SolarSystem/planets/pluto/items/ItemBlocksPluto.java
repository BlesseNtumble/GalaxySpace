package galaxyspace.systems.SolarSystem.planets.pluto.items;

import galaxyspace.systems.SolarSystem.planets.pluto.blocks.PlutoBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlocksPluto extends ItemBlock 
{
    public ItemBlocksPluto(Block block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    public String getUnlocalizedName(ItemStack is) {
        int metadata = is.getItemDamage();
        if (metadata >= 0 && metadata < PlutoBlocks.metadata.length) {
        	if(metadata >= 0 && metadata < 4)
        		return "tile." + PlutoBlocks.metadata[0];
            return "tile." + PlutoBlocks.metadata[metadata];
        }
        return super.getUnlocalizedName();
    }
    
}
