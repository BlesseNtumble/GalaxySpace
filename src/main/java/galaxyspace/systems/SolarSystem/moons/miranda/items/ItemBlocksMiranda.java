package galaxyspace.systems.SolarSystem.moons.miranda.items;

import galaxyspace.systems.SolarSystem.moons.miranda.blocks.MirandaBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlocksMiranda extends ItemBlock 
{
    public ItemBlocksMiranda(Block block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    public String getUnlocalizedName(ItemStack is) {
        int metadata = is.getItemDamage();
        if (metadata >= 0 && metadata < MirandaBlocks.metadata.length) {
            return "tile." + MirandaBlocks.metadata[metadata];
        }
        return super.getUnlocalizedName();
    }
    
}
