package galaxyspace.systems.SolarSystem.planets.mercury.items;

import galaxyspace.systems.SolarSystem.planets.mercury.blocks.MercuryBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlocksMercury extends ItemBlock 
{
    public ItemBlocksMercury(Block block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    public String getUnlocalizedName(ItemStack is) {
        int metadata = is.getItemDamage();
        if (metadata >= 0 && metadata < MercuryBlocks.metadata.length) {
            return "tile." + MercuryBlocks.metadata[metadata];
        }
        return super.getUnlocalizedName();
    }
    
}
