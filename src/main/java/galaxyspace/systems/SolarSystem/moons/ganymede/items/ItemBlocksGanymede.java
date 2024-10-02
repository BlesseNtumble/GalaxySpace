package galaxyspace.systems.SolarSystem.moons.ganymede.items;

import galaxyspace.systems.SolarSystem.moons.ganymede.blocks.GanymedeBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlocksGanymede extends ItemBlock 
{
    public ItemBlocksGanymede(Block block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    public String getUnlocalizedName(ItemStack is) {
        int metadata = is.getItemDamage();
        if (metadata >= 0 && metadata < GanymedeBlocks.metadata.length) {
            return "tile." + GanymedeBlocks.metadata[metadata];
        }
        return super.getUnlocalizedName();
    }
    
}
