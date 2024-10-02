package galaxyspace.systems.SolarSystem.planets.mars.items;


import galaxyspace.systems.SolarSystem.planets.mars.blocks.MarsOresBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlocksMarsOres extends ItemBlock 
{
    public ItemBlocksMarsOres(Block block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    public String getUnlocalizedName(ItemStack is) {
        int metadata = is.getItemDamage();
        if (metadata >= 0 && metadata < MarsOresBlocks.metadata.length) {
            return "tile.MarsOre" + MarsOresBlocks.metadata[metadata];
        }
        return super.getUnlocalizedName();
    }
    
}
