package galaxyspace.systems.SolarSystem.planets.overworld.items;

import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockOres;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlocksOres extends ItemBlock 
{
    public ItemBlocksOres(Block block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    public String getUnlocalizedName(ItemStack is) {
        int metadata = is.getItemDamage();
        if (metadata >= 0 && metadata < BlockOres.metadata.length) {
            return "tile." + BlockOres.metadata[metadata];
        }
        return super.getUnlocalizedName();
    }
    
}
