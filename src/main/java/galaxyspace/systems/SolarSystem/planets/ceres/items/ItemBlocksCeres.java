package galaxyspace.systems.SolarSystem.planets.ceres.items;

import galaxyspace.systems.SolarSystem.planets.ceres.blocks.CeresBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlocksCeres extends ItemBlock 
{
    public ItemBlocksCeres(Block block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    public String getUnlocalizedName(ItemStack is) {
        int metadata = is.getItemDamage();
        if (metadata >= 0 && metadata < CeresBlocks.metadata.length) {
            return "tile." + CeresBlocks.metadata[metadata];
        }
        return super.getUnlocalizedName();
    }
    
}
